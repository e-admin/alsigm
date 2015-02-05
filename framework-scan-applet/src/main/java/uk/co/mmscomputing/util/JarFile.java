package uk.co.mmscomputing.util;

import java.io.*;
import java.net.*;

public class JarFile extends File{

  static private ClassLoader cl = JarFile.class.getClassLoader();

  public JarFile(String fn)throws IOException{
    super(load(fn));
  }

  static private String load(String fn)throws IOException{
    String filefn = System.getProperty("user.dir")+File.separator+fn.replace('/',File.separatorChar);
    String dir=new File(filefn).getParent();
    if(dir!=null){new File(dir).mkdirs();}
    if(new File(filefn).exists()){ return filefn;}

    fn=fn.replace(File.separatorChar,'/');
    URL ufn=cl.getResource(fn);
    if(ufn==null){ return filefn;}
                                            
    if(ufn.toString().startsWith("file:")){
      filefn=ufn.getFile();    
    }else if(ufn.toString().startsWith("jar:")){
      extract(filefn,ufn);  // extract file into dir and load from there
    }
    return filefn;
  }

  static private void extract(String fn, URL url)throws IOException{
    InputStream      in  = url.openStream();
    FileOutputStream out = new FileOutputStream(fn);
    byte[] buffer=new byte[4096];
    int count=0;
    while((count=in.read(buffer))>0){
      out.write(buffer,0,count);
    }
    out.close();
    in.close();
  }


}