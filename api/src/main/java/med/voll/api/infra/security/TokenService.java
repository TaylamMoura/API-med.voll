package med.voll.api.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import med.voll.api.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}") //nome do parametro no properties, para o spring sabe aonde pegar o dado
    private String secret;

    public String gerarToken(Usuario usuario) {  //é um String pq vai retornar um token
        try {
            RSAPublicKey rsaPublicKey;
            var algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API Voll.med") //info que vai dentro do token, de quem está gerando o token.
                    .withSubject(usuario.getLogin())  //info de quem é o usuario relacionado ao token
                    .withExpiresAt(dataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar token jwt", exception);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

//precisa ficar dentro de um try/catch pq essa classe lança exceções

//.withSubject(usuario.getLogin())  //info de quem é o usuario relacionado ao token
//.withClaim("id", usuario.getId())  //guarda o id do usuario

//o token precisa de uma data de expiração para não funcionar para sempre.

//O Instant é uma classe do pacote java.time que representa um ponto específico na linha do tempo, com precisão de nanosegundos.
// Ele é usado para representar um momento exato,
//toInstant(ZoneOffset.of("-03:00")) = fica de acordo com o fuso horario do Brasil