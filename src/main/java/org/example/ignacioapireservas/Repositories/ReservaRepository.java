package org.example.ignacioapireservas.Repositories;
import org.example.ignacioapireservas.Entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
      List<Reserva> findByfechaReserva(LocalDate fecha);


}