package uk.co.mmscomputing.device.twain;

import java.util.*;

class TwainArray extends TwainContainer{

// TWON_ARRAY. Container for array of values (a simplified TW_ENUMERATION)
/*
typedef struct {
   TW_UINT16  ItemType;
   TW_UINT32  NumItems;    // How many items in ItemList
   TW_UINT8   ItemList[1]; // Array of ItemType values starts here
} TW_ARRAY, FAR * pTW_ARRAY;
*/
  int            count;
  Vector items=new Vector();

  TwainArray(int cap,byte[] container){                       // TW_ARRAY
    super(cap,container);
    count = jtwain.getINT32(container,2);

    for(int i=0,off=6;i<count;i++){
      items.add(getObjectAt(container,off));
      off+=typeSizes[type];
    }
  }

/*
  public TwainArray(int cap,int type){
    super(cap,type);
  }
*/

  int getType(){return TWON_ARRAY;};

  byte[] getBytes(){
    int count=items.size();
    int len  =6+count*typeSizes[type];
    byte[] container=new byte[len];
    jtwain.setINT16(container,0,type);
    jtwain.setINT32(container,2,count);

    for(int i=0,off=6;i<count;i++){
      setObjectAt(container,off,items.get(i));
      off+=typeSizes[type];
    }
    return container;
  }

  public Object getCurrentValue()throws TwainIOException{
    throw new TwainIOException(getClass().getName()+".getCurrentValue:\n\tnot applicable");
  }

  public void setCurrentValue(Object obj)throws TwainIOException{
    throw new TwainIOException(getClass().getName()+".setCurrentValue:\n\tnot applicable");
  }

  public Object getDefaultValue()throws TwainIOException{
    throw new TwainIOException(getClass().getName()+".getDefaultValue:\n\tnot applicable");
  }

  public void setDefaultValue(Object obj)throws TwainIOException{
    throw new TwainIOException(getClass().getName()+".setDefaultValue:\n\tnot applicable");
  }

  public Object[] getItems(){return items.toArray();}

  public String toString(){
    String s=super.toString();
    s+="count        = "+count+"\n";

    Enumeration e=items.elements();
    for(int i=0;e.hasMoreElements();i++) {
      s+="items["+i+"] = "+e.nextElement()+"\n";
    }
    return s;
  }
}