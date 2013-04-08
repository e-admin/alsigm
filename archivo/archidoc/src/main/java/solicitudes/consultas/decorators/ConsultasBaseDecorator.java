package solicitudes.consultas.decorators;

import org.displaytag.decorator.TableDecorator;

import solicitudes.consultas.vos.ConsultaVO;

/**
 *  
 */
public class ConsultasBaseDecorator extends TableDecorator {

	// Devuelve el id de la consulta como año\id
	public String getIdentificadorCompleto() {
		String ret = null;
		ret += ((ConsultaVO) getCurrentRowObject()).getAno();
		ret += "\\";
		ret += ((ConsultaVO) getCurrentRowObject()).getId();
		return ret;

	}
}
