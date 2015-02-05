package fondos.exceptions;

import java.util.ArrayList;
import java.util.List;

import se.procedimientos.IOrganoProductor;

import common.exceptions.CheckedArchivoException;

/**
 * Excepción que se produce cuando se trata de vincular un procedimiento a una
 * serie durante su identificación y alguno de los productores del procedimiento
 * todavía no han sido incorporados al sistema
 */
public class ProductorProcedimientoNoIncorporadoException extends
		CheckedArchivoException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Conjunto de productores del procedimiento no presentes en el sistema */
	List productores = null;

	public ProductorProcedimientoNoIncorporadoException(List productores) {
		this.productores = productores;
	}

	public void addProductor(IOrganoProductor productorCatalogoVO) {
		if (productores == null)
			productores = new ArrayList();

		productores.add(productorCatalogoVO);
	}

	public List getProductores() {
		return productores;

	}

}