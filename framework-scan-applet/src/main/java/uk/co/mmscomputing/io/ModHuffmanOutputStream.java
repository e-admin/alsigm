package uk.co.mmscomputing.io;

import java.io.*;

public class ModHuffmanOutputStream extends BitOutputStream implements ModHuffmanTable{

  protected int state=WHITE;

  public ModHuffmanOutputStream(){
    super();
    state=WHITE;
  }

  public ModHuffmanOutputStream(OutputStream out){
    super(out);
    state=WHITE;
  }

  public void flush()throws IOException{
    super.flush();
    state=WHITE;    
  }

  public void reset()throws IOException{
    super.reset();
    state=WHITE;    
  }

  public int getState(){
    return state;
  }

  public void writeEOL()throws IOException{
    super.flush();
    write(0,4);write(EOLCW,12);
    state=WHITE;    
  }

  public void write(int runlen)throws IOException{
    if(state==WHITE){ writeWhite(runlen);state=BLACK;
    }else{            writeBlack(runlen);state=WHITE;
    }
  }

  private void writeWhite(int runlen)throws IOException{
    int[] rle;

    while(runlen>2623){                // 2560+63=2623
      write(0x00000F80,12);    
      runlen-=2560;
    }
    if(runlen>63){                     // max 2560
      int makeup=runlen/64-1;
      rle=makeUpWhite[makeup];
      write(rle[0],rle[2]);    
    }
    rle=termWhite[runlen&0x3F];        // max 63
    write(rle[0],rle[2]);
  }

  private void writeBlack(int runlen)throws IOException{
    int[] rle;

    while(runlen>2623){                // 2560+63=2623
      write(0x00000F80,12);    
      runlen-=2560;
    }
    if(runlen>63){                     // max 2560
      int makeup=runlen/64-1;
      rle=makeUpBlack[makeup];
      write(rle[0],rle[2]);    
    }
    rle=termBlack[runlen&0x3F];        // max 63
    write(rle[0],rle[2]);
  }

  public static void main(String[] argv){
    try{
      ByteArrayOutputStream  baos=new ByteArrayOutputStream();
      ModHuffmanOutputStream mhos=new ModHuffmanOutputStream(baos);
      mhos.writeWhite(1728);             // 1728 white standard G3 fax line => B2 59 01
//      mhos.writeWhite(2048);             // 2048 white B4 fax line => 80 CC 0A
//      mhos.writeWhite(2432);             // 2432 white A3 fax line => 80 CD 0A
      mhos.flush();mhos.close();
      byte[] buf=baos.toByteArray();
      for(int i=0;i<buf.length;i++){
        System.out.println("["+i+"]="+Integer.toHexString(buf[i]&0x000000FF));
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}