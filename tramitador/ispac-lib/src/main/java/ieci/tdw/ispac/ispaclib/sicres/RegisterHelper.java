package ieci.tdw.ispac.ispaclib.sicres;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IGenDocAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.IThirdPartyAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.IClientContext;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.sicres.ISicresConnector;
import ieci.tdw.ispac.ispaclib.sicres.SicresConnectorFactory;
import ieci.tdw.ispac.ispaclib.sicres.vo.Annexe;
import ieci.tdw.ispac.ispaclib.sicres.vo.Register;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.sicres.vo.ThirdPerson;
import ieci.tdw.ispac.ispaclib.thirdparty.IElectronicAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IPostalAddressAdapter;
import ieci.tdw.ispac.ispaclib.thirdparty.IThirdPartyAdapter;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.MimetypeMapping;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

public class RegisterHelper {

	
	public static void insertParticipants(ClientContext ctx, String registerNumber, String registerType, String numExp, boolean insertarMainInterestedAsParticipant) throws ISPACException {
		ISicresConnector sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(ctx);
		RegisterInfo registerInfo = new RegisterInfo(null, registerNumber, null, registerType);
		Register register = sicresConnector.readRegister(registerInfo);
		ThirdPerson[] participants = register.getRegisterData().getParticipants();
		if (participants != null){
			int i = 0;
			//Para aquellos apuntes que dan origen al expediente, el interesado no se añade como un participante mas  
			if (!insertarMainInterestedAsParticipant){
				i++;
			}
			for (; i < participants.length; i++) {
				insertParticipant(ctx, numExp, participants[i]);
			}
		}
		
	}	
	
	
	public static void onDeleteDocument(ClientContext ctx, IItem itemDocument) throws ISPACException{
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		IRegisterAPI registerAPI = ctx.getAPI().getRegisterAPI();
		String numExp = itemDocument.getString("NUMEXP");

		//Comprobamos si la entidad SPAC_REGISTROS_ES esta vinculada al procedimiento
		if (!isAssocitedRegistrosESEntity(ctx, numExp)){
			return;
		}
		
		//Si se elimina el documento se elimian los datos del registro asociado si lo hubiera y no esta asociado directamente al expediente
		String registerNumber = itemDocument.getString("NREG");
		String registerType = itemDocument.getString("TP_REG");
		
		//Se compruba si el numero de registro vinculado con el documento no esta vinculado con otro documento o a nivel del expediente
		if (StringUtils.isNotEmpty(registerNumber)){  
			if (entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS,
					"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numExp)
							+ "' AND NREG = '" + registerNumber
							+ "' AND TP_REG = '" + registerType
							+ "' AND ID != " + itemDocument.getKeyInt()) == 0						
			 && (StringUtils.equals(RegisterType.SALIDA, registerType)
				|| (StringUtils.equals(RegisterType.ENTRADA, registerType) 
					&& entitiesAPI.countEntities(SpacEntities.SPAC_EXPEDIENTES,
						"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numExp)
						   + "' AND NREG = '" + registerNumber+ "'") == 0)
				) 
			) {
				//se elimian los datos del registro asociado si lo hubiera
				entitiesAPI.deleteEntities(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NREG = '" + registerNumber +"' AND TP_REG = '" + registerType + "'");
				//Se desvincula el expediente del Apunte 
				registerAPI.unlinkExpedient(new RegisterInfo(null, registerNumber, null, registerType), numExp );
			}
		}
	}
	
	private static void insertParticipant(IClientContext ctx, String numExp, ThirdPerson thirdPerson) throws ISPACException {

		IThirdPartyAPI thirdPartyAPI = ctx.getAPI().getThirdPartyAPI();
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		
		//Si es un tercero no validado, solo se obtiene de registro el Nombre como texto libre (sin nif/cif ni direccion postal)
		if (StringUtils.equals(thirdPerson.getId(), "0")){
			if (entitiesAPI.countEntities(SpacEntities.SPAC_DT_INTERVINIENTES, "WHERE NUMEXP = '" + DBUtil.replaceQuotes(numExp) 
					+ "' AND ID_EXT =  '0'  AND NOMBRE = '"+ thirdPerson.getName() + "'") > 0) {
				return;
			}
			IItem itemThird = entitiesAPI.createEntity(SpacEntities.SPAC_DT_INTERVINIENTES);
			itemThird.set("NUMEXP", numExp);
			itemThird.set("NOMBRE", thirdPerson.getName());
			itemThird.store(ctx);
			return;
		}

		IThirdPartyAdapter tercero = thirdPartyAPI.lookupById(thirdPerson.getId(), true);
		
		//Comprobamos si ya esta dado de alta, para ello hacemos la consulta por el identificador externo, el NIF/CIF y el nombre, ya que puede haber mas
		//de un tercero con el mismo NIF, con el mismo nombre e incluso con el miso identificdaor externo (al poder incluir no validados cuyo id = 0 para todos)
		if (entitiesAPI.countEntities(SpacEntities.SPAC_DT_INTERVINIENTES,
				"WHERE NUMEXP = '" + DBUtil.replaceQuotes(numExp) 
				+ "' AND ID_EXT = '"+ thirdPerson.getId() + "' " 
				+ "AND NDOC = '" + tercero.getIdentificacion() + "' " 
				+ "AND NOMBRE = '"+ tercero.getNombreCompleto() + "'") > 0) {
			return;
		}
		
		
		
		IItem itemThird = entitiesAPI.createEntity(SpacEntities.SPAC_DT_INTERVINIENTES);
		itemThird.set("NUMEXP", numExp);
		
		//Si es un tercero no validado, nos vendra con identificador nulo
		if (StringUtils.isEmpty(tercero.getIdExt())){ 
			itemThird.set("ID_EXT", 0);
		}else{
			itemThird.set("ID_EXT", tercero.getIdExt());
		}
		itemThird.set("NOMBRE", tercero.getNombreCompleto());
		itemThird.set("TIPO_PERSONA",tercero.getTipoPersona());
		itemThird.set("NDOC",tercero.getIdentificacion());
		
		
		IPostalAddressAdapter dirPostal = tercero.getDefaultDireccionPostal();
		if (dirPostal != null) {
			itemThird.set("IDDIRECCIONPOSTAL", dirPostal.getId());
			itemThird.set("DIRNOT", dirPostal.getDireccionPostal());
			itemThird.set("C_POSTAL", dirPostal.getCodigoPostal());
			itemThird.set("LOCALIDAD", dirPostal.getMunicipio());
			itemThird.set("TFNO_FIJO", dirPostal.getTelefono());
	
	    	String regionPais = dirPostal.getProvincia();
	    	if (StringUtils.isNotEmpty(dirPostal.getPais()))
	    		regionPais += "/"+dirPostal.getPais();
	    	
	    	itemThird.set("CAUT", regionPais);
		}
		IElectronicAddressAdapter dirElectronica = tercero.getDefaultDireccionElectronica();
		if (dirElectronica != null) {
			if (dirElectronica.getTipo() == IElectronicAddressAdapter.PHONE_TYPE) {
				itemThird.set("TFNO_MOVIL", dirElectronica.getDireccion());
			} else {
				itemThird.set("DIRECCIONTELEMATICA", dirElectronica.getDireccion());
			}
		}
		String addressType = IThirdPartyAdapter.ADDRESS_TYPE_POSTAL;
		if (tercero.isNotificacionTelematica()) {
			addressType = IThirdPartyAdapter.ADDRESS_TYPE_TELEMATIC;
		}
		itemThird.set("TIPO_DIRECCION", addressType);
		itemThird.store(ctx);		
	}
	
	public static int attachAnnexes(ClientContext ctx, String registerNumber, String registerType, Date registerDate, String interesadoId, String interesado, int stageId, int taskId) throws ISPACException {
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		ISicresConnector sicresConnector = SicresConnectorFactory.getInstance().getSicresConnector(ctx);
		
		Annexe[] annexes = sicresConnector.getAnnexes(registerNumber, registerType);
		int i = 0;
		if (annexes != null){
			
			for (; i < annexes.length; i++) {
				
				//Obtenemos el Iitem como EntityDAO para luego invocar al store(DbCnt), ya que este store no lanza los eventos, y no interesa que se lancen
				//si no volvería a crear un registro de la entidad SPAC_REGISTROS_ES por las reglas asociadas al guardar la entidad 'Documentos'
				EntityDAO entityDocument = null;
				
				if (stageId != 0){
					entityDocument = (EntityDAO)genDocAPI.createStageDocument(stageId, 0);
				}else {
					entityDocument = (EntityDAO)genDocAPI.createTaskDocument(taskId, 0);
				}
				
				entityDocument.set("NOMBRE", annexes[i].getName());
				entityDocument.set("NREG", registerNumber);
				entityDocument.set("FREG", registerDate);
				entityDocument.set("TP_REG", registerType);
				
				IResponsible dept = ctx.getResponsible().getRespOrgUnit();
				if (StringUtils.equals(registerType, RegisterType.ENTRADA)){
					entityDocument.set("ORIGEN", interesado);				
					entityDocument.set("ORIGEN_ID", interesadoId);
					entityDocument.set("DESTINO", dept.getName());				
					entityDocument.set("DESTINO_ID", dept.getUID());
				}else{
					entityDocument.set("ORIGEN", dept.getName());
					entityDocument.set("ORIGEN_ID", dept.getUID());
					entityDocument.set("DESTINO", interesado);				
					entityDocument.set("DESTINO_ID", interesadoId);
				}
				String extension = annexes[i].getExt();
				entityDocument.set("EXTENSION", extension);
				//Se invoca al store que NO procesa reglas asociadas al evento guardar
				entityDocument.store(ctx.getConnection());
				
				//Anexamos el documento al gestor documental 
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				sicresConnector.getAnnexe(annexes[i], out);
				ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
				RegisterHelper.attachDocument(ctx, stageId, taskId, entityDocument.getKeyInt(),  in, out.size(), MimetypeMapping.getMimeType(extension), "Anexado desde Registro (nº registro:"+registerNumber+")");
			}
		}
		return i;
	}		
	
	
	

	private static void attachDocument(ClientContext ctx, int stageId, int taskId, int docId, InputStream in, int length, String mimeType, String name) throws ISPACException{
		IGenDocAPI genDocAPI = ctx.getAPI().getGenDocAPI();
		Object connectorSession = null;
		try {
			connectorSession = genDocAPI.createConnectorSession();
			if (stageId != 0){
				genDocAPI.attachStageInputStream(connectorSession, stageId, docId, in, length, mimeType, name);
			}else{
				genDocAPI.attachTaskInputStream(connectorSession, taskId, docId, in, length, mimeType, name);
			}
		}
		finally {
			if (connectorSession != null) {
				genDocAPI.closeConnectorSession(connectorSession);
			}
    	}		
	}


	public static void insertRegistroES(ClientContext ctx, Register register,  ThirdPerson destiny, String numExp, int taskId) throws ISPACException {
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		//Comprobamos si la entidad SPAC_REGISTROS_ES esta vinculada al procedimiento
		if (!isAssocitedRegistrosESEntity(ctx, numExp)){
			return;
		}
		
		String asunto = null;
        if (register.getRegisterData() != null) {
	    	if (register.getRegisterData().getSummary() != null) {
	    		asunto = register.getRegisterData().getSummary();
	    	}            	
        }
		IItem itemRegisterES = entitiesAPI.createEntity(SpacEntities.SPAC_REGISTROS_ES_NAME, numExp);
		itemRegisterES.set("NREG", register.getRegisterOrigin().getRegisterNumber());
		itemRegisterES.set("FREG", register.getRegisterOrigin().getRegisterDate().getTime());
		itemRegisterES.set("ASUNTO", asunto);
		itemRegisterES.set("ID_INTERESADO", destiny.getId());
		itemRegisterES.set("INTERESADO", destiny.getName());
		itemRegisterES.set("TP_REG", register.getRegisterOrigin().getRegisterType());
		itemRegisterES.set("ID_TRAMITE",taskId);
		itemRegisterES.set("ORIGINO_EXPEDIENTE", "NO");

		itemRegisterES.store(ctx);
		
	}
	
	public static boolean isAssocitedRegistrosESEntity(ClientContext ctx, String numExp) throws ISPACException{
		IEntitiesAPI entitiesAPI = ctx.getAPI().getEntitiesAPI();
		IItem itemExp = entitiesAPI.getExpedient(numExp);
		if (itemExp == null){
			return false;
		}
		
		IItemCollection itemcol = entitiesAPI.getProcedureEntities(itemExp.getInt("ID_PCD"));
		Map map = itemcol.toMapStringKey("ID_ENT");
		return map.get(String.valueOf(SpacEntities.SPAC_REGISTROS_ES)) != null;
	}
	
}