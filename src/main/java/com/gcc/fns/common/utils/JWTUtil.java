package com.gcc.fns.common.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author xiaozhi
 * @description jwt工具类
 * @create 2022-10-2022/10/24 21:15
 */
@ConfigurationProperties(prefix = "custom.jwt")
@Component
@Data
public class JWTUtil {

    /**
     * 主体
     */
    private String subject;

    /**
     * 秘钥
     */
    private String secret;

    /**
     * 过期时间（天）
     */
    private int expiration;

    /**
     * 创建token
     */
    public String createToken(Long userId, String username) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(DateUtils.addDays(new Date(), expiration))
                .claim("userId", userId)
                .claim("username", username)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * 获取用户ID
     */
    public Long getUserId(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = jws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    /**
     * 获取username
     */
    public String getUsername(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = jws.getBody();
        return (String) claims.get("username");
    }

    /**
     * 获取过期时间
     */
    public Date getTokenExpiration(String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }
        Jws<Claims> jws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        Claims claims = jws.getBody();
        return claims.getExpiration();
    }

}
