package br.com.jsf.sandbox.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.jsf.sandbox.dao.LivroDao;

public class LivroDataModel extends LazyDataModel<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -423879738937995474L;
	
	private LivroDao livroDao;
	
	public LivroDataModel(LivroDao livroDao) {
		this.livroDao = livroDao;
		super.setRowCount(livroDao.quantidadeDeElementos());
	}

	@Override
	public List<Livro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String titulo = (String) filters.get("titulo");
		return livroDao.listaTodosPaginada2(first, pageSize, "titulo", titulo);
	}

}
