package org.example.ignacioapireservas.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del cliente no puede estar en blanco")
    @Size(min = 3,message = "El nombre del cliente debe tener al menos 3 caracteres")
    private String nombre;

    @Column(unique = true)
    @Email
    private String email;

    private String telefono;

    @OneToMany(targetEntity = Reserva.class, cascade = CascadeType.ALL,
            mappedBy = "cliente")
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private UserEntity usuario;


}