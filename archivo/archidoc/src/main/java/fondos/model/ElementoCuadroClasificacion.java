package fondos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;

import util.TreeModel;
import util.TreeModelItem;

import common.Constants;
import common.util.StringUtils;
import common.view.IVisitedRowVO;

import fondos.vos.ElementoCuadroClasificacionVO;

/**
 * Elementos de la estructura del cuadro de clasificación de fondos
 * documentales.
 */
public class ElementoCuadroClasificacion implements
		IElementoCuadroClasificacion, IVisitedRowVO {

	// TODO SUSTITUIR ESTO POR MAPEO DE PROPIEDADES PERSISTENTES
	public static final DynaProperty IDFICHADESCR_BEAN_PROPERTY = new DynaProperty(
			"idFichaDescr");

	public static final DynaProperty REPOSITORIO_ECM_BEAN_PROPERTY = new DynaProperty(
			"IDREPECM");

	/** TODOS los Tipos de elemento */
	public final static int TIPO_ALL = 0;
	/** Tipo de elemento 'CLASIFICADOR DE FONDOS' */
	public final static int TIPO_CL_FONDO = 1;
	/** Tipo de elemento 'FONDO' */
	public final static int TIPO_FONDO = 2;
	/** Tipo de elemento 'CLASIFICADOR DE SERIES' */
	public final static int TIPO_CL_SERIES = 3;
	/** Tipo de elemento 'SERIE' */
	public final static int TIPO_SERIE = 4;
	/** Tipo de elemento 'CLASIFICADOR DE UNIDAD DOCUMENTAL' */
	public final static int TIPO_CL_UNIDAD_DOCUMENTAL = 5;
	/** Tipo de elemento 'UNIDAD DOCUMENTAL' */
	public final static int TIPO_UNIDAD_DOCUMENTAL = 6;
	public final static int TIPO_DESCRIPTOR = 7;
	/** Subtipo de elemento unidad documental simple **/
	public final static int SUBTIPO_UNIDAD_DOCUMENTAL_SIMPLE = 61;
	/** Subtipo de elemento que se trata como una entidad caja **/
	public final static int SUBTIPO_CAJA = 62;

	/**
	 * Propiedad de estilo de la fila para las listas.
	 */
	private String rowStyle = IVisitedRowVO.CSS_FILA_NORMAL;

	String id = null;
	int tipo;
	String idNivel = null;
	String nombreNivel = null;
	String codigo = null;
	String titulo = null;
	String idPadre = null;
	String idFondo = null;
	String nombreFondo = null;
	String codRefFondo = null;
	String codReferencia = null;
	int estado;
	String idFichaDescr = null;
	String tienedescr = null;
	ElementoCuadroClasificacionVO parentElement = null;
	List childs = null;
	String idArchivo = null;
	String nombreArchivo = null;
	String idLCA = null;
	int nivelAcceso = 1;
	String finalCodRefPadre = null;
	String editClfDocs = null;
	String idRepEcm = null;
	String idEliminacion = null;
	Date fechaInicial = null;
	Date fechaFinal = null;
	int subtipo = 0;
	String codReferenciaPersonalizado = null;
	String ordpos = null;
	int marcasBloqueo = 0;
	String idobjref;
	String nombreProductor;
	String nombreCortoProductor;

	public ElementoCuadroClasificacion() {
		this.estado = IElementoCuadroClasificacion.TEMPORAL;
	}

	public ElementoCuadroClasificacion(String id) {
		super();
		this.id = id;
		this.estado = IElementoCuadroClasificacion.TEMPORAL;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodRefFondo() {
		return this.codRefFondo;
	}

	public void setCodRefFondo(String codRefFondo) {
		this.codRefFondo = codRefFondo;
	}

	public int getEstado() {
		return this.estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getEtiqueta() {
		StringBuffer separador = new StringBuffer();
		separador.append(Constants.STRING_SPACE).append("-")
				.append(Constants.STRING_SPACE);
		return getCodReferenciaTitulo(separador.toString());

		/*
		 * StringBuffer etiqueta = new StringBuffer();
		 * if(StringUtils.isNotEmpty(getCodReferencia())) {
		 * etiqueta.append(getCodReferencia()) .append(Constants.STRING_SPACE)
		 * .append("-") .append(Constants.STRING_SPACE); }
		 *
		 * etiqueta.append(getTitulo());
		 *
		 * return etiqueta.toString(); // return new
		 * StringBuffer().append(getCodReferencia
		 * ()).append(" - ").append(getTitulo()).toString();
		 */
	}

	public String getCodigoTitulo() {
		return getCodigoTitulo(Constants.STRING_SPACE);
	}

	public String getCodigoTitulo(String sep) {
		StringBuffer codigoTitulo = new StringBuffer();
		if (StringUtils.isNotEmpty(this.codigo)) {
			codigoTitulo.append(this.codigo).append(sep);
		}
		codigoTitulo.append(this.titulo);

		return codigoTitulo.toString();
	}

	public String getLabel() {
		StringBuffer label = new StringBuffer();
		label.append(this.titulo);
		if (StringUtils.isNotEmpty(this.codReferencia)) {
			label.append(Constants.STRING_SPACE)
				.append(Constants.ABRIR_PARENTESIS)
				.append(this.codReferencia)
				.append(Constants.CERRAR_PARENTESIS);
		}

		return label.toString();
	}

	public String getKey(){
		return getId();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String idElementoCf) {
		this.id = idElementoCf;
	}

	public String getIdFichaDescr() {
		return this.idFichaDescr;
	}

	public void setIdFichaDescr(String idFichaDescr) {
		this.idFichaDescr = idFichaDescr;
	}

	public String getIdFondo() {
		return this.idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public String getIdNivel() {
		return this.idNivel;
	}

	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	public String getIdPadre() {
		return this.idPadre;
	}

	public void setIdPadre(String idPadre) {
		this.idPadre = idPadre;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCodReferencia() {
		return codReferencia;
	}

	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	public String getCodReferenciaTitulo() {
		return getCodReferenciaTitulo(Constants.STRING_SPACE);
	}

	public String getCodReferenciaTitulo(String sep) {
		StringBuffer codReferenciaTitulo = new StringBuffer();
		if (StringUtils.isNotEmpty(this.codReferencia)) {
			codReferenciaTitulo.append(this.codReferencia).append(sep);
		}
		codReferenciaTitulo.append(this.titulo);

		return codReferenciaTitulo.toString();
	}

	public String getEditClfDocs() {
		return editClfDocs;
	}

	public void setEditClfDocs(String editClfDocs) {
		this.editClfDocs = editClfDocs;
	}

	public String getIdRepEcm() {
		return idRepEcm;
	}

	public void setIdRepEcm(String idRepEcm) {
		this.idRepEcm = idRepEcm;
	}

	public boolean isVigente() {
		return this.estado == IElementoCuadroClasificacion.VIGENTE;
	}

	public boolean isTemporal() {
		return this.estado == IElementoCuadroClasificacion.TEMPORAL;
	}

	public boolean isNoVigente() {
		return this.estado == IElementoCuadroClasificacion.NO_VIGENTE;
	}

	// public NivelCFVO getNivel() {
	// return nivel;
	// }
	//
	// public void setNivel(NivelCFVO nivel) {
	// this.nivel = nivel;
	// }

	public ElementoCuadroClasificacionVO getParentElement() {
		return parentElement;
	}

	public void setParentElement(ElementoCuadroClasificacionVO parentElement) {
		this.parentElement = parentElement;
	}

	public String getNombreFondo() {
		return nombreFondo;
	}

	public void setNombreFondo(String nombreFondo) {
		this.nombreFondo = nombreFondo;
	}

	public String getNombreNivel() {
		return nombreNivel;
	}

	public void setNombreNivel(String nombreNivel) {
		this.nombreNivel = nombreNivel;
	}

	public void addChild(IElementoCuadroClasificacion child) {
		if (this.childs == null)
			this.childs = new ArrayList();
		this.childs.add(child);
		child.setParentElement(this);
	}

	// public List listaDescendientes() {
	// return this.childs;
	// }

	// public List getAncestors() {
	// List ancestors = null;
	// if (parentElement != null) {
	// ancestors = new ArrayList();
	// ElementoCuadroClasificacionVO padre = parentElement;
	// do {
	// ancestors.add(0, padre);
	// padre = padre.getParentElement();
	// } while (padre != null);
	// }
	// return ancestors;
	// }

	public Object getItemId() {
		return id;
	}

	public String getItemName() {
		return getCodigoTitulo();
	}

	// public Collection childItems() {
	// // mediante proxy dinamico son puestos antes de devolverlos
	// return CollectionUtils.select(this.listaDescendientes(), new Predicate()
	// {
	// public boolean evaluate(Object obj) {
	// return ((IElementoCuadroClasificacion) obj).getEstado()
	// != IElementoCuadroClasificacion.TEMPORAL;
	// }
	// });
	// }

	// public TreeModelItem getParent() {
	// return (TreeModelItem) this.parentElement;
	// }

	// TODO esto no deberia ir aqui sino en el treeview ... si debe ir aqui pero
	// el treeview al sacar el path lo debe sacar del modelo
	public String getItemPath() {
		StringBuffer itemPath = new StringBuffer();
		if (parentElement != null)
			itemPath.append(((TreeModelItem) parentElement).getItemPath())
					.append("/");
		itemPath.append("item").append(this.getItemId());
		return itemPath.toString();
	}

	public TreeModel getTreeModel() {
		return null;
	}

	public boolean isLeaf() {
		return this.tipo == TipoNivelCF.SERIE.getIdentificador();
	}

	public void setChilds(List childs) {
		this.childs = childs != null ? childs : new ArrayList();
		CollectionUtils.forAllDo(this.childs, parentSetter);
	}

	// private static final List EMPTY_CHILD_LIST = new ArrayList();

	private final Closure parentSetter = new Closure() {
		public void execute(Object arg0) {
			((IElementoCuadroClasificacion) arg0)
					.setParentElement(ElementoCuadroClasificacion.this);
		}
	};

	public String getTienedescr() {
		return tienedescr;
	}

	public void setTienedescr(String tienedescr) {
		this.tienedescr = tienedescr;
	}

	public boolean tieneDescripcion() {
		if (tienedescr != null)
			return tienedescr.equalsIgnoreCase(Constants.FALSE_STRING) ? false
					: true;
		else
			return false;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getIdLCA() {
		return idLCA;
	}

	public void setIdLCA(String idLCA) {
		this.idLCA = idLCA;
	}

	public int getNivelAcceso() {
		return nivelAcceso;
	}

	public void setNivelAcceso(int nivelAcceso) {
		this.nivelAcceso = nivelAcceso;
	}

	public String getFinalCodRefPadre() {
		return finalCodRefPadre;
	}

	public void setFinalCodRefPadre(String finalCodRefPadre) {
		this.finalCodRefPadre = finalCodRefPadre;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fondos.vos.ElementoCuadroClasificacionVO#isClasificadorFondo()
	 */
	public boolean isTipoClasificadorFondo() {
		return getTipo() == TIPO_CL_FONDO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fondos.vos.ElementoCuadroClasificacionVO#isClasificadorSerie()
	 */
	public boolean isTipoClasificadorSerie() {
		return getTipo() == TIPO_CL_SERIES;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fondos.vos.ElementoCuadroClasificacionVO#isSerie()
	 */
	public boolean isTipoSerie() {
		return getTipo() == TIPO_SERIE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fondos.vos.ElementoCuadroClasificacionVO#isFondo()
	 */
	public boolean isTipoFondo() {
		return getTipo() == TIPO_FONDO;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see fondos.vos.ElementoCuadroClasificacionVO#isUnidadDocumental()
	 */
	public boolean isTipoUnidadDocumental() {
		return getTipo() == TIPO_UNIDAD_DOCUMENTAL;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	/**
	 * @return el fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}

	/**
	 * @param fechaFinal
	 *            el fechaFinal a establecer
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return el fechaInicial
	 */
	public Date getFechaInicial() {
		return fechaInicial;
	}

	/**
	 * @param fechaInicial
	 *            el fechaInicial a establecer
	 */
	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	/**
	 * @param subtipo
	 *            el subtipo a establecer
	 */
	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public boolean isSubtipoCaja() {
		return this.subtipo == SUBTIPO_CAJA;
	}

	public String getCodReferenciaPersonalizado() {
		return codReferenciaPersonalizado;
	}

	public void setCodReferenciaPersonalizado(String codRefPersonalizado) {
		this.codReferenciaPersonalizado = codRefPersonalizado;
	}

	public String getOrdPos() {
		return ordpos;
	}

	public void setOrdPos(String ordpos) {
		this.ordpos = ordpos;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcasBloqueo) {
		this.marcasBloqueo = marcasBloqueo;
	}

	public String getIdobjref() {
		return idobjref;
	}

	public void setIdobjref(String idobjref) {
		this.idobjref = idobjref;
	}

	public String getNombreProductor() {
		return nombreProductor;
	}

	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	public String getNombreCortoProductor() {
		if (StringUtils.isNotEmpty(nombreProductor)) {
			String organos[] = getNombreProductor().split(
					Constants.SEPARADOR_ANTECESORES_ORGANO);
			nombreCortoProductor = organos[0];
		}
		return nombreCortoProductor;
	}

	public void setNombreCortoProductor(String nombreCortoProductor) {
		this.nombreCortoProductor = nombreCortoProductor;
	}
}