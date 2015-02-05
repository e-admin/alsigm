package ieci.tdw.ispac.ispaclib.sharepoint.gendoc;

import ieci.tdw.ispac.api.connector.IDocConnector;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.sharepoint.gendoc.moss.MossConnector;

import java.io.InputStream;
import java.io.OutputStream;

public class MossWrapperConnector implements IDocConnector{
	
	private ClientContext ctx = null;
	
	public MossWrapperConnector(ClientContext ctx) {
		this.ctx = ctx;
	}
	
	public boolean existsDocument(Object sesion, String sGUID)
		throws ISPACException{
		return getInstance().existsDocument(sesion, sGUID);
	}

	public void getDocument(Object sesion, String sGUID, OutputStream out)
		throws ISPACException{
		getInstance().getDocument(sesion, sGUID,out);
	}

	public String updateDocument(Object sesion, String sGUID, InputStream in, int length, String sProperties)
		throws ISPACException{
		return getInstance().updateDocument(sesion, sGUID,in,length,sProperties);
	}

	public String newDocument(Object sesion, InputStream in, int length, String sProperties)
		throws ISPACException{
		return getInstance().newDocument(sesion, in,length,sProperties);
	}

	public void deleteDocument(Object sesion, String sGUID)
		throws ISPACException{
		getInstance().deleteDocument(sesion, sGUID);
	}

	public int getDocumentSize(Object sesion, String sGUID)
		throws ISPACException{
		return getInstance().getDocumentSize(sesion, sGUID);
	}

	public String getMimeType(Object sesion, String sGUID)
		throws ISPACException{
		return getInstance().getMimeType(sesion, sGUID);
	}


	public String getProperties(Object sesion, String sGUID)
		throws ISPACException{
		return getInstance().getProperties(sesion, sGUID);
	}

	public String getProperty(Object sesion, String sGUID, String property)
		throws ISPACException{
		return getInstance().getProperty(sesion, sGUID,property);
	}

	public void setProperty(Object sesion, String sGUID, String name, String value)
		throws ISPACException{
		getInstance().setProperty(sesion, sGUID,name,value);
	}
	
	public void createRepository(Object session) throws ISPACException {
		getInstance().createRepository(session);
	}
	
	

// Habria que revisar los parametros necesarios para la creacion de la session. Ahora mismo tal y como esta el metodo
// suponemos que el conector leera dichos parametros de un fichero de configuración
	public Object createSession()
		throws ISPACException{
		return getInstance().createSession();
	}

	public void closeSession(Object sesion)
		throws ISPACException{
		getInstance().createSession();
	}

	public String getRepositoryInfo(Object sesion, String repId) 
		throws ISPACException{
		return getInstance().getRepositoryInfo(sesion,repId);
	}

//	private static final String DOCUMENT_NAME_PROPERTY="Title";
//	private static final String DOCUMENT_FECHA_PRESENTACION_PROPERTY="FechaPresentacion";
//	private static final String DOCUMENT_HORA_PRESENTACION_PROPERTY="HoraPresentacion";
//	private static final String DOCUMENT_MIMETYPE_PROPERTY="mimetype";
	
//	public String getDocumentName(Object sesion, String sGUID) 
//		throws ISPACException{
//		return ConnectorsLocator.getConectorRepositorioDocumental().getProperty(sesion, sGUID, DOCUMENT_NAME_PROPERTY);
//	}
//	
//	public Date getDocumentTimeStamp(Object sesion, String sGUID) 
//		throws ISPACException{
//		//Ej fechaPresentacion 15/12/2011 	horaPresentacion 15:12:57  
//		String fechaPresentacion=ConnectorsLocator.getConectorRepositorioDocumental().getProperty(sesion, sGUID, DOCUMENT_FECHA_PRESENTACION_PROPERTY);
//		String horaPresentacion=ConnectorsLocator.getConectorRepositorioDocumental().getProperty(sesion, sGUID, DOCUMENT_HORA_PRESENTACION_PROPERTY);
//		
//		Calendar timestamp=null;
//		try{ 
//			Date auxDate=DateFormat.getInstance().parse(fechaPresentacion);
//			timestamp=Calendar.getInstance();
//			timestamp.setTime(auxDate);
//		}catch(Exception e){
//			logger.error(e);
//			return null;
//		}
//		
//		String cadAux=horaPresentacion;
//		int posDosPuntos=cadAux.indexOf(":");
//		int hora=Integer.parseInt(cadAux.substring(0,posDosPuntos));
//		cadAux=cadAux.substring(posDosPuntos+1);
//		
//		posDosPuntos=cadAux.indexOf(":");
//		int minutos=Integer.parseInt(cadAux.substring(0,posDosPuntos));
//		cadAux=cadAux.substring(posDosPuntos+1);
//		
//		int segundos=Integer.parseInt(cadAux);
//		
//		timestamp.set(Calendar.HOUR_OF_DAY, hora);
//		timestamp.set(Calendar.MINUTE, minutos);
//		timestamp.set(Calendar.SECOND, segundos);
//
//		
//		return timestamp.getTime();
//	}
//
//	public String getDocumentHash(Object sesion, String sGUID)
//		throws ISPACException{
//		ByteArrayOutputStream baos=new ByteArrayOutputStream();
//		ConnectorsLocator.getConectorRepositorioDocumental().getDocument(sesion, sGUID, baos);
//		try{	return getHash(baos.toByteArray()); }
//		catch(Exception e){ logger.error(e); }
//		return null;
//	}
//	
//	public String getDocumentMimetype(Object sesion, String sGUID)
//		return ConnectorsLocator.getConectorRepositorioDocumental().getProperty(sesion, sGUID, DOCUMENT_MIMETYPE_PROPERTY);
//	}
//	
//	/**
//	   * Devuelve el hash o resumen de un conjunto de bytes
//	   * @param clear Conjunto de bytes sobre los que calcular el hash
//	   * @return String Hash del conjunto de bytes
//	   * @throws Exception
//	   */
//	  private String getHash(byte[] clear) throws Exception {
//	    MessageDigest md = MessageDigest.getInstance("MD5");
//	    byte[] b = md.digest(clear);
//
//	    int size = b.length;
//	    StringBuffer h = new StringBuffer(size);
//	    for (int i = 0; i < size; i++) {
//	        int u = b[i]&255; // unsigned conversion
//	        if (u<16) {
//	            h.append("0"+Integer.toHexString(u));
//	        } else {
//	            h.append(Integer.toHexString(u));
//	        }
//	    }
//	    return h.toString();
//	  } 

	private IDocConnector getInstance() throws ISPACException{
		return MossConnector.getInstance(ctx);
	}

}
