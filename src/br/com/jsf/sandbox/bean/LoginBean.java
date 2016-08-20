package br.com.jsf.sandbox.bean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.com.jsf.sandbox.dao.UsuarioDao;
import br.com.jsf.sandbox.model.Usuario;

@ManagedBean
@ViewScoped
public class LoginBean {

	private Usuario usuario = new Usuario();

	public Usuario getUsuario() {
		return usuario;
	}
	
	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário " + this.usuario.getEmail());
		
		boolean existe = new UsuarioDao().existe(usuario);
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		if(existe) {
			currentInstance.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return "livro?faces-redirect=true";
		}
		
		// problema usando redirect no return é que o addMessage só existe por uma requisição
		currentInstance.addMessage(null, new FacesMessage("Usuário não encontrado"));
		
		// escopo flash dura duas requisições
		currentInstance.getExternalContext().getFlash().setKeepMessages(true);
		
		return "login?faces-redirect=true"; 
		// usando redirect para limpar os dados e garantir que não existe mal intencionados querendo inspecionar
	}
	
	public String deslogar() {
		System.out.println("Fazendo logout do usuário " + this.usuario.getEmail());
		FacesContext currentInstance = FacesContext.getCurrentInstance();
		currentInstance.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}

}
