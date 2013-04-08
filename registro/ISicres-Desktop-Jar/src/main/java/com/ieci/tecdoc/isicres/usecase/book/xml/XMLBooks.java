package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.utils.ScrRegStateByLanguage;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;


/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:52:18
 * @version
 * @since
 */
public class XMLBooks implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLBooks.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static Document createXMLBooks(List inList, List outList, Locale locale, int sizeNewDist, int sizeRejectedDist,
    		SessionInformation sessionInformation) {
        Document document = DocumentHelper.createDocument();
        String[] message = new String[2];

        if (sizeNewDist == 1){
        	message[0]= RBUtil.getInstance(locale).getProperty(I18N_DISTRIBUTION_NEW_FOLDER_FOR_USER);
        } else if (sizeNewDist > 1){
        	message[0] = MessageFormat.format(RBUtil.getInstance(locale).getProperty(
                    I18N_DISTRIBUTION_NEW_FOLDERS_FOR_USER), new String[] { new Integer(sizeNewDist).toString() });
        }

        if (sizeRejectedDist == 1){
        	message[1]= RBUtil.getInstance(locale).getProperty(I18N_DISTRIBUTION_NEW_FOLDER_REJECTED_FOR_USER);
        } else if (sizeRejectedDist > 1){
        	message[1] = MessageFormat.format(RBUtil.getInstance(locale).getProperty(
                    I18N_DISTRIBUTION_NEW_FOLDERS_REJECTED_FOR_USER), new String[] { new Integer(sizeRejectedDist).toString() });
        }

        Element root = document.addElement(XML_INVESICRES_TEXT);
        Element sessionInfo = root.addElement(XML_SESSION_TEXT);
        Element bookTree = root.addElement(XML_BOOKTREE_TEXT);

        addSessionInfo(sessionInformation, sessionInfo);

		Element messagesDist = root.addElement(XML_MESSAGES_TEXT);
		Element messageText = null;
		for (int i = 0; i < message.length; i++) {
			if (message[i] != null) {
				// messagesDist.addElement(XML_MESSAGE_TEXT).add(DocumentHelper.createCDATA(message[i]));
				messageText = messagesDist.addElement(XML_MESSAGE_TEXT);
				messageText.addAttribute(XML_TYPE_TEXT,
						new Integer(i).toString()).add(
						DocumentHelper.createCDATA(message[i]));
			}
		}

        bookTree.addElement(XML_ROOTNAME_TEXT).add(DocumentHelper.createCDATA(RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_ROOTNAME)));

        Element inNode = addNode(XML_DIR_VALUE, XML_INBOOK_ID_VALUE, RBUtil.getInstance(locale)
                .getProperty(I18N_BOOKUSECASE_NODE_INBOOK_NAME), XML_FALSE_VALUE, bookTree, 0);

        ScrRegStateByLanguage scrRegStateByLanguage = null;
		Iterator it = null;
		for (it = inList.iterator(); it.hasNext();) {
			// scrregstate = (ScrRegstate) it.next();
			scrRegStateByLanguage = (ScrRegStateByLanguage) it.next();

			addNode(XML_ARCH_VALUE,
					scrRegStateByLanguage.getIdocarchhdrId().toString(),
					scrRegStateByLanguage.getIdocarchhdrName(),
					String.valueOf(scrRegStateByLanguage.getScrregstateState()),
					inNode, scrRegStateByLanguage.getType());
		}

		Element outNode = addNode(XML_DIR_VALUE, XML_OUTBOOK_ID_VALUE, RBUtil
				.getInstance(locale).getProperty(
						I18N_BOOKUSECASE_NODE_OUTBOOK_NAME), XML_FALSE_VALUE,
				bookTree, 0);

		for (it = outList.iterator(); it.hasNext();) {
			// scrregstate = (ScrRegstate) it.next();
			scrRegStateByLanguage = (ScrRegStateByLanguage) it.next();

			addNode(XML_ARCH_VALUE,
					scrRegStateByLanguage.getIdocarchhdrId().toString(),
					scrRegStateByLanguage.getIdocarchhdrName(),
					String.valueOf(scrRegStateByLanguage.getScrregstateState()),
					outNode, scrRegStateByLanguage.getType());
		}

        return document;
    }

    /***************************************************************************
	 * Protected methods
	 **************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    private static void addSessionInfo(SessionInformation sessionInformation, Element parent) {

        parent.addElement(XML_USER_TEXT).addText(sessionInformation.getUser());
        parent.addElement(XML_USERNAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getUserName()));
        parent.addElement(XML_OFFICECODE_TEXT).addText(sessionInformation.getOfficeCode());
        parent.addElement(XML_OFFICENAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getOfficeName()));
        parent.addElement(XML_OFFICEENABLED_TEXT).addText(sessionInformation.getOfficeEnabled());
        parent.addElement(XML_OTHEROFFICE_TEXT).addText(sessionInformation.getOtherOffice());
        parent.addElement(XML_SESSIONID_TEXT).addText(sessionInformation.getSessionId());
        parent.addElement(XML_CASESENSITIVE_TEXT).addText(sessionInformation.getCaseSensitive());

    }
    private static Element addNode(String type, String id, String title, String readOnly, Element parent, int typeBook) {
        Element node = parent.addElement(XML_NODE_TEXT);
        node.addElement(XML_TYPE_TEXT).add(DocumentHelper.createCDATA(type));
        node.addElement(XML_ID_TEXT).addText(id);
        node.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(title));
        node.addElement(XML_READONLY_TEXT).addText(readOnly);
        node.addElement(XML_BOOKTYPE_TEXT).addText(Integer.toString(typeBook));

        return node;
    }

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

