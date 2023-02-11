package cn.wwinter.springsecurity02_jwt.auth;

import cn.wwinter.springsecurity02_jwt.config.JwtService;
import cn.wwinter.springsecurity02_jwt.user.Role;
import cn.wwinter.springsecurity02_jwt.user.User;
import cn.wwinter.springsecurity02_jwt.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author zhangdongdong
 * @Date 2023/2/10
 */
@Service
public class AuthenticationService {

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 创建一个用户，保存至数据库，并返回token
     */
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * 验证
     */
    public AuthenticationResponse authentication(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
