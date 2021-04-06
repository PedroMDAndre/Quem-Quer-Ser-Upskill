package pt.upskil.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.upskil.desafio.entities.Resposta;

@Repository
public interface RespostaRepository extends JpaRepository<Resposta, Long> {

}
