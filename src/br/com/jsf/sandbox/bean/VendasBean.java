package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.jsf.sandbox.dao.DAO;
import br.com.jsf.sandbox.model.Livro;
import br.com.jsf.sandbox.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170904984979474031L;

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		        
        ChartSeries vendaSerie2015 = new ChartSeries();
        vendaSerie2015.setLabel("Vendas 2015");
        
        List<Venda> vendas = getVendas(1234);
        for (Venda venda : vendas) {
			vendaSerie2015.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
        
        model.addSeries(vendaSerie2015);
        
        
        ChartSeries vendaSerie2016 = new ChartSeries();
        vendaSerie2016.setLabel("Vendas 2016");
      
       vendas = getVendas(4321);
        for (Venda venda : vendas) {
			vendaSerie2016.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
        
        model.addSeries(vendaSerie2016);
 
        
         
        return model;
	}
	
	public List<Venda> getVendas(long seed) {
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();
		
		Random random = new Random(seed);
		
		for(Livro livro: livros) {
			Integer quantidade = random.nextInt(200);
			vendas.add(new Venda(livro, quantidade));
		}
		
		return vendas;
	}

}
