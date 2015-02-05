package ieci.tdw.ispac.ispaclib.gendoc.test;

import ieci.tdw.ispac.ispaclib.util.FileTemporary;

import java.util.ArrayList;

public class GenerateDocumentTest
{

    public final static String DIR_TEMPORAL_IN 	=  "/home/juanin/test/temp";
    public final static String DIR_TEMPORAL_OUT	=  "/home/juanin/test/out";
    public final static String TEST_TEMPLATE	=  "/home/juanin/test/templates/mutua.sxw";

    public final static int NUM_DOCUMENTS = 100;
    public final static int NUM_THREADS=5;

    public static void main(String args[]) {

        String in = null;
        String template = null;
        ArrayList threadlist = new ArrayList();
        ArrayList tasklist = new ArrayList();

        try
		{
    		FileTemporary temporary = new FileTemporary(GenerateDocumentTest.DIR_TEMPORAL_IN);
            template = GenerateDocumentTest.TEST_TEMPLATE;

            //int numthreads=factory.getSize();


            long begin = System.currentTimeMillis();

            int threadbatch= NUM_DOCUMENTS / NUM_THREADS;

        	threadlist.clear();

			in =  "file:///"+ GenerateDocumentTest.DIR_TEMPORAL_IN +"/" + temporary.put(template, ".doc");
            for (int i = 0; i < NUM_THREADS ; i++)
	        {
                Task task=new Task("THREAD("+i+")-",threadbatch, in);
                tasklist.add(task);
	            threadlist.add( new Thread(task));
	        }

            // Arranca las hebras simultáneamente
	        for (int i = 0; i < threadlist.size(); i++)
	        {
	        	((Thread) threadlist.get( i)).start();
	        }

	        // Espera a que acaben todas las hebras
	        for (int i = 0; i < threadlist.size(); i++)
	        {
	            ((Thread) threadlist.get(i)).join();
	        }

	        long end = System.currentTimeMillis();


	        long srvtime=0;
	        // calcula estadísticas
	        for (int i = 0; i < tasklist.size(); i++)
	        {
	            Task task=(Task)tasklist.get(i);
	            srvtime+=task.timesrv;
	        }
	        srvtime=srvtime/tasklist.size();

	        System.out.println("\n=======================");
        	System.out.println("Media  (ms): " + srvtime);
	        System.out.println("Tiempo total (s): " + Long.toString((end - begin)/1000));
	        System.out.println("Hebras: " + NUM_THREADS);
	        System.out.println("Documentos generados: " + Long.toString( NUM_DOCUMENTS));
	        System.exit(0);
		}
        catch(Exception e)
		{
            e.printStackTrace( System.out);
		}
    }



    static class Task implements Runnable
	{
    	String in = null;
    	String out = null;
    	int ntimes;
    	String threadid;
    	public long timetotal=0;
    	public long timesrv=0;

    	public Task(String threadid, int ntimes ,String in)
    	{
    		this.in = in;
    		out = "file:///"+ GenerateDocumentTest.DIR_TEMPORAL_OUT +"/"+threadid+ ".doc";
    		this.threadid=threadid;
    		this.ntimes=ntimes;
    	}

    	public void run()
    	{

    	    int timeout=0;


    	    for (int i=0;i<ntimes;i++)
		    {
	    		try
				{
	    		    long lapse = System.currentTimeMillis();

	        		DocumentPoolTest factory = DocumentPoolTest.getInstance();
	        		DocumentParserTest document=null;
	        		try
	        		{
	        		    while(document==null)
	        		    {
	        		        document = factory.getDocument();
	        		        if (document==null)
	        		            System.out.println(threadid+"["+i+"] tiempo: timeout "+timeout++);
	        		    }
			        	document.generateDocument(in,out);

	        		}
	        		finally
	        		{
	        		    factory.releaseDocument( document);
	        		}

	        		lapse=System.currentTimeMillis()-lapse;
	        		System.out.println(threadid+"["+i+"] tiempo: "+lapse);
	        		timetotal+=lapse;

				}
	            catch( Exception e)
	    		{
	                e.printStackTrace(System.out);
	                return;
	            }
		    }

    	    timesrv=timetotal/ntimes;

	        System.out.println(threadid+" Tiempo srv (ms): " + timesrv);
	        System.out.println(threadid+" Tiempo total (s): " + timetotal/1000);
	        System.out.println(threadid+" Documentos generados: " + Long.toString(ntimes));
	        System.out.println(threadid+" Timeouts : " + timeout);
    	}
    }
}
