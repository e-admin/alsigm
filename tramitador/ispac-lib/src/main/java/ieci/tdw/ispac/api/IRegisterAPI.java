package ieci.tdw.ispac.api;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;

import java.io.OutputStream;
import java.util.List;

public interface IRegisterAPI {
	
	public static final int BOOK_TYPE_INPUT 	= 1;
	public static final int BOOK_TYPE_OUTPUT	= 2;

	
	/**
	 * Indica si hay conector con registro configurado
	 * @return Cierto si existe conector configurado.
	 */
	public boolean existConnector();

	/**
	 * Indica si el usuario conectado tiene permisos para registrar.
	 * @param registerType Tipo de registro: @see RegisterType
	 * @return true si el usuario puede registrar.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean canRegister(String registerType) throws ISPACException;
	
	/**
	 * Obtiene la información completa de un registro.
	 * @param registerInfo Información básica del registro.
	 * @return Información completa del registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Register readRegister(RegisterInfo registerInfo) throws ISPACException;
	
	/**
	 * Crea un registro.
	 * @param register Información completa del registro.
	 * @return Información básica del registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public RegisterInfo insertRegister(Register register) throws ISPACException;

	/**
	 * Obtiene el número de registros distribuidos asociados al usuario conectado.
	 * @return Número de registros distribuidos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int countIntrais() throws ISPACException;

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario conectado.
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getIntrays() throws ISPACException;

	/**
	 * Obtiene la información del registro distribuido.
	 * @param register Número de registro.
	 * @return Información del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Intray getIntray(String register) throws ISPACException;

//	/**
//	 * Asocia un registro de distribución con un identificador de
//	 * proceso.
//	 * @param register identificador del registro de distribución
//	 * @param process identificador del proceso
//	 * @param type tipo de relación:
//	 * 	1: Inicio de expediente.
//	 * 	2: Inclusión en expediente.
//	 */
//	public void addToProccess(int register, int process, int type)
//			throws ISPACException;

//	/**
//	 * Distribuye un registro de entrada.
//	 * @param register identificador del registro.
//	 * @param state estado del registro.
//	 * @param responsible responsable a quien se le distribuye.
//	 * @param message mensaje asociado a la acción
//	 * @throws ISPACException
//	 */
//	public void distribute(int register, Responsible responsible,
//			String message, boolean archive) throws ISPACException;

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
//	 * Cambia el estado de un registro de distribución
//	 * @param register identificador del registro.
//	 * @param state estado actual.
//	 * @param newState nuevo estado.
//	 * @throws ISPACException
//	 */
//	public void changeState(int register, int state)
//			throws ISPACException;

	/**
	 * Obtiene los anexos del registro de entrada.
	 * @param register identificador del registro de distribución.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Annexe[] getAnnexes(String register) throws ISPACException;

	/**
	 * Descarga un anexo del registro de entrada.
	 * @param register identificador del registro de distribución.
	 * @param annexe identificador del anexo.
	 * @param out stream de salida.
	 * @throws ISPACException
	 */
	public void getAnnexe(String register, String annexe, OutputStream out)
			throws ISPACException;

	/**
	 * Vincula un expediente administrativo a un apunte de registro 
	 * @param register Información del registro
	 * @param numExpedient Nº de expediente administrativo
	 * @throws ISPACException
	 */
	public void linkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException;

	
	/**
	 * Desvincula un expediente administrativo de un apunte de registro
	 * @param register Información del registro
	 * @param numExpedient Nº de expediente administrativo
	 * @throws ISPACException
	 */	
	public void unlinkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException;	
	
	/**
	 * Obtiene los libros de registro por tipo de libro.
	 * @param bookType Tipo de libro (@see IRegisterAPI).
	 * @return Libros de registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getBooks(int bookType) throws ISPACException;
	
	/**
	 * Obtiene las oficinas de registro dependientes de un libro.
	 * @param bookId Identificador del libro.
	 * @return Oficinas de registro
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getOffices(String bookId) throws ISPACException;
	
	/**
	 * Obtiene la información de una organización a partir de su código.
	 * @param code Código de la organización.
	 * @return Información de la organización.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Organization getOrganizationByCode(String code) throws ISPACException;
	

	
}
