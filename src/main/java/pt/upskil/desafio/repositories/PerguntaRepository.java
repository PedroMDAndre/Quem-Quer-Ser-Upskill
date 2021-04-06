package pt.upskil.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.upskil.desafio.entities.Pergunta;

@Repository
public interface PerguntaRepository extends JpaRepository<Pergunta, Long> {
}
