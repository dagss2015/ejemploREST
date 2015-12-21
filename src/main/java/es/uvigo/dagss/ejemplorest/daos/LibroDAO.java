package es.uvigo.dagss.ejemplorest.daos;

import es.uvigo.dagss.ejemplorest.entidades.Libro;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class LibroDAO {
    @PersistenceContext
    EntityManager em;
    
    public Libro crear(Libro libro) {
        em.persist(libro);
        return libro;
    }
    
    public Libro actualizar(Libro libro) {
        return em.merge(libro);
    }
    
    public void borrar(Libro libro) {
        em.remove(libro);
    }
    
    public Libro buscar(Long id) {
        return em.find(Libro.class, id);
    }
    
    public List<Libro> buscarTodos() {
        Query q = em.createQuery("SELECT l FROM Libro l");
        return q.getResultList();
    }
}
