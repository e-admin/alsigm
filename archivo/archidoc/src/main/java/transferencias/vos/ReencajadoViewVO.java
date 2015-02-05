package transferencias.vos;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;

import transferencias.actions.InfoUDocReeacr;

import common.vos.BaseVO;

import deposito.vos.FormatoHuecoVO;

/**
 * 
 * @author Iecisa
 * @version $Revision$
 * 
 */
public class ReencajadoViewVO extends BaseVO {

	private static final long serialVersionUID = 1L;

	private boolean mostrarBotonBajar = false;
	private boolean mostrarBotonSubir = false;
	private boolean mostrarBotonIncorporar = false;
	private boolean mostrarBotonExtraer = false;
	private boolean mostrarBotonDividir = false;
	private boolean mostrarBotonEliminarParte = false;
	private boolean editarDescripcionUI = false;
	private boolean editarDescripcionUdoc = false;
	private List listaUdocs = ListUtils.EMPTY_LIST;
	private List udocsSinAsignar = ListUtils.EMPTY_LIST;

	public ReencajadoViewVO(FormatoHuecoVO formatoRelacion,
			InfoUDocReeacr infoUdocReeacr, UIReeaCRVO uiReeaCrVO) {
		if (infoUdocReeacr.getUisWithUDocs().containsKey(uiReeaCrVO)) {
			listaUdocs = (List) infoUdocReeacr.getUisWithUDocs()
					.get(uiReeaCrVO);
		}
		udocsSinAsignar = infoUdocReeacr.getUdocsSinAsignar();

		editarDescripcionUI = true;

		if (formatoRelacion.isMultidoc()) {
			if (listaUdocs != null && listaUdocs.size() > 1) {
				mostrarBotonBajar = true;
				mostrarBotonSubir = true;
			}
		}

		if (listaUdocs != null && !listaUdocs.isEmpty()) {
			mostrarBotonExtraer = true;
			mostrarBotonDividir = true;
			editarDescripcionUdoc = true;
		}

		if (udocsSinAsignar != null && !udocsSinAsignar.isEmpty()) {
			mostrarBotonIncorporar = true;
		}

		if (!listaUdocs.isEmpty()) {
			for (Iterator iter = listaUdocs.iterator(); iter.hasNext();) {
				UDocEnUIReeaCRVO udocEnUIReeaCrVO = (UDocEnUIReeaCRVO) iter
						.next();
				if (!mostrarBotonEliminarParte
						&& !udocEnUIReeaCrVO.isCompleta()) {
					mostrarBotonEliminarParte = true;
				}
			}
		}
	}

	public boolean isMostrarBotonBajar() {
		return mostrarBotonBajar;
	}

	public boolean isMostrarBotonSubir() {
		return mostrarBotonSubir;
	}

	public boolean isMostrarBotonIncorporar() {
		return mostrarBotonIncorporar;
	}

	public boolean isMostrarBotonExtraer() {
		return mostrarBotonExtraer;
	}

	public boolean isMostrarBotonDividir() {
		return mostrarBotonDividir;
	}

	public boolean isMostrarBotonEliminarParte() {
		return mostrarBotonEliminarParte;
	}

	public boolean isEditarDescripcionUI() {
		return editarDescripcionUI;
	}

	public boolean isEditarDescripcionUdoc() {
		return editarDescripcionUdoc;
	}

	public List getListaUdocs() {
		return listaUdocs;
	}

	public List getUdocsSinAsignar() {
		return udocsSinAsignar;
	}
}