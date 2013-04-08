package ieci.tdw.ispac.ispaclib.notices;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.impl.InvesflowAPI;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.XMLUtil.XMLDocUtil;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.utils.DBUtil;
import ieci.tdw.ispac.ispaclib.utils.XPathUtil;
import ieci.tdw.ispac.ispaclib.utils.XmlTag;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class NoticesTest extends TestCase {

	
	private static final Logger logger = Logger.getLogger("TEST");
	
	private final String ID_RESP = "1-1";
	private final String NUMEXP = "EXP/000000";
	
	private int processId = 1000;
	private int stageId = 100;
	private int taskId = 10;

	protected ClientContext getClientContext() throws ISPACException {
		ClientContext ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		ctx.setLocale(Locale.getDefault());
		ctx.setUser(new MyUser("1-4", "tramitador"));
		return ctx;
	}
	
	public void testGenerateNoticeTaskDelegate(){
		int noticeId = generateNoticeTaskDelegate();
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(noticeId >= 0);
	}

	public void testGenerateNoticeStageDelegate(){
		int noticeId = generateNoticeStageDelegate();
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(noticeId >= 0);
	}

	public void testGenerateNoticeActivityDelegate(){
		int noticeId = generateNoticeActivityDelegate();
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(noticeId >= 0);
	}
	
	public void testArchiveNoticeCloseTask(){
		int noticeId = generateNoticeTaskDelegate();
		archiveObjectNotice(1, 1);
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(true);
	}
	
	public void testArchiveNoticeCloseStage(){
		int noticeId = generateNoticeStageDelegate();
		archiveObjectNotice(1, 0);
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(true);
	}

	public void testArchiveNoticeCloseActivity(){
		int noticeId = generateNoticeActivityDelegate();
		archiveObjectNotice(1, 0);
		cleanGenerateNotice(noticeId);
		Assert.assertTrue(true);
	}	
	
	
	public void testGetNotices(){
		try {
			Notices notices = new Notices(getClientContext());
			IItemCollection itemcol = notices.getNotices();
			Assert.assertTrue(itemcol != null);
		} catch (ISPACException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	public void testCountNotices(){
		try {
			Notices notices = new Notices(getClientContext());
			int count = notices.countNotices();
			Assert.assertTrue(true);
		} catch (ISPACException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	

	public int generateNoticeTaskDelegate(){
		return generateObjectNotice("notice.delegateTask",  Notices.TIPO_AVISO_TRAMITE_DELEGADO);
	}
	
	private int generateNoticeStageDelegate(){
		taskId = 0;
		return generateObjectNotice("notice.delegateStage",  Notices.TIPO_AVISO_FASE_DELEGADA);
	}
	
	public int generateNoticeActivityDelegate(){
		return generateObjectNotice("notice.delegateActivity", Notices.TIPO_AVISO_ACTIVIDAD_DELEGADA);
	}	
	
	private void cleanGenerateNotice(int noticeId){
		try {
			getClientContext().getAPI().getEntitiesAPI().deleteEntities("SPAC_AVISOS_ELECTRONICOS", "WHERE ID_AVISO = "+noticeId);
		} catch (ISPACException e) {
			Assert.fail(e.getMessage());
		}
	}

	private int generateObjectNotice(String message, int tipoAviso){
		try{
			Notices notices = new Notices(getClientContext());
			int noticeId = notices.generateDelegateObjectNotice(processId, stageId, taskId, NUMEXP, message, ID_RESP, tipoAviso);
			return noticeId;
		} catch (ISPACException e) {
			Assert.fail(e.getMessage());
		}
		return 0;
	}
	
	private void archiveObjectNotice(int stageId, int taskId){
		try{
			Notices notices = new Notices(getClientContext());
			if (taskId != 0){
				notices.archiveDelegateTaskNotice(stageId, taskId);
				return;
			}
			notices.archiveDelegateStageNotice(stageId);
		} catch (ISPACException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	private class MyUser extends Responsible {

		private static final long serialVersionUID = 6783456733124512200L;

		public MyUser(String id, String name) throws ISPACException {
			super(id, name);
		}

		public Responsible getOrgUnit() throws ISPACException {
			return null;
		}

		public Collection getOrgUnits() {
			return new ArrayList();
		}

		public Collection getUsers() throws ISPACException {
			return new ArrayList();
		}

		public Collection getUserGroups() throws ISPACException {
			return new ArrayList();
		}

		public List getRespList() throws ISPACException {
			return new ArrayList();
		}

//		public String getRespString() throws ISPACException {
//			return "";
//		}

		public String getRespString() throws ISPACException {
//			String resplist = "'" + DBUtil.replaceQuotes(getUID()) + "','"
//					+ DBUtil.replaceQuotes(getOrgUnit().getUID()) + "'";
//			Iterator it = getUserGroups().iterator();
//			while (it.hasNext()) {
//				Responsible resp = (Responsible) it.next();
//				resplist += ",'" + DBUtil.replaceQuotes(resp.getUID()) + "'";
//			}

			return "'" + DBUtil.replaceQuotes(getUID()) + "'";
		}		
		
		public boolean isInResponsibleList(String sUID) throws ISPACException {
			Responsible responsible;
			return false;
		}

		public String toXmlString() {
			String userXml = getXmlValues();
			return XmlTag.newTag("user", userXml);
		}

		public void loadObject(String xml) throws ISPACException {
			Document xmldoc = XMLDocUtil.newDocument(xml);

			set(PROPERTY_UID,
					XPathUtil.get(xmldoc, "/user/value[@id =1]/text()"));
			set(PROPERTY_NAME,
					XPathUtil.get(xmldoc, "/user/value[@id =2]/text()"));
		}

		public boolean isUser() {
			return true;
		}

		public boolean isGroup() {
			return false;
		}

		public boolean isOrgUnit() {
			return false;
		}

		public boolean isDomain() {
			return false;
		}

	}
	
	
	
}