package com.project.back_end.security;
import com.project.back_end.services.TokenService;
import com.project.back_end.repo.UserAccountRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import java.util.List;
@Configuration public class SecurityConfig {
@Bean PasswordEncoder encoder(){return new BCryptPasswordEncoder();}
@Bean SecurityFilterChain filterChain(HttpSecurity http,TokenService tokens,UserAccountRepository users)throws Exception {
OncePerRequestFilter jwtFilter=new OncePerRequestFilter(){@Override protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest req,jakarta.servlet.http.HttpServletResponse res,jakarta.servlet.FilterChain chain)throws java.io.IOException,jakarta.servlet.ServletException{
String h=req.getHeader("Authorization");if(h!=null&&h.startsWith("Bearer ")){try{String token=h.substring(7);String email=tokens.email(token);String role=tokens.role(token);if(users.findByEmailIgnoreCase(email).filter(u->u.role.name().equals(role)).isPresent()){var a=new UsernamePasswordAuthenticationToken(email,null,List.of(new SimpleGrantedAuthority("ROLE_"+role)));org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(a);}}catch(Exception ignored){org.springframework.security.core.context.SecurityContextHolder.clearContext();}}
chain.doFilter(req,res);}};
return http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
.exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->res.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
.authorizeHttpRequests(a->a.requestMatchers("/","/*.html","/css/**","/js/**","/api/auth/login","/api/auth/register","/actuator/health").permitAll()
.requestMatchers(HttpMethod.GET,"/api/doctors","/api/doctors/*","/api/doctors/search","/api/doctors/by-name").permitAll()
.requestMatchers("/api/admin/**").hasRole("ADMIN")
.requestMatchers("/api/doctor/**").hasRole("DOCTOR")
.requestMatchers("/api/patient/**").hasRole("PATIENT")
.anyRequest().authenticated())
.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class).build();}
}
