package med.voll.api.infra.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GeradorDeSenha{
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String senha = "123456"; // Substitua pela sua senha
        String senhaHash = encoder.encode(senha);
        System.out.println(senhaHash);
    }
}