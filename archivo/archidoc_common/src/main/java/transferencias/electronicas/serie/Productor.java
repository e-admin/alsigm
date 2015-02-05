package transferencias.electronicas.serie;

import java.util.Date;

import se.instituciones.InfoOrgano;
import se.instituciones.TipoAtributo;
import se.procedimientos.IOrganoProductor;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;
import transferencias.electronicas.common.Fecha;

public class Productor extends CampoFichaBase implements IOrganoProductor{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String tipoAtributo;

	private String tipoSistema;

	private Fecha fechaInicio;

	/**
	 * Información del órgano obtenida del sistema de organización
	 */
	private InfoOrgano infoOrgano;

	/**
	 * @return el tipoAtributo
	 */
	public String getTipoAtributo() {
		return tipoAtributo;
	}

	/**
	 * @param tipoAtributo el tipoAtributo a fijar
	 */
	public void setTipoAtributo(String tipoAtributo) {
		this.tipoAtributo = tipoAtributo;
	}

	/**
	 * @return el tipoSistema
	 */
	public String getTipoSistema() {
		return tipoSistema;
	}

	/**
	 * @param tipoSistema el tipoSistema a fijar
	 */
	public void setTipoSistema(String tipoSistema) {
		this.tipoSistema = tipoSistema;
	}

	/**
	 * @return el fechaInicio
	 */
	public Fecha getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio el fechaInicio a fijar
	 */
	public void setFechaInicio(Fecha fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public boolean isTipoSistemaOrganizacion(){
		return TransferenciasElectronicasConstants.SE_ORGANIZACION.equals(tipoSistema);
	}

	/**
	 * Indica que el atributo es un código
	 * @return
	 */
	public boolean isAtributoCodigo(){
		return TransferenciasElectronicasConstants.TIPO_ATRIBUTO_SE_CODIGO.equalsIgnoreCase(tipoAtributo);
	}

	/**
	 * Indica si el atributo es un Identificador
	 * @return
	 */
	public boolean isAtributoId(){
		return TransferenciasElectronicasConstants.TIPO_ATRIBUTO_SE_ID.equalsIgnoreCase(tipoAtributo);
	}

	public short getAtributo(){
		if(isAtributoId()){
			return TipoAtributo.IDENTIFICADOR_ORGANO;
		}
		if(isAtributoCodigo()){
			return TipoAtributo.CODIGO_ORGANO;
		}
		return 0;
	}

	public String getId() {
		if(isAtributoId()){
			return getValor();
		}
		else{
			return null;
		}
	}

	public Date getInicioProduccion() {
		if(fechaInicio != null){
			return getFechaInicio().asDate();
		}
		return null;
	}

	/**
	 * @param infoOrgano el objeto infoOrgano a fijar
	 */
	public void setInfoOrgano(InfoOrgano infoOrgano) {
		this.infoOrgano = infoOrgano;
	}

	/**
	 * @return el objeto infoOrgano
	 */
	public InfoOrgano getInfoOrgano() {
		return infoOrgano;
	}

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.PRODUCTOR;
	}

}
