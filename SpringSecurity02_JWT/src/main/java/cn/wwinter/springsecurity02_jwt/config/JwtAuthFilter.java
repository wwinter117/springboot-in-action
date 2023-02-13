package cn.wwinter.springsecurity02_jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 过滤器，用于检查请求中是否包含JWT token
 * @Author: zhangdongdong
 * @CreateTime: 2023-02-07
 */
@Component
@RequiredArgsConstructor // 用于生成包含 final 和 @NonNull 注解的成员变量的构造方法
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * 过滤请求
     * @param: [request, response, filterChain]
     * @return: void
     **/
    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain) throws jakarta.servlet.ServletException, IOException {

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

        // 标头中有Jwt，提取出来（从"Bearer "之后开始）
        jwt = authHeader.substring(7);

        // 从JWT中提取出userEmail
        userEmail = jwtService.extractUsername(jwt);

        // 开始验证流程（userEmail非空，且此前未经过验证）
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // 更新context
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 验证通过
        filterChain.doFilter(request, response);

    }
}
