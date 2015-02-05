package clients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

import org.apache.axis.client.Stub;

import ws.batch.WSProcesosBatch;
import ws.batch.WSProcesosBatchServiceLocator;

import common.constants.Constants;

/**
 * Cliente de pruebas del servicio web de documentos vitales
 */
public class ProcesosBatchClient
{
    
    public static void main(String args[])
    {
        try
        {
            Properties props = getProperties();
            
			WSProcesosBatchServiceLocator locator = new WSProcesosBatchServiceLocator();
			
			// Establecer la URL del servicio Web
			WSProcesosBatch remote = null;
            String endpoint = props.getProperty("procesosBatch.url");
            if ((endpoint != null) && (!endpoint.equals("")))
            {
            	URL url = new URL(endpoint);
            	remote = locator.getWSProcesosBatch(url);                
                System.out.println("SOAP URL actualizada: " + endpoint);
            } else {
            	remote = locator.getWSProcesosBatch();
            }
			
			Stub axisPort = (Stub) remote;
			System.out.println("Proxy cargado");

            // Establecer el usuario y contraseña
            String user = props.getProperty("procesosBatch.autenticacion.usuario"); 
            String password = props.getProperty("procesosBatch.autenticacion.clave");	
            String entity = props.getProperty("procesosBatch.autenticacion.entidad");
            
            if ((user != null) && (!user.equals(""))){
            	axisPort.setHeader("http://my.name.space/headers",Stub.USERNAME_PROPERTY, user);
            }
            
            if ((password != null) && (!password.equals(""))){
    			axisPort.setHeader("http://my.name.space/headers",Stub.PASSWORD_PROPERTY, password);
            }
            
            if ((entity != null) && (!entity.equals(""))){
    			axisPort.setHeader("http://my.name.space/headers",Constants.ENTITY_PROPERTY, entity);
            }

            String metodo = "";
            while (!metodo.equals("6")) {
	            System.out.println("Lista de metodos");
	            System.out.println("---------------------------------------------------");
	            System.out.println("1. cerrarPrevisiones");
	            System.out.println("2. eliminarDocumentosVitalesObsoletos");	            
	            System.out.println("3. eliminarUnidadesDocumentalesPrestadasNoDevueltas");
	            System.out.println("4. pasarAHistoricoDocumentosVitalesCaducados");
	            System.out.println("5. publicarUnidadesDocumentales");
	            System.out.println("---------------------------------------------------");
	            System.out.println("6. Salir");
	            System.out.println("---------------------------------------------------");
	            System.out.println();
	            System.out.print("Opción: ");
	            metodo = readLine();
	            
	            try {
		            if ((metodo == null) || (metodo.equals(""))) {
		            	System.out.println("Error: Metodo incorrecto");
		            } else {
		            	if (metodo.trim().equals("1")){
		                    System.out.print("Cerrar previsiones. Desea continuar (S/N): ");
		            		String line = readLine();
		            		if ((line!=null) && (!line.equals(""))&& (line.equals("S"))) {
		            			remote.cerrarPrevisiones();
		            			System.out.println("Previsiones cerradas");
		            		}
		                    System.out.println("");
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();	                    
		            	} else if (metodo.trim().equals("2")){
		                    System.out.print("Eliminar documentos vitales obsoletos. Desea continuar (S/N): ");
		            		String line = readLine();
		            		if ((line!=null) && (!line.equals(""))&& (line.equals("S"))) {
		            			remote.eliminarDocumentosVitalesObsoletos();
		            			System.out.println("Documentos vitales obsoletos eliminados");
		            		}
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();	                    
		            	} else if (metodo.trim().equals("3")){
		                    System.out.print("Eliminar unidades documentales prestadas no devueltas. Desea continuar (S/N): ");
		            		String line = readLine();
		            		if ((line!=null) && (!line.equals(""))&& (line.equals("S"))) {
		            			remote.eliminarUnidadesDocumentalesPrestadasNoDevueltas();
		            			System.out.println("Unidades documentales prestadas no devueltas eliminadas");
		            		}	            			
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();	                    
		            	}  else if (metodo.trim().equals("4")){
		                    System.out.print("Pasar a historico documentos vitales caducados. Desea continuar (S/N): ");
		            		String line = readLine();
		            		if ((line!=null) && (!line.equals(""))&& (line.equals("S"))) {
		            			remote.pasarAHistoricoDocumentosVitalesCaducados();
		            			System.out.println("Documentos vitales caducados pasados a historico");
		            		}	            			
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();	                    
		            	} else if (metodo.trim().equals("5")){
		                    System.out.print("Publicar unidades documentales. Desea continuar (S/N): ");
		            		String line = readLine();
		            		if ((line!=null) && (!line.equals(""))&& (line.equals("S"))) {
		            			remote.publicarUnidadesDocumentales();
		            			System.out.println("Unidades documentales publicadas");
		            		}	            			
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();	                    
		            	} else if (!metodo.equals("6")) {
		            		System.out.println("Error: Metodo incorrecto");
		            	}
		            }
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
            }   
        } 
        catch (Throwable e)
        {
            e.printStackTrace();
        }
    }
    
    public static String readLine() {
		BufferedReader standard = new BufferedReader(new InputStreamReader(
				System.in));
		try {
			String inline = standard.readLine();
			return inline;
		} catch (Exception e) {
			return ("data entry error");
		}
	}
    
    public static Properties getProperties() throws Exception{

    	String DEFAULT_PROPERTIES_FILE = "conf/procesosBatchClient.properties";

    	InputStream is = null;
    	try {
    		is = Thread.currentThread().getContextClassLoader().getResourceAsStream( DEFAULT_PROPERTIES_FILE );
    	} catch (Exception e) {
    		System.out.println("Error al obtener el fichero de propiedades con: Thread.currentThread().getContextClassLoader().getResourceAsStream( DEFAULT_PROPERTIES_FILE );");
    	}
    	if (is==null){
    		try {
    			is = ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTIES_FILE);

    		} catch (Exception e) {
    			System.out.println("Error al obtener el fichero de propiedades con: ClassLoader.getSystemResourceAsStream(DEFAULT_PROPERTIES_FILE);");
    		}
    	}

    	DEFAULT_PROPERTIES_FILE = "/conf/procesosBatchClient.properties";
    	if (is==null){
    		try {
    			is = ProcesosBatchClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);

    		} catch (Exception e) {
    			System.out.println("Error al obtener el fichero de propiedades con: DocVitalesClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);");
    		}
    	}

    	Properties props = new Properties();
    	props.load(is);
    	return props; 
    }
    
}
