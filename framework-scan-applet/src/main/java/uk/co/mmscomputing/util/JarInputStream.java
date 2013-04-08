package uk.co.mmscomputing.util;

import java.io.*;
import java.net.*;

public class JarInputStream extends FilterInputStream{

  static private ClassLoader cl = JarInputStream.class.getClassLoader();

  public JarInputStream(String fn)throws IOException{
    super(load(fn));
  }

  

  static private InputStream load(String fn)throws IOException{
    String filefn = System.getProperty("user.dir")+File.separator+fn.replace('/',File.separatorChar);
    if(new File(filefn).exists()){ return new FileInputStream(filefn);}
/*
    String ufn=fn.replace(File.separatorChar,'/');

    URL url=cl.getResource(ufn);
    if(url==null){ 
      String dir=new File(filefn).getParent();
      if(dir!=null){new File(dir).mkdirs();}
      return new FileInputStream(filefn);
    }
*/
    InputStream in = cl.getResourceAsStream(fn);
    if(in == null){
      throw new IOException("uk.co.mmscomputing.util.JarInputStream.load\n\tFile "+fn+" does not exist");
    }
    return in;
  }
}