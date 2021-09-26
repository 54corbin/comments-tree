package com.corbin.commenttree;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author corbin
 */
@MapperScan("com.corbin.commenttree.dao")
@SpringBootApplication
public class CommentsTreeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentsTreeBackendApplication.class, args);
    }

}
