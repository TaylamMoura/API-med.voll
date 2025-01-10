package med.voll.api.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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


//    @Bean //expoe o retorno do metodo - o metodo devolve um objeto do tipo securityFilterChain (o bean diz isso ao spring)
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
//        return  http.csrf().disable()   //desabilita a proteção contra ataques do tipo CSRF (o token já é uma proteção)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //determina que seja um stateless por ser uma API REST
//                .and().build();
//    } --> versao desatualizada.
}
