package org.example.ignacioapireservas.Controllers;

import jakarta.validation.Valid;
import org.example.ignacioapireservas.DTO.ClienteReservaMesaDTO;
import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Entities.Mesa;
import org.example.ignacioapireservas.Entities.Reserva;
import org.example.ignacioapireservas.Repositories.ClienteRepository;
import org.example.ignacioapireservas.Repositories.MesaRepository;
import org.example.ignacioapireservas.Repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    @GetMapping("/reservas/paginacion")
    public ResponseEntity<Page<Reserva>> dameClientesPaginacion(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Reserva> reservas = reservaRepository.findAll(pageable);
        return ResponseEntity.ok(reservas);
    }

    @PostMapping("/reserva")
    public ResponseEntity<Reserva> insertaReserva(@RequestBody @Valid Reserva reserva) {

        //obtenemos los datos del cliente y de la mesa para posteriormente comprobar si existen
        Optional<Cliente> cliente = clienteRepository.findById(reserva.getCliente().getId());
        Optional<Mesa> mesa = mesaRepository.findById(reserva.getMesa().getId());
        //si el cliente y la mesa existe procedemos a realizar la reserva
        if (cliente.isPresent() && mesa.isPresent()) {
            List<Reserva> reservasDelDia = reservaRepository.findByfechaReserva(reserva.getFechaReserva());

            for (Reserva res : reservasDelDia) {
                if(res.getMesa().getId() == reserva.getMesa().getId() && res.getHoraReserva() == reserva.getHoraReserva()) {

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

    @DeleteMapping("/reserva/{id}")
    public ResponseEntity<Void> eliminaReservaId(@PathVariable Long id){
        Optional<Reserva> reserva = reservaRepository.findById(id);

        if(reserva.isPresent()){
            reservaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping("/reserva/{id}")
    public ResponseEntity<Reserva> actualizaReservaId(@PathVariable Long id, @RequestBody Reserva nuevaReserva){
        Reserva reserva = reservaRepository.findById(id).get();
        Optional<Mesa> mesa = mesaRepository.findById(nuevaReserva.getMesa().getId());
        Optional<Cliente> cliente = clienteRepository.findById(nuevaReserva.getCliente().getId());

        if(cliente.isPresent() && mesa.isPresent()){
            reserva.setMesa(mesa.get());
            reserva.setCliente(cliente.get());
            reserva.setFechaReserva(nuevaReserva.getFechaReserva());
            reserva.setHoraReserva(nuevaReserva.getHoraReserva());
            return ResponseEntity.ok(reservaRepository.save(reserva));

        }else{
            return ResponseEntity.notFound().build();
        }

    }

}