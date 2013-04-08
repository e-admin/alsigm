package ieci.tecdoc.sgm.admin.database;


import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlElements;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.core.config.ports.PortsConfig;
import ieci.tecdoc.sgm.admin.AdministracionException;
import ieci.tecdoc.sgm.admin.beans.AplicacionImpl;
import ieci.tecdoc.sgm.admin.beans.Aplicaciones;
import ieci.tecdoc.sgm.admin.interfaces.Aplicacion;

import java.io.InputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;



/**
 * Clase de tratamiento de los datos xml
 * @author IECISA
 */

public class AplicacionDatos extends AplicacionImpl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2810406837255653493L;
	private static final Logger logger = Logger.getLogger(AplicacionDatos.class);
	protected boolean isDebugeable = true;

	public AplicacionDatos() {
		super();
	}

	/**
	 * Realiza la consulta por Aplicacion / contraseña.
	 *
	 * @param extension Extension.
	 * @throws DbExcepcion Si se produce algún error.
	 */
	public Aplicacion load(String identificador) throws AdministracionException {
		String applicationFile ="Aplicaciones.xml";
		AplicacionImpl aplicacion=new AplicacionImpl();
		XmlDocument appXml = new XmlDocument();
		XmlElement root = null;
		XmlElements appNodes = null;

		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de Aplicacion...");
		}

		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(applicationFile);
			appXml.createFromUtf8Text(Goodies.getBytes(is));
			root = appXml.getRootElement();
			appNodes = root.getChildElements();
			for (int i = 1; i<=appNodes.getCount();i++){
				if (appNodes.getItem(i).getDescendantElement(AplicacionEtiquetas.identificador).getValue().equals(identificador)){
					aplicacion.setIdentificador(identificador);
					aplicacion.setDefinicion(appNodes.getItem(i).
							getChildElement(AplicacionEtiquetas.definicion).getValue());
					aplicacion.setServidor(appNodes.getItem(i).
							getChildElement(AplicacionEtiquetas.servidor).getValue());
					// aplicacion.setPuertoApp(appNodes.getItem(i).
					//		getChildElement(AplicacionEtiquetas.puertoApp).getValue());
					aplicacion.setPuertoApp(PortsConfig.getHttpsPort());
					aplicacion.setProtocolo(appNodes.getItem(i).
							getChildElement(AplicacionEtiquetas.protocolo).getValue());
					aplicacion.setContextoApp(appNodes.getItem(i).
							getChildElement(AplicacionEtiquetas.contextoApp).getValue());
				}
			}

		} catch (Exception e) {
			logger.error("Error recogiendo datos de Aplicacion.", e);
			throw new AdministracionException(AdministracionException.EC_APP_NOT_FOUND, e.getCause());
		}
		return aplicacion;
	}

	public Aplicaciones loadAll() throws AdministracionException {
		String applicationFile ="Aplicaciones.xml";
		Aplicaciones aplicaciones = new Aplicaciones();
		XmlDocument appXml = new XmlDocument();
		XmlElement root = null;
		XmlElements appNodes = null;

		if(logger.isDebugEnabled()){
			logger.debug("Obteniendo datos de Aplicacion...");
		}

		try {
			InputStream is = this.getClass().getClassLoader().getResourceAsStream(applicationFile);
			appXml.createFromUtf8Text(Goodies.getBytes(is));
			root = appXml.getRootElement();
			appNodes = root.getChildElements();
			for (int i = 1; i<=appNodes.getCount();i++){
				AplicacionImpl aplicacion=new AplicacionImpl();
				aplicacion.setIdentificador(appNodes.getItem(i).
						getChildElement(AplicacionEtiquetas.identificador).getValue());
				aplicacion.setDefinicion(appNodes.getItem(i).
						getChildElement(AplicacionEtiquetas.definicion).getValue());
				aplicacion.setServidor(appNodes.getItem(i).
						getChildElement(AplicacionEtiquetas.servidor).getValue());
				// aplicacion.setPuertoApp(appNodes.getItem(i).
				//		getChildElement(AplicacionEtiquetas.puertoApp).getValue());
				aplicacion.setPuertoApp(PortsConfig.getHttpsPort());				
				aplicacion.setProtocolo(appNodes.getItem(i).
						getChildElement(AplicacionEtiquetas.protocolo).getValue());
				aplicacion.setContextoApp(appNodes.getItem(i).
						getChildElement(AplicacionEtiquetas.contextoApp).getValue());
				aplicaciones.add(aplicacion);
			}
		} catch (Exception e) {
			logger.error("Error recogiendo datos de Aplicacion.", e);
			throw new AdministracionException(AdministracionException.EC_APP_NOT_FOUND, e.getCause());
		}
		return aplicaciones;
	}



}
