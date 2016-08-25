package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.com.jsf.sandbox.dao.DAO;
import br.com.jsf.sandbox.model.Autor;
import br.com.jsf.sandbox.model.Livro;
import br.com.jsf.sandbox.model.LivroDataModel;


@Named
@ViewScoped
public class LivroBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4394564641331466669L;

	private Integer livroId;

	private Livro livro = new Livro();

	private Integer autorId;

	private List<Livro> livros;
	
	private LivroDataModel livroDataModel = new LivroDataModel();
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");

	public String gravarAutor() {
		Autor autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
		return null;
	}

	public String gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um
			// Autor.");
			FacesContext.getCurrentInstance().addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor"));
			return null;
		}

		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livro.getId() == null) {
			dao.adiciona(this.livro);
			this.livros = dao.listaTodos();
		} else {
			dao.atualiza(this.livro);
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
		if (livroBuscado == null) {
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
		if (!valor.startsWith("9")) {
			throw new ValidatorException(new FacesMessage("ISBN deve começar com 9"));
		}
	}

	public List<Livro> getLivros() {
		DAO<Livro> dao = new DAO<Livro>(Livro.class);
		if (this.livros == null) {
			this.livros = dao.listaTodos();
		}
		return this.livros;
	}

	public boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) {
		// tirando espaços do filtro
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();

		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " + valorColuna);

		// o filtro é nulo ou vazio?
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}

		// elemento da tabela é nulo?
		if (valorColuna == null) {
			return false;
		}

		try {
			// fazendo o parsing do filtro para converter para Double
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;

			// comparando os valores, compareTo devolve um valor negativo se o
			// value é menor do que o filtro
			return precoColuna.compareTo(precoDigitado) < 0;

		} catch (NumberFormatException e) {

			// usuario nao digitou um numero
			return false;
		}
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
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
	
	public LivroDataModel getLivroDataModel() {
		return livroDataModel;
	}
	
	public void setLivroDataModel(LivroDataModel livroDataModel) {
		this.livroDataModel = livroDataModel;
	}
	
	public List<String> getGeneros() {
	    return generos;
	}

}
