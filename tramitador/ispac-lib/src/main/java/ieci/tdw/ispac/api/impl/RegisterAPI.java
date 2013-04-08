package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.SicresConnectorFactory;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;

import java.io.OutputStream;
import java.util.List;

public class RegisterAPI implements IRegisterAPI {

//	/**
//	 * Logger de la clase.
//	 */
//	private static final Logger logger = Logger.getLogger(RegisterAPI.class);
//	
//	/**
//	 * Contexto del cliente.
//	 */
//    private ClientContext context = null;

	/**
	 * Conector con SICRES.
	 */
	private ISicresConnector sicresConnector = null;

    
    /**
     * Constructor.
     * @param context Contexto del cliente.
     * @throws ISPACException 
     */
	public RegisterAPI(ClientContext context) throws ISPACException {
//		this.context = context;
		sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(context);
	}

	public boolean existConnector(){
		return sicresConnector != null;
	}

	public void acceptIntray(String register) throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.acceptIntray(register);
		}
		
	}


	public void archiveIntray(String register) throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.archiveIntray(register);
		}
	}


	public int countIntrais() throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.countIntrais();
		}
		return 0;
	}


	public void getAnnexe(String register, String annexe, OutputStream out)
			throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.getAnnexe(register, annexe, out);
		}
	}


	public Annexe[] getAnnexes(String register) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getAnnexes(register);
		}
		return null;
	}


	public Intray getIntray(String register) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getIntray(register);
		}
		return null;
	}


	public List getIntrays() throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getIntrays();
		}
		return null;
	}

	/**
	 * Indica si el usuario conectado tiene permisos para registrar.
	 * @param registerType Tipo de registro: @see RegisterType
	 * @return true si el usuario puede registrar.
	 * @throws ISPACException si ocurre algún error.
	 */
	public boolean canRegister(String registerType) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.canRegister(registerType);
		}
		return false;
	}


	public RegisterInfo insertRegister(Register register) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.insertRegister(register);
		}
		return null;
	}


	public Register readRegister(RegisterInfo registerInfo)
			throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.readRegister(registerInfo);
		}
		return null;
	}


	public void rejectIntray(String register, String reason)
			throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.rejectIntray(register, reason);
		}
	}

	public void linkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.linkExpedient(registerInfo, numExpedient);
		}
	}

	public void unlinkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		if (sicresConnector != null){
			sicresConnector.unlinkExpedient(registerInfo, numExpedient);
		}
	}

	/**
	 * Obtiene los libros de registro por tipo de libro.
	 * @param bookType Tipo de libro (@see IRegisterAPI).
	 * @return Libros de registro.
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getBooks(int bookType) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getBooks(bookType);
		}
		return null;
	}
	
	/**
	 * Obtiene las oficinas de registro dependientes de un libro.
	 * @param bookId Identificador del libro.
	 * @return Oficinas de registro
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getOffices(String bookId) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getOffices(bookId);
		}
		return null;
	}

	/**
	 * Obtiene la información de una organización a partir de su código.
	 * @param code Código de la organización.
	 * @return Información de la organización.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Organization getOrganizationByCode(String code) throws ISPACException {
		if (sicresConnector != null){
			return sicresConnector.getOrganizationByCode(code);
		}
		return null;
	}
	

	
	
}