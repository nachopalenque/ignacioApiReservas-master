package org.example.ignacioapireservas.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClienteReservaMesaDTO {
    public String nombre;
    public String email;
    public LocalDate fecha_reserva;
    public Double hora_reserva;
    public Long numero_mesa;
    public Long numero_personas_reserva;
}
