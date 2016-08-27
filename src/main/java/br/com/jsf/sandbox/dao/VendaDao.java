package br.com.jsf.sandbox.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.jsf.sandbox.modelo.Venda;

@Stateless
public class VendaDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1742310599992922284L;

	@PersistenceContext
	private EntityManager entityManager;

	private DAO<Venda> dao;

	@PostConstruct
	public void init() {
		this.dao = new DAO<Venda>(this.entityManager, Venda.class);
	}

	public void adiciona(Venda t) {
		dao.adiciona(t);
	}

	public void remove(Venda t) {
		dao.remove(t);
	}

	public void atualiza(Venda t) {
		dao.atualiza(t);
	}

	public List<Venda> listaTodos() {
		return dao.listaTodos();
	}

	public Venda buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public int contaTodos() {
		return dao.contaTodos();
	}

	public int quantidadeDeElementos() {
		return dao.quantidadeDeElementos();
	}

	public List<Venda> listaTodosPaginada(int firstResult, int maxResults) {
		return dao.listaTodosPaginada(firstResult, maxResults);
	}

}
