package ieci.tdw.ispac.ispacmgr.app;

import ieci.tdw.ispac.api.IEntitiesAPI;
import ieci.tdw.ispac.api.IRegisterAPI;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.api.item.Properties;
import ieci.tdw.ispac.api.item.Property;
import ieci.tdw.ispac.ispaclib.app.GenericSecondaryEntityApp;
import ieci.tdw.ispac.ispaclib.common.constants.DocumentLockStates;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.item.CompositeItem;
import ieci.tdw.ispac.ispaclib.item.GenericItem;
import ieci.tdw.ispac.ispaclib.sicres.RegisterHelper;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterInfo;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.sql.Types;
import java.util.Iterator;

public class DocumentTaskEntityApp extends GenericSecondaryEntityApp {
	
	private static final long serialVersionUID = 6264353429120524973L;

	public DocumentTaskEntityApp(ClientContext context)
	{
		super(context);
	}
	
	public void initiate() throws ISPACException {
		
		super.initiate();
		
	    // Establecer el nombre del responsable para mostrarlo en el formulario
	    if (getProperty("SPAC_DT_TRAMITES:FECHA_CIERRE") == null) {
	    	setProperty("ID_RESP_NAME", TaskHelper.getNameResp(mContext));
	    	if ( StringUtils.isEmpty(mitem.getString("SPAC_DT_TRAMITES:UND_RESP")) ){
	    		setProperty("SPAC_DT_TRAMITES:UND_RESP", TaskHelper.getDptoResp(mContext));
	    	}
	    }
	    
	    // Comprobar si el documento está bloqueado
//	    if ((getProperty("SPAC_DT_DOCUMENTOS:BLOQUEADO") != null) && 
//	    	(getProperty("SPAC_DT_DOCUMENTOS:BLOQUEADO").equals("SI"))) {
//	    	
//	    	setProperty("BLOQUEADO", Boolean.TRUE);
//	    }	    

	    if (StringUtils.equals(getString("SPAC_DT_DOCUMENTOS:BLOQUEO"), DocumentLockStates.TOTAL_LOCK))
	    	setProperty("BLOQUEO", Boolean.TRUE);
	    
	    IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
	    IItem itemCatDoc = entitiesAPI.getCatalogEntity(getMainEntity());
	    IItem itemExp = entitiesAPI.getExpedient(mitem.getString(itemCatDoc.getString("NOMBRE") + ":" + itemCatDoc.getString("CAMPO_NUMEXP")));
	    IItemCollection participantes=entitiesAPI.getParticipants(itemExp.get("NUMEXP").toString(), "", "");

	    // Obtener la información del destino
		IItem itemThird = getDestino(itemCatDoc, itemExp);
    	if (itemThird != null) {
    		((CompositeItem)mitem).addItem(itemThird, "THIRD_PARTY:", false);
	    }
	   
		((CompositeItem) mitem).addItem(
				getTipoDireccionTelematica(
						itemExp.getString("TIPODIRECCIONINTERESADO"),
						participantes), "SPAC_DT_INTERVINIENTES:");	  	   
	}
	
	protected IItem getDestino(IItem itemCatDoc, IItem itemExp) throws ISPACException {
		
		IItem destino = null;
		
		// Identificador del destinatario
		String idExt = mitem.getString(itemCatDoc.getString("NOMBRE") + ":DESTINO_ID");
	    if (idExt != null) {
	    	
	    	IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
	    	
	    	if ("0".equals(idExt)) {
	    		
	    		// El destinatario es un participante no validado
	    		// Buscamos el participante por nombre
	    		
	    		String nombre = mitem.getString(itemCatDoc.getString("NOMBRE") + ":DESTINO");

				if (StringUtils.equals(nombre, itemExp.getString("IDENTIDADTITULAR"))) {
					destino = composeThirdParty(
							itemExp.getString("IDENTIDADTITULAR"),
							itemExp.getString("IDTITULAR"),
							itemExp.getString("TIPODIRECCIONINTERESADO"));
				} else {
					IItemCollection itemcol = entitiesAPI.getParticipants(
							mitem.getString(itemCatDoc.getString("NOMBRE")
									+ ":"
									+ itemCatDoc.getString("CAMPO_NUMEXP")),
							null, null);
					while ((destino == null) && itemcol.next()) {
						IItem item = itemcol.value();
						if (StringUtils
								.equals(nombre, item.getString("NOMBRE"))) {
							destino = composeThirdParty(
									item.getString("NOMBRE"),
									item.getString("ID_EXT"),
									item.getString("TIPO_DIRECCION"));
						}
					}
				}

	    	} else {

	    		// El destinatario es un participante validado

				if (StringUtils.equals(idExt, itemExp.getString("IDTITULAR"))) {
					destino = composeThirdParty(
							itemExp.getString("IDENTIDADTITULAR"),
							itemExp.getString("IDTITULAR"),
							itemExp.getString("TIPODIRECCIONINTERESADO"));
				} else {
					IItemCollection itemcol = entitiesAPI
							.queryEntities(
									SpacEntities.SPAC_DT_INTERVINIENTES,
									"WHERE NUMEXP = '"
											+ DBUtil.replaceQuotes(mitem.getString(itemCatDoc
													.getString("NOMBRE")
													+ ":"
													+ itemCatDoc
															.getString("CAMPO_NUMEXP")))
											+ "' AND ID_EXT = '" + idExt + "'");
					if (itemcol.next()) {
						IItem item = itemcol.value();
						destino = composeThirdParty(item.getString("NOMBRE"),
								item.getString("ID_EXT"),
								item.getString("TIPO_DIRECCION"));
					}
				}
	    	}
	    }
	    
	    return destino;
	}
	
    public void store() throws ISPACException {
    	
        // Se establece la fecha de alarma si procede
        TaskHelper.setLimitDate(mContext, getItem());

        //Se comprueban si han habido cambios sobre el numero de registro para realizar las actuaciones oportunas 
        checkNumRegChange();

        super.store();
    }
    

	private void checkNumRegChange() throws ISPACException {
        IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
        IRegisterAPI registerAPI = mContext.getAPI().getRegisterAPI();
		IItem itemStored = entitiesAPI.getDocument(mitem.getKeyInt());

		//Comprobamos si la entidad SPAC_REGISTROS_ES esta vinculada al procedimiento
		boolean associatedRegESEntity = RegisterHelper.isAssocitedRegistrosESEntity(mContext, msExpedient);
		
		//Si no ha habido cambio en el numero de registro no se realiza nada
		if (StringUtils.equalsNullEmpty(itemStored.getString("NREG"), mitem.getString("SPAC_DT_DOCUMENTOS:NREG"))){
			return;		
		}

		//Si se ha producido un cambio en el numero de registro se borra el apunte del registro anterior si es que lo hubiera y no esta asociado al expediente directamente
		if (associatedRegESEntity && StringUtils.isNotEmpty(itemStored.getString("NREG"))){
			if (   entitiesAPI.countEntities(SpacEntities.SPAC_DT_DOCUMENTOS,
					"WHERE NUMEXP = '" + DBUtil.replaceQuotes(msExpedient)
							+ "' AND NREG = '" + itemStored.getString("NREG") 
							+ "' AND TP_REG = '" + itemStored.getString("TP_REG")
							+ "' AND ID != " + itemStored.getKeyInt()) == 0
				&& (StringUtils.equals(itemStored.getString("TP_REG"), RegisterType.SALIDA) 
				   || (StringUtils.equals(itemStored.getString("TP_REG"), RegisterType.ENTRADA) 
					    && entitiesAPI.countEntities(SpacEntities.SPAC_EXPEDIENTES,"WHERE NUMEXP = '" + DBUtil.replaceQuotes(msExpedient)+ "' AND NREG = '"+ itemStored.getString("NREG") + "'") == 0
					  )
				   )
				) {
				//se elimian los datos del registro asociado si lo hubiera
				entitiesAPI.deleteEntities(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NREG = '" + itemStored.getString("NREG") +"' AND TP_REG = '" + itemStored.getString("TP_REG") + "'");
				//Se desvincula el expediente del apunte de registro
				registerAPI.unlinkExpedient(new RegisterInfo(null, itemStored.getString("NREG"), null, itemStored.getString("TP_REG")), msExpedient);
			}
		}
		//Si el nuevo numero de registro esta vacio no se haria nada mas
		if (StringUtils.isEmpty(mitem.getString("SPAC_DT_DOCUMENTOS:NREG"))){ 
			return;
		}

		String tipoReg = mitem.getString("SPAC_DT_DOCUMENTOS:TP_REG");
		
		if (associatedRegESEntity){ 
			//Se crea un nuevo registro para el apunte de Entrada o Salida si no existe para el numero de registro actual
			if (entitiesAPI.countResultQuery(SpacEntities.SPAC_REGISTROS_ES_NAME, "WHERE NREG = '" + mitem.getString("SPAC_DT_DOCUMENTOS:NREG") + "' AND NUMEXP = '"+DBUtil.replaceQuotes(msExpedient)+"' AND TP_REG = '" + tipoReg + "'") == 0){
				IItem itemRegisterES = entitiesAPI.createEntity(SpacEntities.SPAC_REGISTROS_ES_NAME, DBUtil.replaceQuotes(msExpedient));
				
				itemRegisterES.set("NREG", mitem.get("SPAC_DT_DOCUMENTOS:NREG"));
				itemRegisterES.set("FREG", mitem.get("SPAC_DT_DOCUMENTOS:FREG"));
				itemRegisterES.set("ASUNTO", mitem.get("SPAC_DT_DOCUMENTOS:DESCRIPCION"));
				
				if (StringUtils.equals(tipoReg, RegisterType.ENTRADA)){
					itemRegisterES.set("ID_INTERESADO", mitem.get("SPAC_DT_DOCUMENTOS:ORIGEN_ID"));
					itemRegisterES.set("INTERESADO", mitem.get("SPAC_DT_DOCUMENTOS:ORIGEN"));
				}else{
					itemRegisterES.set("ID_INTERESADO", mitem.get("SPAC_DT_DOCUMENTOS:DESTINO_ID"));
					itemRegisterES.set("INTERESADO", mitem.get("SPAC_DT_DOCUMENTOS:DESTINO"));
				}
				itemRegisterES.set("TP_REG", tipoReg);
				itemRegisterES.set("ORIGINO_EXPEDIENTE", "NO");
				itemRegisterES.set("ID_TRAMITE", mitem.getInt("SPAC_DT_DOCUMENTOS:ID_TRAMITE"));
				itemRegisterES.store(mContext);
	
				//Se vincula el expediente al apunte de registro
				registerAPI.linkExpedient(new RegisterInfo(null, itemStored.getString("NREG"), null, itemStored.getString("TP_REG")), msExpedient);
			}
		}
		
		//Se da de alta los interesados como participantes en el expediente
		RegisterHelper.insertParticipants(mContext, mitem.getString("SPAC_DT_DOCUMENTOS:NREG"),tipoReg, msExpedient, true);
	}

	public IItem getTipoDireccionTelematica(String direccion, IItemCollection participantes ) throws ISPACException{
    	Properties properties = new Properties();
    	int ordinal = 0;
    	boolean asignado=false;
    	Object destino=this.getProperty("SPAC_DT_DOCUMENTOS:DESTINO");
    	properties.add(new Property(ordinal++,"TIPODIRECCIONINTERESADO",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"ID",Types.VARCHAR));
    	IItem item = new GenericItem(properties, "TIPODIRECCIONINTERESADO");
    	//Es el principal
    	if(participantes==null || !participantes.iterator().hasNext() || destino==null){
    		item.set("TIPODIRECCIONINTERESADO", direccion);
    		
    		asignado=true;
    	}
    	else{
    		
    		Iterator itr= participantes.iterator();
    		while(itr.hasNext() && !asignado){
    			IItem item1= (IItem) itr.next();
    			String nombrePart=(String) item1.get("NOMBRE");
    			
    			if (StringUtils.isNotBlank(nombrePart) 
    					&& nombrePart.equalsIgnoreCase(destino.toString())) {
    				asignado=true;
    				item.set("TIPODIRECCIONINTERESADO", item1.get("TIPO_DIRECCION"));
    				item.set("ID",item1.get("ID"));
    			}
    		}
    		
    	}
    	//Es destino es el interesado principal
    	if(!asignado){
    		item.set("TIPODIRECCIONINTERESADO", direccion);
    	}
    	return item;
    }
    
    public IItem composeThirdParty (String nombre, String idExt, String tipoDireccionTelematica) throws ISPACException{
    	Properties properties = new Properties();
    	int ordinal = 0;
    	properties.add(new Property(ordinal++,"IDTITULAR",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"IDENTIDADTITULAR",Types.VARCHAR));
    	properties.add(new Property(ordinal++,"TIPO_DIRECCION_TELEMATICA_VALOR",Types.VARCHAR));    	
    	properties.add(new Property(ordinal++,"TIPO_DIRECCION_TELEMATICA_SUSTITUTO",Types.VARCHAR));    	
    	
    	IItem item = new GenericItem(properties, "IDTITULAR");
    	item.set("IDENTIDADTITULAR", nombre);
    	item.set("IDTITULAR", idExt);
    	item.set("TIPO_DIRECCION_TELEMATICA_VALOR", tipoDireccionTelematica);

    	IEntitiesAPI entitiesAPI = mContext.getAPI().getEntitiesAPI();
    	IItemCollection itemcol =  entitiesAPI.queryEntities(SpacEntities.SPAC_TBL_005, "WHERE VALOR = '" + DBUtil.replaceQuotes(tipoDireccionTelematica) + "'");
    	
    	if (itemcol.next())
    		tipoDireccionTelematica = itemcol.value().getString("SUSTITUTO");
    	else
    		tipoDireccionTelematica = "";
    		item.set("TIPO_DIRECCION_TELEMATICA_SUSTITUTO", tipoDireccionTelematica);
    	return item;
    }
    
}

