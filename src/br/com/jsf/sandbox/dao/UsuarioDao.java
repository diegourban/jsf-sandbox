package br.com.jsf.sandbox.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.jsf.sandbox.modelo.Usuario;

public class UsuarioDao implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3577317111540941927L;
	
	@Inject
	EntityManager entityManager;

	public boolean existe(Usuario usuario) {
		TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		
		try {
			Usuario result = query.getSingleResult();
			if(result != null) {
				return true;
			}
		} catch (NoResultException e) {
			return false;
		} finally {
			entityManager.close();
		}

		return false;
	}

}
