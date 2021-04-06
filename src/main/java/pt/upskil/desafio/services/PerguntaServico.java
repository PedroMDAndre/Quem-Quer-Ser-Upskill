package pt.upskil.desafio.services;

import pt.upskil.desafio.entities.Dificuldade;
import pt.upskil.desafio.entities.Pergunta;
import pt.upskil.desafio.exceptions.AdicionarPerguntaException;
import pt.upskil.desafio.exceptions.ObterEstatisticaException;
import pt.upskil.desafio.exceptions.ObterPerguntasException;

import java.util.List;
import java.util.Map;

public interface PerguntaServico {

    void addicionarPergunta(Pergunta pergunta) throws AdicionarPerguntaException;

    List<Pergunta> obterPerguntas(Dificuldade dificuldade) throws ObterPerguntasException;

    Map<String, Integer> obterEstatisticas() throws ObterEstatisticaException;

    int obterNumeroTotalDePerguntas() throws ObterEstatisticaException;

    List<Pergunta> obter15Perguntas() throws ObterPerguntasException;

    Pergunta obterPergunta(Dificuldade dificuldade) throws ObterPerguntasException;

}

