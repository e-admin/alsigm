package fondos.forms;

import ieci.core.types.IeciTdType;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import xml.config.Busqueda;

import common.Constants;
import common.Messages;
import common.Operadores;
import common.forms.CustomForm;
import common.pagination.PageInfo;
import common.util.ArrayUtils;
import common.util.CustomDate;
import common.util.CustomDateFormat;
import common.util.CustomDateRange;
import common.util.StringUtils;

import descripcion.DescripcionConstants;
import descripcion.FormaReemplazo;
import descripcion.model.TipoCampo;
import fondos.model.CamposBusquedas;
import fondos.model.PrecondicionesBusquedaFondosGenerica;
import fondos.utils.BusquedasHelper;
import fondos.vos.BusquedaElementosVO;

/**
 * Formulario de búsqueda de elementos del cuadro.
 */
public class BusquedaElementosForm extends CustomForm implements IBusquedaForm {

	private static final long serialVersionUID = 1L;

	private String postBack = Constants.FALSE_STRING;

	private String numeroExpediente = null;
	private String numeroExpediente_like = null;

	private String productores = null;
	private String codigoReferencia = null;
	private String titulo = null;
	private String texto = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private String procedimiento = null;
	private String rango = null;
	private String signatura_like = null;
	private String signatura_buscar = null;
	private String signatura = null;
	private String signaturaudoc = null;

	/* Fecha */
	private String fechaComp = null;
	private String fechaFormato = null;
	private String fechaA = null;
	private String fechaM = null;
	private String fechaD = null;
	private String fechaS = null;
	private String fechaIniFormato = null;
	private String fechaIniA = null;
	private String fechaIniM = null;
	private String fechaIniD = null;
	private String fechaIniS = null;
	private String fechaFinFormato = null;
	private String fechaFinA = null;
	private String fechaFinM = null;
	private String fechaFinD = null;
	private String fechaFinS = null;

	/*
	 * Genéricos de Fecha
	 */
	private String[] genericoFechaComp = new String[0];
	private String[] genericoFechaFormato = new String[0];
	private String[] genericoFechaA = new String[0];
	private String[] genericoFechaM = new String[0];
	private String[] genericoFechaD = new String[0];
	private String[] genericoFechaS = new String[0];
	private String[] genericoFechaIniFormato = new String[0];
	private String[] genericoFechaIniA = new String[0];
	private String[] genericoFechaIniM = new String[0];
	private String[] genericoFechaIniD = new String[0];
	private String[] genericoFechaIniS = new String[0];
	private String[] genericoFechaFinFormato = new String[0];
	private String[] genericoFechaFinA = new String[0];
	private String[] genericoFechaFinM = new String[0];
	private String[] genericoFechaFinD = new String[0];
	private String[] genericoFechaFinS = new String[0];
	private String[] genericoFechaCalificador = new String[0];

	/**
	 * Identificador del Campo Fecha
	 */
	private String[] genericoIdFecha = new String[0];

	/**
	 * Clave del properties que contiene el titulo del campo
	 */
	private String[] genericoEtiquetaFecha = new String[0];

	// Genéricos de Texto Largo
	private String[] genericoOperadorTextoCorto = new String[0];

	/**
	 * Identificador del Campo Texto Largo
	 */
	private String[] genericoIdTextoLargo = new String[0];

	/**
	 * Valores de Textos Largos
	 */
	private String[] genericoTextoLargo = new String[0];

	/**
	 * Clave del properties que contiene el titulo del campo
	 */
	private String[] genericoEtiquetaTextoLargo = new String[0];

	// Genericos de Texto Corto
	/**
	 * Identificador de los Ids de Texto Corto
	 */
	private String[] genericoIdTextoCorto = new String[0];

	/**
	 * Valores de Texto Texto Corto
	 */
	private String[] genericoTextoCorto = new String[0];

	/**
	 * Clave del properties que contiene el titulo del campo
	 */
	private String[] genericoEtiquetaTextoCorto = new String[0];

	// Genéricos de Campo Numérico
	/**
	 * Identificadores de campos numéricos
	 */
	private String[] genericoIdCampoNumerico = new String[0];

	/**
	 * Valores de Campos Numéricos
	 */
	private String[] genericoCampoNumerico = new String[0];

	/**
	 * Clave del properties que contiene el titulo del campo
	 */
	private String[] genericoEtiquetaCampoNumerico = new String[0];

	/**
	 * Operadores de Campos Numéricos
	 */
	private String[] genericoOperadorCampoNumerico = new String[0];

	/**
	 * Valores de Rangos Finales de Campos Numéricos.
	 */
	private String[] genericoCampoNumericoFin = new String[0];

	// Identificadores
	// private String[] genericoIdentificador = new String[0];

	/* Fecha inicial */
	private String fechaCompIni = null;
	private String fechaFormatoIni = null;
	private String fechaAIni = null;
	private String fechaMIni = null;
	private String fechaDIni = null;
	private String fechaSIni = null;
	private String fechaIniFormatoIni = null;
	private String fechaIniAIni = null;
	private String fechaIniMIni = null;
	private String fechaIniDIni = null;
	private String fechaIniSIni = null;
	private String fechaFinFormatoIni = null;
	private String fechaFinAIni = null;
	private String fechaFinMIni = null;
	private String fechaFinDIni = null;
	private String fechaFinSIni = null;

	/* Fecha final */
	private String fechaCompFin = null;
	private String fechaFormatoFin = null;
	private String fechaAFin = null;
	private String fechaMFin = null;
	private String fechaDFin = null;
	private String fechaSFin = null;
	private String fechaIniFormatoFin = null;
	private String fechaIniAFin = null;
	private String fechaIniMFin = null;
	private String fechaIniDFin = null;
	private String fechaIniSFin = null;
	private String fechaFinFormatoFin = null;
	private String fechaFinAFin = null;
	private String fechaFinMFin = null;
	private String fechaFinDFin = null;
	private String fechaFinSFin = null;

	private String calificador = null;
	private String calificadorInicial = null;
	private String calificadorFinal = null;
	private String fondo = null;
	private String[] niveles = new String[0];
	private boolean activarNiveles = false;
	private String tipoElemento = null;
	private String descriptores = null;
	private String tipoBusqueda = null;
	private boolean usaCache = false;

	/* Campos de BusquedaElementosForm */
	private String[] nombreObjetoAmbito = new String[0];
	// private String [] codRefObjetoAmbito = new String[0];
	private String[] idObjetoAmbito = new String[0];
	private String[] tipoObjetoAmbito = new String[0];
	private String codigoRelacion = null;
	private String codigo = null;
	private String[] estados = new String[0];
	private boolean activarEstados = false;
	private String idFicha = "0";

	private String[] booleano = new String[0];
	private String[] abrirpar = new String[0];
	private String[] campo = new String[0];
	private Integer[] tipoCampo = new Integer[0];
	private String[] operador = new String[0];
	private String[] cerrarpar = new String[0];
	private String[] formatoFechaSel1 = new String[0];
	private String[] formatoFecha1 = new String[0];
	private String[] valor1 = new String[0];
	private String[] valor1D = new String[0];
	private String[] valor1M = new String[0];
	private String[] valor1A = new String[0];
	private String[] valor1S = new String[0];

	private String[] formatoFechaSel2 = new String[0];
	private String[] formatoFecha2 = new String[0];
	private String[] valor2 = new String[0];
	private String[] valor2D = new String[0];
	private String[] valor2M = new String[0];
	private String[] valor2A = new String[0];
	private String[] valor2S = new String[0];

	private String contenidoFichero = null;

	/**
	 * Cada vez que se crea una nueva fila de detalle avanzado, este valor se va
	 * incrementando en uno. De esta forma, se van creando los controles con un
	 * identificador único por fila. Este valor no tiene porque corresponderse
	 * con el de la fila que ocupa, ya que si se borran filas intermedias el
	 * numero no se decrementará o si se insertan filas por el medio no se
	 * volverán a calcular.
	 */
	private int contadorDetallesAvanzados = 0;

	private String[] nombreDesc = new String[0];

	private ArrayList camposRellenos = new ArrayList();

	private PageInfo pageInfo = null;

	private String[] selectedElem = null;

	private boolean mostrarSinPaginar = false;

	private String formaReemplazo = null;

	private String valorReemplazoParcial = null;

	private String tipoReferencia = null;

	private String[] tiposReferencia = null;

	private String formatoFecha4 = null;
	private String valor4 = null;
	private String valor4D = null;
	private String valor4M = null;
	private String valor4A = null;
	private String valor4S = null;
	private String nombreDesc4 = null;

	/**
	 * Nombre del campo de referencia en el cual se debe copiar el valor
	 * seleccionado. Esta propiedad se utiliza para la asociación de unidades
	 * realacionadas.
	 */
	private String ref = null;

	private String[] bloqueos = new String[0];

	private String conservada;

	private boolean activarBloqueos = false;

	/**
	 * Constructor.
	 */
	public BusquedaElementosForm() {
		super();
	}

	/**
	 * Inicia el formulario.
	 *
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		nombreObjetoAmbito = new String[0];
		// codRefObjetoAmbito = new String[0];
		idObjetoAmbito = new String[0];
		tipoObjetoAmbito = new String[0];
		niveles = new String[0];
		estados = new String[0];

		booleano = new String[0];
		abrirpar = new String[0];
		campo = new String[0];
		tipoCampo = new Integer[0];
		operador = new String[0];
		cerrarpar = new String[0];
		formatoFechaSel1 = new String[0];
		formatoFecha1 = new String[0];
		valor1 = new String[0];
		valor1D = new String[0];
		valor1M = new String[0];
		valor1A = new String[0];
		valor1S = new String[0];
		valor2 = new String[0];
		valor2D = new String[0];
		valor2M = new String[0];
		valor2A = new String[0];
		valor2S = new String[0];
		formatoFechaSel2 = new String[0];
		formatoFecha2 = new String[0];
		nombreDesc = new String[0];

		/*
		 * idObjetoAmbito = new String[0]; nombreObjetoAmbito= new String[0];
		 * idFicha = null;
		 *
		 * tipoReferencia = null; tiposReferencia = null; formatoFecha4 = null;
		 * valor4 = null; valor4D = null; valor4M = null; valor4A = null;
		 * valor4S = null; nombreDesc4 = null;
		 */
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public void setSignatura_like(String signatura_like) {
		this.signatura_like = signatura_like;
	}

	public String getNumeroExpediente_like() {
		return numeroExpediente_like;
	}

	public void setNumeroExpediente_like(String numeroExpediente_like) {
		this.numeroExpediente_like = numeroExpediente_like;
	}

	public String getSignatura_buscar() {
		return signatura_buscar;
	}

	public void setSignatura_buscar(String signatura_buscar) {
		this.signatura_buscar = signatura_buscar;
	}

	public String getSignaturaudoc() {
		return signaturaudoc;
	}

	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	/**
	 * @return Returns the codigo.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            The codigo to set.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoRelacion() {
		return codigoRelacion;
	}

	public void setCodigoRelacion(String codigoRelacion) {
		this.codigoRelacion = codigoRelacion;
	}

	/**
	 * @return Returns the estados.
	 */
	public String[] getEstados() {
		return estados;
	}

	/**
	 * @param estados
	 *            The estados to set.
	 */
	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	/**
	 * @return Returns the niveles.
	 */
	public String[] getNiveles() {
		return niveles;
	}

	/**
	 * @param niveles
	 *            The niveles to set.
	 */
	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
	}

	/**
	 * @return Returns the pageInfo.
	 */
	public PageInfo getPageInfo() {
		return pageInfo;
	}

	/**
	 * @param pageInfo
	 *            The pageInfo to set.
	 */
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
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

	/**
	 * @return Returns the nombreObjetoAmbito.
	 */
	public String[] getNombreObjetoAmbito() {
		return nombreObjetoAmbito;
	}

	/**
	 * @param nombreObjetoAmbito
	 *            The nombreObjetoAmbito to set.
	 */
	public void setNombreObjetoAmbito(String[] nombreObjetoAmbito) {
		this.nombreObjetoAmbito = nombreObjetoAmbito;
	}

	/**
	 * @return Returns the idFicha.
	 */
	public String getIdFicha() {
		return idFicha;
	}

	/**
	 * @param idFicha
	 *            The idFicha to set.
	 */
	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	/**
	 * @return el tipoBusqueda
	 */
	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	/**
	 * @param tipoBusqueda
	 *            el tipoBusqueda a establecer
	 */
	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	/**
	 * @return the idObjetoAmbito
	 */
	public String[] getIdObjetoAmbito() {
		return idObjetoAmbito;
	}

	/**
	 * @param idObjetoAmbito
	 *            the idObjetoAmbito to set
	 */
	public void setIdObjetoAmbito(String[] idObjetoAmbito) {
		this.idObjetoAmbito = idObjetoAmbito;
	}

	public String[] getTipoObjetoAmbito() {
		return tipoObjetoAmbito;
	}

	public void setTipoObjetoAmbito(String[] tipoObjetoAmbito) {
		this.tipoObjetoAmbito = tipoObjetoAmbito;
	}

	// /**
	// * @return Returns the codRefObjetoAmbito.
	// */
	// public String[] getCodRefObjetoAmbito()
	// {
	// return codRefObjetoAmbito;
	// }
	// /**
	// * @param codRefObjetoAmbito The codRefObjetoAmbito to set.
	// */
	// public void setCodRefObjetoAmbito(String[] codRefObjetoAmbito)
	// {
	// this.codRefObjetoAmbito = codRefObjetoAmbito;
	// }
	/**
	 * @return Returns the abrirpar.
	 */
	public String[] getAbrirpar() {
		return abrirpar;
	}

	/**
	 * @param abrirpar
	 *            The abrirpar to set.
	 */
	public void setAbrirpar(String[] abrirpar) {
		this.abrirpar = abrirpar;
	}

	/**
	 * @return Returns the booleano.
	 */
	public String[] getBooleano() {
		return booleano;
	}

	/**
	 * @param booleano
	 *            The booleano to set.
	 */
	public void setBooleano(String[] booleano) {
		this.booleano = booleano;
	}

	/**
	 * @return Returns the campo.
	 */
	public String[] getCampo() {
		return campo;
	}

	/**
	 * @param campo
	 *            The campo to set.
	 */
	public void setCampo(String[] campo) {
		this.campo = campo;
	}

	/**
	 * @return Returns the cerrarpar.
	 */
	public String[] getCerrarpar() {
		return cerrarpar;
	}

	/**
	 * @param cerrarpar
	 *            The cerrarpar to set.
	 */
	public void setCerrarpar(String[] cerrarpar) {
		this.cerrarpar = cerrarpar;
	}

	/**
	 * @return el tipoElemento
	 */
	public String getTipoElemento() {
		return tipoElemento;
	}

	/**
	 * @param tipoElemento
	 *            el tipoElemento a establecer
	 */
	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	/**
	 * @return Returns the operador.
	 */
	public String[] getOperador() {
		return operador;
	}

	/**
	 * @param operador
	 *            The operador to set.
	 */
	public void setOperador(String[] operador) {
		this.operador = operador;
	}

	/**
	 * @return Returns the tipoCampo.
	 */
	public Integer[] getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * @param tipoCampo
	 *            The tipoCampo to set.
	 */
	public void setTipoCampo(Integer[] tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	/**
	 * @return Returns the valor1.
	 */
	public String[] getValor1() {
		return valor1;
	}

	/**
	 * @param valor1
	 *            The valor1 to set.
	 */
	public void setValor1(String[] valor1) {
		this.valor1 = valor1;
	}

	/**
	 * @return Returns the valor1A.
	 */
	public String[] getValor1A() {
		return valor1A;
	}

	/**
	 * @param valor1A
	 *            The valor1A to set.
	 */
	public void setValor1A(String[] valor1A) {
		this.valor1A = valor1A;
	}

	/**
	 * @return Returns the valor1D.
	 */
	public String[] getValor1D() {
		return valor1D;
	}

	/**
	 * @param valor1D
	 *            The valor1D to set.
	 */
	public void setValor1D(String[] valor1D) {
		this.valor1D = valor1D;
	}

	/**
	 * @return Returns the valor1M.
	 */
	public String[] getValor1M() {
		return valor1M;
	}

	/**
	 * @param valor1M
	 *            The valor1M to set.
	 */
	public void setValor1M(String[] valor1M) {
		this.valor1M = valor1M;
	}

	/**
	 * @return Returns the valor2.
	 */
	public String[] getValor2() {
		return valor2;
	}

	/**
	 * @param valor2
	 *            The valor2 to set.
	 */
	public void setValor2(String[] valor2) {
		this.valor2 = valor2;
	}

	/**
	 * @return Returns the valor2A.
	 */
	public String[] getValor2A() {
		return valor2A;
	}

	/**
	 * @param valor2A
	 *            The valor2A to set.
	 */
	public void setValor2A(String[] valor2A) {
		this.valor2A = valor2A;
	}

	/**
	 * @return Returns the valor2D.
	 */
	public String[] getValor2D() {
		return valor2D;
	}

	/**
	 * @param valor2D
	 *            The valor2D to set.
	 */
	public void setValor2D(String[] valor2D) {
		this.valor2D = valor2D;
	}

	/**
	 * @return Returns the valor2M.
	 */
	public String[] getValor2M() {
		return valor2M;
	}

	/**
	 * @param valor2M
	 *            The valor2M to set.
	 */
	public void setValor2M(String[] valor2M) {
		this.valor2M = valor2M;
	}

	/**
	 * @return Returns the nombreDesc.
	 */
	public String[] getNombreDesc() {
		return nombreDesc;
	}

	/**
	 * @param nombreDesc
	 *            The nombreDesc to set.
	 */
	public void setNombreDesc(String[] nombreDesc) {
		this.nombreDesc = nombreDesc;
	}

	/**
	 * @return el calificador
	 */
	public String getCalificador() {
		return calificador;
	}

	/**
	 * @param calificador
	 *            el calificador a establecer
	 */
	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	/**
	 * @return el codigoReferencia
	 */
	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	/**
	 * @param codigoReferencia
	 *            el codigoReferencia a establecer
	 */
	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	/**
	 * @return el descriptores
	 */
	public String getDescriptores() {
		return descriptores;
	}

	/**
	 * @param descriptores
	 *            el descriptores a establecer
	 */
	public void setDescriptores(String descriptores) {
		this.descriptores = descriptores;
	}

	/**
	 * @return el fechaAFin
	 */
	public String getFechaAFin() {
		return fechaAFin;
	}

	/**
	 * @param fechaAFin
	 *            el fechaAFin a establecer
	 */
	public void setFechaAFin(String fechaAFin) {
		this.fechaAFin = fechaAFin;
	}

	/**
	 * @return el fechaAIni
	 */
	public String getFechaAIni() {
		return fechaAIni;
	}

	/**
	 * @param fechaAIni
	 *            el fechaAIni a establecer
	 */
	public void setFechaAIni(String fechaAIni) {
		this.fechaAIni = fechaAIni;
	}

	/**
	 * @return el fechaCompFin
	 */
	public String getFechaCompFin() {
		return fechaCompFin;
	}

	/**
	 * @param fechaCompFin
	 *            el fechaCompFin a establecer
	 */
	public void setFechaCompFin(String fechaCompFin) {
		this.fechaCompFin = fechaCompFin;
	}

	/**
	 * @return el fechaCompIni
	 */
	public String getFechaCompIni() {
		return fechaCompIni;
	}

	/**
	 * @param fechaCompIni
	 *            el fechaCompIni a establecer
	 */
	public void setFechaCompIni(String fechaCompIni) {
		this.fechaCompIni = fechaCompIni;
	}

	/**
	 * @return el fechaDFin
	 */
	public String getFechaDFin() {
		return fechaDFin;
	}

	/**
	 * @param fechaDFin
	 *            el fechaDFin a establecer
	 */
	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	/**
	 * @return el fechaDIni
	 */
	public String getFechaDIni() {
		return fechaDIni;
	}

	/**
	 * @param fechaDIni
	 *            el fechaDIni a establecer
	 */
	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	/**
	 * @return el fechaFinAFin
	 */
	public String getFechaFinAFin() {
		return fechaFinAFin;
	}

	/**
	 * @param fechaFinAFin
	 *            el fechaFinAFin a establecer
	 */
	public void setFechaFinAFin(String fechaFinAFin) {
		this.fechaFinAFin = fechaFinAFin;
	}

	/**
	 * @return el fechaFinAIni
	 */
	public String getFechaFinAIni() {
		return fechaFinAIni;
	}

	/**
	 * @param fechaFinAIni
	 *            el fechaFinAIni a establecer
	 */
	public void setFechaFinAIni(String fechaFinAIni) {
		this.fechaFinAIni = fechaFinAIni;
	}

	/**
	 * @return el fechaFinDFin
	 */
	public String getFechaFinDFin() {
		return fechaFinDFin;
	}

	/**
	 * @param fechaFinDFin
	 *            el fechaFinDFin a establecer
	 */
	public void setFechaFinDFin(String fechaFinDFin) {
		this.fechaFinDFin = fechaFinDFin;
	}

	/**
	 * @return el fechaFinDIni
	 */
	public String getFechaFinDIni() {
		return fechaFinDIni;
	}

	/**
	 * @param fechaFinDIni
	 *            el fechaFinDIni a establecer
	 */
	public void setFechaFinDIni(String fechaFinDIni) {
		this.fechaFinDIni = fechaFinDIni;
	}

	/**
	 * @return el fechaFinFormatoFin
	 */
	public String getFechaFinFormatoFin() {
		return fechaFinFormatoFin;
	}

	/**
	 * @param fechaFinFormatoFin
	 *            el fechaFinFormatoFin a establecer
	 */
	public void setFechaFinFormatoFin(String fechaFinFormatoFin) {
		this.fechaFinFormatoFin = fechaFinFormatoFin;
	}

	/**
	 * @return el fechaFinFormatoIni
	 */
	public String getFechaFinFormatoIni() {
		return fechaFinFormatoIni;
	}

	/**
	 * @param fechaFinFormatoIni
	 *            el fechaFinFormatoIni a establecer
	 */
	public void setFechaFinFormatoIni(String fechaFinFormatoIni) {
		this.fechaFinFormatoIni = fechaFinFormatoIni;
	}

	/**
	 * @return el fechaFinMFin
	 */
	public String getFechaFinMFin() {
		return fechaFinMFin;
	}

	/**
	 * @param fechaFinMFin
	 *            el fechaFinMFin a establecer
	 */
	public void setFechaFinMFin(String fechaFinMFin) {
		this.fechaFinMFin = fechaFinMFin;
	}

	/**
	 * @return el fechaFinMIni
	 */
	public String getFechaFinMIni() {
		return fechaFinMIni;
	}

	/**
	 * @param fechaFinMIni
	 *            el fechaFinMIni a establecer
	 */
	public void setFechaFinMIni(String fechaFinMIni) {
		this.fechaFinMIni = fechaFinMIni;
	}

	/**
	 * @return el fechaFinSFin
	 */
	public String getFechaFinSFin() {
		return fechaFinSFin;
	}

	/**
	 * @param fechaFinSFin
	 *            el fechaFinSFin a establecer
	 */
	public void setFechaFinSFin(String fechaFinSFin) {
		this.fechaFinSFin = fechaFinSFin;
	}

	/**
	 * @return el fechaFinSIni
	 */
	public String getFechaFinSIni() {
		return fechaFinSIni;
	}

	/**
	 * @param fechaFinSIni
	 *            el fechaFinSIni a establecer
	 */
	public void setFechaFinSIni(String fechaFinSIni) {
		this.fechaFinSIni = fechaFinSIni;
	}

	/**
	 * @return el fechaFormatoFin
	 */
	public String getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	/**
	 * @param fechaFormatoFin
	 *            el fechaFormatoFin a establecer
	 */
	public void setFechaFormatoFin(String fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	/**
	 * @return el fechaFormatoIni
	 */
	public String getFechaFormatoIni() {
		return fechaFormatoIni;
	}

	/**
	 * @param fechaFormatoIni
	 *            el fechaFormatoIni a establecer
	 */
	public void setFechaFormatoIni(String fechaFormatoIni) {
		this.fechaFormatoIni = fechaFormatoIni;
	}

	/**
	 * @return el fechaIniAFin
	 */
	public String getFechaIniAFin() {
		return fechaIniAFin;
	}

	/**
	 * @param fechaIniAFin
	 *            el fechaIniAFin a establecer
	 */
	public void setFechaIniAFin(String fechaIniAFin) {
		this.fechaIniAFin = fechaIniAFin;
	}

	/**
	 * @return el fechaIniAIni
	 */
	public String getFechaIniAIni() {
		return fechaIniAIni;
	}

	/**
	 * @param fechaIniAIni
	 *            el fechaIniAIni a establecer
	 */
	public void setFechaIniAIni(String fechaIniAIni) {
		this.fechaIniAIni = fechaIniAIni;
	}

	/**
	 * @return el fechaIniDFin
	 */
	public String getFechaIniDFin() {
		return fechaIniDFin;
	}

	/**
	 * @param fechaIniDFin
	 *            el fechaIniDFin a establecer
	 */
	public void setFechaIniDFin(String fechaIniDFin) {
		this.fechaIniDFin = fechaIniDFin;
	}

	/**
	 * @return el fechaIniDIni
	 */
	public String getFechaIniDIni() {
		return fechaIniDIni;
	}

	/**
	 * @param fechaIniDIni
	 *            el fechaIniDIni a establecer
	 */
	public void setFechaIniDIni(String fechaIniDIni) {
		this.fechaIniDIni = fechaIniDIni;
	}

	/**
	 * @return el fechaIniFormatoFin
	 */
	public String getFechaIniFormatoFin() {
		return fechaIniFormatoFin;
	}

	/**
	 * @param fechaIniFormatoFin
	 *            el fechaIniFormatoFin a establecer
	 */
	public void setFechaIniFormatoFin(String fechaIniFormatoFin) {
		this.fechaIniFormatoFin = fechaIniFormatoFin;
	}

	/**
	 * @return el fechaIniFormatoIni
	 */
	public String getFechaIniFormatoIni() {
		return fechaIniFormatoIni;
	}

	/**
	 * @param fechaIniFormatoIni
	 *            el fechaIniFormatoIni a establecer
	 */
	public void setFechaIniFormatoIni(String fechaIniFormatoIni) {
		this.fechaIniFormatoIni = fechaIniFormatoIni;
	}

	/**
	 * @return el fechaIniMFin
	 */
	public String getFechaIniMFin() {
		return fechaIniMFin;
	}

	/**
	 * @param fechaIniMFin
	 *            el fechaIniMFin a establecer
	 */
	public void setFechaIniMFin(String fechaIniMFin) {
		this.fechaIniMFin = fechaIniMFin;
	}

	/**
	 * @return el fechaIniMIni
	 */
	public String getFechaIniMIni() {
		return fechaIniMIni;
	}

	/**
	 * @param fechaIniMIni
	 *            el fechaIniMIni a establecer
	 */
	public void setFechaIniMIni(String fechaIniMIni) {
		this.fechaIniMIni = fechaIniMIni;
	}

	/**
	 * @return el fechaIniSFin
	 */
	public String getFechaIniSFin() {
		return fechaIniSFin;
	}

	/**
	 * @param fechaIniSFin
	 *            el fechaIniSFin a establecer
	 */
	public void setFechaIniSFin(String fechaIniSFin) {
		this.fechaIniSFin = fechaIniSFin;
	}

	/**
	 * @return el fechaIniSIni
	 */
	public String getFechaIniSIni() {
		return fechaIniSIni;
	}

	/**
	 * @param fechaIniSIni
	 *            el fechaIniSIni a establecer
	 */
	public void setFechaIniSIni(String fechaIniSIni) {
		this.fechaIniSIni = fechaIniSIni;
	}

	/**
	 * @return el fechaMFin
	 */
	public String getFechaMFin() {
		return fechaMFin;
	}

	/**
	 * @param fechaMFin
	 *            el fechaMFin a establecer
	 */
	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	/**
	 * @return el fechaMIni
	 */
	public String getFechaMIni() {
		return fechaMIni;
	}

	/**
	 * @param fechaMIni
	 *            el fechaMIni a establecer
	 */
	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	/**
	 * @return el fechaSFin
	 */
	public String getFechaSFin() {
		return fechaSFin;
	}

	/**
	 * @param fechaSFin
	 *            el fechaSFin a establecer
	 */
	public void setFechaSFin(String fechaSFin) {
		this.fechaSFin = fechaSFin;
	}

	/**
	 * @return el fechaSIni
	 */
	public String getFechaSIni() {
		return fechaSIni;
	}

	/**
	 * @param fechaSIni
	 *            el fechaSIni a establecer
	 */
	public void setFechaSIni(String fechaSIni) {
		this.fechaSIni = fechaSIni;
	}

	/**
	 * @return el fondo
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @param fondo
	 *            el fondo a establecer
	 */
	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	/**
	 * @return el numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            el numero a establecer
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return el numeroComp
	 */
	public String getNumeroComp() {
		return numeroComp;
	}

	/**
	 * @param numeroComp
	 *            el numeroComp a establecer
	 */
	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	/**
	 * @return el texto
	 */
	public String getTexto() {
		return texto;
	}

	/**
	 * @param texto
	 *            el texto a establecer
	 */
	public void setTexto(String texto) {
		this.texto = texto;
	}

	/**
	 * @return el tipoMedida
	 */
	public String getTipoMedida() {
		return tipoMedida;
	}

	/**
	 * @param tipoMedida
	 *            el tipoMedida a establecer
	 */
	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	/**
	 * @return el unidadMedida
	 */
	public String getUnidadMedida() {
		return unidadMedida;
	}

	/**
	 * @param unidadMedida
	 *            el unidadMedida a establecer
	 */
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	/**
	 * @return el usaCache
	 */
	public boolean isUsaCache() {
		return usaCache;
	}

	/**
	 * @param usaCache
	 *            el usaCache a establecer
	 */
	public void setUsaCache(boolean usaCache) {
		this.usaCache = usaCache;
	}

	/**
	 * @return el fechaA
	 */
	public String getFechaA() {
		return fechaA;
	}

	/**
	 * @param fechaA
	 *            el fechaA a establecer
	 */
	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	/**
	 * @return el fechaComp
	 */
	public String getFechaComp() {
		return fechaComp;
	}

	/**
	 * @param fechaComp
	 *            el fechaComp a establecer
	 */
	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	/**
	 * @return el fechaD
	 */
	public String getFechaD() {
		return fechaD;
	}

	/**
	 * @param fechaD
	 *            el fechaD a establecer
	 */
	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	/**
	 * @return el fechaFinA
	 */
	public String getFechaFinA() {
		return fechaFinA;
	}

	/**
	 * @param fechaFinA
	 *            el fechaFinA a establecer
	 */
	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	/**
	 * @return el fechaFinD
	 */
	public String getFechaFinD() {
		return fechaFinD;
	}

	/**
	 * @param fechaFinD
	 *            el fechaFinD a establecer
	 */
	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	/**
	 * @return el fechaFinFormato
	 */
	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	/**
	 * @param fechaFinFormato
	 *            el fechaFinFormato a establecer
	 */
	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	/**
	 * @return el fechaFinM
	 */
	public String getFechaFinM() {
		return fechaFinM;
	}

	/**
	 * @param fechaFinM
	 *            el fechaFinM a establecer
	 */
	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	/**
	 * @return el fechaFinS
	 */
	public String getFechaFinS() {
		return fechaFinS;
	}

	/**
	 * @param fechaFinS
	 *            el fechaFinS a establecer
	 */
	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	/**
	 * @return el fechaFormato
	 */
	public String getFechaFormato() {
		return fechaFormato;
	}

	/**
	 * @param fechaFormato
	 *            el fechaFormato a establecer
	 */
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	/**
	 * @return el fechaIniA
	 */
	public String getFechaIniA() {
		return fechaIniA;
	}

	/**
	 * @param fechaIniA
	 *            el fechaIniA a establecer
	 */
	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	/**
	 * @return el fechaIniD
	 */
	public String getFechaIniD() {
		return fechaIniD;
	}

	/**
	 * @param fechaIniD
	 *            el fechaIniD a establecer
	 */
	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	/**
	 * @return el fechaIniFormato
	 */
	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	/**
	 * @param fechaIniFormato
	 *            el fechaIniFormato a establecer
	 */
	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	/**
	 * @return el fechaIniM
	 */
	public String getFechaIniM() {
		return fechaIniM;
	}

	/**
	 * @param fechaIniM
	 *            el fechaIniM a establecer
	 */
	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	/**
	 * @return el fechaIniS
	 */
	public String getFechaIniS() {
		return fechaIniS;
	}

	/**
	 * @param fechaIniS
	 *            el fechaIniS a establecer
	 */
	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	/**
	 * @return el fechaM
	 */
	public String getFechaM() {
		return fechaM;
	}

	/**
	 * @param fechaM
	 *            el fechaM a establecer
	 */
	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	/**
	 * @return el fechaS
	 */
	public String getFechaS() {
		return fechaS;
	}

	/**
	 * @param fechaS
	 *            el fechaS a establecer
	 */
	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	/**
	 * @return el numeroExpediente
	 */
	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	/**
	 * @param numeroExpediente
	 *            el numeroExpediente a establecer
	 */
	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	/**
	 * @return el productores
	 */
	public String getProductores() {
		return productores;
	}

	/**
	 * @param productores
	 *            el productores a establecer
	 */
	public void setProductores(String productores) {
		this.productores = productores;
	}

	/**
	 * @return el calificadorFinal
	 */
	public String getCalificadorFinal() {
		return calificadorFinal;
	}

	/**
	 * @param calificadorFinal
	 *            el calificadorFinal a establecer
	 */
	public void setCalificadorFinal(String calificadorFinal) {
		this.calificadorFinal = calificadorFinal;
	}

	/**
	 * @return el calificadorInicial
	 */
	public String getCalificadorInicial() {
		return calificadorInicial;
	}

	/**
	 * @param calificadorInicial
	 *            el calificadorInicial a establecer
	 */
	public void setCalificadorInicial(String calificadorInicial) {
		this.calificadorInicial = calificadorInicial;
	}

	/**
	 * @return el procedimiento
	 */
	public String getProcedimiento() {
		return procedimiento;
	}

	/**
	 * @param procedimiento
	 *            el procedimiento a establecer
	 */
	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	/**
	 * @return el rango
	 */
	public String getRango() {
		return rango;
	}

	/**
	 * @param rango
	 *            el rango a establecer
	 */
	public void setRango(String rango) {
		this.rango = rango;
	}

	/**
	 * @return el formatoFecha1
	 */
	public String[] getFormatoFecha1() {
		return formatoFecha1;
	}

	/**
	 * @param formatoFecha1
	 *            el formatoFecha1 a establecer
	 */
	public void setFormatoFecha1(String[] formatoFecha1) {
		this.formatoFecha1 = formatoFecha1;
	}

	/**
	 * @return el formatoFecha2
	 */
	public String[] getFormatoFecha2() {
		return formatoFecha2;
	}

	/**
	 * @param formatoFecha2
	 *            el formatoFecha2 a establecer
	 */
	public void setFormatoFecha2(String[] formatoFecha2) {
		this.formatoFecha2 = formatoFecha2;
	}

	/**
	 * @return el valor1S
	 */
	public String[] getValor1S() {
		return valor1S;
	}

	/**
	 * @param valor1S
	 *            el valor1S a establecer
	 */
	public void setValor1S(String[] valor1S) {
		this.valor1S = valor1S;
	}

	/**
	 * @return el valor2S
	 */
	public String[] getValor2S() {
		return valor2S;
	}

	/**
	 * @param valor2S
	 *            el valor2S a establecer
	 */
	public void setValor2S(String[] valor2S) {
		this.valor2S = valor2S;
	}

	/**
	 * @return el contadorDetallesAvanzados
	 */
	public int getContadorDetallesAvanzados() {
		return contadorDetallesAvanzados;
	}

	/**
	 * @param contadorDetallesAvanzados
	 *            el contadorDetallesAvanzados a establecer
	 */
	public void setContadorDetallesAvanzados(int contadorDetallesAvanzados) {
		this.contadorDetallesAvanzados = contadorDetallesAvanzados;
	}

	/**
	 * @return el formatoFechaSel1
	 */
	public String[] getFormatoFechaSel1() {
		return formatoFechaSel1;
	}

	/**
	 * @param formatoFechaSel1
	 *            el formatoFechaSel1 a establecer
	 */
	public void setFormatoFechaSel1(String[] formatoFechaSel1) {
		this.formatoFechaSel1 = formatoFechaSel1;
	}

	/**
	 * @return el formatoFechaSel2
	 */
	public String[] getFormatoFechaSel2() {
		return formatoFechaSel2;
	}

	/**
	 * @param formatoFechaSel2
	 *            el formatoFechaSel2 a establecer
	 */
	public void setFormatoFechaSel2(String[] formatoFechaSel2) {
		this.formatoFechaSel2 = formatoFechaSel2;
	}

	/**
	 * @param selectedElem
	 *            el selectedElem a establecer
	 */
	public void setSelectedElem(String[] selectedElem) {
		this.selectedElem = selectedElem;
	}

	public boolean isMostrarSinPaginar() {
		return mostrarSinPaginar;
	}

	public void setMostrarSinPaginar(boolean mostrarSinPaginar) {
		this.mostrarSinPaginar = mostrarSinPaginar;
	}

	public String getContenidoFichero() {
		return contenidoFichero;
	}

	public void setContenidoFichero(String contenidoFichero) {
		this.contenidoFichero = contenidoFichero;
	}

	public String[] getGenericoFechaComp() {
		return genericoFechaComp;
	}

	public void setGenericoFechaComp(String[] genericoFechaComp) {
		this.genericoFechaComp = genericoFechaComp;
	}

	public String[] getGenericoFechaFormato() {
		return genericoFechaFormato;
	}

	public void setGenericoFechaFormato(String[] genericoFechaFormato) {
		this.genericoFechaFormato = genericoFechaFormato;
	}

	public String[] getGenericoFechaA() {
		return genericoFechaA;
	}

	public void setGenericoFechaA(String[] genericoFechaA) {
		this.genericoFechaA = genericoFechaA;
	}

	public String[] getGenericoFechaM() {
		return genericoFechaM;
	}

	public void setGenericoFechaM(String[] genericoFechaM) {
		this.genericoFechaM = genericoFechaM;
	}

	public String[] getGenericoFechaD() {
		return genericoFechaD;
	}

	public void setGenericoFechaD(String[] genericoFechaD) {
		this.genericoFechaD = genericoFechaD;
	}

	public String[] getGenericoFechaS() {
		return genericoFechaS;
	}

	public void setGenericoFechaS(String[] genericoFechaS) {
		this.genericoFechaS = genericoFechaS;
	}

	public String[] getGenericoFechaIniFormato() {
		return genericoFechaIniFormato;
	}

	public void setGenericoFechaIniFormato(String[] genericoFechaIniFormato) {
		this.genericoFechaIniFormato = genericoFechaIniFormato;
	}

	public String[] getGenericoFechaIniA() {
		return genericoFechaIniA;
	}

	public void setGenericoFechaIniA(String[] genericoFechaIniA) {
		this.genericoFechaIniA = genericoFechaIniA;
	}

	public String[] getGenericoFechaIniM() {
		return genericoFechaIniM;
	}

	public void setGenericoFechaIniM(String[] genericoFechaIniM) {
		this.genericoFechaIniM = genericoFechaIniM;
	}

	public String[] getGenericoFechaIniD() {
		return genericoFechaIniD;
	}

	public void setGenericoFechaIniD(String[] genericoFechaIniD) {
		this.genericoFechaIniD = genericoFechaIniD;
	}

	public String[] getGenericoFechaIniS() {
		return genericoFechaIniS;
	}

	public void setGenericoFechaIniS(String[] genericoFechaIniS) {
		this.genericoFechaIniS = genericoFechaIniS;
	}

	public String[] getGenericoFechaFinFormato() {
		return genericoFechaFinFormato;
	}

	public void setGenericoFechaFinFormato(String[] genericoFechaFinFormato) {
		this.genericoFechaFinFormato = genericoFechaFinFormato;
	}

	public String[] getGenericoFechaFinA() {
		return genericoFechaFinA;
	}

	public void setGenericoFechaFinA(String[] genericoFechaFinA) {
		this.genericoFechaFinA = genericoFechaFinA;
	}

	public String[] getGenericoFechaFinM() {
		return genericoFechaFinM;
	}

	public void setGenericoFechaFinM(String[] genericoFechaFinM) {
		this.genericoFechaFinM = genericoFechaFinM;
	}

	public String[] getGenericoFechaFinD() {
		return genericoFechaFinD;
	}

	public void setGenericoFechaFinD(String[] genericoFechaFinD) {
		this.genericoFechaFinD = genericoFechaFinD;
	}

	public String[] getGenericoFechaFinS() {
		return genericoFechaFinS;
	}

	public void setGenericoFechaFinS(String[] genericoFechaFinS) {
		this.genericoFechaFinS = genericoFechaFinS;
	}

	public String[] getGenericoFechaCalificador() {
		return genericoFechaCalificador;
	}

	public void setGenericoFechaCalificador(String[] genericoFechaCalificador) {
		this.genericoFechaCalificador = genericoFechaCalificador;
	}

	public String[] getGenericoTextoLargo() {
		return genericoTextoLargo;
	}

	public void setGenericoTextoLargo(String[] genericoTextoLargo) {
		this.genericoTextoLargo = genericoTextoLargo;
	}

	public String[] getGenericoTextoCorto() {
		return genericoTextoCorto;
	}

	public void setGenericoTextoCorto(String[] genericoTextoCorto) {
		this.genericoTextoCorto = genericoTextoCorto;
	}

	// public String[] getGenericoIdentificador() {
	// return genericoIdentificador;
	// }
	//
	//
	// public void setGenericoIdentificador(String[] genericoIdentificador) {
	// this.genericoIdentificador = genericoIdentificador;
	// }

	public String[] getGenericoIdFecha() {
		return genericoIdFecha;
	}

	public void setGenericoIdFecha(String[] genericoIdFecha) {
		this.genericoIdFecha = genericoIdFecha;
	}

	public String[] getGenericoIdTextoLargo() {
		return genericoIdTextoLargo;
	}

	public void setGenericoIdTextoLargo(String[] genericoIdTextoLargo) {
		this.genericoIdTextoLargo = genericoIdTextoLargo;
	}

	public String[] getGenericoIdTextoCorto() {
		return genericoIdTextoCorto;
	}

	public void setGenericoIdTextoCorto(String[] genericoIdTextoCorto) {
		this.genericoIdTextoCorto = genericoIdTextoCorto;
	}

	public String[] getGenericoCampoNumerico() {
		return genericoCampoNumerico;
	}

	public void setGenericoCampoNumerico(String[] genericoCampoNumerico) {
		this.genericoCampoNumerico = genericoCampoNumerico;
	}

	public String[] getGenericoIdCampoNumerico() {
		return genericoIdCampoNumerico;
	}

	public void setGenericoIdCampoNumerico(String[] genericoIdCampoNumerico) {
		this.genericoIdCampoNumerico = genericoIdCampoNumerico;
	}

	public String[] getGenericoOperadorCampoNumerico() {
		return genericoOperadorCampoNumerico;
	}

	public void setGenericoOperadorCampoNumerico(
			String[] genericoOperadorCampoNumerico) {
		this.genericoOperadorCampoNumerico = genericoOperadorCampoNumerico;
	}

	public String[] getGenericoCampoNumericoFin() {
		return genericoCampoNumericoFin;
	}

	public void setGenericoCampoNumericoFin(String[] genericoCampoNumericoFin) {
		this.genericoCampoNumericoFin = genericoCampoNumericoFin;
	}

	public String[] getGenericoEtiquetaFecha() {
		return genericoEtiquetaFecha;
	}

	public void setGenericoEtiquetaFecha(String[] genericoEtiquetaFecha) {
		this.genericoEtiquetaFecha = genericoEtiquetaFecha;
	}

	public String[] getGenericoEtiquetaTextoLargo() {
		return genericoEtiquetaTextoLargo;
	}

	public void setGenericoEtiquetaTextoLargo(
			String[] genericoEtiquetaTextoLargo) {
		this.genericoEtiquetaTextoLargo = genericoEtiquetaTextoLargo;
	}

	public String[] getGenericoEtiquetaTextoCorto() {
		return genericoEtiquetaTextoCorto;
	}

	public void setGenericoEtiquetaTextoCorto(
			String[] genericoEtiquetaTextoCorto) {
		this.genericoEtiquetaTextoCorto = genericoEtiquetaTextoCorto;
	}

	public String[] getGenericoEtiquetaCampoNumerico() {
		return genericoEtiquetaCampoNumerico;
	}

	public void setGenericoEtiquetaCampoNumerico(
			String[] genericoEtiquetaCampoNumerico) {
		this.genericoEtiquetaCampoNumerico = genericoEtiquetaCampoNumerico;
	}

	public String[] getGenericoOperadorTextoCorto() {
		return genericoOperadorTextoCorto;
	}

	public void setGenericoOperadorTextoCorto(
			String[] genericoOperadorTextoCorto) {
		this.genericoOperadorTextoCorto = genericoOperadorTextoCorto;
	}

	/**
	 * @return the activarNiveles
	 */
	public boolean isActivarNiveles() {
		return activarNiveles;
	}

	/**
	 * @param activarNiveles
	 *            the activarNiveles to set
	 */
	public void setActivarNiveles(boolean activarNiveles) {
		this.activarNiveles = activarNiveles;
	}

	/**
	 * @return the activarEstados
	 */
	public boolean isActivarEstados() {
		return activarEstados;
	}

	/**
	 * @param activarEstados
	 *            the activarEstados to set
	 */
	public void setActivarEstados(boolean activarEstados) {
		this.activarEstados = activarEstados;
	}

	public String getTipoReferencia() {
		return tipoReferencia;
	}

	public void setTipoReferencia(String tipoReferencia) {
		this.tipoReferencia = tipoReferencia;
	}

	public String[] getTiposReferencia() {
		return tiposReferencia;
	}

	public void setTiposReferencia(String[] tiposReferencia) {
		this.tiposReferencia = tiposReferencia;
	}

	public ArrayList getCamposRellenos() {
		return camposRellenos;
	}

	/**
	 * Comprueba que todos los arrays tienen la misma longitud.
	 *
	 * @return
	 */
	public boolean arraysCorrectos() {
		int longitud = booleano.length;
		if (longitud == 1)
			return true;
		if (longitud == abrirpar.length && longitud == booleano.length
				&& longitud == abrirpar.length && longitud == campo.length
				&& longitud == tipoCampo.length && longitud == operador.length
				&& longitud == cerrarpar.length
				&& longitud == formatoFecha1.length
				&& longitud == formatoFechaSel1.length
				&& longitud == valor1.length && longitud == valor1D.length
				&& longitud == valor1M.length && longitud == valor1A.length
				&& longitud == valor1S.length
				&& longitud == formatoFecha2.length
				&& longitud == formatoFechaSel2.length
				&& longitud == valor2.length && longitud == valor2D.length
				&& longitud == valor2M.length && longitud == valor2A.length
				&& longitud == valor2S.length) {

			return true;
		}
		return false;
	}

	/**
	 * @return el selectedElem
	 */
	public String[] getSelectedElem() {
		return selectedElem;
	}

	/**
	 * @return the postBack
	 */
	public String getPostBack() {
		return postBack;
	}

	/**
	 * @param postBack
	 *            the postBack to set
	 */
	public void setPostBack(String postBack) {
		this.postBack = postBack;
	}

	public void setPostBack(boolean postBack) {
		this.postBack = postBack ? Constants.TRUE_STRING : null;
	}

	public void setFormaReemplazo(String formaReemplazo) {
		this.formaReemplazo = formaReemplazo;
	}

	public String getFormaReemplazo() {
		return formaReemplazo;
	}

	public boolean isReemplazoValoresNulos() {
		return FormaReemplazo.isReemplazoValoresNulos(formaReemplazo);
	}

	public boolean isReemplazoValoresParciales() {
		return FormaReemplazo.isReemplazoValoresParciales(formaReemplazo);
	}

	public boolean isReemplazoValoresExactos() {
		return FormaReemplazo.isReemplazoValoresParciales(formaReemplazo);
	}

	public void setValorReemplazoParcial(String valorReemplazoParcial) {
		this.valorReemplazoParcial = valorReemplazoParcial;
	}

	public String getValorReemplazoParcial() {
		return valorReemplazoParcial;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getRef() {
		return ref;
	}

	public String getFormatoFecha4() {
		return formatoFecha4;
	}

	public void setFormatoFecha4(String formatoFecha4) {
		this.formatoFecha4 = formatoFecha4;
	}

	public String getValor4() {
		return valor4;
	}

	public void setValor4(String valor4) {
		this.valor4 = valor4;
	}

	public String getValor4D() {
		return valor4D;
	}

	public void setValor4D(String valor4D) {
		this.valor4D = valor4D;
	}

	public String getValor4M() {
		return valor4M;
	}

	public void setValor4M(String valor4M) {
		this.valor4M = valor4M;
	}

	public String getValor4A() {
		return valor4A;
	}

	public void setValor4A(String valor4A) {
		this.valor4A = valor4A;
	}

	public String getValor4S() {
		return valor4S;
	}

	public void setValor4S(String valor4S) {
		this.valor4S = valor4S;
	}

	public String getNombreDesc4() {
		return nombreDesc4;
	}

	public void setNombreDesc4(String nombreDesc4) {
		this.nombreDesc4 = nombreDesc4;
	}

	public String[] getBloqueos() {
		return bloqueos;
	}

	public void setBloqueos(String[] bloqueos) {
		this.bloqueos = bloqueos;
	}

	public boolean isActivarBloqueos() {
		return activarBloqueos;
	}

	public void setActivarBloqueos(boolean activarBloqueos) {
		this.activarBloqueos = activarBloqueos;
	}

	// REEMPLAZO

	private String[] nombreDesc1;
	private String nombreDesc2;
	private String nombreDesc3;

	private String campoCambio;
	private Integer tipoCampoCambio;

	private String formatoFecha3;
	private String valor3;
	private String valor3D;
	private String valor3M;
	private String valor3A;
	private String valor3S;
	private boolean reemplazoSimple;

	private String[] selectedIds = new String[0];

	public boolean isReemplazoSimple() {
		return reemplazoSimple;
	}

	public String getFormatoFecha3() {
		return formatoFecha3;
	}

	public void setFormatoFecha3(String formatoFecha3) {
		this.formatoFecha3 = formatoFecha3;
	}

	public String getValor3() {
		return valor3;
	}

	public void setValor3(String valor3) {
		this.valor3 = valor3;
	}

	public String getValor3A() {
		return valor3A;
	}

	public void setValor3A(String valor3A) {
		this.valor3A = valor3A;
	}

	public String getValor3D() {
		return valor3D;
	}

	public void setValor3D(String valor3D) {
		this.valor3D = valor3D;
	}

	public String getValor3M() {
		return valor3M;
	}

	public void setValor3M(String valor3M) {
		this.valor3M = valor3M;
	}

	public String getValor3S() {
		return valor3S;
	}

	public void setValor3S(String valor3S) {
		this.valor3S = valor3S;
	}

	public void setNombreDesc2(String nombreDesc2) {
		this.nombreDesc2 = nombreDesc2;
	}

	public String[] getNombreDesc1() {
		return nombreDesc1;
	}

	public void setNombreDesc1(String[] nombreDesc1) {
		this.nombreDesc1 = nombreDesc1;
	}

	public BusquedaElementosVO getBusquedaElementosVOReemplazo(
			Busqueda busqueda,
			PrecondicionesBusquedaFondosGenerica precondiciones) {
		BusquedaElementosVO vo = BusquedasHelper.getBusquedaElementosVO(
				busqueda, precondiciones, this);
		if (busqueda != null) {
			/* Condiciones avanzadas */
			if (busqueda
					.getMapEntrada()
					.containsKey(
							CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CONDICIONES_AVANZADAS)) {
				String[] listaValores1 = new String[1];
				String[] listaValores2 = new String[1];
				vo.setFormatoFecha1(getFormatoFecha1());
				vo.setFormatoFecha2(getFormatoFecha2());

				// for (int i = 0; i < listaValores1.length; i++){
				int i = 0;
				if (isReemplazoSimple()) {
					if (getTipoCampo()[i].intValue() == TipoCampo.FECHA_VALUE) {
						CustomDateRange range = CustomDateFormat.getDateRange(
								getOperador()[i], new CustomDate(
										getFormatoFecha1()[i], getValor1A()[i],
										getValor1M()[i], getValor1D()[i],
										getValor1S()[i]), new CustomDate(
										getFormatoFecha1()[i], getValor1A()[i],
										getValor1M()[i], getValor1D()[i],
										getValor1S()[i]), new CustomDate(
										getFormatoFecha1()[i], getValor1A()[i],
										getValor1M()[i], getValor1D()[i],
										getValor1S()[i]));

						// Si existe fecha Inicial se asigna
						if (range.getInitialDate() != null) {
							listaValores1[i] = CustomDateFormat.SDF_YYYYMMDD
									.format(range.getInitialDate());

							// Si además existe fecha Final, asignarla al
							// operador2
							if (range.getFinalDate() != null) {
								listaValores2[i] = CustomDateFormat.SDF_YYYYMMDD
										.format(range.getFinalDate());
							} else {
								listaValores2[i] = null;
							}
						} else {
							if (range.getFinalDate() != null) {
								listaValores1[i] = CustomDateFormat.SDF_YYYYMMDD
										.format(range.getFinalDate());
							} else {
								listaValores1[i] = null;
							}
						}
						vo.setValor2(listaValores2);
					} else if (getTipoCampo()[i].intValue() == TipoCampo.TEXTO_CORTO_VALUE
							|| getTipoCampo()[i].intValue() == TipoCampo.TEXTO_LARGO_VALUE) {
						if (isReemplazoValoresParciales()) {
							if (isReemplazoSimple()) {
								vo.setOperador(new String[] { Operadores.OPERADOR_QC });
							}
						}
						listaValores1[i] = getValor1()[i];
					} else {
						listaValores1[i] = getValor1()[i];
					}
					// }
					vo.setValor1(listaValores1);
				}

				String[] listaValores3 = new String[2];
				listaValores3[0] = null;
				listaValores3[1] = null;

				if (getTipoCampoCambio() != null) {
					if (getTipoCampoCambio().intValue() == TipoCampo.FECHA_VALUE) {
						// en la posicicion 0 el valor y en la 1 el formato
						CustomDate periodo = new CustomDate(getFormatoFecha3(),
								getValor3A(), getValor3M(), getValor3D(),
								getValor3S());
						listaValores3[0] = periodo.getValue();
						listaValores3[1] = getFormatoFecha3();
					} else {
						listaValores3[i] = getValor3();
						if (StringUtils.isEmpty(listaValores3[i])) {
							listaValores3[i] = getNombreDesc3();
						}
					}
					vo.setValor3(listaValores3);
				}
			}
			if (isReemplazoSimple()) {
				vo.setSelectedIds(null);
			} else {
				vo.setSelectedIds(getSelectedIds());
				if (isReemplazoValoresParciales()) {
					vo.setValorReemplazoParcial(getValorReemplazoParcial());
				}
			}
			vo.setTipoCampoCambio(getTipoCampoCambio());
			vo.setCampoCambio(getCampoCambio());

		}
		return vo;
	}

	public ActionErrors validateCampos(HttpServletRequest request,
			int tipoReemplazo) {
		if (tipoReemplazo == 1) {
			return validateCamposSimple(request);
		} else {
			return validateCamposAvanzada(request);
		}

	}

	private ActionErrors validateCamposAvanzada(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		errors = checkValorNuevo(request, errors);
		return errors;
	}

	/**
	 * Valida el formulario
	 *
	 * @param mapping
	 *            {@link ActionMapping} con los mapeos asociado.
	 * @param request
	 *            {@link HttpServletRequest}
	 */
	private ActionErrors validateCamposSimple(HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (getCampo().length == 1 && StringUtils.isEmpty(getCampoCambio())) {
			errors.add(
					ActionErrors.GLOBAL_MESSAGE,
					new ActionError(
							DescripcionConstants.ERROR_DESCRIPCION_REEEMPLAZO_SELECCION_CAMPO_OBLIGATORIA));
			return errors;
		}

		boolean buscandoValoresVacio = false;
		int i = 0;
		if (isReemplazoValoresNulos()) {
			if (getTipoCampo()[i].intValue() == 3) {
				if (StringUtils.isNotEmpty(getCampo()[i])
						&& StringUtils.isEmpty(getValor1D()[i])
						&& StringUtils.isEmpty(getValor1M()[i])
						&& StringUtils.isEmpty(getValor1A()[i])
						&& StringUtils.isEmpty(getValor1S()[i])) {
					buscandoValoresVacio = true;
				}
			} else {
				if (StringUtils.isNotEmpty(getCampo()[i])
						&& StringUtils.isEmpty(getValor1()[i])) {
					buscandoValoresVacio = true;
				}
			}
		}

		if (!buscandoValoresVacio) {
			// Validar fechas en la búsqueda avanzada
			// for (int i = 0; i < getTipoCampo().length; i++){
			// Comprobar que se ha introducido un valor
			if (getTipoCampo()[i].intValue() == TipoCampo.FECHA_VALUE) {
				if (StringUtils.isNotEmpty(getCampo()[i])
						&& StringUtils.isEmpty(getValor1D()[i])
						&& StringUtils.isEmpty(getValor1M()[i])
						&& StringUtils.isEmpty(getValor1A()[i])
						&& StringUtils.isEmpty(getValor1S()[i])) {
					// String fila = new Integer(i+1).toString();
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO));
				}

				// Comprobar la fecha1 es correcta
				try {
					CustomDate fecha1 = new CustomDate(getFormatoFecha1()[i],
							getValor1A()[i], getValor1M()[i], getValor1D()[i],
							getValor1S()[i]);

					if (!fecha1.validate()) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_DATE,
										Messages.getString(
												Constants.ETIQUETA_CONDICIONES_AVANZADAS,
												request.getLocale())));
					}
				} catch (Exception e) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											Constants.ETIQUETA_CONDICIONES_AVANZADAS,
											request.getLocale())));
				}

				// Comprobar la Fecha2 si el operador es rango (nunca va a
				// entrar por aqui)
				if (getOperador()[i]
						.equals(CustomDateFormat.DATE_OPERATOR_RANGE)) {
					// if()
					try {
						CustomDate fecha2 = new CustomDate(
								getFormatoFecha2()[i], getValor2A()[i],
								getValor2M()[i], getValor2D()[i],
								getValor2S()[i]);

						if (!fecha2.validate()) {
							errors.add(
									ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_DATE,
											Messages.getString(
													Constants.ETIQUETA_CONDICIONES_AVANZADAS,
													request.getLocale())));
						}
					} catch (Exception e) {
						errors.add(
								ActionErrors.GLOBAL_MESSAGE,
								new ActionError(
										Constants.ERROR_DATE,
										Messages.getString(
												Constants.ETIQUETA_CONDICIONES_AVANZADAS,
												request.getLocale())));
					}
				}
			} else {
				if (StringUtils.isNotEmpty(getCampo()[i])
						&& (getValor1().length == 0 || StringUtils
								.isEmpty(getValor1()[i]))) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO_SOLOUNO));
				}

				// if(getCampo()[i].intValue() != 0 && getValor2().length>0 ||
				// StringUtils.isEmpty(getValor1()[i])){
				// String fila = new Integer(i+1).toString();
				// errors.add(ActionErrors.GLOBAL_MESSAGE,
				// new
				// ActionError(Constants.ERROR_BUSQUEDA_AVANZADA_VALOR_OBLIGATORIO,fila
				// ));
				// }
			}
			// }
		}

		errors = checkValorNuevo(request, errors);

		// Validar los ámbitos
		if (!ArrayUtils.isEmpty(getIdObjetoAmbito())) {
			String[] codRefs = (String[]) ArrayUtils.clone(getIdObjetoAmbito());
			for (i = codRefs.length - 1; i >= 0; i--) {
				if (StringUtils.isBlank(codRefs[i]))
					codRefs = (String[]) ArrayUtils.remove(codRefs, i);
				else {
					for (int j = i - 1; j >= 0; j--) {
						if (codRefs[i].equals(codRefs[j])) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_REPETIDO,
											getNombreObjetoAmbito()[i]));
						} else if (StringUtils.indexOf(codRefs[i], codRefs[j]) == 0) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_CONTENIDO,
											getNombreObjetoAmbito()[i],
											getNombreObjetoAmbito()[j]));
						} else if (StringUtils.indexOf(codRefs[j], codRefs[i]) == 0) {
							errors.add(ActionErrors.GLOBAL_MESSAGE,
									new ActionError(
											Constants.ERROR_AMBITO_CONTENIDO,
											getNombreObjetoAmbito()[j],
											getNombreObjetoAmbito()[i]));
						}
					}
				}
			}
		}

		return errors;
	}

	private ActionErrors checkValorNuevo(HttpServletRequest request,
			ActionErrors errors) {
		if (StringUtils.isEmpty(getCampoCambio())
				|| (StringUtils.isNotEmpty(getCampoCambio()) && (StringUtils
						.isEmpty(getValor3())
						&& StringUtils.isEmpty(getValor3D())
						&& StringUtils.isEmpty(getValor3M())
						&& StringUtils.isEmpty(getValor3A()) && StringUtils
						.isEmpty(getValor3S())))) {
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
					Constants.ERROR_REEEMPLAZO_VALOR_NUEVO_OBLIGATORIO));
		}

		if (getTipoCampoCambio().intValue() == TipoCampo.FECHA_VALUE) {
			// Comprobar la Fecha3 -> fecha del reemplazo
			try {
				CustomDate fecha3 = new CustomDate(getFormatoFecha3(),
						getValor3A(), getValor3M(), getValor3D(), getValor3S());

				if (!fecha3.validate()) {
					errors.add(
							ActionErrors.GLOBAL_MESSAGE,
							new ActionError(
									Constants.ERROR_DATE,
									Messages.getString(
											Constants.ETIQUETA_CONDICIONES_AVANZADAS,
											request.getLocale())));
				}
			} catch (Exception e) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(
								Constants.ERROR_DATE,
								Messages.getString(
										Constants.ETIQUETA_CONDICIONES_AVANZADAS,
										request.getLocale())));
			}
		} else if (getTipoCampoCambio().intValue() == TipoCampo.NUMERICO_VALUE) {
			// hay que comprobar que el campo tenga solo numeros y que ademas
			// entre en el campo
			if (!StringUtils.isNumeric(getValor3())) {
				errors.add(
						ActionErrors.GLOBAL_MESSAGE,
						new ActionError(Constants.ERROR_CAMPO_NO_NUMERICO,
								Messages.getString(
										Constants.ERROR_REEEMPLAZO_VALOR_NUEVO,
										request.getLocale())));
			} else if (!IeciTdType.isLongDecimal(getValor3())) {
				errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionError(
						Constants.ERROR_REEEMPLAZO_VALOR_NUMERICO_FUERA_RANGO));
			}

		}
		return errors;
	}

	public void checkNulosAndResetValor1() {
		if (isReemplazoValoresNulos()) {
			for (int i = 0; i < getValor1().length; i++) {
				getValor1()[0] = "";
				if (i < getValor1A().length)
					getValor1A()[i] = "";
				if (i < getValor1M().length)
					getValor1M()[i] = "";
				if (i < getValor1D().length)
					getValor1D()[i] = "";
				if (i < getValor1S().length)
					getValor1S()[i] = "";
				if (i < getNombreDesc1().length)
					getNombreDesc1()[i] = "";
			}
		}
	}

	public String getCampoCambio() {
		return campoCambio;
	}

	public void setCampoCambio(String campoCambio) {
		this.campoCambio = campoCambio;
	}

	public String getNombreDesc2() {
		return nombreDesc2;
	}

	public Integer getTipoCampoCambio() {
		return tipoCampoCambio;
	}

	public void setTipoCampoCambio(Integer tipoCampoCambio) {
		this.tipoCampoCambio = tipoCampoCambio;
	}

	public String getNombreDesc3() {
		return nombreDesc3;
	}

	public void setNombreDesc3(String nombreDesc3) {
		this.nombreDesc3 = nombreDesc3;
	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getReemplazoSimple() {
		return reemplazoSimple ? "1" : "0";
	}

	public void setReemplazoSimple(boolean reemplazoSimple) {
		this.reemplazoSimple = reemplazoSimple;
	}

	public void setConservada(String conservada) {
		this.conservada = conservada;
	}

	public String getConservada() {
		return conservada;
	}

}