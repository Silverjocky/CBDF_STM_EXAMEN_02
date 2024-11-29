package com.upiiz.suppliers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // Configuración de la seguridad personalizada
        return httpSecurity
                .csrf(csrf -> csrf
                                .ignoringRequestMatchers("/api/v1/suppliers/**") // Ignorar CSRF en rutas
                        // específicas
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    http
                            .requestMatchers("/v3/api-docs/**").permitAll()
                            .requestMatchers("/swagger-ui/**").permitAll()
                            .requestMatchers("/swagger-resources/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/v1/suppliers/**").hasAnyAuthority("READ")
                            .requestMatchers(HttpMethod.POST, "/api/v1/suppliers/**").hasAnyAuthority("CREATE")
                            .requestMatchers(HttpMethod.PUT, "/api/v1/suppliers/**").hasAnyAuthority("UPDATE")
                            .requestMatchers(HttpMethod.DELETE, "/api/v1/suppliers/**").hasAnyAuthority("DELETE")
                            .anyRequest().denyAll();
                })
                .build();
    }

    //authentication manager - Lo obtenemos de una instancia que ya existe
    public AuthenticationManager authenticationManager() throws Exception{
        return  authenticationConfiguration.getAuthenticationManager();
    }

    // authentication provider - DAO - Va a proporcionar la autenticacion
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        return daoAuthenticationProvider;
    }

    // Password encoder
    public PasswordEncoder passwordEncoder(){
        //return  new BCryptPasswordEncoder();
        return  NoOpPasswordEncoder.getInstance();
    }

    // UserDetailsService - base de datos o usuarios en memoria
    @Bean
    public UserDetailsService userDetailsService(){
        // Definir usuario en memoria por el momento
        UserDetails usuarioSalvador = User.withUsername("Salvador").password(passwordEncoder().encode("chava")).roles("ADMIN").authorities(getAuthorities("ADMIN")).build();
        UserDetails usuarioRaul = User.withUsername("Raul").password(passwordEncoder().encode("raul")).roles("USER").authorities(getAuthorities("USER")).build();
        UserDetails usuarioGuille = User.withUsername("Guille").password(passwordEncoder().encode("guille")).roles("MODERATOR").authorities(getAuthorities("MODERATOR")).build();
        UserDetails usuarioRefugio = User.withUsername("Refugio").password(passwordEncoder().encode("refugio")).roles("EDITOR").authorities(getAuthorities("EDITOR")).build();
        UserDetails usuarioJose = User.withUsername("Jose").password(passwordEncoder().encode("jose")).roles("DEVELOPER").authorities(getAuthorities("DEVELOPER")).build();
        UserDetails usuarioEfrain = User.withUsername("Efrain").password(passwordEncoder().encode("efrain")).roles("ANALYST").authorities(getAuthorities("ANALYST")).build();

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(usuarioSalvador);
        userDetailsList.add(usuarioRaul);
        userDetailsList.add(usuarioGuille);
        userDetailsList.add(usuarioRefugio);
        userDetailsList.add(usuarioJose);
        userDetailsList.add(usuarioEfrain);
        return new InMemoryUserDetailsManager(userDetailsList);
    }

    private String[] getAuthorities(String role) {
        switch (role) {
            case "ADMIN":
                return new String[]{"READ", "CREATE", "UPDATE", "DELETE"};
            case "USER":
                return new String[]{"READ"};
            case "MODERATOR":
                return new String[]{"READ", "UPDATE"};
            case "EDITOR":
                return new String[]{"UPDATE"};
            case "DEVELOPER":
                return new String[]{"READ", "CREATE", "UPDATE", "DELETE", "CREATE-USER"};
            case "ANALYST":
                return new String[]{"READ", "DELETE"};
            default:
                return new String[]{};
        }
    }
}