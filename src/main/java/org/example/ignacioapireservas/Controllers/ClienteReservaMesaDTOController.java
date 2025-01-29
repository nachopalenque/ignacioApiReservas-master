package org.example.ignacioapireservas.Controllers;
import org.example.ignacioapireservas.DTO.ClienteReservaMesaDTO;
import org.example.ignacioapireservas.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClienteReservaMesaDTOController {
    @Autowired
    ReservaRepository reservaRepository;

    @GetMapping("/ReservasFecha/{fecha_reserva}")
    public ResponseEntity<List<ClienteReservaMesaDTO>>dameReservasClientesFecha(@PathVariable LocalDate fecha_reserva){
        List<ClienteReservaMesaDTO> listaReservas = new ArrayList<>();
        /*obtenemos todas las reservas de la fecha del path {fecha_reserva}
         mientras que recorremos todas las fechas obtenidas vamos rellenando la lista
         de objetos ClienteReservaMesaDTO. */

        reservaRepository.findByfechaReserva(fecha_reserva).forEach(
                reserva -> {
                    listaReservas.add(ClienteReservaMesaDTO.builder()
                    .nombre(reserva.getCliente().getNombre())
                    .email(reserva.getCliente().getEmail())
                    .fecha_reserva(reserva.getFechaReserva())
                    .hora_reserva(reserva.getHoraReserva())
                    .numero_mesa(reserva.getMesa().getNumeroMesa())
                    .numero_personas_reserva(reserva.getNumeroPersonas())
                    .build());
        });

        return ResponseEntity.ok(listaReservas);
    }
}
