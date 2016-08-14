package br.com.jsf.sandbox.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

import br.com.jsf.sandbox.model.Livro;

@ManagedBean
public class LivroBean {
	
	private Livro livro = new Livro();
	
	@PostConstruct
	public void postConstruct() {
		System.out.println("Bean criado");
	}
	
	public String gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		return null;
	}
	
	public Livro getLivro() {
        return livro;
    }

}
