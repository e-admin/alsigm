package com.ieci.tecdoc.isicres.usecase.validationlist.xml;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.invesdoc.Iuserdepthdr;
import com.ieci.tecdoc.common.invesdoc.Iusergrouphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapgrphdr;
import com.ieci.tecdoc.common.invesdoc.Iuserldapuserhdr;
import com.ieci.tecdoc.common.invesdoc.Iuseruserhdr;

public class XMLDeptsGroupsUsers extends XMLValidationListAbstract {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLDeptsGroupsUsers.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static String createXMLDeptsGroupsUser(List results) {
        Object object = null;
        
        Document document = DocumentHelper.createDocument();

        Element root = document.addElement(XML_USERS_TEXT);
        Iuserdepthdr dept = null;
        Iusergrouphdr group = null;
        Iuseruserhdr user = null;
        Iuserldapuserhdr userLdap = null;
        Iuserldapgrphdr groupLdap = null;
        Element deptsGroupsUsersElement = null;
        
        for (Iterator it = results.iterator(); it.hasNext();) {
            object = it.next();
        	deptsGroupsUsersElement = root.addElement(XML_USER_TEXT);
            if (object instanceof Iuserdepthdr) {
            	dept = (Iuserdepthdr) object;
            	deptsGroupsUsersElement.addElement(XML_ID_TEXT).setText(dept.getId().toString());
            	deptsGroupsUsersElement.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(dept.getName()));
            } else if (object instanceof Iusergrouphdr) {
            	group = (Iusergrouphdr) object;
            	deptsGroupsUsersElement.addElement(XML_ID_TEXT).setText(group.getId().toString());
            	deptsGroupsUsersElement.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(group.getName()));
            } else if (object instanceof Iuseruserhdr) {
            	user = (Iuseruserhdr) object;
            	deptsGroupsUsersElement.addElement(XML_ID_TEXT).setText(user.getId().toString());
            	deptsGroupsUsersElement.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(user.getName()));
            }
            else if (object instanceof Iuserldapuserhdr) {
            	userLdap = (Iuserldapuserhdr) object;
            	deptsGroupsUsersElement.addElement(XML_ID_TEXT).setText(Integer.toString(userLdap.getId()));
            	deptsGroupsUsersElement.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(userLdap.getLdapfullname()));
            }
            else if (object instanceof Iuserldapgrphdr) {
            	groupLdap = (Iuserldapgrphdr) object;
            	deptsGroupsUsersElement.addElement(XML_ID_TEXT).setText(Integer.toString(groupLdap.getId()));
            	deptsGroupsUsersElement.addElement(XML_NAME_TEXT).add(DocumentHelper.createCDATA(groupLdap.getLdapfullname()));
            }
        }

        return document.asXML();
    }

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

