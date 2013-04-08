package es.ieci.tecdoc.fwktd.core.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;

/**
 * @author Iecisa
 * @version $Revision$
 * 
 *          Clase para loggear mediante aop excepciones de tipo
 *          <code>RuntimeException</code>
 * 
 *          Ejemplo de configuración:
 * 
 * 
 *          <!-- Configuración de logger advisor --> <bean
 *          id="loggerThrowsAdvice"
 *          class="es.ieci.tecdoc.fwktd.core.aop.LoggerThrowsAdvice">  </bean>
 * 
 *          <!--configuración de autoproxy para todo los beans cuyo nombre que
 *          sigan el patron *DAO --> <bean id="daoBeanAutoProxy" class=
 *          "org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator"
 *          > <property name="beanNames"><value>*DAO</value></property>
 *          <property name="interceptorNames"> <list>
 *          <value>loggerThrowsAdvice</value> </list> </property> </bean>
 * 
 * 
 * 
 */
public class LoggerThrowsAdvice implements ThrowsAdvice {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(LoggerThrowsAdvice.class);

	public void afterThrowing(Method m, Object[] args, Object target,
			RuntimeException ex) {
		StringBuffer msg = new StringBuffer();

		msg.append("Se ha producido una Excepción : \r\n EXCEPCION: ").append(
				ex.getClass()).append("\r\n CLASE: ").append(target.getClass())
				.append("\r\n METODO: ").append(m.getName()).append("\r\n");

		msg.append("  PARAMETROS \r\n");
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				msg.append("  ARG[").append(i).append("]=").append(args[i])
						.append(" \r\n");
			}
		} else {
			msg.append("     null");
		}

		msg.append("\r\n MENSAJE: ").append(ex.getLocalizedMessage()).append(
				"-").append(
						ex.getCause());

		logger.error(msg.toString(), ex);

	}

}
