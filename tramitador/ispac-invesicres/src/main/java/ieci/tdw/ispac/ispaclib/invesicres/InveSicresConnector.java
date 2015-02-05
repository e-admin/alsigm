package ieci.tdw.ispac.ispaclib.invesicres;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItemCollection;

import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.CollectionDAO;
import ieci.tdw.ispac.ispaclib.dao.ObjectDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.invesicres.intray.SicresIntray;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.GroupLDAPDAO;
import ieci.tdw.ispac.ispaclib.invesicres.intray.dao.UserLDAPDAO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.api.InvesDocConnection;
import ieci.tdw.ispac.ispaclib.invesicres.registro.api.Registro;
import ieci.tdw.ispac.ispaclib.invesicres.registro.api.RegistroEntrada;
import ieci.tdw.ispac.ispaclib.invesicres.registro.api.RegistroSalida;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.DocumentoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.InteresadoVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.RegistroEntradaVO;
import ieci.tdw.ispac.ispaclib.invesicres.registro.vo.RegistroSalidaVO;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.session.OrganizationUser;
import ieci.tdw.ispac.ispaclib.session.OrganizationUserInfo;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.DocumentInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.Intray;
import ieci.tdw.ispac.ispaclib.sicres.vo.Organization;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterData;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOffice;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterOrigin;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.Subject;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.sicres.vo.Transport;
import ieci.tdw.ispac.ispaclib.util.FileTemplateManager;
import ieci.tdw.ispac.ispaclib.util.FileTemporary;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.DateUtil;
import ieci.tecdoc.core.db.DbConnectionConfig;
import ieci.tecdoc.sbo.idoc.api.ArchiveObject;
import ieci.tdw.ispac.ispaclib.resp.User;
import ieci.tecdoc.sbo.idoc.api.Folder;
import ieci.tecdoc.sbo.idoc.api.Archive;
import ieci.tecdoc.sbo.idoc.api.FolderObject;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * Conector de SICRES con inveSicres.
 *
 */
public class InveSicresConnector implements ISicresConnector {
	
    /** 
     * Logger de la clase. 
     */
	protected static final Logger logger = Logger.getLogger(InveSicresConnector.class);
	
    ClientContext ctx = null;
	private String poolName;
	private InvesDocConnection invesDocConnection;
	
	private int iDocUserId;
	private String iDocUserNam;
		
	/**
	 * Constructor
	 * @throws ISPACException si ocurre algún error.
	 */
	public InveSicresConnector() throws ISPACException {
		createInvesdocConnection();
	}
	
	/**
	 * Constructor
	 * @param ctx Contexto de cliente.
	 * @throws ISPACException si ocurre algún error.
	 */
	public InveSicresConnector(ClientContext ctx) throws ISPACException {
		Responsible user = ctx.getUser();
	
		this.ctx = ctx;
		this.iDocUserNam = user.getName();
		
		createInvesdocConnection();
	}

	private void createInvesdocConnection() throws ISPACException {
		
        InveSicresConfiguration config = InveSicresConfiguration.getInstance();
        
        String poolName = null;
        
		OrganizationUserInfo info = OrganizationUser.getOrganizationUserInfo();
		if (info != null) {
			String pattern = config.get(InveSicresConfiguration.POOLNAME_PATTERN);
			poolName = info.getSicresPoolName(pattern);
		} else {
	        poolName = config.get(InveSicresConfiguration.POOL_NAME);
		}

        setPoolName(poolName);
        
        InvesDocConnection invesDocConnection = new InvesDocConnection(
        		getPoolName(), getIDocUserId(), getIDocUserNam());
        
        setInvesDocConnection(invesDocConnection);
	}

	public Register readRegister(RegisterInfo registerInfo) throws ISPACException {

	    String registerNumber = registerInfo.getRegisterNumber();
	    
	    Register register = null;
	    
	    try {
	    	getInvesDocConnection().Connect();
            
	        if (RegisterType.ENTRADA.equals(registerInfo.getRegisterType())) {
	        	register = consultarApunteEntrada(getInvesDocConnection(), registerNumber);
	        }
	        
	        if (RegisterType.SALIDA.equals(registerInfo.getRegisterType())) {
	        	register = consultarApunteSalida(getInvesDocConnection(), registerNumber);
	        }
	    }
        finally{
        	getInvesDocConnection().Disconnect();
        }
		
		return register;
	}
	
    private Register consultarApunteEntrada(InvesDocConnection connection, 
    		String registerNumber) throws ISPACException {
               
        RegistroEntrada registroEntrada = new RegistroEntrada(connection);
        RegistroEntradaVO registroEntradaVO = registroEntrada.getRegistro(registerNumber);
        
        if (registroEntradaVO != null) {   
        	return populateApunte(registroEntradaVO);
        }
        
        return null;
    }
    
    private Register consultarApunteSalida(InvesDocConnection connection, 
    		String registerNumber) throws ISPACException {
               
        
    	RegistroSalida registroSalida = new RegistroSalida(connection);
    	RegistroSalidaVO registroSalidaVO = registroSalida.getRegistro(registerNumber);
        
        if (registroSalidaVO != null) {   
        	return populateApunte(registroSalidaVO);
        }
        
        return null;
    }
    
    private Register populateApunte(RegistroEntradaVO registroEntradaVO) {
    	
    	Register register = new Register();
        
        // PROCEDENCIA
    	RegisterOffice registerOfficeOrigin = new RegisterOffice(registroEntradaVO.getOficinaRegistroCode(),
    															 registroEntradaVO.getOficinaRegistroName());
    	
    	RegisterOrigin registerOrigin = new RegisterOrigin(registerOfficeOrigin,
    													   registroEntradaVO.getNumeroRegistro(),
    													   DateUtil.getCalendar(registroEntradaVO.getFechaHoraRegistro()),
    													   RegisterType.ENTRADA,
    													   DateUtil.getCalendar(registroEntradaVO.getFechaTrabajo()),
    													   registroEntradaVO.getUsuario());
    	
    	register.setRegisterOrigin(registerOrigin);
        
        // ORGANISMOS ORIGEN Y DESTINO
        Organization originOrganization = new Organization(registroEntradaVO.getOrigenId(),
        												   registroEntradaVO.getOrigenCode(),
        												   registroEntradaVO.getOrigenName(),
        												   null, null, null);

        Organization destinationOrganization = new Organization(registroEntradaVO.getDestinoId(),
        														registroEntradaVO.getDestinoCode(),
				   												registroEntradaVO.getDestinoName(),
				   												null, null, null);
        
        register.setOriginOrganization(originOrganization);
        register.setDestinationOrganization(destinationOrganization);
        
        // CONTENIDO / INTERESADOS
        Subject subject = new Subject(registroEntradaVO.getTipoAsuntoCode(),
        							  registroEntradaVO.getTipoAsuntoName());
        
        RegisterData registerData = new RegisterData(getParticipants(registroEntradaVO.getRemitentes()),
        											 subject, 
        											 registroEntradaVO.getResumen(), 
        											 getInfoDocuments(registroEntradaVO.getArchiveId(),
        	                										  registroEntradaVO.getFolderId(),
        	                										  registroEntradaVO.getDocumentos()));
        
        register.setRegisterData(registerData);
	
        // ORIGINADOR
        RegisterInfo originalRegister = new RegisterInfo();
        boolean hayOriginador = false;
        if (registroEntradaVO.getRegistroOriginalCode() != null) {
        	RegisterOffice registerOfficeOriginal = new RegisterOffice(registroEntradaVO.getRegistroOriginalCode(),
					 												   registroEntradaVO.getRegistroOriginalName());
        	originalRegister.setRegisterOffice(registerOfficeOriginal);
        	hayOriginador = true;
        }
        if (registroEntradaVO.getFechaRegistroOriginal() != null) {
        	originalRegister.setRegisterDate(DateUtil.getCalendar(registroEntradaVO.getFechaRegistroOriginal()));
        	hayOriginador = true;
        }
        if (registroEntradaVO.getNumRegistroOriginal() != null) {
        	originalRegister.setRegisterNumber(registroEntradaVO.getNumRegistroOriginal());
        	hayOriginador = true;
        }
        if (registroEntradaVO.getTipoRegistroOriginalId()!= null) {
        	if (registroEntradaVO.getTipoRegistroOriginalId().equals("1")) {
        		originalRegister.setRegisterType(RegisterType.ENTRADA);
        	}
        	else {
        		originalRegister.setRegisterType(RegisterType.SALIDA);
        	}
        	hayOriginador = true;
        }
        if (hayOriginador) {
        	register.setOriginalRegister(originalRegister);
        }

        // TRANSPORTE
        Transport transport = new Transport(registroEntradaVO.getTipoTransporte(),
        									registroEntradaVO.getNumeroTransporte());
        		
        register.setTransport(transport);
        
        return register;
    }
    
    
    private Register populateApunte(RegistroSalidaVO registroSalidaVO) {
    	
    	Register register = new Register();	
    	
        // PROCEDENCIA
    	RegisterOffice registerOfficeOrigin = new RegisterOffice(registroSalidaVO.getOficinaRegistroCode(),
    															 registroSalidaVO.getOficinaRegistroName());
    	
    	RegisterOrigin registerOrigin = new RegisterOrigin(registerOfficeOrigin,
    													   registroSalidaVO.getNumeroRegistro(),
    													   DateUtil.getCalendar(registroSalidaVO.getFechaHoraRegistro()),
    													   RegisterType.SALIDA,
    													   DateUtil.getCalendar(registroSalidaVO.getFechaTrabajo()),
    													   registroSalidaVO.getUsuario());
    	
    	register.setRegisterOrigin(registerOrigin);
        
        // ORGANISMOS ORIGEN Y DESTINO
        Organization originOrganization = new Organization(registroSalidaVO.getOrigenId(),
        												   registroSalidaVO.getOrigenCode(),
        												   registroSalidaVO.getOrigenName(),
        												   null, null, null);
        
        register.setOriginOrganization(originOrganization);

        Organization destinationOrganization = new Organization(registroSalidaVO.getDestinoId(),
        														registroSalidaVO.getDestinoCode(),
				   												registroSalidaVO.getDestinoName(),
				   												null, null, null);
             
        register.setDestinationOrganization(destinationOrganization);
        

        // CONTENIDO / INTERESADOS
        Subject subject = new Subject(registroSalidaVO.getTipoAsuntoCode(),
        							  registroSalidaVO.getTipoAsuntoName());
        
        RegisterData registerData = new RegisterData(getParticipants(registroSalidaVO.getDestinatarios()),
        											 subject, 
        											 registroSalidaVO.getResumen(), 
        											 getInfoDocuments(registroSalidaVO.getArchiveId(),
        	                										  registroSalidaVO.getFolderId(),
        	                										  registroSalidaVO.getDocumentos()));
        
        register.setRegisterData(registerData);

        // TRANSPORTE
        Transport transport = new Transport(registroSalidaVO.getTipoTransporte(),
        									registroSalidaVO.getNumeroTransporte());
        		
        register.setTransport(transport);
        
        return register;
    }    
    
    
    
	private DocumentInfo[] getInfoDocuments(String archiveId,
											String folderId,
											List<DocumentoVO> ltFiles) {
		
	    try {
		    if (ltFiles != null) {
		    	
		    	DocumentInfo[] documents = new DocumentInfo[ltFiles.size()];
		    	
	            int i = 0;
	            Iterator<DocumentoVO> it = ltFiles.iterator();
	            while (it.hasNext()) {
	            	
	                DocumentoVO unDocumento = it.next();
	                
	                DocumentInfo document = new DocumentInfo(getComposedIdFile(unDocumento.getPageId(), archiveId, folderId),
	                										 unDocumento.getPageName() + "." + unDocumento.getLoc(),
	                										 null);
	                
	                documents[i] = document;
	                i++;
	            }
	            
	            return documents;
	        }    
        }
	    catch (Exception e) {
           return null;
        }

	    return null;
    }
	
   	private String getComposedIdFile(String pageId,
   									 String archiveId,
   									 String folderId) {
   		
        return new StringBuffer().append(archiveId)
        						 .append("*")
        						 .append(folderId)
        						 .append("*")
        						 .append(pageId).toString();
    }
   	
    private ThirdPerson[] getParticipants(List ltThirdPersons) {
    	
        if (ltThirdPersons != null) {
        	
        	ThirdPerson[] thirdPersons = new ThirdPerson[ltThirdPersons.size()];
        	
		    int i = 0;
		    Iterator it = ltThirdPersons.iterator();
		    while (it.hasNext()) {
		    	
		        InteresadoVO unInteresado = (InteresadoVO) it.next();
		        
		        ThirdPerson thirdPerson = new ThirdPerson(unInteresado.getIdTercero(),
		        										  unInteresado.getNombre(),
		        										  unInteresado.getIdDireccion());

		        thirdPersons[i] = thirdPerson;
		        i++;
		    }
		    
		    return thirdPersons;
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
		return true;
	}

	public RegisterInfo insertRegister(Register register) throws ISPACException {
	    
	    RegisterInfo registerInfo = null;
	    
	    try {
	    	getInvesDocConnection().Connect();
            
	    	RegistroSalida registroSalida = new RegistroSalida(getInvesDocConnection());
	    	
	    	RegistroSalidaVO registroSalidaVO = populateRegistroSalida(register);
            RegistroSalidaVO mRegistroSalidaVO = registroSalida.addRegistro(registroSalidaVO);
            
            if (mRegistroSalidaVO != null) {
            	registerInfo = new RegisterInfo(null,
            									mRegistroSalidaVO.getNumeroRegistro(),
            									DateUtil.getCalendar(mRegistroSalidaVO.getFechaRegistro()),
            									RegisterType.SALIDA);
            }
	    } finally{
        	getInvesDocConnection().Disconnect();
        }
		
		return registerInfo;
	}
	
    private RegistroSalidaVO populateRegistroSalida(Register register) throws ISPACException {
    	
        RegisterInfo originalRegister = register.getOriginalRegister();
        
        RegistroSalidaVO ret = new RegistroSalidaVO();
        
        // PROCEDENCIA
        ret.setUsuario(getIDocUserNam());
        
        if (originalRegister != null) {
            
        	// Identificador del libro de salida
        	ret.setArchiveId(originalRegister.getBookId());
        	
            // Código de la oficina de registro
        	if (originalRegister.getRegisterOffice() != null) {
        		ret.setOficinaRegistroCode(originalRegister.getRegisterOffice().getCode());
        	}
        }
        
        // DESTINATARIOS
        if ((register.getRegisterData().getParticipants() != null) &&
        	(register.getRegisterData().getParticipants().length > 0)) {
        	
        	ThirdPerson destiny = register.getRegisterData().getParticipants() [0];
        	
        	//ret.setDestinoId(destiny.getId());
        	//ret.setDestinatario(destiny.getName());
        	
        	InteresadoVO interesadoVO = new InteresadoVO();
        	interesadoVO.setNombre(destiny.getName());
        	interesadoVO.setIdTercero(destiny.getId());
        	
        	List<InteresadoVO> destinatarios = new ArrayList<InteresadoVO>();
        	destinatarios.add(interesadoVO);
        	
        	ret.setDestinatarios(destinatarios);
        }
        
        // DOCUMENTOS
        DocumentInfo [] docs = register.getRegisterData().getInfoDocuments();
        if (docs != null && docs.length > 0) {
        	LinkedList<DocumentInfo> docList = new LinkedList<DocumentInfo>();
        	for (int i = 0; i < docs.length; i++) {
				docList.add(docs[i]);
			}
        	ret.setDocumentos(docList);
        }
        
        // ORGANISMO ORIGEN
        if (register.getOriginOrganization() != null)  {
        	ret.setOrigenCode(register.getOriginOrganization().getCode());
        }
        
        // CONTENIDO
        ret.setResumen(register.getRegisterData().getSummary());
        
        return ret;
    }
	
	/**
	 * @return Returns the iDocUserId.
	 */
	public int getIDocUserId() {
		return iDocUserId;
	}
	/**
	 * @param docUserId The iDocUserId to set.
	 */
	public void setIDocUserId(int docUserId) {
		iDocUserId = docUserId;
	}

	/**
	 * @return Returns the iDocUserNam.
	 */
	public String getIDocUserNam() {
		return iDocUserNam;
	}
	/**
	 * @param docUserNam The iDocUserNam to set.
	 */
	public void setIDocUserNam(String docUserNam) {
		iDocUserNam = docUserNam;
	}

	/**
	 * @return Returns the invesDocConnection.
	 */
	public InvesDocConnection getInvesDocConnection() {
		return invesDocConnection;
	}
	/**
	 * @param invesDocConnection The invesDocConnection to set.
	 */
	public void setInvesDocConnection(InvesDocConnection invesDocConnection) {
		this.invesDocConnection = invesDocConnection;
	}

	/**
	 * @return Returns the poolName.
	 */
	public String getPoolName() {
		return poolName;
	}
	/**
	 * @param poolName The poolName to set.
	 */
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
    
	/**
	 * Obtiene el número de registros distribuidos asociados al usuario conectado.
	 * @return Número de registros distribuidos.
	 * @throws ISPACException si ocurre algún error.
	 */
	public int countIntrais() throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		return sicres.countIntrais();
	}

	/**
	 * Obtiene la lista de registros distribuidos asociados al usuario conectado.
	 * @return Lista de registros distribuidos ({@link Intray}).
	 * @throws ISPACException si ocurre algún error.
	 */
	public List getIntrays() throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		return sicres.getIntrays();
	}

	/**
	 * Obtiene la información del registro distribuido.
	 * @param register Número de registro.
	 * @return Información del registro distribuido.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Intray getIntray(String register) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		return sicres.getIntray(Integer.parseInt(register));
	}

//	/**
//	 * Asocia un registro de distribución con un identificador de
//	 * proceso.
//	 * @param register identificador del registro de distribución
//	 * @param process identificador del proceso
//	 * @param type tipo de relación:
//	 * 	1: Inicio de expediente.
//	 * 	2: Inclusión en expediente.
//	 */
//	public void addToProccess(int register, int process, int type) throws ISPACException {
//		SicresIntray sicres = new SicresIntray(ctx);
//		sicres.addToProccess(register, process, type);
//	}

//	/**
//	 * Distribuye un registro de entrada.
//	 * @param register identificador del registro.
//	 * @param state estado del registro.
//	 * @param responsible responsable a quien se le distribuye.
//	 * @param message mensaje asociado a la acción
//	 * @throws ISPACException
//	 */
//	public void distribute(int register, Responsible responsible, String message, boolean archive) throws ISPACException {
//		SicresIntray sicres = new SicresIntray(ctx);
//		sicres.distribute(register, responsible, message, archive);
//	}

	/**
	 * Acepta un registro distribuido.
	 * @param register Número de registro.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void acceptIntray(String register) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		sicres.acceptIntray(Integer.parseInt(register));
	}

	/**
	 * Rechaza un registro distribuido.
	 * @param register Número de registro.
	 * @param reason Motivo del rechazo.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void rejectIntray(String register, String reason) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		sicres.rejectIntray(Integer.parseInt(register), reason);
	}

	/**
	 * Archiva un registro distribuido.
	 * @param register Número de registro.
	 * @throws ISPACException si ocurre algún error. 
	 */
	public void archiveIntray(String register) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		sicres.archiveIntray(Integer.parseInt(register));
	}

//	/**
//	 * Cambia el estado de un registro de distribución
//	 * @param register identificador del registro.
//	 * @param state estado actual.
//	 * @param newState nuevo estado.
//	 * @throws ISPACException
//	 */
//	public void changeState(int register, int state) throws ISPACException {
//		SicresIntray sicres = new SicresIntray(ctx);
//		sicres.changeState(register, state);
//	}

	/**
	 * Obtiene los anexos del registro de entrada.
	 * @param register identificador del registro de distribución.
	 * @throws ISPACException si ocurre algún error.
	 */
	public Annexe[] getAnnexes(String register) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		return sicres.getAnnexes(Integer.parseInt(register));
	}

	/**
	 * Descarga un anexo del registro de entrada.
	 * @param register identificador del registro de distribución.
	 * @param annexe identificador del anexo.
	 * @param out stream de salida.
	 * @throws ISPACException
	 */
	public void getAnnexe(String register, String annexe, OutputStream out) throws ISPACException {
		SicresIntray sicres = new SicresIntray(ctx);
		sicres.getAnnexe(Integer.parseInt(register), Integer.parseInt(annexe), out);
	}


	public Annexe[] getAnnexes(String registerNumber, String registerType)
			throws ISPACException {
		
		List<DocumentoVO> documentos = null;
		String archiveId = null;
		String folderId = null;
	    try {
	    	getInvesDocConnection().Connect();
			Registro registroApi = new Registro(invesDocConnection);
	        if (RegisterType.ENTRADA.equals(registerType)) {
	    		RegistroEntrada registroEntrada = new RegistroEntrada(invesDocConnection);
	    		RegistroEntradaVO registroEntradaVO = registroEntrada.getRegistro(registerNumber);
	    		archiveId = registroEntradaVO.getArchiveId();
	    		folderId = registroEntradaVO.getFolderId();
	    		documentos = registroEntrada.getRegistro(registerNumber).getDocumentos();
	        	
	        }else if (RegisterType.SALIDA.equals(registerType)) {
	    		RegistroSalida registroSalida = new RegistroSalida(invesDocConnection);
	    		RegistroSalidaVO registroSalidaVO = registroSalida.getRegistro(registerNumber);
	    		archiveId = registroSalidaVO.getArchiveId();
	    		folderId = registroSalidaVO.getFolderId();
	    		documentos = registroSalida.getRegistro(registerNumber).getDocumentos();
	        }

	    }
        finally{
        	getInvesDocConnection().Disconnect();
        }
        return adapterAnnexes(documentos, archiveId, folderId);
		
	}
	
	private Annexe[] adapterAnnexes(List<DocumentoVO> documentos, String archiveId, String folderId){
		if (documentos == null || documentos.size() == 0){
			return null;
		}
		
		Annexe[] annexes = new Annexe[documentos.size()];
		int i = 0;
		for (Iterator iterator = documentos.iterator(); iterator.hasNext();i++) {
			DocumentoVO documentoVO = (DocumentoVO) iterator.next();
			Annexe annexe = new Annexe(documentoVO.getDocId(), documentoVO.getDocName(), documentoVO.getLoc());
			annexe.setBookId(archiveId);
			annexe.setFolderId(folderId);
			annexe.setDocId(documentoVO.getPageId());
			annexes[i] = annexe;
		}
		return annexes;
	}
	

	public void getAnnexe(Annexe annexe, OutputStream out) throws ISPACException {
		DbCnt cnt = new DbCnt(poolName);
		
		cnt.getConnection();
		try
		{		
			int archiveId = Integer.parseInt(annexe.getBookId());
			int folderId = Integer.parseInt(annexe.getFolderId());
			int docId = Integer.parseInt(annexe.getDocId());
			
			DbConnectionConfig config = new DbConnectionConfig( poolName, null, null);
			Archive archiveAPI = new Archive();
			archiveAPI.setConnectionConfig(config);
			Folder folderAPI = new Folder();
			folderAPI.setConnectionConfig(config);
			
			ArchiveObject archive = archiveAPI.loadArchive(null, archiveId);
			
			User user = (User) ctx.getUser();
			
			int userId = getResponsibleId( cnt, user);
	
			FolderObject folder = folderAPI.loadFolder(null, userId, archive, folderId);  
	
		    byte[] bytes = folderAPI.retrieveFolderDocumentFile(null, archive, folder, docId);
		    
		    out.write( bytes);
		    
		    out.flush();
			
		}
		catch (Exception e)
		{
			throw new ISPACException(e);
		}
		finally
		{
			cnt.closeConnection();
		}	    
		
	}	
	
	
	protected int getResponsibleId( DbCnt cnt, Responsible responsible)
	throws ISPACException
	{
		String sUID = responsible.getUID();
		
		String msUserManager = InveSicresConfiguration.getInstance().get(InveSicresConfiguration.USER_MANAGER);
		
		if (msUserManager.equalsIgnoreCase("INVESDOC"))
		{
		    StringTokenizer tokens = new StringTokenizer( sUID, "-");
		    
		    if (tokens.hasMoreTokens()) 
		    {
			    if (tokens.hasMoreTokens())
			    {
			    	tokens.nextToken();
			    	
			    	if (tokens.hasMoreTokens())
			    	{
			    		return Integer.parseInt(tokens.nextToken());
			    	}
			    }
		    }
		}
		// Si el usuario es LDAP existe una tablas invesdoc 
		// (IUSERLDAPUSERHDR) que mantiene la correspondencia 
		// entre el GUID de LDAP y el identificador del
		// usuario, grupo de invesdoc
		else if (msUserManager.equalsIgnoreCase("LDAP"))
		{
			CollectionDAO collection;
			IItemCollection list;
			Iterator iterator;
			
			if (responsible instanceof  User)
			{
				String where = "WHERE LDAPGUID = '"
					  		 + DBUtil.replaceQuotes(sUID)
							 + "'";
				
				collection = new CollectionDAO(UserLDAPDAO.class);
				collection.query(cnt,where);
				list = collection.disconnect();
				
				iterator = list.iterator();
				
				if (!iterator.hasNext())
				{
					logger.info("No existe el usuario LDAP: " + responsible.getName());
					return 0;
				}
			}
			else
			{
				String where = "WHERE LDAPGUID = '"
					  		 + DBUtil.replaceQuotes(sUID)
							 + "'";
				
				collection = new CollectionDAO(GroupLDAPDAO.class);
				collection.query(cnt,where);
				list = collection.disconnect();
				
				iterator = list.iterator();
				
				if (!iterator.hasNext())
				{
					logger.info("No existe el usuario LDAP: " + responsible.getName());
					return 0;
				}
			}
			
			return ((ObjectDAO) iterator.next()).getKeyInt();
		}
	    
	    return 0;
	}	
	
	
	public void linkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		// TODO Futuras implementaciones
		
	}

	public void unlinkExpedient(RegisterInfo registerInfo, String numExpedient)
			throws ISPACException {
		// TODO Futuras implementaciones
		
	}

	public List getBooks(int bookType) throws ISPACException {
	    try {
	    	getInvesDocConnection().Connect();
	    	Registro registro = new Registro(getInvesDocConnection());
        	return registro.getBooks(bookType);
	    } finally{
        	getInvesDocConnection().Disconnect();
        }
	}

	public List getOffices(String bookId) throws ISPACException {
	    try {
	    	getInvesDocConnection().Connect();
	    	Registro registro = new Registro(getInvesDocConnection());
        	return registro.getOffices(bookId);
	    } finally{
        	getInvesDocConnection().Disconnect();
        }
	}

	public Organization getOrganizationByCode(String code) throws ISPACException {
	    try {
	    	getInvesDocConnection().Connect();
	    	Registro registro = new Registro(getInvesDocConnection());
        	return registro.getOrganizationByCode(code);
	    } finally{
        	getInvesDocConnection().Disconnect();
        }
	}




}