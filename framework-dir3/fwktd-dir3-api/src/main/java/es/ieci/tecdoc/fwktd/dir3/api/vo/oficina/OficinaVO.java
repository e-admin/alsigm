package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class OficinaVO {
	@XStreamAlias("Datos_Identificativos")
	private OficinaDatosIdentificativosVO datosIdentificativos;
	@XStreamAlias("Datos_Dependencia_Jerarquica")
	private OficinaDatosDependenciaJerarquicaVO datosDependenciaJerarquica;
	@XStreamAlias("Datos_Operativos")
	private OficinaDatosOperativosVO datosOperativos;
	@XStreamAlias("Datos_Localizacion")
	private OficinaDatosLocalizacionVO datosLocalizacion;
	@XStreamAlias("Datos_Vigencia")
	private OficinaDatosVigenciaVO datosVigencia;
	
	public OficinaDatosIdentificativosVO getDatosIdentificativos() {
		return datosIdentificativos;
	}
	public void setDatosIdentificativos(OficinaDatosIdentificativosVO datosIdentificativos) {
		this.datosIdentificativos = datosIdentificativos;
	}
	public OficinaDatosDependenciaJerarquicaVO getDatosDependenciaJerarquica() {
		return datosDependenciaJerarquica;
	}
	public void setDatosDependenciaJerarquica(
			OficinaDatosDependenciaJerarquicaVO datosDependenciaJerarquica) {
		this.datosDependenciaJerarquica = datosDependenciaJerarquica;
	}
	public OficinaDatosOperativosVO getDatosOperativos() {
		return datosOperativos;
	}
	public void setDatosOperativos(OficinaDatosOperativosVO datosOperativos) {
		this.datosOperativos = datosOperativos;
	}
	public OficinaDatosLocalizacionVO getDatosLocalizacion() {
		return datosLocalizacion;
	}
	public void setDatosLocalizacion(OficinaDatosLocalizacionVO datosLocalizacion) {
		this.datosLocalizacion = datosLocalizacion;
	}
	public OficinaDatosVigenciaVO getDatosVigencia() {
		return datosVigencia;
	}
	public void setDatosVigencia(OficinaDatosVigenciaVO datosVigencia) {
		this.datosVigencia = datosVigencia;
	}
}
