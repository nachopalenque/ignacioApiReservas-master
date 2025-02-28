package org.example.ignacioapireservas.Controllers;

import jakarta.validation.Valid;
import org.example.ignacioapireservas.Entities.Horario;
import org.example.ignacioapireservas.Repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class HorarioController {

    @Autowired
    private HorarioRepository repositorioHorario;

    @GetMapping("/horarios")
    public ResponseEntity<List<Horario>> dameHorarios(){
        var horarios = repositorioHorario.findAll();
        return ResponseEntity.ok(horarios);
    }

    @PostMapping("/horario")
    public ResponseEntity<Horario> insertaHorario(@RequestBody @Valid Horario horario){
        var nuevoHorario = repositorioHorario.save(horario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);

    }

    @PutMapping("/horario/{id}")
    public ResponseEntity<Horario>actualizaHorario(@RequestBody @Valid Horario horario, @PathVariable Long id){
      return ResponseEntity.status(HttpStatus.OK).body(repositorioHorario.save(horario));
    }

    @DeleteMapping("/horario/{id}")
    public ResponseEntity<Void> eliminaHorario(@PathVariable Long id){
        Optional<Horario> horario = repositorioHorario.findById(id);
        if(horario.isPresent()){
            repositorioHorario.deleteById(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
