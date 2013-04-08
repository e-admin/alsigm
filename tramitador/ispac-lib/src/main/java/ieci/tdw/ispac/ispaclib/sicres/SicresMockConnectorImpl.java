package ieci.tdw.ispac.ispaclib.sicres;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterBook;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterData;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOffice;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOrigin;
import ieci.tdw.ispac.ispaclib.sicres.vo.Subject;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class SicresMockConnectorImpl implements ISicresConnector {
	
    /** 
     * Logger de la clase. 
     */
	protected static final Logger logger = Logger.getLogger(SicresMockConnectorImpl.class);
	
    private List<Intray> intrayList = new ArrayList<Intray>();
    
    private List<Annexe> annexeList = new ArrayList<Annexe>();
    
    private Map<Integer, List<RegisterBook>> bookListMap = new HashMap<Integer, List<RegisterBook>>();
    
    private Map<String, List<RegisterOffice>> bookOfficeListMap = new HashMap<String, List<RegisterOffice>>();
    
    private Map<String, Organization> organizationMap = new HashMap<String, Organization>();
    
	/**
	 * Constructor
	 * @throws ISPACException si ocurre algún error.
	 */
	public SicresMockConnectorImpl() throws ISPACException {
	}
	
	/**
	 * Constructor
	 * @param ctx Contexto de cliente.
	 * @throws ISPACException si ocurre algún error.
	 */
	public SicresMockConnectorImpl(ClientContext ctx) throws ISPACException {
		this();
	}

	public boolean canRegister(String registerType) throws ISPACException {
		return true;
	}

	public Register readRegister(RegisterInfo registerInfo)
			throws ISPACException {
		
		Register register = null;
		
		if (registerInfo != null) {
			
			RegisterOrigin registerOrigin = null;

			RegisterData registerData = new RegisterData(
					new ThirdPerson[] { new ThirdPerson("1", "Persona 1") },
					new Subject("000", "Asunto del registro"),
					"Resumen del registro", new DocumentInfo[0]);
			
		    Organization originOrganization = null; //new Organization(id, code, name, acronym, creationDate, disabledDate);
		    Organization destinationOrganization = null; //new Organization(id, code, name, acronym, creationDate, disabledDate);
		    
		    register = new Register(registerOrigin, registerData,
					originOrganization, destinationOrganization,
					registerInfo, null);
			
		}
		
		return register;
	}

	public RegisterInfo insertRegister(Register register) throws ISPACException {
		if (register != null) {
			return register.getOriginalRegister();
		}
		return null;
	}

	public int countIntrais() throws ISPACException {
		return intrayList.size();
	}

	public List<Intray> getIntrays() throws ISPACException {
		return intrayList;
	}

	public Intray getIntray(String intrayId) throws ISPACException {
		
		if (StringUtils.isNotBlank(intrayId)) {
			for (Intray intray : intrayList) {
				if (intrayId.equalsIgnoreCase(intray.getId())) {
					return intray;
				}
			}
		}
		
		return null;
	}

	public void acceptIntray(String intrayId) throws ISPACException {
	}

	public void rejectIntray(String intrayId, String reason)
			throws ISPACException {
	}

	public void archiveIntray(String intrayId) throws ISPACException {
	}

	public Annexe[] getAnnexes(String intrayId) throws ISPACException {
		return (Annexe[]) annexeList.toArray(new Annexe[annexeList.size()]);
	}

	public void getAnnexe(String intrayId, String annexe, OutputStream out)
			throws ISPACException {
	}

	public Annexe[] getAnnexes(String registerNumber, String registerType)
			throws ISPACException {
		return (Annexe[]) annexeList.toArray(new Annexe[annexeList.size()]);
	}

	public void getAnnexe(Annexe annexe, OutputStream out) {
	}
	
	
	public void linkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
	}

	public void unlinkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
	}

	public List<RegisterBook> getBooks(int bookType) throws ISPACException {
		return bookListMap.get(bookType);
	}

	public List<RegisterOffice> getOffices(String bookId) throws ISPACException {
		return bookOfficeListMap.get(bookId);
	}

	public Organization getOrganizationByCode(String code)
			throws ISPACException {
		return organizationMap.get(code);
	}


}