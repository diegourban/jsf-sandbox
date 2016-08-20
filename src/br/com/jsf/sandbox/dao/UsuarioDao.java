package br.com.jsf.sandbox.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.jsf.sandbox.model.Usuario;

public class UsuarioDao {

	public boolean existe(Usuario usuario) {
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.email = :pEmail and u.senha = :pSenha", Usuario.class);
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
			em.close();
		}

		return false;
	}

}
