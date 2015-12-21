package es.uvigo.dagss.ejemplorest.daos;

import es.uvigo.dagss.ejemplorest.entidades.Autor;
import es.uvigo.dagss.ejemplorest.entidades.Libro;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class AutorDAO {
    @PersistenceContext
    EntityManager em;
    
    public Autor crear(Autor autor) {
        em.persist(autor);
        return autor;
    }
    
    public Autor actualizar(Autor autor) {
        return em.merge(autor);
    }
    
    public void borrar(Autor autor) {
        em.remove(autor);
    }
    
    public Autor buscar(Long id) {
        return em.find(Autor.class, id);
    }
    
    public List<Autor> buscarTodos() {
        Query q = em.createQuery("SELECT a FROM Autor a");
        return q.getResultList();
    }    
    
    public List<Autor> buscarPorPatron(String patron) {
        Query q = em.createQuery("SELECT a FROM Autor a "+
                                 " WHERE a.nombre LIKE %:patron1% "+
                                 "    OR a.apellidos LIKE %:patron2% ");
        q.setParameter("patron1", patron);
        q.setParameter("patron2", patron);
        return q.getResultList();
    }    
    
    
    public List<Libro> buscarLibrosPorAutor(Long idAutor) {
        Query q = em.createQuery("SELECT l FROM Libro l JOIN l.autores a WHERE a.id = :idAutor");
        q.setParameter("idAutor", idAutor);
        return q.getResultList();
    }
}
