package deposito.actions.navegador;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import transferencias.TransferenciasConstants;
import transferencias.actions.ComposicionEmplazamientoAction;

import common.ConfigConstants;
import common.Constants;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.util.ArrayUtils;
import common.util.ListUtils;

import deposito.DepositoConstants;
import deposito.forms.NavegadorReservaDepositoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.model.NoAvailableSpaceException;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;

public class NavegadorReservaDepositoAction extends BaseAction {

	static Logger logger = Logger
			.getLogger(ComposicionEmplazamientoAction.class);

	public final static String ACTION_KEY = "action";

	public final static String MAP_N_HUECOS_LIBRES_KEY = "MAP_N_HUECOS_LIBRES_KEY";

	public final static String GET_PARENT_SIBLINGS = "getparentsiblings";

	public final static String GET_CHILDS = "getchilds";

	public final static String PATH_KEY = "PATH_KEY";

	public final static String PARENT_NAME = "PARENT_NAME";

	public final static String NUM_HUECOS_LIBRES = "NUM_HUECOS_LIBRES";

	public final static String LISTA_KEY = DepositoConstants.LISTA_DESCENDIENTES_KEY;

	public final static String NUM_HUECOS_LIBRES_DESDE_POSICION = "NUM_HUECOS_LIBRES_DESDE_POSICION";

	public final static String GET_NUM_HUECOS_DISPONIBLES = "getNumHuecosLibresDesdePosicion";

	public final static String ERROR_HUECOS_INSUFICIENTES = "ERROR_HUECOS_INSUFICIENTES";

	/**
	 * Metodo a llamar al iniciar el navegador. Recibe por request el
	 * idseleccionado y el tipo
	 */
	protected void initialExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NavegadorReservaDepositoForm frm = (NavegadorReservaDepositoForm) form;

		removeSession(request);

		ServiceRepository services = getServiceRepository(request);

		// devolver hermanos del elemento q nos llega como seleccionado
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		String nombrePadre = null;
		String pathPadre = null;
		if (!StringUtils.isBlank(frm.getIdseleccionadoinicial())
				|| !StringUtils.isBlank(frm.getIdtiposeleccionadoinicial())) {

			String idElementoSeleccionado = frm.getIdseleccionadoinicial();
			String idTipoElementoSeleccionado = null;

			// Obtener el tipo del Elemento que se ha seleccionado para realizar
			// la reserva.
			ElementoVO elementoVO = serviceDeposito
					.getElementoNoAsignable(idElementoSeleccionado);

			if (elementoVO == null) {
				elementoVO = serviceDeposito
						.getElementoAsignable(idElementoSeleccionado);
			}

			if (elementoVO != null) {
				idTipoElementoSeleccionado = elementoVO.getIdTipoElemento();
			}

			String idElemento = idElementoSeleccionado;
			String idTipoElemento = idTipoElementoSeleccionado;

			ElementoVO padreVO = serviceDeposito.getElementoPadre(idElemento,
					idTipoElemento);

			if (padreVO != null) {
				frm.setIdpadre(padreVO.getId());
				frm.setIdtipopadre(padreVO.getIdTipoElemento());
			}

			frm.setSeleccionadoinicial(getCodigoElemento(
					idElementoSeleccionado, idTipoElementoSeleccionado));
			frm.setSeleccionado(frm.getSeleccionadoinicial());

			int nHuecosLibres = getNumHuecosDesdePosicion(serviceDeposito,
					idElementoSeleccionado, idTipoElementoSeleccionado,
					frm.getNumHuecosNecesarios(), frm.getFilterByIdformato(),
					frm.getRecorrerDepositos());

			frm.setNumHuecosLibres(nHuecosLibres);

			/*
			 * // establecer datos del padre boolean isUbicacion = (padreVo ==
			 * null); if (!isUbicacion) { frm.setIdpadre(padreVo.getId());
			 * frm.setIdtipopadre(padreVo.getIdTipoElemento());
			 * 
			 * //Es un Depósito
			 * if(DepositoVO.ID_TIPO_ELEMENTO_UBICACION.equals(padreVo
			 * .getIdTipoElemento())){
			 * frm.setHideHuecosLibres(Boolean.toString(false));
			 * frm.setDepositoseleccionado(null); } else{
			 * frm.setHideHuecosLibres(Boolean.toString(true));
			 * frm.setDepositoseleccionado(frm.getIdseleccionadoinicial() +
			 * Constants.DELIMITER_IDS + frm.getIdseleccionadoinicial()); } }
			 * else { frm.setIdpadre(null); frm.setIdtipopadre(null);
			 * frm.setDepositoseleccionado(null);
			 * 
			 * }
			 */

			removeInTemporalSession(request,
					DepositoConstants.LISTA_DESCENDIENTES_KEY);

			ElementoVO padreVo = serviceDeposito.getElementoPadre(
					idElementoSeleccionado, idTipoElementoSeleccionado);

			Collection hermanos = getparentsiblings(padreVo,
					frm.getFilterByIdformato(), serviceDeposito);

			CollectionUtils
					.transform(hermanos, ElementoToEspacioEnElemento
							.getInstance(services, frm.getFilterByIdformato(),
									frm.isCheckHasHuecos()));

			Iterator it = util.CollectionUtils.getIterator(hermanos);

			if (it.hasNext()) {
				ElementoVO elementoHijo = (ElementoVO) it.next();

				if (isLastLevel(elementoHijo.getIdTipoElemento())) {
					setInTemporalSession(request,
							TransferenciasConstants.IS_LAST_LEVEL,
							String.valueOf(true));
				} else {
					setInTemporalSession(request,
							TransferenciasConstants.IS_LAST_LEVEL,
							String.valueOf(false));
				}
			} else {
				setInTemporalSession(request,
						TransferenciasConstants.IS_LAST_LEVEL,
						String.valueOf(false));
			}

			pathPadre = padreVo.getPathName();
			nombrePadre = padreVo.getItemName();

			if (nombrePadre.equals(pathPadre)) {
				nombrePadre = null;
			}

			setInTemporalSession(request, PATH_KEY, pathPadre);
			setInTemporalSession(request, PARENT_NAME, nombrePadre);
			setInTemporalSession(request,
					DepositoConstants.LISTA_DESCENDIENTES_KEY, hermanos);
			setInTemporalSession(request, NUM_HUECOS_LIBRES,
					String.valueOf(frm.getNumHuecosLibres()));
		} else {
			String idSeleccionado = frm.getIdroot();
			String idTipoSeleccionado = frm.getIdtiporoot();

			ElementoVO elementoSeleccionadoVO = serviceDeposito
					.getInfoElemento(idSeleccionado, idTipoSeleccionado);

			String pathKey = null;
			String parentName = null;

			if (elementoSeleccionadoVO != null) {
				if (frm.getIdroot().equals(elementoSeleccionadoVO.getId())) {
					pathKey = elementoSeleccionadoVO.getPathName();
				} else {
					parentName = elementoSeleccionadoVO.getNombre();
					pathKey = elementoSeleccionadoVO.getPathName();
				}
			}

			removeInTemporalSession(request,
					DepositoConstants.LISTA_DESCENDIENTES_KEY);
			Collection hijos = serviceDeposito.getHijosElemento(idSeleccionado,
					idTipoSeleccionado);
			CollectionUtils
					.transform(hijos, ElementoToEspacioEnElemento.getInstance(
							services, frm.getFilterByIdformato(),
							frm.isCheckHasHuecos()));

			Iterator it = util.CollectionUtils.getIterator(hijos);

			if (it.hasNext()) {
				ElementoVO elementoHijo = (ElementoVO) it.next();

				if (isLastLevel(elementoHijo.getIdTipoElemento())) {
					setInTemporalSession(request,
							TransferenciasConstants.IS_LAST_LEVEL,
							String.valueOf(true));
				} else {
					setInTemporalSession(request,
							TransferenciasConstants.IS_LAST_LEVEL,
							String.valueOf(false));
				}
			} else {
				setInTemporalSession(request,
						TransferenciasConstants.IS_LAST_LEVEL,
						String.valueOf(false));
			}

			setInTemporalSession(request, PATH_KEY, pathKey);
			setInTemporalSession(request, PARENT_NAME, parentName);
			setInTemporalSession(request,
					DepositoConstants.LISTA_DESCENDIENTES_KEY, hijos);
			setInTemporalSession(request, NUM_HUECOS_LIBRES,
					String.valueOf(frm.getNumHuecosLibres()));
		}

	}

	protected void getchildsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NavegadorReservaDepositoForm frm = (NavegadorReservaDepositoForm) form;

		ServiceRepository services = getServiceRepository(request);
		// colocar en session lista de hijos(es necesario q cada hijo indique si
		// tiene mas hijos)
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		// comprobar q el idseleccionado no sea un asignable, ya q no tienen
		// hijos a mostrar
		removeInTemporalSession(request,
				DepositoConstants.LISTA_DESCENDIENTES_KEY);

		Collection hijos;
		boolean isAsignable = serviceDeposito.getInfoElemento(
				frm.getIdseleccionado(), frm.getIdtiposeleccionado())
				.isAsignable();
		if (!isAsignable) {
			frm.setIdpadre(frm.getIdseleccionado());
			frm.setIdtipopadre(frm.getIdtiposeleccionado());

			hijos = serviceDeposito.getHijosElemento(frm.getIdseleccionado(),
					frm.getIdtiposeleccionado());

		} else {
			// reobtener de nuevo los hermanos porque estan en request=>podrian
			// ponerse en session y no realizar de
			// nuevo la consulta al servicio
			hijos = serviceDeposito.getHijosElemento(frm.getIdpadre(),
					frm.getIdtipopadre());
		}

		String idFormato = frm.getFilterByIdformato();
		if (idFormato != null)
			removeElementosNoFormato(idFormato, hijos);

		ElementoVO seleccionadoVO = serviceDeposito.getInfoElemento(
				frm.getIdpadre(), frm.getIdtipopadre());
		String pathPadre = null;
		String nombrePadre = null;
		if (seleccionadoVO != null) {
			pathPadre = seleccionadoVO.getPathName();
			nombrePadre = seleccionadoVO.getItemName();
		}

		Iterator it = util.CollectionUtils.getIterator(hijos);

		ElementoVO elemento = (ElementoVO) it.next();

		if (isLastLevel(elemento.getIdTipoElemento())) {
			setInTemporalSession(request,
					TransferenciasConstants.IS_LAST_LEVEL, String.valueOf(true));
		}
		// Solo se pone el enlace si no es una ubicación.

		setInTemporalSession(request, PATH_KEY, pathPadre);

		setInTemporalSession(request, PARENT_NAME, nombrePadre);

		// List hijosEntities2 = generateListEntities(hijos, frm, false,
		// serviceDeposito);
		CollectionUtils.transform(
				hijos,
				ElementoToEspacioEnElemento.getInstance(services,
						frm.getFilterByIdformato(), frm.isCheckHasHuecos()));

		// setInTemporalSession(request,LISTA_KEY, hijosEntities2);
		setInTemporalSession(request,
				DepositoConstants.LISTA_DESCENDIENTES_KEY, hijos);
		setInTemporalSession(request, NUM_HUECOS_LIBRES,
				String.valueOf(frm.getNumHuecosLibres()));

		frm.setSeleccionado(null);
		frm.setSeleccionadoinicial(null);
	}

	private boolean isLastLevel(String level) {
		ConfigConstants cfgConstants = ConfigConstants.getInstance();
		String lastLevels = cfgConstants.getLastLevelsUbicacionReserva();
		String separadorValores = cfgConstants.getMultivalueSeparator();
		if (!StringUtils.isBlank(lastLevels) && !StringUtils.isBlank(level)) {
			String[] levels = lastLevels.split(separadorValores);
			if (!ArrayUtils.isEmpty(levels)) {
				for (int i = 0; i < levels.length; i++) {
					if (level.equals(levels[i])) {
						return true;
					}
				}
			}

		}

		return false;
	}

	/**
	 * 
	 * @param argIdPadre
	 * @param argIdTipoPadre
	 * @return Hermanos de un nodo identificado por los parametros que recibe el
	 *         metodo
	 */
	// private Collection getparentsiblings(ElementoVO abueloVo,String
	// argIdPadre,String argIdTipoPadre){
	private Collection getparentsiblings(ElementoVO abueloVo, String idformato,
			GestorEstructuraDepositoBI serviceDeposito) {
		// Collection ret;

		// tratamiento especial en caso de q sea el padre de un deposito(null ya
		// q deposito no tien padre)
		boolean isUbicacion = (abueloVo == null);
		Collection hermanos;
		if (!isUbicacion) {
			hermanos = serviceDeposito.getHijosElemento(abueloVo.getId(),
					abueloVo.getIdTipoElemento());
		} else {
			hermanos = serviceDeposito.getEdificios();
		}

		// no mostrar los asignables q no tengan el formato especificado
		if (idformato != null)
			removeElementosNoFormato(idformato, hermanos);

		return hermanos;
	}

	/**
	 * @param idformato
	 * @param elementos
	 */
	private void removeElementosNoFormato(final String idformato,
			Collection elementos) {
		CollectionUtils.filter(elementos, new Predicate() {
			public boolean evaluate(Object arg0) {
				ElementoVO hermano = (ElementoVO) arg0;
				if (hermano.isAsignable()) {
					ElementoAsignableVO elementoAsignable = (ElementoAsignableVO) hermano;
					return elementoAsignable.getIdFormato().equalsIgnoreCase(
							idformato);

				}
				return true;
			}
		});

	}

	public void getparentsiblingsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		NavegadorReservaDepositoForm frm = (NavegadorReservaDepositoForm) form;

		removeInTemporalSession(request,
				DepositoConstants.LISTA_DESCENDIENTES_KEY);

		// devolver hermanos del elemento q nos llega como seleccionado
		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI service = services
				.lookupGestorEstructuraDepositoBI();

		ElementoVO abueloVo = service.getElementoPadre(frm.getIdpadre(),
				frm.getIdtipopadre());

		Collection hermanosDePadre = getparentsiblings(abueloVo,
				frm.getFilterByIdformato(), service);

		Iterator it = util.CollectionUtils.getIterator(hermanosDePadre);

		ElementoVO elemento = (ElementoVO) it.next();

		boolean isLastLevel = isLastLevel(elemento.getIdTipoElemento());
		setInTemporalSession(request, TransferenciasConstants.IS_LAST_LEVEL,
				String.valueOf(isLastLevel));

		String parentName = null;
		String pathKey = null;

		// establecer datos del padre(si es deposito=> el idpadre
		boolean isUbicacion = (abueloVo == null);
		if (!isUbicacion) {
			frm.setIdpadre(abueloVo.getId());
			frm.setIdtipopadre(abueloVo.getIdTipoElemento());

			pathKey = abueloVo.getPathName();
			parentName = abueloVo.getNombre();

			if (pathKey != null && pathKey.equals(parentName)) {
				parentName = null;
			}

			if (abueloVo.getIdElemento().equals(frm.getIdroot())) {
				parentName = null;
			}

		} else { // Es ubicacion
			frm.setIdpadre(null);
			frm.setIdtipopadre(null);
		}

		CollectionUtils.transform(
				hermanosDePadre,
				ElementoToEspacioEnElemento.getInstance(services,
						frm.getFilterByIdformato(), frm.isCheckHasHuecos()));

		setInTemporalSession(request, PATH_KEY, pathKey);
		setInTemporalSession(request, PARENT_NAME, parentName);

		setInTemporalSession(request,
				DepositoConstants.LISTA_DESCENDIENTES_KEY, hermanosDePadre);

		frm.setSeleccionado(null);
		frm.setSeleccionadoinicial(null);

		setInTemporalSession(request, NUM_HUECOS_LIBRES,
				String.valueOf(frm.getNumHuecosLibres()));

	}

	/**
	 * Obtiene el número de Huecos Libres a partir del elemento Seleccionado
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getNumHuecosLibresDesdePosicionExecuteLogic(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		NavegadorReservaDepositoForm frm = (NavegadorReservaDepositoForm) form;

		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		String idElementoSeleccionado = frm.getIdseleccionado();
		String idTipoElementoSeleccionado = frm.getIdtiposeleccionado();

		int nHuecosLibres = getNumHuecosDesdePosicion(serviceDeposito,
				idElementoSeleccionado, idTipoElementoSeleccionado,
				frm.getNumHuecosNecesarios(), frm.getFilterByIdformato(),
				frm.getRecorrerDepositos());

		frm.setNumHuecosLibres(nHuecosLibres);

	}

	private int getNumHuecosDesdePosicion(
			GestorEstructuraDepositoBI serviceDeposito,
			String idElementoSeleccionado, String idTipoElementoSeleccionado,
			int nHuecos, String idFormato, String recorrerDepositos) {
		int retorno = 0;
		try {
			boolean recorrerTodos = false;

			if (Constants.TRUE_STRING.equals(recorrerDepositos)) {
				recorrerTodos = true;
			}

			// Se ha seleccionado un depósito
			List listaHuecos = serviceDeposito.searchNHuecosLibres(
					idElementoSeleccionado, idTipoElementoSeleccionado,
					nHuecos, null, null, idFormato, recorrerTodos);

			if (!ListUtils.isEmpty(listaHuecos)) {
				return listaHuecos.size();
			}
		} catch (NoAvailableSpaceException e) {
			return 0;
		}

		return retorno;
	}

	/**
	 * Elimina los Valores de las Variables almacenadas en Sesión.
	 * 
	 * @param request
	 *            Request de la petición.
	 */
	private void removeSession(HttpServletRequest request) {
		removeInTemporalSession(request,
				DepositoConstants.LISTA_DESCENDIENTES_KEY);
		removeInTemporalSession(request, TransferenciasConstants.IS_LAST_LEVEL);
		removeInTemporalSession(request, NUM_HUECOS_LIBRES_DESDE_POSICION);
		removeInTemporalSession(request, PATH_KEY);
		removeInTemporalSession(request, PARENT_NAME);
		removeInTemporalSession(request, NUM_HUECOS_LIBRES);

	}

	/**
	 * Retorna el valor de los codigo concatenado con el tipo
	 * 
	 * @param idElemento
	 * @param idTipoElemento
	 * @return
	 */
	private String getCodigoElemento(String idElemento, String idTipoElemento) {
		return idElemento + Constants.DELIMITER_IDS + idTipoElemento;
	}
}
