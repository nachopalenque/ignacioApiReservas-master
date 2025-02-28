package org.example.ignacioapireservas.Controllers;

import org.example.ignacioapireservas.Config.JwtTokenProvider;
import org.example.ignacioapireservas.DTO.LoginRequestDTO;
import org.example.ignacioapireservas.DTO.LoginResponseDTO;
import org.example.ignacioapireservas.DTO.UserRegisterDTO;
import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Entities.UserEntity;
import org.example.ignacioapireservas.Repositories.ClienteRepository;
import org.example.ignacioapireservas.Repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class AuthController {
    @Autowired
    private UserEntityRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ClienteRepository repositorioCliente;

    @PostMapping("/auth/register")
    public ResponseEntity<UserEntity> save(@RequestBody UserRegisterDTO userDTO) {

        Cliente cliente = new Cliente();
        cliente.setNombre(userDTO.getUsername());
        cliente.setEmail(userDTO.getEmail());
        cliente.setTelefono(userDTO.getTelefono());
        repositorioCliente.save(cliente);

        UserEntity userEntity = this.userRepository.save(
                UserEntity.builder()
                        .username(userDTO.getUsername())
                        .password(passwordEncoder.encode(userDTO.getPassword()))
                        .email(userDTO.getEmail())
                        .cliente(cliente)
                        .authorities(List.of("ROLE_USER", "ROLE_ADMIN"))
                        .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(userEntity);
        //otra forma es mandar una mapa string clave valor de respuesta
        //habria que cambiar ResponseEntity<UserEntity> por ResponseEntity<Map<String,String>>
        //return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("email",userEntity.getEmail()));

    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginDTO) {
        try {

            //Validamos al usuario en Spring (hacemos login manualmente)
            UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
            Authentication auth = authenticationManager.authenticate(userPassAuthToken);    //valida el usuario y devuelve un objeto Authentication con sus datos
            //Obtenemos el UserEntity del usuario logueado
            UserEntity user = (UserEntity) auth.getPrincipal();

            //Generamos un token con los datos del usuario (la clase tokenProvider ha hemos creado nosotros para no poner aquí todo el código
            String token = this.tokenProvider.generateToken(auth);

            //Devolvemos un código 200 con el username y token JWT
            return ResponseEntity.ok(new LoginResponseDTO(user.getUsername(),token));
        }catch (Exception e) {  //Si el usuario no es válido, salta una excepción BadCredentialsException
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of(
                            "path", "/auth/login",
                            "message", "Credenciales erróneas",
                            "timestamp", new Date()
                    )
            );
        }
    }
}