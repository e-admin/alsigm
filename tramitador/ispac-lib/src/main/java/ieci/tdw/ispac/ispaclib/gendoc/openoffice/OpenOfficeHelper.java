package ieci.tdw.ispac.ispaclib.gendoc.openoffice;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.ispaclib.templates.TemplateDocumentInfo;
import ieci.tdw.ispac.ispaclib.templates.TemplateTableInfo;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.MapUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.star.beans.PropertyValue;
import com.sun.star.beans.XPropertySet;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.container.XIndexAccess;
import com.sun.star.container.XNameAccess;
import com.sun.star.container.XNamed;
import com.sun.star.document.XDocumentInsertable;
import com.sun.star.drawing.XShape;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XController;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XDispatchHelper;
import com.sun.star.frame.XDispatchProvider;
import com.sun.star.frame.XFrame;
import com.sun.star.frame.XModel;
import com.sun.star.frame.XStorable;
import com.sun.star.io.IOException;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.lang.XServiceInfo;
import com.sun.star.text.HoriOrientation;
import com.sun.star.text.TextContentAnchorType;
import com.sun.star.text.WrapTextMode;
import com.sun.star.text.XBookmarksSupplier;
import com.sun.star.text.XText;
import com.sun.star.text.XTextContent;
import com.sun.star.text.XTextCursor;
import com.sun.star.text.XTextDocument;
import com.sun.star.text.XTextGraphicObjectsSupplier;
import com.sun.star.text.XTextRange;
import com.sun.star.text.XTextTable;
import com.sun.star.text.XTextViewCursor;
import com.sun.star.text.XTextViewCursorSupplier;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XNamingService;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;

public class OpenOfficeHelper {
	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(OpenOfficeHelper.class);
	
	private static XDesktop mxDesktop = null;

	private static XMultiServiceFactory xFactory = null;

	
	private final int BACKGROUND_COLOR_TITLE_COLUMN =  16777215;
	private final static int BACKGROUND_WHITE_COLOR =  0;

	private final int DEFAULT_BACKGROUND_COLOR_TABLE = 13421823;
	private final int DEFAULT_BACKGROUND_COLOR_TABLEROW = 6710932;
	
	private static final int OO_WRITER = 0;
	private static final int OO_CALC = 1;
	private static final int OO_IMPRESS = 2;
	private static final int OO_DRAW = 3;
	private static final int OO_MATH = 4;
	private static final int OO_CHART = 5; 	
	
	private static final String OO_WRITER_EXT = "odt";
	private static final String OO_CALC_EXT = "ods";
	private static final String OO_IMPRESS_EXT = "odp";
	private static final String OO_DRAW_EXT = "odg";
	
	private static final String DOCTYPE_SCALC = "com.sun.star.sheet.SpreadsheetDocument";
	private static final String DOCTYPE_SWRITER = "com.sun.star.text.TextDocument";
	private static final String DOCTYPE_SDRAW = "com.sun.star.drawing.DrawingDocument";
	private static final String DOCTYPE_SMATH = "com.sun.star.formula.FormulaProperties";
	private static final String DOCTYPE_SIMPRESS = "com.sun.star.presentation.PresentationDocument";
	private static final String DOCTYPE_SCHART = "com.sun.star.chart.ChartDocument";	
	
	private static final Map<String, String> FILTER_NAMES_MAP = new HashMap<String, String>();
	static {
		FILTER_NAMES_MAP.put("application/msword", "MS Word 97");
		FILTER_NAMES_MAP.put("application/vnd.oasis.opendocument.text", "");
		FILTER_NAMES_MAP.put("application/rtf", "Rich Text Format");
		FILTER_NAMES_MAP.put("application/vnd.ms-excel", "MS Excel 97");
		//FILTER_NAMES_MAP.put("application/pdf", "writer_pdf_Export");
	}

	private String cnt = null;
	

	/**
	 * Constructor.
	 * @param cnt
	 */
	public OpenOfficeHelper(String cnt) {
		super();
		this.cnt = cnt;
	}

	public synchronized static OpenOfficeHelper getInstance(String cnt) {
		return new OpenOfficeHelper(cnt);
	}

	public synchronized static OpenOfficeHelper getInstance() throws ISPACException {
		String cnt = ISPACConfiguration.getInstance().get(ISPACConfiguration.OPEN_OFFICE_CONNECT);
		return new OpenOfficeHelper(cnt);
	}


	/*
	 * Se conecta con OpenOffice.
	 *
	 * @param strConnect Define la conexión con Open Office con el siguiente
	 * formato: <br/>
	 * uno:socket,host=<<nombrehost>>,port=<<puerto>>;urp;StarOffice.NamingService
	 *
	 *
	 * OpenOffice se ejecuta como servicio ejecutando el siguiente comando:
	 *
	 * ./soffice -headless -nologo -accept=socket,host=localhost,port=8100;urp;
	 *
	 * @exception Exception if an error occurs
	 */
	private static XMultiServiceFactory connect(String strConnect) throws ISPACException{
		try{
			XMultiServiceFactory xFactory = null;

			// Get component context
			XComponentContext xcomponentcontext = Bootstrap.createInitialComponentContext(null);

			// initial serviceManager
			XMultiComponentFactory xLocalServiceManager = xcomponentcontext.getServiceManager();

			// create a connector, so that it can contact the office
			Object xUrlResolver =
			xLocalServiceManager.createInstanceWithContext(	"com.sun.star.bridge.UnoUrlResolver", xcomponentcontext);

			XUnoUrlResolver urlResolver =
			(XUnoUrlResolver) UnoRuntime.queryInterface( XUnoUrlResolver.class, xUrlResolver);

			Object rInitialObject = urlResolver.resolve(strConnect);

			XNamingService rName = (XNamingService) UnoRuntime.queryInterface(XNamingService.class, rInitialObject);

			if (rName != null){
				Object rXsmgr = rName.getRegisteredObject("StarOffice.ServiceManager");
				xFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, rXsmgr);
			}
			return xFactory;
		}catch (Exception e){
			logger.error("Error al establecer la conexión con OpenOffice: " + strConnect, e);
			throw new ISPACException(e);
		}
	}

	public XDesktop getDeskTop() throws ISPACException {

		if (mxDesktop == null) {
			try {
				if (xFactory == null) {
					xFactory = connect(cnt);
				}
				mxDesktop = getDesktop(xFactory);
			} catch (ISPACException e) {
				logger.error("Error al obtener el DeskTop", e);
				throw e;
			} catch (Exception e) {
				logger.error("Error al obtener el DeskTop", e);
				throw new ISPACException(e);
			}
		}
		return mxDesktop;
	}

	public static XDesktop getDesktop(XMultiServiceFactory xFactory) throws ISPACException {

		try {
			XInterface xInterface = (XInterface) xFactory.createInstance("com.sun.star.frame.Desktop");
			XDesktop xDesktop = (XDesktop) UnoRuntime.queryInterface(XDesktop.class, xInterface);
			return xDesktop;
		} catch (Exception e) {
			logger.error("Error al obtener el XDesktop", e);
			throw new ISPACException(e);
		}
	}
	
	public XComponent loadDocument(String strURL) throws Exception {

		XDesktop xDesktop = getDeskTop();

		PropertyValue[] arguments = new PropertyValue[1];
		arguments[0] = new PropertyValue();
		arguments[0].Name = "Hidden";
		arguments[0].Value = new Boolean(true);

		XComponentLoader xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface(XComponentLoader.class, xDesktop);

		XComponent xComponent = xComponentLoader.loadComponentFromURL(strURL, "_blank", 0, arguments);

		return xComponent;
	}
	
	public void insertTable(TemplateTableInfo tableInfo, XComponent xComponent, XTextRange xTextRange) throws Exception {
	    XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
	    XText xText = xTextDocument.getText();
		//create instance of a text table
	    XTextTable xTextTable = null;
        
        XMultiServiceFactory xDocMSF =(XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, xTextDocument);        
        Object oInt = xDocMSF.createInstance("com.sun.star.text.TextTable");
        xTextTable = (XTextTable) UnoRuntime.queryInterface(XTextTable.class,oInt);
        
        //initialize the text table with 4 columns an 4 rows
        int columns = tableInfo.getColumns().length;
        int rows = tableInfo.getSize();
        xTextTable.initialize(rows+1,columns);
        
        XTextCursor xTextCursor = xTextRange.getText().createTextCursor();
        xTextCursor.gotoRange(xTextRange,false);        
        //insert the table
        xText.insertTextContent(xTextCursor, xTextTable, true);
        
        // get first Row
        XIndexAccess xTextTableRows = xTextTable.getRows();
        XPropertySet xTextTableRowPS = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextTableRows.getByIndex(0));
            
        // get the property set of the text table
        
        XPropertySet xTextTablePS = (XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, xTextTable);
        
        // Change the BackColor
        xTextTablePS.setPropertyValue("BackTransparent", new Boolean(false));
        xTextTablePS.setPropertyValue("BackColor",new Integer(DEFAULT_BACKGROUND_COLOR_TABLE));
        xTextTableRowPS.setPropertyValue("BackTransparent", new Boolean(false));
        xTextTableRowPS.setPropertyValue("BackColor",new Integer(DEFAULT_BACKGROUND_COLOR_TABLEROW));
            
        // write Text in the Table headers
        for(int i=0; i<columns; i++){
            insertIntoCell(""+((char)(65+i))+"1",tableInfo.getTitleColumns()[i], xTextTable, BACKGROUND_COLOR_TITLE_COLUMN);
        }
        int i = 2;
        for (Iterator iterator = tableInfo.getResults().iterator(); iterator.hasNext();i++) {
			IItem item = (IItem) iterator.next();
			for(int j=0; j<columns; j++){
				insertIntoCell(""+((char)(65+j))+ i, item.getString((String)tableInfo.getColumns()[j]), xTextTable, BACKGROUND_WHITE_COLOR);
			}
		}
	}

    public static void insertIntoCell(String CellName, String theText,XTextTable xTextTable, int charColor) throws Exception {
		XText xTableText = (XText) UnoRuntime.queryInterface(XText.class, xTextTable.getCellByName(CellName));
		// create a cursor object
		XTextCursor xTC = xTableText.createTextCursor();
		XPropertySet xTPS = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTC);
		if (charColor != BACKGROUND_WHITE_COLOR)
			xTPS.setPropertyValue("CharColor", new Integer(charColor));
		// inserting some Text
		xTableText.setString(theText);
	}	
	
	
	
	
	
	
	
	public void insertDocument(String fileName, XTextRange xTextRangeReplace, String tag, XReplaceDescriptor xReplaceDescriptor, XReplaceable xReplaceable) throws Exception {
		XTextCursor iTextCursor = xTextRangeReplace.getText().createTextCursor();
		iTextCursor.gotoRange(xTextRangeReplace ,false);
	    XDocumentInsertable iDocumentInsertable = (XDocumentInsertable) UnoRuntime.queryInterface(XDocumentInsertable.class, iTextCursor);
	    // Insert the document "fileName" with the iDocumentInsertable.
	    iDocumentInsertable.insertDocumentFromURL(fileName, null);
		xReplaceDescriptor.setSearchString(tag);
		xReplaceDescriptor.setReplaceString("");
		xReplaceable.replaceAll(xReplaceDescriptor);
	}
	
	public void concatFiles(String fileName, String fileName2)throws ISPACException{
		try{
			XComponent xComponent = loadDocument(fileName);
			
			XTextDocument xTextDocument = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xComponent);
		    XText xText = xTextDocument.getText();
		    // create a text cursor
		    XTextCursor xTextCursor = xText.createTextCursor();
		    xTextCursor.gotoRange(xText.getEnd(),false);
		    XDocumentInsertable xDocInsert = (XDocumentInsertable)UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
			xDocInsert.insertDocumentFromURL(fileName2, null);
		}catch(Exception e){
			throw new ISPACException(e);
		}
	}	
	
	public void concatFiles(XComponent xComponent , String fileName2)throws ISPACException{
		try{
		    XTextDocument xTextDocument = (XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xComponent);
		    XText xText = xTextDocument.getText();
		    
		    // create a text cursor
		    XTextCursor xTextCursor = xText.createTextCursor();
		    xTextCursor.gotoRange(xText.getEnd(),false);
		    XDocumentInsertable xDocInsert = (XDocumentInsertable)UnoRuntime.queryInterface(XDocumentInsertable.class, xTextCursor);
			xDocInsert.insertDocumentFromURL(fileName2, null);
		}catch(Exception e){
			throw new ISPACException(e);
		}
	}	
	
	public void insertContentBookmark(TemplateDocumentInfo documentInfo, String tag, XReplaceDescriptor xReplaceDescriptor, XReplaceable xReplaceable) throws Exception {
		
		XComponent xComponent = loadDocument(documentInfo.getUrl());
		XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
		
		XBookmarksSupplier bookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class, xTextDocument);
		Object iDeleteBookmark = bookmarksSupplier.getBookmarks().getByName(documentInfo.getBokkmark());
		XTextContent iBookmarkContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, iDeleteBookmark);
		XTextRange iBookmarkRange = iBookmarkContent.getAnchor();
		//return iBookmarkRange.getText();
		//return iBookmarkRange.getString();
		
		xReplaceDescriptor.setSearchString(tag);
    	xReplaceDescriptor.setReplaceString(iBookmarkRange.getString());
    	xReplaceable.replaceAll(xReplaceDescriptor);
	}
	

//	private void insertContentBookmark(DocumentInfo documentInfo, XComponent xComponent, XTextRange xTextRange) throws Exception {
//	    XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
//	    XText xText = xTextDocument.getText();
//        
//        XTextCursor xTextCursor = xTextRange.getText().createTextCursor();
//        xTextCursor.gotoRange(xTextRange,false);        
//		XComponent xComponent2 = loadDocument(documentInfo.getUrl());
//		XTextDocument xTextDocument2 = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent2);
//		
//		XBookmarksSupplier bookmarksSupplier = (XBookmarksSupplier) UnoRuntime.queryInterface(XBookmarksSupplier.class, xTextDocument2);
//		Object iBookmark = bookmarksSupplier.getBookmarks().getByName(documentInfo.getBokkmark());
//		XTextContent iBookmarkContent = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, iBookmark);
//
//        xText.insertTextContent(xTextCursor, iBookmarkContent, true);
//	}
	
	
	
	
	
//    public static void insertBookmark(
//			com.sun.star.text.XTextDocument xTextDocument,
//			com.sun.star.text.XTextRange xTextRange, String sBookName) {
//		// create a bookmark on a TextRange
//		try {
//			// get the MultiServiceFactory from the text document
//			com.sun.star.lang.XMultiServiceFactory xDocMSF;
//			xDocMSF = (com.sun.star.lang.XMultiServiceFactory) UnoRuntime.queryInterface(com.sun.star.lang.XMultiServiceFactory.class,xTextDocument);
//
//			// the bookmark service is a context dependend service, you need
//			// the MultiServiceFactory from the document
//			Object xObject = xDocMSF.createInstance("com.sun.star.text.Bookmark");
//
//			// set the name from the bookmark
//			com.sun.star.container.XNamed xNameAccess = null;
//			xNameAccess = (com.sun.star.container.XNamed) UnoRuntime.queryInterface(com.sun.star.container.XNamed.class,xObject);
//
//			xNameAccess.setName(sBookName);
//
//			// create a XTextContent, for the method 'insertTextContent'
//			com.sun.star.text.XTextContent xTextContent = null;
//			xTextContent = (com.sun.star.text.XTextContent) UnoRuntime.queryInterface(com.sun.star.text.XTextContent.class,xNameAccess);
//
//			// insertTextContent need a TextRange not a cursor to specify the
//			// position from the bookmark
//			xTextDocument.getText().insertTextContent((XTextRange)xTextRange, xTextContent,true);
//
//		} catch (Exception e) {
//			e.printStackTrace(System.err);
//		}
//	}	
	
	
	
	
	
	
	
	
	
//	private void insertDocument(XMultiServiceFactory xFactory, XComponent xcomponent, XTextRange xTextRange, String url) throws ISPACException	{
//		XTextDocument xtextdocument = ( XTextDocument ) UnoRuntime.queryInterface(XTextDocument.class, xcomponent ); 
//		XModel xModel = (XModel)UnoRuntime.queryInterface(XModel.class, xcomponent); 
//		XController xController = xModel.getCurrentController(); 
//		
//		// the controller gives us the TextViewCursor 
//		XTextViewCursorSupplier xViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, xController); 
//		XTextViewCursor xViewCursor = xViewCursorSupplier.getViewCursor(); 
//	
//		xViewCursor.gotoRange(xTextRange, false); 
//	
//		try { 
//			Object dispatchHelperObject = xFactory.createInstance("com.sun.star.frame.DispatchHelper"); 
//			XDispatchHelper xDispatchHelper = (com.sun.star.frame.XDispatchHelper) UnoRuntime.queryInterface(com.sun.star.frame.XDispatchHelper.class, dispatchHelperObject); 
//		
//			PropertyValue[] args = new PropertyValue[1]; 
//			args[0] = new PropertyValue(); 
//			args[0].Name = "FileName"; 
//			args[0].Value = url; 
//		
//			xController = xtextdocument.getCurrentController(); 
//			XFrame xFrame = xController.getFrame(); 
//			XDispatchProvider xDispatchProvider = (XDispatchProvider)UnoRuntime.queryInterface(XDispatchProvider.class, xFrame); 
//		
//			xDispatchHelper.executeDispatch(xDispatchProvider, ".uno:InsertText", "", 0, args); 
//		} catch (Exception e) { 
//			throw new ISPACException(e); 
//		} 
//	}
	
	public void insertGraphic(XComponent xComp, XTextRange xTextRange, String url) throws Exception	{ 
        // Querying for the interface XTextDocument on the xcomponent
        XTextDocument xTextDoc =(XTextDocument)UnoRuntime.queryInterface(XTextDocument.class, xComp);
        // Querying for the interface XMultiServiceFactory on the xtextdocument
        XMultiServiceFactory xMSFDoc =(XMultiServiceFactory)UnoRuntime.queryInterface(XMultiServiceFactory.class, xTextDoc);
        //Object oGraphic = xMSFDoc.createInstance("com.sun.star.text.TextGraphicObject");
        Object oGraphic = xMSFDoc.createInstance("com.sun.star.text.GraphicObject");
        
        
        // Getting the text
        XText xText = xTextRange.getText();
        // Getting the cursor on the document
        XTextCursor xTextCursor = xTextRange.getText().createTextCursor();
        xTextCursor.gotoRange(xTextRange,false);
        // Querying for the interface XTextContent on the GraphicObject
        XTextContent xTextContent =(XTextContent)UnoRuntime.queryInterface(XTextContent.class, oGraphic );
        // Inserting the content
        xText.insertTextContent( xTextCursor, xTextContent, true);
        // Querying for the interface XPropertySet on GraphicObject
        XPropertySet xPropSet =(XPropertySet)UnoRuntime.queryInterface(XPropertySet.class, oGraphic);

        // Setting the anchor type
        xPropSet.setPropertyValue("AnchorType",TextContentAnchorType.AT_PARAGRAPH);
        
        // Setting the graphic url
        xPropSet.setPropertyValue( "GraphicURL", url );

        //xPropSet.setPropertyValue("SurroundContour",new Boolean(true));
        //xPropSet.setPropertyValue("ContourOutside",new Boolean(true));
	}
	
	
	
	
	public void deletePageBreak(XComponent xcomponent, XTextRange xTextRange) throws ISPACException	{
		// Querying for the interface XTextDocument on the xcomponent 
		XTextDocument xtextdocument = ( XTextDocument ) UnoRuntime.queryInterface(XTextDocument.class, xcomponent ); 
		XModel xModel = (XModel)UnoRuntime.queryInterface(XModel.class, xcomponent); 
		XController xController = xModel.getCurrentController(); 
		
		
		// the controller gives us the TextViewCursor 
		XTextViewCursorSupplier xViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, xController); 
		XTextViewCursor xViewCursor = xViewCursorSupplier.getViewCursor(); 
		xViewCursor.gotoRange(xTextRange, false); 
		try { 
			Object dispatchHelperObject = xFactory.createInstance("com.sun.star.frame.DispatchHelper"); 
			XDispatchHelper xDispatchHelper = (com.sun.star.frame.XDispatchHelper) UnoRuntime.queryInterface(com.sun.star.frame.XDispatchHelper.class, dispatchHelperObject); 
			xController = xtextdocument.getCurrentController(); 
			XFrame xFrame = xController.getFrame(); 
			XDispatchProvider xDispatchProvider = (XDispatchProvider)UnoRuntime.queryInterface(XDispatchProvider.class, xFrame); 
			xDispatchHelper.executeDispatch(xDispatchProvider, ".uno:Delete", "", 0, new PropertyValue[] {});			
		} catch (Exception e) { 
			throw new ISPACException(e); 
		} 		
	}
	
	
	
//	private void insertImage(XMultiServiceFactory xFactory, XComponent xcomponent, XTextRange xTextRange, String url, boolean asLink) throws ISPACException	{ 
//		// Querying for the interface XTextDocument on the xcomponent 
//		XTextDocument xtextdocument = ( XTextDocument ) UnoRuntime.queryInterface(XTextDocument.class, xcomponent ); 
//		XModel xModel = (XModel)UnoRuntime.queryInterface(XModel.class, xcomponent); 
//		XController xController = xModel.getCurrentController(); 
//		
//		// the controller gives us the TextViewCursor 
//		XTextViewCursorSupplier xViewCursorSupplier = (XTextViewCursorSupplier)UnoRuntime.queryInterface(XTextViewCursorSupplier.class, xController); 
//		XTextViewCursor xViewCursor = xViewCursorSupplier.getViewCursor(); 
//		xViewCursor.gotoRange(xTextRange, false); 
//	
//
//		try { 
//			Object dispatchHelperObject = xFactory.createInstance("com.sun.star.frame.DispatchHelper"); 
//			XDispatchHelper xDispatchHelper = (com.sun.star.frame.XDispatchHelper) UnoRuntime.queryInterface(com.sun.star.frame.XDispatchHelper.class, dispatchHelperObject); 
//		
//			PropertyValue[] args = new PropertyValue[4]; 
//			args[0] = new PropertyValue(); 
//			args[0].Name = "FileName"; 
//			args[0].Value = url; 
//			args[1] = new PropertyValue(); 
//			args[1].Name = "FilterName"; 
//			args[1].Value = "<Todos los formatos>"; 
//			args[2] = new PropertyValue(); 
//			args[2].Name = "AsLink"; 
//			args[2].Value = new Boolean(asLink); 
//			args[3] = new PropertyValue(); 
//			args[3].Name = "Style"; 
//			args[3].Value = "Imagen"; 
//		
//			xController = xtextdocument.getCurrentController(); 
//			XFrame xFrame = xController.getFrame(); 
//			XDispatchProvider xDispatchProvider = (XDispatchProvider)UnoRuntime.queryInterface(XDispatchProvider.class, xFrame); 
//		
//			xDispatchHelper.executeDispatch(xDispatchProvider, ".uno:InsertGraphic", "", 0, args); 
//		} catch (Exception e) { 
//			throw new ISPACException(e); 
//		} 
//	}
	

	/*
	 * Es necesario que esten instalados los filtros en Open Office. Comprobar
	 * que en el fichero de configuración
	 * <OfficePath>/share/registry/data/org/openoffice/office/TypeDetection.xcu
	 * tiene instalados los filtros: Ms Word 97 Rich Text Format
	 */
	public static void saveDocument(XComponent xComponent, String strURL, String strFilter)
	throws com.sun.star.uno.Exception
	{
		int count = (strFilter.length() > 0) ? 2 : 1;
		PropertyValue[] arguments = new PropertyValue[count];
		arguments[0] = new PropertyValue();
		arguments[0].Name = "Overwrite";
		arguments[0].Value = new Boolean(true);
		if (count > 1)
		{
			arguments[1] = new PropertyValue();
			arguments[1].Name = "FilterName";
			arguments[1].Value = strFilter;
		}

		XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, xComponent);
		xStorable.storeToURL(strURL, arguments);
	}
	
	/**
	 * Guarda el contenido del documento.
	 * @param xComponent Contenedor del documento.
	 * @throws IOException si ocurre algún error.
	 */
	public static void saveDocument(XComponent xComponent) throws IOException {
        XStorable xStorable = (XStorable) UnoRuntime.queryInterface(XStorable.class, xComponent);
        xStorable.store();
	}
	
	
	public void load_and_Convert(String sourceFilePath,String finalFilePath, String sourceExtension, String finalExtension)throws Exception {
	XComponent xComponent = null;
	try {
		xComponent = loadDocument(sourceFilePath);

		//String strFilterName = detectFilterName(getDocumentType(sourceExtension), finalExtension);
		String strFilterName = detectFilterName(
				getDocumentType(xComponent), finalExtension);

		saveDocument(xComponent, finalFilePath, strFilterName);
	} catch (Exception e) {
		logger.error("Error al convertir el fichero: " + sourceFilePath, e);
		throw e;
	} finally {
		if (xComponent != null)
			xComponent.dispose();
	}
}

	public static int getDocumentType(String extention) {
		int getDocumentType = OO_WRITER;

		if (StringUtils.indexOfAny(extention, new String[] { "sxw", "stw",
				"sdw", "odt", "ott", "oth", "odm", "doc" }) != -1)
			return OO_WRITER;

		if (StringUtils.indexOfAny(extention, new String[] { "sxc", "stc",
				"sdc", "ods", "ots", "xls" }) != -1)
			return OO_CALC;

		if (StringUtils.indexOfAny(extention, new String[] { "sxi", "sti",
				"sdd", "sdp", "odp", "otp", "ppt" }) != -1)
			return OO_IMPRESS;

		if (StringUtils.indexOfAny(extention, new String[] { "odg" }) != -1)
			return OO_DRAW;

		return getDocumentType;
	}
	
	/**
	 * @param oDoc The currently open document.
	 * @return Returns the documentType.
	 * @throws Exception
	 */
	public static int getDocumentType(XComponent oDoc) throws Exception {
		int getDocumentType = OO_WRITER;

		XServiceInfo xServiceInfo = (XServiceInfo) UnoRuntime.queryInterface(
				XServiceInfo.class, oDoc);

		if (xServiceInfo.supportsService(DOCTYPE_SCALC)) {
			getDocumentType = OO_CALC;
		} else if (xServiceInfo.supportsService(DOCTYPE_SWRITER)) {
			getDocumentType = OO_WRITER;
		} else if (xServiceInfo.supportsService(DOCTYPE_SDRAW)) {
			getDocumentType = OO_DRAW;
		} else if (xServiceInfo.supportsService(DOCTYPE_SMATH)) {
			getDocumentType = OO_MATH;
		} else if (xServiceInfo.supportsService(DOCTYPE_SIMPRESS)) {
			getDocumentType = OO_IMPRESS;
		} else if (xServiceInfo.supportsService(DOCTYPE_SCHART)) {
			getDocumentType = OO_CHART;
		}

		return getDocumentType;
	}	
	
	
	public static String detectFilterName(int pintFromType,
			String pstrToExtension) throws ISPACException {
		String detectFilterName = "writer8";

		if (StringUtils.isNotBlank(pstrToExtension)) {

			if (pstrToExtension.equalsIgnoreCase("pdf")) {
				detectFilterName = getPDFFilter(pintFromType);

			} else if (pstrToExtension.equalsIgnoreCase("html")
					|| pstrToExtension.equalsIgnoreCase("htm")) {
				switch (pintFromType) {
				case OO_WRITER:
					detectFilterName = "HTML (StarWriter)";
					break;
				case OO_CALC:
					detectFilterName = "HTML (StarCalc)";
					break;
				case OO_IMPRESS:
				case OO_DRAW:
					detectFilterName = "impress_html_Export";
				}

				/**
				 * Test documents only.
				 */
			} else if (pstrToExtension.equals("doc")) {
				detectFilterName = "MS Word 97";
			} else if (pstrToExtension.equals("rtf")) {
				detectFilterName = "Rich Text Format";
			} else if (pstrToExtension.equals("txt")) {
				detectFilterName = "Text (encoded)";

				/**
				 * Spreedsheets only.
				 */
			} else if (pstrToExtension.equals("csv")) {
				// Different for xls and doc.
				detectFilterName = "Text - txt - csv (StarCalc)";
			} else if (pstrToExtension.equals("xls")) {
				//detectFilterName = "MS Excel 2003 XML";
				detectFilterName = "MS Excel 97 Vorlage/Template";

				/**
				 * Native openoffice documents.
				 */
			} else if (pstrToExtension.equals(OO_WRITER_EXT)) {
				detectFilterName = "writer8";
			} else if (pstrToExtension.equals(OO_CALC_EXT)) {
				detectFilterName = "calc8";
			} else if (pstrToExtension.equals(OO_DRAW_EXT)) {
				detectFilterName = "draw8";
			} else if (pstrToExtension.equals(OO_IMPRESS_EXT)) {
				detectFilterName = "impress8";

			} else {
				/**
				 * Other.. jpg etc..
				 */
				switch (pintFromType) {
				case OO_WRITER:
					throw new ISPACException("Unsupported file type");

				case OO_CALC:
					throw new ISPACException("Unsupported file type");

				case OO_IMPRESS:
				case OO_DRAW:
				case OO_CHART:
					if (pstrToExtension.equals("jpg")) {
						detectFilterName = "impress_jpg_Export";

					} else if (pstrToExtension.equals("png")) {
						detectFilterName = "impress_png_Export";

					} else if (pstrToExtension.equals("gif")) {
						detectFilterName = "impress_gif_Export";

					} else if (pstrToExtension.equals("swf")) {
						detectFilterName = "impress_flash_Export";

					} else {
						throw new ISPACException("Unsupported file type");
					}

					break;
				}

			}
		}

		return detectFilterName;
	}
	
	/**
	 * @param documentType The openoffice doc type.
	 * @return Returns the correct pdf filter.
	 */
	public static String getPDFFilter(int documentType) {
		String getPDFFilter = "writer_pdf_Export";

		switch (documentType) {
		case OO_CALC:
			getPDFFilter = "calc_pdf_Export";
			break;
		case OO_DRAW:
			getPDFFilter = "draw_pdf_Export";
			break;
		case OO_IMPRESS:
			getPDFFilter = "impress_pdf_Export";
			break;
		case OO_MATH:
			getPDFFilter = "math_pdf_Export";
			break;
		case OO_WRITER:
			getPDFFilter = "writer_pdf_Export";
			break;
		}

		return getPDFFilter;
	}



	public void dispose() {
		mxDesktop = null;
		xFactory = null;
	}	
	
	public synchronized void stampDocument(File documentFile, File imageFile, int width, int height) throws ISPACException {

		XComponent xComponent = null;

		try {

			String documentUrl = convertToUrl(documentFile);
			String imageUrl = convertToUrl(imageFile);

			// Cargar el documento
			xComponent = loadDocument(documentUrl);

			// Posicionamiento para insercion de grafico
			XTextDocument xTextDocument = (XTextDocument) UnoRuntime.queryInterface(XTextDocument.class, xComponent);
			XText text = xTextDocument.getText();
			XTextCursor cursor = text.createTextCursor();
			//XTextRange xTextRange = cursor.getEnd();

			XMultiServiceFactory mxDocFactory = (XMultiServiceFactory) UnoRuntime.queryInterface(XMultiServiceFactory.class, xTextDocument);

			// Inserción de objeto gráfico
			Object GraphicObject = mxDocFactory.createInstance("com.sun.star.text.TextGraphicObject");
			XTextContent graphic = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, GraphicObject);
			XPropertySet xProperties = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, graphic);
			xProperties.getPropertySetInfo().getProperties();
			xProperties.setPropertyValue("Transparency", new Short((short) -50));
			xProperties.setPropertyValue("GraphicURL", imageUrl);
			xProperties.setPropertyValue("TextWrap", WrapTextMode.THROUGHT);
			xProperties.setPropertyValue("Width", new Integer(width));
			xProperties.setPropertyValue("Height", new Integer(height));
			// xProperties.setPropertyValue("Opaque", Boolean.FALSE);
			// xProperties.setPropertyValue("BackTransparent", Boolean.TRUE);
			// xProperties.setPropertyValue("Transparency", new Short((short)
			// 50));
			
			xProperties.setPropertyValue("HoriOrient", new Short(HoriOrientation.LEFT));
			 // Setting the anchor type
			//xProperties.setPropertyValue( "AnchorType",
	        //  TextContentAnchorType.AT_PARAGRAPH );
			text.insertTextContent(cursor, graphic, false);
			

			// Nombre de la imagen
//			String[] name = xNAGraphicObjects.getElementNames();
//			String imageName = name[xNAGraphicObjects.getElementNames().length - 1];
			XNamed xName = (XNamed) UnoRuntime.queryInterface(XNamed.class, GraphicObject);
			String imageName = xName.getName();

			// Conversión de la imagen linkada en imagen embebida
			XTextGraphicObjectsSupplier xTGOS = (XTextGraphicObjectsSupplier) UnoRuntime
				.queryInterface(XTextGraphicObjectsSupplier.class, xTextDocument);
			XNameAccess xNAGraphicObjects = xTGOS.getGraphicObjects();

			// Asignar un nombre a la imagen
			Object jloGraphicObject = xNAGraphicObjects.getByName(imageName);
			XTextContent xTCGraphicObject = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, jloGraphicObject);
			XPropertySet xPSTCGraphicObject = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xTCGraphicObject);
			//String jlsGOUrl = xPSTCGraphicObject.getPropertyValue("GraphicURL").toString();
			Object jlsGOUrl = xPSTCGraphicObject.getPropertyValue("GraphicURL");
			
			Object jloShape1 = mxDocFactory.createInstance("com.sun.star.drawing.GraphicObjectShape");
			XShape xGOShape1 = (XShape) UnoRuntime.queryInterface(XShape.class,jloShape1);
			XTextContent xTCShape1 = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, xGOShape1);
			XPropertySet xPSShape1 = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xGOShape1);
			xPSShape1.setPropertyValue("GraphicURL", jlsGOUrl);

			Object jloShape2 = mxDocFactory.createInstance("com.sun.star.drawing.GraphicObjectShape");
			XShape xGOShape2 = (XShape) UnoRuntime.queryInterface(XShape.class, jloShape2);
			XTextContent xTCShape2 = (XTextContent) UnoRuntime.queryInterface(XTextContent.class, xGOShape2);
			XPropertySet xPSShape2 = (XPropertySet) UnoRuntime.queryInterface(XPropertySet.class, xGOShape2);

			XTextRange xTRTCGraphicObject = xTCGraphicObject.getAnchor();
			xTRTCGraphicObject.getText().insertTextContent(xTRTCGraphicObject, xTCShape1, false);

			xPSShape2.setPropertyValue("Graphic", xPSShape1.getPropertyValue("Graphic"));
			xTRTCGraphicObject.getText().insertTextContent(xTRTCGraphicObject, xTCShape2, false);
			
			xTRTCGraphicObject.getText().removeTextContent(xTCShape1);
			String jlsInternalUrl = xPSShape2.getPropertyValue("GraphicURL").toString();
			xPSTCGraphicObject.setPropertyValue("GraphicURL", jlsInternalUrl);
			xTRTCGraphicObject.getText().removeTextContent(xTCShape2);
			
			// Guardar el documento sellado
			saveDocument(xComponent);

		} catch (ISPACException e) {
			logger.error("Error al sellar el documento", e);
			throw e;
		} catch (Throwable e) {
			logger.error("Error al sellar el documento", e);
			throw new ISPACException(e);
		} finally {
			if (xComponent != null) {
				xComponent.dispose();
			}
		}
	}

    public static final String convertToUrl(File file) throws java.io.IOException {
    	String url = null;
    	
    	if (file != null) {
    		url = "file://" + file.toURL().getFile();
    	}
    	
    	return url;
    }
    
    public static final String getFilterName(String mimeType) {
    	return MapUtils.getString(FILTER_NAMES_MAP, mimeType, "");
    }

    public static final boolean isMimeTypeSupportedForMerging(String mimeType) {
    	return MapUtils.getString(FILTER_NAMES_MAP, mimeType) != null;
    }
}

