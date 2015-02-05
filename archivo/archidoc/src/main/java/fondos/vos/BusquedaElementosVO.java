package fondos.vos;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import se.usuarios.ServiceClient;

import common.pagination.PageInfo;
import common.vos.BaseVO;

import fondos.model.CamposBusquedas;

/**
 * Criterios de búsqueda de elementos del cuadro.
 */
// public class BusquedaElementosVO

public class BusquedaElementosVO extends BaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tipoBusqueda = null;

	private String fondo = null;
	// private String [] codRefObjetoAmbito = null;
	private String[] idObjetoAmbito = null;
	private String[] tipoObjetoAmbito = null;
	private String codigo = null;
	private String titulo = null;
	private String[] niveles = new String[0];
	private String tipoElemento = null;
	private String[] estados = new String[0];
	private String idFicha = null;

	private String[] booleano = new String[0];
	private String[] abrirpar = new String[0];
	private String[] campo = new String[0];
	private int[] tipoCampo = new int[0];
	private String[] operador = new String[0];
	private String[] cerrarpar = new String[0];

	private String[] valor1 = new String[0];
	private String[] valor1A = new String[0];
	private String[] valor1D = new String[0];
	private String[] valor1M = new String[0];

	private String[] valor2 = new String[0];
	private String[] valor2A = new String[0];
	private String[] valor2D = new String[0];
	private String[] valor2M = new String[0];

	private PageInfo pageInfo = null;
	private ServiceClient serviceClient = null;
	private String orderColumnName = null;
	private String tipoOrdenacion = null;
	private int orderColumn = 1;

	private String codigoReferencia = null;
	private String texto = null;
	private String numeroComp = null;
	private String numero = null;
	private String tipoMedida = null;
	private String unidadMedida = null;
	private String fechaIniOperador = null;
	private String fechaFinOperador = null;
	private Date fechaIni = null;
	private Date fechaFin = null;
	private Date fechaIniIni = null;
	private Date fechaIniFin = null;
	private Date fechaFinIni = null;
	private Date fechaFinFin = null;

	private String calificador = null;
	private String calificadorInicial = null;
	private String calificadorFinal = null;
	private String[] descriptores = null;
	private String[] productores = null;
	private String numeroExpediente = null;
	private String procedimiento = null;
	private String rango = null;
	private String signatura = null;
	private String codigoRelacion = null;
	private String idArchivo = null;
	private String calificadorSignatura = null;
	private String calificadorNumeroExpediente = null;
	private String tipoUsuario = null;

	private ArrayList camposRellenos = new ArrayList();
	private String[] tiposServicio = null;
	private String tipoUsuarioPrestamos = null;
	private String tipoUsuarioConsultas = null;

	private Map elementosExcluir = null;
	private Map elementosRestringir = null;
	private Map subqueryElementosRestringir = null;

	private String[] valor3 = new String[0];
	private String[] selectedIds = new String[0];
	private String campoCambio = null;
	private Integer tipoCampoCambio = null;
	private boolean comprobarPermisos = true;

	private String[] formatoFecha1 = new String[0];
	private String[] formatoFecha2 = new String[0];
	private String[] valor1S = new String[0];

	private String idUbicacion = null;
	private String signaturaLike = null;
	private String formato = null;
	private String[] idAmbito = null;
	private String[] codOrdenAmbito = null;

	private String contenidoFichero = null;

	// Campos de Tipo Texto
	private String[] genericoIdTextoCorto = new String[0];
	private String[] genericoTextoCorto = new String[0];
	private String[] genericoOperadorTextoCorto = new String[0];

	// Campos de Tipo Texto Corto
	private String[] genericoIdTextoLargo = new String[0];
	private String[] genericoTextoLargo = new String[0];
	// private String[] genericoOperadorTextoCorto = new String[0];

	// Campos Genéricos de Tipo Numérico
	private String[] genericoIdCampoNumerico = new String[0];
	private String[] genericoCampoNumerico = new String[0];
	private String[] genericoOperadorCampoNumerico = new String[0];
	private String[] genericoCampoNumericoFin = new String[0];

	// Campos Genericos de Tipo Fecha
	private String[] genericoIdFecha = new String[0];
	private Date[] genericoFechaIni = new Date[0];
	private Date[] genericoFechaFin = new Date[0];
	private String[] genericoFechaCalificador = new String[0];
	private String[] genericoFechaOperador = new String[0];

	private ArrayList camposConfigurablesNumericosRellenos = new ArrayList();

	private String valorReemplazoParcial = null;

	private String solicitante = null;

	private String tipoReferencia = null;
	private String[] tiposReferencia = null;

	private String valor4 = null;
	private String valor4A = null;
	private String valor4D = null;
	private String valor4M = null;
	private String valor4S = null;
	private String formatoFecha4 = null;
	private String nombreDesc4 = null;

	private String[] bloqueos = new String[0];

	private String conservada;

	private boolean reemplazoValoresNulos = false;

	/**
	 * Constructor.
	 */
	public BusquedaElementosVO() {
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
	 * @return Returns the idFondo.
	 */
	public String getFondo() {
		return fondo;
	}

	/**
	 * @return el elementosExcluir
	 */
	public Map getElementosExcluir() {
		return elementosExcluir;
	}

	/**
	 * @param elementosExcluir
	 *            el elementosExcluir a establecer
	 */
	public void setElementosExcluir(Map elementosExcluir) {
		this.elementosExcluir = elementosExcluir;
	}

	/**
	 * @return the elementosRestringir
	 */
	public Map getElementosRestringir() {
		return elementosRestringir;
	}

	/**
	 * @param elementosRestringir
	 *            the elementosRestringir to set
	 */
	public void setElementosRestringir(Map elementosRestringir) {
		this.elementosRestringir = elementosRestringir;
	}

	public Map getSubqueryElementosRestringir() {
		return subqueryElementosRestringir;
	}

	public void setSubqueryElementosRestringir(Map subqueryElementosRestringir) {
		this.subqueryElementosRestringir = subqueryElementosRestringir;
	}

	/**
	 * @param idFondo
	 *            The idFondo to set.
	 */
	public void setFondo(String idFondo) {
		this.fondo = idFondo;
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
	// * @return Returns the idObjetoAmbito.
	// */
	// public String[] getCodRefObjetoAmbito()
	// {
	// return codRefObjetoAmbito;
	// }
	// /**
	// * @param idObjetoAmbito The idObjetoAmbito to set.
	// */
	// public void setCodRefObjetoAmbito(String[] idObjetoAmbito)
	// {
	// this.codRefObjetoAmbito = idObjetoAmbito;
	// }
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
	 * @return Returns the serviceClient.
	 */
	public ServiceClient getServiceClient() {
		return serviceClient;
	}

	/**
	 * @param serviceClient
	 *            The serviceClient to set.
	 */
	public void setServiceClient(ServiceClient serviceClient) {
		this.serviceClient = serviceClient;
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
	public int[] getTipoCampo() {
		return tipoCampo;
	}

	/**
	 * @param tipoCampo
	 *            The tipoCampo to set.
	 */
	public void setTipoCampo(int[] tipoCampo) {
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

	public String getOrderColumnName() {
		return orderColumnName;
	}

	public void setOrderColumnName(String orderColumnName) {
		this.orderColumnName = orderColumnName;
	}

	public String getTipoOrdenacion() {
		return tipoOrdenacion;
	}

	public void setTipoOrdenacion(String tipoOrdenacion) {
		this.tipoOrdenacion = tipoOrdenacion;
	}

	public int getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(int orderColumn) {
		this.orderColumn = orderColumn;
	}

	public String getCalificador() {
		return calificador;
	}

	public void setCalificador(String calificador) {
		this.calificador = calificador;
	}

	public String getCodigoReferencia() {
		return codigoReferencia;
	}

	public void setCodigoReferencia(String codigoReferencia) {
		this.codigoReferencia = codigoReferencia;
	}

	public String[] getDescriptores() {
		return descriptores;
	}

	public void setDescriptores(String[] descriptores) {
		this.descriptores = descriptores;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getNumeroComp() {
		return numeroComp;
	}

	public void setNumeroComp(String numeroComp) {
		this.numeroComp = numeroComp;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
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
	 * @return el fechaFinFin
	 */
	public Date getFechaFinFin() {
		return fechaFinFin;
	}

	/**
	 * @param fechaFinFin
	 *            el fechaFinFin a establecer
	 */
	public void setFechaFinFin(Date fechaFinFin) {
		this.fechaFinFin = fechaFinFin;
	}

	/**
	 * @return el fechaFinIni
	 */
	public Date getFechaFinIni() {
		return fechaFinIni;
	}

	/**
	 * @param fechaFinIni
	 *            el fechaFinIni a establecer
	 */
	public void setFechaFinIni(Date fechaFinIni) {
		this.fechaFinIni = fechaFinIni;
	}

	/**
	 * @return el fechaIniFin
	 */
	public Date getFechaIniFin() {
		return fechaIniFin;
	}

	/**
	 * @param fechaIniFin
	 *            el fechaIniFin a establecer
	 */
	public void setFechaIniFin(Date fechaIniFin) {
		this.fechaIniFin = fechaIniFin;
	}

	/**
	 * @return el fechaIniIni
	 */
	public Date getFechaIniIni() {
		return fechaIniIni;
	}

	/**
	 * @param fechaIniIni
	 *            el fechaIniIni a establecer
	 */
	public void setFechaIniIni(Date fechaIniIni) {
		this.fechaIniIni = fechaIniIni;
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
	 * @return el productores
	 */
	public String[] getProductores() {
		return productores;
	}

	/**
	 * @param productores
	 *            el productores a establecer
	 */
	public void setProductores(String[] productores) {
		this.productores = productores;
	}

	public ArrayList getCamposRellenos() {
		return camposRellenos;
	}

	public void setCamposRellenos(ArrayList camposRellenos) {
		this.camposRellenos = camposRellenos;
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
	 * FUNCIONES AUXILIARES PARA REDUCIR CÓDIGO DE BÚSQUEDAS SACAR DE AQUÍ?
	 */

	public Date getFechaIniCampo(String campo) {
		Date ret = new Date();

		if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL))
			ret = this.getFechaIniIni();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL))
			ret = this.getFechaFinIni();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS))
			ret = this.getFechaIni();

		return ret;
	}

	public String getFechaOperadorCampo(String campo) {
		String ret = new String();

		if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL))
			ret = this.getFechaIniOperador();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL))
			ret = this.getFechaFinOperador();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS))
			ret = this.getFechaIniOperador();

		return ret;
	}

	public Date getFechaFinCampo(String campo) {
		Date ret = new Date();

		if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL))
			ret = this.getFechaIniFin();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL))
			ret = this.getFechaFinFin();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS))
			ret = this.getFechaFin();

		return ret;
	}

	public String getCalificadorFechaCampo(String campo) {
		String ret = new String();

		if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_INICIAL))
			ret = this.getCalificadorInicial();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHA_FINAL))
			ret = this.getCalificadorFinal();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FECHAS))
			ret = this.getCalificador();

		return ret;
	}

	public String getTextoCampo(String campo) {
		String ret = new String();

		if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TITULO))
			ret = this.getTitulo();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_TEXTO))
			ret = this.getTexto();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO))
			ret = this.getCodigo();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_CODIGO_REFERENCIA))
			ret = this.getCodigoReferencia();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_FONDO))
			ret = this.getFondo();
		else if (campo
				.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_NUMERO_EXPEDIENTE))
			ret = this.getNumeroExpediente();
		else if (campo.equals(CamposBusquedas.CAMPO_ENTRADA_BUSQUEDA_RANGOS))
			ret = this.getRango();
		return ret;
	}

	public String getSignatura() {
		return signatura;
	}

	public void setSignatura(String signatura) {
		this.signatura = signatura;
	}

	public String getCodigoRelacion() {
		return codigoRelacion;
	}

	public void setCodigoRelacion(String codigoRelacion) {
		this.codigoRelacion = codigoRelacion;
	}

	public String getIdArchivo() {
		return idArchivo;
	}

	public void setIdArchivo(String idArchivo) {
		this.idArchivo = idArchivo;
	}

	public String getCalificadorSignatura() {
		return calificadorSignatura;
	}

	public void setCalificadorSignatura(String calificadorSignatura) {
		this.calificadorSignatura = calificadorSignatura;
	}

	public String getCalificadorNumeroExpediente() {
		return calificadorNumeroExpediente;
	}

	public void setCalificadorNumeroExpediente(
			String calificadorNumeroExpediente) {
		this.calificadorNumeroExpediente = calificadorNumeroExpediente;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String[] getTiposServicio() {
		return tiposServicio;
	}

	public void setTiposServicio(String[] tiposServicio) {
		this.tiposServicio = tiposServicio;
	}

	public String getTipoUsuarioConsultas() {
		return tipoUsuarioConsultas;
	}

	public void setTipoUsuarioConsultas(String tipoUsuarioConsultas) {
		this.tipoUsuarioConsultas = tipoUsuarioConsultas;
	}

	public String getTipoUsuarioPrestamos() {
		return tipoUsuarioPrestamos;
	}

	public void setTipoUsuarioPrestamos(String tipoUsuarioPrestamos) {
		this.tipoUsuarioPrestamos = tipoUsuarioPrestamos;
	}

	/**
	 * @return el fechaFinOperador
	 */
	public String getFechaFinOperador() {
		return fechaFinOperador;
	}

	/**
	 * @param fechaFinOperador
	 *            el fechaFinOperador a establecer
	 */
	public void setFechaFinOperador(String fechaFinOperador) {
		this.fechaFinOperador = fechaFinOperador;
	}

	/**
	 * @return el fechaIniOperador
	 */
	public String getFechaIniOperador() {
		return fechaIniOperador;
	}

	/**
	 * @param fechaIniOperador
	 *            el fechaIniOperador a establecer
	 */
	public void setFechaIniOperador(String fechaIniOperador) {
		this.fechaIniOperador = fechaIniOperador;
	}

	public String[] getValor3() {
		return valor3;
	}

	public void setValor3(String[] valor3) {
		this.valor3 = valor3;
	}

	public String[] getSelectedIds() {
		return selectedIds;
	}

	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}

	public String getCampoCambio() {
		return campoCambio;
	}

	public void setCampoCambio(String campoCambio) {
		this.campoCambio = campoCambio;
	}

	public Integer getTipoCampoCambio() {
		return tipoCampoCambio;
	}

	public void setTipoCampoCambio(Integer tipoCampoCambio) {
		this.tipoCampoCambio = tipoCampoCambio;
	}

	public boolean isComprobarPermisos() {
		return comprobarPermisos;
	}

	public void setComprobarPermisos(boolean comprobarPermisos) {
		this.comprobarPermisos = comprobarPermisos;
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

	public String[] getValor1S() {
		return valor1S;
	}

	public void setValor1S(String[] valor1S) {
		this.valor1S = valor1S;
	}

	public String getFormato() {
		return formato;
	}

	public void setFormato(String formato) {
		this.formato = formato;
	}

	public String getIdUbicacion() {
		return idUbicacion;
	}

	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
	}

	public String getSignaturaLike() {
		return signaturaLike;
	}

	public void setSignaturaLike(String signaturaLike) {
		this.signaturaLike = signaturaLike;
	}

	public String[] getIdAmbito() {
		return idAmbito;
	}

	public void setIdAmbito(String[] idAmbito) {
		this.idAmbito = idAmbito;
	}

	public String[] getCodOrdenAmbito() {
		return codOrdenAmbito;
	}

	public void setCodOrdenAmbito(String[] codOrdenAmbito) {
		this.codOrdenAmbito = codOrdenAmbito;
	}

	public String getContenidoFichero() {
		return contenidoFichero;
	}

	public void setContenidoFichero(String contenidoFichero) {
		this.contenidoFichero = contenidoFichero;
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

	public ArrayList getCamposConfigurablesNumericosRellenos() {
		return camposConfigurablesNumericosRellenos;
	}

	public void setCamposConfigurablesNumericosRellenos(
			ArrayList camposConfigurablesNumericosRellenos) {
		this.camposConfigurablesNumericosRellenos = camposConfigurablesNumericosRellenos;
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

	public Date[] getGenericoFechaIni() {
		return genericoFechaIni;
	}

	public void setGenericoFechaIni(Date[] genericoFechaIni) {
		this.genericoFechaIni = genericoFechaIni;
	}

	public Date[] getGenericoFechaFin() {
		return genericoFechaFin;
	}

	public void setGenericoFechaFin(Date[] genericoFechaFin) {
		this.genericoFechaFin = genericoFechaFin;
	}

	public String[] getGenericoIdFecha() {
		return genericoIdFecha;
	}

	public void setGenericoIdFecha(String[] genericoIdFecha) {
		this.genericoIdFecha = genericoIdFecha;
	}

	public String[] getGenericoFechaCalificador() {
		return genericoFechaCalificador;
	}

	public void setGenericoFechaCalificador(String[] genericoFechaCalificador) {
		this.genericoFechaCalificador = genericoFechaCalificador;
	}

	public String[] getGenericoFechaOperador() {
		return genericoFechaOperador;
	}

	public void setGenericoFechaOperador(String[] genericoFechaOperador) {
		this.genericoFechaOperador = genericoFechaOperador;
	}

	public void setValorReemplazoParcial(String valorReemplazoParcial) {
		this.valorReemplazoParcial = valorReemplazoParcial;
	}

	public String getValorReemplazoParcial() {
		return valorReemplazoParcial;
	}

	public void setSolicitante(String solicitante) {
		this.solicitante = solicitante;
	}

	public String getSolicitante() {
		return solicitante;
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

	public String getValor4() {
		return valor4;
	}

	public void setValor4(String valor4) {
		this.valor4 = valor4;
	}

	public String getValor4A() {
		return valor4A;
	}

	public void setValor4A(String valor4A) {
		this.valor4A = valor4A;
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

	public String getValor4S() {
		return valor4S;
	}

	public void setValor4S(String valor4S) {
		this.valor4S = valor4S;
	}

	public String getFormatoFecha4() {
		return formatoFecha4;
	}

	public void setFormatoFecha4(String formatoFecha4) {
		this.formatoFecha4 = formatoFecha4;
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

	public void setReemplazoValoresNulos(boolean reemplazoValoresNulos) {
		this.reemplazoValoresNulos = reemplazoValoresNulos;
	}

	public boolean isReemplazoValoresNulos() {
		return reemplazoValoresNulos;
	}

	public void setConservada(String conservada) {
		this.conservada = conservada;
	}

	public String getConservada() {
		return conservada;
	}
}