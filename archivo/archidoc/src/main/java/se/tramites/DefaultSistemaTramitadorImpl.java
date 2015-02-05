package se.tramites;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import se.NotAvailableException;
import se.tramites.exceptions.SistemaTramitadorException;
import util.CollectionUtils;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.util.ArrayUtils;
import common.util.DateUtils;

/**
 * Implementación del interfaz para los Sistemas Tramitadores.
 */
public class DefaultSistemaTramitadorImpl implements SistemaTramitador {
	/** Logger de la clase. */
	private static Logger logger = Logger
			.getLogger(DefaultSistemaTramitadorImpl.class);

	static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(
			"dd/MM/yyyy");

	HashMap infoExpedientes = new HashMap();
	HashMap expedientes = new HashMap();
	HashMap expedientesXProcedimiento = new HashMap();

	/**
	 * Constructor.
	 */
	public DefaultSistemaTramitadorImpl() {
	}

	/**
	 * Inicializa con los parámetros de configuración.
	 * 
	 * @param params
	 *            Parámetros de configuración.
	 */
	public void initialize(Properties params) {
		try {
			if (infoExpedientes.isEmpty()) {

				Document expedientesDOM = ConfiguracionArchivoManager
						.getInstance().getDefaultSistemaGestorTramitador();

				if (expedientes != null) {

					List expedientes = expedientesDOM
							.selectNodes("/expedientes/expediente");
					for (Iterator i = expedientes.iterator(); i.hasNext();) {
						Node expedienteDOM = (Node) i.next();
						InfoBExpediente infoExpediente = new InfoBExpedienteImpl(
								expedienteDOM.valueOf("id"),
								expedienteDOM.valueOf("numeroExpediente"),
								expedienteDOM.valueOf("asunto"));
						String fechaInicio = expedienteDOM
								.valueOf("fechaInicio");
						String fechaFinalizacion = expedienteDOM
								.valueOf("fechaFinalizacion");
						ExpedienteImpl unExpediente = new ExpedienteImpl(
								infoExpediente, expedienteDOM.valueOf("asunto"));
						if (!StringUtils.isEmpty(fechaInicio))
							unExpediente.setFechaInicio(FORMATO_FECHA
									.parse(fechaInicio));
						if (!StringUtils.isEmpty(fechaFinalizacion))
							unExpediente.setFechaFin(FORMATO_FECHA
									.parse(fechaFinalizacion));
						unExpediente.setOrganoProductor(expedienteDOM
								.valueOf("organo_productor/id"), expedienteDOM
								.valueOf("organo_productor/nombre"));
						List documentosDOM = expedienteDOM
								.selectNodes("documentosFisicos/documento");
						for (Iterator j = documentosDOM.iterator(); j.hasNext();) {
							Node documentoDOM = (Node) j.next();
							unExpediente.addDocumentoFisico(
									documentoDOM.valueOf("nombre"),
									documentoDOM.valueOf("descripcion"));
						}

						List documentosElectronicosDOM = expedienteDOM
								.selectNodes("documentosElectronicos/documento");
						for (Iterator j = documentosElectronicosDOM.iterator(); j
								.hasNext();) {
							Node documentoDOM = (Node) j.next();

							DocElectronicoImpl docElectronico = new DocElectronicoImpl();

							String nombre = documentoDOM.valueOf("nombre");
							String descripcion = documentoDOM
									.valueOf("descripcion");
							String extension = documentoDOM
									.valueOf("extension");
							String localizador = documentoDOM
									.valueOf("localizador");
							String repositorio = documentoDOM
									.valueOf("repositorio");

							docElectronico.setAsunto(nombre);
							docElectronico.setTipoDocumento(descripcion);
							docElectronico.setExtension(extension);
							docElectronico.setLocalizador(localizador);
							docElectronico.setRepositorio(repositorio);

							unExpediente
									.addDocumentoElectronico(docElectronico);
						}

						List interesadosDOM = expedienteDOM
								.selectNodes("interesados/interesado");
						if (interesadosDOM.size() > 0) {
							List interesados = new ArrayList();
							for (Iterator j = interesadosDOM.iterator(); j
									.hasNext();) {
								Node interesadoDOM = (Node) j.next();
								InteresadoImpl interesado = new InteresadoImpl(
										interesadoDOM.valueOf("nombre"),
										interesadoDOM.valueOf("identificacion"),
										interesadoDOM.valueOf("rol"),
										interesadoDOM.valueOf("id_en_terceros"),
										Constants.TRUE_STRING
												.equalsIgnoreCase(interesadoDOM
														.valueOf("@principal")));
								interesados.add(interesado);
								unExpediente.setInteresados(interesados);
							}
						}
						List emplazamientosDOM = expedienteDOM
								.selectNodes("emplazamientos/emplazamiento");
						for (Iterator j = emplazamientosDOM.iterator(); j
								.hasNext();) {
							Node nodeEmplazamiento = (Node) j.next();
							unExpediente.addEmplazamiento(
									nodeEmplazamiento.valueOf("pais"),
									nodeEmplazamiento.valueOf("comunidad"),
									nodeEmplazamiento.valueOf("concejo"),
									nodeEmplazamiento.valueOf("poblacion"),
									nodeEmplazamiento.valueOf("localizacion"),
									nodeEmplazamiento.valueOf("validado"));
						}
						addExpediente(unExpediente,
								expedienteDOM.valueOf("procedimiento"));
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error al leer el fichero de datos geográficos", e);
		}
	}

	private void addExpediente(Expediente unExpediente, String procedimiento) {
		InfoBExpediente infoExpediente = unExpediente.getInformacionBasica();
		List expedientesProcedimiento = (List) expedientesXProcedimiento
				.get(procedimiento);
		if (expedientesProcedimiento == null)
			expedientesXProcedimiento.put(procedimiento,
					expedientesProcedimiento = new ArrayList());
		expedientesProcedimiento.add(infoExpediente.getId());
		infoExpedientes.put(infoExpediente.getId(), infoExpediente);
		expedientes.put(infoExpediente.getId(), unExpediente);
	}

	/**
	 * Recupera los identificadores de los expedientes, del procedimiento
	 * identificado por idProc, que hayan finalizado en el rango de fechas
	 * comprendido entre fechaIni y fechaFin ordenados por lo que indique el
	 * parámetro tipoOrd.
	 * 
	 * @param idProc
	 *            Identificador del procedimiento.
	 * @param fechaIni
	 *            Fecha de inicio.
	 * @param fechaFin
	 *            Fecha de fin.
	 * @param tipoOrd
	 *            Tipo de ordenación.
	 *            <p>
	 *            Valores posibles:
	 *            <li>1 - Número de expediente</li>
	 *            <li>2 - Fecha finalización</li>
	 *            </p>
	 * @return Lista de identificadores de expedientes.
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 */
	public List recuperarIdsExpedientes(String idProc, Date fechaIni,
			Date fechaFin, int tipoOrd) throws SistemaTramitadorException {
		List lista = (List) expedientesXProcedimiento.get(idProc);
		List listaNoValidos = new ArrayList();

		if (!CollectionUtils.isEmpty(lista)) {
			Iterator it = lista.iterator();
			while (it.hasNext()) {
				String id = (String) it.next();
				Expediente expediente = (Expediente) expedientes.get(id);

				// Comprobar las fechas
				if (DateUtils.isFechaMenor(expediente.getFechaFinalizacion(),
						fechaIni)
						|| DateUtils.isFechaMayor(
								expediente.getFechaFinalizacion(), fechaFin)) {
					listaNoValidos.add(id);
				}
			}
		}

		if (!CollectionUtils.isEmpty(listaNoValidos)) {
			lista.removeAll(listaNoValidos);
		}

		if (!CollectionUtils.isEmpty(lista))
			return lista;
		else
			return new ArrayList();
	}

	/**
	 * Recupera la lista de expedientes cuyos identificadores se incluyen en el
	 * parámetro idExps.
	 * 
	 * @param idExps
	 *            Identificadores de expedientes.
	 * @return Lista de expedientes.
	 *         <p>
	 *         Los objetos de la lista tienen que implementar el interface
	 *         {@link InfoBExpediente}.
	 *         </p>
	 */
	public List recuperarInfoBExpedientes(String[] idExps) {
		List result = new ArrayList();

		if (!ArrayUtils.isEmpty(idExps)) {
			InfoBExpediente expediente = null;
			for (int i = 0; i < idExps.length; i++) {
				expediente = (InfoBExpediente) infoExpedientes.get(idExps[i]);
				if (expediente != null)
					result.add(expediente);
			}
		}

		return result;
	}

	/**
	 * Recupera la información de un expediente cuyo identificador único es
	 * idExp.
	 * 
	 * @param idExp
	 *            Identificador del expediente.
	 * @return Información de un expediente.
	 */
	public Expediente recuperarExpediente(String idExp) {
		return (Expediente) expedientes.get(idExp);
	}

	/**
	 * Modifica el estado de los expedientes recibidos como parámetro a estado
	 * archivado
	 * 
	 * @param idExps
	 *            Array de String con los expedientes que se quieren pasar al
	 *            estado archivado
	 * @throws SistemaTramitadorException
	 *             si ocurre algún error.
	 * @throws NotAvailableException
	 *             si la funcionalidad no es aplicable.
	 */
	public void archivarExpedientes(String[] idExps)
			throws SistemaTramitadorException, NotAvailableException {

	}

}
