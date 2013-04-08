package transferencias.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import transferencias.vos.IParteUnidadDocumentalVO;
import transferencias.vos.ParteUdocID;
import transferencias.vos.ParteUnidadDocumentalVO;
import transferencias.vos.RelacionEntregaVO;
import transferencias.vos.UDocElectronicaVO;
import transferencias.vos.UnidadDocumentalVO;
import transferencias.vos.UnidadInstalacionVO;

import common.Constants;
import common.bi.GestionRelacionesEntregaBI;
import common.exceptions.TooManyResultsException;
import common.pagination.PageInfo;
import common.view.POUtils;

/**
 * Asignacion de unidades documentales a unidades de instalacion
 * 
 */
public class InfoAsignacionUdocs {
	Logger logger = Logger.getLogger(InfoAsignacionUdocs.class);

	GestionRelacionesEntregaBI relacionBI = null;

	RelacionEntregaVO relacionEntrega = null;
	List partesSinAsignar = null;
	List listaUnidadesInstalacion = null;
	List listaUdocsRelacion = null;
	List listaPartesEnRelacion = null;
	Collection udocsSinDocumentosFisicos = null;

	Map unidadesInstalacion = null;
	Map unidadesDocumentales = null;
	// Map uiUdoc = null;
	Map udocsXui = null;
	Integer nofUdocsRelacion = null;
	// List listaUiXUdoc = null;
	boolean docsElectronicasConErrores = false;

	public InfoAsignacionUdocs(RelacionEntregaVO relacionEntrega,
			GestionRelacionesEntregaBI relacionBI, PageInfo pageInfo)
			throws TooManyResultsException {
		this.relacionEntrega = relacionEntrega;
		this.relacionBI = relacionBI;
		init(pageInfo);
	}

	public InfoAsignacionUdocs(RelacionEntregaVO relacionEntrega,
			GestionRelacionesEntregaBI relacionBI) {
		this.relacionEntrega = relacionEntrega;
		this.relacionBI = relacionBI;
		init();
	}

	public void init() {
		try {
			init(null);
		} catch (TooManyResultsException e) {
			logger.error("Error consultando asignacion udocs" + e);
		}
	}

	public void init(PageInfo pageInfo) throws TooManyResultsException {

		boolean isEntreArchivos = false;
		boolean tieneDescripcion = false;

		if (this.relacionEntrega.isEntreArchivos()) {
			isEntreArchivos = true;
		}

		if (StringUtils.isNotEmpty(this.relacionEntrega.getIdFicha())) {
			tieneDescripcion = true;
		}

		if (isEntreArchivos) {
			listaUdocsRelacion = relacionBI
					.getUDocsXidRelacionEntreArchivos(relacionEntrega.getId());

			udocsSinDocumentosFisicos = relacionBI
					.getUDocsElectronicasByIdRelacionEntreArchivos(relacionEntrega
							.getId());
		} else {
			if (pageInfo != null) {
				listaUdocsRelacion = relacionBI.getUdocs(
						relacionEntrega.getId(), pageInfo, tieneDescripcion);
			} else {
				listaUdocsRelacion = relacionBI.getUdocs(
						relacionEntrega.getId(), tieneDescripcion);
			}
			udocsSinDocumentosFisicos = relacionBI
					.getUDocsElectronicasByIdRelacion(relacionEntrega.getId());
		}

		unidadesDocumentales = new HashMap();
		partesSinAsignar = new ArrayList();
		UnidadDocumentalVO unaUdoc = null;
		for (Iterator i = listaUdocsRelacion.iterator(); i.hasNext();) {
			unaUdoc = (UnidadDocumentalVO) i.next();

			if (!isEntreArchivos) {
				if (unaUdoc.tieneDocumentosFisicos()) {
					// anadir las partes a la lista de partes
					List partesUdoc = ParteUnidadDocumentalVO
							.getPartesUdoc(unaUdoc);
					partesSinAsignar.addAll(partesUdoc);
				}
			} else {
				List partesUdoc = ParteUnidadDocumentalVO
						.getPartesUdoc(unaUdoc);
				partesSinAsignar.addAll(partesUdoc);
			}
			unidadesDocumentales.put(unaUdoc.getId(), unaUdoc);
		}

		if (udocsSinDocumentosFisicos != null
				&& !relacionEntrega.isEntreArchivos()) {
			for (Iterator i = udocsSinDocumentosFisicos.iterator(); i.hasNext();) {
				UDocElectronicaVO udocElectronica = (UDocElectronicaVO) i
						.next();

				UnidadDocumentalVO aUdoc = (UnidadDocumentalVO) unidadesDocumentales
						.get(udocElectronica.getId());
				aUdoc.setEstadoCotejo(udocElectronica.getEstadoCotejo());
			}
		}

		if (isEntreArchivos) {
			listaUnidadesInstalacion = relacionBI
					.getUnidadesInstalacionEntreArchivos(relacionEntrega
							.getId());
		} else {
			listaUnidadesInstalacion = relacionBI
					.getUnidadesInstalacion(relacionEntrega.getId());
		}
		if (listaUnidadesInstalacion != null) {
			udocsXui = new HashMap();
			unidadesInstalacion = new HashMap();

			String[] idsUis = new String[listaUnidadesInstalacion.size()];
			int j = 0;
			for (Iterator it = listaUnidadesInstalacion.iterator(); it
					.hasNext();) {
				UnidadInstalacionVO unaUI = (UnidadInstalacionVO) it.next();
				idsUis[j] = unaUI.getId();
				j++;
			}

			Map mapUdocsEnUInstalacion = relacionBI.getUdocsEnUIs(idsUis);

			for (Iterator i = listaUnidadesInstalacion.iterator(); i.hasNext();) {
				UnidadInstalacionVO unaUI = (UnidadInstalacionVO) i.next();
				List udocsEnUInstalacion = null;
				if (isEntreArchivos) {
					udocsEnUInstalacion = relacionBI.getUdocsEnUInst(unaUI
							.getId());
				} else {
					udocsEnUInstalacion = (List) mapUdocsEnUInstalacion
							.get(unaUI.getId());
					if (udocsEnUInstalacion == null) {
						udocsEnUInstalacion = new ArrayList();
					}
					partesSinAsignar.removeAll(udocsEnUInstalacion);
					CollectionUtils.transform(udocsEnUInstalacion,
							parteUdocToPO);

				}
				udocsXui.put(unaUI, udocsEnUInstalacion);

				if (!isEntreArchivos) {
					// Actualizar estado cotejo de la udoc
					for (Iterator itPartesUdoc = udocsEnUInstalacion.iterator(); itPartesUdoc
							.hasNext();) {
						IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdoc
								.next();
						// if (aPart.getEstadoCotejo() ==
						// EstadoCotejo.ERRORES.getIdentificador())
						// {
						String idUnidadDoc = aPart.getIdUnidadDoc();
						if (idUnidadDoc != null) {
							UnidadDocumentalVO aUdoc = (UnidadDocumentalVO) unidadesDocumentales
									.get(idUnidadDoc.trim());
							if (aUdoc != null)
								aUdoc.setEstadoCotejo(aPart.getEstadoCotejo());
						}

						// aUdoc.setEstadoCotejo(EstadoCotejo.ERRORES.getIdentificador());
						// break;
						// }
					}

				}
				// Nuevo para sacar la caja en la jsp de cajas de relación de
				// entrega
				// Actualizar el valor de número de caja en la que están las
				// u.docs de esta caja
				if (!isEntreArchivos) {
					for (Iterator itPartesUdoc = udocsEnUInstalacion.iterator(); itPartesUdoc
							.hasNext();) {
						IParteUnidadDocumentalVO aPart = (IParteUnidadDocumentalVO) itPartesUdoc
								.next();
						UnidadDocumentalVO udocVO = (UnidadDocumentalVO) unidadesDocumentales
								.get(aPart.getIdUnidadDoc());

						if (udocVO != null) {
							String uiOcupadas = udocVO.getUiOcupadas();
							if (StringUtils.isEmpty(uiOcupadas))
								uiOcupadas = new Integer(unaUI.getOrden())
										.toString();
							else
								uiOcupadas = uiOcupadas + Constants.COMMA
										+ unaUI.getOrden();
							udocVO.setUiOcupadas(uiOcupadas);
						}
					}
				} else {
					for (Iterator itUdoc = udocsEnUInstalacion.iterator(); itUdoc
							.hasNext();) {
						// ParteUnidadDocumentalVO aPart =
						// (ParteUnidadDocumentalVO) itPartesUdoc.next();
						UnidadDocumentalVO udocVO = (UnidadDocumentalVO) itUdoc
								.next();

						if (udocVO != null) {
							String uiOcupadas = udocVO.getUiOcupadas();
							uiOcupadas = new Integer(unaUI.getOrden())
									.toString();
							udocVO.setUiOcupadas(uiOcupadas);
						}
					}
				}

				unidadesInstalacion.put(unaUI.getId(), unaUI);
			}
		}
		CollectionUtils.transform(partesSinAsignar, parteUdocToPO);
		CollectionUtils.transform(listaUnidadesInstalacion, uiToPO);

		this.listaPartesEnRelacion = new ArrayList();
		if (udocsXui != null && udocsXui.size() > 0)
			for (Iterator i = udocsXui.keySet().iterator(); i.hasNext();)
				listaPartesEnRelacion.addAll((List) udocsXui.get(i.next()));
		listaPartesEnRelacion.addAll(partesSinAsignar);

		if (listaUdocsRelacion != null)
			nofUdocsRelacion = new Integer(listaUdocsRelacion.size());

		docsElectronicasConErrores = relacionBI
				.checkUdocsElectronicasConErrores(relacionEntrega.getId());

		/*
		 * listaUiXUdoc = new ArrayList(); if (udocsXui != null &&
		 * udocsXui.size() > 0) { for (Iterator i =
		 * udocsXui.keySet().iterator(); i.hasNext();) { List udocsThisUI =
		 * (List)udocsXui.get(i.next());
		 * 
		 * } }
		 */
	}

	public Map getUdocsXui() {
		return udocsXui;
	}

	public List getPartesSinAsignar() {
		return partesSinAsignar;
	}

	public int getNumUdocsSinAsignar() {
		int numUdocsSinAsignar = 0;
		if (partesSinAsignar != null)
			numUdocsSinAsignar = partesSinAsignar.size();
		return numUdocsSinAsignar;
	}

	public int getNumUdocsSinDocumentosFisicos() {
		int numUdocsSinDocumentosFisicos = 0;
		if (udocsSinDocumentosFisicos != null)
			numUdocsSinDocumentosFisicos = udocsSinDocumentosFisicos.size();
		return numUdocsSinDocumentosFisicos;
	}

	public IParteUnidadDocumentalVO getParteUnidadDocumental(String udocID,
			int numeroParte) {
		int index = listaPartesEnRelacion.indexOf(new ParteUdocID(udocID,
				numeroParte));
		IParteUnidadDocumentalVO parteUdoc = null;
		if (!(index < 0))
			parteUdoc = (IParteUnidadDocumentalVO) listaPartesEnRelacion
					.get(index);
		return parteUdoc;
	}

	public List getUdocsEnUI(UnidadInstalacionVO unidadInstalacion) {
		return (List) udocsXui.get(unidadInstalacion);
	}

	public List getUnidadesInstalacion() {
		return listaUnidadesInstalacion;
	}

	public int getNUnidadesInstalacion() {
		int nUInstalacion = 0;
		if (listaUnidadesInstalacion != null)
			nUInstalacion = listaUnidadesInstalacion.size();
		return nUInstalacion;
	}

	public void addUnidadInstalacion(UnidadInstalacionVO unidadInstalacion) {
		listaUnidadesInstalacion.add(uiToPO.transform(unidadInstalacion));
	}

	public List getUdocsRelacion() {
		return listaUdocsRelacion;
	}

	public Collection getUdocsSinDocumentosFisicos() {
		return udocsSinDocumentosFisicos;
	}

	public UnidadInstalacionVO getUnidadInstalacionUdoc(
			IParteUnidadDocumentalVO parteUdoc) {
		return ((ParteUnidadDocumentalPO) parteUdoc).getUnidadInstalacion();
	}

	public IParteUnidadDocumentalVO getParteUdoc(String udoc, int numeroParte) {
		ParteUdocID idParte = new ParteUdocID(udoc, numeroParte);
		IParteUnidadDocumentalVO parteUdoc = null;
		int pos = listaPartesEnRelacion.indexOf(idParte);
		if (!(pos < 0))
			parteUdoc = (IParteUnidadDocumentalVO) listaPartesEnRelacion
					.get(pos);
		return parteUdoc;
	}

	/**
	 * @param udocID
	 * @param numeroParte
	 * @param object
	 * @return
	 */
	public IParteUnidadDocumentalVO createParteUdoc(String udocID,
			int numeroParte, String signatura) {
		UnidadDocumentalVO udoc = (UnidadDocumentalVO) unidadesDocumentales
				.get(udocID);
		IParteUnidadDocumentalVO parteUdoc = (IParteUnidadDocumentalVO) parteUdocToPO
				.transform(ParteUnidadDocumentalVO.generateParteUdoc(udoc,
						numeroParte, signatura));
		listaPartesEnRelacion.add(parteUdoc);
		partesSinAsignar.add(parteUdoc);
		return parteUdoc;
	}

	Transformer uiToPO = new Transformer() {
		public Object transform(Object vo) {
			UnidadInstalacionPO po = new UnidadInstalacionPO(
					(List) udocsXui.get(vo));
			POUtils.copyVOProperties(po, vo);
			return po;
		}
	};

	public class ParteUnidadDocumentalPO extends ParteUnidadDocumentalVO {

		UnidadDocumentalVO unidadDocumental = null;
		UnidadInstalacionVO unidadInstalacion = null;

		ParteUnidadDocumentalPO() {
		}

		public UnidadDocumentalVO getUnidadDocumental() {
			if (unidadDocumental == null)
				unidadDocumental = (UnidadDocumentalVO) unidadesDocumentales
						.get(getIdUnidadDoc());
			return unidadDocumental;
		}

		public UnidadInstalacionVO getUnidadInstalacion() {
			if (unidadInstalacion == null)
				unidadInstalacion = (UnidadInstalacionVO) unidadesInstalacion
						.get(getIdUinstalacionRe());
			return unidadInstalacion;
		}
	}

	/**
	 * Transformer para convertir instancias de value objects para partes de
	 * unidad documental en objetos para presentacion
	 */
	Transformer parteUdocToPO = new Transformer() {

		public Object transform(Object vo) {
			// ParteUnidadDocumentalVO udocVO = (ParteUnidadDocumentalVO)vo;
			ParteUnidadDocumentalPO po = new ParteUnidadDocumentalPO();
			POUtils.copyVOProperties(po, vo);
			return po;
		}

	};

	public Integer getNofUdocsRelacion() {
		if (nofUdocsRelacion != null)
			return nofUdocsRelacion;
		else
			return new Integer(Constants.STRING_CERO);
	}

	public boolean isDocsElectronicasConErrores() {
		return docsElectronicasConErrores;
	}

	public void setDocsElectronicasConErrores(boolean docsElectronicasConErrores) {
		this.docsElectronicasConErrores = docsElectronicasConErrores;
	}

}