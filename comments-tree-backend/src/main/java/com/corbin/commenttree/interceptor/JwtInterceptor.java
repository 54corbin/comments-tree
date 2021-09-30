package com.corbin.commenttree.interceptor;

import com.alibaba.fastjson.JSON;
import com.corbin.commenttree.annotation.AuthRequired;
import com.corbin.commenttree.bean.dto.PayloadDto;
import com.corbin.commenttree.bean.vo.RestResult;
import com.corbin.commenttree.exception.JwtExpiredException;
import com.corbin.commenttree.exception.JwtInvalidException;
import com.corbin.commenttree.service.JwtTokenService;
import com.corbin.commenttree.util.ThreadLocalUtil;
import com.nimbusds.jose.JOSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.ParseException;

import static com.corbin.commenttree.bean.vo.RestCodeEnum.TOKEN_EXPIRED;

/**
 * @author corbin
 */
@Slf4j
public class JwtInterceptor extends HandlerInterceptorAdapter {


    @Autowired
    private JwtTokenService jwtTokenService;

    @Value("${jwt.header-name}")
    private String headerName;

    @Value("${jwt.secret}")
    private String secret;

    /**
     * 预处理回调方法，实现处理器的预处理（如检查登陆），第三个参数为响应的处理器，自定义 Controller
     * 返回值：
     * true 表示继续流程（如调用下一个拦截器或处理器）；
     * false 表示流程中断（如登录检查失败），不会继续调用其他的拦截器或处理器，此时我们需要通过 response 来产生响应。
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有 @AuthRequired 注解，有且 required() 为 false 则跳过
        if (!method.isAnnotationPresent(AuthRequired.class)) {
            return true;
        }

        String token = request.getHeader(headerName);

        if (StringUtils.isEmpty(token) ) {
            RestResult<Object> resp = RestResult.fail("header中" + headerName + "不能为空！");
            response.getOutputStream().write(JSON.toJSONString(resp).getBytes());
            return false;
        }

        try {
            PayloadDto payloadDto = jwtTokenService.verifyTokenByHMAC(token, DigestUtils.md5DigestAsHex(secret.getBytes()));
            ThreadLocalUtil.set("jwtPayload",payloadDto);
            return true;
        }catch (ParseException | JOSEException | JwtInvalidException | JwtExpiredException e) {
            log.error("verifyTokenByHMAC:",e);
            RestResult<Object> resp = RestResult.fail(TOKEN_EXPIRED.message,TOKEN_EXPIRED.code);
            response.getOutputStream().write(JSON.toJSONString(resp).getBytes());
            return false;
        }

    }

}
