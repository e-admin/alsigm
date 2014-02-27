package ieci.tecdoc.sgm.migration.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import ieci.tecdoc.sgm.migration.config.Config;
import ieci.tecdoc.sgm.migration.exception.MigrationException;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentDto;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentPageDto;
import ieci.tecdoc.sgm.migration.mgr.dto.DocumentsPagesDto;
import ieci.tecdoc.sgm.registropresencial.ws.server.Document;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfo;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldInfoSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.server.Fields;
import ieci.tecdoc.sgm.registropresencial.ws.server.FieldsSearchCriteria;
import ieci.tecdoc.sgm.registropresencial.ws.server.Folder;
import ieci.tecdoc.sgm.registropresencial.ws.server.Page;

public class Utils {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(Utils.class);

	/**
	 * Devuelve un objeto de tipo FieldsSearchCriteria con los criterios de búsqueda de registros
	 * @return - (FieldsSearchCriteria) Criterios de búsqueda
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	public static FieldsSearchCriteria getFieldsCriteria() throws MigrationException {
		FieldsSearchCriteria fieldsCriteria = new FieldsSearchCriteria();
		fieldsCriteria.setFields(getFieldInfoCriteria());
		return fieldsCriteria;
	}
	
	/**
	 * Devuelve un array de campos de filtrado de búsqueda de registros
	 * @return (FieldsSearchCriteria[]) Campos de Criterios de búsqueda
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static FieldInfoSearchCriteria[] getFieldInfoCriteria() throws MigrationException {
		FieldInfoSearchCriteria[] fieldsInfo = new FieldInfoSearchCriteria[1];
		FieldInfoSearchCriteria fieldInfo = new FieldInfoSearchCriteria();
		fieldInfo.setFieldId(Config.getInstance().getFieldIdConsolidated());
		fieldInfo.setOperator(Constantes.OPERATOR_NOT_CONSOLIDATED);
		fieldInfo.setValue(Constantes.FLG_CONSOLIDATED);
		fieldsInfo[0] = fieldInfo;
		return fieldsInfo;	
	}
	
	/**
	 * Muestra por log y/o por consola el mensaje que se pasa como parámetro
	 * dependiendo del flg TRACE_SYSTEM del fichero de configuración de la aplicación
	 * @param message - (String) Texto a mostrar por log y/o consola
	 */
	public static void trace(String message) {
		if (logger.isDebugEnabled()) {
			logger.debug(message);
		}
		try {
			if(Config.getInstance().getTraceSystem()) 
				System.out.println(message);
		} catch (MigrationException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Devuelve un array de campos de filtrado de búsqueda de registros
	 * @param fields - (Fields) Canpos de filtrado
	 * @param bookId - (String) Identificador de librode búsqueda
	 * @return - FieldInfo[] Array de campos de filtrado
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	public static FieldInfo[] getFields(Fields fields, String bookId) throws MigrationException {
		FieldInfo[] fieldsInfo = fields.getFields();
		FieldInfo fieldInfo = null;
		String fldId = null;
		HashMap<String, String> map = new HashMap<String, String>(); 
		for(int i = 0; i < fieldsInfo.length; i++) {
			fieldInfo = (FieldInfo)fieldsInfo[i];
			if(fieldInfo.getFieldId().startsWith(Constantes.FLD)){
				fldId = fieldInfo.getFieldId().substring(Constantes.FLD.length());
				// Se elimina el campo Fld6 (ESTADO) del mapeo de campos, puesto que este lo introduce el SW automáticamente
				if(isFldIdIncluyed(fldId)) map.put(fldId, getValue(fldId, fieldInfo.getValue()));
			}
		}
		// Se incluye el Tipo de Transporte
		map.put(getIdTipoTransporte(bookId), Config.getInstance().getDestinoIdBBDDScrTTElectronico());
		
		// Se incluye el Tipo de Registro ??? [Incidencia del SW de Registro], se tiene que enviar un null
		map = putTipoRegistroOriginalEntrada(map, bookId);
		
		// Se incluye Fecha de registro original, ??? [Incidencia del SW de Registro], se tiene que enviar un null
		map = putFechaRegistroOriginalEntrada(map, bookId);
		
		// Modificamos la fecha de Trabajo del Registro por la fecha actual
		map.put(getIdFechaTrabajo(bookId), getDateJob());
		
		return getFieldsInfo(map);
	}
	
	/**
	 * Devuelve un array de campos de filtrado de búsqueda de registros
	 * @param fields - (Fields) Canpos de filtrado
	 * @param bookId - (String) Identificador de librode búsqueda
	 * @return - FieldInfo[] Array de campos de filtrado
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	public static FieldInfo[] getFieldsOrigen(Fields fields, String bookId) throws MigrationException {
		FieldInfo[] fieldsInfo = fields.getFields();
		FieldInfo fieldInfo = null;
		String fldId = null;
		HashMap<String, String> map = new HashMap<String, String>(); 
		for(int i = 0; i < fieldsInfo.length; i++) {
			fieldInfo = (FieldInfo)fieldsInfo[i];
			if(fieldInfo.getFieldId().startsWith(Constantes.FLD)){
				fldId = fieldInfo.getFieldId().substring(Constantes.FLD.length());
				map.put(fldId, getValue(fldId, fieldInfo.getValue()));
			}
		}
		return getFieldsInfo(map);
	}
	
	/**
	 * Se comprueba que el id del Fld se corresponde con el campo Fld6 (ESTADO)
	 * @param fldId - (String) Identificador del campo
	 * @return (boolean) - Devuelve true si es 6 y false si no lo es
	 */
	private static boolean isFldIdIncluyed(String fldId) {
		StringTokenizer token = new StringTokenizer(Constantes.FIELD_NOT_INCLUDED_CONSOLIDATION, ";");
		while(token.hasMoreTokens()) {
			if(fldId.equals(token.nextToken())) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Recupera el identificador del campo Tipo de transporte en función si es de entrada o salida.
	 * Estos datos se obtienen del fichero de configuración de la aplicación
	 * @param bookId - Identificador del libro
	 * @return (String) Identificador del tipo de transporte
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static String getIdTipoTransporte(String bookId) throws MigrationException {
		if(bookId.equalsIgnoreCase(Config.getInstance().getOrigenIdBookEntrada()))
			return Config.getInstance().getDestinoIdTipoTransporteEntrada();
		else return Config.getInstance().getDestinoIdTipoTransporteSalida();	
	}
	
	/**
	 * Estable null al campo Tipo de Registro Original del libro de entrada puesto que el SW comprueba este dato
	 * @param map - (HashMap) - Contiene los campos del registro
	 * @param bookId - Identificador del libro
	 * @return (HashMap<String, String>) - [Id del campo, valor]
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static HashMap<String, String> putTipoRegistroOriginalEntrada(HashMap<String, String> map, String bookId) throws MigrationException {
		if(bookId.equalsIgnoreCase(Config.getInstance().getOrigenIdBookEntrada()))
			map.put((String)Config.getInstance().getDestinoIdTipoRegistroEntrada(), null);
		return map;
	}
	
	/**
	 * Estable null al campo Fecha de Registro Original del libro de entrada puesto que el SW comprueba este dato
	 * @param map - (HashMap) - Contiene los campos del registro
	 * @param bookId - Identificador del libro
	 * @return (HashMap<String, String>) - [Id del campo, valor]
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static HashMap<String, String> putFechaRegistroOriginalEntrada(HashMap<String, String> map, String bookId) throws MigrationException {
		if(bookId.equalsIgnoreCase(Config.getInstance().getOrigenIdBookEntrada()))
			map.put((String)Config.getInstance().getDestinoIdFechaRegistroOriginalEntrada(), null);
		return map;
	}
	
	
	/**
	 * Recupera el identificador del campo Fecha de trabajo en función si es de entrada o salida.
	 * Estos datos se obtienen del fichero de configuración de la aplicación
	 * @param bookId - Identificador del libro
	 * @return (String) Identificador del Fecha de trabajo
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static String getIdFechaTrabajo(String bookId) throws MigrationException {
		if(bookId.equalsIgnoreCase(Config.getInstance().getOrigenIdBookEntrada()))
			return Config.getInstance().getDestinoIdFechaTrabajoRegistroEntrada();
		else return Config.getInstance().getDestinoIdFechaTrabajoRegistroSalida();	
	}
	
	/**
	 * Método que parsea los campos en formato [xx - zz] y devuelve xx
	 * Se hace esto debido a que los datos que devuelve el SW vienen en el formato indicado
	 * y hay que recuperar solamente la parte inicial del String hasta el separador "-" 
	 * Los campos que hay que parsear son Oficina de Registro (Fld5), Origen (Fld7), Destino (Fld8) y Tipo de Asunto (Fld16) 
	 * @param fldId - Identificador del campo
	 * @param value - Valor del campo
	 * @return(String) Valor del campo parseado
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static String getValue(String fldId, String value) throws MigrationException {
		StringTokenizer token = new StringTokenizer(Constantes.FIELDS_FORMAT, ";");
		StringTokenizer tokenValue = null; 
		while(token.hasMoreTokens()) {
			if(fldId.equals(token.nextToken())) {
				if(value != null && !value.equals("")) {
					tokenValue = new StringTokenizer(value, Constantes.VALUE_SEPARATOR);
					return tokenValue.nextToken().trim();
				}
			}
		}
		return value;
	}	
	
	
	/**
	 * Recupera un array de campos a partir de un HasMap
	 * @param hashMap - Mapa de propiedades de tipo [IdFld, Valor]
	 * @return FieldInfo[] - Array de campos fields
	 */
	private static FieldInfo[] getFieldsInfo(HashMap<String, String> hashMap) {
		
		FieldInfo[] fieldsInfo = new FieldInfo[hashMap.size()];
		@SuppressWarnings("rawtypes")
		Iterator it = hashMap.entrySet().iterator();
		int i = 0;
		while (it.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry e = (Map.Entry)it.next();
			fieldsInfo[i] = getFieldInfo((String)e.getKey(), (String)e.getValue());
			i++;
		}
		return fieldsInfo;
	}
	
	/**
	 * Crea un objeto de tipo FieldInfo con el identificador y valor del campo
	 * @param fieldId -Identificador del cammpo
	 * @param value - Valor del campo
	 * @return FieldInfo - Objeto con la información de los campos
	 */
	private static FieldInfo getFieldInfo(String fieldId, String value) {
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(fieldId);
		fieldInfo.setValue(value);
		Utils.trace("Fld" + fieldId + ": " + value);
		return fieldInfo;
	}
	
	
	/**
	 * Parsea un Array de objetos de tipo Document a partir de un objeto de tipo DocumentsPagesDto 
	 * @param documentsPage - Contiene la información de los documentos del registro
	 * @return Document[] - Array de Documentos del registro
	 */
	public static Document[] getDocuments(DocumentsPagesDto documentsPage) {
		Document[] documents = new Document[getTotalDocs(documentsPage)];
		if(documentsPage.getDocuments() != null) {
			DocumentPageDto[] documentPageDto = new DocumentPageDto[documentsPage.getDocuments().length];
			int contador = 0;
			for(int i = 0; i < documentsPage.getDocuments().length; i++) {
				documentPageDto[i] = documentsPage.getDocuments()[i];
				contador = getDocument(documentPageDto[i], documents, contador);
			}
		}
		return documents;
		
	}
	
	/**
	 * Devuelve el total de documentos del registro
	 * @param documentPageDto - Contiene información de las páginas y documentos del registro   
	 * @param documents - Contiene información de los documentos del registro
	 * @param contador - Contador del total de documentos
	 * @return int - Total de documentos del registro
	 */
	public static int getDocument(DocumentPageDto documentPageDto, Document[] documents, int contador) {
		Document document = new Document();
		DocumentDto documentDto = new DocumentDto();
		document.setDocumentName(documentPageDto.getDocumentName());
		if(documentPageDto.getDocumentDto() != null && documentPageDto.getDocumentDto().length > 0) {
			for(int i = 0; i < documentPageDto.getDocumentDto().length; i++) {
				documentDto = documentPageDto.getDocumentDto()[i];
				if(documentPageDto.getDocumentDto().length > 0) document = new Document();
				document.setDocumentName(documentPageDto.getDocumentName());
				document.setDocumentContentB64(documentDto.getContentB64());
				document.setExtension(documentDto.getExtension());
				document.setPageName(documentDto.getPageName());
				documents[contador] = document;
				contador++;
			}
		}
		return contador;
	}
	
	/**
	 * Devuelve el folder modificando el campo CONSOLIDADO a valor 1 
	 * @param folderDestino - Carpeta del registro recuperado de Sigem UAM 
	 * @return Folder - Carpeta con los campos modificados
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	public static Folder modifyFieldConsolidated(Folder folderDestino) throws MigrationException {
		Fields fields = folderDestino.getFields();
		FieldInfo[] fieldsInfo = modify(fields.getFields(), Constantes.FLG_CONSOLIDATED);
		fields.setFields(fieldsInfo);
		folderDestino.setFields(fields);
		return folderDestino;
	}
	
	
	
	public static Folder modifyFieldConsolidatedDeleteDocument(Folder folderOrigen) throws MigrationException {
		Fields fields = folderOrigen.getFields();
		FieldInfo[] fieldsInfoAux = new FieldInfo[2];
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(Constantes.FLD_BORRADO_DOCUMENTOS);
		fieldInfo.setValue(Constantes.FLG_CONSOLIDATED);
		fieldsInfoAux[0] = fieldInfo;
		fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(Config.getInstance().getFieldIdConsolidated());
		fieldInfo.setValue(Constantes.FLG_CONSOLIDATED);
		fieldsInfoAux[1] = fieldInfo;
		fields.setFields(fieldsInfoAux);
		folderOrigen.setFields(fields);
		return folderOrigen;
	}
	
	
	/**
	 * Modifica el campo CONSOLIDADO a NO_CONSOLIDADO (0)
	 * @param folderDestino - Carpeta del registro recuperado de Sigem UAM 
	 * @return Folder - Carpeta con los campos modificados
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	public static Folder modifyFieldNotConsolidated(Folder folderDestino) throws MigrationException {
		Fields fields = folderDestino.getFields();
		modify(fields.getFields(), Constantes.FLG_NOT_CONSOLIDATED);
		return folderDestino;
	}
	

	/**
	 * Método que modifica el campo CONSOLIDADO si el FldId se encuentra en el array de campos que se pasan como
	 * parámetro, en caso de que no lo encuentre se agrega como nuevo campo [FldId, 1/0]
	 * @param fieldsInfo - Array con los campos del registro
	 * @param flg - Flag que establece el campo CONSOLIDADO a 1 o 0
	 * @return FieldInfo[] - Array de camnpos modificados
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static FieldInfo[] modify(FieldInfo[] fieldsInfo, String flg) throws MigrationException {
		FieldInfo fieldInfo = null;
		boolean isConsolidated = false;
		for(int i = 0; i < fieldsInfo.length; i++) {
			fieldInfo = fieldsInfo[i];
			if(fieldInfo.getFieldId().equalsIgnoreCase(Config.getInstance().getFieldIdConsolidated())) {
				fieldInfo.setValue(flg);
				isConsolidated = true;
				break;
			}
		}
		if(!isConsolidated) {
			fieldsInfo = pushFieldsInfo(flg);
		}
		return fieldsInfo;
	}
	
	/**
	 * Devuelve un nuevo array de campos con el campo CONSOLIDADO añadido
	 * @param flg - Flag que establece el campo CONSOLIDADO a 1 o 0
	 * @return FieldInfo[] - Array de camnpos modificados
	 * @throws MigrationException - Excepción lanzada en caso de error
	 */
	private static FieldInfo[] pushFieldsInfo(String flg) throws MigrationException {
		FieldInfo[] fieldsInfoAux = new FieldInfo[1];
		fieldsInfoAux[0] = getFieldConsolidated(flg);
		return fieldsInfoAux;
	}
	
	/**
	 * Recupera el total de documentos a partir del objeto DocumentsPagesDto
	 * @param documentsPage - Contiene información de las páginas y documentos del registro
	 * @return int - Total de documentos
	 */
	private static int getTotalDocs(DocumentsPagesDto documentsPage) {
		int iTotal = 0;
		if(documentsPage.getDocuments() != null) {
			for( int i = 0;  i < documentsPage.getDocuments().length; i++) {
				if((documentsPage.getDocuments()[i]).getDocumentDto() != null && (documentsPage.getDocuments()[i]).getDocumentDto().length > 0) {
					for(int z = 0; z < (documentsPage.getDocuments()[i]).getDocumentDto().length; z++) {
						iTotal++;
					}
				}
			}
		}
		return iTotal;
	}
	
	/**
	 * Establece el FldId y el valor del campo CONSOLIDADO
	 * @param flg -  Flag que establece el campo CONSOLIDADO a 1 o 0
	 * @return FieldInfo - Campo CONSOLIDADO
	 * @throws MigrationException -  Excepción lanzada en caso de error
	 */
	private static FieldInfo getFieldConsolidated(String flg) throws MigrationException {
		FieldInfo fieldInfo = new FieldInfo();
		fieldInfo.setFieldId(Config.getInstance().getFieldIdConsolidated());
		fieldInfo.setValue(flg);
		return fieldInfo;
	}
	
	/**
	 * Formatea a tipo fecha (dd/MM/yyyy hh:mm:ss) un dato fecha en milisegundos  
	 * @param now - Fecha en milisedundos
	 * @return - String con la fecha en el formato indicado anteriormente
	 */
	public static String getDateProcess(long now) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);
        DateFormat formatter = new SimpleDateFormat(Constantes.FORMAT_DATE_SECONDS);
        return formatter.format(calendar.getTime()); 
	}
	
	/**
	 * Formatea la fecha actual a tipo fecha (dd-MM-yyyy) 
	 * @return - String con la fecha en el formato indicado anteriormente
	 */
	private static String getDateJob() {
		SimpleDateFormat date = new SimpleDateFormat(Constantes.FORMAT_DATE);
		return date.format(new Date());
	}
	
	/**
	 * Convierte la fecha que se pasa como parámetro a formato (dd 'de' MMMM 'de' yyyy)
	 * @param fecha - Fecha en formato (dd/MM/yyyy hh:mm:ss)
	 * @return - String con la fecha en el formato indicado anteriormente
	 * @throws ParseException - Excepción lanzada en caso de error en el parseo de la fecha
	 */
	public static String getDateResult(String fecha) throws ParseException {
		SimpleDateFormat date = new SimpleDateFormat(Constantes.FORMAT_DATE_SECONDS);
		Date dateFormat = date.parse(fecha);
		SimpleDateFormat dateResult = new SimpleDateFormat(Constantes.FORMAT_DATE_MOUNTH);
		return dateResult.format(dateFormat);
	}
	
	/**
	 * Método que muestra por log los datos del documento
	 * @param contentB64 - String con el contenido del fichero
	 * @param documentName - String con el nombre del documento
	 * @param page - Objecto Page con los datos de la página del documento
	 */
	public static void traceDocs(String contentB64, String documentName, Page page) {
		//trace("Contenido documento B64: " + contentB64);
		trace("Nombre de documento: " + documentName);
		trace("Extensión de documento: " + page.getLoc());
		trace("Nombre de página: " + page.getPageName());
	}
	
	
	/**
	 * Devuelve la fecha en el momento en el que se inicia el proceso de migración
	 * Muestra en el log la fecha del momento actual
	 * @return - (long) Devuelve la fecha en milisegundos
	 */
	public static long timeInit() {
		long millis = System.currentTimeMillis();
		/*String time = "Tiempo de ejecución [INICIO]: " + String.format("%d min, %d sec", 
			    TimeUnit.MILLISECONDS.toMinutes(millis),
			    TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));*/
		
		int seconds = (int) (millis / 1000) % 60 ;
		int minutes = (int) ((millis / (1000*60)) % 60);
		int hours   = (int) (((millis / (1000*60*60)) % 24) + 1);
		
		String time = "Tiempo de ejecución [INICIO]: " + hours + " horas, " + minutes + " minutos, " + seconds + " segundos";
		
		trace(time);
		return System.currentTimeMillis();
	}
	
	/**
	 * Devuelve la fecha en el momento en el que se para el proceso de migración
	 * Muestra en el log la fecha del momento actual y el tiempo resultante de la migración
	 * @param millis - (long) Fecha de arranque del proceso de migración en milisegundos
	 * @return - (long) Fecha en milisegundos
	 */
	public static long timeStop(long millis){
		long resultado = millis - System.currentTimeMillis();
		/*String time = "Tiempo de ejecución [FIN]: " + String.format("%d min, %d sec", 
			    	  TimeUnit.MILLISECONDS.toMinutes(resultado),
			          TimeUnit.MILLISECONDS.toSeconds(resultado) - 
			          TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(resultado)));
		*/
		
		int seconds = (int) (System.currentTimeMillis() / 1000) % 60 ;
		int minutes = (int) ((System.currentTimeMillis() / (1000*60)) % 60);
		int hours   = (int) (((System.currentTimeMillis() / (1000*60*60)) % 24) + 1);
		
		String secondsResultado = !String.valueOf((int) (resultado / 1000) % 60).equals("0")? String.valueOf((int) (resultado / 1000) % 60) : "0";
		String minutesResultado = !String.valueOf((int) ((resultado / (1000*60)) % 60)).equals("0")? String.valueOf((int) ((resultado / (1000*60)) % 60)) : "0";
		String hoursResultado   = !String.valueOf((int) ((resultado / (1000*60*60)) % 24)).equals("0")? String.valueOf((int) ((resultado / (1000*60*60)) % 24)) : "0";
		
		String time = "Tiempo de ejecución [FIN]: " + hours + " horas, " + minutes + " minutos, " + seconds + " segundos";
		trace(time);
		time = "Tiempo resultante: " + hoursResultado.replaceAll("-", "") + " horas, " + minutesResultado.replaceAll("-", "") + " minutos, " + secondsResultado.replaceAll("-", "") + " segundos";
		trace(time);
		return System.currentTimeMillis();
	}
	
	public static void main(String[] args) {
		trace("sojdfjidso");
	}
}
