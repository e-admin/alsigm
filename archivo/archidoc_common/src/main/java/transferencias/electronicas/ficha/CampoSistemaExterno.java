package transferencias.electronicas.ficha;

import se.instituciones.TipoAtributo;
import transferencias.TransferenciasElectronicasConstants;
import transferencias.electronicas.common.CampoFichaBase;

public class CampoSistemaExterno  extends CampoFichaBase{


	/**
	 * Tipo de sistema externo
	 */
	private String tipoSistema;


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

	private String tipoAtributo;

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
	 * Indica si es un tipo de sistema externo de organización
	 * @return
	 */
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

	public String getTipoElemento() {
		return TransferenciasElectronicasConstants.CAMPO_SISTEMA_EXTERNO;
	}

}
