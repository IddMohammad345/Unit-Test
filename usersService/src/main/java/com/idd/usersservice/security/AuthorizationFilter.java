package com.idd.usersservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;


public class AuthorizationFilter extends BasicAuthenticationFilter {
    private Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment = environment;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));
        if (authorizationHeader == null ||
                !authorizationHeader.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /*private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader= request.getHeader(environment.getProperty("authorization.token.header.name"));
        if (authorizationHeader == null){
            return null;
        }
        String token=authorizationHeader.replace(environment.getProperty("authorization.token.header.prefix"),"").trim();
        String tokenSecret=environment.getProperty("token.secret");

        if (tokenSecret==null){
            return null;
        }
        byte[] secretKeyBytes= Base64.getDecoder().decode(tokenSecret.getBytes());
        SecretKey secretKey= Keys.hmacShaKeyFor(secretKeyBytes);
        JwtParser jwtParser=Jwts.parser()
                .verifyWith(secretKey)
                .buildd();

        Jwt<?,?>jwtObject=jwtParser.parse(token);
       String userId= ((Claims)jwtObject.getPayload()).getSubject();
       if (userId==null){
           return null;
       }
       return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());
    }*/

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(environment.getProperty("authorization.token.header.name"));

        if (authorizationHeader == null) {
            return null;
        }

        String token = authorizationHeader
                .replace(environment.getProperty("authorization.token.header.prefix"), "")
                .trim();

        // Fix: Correctly retrieve and declare tokenSecret
        String tokenSecret = environment.getProperty("token.secret");

        if (tokenSecret == null || tokenSecret.isEmpty()) {
            return null;
        }

        try {
            // Fix: Use Base64 to decode the secret string for HMAC
            byte[] secretKeyBytes = Base64.getDecoder().decode(tokenSecret);
            SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

            // Fix: Correct JJWT 0.12.x syntax (removed .buildd() typo)
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token) // Parses the JWS
                    .getPayload();

            String userId = claims.getSubject();

            if (userId == null) {
                return null;
            }

            return new UsernamePasswordAuthenticationToken(userId, null, new ArrayList<>());
        } catch (Exception e) {
            // Return null if token is expired, tampered, or invalid
            return null;
        }
    }
}
