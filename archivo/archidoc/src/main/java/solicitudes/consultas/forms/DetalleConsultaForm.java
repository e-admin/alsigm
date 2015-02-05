package solicitudes.consultas.forms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import common.Constants;
import common.pagination.PageInfo;

import descripcion.FormaReemplazo;
import es.archigest.framework.web.action.ArchigestActionForm;
import fondos.forms.IBusquedaForm;

/**
 * @author Eleazar Vicente
 */
public class DetalleConsultaForm extends ArchigestActionForm implements
		IBusquedaForm {

	private static final long serialVersionUID = 1L;
	static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			Constants.FORMATO_FECHA);
	private String postBack = Constants.FALSE_STRING;
	private String detallesseleccionados[] = null;

	private String idsolicitud = null;
	private int tiposolicitud = 0;;
	private String idudoc = null;;
	private String titulo = null;
	private String signaturaudoc = null;
	private String expedienteudoc = null;
	private int estado = 0;
	private String festado = null;
	private String finicialuso = null;
	private String ffinaluso = null;
	private String motivorechazo = null;
	private String informacion = null;
	private String identificacion = null;
	private String observaciones = null;
	private String idMotivoRechazo = null;
	private String productores = null;
	private String numero = null;
	private String rango = null;
	private String calificador = null;
	private String[] niveles = new String[0];
	private String procedimiento = null;
	private String texto = null;
	private String[] idObjetoAmbito = new String[0];
	private String[] tipoObjetoAmbito = new String[0];
	private String codigoReferencia = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private String numeroComp = null;

	private String[] genericoIdFecha = new String[0];
	private String[] genericoEtiquetaFecha = new String[0];
	private String[] genericoOperadorTextoCorto = new String[0];
	private String[] genericoIdTextoLargo = new String[0];
	private String[] genericoTextoLargo = new String[0];
	private String[] genericoEtiquetaTextoLargo = new String[0];
	private String[] genericoIdTextoCorto = new String[0];
	private String[] genericoTextoCorto = new String[0];
	private String[] genericoEtiquetaTextoCorto = new String[0];
	private String[] genericoIdCampoNumerico = new String[0];
	private String[] genericoCampoNumerico = new String[0];
	private String[] genericoEtiquetaCampoNumerico = new String[0];
	private String[] genericoOperadorCampoNumerico = new String[0];
	private String[] genericoCampoNumericoFin = new String[0];

	// Nuevos campos para búsquedas
	private String signatura_like = null;
	private String signatura_buscar = null;
	private String signatura = null;
	private String numeroExpediente_like = null;
	private String expediente_buscar = null;
	private String numeroExpediente = null;
	private String fondo = null;
	private String codigoRelacion = null;
	private String codigo = null;

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

	private String calificadorInicial = null;
	private String calificadorFinal = null;

	private String[] estados = new String[0];

	// Nuevos campos necesarios para incluir la búsqueda avanzada
	private String[] nombreObjetoAmbito = new String[0];
	private String[] codRefObjetoAmbito = new String[0];
	private String idFicha = null;
	private boolean activarEstados = false;
	private String contenidoFichero = null;
	private boolean activarNiveles = false;
	private String tipoElemento = null;
	private String descriptores = null;
	private String tipoBusqueda = null;
	private boolean usaCache = false;

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

	private String[] bloqueos = new String[0];
	private boolean activarBloqueos = false;

	public boolean isActivarBloqueos() {
		return activarBloqueos;
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

	/**
	 * @return Returns the dateFormat.
	 */
	public static SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * @return Returns the codfondo.
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param codfondo
	 *            The codfondo to set.
	 */
	public void setTitulo(String codfondo) {
		this.titulo = codfondo;
	}

	/**
	 * @return Returns the estado.
	 */
	public int getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            The estado to set.
	 */
	public void setEstado(int estado) {
		this.estado = estado;
	}

	/**
	 * @return Returns the expedienteudoc.
	 */
	public String getExpedienteudoc() {
		return expedienteudoc;
	}

	/**
	 * @param expedienteudoc
	 *            The expedienteudoc to set.
	 */
	public void setExpedienteudoc(String expedienteudoc) {
		this.expedienteudoc = expedienteudoc;
	}

	/**
	 * @return Returns the festado.
	 */
	public String getFestado() {
		return festado;
	}

	/**
	 * @param festado
	 *            The festado to set.
	 */
	public void setFestado(String festado) {
		this.festado = festado;
	}

	/**
	 * @return Returns the ffinaluso.
	 */
	public String getFfinaluso() {
		return ffinaluso;
	}

	/**
	 * @param ffinaluso
	 *            The ffinaluso to set.
	 */
	public void setFfinaluso(String ffinaluso) {
		this.ffinaluso = ffinaluso;
	}

	/**
	 * @return Returns the finicialuso.
	 */
	public String getFinicialuso() {
		return finicialuso;
	}

	/**
	 * @param finicialuso
	 *            The finicialuso to set.
	 */
	public void setFinicialuso(String finicialuso) {
		this.finicialuso = finicialuso;
	}

	/**
	 * @return Returns the idsolicitud.
	 */
	public String getIdsolicitud() {
		return idsolicitud;
	}

	/**
	 * @param idsolicitud
	 *            The idsolicitud to set.
	 */
	public void setIdsolicitud(String idsolicitud) {
		this.idsolicitud = idsolicitud;
	}

	/**
	 * @return Returns the idudoc.
	 */
	public String getIdudoc() {
		return idudoc;
	}

	/**
	 * @param idudoc
	 *            The idudoc to set.
	 */
	public void setIdudoc(String idudoc) {
		this.idudoc = idudoc;
	}

	/**
	 * @return Returns the informacion.
	 */
	public String getInformacion() {
		return informacion;
	}

	/**
	 * @param informacion
	 *            The informacion to set.
	 */
	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}

	/**
	 * @return Returns the motivorechazo.
	 */
	public String getMotivorechazo() {
		return motivorechazo;
	}

	/**
	 * @param motivorechazo
	 *            The motivorechazo to set.
	 */
	public void setMotivorechazo(String motivorechazo) {
		this.motivorechazo = motivorechazo;
	}

	/**
	 * @return Returns the signaturaudoc.
	 */
	public String getSignaturaudoc() {
		return signaturaudoc;
	}

	/**
	 * @param signaturaudoc
	 *            The signaturaudoc to set.
	 */
	public void setSignaturaudoc(String signaturaudoc) {
		this.signaturaudoc = signaturaudoc;
	}

	/**
	 * @return Returns the tiposolicitud.
	 */
	public int getTiposolicitud() {
		return tiposolicitud;
	}

	/**
	 * @param tiposolicitud
	 *            The tiposolicitud to set.
	 */
	public void setTiposolicitud(int tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}

	/**
	 * @return Returns the detallesseleccionados.
	 */
	public String[] getDetallesseleccionados() {
		return detallesseleccionados;
	}

	/**
	 * @param detallesseleccionados
	 *            The detallesseleccionados to set.
	 */
	public void setDetallesseleccionados(String[] detallesseleccionados) {
		this.detallesseleccionados = detallesseleccionados;
	}

	/**
	 * @return Returns the identificacion.
	 */
	public String getIdentificacion() {
		return identificacion;
	}

	/**
	 * @param identificacion
	 *            The identificacion to set.
	 */
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getCalificadorFinal() {
		return calificadorFinal;
	}

	public void setCalificadorFinal(String calificadorFinal) {
		this.calificadorFinal = calificadorFinal;
	}

	public String getCalificadorInicial() {
		return calificadorInicial;
	}

	public void setCalificadorInicial(String calificadorInicial) {
		this.calificadorInicial = calificadorInicial;
	}

	public ArrayList getCamposRellenos() {
		return camposRellenos;
	}

	public void setCamposRellenos(ArrayList camposRellenos) {
		this.camposRellenos = camposRellenos;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCodigoRelacion() {
		return codigoRelacion;
	}

	public void setCodigoRelacion(String codigoRelacion) {
		this.codigoRelacion = codigoRelacion;
	}

	public String[] getEstados() {
		return estados;
	}

	public void setEstados(String[] estados) {
		this.estados = estados;
	}

	public String getExpediente_buscar() {
		return expediente_buscar;
	}

	public void setExpediente_buscar(String expediente_buscar) {
		this.expediente_buscar = expediente_buscar;
	}

	public String getNumeroExpediente_like() {
		return numeroExpediente_like;
	}

	public void setNumeroExpediente_like(String numeroExpediente_like) {
		this.numeroExpediente_like = numeroExpediente_like;
	}

	public String getFechaAFin() {
		return fechaAFin;
	}

	public void setFechaAFin(String fechaAFin) {
		this.fechaAFin = fechaAFin;
	}

	public String getFechaAIni() {
		return fechaAIni;
	}

	public void setFechaAIni(String fechaAIni) {
		this.fechaAIni = fechaAIni;
	}

	public String getFechaCompFin() {
		return fechaCompFin;
	}

	public void setFechaCompFin(String fechaCompFin) {
		this.fechaCompFin = fechaCompFin;
	}

	public String getFechaCompIni() {
		return fechaCompIni;
	}

	public void setFechaCompIni(String fechaCompIni) {
		this.fechaCompIni = fechaCompIni;
	}

	public String getFechaDFin() {
		return fechaDFin;
	}

	public void setFechaDFin(String fechaDFin) {
		this.fechaDFin = fechaDFin;
	}

	public String getFechaDIni() {
		return fechaDIni;
	}

	public void setFechaDIni(String fechaDIni) {
		this.fechaDIni = fechaDIni;
	}

	public String getFechaFinAFin() {
		return fechaFinAFin;
	}

	public void setFechaFinAFin(String fechaFinAFin) {
		this.fechaFinAFin = fechaFinAFin;
	}

	public String getFechaFinAIni() {
		return fechaFinAIni;
	}

	public void setFechaFinAIni(String fechaFinAIni) {
		this.fechaFinAIni = fechaFinAIni;
	}

	public String getFechaFinDFin() {
		return fechaFinDFin;
	}

	public void setFechaFinDFin(String fechaFinDFin) {
		this.fechaFinDFin = fechaFinDFin;
	}

	public String getFechaFinDIni() {
		return fechaFinDIni;
	}

	public void setFechaFinDIni(String fechaFinDIni) {
		this.fechaFinDIni = fechaFinDIni;
	}

	public String getFechaFinFormatoFin() {
		return fechaFinFormatoFin;
	}

	public void setFechaFinFormatoFin(String fechaFinFormatoFin) {
		this.fechaFinFormatoFin = fechaFinFormatoFin;
	}

	public String getFechaFinFormatoIni() {
		return fechaFinFormatoIni;
	}

	public void setFechaFinFormatoIni(String fechaFinFormatoIni) {
		this.fechaFinFormatoIni = fechaFinFormatoIni;
	}

	public String getFechaFinMFin() {
		return fechaFinMFin;
	}

	public void setFechaFinMFin(String fechaFinMFin) {
		this.fechaFinMFin = fechaFinMFin;
	}

	public String getFechaFinMIni() {
		return fechaFinMIni;
	}

	public void setFechaFinMIni(String fechaFinMIni) {
		this.fechaFinMIni = fechaFinMIni;
	}

	public String getFechaFinSFin() {
		return fechaFinSFin;
	}

	public void setFechaFinSFin(String fechaFinSFin) {
		this.fechaFinSFin = fechaFinSFin;
	}

	public String getFechaFinSIni() {
		return fechaFinSIni;
	}

	public void setFechaFinSIni(String fechaFinSIni) {
		this.fechaFinSIni = fechaFinSIni;
	}

	public String getFechaFormatoFin() {
		return fechaFormatoFin;
	}

	public void setFechaFormatoFin(String fechaFormatoFin) {
		this.fechaFormatoFin = fechaFormatoFin;
	}

	public String getFechaFormatoIni() {
		return fechaFormatoIni;
	}

	public void setFechaFormatoIni(String fechaFormatoIni) {
		this.fechaFormatoIni = fechaFormatoIni;
	}

	public String getFechaIniAFin() {
		return fechaIniAFin;
	}

	public void setFechaIniAFin(String fechaIniAFin) {
		this.fechaIniAFin = fechaIniAFin;
	}

	public String getFechaIniAIni() {
		return fechaIniAIni;
	}

	public void setFechaIniAIni(String fechaIniAIni) {
		this.fechaIniAIni = fechaIniAIni;
	}

	public String getFechaIniDFin() {
		return fechaIniDFin;
	}

	public void setFechaIniDFin(String fechaIniDFin) {
		this.fechaIniDFin = fechaIniDFin;
	}

	public String getFechaIniDIni() {
		return fechaIniDIni;
	}

	public void setFechaIniDIni(String fechaIniDIni) {
		this.fechaIniDIni = fechaIniDIni;
	}

	public String getFechaIniFormatoFin() {
		return fechaIniFormatoFin;
	}

	public void setFechaIniFormatoFin(String fechaIniFormatoFin) {
		this.fechaIniFormatoFin = fechaIniFormatoFin;
	}

	public String getFechaIniFormatoIni() {
		return fechaIniFormatoIni;
	}

	public void setFechaIniFormatoIni(String fechaIniFormatoIni) {
		this.fechaIniFormatoIni = fechaIniFormatoIni;
	}

	public String getFechaIniMFin() {
		return fechaIniMFin;
	}

	public void setFechaIniMFin(String fechaIniMFin) {
		this.fechaIniMFin = fechaIniMFin;
	}

	public String getFechaIniMIni() {
		return fechaIniMIni;
	}

	public void setFechaIniMIni(String fechaIniMIni) {
		this.fechaIniMIni = fechaIniMIni;
	}

	public String getFechaIniSFin() {
		return fechaIniSFin;
	}

	public void setFechaIniSFin(String fechaIniSFin) {
		this.fechaIniSFin = fechaIniSFin;
	}

	public String getFechaIniSIni() {
		return fechaIniSIni;
	}

	public void setFechaIniSIni(String fechaIniSIni) {
		this.fechaIniSIni = fechaIniSIni;
	}

	public String getFechaMFin() {
		return fechaMFin;
	}

	public void setFechaMFin(String fechaMFin) {
		this.fechaMFin = fechaMFin;
	}

	public String getFechaMIni() {
		return fechaMIni;
	}

	public void setFechaMIni(String fechaMIni) {
		this.fechaMIni = fechaMIni;
	}

	public String getFechaSFin() {
		return fechaSFin;
	}

	public void setFechaSFin(String fechaSFin) {
		this.fechaSFin = fechaSFin;
	}

	public String getFechaSIni() {
		return fechaSIni;
	}

	public void setFechaSIni(String fechaSIni) {
		this.fechaSIni = fechaSIni;
	}

	public String getFondo() {
		return fondo;
	}

	public void setFondo(String fondo) {
		this.fondo = fondo;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getSignatura_buscar() {
		return signatura_buscar;
	}

	public void setSignatura_buscar(String signatura_buscar) {
		this.signatura_buscar = signatura_buscar;
	}

	public String getSignatura_like() {
		return signatura_like;
	}

	public void setSignatura_like(String signatura_like) {
		this.signatura_like = signatura_like;
	}

	public String[] getAbrirpar() {
		return abrirpar;
	}

	public void setAbrirpar(String[] abrirpar) {
		this.abrirpar = abrirpar;
	}

	public String[] getBooleano() {
		return booleano;
	}

	public void setBooleano(String[] booleano) {
		this.booleano = booleano;
	}

	public String[] getCampo() {
		return campo;
	}

	public void setCampo(String[] campo) {
		this.campo = campo;
	}

	public String[] getCerrarpar() {
		return cerrarpar;
	}

	public void setCerrarpar(String[] cerrarpar) {
		this.cerrarpar = cerrarpar;
	}

	public String[] getCodRefObjetoAmbito() {
		return codRefObjetoAmbito;
	}

	public void setCodRefObjetoAmbito(String[] codRefObjetoAmbito) {
		this.codRefObjetoAmbito = codRefObjetoAmbito;
	}

	public int getContadorDetallesAvanzados() {
		return contadorDetallesAvanzados;
	}

	public void setContadorDetallesAvanzados(int contadorDetallesAvanzados) {
		this.contadorDetallesAvanzados = contadorDetallesAvanzados;
	}

	public String[] getFormatoFecha1() {
		return formatoFecha1;
	}

	public void setFormatoFecha1(String[] formatoFecha1) {
		this.formatoFecha1 = formatoFecha1;
	}

	public String[] getFormatoFecha2() {
		return formatoFecha2;
	}

	public void setFormatoFecha2(String[] formatoFecha2) {
		this.formatoFecha2 = formatoFecha2;
	}

	public String[] getFormatoFechaSel1() {
		return formatoFechaSel1;
	}

	public void setFormatoFechaSel1(String[] formatoFechaSel1) {
		this.formatoFechaSel1 = formatoFechaSel1;
	}

	public String[] getFormatoFechaSel2() {
		return formatoFechaSel2;
	}

	public void setFormatoFechaSel2(String[] formatoFechaSel2) {
		this.formatoFechaSel2 = formatoFechaSel2;
	}

	public String getIdFicha() {
		return idFicha;
	}

	public void setIdFicha(String idFicha) {
		this.idFicha = idFicha;
	}

	public String[] getNombreObjetoAmbito() {
		return nombreObjetoAmbito;
	}

	public void setNombreObjetoAmbito(String[] nombreObjetoAmbito) {
		this.nombreObjetoAmbito = nombreObjetoAmbito;
	}

	public String[] getOperador() {
		return operador;
	}

	public void setOperador(String[] operador) {
		this.operador = operador;
	}

	public Integer[] getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(Integer[] tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String[] getValor1() {
		return valor1;
	}

	public void setValor1(String[] valor1) {
		this.valor1 = valor1;
	}

	public String[] getValor1A() {
		return valor1A;
	}

	public void setValor1A(String[] valor1A) {
		this.valor1A = valor1A;
	}

	public String[] getValor1D() {
		return valor1D;
	}

	public void setValor1D(String[] valor1D) {
		this.valor1D = valor1D;
	}

	public String[] getValor1M() {
		return valor1M;
	}

	public void setValor1M(String[] valor1M) {
		this.valor1M = valor1M;
	}

	public String[] getValor1S() {
		return valor1S;
	}

	public void setValor1S(String[] valor1S) {
		this.valor1S = valor1S;
	}

	public String[] getValor2() {
		return valor2;
	}

	public void setValor2(String[] valor2) {
		this.valor2 = valor2;
	}

	public String[] getValor2A() {
		return valor2A;
	}

	public void setValor2A(String[] valor2A) {
		this.valor2A = valor2A;
	}

	public String[] getValor2D() {
		return valor2D;
	}

	public void setValor2D(String[] valor2D) {
		this.valor2D = valor2D;
	}

	public String[] getValor2M() {
		return valor2M;
	}

	public void setValor2M(String[] valor2M) {
		this.valor2M = valor2M;
	}

	public String[] getValor2S() {
		return valor2S;
	}

	public void setValor2S(String[] valor2S) {
		this.valor2S = valor2S;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public String getIdMotivoRechazo() {
		return idMotivoRechazo;
	}

	public void setIdMotivoRechazo(String idMotivoRechazo) {
		this.idMotivoRechazo = idMotivoRechazo;
	}

	public String getProductores() {
		return productores;
	}

	public void setProductores(String productores) {
		this.productores = productores;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

	public String getCalificador() {
		return calificador;
	}

	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	public String[] getNiveles() {
		return niveles;
	}

	public void setNiveles(String[] niveles) {
		this.niveles = niveles;
	}

	public String[] getGenericoIdFecha() {
		return genericoIdFecha;
	}

	public void setGenericoIdFecha(String[] genericoIdFecha) {
		this.genericoIdFecha = genericoIdFecha;
	}

	public String[] getGenericoEtiquetaFecha() {
		return genericoEtiquetaFecha;
	}

	public void setGenericoEtiquetaFecha(String[] genericoEtiquetaFecha) {
		this.genericoEtiquetaFecha = genericoEtiquetaFecha;
	}

	public String[] getGenericoOperadorTextoCorto() {
		return genericoOperadorTextoCorto;
	}

	public void setGenericoOperadorTextoCorto(
			String[] genericoOperadorTextoCorto) {
		this.genericoOperadorTextoCorto = genericoOperadorTextoCorto;
	}

	public String[] getGenericoIdTextoLargo() {
		return genericoIdTextoLargo;
	}

	public void setGenericoIdTextoLargo(String[] genericoIdTextoLargo) {
		this.genericoIdTextoLargo = genericoIdTextoLargo;
	}

	public String[] getGenericoTextoLargo() {
		return genericoTextoLargo;
	}

	public void setGenericoTextoLargo(String[] genericoTextoLargo) {
		this.genericoTextoLargo = genericoTextoLargo;
	}

	public String[] getGenericoEtiquetaTextoLargo() {
		return genericoEtiquetaTextoLargo;
	}

	public void setGenericoEtiquetaTextoLargo(
			String[] genericoEtiquetaTextoLargo) {
		this.genericoEtiquetaTextoLargo = genericoEtiquetaTextoLargo;
	}

	public String[] getGenericoIdTextoCorto() {
		return genericoIdTextoCorto;
	}

	public void setGenericoIdTextoCorto(String[] genericoIdTextoCorto) {
		this.genericoIdTextoCorto = genericoIdTextoCorto;
	}

	public String[] getGenericoTextoCorto() {
		return genericoTextoCorto;
	}

	public void setGenericoTextoCorto(String[] genericoTextoCorto) {
		this.genericoTextoCorto = genericoTextoCorto;
	}

	public String[] getGenericoEtiquetaTextoCorto() {
		return genericoEtiquetaTextoCorto;
	}

	public void setGenericoEtiquetaTextoCorto(
			String[] genericoEtiquetaTextoCorto) {
		this.genericoEtiquetaTextoCorto = genericoEtiquetaTextoCorto;
	}

	public String[] getGenericoIdCampoNumerico() {
		return genericoIdCampoNumerico;
	}

	public void setGenericoIdCampoNumerico(String[] genericoIdCampoNumerico) {
		this.genericoIdCampoNumerico = genericoIdCampoNumerico;
	}

	public String[] getGenericoCampoNumerico() {
		return genericoCampoNumerico;
	}

	public void setGenericoCampoNumerico(String[] genericoCampoNumerico) {
		this.genericoCampoNumerico = genericoCampoNumerico;
	}

	public String[] getGenericoEtiquetaCampoNumerico() {
		return genericoEtiquetaCampoNumerico;
	}

	public void setGenericoEtiquetaCampoNumerico(
			String[] genericoEtiquetaCampoNumerico) {
		this.genericoEtiquetaCampoNumerico = genericoEtiquetaCampoNumerico;
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

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getProcedimiento() {
		return procedimiento;
	}

	public void setProcedimiento(String procedimiento) {
		this.procedimiento = procedimiento;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public String[] getIdObjetoAmbito() {
		return idObjetoAmbito;
	}

	public void setIdObjetoAmbito(String[] idObjetoAmbito) {
		this.idObjetoAmbito = idObjetoAmbito;
	}

	public String[] getTipoObjetoAmbito() {
		return tipoObjetoAmbito;
	}

	public void setTipoObjetoAmbito(String[] tipoObjetoAmbito) {
		this.tipoObjetoAmbito = tipoObjetoAmbito;
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

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public String getTipoMedida() {
		return tipoMedida;
	}

	public void setTipoMedida(String tipoMedida) {
		this.tipoMedida = tipoMedida;
	}

	public String getUnidadMedida() {
		return unidadMedida;
	}

	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

	public String getNumeroComp() {
		return numeroComp;
	}

	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	public boolean isActivarEstados() {
		return activarEstados;
	}

	public void setActivarEstados(boolean activarEstados) {
		this.activarEstados = activarEstados;
	}

	public String[] getNombreDesc() {
		return nombreDesc;
	}

	public void setNombreDesc(String[] nombreDesc) {
		this.nombreDesc = nombreDesc;
	}

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public String[] getSelectedElem() {
		return selectedElem;
	}

	public void setSelectedElem(String[] selectedElem) {
		this.selectedElem = selectedElem;
	}

	public boolean isMostrarSinPaginar() {
		return mostrarSinPaginar;
	}

	public void setMostrarSinPaginar(boolean mostrarSinPaginar) {
		this.mostrarSinPaginar = mostrarSinPaginar;
	}

	public String getFormaReemplazo() {
		return formaReemplazo;
	}

	public void setFormaReemplazo(String formaReemplazo) {
		this.formaReemplazo = formaReemplazo;
	}

	public String getValorReemplazoParcial() {
		return valorReemplazoParcial;
	}

	public void setValorReemplazoParcial(String valorReemplazoParcial) {
		this.valorReemplazoParcial = valorReemplazoParcial;
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

	public void setValor4D(String valor4d) {
		valor4D = valor4d;
	}

	public String getValor4M() {
		return valor4M;
	}

	public void setValor4M(String valor4m) {
		valor4M = valor4m;
	}

	public String getValor4A() {
		return valor4A;
	}

	public void setValor4A(String valor4a) {
		valor4A = valor4a;
	}

	public String getValor4S() {
		return valor4S;
	}

	public void setValor4S(String valor4s) {
		valor4S = valor4s;
	}

	public String getNombreDesc4() {
		return nombreDesc4;
	}

	public void setNombreDesc4(String nombreDesc4) {
		this.nombreDesc4 = nombreDesc4;
	}

	public String getFechaComp() {
		return fechaComp;
	}

	public void setFechaComp(String fechaComp) {
		this.fechaComp = fechaComp;
	}

	public String getFechaFormato() {
		return fechaFormato;
	}

	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}

	public String getFechaA() {
		return fechaA;
	}

	public void setFechaA(String fechaA) {
		this.fechaA = fechaA;
	}

	public String getFechaM() {
		return fechaM;
	}

	public void setFechaM(String fechaM) {
		this.fechaM = fechaM;
	}

	public String getFechaD() {
		return fechaD;
	}

	public void setFechaD(String fechaD) {
		this.fechaD = fechaD;
	}

	public String getFechaS() {
		return fechaS;
	}

	public void setFechaS(String fechaS) {
		this.fechaS = fechaS;
	}

	public String getFechaIniFormato() {
		return fechaIniFormato;
	}

	public void setFechaIniFormato(String fechaIniFormato) {
		this.fechaIniFormato = fechaIniFormato;
	}

	public String getFechaIniA() {
		return fechaIniA;
	}

	public void setFechaIniA(String fechaIniA) {
		this.fechaIniA = fechaIniA;
	}

	public String getFechaIniM() {
		return fechaIniM;
	}

	public void setFechaIniM(String fechaIniM) {
		this.fechaIniM = fechaIniM;
	}

	public String getFechaIniD() {
		return fechaIniD;
	}

	public void setFechaIniD(String fechaIniD) {
		this.fechaIniD = fechaIniD;
	}

	public String getFechaIniS() {
		return fechaIniS;
	}

	public void setFechaIniS(String fechaIniS) {
		this.fechaIniS = fechaIniS;
	}

	public String getFechaFinFormato() {
		return fechaFinFormato;
	}

	public void setFechaFinFormato(String fechaFinFormato) {
		this.fechaFinFormato = fechaFinFormato;
	}

	public String getFechaFinA() {
		return fechaFinA;
	}

	public void setFechaFinA(String fechaFinA) {
		this.fechaFinA = fechaFinA;
	}

	public String getFechaFinM() {
		return fechaFinM;
	}

	public void setFechaFinM(String fechaFinM) {
		this.fechaFinM = fechaFinM;
	}

	public String getFechaFinD() {
		return fechaFinD;
	}

	public void setFechaFinD(String fechaFinD) {
		this.fechaFinD = fechaFinD;
	}

	public String getFechaFinS() {
		return fechaFinS;
	}

	public void setFechaFinS(String fechaFinS) {
		this.fechaFinS = fechaFinS;
	}

	public String getContenidoFichero() {
		return contenidoFichero;
	}

	public void setContenidoFichero(String contenidoFichero) {
		this.contenidoFichero = contenidoFichero;
	}

	public boolean isActivarNiveles() {
		return activarNiveles;
	}

	public void setActivarNiveles(boolean activarNiveles) {
		this.activarNiveles = activarNiveles;
	}

	public String getTipoElemento() {
		return tipoElemento;
	}

	public void setTipoElemento(String tipoElemento) {
		this.tipoElemento = tipoElemento;
	}

	public String getDescriptores() {
		return descriptores;
	}

	public void setDescriptores(String descriptores) {
		this.descriptores = descriptores;
	}

	public String getTipoBusqueda() {
		return tipoBusqueda;
	}

	public void setTipoBusqueda(String tipoBusqueda) {
		this.tipoBusqueda = tipoBusqueda;
	}

	public boolean isUsaCache() {
		return usaCache;
	}

	public void setUsaCache(boolean usaCache) {
		this.usaCache = usaCache;
	}

	public boolean isReemplazoValoresNulos() {
		return FormaReemplazo.isReemplazoValoresNulos(formaReemplazo);
	}

	public String[] getBloqueos() {
		return bloqueos;
	}

	public void setBloqueos(String[] bloqueos) {
		this.bloqueos = bloqueos;
	}

	public void setActivarBloqueos(boolean activarBloqueos) {
		this.activarBloqueos = activarBloqueos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getReemplazoSimple()
	 */
	public String getReemplazoSimple() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#isReemplazoSimple()
	 */
	public boolean isReemplazoSimple() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setReemplazoSimple(boolean)
	 */
	public void setReemplazoSimple(boolean reemplazoSimple) {
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#getConservada()
	 */
	public String getConservada() {
		// TODO Plantilla de método auto-generado
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see fondos.forms.IBusquedaForm#setConservada(java.lang.String)
	 */
	public void setConservada(String conservada) {
		// TODO Plantilla de método auto-generado

	}
}