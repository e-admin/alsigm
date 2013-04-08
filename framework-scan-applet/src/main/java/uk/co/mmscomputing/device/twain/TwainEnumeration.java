package uk.co.mmscomputing.device.twain;

import java.util.*;

class TwainEnumeration extends TwainContainer{

// TWON_ENUMERATION. Container for a collection of values.
/*
typedef struct {
   TW_UINT16  ItemType;
   TW_UINT32  NumItems;     // How many items in ItemList
   TW_UINT32  CurrentIndex; // Current value is in ItemList[CurrentIndex]
   TW_UINT32  DefaultIndex; // Powerup value is in ItemList[DefaultIndex]
   TW_UINT8   ItemList[1];  // Array of ItemType values starts here
} TW_ENUMERATION, FAR * pTW_ENUMERATION;
*/

  int            count;
  int            currentIndex;
  int            defaultIndex;                                // don't care = -1
  Vector items=new Vector();

  TwainEnumeration(int cap,byte[] container){                 // TW_ENUMERATION
    super(cap,container);
    count         = jtwain.getINT32(container,2);
    currentIndex  = jtwain.getINT32(container,6);
    defaultIndex  = jtwain.getINT32(container,10);

    for(int i=0,off=14;i<count;i++){
      items.add(getObjectAt(container,off));
      off+=typeSizes[type];
    }
  }

  int getType(){return TWON_ENUMERATION;};

  public Object[] getItems(){return items.toArray();}

  byte[] getBytes(){
    int count=items.size();
    int len  =14+count*typeSizes[type];
    byte[] container=new byte[len];
    jtwain.setINT16(container,0,type);
    jtwain.setINT32(container,2,count);
    jtwain.setINT32(container,6,currentIndex);
    jtwain.setINT32(container,10,defaultIndex);

    for(int i=0,off=14;i<count;i++){
      setObjectAt(container,off,items.get(i));
      off+=typeSizes[type];
    }
    return container;
  }

  public Object getCurrentValue()throws TwainIOException{
    return items.get(currentIndex);
  }

  public void setCurrentValue(Object obj)throws TwainIOException{
    int count=items.size();
    for(int i=0;i<count;i++){
      Object item=items.get(i);
      if(obj.equals(item)){
        currentIndex=i;
        return;
      }
    }
    throw new TwainIOException(getClass().getName()+".setCurrentValue:\n\tCould not find "+obj.toString());
  }

  public Object getDefaultValue()throws TwainIOException{
    return items.get(defaultIndex);
  }

  public void setDefaultValue(Object obj)throws TwainIOException{
    int count=items.size();
    for(int i=0;i<count;i++){
      Object item=items.get(i);
      if(obj.equals(item)){
        defaultIndex=i;
        return;
      }
    }
    throw new TwainIOException(getClass().getName()+".setDefaultValue:\n\tCould not find "+obj.toString());
  }

  public String toString(){
    String s=super.toString();
    s+="count        = "+count+"\n";
    s+="currentIndex = "+currentIndex+"\n";
    s+="defaultIndex = "+defaultIndex+"\n";

    Enumeration e=items.elements();
    for(int i=0;e.hasMoreElements();i++) {
      s+="items["+i+"] = "+e.nextElement()+"\n";
    }
    return s;
  }
}