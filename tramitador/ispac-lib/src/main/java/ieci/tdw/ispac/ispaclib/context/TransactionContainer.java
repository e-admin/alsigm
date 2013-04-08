package ieci.tdw.ispac.ispaclib.context;

import ieci.tdw.ispac.api.IBPMAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

/**
 * Contenedor de transaccciones.
 */
public class TransactionContainer {
	
    private IClientContext context = null;
    private boolean previoustx = false;
    
    private IBPMAPI bpmAPI = null;
    private boolean error = false;

    
    /**
     * Constructor
     * @param context Contexto de cliente.
     */
    public TransactionContainer(IClientContext context) {
        this.context=context;
        this.previoustx=false;

		try {
			//Se obtiene el API del BPM el cual iniciara la sesion
			this.bpmAPI = context.getAPI().getBPMAPI();
		} catch (ISPACException e) {
			this.bpmAPI = null;
		}
    }
    
    /**
     * Inicia una transacción
     * @throws ISPACException si ocurre algún error.
     */
    public void begin() throws ISPACException {
		try {
			previoustx = context.ongoingTX();
			if (!previoustx) {
				context.beginTX();
			}
			if (bpmAPI != null) {
				bpmAPI.initBPMSession();
			}
		} catch (ISPACException e) {
			throw e;
		} catch (Exception e) {
			throw new ISPACException("Error en TransactionContainer.begin() ",
					e);
		}
	}

    /**
	 * Obtiene una conexión con la base de datos.
	 * @return Conexión con la base de datos.
	 * @throws ISPACException si ocurre algún error.
	 */
    public DbCnt getConnection() throws ISPACException {
		return context.getConnection();
	}

    /**
	 * Realiza un commit de la transacción.
	 * @throws ISPACException si ocurre algún error.
	 */
    public void commit() throws ISPACException {
		try {
			if (bpmAPI != null) {
				bpmAPI.closeBPMSession(true);
			}
			if (!previoustx) {
				context.endTX(true);
			}
		} catch (ISPACException e) {
			throw e;
		} catch (Exception e) {
			throw new ISPACException("Error en TransactionContainer.commit()",
					e);
		}
	}

    /**
     * Libera la transacción
     * @throws ISPACException si ocurre algún error.
     */
    public void release() throws ISPACException {
		try {
			if (!previoustx) {
				context.releaseTX();
			}
			if (error && (bpmAPI != null)) {
				bpmAPI.closeBPMSession(false);
			}
		} catch (ISPACException e) {
			throw e;
		} catch (Exception e) {
			throw new ISPACException(
					"Error en TransactionContainer.release() ", e);
		}
	}

    /**
     * Obtiene el API de BPM.
     * @return API de BPM.
     */
	public IBPMAPI getBPMAPI() {
		return bpmAPI;
	}

	/**
	 * Establece si hay error en la transacción.
	 * @param error true si hay un error en la transacción.
	 */
	public void setError(boolean error) {
		this.error = error;
	}
}
