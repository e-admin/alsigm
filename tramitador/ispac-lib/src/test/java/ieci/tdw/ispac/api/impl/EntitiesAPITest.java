package ieci.tdw.ispac.api.impl;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.errors.ISPACNullObject;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.context.StateContext;
import ieci.tdw.ispac.ispaclib.dao.cat.CTApplicationDAO;
import ieci.tdw.ispac.ispaclib.dao.cat.CTRuleDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PEntidadDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmFaseDAO;
import ieci.tdw.ispac.ispaclib.dao.procedure.PFrmTramiteDAO;
import ieci.tdw.ispac.ispaclib.db.DbCnt;

import java.util.Locale;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class EntitiesAPITest extends TestCase{

	private static final Logger logger = Logger.getLogger("TEST");

	private final String RULE_VISIBLE_FIELD = "ID_RULE_VISIBLE";
	private final String RULE_FORM_FIELD = "ID_RULE_FRM";

	private final String CLASE_RETURNFORMIDRULE	= 	"ieci.tdw.ispac.api.rule.ReturnFormIdRuleTest";
	private final String CLASE_RETURNFORMNULLRULE	= "ieci.tdw.ispac.api.rule.ReturnFormNullRuleTest";
	private final String CLASE_RETURNFORMAPPLICATIONRULE	= "ieci.tdw.ispac.api.rule.ReturnFormApplicationRuleTest";

	private final String CLASE_RETURNFORMVISIBILITYTRUERULE	= 	"ieci.tdw.ispac.api.rule.ReturnFormVisibilityTrueRuleTest";
	private final String CLASE_RETURNFORMVISIBILITYFALSERULE	= "ieci.tdw.ispac.api.rule.ReturnFormVisibilityFalseRuleTest";
	private final String CLASE_RETURNFORMVISIBILITYNULLRULE	= "ieci.tdw.ispac.api.rule.ReturnFormVisibilityNullRuleTest";


	static InnerEntitiesAPI innerEntitiesAPI;
	static ClientContext ctx;
	static {
		ctx = new ClientContext();
		ctx.setAPI(new InvesflowAPI(ctx));
		ctx.setLocale(Locale.getDefault());
		StateContext stateContext = new StateContext();
		ctx.setStateContext(stateContext);
	}

	protected InnerEntitiesAPI getInnerEntitiesAPI (){
		if (innerEntitiesAPI == null){
			innerEntitiesAPI = new InnerEntitiesAPI(ctx);
		}
		return innerEntitiesAPI;
	}

	// ---------------------------------------------------------------------------
	// Obtención de formularios asociados a FASE mediante ejecución de una regla
	public void testGetStageApplicationById() {
		logger.info("testGetStageApplicationById");
		try {
			CTApplicationDAO obj = getStageApplication(CLASE_RETURNFORMIDRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void testGetStageApplicationByCTApplication() {
		logger.info("testGetStageApplicationByCTApplication");
		try {
			CTApplicationDAO obj = getStageApplication(CLASE_RETURNFORMAPPLICATIONRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	public void testGetStageApplicationNull() {
		logger.info("testGetStageApplicationNull");
		try {
			getStageApplication(CLASE_RETURNFORMNULLRULE);
			Assert.fail("No se debería haber obtenido ningún formulario");
		} catch (ISPACInfo e) {
			logger.info("\t"+e.getMessage());
			Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.empty"));
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	// ---------------------------------------------------------------------------
	// Obtención de formularios asociados a TRAMITE mediante ejecución de una regla
	public void testGetTaskApplicationById() {
		logger.info("testGetTaskApplicationById");
		try {
			CTApplicationDAO obj = getTaskApplication(CLASE_RETURNFORMIDRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testGetTaskApplicationByCTApplication() {
		logger.info("testGetTaskApplicationByCTApplication");
		try {
			CTApplicationDAO obj = getTaskApplication(CLASE_RETURNFORMAPPLICATIONRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testGetTaskApplicationNull() {
		logger.info("testGetTaskApplicationNull");
		try {
			getTaskApplication(CLASE_RETURNFORMNULLRULE);
			Assert.fail("No se debería haber obtenido ningún formulario");
		} catch (ISPACInfo e) {
			logger.info("\t"+e.getMessage());
			Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.empty"));
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	// --------------------------------------------------------------------
	// Obtención de formularios asociados a PROCEDIMIENTO mediante ejecución de una regla
	public void testGetProcedureApplicationById() {
		logger.info("testGetProcedureApplicationById");
		try {
			CTApplicationDAO obj = getProcedureApplication(CLASE_RETURNFORMIDRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testGetProcedureApplicationByCTApplication() {
		logger.info("testGetProcedureApplicationByCTApplication");
		try {
			CTApplicationDAO obj = getProcedureApplication(CLASE_RETURNFORMAPPLICATIONRULE);
			logResult(obj);
			Assert.assertNotNull(obj);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	public void testGetProcedureApplicationNull() {
		logger.info("testGetProcedureApplicationNull");
		try {
			getProcedureApplication(CLASE_RETURNFORMNULLRULE);
			Assert.fail("No se debería haber obtenido ningún formulario");
		} catch (ISPACInfo e) {
			logger.info("\t"+e.getMessage());
			Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.empty"));
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}


	// ===========================================================================
	// CALCULO DE VISIBILIDAD DE UN FORMULARIO MEDIANTE EJECUCIÓN DE UNA REGLA
	// ===========================================================================
	// ---------------------------------------------------------------------------
	// Obtención de visibilidad de un formulario a nivel de Procedimiento
	 public void testGetProcedureEntitiesFormVisibilityTrue(){
			logger.info("testGetProcedureEntitiesFormVisibilityTrue");
		 try{
			 PEntidadDAO item = getProcedureFormVisibility(CLASE_RETURNFORMVISIBILITYTRUERULE);
			 logResult(item);
			 Assert.assertNotNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	 }


	public void testGetProcedureEntitiesFormVisibilityFalse(){
		logger.info("testGetProcedureEntitiesFormVisibilityFalse");
		 try{
			 PEntidadDAO item = getProcedureFormVisibility(CLASE_RETURNFORMVISIBILITYFALSERULE);
			 logResult(item);
			 Assert.assertNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	}

	public void testGetProcedureEntitiesFormVisibilityNull(){
		logger.info("testGetProcedureEntitiesFormVisibilityNull");
		try{
			 getProcedureFormVisibility(CLASE_RETURNFORMVISIBILITYNULLRULE);
		 }catch (ISPACInfo e) {
			 logger.info("\t"+e.getMessage());
			 Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.visible.invalid"));
		 }catch (Exception e) {
			 Assert.fail(e.getMessage());
		 }
	 }

	// ---------------------------------------------------------------------------
	// Obtención de visibilidad de un formulario a nivel de FASE
	public void testGetStageEntitiesFormVisibilityTrue(){
		logger.info("testGetStageEntitiesFormVisibilityTrue");
		 try{
			 PEntidadDAO item = getStageFormVisibility(CLASE_RETURNFORMVISIBILITYTRUERULE);
			 logResult(item);
			 Assert.assertNotNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	 }

	public void testGetStageEntitiesFormVisibilityFalse(){
		logger.info("testGetStageEntitiesFormVisibilityFalse");
		 try{
			 PEntidadDAO item = getStageFormVisibility(CLASE_RETURNFORMVISIBILITYFALSERULE);
			 logResult(item);
			 Assert.assertNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	}

	public void testGetStageEntitiesFormVisibilityNull(){
		logger.info("testGetStageEntitiesFormVisibilityNull");
		try{
			getStageFormVisibility(CLASE_RETURNFORMVISIBILITYNULLRULE);
		}catch (ISPACInfo e) {
			logger.info("\t"+e.getMessage());
			Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.visible.invalid"));
		}catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	 }

	// ---------------------------------------------------------------------------
	// Obtención de visibilidad de un formulario a nivel de TRAMITE
	public void testGetTaskEntitiesFormVisibilityTrue(){
		logger.info("testGetTaskEntitiesFormVisibilityTrue");
		 try{
			 PEntidadDAO item = getTaskFormVisibility(CLASE_RETURNFORMVISIBILITYTRUERULE);
			 logResult(item);
			 Assert.assertNotNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	 }

	public void testGetTaskEntitiesFormVisibilityFalse(){
		logger.info("testGetTaskEntitiesFormVisibilityFalse");
		 try{
			 PEntidadDAO item = getTaskFormVisibility(CLASE_RETURNFORMVISIBILITYFALSERULE);
			 logResult(item);
			 Assert.assertNull(item);
		 }catch(Exception e){
			 Assert.fail(e.getMessage());
		 }
	}

	 public void testGetTaskEntitiesFormVisibilityNull(){
		logger.info("testGetTaskEntitiesFormVisibilityNull");
		 try{
			 getTaskFormVisibility(CLASE_RETURNFORMVISIBILITYNULLRULE);
		 }catch (ISPACInfo e) {
			 logger.info("\t"+e.getMessage());
			 Assert.assertTrue(StringUtils.equals(e.getMessage(), "exception.entities.form.rule.visible.invalid"));
		 }catch (Exception e) {
			 Assert.fail(e.getMessage());
		 }
	 }




	 // ===========================================================================
	 // ===========================================================================


	private void logResult(CTApplicationDAO obj) throws ISPACException {
		logger.info("\tFormulario obtenido: "+obj.getName());
	}

	private void logResult(PEntidadDAO item) throws ISPACException {
		if (item!= null){
			logger.info("\tObtenido formulario de Entidad a presentar");
		}else{
			logger.info("\tNo se ha obtenido formulario de Entidad a presentar");
		}
	}


	private PEntidadDAO getProcedureFormVisibility(String ruleClass) throws ISPACException {
		int procedureId = 1;
		int entityId = 1;
		int stagePcdId = 0;
		int taskPcdId = 0;

		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleVisibilityToProcedure(cnt, entityId, procedureId, rule);

			IItemCollection itemcol = getProcedureEntities(procedureId, stagePcdId, taskPcdId);
			return (PEntidadDAO)getFormEntity(itemcol, entityId);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	 }


	 private PEntidadDAO getStageFormVisibility(String ruleClass) throws ISPACException {
		int procedureId = 1;
		int entityId = 1;
		int stagePcdId = 1;
		int taskPcdId = 0;

		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleVisibilityToStage(cnt, entityId, stagePcdId, rule);

			IItemCollection itemcol = getProcedureEntities(procedureId, stagePcdId, taskPcdId);
			return (PEntidadDAO)getFormEntity(itemcol, entityId);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	 }


	 private PEntidadDAO getTaskFormVisibility(String ruleClass) throws ISPACException {
		int procedureId = 1;
		int entityId = 1;
		int stagePcdId = 1;
		int taskPcdId = 1;

		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleVisibilityToTask(cnt, entityId, stagePcdId, rule);

			IItemCollection itemcol = getProcedureEntities(procedureId, stagePcdId, taskPcdId);
			return (PEntidadDAO)getFormEntity(itemcol, entityId);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	 }


	private IItem getFormEntity(IItemCollection itemcol, int entityId) throws ISPACException{
		while(itemcol.next()){
			IItem item = itemcol.value();
			if (item.getKeyInt() == entityId){
				return (PEntidadDAO)item;
			}
		}
		return null;
	}

	private CTApplicationDAO getStageApplication(String ruleClass) throws ISPACException{
		int stagePcdId = 1;
		int entityId = 1;

		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleFormToStage(cnt, entityId, stagePcdId, rule);

			return getStageApplication(stagePcdId, entityId, null);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	}

	private CTApplicationDAO getTaskApplication(String ruleClass) throws ISPACException{
		int taskPcdId = 1;
		int entityId = 1;
		boolean noDefault = false;
		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleFormToTask(cnt, entityId, taskPcdId, rule);

			return getTaskApplication(taskPcdId, entityId, noDefault, null);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	}


	private CTApplicationDAO getProcedureApplication(String ruleClass) throws ISPACException{
		int procedureId = 1;
		int entityId = 1;
		DbCnt cnt = ctx.getConnection();
		try{
			cnt.openTX();

			CTRuleDAO rule = insertRule(cnt, ruleClass, StringUtils.substring(ruleClass, StringUtils.lastIndexOf(ruleClass, '.')+1));
			relateRuleFormToProcedure(cnt, entityId, procedureId, rule);

			return getProcedureApplication(procedureId, entityId, null);
		}finally{
			//rollback
			cnt.closeTX(false);
		}
	}


	private CTApplicationDAO getStageApplication(int stagePcdId, int entityId, Map params) throws ISPACException{
		return getInnerEntitiesAPI().getStageApplication(ctx.getConnection(), stagePcdId, entityId, params);
	}

	private CTApplicationDAO getTaskApplication(int taskPcdId, int entityId, boolean noDefault, Map params) throws ISPACException{
		return getInnerEntitiesAPI().getTaskApplication(ctx.getConnection(), taskPcdId, entityId, noDefault, params);
	}

	private CTApplicationDAO getProcedureApplication(int procedureId, int entityId, Map params) throws ISPACException{
		return getInnerEntitiesAPI().getProcedureApplication(ctx.getConnection(), procedureId, entityId, params);
	}

	private IItemCollection getProcedureEntities(int procedureId, int stagePcdId, int taskPcdId) throws ISPACException{
		return getInnerEntitiesAPI().getProcedureEntities(procedureId, stagePcdId, taskPcdId);
	}

	private CTRuleDAO insertRule(DbCnt cnt, String clase, String nombre) throws ISPACException{
		CTRuleDAO rule = new CTRuleDAO(cnt);
		rule.createNew(cnt);
		rule.set("CLASE", clase);
		rule.set("NOMBRE", nombre);
		rule.store(cnt);
		return rule;
	}


	private void relateRuleVisibilityToStage(DbCnt cnt, int entityId, int stagePcdId, CTRuleDAO rule) throws ISPACException{
		relateRuleToStage(cnt, entityId, stagePcdId, rule, RULE_VISIBLE_FIELD);
	}

	private void relateRuleFormToStage(DbCnt cnt, int entityId, int stagePcdId, CTRuleDAO rule) throws ISPACException{
		relateRuleToStage(cnt, entityId, stagePcdId, rule, RULE_FORM_FIELD);
	}


	private void relateRuleToStage(DbCnt cnt, int entityId, int stagePcdId, CTRuleDAO rule, String ruleField) throws ISPACException{

		PFrmFaseDAO frmStage = new PFrmFaseDAO(cnt);
		//Comprobamos si ya hay un formulario asociado a esa fase y entidad para vincularle una regla
		try{
			frmStage.load(cnt, stagePcdId, entityId);
		}catch(ISPACNullObject e){
			frmStage.createNew(cnt);
			frmStage.set("ID_ENT", entityId);
			frmStage.set("ID_FASE",stagePcdId);
		}
        frmStage.set(ruleField, rule.getKeyInt());
        frmStage.store(cnt);
	}


	private void relateRuleVisibilityToTask(DbCnt cnt, int entityId, int taskPcdId,CTRuleDAO rule) throws ISPACException{
		relateRuleToTask(cnt, entityId, taskPcdId, rule, RULE_VISIBLE_FIELD);
	}

	private void relateRuleFormToTask(DbCnt cnt, int entityId, int taskPcdId,CTRuleDAO rule) throws ISPACException{
		relateRuleToTask(cnt, entityId, taskPcdId, rule, RULE_FORM_FIELD);
	}

	private void relateRuleToTask(DbCnt cnt, int entityId, int taskPcdId,CTRuleDAO rule, String ruleField) throws ISPACException {
		PFrmTramiteDAO frmTask = new PFrmTramiteDAO(cnt);
		//Comprobamos si ya hay un formulario asociado a ese tramite y entidad para vincularle una regla
		try{
			frmTask.load(cnt, taskPcdId, entityId);
		}catch(ISPACNullObject e){
			frmTask.createNew(cnt);
			frmTask.set("ID_ENT", entityId);
			frmTask.set("ID_TRAMITE",taskPcdId);
		}
        frmTask.set(ruleField, rule.getKeyInt());
        frmTask.store(cnt);
	}


	private void relateRuleVisibilityToProcedure(DbCnt cnt, int entityId, int procedureId, CTRuleDAO rule) throws ISPACException {
		relateRuleToProcedure(cnt, entityId, procedureId, rule, RULE_VISIBLE_FIELD);
	}

	private void relateRuleFormToProcedure(DbCnt cnt, int entityId, int procedureId, CTRuleDAO rule) throws ISPACException {
		relateRuleToProcedure(cnt, entityId, procedureId, rule, RULE_FORM_FIELD);
	}


	private void relateRuleToProcedure(DbCnt cnt, int entityId, int procedureId, CTRuleDAO rule, String ruleField) throws ISPACException {

		PEntidadDAO frmEntidad = new PEntidadDAO(cnt);
		//Comprobamos si ya hay un formulario asociado a ese procedimiento y entidad para vincularle una regla
		try{
			frmEntidad.load(cnt, procedureId, entityId);
		}catch(ISPACNullObject e){
			frmEntidad.createNew(cnt);
			frmEntidad.set("ID_ENT", entityId);
			frmEntidad.set("ID_PCD", procedureId);
		}
        frmEntidad.set(ruleField, rule.getKeyInt());
        frmEntidad.store(cnt);
	}


	public class InnerEntitiesAPI extends EntitiesAPI {

		public InnerEntitiesAPI(ClientContext context) {
			super(context);
		}

		// Prueba de 'Formulario asignado mediante regla'
		public CTApplicationDAO testGetStageApplication(int stagePcdId, int entityId, Map params) throws ISPACException{
			return this.getStageApplication(ctx.getConnection(), stagePcdId, entityId, params);
		}

		public CTApplicationDAO testGetTaskApplication(int taskPcdId, int entityId, boolean noDefault, Map params) throws ISPACException{
			return this.getTaskApplication(ctx.getConnection(), taskPcdId, entityId, noDefault, params);
		}

		public CTApplicationDAO testGetProcedureApplication(int procedureId, int entityId, Map params) throws ISPACException{
			return this.getProcedureApplication(ctx.getConnection(), procedureId, entityId, params);
		}

		// Prueba de 'Visibilidad de formulario calculada mediante regla'
		public IItemCollection getProcedureEntities(int procedureId, int stagePcdId, int taskPcdId) throws ISPACException {
			return super.getProcedureEntities(procedureId, stagePcdId, taskPcdId);
		}
	}

}