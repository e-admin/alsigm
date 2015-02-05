package deposito.actions.navegador;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import transferencias.actions.ComposicionEmplazamientoAction;

import common.Constants;
import common.actions.BaseAction;
import common.bi.ServiceRepository;
import common.util.ListUtils;
import common.util.StringUtils;

import deposito.DepositoConstants;
import deposito.forms.NavegadorDepositoForm;
import deposito.model.GestorEstructuraDepositoBI;
import deposito.model.NoAvailableSpaceException;
import deposito.vos.DepositoVO;
import deposito.vos.ElementoAsignableVO;
import deposito.vos.ElementoVO;

public class NavegadorDepositoAction extends BaseAction {

	static Logger logger = Logger
			.getLogger(ComposicionEmplazamientoAction.class);

	public final static String ACTION_KEY = "action";
	public final static String MAP_N_HUECOS_LIBRES_KEY = "MAP_N_HUECOS_LIBRES_KEY";
	public final static String GET_PARENT_SIBLINGS = "getparentsiblings";
	public final static String GET_CHILDS = "getchilds";
	public final static String REFRESCAR_FORMATOS = "refrescarFormatos";
	public final static String GET_NUM_HUECOS_DISPONIBLES = "getNumHuecosLibresDesdePosicion";
	public final static String GO_TOP = "goTop";

	public final static String LISTA_KEY = DepositoConstants.LISTA_DESCENDIENTES_KEY;

	/**
	 * Metodo a llamar al iniciar el navegador. Recibe por request el
	 * idseleccionado y el tipo
	 */
	protected void initialExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		removeSession(request);

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;

		// Si existe en sesión el valor de formato inicial, lo establecemos como
		// formato
		String formatoInicial = (String) getFromTemporalSession(request,
				DepositoConstants.ID_FORMATO_INICIAL);
		if (StringUtils.isNotEmpty(formatoInicial)) {
			frm.setFilterByIdformato(formatoInicial);
		}

		ServiceRepository services = getServiceRepository(request);
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		// Leer el elemento y meterlo en sesión
		ElementoVO elemento = serviceDeposito.getInfoElemento(
				frm.getIdseleccionadoinicial(),
				frm.getIdtiposeleccionadoinicial());

		/*
		 * if(elemento.isAsignable()){
		 * frm.setAsignableSeleccionado(elemento.getId() +
		 * Constants.DELIMITER_IDS + elemento.getIdTipoElemento()); }
		 */

		setInTemporalSession(request, DepositoConstants.ELEMENTO_DEPOSITO_KEY,
				elemento);

		cargarDatosNavegador(frm, request, null);
	}

	protected void getchildsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;

		ServiceRepository services = getServiceRepository(request);

		// Leer el elemento y meterlo en sesión
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();
		ElementoVO elemento = serviceDeposito.getInfoElemento(
				frm.getIdseleccionado(), frm.getIdtiposeleccionado());
		setInTemporalSession(request, DepositoConstants.ELEMENTO_DEPOSITO_KEY,
				elemento);

		cargarDatosNavegador(frm, request, GET_CHILDS);
	}

	/**
	 * Obtiene los Hijos de Un Elemento
	 * 
	 * @param elemento
	 * @param serviceDeposito
	 * @return Collection d
	 */
	private Collection getChilds(ElementoVO elemento,
			GestorEstructuraDepositoBI serviceDeposito) {
		Collection hijos = null;

		if (elemento != null) {
			hijos = serviceDeposito.getHijosElemento(elemento.getId(),
					elemento.getIdTipoElemento());
		}
		return hijos;
	}

	protected String getTipoFromLista(HttpServletRequest request,
			String idToSearch) {
		// consulto la lista que habia en la vista para saber el tipo del
		// elemento seleccionado
		Collection lista = (Collection) request.getSession().getAttribute(
				DepositoConstants.LISTA_DESCENDIENTES_KEY);
		Iterator it = lista.iterator();
		ElementoVO vo;
		while (it.hasNext()) {
			vo = (ElementoVO) it.next();
			if (vo.getId().equalsIgnoreCase(idToSearch))
				return vo.getIdTipoElemento();
		}
		return null;
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
	/*
	 * private Collection getparentsiblings(ElementoVO abueloVo, String
	 * idformato, GestorEstructuraDepositoBI serviceDeposito){ //tratamiento
	 * especial en caso de q sea el padre de un deposito(null ya q deposito no
	 * tien padre) boolean isDeposito=(abueloVo==null); Collection hermanos; if
	 * (!isDeposito){
	 * hermanos=serviceDeposito.getHijosElemento(abueloVo.getId(),
	 * abueloVo.getIdTipoElemento()); }else{
	 * hermanos=serviceDeposito.getEdificios(); }
	 * 
	 * //no mostrar los asignables q no tengan el formato especificado if
	 * (idformato!=null) removeElementosNoFormato(idformato, hermanos);
	 * 
	 * 
	 * return hermanos; }
	 */
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

	public void goTopExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;
		cargarDatosNavegador(frm, request, GET_PARENT_SIBLINGS, true);
	}

	public void getparentsiblingsExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;
		cargarDatosNavegador(frm, request, GET_PARENT_SIBLINGS);
	}

	private void removeSession(HttpServletRequest request) {
		request.getSession().removeAttribute(
				DepositoConstants.LISTA_DESCENDIENTES_KEY);
		request.getSession().removeAttribute(DepositoConstants.SHOW_PARENT);
	}

	public void refrescarFormatosExecuteLogic(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;

		String formatoInicial = (String) getFromTemporalSession(request,
				DepositoConstants.ID_FORMATO_INICIAL);
		if (StringUtils.isNotEmpty(formatoInicial)) {
			frm.setFilterByIdformato(formatoInicial);
		} else {
			formatoInicial = frm.getFilterByIdformato();
			if (StringUtils.isNotEmpty(formatoInicial))
				setInTemporalSession(request,
						DepositoConstants.ID_FORMATO_INICIAL, formatoInicial);
		}

		cargarDatosNavegador(frm, request, REFRESCAR_FORMATOS);
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
		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;

		ServiceRepository services = getServiceRepository(request);

		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		String idElementoSeleccionado = frm.getIdAsignableSeleccionado();
		String idTipoElementoSeleccionado = frm
				.getIdTipoAsignableSeleccionado();

		int nHuecosLibres = getNumHuecosDesdePosicion(serviceDeposito,
				idElementoSeleccionado, idTipoElementoSeleccionado,
				frm.getNumHuecosNecesarios(), frm.getFilterByIdformato(), null);

		if (nHuecosLibres == 0) {
			request.setAttribute(
					DepositoConstants.ID_ELEMENTO_SIN_HUECOS_DISPONIBLES,
					idElementoSeleccionado);
		}

		frm.setNumHuecosLibres(nHuecosLibres);
		cargarDatosNavegador(form, request, GET_NUM_HUECOS_DISPONIBLES);
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

	private void cargarDatosNavegador(ActionForm form,
			HttpServletRequest request, String method) {
		cargarDatosNavegador(form, request, method, false);
	}

	private void cargarDatosNavegador(ActionForm form,
			HttpServletRequest request, String method, boolean verRoot) {

		removeInTemporalSession(request, DepositoConstants.ALLOW_ALL_FORMATS);

		NavegadorDepositoForm frm = (NavegadorDepositoForm) form;

		ElementoVO elementoSeleccionado = (ElementoVO) getFromTemporalSession(
				request, DepositoConstants.ELEMENTO_DEPOSITO_KEY);

		ServiceRepository services = getServiceRepository(request);
		// Obtener el Elemento Seleccionado
		GestorEstructuraDepositoBI serviceDeposito = services
				.lookupGestorEstructuraDepositoBI();

		String idFormato = frm.getFilterByIdformato();

		if (frm.isAllowAllFormats() && frm.isTodosFormatos()) {
			idFormato = null;
		}

		String pathPadre = null;
		Boolean showParent = Boolean.FALSE;
		Collection hijos = null;

		if (verRoot
				|| (GET_PARENT_SIBLINGS.equals(method) && DepositoConstants.ID_TIPO_ELEMENTO_UBICACION
						.equals(elementoSeleccionado.getIdTipoElemento()))) {
			frm.setIdpadre(null);
			frm.setIdtipopadre(DepositoConstants.ID_TIPO_ELEMENTO_ROOT_UBICACION);

			String idArchivo = frm.getIdArchivo();

			if (StringUtils.isEmpty(idArchivo)) {
				DepositoVO edificio = serviceDeposito
						.getUbicacion(elementoSeleccionado.getIddeposito());
				idArchivo = edificio.getIdArchivo();
				frm.setIdArchivo(idArchivo);
			}

			hijos = serviceDeposito
					.getUbicacionesXIdsArchivo(new String[] { idArchivo });
			pathPadre = Constants.SEPARADOR_ANTECESORES_ORGANO;
		} else {
			if (method == null || GET_PARENT_SIBLINGS.equals(method)) {
				elementoSeleccionado = serviceDeposito.getElementoPadre(
						elementoSeleccionado.getId(),
						elementoSeleccionado.getIdTipoElemento());
				// Eliminar elemento seleccionado

			}

			setInTemporalSession(request,
					DepositoConstants.ELEMENTO_DEPOSITO_KEY,
					elementoSeleccionado);

			frm.setIdpadre(elementoSeleccionado.getId());
			frm.setIdtipopadre(elementoSeleccionado.getIdTipoElemento());
			pathPadre = elementoSeleccionado.getPathName();
			showParent = Boolean.TRUE;
			// Obtener los Hijos de Elemento
			hijos = getChilds(elementoSeleccionado, serviceDeposito);

			if (StringUtils.isNotEmpty(idFormato)) {
				removeElementosNoFormato(idFormato, hijos);
			}

		}

		// Comprobar si hay nivel tope superior
		if (StringUtils.isNotEmpty(frm.getIdtiporoot())) {
			if (elementoSeleccionado.getIdTipoElemento().equals(
					frm.getIdtiporoot())) {
				showParent = Boolean.FALSE;
			}
		}

		if (elementoSeleccionado != null) {
			frm.setSeleccionado(elementoSeleccionado.getId()
					+ Constants.DELIMITER_IDS
					+ elementoSeleccionado.getIdTipoElemento());
		}

		CollectionUtils.transform(hijos, ElementoToEspacioEnElemento
				.getInstance(services, idFormato, frm.isCheckHasHuecos()));
		request.setAttribute(DepositoConstants.LISTA_DESCENDIENTES_KEY, hijos);

		boolean asignables = serviceDeposito
				.hasChildsAsignables(elementoSeleccionado.getIdTipoElemento());

		if (asignables && frm.isAllowAllFormats()) {
			setInTemporalSession(request, DepositoConstants.ALLOW_ALL_FORMATS,
					Boolean.TRUE);
		}

		setInTemporalSession(request, DepositoConstants.PATH_KEY, pathPadre);
		setInTemporalSession(request, DepositoConstants.SHOW_PARENT, showParent);

		frm.setSeleccionado(null);
	}

	/*
	 * private String getPathElemento(String pathElemento, boolean mostrarRoot){
	 * if(StringUtils.isEmpty(pathElemento)){ return
	 * Constants.SEPARADOR_ANTECESORES_ORGANO; } return pathElemento; }
	 */
}
