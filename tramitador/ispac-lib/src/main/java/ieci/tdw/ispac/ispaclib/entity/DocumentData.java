/*
 * Created on 10-sep-2004
 *
 */
package ieci.tdw.ispac.ispaclib.entity;

import ieci.tdw.ispac.api.rule.IRuleContextParams;
import ieci.tdw.ispac.api.rule.RuleProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author juanin
 *
 */
public class DocumentData implements IRuleContextParams
{
	int mIdDocType;
	int mIdTemplate;
	int mIdStagePCD;
	int mIdStage;
	int mIdTaskPCD;
	int mIdTask;
	int mIdDoc;

	String mdocname;
	String mdocauthor;
	String mdocauthorname;

	String mdocref;
	String msmimetype;

	String mTemplateName;
	
	String mRegType;

	String mnumexp;

	int mIdEntity;	// Entidad relacionada
	int mIdKey;		// Identificador del registro de la entidad
	
	public DocumentData()
	{
		mnumexp="";
		mIdDocType=0;
		mIdStagePCD=0;
		mIdStage=0;
		mIdTaskPCD=0;
		mIdTask=0;
		mdocname=null;
		mdocauthor=null;
		mdocauthorname=null;
		mdocref=null;
		mIdTemplate=0;
		mIdDoc=0;
		mdocref = "";
		msmimetype = "";

		mTemplateName="";
		mRegType = null;

		mIdEntity = 0;
		mIdKey = 0;
	}

	public DocumentData(
	String numexp,
	int IdDocType,
	int IdStagePCD,
	int IdStage,
	int IdTaskPCD,
	int IdTask,
	String docname,
	String docauthor,
	String docauthorname,
	String regType)
	{
	    mnumexp=numexp;
		mIdDocType=IdDocType;
		mIdStagePCD=IdStagePCD;
		mIdStage=IdStage;
		mIdTaskPCD=IdTaskPCD;
		mIdTask=IdTask;
		mdocname=docname;
		mdocauthor=docauthor;
		mdocauthorname=docauthorname;
		mdocref=null;
		mIdTemplate=0;
		mIdDoc=0;
		mdocref = "";
		msmimetype = "";
		mTemplateName="";
		mRegType = regType;

		mIdEntity = 0;
		mIdKey = 0;
	}

	public int getDocType()
	{
		return mIdDocType;
	}

	public int getStagePCD()
	{
		return mIdStagePCD;
	}

	public int getStage()
	{
		return mIdStage;
	}

	public int getTaskPCD()
	{
		return mIdTaskPCD;
	}

	public int getTask()
	{
		return mIdTask;
	}

	public String getName()
	{
		return mdocname;
	}

	public String getAuthor()
	{
		return mdocauthor;
	}

	public String getAuthorName()
	{
		return mdocauthorname;
	}

	public int getDocId()
	{
		return mIdDoc;
	}

	public void setDoc(int iddoc)
	{
		mIdDoc=iddoc;
	}


	public String getDocRef()
	{
		return mdocref;
	}

	public void setDocRef(String docref)
	{
		mdocref=docref;
	}

	public String getMimeType()
	{
		return msmimetype;
	}

	public void setMimeType(String mimeype)
	{
		msmimetype=mimeype;
	}

	public int getTemplate()
	{
		return mIdTemplate;
	}

	public void setTemplate(int IdTemplate)
	{
		mIdTemplate=IdTemplate;
	}

	public String getTemplateName()
	{
		return mTemplateName;
	}

	public void setTemplateName(String name)
	{
		mTemplateName=name;
	}

	public String getRegType() {
		return mRegType;
	}

	public void setRegType(String regType) {
		mRegType = regType;
	}

	public int getEntity()
	{
		return mIdEntity;
	}

	public void setEntity(int entity)
	{
		mIdEntity = entity;
	}
	
	public int getKey()
	{
		return mIdKey;
	}

	public void setKey(int key)
	{
		mIdKey = key;
	}
	
	public void setNumExp (String numExp){
		this.mnumexp = numExp;
	}
	
	public String getNumExp (){
		return mnumexp;
	}

	
    // Implementación de IRuleContextParams

    public int getRuleProcedureId()
    {
        return 0;
    }

    public int getRuleProcId()
    {
        return 0;
    }

    public String getRuleNumexp()
    {
        return mnumexp;
    }

    public int getRuleStagePCDId()
    {
        return getStagePCD();
    }

    public int getRuleStageId()
    {
        return getStage();
    }

    public int getRuleTaskPCDId()
    {
        return getTaskPCD();
    }

    public int getRuleTaskId()
    {
        return getTask();
    }

    public Map getRuleParameters()
    {
        Map parammap=new HashMap();

        parammap.put(RuleProperties.RCTX_DOCUMENTID,String.valueOf(getDocId()));
        parammap.put(RuleProperties.RCTX_DOCUMENTTYPE,String.valueOf(getDocType()));
        parammap.put(RuleProperties.RCTX_DOCUMENTNAME,getName());
        parammap.put(RuleProperties.RCTX_DOCUMENTAUTHOR,getAuthor());
        parammap.put(RuleProperties.RCTX_DOCUMENTAUTHORNAME,getAuthorName());
        parammap.put(RuleProperties.RCTX_DOCUMENTMIMETYPE,getMimeType());
        parammap.put(RuleProperties.RCTX_DOCUMENTREF,getDocRef());
        parammap.put(RuleProperties.RCTX_TEMPLATEID,String.valueOf(getTemplate()));
        parammap.put(RuleProperties.RCTX_TEMPLATENAME,getTemplateName());
        parammap.put(RuleProperties.RCTX_ENTITYID,String.valueOf(getEntity()));
        parammap.put(RuleProperties.RCTX_REGENTITYID,String.valueOf(getKey()));

        return parammap;
    }
}
