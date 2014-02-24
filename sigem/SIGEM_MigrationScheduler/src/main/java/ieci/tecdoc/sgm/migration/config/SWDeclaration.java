package ieci.tecdoc.sgm.migration.config;

import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebService;
import ieci.tecdoc.sgm.registropresencial.ws.server.ServicioRegistroWebServiceServiceLocator;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

public class SWDeclaration {

	/**
	 * Instancia singleton de la clase
	 */
	public static SWDeclaration instance = null;
	
	/**
	 * URL del SW de SIGEM Housing
	 */
	protected String endPointOrigen = null;
	
	/**
	 * URL del SW de SIGEM UAM
	 */
	protected String endPointDestino = null;
	
	/**
	 * SW de SIGEM Housing
	 */
	protected ServicioRegistroWebService oServicioOrigen = null;
	
	/**
	 * SW de SIGEM UAM
	 */
	protected ServicioRegistroWebService oServicioDestino = null;
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(SWDeclaration.class);
	
	
	public static SWDeclaration getInstance() throws MigrationException, ServiceException {
		if(instance == null) return new SWDeclaration();
		return instance;
	}
	
	/**
	 * Método inicial que instancia los SW a partir del fichero de configuración de la aplicación
	 * @throws MigrationException - Error lanzado en caso de que se produzca un error al cargar los datos del fichero de configuración
	 * @throws ServiceException - Error lanzado en caso de que se produzca un error al instanciar los SW
	 */
	public void init() throws MigrationException, ServiceException {
		endPointOrigen = Config.getInstance().getEndPointOrigen();
		endPointDestino = Config.getInstance().getEndPointDestino();
		oServicioOrigen = getService(endPointOrigen);
		oServicioDestino = getService(endPointDestino);
	}
	
	public SWDeclaration() throws MigrationException, ServiceException  {
		try {
			init();
		} catch (MigrationException e1) {
			logger.error("Error al cargar los datos de configuracion", e1);
			throw e1;
		} 
	}
	
	/**
	 * Declaración de los SW de Registro Presencial
	 * @param endPoint - URL del SW
	 * @return ServicioRegistroWebService - SW
	 * @throws ServiceException - Error de instancia del SW
	 */
	private ServicioRegistroWebService getService(String endPoint) throws ServiceException {
		try {
			ServicioRegistroWebServiceServiceLocator oLocator = new ServicioRegistroWebServiceServiceLocator();
			oLocator.setServicioRegistroWebServiceEndpointAddress(endPoint);
			return oLocator.getServicioRegistroWebService();
		} catch (ServiceException e) {
			logger.error("Error al instanciar el SW de registro presencial para el endPoint: " + endPoint, e);
			throw e;
		}
	}

	public ServicioRegistroWebService getoServicioOrigen() {
		return oServicioOrigen;
	}

	public void setoServicioOrigen(ServicioRegistroWebService oServicioOrigen) {
		this.oServicioOrigen = oServicioOrigen;
	}

	public ServicioRegistroWebService getoServicioDestino() {
		return oServicioDestino;
	}

	public void setoServicioDestino(ServicioRegistroWebService oServicioDestino) {
		this.oServicioDestino = oServicioDestino;
	}
}
