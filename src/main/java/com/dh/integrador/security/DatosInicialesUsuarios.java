package com.dh.integrador.security;

import com.dh.integrador.entity.Usuario;
import com.dh.integrador.entity.UsuarioRole;
import com.dh.integrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesUsuarios implements ApplicationRunner {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder cifrador= new BCryptPasswordEncoder();
        String passSinCifrar="123";
        String passCifrada=cifrador.encode(passSinCifrar);
        Usuario usuarioAInsertar=new Usuario("jhont","jhont3","jhont@gmail.com",
                passCifrada, UsuarioRole.ROLE_USER);
        usuarioRepository.save(usuarioAInsertar);

        String passCifrada2=cifrador.encode("DH123");
        usuarioAInsertar= new Usuario("Lion","Lion7","admin@gmail.com",
                passCifrada2,UsuarioRole.ROLE_ADMIN);
        usuarioRepository.save(usuarioAInsertar);
    }
}
