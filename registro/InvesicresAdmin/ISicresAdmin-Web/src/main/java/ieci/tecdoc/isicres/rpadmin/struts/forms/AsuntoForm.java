package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import es.ieci.tecdoc.isicres.admin.beans.DocumentosTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasTipoAsuntoBean;
import es.ieci.tecdoc.isicres.admin.beans.TipoAsuntoBean;

public class AsuntoForm extends RPAdminActionForm {
	private static final long serialVersionUID = 1L;

	private String[] attrsToUpper = new String[] { "codigo", "nombre",
			"infoAuxiliar", "estadoOficina", "codigoOficina", "nombreOficina",
			"estadoDocumento", "nombreDocumento" };

	//CAMPOS DEL VO
	private String id;
	private String codigo;
	private String nombre;
	private String idUnidadAdministrativa;
	private String fechaCreacion;
	private Boolean deshabilitado;
	private String fechaBaja;
	private Boolean usarParaEntrada;
	private Boolean usarParaSalida;
	private String infoAuxiliar;
	private Boolean usarParaTodasOficinas;

	//Datos Unidad Administrativa
	private String codigoUnidadAdministrativa;
	private String nombreUnidadAdministrativa;

	//Oficinas
	private String estadoOficina;
	private String posOficina;
	private String idOficinaAsunto;
	private String idOficina;
	private String codigoOficina;
	private String nombreOficina;

	//Documentos
	private String estadoDocumento;
	private String posDocumento;
	private String idDocumento;
	private String nombreDocumento;

	//Indicador del Tab Activo
	private Integer activeTab=new Integer(1);


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIdUnidadAdministrativa() {
		return idUnidadAdministrativa;
	}

	public void setIdUnidadAdministrativa(String idUnidadAdministrativa) {
		this.idUnidadAdministrativa = idUnidadAdministrativa;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Boolean getDeshabilitado() {
		return deshabilitado;
	}

	public void setDeshabilitado(Boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	public String getFechaBaja() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja = fechaBaja;
	}

	public Boolean getUsarParaEntrada() {
		return usarParaEntrada;
	}

	public void setUsarParaEntrada(Boolean usarParaEntrada) {
		this.usarParaEntrada = usarParaEntrada;
	}

	public Boolean getUsarParaSalida() {
		return usarParaSalida;
	}

	public void setUsarParaSalida(Boolean usarParaSalida) {
		this.usarParaSalida = usarParaSalida;
	}

	public String getInfoAuxiliar() {
		return infoAuxiliar;
	}

	public void setInfoAuxiliar(String infoAuxiliar) {
		this.infoAuxiliar = infoAuxiliar;
	}

	public Boolean getUsarParaTodasOficinas() {
		return usarParaTodasOficinas;
	}

	public void setUsarParaTodasOficinas(Boolean usarParaTodasOficinas) {
		this.usarParaTodasOficinas = usarParaTodasOficinas;
	}

	public String getCodigoUnidadAdministrativa() {
		return codigoUnidadAdministrativa;
	}

	public void setCodigoUnidadAdministrativa(String codigoUnidadAdministrativa) {
		this.codigoUnidadAdministrativa = codigoUnidadAdministrativa;
	}

	public String getNombreUnidadAdministrativa() {
		return nombreUnidadAdministrativa;
	}

	public void setNombreUnidadAdministrativa(String nombreUnidadAdministrativa) {
		this.nombreUnidadAdministrativa = nombreUnidadAdministrativa;
	}

	public String getEstadoOficina() {
		return estadoOficina;
	}

	public void setEstadoOficina(String estadoOficina) {
		this.estadoOficina = estadoOficina;
	}

	public String getPosOficina() {
		return posOficina;
	}

	public void setPosOficina(String posOficina) {
		this.posOficina = posOficina;
	}

	public String getIdOficinaAsunto() {
		return idOficinaAsunto;
	}

	public void setIdOficinaAsunto(String idOficinaAsunto) {
		this.idOficinaAsunto = idOficinaAsunto;
	}

	public String getIdOficina() {
		return idOficina;
	}

	public void setIdOficina(String idOficina) {
		this.idOficina = idOficina;
	}

	public String getCodigoOficina() {
		return codigoOficina;
	}

	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	public String getNombreOficina() {
		return nombreOficina;
	}

	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}

	public String getEstadoDocumento() {
		return estadoDocumento;
	}

	public void setEstadoDocumento(String estadoDocumento) {
		this.estadoDocumento = estadoDocumento;
	}

	public String getPosDocumento() {
		return posDocumento;
	}

	public void setPosDocumento(String posDocumento) {
		this.posDocumento = posDocumento;
	}

	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	public Integer getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(Integer activeTab) {
		this.activeTab = activeTab;
	}

	public void set(TipoAsuntoBean tipoAsuntoBean){
		setId(new Integer(tipoAsuntoBean.getId()).toString());
		setCodigo(tipoAsuntoBean.getCode());
		setNombre(tipoAsuntoBean.getMatter());
		setIdUnidadAdministrativa(new Integer(tipoAsuntoBean.getIdOrg()).toString());
		setCodigoUnidadAdministrativa(tipoAsuntoBean.getCodigoOrg());
		setNombreUnidadAdministrativa(tipoAsuntoBean.getNombreOrg());
		setFechaCreacion(tipoAsuntoBean.getCreationDateVista());
		setFechaBaja(tipoAsuntoBean.getDisableDateVista());

		if(!Utils.getBooleanValue(tipoAsuntoBean.getEnabled())){
			setDeshabilitado(Boolean.TRUE);
		}

		setUsarParaEntrada(new Boolean(Utils.getBooleanValue(tipoAsuntoBean.getForEreg())));
		setUsarParaSalida(new Boolean(Utils.getBooleanValue(tipoAsuntoBean.getForSreg())));
		setInfoAuxiliar(tipoAsuntoBean.getInformacionAuxiliar());
		setUsarParaTodasOficinas(new Boolean(Utils.getBooleanValue(tipoAsuntoBean.getAllOfics())));
	}





	public TipoAsuntoBean populate() throws Exception{
		TipoAsuntoBean tipoAsuntoBean = new TipoAsuntoBean();

		if(StringUtils.isNotBlank(getId())){
			tipoAsuntoBean.setId(new Integer(getId()).intValue());
		}
		tipoAsuntoBean.setCode(getCodigo());
		tipoAsuntoBean.setMatter(getNombre());
		if(StringUtils.isNotBlank(getIdUnidadAdministrativa())){
			tipoAsuntoBean.setIdOrg(new Integer(getIdUnidadAdministrativa()).intValue());
			tipoAsuntoBean.setNombreOrg(getNombreUnidadAdministrativa());
			tipoAsuntoBean.setCodigoOrg(getCodigoUnidadAdministrativa());
		}
		tipoAsuntoBean.setCreationDateVista(getFechaCreacion());
		tipoAsuntoBean.setInformacionAuxiliar(getInfoAuxiliar());

		if(getDeshabilitado() != null && getDeshabilitado().booleanValue()){
			tipoAsuntoBean.setEnabled(Utils.getIntValue(false));
			tipoAsuntoBean.setDisableDateVista(getFechaBaja());
		}
		else{
			tipoAsuntoBean.setEnabled(Utils.getIntValue(true));
		}

		if(getUsarParaEntrada() != null) tipoAsuntoBean.setForEreg(Utils.getIntValue(getUsarParaEntrada().booleanValue()));
		if(getUsarParaSalida() != null) tipoAsuntoBean.setForSreg(Utils.getIntValue(getUsarParaSalida().booleanValue()));
		if(getUsarParaTodasOficinas() != null)tipoAsuntoBean.setAllOfics(Utils.getIntValue(getUsarParaTodasOficinas().booleanValue()));

		return tipoAsuntoBean;
	}


	public TipoAsuntoBean populate(List listaOficinas, List listaDocumentos, List listaOficinasEliminadas, List listaDocumentosEliminados) throws Exception {
		TipoAsuntoBean tipoAsuntoBean = this.populate(listaOficinas, listaDocumentos);

	    if(listaDocumentosEliminados != null && listaDocumentosEliminados.size() > 0){
	    	DocumentosTipoAsuntoBean documentosTipoAsuntoBean = new DocumentosTipoAsuntoBean();
	    	documentosTipoAsuntoBean.setLista(listaDocumentosEliminados);
	    	tipoAsuntoBean.setDocumentosEliminados(documentosTipoAsuntoBean);
	    }

	    if(listaOficinasEliminadas != null && listaOficinasEliminadas.size() > 0){
	    	OficinasTipoAsuntoBean oficinasTipoAsuntoBean = new OficinasTipoAsuntoBean();
	    	oficinasTipoAsuntoBean.setLista(listaOficinasEliminadas);
	    	tipoAsuntoBean.setOficinasEliminadas(oficinasTipoAsuntoBean);
	    }

	    return tipoAsuntoBean;

	}


	public TipoAsuntoBean populate(List listaOficinas, List listaDocumentos) throws Exception {
		TipoAsuntoBean tipoAsuntoBean = this.populate();

	    if(listaDocumentos != null && listaDocumentos.size() > 0){
	    	DocumentosTipoAsuntoBean documentosTipoAsuntoBean = new DocumentosTipoAsuntoBean();
	    	documentosTipoAsuntoBean.setLista(listaDocumentos);
	    	tipoAsuntoBean.setDocumentos(documentosTipoAsuntoBean);
	    }

	    if(listaOficinas != null && listaOficinas.size() > 0){
	    	OficinasTipoAsuntoBean oficinasTipoAsuntoBean = new OficinasTipoAsuntoBean();
	    	oficinasTipoAsuntoBean.setLista(listaOficinas);
	    	tipoAsuntoBean.setOficinas(oficinasTipoAsuntoBean);
	    }
	    return tipoAsuntoBean;
	}

	public void resetDocumento(){
		this.nombreDocumento = null;
		this.idDocumento = null;
		this.estadoDocumento = null;
		this.posDocumento = null;
	}

	public void resetOficina(){
		this.estadoOficina = null;
		this.posOficina = null;
		this.idOficinaAsunto= null;
		this.idOficina = null;
		this.codigoOficina = null;
		this.nombreOficina = null;
	}

	public void toUpperCase(HttpServletRequest request)
			throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}



}
