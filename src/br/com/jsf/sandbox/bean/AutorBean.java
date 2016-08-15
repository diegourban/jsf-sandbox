package br.com.jsf.sandbox.bean;

import javax.faces.bean.ManagedBean;

import br.com.jsf.sandbox.dao.DAO;
import br.com.jsf.sandbox.model.Autor;

@ManagedBean
public class AutorBean {

	private Autor autor = new Autor();

	public Autor getAutor() {
		return autor;
	}

	public void gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		new DAO<Autor>(Autor.class).adiciona(this.autor);
	}
}
