package ieci.tdw.ispac.ispaclib.gendoc.parser;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.gendoc.openoffice.OpenOfficeHelper;
import ieci.tdw.ispac.ispaclib.tageval.ITagTranslator;
import ieci.tdw.ispac.ispaclib.templates.TemplateDocumentInfo;
import ieci.tdw.ispac.ispaclib.templates.TemplateGraphicInfo;
import ieci.tdw.ispac.ispaclib.templates.TemplateTableInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sun.star.container.XIndexAccess;
import com.sun.star.lang.DisposedException;
import com.sun.star.lang.XComponent;
import com.sun.star.sheet.XCellRangeData;
import com.sun.star.sheet.XSpreadsheet;
import com.sun.star.sheet.XSpreadsheetDocument;
import com.sun.star.table.XCellRange;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;

/**
 * Combinador de documentos. Su labor consiste buscar y sustituir las etiquetas de la plantilla por
 * sus valores correspondientes. Una vez terminada la combinaci&oacute;n guarda el documento resultante.
 *
 * DocumentParser utiliza los servicios de ofim&aacute;tica del servidor OpenOffice para realizar
 * la combinaci&oacute;n.
 *
 */
public class DocumentParser
{
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(DocumentParser.class);

	private OpenOfficeHelper ooHelper = null;

	//ESTE PATRON RECOGE TEXTO FUERA DE UN TAG
	//private static final String ISPACTAG="<[ \\t\\n\\x0B\\f\\r]*ispactag[ \\t\\n\\x0B\\f\\r]+.*/>";

	//ESTE PATRON NO ACEPTA PARENTESIS DENTRO DE LOS TAG, NECESARIO PARA CONSULTAS DEL ESTILO WHERE ... IN (...)
	//private static final String ISPACTAG="<[ \\t\\n\\x0B\\f\\r]*ispactag[ \\t\\n\\x0B\\f\\r]+[^(/>)]*/>";

	//ESTE PATRON NO ACEPTA EL CARACTER / DENTRO DE UN TAG, p ej: para el patron de una fecha: dateFormat="dd/MM/yyyy".
	//private static final String ISPACTAG="<[ \\t\\n\\x0B\\f\\r]*ispactag[ \\t\\n\\x0B\\f\\r]+[^/>]*/>";

	private static final String ISPACTAG=  "<[ \\t\\n\\x0B\\f\\r]*ispactag[ \\t\\n\\x0B\\f\\r]+[^>]*/>";

	//private static final String TEMPLATE_ATTRIBUTE = "template";

	//private static final String ISPACTAG_TEMPLATE="<[ \\t\\n\\x0B\\f\\r]*ispactag[ \\t\\n\\x0B\\f\\r]+"+TEMPLATE_ATTRIBUTE+"[^(/>)]*/>";

	private final int DEFAULT_TAGS_NESTING_LEVEL = 2;

	/**
	 * Documento de salida en formato <code>OpenOffice</code>
	 */
	public static final int OpenOffice = 1;

	/**
	 * Documento de salida en formato <code>RTF</code>
	 */
	public static final int RichTextFormat = 2;

	/**
	 * Documento de salida en formato <code>Microsoft Word</code>
	 */
	public static final int MicrosoftWord = 3;

	/**
	 * Documento de salida en formato <code>PDF</code>
	 */
	public static final int PDFWriter = 4;

	/**
	 * Documento de salida en formato <code>Microsoft Excel</code>
	 */
	public static final int MicrosoftExcel = 5;


	/**
	 * Inicializa el componente de combinaci&oacute;n conect&aacute;ndolo con el servicio de combinaci&oacute;n
	 * (OpenOffice).
	 *
	 * @param cnt Cadena de conexi&oacute;n que indentifica el servicio OpenOffice.
	 * <br/>Formato:
	 * <ul>
	 * <li>
	 * 	<code>
	 * 		uno:socket,host=<b>nombrehost</b>,port=<b>puerto</b>;urp;StarOffice.NamingService
	 * 	</code>
	 * </li>
	 * </ul>
	 * @exception Exception Error al conectarse con el servicio OpenOffice.
	 *
	 */
	public DocumentParser(String cnt) throws ISPACException {
		ooHelper = OpenOfficeHelper.getInstance(cnt);
	}

	/**
	 * Sustituye las etiquetas de combinaci&oacute;n de la plantilla por sus valores calculados
	 * por ITagTranslator. Una vez terminado guarda el documento en la URL indicada por
	 * strTargetURL con el tipo de archivo (MSWord, OpenOffice, RTF) especificado por targetfilter
	 * <br/>
	 *
	 * @param strTemplateURL  URL para la plantilla que contiene el documento con los tags que
	 * hay que interpretar.
	 * @param strTargetURL URL para el documento combinado.
	 * @param tagtranslator Traductor de tags a utilizar en la combinaci&oacute;n
	 * @param targetMimeType Tipo MIME para el documento combinado.
	 * @exception ISPACException Si ocurre alg&uacute;n error durante la combinación
	 */
	public void mergeDocument(String strTemplateURL, String strTargetURL,
			ITagTranslator tagtranslator, String targetMimeType)
			throws ISPACException {

		merge(strTemplateURL, strTargetURL, tagtranslator,
				OpenOfficeHelper.getFilterName(targetMimeType));
	}

	/**
	 * Sustituye las etiquetas de combinaci&oacute;n de la plantilla por sus valores calculados
	 * por ITagTranslator. Una vez terminado guarda el documento en la URL indicada por
	 * strTargetURL con el tipo de archivo (MSWord, OpenOffice, RTF) especificado por targetfilter
	 * <br/>
	 *
	 * @param strTemplateURL  URL para la plantilla que contiene el documento con los tags que
	 * hay que interpretar.
	 * @param strTargetURL URL para el documento combinado.
	 * @param tagtranslator Traductor de tags a utilizar en la combinaci&oacute;n
	 * @param targetfilter Determina el tipo de documento resultado de la combinaci&oacute;n del documento:
	 * @exception ISPACException Si ocurre alg&uacute;n error durante la combinación
	 * @deprecated
	 */
	public void mergeDocument(
	String strTemplateURL,
	String strTargetURL,
	ITagTranslator tagtranslator,
	int targetfilter)
	throws ISPACException
	{
		String strFilter;

		if (logger.isInfoEnabled()) {
			logger.info("mergeDocument: strTemplateURL=[" + strTemplateURL
					+ "], strTargetURL=[" + strTargetURL
					+ "], tagtranslator=[" + tagtranslator
					+ "], targetfilter=[" + targetfilter + "]");
		}

		switch (targetfilter)
		{
		case DocumentParser.OpenOffice:
			strFilter = "";
			break;
		case DocumentParser.MicrosoftWord:
			strFilter = "MS Word 97";
			break;
		case DocumentParser.MicrosoftExcel:
			strFilter =  "MS Excel 97";
			break;
		case DocumentParser.RichTextFormat:
			strFilter = "Rich Text Format";
			break;
		case DocumentParser.PDFWriter:
			strFilter = "writer_pdf_Export";
			break;
		default:
		    /* OpenOffice */
			strFilter = "";
			break;
		}

		merge(strTemplateURL, strTargetURL, tagtranslator, strFilter);
	}


	protected void merge(String strTemplateURL, String strTargetURL,
			ITagTranslator tagtranslator, String strFilter)
			throws ISPACException	{

		XComponent xComponent = null;

		if (logger.isInfoEnabled()) {
			logger.info("mergeDocument: strTemplateURL=[" + strTemplateURL
					+ "], strTargetURL=[" + strTargetURL
					+ "], tagtranslator=[" + tagtranslator
					+ "], targetfilterName=[" + strFilter + "]");
		}

        try{
			if (logger.isDebugEnabled()) {
				logger.debug("Cargando documento: " + strTemplateURL);
			}

			if ((xComponent = ooHelper.loadDocument(strTemplateURL)) == null){
				throw new ISPACException("exception.documents.templates.load",new Object[]{strTemplateURL}, true);
			}

			//generateDocument(xComponent,tagtranslator);

			if (logger.isDebugEnabled()) {
				logger.debug("Generando documento...");
			}

			if ("MS Excel 97".equalsIgnoreCase(strFilter)) {
				generateDocumentExcel(xComponent,tagtranslator);
			} else {
				generateDocument(xComponent,tagtranslator);
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Guardando documento: " + strTargetURL);
			}

			OpenOfficeHelper.saveDocument(xComponent, strTargetURL, strFilter);

			if (logger.isDebugEnabled()) {
				logger.debug("Documento salvado: " + strTargetURL);
			}

			// mxDesktop.terminate();
		}catch (DisposedException e){
			logger.warn("El servidor OpenOffice no está disponible", e);
        	ooHelper.dispose();
        	throw e;
        }catch (ISPACException e){
        	logger.error("Error al mezclar el documento", e);
			throw e;
		}catch (Exception e){
			logger.error("Error al mezclar el documento", e);
			throw new ISPACException(e);
		}finally{
		    if (xComponent != null) xComponent.dispose();
		}
	}

	/**
	 * El filtro de salida para la combinaci&oacute;n de documentos correspondiente al mimetype
	 * proporcionado.
	 * @param mimetype El mimetype deseado para el documento destino
	 * @return Filtro OpenOffice a utilizar
	 * @deprecated
	 */
	public int getTargetDocumentFilter(String mimetype)
	{
		if (mimetype.equalsIgnoreCase("application/msword"))
			return DocumentParser.MicrosoftWord;
		else if (mimetype.equalsIgnoreCase("application/vnd.sun.xml.writer"))
			return DocumentParser.OpenOffice;
		else if (mimetype.equalsIgnoreCase("application/pdf"))
			return DocumentParser.PDFWriter;
		else if (mimetype.equalsIgnoreCase("application/vnd.ms-excel")
				|| mimetype.equalsIgnoreCase("application/x-excel")
				|| mimetype.equalsIgnoreCase("application/x-msexcel")
				|| mimetype.equalsIgnoreCase("application/excel")
				|| mimetype.equalsIgnoreCase("application/vndms-excel"))
			return DocumentParser.MicrosoftExcel;

		return OpenOffice;
	}

	private void generateDocumentExcel(XComponent xComponent,ITagTranslator tagtranslator)
	throws ISPACException
	{
    	XReplaceable xReplaceable = null;
    	XReplaceDescriptor xReplaceDescriptor = null;
		XIndexAccess xIndexAccess_ = null;
		XSpreadsheetDocument xDocument = (XSpreadsheetDocument)UnoRuntime.queryInterface(XSpreadsheetDocument.class, xComponent);
		//CARGAMOS LA COLECCION DE HOJAS DEL LIBRO
		com.sun.star.sheet.XSpreadsheets xSheets = xDocument.getSheets();
		com.sun.star.sheet.XSpreadsheet xSheet= null;
		//String[] names = xSheets.getElementNames();
        xIndexAccess_ = (XIndexAccess)UnoRuntime.queryInterface(XIndexAccess.class, xSheets );


        //ACCEDEMOS A CADA UNA DE LAS HOJAS PARA BUSCAR LOS TAGS DECLARADOS Y SUSTITUIRLOS POR SU VALOR
        for (int x = 0; x < xIndexAccess_.getCount(); x++){

        	try {
        		xSheet = (XSpreadsheet) UnoRuntime.queryInterface(XSpreadsheet.class, xIndexAccess_.getByIndex(x));
        	} catch (Exception e) {
				logger.warn("Error al obtener el interfaz", e);
			}

			//TODO Cargar el limite del rango de alguna constante que debe existe, aunque no la encontre,
			//ya que el rango maximo dependera de la version, p.e. para OOffice 1.1.3 es: 'A1:IV32000', para OOffice 2.0 es 'A1:IV32000'
			//cargamos todas la celdas de la hoja
 			com.sun.star.table.XCellRange xCellRange = xSheet.getCellRangeByName( "A1:IV32000");
 			xReplaceable = (XReplaceable) UnoRuntime.queryInterface( XReplaceable.class,xCellRange);
 			// Cescriptor para establecer las propiedades del reemplazo
 			xReplaceDescriptor = (XReplaceDescriptor) xReplaceable.createReplaceDescriptor();
 			xReplaceDescriptor.setSearchString(ISPACTAG);
 			Object object = new Boolean(true);

 			try
			{
				xReplaceDescriptor.setPropertyValue("SearchRegularExpression", object);

			}catch(Exception e)
			{
				logger.error("Error al establecer la propiedad [SearchRegularExpression]: " + object, e);
				throw new ISPACException(e);
			}

				//variables de acceso a datos
				XIndexAccess xIndexAccess = null;

				String cellText = null;
				Object tag;

				// Busca todos los tags declarados en el documento
				xIndexAccess = xReplaceable.findAll(xReplaceDescriptor);
			    ArrayList tagslist = new ArrayList();
				//Comprobamos que hay Tags declarados en la hoja activa antes de recorrerla
				if (xIndexAccess!=null){

					for (int i = 0; i < xIndexAccess.getCount(); i++)
						{
							try
							{

							tag = xIndexAccess.getByIndex(i);
							}catch(Exception e)
							{
								logger.error("Error al obtener el tag", e);
								throw new ISPACException(e);
							}
							//Accedemos al rango de la hoja indexdo por el objeto tag
                            XCellRange rango= (XCellRange) UnoRuntime.queryInterface(XCellRange.class,tag);
                            XCellRangeData datos = (XCellRangeData) UnoRuntime.queryInterface(XCellRangeData.class,rango);

							//Accedemos al contenido de las celdas del rango
                            Object [][] contenido =datos.getDataArray();
                            for (int j = 0; j < contenido.length; j++) {
								for (int k = 0; k < contenido[j].length; k++) {
									 cellText=contenido [j][k].toString();
									 if (StringUtils.isNotEmpty(cellText)) {
										//tagslist.add(cellText);
										tagslist.addAll(getAllTgas(cellText));
									}
							   }//fin for para el segundo indice del array
							}//fin for para el primer indice del array

						}

						List translatedtags = tagtranslator.translateTags(tagslist);

						object = new Boolean(false);
					try
					{
						xReplaceDescriptor.setPropertyValue("SearchRegularExpression", object);
					}catch(Exception e)
					{
						logger.error("Error al establecer la propiedad [SearchRegularExpression]: " + object, e);
						throw new ISPACException(e);
					}

						// Los dos conjuntos estan ordenados.
					Iterator it = tagslist.iterator();
					Iterator ittranslated = translatedtags.iterator();

					while (ittranslated.hasNext()&&it.hasNext())
						{

						String stagvalue =(String) ittranslated.next();
						if (stagvalue==null)
							stagvalue="";

						xReplaceDescriptor.setSearchString((String) it.next());
						xReplaceDescriptor.setReplaceString(stagvalue);
						xReplaceable.replaceAll(xReplaceDescriptor);
						}
				}//fin de if que comprobueba que hay tags declarados
       } //final del recorrido de todas las hojas del libro (primer for abierto en la funcion)

}


	/**
	 * @param cellText Contenido de una celda que contiene algún tag
	 * @return listado de tags contenidos en la celda <code>cellText</code>
	 */
	private List getAllTgas(String cellText) {
		List list = new ArrayList();

		Pattern pattern = Pattern.compile(ISPACTAG);
		Matcher matcher = pattern.matcher(cellText);
		while (matcher.find()) {
			String key = matcher.group();
			list.add(key);
		}
		return list;
	}

	private void generateDocument(XComponent xComponent,ITagTranslator tagtranslator)throws ISPACException{
		try{

			//Para permitir que al incluir dentro de una plantilla un documento este documento pueda contener a su vez
			//tags, se realiza un bucle. Se incluye un nivel de anidamiento para que no se puedan producir bucles infinitos
			int nivelAnidamiento = ISPACConfiguration.getInstance().getInt(
					ISPACConfiguration.PARSER_CONNECTOR_TAGS_NESTING_LEVEL,
					DEFAULT_TAGS_NESTING_LEVEL);

			int contador = 0;
			for(;;contador++){

				if (contador > nivelAnidamiento){
					logger.warn("Se ha superado el nivel de anidamiento de marcadores en plantillas. Comprobar la definición de la plantilla");
					break;
				}

				XReplaceable xReplaceable = null;
				XReplaceDescriptor xReplaceDescriptor = null;
				xReplaceable = (XReplaceable) UnoRuntime.queryInterface(XReplaceable.class, xComponent);
				// Descriptor to set properies for replace
				xReplaceDescriptor = xReplaceable.createReplaceDescriptor();
				xReplaceDescriptor.setSearchString(ISPACTAG);

				Object object = new Boolean(true);
				xReplaceDescriptor.setPropertyValue("SearchRegularExpression", object);

				XIndexAccess xIndexAccess = null;
				String strTag = null;
				// Busca todos los tags declarados en el documento
				xIndexAccess = xReplaceable.findAll(xReplaceDescriptor);

				ArrayList tagslist = new ArrayList();
				ArrayList xTextRangeList = new ArrayList();
				for (int i = 0; i < xIndexAccess.getCount(); i++){
					object = xIndexAccess.getByIndex(i);
					XTextRange xTextRange = (XTextRange) UnoRuntime.queryInterface(XTextRange.class, object);
					strTag = xTextRange.getString();
					tagslist.add(strTag);
					xTextRangeList.add(xTextRange);
				}
				if (tagslist.size() == 0){
					break;
				}

				List translatedtags = tagtranslator.translateTags(tagslist);
				object = new Boolean(false);
				xReplaceDescriptor.setPropertyValue("SearchRegularExpression", object);

				// Los dos conjuntos estan ordenados.
				Iterator it = tagslist.iterator();
				Iterator ittranslated = translatedtags.iterator();
				Iterator itXTextRange = xTextRangeList.iterator();

				while (ittranslated.hasNext()&&it.hasNext() && itXTextRange.hasNext()){
					Object stagvalue = ittranslated.next();
					XTextRange xTextRangeReplace = (XTextRange)itXTextRange.next();
					String tag = (String)it.next();
					if (stagvalue==null){
						xReplaceDescriptor.setSearchString(tag);
						xReplaceDescriptor.setReplaceString("");
						xReplaceable.replaceAll(xReplaceDescriptor);
					}else if (stagvalue instanceof TemplateDocumentInfo){
			        	TemplateDocumentInfo documentInfo = (TemplateDocumentInfo)stagvalue;
			        	if (StringUtils.isNotEmpty(documentInfo.getBokkmark())){
							ooHelper.insertContentBookmark(documentInfo, tag, xReplaceDescriptor, xReplaceable);
			        		//insertContentBookmark(documentInfo, xComponent, xTextRangeReplace);
			        	}else if (documentInfo.isAsText()){
				        	//Insertar el contenido del documento como texto => Si el documento tiene imagenes no las inserta
				        	XComponent xComponent1 = ooHelper.loadDocument(((TemplateDocumentInfo)stagvalue).getUrl());
				        	XTextDocument xtextdocument = ( XTextDocument ) UnoRuntime.queryInterface(XTextDocument.class, xComponent1 );
				        	xReplaceDescriptor.setSearchString(tag);
				        	xReplaceDescriptor.setReplaceString(xtextdocument.getText().getString());
				        	xReplaceable.replaceAll(xReplaceDescriptor);
			        	}else{
						//Insertar el documento
			        		ooHelper.insertDocument(documentInfo.getUrl(), xTextRangeReplace.getEnd(), tag, xReplaceDescriptor,xReplaceable);
							//Se borra el salto de pagina que se inserta con el documento
			        		ooHelper.deletePageBreak(xComponent, xTextRangeReplace.getStart());
			        	}
					}else if (stagvalue instanceof TemplateGraphicInfo){
						TemplateGraphicInfo graphicInfo = ((TemplateGraphicInfo)stagvalue);
						//Inserccion de una imagen => la inserta pero la situa centrada
						ooHelper.insertGraphic(xComponent, xTextRangeReplace, graphicInfo.getUrl());

						//Inserccion de una imagen => no funciona si se inserta mas de una imagen
						//insertImage(xFactory,xComponent, xTextRangeReplace,graphicInfo.getUrl(),graphicInfo.isAsLink() );
					}else if (stagvalue instanceof TemplateTableInfo){
						TemplateTableInfo tableInfo = ((TemplateTableInfo)stagvalue);
						ooHelper.insertTable(tableInfo, xComponent, xTextRangeReplace);
					}else{
						xReplaceDescriptor.setSearchString(tag);
						xReplaceDescriptor.setReplaceString((String)stagvalue);
						xReplaceable.replaceAll(xReplaceDescriptor);
					}
				}
			}


        } catch (ISPACException e) {
        	logger.error("Error al generar el documento", e);
			throw e;
		} catch (Exception e) {
			logger.error("Error al generar el documento", e);
			throw new ISPACException(e);
		}
	}
}