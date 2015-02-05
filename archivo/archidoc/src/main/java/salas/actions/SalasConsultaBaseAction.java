package salas.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.config.ForwardConfig;

import salas.SalasConsultaConstants;
import salas.model.GestionSalasConsultaBI;
import salas.model.TipoDocumentoIdentificativo;
import salas.vos.SalasConsultaVOBase;
import salas.vos.TipoDocumentoIdentificativoVO;
import util.TreeNode;
import util.TreeView;

import common.Constants;
import common.Messages;
import common.actions.ActionRedirect;
import common.actions.BaseAction;
import common.bi.GestionArchivosBI;

public class SalasConsultaBaseAction extends BaseAction {

	public ActionRedirect getActionRedirectVerEdificio(ActionMapping mappings,
			String idEdificio, boolean refreshView) {
		return getActionRedirect(
				mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_EDIFICIO),
				SalasConsultaConstants.PARAM_ID_EDIFICIO, idEdificio,
				refreshView);
	}

	public ActionRedirect getActionRedirectVerSala(ActionMapping mappings,
			String idSala, boolean refreshView) {
		return getActionRedirect(
				mappings.findForward(SalasConsultaConstants.GLOBAL_FORWARD_VER_SALA),
				SalasConsultaConstants.PARAM_ID_SALA, idSala, refreshView);
	}

	public ActionRedirect getActionRedirectCrearMesas(ActionMapping mappings,
			String numeroMesas) {
		return getActionRedirect(
				mappings.findForward(SalasConsultaConstants.FORWARD_CREAR_MESAS),
				SalasConsultaConstants.PARAM_NUM_MESAS, numeroMesas, false);
	}

	private ActionRedirect getActionRedirect(ForwardConfig forwardConfig,
			String paramName, String paramValue, boolean refreshView) {
		ActionRedirect actionRedirect = new ActionRedirect(forwardConfig, true);
		actionRedirect.addParameter(paramName, paramValue);

		if (refreshView) {
			actionRedirect.addParameter(
					SalasConsultaConstants.PARAM_REFRESHVIEW,
					Constants.BOOLEAN_TRUE_STRING);
		}

		return actionRedirect;

	}

	private EstructuraSalasConsultaTreeView getTreeView(
			HttpServletRequest request) {
		TreeView treeView = (TreeView) getFromTemporalSession(request,
				SalasConsultaConstants.SALAS_CONSULTA_VIEW_NAME);

		if (treeView == null) {
			GestionSalasConsultaBI bi = getGestionSalasBI(request);
			treeView = new EstructuraSalasConsultaTreeView(Messages.getString(
					SalasConsultaConstants.ETIQUETA_ARBOL_NAME,
					request.getLocale()), bi.getEstructuraSalasConsulta());
			treeView.setNodeSelectionHandler(new GestionVistaSalasConsulta());
			setInTemporalSession(request,
					SalasConsultaConstants.SALAS_CONSULTA_VIEW_NAME, treeView);
		}

		return (EstructuraSalasConsultaTreeView) treeView;
	}

	public void verNodoTreeView(HttpServletRequest request,
			SalasConsultaVOBase vo) {
		EstructuraSalasConsultaTreeView treeView = getTreeView(request);
		if (treeView != null) {
			TreeNode nodeToShow = treeView.getNode(vo.getItemPath());
			if (nodeToShow != null) {
				treeView.setSelectedNode(nodeToShow);
				nodeToShow.setVisible();
			}
		}
	}

	public void insertarNodoTreeView(HttpServletRequest request,
			SalasConsultaVOBase vo) {
		EstructuraSalasConsultaTreeView treeView = getTreeView(request);
		if (treeView != null) {
			TreeNode newViewNode = treeView.insertNode(
					treeView.getSelectedNode(), vo);
			treeView.setSelectedNode(newViewNode);
			request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
					Boolean.TRUE);
		}
	}

	public void actualizarNodoTreeView(HttpServletRequest request,
			SalasConsultaVOBase vo) {
		EstructuraSalasConsultaTreeView treeView = getTreeView(request);
		if (treeView != null) {
			TreeNode nodoModificado = treeView.findNode(vo);
			nodoModificado.setTreeModelItem(vo);
		}
	}

	public void eliminarNodoTreeView(HttpServletRequest request) {
		EstructuraSalasConsultaTreeView treeView = getTreeView(request);
		if (treeView != null) {
			TreeNode node = treeView.getSelectedNode();
			treeView.itemRemoved(node.getModelItem());
			request.setAttribute(SalasConsultaConstants.REFRESH_VIEW_KEY,
					new Boolean(true));
		}
	}

	public void colocarListaTipoDocumentos(HttpServletRequest request) {
		List listaTipos = new ArrayList();

		for (Iterator iterator = TipoDocumentoIdentificativo
				.getListaTiposDocumentoIdentificativo().iterator(); iterator
				.hasNext();) {
			String id = (String) iterator.next();
			String key = TipoDocumentoIdentificativoVO.getKey(id);
			String nombre = Messages.getString(key, request.getLocale());
			listaTipos.add(new TipoDocumentoIdentificativoVO(id, nombre));
		}

		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_TIPOS_DOC_IDENTIFICATIVO_KEY,
				listaTipos);

	}

	public void colocarListaArchivos(HttpServletRequest request) {
		GestionArchivosBI archivoBI = getGestionArchivosBI(request);
		String[] idsArchivo = getAppUser(request).getIdsArchivosUser();
		List archivos = archivoBI.getArchivosXId(idsArchivo);
		setInTemporalSession(request,
				SalasConsultaConstants.LISTA_ARCHIVOS_KEY, archivos);
	}
}
