package pt.upskil.desafio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.upskil.desafio.entities.Ronda;
import pt.upskil.desafio.repositories.RondaRepository;

@Service
public class RondaServiceImpl implements RondaService{
    @Autowired
    RondaRepository rondaRepository;

    @Override
    public void save(Ronda ronda) {
        rondaRepository.save(ronda);
    }
}
