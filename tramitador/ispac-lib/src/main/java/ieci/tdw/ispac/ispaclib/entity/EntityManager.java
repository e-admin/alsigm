
package ieci.tdw.ispac.ispaclib.entity;

import ieci.tdw.ispac.api.ISPACEntities;
import ieci.tdw.ispac.api.entities.SpacEntities;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IProcess;
import ieci.tdw.ispac.api.item.IResponsible;
import ieci.tdw.ispac.ispaclib.common.constants.NotifyStatesConstants;
import ieci.tdw.ispac.ispaclib.common.constants.SignStatesConstants;
import ieci.tdw.ispac.ispaclib.configuration.ConfigurationMgr;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTEntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityDAO;
import ieci.tdw.ispac.ispaclib.dao.entity.EntityFactoryDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;
import ieci.tdw.ispac.ispaclib.sicres.vo.RegisterType;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.Date;



public class EntityManager
{
	ClientContext mcontext;

	public EntityManager(ClientContext context)
	{
		mcontext=context;
	}

	public EntityDAO createEntity(DbCnt cnt,int entityId,String numexp)
	throws ISPACException
	{
		CTEntityDAO catentity =EntityFactoryDAO.getInstance().getCatalogEntityDAO(cnt,entityId);
		String propname=catentity.getString("CAMPO_NUMEXP");

		EntityDAO entity = EntityFactoryDAO.getInstance().newEntityDAO(cnt,catentity);
		entity.createNew(cnt);
		entity.set(propname,numexp);
//		entity.store(cnt);
		return entity;
	}

	public EntityDAO createExpedient(DbCnt cnt, IProcess process)
	throws ISPACException
	{
		EntityFactoryDAO entityFactoryDAO = EntityFactoryDAO.getInstance();
		
		EntityDAO entity = entityFactoryDAO.newEntityDAO(cnt, ISPACEntities.DT_ID_EXPEDIENTES);
		entity.createNew(cnt);
		entity.set("NUMEXP", process.getString("NUMEXP"));
		entity.set("ID_PCD", process.getInt("ID_PCD"));
		entity.set("FAPERTURA", new Date());

        // Copiar valores del catálogo
        IItem ctprocedure = entityFactoryDAO.getEntity(cnt, mcontext, SpacEntities.SPAC_CT_PROCEDIMIENTOS, process.getInt("ID_PCD"));
        entity.set("NOMBREPROCEDIMIENTO", ctprocedure.get("NOMBRE"));
        entity.set("CODPROCEDIMIENTO", ctprocedure.get("COD_PCD"));
        entity.set("ASUNTO", ctprocedure.get("ASUNTO"));
        entity.set("PLAZO", ctprocedure.get("PLZ_RESOL"));
        entity.set("UPLAZO", ctprocedure.get("UNID_PLZ"));
        entity.set("FUNCIONACTIVIDAD", ctprocedure.get("ACT_FUNC"));
        entity.set("MATERIAS", ctprocedure.get("MTRS_COMP"));
        entity.set("EFECTOSDELSILENCIO", ctprocedure.get("EFEC_SILEN"));
        entity.set("ROLTITULAR", ctprocedure.get("TP_REL"));
        entity.set("IDPROCESO", process.getKeyInt());

        // Estado administrativo
        String valorEstadoInicial = ConfigurationMgr.getVarGlobal(mcontext, ConfigurationMgr.ESTADOADM_INICIAL);
        entity.set("ESTADOADM", valorEstadoInicial);

        //Obtener la unidad organizativa del responsable
        IResponsible resp = mcontext.getResponsible();
        if (resp != null) {
        	IResponsible departamento = null;
	        if (resp.isUser()) {
	        	departamento = resp.getRespOrgUnit();
	        } else if (resp.isOrgUnit()) {
	        	departamento = resp;
	        } else { // resp.isGroup()
	        	// No hay departamento
	        }
	        if (departamento != null) {
	        	
	        	//entity.set("UTRAMITADORA", departamento.getName());
	        	entity.set("IDSECCIONINICIADORA", departamento.getUID());
	        	entity.set("SECCIONINICIADORA", departamento.getName());
	        }
        }

//		entity.store(cnt);
		return entity;
	}

	public EntityDAO getExpedient(DbCnt cnt, String numexp) throws ISPACException {
		return (EntityDAO) EntityFactoryDAO.getInstance().getEntity(cnt, ISPACEntities.DT_ID_EXPEDIENTES, numexp);
	}
	
	public EntityDAO createDocument(DbCnt cnt,String numexp,DocumentData docdata)
	throws ISPACException
	{
		EntityFactoryDAO entityFactoryDAO = EntityFactoryDAO.getInstance();
		
		EntityDAO entity = entityFactoryDAO.newEntityDAO(cnt,ISPACEntities.DT_ID_DOCUMENTOS);
		entity.createNew(cnt);
		entity.set("NUMEXP",numexp);
		entity.set("FDOC",new Date());
		entity.set("NOMBRE",docdata.getName());
		entity.set("AUTOR",docdata.getAuthor());
		entity.set("AUTOR_INFO", docdata.getAuthorName());
		entity.set("ID_FASE",docdata.getStage());
		entity.set("ID_TRAMITE",docdata.getTask());
		entity.set("ID_FASE_PCD",docdata.getStagePCD());
		entity.set("ID_TRAMITE_PCD",docdata.getTaskPCD());
		entity.set("ID_TPDOC",docdata.getDocType());
		entity.set("ESTADO","BORRADOR");
		entity.set("ID_PLANTILLA", docdata.getTemplate());
		entity.set("ID_ENTIDAD", docdata.getEntity());
		entity.set("ID_REG_ENTIDAD", docdata.getKey());

		// Establecer información en función del tipo de registro del tipo de documento
		String regType = docdata.getRegType();
	    if (StringUtils.isBlank(regType) || StringUtils.equalsIgnoreCase(regType, RegisterType.NINGUNO)) {
	    	regType = RegisterType.NINGUNO;
	    } else if (StringUtils.equalsIgnoreCase(regType, RegisterType.SALIDA)) {
	    	
	    	//Establecer como destino por defecto el interesado principal del Expediente
	        IItem expediente = entityFactoryDAO.getEntity(cnt, SpacEntities.SPAC_EXPEDIENTES, numexp);
	        if (expediente != null) { 
		        String id = expediente.getString("IDTITULAR");
		        String nombre = expediente.getString("IDENTIDADTITULAR");
		        String tipo = expediente.getString("TIPOPERSONA");
		        String tipoDir = expediente.getString("TIPODIRECCIONINTERESADO");
	
		        if (StringUtils.isNotBlank(nombre)) {
		            if (StringUtils.isBlank(id)) {
		            	id = "0";
		            }
		            if (StringUtils.isBlank(tipo)|| id.equals("0")) {
		            	tipo = "N"; //No validado
		            }
		            
		            entity.set("DESTINO", nombre);
		            entity.set("DESTINO_ID", id);
		            entity.set("DESTINO_TIPO", tipo);
		        }

		        // Notificación telemática
		        if ("T".equalsIgnoreCase(tipoDir)) {
			        entity.set("ESTADONOTIFICACION", NotifyStatesConstants.UNRESOLVED);
			        entity.set("FNOTIFICACION", new Date());
		        }
	        }

	        
	    } else if (StringUtils.equalsIgnoreCase(regType, RegisterType.ENTRADA)) {
	    }
	    
	    entity.set("TP_REG", regType);

	    // Estado y fecha de firma
        entity.set("ESTADOFIRMA", SignStatesConstants.SIN_FIRMA);
        entity.set("FFIRMA", new Date());

		return entity;
	}

	public EntityDAO getDocument(DbCnt cnt,int keyId)
	throws ISPACException
	{
		EntityDAO entity = EntityFactoryDAO.getInstance().newEntityDAO(cnt,ISPACEntities.DT_ID_DOCUMENTOS);
		entity.setKey(keyId);
		entity.load(cnt);
		return entity;
	}

	public EntityDAO createTask(DbCnt cnt,TXTramiteDAO task)
	throws ISPACException
	{
		EntityDAO entity = EntityFactoryDAO.getInstance().newEntityDAO(cnt,ISPACEntities.DT_ID_TRAMITES);
		entity.createNew(cnt);
		entity.set("NUMEXP",task.getString("NUMEXP"));
		entity.set("NOMBRE",task.getString("NOMBRE"));
		entity.set("ESTADO",1);
		entity.set("ID_TRAM_EXP",task.getInt("ID"));
		entity.set("ID_FASE_EXP",task.getInt("ID_FASE_EXP"));
		entity.set("ID_TRAM_PCD",task.getInt("ID_TRAMITE"));
		entity.set("ID_FASE_PCD",task.getInt("ID_FASE_PCD"));
		entity.set("FECHA_INICIO",task.getDate("FECHA_INICIO"));
		entity.set("ID_TRAM_CTL",task.getInt("ID_CTTRAMITE"));
		return entity;
	}
}
