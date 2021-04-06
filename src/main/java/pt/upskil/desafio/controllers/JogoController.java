package pt.upskil.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.upskil.desafio.entities.Jogo;
import pt.upskil.desafio.entities.*;
import pt.upskil.desafio.exceptions.NoGameActiveException;
import pt.upskil.desafio.exceptions.ObterPerguntasException;
import pt.upskil.desafio.services.JogoService;
import pt.upskil.desafio.services.RondaService;
import pt.upskil.desafio.services.UserService;
import pt.upskil.desafio.utils.AlertMessageImage;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;


@Controller
public class JogoController {
    @Autowired
    JogoService jogoService;
    @Autowired
    UserService userService;
    @Autowired
    RondaService rondaService;

    @GetMapping("/player/game")
    public String iniciarJogo(ModelMap modelMap) throws NoGameActiveException {
        User user = userService.currentUser();
        modelMap.put("user", user);

        try {
            jogoService.iniciarJogo(user);
        } catch (ObterPerguntasException e) {
            modelMap.put("message", "Não foi possível buscar as perguntas para o jogo.");
            modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
            modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());
            return "components/alert-message";
        }


        Ronda ronda = null;
        try {
            ronda = user.getJogoCorrente().getRondaAtual();
        } catch (NoGameActiveException e) {
            modelMap.put("message", "Não foi possível encontrar o jogo.");
            modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
            modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());
            return "components/alert-message";
        }

        //obter duração do tempo de pergunta
        Duration duration = ronda.getPergunta().getDificuldade().getDuration();
        Long tempo = duration.getSeconds();

        modelMap.put("ronda", ronda);
        modelMap.put("tempo", tempo);
        return "player/game";
    }

    @GetMapping("/player/game/over")
    public String gameOver(ModelMap modelMap) {
        try {
            Jogo jogo = userService.currentUser().getJogoCorrente();
            User user = userService.currentUser();
            modelMap.put("user", user);
            jogo.setFinished(true);
            jogoService.save(jogo);

            modelMap.put("message", "Game Over !!!");
            modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
            modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());

            return "components/alert-message";
        } catch (NoGameActiveException e) {
            modelMap.put("message", "Game Over !!!");
            modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
            modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());
            return "components/alert-message";
        }

    }

    @GetMapping("/player/game/victory")
    public String gameVictory(ModelMap modelMap) {
        User user = userService.currentUser();
        modelMap.put("user", user);
        modelMap.put("message", "Ganhou o Jogo !!! Parabéns !");
        modelMap.put("imageURL", AlertMessageImage.SUCCESS.getImageURL());
        modelMap.put("audioURL", AlertMessageImage.SUCCESS.getAudioURL());

        return "components/alert-message";
    }

    @GetMapping("/player/game/erro/{mensagem}")
    public String gameVictory(ModelMap modelMap,
                              @PathVariable String mensagem) {
        User user= userService.currentUser();
        modelMap.put("user", user);
        modelMap.put("load", jogoService.existeJogoAberto(user));
        modelMap.put("message", mensagem);
        modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
        modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());

        return "components/alert-message";
    }

    @GetMapping("/player/game/continue")
    public String continuarJogo(ModelMap modelMap) {
        User user = userService.currentUser();
        modelMap.put("user", user);

        Jogo jogo = null;
        try {
            jogo = userService.currentUser().getJogoCorrente();
        } catch (NoGameActiveException e) {
            modelMap.put("message", "O jogo já se encontra terminado!");
            modelMap.put("imageURL", AlertMessageImage.FAILURE.getImageURL());
            modelMap.put("audioURL", AlertMessageImage.FAILURE.getAudioURL());

            return "components/alert-message";
        }

        Ronda ronda = jogo.getRondaAtual();
        Pergunta pergunta = ronda.getPergunta();
        jogo.addScore(-100);
        jogoService.save(jogo);
        ronda.setStartTime(LocalDateTime.now());
        rondaService.save(ronda);

        modelMap.put("ronda", ronda);
        modelMap.put("pontos", Integer.toString(jogo.getGameScore()));
        modelMap.put("tempo", Long.toString(pergunta.getDificuldade().getDuration().get(SECONDS)));

        return "player/game";
    }
}
