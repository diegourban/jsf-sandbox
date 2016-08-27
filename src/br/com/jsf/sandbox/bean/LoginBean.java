package br.com.jsf.sandbox.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.jsf.sandbox.dao.UsuarioDao;
import br.com.jsf.sandbox.modelo.Usuario;

@Named
@SessionScoped
public class LoginBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3909392460124650158L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	private UsuarioDao usuarioDao;
	
	@Inject
	private FacesContext facesContext;

	public Usuario getUsuario() {
		return usuario;
	}
	
	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário " + this.usuario.getEmail());
		
		boolean existe = usuarioDao.existe(usuario);
		if(existe) {
			facesContext.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		// problema usando redirect no return é que o addMessage só existe por uma requisição
		facesContext.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		// escopo flash dura duas requisições
		facesContext.getExternalContext().getFlash().setKeepMessages(true);
		
		return "login?faces-redirect=true"; 
		// usando redirect para limpar os dados e garantir que não existe mal intencionados querendo inspecionar
	}
	
	public String deslogar() {
		System.out.println("Fazendo logout do usuário " + this.usuario.getEmail());
		facesContext.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

}
