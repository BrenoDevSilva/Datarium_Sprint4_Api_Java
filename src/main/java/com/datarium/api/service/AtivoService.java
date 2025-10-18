package com.datarium.api.service;

import com.datarium.api.model.Ativo;
import com.datarium.api.repository.AtivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtivoService {

    @Autowired
    private AtivoRepository ativoRepository;

    public Ativo salvarAtivo(Ativo ativo) {
        return ativoRepository.save(ativo);
    }

    public List<Ativo> buscarTodosAtivos() {
        return ativoRepository.findAll();
    }

    public Optional<Ativo> buscarAtivoPorId(Long id) {
        return ativoRepository.findById(id);
    }

    public void deletarAtivo(Long id) {
        ativoRepository.deleteById(id);
    }

    public List<Ativo> buscarAtivosPorClienteId(Long clienteId) {
        return ativoRepository.findByClienteId(clienteId);
    }
}