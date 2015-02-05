package descripcion.model.xsl;

import gcontrol.model.TipoAcceso;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import se.usuarios.ServiceClient;
import xml.config.ConfiguracionArchivoManager;

import common.util.FileHelper;

import descripcion.exceptions.NotDeclaredXSLFichaException;
import descripcion.exceptions.NotFoundXSLFichaException;

/**
 * Factoría para cargar las plantillas XSL.
 */
public class TemplateFactory {

	/** Logger de la clase. */
	private static Logger logger = Logger.getLogger(TemplateFactory.class);

	/** Cliente del servicio. */
	protected ServiceClient serviceClient = null;

	/**
	 * Constructor.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 */
	private TemplateFactory(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
	}

	/**
	 * Instancia un objeto TemplateFactory.
	 * 
	 * @param serviceClient
	 *            Cliente del servicio.
	 * @return Instancia de TemplateFactory.
	 */
	public static TemplateFactory getInstance(ServiceClient serviceClient) {
		return new TemplateFactory(serviceClient);
	}

	/**
	 * Obtiene la plantilla XSL de un usuario.
	 * 
	 * @param tipoAcceso
	 *            Tipo de acceso ({@link TipoAcceso}).
	 * @return Plantilla XSL.
	 */
	public String getTemplate(int tipoAcceso) {
		String template = null;
		String fileName = null;

		try {
			// ConfiguracionDescripcion cfgDesc =
			// ConfiguracionSistemaArchivoFactory
			// .getConfiguracionSistemaArchivo().getConfiguracionDescripcion();

			fileName = ConfiguracionArchivoManager.getInstance()
					.getRutaPlantillaXSL(tipoAcceso);

			// if (TipoAcceso.CONSULTA == tipoAcceso)
			// fileName = cfgDesc.getPlantillaXSLConsulta();
			// else if (TipoAcceso.EDICION == tipoAcceso)
			// fileName = cfgDesc.getPlantillaXSLEdicion();
			// else if(TipoAcceso.EDICION == tipoAcceso)
			// fileName = cfgDesc.getPlantillaXSLExportacion();
			if (StringUtils.isEmpty(fileName))
				throw new NotDeclaredXSLFichaException(
						"XSL no declarada en fichero de configuracion archivo.cfg");
			else
				template = FileHelper.getTextFileContent(fileName);
		} catch (FileNotFoundException f) {
			logger.error("Error al buscar la plantilla XSL por defecto ["
					+ fileName + "]", f);
			throw new NotFoundXSLFichaException("XSL no encontrada");
		} catch (IOException e) {
			logger.error("Error al leer la plantilla XSL por defecto ["
					+ fileName + "]", e);
		}

		return template;
	}
}
