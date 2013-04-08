package descripcion.vos;

import fondos.db.ElementoCuadroClasificacionDBEntityImplBase;
import fondos.db.NivelCFDBEntityImpl;
import fondos.db.UnidadDocumentalDBEntityImpl;
import fondos.model.CamposBusquedas;
import fondos.model.ElementoCuadroClasificacion;
import ieci.core.db.DbColumnDef;

import java.util.Date;

import common.view.IVisitedRowVO;
import common.vos.BaseVO;

import deposito.db.UDocEnUiDepositoDbEntityImpl;
import descripcion.db.FechaDBEntityImpl;

/**
 * Clase que contiene la información de los elementos del cuadro de
 * clasificación que se mostrará en el listado de resultados de la búsqueda.
 */
public class ElementoCFVO extends BaseVO implements IVisitedRowVO {

	// Nombres de campos por los que es posible ordenar los resultados de las
	// búsquedas en el DisplayTable
	/*
	 * public static String COD_REFERENCIA_FIELD_POS = "0"; public static String
	 * TITULO_FIELD_POS = "1"; public static String NOMBRE_NIVEL_FIELD_POS =
	 * "2";
	 */

	// private static String defaultOrderingColName = "codReferencia";

	// Campo de ordenación por defecto de los resultados
	// private static String defaultOrderingColName =
	// ElementoCuadroClasificacionDBEntityImplBase.CODREFERENCIA_COLUMN_NAME;

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Mappings de las posiciones a la columna por la q ordenar en la bbdd
	private static DbColumnDef[] colsMappings = new DbColumnDef[] {
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_REFERENCIA_FIELD,
			ElementoCuadroClasificacionDBEntityImplBase.TITULO_FIELD,
			NivelCFDBEntityImpl.NOMBRE_NIVEL_FIELD,
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD,
			ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_FIELD,
			UnidadDocumentalDBEntityImpl.CAMPO_NUMERO_EXPEDIENTE,
			UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_FIELD,
			FechaDBEntityImpl.CAMPO_FECHA_INICIAL,
			FechaDBEntityImpl.CAMPO_FECHA_FINAL
	// ElementoCuadroClasificacionDBEntityImplBase.CODIGO_FIELD
	};
	private static String[] colsMappingsName = new String[] {
			ElementoCuadroClasificacionDBEntityImplBase.CODREFERENCIA_COLUMN_NAME,
			ElementoCuadroClasificacionDBEntityImplBase.TITULO_COLUMN_NAME,
			NivelCFDBEntityImpl.NOMBRE_NIVEL_COLUMN_NAME,
			// ElementoCuadroClasificacionDBEntityImplBase.NIVEL_COLUMN_NAME,
			ElementoCuadroClasificacionDBEntityImplBase.CODIGO_COLUMN_NAME,
			ElementoCuadroClasificacionDBEntityImplBase.ESTADO_ELEMENTO_COLUMN_NAME,
			UnidadDocumentalDBEntityImpl.NUMERO_EXPEDIENTE_COLUMN_NAME,
			UDocEnUiDepositoDbEntityImpl.SIGNATURAUDOC_COLUMN_NAME,
			"valorFInicial", "valorFFinal"
	// ElementoCuadroClasificacionDBEntityImplBase.CODIGO_COLUMN_NAME
	};

	/*
	 * Array de Strings que contendrá los nombres de los posibles campos de
	 * ordenación que se pueden definir el los XML de búsquedas y que se
	 * dispondrán en la misma posición que están en el colsMappingsName, que son
	 * los nombres reales de los campos en la bbdd por ejemplo: campo en el XML:
	 * "Codigo" - campo en bbdd:
	 * ElementoCuadroClasificacionDBEntityImplBase.CODIGO_COLUMN_NAME
	 */
	private static String[] colsXMLName = new String[] {
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_CODIGO_REFERENCIA,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_TITULO,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_NIVEL,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_CODIGO,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_ESTADO,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_NUMERO_EXPEDIENTE,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_SIGNATURA,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_FECHA_INICIAL,
			CamposBusquedas.CAMPO_ORDENACION_BUSQUEDA_FECHA_FINAL };

	/*
	 * private static String [] colsMappingsName = new String [] {
	 * "codReferencia", "titulo", "nivel" };
	 */

	private String id = null;
	private String codReferencia = null;
	private String titulo = null;
	private String idNivel = null;
	private String nombre = null;
	// private String nombreNivel = null;

	// Valores de fechas
	private String valorFInicial = null;
	private String calificadorFInicial = null;
	private String formatoFInicial = null;

	private String valorFFinal = null;
	private String calificadorFFinal = null;
	private String formatoFFinal = null;

	// Nuevo para sincro con ElementoCuadroClasificacion
	private String nombreFondo = null;
	private String codigo = null;
	private String idFondo = null;
	private int tipo;
	private int estado;
	private String nombreProductor = null;
	private String numexp = null;
	private String signaturaudoc = null;
	private String codsistproductor = null;
	private String identificacion = null;
	private int subtipo;
	private int orderNumExp;

	private Date fechaini = null;
	private Date fechafin = null;
	private int order2;
	private String order1 = null;

	private int marcasBloqueo;

	private String idEliminacion = null;

	/**
	 * Propiedad de estilo de la fila para las listas.
	 */
	private String rowStyle = IVisitedRowVO.CSS_FILA_NORMAL;

	public ElementoCFVO() {

	}

	/**
	 * @return Returns the codReferencia.
	 */
	public String getCodReferencia() {
		return codReferencia;
	}

	/**
	 * @param codReferencia
	 *            The codReferencia to set.
	 */
	public void setCodReferencia(String codReferencia) {
		this.codReferencia = codReferencia;
	}

	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            The id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return Returns the idNivel.
	 */
	public String getIdNivel() {
		return idNivel;
	}

	/**
	 * @param idNivel
	 *            The idNivel to set.
	 */
	public void setIdNivel(String idNivel) {
		this.idNivel = idNivel;
	}

	/**
	 * @return Returns the titulo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo
	 *            The titulo to set.
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public static DbColumnDef[] getColsMappings() {
		return colsMappings;
	}

	public static void setColsMappings(DbColumnDef[] colsMappings) {
		ElementoCFVO.colsMappings = colsMappings;
	}

	public static String[] getColsMappingsName() {
		return colsMappingsName;
	}

	public static void setColsMappingsName(String[] colsMappingsName) {
		ElementoCFVO.colsMappingsName = colsMappingsName;
	}

	public static int getColumnNumberByName(String columnName) {
		int ret = 0;

		for (int i = 0; i < colsMappingsName.length; i++)
			if (colsMappingsName[i].toUpperCase().equals(
					columnName.toUpperCase()))
				return i;

		return ret;
	}

	public static int getColumnNumberByXMLColumnName(String XMLColumnName) {
		int ret = 0;

		for (int i = 0; i < colsXMLName.length; i++)
			if (colsXMLName[i].equals(XMLColumnName))
				ret = i;

		return ret;
	}

	public static String getColumnNameByNumber(int columnNumber) {
		return colsMappingsName[columnNumber];
	}

	/*
	 * public static String getDefaultOrderingColName() { return
	 * defaultOrderingColName; }
	 * 
	 * 
	 * public static void setDefaultOrderingColName(String
	 * defaultOrderingColName) { ElementoCFVO.defaultOrderingColName =
	 * defaultOrderingColName; }
	 */

	public String getNombreFondo() {
		return nombreFondo;
	}

	public void setNombreFondo(String nombreFondo) {
		this.nombreFondo = nombreFondo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getIdFondo() {
		return idFondo;
	}

	public void setIdFondo(String idFondo) {
		this.idFondo = idFondo;
	}

	public int getTipo() {
		return tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public int getEstado() {
		return estado;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getRowStyle() {
		return rowStyle;
	}

	public void setRowStyle(String rowStyle) {
		this.rowStyle = rowStyle;
	}

	/*
	 * public String getCalificadorFFinal() { return calificadorFFinal; }
	 * 
	 * 
	 * public void setCalificadorFFinal(String calificadorFinal) {
	 * this.calificadorFFinal = calificadorFinal; }
	 * 
	 * 
	 * public String getCalificadorFInicial() { return calificadorFInicial; }
	 * 
	 * 
	 * public void setCalificadorFInicial(String calificadorInicial) {
	 * this.calificadorFInicial = calificadorInicial; }
	 * 
	 * 
	 * public String getFormatoFFinal() { return formatoFFinal; }
	 * 
	 * 
	 * public void setFormatoFFinal(String formatoFinal) { this.formatoFFinal =
	 * formatoFinal; }
	 * 
	 * 
	 * public String getFormatoFInicial() { return formatoFInicial; }
	 * 
	 * 
	 * public void setFormatoFInicial(String formatoInicial) {
	 * this.formatoFInicial = formatoInicial; }
	 * 
	 * 
	 * public String getValorFFinal() { return valorFFinal; }
	 * 
	 * 
	 * public void setValorFFinal(String valorFinal) { this.valorFFinal =
	 * valorFinal; }
	 * 
	 * 
	 * public String getValorFInicial() { return valorFInicial; }
	 * 
	 * 
	 * public void setValorFInicial(String valorInicial) { this.valorFInicial =
	 * valorInicial; }
	 */

	public String getNombreProductor() {
		return nombreProductor;
	}

	public void setNombreProductor(String nombreProductor) {
		this.nombreProductor = nombreProductor;
	}

	public static String[] getColsXMLName() {
		return colsXMLName;
	}

	public String getNumexp() {
		return numexp;
	}

	public void setNumexp(String numeroExpediente) {
		this.numexp = numeroExpediente;
	}

	public String getValorFInicial() {
		return valorFInicial;
	}

	public void setValorFInicial(String valorFInicial) {
		this.valorFInicial = valorFInicial;
	}

	public String getCalificadorFFinal() {
		return calificadorFFinal;
	}

	public void setCalificadorFFinal(String calificadorFFinal) {
		this.calificadorFFinal = calificadorFFinal;
	}

	public String getCalificadorFInicial() {
		return calificadorFInicial;
	}

	public void setCalificadorFInicial(String calificadorFInicial) {
		this.calificadorFInicial = calificadorFInicial;
	}

	public String getFormatoFFinal() {
		return formatoFFinal;
	}

	public void setFormatoFFinal(String formatoFFinal) {
		this.formatoFFinal = formatoFFinal;
	}

	public String getFormatoFInicial() {
		return formatoFInicial;
	}

	public void setFormatoFInicial(String formatoFInicial) {
		this.formatoFInicial = formatoFInicial;
	}

	public String getValorFFinal() {
		return valorFFinal;
	}

	public void setValorFFinal(String valorFFinal) {
		this.valorFFinal = valorFFinal;
	}

	public String getSignaturaudoc() {
		return signaturaudoc;
	}

	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	public String getCodsistproductor() {
		return codsistproductor;
	}

	public void setCodsistproductor(String codsistproductor) {
		this.codsistproductor = codsistproductor;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public int getSubtipo() {
		return subtipo;
	}

	public void setSubtipo(int subtipo) {
		this.subtipo = subtipo;
	}

	public boolean isSubtipoCaja() {
		return this.subtipo == ElementoCuadroClasificacion.SUBTIPO_CAJA;
	}

	public int getOrderNumExp() {
		return orderNumExp;
	}

	public void setOrderNumExp(int orderNumExp) {
		this.orderNumExp = orderNumExp;
	}

	public Date getFechaini() {
		return fechaini;
	}

	public void setFechaini(Date fechaini) {
		this.fechaini = fechaini;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public int getMarcasBloqueo() {
		return marcasBloqueo;
	}

	public void setMarcasBloqueo(int marcasBloqueo) {
		this.marcasBloqueo = marcasBloqueo;
	}

	public int getOrder2() {
		return order2;
	}

	public void setOrder2(int order2) {
		this.order2 = order2;
	}

	public String getOrder1() {
		return order1;
	}

	public void setOrder1(String order1) {
		this.order1 = order1;
	}

	public void setIdEliminacion(String idEliminacion) {
		this.idEliminacion = idEliminacion;
	}

	public String getIdEliminacion() {
		return idEliminacion;
	}
}