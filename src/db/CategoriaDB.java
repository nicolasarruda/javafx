package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.entities.Categoria;

public class CategoriaDB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db"); 
    
    private Categoria categoria;
    
    public CategoriaDB() {
    }
    
    public CategoriaDB(Categoria categoria) {
	this.categoria = categoria;
    }
    
    public List<Categoria> categorias() {
	EntityManager em = emf.createEntityManager();
	Query query = em.createNativeQuery("Select * from categoria", Categoria.class);
	List<Categoria> categorias = query.getResultList();
	return categorias;
    }
    
    public Categoria encontrarCategoria(Long id) {
	EntityManager em = emf.createEntityManager();
	Categoria categoria = em.find(Categoria.class, id);
	return categoria;
    }
}
