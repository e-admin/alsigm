package ieci.tdw.ispac.services.mgr;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.services.db.CatalogoDAO;
import ieci.tdw.ispac.services.db.DbCommand;
import ieci.tdw.ispac.services.dto.InfoBProcedimiento;
import ieci.tdw.ispac.services.dto.Procedimiento;

import java.util.List;

public class CatalogoManager extends ServiceManager {

	/**
	 * Constructor.
	 */
	private CatalogoManager() {
		super();
	}
	
	/**
	 * Obtiene una instancia del manager.
	 * @return Instancia del manager.
	 */
	public static CatalogoManager getInstance() {
		return new CatalogoManager();
	}
	
	/**
	 * Recupera la lista de procedimientos del tipo que se indica en 
	 * tipoProc, con su información básica.
	 * @param tipoProc Tipo de procedimiento.
	 * <p>Valores posibles de tipoProc (@see InfoBProcedimiento):
	 * <li>1 - Todos</li>
	 * <li>2 - Procedimientos  automatizados</li>
	 * <li>3 - Procedimientos no automatizados</li>
	 * </p>
	 * @param nombre Nombre del procedimiento.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(final int tipoProc, 
			final String nombre) throws ISPACException {
		DbCommand dbCommand = new DbCommand(getContext()) {
			public Object logic(DbCnt cnt) throws ISPACException {
				return CatalogoDAO.getProcedimientos(cnt, tipoProc, nombre);
			}
		};
		List procedimientos = (List) dbCommand.exec();
		return (InfoBProcedimiento[]) procedimientos
				.toArray(new InfoBProcedimiento[procedimientos.size()]);
	}

	/**
	 * Recupera la lista de procedimientos cuyos identificadores se 
	 * incluyen en el parámetro idProcs.
	 * @param idProcs Lista de identificadores de procedimientos.
	 * @return Lista de información de procedimientos.
	 */
	public InfoBProcedimiento[] getProcedimientos(final String[] idProcs) 
			throws ISPACException {
		DbCommand dbCommand = new DbCommand(getContext()) {
			public Object logic(DbCnt cnt) throws ISPACException {
				return CatalogoDAO.getProcedimientos(cnt, idProcs);
			}
		};
		
		List procedimientos = (List) dbCommand.exec();
		return (InfoBProcedimiento[]) procedimientos
				.toArray(new InfoBProcedimiento[procedimientos.size()]);
	}

	/**
	 * Recupera la información de un procedimiento cuyo identificador 
	 * único es idProc.
	 * @param idProc Identificador de procedimiento.
	 * @return Información del procedimiento.
	 */
	public Procedimiento getProcedimiento(final String idProc) 
			throws ISPACException {
		DbCommand dbCommand = new DbCommand(getContext()) {
			public Object logic(DbCnt cnt) throws ISPACException {
				return CatalogoDAO.getProcedimiento(cnt, idProc);
			}
		};
		return (Procedimiento) dbCommand.exec();
	}

}
