package uk.co.mmscomputing.util.configuration;

import java.io.*;
import java.util.*;

import uk.co.mmscomputing.util.JarFile;

public class ConfigurationMap extends Hashtable{

  static private String basePath="base_path";
  static private String confPath="config_path";
  static private String conffn="configuration.txt";

  public ConfigurationMap(){
  }

  public ConfigurationMap(Class main){
    try{
      String cn=main.getName();
      String dn=cn.substring(0,cn.lastIndexOf('.'));
      String base=dn.replace('.',File.separatorChar)+File.separator;
      put(basePath,base);

      String cfn=base+conffn;         // search 'conffn' is in same directory as class main
      if(new JarFile(cfn).exists()){  // load defaults
        ConfigurationReader cr=new ConfigurationReader(cfn);
        cr.read(this);cr.close();
      }
      put(confPath,cfn);              // set conffn
//      System.err.println("ConfigurationMap(Class): confPath = "+getString(confPath));
    }catch(IOException ioe){
      System.err.println("ConfigurationMap(Class): "+ioe.getMessage());
    }
  }

  public String getString(String key, String value){
    Object s=get(key);
    if(s!=null){ return (String)s; }
    put(key,value);
    return value;
  }

  public String getString(String key){
    Object s=get(key);
    if(s!=null){ return (String)s; }
    put(key,"");
    return "";
  }

  public int getInt(String key, int value){
    Object s=get(key);
    if(s!=null){
      try{
         return Integer.parseInt((String)s);
      }catch(NumberFormatException nfe){
      }
    }
    put(key,Integer.toString(value));
    return value;
  }
  
  public int getInt(String key){
    Object s=get(key);
    if(s!=null){
      try{
         return Integer.parseInt((String)s);
      }catch(NumberFormatException nfe){
      }
    }
    put(key,"0");
    return 0;
  }
  
}

