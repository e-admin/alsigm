package clients;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;

import org.apache.axis.client.Stub;

import ws.tiposdocvitales.WSTiposDocumentosVitales;
import ws.tiposdocvitales.WSTiposDocumentosVitalesServiceLocator;

import common.constants.Constants;

/**
 * Cliente de pruebas del servicio web de tipos de documentos vitales
 */
public class TiposDocVitalesClient
{
	
    public static void main(String args[])
    {
        try
        {
            Properties props = getProperties();

            String indPedirDatos = props.getProperty("tiposDocVit.indPedirDatos");
            boolean pedirDatos = false;
            pedirDatos = (indPedirDatos!=null) && (indPedirDatos.equals("S"));
            
			WSTiposDocumentosVitalesServiceLocator locator = new WSTiposDocumentosVitalesServiceLocator();
			
			// Establecer la URL del servicio Web
			WSTiposDocumentosVitales remote = null;
            String endpoint = props.getProperty("tiposDocVit.url");
            if ((endpoint != null) && (!endpoint.equals("")))
            {
            	URL url = new URL(endpoint);
            	remote = locator.getWSTiposDocumentosVitales(url);                
                System.out.println("SOAP URL actualizada: " + endpoint);
            } else {
            	remote = locator.getWSTiposDocumentosVitales();
            }
			
			Stub axisPort = (Stub) remote;
			System.out.println("Proxy cargado");

            // Establecer el usuario y contraseña
            String user = props.getProperty("tiposDocVit.autenticacion.usuario"); 
            String password = props.getProperty("tiposDocVit.autenticacion.clave");
            String entity = props.getProperty("tiposDocVit.autenticacion.entity");
            
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
            while (!metodo.equals("8")) {
	            System.out.println("Lista de metodos");
	            System.out.println("----------------------------");
	            System.out.println("1. getTiposDocVit");
	            System.out.println("2. getTipoDocVit");	            
	            System.out.println("3. getTiposDocVitPorProc");
	            System.out.println("4. addRelTipoDocVitProc");
	            System.out.println("5. removeRelTipoDocVitProc");
	            System.out.println("6. removeRelsPorProc");
	            System.out.println("7. removeRelsPorTiposDocProc");
	            System.out.println("----------------------------");
	            System.out.println("8. Salir");
	            System.out.println("----------------------------");
	            System.out.println();
	            System.out.print("Opcion: ");
	            metodo = readLine();
	            
	            try {
		            if ((metodo == null) || (metodo.equals(""))) {
		            	System.out.println("Error: Metodo incorrecto");
		            } else {
		            	if (metodo.trim().equals("1")){
		                    System.out.println("Buscando los tipos de documentos vitales");
		                    debug(remote.getTiposDocVit());
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		                    
		            	} else if (metodo.trim().equals("2")){
		            		String idTipoDocVit = props.getProperty("tiposDocVit.getTipoDocVit.idTipoDocVit") == null ? "" : props.getProperty("tiposDocVit.getTipoDocVit.idTipoDocVit") ;
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTipoDocVit [" + idTipoDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTipoDocVit = line;
		            		}
		                    System.out.println("Buscando el tipo de documentos vitales: idTipoDocVit = " + idTipoDocVit);
		                    debug(remote.getTipoDocVit(idTipoDocVit));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("3")){
		            		String idProc = props.getProperty("tiposDocVit.getTiposDocVitPorProc.idProc") == null ? "" : props.getProperty("tiposDocVit.getTiposDocVitPorProc.idProc") ;
		            		if (pedirDatos) {
		            			System.out.print("Introduzca idProc [" + idProc + "]: ");
		            			String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		                    System.out.println("Buscando los tipos de documentos vitales: idProc = " + idProc);
		                    debug(remote.getTiposDocVitPorProc(idProc));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	}  else if (metodo.trim().equals("4")){
		                    String idProc = props.getProperty("tiposDocVit.addRelTipoDocVitProc.idProc");
		                    String idTipoDocVit = props.getProperty("tiposDocVit.addRelTipoDocVitProc.idTipoDocVit");
		            		if (pedirDatos) {
		            			System.out.print("Introduzca idProc [" + idProc + "]: ");
		            			String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTipoDocVit [" + idTipoDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTipoDocVit = line;
		            		}
		                    System.out.println("Añadiendo relacion: idProc=" + idProc + ", idTipoDocVit=" + idTipoDocVit);
		                    remote.addRelTipoDocVitProc(idProc, idTipoDocVit);
		                    System.out.println("Relacion añadida");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("5")){
		                    String idProc = props.getProperty("tiposDocVit.removeRelTipoDocVitProc.idProc");
		                    String idTipoDocVit = props.getProperty("tiposDocVit.removeRelTipoDocVitProc.idTipoDocVit");
		            		if (pedirDatos) {
		            			System.out.print("Introduzca idProc [" + idProc + "]: ");
		            			String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTipoDocVit [" + idTipoDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTipoDocVit = line;
		            		}
		                    System.out.println("Eliminando relacion: idProc=" + idProc + ", idTipoDocVit=" + idTipoDocVit);
		                    remote.removeRelTipoDocVitProc(idProc, idTipoDocVit);
		                    System.out.println("Relacion eliminada");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("6")){
		                    String idProc = props.getProperty("tiposDocVit.removeRelsPorProc.idProc");
		            		if (pedirDatos) {
		            			System.out.print("Introduzca idProc [" + idProc + "]: ");
		            			String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		            		System.out.println("Eliminando relaciones: idProc=" + idProc);
		                    remote.removeRelsPorProc(idProc);
		                    System.out.println("Relaciones eliminadas");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("7")){
		                    String idProc = props.getProperty("tiposDocVit.removeRelsPorTiposDocProc.idProc");
		                    String idsTiposDocVit = props.getProperty("tiposDocVit.removeRelsPorTiposDocProc.idsTiposDocVit");
		            		if (pedirDatos) {
		            			System.out.print("Introduzca idProc [" + idProc + "]: ");
		            			String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idsTiposDocVit separados por , [" + idsTiposDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idsTiposDocVit = line;
		            		}
		            		
		                    StringTokenizer tok = new StringTokenizer(idsTiposDocVit, ",");
		                    List tokens = new ArrayList();
		                    while (tok.hasMoreTokens())
		                        tokens.add(tok.nextToken());
		                    
		                    System.out.println("Eliminando relaciones: idProc=" + idProc + ", idsTiposDocVit=" + idsTiposDocVit);
		                    remote.removeRelsPorTiposDocProc(idProc, (String[])tokens.toArray(new String[tokens.size()]));
		                    System.out.println("Relaciones eliminadas");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (!metodo.equals("8")) {
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
    
    protected static void debug(docvitales.webservice.vos.TipoDocumentoVital[] tipos)
    {
        if (tipos != null)
        {
            System.out.println("Nº de tipos de documentos vitales: " + tipos.length);
            for (int i = 0; i < tipos.length; i++)
            {
                System.out.println("- Tipo #" + (i+1));
                debug(tipos[i]);
            }
        }
        else
            System.out.println("No se ha encontrado ningun tipo de documentos vitales.");
    }

    protected static void debug(docvitales.webservice.vos.TipoDocumentoVital tipo)
    {
        if (tipo != null)
        {
            System.out.println("  - id:         \t" + tipo.getId());
            System.out.println("  - nombre:     \t" + tipo.getNombre());
            System.out.println("  - descripcion:\t" + tipo.getDescripcion());
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

    	String DEFAULT_PROPERTIES_FILE = "conf/tiposDocVitalesClient.properties";

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

    	DEFAULT_PROPERTIES_FILE = "/conf/tiposDocVitalesClient.properties";
    	if (is==null){
    		try {
    			is = TiposDocVitalesClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);

    		} catch (Exception e) {
    			System.out.println("Error al obtener el fichero de propiedades con: DocVitalesClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);");
    		}
    	}

    	Properties props = new Properties();
    	props.load(is);
    	return props; 
    }
    
}
