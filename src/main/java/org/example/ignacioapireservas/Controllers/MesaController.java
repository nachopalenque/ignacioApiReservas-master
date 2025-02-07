package org.example.ignacioapireservas.Controllers;


import jakarta.validation.Valid;
import org.example.ignacioapireservas.Entities.Mesa;
import org.example.ignacioapireservas.Repositories.MesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class MesaController {
    @Autowired
    private MesaRepository mesaRepository;


    @GetMapping("/mesas")
    public ResponseEntity<List<Mesa>> dameMesas(){
        List<Mesa> mesas = mesaRepository.findAll();
        return ResponseEntity.ok(mesas);
    }

    @GetMapping("/mesa/{id}")
    public ResponseEntity<Mesa> dameMesa(@PathVariable Long id){

        Optional<Mesa> mesa = mesaRepository.findById(id);
        if(mesa.isPresent()){

            return ResponseEntity.ok(mesa.get());

        }else{
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping("/mesa")
    public ResponseEntity<Mesa> insertaMesa(@RequestBody @Valid Mesa mesa){
        var mesaNueva = mesaRepository.save(mesa);
        return ResponseEntity.status(HttpStatus.CREATED).body(mesaNueva);
    }

    @GetMapping("/mesas/paginacion")
    public ResponseEntity<Page<Mesa>> dameClientesPaginacion(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Mesa> mesas = mesaRepository.findAll(pageable);
        return ResponseEntity.ok(mesas);
    }

    @PutMapping("/mesa/{id}")
    public ResponseEntity<Mesa> modificaMesa(@PathVariable Long id, @RequestBody Mesa mesaNueva) {
        return mesaRepository.findById(id)
                .map(mesa -> {
                    if (mesa.getNumeroMesa() != null)
                        mesa.setNumeroMesa(mesaNueva.getNumeroMesa());
                    if (mesa.getDescripcion() != null)
                        mesa.setDescripcion(mesaNueva.getDescripcion());

                    if (mesa.getReservas() != null)
                        mesa.setReservas(mesaNueva.getReservas());

                    return ResponseEntity.ok(mesaRepository.save(mesa));
                })
                .orElseGet(() -> {
                    return ResponseEntity.notFound().build();
                });
    }


    @DeleteMapping("/mesa/{id}")
    public ResponseEntity<Void> eliminaMesaId(@PathVariable Long id){
        Optional<Mesa> mesa = mesaRepository.findById(id);

        if(mesa.isPresent()){
            mesaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }

}