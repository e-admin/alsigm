/*
 * Created on 02-jul-2004
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package ieci.tdw.ispac.ispactx;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PSincNodoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXProcesoDAO;
import ieci.tdw.ispac.ispaclib.dao.tx.TXSincNodoDAO;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Node;



/**
 * @author   juanin  To change the template for this generated type comment go to  Window - Preferences - Java - Code Generation - Code and Comments
 */
public class TXNodeActivationManager
{
	private final TXDAOGen mgenDAO;
	private final TXProcedure mprocedure;
	private final TXTransactionDataContainer mdtc;

	public TXNodeActivationManager(TXDAOGen genDAO,TXProcedure procedure,TXTransactionDataContainer dtc)
	{
		mgenDAO=genDAO;
		mprocedure=procedure;
		mdtc=dtc;
	}

	public List activateNodes(int nIdNodeActivatorPCD,Iterator nodeit, TXProcesoDAO exped, String nodeRespId)
	throws ISPACException
	{
		int nIdProc=exped.getKeyInt();

		//Se necesitan todas los nodos del expediente en TXTransactionDataContainer
		//para que testStageOpen y testSyncNodeOpen funcionen correctamente.
		mdtc.loadProcessStages(nIdProc);
		mdtc.loadProcessSyncNodes(nIdProc);

		List list = new ArrayList();
		while(nodeit.hasNext())
		{
			list.add(processNode(nIdNodeActivatorPCD,((Integer)nodeit.next()).intValue(),exped,nodeRespId));
		}
		return list;
	}

	public TXFaseDAO activateNode(int nIdNodeActivatorPCD, int nIdNodePCD, TXProcesoDAO process, String nodeRespId) throws ISPACException {
		int nIdProc=process.getKeyInt();

		//Se necesitan todas los nodos del expediente en TXTransactionDataContainer
		//para que testStageOpen y testSyncNodeOpen funcionen correctamente.
		mdtc.loadProcessStages(nIdProc);
		mdtc.loadProcessSyncNodes(nIdProc);		
		return processNode(nIdNodeActivatorPCD,nIdNodePCD,process,nodeRespId);
	}
	
	
	
	
	public boolean testStageOpen(int nIdProc,int nIdStagePCD)
	throws ISPACException
	{
		//Se necesitan todas los nodos del expediente en TXTransactionDataContainer
		//para que testStageOpen y testSyncNodeOpen funcionen correctamente.
		mdtc.loadProcessStages(nIdProc);
		mdtc.loadProcessSyncNodes(nIdProc);

		return checkStageOpen(nIdProc,nIdStagePCD);
	}

	public boolean testSyncNodeOpen(int nIdProc,int nIdSyncNodePCD)
	throws ISPACException
	{
		//Se necesitan todas los nodos del expediente en TXTransactionDataContainer
		//para que testStageOpen y testSyncNodeOpen funcionen correctamente.
		mdtc.loadProcessStages(nIdProc);
		mdtc.loadProcessSyncNodes(nIdProc);

		return checkSyncNodeOpen(nIdProc,nIdSyncNodePCD);
	}

	private TXFaseDAO processNode(int nIdNodeActivatorPCD,int nIdNodePCD,TXProcesoDAO process,String nodeRespId)
	throws ISPACException
	{
		int nIdProc=process.getKeyInt();
		PFaseDAO pstage=mprocedure.getStageDAO(nIdNodePCD);
		if (pstage!=null)
		{
			//Sólo se instancia si la fase no está ya abierta.
			if (!checkStageOpen(nIdProc,pstage.getKeyInt()))
			{
				// Marcar hito en fase / actividad
				int milestoneType = TXConstants.MILESTONE_STAGE_START;
				if (process.isSubProcess())
					milestoneType = TXConstants.MILESTONE_ACTIVITY_START;
						
				mdtc.newMilestone(process.getKeyInt(),pstage.getKeyInt(),0,milestoneType);
				
				// Iniciar fase / actividad
				TXFaseDAO newstage=mdtc.newStage();
				mgenDAO.instance(pstage,newstage,process,nodeRespId);
				
				return newstage;
			}
			return null;
		}

		processSyncNode(nIdNodeActivatorPCD,nIdNodePCD,process);
		//Sólo se instancia si el nodo no está ya abierto.
//		if (!checkSyncNodeOpen(nIdProc,nIdNodePCD))
//		{
//			PSincNodoDAO sincnodo=mprocedure.getSyncNode(nIdNodePCD);
//			mgenDAO.instance(sincnodo,mdtc.newSyncNode(),exped);
//		}
		return null;
	}


	public List processSyncNode(int nIdNodeActivatorPCD,int nIdNodePCD,TXProcesoDAO exped)
	throws ISPACException
	{
		mdtc.loadProcessSyncNodes(exped.getKeyInt());
		TXSincNodoDAO syncnode=getSyncNodeByPCD(exped.getKeyInt(),nIdNodePCD);

		
		if (syncnode==null)
		{
			PSincNodoDAO sincnodo=mprocedure.getSyncNodeDAO(nIdNodePCD);
			syncnode=mdtc.newSyncNode();
			mgenDAO.instance(sincnodo,syncnode,exped);
		}
		
//		int processNode = processSyncNodeState(nIdNodeActivatorPCD,syncnode);
//		//Se podra eliminar el nodo de sincronizacion si se retorna estado OK para un nodo AND o que todos las entradas han llegado a un nodo OR
//		if (processNode == TXConstants.SYNCNODE_AND_EVALUATE_OK || processNode == TXConstants.SYNCNODE_OR_EVALUATE_ALL_OK || processNode == TXConstants.SYNCNODE_EVALUATE_OK)
//			closeSyncNode(syncnode,exped);
//		//Se retornar las siguientes fases de un nodo si retorna OK un nodo AND o cuando haya llegado el primero flujo a un nodo OR
//		//OJO: Esta condicion puede cambiar, si se decide el funcionamiento de un proceso no es asi
//		if (processNode == TXConstants.SYNCNODE_AND_EVALUATE_OK || processNode == TXConstants.SYNCNODE_OR_FIRST_OK || processNode == TXConstants.SYNCNODE_EVALUATE_OK){
//			TXStateTable statetable=mprocedure.getStateTable();
//			Set stageset=statetable.nextStages(mgenDAO.getEventManager(),syncnode.getInt("ID_NODO"));
//			return new ArrayList(stageset);
//		}
//		return null;
//		
		if (processSyncNodeState(nIdNodeActivatorPCD,syncnode)){
			closeSyncNode(syncnode,exped);
			TXStateTable statetable=mprocedure.getStateTable();
			Set stageset=statetable.nextStages(mgenDAO.getEventManager(),syncnode.getInt("ID_NODO"));
			return new ArrayList(stageset);			
		}
		return null;
	}

	private boolean checkStageOpen(int nIdProc,int nIdStagePCD)
	throws ISPACException
	{
		Iterator it=mdtc.getStages();
		while (it.hasNext())
		{
			//TXFaseDAO stage = (TXFaseDAO) it.next();
			TXFaseDAO stage = (TXFaseDAO)((Map.Entry)it.next()).getValue();
			if (nIdProc == stage.getInt("ID_EXP") &&
			    nIdStagePCD == stage.getInt("ID_FASE"))
				return (stage.getInt("ESTADO")==TXConstants.STATUS_OPEN);
		}
		return false;
	}

	private boolean checkSyncNodeOpen(int nIdProc,int nIdSyncNodePCD)
	throws ISPACException
	{
		Iterator it=mdtc.getSyncNodes();
		while (it.hasNext())
		{
			TXSincNodoDAO syncnode = (TXSincNodoDAO)((Map.Entry)it.next()).getValue();
			if (	nIdProc == syncnode.getInt("ID_EXP") &&
			        nIdSyncNodePCD == syncnode.getInt("ID_NODO"))
				return true;
		}
		return false;
	}

	private TXSincNodoDAO getSyncNodeByPCD(int nIdProc,int nIdSyncNodePCD)
	throws ISPACException
	{
		Iterator it=mdtc.getSyncNodes();
		while (it.hasNext())
		{
			TXSincNodoDAO syncnode = (TXSincNodoDAO)((Map.Entry)it.next()).getValue();
			if (	nIdProc == syncnode.getInt("ID_EXP") &&
			        nIdSyncNodePCD == syncnode.getInt("ID_NODO"))
				return syncnode;
		}
		return null;
	}

	private boolean processSyncNodeState(int nIdNodeActivatorPCD,TXSincNodoDAO syncnode)
	throws ISPACException
	{
		//Un nodo de sincronización sin antecesor implica que es un nodo inicio y por tanto
		// debe cerrarse inmediatamente.
		if (nIdNodeActivatorPCD==0)
			return true;

		String syncnodestate=syncnode.getString("ESTADO");
		if (syncnodestate==null)
			syncnodestate=newSyncNodeState(syncnode);

		Document docxml=XMLDocUtil.newDocument(syncnodestate);
		Node node=XPathUtil.selectNode(docxml,"/estado/nodo[@id='"+nIdNodeActivatorPCD+"']/text()");
		node.setNodeValue("1");

		syncnode.set("ESTADO",XMLDocUtil.toString(docxml));

		return testSyncNodeState(docxml,syncnode.getInt("TIPO"));
	}

	/**
	 * @param docxml
	 * @param tipo
	 */
	private boolean testSyncNodeState(Document docxml, int tipo)
	throws ISPACException
	{
		switch(tipo)
		{
			case TXConstants.SYNCNODE_AND:
			{
				//Deben haberse activado todos los nodos antecesores.
				return (XPathUtil.getNodeList(docxml,"/estado/nodo[text() = '0']").getLength()==0);
			}
			case TXConstants.SYNCNODE_OR:
			{
				//Deben haberse activado algún nodo antecesor.
				return (XPathUtil.getNodeList(docxml,"/estado/nodo[text() = '1']").getLength()!=0);
			}
			case TXConstants.SYNCNODE_CUSTOM:
			{
				//TODO Implementar nodos con eventos.
				return true;
			}
			default:
				return false;
		}
	}

	
//	private int testSyncNodeState(Document docxml, int tipo)
//	throws ISPACException
//	{
//		switch(tipo)
//		{
//			case TXConstants.SYNCNODE_AND:
//			{
//				//Deben haberse activado todos los nodos antecesores.
//				boolean ok = (XPathUtil.getNodeList(docxml,"/estado/nodo[text() = '0']").getLength()==0);
//				if (ok)
//					return TXConstants.SYNCNODE_AND_EVALUATE_OK;
//				return TXConstants.SYNCNODE_EVALUATE_KO;
//			}
//			case TXConstants.SYNCNODE_OR:
//			{
//				//Deben haberse activado algún nodo antecesor.
//				int previousNodes = XPathUtil.getNodeList(docxml,"/estado/nodo").getLength();
//				int previousNodesOK = XPathUtil.getNodeList(docxml,"/estado/nodo[text() = '1']").getLength(); 
//				if (previousNodesOK == 0)
//					return TXConstants.SYNCNODE_EVALUATE_KO;
//				
//				if (previousNodes == previousNodesOK)
//					return TXConstants.SYNCNODE_OR_EVALUATE_ALL_OK;
//				if (previousNodesOK == 1)
//					return TXConstants.SYNCNODE_OR_FIRST_OK;
//				if (previousNodesOK > 1)
//					return TXConstants.SYNCNODE_OR_ALREADY_EVALUATED_OK;
//				
//								
//				
//			}
//			case TXConstants.SYNCNODE_CUSTOM:
//			{
//				//TODO Implementar nodos con eventos.
//				return TXConstants.SYNCNODE_EVALUATE_OK;
//			}
//			default:
//				return TXConstants.SYNCNODE_EVALUATE_KO;
//		}
//	}
	
	
	
	private String newSyncNodeState(TXSincNodoDAO syncnode)
	throws ISPACException
	{
		TXStateTable statetable=mprocedure.getStateTable();
		Set stageset=statetable.previousStages(syncnode.getInt("ID_NODO"));

		String sXml="";
		Iterator it=stageset.iterator();
		while (it.hasNext())
		{
			Number idnodo=(Number)it.next();
			sXml+=XmlTag.newTag("nodo",0,idnodo.intValue());
		}

		String stateXml=XmlTag.getXmlInstruction("ISO-8859-1")+
						XmlTag.newTag("estado",sXml);

		syncnode.set("ESTADO",stateXml);
		return stateXml;
	}

	public void closeSyncNode(TXSincNodoDAO syncnode,TXProcesoDAO exped)
	throws ISPACException
	{
		mdtc.deleteSyncNode(syncnode.getKeyInt());

//		TXStateTable statetable=mprocedure.getStateTable();
//		Set stageset=statetable.nextStages(mgenDAO.getEventManager(),syncnode.getInt("ID_NODO"));
//
//		Iterator nodeit=stageset.iterator();
//		while(nodeit.hasNext())
//		{
//			processNode(syncnode.getInt("ID_NODO"),((Integer)nodeit.next()).intValue(),exped);
//		}
	}

}