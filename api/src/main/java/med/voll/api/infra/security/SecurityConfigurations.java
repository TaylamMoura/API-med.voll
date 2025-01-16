package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return
                http.csrf(csrf -> csrf.disable())
                        .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        .authorizeHttpRequests(req -> {
                            req.requestMatchers("/login").permitAll();
                            req.requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll();
                            req.anyRequest().authenticated();
                        })
                        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //chama nosso filtro 1º, e depois o Username(que é do spring)
                        .build();
    }
  //  http://server:port/context-path/v3/api-docs
   // http://server:port/context-path/swagger-ui.html

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

// ----> SOMENTE ADMINS PODEM EXCLUIR USUARIO, SE FAZ ASSIM:
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().authorizeHttpRequests()
//                .requestMatchers(HttpMethod.POST, "/login").permitAll()
//                .requestMatchers(HttpMethod.DELETE, "/medicos").hasRole("ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/pacientes").hasRole("ADMIN")
//                .anyRequest().authenticated()
//                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
//                .build();
//    }
    //---> Aqui suponhamos que na aplicação tenhamos um perfil de acesso chamado de ADMIN, sendo que somente
    // usuários com esse perfil possam excluir médicos e pacientes.




// o return configuration.getAuthenticationManager() esse metodo dabe criar o Authentication Manager

//O Bean exporta uma classe para o spring.

//    @Bean //expoe o retorno do metodo - o metodo devolve um objeto do tipo securityFilterChain (o bean diz isso ao spring)
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
//        return  http.csrf().disable()   //desabilita a proteção contra ataques do tipo CSRF (o token já é uma proteção)
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //determina que seja um stateless por ser uma API REST
//                .and().build();
//    } --> versao desatualizada.

