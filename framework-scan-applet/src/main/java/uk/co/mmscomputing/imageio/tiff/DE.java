package uk.co.mmscomputing.imageio.tiff;

import java.io.*;
import java.util.*;
import java.nio.ByteOrder;

import javax.imageio.stream.*;

import uk.co.mmscomputing.math.Rational;

abstract class DE implements TIFFConstants{  // ImageFileDirectoryEntry

  private   int  tag;
  protected int  type;
  protected long len;
  protected long val;

  DE(int tagid)throws IOException{
    tag=tagid;
  }

  int  getTag(){return tag;}

  int  getType(){return type;}
  void setType(int type){this.type=type;}

  long getCount(){return (int)len;}
  void setCount(long count){this.len=count;}

  long getLength(){return (int)len;}
  void setLength(long len){this.len=len;}

  long getValue(){return val;}
  void setValue(long value){val=value;}

  void read(ImageInputStream in)throws IOException{
    type=in.readUnsignedShort();
    len=in.readUnsignedInt();           // count of items of tag type to follow
    val=readValue(in);

//    System.out.println(getClass().getName()+"\n\t Tag = "+tag+" Type = "+type+" Len = "+len+" Val = 0x"+Long.toHexString(val)+" Val = "+val);
  }

  private long readValue(ImageInputStream in)throws IOException{
    long val=0;
    int sot=sizeOfType();
    if(len*sot<=4){
      val=in.readUnsignedInt();
      if(in.getByteOrder()==ByteOrder.BIG_ENDIAN){
        long v;
        switch(type){
        case BYTE:      case SBYTE:     
          v   = val     &0x000000FF;
          val =(val>>24)&0x000000FF;
          val|=(v  <<24)&0xFF000000;

          v   = val     &0x0000FF00;
          val|=(val>> 8)&0x0000FF00;
          val|=(v  << 8)&0x00FF0000;
          break;
        case SHORT:     case SSHORT:
          v   = val     &0x0000FFFF;
          val =(val>>16)&0x0000FFFF;
          val|=(v  <<16)&0xFFFF0000;
          break;
        }
      }
    }else{
      val=in.readUnsignedInt();
    }
    return val;
  }

  void writeEntry(ImageOutputStream out) throws IOException {
    out.writeShort(tag);
    out.writeShort(type);
    out.writeInt((int)len);
    out.writeInt((int)val);
  }

  protected int sizeOfType(){
    switch(type){
    case BYTE:      case SBYTE:     case UNDEFINED: return 1;
    case SHORT:     case SSHORT:                    return 2;
    case LONG:      case SLONG:     case FLOAT:     return 4;
    case RATIONAL:  case SRATIONAL: case DOUBLE:    return 8;
    default:/*error*/return -1;
    }    
  }

  protected long readInt(ImageInputStream in)throws IOException{
    switch(type){
    case BYTE:   return in.readUnsignedByte();
    case SHORT:  return in.readUnsignedShort();
    case LONG:   return in.readUnsignedInt();

    case SBYTE:  return in.read();
    case SSHORT: return in.readShort();
    case SLONG:  return in.readInt();

    default:/*error*/return 0;
    }    
  }

  protected void writeInt(ImageOutputStream out,long val)throws IOException{
    switch(type){
    case BYTE:   out.writeByte((byte)val);break;
    case SHORT:  out.writeShort((short)val);break;
    case LONG:   out.writeInt((int)val);break;

    case SBYTE:  out.write((byte)val);break;
    case SSHORT: out.writeShort((short)val);break;
    case SLONG:  out.writeInt((int)val);break;

    default:/*error*/break;
    }    
  }

  @SuppressWarnings("unchecked")
protected String[] readString(ImageInputStream in)throws IOException{
    if(type!=ASCII){/*error*/return null;}
    in.mark();
    in.seek(val);

    Vector v=new Vector();
    int i=0;
    while(i<len){
      String s="";
      int b=in.read();i++;
      while((b!=0)&&(i<len)){
        s+=(char)b;
        b=in.read();i++;
      }
      v.add(s);                         // System.out.println("S : "+s);
    }
    in.reset();
    String[] sa=new String[v.size()];
    for(int j=0;j<sa.length;j++){
      sa[j]=(String)v.get(j);
    }
    return sa;
  }

  protected byte[] readByteArray(ImageInputStream in)throws IOException{
    if(type!=UNDEFINED){/*error*/return null;}

    byte[] data=new byte[(int)len];
    if(len<=4){
// todo
      System.out.println("9\b"+getClass().getName()+".readByteArray:\n\tCannot read byte array len<=4 yet.");
    }else{
      in.mark();
      in.seek(val);
      in.read(data);
      in.reset();
    }
    return data;
  }

  private double read1Real(ImageInputStream in)throws IOException{
    switch(type){
    case RATIONAL:  return new Rational((int)in.readUnsignedInt(),(int)in.readUnsignedInt()).doubleValue();
    case SRATIONAL: return new Rational(in.readInt(),in.readInt()).doubleValue();
    case FLOAT:     return in.readFloat();
    case DOUBLE:    return in.readDouble();
    default:/*error*/return 0;
    }
  }

  protected double readReal(ImageInputStream in)throws IOException{
    if(type==FLOAT){
      return read1Real(in);
    }else{
      in.mark();
      in.seek(val);
      double real=read1Real(in);
      in.reset();                              // System.out.println("real = "+real);
      return real;
    }
  }

  protected void write1Real(ImageOutputStream out,double value)throws IOException{
    switch(type){
    case RATIONAL: 
      Rational ur=new Rational(value);
      out.writeInt(ur.getNumerator());
      out.writeInt(ur.getDenominator());
      break;
    case SRATIONAL:
      Rational sr=new Rational(value);
      out.writeInt(sr.getNumerator());
      out.writeInt(sr.getDenominator());
      break;
    case FLOAT:  
      val=Float.floatToIntBits((float)value);
      break;
    case DOUBLE: 
      out.writeDouble(value); 
      break;
    default:/*error*/return;
    }
  }

  protected void writeReal(ImageOutputStream out,double value)throws IOException{
    switch(type){
    case RATIONAL: 
      val=out.getStreamPosition();             // address to rational
      Rational ur=new Rational(value);
      out.writeInt(ur.getNumerator());
      out.writeInt(ur.getDenominator());
      break;
    case SRATIONAL:
      val=out.getStreamPosition();             // address to rational
      Rational sr=new Rational(value);
      out.writeInt(sr.getNumerator());
      out.writeInt(sr.getDenominator());
      break;
    case FLOAT:  
      val=Float.floatToIntBits((float)value);  // size of float is 4 byte, hence we can save this in idf entry
      break;
    case DOUBLE: 
      val=out.getStreamPosition();             // address to double
      out.writeDouble(value); 
      break;
    default:/*error*/return;
    }
  }

  protected long[] readIntArray(ImageInputStream in)throws IOException{
    long[] array=new long[(int)len];

    int sot=sizeOfType();
    if(len*sot<=4){
      int shift=0;
      switch(sot){
      case 1:
        for(int i=0;i<len;i++){
          array[i]=(val>>shift)&0x000000FF;shift+=8;
          // System.out.println("["+i+"]= 0x"+Long.toHexString(array[i]));
        }
        break;
      case 2:
        for(int i=0;i<len;i++){
          array[i]=(val>>shift)&0x0000FFFF;shift+=16;       
          // System.out.println("["+i+"]= 0x"+Long.toHexString(array[i]));
        }
        break;
      case 4:
        array[0]=val;
        // System.out.println("[0]= 0x"+Long.toHexString(array[0]));
        break;
      }
    }else{
      in.mark();
      in.seek(val);

      for(int i=0;i<len;i++){
        array[i]=readInt(in);        // System.out.println("["+i+"]= 0x"+Long.toHexString(array[i]));
      }
      in.reset();  
    }
    return array;
  }

  protected void writeIntArray(ImageOutputStream out,long[] array)throws IOException{
    int sot=sizeOfType();
    if(len*sot<=4){
      int shift=0;
      switch(sot){
      case 1:
        val=0;
        for(int i=0;i<len;i++){
          val|=(array[i]&0x000000FF)<<shift;shift+=8;
        }
        break;
      case 2:
        val=0;
        for(int i=0;i<len;i++){
          val|=(array[i]&0x0000FFFF)<<shift;shift+=16;
        }
        break;
      case 4:     val=array[0];     break;
      }
    }else{
      val=out.getStreamPosition();
      for(int i=0;i<len;i++){
        writeInt(out,array[i]);
      }
    }
  }

  protected double[] readRealArray(ImageInputStream in)throws IOException{
    double[] array=new double[(int)len];

    int sot=sizeOfType();
    if(len*sot<=4){
      array[0]=Float.intBitsToFloat((int)val);
    }else{
      in.mark();
      in.seek(val);

      for(int i=0;i<len;i++){
        array[i]=read1Real(in);        // System.out.println("["+i+"]= "+array[i]);
      }
      in.reset();  
    }
    return array;
  }

  protected void writeRealArray(ImageOutputStream out,double[] array)throws IOException{
    int sot=sizeOfType();
    if(len*sot<=4){
      write1Real(out,array[0]);
    }else{
      val=out.getStreamPosition();
      for(int i=0;i<len;i++){
        write1Real(out,array[i]);
      }
    }
  }

  protected void writeByteArray(ImageOutputStream out,byte[] array)throws IOException{
    int sot=sizeOfType();
    if(sot!=1){throw new IOException(getClass().getName()+".writeByteArray:\n\tInternal error: size of type != 1.");}
    
    if(len<=4){
      val=0;int shift=0;
      for(int i=0;i<len;i++){
        val|=(array[i]&0x000000FF)<<shift;shift+=8;
      }
    }else{
      val=out.getStreamPosition();
      out.write(array,0,(int)len);
      if((len&0x01)==0x01){out.write(0);}                    // word align
    }
  }

  void write(ImageOutputStream out) throws IOException {
    // don't need to write anything
  }
}

// Adobe TIFF6.PDF
