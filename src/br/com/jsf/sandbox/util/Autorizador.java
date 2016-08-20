package br.com.jsf.sandbox.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.jsf.sandbox.model.Usuario;

public class Autorizador implements PhaseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6804904796409817860L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		String nomeDaPagina = facesContext.getViewRoot().getViewId();
		
		if("/login.xhtml".equals(nomeDaPagina)) {
			return;
		}
		
		Usuario usuarioLogado = (Usuario) facesContext.getExternalContext().getSessionMap().get("usuarioLogado");
		if(usuarioLogado != null) {
			return;
		}
		
		NavigationHandler handler = facesContext.getApplication().getNavigationHandler();
		handler.handleNavigation(facesContext, null, "login?faces-redirect=true");
		facesContext.getRenderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
