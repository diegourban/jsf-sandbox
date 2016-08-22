package br.com.jsf.sandbox.model;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.jsf.sandbox.dao.DAO;

public class LivroDataModel extends LazyDataModel<Livro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -423879738937995474L;
	
	private DAO<Livro> dao = new DAO<>(Livro.class);
	
	public LivroDataModel() {
	    super.setRowCount(dao.quantidadeDeElementos());
	}
	
	@Override
	public List<Livro> load(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) {
		String titulo = (String) filters.get("titulo"); 
		return dao.listaTodosPaginada2(first, pageSize, "titulo", titulo);
	}

}
