package es.ieci.scsp.verifdata.persistence.sw;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import es.ieci.scsp.verifdata.model.mapping.clienteligero.Postprocesadores;
import es.ieci.scsp.verifdata.model.mapping.clienteligero.Preprocesadores;
import es.ieci.scsp.verifdata.model.mapping.clienteligero.Sinonimos;

public class CertificadosAutorizacionesSW{

	private String codigoCertificado;
	private String descripcion;
	private String xpathAdjunto;
	private String mimeTypeAdjunto;
	private String xmlTemplate;
	private String classDatosEspecificos;
	private String classExcelProcesador;
	private String excelTemplate;
	private Boolean directo;
	private String classExcelRespProcesador;
	private String excelTemplateRespuesta;
	private Preprocesadores[] preprocesadoreses;
	private PostprocesadoresSW [] postprocesadoreses;
	
	private Sinonimos[] sinonimoses;

	public CertificadosAutorizacionesSW() {
	}

	public CertificadosAutorizacionesSW(String codigoCertificado) {
		this.codigoCertificado = codigoCertificado;
	}

	public CertificadosAutorizacionesSW(String codigoCertificado,
			String descripcion, String xpathAdjunto, String mimeTypeAdjunto,
			String xmlTemplate, String classDatosEspecificos,
			String classExcelProcesador, String excelTemplate, Boolean directo,
			String classExcelRespProcesador, String excelTemplateRespuesta,
			Preprocesadores[] preprocesadoreses,
			Postprocesadores[] postprocesadoresesSW, Sinonimos[] sinonimoses) {
		this.codigoCertificado = codigoCertificado;
		this.descripcion = descripcion;
		this.xpathAdjunto = xpathAdjunto;
		this.mimeTypeAdjunto = mimeTypeAdjunto;
		this.xmlTemplate = xmlTemplate;
		this.classDatosEspecificos = classDatosEspecificos;
		this.classExcelProcesador = classExcelProcesador;
		this.excelTemplate = excelTemplate;
		this.directo = directo;
		this.classExcelRespProcesador = classExcelRespProcesador;
		this.excelTemplateRespuesta = excelTemplateRespuesta;
		this.preprocesadoreses = preprocesadoreses;
		this.postprocesadoreses = postprocesadoreses;
		this.sinonimoses = sinonimoses;
	}

	public String getCodigoCertificado() {
		return this.codigoCertificado;
	}

	public void setCodigoCertificado(String codigoCertificado) {
		this.codigoCertificado = codigoCertificado;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getXpathAdjunto() {
		return this.xpathAdjunto;
	}

	public void setXpathAdjunto(String xpathAdjunto) {
		this.xpathAdjunto = xpathAdjunto;
	}

	public String getMimeTypeAdjunto() {
		return this.mimeTypeAdjunto;
	}

	public void setMimeTypeAdjunto(String mimeTypeAdjunto) {
		this.mimeTypeAdjunto = mimeTypeAdjunto;
	}

	public String getXmlTemplate() {
		return this.xmlTemplate;
	}

	public void setXmlTemplate(String xmlTemplate) {
		this.xmlTemplate = xmlTemplate;
	}

	public String getClassDatosEspecificos() {
		return this.classDatosEspecificos;
	}

	public void setClassDatosEspecificos(String classDatosEspecificos) {
		this.classDatosEspecificos = classDatosEspecificos;
	}


	public String getClassExcelProcesador() {
		return this.classExcelProcesador;
	}

	public void setClassExcelProcesador(String classExcelProcesador) {
		this.classExcelProcesador = classExcelProcesador;
	}

	public String getExcelTemplate() {
		return this.excelTemplate;
	}

	public void setExcelTemplate(String excelTemplate) {
		this.excelTemplate = excelTemplate;
	}

	public Boolean getDirecto() {
		return this.directo;
	}

	public void setDirecto(Boolean directo) {
		this.directo = directo;
	}

	public String getClassExcelRespProcesador() {
		return this.classExcelRespProcesador;
	}

	public void setClassExcelRespProcesador(String classExcelRespProcesador) {
		this.classExcelRespProcesador = classExcelRespProcesador;
	}

	public String getExcelTemplateRespuesta() {
		return this.excelTemplateRespuesta;
	}

	public void setExcelTemplateRespuesta(String excelTemplateRespuesta) {
		this.excelTemplateRespuesta = excelTemplateRespuesta;
	}

	public Preprocesadores[] getPreprocesadoreses() {
		return this.preprocesadoreses;
	}

	public void setPreprocesadoreses(Set<Preprocesadores> preprocesadoreses) {
		
		this.preprocesadoreses = new Preprocesadores [preprocesadoreses.size()];
        Iterator<Preprocesadores> iter = preprocesadoreses.iterator();
        int i = 0;
        while (iter.hasNext()) {
        	this.preprocesadoreses[i] = iter.next();
        	i++;
        }
 
	}

	public PostprocesadoresSW[] getPostprocesadoreses() {
		return this.postprocesadoreses;
	}

	public void setPostprocesadoreses(PostprocesadoresSW [] postprocesadoreses) {
		this.postprocesadoreses = postprocesadoreses;
		
	}

	public Sinonimos[] getSinonimoses() {
		return this.sinonimoses;
	}

	public void setSinonimoses(Set<Sinonimos> sinonimoses) {
		this.sinonimoses = new Sinonimos [sinonimoses.size()];
		Iterator<Sinonimos> iter = sinonimoses.iterator();
		int i = 0;
		while (iter.hasNext()) {
			this.sinonimoses[i] = iter.next();
			i++;
		}
	}

}
