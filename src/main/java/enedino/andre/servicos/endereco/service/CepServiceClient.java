package enedino.andre.servicos.endereco.service;

import enedino.andre.servicos.endereco.dto.EnderecoResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

@ApplicationScoped
public class CepServiceClient {

    @Inject
    @RestClient
    ViaCepService viaCepService;

//    @Inject
//    public CepServiceClient(ViaCepService viaCepService) {
//        this.viaCepService = viaCepService;
//    }

    public EnderecoResponse getCepInfo(String cep) throws ClientWebApplicationException {
        EnderecoResponse enderecoDTO = viaCepService.getCepInfo(cep);
        return viaCepService.getCepInfo(cep);
    }
}
