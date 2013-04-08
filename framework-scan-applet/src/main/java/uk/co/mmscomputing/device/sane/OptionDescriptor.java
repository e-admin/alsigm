package uk.co.mmscomputing.device.sane;

abstract public class OptionDescriptor implements SaneConstants{

  /* [1] p.26 4.3.6
     The descriptor is only valid as long as the device handle is valid.
  */

  protected int    getWordControlOption()throws SaneIOException{
    return jsane.getWordControlOption(handle,no);
  }

  protected int    setWordControlOption(int value)throws SaneIOException{
    return jsane.setWordControlOption(handle,no,value);
  }

  protected void   getWordArrayControlOption(int[] values)throws SaneIOException{
    jsane.getWordArrayControlOption(handle,no,values);
  }

  protected int    setWordArrayControlOption(int[] values)throws SaneIOException{
    return jsane.setWordArrayControlOption(handle,no,values);
  }

  protected String getStringControlOption(int size)throws SaneIOException{
    return jsane.getStringControlOption(handle,no,size);
  }

  protected int    setStringControlOption(int size,String str)throws SaneIOException{
    return jsane.setStringControlOption(handle,no,size,str);
  }

  private int handle=0;   // sane device handle
  private int no=0;       // sane option number

  protected OptionDescriptor(int handle,int no){
    this.handle=handle;
    this.no=no;    
  }
}

/*
[1] SANE Standard Version 1.03
    (Scanner Access Now Easy)
    2002-10-10
    http://www.sane-project.org

*/