package cn.wwinter.springsecurity02_jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

/**
 * @Author: zhangdongdong
 * @CreateTime: 2023-02-07
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "423F4528482B4D6251655368566D597133743677397A24432646294A404E6352";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     *  取出token中的单个声明字段
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims allClaims = extractAllClaims(token);
        return claimsResolver.apply(allClaims);
    }

    /**
     *  取出token中所有的声明字段
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey()) // SigningKey密钥用于创建Jwt的签名部分，用于验证Jwt的发送者信息，并确保没有被更改过
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 生成token
     */
    public String generateToken() {
        return null;
    }

    /**
     *  获得SigningKey
     */
    private Key getSignInKey() {
        // 对我们的密钥进行解码
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // 使用hmacShaKeyFor登录算法
        return Keys.hmacShaKeyFor(keyBytes);
    }


}
