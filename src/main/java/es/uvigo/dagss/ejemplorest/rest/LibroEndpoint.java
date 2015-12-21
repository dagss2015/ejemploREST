package es.uvigo.dagss.ejemplorest.rest;

import es.uvigo.dagss.ejemplorest.daos.LibroDAO;
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

@Path(value = "libros")
@Stateless
public class LibroEndpoint {

    @Inject
    LibroDAO libroDAO;

    @Context
    private UriInfo uriInfo;

    public LibroEndpoint() {
    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response buscarLibros() {
        List<Libro> libros = libroDAO.buscarTodos();
        // Necesario para devolver Listas como entidades XML/JSON
        GenericEntity<List<Libro>> entidadXML = new GenericEntity<List<Libro>>(libros) {/*vacio*/

        };
        return Response.ok(entidadXML).build();
    }

    @POST
    @Consumes(value = MediaType.APPLICATION_XML)
    public Response crearLibro(Libro libro) {
        try {
            Libro nuevoLibro = libroDAO.crear(libro);
            URI nuevoLibroURI = uriInfo.getAbsolutePathBuilder().path(nuevoLibro.getId().toString()).build();
            return Response.created(nuevoLibroURI).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GET
    @Produces(value = {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("{id:[0-9]+}")
    public Response buscarLibro(@PathParam("id") Long id) {
        Libro libro = libroDAO.buscar(id);
        if (libro != null) {
            return Response.ok(libro).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    @Path("{id:[0-9]+}")
    public Response actualizarLibro(Libro libro) {
        try {
            Libro nuevoLibro = libroDAO.actualizar(libro);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("{id:[0-9]+}")
    public Response borrarLibro(@PathParam("id") Long id) {
        Libro libro = libroDAO.buscar(id);
        if (libro != null) {
            libroDAO.borrar(libro);
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
