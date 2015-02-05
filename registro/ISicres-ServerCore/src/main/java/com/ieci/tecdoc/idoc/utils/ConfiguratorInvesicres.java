package com.ieci.tecdoc.idoc.utils;

import java.util.Iterator;
import java.util.List;

import gnu.trove.THashMap;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Session;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ieci.tecdoc.utils.HibernateUtil;
import com.ieci.tecdoc.common.conf.InvesicresConf;
import com.ieci.tecdoc.common.invesicres.ScrConfiguration;
import com.ieci.tecdoc.common.keys.HibernateKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;


/**
 * @author 79426599
 *
 */
public class ConfiguratorInvesicres {

	private static final Logger log = Logger.getLogger(ConfiguratorInvesicres.class);

	private static ConfiguratorInvesicres singleton = null;
	// HashMap
	private THashMap cacheConfigInv = null;

	public ConfiguratorInvesicres(String entidad) {
		cacheConfigInv = new THashMap();

		init(entidad);
	}

	public synchronized static ConfiguratorInvesicres getInstance(String entidad) {
		if (singleton == null) {
			singleton = new ConfiguratorInvesicres(entidad);
		}
		return singleton;
	}

	public InvesicresConf getInvesicresConf(){
		return (InvesicresConf) cacheConfigInv.get(ServerKeys.INVESICRES_CONFIGURATION);
	}

	public synchronized void setProperty(String key, Object value) {
		if (key != null && value != null) {
			cacheConfigInv.put(key, value);
		}
	}

	private void init(String entidad) {
		try {
			Session session = HibernateUtil.currentSession(entidad);

			// Obtenemos la configuración de invesicres para el sistema
	        StringBuffer query = new StringBuffer();
	        query.append("FROM ");
	        query.append(HibernateKeys.HIBERNATE_ScrConfiguration);
	        List list =  session.find(query.toString());
	        ScrConfiguration scrConfiguration = null;
	        String textConf = null;
            if(list != null && !list.isEmpty()) {
            	scrConfiguration = (ScrConfiguration) list.get(0);
            	textConf = scrConfiguration.getOptions();
            }
			setProperty(ServerKeys.INVESICRES_CONFIGURATION, createFromStringText(textConf));

		} catch (HibernateException e) {
			log.error("Impossible to load values for invesicres configuration.", e);
		} catch (Exception e) {
			log.error(
					"Impossible to load values for invesicres configuration.",
					e);
		} finally {
			HibernateUtil.closeSession(entidad);
		}

	}

    private InvesicresConf createFromStringText(String text) throws Exception {
    	String xmlText = null;
    	Document document = null;
    	if (text != null){
    		xmlText = text.substring(text.indexOf("Configuration")-1, text.length());
    		document = DocumentHelper.parseText(xmlText);
    	}
		InvesicresConf invesicresConf = getInvesicresConf (document);
		return invesicresConf;

	}

    private InvesicresConf getInvesicresConf(Document document) throws Exception{
    	InvesicresConf invesicresConf = new InvesicresConf();
    	Node node = null;

    	if (document != null){
	    	node = document.selectSingleNode(ServerKeys.XPATH_ALARM_ON_INCOMPLETE_REG);
	    	if(node != null){
	    		invesicresConf.setAlarmOnIncompleteReg(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_MODIFY_SYSTEM_DATE);
	    	if(node != null){
	    		invesicresConf.setModifySystemDate(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_REPOSITORY_SYSTEM);
	    	if(node != null){
	    		invesicresConf.setRepositorySystem(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_POPULATION_PARTITION);
	    	if(node != null){
	    		invesicresConf.setPopulationPartition(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_DOCUMENT_HASHING);
	    	if(node != null){
	    		invesicresConf.setDocumentHashing(new Integer(node.getText()).intValue());
	    	}

	    	List list = document.selectNodes(ServerKeys.XPATH_FORMAT_TYPE_ATRIBUTE);
	    	if (list != null && !list.isEmpty()){
		        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
		             Attribute attribute = (Attribute) iter.next();
		             int type = new Integer(attribute.getValue()).intValue();
		             if (type == 0){
		             	invesicresConf.setFormatType0(attribute.getParent().getText());
		             }
		             if (type == 1){
		             	invesicresConf.setFormatType1(attribute.getParent().getText());
		             }
		             if (type == 2){
		             	invesicresConf.setFormatType2(attribute.getParent().getText());
		             }
		        }
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_TIMEOUT);
	    	if(node != null){
	    		invesicresConf.setTimeOut(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_ALARM_ON_NEW_DIST);
	    	if(node != null){
	    		invesicresConf.setAlarmOnNewDist(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_AUTO_ARCHIVING);
	    	if(node != null){
	    		invesicresConf.setAutoArchiving(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_ALARM_ON_REJECTED);
	    	if(node != null){
	    		invesicresConf.setAlarmOnRejected(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_DIST_OUT_REGISTERS);
	    	if(node != null){
	    		invesicresConf.setDistSRegister(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_VIEW_ACCEPTED_REGISTERS);
	    	if(node != null){
	    		invesicresConf.setViewAcceptedRegisters(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_CAN_CHANGE_DEST_WITHOUT_LIST);
	    	if(node != null){
	    		invesicresConf.setCanChangeDestWithoutList(new Integer(node.getText()).intValue());
	    	}
			node = document.selectSingleNode(ServerKeys.XPATH_AUTO_DIST);
	    	if(node != null){
	    		invesicresConf.setAutoDist(new Integer(node.getText()).intValue());
	    	}
	    	node = document.selectSingleNode(ServerKeys.XPATH_VALIDATION);
	    	if(node != null){
	    		invesicresConf.setValidation(node.getText());
	    	}
    	}

    	return invesicresConf;
    }

}
