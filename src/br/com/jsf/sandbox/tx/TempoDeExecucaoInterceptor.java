package br.com.jsf.sandbox.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class TempoDeExecucaoInterceptor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1808102273755722275L;

	@AroundInvoke
	public Object intercepta(InvocationContext context) throws Exception {
		long millis = System.currentTimeMillis();

		Object o = context.proceed();

		String nomeClasse = context.getTarget().getClass().getSimpleName();
		String nomeMetodo = context.getMethod().getName();
		System.out.println("[INFO] " + nomeClasse + "->" + nomeMetodo);
		System.out.println("[INFO] Tempo gasto no acesso ao BD: " + (System.currentTimeMillis() - millis) + "ms");

		return o;
	}

}
