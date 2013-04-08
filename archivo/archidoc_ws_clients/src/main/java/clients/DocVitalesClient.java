package clients;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;

import org.apache.axis.client.Stub;

import ws.docvitales.WSDocumentosVitales;
import ws.docvitales.WSDocumentosVitalesServiceLocator;

import common.constants.Constants;

import docvitales.webservice.vos.DocumentoAntecedente;
import docvitales.webservice.vos.DocumentoVital;
import docvitales.webservice.vos.FormDocumentoVital;
import docvitales.webservice.vos.InfoBDocumentoVital;
import docvitales.webservice.vos.Serie;

/**
 * Cliente de pruebas del servicio web de documentos vitales
 */
public class DocVitalesClient
{
    
    private static final SimpleDateFormat SDF_YYYYMMDD 	= new SimpleDateFormat("yyyy-MM-dd"); 
    
    public static void main(String args[])
    {
        try
        {
            Properties props = getProperties();

            String indPedirDatos = props.getProperty("docVit.indPedirDatos");
            boolean pedirDatos = false;
            pedirDatos = (indPedirDatos!=null) && (indPedirDatos.equals("S"));
            
			WSDocumentosVitalesServiceLocator locator = new WSDocumentosVitalesServiceLocator();
			
			// Establecer la URL del servicio Web
			WSDocumentosVitales remote = null;
            String endpoint = props.getProperty("docVit.url");
            if ((endpoint != null) && (!endpoint.equals("")))
            {
            	URL url = new URL(endpoint);
            	remote = locator.getWSDocumentosVitales(url);                
                System.out.println("SOAP URL actualizada: " + endpoint);
            } else {
            	remote = locator.getWSDocumentosVitales();
            }
			
			Stub axisPort = (Stub) remote;
			System.out.println("Proxy cargado");

            // Establecer el usuario, la contraseña y la entidad
            String user = props.getProperty("docVit.autenticacion.usuario"); 
            String password = props.getProperty("docVit.autenticacion.clave");		
            String entity = props.getProperty("docVit.autenticacion.entidad");
            
            if ((user != null) && (!user.equals(""))){
            	axisPort.setHeader("http://my.name.space/headers",Stub.USERNAME_PROPERTY, user);
            }
            if ((password != null) && (!password.equals(""))){
    			axisPort.setHeader("http://my.name.space/headers",Stub.PASSWORD_PROPERTY, password);
            }
            if ((entity != null) && (!entity.equals(""))) {
            	axisPort.setHeader("http://my.name.space/headers",Constants.ENTITY_PROPERTY, entity);
            }

            String metodo = "";
            while (!metodo.equals("11")) {
	            System.out.println("Lista de metodos");
	            System.out.println("----------------------------");
	            System.out.println("1. getSeriesTercero");
	            System.out.println("2. getDocumentosAntecedentesTercero");	            
	            System.out.println("3. getDocumentosAntecedentes");
	            System.out.println("4. getISADG");
	            System.out.println("5. getDocumentosVitalesTercero");
	            System.out.println("6. getDocumentosVitalesTerProc");
	            System.out.println("7. getDocumentoVital");
	            System.out.println("8. altaDocumentoVital");
	            System.out.println("9. vinculaExpediente");
	            System.out.println("10. eliminaVinculosExpediente");
	            System.out.println("----------------------------");
	            System.out.println("11. Salir");
	            System.out.println("----------------------------");
	            System.out.println();
	            System.out.print("Opcion: ");
	            metodo = readLine();
	            
	            try {
		            if ((metodo == null) || (metodo.equals(""))) {
		            	System.out.println("Error: Metodo incorrecto");
		            } else {
		            	if (metodo.trim().equals("1")){
		            		String idTercero = props.getProperty("docVit.getSeriesTercero.idTercero") == null ? "" : props.getProperty("docVit.getSeriesTercero.idTercero");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;
		            		}
		            		
		            		System.out.println("Buscando las series con documentos antecedentes: idTercero = " + idTercero);
		                    debug(remote.getSeriesTercero(idTercero));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("2")){
		            		String idTercero = props.getProperty("docVit.getDocumentosAntecedentesTercero.idTercero") == null ? "" : props.getProperty("docVit.getDocumentosAntecedentesTercero.idTercero");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;
		            		}
		            		
		            		System.out.println("Buscando los documentos antecedentes: idTercero = " + idTercero);
		                    debug(remote.getDocumentosAntecedentesTercero(idTercero));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("3")){
		            		String idTercero = props.getProperty("docVit.getDocumentosAntecedentes.idTercero") == null ? "" : props.getProperty("docVit.getDocumentosAntecedentes.idTercero");
		            		String idSerie = props.getProperty("docVit.getDocumentosAntecedentes.idSerie") == null ? "" : props.getProperty("docVit.getDocumentosAntecedentes.idSerie");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;
			            		System.out.print("Introduzca idSerie [" + idSerie + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idSerie= line;
		            		}
		            		
		            		System.out.println("Buscando los documentos antecedentes: idTercero = " + idTercero + ", idSerie = " + idSerie);
		                    debug(remote.getDocumentosAntecedentes(idTercero, idSerie));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	}  else if (metodo.trim().equals("4")){
		            		String idDocAnt = props.getProperty("docVit.getISADG.idDocAnt") == null ? "" : props.getProperty("docVit.getISADG.idDocAnt");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idDocAnt [" + idDocAnt + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idDocAnt = line;
		            		}
		            		
		            		System.out.println("Buscando la ISAD(G): idDocAnt = " + idDocAnt);
		                    System.out.println("ISAD(G):\n");
		                    debug(getISADGWrapper(idDocAnt, remote));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("5")){
		            		String idTercero = props.getProperty("docVit.getDocumentosVitalesTercero.idTercero") == null ? "" : props.getProperty("docVit.getDocumentosVitalesTercero.idTercero");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;
		            		}
		            		
		            		System.out.println("Buscando los documentos vitales: idTercero = " + idTercero);
		                    debug(remote.getDocumentosVitalesTercero(idTercero));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("6")){
		            		String idTercero = props.getProperty("docVit.getDocumentosVitalesTerProc.idTercero") == null ? "" : props.getProperty("docVit.getDocumentosVitalesTerProc.idTercero");
		            		String idProc = props.getProperty("docVit.getDocumentosVitalesTerProc.idProc") == null ? "" : props.getProperty("docVit.getDocumentosVitalesTerProc.idProc");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;
			            		System.out.print("Introduzca idProc [" + idProc + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idProc = line;
		            		}
		            		
		                    System.out.println("Buscando los documentos vitales: idTercero = " + idTercero + ", idProc = " + idProc);
		                    debug(remote.getDocumentosVitalesTerProc(idTercero, idProc));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("7")){
		            		String idDocVit = props.getProperty("docVit.getDocumentoVital.idDocVit") == null ? "" : props.getProperty("docVit.getDocumentoVital.idDocVit");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idDocVit [" + idDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idDocVit = line;
		            		}
		            		
		            		System.out.println("Buscando el documento vital: idDocVit = " + idDocVit);
		                    debug(remote.getDocumentoVital(idDocVit));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("8")){
		            		String fechaCaducidad = props.getProperty("docVit.altaDocumentoVital.fechaCaducidad") == null ? "" : props.getProperty("docVit.altaDocumentoVital.fechaCaducidad");
		            		String fichero = props.getProperty("docVit.altaDocumentoVital.fichero") == null ? "" : props.getProperty("docVit.altaDocumentoVital.fichero");	            		
		            		String idTercero = props.getProperty("docVit.altaDocumentoVital.idTercero") == null ? "" : props.getProperty("docVit.altaDocumentoVital.idTercero");
		            		String idTipoDocVit = props.getProperty("docVit.altaDocumentoVital.idTipoDocVit") == null ? "" : props.getProperty("docVit.altaDocumentoVital.idTipoDocVit");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca fechaCaducidad [" + fechaCaducidad + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			fechaCaducidad = line;
			            		System.out.print("Introduzca fichero [" + fichero + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			fichero = line;		            		
			            		System.out.print("Introduzca idTercero [" + idTercero + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTercero = line;		
			            		System.out.print("Introduzca idTipoDocVit [" + idTipoDocVit + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idTipoDocVit = line;
		            		}
		            		
		            		FormDocumentoVital form = new FormDocumentoVital();
		            		Calendar calendar = new GregorianCalendar();
		            		calendar.setTime(SDF_YYYYMMDD.parse(fechaCaducidad));
		                    form.setFechaCaducidad(calendar);
		                    form.setIdTercero(idTercero);
		                    form.setIdTipoDocVit(idTipoDocVit);
		                    
		                    attachFile(form, fichero);
		                    
		                    System.out.println("Creando documento vital...");
		                    debug(altaDocumentoVitalWrapper(form,remote));
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("9")){
		            		String idDocVit = props.getProperty("docVit.vinculaExpediente.idDocVit") == null ? "" : props.getProperty("docVit.vinculaExpediente.idDocVit");
		            		String idExp = props.getProperty("docVit.vinculaExpediente.idExp") == null ? "" : props.getProperty("docVit.vinculaExpediente.idExp");	            		
		            		String idSist = props.getProperty("docVit.vinculaExpediente.idSist") == null ? "" : props.getProperty("docVit.vinculaExpediente.idSist");
		            		String usuario = props.getProperty("docVit.vinculaExpediente.usuario") == null ? "" : props.getProperty("docVit.vinculaExpediente.usuario");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idDocVit [" + idDocVit + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idDocVit = line;
			            		System.out.print("Introduzca idExp [" + idExp + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idExp = line;		            		
			            		System.out.print("Introduzca idSist [" + idSist + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idSist = line;		
			            		System.out.print("Introduzca usuario [" + usuario + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			usuario = line;
		            		}
		            		
		                    System.out.println("Vinculando el documento vital: idDocVit = " + idDocVit 
		                            + ", idExp = " + idExp
		                            + ", idSist = " + idSist
		                            + ", usuario = " + usuario);
		                    
		                    remote.vinculaExpediente(idDocVit, idExp, idSist, usuario);
		                    System.out.println("Expediente vinculado...");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (metodo.trim().equals("10")){
		            		String idExp = props.getProperty("docVit.eliminaVinculosExpediente.idExp") == null ? "" : props.getProperty("docVit.eliminaVinculosExpediente.idExp");	            		
		            		String idSist = props.getProperty("docVit.eliminaVinculosExpediente.idSist") == null ? "" : props.getProperty("docVit.eliminaVinculosExpediente.idSist");
		            		
		            		if (pedirDatos) {
			            		System.out.print("Introduzca idExp [" + idExp + "]: ");
			            		String line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idExp = line;		            		
			            		System.out.print("Introduzca idSist [" + idSist + "]: ");
			            		line = readLine();
			            		if ((line!=null) && (!line.equals("")))
			            			idSist = line;		
		            		}
		            		
		                    System.out.println("Eliminando vinculos del expediente: idExp = " + idExp
		                            + ", idSist = " + idSist);
		                    
		                    remote.eliminaVinculosExpediente(idExp, idSist);
		                    System.out.println("Vinculos eliminados...");
		                    System.out.println();
		                    System.out.println("Presione INTRO para continuar");
		                    readLine();
		            	} else if (!metodo.equals("11")) {
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
    
    private static InfoBDocumentoVital  altaDocumentoVitalWrapper (FormDocumentoVital form, WSDocumentosVitales remote)
    {
    	InfoBDocumentoVital infoBDocumentoVital = null;
    	
    	try
    	{
    		infoBDocumentoVital = remote.altaDocumentoVital(form);
    	}
    	catch(Exception ex)
    	{}
    	
    	return infoBDocumentoVital;
    }
    
    private static String getISADGWrapper(String idDocAnt, WSDocumentosVitales remote)
    {
    	String ret = null;
    	
    	try
    	{
    		ret = remote.getISADG(idDocAnt);
    	}
    	catch (Exception ex)
    	{}
    	
    	return ret;
    }
    
    protected static void debug(Serie [] series)
    {
        if (series != null)
        {
            System.out.println("Nº de series: " + series.length);
            for (int i = 0; i < series.length; i++)
            {
                System.out.println("- Serie #" + (i+1));
                debug(series[i]);
            }
        }
        else
            System.out.println("No se ha encontrado ninguna serie.");
    }

    protected static void debug(Serie serie)
    {
        if (serie != null)
        {
            System.out.println("  - descEstado:  \t\t" + serie.getDescEstado());
            System.out.println("  - estado:      \t\t" + serie.getEstado());
            System.out.println("  - fondo:       \t\t" + serie.getFondo());
            System.out.println("  - id:          \t\t" + serie.getId());
            System.out.println("  - numUdocs:    \t\t" + serie.getNumUdocs());
            System.out.println("  - titulo:      \t\t" + serie.getTitulo());
        }
    }

    protected static void debug(InfoBDocumentoVital [] docs)
    {
        if (docs != null)
        {
            System.out.println("Nº de documentos vitales: " + docs.length);
            for (int i = 0; i < docs.length; i++)
            {
                System.out.println("- Documento #" + (i+1));
                debug(docs[i]);
            }
        }
        else
            System.out.println("No se ha encontrado ningun documento vital.");
    }

    protected static void debug(InfoBDocumentoVital doc)
    {
        if (doc != null)
        {
            System.out.println("  - estadoDocVit:     \t\t" + doc.getEstadoDocVit());
            System.out.println("  - extFich:          \t\t" + doc.getExtFich());
            System.out.println("  - fechaCad:         \t\t" + doc.getFechaCad());
            System.out.println("  - id:               \t\t" + doc.getId());
            System.out.println("  - idBdTerceros:     \t\t" + doc.getIdBdTerceros());
            System.out.println("  - idFich:           \t\t" + doc.getIdFich());
            System.out.println("  - idTipoDocVit:     \t\t" + doc.getIdTipoDocVit());
            System.out.println("  - identidad:        \t\t" + doc.getIdentidad());
            System.out.println("  - nombreOrgFich:    \t\t" + doc.getNombreOrgFich());
            System.out.println("  - nombreTipoDocVit: \t\t" + doc.getNombreTipoDocVit());
            System.out.println("  - numIdentidad:     \t\t" + doc.getNumIdentidad());
            System.out.println("  - tamanoFich:       \t\t" + doc.getTamanoFich());
        }
        else
        	System.out.println("No se ha podido dar de alta el documento vital.");
    }
    
    protected static void debug(DocumentoAntecedente [] docs)
    {
        if (docs != null)
        {
            System.out.println("Nº de documentos antecedentes: " + docs.length);
            for (int i = 0; i < docs.length; i++)
            {
                System.out.println("- Documento #" + (i+1));
                debug(docs[i]);
            }
        }
        else
            System.out.println("No se ha encontrado ningun documento antecedente.");
    }

    protected static void debug(DocumentoAntecedente doc)
    {
        if (doc != null)
        {
            System.out.println("  - codReferencia:    \t\t" + doc.getCodReferencia());
            System.out.println("  - codSistProductor: \t\t" + doc.getCodSistProductor());
            System.out.println("  - descEstado:       \t\t" + doc.getDescEstado());
            System.out.println("  - estado:           \t\t" + doc.getEstado());
            System.out.println("  - fechaFinal:       \t\t" + doc.getFechaFinal());
            System.out.println("  - fechaInicial:     \t\t" + doc.getFechaInicial());
            System.out.println("  - fondo:            \t\t" + doc.getFondo());
            System.out.println("  - id:               \t\t" + doc.getId());
            System.out.println("  - numExp:           \t\t" + doc.getNumExp());
            System.out.println("  - serie:            \t\t" + doc.getSerie());
            System.out.println("  - tipoDocumental:   \t\t" + doc.getTipoDocumental());
            System.out.println("  - titulo:           \t\t" + doc.getTitulo());
        }
    }

    protected static void debug(DocumentoVital doc)
    {
        if (doc != null)
        {
            System.out.println("  - contenido:\t\t" + (doc.getContenido() != null ? "[" + doc.getContenido().length + " bytes]" : null));
            System.out.println("  - estadoDocVit:\t\t" + doc.getEstadoDocVit());
            System.out.println("  - extFich:\t\t" + doc.getExtFich());
            System.out.println("  - fechaCad:\t\t" + doc.getFechaCad());
            System.out.println("  - id:\t\t\t" + doc.getId());
            System.out.println("  - idBdTerceros:\t\t" + doc.getIdBdTerceros());
            System.out.println("  - idFich:\t\t\t" + doc.getIdFich());
            System.out.println("  - idTipoDocVit:\t\t" + doc.getIdTipoDocVit());
            System.out.println("  - identidad:\t\t" + doc.getIdentidad());
            System.out.println("  - nombreOrgFich:\t\t" + doc.getNombreOrgFich());
            System.out.println("  - nombreTipoDocVit:\t" + doc.getNombreTipoDocVit());
            System.out.println("  - numIdentidad:\t\t" + doc.getNumIdentidad());
            System.out.println("  - tamanoFich:\t\t" + doc.getTamanoFich());
        }
    }
    
    
    protected static void debug(String ficha)
    {
        if (ficha != null)
            System.out.println(ficha);           
        else
            System.out.println("No se ha encontrado ninguna ficha ISAD(G) asociada al elemento.");
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

    	String DEFAULT_PROPERTIES_FILE = "conf/docVitalesClient.properties";

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

    	DEFAULT_PROPERTIES_FILE = "/conf/docVitalesClient.properties";
    	if (is==null){
    		try {
    			is = DocVitalesClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);

    		} catch (Exception e) {
    			System.out.println("Error al obtener el fichero de propiedades con: DocVitalesClient.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE);");
    		}
    	}

    	Properties props = new Properties();
    	props.load(is);
    	return props; 
    }
    
    protected static void attachFile(FormDocumentoVital form,
            String filename) throws Exception
    {
        if (form != null)
        {
            File file = new File(filename);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			
			byte [] buffer = new byte[1024];
			int i = fis.read(buffer, 0, buffer.length);
			while (i > -1)
			{
				baos.write(buffer, 0, i);
				i = fis.read(buffer, 0, buffer.length);
			}
			
			fis.close();
			baos.flush();
			baos.close();
			
			byte [] contenido = baos.toByteArray();
/*			Byte [] byteA = new Byte[contenido.length];
			for (int j = 0; j < contenido.length; j++)
			    byteA[j] = new Byte(contenido[j]);*/
            form.setContenidoFich(contenido);
            
            String nombreCompletoFichero = file.getName();
            int ix = nombreCompletoFichero.lastIndexOf(".");
            if (ix > 0)
            {
                form.setExtFich(nombreCompletoFichero.substring(ix+1));
                form.setNombreFich(nombreCompletoFichero.substring(0, ix));
            }
            else
                form.setNombreFich(nombreCompletoFichero);
        }
    }
}
