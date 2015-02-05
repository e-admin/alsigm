/**
 *
 */
package es.ieci.tecdoc.isicres.api.business.helper;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.isicres.session.book.BookSession;

import es.ieci.tecdoc.isicres.api.business.exception.LibroException;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.BaseRegistroVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;

/**
 * @author Iecisa
 * @version $Revision$
 *
 */

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 *          Clase que contiene metodos tipicos a realizar con la informacion de
 *          Libro
 *
 */
public class LibroHelper {

	private static final Logger logger = Logger.getLogger(LibroHelper.class);

	/**
	 * Método para obtener los datos basicos del libro que se encuentran en los
	 * datos basicos de registro
	 *
	 * @param registro
	 * @param libro
	 * @return
	 */
	public static BaseLibroVO obtenerDatosBasicosLibro(BaseRegistroVO registro,
			BaseLibroVO libro) {

		if (registro != null) {
			libro.setId(registro.getIdLibro());
		}

		return libro;
	}

	/**
	 * Método para obtener el formato del libro
	 *
	 * @param usuario
	 * @param libro
	 * @return
	 */
	public static AxSf getBookFormFormat(UsuarioVO usuario, BaseLibroVO libro) {
		AxSf axsfQ = null;
		try {
			axsfQ = BookSession.getFormFormat(usuario.getConfiguracionUsuario()
					.getSessionID(), new Integer(libro.getId()), usuario
					.getConfiguracionUsuario().getIdEntidad());
		} catch (Exception e) {
			logger.debug("Error obteniento el formato del libro", e);
			throw new LibroException("Error obteniento el formato del libro");
		}

		return axsfQ;

	}

}
