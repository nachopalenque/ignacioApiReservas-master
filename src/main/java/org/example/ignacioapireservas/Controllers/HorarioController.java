package org.example.ignacioapireservas.Controllers;

import jakarta.validation.Valid;
import org.example.ignacioapireservas.Entities.Horario;
import org.example.ignacioapireservas.Repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    //función para rellenar datos de prueba
    @PostMapping("/horario/test")
    public ResponseEntity<List<Horario>>  insertaHorarioTest(){

        List<Horario> horarios = new ArrayList<>();

        var nuevoHorario1 = repositorioHorario.save(Horario.builder().tramoHorario("Almuerzos 08:00 - 11:00")
                .descripcion("Tramo horario para los almuerzos")
                .build());

        horarios.add(nuevoHorario1);

        var nuevoHorario2 = repositorioHorario.save(Horario.builder().tramoHorario("Comidas 13:00 - 17:30")
                .descripcion("Tramo horario para las comidas")
                .build());

        horarios.add(nuevoHorario2);

        var nuevoHorario3 = repositorioHorario.save(Horario.builder().tramoHorario("Cenas 20:30 - 23:30")
                .descripcion("Tramo horario para las cenas")
                .build());

        horarios.add(nuevoHorario3);

        return ResponseEntity.status(HttpStatus.CREATED).body(horarios);

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
