package uk.co.mmscomputing.io;

import java.io.*;
import java.net.URL;

public class LEOutputStream extends BufferedOutputStream{

  public LEOutputStream(OutputStream out) throws IOException{
    super(out);
  }

  public LEOutputStream(File f) throws IOException{
    super(new FileOutputStream(f));
  }

  public LEOutputStream(URL url) throws IOException{
    super((url.openConnection()).getOutputStream());
  }

  public void writeInt(int i) throws IOException{
    write(i&0x000000FF);		// first : LSB least significant byte
    write((i>>8)&0x000000FF);
    write((i>>16)&0x000000FF);
    write((i>>24)&0x000000FF);		// MSB most significant byte
  }

  public void writeShort(int i) throws IOException{
    write(i&0x000000FF);		// first : LSB least significant byte
    write((i>>8)&0x000000FF);		// MSB most significant byte
  }

  public void writeUnsignedShort(int i) throws IOException{
    write(i&0x000000FF);		// first : LSB least significant byte
    write((i>>8)&0x000000FF);		// MSB most significant byte
  }

  public void writeLong(long i) throws IOException{
    write((int)i&0x000000FF);		// first : LSB least significant byte
    write((int)(i>>8)&0x000000FF);	// MSB most significant byte
    write((int)(i>>16)&0x000000FF);
    write((int)(i>>24)&0x000000FF);	// MSB most significant byte

    write((int)(i>>32)&0x000000FF);
    write((int)(i>>40)&0x000000FF);
    write((int)(i>>48)&0x000000FF);
    write((int)(i>>56)&0x000000FF);
  }

}

