package enedino.andre.dto;

import enedino.andre.servicos.endereco.model.Endereco;

public record EnderecoDTO(String cep, String logradouro, String numero, String cidade, String bairro, String uf) {

    public Endereco toEndereco(){
        return new Endereco(
                cep, logradouro, numero, bairro, cidade, uf
        );
    }
}
