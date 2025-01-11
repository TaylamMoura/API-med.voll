package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //com isso o spring sabe que tem que usar esse algoritmo (BCrypt) como hashing de senha

}
// o return configuration.getAuthenticationManager() esse metodo dabe criar o Authentication Manager

//O Bean exporta uma classe para o spring.

//    @Bean //expoe o retorno do metodo - o metodo devolve um objeto do tipo securityFilterChain (o bean diz isso ao spring)
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
//        return  http.csrf().disable()   //desabilita a proteção contra ataques do tipo CSRF (o token já é uma proteção)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //determina que seja um stateless por ser uma API REST
//                .and().build();
//    } --> versao desatualizada.

