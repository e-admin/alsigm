package es.ieci.tecdoc.fwktd.dir3.api.vo.oficina;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Datos_Operativos")
public class OficinaDatosOperativosVO {
	@XStreamAlias("Indicador_Oficina_Registro")
	private String indicadorOficinaRegistro;
	@XStreamAlias("Indicador_Oficina_Informacion")
	private String indicadorOficinaInformacion;
	@XStreamAlias("Indicador_Oficina_Tramitacion")
	private String indicadorOficinaTramitacion;
	@XStreamAlias("Indicador_Registro_Electronico")
	private String indicadorRegistroElectronico;
	@XStreamAlias("Indicador_Interop_Sin_Restriccion")
	private String indicadorInteropSinRestriccion;
	@XStreamAlias("Indicador_Interop_Local_Estatal")
	private String indicadorInteropLocalEstatal;
	@XStreamAlias("Indicador_Interop_Local_Autonomica_res")
	private String indicadorInteropLocalAutonomicares;
	@XStreamAlias("Indicador_Interop_Local_Local_res")
	private String indicadorInteropLocalLocalres;
	@XStreamAlias("Indicador_Interop_Local_Autonomica_gen")
	private String indicadorInteropLocalAutonomicagen;
	@XStreamAlias("Indicador_Interop_Local_Local_gen")
	private String indicadorInteropLocalLocalgen;
	@XStreamAlias("Indicador_Interop_Ayuntamiento_Ayuntamiento")
	private String indicadorInteropAyuntamientoAyuntamiento;
	@XStreamAlias("Indicador_SIR")
	private String indicadorSIR;
	
	public String getIndicadorOficinaRegistro() {
		return indicadorOficinaRegistro;
	}
	public void setIndicadorOficinaRegistro(String indicadorOficinaRegistro) {
		this.indicadorOficinaRegistro = indicadorOficinaRegistro;
	}
	public String getIndicadorOficinaInformacion() {
		return indicadorOficinaInformacion;
	}
	public void setIndicadorOficinaInformacion(String indicadorOficinaInformacion) {
		this.indicadorOficinaInformacion = indicadorOficinaInformacion;
	}
	public String getIndicadorOficinaTramitacion() {
		return indicadorOficinaTramitacion;
	}
	public void setIndicadorOficinaTramitacion(String indicadorOficinaTramitacion) {
		this.indicadorOficinaTramitacion = indicadorOficinaTramitacion;
	}
	public String getIndicadorRegistroElectronico() {
		return indicadorRegistroElectronico;
	}
	public void setIndicadorRegistroElectronico(String indicadorRegistroElectronico) {
		this.indicadorRegistroElectronico = indicadorRegistroElectronico;
	}
	public String getIndicadorInteropSinRestriccion() {
		return indicadorInteropSinRestriccion;
	}
	public void setIndicadorInteropSinRestriccion(
			String indicadorInteropSinRestriccion) {
		this.indicadorInteropSinRestriccion = indicadorInteropSinRestriccion;
	}
	public String getIndicadorInteropLocalEstatal() {
		return indicadorInteropLocalEstatal;
	}
	public void setIndicadorInteropLocalEstatal(String indicadorInteropLocalEstatal) {
		this.indicadorInteropLocalEstatal = indicadorInteropLocalEstatal;
	}
	public String getIndicadorInteropLocalAutonomicares() {
		return indicadorInteropLocalAutonomicares;
	}
	public void setIndicadorInteropLocalAutonomicares(
			String indicadorInteropLocalAutonomicares) {
		this.indicadorInteropLocalAutonomicares = indicadorInteropLocalAutonomicares;
	}
	public String getIndicadorInteropLocalLocalres() {
		return indicadorInteropLocalLocalres;
	}
	public void setIndicadorInteropLocalLocalres(
			String indicadorInteropLocalLocalres) {
		this.indicadorInteropLocalLocalres = indicadorInteropLocalLocalres;
	}
	public String getIndicadorInteropLocalAutonomicagen() {
		return indicadorInteropLocalAutonomicagen;
	}
	public void setIndicadorInteropLocalAutonomicagen(
			String indicadorInteropLocalAutonomicagen) {
		this.indicadorInteropLocalAutonomicagen = indicadorInteropLocalAutonomicagen;
	}
	public String getIndicadorInteropLocalLocalgen() {
		return indicadorInteropLocalLocalgen;
	}
	public void setIndicadorInteropLocalLocalgen(
			String indicadorInteropLocalLocalgen) {
		this.indicadorInteropLocalLocalgen = indicadorInteropLocalLocalgen;
	}
	public String getIndicadorInteropAyuntamientoAyuntamiento() {
		return indicadorInteropAyuntamientoAyuntamiento;
	}
	public void setIndicadorInteropAyuntamientoAyuntamiento(
			String indicadorInteropAyuntamientoAyuntamiento) {
		this.indicadorInteropAyuntamientoAyuntamiento = indicadorInteropAyuntamientoAyuntamiento;
	}
	public String getIndicadorSIR() {
		return indicadorSIR;
	}
	public void setIndicadorSIR(String indicadorSIR) {
		this.indicadorSIR = indicadorSIR;
	}
}
