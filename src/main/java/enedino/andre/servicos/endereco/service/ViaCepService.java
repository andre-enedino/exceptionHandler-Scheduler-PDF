package enedino.andre.servicos.endereco.service;

import enedino.andre.servicos.endereco.dto.EnderecoResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/ws")
@RegisterRestClient(configKey = "viacep-api")
public interface ViaCepService {

    @GET
    @Path("/{cep}/json/")
    @Produces(MediaType.APPLICATION_JSON)
    public EnderecoResponse getCepInfo(@PathParam("cep") String cep);
}
