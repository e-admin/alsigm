package uk.co.mmscomputing.device.sane.option;

import uk.co.mmscomputing.device.sane.SaneIOException;

public class IntRange extends IntDesc{

  int min,max,quant;

  public IntRange(
    int      handle,
    int      no,

    String   name,
    String   title,
    String   desc,
    int      type,
    int      unit,
    int      size,
    int      cap,
    int      min,
    int      max,
    int      quant,
    int[]    values
  ){
    super(handle,no,name,title,desc,type,unit,size,cap,values);
    this.min=min;this.max=max;this.quant=quant;
  }

  public int getMin() {
	return min;
}

public int getMax() {
	return max;
}

public int setWordValue(int i, int val)throws SaneIOException{

    if(quant!=0){
      val+=quant/2;
      val/=quant;
      val =Math.abs(val);
      val*=quant;
    }
    if(val<min){
      val=min;
    }else if(max<val){
      val=max;
    }

    return super.setWordValue(i, val);
  }

  public String toString(){
    String s=super.toString();
    s+="\n    min   "+min;
    s+="\n    max   "+max;
    s+="\n    quant "+quant;
    return s;
  }

  public DescriptorPanel getGUI(){
    gui=new IntRangePanel(this);
    return gui;
  }


}

