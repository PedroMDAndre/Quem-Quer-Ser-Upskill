package pt.upskil.desafio.services;

import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import pt.upskil.desafio.entities.Dificuldade;
import pt.upskil.desafio.entities.Jogo;
import pt.upskil.desafio.entities.Pergunta;
import pt.upskil.desafio.entities.Resposta;
import pt.upskil.desafio.exceptions.AdicionarPerguntaException;
import pt.upskil.desafio.exceptions.InvalidPerguntaException;
import pt.upskil.desafio.exceptions.ObterEstatisticaException;
import pt.upskil.desafio.exceptions.ObterPerguntasException;
import pt.upskil.desafio.repositories.PerguntaRepository;
import pt.upskil.desafio.repositories.RespostaRepository;
import pt.upskil.desafio.services.apiUtils.EstatisticaResponse;
import pt.upskil.desafio.services.apiUtils.PerguntaResponse;
import pt.upskil.desafio.services.apiUtils.StatusResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PerguntaServicoImpl implements PerguntaServico {

    public static void main(String[] args) {
        PerguntaServico perguntaServico = new PerguntaServicoImpl();
        /*Pergunta p = new Pergunta();
        ArrayList<Resposta> respostas = new ArrayList<>(4);
        respostas.add(new Resposta(p, "wrong answer", false));
        respostas.add(new Resposta(p, "wrong answer", false));
        respostas.add(new Resposta(p, "right answer", true));
        respostas.add(new Resposta(p, "wrong answer", false));
        p.setRespostas(respostas);
        p.setDificuldade(Dificuldade.FACIL);
        p.setDescricao("Qual é a certa? (Teste pergunta feito pelo grupo 2)");
        try {
            perguntaServico.addicionarPergunta(p);
        } catch (AdicionarPerguntaException e) {
            e.printStackTrace();
        }*/

        try {
            System.out.println(perguntaServico.obterEstatisticas());
            for (Pergunta p : perguntaServico.obterPerguntas(Dificuldade.IMPOSSIVEL)) {
                System.out.println(p.getDescricao());
                for (Resposta r : p.getRespostas())
                    System.out.println(r.getTexto());
            }
        } catch (ObterPerguntasException | ObterEstatisticaException e) {
            e.printStackTrace();
        }
    }

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();

    static {
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    private static final String URL_ADDICIONAR_PERGUNTA = "https://serro.pt/perguntas/nova";
    private static final String URL_PEDIR_PERGUNTAS = "https://serro.pt/perguntas/%s"; //%s place-holder para difficuldade
    private static final String URL_PEDIR_ESTATISTICAS = "https://serro.pt/perguntas";

    @Autowired
    private PerguntaRepository perguntaRepository;
    @Autowired
    private RespostaRepository respostaRepository;

    @Override
    public void addicionarPergunta(Pergunta pergunta) throws AdicionarPerguntaException {
        JSONObject perguntaRequest = new JSONObject();
        perguntaRequest.put("pergunta", pergunta.getDescricao());
        List<Resposta> respostas = pergunta.getRespostas();
        if (respostas.size() != Pergunta.NUM_RESPOSTAS) {
            throw new InvalidPerguntaException("A pergunta não tem o numero de respostas certo");
        }
        int certa = -1;
        for (int i = 1; i < respostas.size() + 1; i++) {
            Resposta resposta = respostas.get(i - 1);
            if (resposta.isCerta())
                certa = i;
            perguntaRequest.put("resposta" + i, resposta.getTexto());
        }
        if (certa == -1) {
            throw new InvalidPerguntaException("A pergunta não tem nenhuma resposta indicada como certa");
        }
        perguntaRequest.put("certa", Integer.toString(certa));
        perguntaRequest.put("dificuldade", pergunta.getDificuldade().getApiText());

        StatusResponse response = null;
        try {
            response = restTemplate.postForObject(URL_ADDICIONAR_PERGUNTA,
                    new HttpEntity<>(perguntaRequest.toString(), headers), StatusResponse.class);
        } catch (HttpClientErrorException |
                HttpServerErrorException |
                UnknownHttpStatusCodeException e) {
        }
        if (response == null || response.status.equals("error")) {
            throw new AdicionarPerguntaException();
        }
    }

    private static final int NUM_TENTATIVAS_BUSCAR_PERGUNTA_VALIDA = 10;

    @Override
    public List<Pergunta> obterPerguntas(Dificuldade dificuldade) throws ObterPerguntasException {
        ResponseEntity<PerguntaResponse[]> response;

        try {
            response = restTemplate.getForEntity(String.format(URL_PEDIR_PERGUNTAS, dificuldade.getApiText()), PerguntaResponse[].class);
        } catch (HttpClientErrorException |
                HttpServerErrorException |
                UnknownHttpStatusCodeException e) {
            throw new ObterPerguntasException();
        }

        if (response.getBody() == null) {
            throw new ObterPerguntasException();
        }

        List<Pergunta> perguntas = new ArrayList<>(response.getBody().length);

        for (PerguntaResponse pr : response.getBody()) {
            if (!isValidPerguntaResponse(pr)) {
                boolean perguntaValida = false;
                for (int i = 0; i < NUM_TENTATIVAS_BUSCAR_PERGUNTA_VALIDA; i++) {
                    ResponseEntity<PerguntaResponse[]> newResponse;

                    try {
                        newResponse = restTemplate.getForEntity(String.format(URL_PEDIR_PERGUNTAS, dificuldade.getApiText()), PerguntaResponse[].class);
                    } catch (HttpClientErrorException |
                            HttpServerErrorException |
                            UnknownHttpStatusCodeException e) {
                        throw new ObterPerguntasException();
                    }

                    if (newResponse.getBody() == null || newResponse.getBody().length == 0) {
                        break;
                    }
                    PerguntaResponse replacementpr = newResponse.getBody()[0];

                    if (isValidPerguntaResponse(replacementpr)) {
                        pr.setPergunta(replacementpr.getPergunta());
                        pr.setResposta1(replacementpr.getResposta1());
                        pr.setResposta2(replacementpr.getResposta2());
                        pr.setResposta3(replacementpr.getResposta3());
                        pr.setResposta4(replacementpr.getResposta4());
                        perguntaValida = true;
                        break;
                    }
                }
                if (!perguntaValida) {
                    throw new ObterPerguntasException();
                }
            }
        }

        for (PerguntaResponse pr : response.getBody()) {
            Pergunta pergunta = new Pergunta();

            pergunta.setDescricao(pr.getPergunta());

            List<Resposta> respostas = new ArrayList<>(Pergunta.NUM_RESPOSTAS);
            Resposta r1 = new Resposta(pergunta, pr.getResposta1(), pr.getCerta().equals("1"));
            Resposta r2 = new Resposta(pergunta, pr.getResposta2(), pr.getCerta().equals("2"));
            Resposta r3 = new Resposta(pergunta, pr.getResposta3(), pr.getCerta().equals("3"));
            Resposta r4 = new Resposta(pergunta, pr.getResposta4(), pr.getCerta().equals("4"));

            respostas.add(r1);
            respostas.add(r2);
            respostas.add(r3);
            respostas.add(r4);

            perguntaRepository.save(pergunta);
            respostaRepository.save(r1);
            respostaRepository.save(r2);
            respostaRepository.save(r3);
            respostaRepository.save(r4);

            pergunta.setRespostas(respostas);
            pergunta.setDificuldade(Dificuldade.getDificuldadeFromApiText(pr.getDificuldade()));

            perguntaRepository.save(pergunta);


            perguntas.add(pergunta);
        }

        return perguntas;
    }

    private static boolean isValidPerguntaResponse (PerguntaResponse pr) {
        return !pr.getPergunta().replace(" ", "").isEmpty() &&
                !pr.getResposta1().replace(" ", "").isEmpty() &&
                !pr.getResposta2().replace(" ", "").isEmpty() &&
                !pr.getResposta3().replace(" ", "").isEmpty() &&
                !pr.getResposta4().replace(" ", "").isEmpty();
    }

    @Override
    public Map<String, Integer> obterEstatisticas() throws ObterEstatisticaException {
        EstatisticaResponse response;
        try {
            response = restTemplate.getForObject(URL_PEDIR_ESTATISTICAS, EstatisticaResponse.class);
        } catch (HttpClientErrorException |
                HttpServerErrorException |
                UnknownHttpStatusCodeException e) {
            throw new ObterEstatisticaException();
        }

        Map<String, Integer> result = new HashMap<>();
        result.put("total", Integer.valueOf(response.getTotal()));
        result.put("fáceis", Integer.valueOf(response.getFaceis()));
        result.put("médias", Integer.valueOf(response.getMedias()));
        result.put("difíceis", Integer.valueOf(response.getDificeis()));
        result.put("impossíveis", Integer.valueOf(response.getImpossiveis()));
        return result;
    }

    @Override
    public int obterNumeroTotalDePerguntas() throws ObterEstatisticaException {
        return obterEstatisticas().get("total");
    }

    @Override
    public List<Pergunta> obter15Perguntas() throws ObterPerguntasException {
        List<Pergunta> perguntas = new ArrayList<>();

        for (Dificuldade dificuldade : Dificuldade.values()) {
            perguntas.addAll(obterPerguntas(dificuldade));
        }

        if (perguntas.size() != 15) {
            throw new ObterPerguntasException();
        }

        return perguntas;
    }

    @Override
    public Pergunta obterPergunta(Dificuldade dificuldade) throws ObterPerguntasException {
        List<Pergunta> perguntas = obterPerguntas(dificuldade);
        return perguntas.get((int) (Math.random() * perguntas.size()));
    }
}
