package ieci.tecdoc.sgm.registropresencial.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.isicres.session.book.BookSession;
import com.ieci.tecdoc.isicres.session.distribution.DistributionSession;

/*$Id*/

public class SigemFunctions {
	public static final Logger logger = Logger.getLogger(SigemFunctions.class);

	/**
	 * Metodo que obtiene el id de los libros, que pertenecen al tipo
	 * (1-Entrada/2-Salida) pasado como parametro y en lo cuales tienen
	 * registros distribuidos de entrada para el usuario logeado con el numero
	 * de registro pasado también como parametro
	 *
	 * @param sessionId
	 * @param numRegister - Numero de Registro a buscar
	 * @param entidad
	 * @param type - (1-Entrada / 2- Salida / 0 - Obtiene todos los libros)
	 * @return Collection  de Ids de los libros, según el tipo, que cumplen el criterio de busqueda.
	 */
	public static Set booksDistributed(String sessionId, String numRegister,
			Locale locale, String entidad, int type) {
		List folderIds = new ArrayList();
		Set bookIds = new HashSet();
		try {
			StringBuffer sb = new StringBuffer("@FLD1 = '").append(numRegister)
					.append("'");
			List booksOpenList = BookSession.getBooksOpenByType(sessionId,
					type, entidad);

			folderIds.addAll(DistributionSession.getDistribution(sessionId, -1,
					0, 1, DistributionSession.DISTRIBUCION_IN_DIST, "",
					sb.toString(), true, locale, entidad, booksOpenList)
					.getBooks());

			for (Iterator iter = folderIds.iterator(); iter.hasNext();) {
				String folderId = iter.next().toString();
				bookIds.add(Integer.valueOf(folderId.split("_")[0]));
			}
		} catch (Exception e) {
			logger
					.error(
							"Error comprobando si el usuario tiene acceso al registro como distribución",
							e);
		}
		return bookIds;
	}

	public static boolean isDistributed(String sessionId, Integer bookId,
			Integer folderId, Locale locale, String entidad) {
		List folderIds = new ArrayList();

		try {
			StringBuffer sb_Reg = new StringBuffer("ID_ARCH = ")
					.append(bookId).append(" AND ").append("ID_FDR = ")
					.append(folderId);
			folderIds.addAll(DistributionSession.getDistribution(sessionId, -1,
					0, 1, DistributionSession.DISTRIBUCION_IN_DIST,
					sb_Reg.toString(), "", true, locale, entidad).getBooks());
			folderIds.addAll(DistributionSession.getDistribution(sessionId, -1,
					0, 1, DistributionSession.DISTRIBUCION_OUT_DIST,
					sb_Reg.toString(), "", true, locale, entidad).getBooks());

		} catch (Exception e) {
			logger
					.error(
							"Error comprobando si el usuario tiene acceso al registro como distribución",
							e);
		}
		StringBuffer sb = new StringBuffer().append(bookId).append("_").append(
				folderId);
		return folderIds.contains(sb.toString());
	}
}
