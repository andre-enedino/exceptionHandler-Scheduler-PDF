package enedino.andre.dto;

import enedino.andre.entity.User;
import enedino.andre.servicos.endereco.dto.EnderecoResponse;

public record UserDTO(String nome, String email, String senha, String cpf, EnderecoDTO enderecoDTO) {

    public User toUser() {
        return new User(nome, email, senha, cpf);
    }

}
