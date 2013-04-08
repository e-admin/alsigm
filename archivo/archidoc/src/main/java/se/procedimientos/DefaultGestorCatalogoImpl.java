package se.procedimientos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Node;

import se.instituciones.exceptions.GestorOrganismosException;
import xml.config.ConfiguracionArchivoManager;

import common.Constants;
import common.exceptions.SistemaExternoException;

public class DefaultGestorCatalogoImpl implements GestorCatalogo {

	/** Logger de la clase. */
	protected static final Logger logger = Logger
			.getLogger(DefaultGestorCatalogoImpl.class);

	private static final SimpleDateFormat FORMATO_FECHA = new SimpleDateFormat(
			"dd/MM/yyyy");

	private Map tiposProcedimiento = new HashMap();
	private Map infoProcedimientos = new HashMap();
	private Map procedimientos = new HashMap();

	/**
	 * Constructor.
	 */
	public DefaultGestorCatalogoImpl() {
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
			if (infoProcedimientos.isEmpty()) {
				tiposProcedimiento.put(
						String.valueOf(IProcedimiento.AUTOMATICO),
						new ArrayList());
				tiposProcedimiento.put(
						String.valueOf(IProcedimiento.NO_AUTOMATICO),
						new ArrayList());

				// SAXReader saxReader = new SAXReader();
				// Document procedimientosDOM =
				// saxReader.read(getClass().getClassLoader().getResource(
				// "test/sistema_gestor_procedimientos.xml"));

				Document procedimientosDOM = ConfiguracionArchivoManager
						.getInstance().getDefaultSistemaGestorCatalogo();

				if (procedimientosDOM != null) {
					List nodesProcedimiento = procedimientosDOM
							.selectNodes("/catalogo_procedimientos/procedimiento");

					Node nodo;
					InfoBProcedimientoImpl infoProcedimiento;

					for (Iterator j = nodesProcedimiento.iterator(); j
							.hasNext();) {
						nodo = (Node) j.next();
						infoProcedimiento = new InfoBProcedimientoImpl(
								nodo.valueOf("id"), nodo.valueOf("codigo"),
								nodo.valueOf("nombre"));

						infoProcedimiento.setSistemaProductor(
								nodo.valueOf("sistema_productor/id"),
								nodo.valueOf("sistema_productor/nombre"));

						infoProcedimientos.put(infoProcedimiento.getId(),
								infoProcedimiento);

						List productores = nodo
								.selectNodes("productores/productor");
						List productoresProcedimiento = new ArrayList();
						for (Iterator k = productores.iterator(); k.hasNext();) {
							Node unProductorDOM = (Node) k.next();
							productoresProcedimiento
									.add(new OrganoProductorImpl(
											unProductorDOM
													.valueOf(Constants.ID),
											StringUtils.isNotEmpty(unProductorDOM
													.valueOf("fecha_inicio")) ? FORMATO_FECHA.parse(unProductorDOM
													.valueOf("fecha_inicio"))
													: null));
						}
						ProcedimientoImpl unProcedimiento = new ProcedimientoImpl(
								infoProcedimiento, nodo.valueOf("objeto"),
								nodo.valueOf("tramites"),
								nodo.valueOf("normativa"),
								nodo.valueOf("documentos"));
						unProcedimiento
								.setOrganosProductores(productoresProcedimiento);
						procedimientos.put(infoProcedimiento, unProcedimiento);

						String tipoProcedimiento = null;
						if (!StringUtils.isBlank(infoProcedimiento
								.getCodSistProductor()))
							tipoProcedimiento = String
									.valueOf(IProcedimiento.AUTOMATICO);
						else
							tipoProcedimiento = String
									.valueOf(IProcedimiento.NO_AUTOMATICO);
						List listaProcedimientos = (List) tiposProcedimiento
								.get(tipoProcedimiento);
						listaProcedimientos.add(infoProcedimiento);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error al leer el fichero de datos de procedimientos",
					e);
			throw new GestorOrganismosException(e);
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
	 */
	public List recuperarInfoBProcedimientos(int tipoProc, String nombre) {
		List listaProc = new ArrayList();
		Collection col = null;

		switch (tipoProc) {
		case 1:
			col = infoProcedimientos.values();
			break;
		case 2:
			col = (List) tiposProcedimiento.get(String
					.valueOf(IProcedimiento.AUTOMATICO));
			break;
		case 3:
			col = (List) tiposProcedimiento.get(String
					.valueOf(IProcedimiento.NO_AUTOMATICO));
			break;
		}

		InfoBProcedimiento proc;
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				proc = (InfoBProcedimiento) it.next();

				if (StringUtils.isNotBlank(nombre)) {
					if (proc.getNombre().toUpperCase()
							.indexOf(nombre.toUpperCase()) >= 0)
						listaProc.add(proc);
				} else
					listaProc.add(proc);
			}
		}

		return listaProc;
	}

	public List recuperarInfoBProcedimientos(String[] idProcs) {
		List result = new ArrayList();
		InfoBProcedimiento proc;
		for (int i = 0; i < idProcs.length; i++) {
			proc = (InfoBProcedimiento) infoProcedimientos.get(idProcs[i]);
			if (proc != null)
				result.add(proc);
		}
		return result;
	}

	public IProcedimiento recuperarProcedimiento(String idProc) {
		Object infoProcedimiento = infoProcedimientos.get(idProc);
		if (infoProcedimiento == null)
			return null;
		return (IProcedimiento) procedimientos.get(infoProcedimiento);
	}
}