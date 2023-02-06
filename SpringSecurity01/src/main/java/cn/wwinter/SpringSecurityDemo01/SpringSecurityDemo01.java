package cn.wwinter.SpringSecurityDemo01;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  启动类
 *
 * @Author zhangdongdong
 * @Date 2023/2/6
 */
@SpringBootApplication
@MapperScan("cn.wwinter.SpringSecurityDemo01.mapper")
public class SpringSecurityDemo01 {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemo01.class, args);
    }
}
