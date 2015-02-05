/**
 *
 */
package com.ieci.tecdoc.isicres.session.folder;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.ieci.tecdoc.common.AuthenticationUser;
import com.ieci.tecdoc.common.conf.BookTypeConf;
import com.ieci.tecdoc.common.entity.AxSfEntity;
import com.ieci.tecdoc.common.invesdoc.Idocarchdet;
import com.ieci.tecdoc.common.invesicres.ScrBookasoc;
import com.ieci.tecdoc.common.invesicres.ScrOfic;
import com.ieci.tecdoc.common.invesicres.ScrRegstate;
import com.ieci.tecdoc.common.invesicres.ScrTmzofic;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;

/**
 * @author 66575267
 *
 */
public class FolderDataSession {

	private boolean create = true;
	private boolean importFolder = false;
	private int newRegisterID = -1;
	private int newAssociatedRegisterID = 0;
	private Integer newAssociatedBookID = null;

	private Date currentDate = null;
	private Locale locale = null;
	private List documentsScrCa = new ArrayList();
	private List inter = new ArrayList();

	private AuthenticationUser user = null;
	private Idocarchdet idoc = null;
	private ScrBookasoc associatedBook = null;
	private ScrOfic scrofic = null;
	private ScrRegstate scrregstate = null;
	private AxSf axsfFormat = null;
	private AxSf axsfNew = null;
	private BookTypeConf bookTypeConf = null;

	private boolean launchDistribution = false;
	private boolean automaticRegisterCreation = false;
	private boolean updateAudit = false;
	private boolean completedState = false;
	private boolean distributeRegInAccepted = false;

	private int launchDistributionType = -1;

	private ScrTmzofic scrTmzofic;

	/** ************************* */
	/** Atributos para actualizar */
	/** ************************* */
	private AxSf axsfOld = null;
	private Idocarchdet idocMisc = null;
	private AxSfEntity axSfEntity = null;

	private int automaticRegisterCreactionType = -1;
	private List scrRegAsocToDelete = null;
	private Map changedFields = new HashMap();
	private Map documentsUpdate = null;

	public FolderDataSession(boolean create, boolean importFolder,
			AxSf axsfNew, List inter, BookTypeConf bookTypeConf,
			boolean distributeRegInAccepted) {
		super();
		this.create = create;
		this.importFolder = importFolder;
		this.distributeRegInAccepted = distributeRegInAccepted;
		this.axsfNew = axsfNew;
		this.inter = inter;
		this.bookTypeConf = bookTypeConf;
		if (create) {
			this.updateAudit = axsfNew.getAttributeValue("fld2") != null;
		} else {
			this.axSfEntity = new AxSfEntity();
			this.documentsUpdate = new HashMap();
		}
		this.locale = axsfNew.getLocale();
	}

	public FolderDataSession(boolean create, boolean importFolder,
			AxSf axsfNew, List inter, Map documents,
			boolean distributeRegInAccepted) {
		super();
		this.create = create;
		this.importFolder = importFolder;
		this.distributeRegInAccepted = distributeRegInAccepted;
		this.axsfNew = axsfNew;
		this.inter = inter;
		if (create) {
			this.updateAudit = axsfNew.getAttributeValue("fld2") != null;
		} else {
			this.axSfEntity = new AxSfEntity();
			this.documentsUpdate = new HashMap();
			if (documents != null && !documents.isEmpty()) {
				documentsUpdate.putAll(documents);
			}
		}
		this.locale = axsfNew.getLocale();
	}

	/** ********************* */
	/** Metodos sobre AxSf ** */
	/** ********************* */
	public AxSf getAxsfNew() {
		return axsfNew;
	}

	public void setAxsfNew(AxSf axsfNew) {
		this.axsfNew = axsfNew;
	}

	public AxSf getAxsfOld() {
		return axsfOld;
	}

	public void setAxsfOld(AxSf axsfOld) {
		this.axsfOld = axsfOld;
	}

	public String getNewAttributeValueAsString(String attribute) {
		return axsfNew.getAttributeValueAsString(attribute);
	}

	public String getOldAttributeValueAsString(String attribute) {
		return axsfOld.getAttributeValueAsString(attribute);
	}

	public Integer getNewAttributeValueAsInteger(String attribute) {
		return (Integer) axsfNew.getAttributeValue(attribute);
	}

	public Integer getOldAttributeValueAsInteger(String attribute) {
		return (Integer) axsfOld.getAttributeValue(attribute);
	}

	public Object getNewAttributeValue(String name) {
		return axsfNew.getAttributeValue(name);
	}

	public Object getOldAttributeValue(String name) {
		return axsfOld.getAttributeValue(name);
	}

	public void setNewAttributeValue(String field, Object value) {
		axsfNew.setAttributeValue(field, value);
	}

	public void setOldAttributeValue(String field, Object value) {
		axsfOld.setAttributeValue(field, value);
	}

	public List getNewAttributesNames() {
		return axsfNew.getAttributesNames();
	}

	public List getOldAttributesNames() {
		return axsfOld.getAttributesNames();
	}

	public void resetAxSfOld() {
		axsfOld.setFld5(null);
		axsfOld.setFld7(null);
		axsfOld.setFld8(null);
		if (axsfOld instanceof AxSfIn) {
			((AxSfIn) axsfOld).setFld13(null);
			((AxSfIn) axsfOld).setFld16(null);
		} else {
			((AxSfOut) axsfOld).setFld12(null);
		}

	}

	/** ************************* */
	/** Fin Metodos sobre AxSf ** */
	/** ************************* */

	/** *********************************** */
	/** Metodos sobre AuthenticationUser ** */
	/** *********************************** */
	public AuthenticationUser getUser() {
		return user;
	}

	public void setUser(AuthenticationUser user) {
		this.user = user;
	}

	public Integer getUserId() {
		return user.getId();
	}

	public String getUserName() {
		return user.getName();
	}

	/** *************************************** */
	/** Fin Metodos sobre AuthenticationUser ** */
	/** *************************************** */

	/** ****************************** */
	/** Metodos sobre ChangedFields ** */
	/** ****************************** */
	public Map getChangedFields() {
		return changedFields;
	}

	public void setChangedFields(Map changedFields) {
		this.changedFields = changedFields;
	}

	public void changedPut(Object key, Object value) {
		this.changedFields.put(key, value);
	}

	public boolean changedContainsKey(Object key) {
		return this.changedFields.containsKey(key);
	}

	public Object changedGet(Object key) {
		return this.changedFields.get(key);
	}

	/** ********************************** */
	/** Fin Metodos sobre ChangedFields ** */
	/** ********************************** */

	public Integer getDeptId() {
		return new Integer(scrofic.getDeptid());
	}

	/**
	 * @return the create
	 */
	public boolean isCreate() {
		return create;
	}

	/**
	 * @param create
	 *            the create to set
	 */
	public void setCreate(boolean create) {
		this.create = create;
	}

	/**
	 *
	 * @return the idoc
	 */
	public Idocarchdet getIdoc() {
		return idoc;
	}

	/**
	 * @param idoc
	 *            the idoc to set
	 */
	public void setIdoc(Idocarchdet idoc) {
		this.idoc = idoc;
	}

	/**
	 * @return the currentDate
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * @param currentDate
	 *            the currentDate to set
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * @return the launchDistribution
	 */
	public boolean isLaunchDistribution() {
		return launchDistribution;
	}

	/**
	 * @param launchDistribution
	 *            the launchDistribution to set
	 */
	public void setLaunchDistribution(boolean launchDistribution) {
		this.launchDistribution = launchDistribution;
	}

	/**
	 * @return the launchDistributionType
	 */
	public int getLaunchDistributionType() {
		return launchDistributionType;
	}

	/**
	 * @param launchDistributionType
	 *            the launchDistributionType to set
	 */
	public void setLaunchDistributionType(int launchDistributionType) {
		this.launchDistributionType = launchDistributionType;
	}

	/**
	 * @return the automaticRegisterCreation
	 */
	public boolean isAutomaticRegisterCreation() {
		return automaticRegisterCreation;
	}

	/**
	 * @param automaticRegisterCreation
	 *            the automaticRegisterCreation to set
	 */
	public void setAutomaticRegisterCreation(boolean automaticRegisterCreation) {
		this.automaticRegisterCreation = automaticRegisterCreation;
	}

	/**
	 * @return the associatedBook
	 */
	public ScrBookasoc getAssociatedBook() {
		return associatedBook;
	}

	/**
	 * @param associatedBook
	 *            the associatedBook to set
	 */
	public void setAssociatedBook(ScrBookasoc associatedBook) {
		this.associatedBook = associatedBook;
	}

	/**
	 * @return the documentsScrCa
	 */
	public List getDocumentsScrCa() {
		return documentsScrCa;
	}

	/**
	 * @param documentsScrCa
	 *            the documentsScrCa to set
	 */
	public void setDocumentsScrCa(List documentsScrCa) {
		this.documentsScrCa = documentsScrCa;
	}

	/**
	 * @return the scrofic
	 */
	public ScrOfic getScrofic() {
		return scrofic;
	}

	/**
	 * @param scrofic
	 *            the scrofic to set
	 */
	public void setScrofic(ScrOfic scrofic) {
		this.scrofic = scrofic;
	}

	/**
	 * @return the scrregstate
	 */
	public ScrRegstate getScrregstate() {
		return scrregstate;
	}

	/**
	 * @param scrregstate
	 *            the scrregstate to set
	 */
	public void setScrregstate(ScrRegstate scrregstate) {
		this.scrregstate = scrregstate;
	}

	/**
	 * @return the updateAudit
	 */
	public boolean isUpdateAudit() {
		return updateAudit;
	}

	/**
	 * @param updateAudit
	 *            the updateAudit to set
	 */
	public void setUpdateAudit(boolean updateAudit) {
		this.updateAudit = updateAudit;
	}

	/**
	 * @return the axsfFormat
	 */
	public AxSf getAxsfFormat() {
		return axsfFormat;
	}

	/**
	 * @param axsfFormat
	 *            the axsfFormat to set
	 */
	public void setAxsfFormat(AxSf axsfFormat) {
		this.axsfFormat = axsfFormat;
	}

	/**
	 * @return the bookTypeConf
	 */
	public BookTypeConf getBookTypeConf() {
		return bookTypeConf;
	}

	/**
	 * @param bookTypeConf
	 *            the bookTypeConf to set
	 */
	public void setBookTypeConf(BookTypeConf bookTypeConf) {
		this.bookTypeConf = bookTypeConf;
	}

	/**
	 * @return the completedState
	 */
	public boolean isCompletedState() {
		return completedState;
	}

	/**
	 * @param completedState
	 *            the completedState to set
	 */
	public void setCompletedState(boolean completedState) {
		this.completedState = completedState;
	}

	/**
	 * @return the idocMisc
	 */
	public Idocarchdet getIdocMisc() {
		return idocMisc;
	}

	/**
	 * @param idocMisc
	 *            the idocMisc to set
	 */
	public void setIdocMisc(Idocarchdet idocMisc) {
		this.idocMisc = idocMisc;
	}

	/**
	 * @return the automaticRegisterCreactionType
	 */
	public int getAutomaticRegisterCreactionType() {
		return automaticRegisterCreactionType;
	}

	/**
	 * @param automaticRegisterCreactionType
	 *            the automaticRegisterCreactionType to set
	 */
	public void setAutomaticRegisterCreactionType(
			int automaticRegisterCreactionType) {
		this.automaticRegisterCreactionType = automaticRegisterCreactionType;
	}

	/**
	 * @return the scrRegAsocToDelete
	 */
	public List getScrRegAsocToDelete() {
		return scrRegAsocToDelete;
	}

	/**
	 * @param scrRegAsocToDelete
	 *            the scrRegAsocToDelete to set
	 */
	public void setScrRegAsocToDelete(List scrRegAsocToDelete) {
		this.scrRegAsocToDelete = scrRegAsocToDelete;
	}

	/**
	 * @return the axSfEntity
	 */
	public AxSfEntity getAxSfEntity() {
		return axSfEntity;
	}

	/**
	 * @param axSfEntity
	 *            the axSfEntity to set
	 */
	public void setAxSfEntity(AxSfEntity axSfEntity) {
		this.axSfEntity = axSfEntity;
	}

	/**
	 * @return the documentsUpdate
	 */
	public Map getDocumentsUpdate() {
		return documentsUpdate;
	}

	/**
	 * @param documentsUpdate
	 *            the documentsUpdate to set
	 */
	public void setDocumentsUpdate(Map documentsUpdate) {
		this.documentsUpdate = documentsUpdate;
	}

	/**
	 * @return the newRegisterID
	 */
	public int getNewRegisterID() {
		return newRegisterID;
	}

	/**
	 * @param newRegisterID
	 *            the newRegisterID to set
	 */
	public void setNewRegisterID(int newRegisterID) {
		this.newRegisterID = newRegisterID;
	}

	/**
	 * @return the newAssociatedRegisterID
	 */
	public int getNewAssociatedRegisterID() {
		return newAssociatedRegisterID;
	}

	/**
	 * @param newAssociatedRegisterID
	 *            the newAssociatedRegisterID to set
	 */
	public void setNewAssociatedRegisterID(int newAssociatedRegisterID) {
		this.newAssociatedRegisterID = newAssociatedRegisterID;
	}

	/**
	 * @return the newAssociatedBookID
	 */
	public Integer getNewAssociatedBookID() {
		return newAssociatedBookID;
	}

	/**
	 * @param newAssociatedBookID
	 *            the newAssociatedBookID to set
	 */
	public void setNewAssociatedBookID(Integer newAssociatedBookID) {
		this.newAssociatedBookID = newAssociatedBookID;
	}

	/**
	 * @return the inter
	 */
	public List getInter() {
		return inter;
	}

	/**
	 * @param inter
	 *            the inter to set
	 */
	public void setInter(List inter) {
		this.inter = inter;
	}

	/**
	 * @return the importFolder
	 */
	public boolean isImportFolder() {
		return importFolder;
	}

	/**
	 * @param importFolder
	 *            the importFolder to set
	 */
	public void setImportFolder(boolean importFolder) {
		this.importFolder = importFolder;
	}

	/**
	 * @return the distributeRegInAccepted
	 */
	public boolean isDistributeRegInAccepted() {
		return distributeRegInAccepted;
	}

	/**
	 * @param distributeRegInAccepted
	 *            the distributeRegInAccepted to set
	 */
	public void setDistributeRegInAccepted(boolean distributeRegInAccepted) {
		this.distributeRegInAccepted = distributeRegInAccepted;
	}

	/**
	 * @return el scrTmzofic
	 */
	public ScrTmzofic getScrTmzofic() {
		return scrTmzofic;
	}

	/**
	 * @param scrTmzofic
	 *            el scrTmzofic a fijar
	 */
	public void setScrTmzofic(ScrTmzofic scrTmzofic) {
		this.scrTmzofic = scrTmzofic;
	}

}
