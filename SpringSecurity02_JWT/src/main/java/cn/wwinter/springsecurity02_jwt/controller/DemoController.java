package cn.wwinter.springsecurity02_jwt.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo
 * @Author: zhangdongdong
 * @CreateTime: 2023-02-07
 */
@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello wwinter!");
    }

    @GetMapping("goodbye")
    public ResponseEntity<String> goodBye() {
        return ResponseEntity.ok("good bye ~");
    }

}
