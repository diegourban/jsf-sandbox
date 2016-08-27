package br.com.jsf.sandbox.dao;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.jsf.sandbox.modelo.Usuario;

@Stateless
public class UsuarioDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3577317111540941927L;

	@PersistenceContext
	EntityManager entityManager;

	public boolean existe(Usuario usuario) {
		TypedQuery<Usuario> query = entityManager.createQuery(
				" select u from Usuario u "
						+ " where u.email = :pEmail and u.senha = :pSenha",
				Usuario.class);

		query.setParameter("pEmail", usuario.getEmail());
		query.setParameter("pSenha", usuario.getSenha());
		try {
			@SuppressWarnings("unused")
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}

		return true;
	}

}
