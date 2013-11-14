package ieci.tecdoc.isicres.rpadmin.struts.forms;

import ieci.tecdoc.isicres.rpadmin.struts.util.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import es.ieci.tecdoc.isicres.admin.beans.InformeBean;
import es.ieci.tecdoc.isicres.admin.beans.LibrosInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.OficinasInformeBean;
import es.ieci.tecdoc.isicres.admin.beans.PerfilesInformeBean;



public class InformeForm extends RPAdminActionForm{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	private String[] attrsToUpper = new String[] { "description",
			"infoAuxiliar", "estadoOficina", "codigoOficina", "nombreOficina" };


	// Campos del VO
	private String id;
	private String description;
	private String report;
	private String nameTypeReport;
	private String typeReport;
	private String allOfics;
	private String allPerfs;
	private String allArch;
	private String typeArch;
	private FormFile  informeFile;
	private Boolean usarParaTodasOficinas;
	private Boolean usarParaTodosPerfiles;
	private Boolean usarParaTodosLibros;

	// Oficinas
	private String estadoOficina;
	private String posOficina;
	private String idOficinaAsunto;
	private String idOficina;
	private String codigoOficina;
	private String nombreOficina;

	// Perfiles
	private String idPerfil;
	private String nombrePerfil;
	private String posPerfil;
	private String estadoPerfil;

	// Libros
	private String idLibro;
	private String nombreLibro;
	private String posLibro;
	private String estadoLibro;

	// Indicador del Tab Activo
	private Integer activeTab=new Integer(1);

	public void toUpperCase(HttpServletRequest request)
	throws IllegalAccessException, InvocationTargetException {
		super.toUpperCase(this, request);
	}

	public String[] getAttrsToUpper() {
		return attrsToUpper;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the report
	 */
	public String getReport() {
		return report;
	}

	/**
	 * @param report the report to set
	 */
	public void setReport(String report) {
		this.report = report;
	}

	/**
	 * @return the nameTypeReport
	 */
	public String getNameTypeReport() {
		return nameTypeReport;
	}

	/**
	 * @param nameTypeReport the nameTypeReport to set
	 */
	public void setNameTypeReport(String nameTypeReport) {
		this.nameTypeReport = nameTypeReport;
	}

	/**
	 * @return the allOfics
	 */
	public String getAllOfics() {
		return allOfics;
	}

	/**
	 * @param allOfics the allOfics to set
	 */
	public void setAllOfics(String allOfics) {
		this.allOfics = allOfics;
	}

	/**
	 * @return the allPerfs
	 */
	public String getAllPerfs() {
		return allPerfs;
	}

	/**
	 * @param allPerfs the allPerfs to set
	 */
	public void setAllPerfs(String allPerfs) {
		this.allPerfs = allPerfs;
	}

	/**
	 * @return the allArch
	 */
	public String getAllArch() {
		return allArch;
	}

	/**
	 * @param allArch the allArch to set
	 */
	public void setAllArch(String allArch) {
		this.allArch = allArch;
	}

	/**
	 * @return the typeArch
	 */
	public String getTypeArch() {
		return typeArch;
	}

	/**
	 * @param typeArch the typeArch to set
	 */
	public void setTypeArch(String typeArch) {
		this.typeArch = typeArch;
	}

	/**
	 * @return the estadoOficina
	 */
	public String getEstadoOficina() {
		return estadoOficina;
	}

	/**
	 * @param estadoOficina the estadoOficina to set
	 */
	public void setEstadoOficina(String estadoOficina) {
		this.estadoOficina = estadoOficina;
	}

	/**
	 * @return the posOficina
	 */
	public String getPosOficina() {
		return posOficina;
	}

	/**
	 * @param posOficina the posOficina to set
	 */
	public void setPosOficina(String posOficina) {
		this.posOficina = posOficina;
	}

	/**
	 * @return the idOficinaAsunto
	 */
	public String getIdOficinaAsunto() {
		return idOficinaAsunto;
	}

	/**
	 * @param idOficinaAsunto the idOficinaAsunto to set
	 */
	public void setIdOficinaAsunto(String idOficinaAsunto) {
		this.idOficinaAsunto = idOficinaAsunto;
	}

	/**
	 * @return the idOficina
	 */
	public String getIdOficina() {
		return idOficina;
	}

	/**
	 * @param idOficina the idOficina to set
	 */
	public void setIdOficina(String idOficina) {
		this.idOficina = idOficina;
	}

	/**
	 * @return the codigoOficina
	 */
	public String getCodigoOficina() {
		return codigoOficina;
	}

	/**
	 * @param codigoOficina the codigoOficina to set
	 */
	public void setCodigoOficina(String codigoOficina) {
		this.codigoOficina = codigoOficina;
	}

	/**
	 * @return the nombreOficina
	 */
	public String getNombreOficina() {
		return nombreOficina;
	}

	/**
	 * @param nombreOficina the nombreOficina to set
	 */
	public void setNombreOficina(String nombreOficina) {
		this.nombreOficina = nombreOficina;
	}

	/**
	 * @return the activeTab
	 */
	public Integer getActiveTab() {
		return activeTab;
	}

	/**
	 * @param activeTab the activeTab to set
	 */
	public void setActiveTab(Integer activeTab) {
		this.activeTab = activeTab;
	}

	/**
	 * @param attrsToUpper the attrsToUpper to set
	 */
	public void setAttrsToUpper(String[] attrsToUpper) {
		this.attrsToUpper = attrsToUpper;
	}



	/**
	 * @return the usarParaTodasOficinas
	 */
	public Boolean getUsarParaTodasOficinas() {
		return usarParaTodasOficinas;
	}

	/**
	 * @param usarParaTodasOficinas the usarParaTodasOficinas to set
	 */
	public void setUsarParaTodasOficinas(Boolean usarParaTodasOficinas) {
		this.usarParaTodasOficinas = usarParaTodasOficinas;
	}

	/**
	 * @return the usarPAraTodosPerfiles
	 */
	public Boolean getUsarParaTodosPerfiles() {
		return usarParaTodosPerfiles;
	}

	/**
	 * @return the usarParaTodosLibros
	 */
	public Boolean getUsarParaTodosLibros() {
		return usarParaTodosLibros;
	}

	/**
	 * @param usarParaTodosLibros the usarParaTodosLibros to set
	 */
	public void setUsarParaTodosLibros(Boolean usarParaTodosLibros) {
		this.usarParaTodosLibros = usarParaTodosLibros;
	}

	/**
	 * @param usarPAraTodosPerfiles the usarPAraTodosPerfiles to set
	 */
	public void setUsarParaTodosPerfiles(Boolean usarParaTodosPerfiles) {
		this.usarParaTodosPerfiles = usarParaTodosPerfiles;
	}

	/**
	 * @return the typeReport
	 */
	public String getTypeReport() {
		return typeReport;
	}

	/**
	 * @param typeReport the typeReport to set
	 */
	public void setTypeReport(String typeReport) {
		this.typeReport = typeReport;
	}




	/**
	 * @return the idPerfil
	 */
	public String getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil the idPefil to set
	 */
	public void setIdPerfil(String idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the nombrePerfil
	 */
	public String getNombrePerfil() {
		return nombrePerfil;
	}

	/**
	 * @param nombrePerfil the nombrePeril to set
	 */
	public void setNombrePerfil(String nombrePerfil) {
		this.nombrePerfil = nombrePerfil;
	}

	/**
	 * @return the posPerfil
	 */
	public String getPosPerfil() {
		return posPerfil;
	}

	/**
	 * @param posPefil the posPerfil to set
	 */
	public void setPosPerfil(String posPerfil) {
		this.posPerfil = posPerfil;
	}

	/**
	 * @return the estadoPerfil
	 */
	public String getEstadoPerfil() {
		return estadoPerfil;
	}

	/**
	 * @param estadoPerfil the estadoPerfil to set
	 */
	public void setEstadoPerfil(String estadoPerfil) {
		this.estadoPerfil = estadoPerfil;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}




	/**
	 * @return the idLibro
	 */
	public String getIdLibro() {
		return idLibro;
	}

	/**
	 * @param idLibro the idLibro to set
	 */
	public void setIdLibro(String idLibro) {
		this.idLibro = idLibro;
	}

	/**
	 * @return the nombreLibro
	 */
	public String getNombreLibro() {
		return nombreLibro;
	}

	/**
	 * @param nombreLibro the nombreLibro to set
	 */
	public void setNombreLibro(String nombreLibro) {
		this.nombreLibro = nombreLibro;
	}

	/**
	 * @return the posLibro
	 */
	public String getPosLibro() {
		return posLibro;
	}

	/**
	 * @param posLibro the posLibro to set
	 */
	public void setPosLibro(String posLibro) {
		this.posLibro = posLibro;
	}

	/**
	 * @return the estadoLibro
	 */
	public String getEstadoLibro() {
		return estadoLibro;
	}

	/**
	 * @param estadoLibro the estadoLibro to set
	 */
	public void setEstadoLibro(String estadoLibro) {
		this.estadoLibro = estadoLibro;
	}



	/**
	 * @return the informeFile
	 */
	public FormFile  getInformeFile() {
		return informeFile;
	}

	/**
	 * @param informeFile the informeFile to set
	 */
	public void setInformeFile(FormFile  informeFile) {
		this.informeFile = informeFile;
	}

	public void set(InformeBean informeBean){

		setAllArch(new Integer(informeBean.getAllArch()).toString());
		setAllOfics(new Integer(informeBean.getAllOfics()).toString());
		setAllPerfs(new Integer(informeBean.getAllPerfs()).toString());
		setDescription(informeBean.getDescription());
		setId((new Integer(informeBean.getId()).toString()));
		setReport(informeBean.getReport());
		setTypeArch(new Integer(informeBean.getTypeArch()).toString());
		setTypeReport(new Integer(informeBean.getTypeReport()).toString());
		setNameTypeReport(informeBean.getNameTypeReport());
		setUsarParaTodasOficinas(new Boolean(Utils.getBooleanValue(informeBean.getAllOfics())));
		setUsarParaTodosPerfiles(new Boolean(Utils.getBooleanValue(informeBean.getAllPerfs())));
		setUsarParaTodosLibros(new Boolean(Utils.getBooleanValue(informeBean.getAllArch())));
	}





	public InformeBean populate() throws Exception{
		InformeBean informeBean = new InformeBean();

		if(StringUtils.isNotBlank(getId())){
			informeBean.setId(new Integer(getId()).intValue());
		}
		informeBean.setDescription(description);
		informeBean.setNameTypeReport(nameTypeReport);
		informeBean.setReport(report);
		informeBean.setTypeArch(new Integer(getTypeArch()).intValue());
		informeBean.setTypeReport(new Integer(getTypeReport()).intValue());
		if(informeFile!=null){
			informeBean.setFileData(informeFile.getFileData());
			informeBean.setReport(informeFile.getFileName());
		}

		if(getUsarParaTodasOficinas() != null)informeBean.setAllOfics(Utils.getIntValue(getUsarParaTodasOficinas().booleanValue()));
		if(getUsarParaTodosPerfiles() != null)informeBean.setAllPerfs(Utils.getIntValue(getUsarParaTodosPerfiles().booleanValue()));
		if(getUsarParaTodosLibros() != null)informeBean.setAllArch(Utils.getIntValue(getUsarParaTodosLibros().booleanValue()));

		return informeBean;
	}


	public InformeBean populate(List listaOficinas, List listaOficinasEliminadas, List listaLibros, List listaLibrosEliminados, List listaPerfiles, List listaPerfilesEliminados) throws Exception {
		InformeBean informeBean = this.populate(listaOficinas,listaLibros,listaPerfiles);
	    if(listaOficinasEliminadas != null && listaOficinasEliminadas.size() > 0){
	    	OficinasInformeBean oficinasInformeBean = new OficinasInformeBean();
		oficinasInformeBean.setLista(listaOficinasEliminadas);
	    	informeBean.setOficinasEliminadas(oficinasInformeBean);
	    }
	    if(listaLibrosEliminados != null && listaLibrosEliminados.size() > 0){
	    	LibrosInformeBean librosInformeBean = new LibrosInformeBean();
		librosInformeBean.setLista(listaLibrosEliminados);
	    	informeBean.setLibrosEliminados(librosInformeBean);
	    }
	    if(listaPerfilesEliminados != null && listaPerfilesEliminados.size() > 0){
	    	PerfilesInformeBean perfilesInformeBean = new PerfilesInformeBean();
	    	perfilesInformeBean.setLista(listaPerfilesEliminados);
	    	informeBean.setPerfilesEliminados(perfilesInformeBean);
	    }
	    return informeBean;
	}


	public InformeBean populate(List listaOficinas, List listaLibros, List listaPerfiles) throws Exception {
		InformeBean informeBean = this.populate();
	    if(listaOficinas != null && listaOficinas.size() > 0){
	    	OficinasInformeBean oficinasInformeBean = new OficinasInformeBean();
	    	oficinasInformeBean.setLista(listaOficinas);
	    	informeBean.setOficinas(oficinasInformeBean);
	    }
	    if(listaLibros != null && listaLibros.size() > 0){
	    	LibrosInformeBean librosInformeBean = new LibrosInformeBean();
		librosInformeBean.setLista(listaLibros);
	    	informeBean.setLibros(librosInformeBean);
	    }
	    if(listaPerfiles != null && listaPerfiles.size() > 0){
	    	PerfilesInformeBean perfilesInformeBean = new PerfilesInformeBean();
		perfilesInformeBean.setLista(listaPerfiles);
	    	informeBean.setPerfiles(perfilesInformeBean);
	    }
	    return informeBean;
	}


	/**
	 * Resetea los datos
	 */
	public void resetOficina(){
		this.estadoOficina = null;
		this.posOficina = null;
		this.idOficinaAsunto= null;
		this.idOficina = null;
		this.codigoOficina = null;
		this.nombreOficina = null;
		this.idPerfil = null;
		this.nombrePerfil = null;
	}

	public void resetPerfil(){
		this.posPerfil = null;
		this.estadoPerfil = null;
		this.idPerfil = null;
		this.nombrePerfil = null;
	}

	public void resetLibro(){
		this.posLibro = null;
		this.estadoLibro = null;
		this.idLibro = null;
		this.nombreLibro = null;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InformeForm [attrsToUpper=");
		builder.append(Arrays.toString(attrsToUpper));
		builder.append(", id=");
		builder.append(id);
		builder.append(", description=");
		builder.append(description);
		builder.append(", report=");
		builder.append(report);
		builder.append(", nameTypeReport=");
		builder.append(nameTypeReport);
		builder.append(", typeReport=");
		builder.append(typeReport);
		builder.append(", allOfics=");
		builder.append(allOfics);
		builder.append(", allPerfs=");
		builder.append(allPerfs);
		builder.append(", allArch=");
		builder.append(allArch);
		builder.append(", typeArch=");
		builder.append(typeArch);
		builder.append(", informeFile=");
		builder.append(informeFile);
		builder.append(", usarParaTodasOficinas=");
		builder.append(usarParaTodasOficinas);
		builder.append(", usarParaTodosPerfiles=");
		builder.append(usarParaTodosPerfiles);
		builder.append(", usarParaTodosLibros=");
		builder.append(usarParaTodosLibros);
		builder.append(", estadoOficina=");
		builder.append(estadoOficina);
		builder.append(", posOficina=");
		builder.append(posOficina);
		builder.append(", idOficinaAsunto=");
		builder.append(idOficinaAsunto);
		builder.append(", idOficina=");
		builder.append(idOficina);
		builder.append(", codigoOficina=");
		builder.append(codigoOficina);
		builder.append(", nombreOficina=");
		builder.append(nombreOficina);
		builder.append(", idPerfil=");
		builder.append(idPerfil);
		builder.append(", nombrePerfil=");
		builder.append(nombrePerfil);
		builder.append(", posPerfil=");
		builder.append(posPerfil);
		builder.append(", estadoPerfil=");
		builder.append(estadoPerfil);
		builder.append(", idLibro=");
		builder.append(idLibro);
		builder.append(", nombreLibro=");
		builder.append(nombreLibro);
		builder.append(", posLibro=");
		builder.append(posLibro);
		builder.append(", estadoLibro=");
		builder.append(estadoLibro);
		builder.append(", activeTab=");
		builder.append(activeTab);
		builder.append("]");
		return builder.toString();
	}


}
