package ieci.tecdoc.sgm.registro.utils;

import java.io.*;

public class FilenameTmpFilter implements FilenameFilter{
    String semiName;
    
    public FilenameTmpFilter(String semiName){
        this.semiName=semiName;
    }
    public boolean accept(File dir, String name){
        return name.startsWith(semiName);
    }
}