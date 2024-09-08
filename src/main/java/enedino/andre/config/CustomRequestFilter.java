package enedino.andre.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class CustomRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        //LOG.info("Interceptando requisição: {}", containerRequestContext.getUriInfo().getRequestUri());
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {
        //LOG.info("Resposta enviada para a requisição: {}", containerRequestContext.getUriInfo().getRequestUri());
    }
}
