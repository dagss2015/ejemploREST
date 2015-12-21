package es.uvigo.dagss.ejemplorest.rest;

import es.uvigo.dagss.ejemplorest.daos.AutorDAO;
import es.uvigo.dagss.ejemplorest.entidades.Autor;
import es.uvigo.dagss.ejemplorest.entidades.Libro;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author ribadas
 */
@Path(value = "autores")
@Stateless
public class AutorEndpoint {

    @Inject
    AutorDAO autorDAO;

    @Context
    private UriInfo uriInfo;

    public AutorEndpoint() {
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarAutores() {
        List<Autor> autores = autorDAO.buscarTodos();
        // Necesario para devolver Listas como entidades XML/JSON
        GenericEntity<List<Autor>> entidadXML = new GenericEntity<List<Autor>>(autores) {/*vacio*/};
        return Response.ok(entidadXML).build();
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_XML)
    public Response crearAutor(Autor autor) {
        try {
            Autor nuevoAutor = autorDAO.crear(autor);
            URI nuevoAutorURI =  uriInfo.getAbsolutePathBuilder().path(nuevoAutor.getId().toString()).build();
            return Response.created(nuevoAutorURI).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id:[0-9]+}")
    public Response buscarAutor(@PathParam("id") Long id) {
        Autor autor = autorDAO.buscar(id);
        if (autor != null) {
            return Response.ok(autor).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{id:[0-9]+}")
    public Response actualizarAutor(Autor autor) {
        try {
            Autor nuevoAutor = autorDAO.actualizar(autor);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id:[0-9]+}")
    public Response borrarAutor(@PathParam("id") Long id) {
        Autor autor = autorDAO.buscar(id);
        if (autor != null) {
            autorDAO.borrar(autor);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id:[0-9]+}/libros")
    public Response buscarLibrosPorAutor(@PathParam("id") Long id) {
        List<Libro> libros = autorDAO.buscarLibrosPorAutor(id);

        // Necesario para devolver Listas como entidades XML/JSON
        GenericEntity<List<Libro>> entidadXML = new GenericEntity<List<Libro>>(libros) {/*vacio*/};
        return Response.ok(entidadXML).build();
    }

}
