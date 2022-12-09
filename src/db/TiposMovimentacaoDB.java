package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.entities.TiposMovimentacao;

public class TiposMovimentacaoDB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db"); 
    
    private TiposMovimentacao tipo;
    
    public TiposMovimentacaoDB() {
    }
    
    public TiposMovimentacaoDB(TiposMovimentacao tipo) {
	this.tipo = tipo;
    }
    
    public List<TiposMovimentacao> tipos() {
	EntityManager em = emf.createEntityManager();
	Query query = em.createNativeQuery("Select * from tipos_movimentacao", TiposMovimentacao.class);
	List<TiposMovimentacao> tipos = query.getResultList();
	return tipos;
    }

    public TiposMovimentacao encontrarTiposMovimentacao(Long id) {
	EntityManager em = emf.createEntityManager();
	TiposMovimentacao tipo = em.find(TiposMovimentacao.class, id);
	return tipo;
    }
}
