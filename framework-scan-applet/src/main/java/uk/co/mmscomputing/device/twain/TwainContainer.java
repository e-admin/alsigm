package uk.co.mmscomputing.device.twain;

import java.awt.geom.*;

public abstract class TwainContainer implements TwainConstants{

  protected int    cap;
  protected int    type;

  TwainContainer(int cap,byte[] container){
    this.cap=cap;
    this.type=jtwain.getINT16(container,0);
  }

  TwainContainer(int cap,int type){
    this.cap=cap;
    this.type=type;
  }

  public int getCapabilityId(){return cap;}

  abstract int    getType();
  abstract byte[] getBytes();

  public int getItemType(){return type;}
  abstract public Object[] getItems();


  private boolean booleanValue(Object obj)throws TwainIOException{
    if(obj instanceof Number){
      return (((Number)obj).intValue()!=0);
    }else if(obj instanceof Boolean){
      return ((Boolean)obj).booleanValue();
    }else if(obj instanceof String){
      return Boolean.valueOf((String)obj).booleanValue();
    }
    throw new TwainIOException(getClass().getName()+".booleanValue:\n\tUnsupported data type: "+obj.getClass().getName());
  }

  private int intValue(Object obj)throws TwainIOException{
    if(obj instanceof Number){
      return ((Number)obj).intValue();
    }else if(obj instanceof Boolean){
      return ((((Boolean)obj).booleanValue())?1:0);
    }else if(obj instanceof String){
      String s=(String)obj;
      try{
        return Integer.parseInt(s);
      }catch(Exception e){
        throw new TwainIOException(getClass().getName()+".intValue:\n\tCannot convert string [\""+s+"\"] to int.");
      }
    }
    throw new TwainIOException(getClass().getName()+".intValue:\n\tUnsupported data type: "+obj.getClass().getName());
  }

  private double doubleValue(Object obj)throws TwainIOException{
    if(obj instanceof Number){
      return ((Number)obj).doubleValue();
    }else if(obj instanceof Boolean){
      return ((((Boolean)obj).booleanValue())?1:0);
    }else if(obj instanceof String){
      String s=(String)obj;
      try{
        return Double.parseDouble(s);
      }catch(Exception e){
        throw new TwainIOException(getClass().getName()+".doubleValue:\n\tCannot convert string [\""+s+"\"] to double.");
      }
    }
    throw new TwainIOException(getClass().getName()+".doubleValue:\n\tUnsupported data type: "+obj.getClass().getName());
  }

  // Current Value

  abstract public Object getCurrentValue()throws TwainIOException;

  public boolean booleanValue()throws TwainIOException{  return booleanValue(getCurrentValue());}
  public int     intValue()throws TwainIOException{      return intValue(getCurrentValue());}
  public double  doubleValue()throws TwainIOException{   return doubleValue(getCurrentValue());}

  abstract public void setCurrentValue(Object v)throws TwainIOException;

  public void setCurrentValue(boolean v)throws TwainIOException{ setCurrentValue(new Boolean(v));}
  public void setCurrentValue(int v)throws TwainIOException{     setCurrentValue(new Integer(v));}
  public void setCurrentValue(double v)throws TwainIOException{  setCurrentValue(new Double(v));}

  // Default Value

  abstract public Object getDefaultValue()throws TwainIOException;

  public boolean booleanDefaultValue()throws TwainIOException{  return booleanValue(getDefaultValue());}
  public int     intDefaultValue()throws TwainIOException{      return intValue(getDefaultValue());}
  public double  doubleDefaultValue()throws TwainIOException{   return doubleValue(getDefaultValue());}

  abstract public void setDefaultValue(Object v)throws TwainIOException;

  public void setDefaultValue(boolean v)throws TwainIOException{ setDefaultValue(new Boolean(v));}
  public void setDefaultValue(int v)throws TwainIOException{     setDefaultValue(new Integer(v));}
  public void setDefaultValue(double v)throws TwainIOException{  setDefaultValue(new Double(v));}

  // Object to byte conversion, vice versa

  protected Object get32BitObjectAt(byte[] container,int index){
    switch(type){
    case TWTY_INT8:
    case TWTY_INT16:
    case TWTY_INT32:    return new Integer(jtwain.getINT32(container,index));
    case TWTY_UINT8:    return new Integer(jtwain.getINT32(container,index)&0x000000FF);
    case TWTY_UINT16:   return new Integer(jtwain.getINT32(container,index)&0x0000FFFF);
    case TWTY_UINT32:   return new Long(((long)jtwain.getINT32(container,index))&0x00000000FFFFFFFFL);
    case TWTY_BOOL:     return new Boolean((jtwain.getINT32(container,index)==0)?false:true);
    case TWTY_FIX32:    return new Double(jtwain.getFIX32(container,index));

    // only null !

    case TWTY_FRAME:    // ICAP_FRAMES
    case TWTY_STR32:
    case TWTY_STR64:
    case TWTY_STR128:
    case TWTY_STR255:
    case TWTY_STR1024:
    case TWTY_UNI512:
                        return new Integer(jtwain.getINT32(container,index));
    default: 
//      System.out.println(getClass().getName()+".get32BitObjectAt:\n\tUnsupported type = "+type);
      new TwainIOException(getClass().getName()+".get32BitObjectAt:\n\tUnsupported type = "+type).printStackTrace();
    }
    return null;
  }

  protected void set32BitObjectAt(byte[] container,int index,Object item){
    int buf=0;
    if(item instanceof Integer){
      int v=((Integer)item).intValue();
      if(type==TWTY_FIX32){
        jtwain.setFIX32(container,index,v);
      }else if(type==TWTY_BOOL){
        jtwain.setINT32(container,index,(v==0)?0:1);
      }else{
        jtwain.setINT32(container,index,v);
      }
    }else if(item instanceof Double){
      double v=((Double)item).doubleValue();
      if(type==TWTY_FIX32){
        jtwain.setFIX32(container,index,v);
      }else if(type==TWTY_BOOL){
        jtwain.setINT32(container,index,(v==0)?0:1);
      }else{
        jtwain.setINT32(container,index,(int)v);
      }
    }else if(item instanceof Boolean){
      int v=(((Boolean)item).booleanValue())?1:0;
      if(type==TWTY_FIX32){
        jtwain.setFIX32(container,index,v);
      }else{
        jtwain.setINT32(container,index,v);
      }
    }else if(item instanceof String){
      if(type==TWTY_FIX32){
        this.set32BitObjectAt(container,index,new Double((String)item));
      }else{
        this.set32BitObjectAt(container,index,new Integer((String)item));
      }
    }else{
      System.out.println(getClass().getName()+".set32BitObjectAt:\n\tUnsupported type = "+type);
    }
  }

  protected Object getObjectAt(byte[] container,int index){
    switch(type){
    case TWTY_INT8:     return new Integer(container[index]);
    case TWTY_INT16:    return new Integer(jtwain.getINT16(container,index));
    case TWTY_INT32:    return new Integer(jtwain.getINT32(container,index));
    case TWTY_UINT8:    return new Integer(container[index]&0x000000FF);
    case TWTY_UINT16:   return new Integer(jtwain.getINT16(container,index)&0x0000FFFF);
    case TWTY_UINT32:   return new Long(((long)jtwain.getINT32(container,index))&0x00000000FFFFFFFFL);
    case TWTY_BOOL:     return new Boolean((jtwain.getINT16(container,index)==0)?false:true);
    case TWTY_FIX32:    return new Double(jtwain.getFIX32(container,index));
    case TWTY_FRAME:
      double x=jtwain.getFIX32(container,index);          // left
      double y=jtwain.getFIX32(container,index+4);        // top
      double w=jtwain.getFIX32(container,index+8)-x;      // right
      double h=jtwain.getFIX32(container,index+12)-y;     // bottom
      return new Rectangle2D.Double(x,y,w,h);
    case TWTY_STR32:
    case TWTY_STR64:
    case TWTY_STR128:
    case TWTY_STR255:
      String s="";
      for(int i=0;(container[index+i]!=0)&&(i<typeSizes[type]);i++){
        s+=(char)container[index+i];
      }
      return s;
    default: System.out.println(getClass().getName()+".getObjectAt:\n\tUnsupported type = "+type);
    }
    return null;
  }

  private void set16BitObjectAt(byte[] container,int index,Object item){
    if(item instanceof Number){
      int v=(((Number)item).intValue());
      jtwain.setINT16(container,index,v);
    }else if(item instanceof Boolean){
      int v=(((Boolean)item).booleanValue())?1:0;
      jtwain.setINT16(container,index,v);
    }else{
      System.out.println("3\b"+getClass().getName()+".set16BitObjectAt:\n\tUnsupported type = "+item.getClass().getName());
    }
  }

  protected void setObjectAt(byte[] container,int index,Object item){
    switch(type){
    case TWTY_INT16:
    case TWTY_UINT16:    set16BitObjectAt(container,index,item);break;
    case TWTY_FIX32:
    case TWTY_INT32:
    case TWTY_UINT32:    set32BitObjectAt(container,index,item);break;
    default: System.out.println("3\b"+getClass().getName()+".setObjectAt:\n\tUnsupported type = "+type);
    }
  }

  public String toString(){
    String s=getClass().getName()+"\n";
    s+="cap          = 0x"+Integer.toHexString(cap)+"\n";
    s+="type         = 0x"+Integer.toHexString(type)+"\n";
    return s;
  }

  static String[] hexs={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};

  static public String toString(byte[] data){
    int    v;
    String s="\n";
    int i=0;
    while(i<data.length){
      s+=" ";
      s+=hexs[(data[i]>>4)&0x0F];
      s+=hexs[(data[i]   )&0x0F];
      if(((i+1)%8)==0){s+="\n";}
      i++;
    }
    if(((i+1)%8)!=0){s+="\n";}
    return s;
  }
}


