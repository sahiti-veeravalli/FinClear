package com.finclear.security;
import jakarta.servlet.*; import jakarta.servlet.http.*; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.stereotype.Component; import org.springframework.web.filter.OncePerRequestFilter; import java.io.IOException; import java.util.*;
@Component public class JwtFilter extends OncePerRequestFilter{
 private final JwtService jwt; public JwtFilter(JwtService jwt){this.jwt=jwt;}
 protected void doFilterInternal(HttpServletRequest r,HttpServletResponse s,FilterChain f)throws IOException,ServletException{
  String h=r.getHeader("Authorization"); if(h!=null&&h.startsWith("Bearer ")) try{var c=jwt.parse(h.substring(7));var role=(String)c.get("role");SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(c.getSubject(),null,List.of(new SimpleGrantedAuthority("ROLE_"+role))));}catch(Exception ignored){}
  f.doFilter(r,s);
 }
}
