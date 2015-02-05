package ieci.tecdoc.sgm.registro;

import ieci.tecdoc.sgm.autenticacion.FirmaManager;
import ieci.tecdoc.sgm.autenticacion.SesionManager;
import ieci.tecdoc.sgm.autenticacion.util.Solicitante;
import ieci.tecdoc.sgm.autenticacion.util.TipoSolicitante;
import ieci.tecdoc.sgm.autenticacion.util.utilities.Validador;
import ieci.tecdoc.sgm.autenticacion.vo.ReceiptVO;
import ieci.tecdoc.sgm.base.datetime.DatePattern;
import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DynamicRow;
import ieci.tecdoc.sgm.base.file.FileManager;
import ieci.tecdoc.sgm.base.miscelanea.Goodies;
import ieci.tecdoc.sgm.base.xml.core.XmlDocument;
import ieci.tecdoc.sgm.base.xml.core.XmlElement;
import ieci.tecdoc.sgm.base.xml.core.XmlElements;
import ieci.tecdoc.sgm.base.xml.lite.XmlTextBuilder;
import ieci.tecdoc.sgm.core.config.impl.spring.MultiEntityContextHolder;
import ieci.tecdoc.sgm.core.services.LocalizadorServicios;
import ieci.tecdoc.sgm.core.services.antivirus.AntivirusException;
import ieci.tecdoc.sgm.core.services.antivirus.ServicioAntivirus;
import ieci.tecdoc.sgm.core.services.calendario.RetornoCalendario;
import ieci.tecdoc.sgm.core.services.calendario.ServicioCalendario;
import ieci.tecdoc.sgm.core.services.catalogo.DocumentoExtendido;
import ieci.tecdoc.sgm.core.services.catalogo.Documentos;
import ieci.tecdoc.sgm.core.services.catalogo.ServicioCatalogoTramites;
import ieci.tecdoc.sgm.core.services.catalogo.Tramite;
import ieci.tecdoc.sgm.core.services.dto.Entidad;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumento;
import ieci.tecdoc.sgm.core.services.repositorio.ContenedorDocumentos;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoInfo;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentoTemporal;
import ieci.tecdoc.sgm.core.services.repositorio.DocumentosTemporales;
import ieci.tecdoc.sgm.core.services.repositorio.ServicioRepositorioDocumentosTramitacion;
import ieci.tecdoc.sgm.core.services.sesion.InfoUsuario;
import ieci.tecdoc.sgm.core.services.sesion.ServicioSesionUsuario;
import ieci.tecdoc.sgm.core.services.telematico.RegistroTelematicoException;
import ieci.tecdoc.sgm.core.services.tiempos.ServicioTiempos;
import ieci.tecdoc.sgm.core.services.tramitacion.ServicioTramitacion;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DatosComunesExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.DocumentoExpediente;
import ieci.tecdoc.sgm.core.services.tramitacion.dto.InteresadoExpediente;
import ieci.tecdoc.sgm.rde.database.util.Utilities;
import ieci.tecdoc.sgm.registro.exception.RegistroCodigosError;
import ieci.tecdoc.sgm.registro.exception.RegistroExcepcion;
import ieci.tecdoc.sgm.registro.util.Definiciones;
import ieci.tecdoc.sgm.registro.util.PeticionDocumento;
import ieci.tecdoc.sgm.registro.util.PeticionDocumentos;
import ieci.tecdoc.sgm.registro.util.Registro;
import ieci.tecdoc.sgm.registro.util.RegistroConsulta;
import ieci.tecdoc.sgm.registro.util.RegistroDocumento;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSV;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentoCSVImpl;
import ieci.tecdoc.sgm.registro.util.RegistroDocumentos;
import ieci.tecdoc.sgm.registro.util.RegistroEstado;
import ieci.tecdoc.sgm.registro.util.RegistroImpl;
import ieci.tecdoc.sgm.registro.util.RegistroPeticion;
import ieci.tecdoc.sgm.registro.util.Registros;
import ieci.tecdoc.sgm.registro.util.database.DBSessionManager;
import ieci.tecdoc.sgm.registro.util.database.RegistroDatos;
import ieci.tecdoc.sgm.registro.util.database.RegistroDocumentoCSVDatos;
import ieci.tecdoc.sgm.registro.util.database.RegistroDocumentoDatos;
import ieci.tecdoc.sgm.registro.util.database.RegistroSecuenciaDatos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import es.ieci.tecdoc.fwktd.util.file.FileUtils;

/**
 * Clase que proporciona la interfaz de funcionalidad de registro.
 *
 * @author IECISA
 *
 */
public class RegistroManager {

	private static final Logger logger = Logger.getLogger(RegistroManager.class);

	public RegistroManager() {

	}

   /**
    * Crea, a partir de los datos aportados por el ciudadano, la solicitud de
    * registro (en formato XML) que debe ser firmada.
    *
    * @param sessionId Identificador de la sesión en curso.
    * @param requestInfo Información con los datos aportados por el ciudadano.
    * @param idiom Idioma (catalán o castellano).
    * @param numeroExpediente Número del expediente asociado
    * @return La solicitud de registro.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static byte[] createRegistryRequest(String sessionId,
      RegistroPeticion requestInfo, String idiom, String organismo,
      String numeroExpediente, String entidad) throws RegistroExcepcion {
      byte[] request = null;
      XmlTextBuilder bdr;
      Tramite procedure;

      try {

    	  logger.debug("Creando solicitud de registro  asociada a la sesion [" + sessionId + "] en la entidad [" + entidad + "]");

    	  ServicioCatalogoTramites oServicio = LocalizadorServicios.getServicioCatalogoTramites();
    	  procedure = oServicio.getProcedure(requestInfo.getProcedureId(), true, getEntidad(entidad));
    	  bdr = new XmlTextBuilder();
    	  bdr.setStandardHeader();
    	  bdr.addOpeningTag(Definiciones.REQUEST_HEADER);
    	  bdr.addSimpleElement(Definiciones.VERSION, Definiciones.VERSION_NUMBER);
    	  setRegistryData(bdr);
    	  setSignedData(procedure, sessionId, requestInfo, bdr, idiom, organismo, numeroExpediente, entidad);
    	  bdr.addSimpleElement(Definiciones.SIGNATURE, "");
    	  bdr.addClosingTag(Definiciones.REQUEST_HEADER);
    	  request = Goodies.fromStrToUTF8(bdr.getText());

    	  logger.debug("Solicitud de registro  creada asociada a la sesion [" + sessionId + "] en la entidad [" + entidad + "]");

      } catch (RegistroExcepcion exc) {
    	  logger.error("Error al crear petición de registro [createRegistryRequest][RegistroException]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al crear petición de registro [createRegistryRequest][Exception]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
      } finally {
         try
         {
            //Log.setRegistryRequest(sessionId, result, request);
         }
         catch (Exception exc)
         {
           throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
         }
      }

      return request;
   }

   /**
    * Añade al xml de solicitud de registro la parte correspondiente a los datos
    * de registro (vacíos, pues se rellenarán al efectuar el registro).
    *
    * @param bdr  Generador del documento xml.
    * @throws RegistroExcepcion  Si se produce algún error.
    */
   private static void setRegistryData(XmlTextBuilder bdr)
   throws RegistroExcepcion {
      try {
         bdr.addOpeningTag(Definiciones.REGISTRY_DATA);
         bdr.addSimpleElement(Definiciones.REGISTRY_NUMBER, "");
         bdr.addSimpleElement(Definiciones.REGISTRY_DATE, "");
         bdr.addSimpleElement(Definiciones.REGISTRY_HOUR, "");
         bdr.addSimpleElement(Definiciones.REGISTRY_EFFECTIVE_DATE, "");
         bdr.addSimpleElement(Definiciones.REGISTRY_EFFECTIVE_HOUR, "");
         bdr.addClosingTag(Definiciones.REGISTRY_DATA);
      } catch (Exception e) {
    	  logger.error("Error al establecer datos de registro [setRegistryData][Exception]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
      }
   }


   /**
    * Añade al xml de solicitud de registro la parte correspondiente a los datos
    * firmados.
    *
    * @param procedure Información del trámite.
    * @param sessionId Identificador de la sesión.
    * @param requestInfo Información de la solicitud de registro.
    * @param bdr Generador del documento xml.
    * @param idiom Idioma.
    * @param numeroExpediente Número del expediente asociado
    * @throws RegistroExcepcion Si se produce algún error.
    */
   private static void setSignedData(Tramite procedure, String sessionId,
           RegistroPeticion requestInfo, XmlTextBuilder bdr, String idiom,
           String organismo, String numeroExpediente, String entidad)
           throws RegistroExcepcion {
      try {
         bdr.addOpeningTag(Definiciones.SIGNED_DATA);
         setGenericData(procedure, sessionId, requestInfo, bdr, idiom, organismo, numeroExpediente, entidad);
         setSpecificData(requestInfo, bdr);
         manageDocuments(sessionId, procedure.getDocuments(), requestInfo.getDocuments(),
                 procedure.getLimitDocs(), bdr, entidad);
         bdr.addClosingTag(Definiciones.SIGNED_DATA);
      } catch (RegistroExcepcion exc) {
    	  logger.error("Error al establecer datos firmados de registro [setSignedData][RegistroExcepcion]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al establecer datos firmados de registro [setSignedData][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
      }
   }

   /**
    * Añade al xml de solicitud de registro la parte correspondiente a los datos
    * genéricos.
    *
    * @param procedure  Datos del trámite.
    * @param sessionId Identificador de la sesión.
    * @param requestInfo Información de la solicitud de registro.
    * @param bdr Generador del documento xml.
    * @param idiom Idioma.
    * @param numeroExpediente Número del expediente asociado
    * @throws RegistroExcepcion Si se produce algún error.
    */
   private static void setGenericData(Tramite procedure, String sessionId,
           RegistroPeticion requestInfo, XmlTextBuilder bdr, String idiom,
           String organismo, String numeroExpediente, String entidad)
           throws RegistroExcepcion {
      Solicitante sender;

      try {
         bdr.addOpeningTag(Definiciones.GENERIC_DATA);
         bdr.addSimpleElement(Definiciones.ORG, organismo, true);
         bdr.addSimpleElement(Definiciones.IDIOM, idiom);
         sender = SesionManager.getSender(sessionId, entidad);
         bdr.addOpeningTag(Definiciones.SENDER);

         // Persona fisica o juridica
         if (sender.getInQuality().equals(String.valueOf(TipoSolicitante.INDIVIDUAL))) {

        	 // Persona fisica (individual)
        	 // Nombre completo
	         bdr.addSimpleElement(Definiciones.SENDER_NAME, sender.getName(), true);
	         // Nombre y apellidos segregados
	         if (StringUtils.isNotBlank(sender.getSurName())) {
	        	 bdr.addOpeningTag(Definiciones.SENDER_NAME_SURNAMES);
	        	 bdr.addSimpleElement(Definiciones.SENDER_NAME, sender.getFirstName(), true);
	        	 bdr.addSimpleElement(Definiciones.SENDER_SURNAME, sender.getSurName(), true);
	        	 bdr.addSimpleElement(Definiciones.SENDER_SURNAME2, sender.getSurName2(), true);
	        	 bdr.addClosingTag(Definiciones.SENDER_NAME_SURNAMES);
	         }
	         bdr.addOpeningTag(Definiciones.ID);
	         bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(sender.getId()));//requestInfo.getSenderIdType());
	         bdr.addSimpleElement(Definiciones.SENDER_ID, sender.getId());
	         bdr.addClosingTag(Definiciones.ID);
         }
         else {
        	// Persona juridica (representante legal)
	         bdr.addSimpleElement(Definiciones.SENDER_NAME, sender.getSocialName(), true);
	         bdr.addOpeningTag(Definiciones.ID);
	         bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(sender.getCIF()));//requestInfo.getSenderIdType());
	         bdr.addSimpleElement(Definiciones.SENDER_ID, sender.getCIF());
	         bdr.addClosingTag(Definiciones.ID);
         }

         bdr.addSimpleElement(Definiciones.SENDER_EMAIL, requestInfo.getEmail(), true);
         bdr.addClosingTag(Definiciones.SENDER);

         // Representante legal de la persona juridica
         if (sender.getInQuality().equals(String.valueOf(TipoSolicitante.LEGAL_REPRESENTATIVE))) {

         	bdr.addOpeningTag(Definiciones.LEGAL_REPRESENTATIVE);
         	// Nombre completo
 	        bdr.addSimpleElement(Definiciones.SENDER_NAME, sender.getName(), true);
 	        // Nombre y apellidos segregados
 	        if (StringUtils.isNotBlank(sender.getSurName())) {
 	        	bdr.addOpeningTag(Definiciones.SENDER_NAME_SURNAMES);
 	        	bdr.addSimpleElement(Definiciones.SENDER_NAME, sender.getFirstName(), true);
 	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME, sender.getSurName(), true);
 	        	bdr.addSimpleElement(Definiciones.SENDER_SURNAME2, sender.getSurName2(), true);
 	        	bdr.addClosingTag(Definiciones.SENDER_NAME_SURNAMES);
 	        }
 	        bdr.addOpeningTag(Definiciones.ID);
 	        bdr.addSimpleElement(Definiciones.SENDER_ID_TYPE, ""+Validador.validateDocumentType(sender.getId()));
 	        bdr.addSimpleElement(Definiciones.SENDER_ID, sender.getId());
 	        bdr.addClosingTag(Definiciones.ID);
 	        bdr.addClosingTag(Definiciones.LEGAL_REPRESENTATIVE);
         }

         bdr.addOpeningTag(Definiciones.TOPIC);
         bdr.addSimpleElement(Definiciones.CODE, procedure.getTopic());
         bdr.addSimpleElement(Definiciones.DESCRIPTION, procedure.getDescription(), true);
         bdr.addClosingTag(Definiciones.TOPIC);
         bdr.addOpeningTag(Definiciones.ADDRESSEE);
         if (requestInfo.getAddressee() == null) {
            bdr.addSimpleElement(Definiciones.CODE, procedure.getAddressee());
         } else {
            bdr.addSimpleElement(Definiciones.CODE, requestInfo.getAddressee());
         }
         bdr.addClosingTag(Definiciones.ADDRESSEE);
         bdr.addSimpleElement(Definiciones.FOLDER_ID, numeroExpediente);
         bdr.addClosingTag(Definiciones.GENERIC_DATA);
      } catch (Exception e) {
    	  logger.error("Error al establecer datos genericos  de registro [setGenericData][Exception]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
      }
   }


   /**
    * Añade al xml de solicitud de registro la parte correspondiente a los datos
    * específicos.
    *
    * @param requestInfo Información de la solicitud de registro.
    * @param bdr Generador del documento xml.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   private static void setSpecificData(RegistroPeticion requestInfo, XmlTextBuilder bdr) throws RegistroExcepcion {
	   try {
    	  	bdr.addOpeningTag(Definiciones.SPECIFIC_DATA);
         	bdr.addFragment(requestInfo.getSpecificData());
         	bdr.addClosingTag(Definiciones.SPECIFIC_DATA);
	   } catch (Exception e) {
    	  logger.error("Error al establecer datos especificos  de registro [setSpecificData][Exception]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_CREATE_REGISTRY_REQUEST);
	   }
   }


   public static String setDocumentosSubsanacion(String sessionId, Documentos procedureDocuments,
		   PeticionDocumentos requestDocuments, String entidad)
   throws RegistroExcepcion {
	   try{
		   XmlTextBuilder bdr = new XmlTextBuilder();
		   manageDocuments(sessionId, procedureDocuments, requestDocuments, false, bdr, entidad);
		   return bdr.getText();
	   }catch (RegistroExcepcion exc) {
		   logger.error("Error al establecer documentos de subsanacion [setDocumentosSubsanacion][RegistroExcepcion]", exc.fillInStackTrace());
		   throw exc;
	   } catch (Exception e) {
		   logger.error("Error al establecer documentos de subsanacion [setDocumentosSubsanacion][Exception]", e.fillInStackTrace());
		   throw new RegistroExcepcion(RegistroCodigosError.EC_MANAGE_DOCUMENTS);
	   }
   }


   /**
    * Gestiona todas las operaciones relacionadas con los documentos:
    * restricciones, inserción de información en la solicitud de registro,
    * firmas y antivirus.
    *
    * @param sessionId Identificador de sesión.
    * @param procedureDocuments Lista de documentos definidos para un trámite.
    * @param requestDocuments Lista de documentos presentados por el remitente.
    * @param limit Si se limitan los documentos a los establecidos en el catálogo
    *           (true) o se permiten anexos que no hay que comprobar (false).
    * @param bdr Generador del documento xml.
    * @throws RegistroExcepcion Si se produce algún error o los documentos
    *           no cumplen alguno de los requisitos de entrada.
    */
   private static void manageDocuments(String sessionId, Documentos procedureDocuments,
           PeticionDocumentos requestDocuments, boolean limit, XmlTextBuilder bdr, String entidad)
           throws RegistroExcepcion {
      boolean clean = false;
      PeticionDocumento requestDocument;
      DocumentoExtendido procedureDocument;
      int i, j, count, size;
      String code, documentDesc;
      byte[] data;
      FileInputStream fileStream;

      try {
    	  ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
    	  checkConstrainsts(procedureDocuments, requestDocuments, limit);

    	  try {
    		  oServicio.deleteDocumentTmp(sessionId, getEntidad(entidad));
    	  } catch(Exception e) {
    		  logger.debug("No hay ficheros asociados al id de sesion " + sessionId + " [manageDocuments][RegistroExcepcion]");
    	  }

         if (requestDocuments != null)
        	 count = requestDocuments.count();
         else count = 0;
         if (count > 0)
         {
             size = procedureDocuments.count();
             bdr.addOpeningTag(Definiciones.DOCUMENTS);
             for (j = 0; j < count; j++) {
                requestDocument = requestDocuments.get(j);
                data = requestDocument.getFileContent();
                //Si no nos pasaron el contenido directamente, cargamos el fichero desde su ruta
                if(data==null)
                {
	                fileStream = new FileInputStream(requestDocument.getLocation());
	                data = Goodies.getBytes(fileStream);
	                fileStream.close();
                }
                code = requestDocument.getCode();
                documentDesc = "";
                procedureDocument = null;
                for (i = 0; i < size; i++) {
                   procedureDocument = procedureDocuments.get(i);
                   if (procedureDocument.getCode().compareTo(code) == 0) {
                      documentDesc = procedureDocument.getDescription();
                      break;
                   }
                }

                /*
                 * Esto se realiza cuando hay varios documentos anexados pertenecientes a un tipo:
                 * Ej: TRAM1D1, TRAM1D1_A, TRAM1D1_B.
                 * Los tres documentos son del tipo TRAM1D1 y el trámite está definido como "No" en la opción "límite de documentos"
                */
                if ("".equals(documentDesc)) {
                	int index_ = code.lastIndexOf("_");
                	if (index_ != -1) {
                		String codeAux = code.substring(0, index_);
                		for (i = 0; i < size; i++) {
                            procedureDocument = procedureDocuments.get(i);
                            if (procedureDocument.getCode().compareTo(codeAux) == 0) {
                               documentDesc = procedureDocument.getDescription();
                               break;
                            }
                         }
                	}
                }

                boolean resultado = true;
                boolean validado = true;
                try {
                	resultado = checkVirus(data);
                } catch (Exception e) {
                	validado = false;
                }

                bdr.addOpeningTag(Definiciones.DOCUMENT);
                bdr.addSimpleElement(Definiciones.NAME, requestDocument.getFileName(), true);
                bdr.addSimpleElement(Definiciones.CODE, requestDocument.getCode());
                bdr.addSimpleElement(Definiciones.EXTENSION, requestDocument.getExtension());
                bdr.addSimpleElement(Definiciones.DESCRIPTION, documentDesc, true);
                bdr.addSimpleElement(Definiciones.HASH, Utilities.getHash(data));
                if (requestDocument.getIdioma() != null && !"".equals(requestDocument.getIdioma()))
                	bdr.addSimpleElement(Definiciones.IDIOM, requestDocument.getIdioma());
                bdr.addSimpleElement(Definiciones.SIGNATURE_HOOK, procedureDocument.getSignatureHook());
                bdr.addSimpleElement(Definiciones.VALIDATION_HOOK, procedureDocument.getValidationHook());
                //Si no hay antivirus disponible no se pone etiqueta en la solicitud
                if (validado)
                	bdr.addSimpleElement(Definiciones.ANTIVIRUS, (resultado) ? Definiciones.ANTIVIRUS_OK : Definiciones.ANTIVIRUS_ERROR);
                bdr.addClosingTag(Definiciones.DOCUMENT);

                if (!validado || (validado && resultado)) {
                	String aux_guid = null;
                	if(requestDocument.getFileContent()!=null)
                	{
                    	aux_guid = oServicio.storeDocument(sessionId, requestDocument.getFileContent(), requestDocument.getExtension(), getEntidad(entidad));
                	}
                	else
                	{
                		fileStream = new FileInputStream(requestDocument.getLocation());
                    	aux_guid = oServicio.storeDocument(sessionId, fileStream, requestDocument.getExtension(), getEntidad(entidad));
                    	fileStream.close();
                	}

                	oServicio.storeDocumentTmp(sessionId, aux_guid, getEntidad(entidad));

                }
                if(requestDocument.getFileContent()==null)
                {
	                File delFile = new File(requestDocument.getLocation());
	                delFile.delete();
                }
                clean = true; // Se repite porque deleteDocuments lanza excepción si no se ha almacenado previamente algún documento
             }
             bdr.addClosingTag(Definiciones.DOCUMENTS);
         }
         else
         {
            bdr.addSimpleElement(Definiciones.DOCUMENTS, "");
         }
         clean = false;
      } catch (RegistroExcepcion exc) {
    	  logger.error("Error al establecer documentacion de registro [manageDocuments][RegistroExcepcion]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al establecer documentacion de registro [manageDocuments][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_MANAGE_DOCUMENTS);
      } finally {
         if (clean) {
            try {
            	ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
            	oServicio.deleteDocumentTmp(sessionId, getEntidad(entidad));
            } catch (Exception e) {
            	logger.error("Error al eliminar documentacion temporal de registro [manageDocuments][Excepcion]", e.fillInStackTrace());
            	throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
            }
         }
      }
   }


   /**
    * Comprueba que los documentos que se han anexado al trámite cumplen los
    * requisitos de entrada: extensiones admitidas, documentos obligatorios y
    * tamaño.
    *
    * @param procedureDocuments  Lista de documentos definidos para un trámite.
    * @param requestDocuments Lista de documentos presentados por el remitente.
    * @param limit Si se limitan los documentos a los establecidos en el catálogo
    *           (true) o se permiten anexos que no hay que comprobar (false).
    * @throws RegistroExcepcion Si se produce algún error o los documentos no cumplen
    *           alguno de los requisitos de entrada.
    */
   private static void checkConstrainsts(Documentos procedureDocuments,
           PeticionDocumentos requestDocuments, boolean limit)
           throws RegistroExcepcion {

       int size, count, i, j;
       DocumentoExtendido procedureDocument = null;
       PeticionDocumento requestDocument = null;
       String code, extp, extr;
       boolean found = false;;
       HashMap checks = new HashMap();

       size = procedureDocuments.count();
       if (requestDocuments != null)
    	   count = requestDocuments.count();
       else count = 0;

       if (limit && (count > size)){
         throw new RegistroExcepcion(RegistroCodigosError.EC_MORE_DOCUMENTS);
       }

       try{
         //Comprueba si cada documento de trámite tiene un documento asociado.
         // Si no lo tiene se comprueba si ese documento es obligatorio.
         for(i=0; i<size; i++){
           procedureDocument = (DocumentoExtendido)procedureDocuments.get(i);
           code = procedureDocument.getCode();
           found = false;
           for (j = 0; j < count; j++) {
             requestDocument = requestDocuments.get(j);
             if (requestDocument.getCode().compareTo(code) == 0) {
               checks.put(""+j, "found");
               found = true;
               break;
             }
           }

           if(found) {
             extp = procedureDocument.getExtension();
             String[] extensiones = obtenerExtensiones(extp);
             extr = requestDocument.getExtension();

             boolean coincide = false;
             for(int w = 0; w < extensiones.length; w++){
            	 if (extensiones[w].equalsIgnoreCase(extr))
            		 coincide = true;
             }
             if (!coincide) {
                 found = false;
                 throw new RegistroExcepcion(RegistroCodigosError.EC_EXTENSION);
             }
           } else {
             if (procedureDocument.isMandatory()){ //no se anexó un documento obligatorio
               throw new RegistroExcepcion(RegistroCodigosError.EC_REQUIRED_DOCUMENT);
             }
           }
         }
       } catch (RegistroExcepcion exc) {
    	   logger.error("Error al comprobar documentacion de registro [checkConstrainsts][RegistroExcepcion]", exc.fillInStackTrace());
    	   throw exc;
       } catch (Exception e) {
    	   logger.error("Error al comprobar documentacion de registro [checkConstrainsts][Excepcion]", e.fillInStackTrace());
    	   throw new RegistroExcepcion(RegistroCodigosError.EC_VALIDATE_INFO);
       }
   }


   /**
    * Método que troces una cadena de extensiones válidas por un fichero separadas por comas
    * @param cadena Cadena con las extensiones aceptadas
    * @return Array con las extensiones contenidas en la cadena
    */
   private static String[] obtenerExtensiones(String cadena) {
	   ArrayList extensiones = new ArrayList();
	   if (cadena == null || "".equals(cadena))
		   return (String[])extensiones.toArray();
	   StringTokenizer st = new StringTokenizer(cadena,",");
	  while (st.hasMoreElements()){
		   String ext = st.nextToken();
		   extensiones.add(ext);
	   }
	  if (extensiones != null && extensiones.size()>0){
		  String[] retorno = new String[extensiones.size()];
		  for(int i=0; i<extensiones.size(); i++)
			  retorno[i] = (String)extensiones.get(i);
		  return retorno;
	  } else return new String[0];
   }

   /**
    * Crea un asiento de registro telemático.
    *
    * @param sessionId Identificador de la sesión en curso.
    * @param registryRequest Solicitud de registro firmada. En caso de autenticación
    *       Web de Nivel 2 la solicitud no está firmada y se firmará en esta
    *           función con un certificado de servidor.
    * @param additionalInfo Información que debe aparecer en el justificante de registro
    * en la parte específica de la solicitud.
    * @param idiom Idioma.
    * @param oficina Oficina de registro telemático.
    * @param plantilla Jasper para generar el justificante.
    * @param certificado Certificado con el que se firma el justificante.
    * @return La solicitud de registro.
    *
    * @throws RegistroExcepcion Si se produce algún error.
    */
   	public static byte[] register(String sessionId,
		   						  byte[] registryRequest,
		   						  String additionalInfo,
		   						  String idiom,
		   						  String oficina,
		   						  String plantilla,
		   						  String certificado,
		   						  String entidad) throws RegistroExcepcion {

   		RegistroDatos registry = null;
   		byte[] request = null;
   		boolean clean = true;

   		try {
   			// Cargar los datos obligatorios de la petición de registro
   			registry = loadRegistryData(oficina, registryRequest, additionalInfo);

   	        logger.debug("Generando numero de registro para la oficina [" + oficina + "] en la entidad [" + entidad + "]");

   			// Crear el registro generando siguiente número de registro y fecha de registro
   			createRegistryData(oficina, entidad, registry);

   			logger.debug("Registro creado con numero [" + registry.getRegistryNumber() + "] en la entidad [" + entidad + "]");

   			request = doRegistry(sessionId, registry, registryRequest, idiom, oficina, plantilla, certificado, entidad);

   			clean = false;
   		}
   		catch (RegistroExcepcion exc) {
   			logger.error("Error al registrar la solicitud [register][RegistroExcepcion]", exc.fillInStackTrace());
   			throw exc;
   		}
   		catch (Exception e) {
   			logger.error("Error al registrar la solicitud [register][Excepcion]", e.fillInStackTrace());
   			throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTER);
   		}
   		finally {
   			if (clean) {

   				try {
   					cleanTempDocuments(sessionId, entidad);
   				}
   				catch (Exception e) {
   				}

   				if ((registry != null) &&
   					(registry.getRegistryNumber() != null) &&
   					(!registry.getRegistryNumber().equals(""))) {

   					logger.debug("Eliminando documentos asociados al registro con numero [" + registry.getRegistryNumber() + "] por errores en la creacion");

   	   				try {
   	   					deleteRegistryDocuments(registry.getRegistryNumber(), entidad);
   	   				}
   	   				catch (Exception e) {
   	   				}
   				}
   			}
   		}

   		return request;
   	}



    	 /**
         * Crea un asiento de registro telemático.
         *
         * @param sessionId Identificador de la sesión en curso.
         * @param registryRequest Solicitud de registro firmada. En caso de autenticación
         *       Web de Nivel 2 la solicitud no está firmada y se firmará en esta
         *           función con un certificado de servidor.
         * @param additionalInfo Información que debe aparecer en el justificante de registro
         * en la parte específica de la solicitud.
         * @param idiom Idioma.
         * @param oficina Oficina de registro telemático.
         * @param plantilla Jasper para generar el justificante.
         * @param certificado Certificado con el que se firma el justificante.
         * @return La solicitud de registro.
         *
         * @throws RegistroExcepcion Si se produce algún error.
         */
        	public static byte[] register(String sessionId,
     		   						  byte[] registryRequest,
     		   						  String additionalInfo,
     		   						  String idiom,
     		   						  String oficina,
     		   						  byte[] plantilla,
     		   						  String certificado,
     		   						  String entidad) throws RegistroExcepcion {
        		try{
        			// Copiar el ZIP de la definición de la plantilla a un fichero
        			// temporal
        			String separador = System.getProperty("file.separator");

        			StringBuffer path = new StringBuffer();
        			String deleteDirectoryPath = null;
        			File definicion = createTempFile(plantilla, "zip");
        			String rutaPlantilla = "";
        			if (definicion != null) {
	        			// Path absoluto al fichero ZIP
	    				int index = definicion.getAbsolutePath().lastIndexOf(separador);
	    				path.append(definicion.getAbsolutePath().substring(0, index));

	    				// Directorio en el que se van a descomprimir las definiciones
	    				// de los justificantes de registro telemático
	    				path.append(separador).append("rt-tram-just");
	    				createDirectory(path.toString());

	    				// Multientidad
	    				String idEntidad = MultiEntityContextHolder.getEntity();
	    				if (StringUtils.isNotBlank(idEntidad)) {

	    					path.append(separador).append(idEntidad);
	    					createDirectory(path.toString());
	    				}


	    				// Directorio final en el que se descomprime la definición del
	    				// justificante que se borrará al finalizar el proceso
	    				deleteDirectoryPath = path.toString();
	    				createDirectory(deleteDirectoryPath);

	    				// Descomprimir el ZIP con la definición del justificante
	    				unzipFile(definicion, deleteDirectoryPath);

	    				// Eliminar el fichero temporal de la definición
	    				definicion.delete();

	    				// Obtener la plantilla del justificante
	    				rutaPlantilla = obtenerPlantillaJustificante(deleteDirectoryPath, "");

        			}
		            byte[] registryResult =  RegistroManager.register(sessionId, registryRequest, additionalInfo, idiom, oficina, rutaPlantilla, certificado, entidad);

		           return registryResult;

        		}
        		catch (RegistroTelematicoException rtEx) {
        			logger.error("Error al crear la plantilla para registrar la solicitud [register][Excepcion]", rtEx.fillInStackTrace());
           			throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTER);
				}
        		catch (Exception e) {
           			logger.error("Error al registrar la solicitud [register][Excepcion]", e.fillInStackTrace());
           			throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTER);
           		}
        	}

        	/**
        	 * Obtener la plantilla de justificante de registro.
        	 *
        	 * @param path
        	 *            Ruta del directorio de la plantilla.
        	 * @param idioma
        	 *            Idioma de la plantilla.
        	 * @return String de la plantilla.
        	 */
        	protected static String obtenerPlantillaJustificante(String path, String idioma) throws RegistroTelematicoException{

        		String jasperPath = path + System.getProperty("file.separator")
        				+ "plantilla_" + idioma + ".jasper";


        		File plantillaFile = new File(jasperPath);
        		if ((plantillaFile == null) || (!plantillaFile.exists())) {

        			jasperPath = path + System.getProperty("file.separator")
        					+ "plantilla.jasper";


            		plantillaFile = new File(jasperPath);
        			if ((plantillaFile == null) || (!plantillaFile.exists())) {

        				// Error de plantilla de justificante no existente
        				logger.error("La plantilla del justificante no existe en el path: "+jasperPath);

        				throw new RegistroTelematicoException(
        						"Error al generar el justificante de presentacion de la solicitud de registro");
        			}
        		}

        		return jasperPath;
        	}
        	/**
        	 * Crea el directorio indicado.
        	 *
        	 * @param path
        	 *            Ruta del directorio a crear.
        	 */
        	protected static void createDirectory(String path) {

        		(new File(path)).mkdir();
        	}
        	/**
        	 * Crear un fichero temporal.
        	 *
        	 * @param content
        	 *            Contenido del fichero temporal.
        	 * @param extension
        	 *            Extensión del fichero temporal
        	 * @return Fichero temporal creado con el contenido copiado y la extensión
        	 *         correspondiente.
        	 */
        	protected static File createTempFile(byte[] content, String extension) {

        		File tempFile = null;
        		OutputStream output = null;
        		InputStream input = null;

        		try {
        			tempFile = File.createTempFile("tmpfile", "." + extension);

        			output = new FileOutputStream(tempFile);
        			input = new ByteArrayInputStream(content);

        			FileUtils.copy(input, output);
        		} catch (IOException e) {
        			logger.error("Error al crear el fichero temporal del justificante",
        							 e);


        		} finally {
        			if (output != null) {
        				try {
        					output.close();
        				} catch (IOException e) {
        				}
        			}
        			if (input != null) {
        				try {
        					input.close();
        				} catch (IOException e) {
        				}
        			}
        		}

        		return tempFile;
        	}

        	/**
        	 * Descomprimir un fichero ZIP.
        	 *
        	 * @param file
        	 *            Fichero ZIP a descomprimir.
        	 * @param path
        	 *            Ruta del directorio en el que se descomprime el fichero ZIP.
        	 */
        	@SuppressWarnings("unchecked")
        	protected static void unzipFile(File file, String path) {

        		ZipFile zipFile = null;
        		OutputStream output = null;
        		InputStream input = null;

        		try {
        			// Fichero ZIP
        			zipFile = new ZipFile(file);

        			Enumeration zipEntries = zipFile.entries();
        			while (zipEntries.hasMoreElements()) {

        				ZipEntry zipEntry = (ZipEntry) zipEntries.nextElement();

        				try {
        					input = new BufferedInputStream(zipFile
        							.getInputStream(zipEntry));

        					File zipEntryFile = new File(path
        							+ System.getProperty("file.separator")
        							+ zipEntry.getName());
        					output = new FileOutputStream(zipEntryFile);

        					FileUtils.copy(input, output);
        				} catch (IOException e) {
        					logger.error("Error al descomprimir el zip del justificante",
        									 e);


        				} finally {
        					if (output != null) {
        						try {
        							output.close();
        						} catch (IOException e) {
        						}
        					}
        					if (input != null) {
        						try {
        							input.close();
        						} catch (IOException e) {
        						}
        					}
        				}
        			}
        		} catch (IOException e) {
        			logger
        					.error("Error al descomprimir el zip del justificante",e);


        		} finally {
        			if (zipFile != null) {
        				try {
        					zipFile.close();
        				} catch (IOException e) {
        				}
        			}
        		}
        	}
    /**
     * Genera un nuevo número de registro a partir de la información almacenada en la
     * tabla SGMRTREGISTRO_SECUENCIA
     * @return String Número de registro
     * @throws Exception Si se produce un error
     */
    public static String getRegistryNumber(String entidad) throws Exception {
        int counter;
        String suma = new String(), year, base, secuencia;
        RegistroSecuenciaDatos sequence = new RegistroSecuenciaDatos();

        try {
          sequence.updateSequence(entidad);
          year = sequence.getYear();
          counter = Integer.parseInt(year);
          if (counter < Integer.parseInt(DateTimeUtil.getCurrentYear())) {
             year = Integer.toString(counter + 1);
             sequence.setYear(year);
             sequence.setSequence(1);
             sequence.updateSequenceAndYear(entidad);
          }
          secuencia = ""+sequence.getSequence();
          base = sequence.getLabel();

          String padding = "";
          if (secuencia.length() < 6)
              for(int k=secuencia.length(); k<6; k++)
            	  padding = "0" + padding;
          suma = year + base + padding + secuencia;

        }catch (RegistroExcepcion e) {
        	logger.error("Error al obtener numero de registro [getRegistryNumber][RegistroExcepcion]", e.fillInStackTrace());
        	throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER);
        } catch (Exception e) {
        	logger.error("Error al obtener numero de registro [getRegistryNumber][Excepcion]", e.fillInStackTrace());
        	throw e;
        }
        return suma;
    }

    /**
     * Genera un nuevo número de registro a partir de la información almacenada en la
     * tabla SGMRTREGISTRO_SECUENCIA y el código de la entidad a la que se destina
     * @param codigoOficina Código de la oficina
     * @return String Número de registro
     * @throws Exception Si se produce un error
     */
    /*
    public static String getRegistryNumber(String codigoOficina, String entidad) throws Exception {

    	String year, secuencia;
        RegistroSecuenciaDatos sequence = new RegistroSecuenciaDatos();

    	String label = "";
       	String oficina  = codigoOficina;
       	for(int i=oficina.length(); i<3; i++)
       	  	label = "0" + label;
       	label = label + oficina + "00";

       	year = DateTimeUtil.getCurrentYear();

        try {
        	sequence.updateSequence(label, entidad, year);
        }
        catch (RegistroExcepcion re) {

        	// Si no existe el registro para la oficina y el año actual
        	// se crea uno nuevo con la secuencia inicializada a 1
        	if (re.getErrorCode() == RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED) {

        		try {
		       		sequence.setLabel(label);
		       		sequence.setSequence(1);
		       		sequence.setYear(year);
		       		sequence.insert(entidad);
                }
        		catch (RegistroExcepcion e) {
                	logger.error("Error al obtener numero de registro [getRegistryNumber][RegistroExcepcion]", e.fillInStackTrace());
                	throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER);
                }
        		catch (Exception e) {
                	logger.error("Error al obtener numero de registro [getRegistryNumber][Excepcion]", e.fillInStackTrace());
                	throw e;
                }
        	}
        	else {
        		throw re;
        	}
        }
        catch (Exception e) {
        	logger.error("Error al obtener numero de registro [getRegistryNumber][Excepcion]", e.fillInStackTrace());
        	throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER);
        }

        // Generar el número de registro
        // Año + Oficina + Secuencia de tamaño 15
    	secuencia = ""+sequence.getSequence();
    	String padding = "";
    	if (secuencia.length() < 6)
    		for(int k=secuencia.length(); k<6; k++)
    			padding = "0" + padding;

    	return year + label + padding + secuencia;
    }
    */

    /**
     * Carga los datos de registro a partir de la petición de registro.
     *
     * @param registryRequest Solicitud de registro.
     * @param additionalInfo Información adicional.
     *
     * @return Los datos de registro.
     *
     * @throws Exception Si se produce algún error.
     */
    private static RegistroDatos loadRegistryData(String oficina, byte[] registryRequest, String additionalInfo) throws Exception {

    	RegistroDatos registry = new RegistroDatos();

    	XmlDocument xmlDoc = new XmlDocument();
    	XmlElement root, genericData;

    	try {
    		xmlDoc.createFromStringText(Goodies.fromUTF8ToStr(registryRequest));

    		root = xmlDoc.getRootElement();
    		genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA);

    		// Nombre
    		registry.setName(genericData.getChildElement(Definiciones.SENDER_NAME).getValue());
    		//registry.setSurName(genericData.getChildElement(Definiciones.SENDER_SURNAME).getValue());

    		// Correo electrónico
    		registry.setEMail(genericData.getChildElement(Definiciones.SENDER_EMAIL).getValue());

    		// Emisor
    		genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA + "/" + Definiciones.ID);
    		registry.setSenderId(genericData.getChildElement(Definiciones.SENDER_ID).getValue());
    		registry.setSenderIdType(Integer.parseInt(genericData.getChildElement(Definiciones.SENDER_ID_TYPE).getValue()));

    		// Asunto
    		genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.TOPIC);
    		registry.setTopic(genericData.getChildElement(Definiciones.CODE).getValue());

    		// Órgano destinatario
    		genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.ADDRESSEE);
    		registry.setAddressee(genericData.getChildElement(Definiciones.CODE).getValue());

    		// Número de expediente
    		genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA);
    		registry.setNumeroExpediente(genericData.getChildElement(Definiciones.FOLDER_ID).getValue());

    		// Oficina
    		registry.setOficina(oficina);

    		// Información adicional
    		registry.setAdittionalInfo(Goodies.fromStrToUTF8(additionalInfo));
    	}
    	catch (RegistroExcepcion exc) {
     	  	logger.error("Error al establecer los datos del registro [createRegistryData][RegistroExcepcion]", exc.fillInStackTrace());
     	  	throw exc;
    	}
    	catch (Exception e) {
    		logger.error("Error al establecer los datos del registro [createRegistryData][RegistroExcepcion]", e.fillInStackTrace());
    		throw new RegistroExcepcion(RegistroCodigosError. EC_REGISTER);
    	}

       return registry;
    }

    /**
     * Crea un nuevo registro obteniendo el siguiente número de registro
     * a partir de la información almacenada en la tabla SGMRTREGISTRO_SECUENCIA
     * y el código de la entidad a la que se destina, todo ello en una misma transacción.
     *
     * @param codigoOficina Código de la oficina
     * @param entidad Entidad
     * @param registry Datos del registro
     * @return String Número de registro
     * @throws Exception Si se produce un error
     */
    public static void createRegistryData(String codigoOficina, String entidad, RegistroDatos registry) throws Exception {

    	String year, secuencia;
        RegistroSecuenciaDatos sequence = new RegistroSecuenciaDatos();

        DbConnection dbConn = new DbConnection();

    	String label = "";
       	String oficina  = codigoOficina;
       	for(int i=oficina.length(); i<3; i++)
       	  	label = "0" + label;
       	label = label + oficina + "00";

       	// El año para obtener el siguiente numero de registro se obtiene a partir
       	// de la fecha de registro obtenida mediante el servicio de tiempos
       	// year = DateTimeUtil.getCurrentYear();
    	ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
    	Date registryDate = servicioTiempos.getCurrentDate();
    	year = DateTimeUtil.getDateTime(registryDate, DatePattern.YEAR4_DATETIME_PATTERN);

       	logger.debug("Se va a obtener el numero de secuencia...");

       	try {
       		dbConn.open(DBSessionManager.getSession(entidad));

       		dbConn.beginTransaction();

       		// Obtener el siguiente número de registro a partir de oficina y año
	        try {
	        	sequence.updateSequence(dbConn, label, year);
	        }
	        catch (RegistroExcepcion re) {

	        	// Si no existe el registro para la oficina y el año actual
	        	// se crea uno nuevo con la secuencia inicializada a 1
	        	if (re.getErrorCode() == RegistroCodigosError.EC_REGISTRY_NUMBER_NOT_INITIALIZED) {

	        		try {
			       		sequence.setLabel(label);
			       		sequence.setSequence(1);
			       		sequence.setYear(year);
			       		sequence.insert(dbConn);
	                }
	        		catch (RegistroExcepcion e) {
	                	logger.error("Error al obtener numero de registro [getRegistryNumber][RegistroExcepcion]", e.fillInStackTrace());
	                	throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER);
	                }
	        		catch (Exception e) {
	                	logger.error("Error al obtener numero de registro [getRegistryNumber][Excepcion]", e.fillInStackTrace());
	                	throw e;
	                }
	        	}
	        	else {
	        		throw re;
	        	}
	        }
	        catch (Exception e) {
	        	logger.error("Error al obtener numero de registro [getRegistryNumber][Excepcion]", e.fillInStackTrace());
	        	throw new RegistroExcepcion(RegistroCodigosError.EC_REGISTRY_NUMBER);
	        }

	        logger.debug("Numero de secuencia [" + sequence.getSequence() + "]");

	        if (sequence.getSequence() == 0)
	        	throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);

	        // Establecer la fecha de registro
	    	registry.setRegistryDate(registryDate);

	    	logger.debug("Se va a generar el numero de registro...");

	        // Generar el número de registro: Año + Oficina + Secuencia de tamaño 15
	    	secuencia = "" + sequence.getSequence();
	    	String padding = "";
	    	if (secuencia.length() < 6)
	    		for(int k = secuencia.length(); k < 6; k++)
	    			padding = "0" + padding;

	    	String registryNumber = year + label + padding + secuencia;

	    	logger.debug("Numero de registro [" + registryNumber + "]");

	    	// Establecer en el registro el número de registro generado
	    	registry.setRegistryNumber(registryNumber);

	    	// Estado inicial de error
	    	// que se modificará cuando el proceso de registro se finalice correctamente
	    	registry.setStatus(RegistroEstado.STATUS_ERROR);

	    	// Insertar el registro
	    	registry.add(dbConn);

	        dbConn.endTransaction(true);

	        logger.debug("Registro [" + registryNumber + "] insertado en SGMRTREGISTRO");
        }
       	catch (RegistroExcepcion e) {
       		throw e;
        }
       	catch (Exception e) {
       		throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_DB);
        }
       	finally {
       		if (dbConn.existConnection())
       			dbConn.close();
        }
    }

   /**
    * Efectúa una operación de registro en la base de datos.
    *
    * @param sessionId Identificador de sesión.
    * @param registry Datos de registro.
    * @param registryRequest Solicitud de registro firmada.
    * @param idiom Idioma.
    * @param oficina Oficina de registro telemático.
    * @param plantilla Jasper para generar el justificante.
    * @param certificado Certificado con el que se firma el justificante.
    *
    * @return La solicitud de registro modificada con los valores de número y
    * fecha y hora de registro.
    *
    * @throws java.lang.Exception Si se produce algún error.
    */
    private static byte[] doRegistry(String sessionId,
    								 RegistroDatos registry,
    								 byte[] registryRequest,
    								 String idiom,
    								 String oficina,
    								 String plantilla,
    								 String certificado,
    								 String entidad) throws Exception {

    	XmlDocument xmlDoc = new XmlDocument();
    	XmlElement root, documentElem, registryData, elem, sign;
    	XmlElements documents;

    	int counter, size;
    	byte[] request = null;
    	//byte[] receipt = null;
    	ReceiptVO receiptVO = null;

    	try {
    		xmlDoc.createFromStringText(Goodies.fromUTF8ToStr(registryRequest));
    		root = xmlDoc.getRootElement();

    		// Establecer los datos del registro en el XML de la petición de registro
    		registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);

    		// Número de registro
    		elem = registryData.getChildElement(Definiciones.REGISTRY_NUMBER);
    		elem.setValue(registry.getRegistryNumber());

    		// Fecha y hora de registro
    		elem = registryData.getChildElement(Definiciones.REGISTRY_DATE);

    		Date fechaRegistro = registry.getRegistryDate();
    		SimpleDateFormat dateFormat = new SimpleDateFormat(Definiciones.DEFAULT_DATE_FORMAT);
    		SimpleDateFormat hourFormat = new SimpleDateFormat(Definiciones.DEFAULT_HOUR_FORMAT);
    		String date = dateFormat.format(fechaRegistro);
    		String hour = hourFormat.format(fechaRegistro);
    		elem = registryData.getChildElement(Definiciones.REGISTRY_DATE);
        	elem.setValue(date);
    		elem = registryData.getChildElement(Definiciones.REGISTRY_HOUR);
        	elem.setValue(hour);

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] con fecha de presentacion [" + date + "] y hora [" + hour + "]");

    		// Obtener la fecha efectiva del registro telemático
    		ServicioCalendario oServicioCalendario = LocalizadorServicios.getServicioCalendario();

    		RetornoCalendario fechaEfectiva = oServicioCalendario.proximoLaborable(registry.getRegistryDate(), getEntidad(entidad));
    		if (fechaEfectiva.isLaborable()) {
    			registry.setEffectiveDate(registry.getRegistryDate());
    		}
    		else {
    			registry.setEffectiveDate(fechaEfectiva.getProximo());
    		}

    		Date effectiveDate = registry.getEffectiveDate();
    		String effectiveDateString = dateFormat.format(effectiveDate);
    		String effectiveHourString = hourFormat.format(effectiveDate);
    		elem = registryData.getChildElement(Definiciones.REGISTRY_EFFECTIVE_DATE);
        	elem.setValue(effectiveDateString);
    		elem = registryData.getChildElement(Definiciones.REGISTRY_EFFECTIVE_HOUR);
        	elem.setValue(effectiveHourString);

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] con fecha efectiva [" + effectiveDateString + "] y hora [" + effectiveHourString + "]");

    		request = xmlDoc.getUtf8Text(false);

    		ServicioRepositorioDocumentosTramitacion oServicioRepoDocTram = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
    		DocumentosTemporales documentTmps = oServicioRepoDocTram.getDocumentTmp(sessionId, getEntidad(entidad));

    		// Procesar los documentos asociados a la petición de registro
    		documents = root.getDescendantElement(Definiciones.XPATH_DOCUMENTS).getChildElements();
    		size = documents.getCount();

    		ArrayList reads = new ArrayList();
    		for (counter = 1; counter <= size; counter++) {

    			documentElem = documents.getItem(counter);
    			HashMap hm = new HashMap();
    			hm.put("CODE", documentElem.getChildElement(Definiciones.CODE).getValue());
    			hm.put("HASH", documentElem.getChildElement(Definiciones.HASH).getValue());

    			reads.add(hm);
    		}

    		// Procesar los anexos de la solicitud almacenados como documentos temporales
    		if (documentTmps != null) {

    			for (int h = 0; h < documentTmps.count(); h++){

    				DocumentoTemporal docTmpData = (DocumentoTemporal)documentTmps.get(h);
    				String guid = docTmpData.getGuid();
    				ContenedorDocumento data = oServicioRepoDocTram.retrieveDocumentInfo(sessionId, guid, getEntidad(entidad));
    				String code = "";

    				for(int k = 0; k <reads.size(); k++) {

    					HashMap hm = (HashMap)reads.get(k);
    					if (( (String) hm.get("HASH")).equalsIgnoreCase(data.getHash())) {

    						code = (String)hm.get("CODE");
         			   		break;
    					}
    				}

    				RegistroDocumentoDatos regDocAnexoSolicitud = new RegistroDocumentoDatos();

    				regDocAnexoSolicitud.setRegistryNumber(registry.getRegistryNumber());
    				regDocAnexoSolicitud.setCode(code);
    				regDocAnexoSolicitud.setGuid(guid);

    				regDocAnexoSolicitud.add(entidad);

    				logger.debug("Registro [" + registry.getRegistryNumber() + "] documento adjunto [" + code + "] insertado en SGMRTREGISTRO_DOCUMENTOS");
            	}
    		}

    		// Eliminar las entradas de la tabla temporal de documentos
    		oServicioRepoDocTram.deleteDocumentTmp(sessionId, getEntidad(entidad));

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] documentos adjuntos eliminados de SGMRTTMP_DOCUMENTOS");

    		String pguid = null;

	        // Comprobar si la solicitud se ha firmado (configuración establecida para el trámite)
		   	// para entonces generar la solicitud sin la firma a partir de la solicitud firmada
    		sign = root.getChildElement(Definiciones.SIGNATURE_HOOK);
    		if (sign.getValue().equals("")) {

    			pguid = oServicioRepoDocTram.storeDocument(sessionId, request, "xml", getEntidad(entidad));
    		}
    		else {
        		// Guardar la solicitud firmada
        		RegistroDocumentoDatos regDocSolicitudFirmada = new RegistroDocumentoDatos();

        		regDocSolicitudFirmada.setRegistryNumber(registry.getRegistryNumber());
        		regDocSolicitudFirmada.setCode(Definiciones.REGISTRY_REQUEST_CODE);
        		pguid = oServicioRepoDocTram.storeDocument(sessionId, request, "xml", getEntidad(entidad));
        		regDocSolicitudFirmada.setGuid(pguid);

        		regDocSolicitudFirmada.add(entidad);

        		logger.debug("Registro [" + registry.getRegistryNumber() + "] solicitud de registro firmada en SGMRTREGISTRO_DOCUMENTOS");

        		// Generar la solicitud sin la firma
    			sign.setValue("");
    			pguid = oServicioRepoDocTram.storeDocument(sessionId, xmlDoc.getUtf8Text(false), "xml", getEntidad(entidad));
    		}

    		// Guardar la solicitud sin la firma
    		RegistroDocumentoDatos regDocSolicitudSinFirma = new RegistroDocumentoDatos();

    		regDocSolicitudSinFirma.setRegistryNumber(registry.getRegistryNumber());
    		regDocSolicitudSinFirma.setCode(Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE);
    		regDocSolicitudSinFirma.setGuid(pguid);

    		regDocSolicitudSinFirma.add(entidad);

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] solicitud de registro en SGMRTREGISTRO_DOCUMENTOS");

    		// COMPATIBILIDAD CON VERSIONES V1.8 Y ANTERIORES
    		// ----------------------------------------------
    		// Añadir los tags de Datos_Registro/Fecha_Registro y Datos_Registro/Hora_Registro al XML de solicitud de registro
    		// ya que estas tags son referenciadas en los .jasper del justificante para versiones V1.8 y anteriores
    		String xml_request = Goodies.fromUTF8ToStr(request);
	        int index = xml_request.indexOf("<" + Definiciones.REGISTRY_DATA + ">") + Definiciones.REGISTRY_DATA.length() + 2;

	        xml_request = xml_request.substring(0, index)
	        			+ "<" + Definiciones.REGISTRY_DATE_TAG_V1_8 + ">"
	        			+ date
	        			+ "</" + Definiciones.REGISTRY_DATE_TAG_V1_8 + ">"
	        			+ "<" + Definiciones.REGISTRY_HOUR_TAG_V1_8 + ">"
	        			+ hour
	        			+ "</" + Definiciones.REGISTRY_HOUR_TAG_V1_8 + ">"
	        			+ xml_request.substring(index, xml_request.length());

    		// Generar el justificante
	        MultiEntityContextHolder.setEntity(entidad);
    		InputStream is = new ByteArrayInputStream(FileManager.readBytesFromFile(plantilla));
    		receiptVO = FirmaManager.signReceipt(sessionId, is, xml_request, plantilla, certificado);

    		// Guardar el justificante
    		RegistroDocumentoDatos regDocJustificante = new RegistroDocumentoDatos();

    		regDocJustificante.setRegistryNumber(registry.getRegistryNumber());
    		regDocJustificante.setCode(Definiciones.REGISTRY_RECEIPT_CODE);
    		pguid = oServicioRepoDocTram.storeDocument(sessionId, receiptVO.getContent(), "pdf", getEntidad(entidad));
    		regDocJustificante.setGuid(pguid);

    		regDocJustificante.add(entidad);

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] justificante de registro en SGMRTREGISTRO_DOCUMENTOS");

    		//Almacenar la asociación entre el pguid y el csv del documento
    		saveCsvDocumento(receiptVO.getCsv(), pguid,entidad);
    		
    		// Si la petición de registro se ha procesado correctamente
    		// se modifica su estado de error a no consolidado
    		registry.setStatus(RegistroEstado.STATUS_NOT_CONSOLIDATED);
    		// Y se actualiza junto con la fecha efectiva del registro
    		updateEffectiveDateAndStatus(registry, entidad);

    		logger.debug("Registro [" + registry.getRegistryNumber() + "] finalizado correctamente");

    		//request = xmlDoc.getUtf8Text(false);
    		request = Goodies.fromStrToUTF8(xml_request);
    	}
    	catch (RegistroExcepcion exc) {
    		logger.error("Error al efectuar el registro con numero [" + registry.getRegistryNumber() + "] [doRegistry][RegistroExcepcion]", exc.fillInStackTrace());
    		throw exc;
    	}
    	catch (Exception e) {
    		logger.error("Error al efectuar el registro con numero [" + registry.getRegistryNumber() + "] [doRegistry][Excepcion]", e.fillInStackTrace());
    		throw new RegistroExcepcion(RegistroCodigosError. EC_REGISTER);
    	}

       return request;
    }
    
    /**
     * Almacena la asociación entre el pguid de un documento y su csv
     * 
     * @param csv Código Seguro de Verificación
     *
     * @param guid Identificador del documento en el servicio documental
     * 
     * @param entidad Entidad sobre la que estamos trabajando
     * @throws RegistroExcepcion 
     */
    private static void saveCsvDocumento(String csv, String guid, String entidad) throws RegistroExcepcion{
		if (logger.isDebugEnabled()) {
			
			logger.debug("saveCsvDocumento(String, String, String) - start");
			logger.debug("Almacenando csv documento: con csv ["+csv+"] guid ["+guid+"] Entidad ["+entidad+"]");
		}
    	
    	
    	RegistroDocumentoCSV documentoCSV = new RegistroDocumentoCSVImpl();
    	documentoCSV.setCsv(csv);
    	documentoCSV.setGuid(guid);
    	
    	RegistroDocumentoCSVDatos documentoCSVDatos = new RegistroDocumentoCSVDatos(documentoCSV);
    	
    	documentoCSVDatos.add(entidad);
		
		if (logger.isDebugEnabled()) {
			logger.debug("saveCsvDocumento(String, String, String) - end");
		}
    }

    /**
     * Elimina los documentos asociados a un registro.
     *
     * @param registryNumber Número de registro.
     * @throws Exception Si se produce algún error.
     */
    private static void deleteRegistryDocuments(String registryNumber,
    											String entidad) throws Exception {

       RegistroDocumentoDatos registryDocument = new RegistroDocumentoDatos();
       RegistroDocumentos registryDocuments;

       try {
    	   logger.debug("Obteniendo los documentos del registro con numero [" + registryNumber + "]");

    	   registryDocument.setRegistryNumber(registryNumber);
    	   registryDocuments = registryDocument.getRegistryDocuments(entidad);

    	   if (registryDocuments != null) {

    		   ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();

    		   for(int i = 0; i < registryDocuments.count(); i++) {

    			   RegistroDocumentoDatos document = (RegistroDocumentoDatos)registryDocuments.get(i);

    			   logger.debug("Eliminando del repositorio el documento [" + document.getCode() + "] del registro con numero [" + registryNumber + "]");

    			   oServicio.deleteDocument("", document.getGuid(), getEntidad(entidad));
    		   }
    	   	}

    	   	logger.debug("Se van a eliminar los documentos del registro con numero [" + registryNumber + "]");
    	   	registryDocument.delete(entidad);
    	   	logger.debug("Documentos eliminados");
       	}
       	catch (Exception exc) {
       		logger.error("Error al eliminar los documentos asociados al registro con numero [" + registryNumber + "] [deleteRegistryDocuments][Excepcion]", exc.fillInStackTrace());
       		throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
       	}
    }

   /**
    * Elimina una entrada de registro.
    *
    * @param registryNumber Número de registro.
    * @throws Exception Si se produce algún error.
    */
   /*
   private static void deleteRegistry(String registryNumber,
		   							  String entidad) throws Exception {

	   RegistroDatos registry = new RegistroDatos();

	   try {
		   // Eliminar los documentos asociados a un registro
		   deleteRegistryDocuments(registryNumber, entidad);

		   // Eliminar el registro
		   registry.setRegistryNumber(registryNumber);

		   logger.debug("Se va a eliminar el registro con numero [" + registryNumber + "]");
		   registry.delete(entidad);
		   logger.debug("Registro eliminado");
	   }
	   catch (Exception exc) {
		   logger.error("Error al eliminar registro [deleteRegistry][Excepcion]", exc.fillInStackTrace());
	   }
   }
   */

   /**
    * Crea una entrada de registro inválida. Se hace para que no haya "huecos"
    * en los números de registro.
    *
    * @param registryNumber Número de registro.
    * @throws java.lang.Exception Si se produce algún error.
    */
   /*
   private static void createNullRegistry(String registryNumber, String entidad)
   throws Exception {
      RegistroDatos registry = new RegistroDatos();

      try {
    	  logger.debug("Se va a crear un registro nulo con numero [" + registryNumber + "]");


    	  ServicioTiempos servicioTiempos = LocalizadorServicios.getServicioTiempos();
    	  Date registryDate  = servicioTiempos.getCurrentDate();
    	  registry.setRegistryDate(registryDate);
    	  registry.setRegistryNumber(registryNumber);
    	  registry.setSenderId("-");
    	  registry.setName("-");
    	  //registry.setSurName("-");
    	  registry.setEMail("-");
    	  registry.setTopic("-");
    	  registry.setAddressee("-");
    	  registry.setStatus(RegistroEstado.STATUS_ERROR);
    	  registry.setSenderIdType(0);
    	  registry.setAdittionalInfo(new byte[0]);

         registry.add(entidad);

         logger.info("Registro nulo creado con numero [" + registryNumber + "]");
      }
      catch (RegistroExcepcion exc) {
    	  logger.error("Error al crear registro vacio [createNullRegistry][RegistroExcepcion]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al crear registro vacio [createNullRegistry][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError. EC_REGISTER);
      }
   }
   */

   /**
    * Elimina todos los documentos temporales que se han guardado en el repositorio de invesDoc.
    * Se debe llamar a esta función si no se completa el proceso de registro.
    *
    * @param sessionId Identificador de sesión.
    *
    * @throws RegistroExcepcion Si se produce algún error.
    */
   	public static void cleanTempDocuments(String sessionId,
   										  String entidad) throws RegistroExcepcion {

   		try {
   			ServicioRepositorioDocumentosTramitacion oServicioRepoDocTram = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();

   			DocumentosTemporales documentTmps = oServicioRepoDocTram.getDocumentTmp(sessionId, getEntidad(entidad));

   			if (documentTmps != null) {

   				for(int h = 0; h < documentTmps.count(); h++) {

   					DocumentoTemporal docTmp = (DocumentoTemporal)documentTmps.get(h);
   					String guid = docTmp.getGuid();

   					oServicioRepoDocTram.deleteDocument(sessionId, guid, getEntidad(entidad));

   					logger.debug("Eliminado del repositorio documento temporal [" + guid + "] de la sesion [" + sessionId + "]");
   				}
   			}

   			oServicioRepoDocTram.deleteDocumentTmp(sessionId, getEntidad(entidad));
   		}
   		catch (Exception e) {
   			logger.error("Error al eliminar documentacion temporal asociada a la sesion [" + sessionId + "] [cleanTempDocuments][Excepcion]", e.fillInStackTrace());
   			throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
   		}
   	}

   /**
    * Elimina todos los documentos temporales que se han guardado en el
    * repositorio de invesDoc. Se debe llamar a esta función si no se completa
    * el proceso de registro.
    *
    * @param sessionId Identificador de sesión.
    * @param registryNumber Número de registro.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   /*
   public static void cleanTempDocuments(String sessionId, String registryNumber, String entidad)
   throws RegistroExcepcion {
      try {
    	 ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
         DocumentosTemporales documentTmps = oServicio.getDocumentTmp(sessionId, getEntidad(entidad));

         if (documentTmps!=null){
           for(int h=0; h<documentTmps.count(); h++){
             DocumentoTemporal docTmp = (DocumentoTemporal)documentTmps.get(h);
             String guid = docTmp.getGuid();
             oServicio.deleteDocument(sessionId, guid, getEntidad(entidad));
           }
         }
         oServicio.deleteDocumentTmp(sessionId, getEntidad(entidad));
      } catch (Exception e) {
    	  logger.error("Error al eliminar documentación temporal [cleanTempDocuments][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
      }
   }
   */

   /**
    * Recupera un conjunto de números de registro a partir de unos valores de
    * búsqueda.
    *
    * @param query Información con los valores de búsqueda.
    * @param sessionId Identificador de sesión. Tiene sentido para la aplicación
    * llamante.
    * @return La lista de trámites que cumplen los criterios de búsqueda.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static Registros query(String sessionId, RegistroConsulta query, String entidad)
   throws RegistroExcepcion {
      RegistroDatos registryData = new RegistroDatos();
      Registros basicRegs = new Registros();

      try {

         DynamicRow rowInfo = registryData.query(query, entidad);
         for (int i = 1; i < rowInfo.getRowCount() ; i++) {

            RegistroImpl registry = new RegistroImpl();
            registryData = (RegistroDatos)rowInfo.getRow(i);

            registry.setRegistryNumber(registryData.getRegistryNumber());
            registry.setRegistryDate(registryData.getRegistryDate());
            registry.setName(registryData.getName());
            //registry.setSurName(registryData.getSurName());
            registry.setSenderId(registryData.getSenderId());
            registry.setEMail(registryData.getEMail());
            registry.setTopic(registryData.getTopic());
            registry.setStatus(registryData.getStatus());
            registry.setAddressee(registryData.getAddressee());
            registry.setSenderIdType(registryData.getSenderIdType());
            registry.setEffectiveDate(registryData.getEffectiveDate());
            //registry.setRepresentedName(registryData.getRepresentedName());
            //registry.setRepresentedId(registryData.getRepresentedId());
            registry.setAdittionalInfo(registryData.getAdditionalInfo());
            registry.setOficina(registryData.getOficina());
            registry.setNumeroExpediente(registryData.getNumeroExpediente());

            basicRegs.add(registry);
         }
         //Log.setQuery(sessionId, query);
      } catch (Exception e) {
    	  logger.error("Error al obtener resultados de busqueda [query][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_NO_REGISTRY);
      }
      return basicRegs;
   }


   /**
    * Recupera la información de un registro telemático a partir de su número de
    * registro.
    *
    * @param registryNumber Número de registro.
    * @return El asiento de registro telemático.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static Registro getRegistry(String sessionId, String registryNumber, String entidad)
   throws RegistroExcepcion {

      RegistroDatos registryData = new RegistroDatos();

      try {
         registryData.load(registryNumber, entidad);
         //Log.setGetRegistry(sessionId, registryNumber);
      } catch (RegistroExcepcion re) {
    	  logger.error("Error al obtener datos de registro [getRegistry][Excepcion]", re);
    	  throw re;
      } catch (Exception e) {
    	  logger.error("Error al obtener datos de registro [getRegistry][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_GET_REGISTRY);
      }
      return registryData;
   }


   /**
    * Recupera la lista de documentos de un asiento de registro.
    *
    * @param registryNumber Número de registro.
    * @return La lista de documentos.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static RegistroDocumentos getRegistryDocuments(String registryNumber, String entidad)
   throws RegistroExcepcion {

      RegistroDocumentos documents = new RegistroDocumentos();

      try {
         RegistroDocumentoDatos registry = new RegistroDocumentoDatos();
         registry.setRegistryNumber(registryNumber);
         documents = registry.getRegistryDocuments(entidad);
      } catch (Exception e) {
    	  logger.error("Error al obtener documentos de registro [getRegistryDocuments][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT);
      }
      return documents;

   }

   /**
    * Recupera los documentos de un asiento de registro.
    *
    * @param registryNumber Número de registro.
    * @return La lista de documentos.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static ContenedorDocumentos getRegistryDocumentsData(String registryNumber, String entidad)
   throws RegistroExcepcion {
      ContenedorDocumentos datadocs = new ContenedorDocumentos();
      try {
    	 ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
         RegistroDocumentos documents = getRegistryDocuments(registryNumber, entidad);
         if (documents!=null){
           for(int i=0; i<documents.count(); i++){
             RegistroDocumentoDatos regDoc = (RegistroDocumentoDatos)documents.get(i);
             ContenedorDocumento doc = oServicio.retrieveDocumentInfo("", regDoc.getGuid(), getEntidad(entidad));
             datadocs.add(doc);
           }
         }
      } catch (Exception e) {
    	  logger.error("Error al obtener contenido de documentos de registro [getRegistryDocumentsData][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT);
      }
      return datadocs;
   }


   /**
    * Obtiene un justificante de registro a partir de un número de registro.
    *
    * @param registryNumber Número de registro.
    * @param sessionId Identificador de sesión. Tiene sentido para la aplicación
    * llamante.
    * @return El contenido del justificante de registro.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static byte[] getRegistryReceipt(String sessionId, String registryNumber, String entidad)
   throws RegistroExcepcion {
      byte[] receipt = null;
      try {
    	  ServicioSesionUsuario servicioSesionUsuario = LocalizadorServicios.getServicioSesionUsuario();

    	  // Obtener la información del usuario en sesión
    	  InfoUsuario infoUsuario = servicioSesionUsuario.getInfoUsuario(sessionId, getEntidad(entidad));

    	  // Obtener el registro para el que se quiere descargar el justificante
    	  Registro registro = getRegistry(sessionId, registryNumber, entidad);

    	  // Para obtener el justificante el usuario en sesión tiene que ser el solicitante del registro
    	  if (registro.getSenderId().equals(infoUsuario.getId())) {

	    	  receipt = getDocument(registryNumber, Definiciones.REGISTRY_RECEIPT_CODE, entidad);
	    	  //Log.setGetRegistryReceipt(sessionId, registryNumber);
    	  } else {
    		  throw new RegistroExcepcion(RegistroCodigosError.EC_JUSTIFICANTE_SIN_PERMISOS);
    	  }
      } catch (RegistroExcepcion re) {
    	  logger.error("Error al obtener justificante de registro [getRegistryReceipt][Excepcion]", re);
    	  throw re;
      } catch (Exception e) {
    	  logger.error("Error al obtener justificante de registro [getRegistryReceipt][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
      }
      return receipt;
   }


   /**
    * Obtiene una solicitud de registro a partir de un número de registro.
    *
    * @param registryNumber Número de registro.
    * @return El contenido del justificante de registro.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static byte[] getRegistryRequest(String registryNumber, String entidad)
   throws RegistroExcepcion {
      byte[] receipt = null;
      try {
    	  // Obtener la solicitud firmada
    	  receipt = getDocument(registryNumber, Definiciones.REGISTRY_REQUEST_CODE, entidad);
      } catch (Exception esf) {
    	  // Si no existe la solicitud firmada es porque el tramite no requiere la firma
    	  try {
    		  // Entonces obtener la solicitud no firmada
    		  receipt = getDocument(registryNumber, Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE, entidad);
    	  } catch (Exception esnf) {
    		  logger.error("Error al obtener solicitud registro [getRegistryRequest][Excepcion]", esnf.fillInStackTrace());
    		  throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
    	  }
      }
      return receipt;
   }

   /**
    * Recupera el conjunto de registros que se han consolidado.
    *
    * @return La lista de registros consolidados.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static Registros getConsolidationRegistries(String entidad) throws RegistroExcepcion {

      RegistroDatos registryData = new RegistroDatos();
      Registros basicRegs = new Registros();

      try {
         DynamicRow rowInfo = registryData.getRegistriesByStatus(RegistroEstado.STATUS_CONSOLIDATED, entidad);
         for (int i = 1; i < rowInfo.getRowCount() ; i++) {

        	 RegistroImpl registry = new RegistroImpl();
        	 registryData = (RegistroDatos)rowInfo.getRow(i);

        	 registry.setRegistryNumber(registryData.getRegistryNumber());
        	 registry.setRegistryDate(registryData.getRegistryDate());
        	 registry.setName(registryData.getName());
        	 //registry.setSurName(registryData.getSurName());
        	 registry.setSenderId(registryData.getSenderId());
        	 registry.setEMail(registryData.getEMail());
        	 registry.setTopic(registryData.getTopic());
        	 registry.setStatus(registryData.getStatus());
        	 registry.setAddressee(registryData.getAddressee());
        	 registry.setSenderIdType(registryData.getSenderIdType());
        	 registry.setAdittionalInfo(registryData.getAdditionalInfo());
        	 registry.setOficina(registryData.getOficina());
        	 registry.setNumeroExpediente(registryData.getNumeroExpediente());
        	 registry.setEffectiveDate(registryData.getEffectiveDate());

        	 basicRegs.add(registry);
         }
      }
      catch (Exception e) {
    	  logger.error("Error al obtener registros consolidados [getConsolidationRegistries][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_NO_REGISTRY);
      }

      return basicRegs;
   }

   /**
    * Recupera el conjunto de registros para mostrar que serán
    * todos los que no han generado error.
    *
    * @return La lista de registros.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static Registros getRegistriesToShow(RegistroConsulta query, String entidad) throws RegistroExcepcion {

      RegistroDatos registryData = new RegistroDatos();
      Registros basicRegs = new Registros();

      try {
         DynamicRow rowInfo = registryData.getRegistriesToShow(query, entidad);
         for (int i = 1; i < rowInfo.getRowCount() ; i++) {

        	 RegistroImpl registry = new RegistroImpl();
        	 registryData = (RegistroDatos)rowInfo.getRow(i);

        	 if (registryData.getStatus() != RegistroEstado.STATUS_ERROR) {

        		 registry.setRegistryNumber(registryData.getRegistryNumber());
        		 registry.setRegistryDate(registryData.getRegistryDate());
        		 registry.setEffectiveDate(registryData.getEffectiveDate());
        		 registry.setName(registryData.getName());
        		 registry.setSenderId(registryData.getSenderId());
        		 registry.setTopic(registryData.getTopic());
                 registry.setEMail(registryData.getEMail());
                 registry.setStatus(registryData.getStatus());
                 registry.setAddressee(registryData.getAddressee());
                 registry.setSenderIdType(registryData.getSenderIdType());
                 //registry.setRepresentedName(registryData.getRepresentedName());
                 //registry.setRepresentedId(registryData.getRepresentedId());
                 registry.setAdittionalInfo(registryData.getAdditionalInfo());
                 registry.setOficina(registryData.getOficina());
                 registry.setNumeroExpediente(registryData.getNumeroExpediente());

        		 basicRegs.add(registry);
            }
         }
      }
      catch (Exception e) {
    	  logger.error("Error al obtener registros para mostrar [getRegistriesToShow][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_NO_REGISTRY);
      }

      return basicRegs;
   }

   /**
    * Comprueba si un número de registro tiene documentos asociados.
    *
    * @param registryNumber Número de registro.
    * @return Verdadero si existen documentos asociados al registro, falso
    * en otro caso.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static boolean hasDocuments(String registryNumber, String entidad)
   throws RegistroExcepcion {
      boolean result=false;

      try {
         RegistroDocumentoDatos rdd = new RegistroDocumentoDatos();
         rdd.setRegistryNumber(registryNumber);
         RegistroDocumentos rd = rdd.getRegistryDocuments(entidad);
         if (rd!=null && rd.count()>0)
           result = true;
      } catch (Exception e) {
    	  logger.error("Error al comprobar si el registro tiene documentos asociados [hasDocuments][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT);
      }
      return result;
   }


   /**
    * Recupera el contenido de un documento.
    *
    * @param registryNumber Número de registro.
    * @param code Código del documento.
    * @return El contenido del documento.
    * @throws RegistroExcepcion Si se produce algún error.
    */
   public static ByteArrayOutputStream getDocumentContent(String sessionId, String registryNumber,
		   String code, String entidad)
   throws RegistroExcepcion {
      ByteArrayOutputStream content = new ByteArrayOutputStream();
      try {
         content.write(getDocument(registryNumber,code, entidad));
      } catch (Exception e) {
    	  logger.error("Error al obtener contenido de documento [getDocumentContent][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_GET_DOCUMENT_CONTENT);
      }
      return content;
   }

   /**
    * Obtiene un documento a partir del registro y el código de documento.
    * @param registryNumber Número de registro
    * @param code Código de documento
    * @return byte[] Datos binarios del documento obtenido
    * @throws RegistroExcepcion Si se produce algún error
    */
   public static byte[] getDocument(String registryNumber, String code, String entidad)
   throws RegistroExcepcion {
      byte[] receipt = null;
      try {
    	  ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
    	  RegistroDocumentoDatos rdd = new RegistroDocumentoDatos();
    	  rdd.load(registryNumber, code, entidad);
    	  DocumentoInfo di = oServicio.retrieveDocument(null, rdd.getGuid(), getEntidad(entidad));
    	  receipt = di.getContent();
      } catch (Exception e) {
    	  logger.error("Error al obtener documento [getDocument][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_BAD_REGISTRY_NUMBER);
      }
      return receipt;
   }

   /**
    * Actualiza la fecha efectiva y el estado del registro.
    *
    * @param registry Registro a actualizar.
    *
    * @throws Exception Si se produce algún error.
    */
   	public static void updateEffectiveDateAndStatus(RegistroDatos registry,
		   								  			String entidad) throws Exception {

   		try {
   			registry.updateEffectiveDateAndStatus(entidad);

   			logger.debug("Actualizada la fecha efectiva y el estado del registro [" + registry.getRegistryNumber() + "] en la entidad [" + entidad + "]");
   		}
   		catch (RegistroExcepcion exc) {
   			logger.error("Error al actualizar la fecha efectiva y el estado del registro con numero [" + registry.getRegistryNumber() + "] [updateRegistryData][RegistroExcepcion]", exc.fillInStackTrace());
   			throw exc;
   		}
   		catch (Exception e) {
   			logger.error("Error al actualizar la fecha efectiva y el estado del registro con numero [" + registry.getRegistryNumber() + "] [updateRegistryData][Excepcion]", e.fillInStackTrace());
   			throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_CONSOLIDATED_REGISTRY);
   		}
   	}

   /**
    * Actualiza el estado de un registro.
    *
    * @param registryNumber Número de registro.
    * @throws java.lang.Exception Si se produce algún error.
    */
   public static void setRegistryStatus(String registryNumber, int status, String entidad)
   throws Exception {
      RegistroDatos registry = new RegistroDatos();

      try {
         registry.setStatus(status);
         registry.updateStatus(registryNumber, entidad);
      } catch (RegistroExcepcion exc) {
    	  logger.error("Error al actualizar estado de registro [setRegistryStatus][RegistroExcepcion]", exc.fillInStackTrace());
    	  throw exc;
      } catch (Exception e) {
    	  logger.error("Error al actualizar estado de registro [setRegistryStatus][Excepcion]", e.fillInStackTrace());
    	  throw new RegistroExcepcion(RegistroCodigosError.EC_ERR_CONSOLIDATED_REGISTRY);
      }
   }


   /**
    * Verifica la firma y el contenido de una solicitud de registro
    * @param registryNumber Número de registro
    * @param sessionId Identificador de sesión
    * @param request Solicitud de registro
    * @param signatureHook Conector de firma
    * @param validationHook Conector de validacidación de contenido
    * @throws Exception Si se produce algún error
    */
   /*
   private static void validateRegistryRequest(String registryNumber, String sessionId,
		   byte[] request, String signatureHook, String validationHook, String entidad)
     throws Exception {

     boolean valid, info;
     String code;
     byte[] data;
     RegistroDocumentoDatos document = new RegistroDocumentoDatos();

     valid = true; //crypto.isDocumentContentValidData(sessionId, validationHook, request, "");
     if (!valid) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_CONTENT_REQUEST);
     }

     info = true; //crypto.verifySignatureData(sessionId, signatureHook, request, "");
     if (!info) {
        throw new RegistroExcepcion(RegistroCodigosError.EC_SIGN_REQUEST);
     }

     data = request;//crypto.getDocumentWithoutSignatureData(signatureHook, request, "");

     ServicioRepositorioDocumentosTramitacion oServicio = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
     String guid = oServicio.storeDocument(sessionId, data, "xml", getEntidad(entidad));

     code = Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE;
     document.setCode(code);
     document.setRegistryNumber(registryNumber);
     document.setGuid(guid);
     document.add(entidad);
   }
   */

   /**
    * Método que elimina un registo creado para el caso de error al iniciar un expediente
    * @param sessionId Identificador de sesión
    * @param registryNumber Número de registro asignado
    * @throws RegistroExcepcion En caso de producirse algún error
    */
   public static void deshacerRegistro(String sessionId, String registryNumber, String entidad) throws RegistroExcepcion {

	   RegistroDatos registry = new RegistroDatos();

	   try {
		   // Eliminar los documentos asociados a un registro
		   deleteRegistryDocuments(registryNumber, entidad);

		   // Establecer el registro como erróneo
		   registry.setRegistryNumber(registryNumber);
		   registry.setStatus(RegistroEstado.STATUS_ERROR);
		   registry.setEffectiveDate(null);

		   updateEffectiveDateAndStatus(registry, entidad);

		   //deleteRegistry(registryNumber, entidad);
		   //createNullRegistry(registryNumber, entidad);

		   cleanTempDocuments(sessionId, entidad);
	   }
	   catch (RegistroExcepcion exc) {
		   logger.error("Error al deshacer registro no valido [deshacerRegistro][RegistroExcepcion]", exc.fillInStackTrace());
		   throw exc;
	   }
	   catch (Exception e) {
		   logger.error("Error al deshacer registro no valido [deshacerRegistro][Excepcion]", e.fillInStackTrace());
		   throw new RegistroExcepcion(RegistroCodigosError.EC_UNDO_REGISTRY);
	   }
   }

   /**
    * Método que iniciar un expediente a partir de los datos de un registro
    * @param sessionId Identificador de sesión
    * @param justificante Datos del justificante de registro
    * @param tramiteId Identificador de trámite
    * @param datosEspecificos Datos específicos de la solicitud
    * @param idProcedimiento Identificador del procedimiento a iniciar
    * @return boolean Verdadero si se ha iniciado, false si no
    * @throws RegistroExcepcion En caso de producirse algún error
    */
   public static boolean iniciarExpediente(String sessionId, String justificante, String tramiteId,
		   String datosEspecificos, String idProcedimiento, String entidad)
	throws RegistroExcepcion {
		try {
			XmlDocument xmlDoc = new XmlDocument();
		   	XmlElement root, genericData, specificData, registryData;
			xmlDoc.createFromStringText(justificante);
			root = xmlDoc.getRootElement();

			ServicioRepositorioDocumentosTramitacion oServicioRde = LocalizadorServicios.getServicioRepositorioDocumentosTramitacion();
			ServicioTramitacion oServicioTramitacion = LocalizadorServicios.getServicioTramitacion();

	        genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA);
	        String name = genericData.getChildElement(Definiciones.SENDER_NAME).getValue();
	        String mail = genericData.getChildElement(Definiciones.SENDER_EMAIL).getValue();
	        //String surname = genericData.getChildElement(Definiciones.SENDER_SURNAME).getValue();
	        genericData = root.getDescendantElement(Definiciones.XPATH_SENDER_DATA + "/" + Definiciones.ID);
	        String id = genericData.getChildElement(Definiciones.SENDER_ID).getValue();
	        specificData = root.getDescendantElement(Definiciones.XPATH_SPECIFIC_DATA);

	        String domicilio="", localidad="", provincia="", pais = "", telefono = "", movil = "", cp="", solicitar = "", deu = "";
	        boolean presencial = true;
	        try{
	        	if (specificData.getChildElement(Definiciones.DOMICILIO_NOTIFICACION) != null)
	        		domicilio = specificData.getChildElement(Definiciones.DOMICILIO_NOTIFICACION).getValue();
	        	if (specificData.getChildElement(Definiciones.LOCALIDAD) != null)
	        		localidad = specificData.getChildElement(Definiciones.LOCALIDAD).getValue();
	        	if (specificData.getChildElement(Definiciones.PROVINCIA) != null)
	        		provincia = specificData.getChildElement(Definiciones.PROVINCIA).getValue();
	        	if (specificData.getChildElement(Definiciones.PAIS) != null) {
	        		pais = specificData.getChildElement(Definiciones.PAIS).getValue();
	        	}
	        	else {
	        		pais = "España";
	        	}
	        	if (specificData.getChildElement(Definiciones.TELEFONO) != null)
	        		telefono = specificData.getChildElement(Definiciones.TELEFONO).getValue();
	        	if (specificData.getChildElement(Definiciones.TELEFONO_MOVIL) != null)
	        		movil = specificData.getChildElement(Definiciones.TELEFONO_MOVIL).getValue();
	        	if (specificData.getChildElement(Definiciones.SOLICITAR_ENVIO) != null)
	        		solicitar = specificData.getChildElement(Definiciones.SOLICITAR_ENVIO).getValue();
	        	if (specificData.getChildElement(Definiciones.DEU) != null)
	        		deu = specificData.getChildElement(Definiciones.DEU).getValue();
	        	if (specificData.getChildElement(Definiciones.CP) != null)
	        		cp = specificData.getChildElement(Definiciones.CP).getValue();
	        	if (solicitar.equals("Si") && !isBlank(deu))
	        		presencial = false;
	        }catch(Exception ex){
	        	presencial = false;
	        }

	        InteresadoExpediente[] interested = new InteresadoExpediente[1];
	        InteresadoExpediente interestedPerson;
	        //InterestedPerson interestedPerson;
	        interestedPerson = new InteresadoExpediente();
	        interestedPerson.setIndPrincipal(InteresadoExpediente.IND_PRINCIPAL);
        	//interestedPerson.setName(name.trim() + " " + surname);
	        interestedPerson.setName(name);
        	interestedPerson.setNifcif(id);
        	interestedPerson.setThirdPartyId(null);
        	if (movil != null && !"".equals(movil))
        		interestedPerson.setMobilePhone(movil);
        	if (telefono != null && !"".equals(telefono))
        		interestedPerson.setPhone(telefono);

	        if (!presencial){
	           	interestedPerson.setNotificationAddressType(InteresadoExpediente.IND_TELEMATIC);
	        	interestedPerson.setTelematicAddress(deu);
	        }else{
	        	interestedPerson.setNotificationAddressType(InteresadoExpediente.IND_POSTAL);
	        	if (mail != null && !"".equals(mail))
	        		interestedPerson.setTelematicAddress(mail);

	        }

	        interestedPerson.setPlaceCity(localidad);
	        interestedPerson.setPostalCode(cp);
	        interestedPerson.setPostalAddress(domicilio);
	        if (isBlank(pais)) {
	        	interestedPerson.setRegionCountry(provincia);
	        }
	        else {
	        	interestedPerson.setRegionCountry(provincia + " / " + pais);
	        }

			interested[0] = interestedPerson;

			genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.TOPIC);
	        String asunto = genericData.getChildElement(Definiciones.DESCRIPTION).getValue();
	        registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);
	        String numero_registro = registryData.getChildElement(Definiciones.REGISTRY_NUMBER).getValue();
	        String fecha = registryData.getChildElement(Definiciones.REGISTRY_DATE).getValue() + " " +
	        				registryData.getChildElement(Definiciones.REGISTRY_HOUR).getValue();
	        String pattern = Definiciones.DEFAULT_DATE_FORMAT + " " + Definiciones.DEFAULT_HOUR_FORMAT;
	        SimpleDateFormat myDateFormat = new SimpleDateFormat(pattern);
	        Date date = myDateFormat.parse(fecha);

	        String cod = tramiteId;
	        DatosComunesExpediente commonData = new DatosComunesExpediente();
	        commonData.setFechaRegistro(date);
	        commonData.setInteresados(interested);
	        commonData.setNumeroRegistro(numero_registro);
	        commonData.setTipoAsunto(cod);

			genericData = root.getDescendantElement(Definiciones.XPATH_GENERIC_DATA + "/" + Definiciones.ADDRESSEE);

			// Datos específicos del procedimiento
			String specificDataXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" ?>" +
								"<" + Definiciones.TAG_INICIAR_EXPEDIENTE_DATOS_ESPECIFICOS + ">" +
								datosEspecificos +
								"</" + Definiciones.TAG_INICIAR_EXPEDIENTE_DATOS_ESPECIFICOS + ">";

			// Obtener los documentos del justificante
			Map docsMap = new HashMap();

			XmlElement docsElement = root.getDescendantElement(Definiciones.XPATH_DOCUMENTS);
			XmlElements xmlElements = docsElement.getChildElements();
			for(int i = 1; i <= xmlElements.getCount(); i++) {

				XmlElement docElement = xmlElements.getItem(i);

				String code = docElement.getChildElement(Definiciones.CODE).getValue();
				String docName = docElement.getChildElement(Definiciones.NAME).getValue();
				docsMap.put(code, docName);
			}

			// Lista de documentos del expediente
			List documents = new ArrayList();

			// Si en el trámite existe la solicitud firmada (requerida), con este documento se iniciará el expediente,
			// y si al contrario, el trámite no ha requerido que se firme la solicitud, se enviará la solicitud sin firmar
			boolean solicitudFirmada = false;
			DocumentoExpediente solicitudNoFirmada = null;

			RegistroDocumentos rds = RegistroDocumentoManager.getRegistryDocuments(numero_registro, entidad);
			for(int i=0; i<rds.count(); i++) {

				RegistroDocumento rd = (RegistroDocumento)rds.get(i);
				String codigo = rd.getCode();
				DocumentoInfo di = oServicioRde.retrieveDocument(sessionId, rd.getGuid(), getEntidad(entidad));
				String extension = di.getExtension();

				DocumentoExpediente document = new DocumentoExpediente();

				if (codigo.equals(Definiciones.REGISTRY_RECEIPT_CODE)) {
					codigo = "Justificante";
				}
				else if (codigo.equals(Definiciones.REGISTRY_REQUEST_CODE)) {
					codigo = "Solicitud Registro";
					solicitudFirmada = true;
				}
				else if (codigo.equals(Definiciones.REGISTRY_REQUEST_NOTSIGNED_CODE)) {
					codigo = "Solicitud Registro";
					solicitudNoFirmada = document;
				}
				else {
					codigo = "Anexo a Solicitud";
				}

				if (extension.equalsIgnoreCase("xml"))
					extension = "txt";

				document.setCode(codigo);
				document.setContent(di.getContent());
				document.setExtension(extension);
				document.setId(null);
				document.setLenght(di.getContent().length);

				String docName = (String) docsMap.get(rd.getCode());
				if (docName != null) {
					document.setName(docName);
				}
				else {
					document.setName(asunto);
				}

				documents.add(document);
			}

			// Si la solicitud firmada existe
			// eliminar la solicitud no firmada de los documentos que se envían para iniciar expediente
			if (solicitudFirmada) {
				documents.remove(solicitudNoFirmada);
			}

			DocumentoExpediente[] documentsExpediente = new DocumentoExpediente[documents.size()];
			for(int j=0; j<documents.size(); j++)
				documentsExpediente[j] = (DocumentoExpediente)documents.get(j);

			return oServicioTramitacion.iniciarExpediente(entidad, commonData, specificDataXML, documentsExpediente);
		}
		catch (Exception e) {

			logger.error("Error al iniciar expediente a partir de un registro [iniciarExpediente][Excepcion]", e.fillInStackTrace());
			throw new RegistroExcepcion(RegistroCodigosError.EC_INIT_EXPEDIENT);
		}
	}

   private static boolean checkVirus(byte[] fichero) throws Exception {
	   boolean resultado = true;
	   try {
		   ServicioAntivirus oServicio = LocalizadorServicios.getServicioAntivirus();
		   resultado = oServicio.comprobarFichero(fichero, true);
	   }
	   catch (AntivirusException ae) {

		   if (ae.getErrorCode() != AntivirusException.EXC_NO_ANTIVIRUS) {
			   logger.error("Error al comprobar si el fichero tiene virus [checkVirus][Exception]", ae.fillInStackTrace());
		   }
		   throw ae;
	   }
	   catch (Exception e) {
		   logger.error("Error al comprobar si el fichero tiene virus [checkVirus][Exception]", e.fillInStackTrace());
		   throw e;
	   }
	   return resultado;
   }

	public static boolean isBlank(String cadena){
		if (cadena == null || cadena.equals(""))
			return true;
		return false;
	}

	protected static String getNumeroRegistro(String xml) {
		try {
			XmlDocument xmlDoc = new XmlDocument();
			XmlElement root, registryData;
			xmlDoc.createFromStringText(xml);
			root = xmlDoc.getRootElement();
			registryData = root.getDescendantElement(Definiciones.XPATH_REGISTRY_DATA);
			return registryData.getChildElement(Definiciones.REGISTRY_NUMBER).getValue();
		}catch(Exception e){
			logger.error("Error al obtener numero de registro del xml [getNumeroRegistro][Excepcion]", e.fillInStackTrace());
			return null;
		}
	}

	private static Entidad getEntidad(String entidad){
		Entidad oEntidad = new Entidad();
		oEntidad.setIdentificador(entidad);
		return oEntidad;
	}

}