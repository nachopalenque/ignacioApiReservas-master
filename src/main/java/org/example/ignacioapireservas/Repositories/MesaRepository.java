package org.example.ignacioapireservas.Repositories;

import org.example.ignacioapireservas.Entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
}