package enedino.andre.servicos.endereco.controller;

import enedino.andre.servicos.endereco.service.CepServiceClient;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/enderecos")
public class EnderecoController {

    private final CepServiceClient cepServiceClient;

    public EnderecoController(CepServiceClient cepServiceClient) {
        this.cepServiceClient = cepServiceClient;
    }

    @GET
    public Response buscarPorCep(@QueryParam("cep") String cep) {

        return Response.status(Response.Status.OK).entity(cepServiceClient.getCepInfo(cep)).build();
    }
}
