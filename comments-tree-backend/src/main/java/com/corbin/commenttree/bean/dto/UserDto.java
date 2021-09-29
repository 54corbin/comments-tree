package com.corbin.commenttree.bean.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 对应用户表
 * @author corbin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    /**
     * 用户名
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}", message = "用户名：只能使用长度在5~20之间的字母和数字")
    private String username;

    /**
     * 邮箱
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "邮箱:地址格式不合法")
    private String email;

    /**
     * 密码
     */
    @NotBlank
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[`~$@!%*#?&])[A-Za-z\\d`~$@!%*#?&]{8,20}$", message = "密码:应符合长度在8~20之间，至少包含一个大写、一个小写、一个数字、一个特殊符号")
    private String password;

}
