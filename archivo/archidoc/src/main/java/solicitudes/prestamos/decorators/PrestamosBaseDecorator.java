package solicitudes.prestamos.decorators;

import org.displaytag.decorator.TableDecorator;

import solicitudes.prestamos.vos.PrestamoVO;

/**
 * @author x44906ga
 * 
 */
public class PrestamosBaseDecorator extends TableDecorator {

	// Devuelve el id del prestamo como año\id
	public String getIdentificadorCompleto() {
		String ret = "";
		ret += ((PrestamoVO) getCurrentRowObject()).getAno();
		ret += "\\";
		ret += ((PrestamoVO) getCurrentRowObject()).getId();
		return ret;
	}

}
