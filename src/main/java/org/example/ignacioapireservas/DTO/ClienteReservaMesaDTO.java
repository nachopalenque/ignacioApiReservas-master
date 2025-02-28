package org.example.ignacioapireservas.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ClienteReservaMesaDTO {
    public Long id_reserva;
    public String nombre;
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate fecha_reserva;
    public String hora_reserva;
    public Long numero_mesa;
    public Long numero_personas_reserva;
}
