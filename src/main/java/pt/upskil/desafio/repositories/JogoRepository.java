package pt.upskil.desafio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pt.upskil.desafio.entities.Jogo;
import pt.upskil.desafio.entities.User;

import java.util.List;

@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {
    List<Jogo> findAllByUserOrderByGameScoreDesc(User user);
    List<Jogo> findAllByOrderByGameScoreDesc();
    List<Jogo> findAll();
    List<Jogo> findAllByFinished(boolean finished);
    List<Jogo> findAllByFinishedOrderByGameScoreDesc(boolean finished);

}
