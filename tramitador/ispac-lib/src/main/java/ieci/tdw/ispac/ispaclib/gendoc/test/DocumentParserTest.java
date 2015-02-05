package ieci.tdw.ispac.ispaclib.gendoc.test;

import ieci.tdw.ispac.api.errors.ISPACException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sun.star.beans.PropertyValue;
import com.sun.star.bridge.XUnoUrlResolver;
import com.sun.star.comp.helper.Bootstrap;
import com.sun.star.container.XIndexAccess;
import com.sun.star.frame.XComponentLoader;
import com.sun.star.frame.XDesktop;
import com.sun.star.frame.XStorable;
import com.sun.star.lang.XComponent;
import com.sun.star.lang.XMultiComponentFactory;
import com.sun.star.lang.XMultiServiceFactory;
import com.sun.star.text.XTextRange;
import com.sun.star.uno.UnoRuntime;
import com.sun.star.uno.XComponentContext;
import com.sun.star.uno.XInterface;
import com.sun.star.uno.XNamingService;
import com.sun.star.util.XReplaceDescriptor;
import com.sun.star.util.XReplaceable;

public class DocumentParserTest {

    private XDesktop mxDesktop = null;
    private String mstrConnection = null;

    private long time = 0;
    private long count = 0;

    public DocumentParserTest( String strConnection)
	throws ISPACException
	{
		//mstrConnection = "uno:socket,host=localhost,port=8100;urp;StarOffice.NamingService";
    	mstrConnection = strConnection;
		XMultiServiceFactory xFactory = connect( mstrConnection);
		mxDesktop = getDesktop( xFactory);
	}

	public void generateDocument( String strOpenURL, String strSaveURL)
	throws ISPACException
	{
        XComponent xComponent = null;

       // long begin = System.currentTimeMillis();

		xComponent = loadDocument( mxDesktop, strOpenURL);

		generateDocument( xComponent);

		saveDocument( xComponent, strSaveURL);

		xComponent.dispose();

//        long end = System.currentTimeMillis();

	}

	public long getAverage()
	{
	    if (count == 0) return 0;

		return time /count;
	}

    protected XMultiServiceFactory connect( String connectStr )
    throws ISPACException {

    	try
		{
	        XMultiServiceFactory xFactory = null;

	        // Get component context
	        XComponentContext xcomponentcontext = Bootstrap.createInitialComponentContext( null);

	        // initial serviceManager
	        XMultiComponentFactory xLocalServiceManager = xcomponentcontext.getServiceManager();

	        // create a connector, so that it can contact the office
	        Object  xUrlResolver  =
	        xLocalServiceManager.createInstanceWithContext( "com.sun.star.bridge.UnoUrlResolver", xcomponentcontext);

	        XUnoUrlResolver urlResolver =
	        (XUnoUrlResolver) UnoRuntime.queryInterface(  XUnoUrlResolver.class, xUrlResolver);

	        Object rInitialObject = urlResolver.resolve( connectStr);

	        XNamingService rName = (XNamingService) UnoRuntime.queryInterface( XNamingService.class, rInitialObject);

	        if( rName != null )
	        {
	            Object rXsmgr = rName.getRegisteredObject( "StarOffice.ServiceManager" );
	            xFactory = (XMultiServiceFactory) UnoRuntime.queryInterface( XMultiServiceFactory.class, rXsmgr);
	        }

	        return  xFactory;
		}
    	catch( Exception e)
		{
    		throw new ISPACException(e);
		}
    }

    protected XDesktop getDesktop( XMultiServiceFactory xFactory )
    throws ISPACException {

    	try
		{
	        XInterface xInterface = null;
	        XDesktop xDesktop = null;

	        xInterface = (XInterface) xFactory.createInstance( "com.sun.star.frame.Desktop");
	        xDesktop = (XDesktop) UnoRuntime.queryInterface( XDesktop.class, xInterface);

	        return xDesktop;
		}
    	catch( Exception e)
		{
     		throw new ISPACException(e);
		}
    }

    protected XComponent loadDocument( XDesktop xDesktop, String strURL)
    throws ISPACException {

    	try
		{
	        XComponent xComponent = null;
	        PropertyValue[] arguments = new PropertyValue[1];
	        arguments[0] = new PropertyValue();
	        arguments[0].Name = "Hidden";
	        arguments[0].Value = new Boolean(true);

	        XComponentLoader xComponentLoader = null;

	        xComponentLoader = (XComponentLoader) UnoRuntime.queryInterface( XComponentLoader.class, xDesktop);

	        xComponent  = xComponentLoader.loadComponentFromURL( strURL, "_blank", 0, arguments);

	        return xComponent;
		}
    	catch( Exception e)
		{
     		throw new ISPACException(e);
		}
    }

    protected void generateDocument(  XComponent xComponent)
    throws ISPACException {

        HashMap tagsMap = new HashMap();

        try
		{
	        XReplaceable xReplaceable = null;
	        XReplaceDescriptor xReplaceDescriptor = null;

	        xReplaceable = (XReplaceable) UnoRuntime.queryInterface( XReplaceable.class, xComponent);

	        // Descriptor to set properies for replace
	        xReplaceDescriptor = xReplaceable.createReplaceDescriptor();
	        xReplaceDescriptor.setSearchString( "@@IFTAG@@.*@@");
	        Object object = new Boolean( true);
	        xReplaceDescriptor.setPropertyValue( "SearchRegularExpression", object);

	        XIndexAccess xIndexAccess = null;
	        XTextRange xTextRange = null;
	        String strTag = null;
	        String strValue = null;

	        // Busca todos los tags declarados en el documento
	        xIndexAccess = xReplaceable.findAll( xReplaceDescriptor);

	        for (int i = 0; i < xIndexAccess.getCount(); i++)
	        {
	            object = xIndexAccess.getByIndex( i);

	            xTextRange = (XTextRange) UnoRuntime.queryInterface( XTextRange.class, object);
	            strTag = xTextRange.getString();

	            if (!tagsMap.containsKey( strTag))
	            {
	                strValue = "## tag ## :" + Integer.toString( i);
	                tagsMap.put( strTag,  strValue);
	            }
	        }

	        object = new Boolean( false);
	        xReplaceDescriptor.setPropertyValue( "SearchRegularExpression", object);

	        Iterator iterator = tagsMap.entrySet().iterator();

	        while (iterator.hasNext())
	        {
	            Entry entry = (Entry) iterator.next();
	            xReplaceDescriptor.setSearchString( (String) entry.getKey());
	            xReplaceDescriptor.setReplaceString( (String) entry.getValue());

	            xReplaceable.replaceAll( xReplaceDescriptor);
	        }
		}
    	catch( Exception e)
		{
     		throw new ISPACException(e);
		}
    }

    protected void saveDocument( XComponent xComponent, String strURL)
    throws ISPACException {

    	try
		{
	        PropertyValue[] arguments = new PropertyValue[2];
	        arguments[0] = new PropertyValue();
	        arguments[0].Name = "Overwrite";
	        arguments[0].Value = Boolean.TRUE;

	        arguments[1] = new PropertyValue();
	        arguments[1].Name = "FilterName";
	        arguments[1].Value = "MS Word 97";

	        XStorable xStorable = (XStorable) UnoRuntime.queryInterface( XStorable.class, xComponent);
	        xStorable.storeAsURL( strURL, arguments);
		}
    	catch( Exception e)
		{
     		throw new ISPACException(e);
		}
    }
}
