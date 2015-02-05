package ieci.tdw.ispac.api.connector;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.io.InputStream;
import java.io.OutputStream;

  /*
   *

   *  Documento XML que describe las propiedades de un documento.
   *
   *  < ?xml version="1.0" encoding=" UTF8"? >
   *  <docref>
   * 	<connector>n</connector>
   *    <guid></guid>
   *  </docref>
   *
   *
   *
   *
   *  Documento XML que describe las propiedades de un documento.
   *
   *  < ?xml version="1.0" encoding=" UTF8"? >
   *  <properties>
   *    <document id='n'>
   * 		<type>n</type>
   * 		<name><![CDATA[nombre del documento]]></name>
   * 	</document>
   *
   * 	<procedure id='n'>
   * 			<name><![CDATA[nombre del procedimiento]]></name>
   * 	</procedure>
   *
   *    <expedient id='n'>
   * 			<name><![CDATA[nombre del expedient]]></name>
   * 	</expedient>
   *
   *    <user>
   * 			<guid><![CDATA[GUID usuario]]></guid>
   * 			<name><![CDATA[nombre del usuario]]></name>
   * 	</user>
   *
   *    <stage id='n'>
   * 			<name><![CDATA[nombre de la fase]]></name>
   * 	</stage>
   *
   *    <task id='n'>
   * 			<name><![CDATA[nombre de la tarea]]></name>
   * 	</task>
   *
   *    <mimetype>
   * 			<name><![CDATA[nombre de la tarea]]></name>
   * 	</mimetype>
   *    <sign>
   * 			<value><![CDATA[valor firma]]></value>
   * 	</sign>
   *  </properties>
  **/

public interface IDocConnector
{
	/**
	 * Comprueba si un documento existe
	 * @param sGUID
	 * @return
	 * @throws ISPACException
	 */
	public boolean existsDocument(Object session, String sGUID)
	throws ISPACException;

	/**
	 * Retorna el documento a traves del parametro out
	 * @param sGUID
	 * @param out
	 * @throws ISPACException
	 */
	public void getDocument(Object session, String sGUID, OutputStream out)
	throws ISPACException;

	public String updateDocument(Object session, String sGUID, InputStream in, int length, String sProperties)
	throws ISPACException;

	public String newDocument(Object session, InputStream in, int length, String sProperties)
	throws ISPACException;

	public void deleteDocument(Object session, String sGUID)
	throws ISPACException;

	public int getDocumentSize(Object session, String sGUID)
	throws ISPACException;

	public void createRepository(Object session )
	throws ISPACException;

	public String getMimeType(Object session, String sGUID)
	throws ISPACException;

	public String getProperties(Object session, String sGUID)
	throws ISPACException;

	public String getProperty(Object session, String sGUID, String property)
	throws ISPACException;
	
	public void setProperty(Object session, String sGUID, String name, String value)
	throws ISPACException;
	
	public String getRepositoryInfo(Object session, String repId) 
	throws ISPACException;
	
	public Object createSession()
	throws ISPACException;

	public void closeSession(Object session)
	throws ISPACException;
}
