package pt.upskil.desafio.services;

import pt.upskil.desafio.entities.Jogo;
import pt.upskil.desafio.entities.Pergunta;
import pt.upskil.desafio.entities.User;
import pt.upskil.desafio.exceptions.AjudaAlreadyUsedException;
import pt.upskil.desafio.exceptions.NoGameActiveException;
import pt.upskil.desafio.exceptions.ObterPerguntasException;

import java.time.LocalDateTime;
import java.util.List;

public interface JogoService {
    int highScore(User user);
    int highScorePosition(User user);
    List<Jogo> findAll();
    void iniciarJogo(User user) throws ObterPerguntasException;
    void fecharJogos(User user);
    void save(Jogo jogo);
    List<Double> usarAjudaPublico(User currentUser) throws NoGameActiveException, AjudaAlreadyUsedException;
    List<Integer> usar5050(User user) throws NoGameActiveException, AjudaAlreadyUsedException;
    Pergunta usarTrocaPergunta(User user) throws NoGameActiveException, AjudaAlreadyUsedException, ObterPerguntasException;
    List<Jogo> findAllByFinished(boolean finished);
    boolean responderPergunta(User user, int nrResposta, LocalDateTime horaResposta) throws NoGameActiveException;
    boolean existeJogoAberto(User user);
}
