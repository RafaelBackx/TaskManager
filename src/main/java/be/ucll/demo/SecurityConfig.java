package be.ucll.demo;

import be.ucll.demo.DB.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SecurityConfig(PasswordEncoder encoder, UserService userService){
        this.passwordEncoder = encoder;
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userService)
                .passwordEncoder(this.passwordEncoder);
        auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder.encode("t")).roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/api/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/register").permitAll()
                .mvcMatchers(HttpMethod.POST, "/add").hasAuthority("ADMIN")
                .mvcMatchers("/add").hasAuthority("ADMIN")
                .mvcMatchers("/tasks/edit/{id}").hasAuthority("ADMIN")
                .mvcMatchers("/tasks/{id}/sub/create").hasAuthority("ADMIN")
                .mvcMatchers("/tasks/{id}/sub/edit/{subtaskid}").hasAuthority("ADMIN")
                .antMatchers("/resources/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().defaultSuccessUrl("/tasks",true)
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedHandler(new AuthenticationHandler());
    }


}
