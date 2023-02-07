package cn.wwinter.springsecurity02_jwt.config;

import cn.wwinter.springsecurity02_jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤器，用于检查请求中是否包含JWT token
 *
 * @Author: zhangdongdong
 * @CreateTime: 2023-02-07
 */
@Component
@RequiredArgsConstructor // 用于生成包含 final 和 @NonNull 注解的成员变量的构造方法
public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    /**
     *
     * @param: [request, response, filterChain]
     * @return: void
     **/
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // 当我们进行一次调用时，需要在标头中传递JWT，该变量用于提取这个标头
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // 开始检查JWT是否存在
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // 不存在，则继续执行剩下的过滤器
            filterChain.doFilter(request, response);
            return;
        }

        // 标头中有Jwt，提取出来（从"Bearer"之后开始）
        jwt = authHeader.substring(7);

        // TODO: 从JWT中提取出userEmail
        userEmail = jwtService.extractUsername(jwt);

        
    }
    
    
    
}