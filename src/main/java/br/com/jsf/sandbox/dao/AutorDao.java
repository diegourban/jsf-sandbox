package br.com.jsf.sandbox.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.jsf.sandbox.modelo.Autor;

@Stateless
public class AutorDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5132936211064819069L;

	@PersistenceContext
	private EntityManager entityManager;
	
	private DAO<Autor> dao;
	
	@PostConstruct
	public void init() {
		this.dao = new DAO<Autor>(this.entityManager, Autor.class);
	}

	public void adiciona(Autor t) {
		dao.adiciona(t);
	}

	public void remove(Autor t) {
		dao.remove(t);
	}

	public void atualiza(Autor t) {
		dao.atualiza(t);
	}

	public List<Autor> listaTodos() {
		return dao.listaTodos();
	}

	public Autor buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}

}
