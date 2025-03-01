package org.example.ignacioapireservas.Controllers;

import jakarta.validation.Valid;
import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Repositories.ClienteRepository;
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
public class ClienteController {
    @Autowired
    private ClienteRepository clienteRepository;

    //listar clientes
    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> dameClientes(){
        var clientes = clienteRepository.findAll();
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/clientes/paginacion")
    public ResponseEntity<Page<Cliente>> dameClientesPaginacion(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = (Pageable) PageRequest.of(page, size);
        Page<Cliente> clientes = clienteRepository.findAll(pageable);
        return ResponseEntity.ok(clientes);
    }


    @GetMapping("/cliente/{id}")
    public ResponseEntity<Cliente> dameClienteId(@PathVariable Long id){

        Optional <Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {

            return ResponseEntity.ok(cliente.get());
        }else{

            return ResponseEntity.notFound().build();
        }

    }


    @GetMapping("/cliente/username/{username}")
    public ResponseEntity<Cliente> dameClienteEmail(@PathVariable String username){

         Cliente cliente = clienteRepository.findByNombre(username);

        if (cliente != null) {
            return ResponseEntity.ok(cliente);
        }else{

            return ResponseEntity.notFound().build();
        }

    }

    //insertar cliente
    @PostMapping("/cliente")
    public ResponseEntity<Cliente> insertaCliente(@RequestBody @Valid Cliente cliente){
        var clienteNuevo = clienteRepository.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo);
    }

    //actualizar cliente
    @PutMapping("/cliente/{id}")
    public ResponseEntity<Cliente> actualizaClienteId(@PathVariable Long id, @RequestBody Cliente nuevoCliente){
        return clienteRepository.findById(id)
                .map(cliente->{
                    if (cliente.getNombre() != null)
                        cliente.setNombre(nuevoCliente.getNombre());

                    if (cliente.getEmail() != null)
                        cliente.setEmail(nuevoCliente.getEmail());

                    if (cliente.getReservas() != null)
                        cliente.setReservas(nuevoCliente.getReservas());

                    return ResponseEntity.ok(clienteRepository.save(cliente));
                })
                .orElseGet(()->{
                    return ResponseEntity.notFound().build();
                }  );

    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Void> eliminaClienteId(@PathVariable Long id){
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }

    }



}