package ieci.tecdoc.sgm.migration.config;


import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.utils.Constantes;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config extends Constantes {

	private static Properties configProp = new Properties(); 
	private static String FILE = "configuration.properties";
	private static Config instance = null;
	
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Config.class);
	
	/*
	 * Método singleton que devuelve una instancia de la clase y
	 * que permite cargar el fichero properties de configuración 
	 * @throws MigracionRegisterException si ocurre algún error.
	 */
	public static Config getInstance() throws MigrationException {
		if(instance == null) instance = new Config();
		return instance;
	}
	
	/*
	 * Constructor de la clase que hace un load properties del fichero de configuración de la aplicación
	 * @throws MigracionRegisterException si ocurre algún error. 
	 */
	private Config() throws MigrationException {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILE);
		try {             
			configProp.load(in);      
			logger.debug("Se ha cargado la configuración con éxito");
		} catch (IOException e) {             
			throw new MigrationException("Error al cargar el fichero de configuración ", e);         
		}
	}
	
	// Ejecución del cron
	public boolean getEjecution() { 
		return Boolean.parseBoolean(configProp.getProperty(EJECUTION)); 
	}
	
	// Saca trazas por consola
	public boolean getTraceSystem() { 
		return Boolean.parseBoolean(configProp.getProperty(TRACE_SYSTEM)); 
	}
	
	// Saca trazas por consola
	public String getEntornoLocal() { 
		return configProp.getProperty(ENTORNO_LOCAL); 
	}
	
	// EndPoint del Servicio Web Origen desplegado en Sigem Housing 
	public String getEndPointOrigen() { 
		return configProp.getProperty(ENT_POINT_SW_REGISTRO_ORIGEN); 
	}
	
	// EndPoint del Servicio Web Destino desplegado en  Sigem UAM
	public String getEndPointDestino() { 
		return configProp.getProperty(ENT_POINT_SW_REGISTRO_DESTINO);
	}
	
	// IdFld del campo CONSOLIDADO del libro de entrada y salida 
	public String getFieldIdConsolidated() { 
		return configProp.getProperty(ID_FLD_CONSOLIDATED); 
	}
	
	// Código de entidad de Sigem Housing
	public String getOrigenEntity() { 
		return configProp.getProperty(ENTITY_ORIGEN);
	}
	
	// Código de entidad de Sigem UAM
	public String getDestinoEntity() { 
		return configProp.getProperty(ENTITY_DESTINO);
	}
	
	// Usuario de registro presencial de Sigem Housing (Origen)
	public String getOrigenUser() { 
		return configProp.getProperty(ORIGEN_USER_REGISTER); 
	}            
	
	// Password de usuario de registro presencial de Sigem Housing (Origen)
	public String getOrigenPassword() { 
		return configProp.getProperty(ORIGEN_PASSWORD_REGISTER); 
	}       
	
	// Idioma de usuario de registro presencial de Sigem Housing (Origen)
	public String getOrigenIdioma() { 
		return configProp.getProperty(ORIGEN_IDIOMA_USER); 
	}
	
	// Usuario de registro presencial de Sigem UAM (Destino)
	public String getDestinoUser() { 
		return configProp.getProperty(DESTINO_USER_REGISTER); 
	}            
	
	// Contraseña de usuario de registro presencial de Sigem UAM (Destino)
	public String getDestinoPassword() { 
		return configProp.getProperty(DESTINO_PASSWORD_REGISTER); 
	}       
	
	// Idioma de usuario de registro presencial de Sigem UAM (Destino)
	public String getDestinoIdioma() { 
		return configProp.getProperty(DESTINO_IDIOMA_USER); 
	}
	
	// Identificador del libro de entrada para el registro presencial de Sigem Housing (Origen)
	public String getOrigenIdBookEntrada() { 
		return configProp.getProperty(ORIGEN_ID_BOOK_ENTRADA); 
	}
	
	// Identificador del libro de salida para el registro presencial de Sigem Housing (Origen)
	public String getOrigenIdBookSalida() { 
		return configProp.getProperty(ORIGEN_ID_BOOK_SALIDA); 
	}
	
	// Identificador del libro de entrada para el registro presencial de Sigem UAM (Destino)
	public String getDestinoIdBookEntrada() { 
		return configProp.getProperty(DESTINO_ID_BOOK_ENTRADA); 
	}
	
	// Identificador del libro de salida para el registro presencial de Sigem UAM (Destino)
	public String getDestinoIdBookSalida() { 
		return configProp.getProperty(DESTINO_ID_BOOK_SALIDA); 
	}
	
	// IdFld del campo Tipo de Transporte del libro de Entrada de SIGEM UAM (Destino)
	public String getDestinoIdTipoTransporteEntrada() { 
		return configProp.getProperty(DESTINO_ID_TIPO_TRANSPORTE_ENTRADA); 
	}
	
	// IdFld del campo Tipo de Transporte del libro de Salida de SIGEM UAM (Destino)
	public String getDestinoIdTipoTransporteSalida() { 
		return configProp.getProperty(DESTINO_ID_TIPO_TRANSPORTE_SALIDA); 
	}
	
	// Texto que se muestra en el campo Campo de Tipo de Transporte para los libros de entrada y salida.
	public String getDestinoIdBBDDScrTTElectronico() { 
		return configProp.getProperty(DESTINO_ID_BBDD_SRC_TT_ELECTRONICO); 
	}
	
	// FldId Tipo de Registro del libro de Entrada de SIGEM UAM (Destino)
	public String getDestinoIdTipoRegistroEntrada() { 
		return configProp.getProperty(DESTINO_ID_TIPO_REGISTRO_ENTRADA); 
	}
	
	// FldId Fecha de Registro Original para el libro de Entrada de Sigem Housing (Origen)
	public String getDestinoIdFechaRegistroOriginalEntrada() { 
		return configProp.getProperty(DESTINO_FIELDID_FECHA_REGISTRO_ORIG_ENTRADA); 
	}
	
	// FldId Fecha de Trabajo para el libro de Entrada de Sigem Housing (Origen)
	public String getDestinoIdFechaTrabajoRegistroEntrada() { 
		return configProp.getProperty(DESTINO_FIELDID_FECHA_TRABAJO_ENTRADA); 
	}
	
	// FldId Fecha de Trabajo para el libro de Entrada de Sigem Housing (Origen)
	public String getDestinoIdFechaTrabajoRegistroSalida() { 
		return configProp.getProperty(DESTINO_FIELDID_FECHA_TRABAJO_SALIDA); 
	}
	
	/** Envío de correo con el resultado de la migración de registros **/
	
	// Correo electrónico desde donde se envía el email (Remitente)
	public String getEmailOrigen() { 
		return configProp.getProperty(EMAIL_ORIGEN); 
	}
	
	// Correo electrónico a quien se envía el email (Destinatario)
	public String getEmailDestino() { 
		return configProp.getProperty(EMAIL_DESTINO); 
	}
	
	// Usuario de correo SMTP
	public String getEmailUserSMTP() { 
		return configProp.getProperty(EMAIL_USER_SMTP); 
	}
	
	// Contraseña de usuario de correo SMTP
	public String getEmailPasswordSMTP() { 
		return configProp.getProperty(EMAIL_PWD_SMTP); 
	}
	
	// Servidor SMTP de correo
	public String getEmailServerHostSMTP() { 
		return configProp.getProperty(EMAIL_HOST_SMTP); 
	}
	
	// Puerto del servidor de correo SMTP
	public String getEmailServerPortSMTP() { 
		return configProp.getProperty(EMAIL_POSRT_SMTP); 
	}
}
