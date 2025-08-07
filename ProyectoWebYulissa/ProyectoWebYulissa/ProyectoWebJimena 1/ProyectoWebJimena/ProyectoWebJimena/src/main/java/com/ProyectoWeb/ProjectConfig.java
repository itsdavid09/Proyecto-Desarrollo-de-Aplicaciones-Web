package com.ProyectoWeb;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class ProjectConfig implements WebMvcConfigurer {

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("es"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean("messageSource")
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/registro/nuevo").setViewName("/registro/nuevo");
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests((request) -> request
//                .requestMatchers("/", "/index", "/errores/**",
//                        "/planes/**", "/pruebas/**", "/tablas/**",
//                        "/registro/**", "/js/**", "/webjars/**",
//                        "/cuenta/**", "/layout/**", "/soporte/**")
//                .permitAll()
//                .requestMatchers(
//                        "/producto/nuevo", "/producto/guardar",
//                        "/producto/modificar/**", "/producto/eliminar/**",
//                        "/categoria/nuevo", "/categoria/guardar",
//                        "/categoria/modificar/**", "/categoria/eliminar/**",
//                        "/usuario/nuevo", "/usuario/guardar",
//                        "/usuario/modificar/**", "/usuario/eliminar/**",
//                        "/reportes/**"
//                ).hasRole("ADMIN")
//                .requestMatchers(
//                        "/producto/listado",
//                        "/categoria/listado",
//                        "/usuario/listado"
//                ).hasAnyRole("ADMIN", "VENDEDOR")
//                .requestMatchers("/facturar/carrito")
//                )
//                .formLogin((form) -> form
//                .loginPage("/login").permitAll())
//                .logout((logout) -> logout.permitAll());
        return http.build();
    }
}