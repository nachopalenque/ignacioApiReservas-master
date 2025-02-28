package org.example.ignacioapireservas.Controllers;
import org.example.ignacioapireservas.DTO.ClienteReservaMesaDTO;
import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Repositories.ClienteRepository;
import org.example.ignacioapireservas.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ClienteReservaMesaDTOController {
    @Autowired
    ReservaRepository reservaRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @GetMapping("/ReservasFecha/{fecha_reserva}")
    public ResponseEntity<List<ClienteReservaMesaDTO>>dameReservasClientesFecha(@PathVariable LocalDate fecha_reserva){
        List<ClienteReservaMesaDTO> listaReservas = new ArrayList<>();
        /*obtenemos todas las reservas de la fecha del path {fecha_reserva}
         mientras que recorremos todas las fechas obtenidas vamos rellenando la lista
         de objetos ClienteReservaMesaDTO. */

        reservaRepository.findByfechaReserva(fecha_reserva).forEach(
                reserva -> {
                    listaReservas.add(ClienteReservaMesaDTO.builder().id_reserva(reserva.getId())
                    .nombre(reserva.getCliente().getNombre())
                    .fecha_reserva(reserva.getFechaReserva())
                    .hora_reserva(reserva.getHorario().getTramoHorario())
                    .numero_mesa(reserva.getMesa().getNumeroMesa())
                    .numero_personas_reserva(reserva.getNumeroPersonas())
                    .build());
        });

        return ResponseEntity.ok(listaReservas);
    }

    @GetMapping("/ReservasUsername/{username}")
    public ResponseEntity<List<ClienteReservaMesaDTO>>dameReservasClientesEmail(@PathVariable String username){
        List<ClienteReservaMesaDTO> listaReservas = new ArrayList<>();
        Cliente cliente =  clienteRepository.findByNombre(username);
        /*obtenemos todas las reservas del email {fecha_reserva}
         mientras que recorremos todas las fechas obtenidas vamos rellenando la lista
         de objetos ClienteReservaMesaDTO. */


        if(cliente != null) {

            reservaRepository.findByCliente(cliente).forEach(
                    reserva -> {
                        listaReservas.add(ClienteReservaMesaDTO.builder().id_reserva(reserva.getId())
                                .nombre(reserva.getCliente().getNombre())
                                .fecha_reserva(reserva.getFechaReserva())
                                .hora_reserva(reserva.getHorario().getTramoHorario())
                                .numero_mesa(reserva.getMesa().getNumeroMesa())
                                .numero_personas_reserva(reserva.getNumeroPersonas())
                                .build());
                    });

            return ResponseEntity.ok(listaReservas);


        }
        else{


            return ResponseEntity.notFound().build();
        }


    }

}
