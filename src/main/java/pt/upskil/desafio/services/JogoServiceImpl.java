package pt.upskil.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.upskil.desafio.entities.*;
import pt.upskil.desafio.exceptions.AjudaAlreadyUsedException;
import pt.upskil.desafio.exceptions.NoGameActiveException;
import pt.upskil.desafio.exceptions.ObterPerguntasException;
import pt.upskil.desafio.repositories.JogoRepository;
import pt.upskil.desafio.repositories.RespostaRepository;
import pt.upskil.desafio.repositories.RondaRepository;
import pt.upskil.desafio.repositories.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class JogoServiceImpl implements JogoService {
    @Autowired
    JogoRepository jogoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    PerguntaServico perguntaServico;
    @Autowired
    RondaRepository rondaRepository;
    @Autowired
    RespostaRepository respostaRepository;


    @Override
    public int highScore(User user) {
        List<Jogo> jogos = jogoRepository.findAllByUserOrderByGameScoreDesc(user);
        if (jogos == null || jogos.isEmpty()) {
            return 0;
        }

        for (Jogo jogo : jogos) {
            if (jogo.getUser().equals(user) && jogo.isFinished()) {
                return jogo.getGameScore();
            }
        }

        return 0;
    }

    @Override
    public int highScorePosition(User user) {
        List<Jogo> jogos = jogoRepository.findAllByFinishedOrderByGameScoreDesc(true);

        for (int i = 0; i < jogos.size(); i++) {
            if (jogos.get(i).getUser().equals(user)) {
                return i + 1;
            }
        }

        return jogos.size() + 1;
    }

    @Override
    public List<Jogo> findAll() {
        return jogoRepository.findAll();
    }

    @Override
    public void iniciarJogo(User user) throws ObterPerguntasException {
        // fechar jogos anteriores
        fecharJogos(user);

        // iniciar jogo
        Jogo jogo = new Jogo();
        jogo.setUser(userService.currentUser());
        user.getJogos().add(jogo);
        jogoRepository.save(jogo);

        // criar obter perguntas
        List<Pergunta> perguntas;

        perguntas = perguntaServico.obter15Perguntas();

        // criar rondas
        List<Ronda> rondas = new ArrayList<>();

        int nrPergunta = 1;
        for (Pergunta pergunta : perguntas) {
            Ronda ronda = new Ronda(nrPergunta, pergunta, jogo);
            rondas.add(ronda);
            nrPergunta += 1;

            // save Ronda
            rondaRepository.save(ronda);
        }

        Ronda rondaActual = rondas.get(0);
        rondaActual.setStartTime(LocalDateTime.now());
        rondaRepository.save(rondaActual);

        jogo.setRondas(rondas);
        jogo.setRondaAtual(rondaActual);

        // save Game
        jogoRepository.save(jogo);

        // actualizar user
        userRepository.save(user);

    }

    @Override
    public void fecharJogos(User user) {
        List<Jogo> jogos = user.getJogos();
        for (Jogo jogo : jogos) {
            if (!jogo.isFinished()) {
                jogo.setFinished(true);
                jogoRepository.save(jogo);
            }
        }
    }

    @Override
    public void save(Jogo jogo) {
        jogoRepository.save(jogo);
    }

    @Override
    public List<Double> usarAjudaPublico(User user) throws NoGameActiveException, AjudaAlreadyUsedException {
        Jogo jogo = user.getJogoCorrente();
        if (jogo.isAjudaPublicoUsed()) {
            throw new AjudaAlreadyUsedException("A ajuda publico ja foi usada");
        }
        jogo.setAjudaPublicoUsed(true);
        save(jogo);

        Pergunta pergunta = jogo.getRondaAtual().getPergunta();
        List<Resposta> respostas = pergunta.getRespostas();
        double precision = pergunta.getDificuldade().getPrecisaoAjuda();

        double total = 0;
        List<Double> randomNumbers = new ArrayList<>();
        for (int i = 0; i < Pergunta.NUM_RESPOSTAS; i++) {
            if (!respostas.get(i).isRemovedBy5050()) {
                double num = Math.random();
                randomNumbers.add(num);
                total += num;
            } else {
                randomNumbers.add(0.0);
            }
        }

        List<Double> result = new ArrayList<>();
        for (int i = 0; i < Pergunta.NUM_RESPOSTAS; i++) {
            result.add((randomNumbers.get(i) / total) * (1 - precision) + (respostas.get(i).isCerta() ? precision : 0));
        }
        return result;
    }

    /**
     * @return Lista de numeros das respostas que foram eliminadas pelo 5050.
     */
    @Override
    public List<Integer> usar5050(User user) throws NoGameActiveException, AjudaAlreadyUsedException {
        Jogo jogo = user.getJogoCorrente();
        if (jogo.isAjuda5050Used()) {
            throw new AjudaAlreadyUsedException("A ajuda 5050 ja foi usada");
        }
        jogo.setAjuda5050Used(true);
        save(jogo);

        Pergunta pergunta = jogo.getRondaAtual().getPergunta();
        List<Resposta> respostas = pergunta.getRespostas();
        int respostaCerta = -1;
        for (int i = 0; i < Pergunta.NUM_RESPOSTAS; i++) {
            if (respostas.get(i).isCerta()) {
                respostaCerta = i + 1;
            }
        }

        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            int newRandomNumber = (int) (Math.random() * (Pergunta.NUM_RESPOSTAS - 1 - i)) + 1;
            for (int existingRandomNumber : randomNumbers) { //check that its not already in the list
                if (newRandomNumber >= existingRandomNumber) {
                    newRandomNumber = (newRandomNumber + 1);
                    if (newRandomNumber == Pergunta.NUM_RESPOSTAS) {
                        newRandomNumber = 1;
                    }
                }
            }
            randomNumbers.add(newRandomNumber);
            randomNumbers.sort((o1, o2) -> o1 - o2);
        }

        Integer numToMove = -1;
        for (int num : randomNumbers) {
            if (num == respostaCerta) {
                numToMove = num;
                break;
            }
        }
        if (numToMove != -1) {
            randomNumbers.remove(numToMove);
            randomNumbers.add(4);
        }

        for (int num : randomNumbers) {
            Resposta resposta = pergunta.getRespostas().get(num - 1);
            resposta.setRemovedBy5050(true);
            respostaRepository.save(resposta);
        }

        return randomNumbers;
    }

    @Override
    public Pergunta usarTrocaPergunta(User user) throws NoGameActiveException, AjudaAlreadyUsedException, ObterPerguntasException {
        Jogo jogo = user.getJogoCorrente();
        if (jogo.isAjudaTrocaPerguntaUsed()) {
            throw new AjudaAlreadyUsedException("A ajuda 5050 ja foi usada");
        }
        jogo.setAjudaTrocaPerguntaUsed(true);
        save(jogo);

        Ronda ronda = jogo.getRondaAtual();
        Pergunta perguntaNova = perguntaServico.obterPergunta(ronda.getPergunta().getDificuldade());
        ronda.setPergunta(perguntaNova);
        rondaRepository.save(ronda);
        return perguntaNova;
    }

    @Override
    public List<Jogo> findAllByFinished(boolean finished) {
        return jogoRepository.findAllByFinished(finished);
    }

    @Override
    public boolean responderPergunta(User user, int nrResposta, LocalDateTime horaResposta) throws NoGameActiveException {
        Jogo jogo = user.getJogoCorrente();
        Ronda ronda = jogo.getRondaAtual();

        if (!respostaAtempada(ronda, horaResposta)) {
            return false;
        }

        Pergunta pergunta = ronda.getPergunta();
        List<Resposta> respostas = pergunta.getRespostas();
        ronda.setRespostaEscolhida(respostas.get(nrResposta - 1));
        rondaRepository.save(ronda);

        if (respostaCorrecta(ronda, nrResposta)) {
            long questionTime = ronda.getPergunta().getDificuldade().getDuration().getSeconds(); // segundos disponíveis para responder à questão
            long tempoRestante = questionTime - ChronoUnit.SECONDS.between(horaResposta, ronda.getStartTime()); // tempo que sobrou, caso resposta atempada

            jogo.addScore(ronda.getPergunta().getDificuldade().getPontos());
            jogo.addScore((int) tempoRestante);

            return true;
        } else {
            return false;
        }
    }


    private boolean respostaCorrecta(Ronda ronda, int nrResposta) {
        List<Resposta> respostas = ronda.getPergunta().getRespostas();
        return respostas.get(nrResposta - 1).isCerta();
    }

    private boolean respostaAtempada(Ronda ronda, LocalDateTime horaResposta) {
        long questionTime = ronda.getPergunta().getDificuldade().getDuration().getSeconds(); // segundos disponíveis para responder à questão
        LocalDateTime timeLimit = ronda.getStartTime().plusSeconds(questionTime); // tempo limite para responder
        return horaResposta.isBefore(timeLimit);
    }

    @Override
    public boolean existeJogoAberto(User user){
        try {
            user.getJogoCorrente();
        } catch (NoGameActiveException e) {
            return false;
        }
        return true;
    }
}

