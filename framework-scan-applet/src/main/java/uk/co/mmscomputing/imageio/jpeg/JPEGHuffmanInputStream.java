package uk.co.mmscomputing.imageio.jpeg;

import java.io.*;

public class JPEGHuffmanInputStream extends InputStream{

  protected int[] BITS=new int[16];             // 16-byte list containing number of Huffman codes of each length
  protected int[] HUFFVAL;
  protected int[] HUFFSIZE;
  protected int[] HUFFCODE;

  protected int[] VALPTR  = new int[16];
  protected int[] MINCODE = new int[16];
  protected int[] MAXCODE = new int[16];

  protected JPEGBitInputStream in;

  public JPEGHuffmanInputStream(JPEGBitInputStream in,InputStream tables)throws IOException{
    this.in=in;
    initialize(tables);
  }

  public JPEGHuffmanInputStream(InputStream tables)throws IOException{
    this.in=null;
    initialize(tables);
  }

  public void setInputStream(JPEGBitInputStream in)throws IOException{
    this.in=in;
  }

  private void initialize(InputStream tables)throws IOException{
    int LASTK=readTableData(tables);
    generateSizeTable(LASTK);
    generateCodeTable(LASTK);
    generateDecoderTable();
  }

  private int readTableData(InputStream tables)throws IOException{ // [1] p.40, p.50
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

  private void generateSizeTable(int LASTK){     // [1] p.51
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

  private void generateDecoderTable(){          // [1] p.108
    int j=0;
    for(int i=0;i<16;i++){
      if(BITS[i]==0){
        MAXCODE[i]=-1;
      }else{
        VALPTR[i]=j;
        MINCODE[i]=HUFFCODE[j];
        j+=BITS[i]-1;
        MAXCODE[i]=HUFFCODE[j];
        j++;
      }
    }
  }

  public String toString(){
    String s="\n"+getClass().getName()+"\n";

    s+="byte[] BITS={";
    for(int i=0;i<BITS.length;i++){             // 16-byte list containing number of Huffman codes of each length
      s+=Integer.toString((BITS[i]<128)?BITS[i]:BITS[i]-256)+",";
//      s+=Integer.toHexString(BITS[i])+",";
    }
    s+="};\n";
    s+="byte[] HUFFVAL={";
    for(int i=0;i<HUFFVAL.length;i++){
      s+=Integer.toString((HUFFVAL[i]<128)?HUFFVAL[i]:HUFFVAL[i]-256)+",";
//      s+=Integer.toHexString(HUFFVAL[i])+",";
    }
    s+="};\n";
    return s;
  }

  public int readBits(int bitSize)throws IOException{
    int V = in.readBits(bitSize);
    int Vt=1<<(bitSize-1);                      // [1] p.105 extend
    if(V<Vt){                                   // if V should be negative (bit T is zero, hence V is smaller then Vt)
      Vt=(-1<<bitSize)+1;                       // sign extend: put 1 bits in front of the T bits of V
      V+=Vt;
    }
    return V;
  }

  private int code,index;

  public void restart()throws IOException{      // Encountered RST marker. This can happen in middle of read()
    code=0;index=0;                             // Hence need to declare code and index as class variables.
  }

  public int read()throws IOException{          // [1] p.109 decode
    int b;
    index=0;
    code=in.readBit();
    if(code==-1){return -1;}
    while(code>MAXCODE[index++]){
      b=in.readBit();                          
      if(b==-1){return -1;}
      code=(code<<1)|b;
    }
    int j=VALPTR[--index];
    j+=code-MINCODE[index];
    return HUFFVAL[j];
  }
}

//  [1] 'JPEG' : ISO/IEC IS 10918-1
//               ITU-T Recommendation T.81

// http://www.w3.org/Graphics/JPEG/itu-t81.pdf