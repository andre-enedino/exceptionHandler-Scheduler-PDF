package enedino.andre.controller;

import enedino.andre.dto.UserDTO;
import enedino.andre.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @POST
    @RolesAllowed("manager")
    public Response salvar(UserDTO dto) {
        userService.salvar(dto);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response buscarTodos() {
        var  usuarios = userService.buscarTodos();

        return Response.status(Response.Status.OK).entity(usuarios).build();
    }

    @GET
    @Path("/buscarPorId")
    public Response buscarPorId(@QueryParam("id") Long id) {
        return Response.status(Response.Status.OK).entity(userService.buscarPorId(id)).build();
    }

    @GET
    @Path("/buscar-por-cpf")
    public Response buscarPoCpf(@QueryParam("cpf") String cpf) {
        return Response.status(Response.Status.OK).entity(userService.buscarPorCpf(cpf)).build();
    }

}
