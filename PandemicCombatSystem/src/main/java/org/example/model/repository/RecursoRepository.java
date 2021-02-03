package org.example.model.repository;

import org.example.model.entity.Recurso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecursoRepository extends JpaRepository<Recurso, Integer> {
}
