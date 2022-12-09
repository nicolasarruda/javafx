package db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.entities.Usuario;

public class UsuarioDB {

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("db");

    private Usuario usuario;

    public UsuarioDB(Usuario usuario) {
	this.usuario = usuario;
    }

    public List<Usuario> procurarUsarios() {
	EntityManager em = emf.createEntityManager();
	Query query = em.createNativeQuery("select * from usuario", Usuario.class);
	List<Usuario> usuarios = query.getResultList();
	return usuarios;
    }

    public String recuperarSenha(String nome) {
	for (Usuario u : procurarUsarios()) {
	    if (u.getNome().compareTo(nome) == 0) {
		return u.getSenha();
	    }
	}
	return null;
    }

    public boolean procurarUsuarioPorNomeSenha(String nome, String senha) {
	EntityManager em = emf.createEntityManager();
	for (Usuario u : procurarUsarios()) {
	    if (u.getNome().compareTo(nome) == 0 && u.getSenha().compareTo(senha) == 0) {
		usuario = em.find(Usuario.class, u.getId());
		return true;
	    }
	}
	return false;
    }

}