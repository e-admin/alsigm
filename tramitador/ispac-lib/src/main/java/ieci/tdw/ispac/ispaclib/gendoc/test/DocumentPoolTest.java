package ieci.tdw.ispac.ispaclib.gendoc.test;

import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.util.Semaphore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DocumentPoolTest
{

	private static DocumentPoolTest mInstance = null;

	private HashMap mSources = null;
	private Semaphore mSemaphore;
	private long mTimeout = 30000;

	private DocumentPoolTest()
	throws ISPACException
	{
		ISPACConfiguration config = ISPACConfiguration.getInstance();

		mSources = new HashMap();
		String connection = config.get( ISPACConfiguration.OPEN_OFFICE_CONNECT);
		DocumentSource source = new DocumentSource( connection);
		DocumentParserTest document = source.getDocument();
		mSources.put( document, source);

		String parameter = config.get( ISPACConfiguration.OPEN_OFFICE_ADDITIONAL_INSTANCES);
		if (parameter != null)
		{
			int count = Integer.parseInt( parameter);
			for (int i = 0; i < count; i++)
			{
				connection = config.get( ISPACConfiguration.OPEN_OFFICE_CONNECT + "_" + i);
				source = new DocumentSource( connection);
				document = source.getDocument();
				mSources.put( document, source);
			}
		}
		String timeout=config.get(ISPACConfiguration.OPEN_OFFICE_TIMEOUT);
		mTimeout=30000;
		if (timeout!=null && timeout.length()>0)
		{
			try
			{
			    mTimeout=Integer.parseInt(timeout);
			} catch (NumberFormatException e)
			{
			}
		}

		mSemaphore = new Semaphore( mSources.size());
	}

	public static synchronized DocumentPoolTest getInstance()
	throws ISPACException
	{
		if (mInstance == null)
		{
			mInstance = new DocumentPoolTest();
		}

		return mInstance;
	}

	public DocumentParserTest getDocument()
	{
	    DocumentParserTest document = null;

		if (mSemaphore.acquire( mTimeout))
		{
			synchronized(mSources)
			{
				Iterator iterator = mSources.entrySet().iterator();

			    while (iterator.hasNext())
	            {
	                Map.Entry entry = (Map.Entry) iterator.next();

					DocumentSource source = (DocumentSource) entry.getValue();
					if (!source.isBusy())
					{
						source.setBusy( true);
						document = source.getDocument();
						break;
					}
	            }
			}
		}

		return document;
	}

	public void releaseDocument( DocumentParserTest document)
	{
		if (document == null) return;

		synchronized(mSources)
		{
		    DocumentSource source=(DocumentSource)mSources.get(document);
			if (source!=null)
			{
				source.setBusy( false);
			}
		}

		mSemaphore.release();
	}

	public int getSize()
	{
		return mSources.size();
	}

	public long getAverage()
	{
	    DocumentParserTest document = null;

        long time = 0;
        long count = 0;

        synchronized(mSources)
		{
			Iterator iterator = mSources.entrySet().iterator();

		    while (iterator.hasNext())
            {
                Map.Entry entry = (Map.Entry) iterator.next();

				DocumentSource source = (DocumentSource) entry.getValue();
				document = source.getDocument();
				if (document.getAverage() != 0)
				{
					time += document.getAverage();
					count++;
				}
            }
		}

		return time / count;
	}

	public class DocumentSource
	{
    	boolean busy;
    	DocumentParserTest document;

    	public DocumentSource( String connection)
    	throws ISPACException
    	{
    		busy = false;
    		document = new DocumentParserTest( connection);
    	}
    	public void setBusy( boolean busy)
    	{
    		this.busy = busy;
    	}
    	public boolean isBusy()
    	{
    		return this.busy;
    	}
    	public DocumentParserTest getDocument()
    	{
    		return document;
    	}
    }
}
