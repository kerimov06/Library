package com.turan.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        String username;
        String token;
        String header;


         header = request.getHeader("Authorization");

          if (header.isEmpty()){
               filterChain.doFilter(request,response);
               return;
          }

          token = header.substring(7);

         try {
             username = jwtToken.getUsernameByToken(token);

              if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null ) {
                  UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                  if (userDetails!=null&&jwtToken.isTokenValid(token)){
                     UsernamePasswordAuthenticationToken authentication =
                             new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());
                      authentication.setDetails(userDetails);

                       SecurityContextHolder.getContext().setAuthentication(authentication);
                  }
              }


         }catch (Exception e ){
              throw new RuntimeException("Have some problems");
         }

         filterChain.doFilter(request,response);
    }

}
