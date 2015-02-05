package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGOutputStream extends OutputStream implements JPEGConstants{

  private int spp=0;
  private int qtprecision=0;                   // 8 bit

  private JPEGBitOutputStream         out;

  private JPEGDCTOutputStream[]       dcts;
  private JPEGComponentsOutputStream  cout;

  public JPEGOutputStream(OutputStream out)throws IOException{
    this.out=new JPEGBitOutputStream(out);
    startOfImage();
  }

  protected int[][] qts = new int[4][];        // available quantization tables

  protected JPEGHuffmanOutputStream[]   dcouts   = new JPEGHuffmanOutputStream[4];
  protected JPEGHuffmanOutputStream[]   acouts   = new JPEGHuffmanOutputStream[4];

  
  public void setZZQuantizationTable(int index,int[] qt){
    qts[index]=qt;
  }

  public void setZZQuantizationTable(int index,int[] qt,int quality){
    if((0<quality)&&(quality<=100)){
      int[] t=new int[64];
      System.arraycopy(qt,0,t,0,64);
      for(int i=0;i<64;i++){
        t[i]=(t[i]*25)/quality;
//      System.out.println("qt["+i+"]="+t[i]);
      }
      setZZQuantizationTable(index,t);
    }else{
      throw new IllegalArgumentException(getClass().getName()+".setZZQuantizationTable:\n\tParameter quality out of range ["+quality+"]");
    }
  }

  public void setRawDCHuffmanTable(int index,byte[] table)throws IOException{
    dcouts[index]=new JPEGHuffmanOutputStream(out,new ByteArrayInputStream(table));
  }

  public void setRawACHuffmanTable(int index,byte[] table)throws IOException{
    acouts[index]=new JPEGHuffmanOutputStream(out,new ByteArrayInputStream(table));
  }

  public void startOfFrame(int height,int width,int[] HV,int[] Q)throws IOException{ // 0xC0
    out.write(0xFF);out.write(0xC0);

    spp=HV.length;

    int len=8+spp*3;
    out.write((len>>8)&0x00FF);
    out.write(len&0x00FF);

    out.write(8);                                             // 8 bits per sample
    out.write((height>>8)&0x00FF);
    out.write(height&0x00FF);
    out.write((width>>8)&0x00FF);
    out.write(width&0x00FF);
    out.write(spp);                                           // samples per pixel
    
    dcts=new JPEGDCTOutputStream[spp];

    for(int i=0;i<spp;i++){
      out.write(i+1);
      out.write(HV[i]);                                       // (Hi<<4)|Vi
      out.write(Q[i]);

      dcts[i]=new JPEGDCTOutputStream(qts[Q[i]]);
    }
    cout=new JPEGComponentsOutputStream(dcts,HV,width);
  }

  public void defineHuffmanTables()throws IOException{                     // 0xC4
    out.write(0xFF);out.write(0xC4);

    int len=2;
    for(int n=0;n<dcouts.length;n++){
      if(dcouts[n]!=null){
        len+=dcouts[n].getTableDataLength()+1;
      }
      if(acouts[n]!=null){
        len+=acouts[n].getTableDataLength()+1;
      }
    }
    out.write((len>>8)&0x00FF);
    out.write(len&0x00FF);

    for(int n=0;n<dcouts.length;n++){
      if(dcouts[n]!=null){
        out.write(n);
        dcouts[n].writeTableData(out);
      }
    }
    for(int n=0;n<acouts.length;n++){
      if(acouts[n]!=null){
        out.write((1<<4)|n);
        acouts[n].writeTableData(out);
      }
    }
  }

  public void startOfImage()throws IOException{                            // 0xD8
    out.write(0xFF);out.write(0xD8);
  }

  public void endOfImage()throws IOException{                              // 0xD9
    out.write(0xFF);out.write(0xD9);
  }

  public void defineQuantizationTables()throws IOException{                // 0xDB
    out.write(0xFF);out.write(0xDB);

    int len=2;
    for(int n=0;n<qts.length;n++){
      if(qts[n]!=null){len+=65;}
    }
    out.write((len>>8)&0x00FF);
    out.write(len&0x00FF);

    int[] qt;
    for(int n=0;n<qts.length;n++){                                         // max 4 tables
      qt=qts[n];
      if(qt!=null){
        out.write((qtprecision<<4)|n);                                     // 8 bit
        for(int i=0;i<64;i++){out.write(qt[i]);}                           // are in zigzag scan order [1]p.40
      }
    }
  }

  public void startOfScan(int[] sel)throws IOException{                    // 0xDA
    out.write(0xFF);out.write(0xDA);

    spp=sel.length;
    int len=6+spp*2;

    out.write((len>>8)&0x00FF);
    out.write(len&0x00FF);

    out.write(spp);
    for(int i=0;i<spp;i++){
      out.write(i+1);                                                      // component id
      out.write(sel[i]);                                                   // dc<<4|ac

      int dci=(sel[i]>>4)&0x0F;
      int aci= sel[i]    &0x0F;
      dcts[i].setHuffmanOutputStreams(dcouts[dci],acouts[aci]);
    }
    out.write(0);                                                          // ss 
    out.write(63);                                                         // se
    out.write(0);                                                          // ah,al
  }

  public void write(int b)throws IOException{
    cout.write(b);
  }

  public void flush()throws IOException{
    if(cout!=null){cout.flush();}
    out.flush();
  }

  public void close()throws IOException{
    flush();
    endOfImage();
    out.close();
  }

}
