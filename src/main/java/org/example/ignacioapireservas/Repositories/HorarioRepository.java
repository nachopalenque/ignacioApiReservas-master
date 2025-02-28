package org.example.ignacioapireservas.Repositories;

import org.example.ignacioapireservas.Entities.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Long> {
}
