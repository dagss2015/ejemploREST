package es.uvigo.dagss.ejemplorest.configuracion;

import es.uvigo.dagss.ejemplorest.entidades.Autor;
import es.uvigo.dagss.ejemplorest.entidades.Libro;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class SingletonCreadorEntidadesIniciales {
    @PersistenceContext
    EntityManager em;
    
    
    @PostConstruct
    public void crearEntidades() {
        Autor a1 = new Autor("juan", "juanez", "jordania");
        Autor a2 = new Autor("alba", "alvez", "albania");
        Autor a3 = new Autor("pedro", "perez", "peru");
        Autor a4 = new Autor("esteban", "estevez", "españa");
        
        em.persist(a1);
        em.persist(a2);
        em.persist(a3);
        em.persist(a4);
        
        em.flush();  // Forzar escritura y asignación de IDs
        
        Libro l1 = new Libro("introducción a java ee 5", Calendar.getInstance().getTime(), 100);
        l1.anadirAutor(a1);
        
        Libro l2 = new Libro("introducción a java ee 6", Calendar.getInstance().getTime(), 100);
        l2.anadirAutor(a1);
        
        Libro l3 = new Libro("introducción a java ee 7", Calendar.getInstance().getTime(), 100);
        l3.anadirAutor(a1);
        l3.anadirAutor(a2);

        Libro l4 = new Libro("php es mejor que java ee", Calendar.getInstance().getTime(), 100);
        l4.anadirAutor(a3);
        
        Libro l5 = new Libro("python es mejor que todo", Calendar.getInstance().getTime(), 100);
        l5.anadirAutor(a4);
        
        Libro l6 = new Libro("python me paga la casa", Calendar.getInstance().getTime(), 100);
        l6.anadirAutor(a4);
        
        em.persist(l1);
        em.persist(l2);
        em.persist(l3);
        em.persist(l4);
        em.persist(l5);
        em.persist(l6);

        
        
        
    }
}
