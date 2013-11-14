package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ieci.tecdoc.common.conf.FieldConf;
import com.ieci.tecdoc.common.conf.UserConf;
import com.ieci.tecdoc.common.exception.SecurityException;
import com.ieci.tecdoc.common.exception.SessionException;
import com.ieci.tecdoc.common.exception.TecDocException;
import com.ieci.tecdoc.common.exception.ValidationException;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.isicres.AxDoch;
import com.ieci.tecdoc.common.isicres.AxPageh;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.SessionInformation;
import com.ieci.tecdoc.common.keys.ISicresKeys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.ISicresGenPerms;
import com.ieci.tecdoc.idoc.decoder.form.FPageDef;
import com.ieci.tecdoc.idoc.decoder.form.FormFormat;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.MiscFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;
import com.ieci.tecdoc.isicres.usecase.UseCaseConf;
import com.ieci.tecdoc.isicres.usecase.security.SecurityUseCase;
import com.ieci.tecdoc.utils.cache.CacheBag;
import com.ieci.tecdoc.utils.cache.CacheFactory;

/**
 * @author LMVICENTE
 * @creationDate 10-may-2004 10:56:35
 * @version @since
 */
public class XMLBookTree implements Keys {

    /***************************************************************************
     * Attributes
     **************************************************************************/

	private static Logger _logger = Logger.getLogger(XMLBookTree.class);

	private static final String FOLDER_NAME = "{0}";

    private static final String FLD_LOOKFOR = "@FLD(";

    private static final String LLAVE_IZQ = "{";

    private static final String LLAVE_DER = "}";

    private static final String PAR_IZQ = ")";

    protected static final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    private static ISicresGenPerms permisos = null;
    /***************************************************************************
     * Constructors
     **************************************************************************/

    /***************************************************************************
     * Public methods
     **************************************************************************/

    public static Document createXMLBookTree(UseCaseConf useCaseConf, Integer bookID, ScrRegstate scrregstate,
    		AxSf axsf, Idocarchdet idocarchdet, boolean readOnly, long folderPId, int folderId, int row, List docs,
            String url, int bookType, boolean isBookAdmin, Locale locale, int vldSave,
            SessionInformation sessionInformation, UserConf usrConf, String archiveName)
    	throws ValidationException, SecurityException {

    	FormFormat formFormat = new FormFormat(axsf.getFormat().getData());
        String folderName = getFolderName(scrregstate, idocarchdet, folderId, axsf, archiveName);
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement(XML_FRMTREE_TEXT);
        Element sessionInfo = root.addElement(XML_SESSION_TEXT);
        String readOnlyS = XML_TRUE_VALUE;

        if (!readOnly) {
            readOnlyS = XML_FALSE_VALUE;
        }

		// obtenemos los permisos del usuario almacenados en la cache y
		// verificamos si el libro esta abierto para obtenerlos
	getPermisosUsuarioCache(useCaseConf, scrregstate);

        addSessionInfo(sessionInformation, sessionInfo);
        addParams(folderName, url, folderPId, readOnlyS, folderId, vldSave, row, bookType, isBookAdmin, root);
        addProperties(useCaseConf, bookType, scrregstate.getImageAuth(), axsf, root);
        addUserConfig(root, usrConf, locale);

        if (formFormat.getDlgDef().getPagedefs() != null && !formFormat.getDlgDef().getPagedefs().isEmpty()) {
            FPageDef page = null;
            Integer key = null;

            for (Iterator it = formFormat.getDlgDef().getPagedefs().keySet().iterator(); it.hasNext();) {
                key = (Integer) it.next();
                page = (FPageDef) formFormat.getDlgDef().getPagedefs().get(key);

                if (!page.getCtrldefs().isEmpty()) {
                    String title = "";

                    try {
                        title = axsf.getLocaleAttributePage(locale, page.getTitle());
                    } catch (Exception e) {
                    }

                    addNodeDat(key.intValue(), title, root);
                }
            }
        }

        addNodeDocs(docs, root);

        return document;
    }

    /**
     * Función que obtiene los permisos del usuario almacenados en la cache
     * @param useCaseConf
     */
	private static void getPermisosUsuarioCache(UseCaseConf useCaseConf, ScrRegstate scrregstate) {
		CacheBag cacheBag = null;
		try {
			if(scrregstate.getState() == ISicresKeys.BOOK_STATE_OPEN){
				// Obtenemos la cache
				cacheBag = CacheFactory.getCacheInterface().getCacheEntry(
						useCaseConf.getSessionID());

			//Obtenemos de la cache los permisos del usuario
			permisos = (ISicresGenPerms) cacheBag.get(ServerKeys.GENPERMS_USER);
			}else{
				permisos = null;
			}
		} catch (SessionException sE) {
			_logger.warn("No se ha podido obtener la información de los permisos del usuario", sE);
			permisos = null;
		} catch (TecDocException tE) {
			_logger.warn("No se ha podido obtener la información de los permisos del usuario", tE);
			permisos = null;
		}
	}

    public static Document createEmptyXMLBookTree(Integer bookID, ScrRegstate scrregstate, AxSf axsf,
            Idocarchdet idocarchdet, boolean readOnly, long folderPId, int folderId, int row, List docs,
            String url, int bookType, Locale locale, String archiveName) {
        FormFormat formFormat = new FormFormat(axsf.getFormat().getData());

        String folderName = getFolderName(scrregstate, idocarchdet, folderId, axsf, archiveName);

        Document document = DocumentHelper.createDocument();

        //document.addProcessingInstruction(XSL_PROINS_OPENFOLDER_1,
        // XSL_PROINS_OPENFOLDER_2);

        Element root = document.addElement(XML_FRMTREE_TEXT);

        String readOnlyS = XML_TRUE_VALUE;
        if (!readOnly) {
            readOnlyS = XML_FALSE_VALUE;
        }

        addParams(folderName, url, folderPId, readOnlyS, folderId, 1, row, bookType, true, root);

        if (formFormat.getDlgDef().getPagedefs() != null && !formFormat.getDlgDef().getPagedefs().isEmpty()) {
            FPageDef page = null;
            Integer key = null;
            for (Iterator it = formFormat.getDlgDef().getPagedefs().keySet().iterator(); it.hasNext();) {
                key = (Integer) it.next();
                page = (FPageDef) formFormat.getDlgDef().getPagedefs().get(key);
                if (!page.getCtrldefs().isEmpty()) {
                    String title = "";
                    try {
                        title = axsf.getLocaleAttributePage(locale, page.getTitle());
                    } catch (Exception e) {
                    }
                    addNodeDat(key.intValue(), title, root);
                }
            }
        }

        addNodeDocs(docs, root);

        return document;
    }

    public static Document createXMLUserConfig(UseCaseConf useCaseConf,
			Integer bookID, UserConf usrConf, Locale locale) throws Exception {
		Document document = null;

		if (usrConf != null) {
			document = DocumentHelper.createDocument();
			Element root = document.addElement(XML_USERCONFIG_TEXT);
			List checkFields = usrConf.getFieldConf();
			Element fields = root.addElement(XML_FIELDS_TEXT);
			Element field = null;
			FieldConf fieldConf = null;
			if (checkFields != null && !checkFields.isEmpty()) {
				for (Iterator it = checkFields.iterator(); it.hasNext();) {
					fieldConf = (FieldConf) it.next();
					field = fields.addElement(XML_FIELD_TEXT);
					field.addAttribute(XML_ID_TEXT, new Integer(fieldConf
							.getFieldId()).toString());
					field.addAttribute(XML_CHECKED_TEXT, new Integer(fieldConf
							.getFieldCheck()).toString());
					field.addAttribute(XML_IND_TEXT, "0");
					field.addElement(XML_LABEL_TEXT).add(
							DocumentHelper.createCDATA(fieldConf
									.getFieldLabel()));

				}
			}
			Element parameters = root.addElement(XML_PARAMETERS_TEXT);
			addParameters(parameters, usrConf, locale, 1);
		}

		return document;
	}

    /***************************************************************************
	 * Protected methods
	 **************************************************************************/

    /***************************************************************************
     * Private methods
     **************************************************************************/

    private static String getFolderName(ScrRegstate scrregstate, Idocarchdet idocarchdet, int folderId,
            AxSf axsf, String archiveName) {
        String folderName = "";
        if (idocarchdet == null) {
            if (folderId == -1) {
				// folderName = scrregstate.getIdocarchhdr().getName();
				folderName = archiveName;
			} else {
				// folderName = MessageFormat.format(FOLDER_NAME, new String[] {
				// scrregstate.getIdocarchhdr().getName()});
				folderName = MessageFormat.format(FOLDER_NAME,
						new String[] { archiveName });
			}
        } else {
            MiscFormat misc = new MiscFormat(idocarchdet.getDetval());
            String f = misc.getFdrname();
            String x = null;
            String sub = null;
            List campos = new ArrayList();
            StringBuffer transformed = new StringBuffer();

            if (StringUtils.countMatches(f, FLD_LOOKFOR) > 0) {
                int transformedIndex = 0;
                int index = 0;
                do {
                    index = f.indexOf(FLD_LOOKFOR);
                    if (index != -1) {
                        transformed.append(f.substring(0, index));
                        transformed.append(LLAVE_IZQ + Integer.toString(transformedIndex++) + LLAVE_DER);
                        sub = f.substring(index + FLD_LOOKFOR.length(), f.length());
                        x = sub.substring(0, sub.indexOf(PAR_IZQ));
                        campos.add(new Integer(x));
                        f = f.substring(index + FLD_LOOKFOR.length() + x.length() + 1, f.length());
                    }
                } while (index != -1);
            } else {
                transformed.append(f);
            }

            if (!campos.isEmpty()) {
                List values = new ArrayList(campos.size());
                for (Iterator it = campos.iterator(); it.hasNext();) {
                    values.add(axsf.getAttributeValueAsString(XML_FLD_TEXT + it.next().toString()));
                }
                folderName = MessageFormat.format(transformed.toString(), values.toArray());
            } else {
            	if (transformed.length() > 0) {
                    folderName = transformed.toString();
                } else {
                    if (folderId == -1) {
						// folderName = scrregstate.getIdocarchhdr().getName();
						folderName = archiveName;
					} else {
						// folderName = MessageFormat.format(FOLDER_NAME, new
						// String[] {
						// scrregstate.getIdocarchhdr().getName()});
						folderName = MessageFormat.format(FOLDER_NAME,
								new String[] { archiveName });
					}
				}
            }
        }
        return folderName;
    }

    private static void addSessionInfo(SessionInformation sessionInformation, Element parent) {

        parent.addElement(XML_USER_TEXT).addText(sessionInformation.getUser());
        parent.addElement(XML_USERNAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getUserName()));
        parent.addElement(XML_OFFICECODE_TEXT).addText(sessionInformation.getOfficeCode());
        parent.addElement(XML_OFFICENAME_TEXT).add(DocumentHelper.createCDATA(sessionInformation.getOfficeName()));
        parent.addElement(XML_OFFICEENABLED_TEXT).addText(sessionInformation.getOfficeEnabled());
    }

    private static void addParams(String folderName, String fileForm, long folderPid, String fdrReadOnly,
            int folderId, int vldSave, int row, int bookType, boolean isBookAdmin, Element parent) {
        Element node = parent.addElement(XML_PARAMS_TEXT);

        node.addElement(XML_FOLDERNAME_TEXT).add(DocumentHelper.createCDATA(folderName));
        if (fileForm != null) {
            node.addElement(XML_FILEFORM_TEXT).addText(fileForm);
        } else {
            node.addElement(XML_FILEFORM_TEXT).addText("");
        }
        node.addElement(XML_FOLDERPID_TEXT).addText(Long.toString(folderPid));
        node.addElement(XML_FDRREADONLY_TEXT).addText(fdrReadOnly);
        node.addElement(XML_FOLDERID_TEXT).addText(Integer.toString(folderId));
        node.addElement(XML_VLDSAVE_TEXT).addText(Integer.toString(vldSave));
        node.addElement(XML_ROW_TEXT).addText(Integer.toString(row));
        node.addElement(XML_BOOKTYPE_TEXT).addText(Integer.toString(bookType));
        if (isBookAdmin) {
            node.addElement(XML_ISBOOKADMIN_TEXT).addText(Integer.toString(1));
        } else {
            node.addElement(XML_ISBOOKADMIN_TEXT).addText(Integer.toString(0));
        }
        //Indica si se puede realizar distribuciones manuales
        if(permisos!=null){
            node.addElement(XML_CAN_DIST_TEXT).addText(Integer.toString((permisos.isCanDistRegisters()? 1 : 0)));
        }else{
		//no se puede distribuir
		node.addElement(XML_CAN_DIST_TEXT).addText(Integer.toString(0));
        }
    }

    private static void addProperties(UseCaseConf useCaseConf, int bookType, int authentication, AxSf axsf, Element parent)
    	throws ValidationException, SecurityException {

    	Element node = parent.addElement(XML_PROPERTIES_TEXT);
    	SecurityUseCase securityUseCase = new SecurityUseCase();
        Date regDate = (Date) axsf.getAttributeValue("fld2");
        String regDateString = "";
        String regNumberString = "";
        String strUnitCode = "";
        String strStampOficCode = "";
        String strStampOficDesc = "";
        ScrOfic ofic = securityUseCase.getCurrentUserOfic(useCaseConf);

        if (regDate != null){
        	regDateString = FORMATTER.format(regDate);
        }

        if (axsf.getAttributeValue("fld1") != null){
        	regNumberString = (String) axsf.getAttributeValue("fld1");
        }

        if (axsf.getAttributeValue("fld5") != null){
        	strStampOficCode = axsf.getFld5().getCode();

        	if (!StringUtils.isBlank(axsf.getFld5().getStamp())) {
				strStampOficDesc = axsf.getFld5().getStamp();
			} else {
				strStampOficDesc = axsf.getFld5Name();
			}

        	strStampOficDesc = strStampOficDesc.replaceAll("\r\n","\r");
        }

        if (axsf instanceof AxSfIn) {
		    if (axsf.getFld8() != null){
				strUnitCode = axsf.getFld8().getCode();
			} else {
			 	strUnitCode = "";
			}
		} else {
		    if (axsf.getFld7() != null){
				strUnitCode = axsf.getFld7().getCode();
			} else {
			 	strUnitCode = "";
			}
		}

        node.addElement(XML_BOOKTYPE_TEXT).addText(Integer.toString(bookType));
        node.addElement(XML_AUTHENTICATION_TEXT).addText(Integer.toString(authentication));
        node.addElement(XML_REGNUMBER_TEXT).addText(regNumberString);
        node.addElement(XML_REGDATE_TEXT).addText(regDateString);
        node.addElement(XML_STAMPOFFICECODE_TEXT).addText(strStampOficCode);
        node.addElement(XML_STAMPOFFICEDESC_TEXT).addText(strStampOficDesc);
        node.addElement(XML_UNITCODE_TEXT).addText(strUnitCode);
    }

    private static void addNodeDat(int id, String title, Element parent) {
        Element node = parent.addElement(XML_NODE_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_DAT_TEXT);

        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(title));
    }

    private static void addNodePage(String title, int id, int order, String ext, Element parent) {
        Element node = parent.addElement(XML_NODE_TEXT);
        node.addAttribute(XML_TYPE_TEXT, XML_PAG_TEXT);

        node.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(title));
        node.addElement(XML_ID_TEXT).addText(Integer.toString(id));
        node.addElement(XML_ORDER_TEXT).addText(Integer.toString(order));
        node.addElement(XML_EXT_TEXT).add(DocumentHelper.createCDATA(ext));
    }

    private static void addNodeDocs(List docs, Element parent) {
        Element infDoc = parent.addElement(XML_NODE_TEXT);
        infDoc.addAttribute(XML_TYPE_TEXT, XML_INFDOC_TEXT);

        if (docs != null && !docs.isEmpty()) {
            AxDoch axdoch = null;
            AxPageh axpageh = null;
            for (Iterator it = docs.iterator(); it.hasNext();) {
                axdoch = (AxDoch) it.next();

                Element doc = infDoc.addElement(XML_NODE_TEXT);
                doc.addAttribute(XML_TYPE_TEXT, XML_DOC_TEXT);

                doc.addElement(XML_TITLE_TEXT).add(DocumentHelper.createCDATA(axdoch.getName()));
                doc.addElement(XML_ID_TEXT).addText(Integer.toString(axdoch.getId()));

                for (Iterator it2 = axdoch.getPages().iterator(); it2.hasNext();) {
                    axpageh = (AxPageh) it2.next();
                    addNodePage(axpageh.getName(), axpageh.getId(), axpageh.getSortOrder(), axpageh.getLoc(),
                            doc);
                }
            }
        }
    }

    private static void addUserConfig(Element parent, UserConf usrConf, Locale locale) {
    	Element usrCf = null;
    	if (usrConf != null) {
			usrCf = parent.addElement(XML_USERCONFIG_TEXT);
			List checkFields = usrConf.getFieldConf();
			Element fields = usrCf.addElement(XML_FIELDS_TEXT);
			Element field = null;
			FieldConf fieldConf = null;
			if (checkFields != null && !checkFields.isEmpty()) {
				for (Iterator it = checkFields.iterator(); it
						.hasNext();) {
					fieldConf = (FieldConf) it.next();
					if (fieldConf.getFieldCheck() == 1){
						field = fields.addElement(XML_FIELD_TEXT);
						field.addAttribute(XML_ID_TEXT, new Integer(fieldConf.getFieldId()).toString());
					}
/*					field.addAttribute(XML_CHECKED_TEXT, new Integer(fieldConf.getFieldCheck()).toString());
					field.addAttribute(XML_IND_TEXT, "0");
					field.addElement(XML_LABEL_TEXT).add(DocumentHelper.createCDATA(fieldConf.getFieldLabel()));
*/
				}
			}
			Element parameters = usrCf.addElement(XML_PARAMETERS_TEXT);
			addParameters(parameters, usrConf, locale, 0);
		}
    }

	private static void addParameters(Element parent, UserConf usrConf,
			Locale locale, int type) {
		Element parameter = null;

		for (int i = 1; i < 5; i++) {
			parameter = parent.addElement(XML_PARAMETER_TEXT);
			parameter.addAttribute(XML_ID_TEXT, new Integer(i).toString());
			if (i == 1) {
				parameter.addAttribute(XML_CHECKED_TEXT, new Integer(usrConf
						.getPersonValidation()).toString());
			}
			if (i == 2) {
				parameter.addAttribute(XML_CHECKED_TEXT, new Integer(usrConf
						.getShowScanDlg()).toString());
			}
			if (i == 3) {
				parameter.addAttribute(XML_CHECKED_TEXT, new Integer(usrConf
						.getRememberLastSelectedUnit()).toString());
			}

			if (i != 4 && type == 1){
				parameter.addAttribute(XML_IND_TEXT, "1");
				parameter.addElement(XML_LABEL_TEXT).add(
						DocumentHelper.createCDATA(RBUtil.getInstance(locale)
								.getProperty(Keys.I18N_USERCONFIG_PARAMETER + i)));
			}
		}
	}
    /***************************************************************************
	 * Inner classes
	 **************************************************************************/

    /***************************************************************************
     * Test brench
     **************************************************************************/

}

