package uk.co.mmscomputing.util.metadata;

import java.util.*;
import uk.co.mmscomputing.util.configuration.*;

public class Metadata extends ConfigurationMap{

  private  Vector listeners=new Vector();


  public Metadata(){
  }

  public Metadata(Class main){
    super(main);
  }

  public void putString(String key, String value){
    put(key,value);
  }

  public void putBoolean(String key, boolean value){
    put(key,new String(Boolean.toString(value)));
  }

  public boolean getBoolean(String key){
    String s=getString(key);
    return Boolean.getBoolean(s);
  }

  public void putInt(String key, int value){
    put(key,new String(Integer.toString(value)));
  }

  public void addListener(MetadataListener listener){
    listeners.add(listener);
  }

  public void addListener(int index,MetadataListener listener){
    listeners.add(index,listener);
  }

//  public void addListeners(Vector listToAdd){
//    listeners.addAll(listToAdd);
//  }

  public void removeListener(MetadataListener listener){
    listeners.remove(listener);
  }

  public void fireListenerUpdate(Object type){
    for(Enumeration e = listeners.elements(); e.hasMoreElements() ;){
      MetadataListener listener=(MetadataListener)e.nextElement();
      listener.update(type,this);
    }
  }

  public String toString(){
    String s=getClass().getName()+"\n";
    for(Enumeration e = listeners.elements(); e.hasMoreElements() ;){
      MetadataListener listener=(MetadataListener)e.nextElement();
      s+=listener+"\n";
    }
    return s;
  }
}