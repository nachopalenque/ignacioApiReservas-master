package org.example.ignacioapireservas.Repositories;

import org.example.ignacioapireservas.Entities.Cliente;
import org.example.ignacioapireservas.Entities.Mesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Long> {
    Page<Mesa> findAll(Pageable pageable);
}