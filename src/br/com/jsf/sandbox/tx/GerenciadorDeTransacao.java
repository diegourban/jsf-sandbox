package br.com.jsf.sandbox.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Interceptor
@Transacional
public class GerenciadorDeTransacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1908692287573679331L;
	
	@Inject
	private EntityManager entityManager;

	@AroundInvoke
	public Object executaTX(InvocationContext context) throws Exception {
		System.out.println("Iniciando transação...");
		entityManager.getTransaction().begin();

		String nomeClasse = context.getTarget().getClass().getSimpleName();
		String nomeMetodo = context.getMethod().getName();
		System.out.println("Executando processo do contexto: " + nomeClasse + "->" + nomeMetodo);
		Object result = context.proceed();
		
		System.out.println("Comitando transação...");
		entityManager.getTransaction().commit();
		
		return result;
	}

}
