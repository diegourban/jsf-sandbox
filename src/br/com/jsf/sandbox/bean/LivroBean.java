package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import br.com.jsf.sandbox.dao.DAO;
import br.com.jsf.sandbox.model.Autor;
import br.com.jsf.sandbox.model.Livro;

@ManagedBean
// @RequestScoped //por default, o bean é request scoped e ele sobrevive apenas no request
@ViewScoped // para garantir que o bean sobreviva enquanto a tela existir
public class LivroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2230866936064983999L;
	
	private Integer livroId;

	private Livro livro = new Livro();
	
	private Integer autorId;
	
	public String gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
		return null;
	}

	public String gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
			return null;
		}

		if(this.livro.getId() == null) {
			new DAO<Livro>(Livro.class).adiciona(this.livro);
		} else {
			new DAO<Livro>(Livro.class).atualiza(this.livro);
		}
		
		this.livro = new Livro();
		return null;
	}
	
	public String editar(Livro livro) {
		System.out.println("Editando livro " + livro.getTitulo());
		this.livro = livro;
		return null;
	}
	
	public String remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		new DAO<Livro>(Livro.class).remove(livro);
		return null;
	}
	
	public void carregarLivroPeloId() {
		Livro livroBuscado = new DAO<Livro>(Livro.class).buscaPorId(this.livroId);
		if(livroBuscado == null) {
			this.livro = new Livro();
		} else {
			this.livro = livroBuscado;
		}
	}
	
	public String removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
		return null;
	}
	
	public void comecaComDigitoNove(FacesContext fc, UIComponent component, Object value) throws ValidatorException {
		String valor = value.toString();
		if(!valor.startsWith("9")) {
			throw new ValidatorException(new FacesMessage("ISBN deve começar com 9"));
		}
	}
	
	public List<Livro> getLivros() {
        return new DAO<Livro>(Livro.class).listaTodos();
    }
	
	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}
	
	public Livro getLivro() {
		return livro;
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}
	
	public Integer getAutorId() {
		return autorId;
	}
	
	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Integer getLivroId() {
		return livroId;
	}
	
	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

}
