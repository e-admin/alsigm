
package transferencias.electronicas.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import common.util.ListUtils;

public abstract class BaseTransferenciaElectronicaVO implements IObjetoValido{

	private List<String> errores = new ArrayList<String>();


	public boolean isConErrores(){
		errores = new ArrayList<String>();

		validate();

		if(ListUtils.isNotEmpty(errores)){
			return true;
		}

		return false;
	}

	/**
	 * @return el objeto errores
	 */
	public List<String> getErrores() {
		return errores;
	}


	/**
	 * @param errores el objeto errores a fijar
	 */
	protected void setErrores(List<String> errores) {
		this.errores = errores;
	}

	/**
	 * Comprueba los campos obligatorios
	 * @param valor
	 * @param error
	 */
	protected void comprobarFecha(String valor, Fecha fecha){
		if(StringUtils.isBlank(valor)){

		}
	}

	protected void addError(String codigoError){
		if(StringUtils.isNotBlank(codigoError)){
			errores.add(codigoError);
		}
	}
}
