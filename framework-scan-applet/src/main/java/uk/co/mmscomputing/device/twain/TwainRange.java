package uk.co.mmscomputing.device.twain;

class TwainRange extends TwainContainer{

// TWON_RANGE. Container for a range of values.
/*
typedef struct {
   TW_UINT16  ItemType;
   TW_UINT32  MinValue;     // Starting value in the range.           
   TW_UINT32  MaxValue;     // Final value in the range.              
   TW_UINT32  StepSize;     // Increment from MinValue to MaxValue.   
   TW_UINT32  DefaultValue; // Power-up value.                        
   TW_UINT32  CurrentValue; // The value that is currently in effect. 
} TW_RANGE, FAR * pTW_RANGE;
*/

  private Object  minValue;
  private Object  maxValue;
  private Object  stepSize;
  private Object  defaultValue;
  private Object  currentValue;

  TwainRange(int cap,byte[] container){                     // TW_RANGE
    super(cap,container);
    minValue     = get32BitObjectAt(container,2);
    maxValue     = get32BitObjectAt(container,6);
    stepSize     = get32BitObjectAt(container,10);
    defaultValue = get32BitObjectAt(container,14);
    currentValue = get32BitObjectAt(container,18);
  }

  int getType(){return TWON_RANGE;};

  byte[] getBytes(){
    byte[] container=new byte[22];
    jtwain.setINT16(container,0,type);
    set32BitObjectAt(container,2,minValue);
    set32BitObjectAt(container,6,maxValue);
    set32BitObjectAt(container,10,stepSize);
    set32BitObjectAt(container,14,defaultValue);            // don't care : new Integer(-1)
    set32BitObjectAt(container,18,currentValue);
    return container;
  }

  public Object getCurrentValue()throws TwainIOException{           return currentValue;}
  public void setCurrentValue(Object obj)throws TwainIOException{   currentValue=obj;}

  public Object getDefaultValue()throws TwainIOException{           return defaultValue;}
  public void   setDefaultValue(Object obj)throws TwainIOException{ defaultValue=obj;}

  public Object[] getItems(){                               // todo add other values from range
    Object[] items=new Object[1];
    items[0]=currentValue;
    return items;
  }

  public String toString(){
    String s=super.toString();
    s+="minValue     = "+minValue+"\n";
    s+="maxValue     = "+maxValue+"\n";
    s+="stepSize     = "+stepSize+"\n";
    s+="defaultValue = "+defaultValue+"\n";
    s+="currentValue = "+currentValue+"\n";
    return s;
  }
}