package com.company.config;

import com.company.dto.ProfileJwtDto;
import com.company.enums.ProfileRole;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header= (String) request.getHeader("Authorization");
        if (header==null||header.isEmpty()||!header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        ProfileJwtDto jwtDto= JwtUtil.decodeJwt(header.split(" ")[1]);
        if (jwtDto==null){
            filterChain.doFilter(request,response);
            return;
        }
        String username=jwtDto.getEmail();
        UserDetails userDetails=customUserDetailService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }
}
