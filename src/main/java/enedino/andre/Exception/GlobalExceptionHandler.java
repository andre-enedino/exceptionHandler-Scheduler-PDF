package enedino.andre.Exception;

import enedino.andre.Exception.model.ErrorResponse;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.resteasy.reactive.ClientWebApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Provider
    public static class ClientWebApplicationExceptionMapper implements ExceptionMapper<ClientWebApplicationException> {
        @Override
        public Response toResponse(ClientWebApplicationException exception) {
            String errorMessage = "Client error: " + exception.getMessage();
            LOG.error("Erro: "+exception.getCause());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("ERRO NA CHAMADA EXTERNA", errorMessage))
                    .build();
        }
    }

    @Provider
    public static class ResourceNotFoundExceptionMapper implements ExceptionMapper<ResourceNotFoundException> {
        @Override
        public Response toResponse(ResourceNotFoundException e) {
            LOG.error("Erro: "+e.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("NOT_FOUND", e.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class NullPointerMapper implements ExceptionMapper<NullPointerException> {

        @Override
        public Response toResponse(NullPointerException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Erro interno", e.getMessage()))
                    .build();
        }
    }

    @Provider
    public static class DuplicacaoDeEntidadesExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

        @Override
        public Response toResponse(ConstraintViolationException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("Usuário já existe", e.getErrorMessage()))
                    .build();
        }
    }
}
