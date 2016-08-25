package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.jsf.sandbox.dao.DAO;
import br.com.jsf.sandbox.model.Autor;

@Named
@ViewScoped
public class AutorBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1282015562923495178L;

	private Integer autorId;

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public String gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if(this.autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		} else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
		
		return "livro?faces-redirect=true";
	}
	
	public String editar(Autor autor) {
		System.out.println("Editando autor " + autor.getNome());
		this.autor = autor;
		return "";
	}
	
	public String remover(Autor autor) {
		System.out.println("Removendo autor " + autor.getNome());
		new DAO<Autor>(Autor.class).remove(autor);
		return "";
	}
	
	public List<Autor> getAutores() {
        return new DAO<Autor>(Autor.class).listaTodos();
    }
	
	public void carregarAutorPeloId() {
		Autor autorBuscado = new DAO<Autor>(Autor.class).buscaPorId(this.autorId);
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
