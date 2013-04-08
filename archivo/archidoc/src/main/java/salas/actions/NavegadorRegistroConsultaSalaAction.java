package salas.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import salas.SalasConsultaConstants;
import salas.form.NavegadorRegistroConsultaSalaForm;
import salas.model.GestionSalasConsultaBI;
import salas.vos.EdificioVO;
import salas.vos.MesaVO;
import salas.vos.SalaVO;
import salas.vos.SalasConsultaVOBase;

import common.Constants;

public class NavegadorRegistroConsultaSalaAction extends
		SalasConsultaBaseAction {

	/**
	 * Método para cargar el navegador incialmente.
	 */
	public void initialExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		NavegadorRegistroConsultaSalaForm navRegForm = (NavegadorRegistroConsultaSalaForm) form;

		removeSession(request);

		String idArchivo = navRegForm.getIdArchivo();
		String salasConEquipoInformatico = navRegForm.getEquipoInformatico();
		List listaEdificios = new ArrayList();
		if (StringUtils.isNotEmpty(idArchivo)) {
			listaEdificios = salasBI.getEdificiosNumHijosByArchivo(idArchivo,
					salasConEquipoInformatico);
			navRegForm
					.setTipoSeleccionado(SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO);
		}
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_DESCENDIENTES_KEY, listaEdificios);
	}

	/**
	 * Obtiene los hijos de un elemento
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getChildsExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		NavegadorRegistroConsultaSalaForm navRegForm = (NavegadorRegistroConsultaSalaForm) form;

		removeSession(request);

		String salasConEquipoInformatico = navRegForm.getEquipoInformatico();

		List hijos = new ArrayList();
		SalasConsultaVOBase elemento = null;
		String idSeleccionado = navRegForm.getIdSeleccionado();
		String tipoSelecccionado = navRegForm.getTipoSeleccionado();
		if (StringUtils.isNotEmpty(tipoSelecccionado)) {
			if (SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO
					.equals(tipoSelecccionado)) {
				hijos = salasBI.getSalasNumHijos(idSeleccionado,
						salasConEquipoInformatico);
				elemento = salasBI.getEdificioById(idSeleccionado);
				navRegForm
						.setTipoSeleccionado(SalasConsultaConstants.TIPO_ELEMENTO_SALA);
			} else if (SalasConsultaConstants.TIPO_ELEMENTO_SALA
					.equals(tipoSelecccionado)) {
				hijos = salasBI.getMesasNavegacionPorSala(idSeleccionado);
				elemento = salasBI.getSalaById(idSeleccionado);
				navRegForm
						.setTipoSeleccionado(SalasConsultaConstants.TIPO_ELEMENTO_MESA);
				setInTemporalSession(request,
						SalasConsultaConstants.IS_LAST_LEVEL_KEY, Boolean.TRUE);
			}
			establecerPaths(request, elemento, tipoSelecccionado, false);
		}
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_DESCENDIENTES_KEY, hijos);
	}

	/**
	 * Obtiene el elemento padre
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void getParentExecuteLogic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		NavegadorRegistroConsultaSalaForm navRegForm = (NavegadorRegistroConsultaSalaForm) form;

		removeSession(request);

		String idArchivo = navRegForm.getIdArchivo();
		String salasConEquipoInformatico = navRegForm.getEquipoInformatico();

		List hijosPadre = new ArrayList();
		SalasConsultaVOBase elemento = null;
		String idSeleccionado = navRegForm.getIdSeleccionado();
		String tipoSelecccionado = navRegForm.getTipoSeleccionado();
		if (SalasConsultaConstants.TIPO_ELEMENTO_MESA.equals(tipoSelecccionado)) {
			elemento = salasBI.getSalaById(idSeleccionado);
			if (elemento == null) {
				MesaVO mesaVO = salasBI.getMesaById(idSeleccionado);
				elemento = salasBI.getSalaById(mesaVO.getIdSala());
				hijosPadre = salasBI.getSalasNumHijos(
						((SalaVO) elemento).getIdEdificio(),
						salasConEquipoInformatico);
			} else {
				hijosPadre = salasBI.getSalasNumHijos(
						((SalaVO) elemento).getIdEdificio(),
						salasConEquipoInformatico);
			}
			navRegForm
					.setTipoSeleccionado(SalasConsultaConstants.TIPO_ELEMENTO_SALA);
		} else if (SalasConsultaConstants.TIPO_ELEMENTO_SALA
				.equals(tipoSelecccionado)) {
			elemento = salasBI.getEdificioById(idSeleccionado);
			hijosPadre = salasBI.getEdificiosNumHijosByArchivo(idArchivo,
					salasConEquipoInformatico);
			navRegForm
					.setTipoSeleccionado(SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO);
		}

		establecerPaths(request, elemento, tipoSelecccionado, true);

		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_DESCENDIENTES_KEY, hijosPadre);
	}

	private void establecerPaths(HttpServletRequest request,
			SalasConsultaVOBase elemento, String tipoSelecccionado, boolean isUp) {

		GestionSalasConsultaBI salasBI = getGestionSalasBI(request);
		String pathPadre = null;
		EdificioVO edificioVO = null;
		if (elemento != null) {
			if (isUp) {
				if (SalasConsultaConstants.TIPO_ELEMENTO_MESA
						.equals(tipoSelecccionado)) {
					edificioVO = salasBI.getEdificioById(((SalaVO) elemento)
							.getIdEdificio());
					pathPadre = Constants.SLASH + edificioVO.getNombre();
				}
			} else {
				if (SalasConsultaConstants.TIPO_ELEMENTO_SALA
						.equals(tipoSelecccionado)) {
					edificioVO = salasBI.getEdificioById(((SalaVO) elemento)
							.getIdEdificio());
					pathPadre = Constants.SLASH + edificioVO.getNombre()
							+ Constants.SLASH + ((SalaVO) elemento).getNombre();
				} else if (SalasConsultaConstants.TIPO_ELEMENTO_EDIFICIO
						.equals(tipoSelecccionado)) {
					edificioVO = ((EdificioVO) elemento);
					pathPadre = Constants.SLASH + edificioVO.getNombre();

				}
			}
			setInTemporalSession(request,
					SalasConsultaConstants.PATH_PADRE_KEY, pathPadre);
			if (edificioVO != null) {
				setInTemporalSession(request,
						SalasConsultaConstants.NOMBRE_PADRE_KEY,
						edificioVO.getNombre());
			}
		}
	}

	private void removeSession(HttpServletRequest request) {
		removeInTemporalSession(request,
				SalasConsultaConstants.LISTA_DESCENDIENTES_KEY);
		removeInTemporalSession(request,
				SalasConsultaConstants.IS_LAST_LEVEL_KEY);
		removeInTemporalSession(request, SalasConsultaConstants.PATH_PADRE_KEY);
		removeInTemporalSession(request,
				SalasConsultaConstants.NOMBRE_PADRE_KEY);
	}
}