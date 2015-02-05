package uk.co.mmscomputing.device.sane;

public class Parameters implements SaneConstants{
  public int format;
  public int lastFrame;
  public int lines;
  public int depth;
  public int pixelsPerLine;
  public int bytesPerLine;

  public Parameters(){
    this.format=0;
    this.lastFrame=SANE_FALSE;
    this.lines=0;
    this.depth=0;
    this.pixelsPerLine=0;
    this.bytesPerLine=0;
  }

  public Parameters(
    int format,
    int lastFrame,
    int lines,
    int depth,
    int pixelsPerLine,
    int bytesPerLine
  ){
    this.format=format;
    this.lastFrame=lastFrame;
    this.lines=lines;
    this.depth=depth;
    this.pixelsPerLine=pixelsPerLine;
    this.bytesPerLine=bytesPerLine;
  }
 

  public String toString(){
    String s="format: "+format;
    s+="\nlastFrame: "+lastFrame;
    s+="\nparameter.lines: "+lines;
    s+="\nparameter.depth: "+depth;
    s+="\nparameter.pixelsPerLine: "+pixelsPerLine;
    s+="\nparameter.bytesPerLine: "+bytesPerLine;
    return s;
  }

}

