package es.ieci.tecdoc.fwktd.dir3.api.vo.unidad;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Organismo")
public class OrganismoVO {

	@XStreamAlias("Datos_Identificativos")
	private OrganismoDatosIdentificativosVO datosIdentificativos;

	@XStreamAlias("Datos_Dependencia")
	private OrganismoDatosDependenciaVO datosDependencia;

	@XStreamAlias("Datos_Vigencia")
	private OrganismoDatosVigenciaVO datosVigencia;

	public OrganismoDatosIdentificativosVO getDatosIdentificativos() {
		return datosIdentificativos;
	}

	public void setDatosIdentificativos(
			OrganismoDatosIdentificativosVO datosIdentificativos) {
		this.datosIdentificativos = datosIdentificativos;
	}

	public OrganismoDatosDependenciaVO getDatosDependencia() {
		return datosDependencia;
	}

	public void setDatosDependencia(OrganismoDatosDependenciaVO datosDependencia) {
		this.datosDependencia = datosDependencia;
	}

	public OrganismoDatosVigenciaVO getDatosVigencia() {
		return datosVigencia;
	}

	public void setDatosVigencia(OrganismoDatosVigenciaVO datosVigencia) {
		this.datosVigencia = datosVigencia;
	}

}
