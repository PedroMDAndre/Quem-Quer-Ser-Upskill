package pt.upskil.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.upskil.desafio.entities.Ronda;

public interface RondaRepository extends JpaRepository<Ronda, Long> {
}
