package org.example.ignacioapireservas.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "horario")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "El tramo horario no puede estar en blanco")
    private String tramoHorario;
    @Size(max=1000)
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String descripcion;

    @OneToMany(targetEntity = Reserva.class, cascade = CascadeType.ALL,
            mappedBy = "horario")
    @JsonIgnore
    private List<Reserva> reservas = new ArrayList<>();

}
