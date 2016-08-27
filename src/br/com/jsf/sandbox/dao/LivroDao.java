package br.com.jsf.sandbox.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.jsf.sandbox.modelo.Livro;
import br.com.jsf.sandbox.tx.Log;

public class LivroDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5835674393334757448L;

	@Inject
	private EntityManager entityManager;

	private DAO<Livro> dao;

	@PostConstruct
	public void init() {
		this.dao = new DAO<Livro>(this.entityManager, Livro.class);
	}

	public void adiciona(Livro t) {
		dao.adiciona(t);
	}

	public void remove(Livro t) {
		dao.remove(t);
	}

	public void atualiza(Livro t) {
		dao.atualiza(t);
	}

	@Log
	public List<Livro> listaTodos() {
		return dao.listaTodos();
	}

	public Livro buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}

	public List<Livro> listaTodosPaginada2(int firstResult, int maxResults, String coluna, String valor) {
		return dao.listaTodosPaginada2(firstResult, maxResults, coluna, valor);
	}

}
