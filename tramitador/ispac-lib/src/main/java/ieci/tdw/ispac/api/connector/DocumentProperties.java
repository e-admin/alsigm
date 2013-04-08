package ieci.tdw.ispac.api.connector;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

/*
 *  < ?xml version="1.0" encoding=" UTF8"? > 
 *  <doc_properties>
 * 		<property>
 * 			<name>document_id</name>
 * 			<value>1</value>
 * 		</property>
 * 		<property>
 * 			<name>document_type</name>
 * 			<value>2</value>
 * 		</property>
 * 		<property>
 * 			<name>document_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>procedure_id</name>
 * 			<value>1</value>
 * 		</property>
 * 		<property>
 * 			<name>procedure_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>expedient_id</name>
 * 			<value>1</value>
 * 		</property>
 * 		<property>
 * 			<name>expedient_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>user_guid</name>
 * 			<value><![CDATA[GUID]]></value>
 * 		</property>
 * 		<property>
 * 			<name>user_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>stage_id</name>
 * 			<value>1</value>
 * 		</property>
 * 		<property>
 * 			<name>stage_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>task_id</name>
 * 			<value>1</value>
 * 		</property>
 * 		<property>
 * 			<name>task_name</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>mimetype</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>folder_path</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property>
 * 		<property>
 * 			<name>register_numbre</name>
 * 			<value><![CDATA[nombre]]></value>
 * 		</property> 
 *  </doc_properties>
**/

public class DocumentProperties extends Properties
{
	protected int mDocumentId = 0;

	protected int mDocumentType = 0;

	protected String msDocumentName = null;

	protected int mProcedureId = 0;

	protected String msProcedureName = null;

	protected int mExpedientId = 0;

	protected String msExpedientName = null;

	protected String msUserGUID = null;
	
	protected String msUserName = null;

	protected int mStageId = 0;

	protected String msStageName = null;

	protected int mTaskId = 0;

	protected String msTaskName = null;

	protected String msMimeType = null;
	
	protected String msFolderPath = null;
	
	protected String msRegisterNumber = null;

	public DocumentProperties(String sProperties) 
	throws ISPACException
	{
		super(sProperties);
		
		Iterator iterator = mProperty.entrySet().iterator();

		while (iterator.hasNext())
		{
			Entry entry = (Entry) iterator.next();
			String sProperty = (String) entry.getKey();
			String sValue = (String) entry.getValue();
			
			if (sProperty.equalsIgnoreCase("document_id"))
				mDocumentId = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("document_type"))
				mDocumentType = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("document_name"))
				msDocumentName = sValue;
			else if (sProperty.equalsIgnoreCase("procedure_id"))
				mProcedureId = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("procedure_name"))
				msProcedureName = sValue;
			else if (sProperty.equalsIgnoreCase("expedient_id"))
				mExpedientId = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("expedient_name"))
				msExpedientName = sValue;
			else if (sProperty.equalsIgnoreCase("user_guid"))
				msUserGUID = sValue;
			else if (sProperty.equalsIgnoreCase("user_name"))
				msUserName = sValue;
			else if (sProperty.equalsIgnoreCase("stage_id"))
				mStageId = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("stage_name"))
				msStageName = sValue;
			else if (sProperty.equalsIgnoreCase("task_id"))
				mTaskId = Integer.parseInt(sValue);
			else if (sProperty.equalsIgnoreCase("task_name"))
				msTaskName = sValue;
			else if (sProperty.equalsIgnoreCase("mimetype"))
				msMimeType = sValue;
			else if (sProperty.equalsIgnoreCase("folder_path"))
				msFolderPath = sValue;
			else if (sProperty.equalsIgnoreCase("register_number"))
				msRegisterNumber = sValue;
		}
	}

	public int getDocumentId()
	{
		return mDocumentId;
	}

	public int getDocumentType()
	{
		return mDocumentType;
	}

	public String getDocumentName()
	{
		return msDocumentName;
	}

	public int getProcedureId()
	{
		return mProcedureId;
	}

	public String getProcedureName()
	{
		return msProcedureName;
	}

	public int getExpedientId()
	{
		return mExpedientId;
	}

	public String getExpedientName()
	{
		return msExpedientName;
	}

	public String getUserGUID()
	{
		return msUserGUID;
	}

	public String getUserName()
	{
		return msUserName;
	}

	public int getStageId()
	{
		return mStageId;
	}

	public String getStageName()
	{
		return msStageName;
	}

	public int getTaskId()
	{
		return mTaskId;
	}

	public String getTaskName()
	{
		return msTaskName;
	}

	public String getMimeType()
	{
		return msMimeType;
	}
	
	public String getFolderPath()
	{
		return msFolderPath;
	}	
	
	public String getRegisterNumber()
	{
		return msRegisterNumber;
	}
	
	public HashMap getProperties() 
	{
		return mProperty;
	}
}