package se.procedimientos.archigest;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import se.NotAvailableException;
import se.procedimientos.GestorCatalogo;
import se.procedimientos.IProcedimiento;
import se.procedimientos.ProcedimientoImpl;
import se.procedimientos.archigest.stub.ProcedimientoVO;
import se.procedimientos.archigest.stub.WSCatalogo;
import se.procedimientos.archigest.stub.WSCatalogoServiceLocator;
import se.procedimientos.exceptions.GestorCatalogoException;
import util.CollectionUtils;

import common.exceptions.SistemaExternoException;

/**
 * Conector con el Sistemas Gestor de Procedimientos
 */
public class GestorCatalogoArchigest implements GestorCatalogo {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(GestorCatalogoArchigest.class);

	/** Stub del servicio web de procedimientos. */
	protected WSCatalogo wsCatalogo = null;

	/** Constante para la localización del WSDL del Servicio Web. */
	private static final String WSDL_LOCATION = "WSDL_LOCATION";

	/**
	 * Constructor.
	 */
	public GestorCatalogoArchigest() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 * @throws SistemaExternoException
	 *             si ocurre algún error.
	 */
	public void initialize(Properties params) throws SistemaExternoException {
		try {
			String wsdlLocation = params.getProperty(WSDL_LOCATION);
			wsCatalogo = WSCatalogoServiceLocator
					.getCatalogoService(wsdlLocation);
		} catch (Exception e) {
			logger.error(
					"Error en la creaci\u00F3n del proxy del Cat\u00E1logo", e);
			throw new GestorCatalogoException(e);
		}
	}

	/**
	 * Recupera la lista de procedimientos del tipo que se indica en tipoProc,
	 * con su información básica.
	 * 
	 * @param tipoProc
	 *            Tipo de procedimiento.
	 *            <p>
	 *            Valores posibles de tipoProc:
	 *            <li>1 - Todos</li>
	 *            <li>2 - Procedimientos automatizados</li>
	 *            <li>3 - Procedimientos no automatizados</li>
	 *            </p>
	 * @param nombre
	 *            Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 * @throws GestorCatalogoException
	 *             si ocurre algún error.
	 */
	public List recuperarInfoBProcedimientos(int tipoProc, String nombre)
			throws GestorCatalogoException {
		try {
			return CollectionUtils.createList(wsCatalogo
					.recuperarInfoBProcedimientos(tipoProc, nombre));
		} catch (Exception e) {
			logger.error(
					"Error en la llamada al servicio web de cat\u00E1logo", e);
			throw new GestorCatalogoException(e);
		}
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se incluyen en
	 * el parámetro idProcs.
	 * 
	 * @param idProcs
	 *            Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 * @throws GestorCatalogoException
	 *             si ocurre algún error.
	 */
	public List recuperarInfoBProcedimientos(String[] idProcs)
			throws GestorCatalogoException {
		try {
			return CollectionUtils.createList(wsCatalogo
					.recuperarInfoBProcedimientos(idProcs));
		} catch (Exception e) {
			logger.error(
					"Error en la llamada al servicio web de cat\u00E1logo", e);
			throw new GestorCatalogoException(e);
		}
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador único es
	 * idProc.
	 * 
	 * @param idProc
	 *            Identificador de procedimiento.
	 * @return Información del procedimiento.
	 * @throws GestorCatalogoException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public IProcedimiento recuperarProcedimiento(String idProc)
			throws GestorCatalogoException {
		ProcedimientoImpl procedimiento = null;

		try {
			ProcedimientoVO proc = wsCatalogo.recuperarProcedimiento(idProc);
			if (proc != null) {
				procedimiento = new ProcedimientoImpl();
				procedimiento.setInformacionBasica(proc.getInformacionBasica());
				procedimiento.setObjeto(proc.getObjeto());
				procedimiento.setTramites(proc.getTramites());
				procedimiento.setNormativa(proc.getNormativa());
				procedimiento.setDocumentosBasicos(proc.getDocumentosBasicos());
				procedimiento.setOrganosProductores(CollectionUtils
						.createList(proc.getOrganosProductores()));
			}
		} catch (Exception e) {
			logger.error(
					"Error en la llamada al servicio web de cat\u00E1logo", e);
			throw new GestorCatalogoException(e);
		}

		return procedimiento;
	}
}
