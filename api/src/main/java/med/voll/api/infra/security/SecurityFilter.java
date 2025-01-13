package med.voll.api.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = recuperarToken(request); //pega o token do cabeçalho

        if(tokenJWT != null){
            var subject = tokenService.getSubject(tokenJWT);
            var usuario = repository.findByLogin(subject); // recupera o login do usuario no cabeçakho

            var authetication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()); //cria o dto que representa o usuario, e força a requisição
            SecurityContextHolder.getContext().setAuthentication(authetication);  //aqui, diz ao spring que o usuario ja esta logado
        }
        filterChain.doFilter(request, response); //chama o filter na proxima requisição
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization"); //recupera o token do usuario
        if (authorizationHeader != null){
            return authorizationHeader.replace("Bearer ", ""); //apagar o prefixo Baarer no print
        }
        return null;
    }
}


//@Component faz o spring carregar uma classe/componente generico
//OncePerRequestFilter = classe do spring que garante que ela vai ser executada uma unica vez a cada requisição

//  PQ PEGAR O TOKEN DO USUARIO:
//Ao autentica o usuario, ele recebe um token que contém informações sobre sua identidade e permissões.
// Ao incluir esse token nas requisições, conseguimos verificar se o usuário está autorizado a acessar
// determinadas funcionalidades ou dados.

//Além disso, o uso do token permite que a API não precise armazenar informações de
// sessão no servidor, tornando o sistema mais escalável e seguro.
