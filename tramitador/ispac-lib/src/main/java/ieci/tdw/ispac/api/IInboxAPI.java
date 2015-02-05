package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;

import java.io.OutputStream;
import java.util.List;

/**
 * &quot;Bandejas de Entrada&quot; del tramitador
 *  De momento existen dos tipos de bandejas de entrada: Avisos y registros de entrada
 */

public interface IInboxAPI {

	public static final int DIST_ESTADO_PENDIENTE 	= 1;
	public static final int DIST_ESTADO_ACEPTADA	= 2;

	public static final int CREADO = 1;
	public static final int ANEXADO = 2;

	/**
	 * Obtiene todas las posibles bandejas y el n&uacute;mero
	 * de items en cada una de ellas que son responsabilidad del
	 * usuario conectado.
    *
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getInbox()
	throws ISPACException;

	/**
	 * Obtiene todas las posibles bandejas y el n&uacute;mero
	 * de items en cada una de ellas que son responsabilidad del
	 * usuario conectado.
    *
	 * @param resp Responsabilidades del usuario conectado (supervisar y sustituir)
	 * @return
	 * @throws ISPACException
	 */
	public IItemCollection getInbox(String resp)
	throws ISPACException;

	/**
	 * Obtiene la lista de avisos pendientes para el usuario conectado
	 * @return IItemCollection con los avisos.
	 * @throws ISPACException
	 */
	public IItemCollection getNotices()
	throws ISPACException;

	/**
	 * Cambia el estado de un aviso.
	 * @param noticeId identificador del aviso.
	 * @param newstate el nuevo estado del apunte.
	 * @throws ISPACException
	 */
	public void modifyNotice(int noticeId, int newstate)
	throws ISPACException;

	/**
	 * Obtiene la información del registro distribuido.
	 * @param register Número de registro.
	 * @return Información del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Intray getIntray(String register) throws ISPACException;

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario conectado.
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getIntrays() throws ISPACException;

//	/**
//	 * Asocia un registro de entrada con un expediente
//	 * @param register
//	 * @param process el identificador de  proceso del expediente.
//	 * @param type tipo de asociaci&oacute;n: &lt;code&gt;IInboxAPI.CREADO&lt;/code&gt; o &lt;code&gt;IInboxAPI.ANEXADO&lt;/code&gt;
//	 * @throws ISPACException
//	 */
//	public void addToProccess( String register, int process, int type)
//	throws ISPACException;

	/**
	 * Inicia un expediente a partir de un registro distribuido.
	 * @param register Número de registro distribuido.
	 * @param pcdId Identificador del procedimiento.
	 * @return Identificador del proceso creado.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int createProcess(String register, int pcdId) throws ISPACException;

	/**
	 * Anexa el registro distribuido al expediente.
	 * @param register Número de registro distribuido.
	 * @param numExp Número de expediente.
	 * @throws ISPACException si ocurre algún error.
	 */
	public void annexToProcess(String register, String numExp) throws ISPACException;

	/**
	 * Anexa el registro distribuido al expediente.
	 * @param register Número de registro distribuido.
	 * @param numExp Número de expediente.
	 * @param taskid Identificador del trámite instanciando en el expediente al que se anexarán los documentos del apunte de registro
	 * @throws ISPACException si ocurre algún error.
	 */
	public void annexToProcess(String register, String numExp, int taskId) throws ISPACException;

	/**
    * Anexa el registro distribuido al expediente.
    * @param register Número de registro distribuido.
    * @param numExp Número de expediente.
    * @param taskid Identificador del trámite instanciando en el expediente al que se anexarán los documentos del apunte de registro
    * @param typeDocId Identificador del tipo de documento que se asignará a los documentos del apunte de registro
    * @throws ISPACException si ocurre algún error.
    */
	public void annexToProcess(String register, String numExp, int taskId, int typeDocId) throws ISPACException;

//	/**
//	 * Distribuye un registro de entrada.
//	 */
//	public void distribute( String register, Responsible responsible, String message, boolean archive)
//	throws ISPACException;

	/**
	 * Acepta un registro distribuido.
	 * @param register Número de registro.
    * @throws ISPACException si ocurre algún error.
	 */
	public void acceptIntray(String register) throws ISPACException;

	/**
	 * Rechaza un registro distribuido.
	 * @param register Número de registro.
	 * @param reason Motivo del rechazo.
    * @throws ISPACException si ocurre algún error.
	 */
	public void rejectIntray(String register, String reason) throws ISPACException;

	/**
	 * Archiva un registro distribuido.
	 * @param register Número de registro.
    * @throws ISPACException si ocurre algún error.
	 */
	public void archiveIntray(String register) throws ISPACException;

//	/**
//	 * Cambia el estado de un registro de entrada
//	 */
//	public void changeState( String register, int state)
//	throws ISPACException;

	/**
	 * Obtiene los identificadores de los anexos del registro de entrada.
	 */
	public Annexe[] getAnnexes( String register) throws ISPACException;

	/**
	 * Descarga un anexo del registro de entrada.
	 */
	public void getAnnexe( String register, String annexe, OutputStream out)
		throws ISPACException;
}
