package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGHuffmanOutputStream extends OutputStream{

  protected int[] BITS=new int[16];             // 16-byte list containing number of Huffman codes of each length
  protected int[] HUFFVAL;
  protected int[] HUFFSIZE;
  protected int[] HUFFCODE;

  protected int[] EHUFSI;
  protected int[] EHUFCO;

  protected int[] VALPTR  = new int[16];
  protected int[] MINCODE = new int[16];
  protected int[] MAXCODE = new int[16];

  protected JPEGBitOutputStream out;

  public JPEGHuffmanOutputStream(JPEGBitOutputStream out,InputStream tables)throws IOException{
    this.out=out;
    initialize(tables);
  }

  private void initialize(InputStream tables)throws IOException{
    int LASTK=readTableData(tables);
    generateSizeTable(LASTK);
    generateCodeTable(LASTK);
    orderCodes(LASTK);
  }

  private int readTableData(InputStream tables)throws IOException{ // [1] p.40
    int m=0;
    for(int i=0;i<16;i++){                      // 16-byte list containing number of Huffman codes of each length
      BITS[i]=tables.read();                    // System.out.println(Integer.toString((BITS[i]<128)?BITS[i]:BITS[i]-256))+",");
      m+=BITS[i];
    }
    HUFFVAL=new int[m];
    for(int i=0;i<m;i++){
      HUFFVAL[i]=tables.read();                 // System.out.println(Integer.toString((HUFFVAL[i]<128)?HUFFVAL[i]:HUFFVAL[i]-256)+",");
    }
    return m;
  }

  private void generateSizeTable(int LASTK){    // [1] p.51
    HUFFSIZE=new int[LASTK+1];
    int k=0;
    for(int i=0;i<16;i++){
      for(int j=0;j<BITS[i];j++){
        HUFFSIZE[k++]=i+1;
      }
    }
    HUFFSIZE[k]=0;
  }

  private void generateCodeTable(int LASTK){    // [1] p.52
    HUFFCODE=new int[LASTK];
    int k=0,code=0;
    int si=HUFFSIZE[0];
    while(true){
      do{
        HUFFCODE[k]=code;
        code++;k++;
      }while(HUFFSIZE[k]==si);
      if(HUFFSIZE[k]==0){break;}
      do{
        code<<=1;si++;
      }while(HUFFSIZE[k]!=si);
    }
  }

  private void orderCodes(int LASTK){           // [1] p.53
    EHUFCO=new int[256];
    EHUFSI=new int[256];

    for(int k=0;k<LASTK;k++){
      int i=HUFFVAL[k];
      EHUFCO[i]=HUFFCODE[k];
      EHUFSI[i]=HUFFSIZE[k];
    } 
  }

  public int getTableDataLength(){
    return BITS.length+HUFFVAL.length;
  }

  public void writeTableData(OutputStream tables)throws IOException{
    for(int i=0;i<BITS.length;i++){
      tables.write(BITS[i]);
    }
    for(int i=0;i<HUFFVAL.length;i++){
      tables.write(HUFFVAL[i]);
    }
  }

  public void write(int b)throws IOException{
    out.writeBits(EHUFCO[b],EHUFSI[b]);
  }

  public void writeBits(int code,int size)throws IOException{
    out.writeBits(code,size);
  }

  public void flush()throws IOException{
    out.flush();
  }

}