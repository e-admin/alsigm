package uk.co.mmscomputing.device.twain;

import uk.co.mmscomputing.util.JarLib;

public class TwainDefaultNativeLoadStrategy implements TwainINativeLoadStrategy {

    public boolean load( Class cl, String libname ) {
        //  win : load library 'jtwain.dll'
        return JarLib.load(jtwain.class,"jtwain");  
    }

}
