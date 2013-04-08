package es.ieci.tecdoc.fwktd.applets.scan.jtwain;


public class TwainDefaultNativeLoadStrategy implements TwainINativeLoadStrategy {

    @SuppressWarnings("unchecked")
	public boolean load( Class cl, String libname ) {
        //  win : load library 'jtwain.dll'
        return JarLib.load(JTwain.class,"jtwain");  
    }
}
