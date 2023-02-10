package cn.wwinter.springsecurity02_jwt.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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
     * 生成token（根据claim和user）
     */
    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SignatureAlgorithm.HS256, getSignInKey())
                .compact();
    }

    /**
     * 生成token（根据user）
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * 检查token是否合法
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * 判断token是否过期
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 提取过期时间
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *  获得SigningKey
     */
    private Key getSignInKey() {
        // 进行解码
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        // 解码后生成的字节数组使用hmacShaKeyFor登录算法生成密钥，返回
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
