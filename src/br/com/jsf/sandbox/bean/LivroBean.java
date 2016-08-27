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
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jsf.sandbox.dao.AutorDao;
import br.com.jsf.sandbox.dao.LivroDao;
import br.com.jsf.sandbox.modelo.Autor;
import br.com.jsf.sandbox.modelo.Livro;
import br.com.jsf.sandbox.tx.Transacional;


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
	
	private List<String> generos = Arrays.asList("Romance", "Drama", "Ação");
	
	@Inject
	private AutorDao autorDao;
	
	@Inject
	private LivroDao livroDao;
	
	@Inject
	private FacesContext facesContext;
	
	public LivroBean() {
	}

	@Transacional
	public String gravarAutor() {
		Autor autor = autorDao.buscaPorId(autorId);
		this.livro.adicionaAutor(autor);
		return null;
	}

	@Transacional
	public String gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());

		if (livro.getAutores().isEmpty()) {
			// throw new RuntimeException("Livro deve ter pelo menos um
			// Autor.");
			facesContext.addMessage("autor",
					new FacesMessage("Livro deve ter pelo menos um Autor"));
			return null;
		}

		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		} else {
			livroDao.atualiza(this.livro);
		}

		this.livro = new Livro();
		return null;
	}

	public String editar(Livro livro) {
		System.out.println("Editando livro " + livro.getTitulo());
		this.livro = livroDao.buscaPorId(livro.getId());
		return null;
	}

	@Transacional
	public String remover(Livro livro) {
		System.out.println("Removendo livro " + livro.getTitulo());
		livroDao.remove(livro);
		this.livros = livroDao.listaTodos();
		return null;
	}

	public void carregarLivroPeloId() {
		Livro livroBuscado = livroDao.buscaPorId(this.livroId);
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
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
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
		return autorDao.listaTodos();
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
	
	public List<String> getGeneros() {
	    return generos;
	}

}
