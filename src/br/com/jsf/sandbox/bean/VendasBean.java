package br.com.jsf.sandbox.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import br.com.jsf.sandbox.dao.VendaDao;
import br.com.jsf.sandbox.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170904984979474031L;
	
	@Inject
	private VendaDao vendaDao;

	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		        
        ChartSeries vendaSerie2016 = new ChartSeries();
        vendaSerie2016.setLabel("Vendas 2016");
      
        List<Venda> vendas = getVendas();
        for (Venda venda : vendas) {
			vendaSerie2016.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
        
        model.addSeries(vendaSerie2016);
 
        
         
        return model;
	}
	
	public List<Venda> getVendas() {
		return vendaDao.listaTodos();
	}

}
