package enedino.andre.service;

import enedino.andre.Exception.ResourceNotFoundException;
import enedino.andre.dto.EnderecoDTO;
import enedino.andre.dto.UserDTO;
import enedino.andre.entity.User;
import enedino.andre.repository.UserRepository;
import enedino.andre.servicos.endereco.dto.EnderecoResponse;
import enedino.andre.servicos.endereco.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    private final UserRepository userRepository;
    private final EnderecoService enderecoService;


    public UserService(UserRepository userRepository, EnderecoService enderecoService) {
        this.userRepository = userRepository;
        this.enderecoService = enderecoService;
    }

    @Transactional
    public void salvar(UserDTO userDTO) {
        User user = userDTO.toUser();
        user.setEndereco(salvarEndereco(userDTO.enderecoDTO()));
        userRepository.persist(user);
    }

    private Endereco salvarEndereco(EnderecoDTO enderecoDTO) {
        return enderecoService.salvar(enderecoDTO);
    }

    public List<UserDTO> buscarTodos() {

        PanacheQuery<User> usuarios = userRepository.findAll();

        List<UserDTO> retorno = new ArrayList<>();

        for(User user : usuarios.stream().toList()) {

            EnderecoDTO enderecoDTO = null;
            if(user.getEndereco() != null) {
                //CAUSAR NULLPOINTEREXCEPTION
                //user.setEndereco(null);
                enderecoDTO = montarEnderecoView(user);
            }
            retorno.add(new UserDTO(user.getNome(), user.getEmail(), user.getSenha(), user.getCpf(), enderecoDTO));
        }

        return retorno;

    }

    public UserDTO buscarPorId(Long id) {
        User usuario = userRepository.findById(id);
        EnderecoDTO enderecoDTO = null;

        if(usuario == null) {
            throw new ResourceNotFoundException("Id não existe: ");
        }
        if(usuario.getEndereco() != null) {
            enderecoDTO = montarEnderecoView(usuario);
        }

        return new UserDTO(usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getCpf(), enderecoDTO);
    }

    private EnderecoDTO montarEnderecoView(User user) {
        return new EnderecoDTO(
                user.getEndereco().getCep(),
                user.getEndereco().getLogradouro(),
                user.getEndereco().getNumero(),
                user.getEndereco().getCidade(),
                user.getEndereco().getBairro(),
                user.getEndereco().getUf()
                );
    }

    public UserDTO buscarPorCpf(String cpf) {
        User user = userRepository.buscarPorCpf(cpf);

        if(user == null) {
            throw new ResourceNotFoundException("Usuário não existe");
        }

        return new UserDTO(user.getNome(), user.getEmail(), user.getSenha(), user.getCpf(), null);
    }
}
