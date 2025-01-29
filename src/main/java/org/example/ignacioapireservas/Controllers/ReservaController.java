package org.example.ignacioapireservas.Controllers;

import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Entities.Mesa;
import org.example.ignacioapireservas.Entities.Reserva;
import org.example.ignacioapireservas.Repositories.ClienteRepository;
import org.example.ignacioapireservas.Repositories.MesaRepository;
import org.example.ignacioapireservas.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;
import java.util.Optional;

@RestController
public class ReservaController {

    @Autowired
    public ReservaRepository reservaRepository;
    @Autowired
    public ClienteRepository clienteRepository;
    @Autowired
    public MesaRepository mesaRepository;

    @GetMapping("/reservas")
    public ResponseEntity<List<Reserva>> dameReservas() {
        return ResponseEntity.ok(reservaRepository.findAll());
    }

    @GetMapping("/reserva/{id}")
    public ResponseEntity<Reserva> dameReservaId(@PathVariable Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        if (reserva.isPresent()) {
            return ResponseEntity.ok(reserva.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> insertaReserva(@RequestBody Reserva reserva) {

        //obtenemos los datos del cliente y de la mesa para posteriormente comprobar si existen
        Optional<Cliente> cliente = clienteRepository.findById(reserva.getCliente().getId());
        Optional<Mesa> mesa = mesaRepository.findById(reserva.getMesa().getId());
        //si el cliente y la mesa existe procedemos a realizar la reserva
        if (cliente.isPresent() && mesa.isPresent()) {
            List<Reserva> reservasDelDia = reservaRepository.findByfechaReserva(reserva.getFechaReserva());

            for (Reserva res : reservasDelDia) {
                if(res.getMesa() == reserva.getMesa() && res.getHoraReserva() == reserva.getHoraReserva()) {

                    return ResponseEntity.notFound().build();
                }
            }

            reserva.setMesa(mesa.get());
            reserva.setCliente(cliente.get());
            return ResponseEntity.ok(reservaRepository.save(reserva));

        }else{
            //si el cliente o la mesa no existe no se puede realizar la reserva
            return ResponseEntity.notFound().build();
        }

    }


}