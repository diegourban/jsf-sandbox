package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jsf.sandbox.dao.AutorDao;
import br.com.jsf.sandbox.modelo.Autor;
import br.com.jsf.sandbox.tx.Transacional;

@Named
@ViewScoped
public class AutorBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1282015562923495178L;

	private Integer autorId;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao autorDao;

	public Autor getAutor() {
		return autor;
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null) {
			this.autorDao.adiciona(this.autor);
		} else {
			this.autorDao.atualiza(this.autor);
		}
		
		return "livro?faces-redirect=true";
	}
	
	@Transacional
	public String editar(Autor autor) {
		System.out.println("Editando autor " + autor.getNome());
		this.autor = autor;
		return "";
	}
	
	@Transacional
	public String remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		this.autorDao.remove(autor);
		return "";
	}
	
	public List<Autor> getAutores() {
		return this.autorDao.listaTodos();
    }
	
	public void carregarAutorPeloId() {
		Autor autorBuscado = this.autorDao.buscaPorId(this.autorId);
		if(autorBuscado == null) {
			this.autor = new Autor();
		} else {
			this.autor = autorBuscado;
		}
	}
	
	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
}
