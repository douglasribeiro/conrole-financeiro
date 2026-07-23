package com.developer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev") // 💡 Só será ativado se você rodar com o profile "dev"
public class SecurityDevConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desativa CSRF para facilitar testes locais
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // 🔓 Libera TODOS os endpoints sem autenticação
            );
        return http.build();
    }
}
