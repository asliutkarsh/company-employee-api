package com.utkarsh.companyemployeeapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private static final String SECRET_KEY = "SecretKeyToGenJWTs";
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days


    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (header != null && header.startsWith("Bearer")){
            //Extracting jwt Token
            token = header.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                log.error("Unable to get JWT Token");
            }catch (ExpiredJwtException e){
                log.error("JWT Token has expired");
            }catch (MalformedJwtException e){
                log.error("Malformed JWT From Auth Filter");
            }
        }else {
            log.error("JWT Token does not begin with Bearer or is not present");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication()==null){
            if (jwtTokenUtil.validateToken(token, username)){
                var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, null);
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else throw new JwtException("Invalid Token");
        }
        filterChain.doFilter(request,response);

    }





}
