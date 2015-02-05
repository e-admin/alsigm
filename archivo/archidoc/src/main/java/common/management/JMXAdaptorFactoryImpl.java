package common.management;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import com.sun.jdmk.comm.AuthInfo;
import com.sun.jdmk.comm.HtmlAdaptorServer;

import es.archigest.framework.core.management.JMXAdaptorFactory;

/**
 * 
 */
public class JMXAdaptorFactoryImpl implements JMXAdaptorFactory {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(JMXAdaptorFactoryImpl.class);

	/**
	 * Constructor.
	 */
	public JMXAdaptorFactoryImpl() {
	}

	/**
	 * Crea y registra los adaptadores JMX.
	 */
	public void createAndRegister(MBeanServer mbs, int port, String username,
			String password) {
		try {
			AuthInfo users[] = { new AuthInfo(username, password) };

			HtmlAdaptorServer adaptorServer;
			if (username != null && password != null) {
				adaptorServer = new HtmlAdaptorServer(port, users);
			} else {
				adaptorServer = new HtmlAdaptorServer(port);
				logger.warn("Se ha arrancado la consola de monitorizacion sin usuario/contrase\361a. Debiera suministrarse un par usuario/contrase\361a en las propiedades JMX.HTTP.USERNAME/JMX.HTTP.PASSWORD en el fichero web.xml");
			}

			ObjectName adaptorObjectName = new ObjectName(
					mbs.getDefaultDomain() + ":type=htmladaptor,port=" + port);
			logger.info("Se esta usando la autenticacion : "
					+ adaptorServer.isAuthenticationOn());

			mbs.registerMBean(adaptorServer, adaptorObjectName);
			adaptorServer.start();
		} catch (Exception e) {
			logger.error(
					"Error creando consola HTML. Consola no se puede crear.", e);
		}
	}

}
