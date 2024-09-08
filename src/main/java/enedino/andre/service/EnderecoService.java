package enedino.andre.service;

import enedino.andre.dto.EnderecoDTO;
import enedino.andre.repository.EnderecoRepository;
import enedino.andre.servicos.endereco.dto.EnderecoResponse;
import enedino.andre.servicos.endereco.model.Endereco;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Transactional
    public Endereco salvar(EnderecoDTO dto){

        Endereco endereco = dto.toEndereco();

        enderecoRepository.persist(endereco);

        return endereco;
    }
}
