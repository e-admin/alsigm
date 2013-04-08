package isicres.document.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.MimetypesFileTypeMap;

import org.alfresco.webservice.util.ContentUtils;

import es.ieci.tecdoc.isicres.document.connector.alfresco.AlfrescoDocumentConnector;
import es.ieci.tecdoc.isicres.document.connector.vo.ISicresAbstractDocumentVO;

public class CreateContent {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ISicresAbstractDocumentVO document = new ISicresAbstractDocumentVO();
		
		File file = new File("c:\\OnAccessScanLog.txt");
		try {
			InputStream viewStream = new FileInputStream(file);
			document.setContent(ContentUtils.convertToByteArray(viewStream));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.setName(file.getName());
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
		
		AlfrescoDocumentConnector alfrescoDocumentConnector = new AlfrescoDocumentConnector();
		
		try {
			document.setId("6db33652-056d-4090-86ae-bc5e3fd44ac8");
			// Create
			//lfrescoDocumentConnector.create(document);
			
			// Delete			
			//alfrescoDocumentConnector.delete(document);
			
			// Search
			/*alfrescoDocumentConnector.retrieve(document);
		    String name = "C:\\prueba.txt";
		    File file2 = new File(name);
		    file2.createNewFile();
		    FileInputStream fileInputStream = new FileInputStream(name);
		    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(document.getContent());
		    OutputStream outputStream = new FileOutputStream(name);
		    int data;
		    while ((data = byteArrayInputStream.read()) != -1) {
		    	outputStream.write(data);
		    }
	
		    fileInputStream.close();
		    outputStream.close();*/
			
			// Update
			alfrescoDocumentConnector.update(document);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
