package org.example.ignacioapireservas.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
    private String nombre;

    @Column(unique = true)
    @Email
    private String email;

    @OneToMany(targetEntity = Reserva.class, cascade = CascadeType.ALL,
            mappedBy = "cliente")
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();


}