package uk.co.mmscomputing.device.twain;

import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import uk.co.mmscomputing.device.scanner.Scanner;
import uk.co.mmscomputing.device.scanner.ScannerDevice;
import uk.co.mmscomputing.device.scanner.ScannerListener;
import uk.co.mmscomputing.device.scanner.ScannerIOException;
import uk.co.mmscomputing.device.scanner.ScannerIOMetadata;

public class TwainExample implements ScannerListener{

  static TwainExample app;  

  Scanner scanner;

  public TwainExample(String[] argv)throws ScannerIOException{
    scanner=Scanner.getDevice();
    scanner.addListener(this);    
    scanner.acquire();
  }

  public void update(ScannerIOMetadata.Type type, ScannerIOMetadata metadata){
    if(type.equals(ScannerIOMetadata.ACQUIRED)){
      BufferedImage image=metadata.getImage();
      System.out.println("Have an image now!");
      try{
        ImageIO.write(image, "png", new File("mmsc_image.png"));
      }catch(Exception e){
        e.printStackTrace();
      }
    }else if(type.equals(ScannerIOMetadata.NEGOTIATE)){
      ScannerDevice device=metadata.getDevice();
      try{
//        device.setShowUserInterface(true);
//        device.setShowProgressBar(true);
//        device.setResolution(100);
      }catch(Exception e){
        e.printStackTrace();
      }
    }else if(type.equals(ScannerIOMetadata.STATECHANGE)){
      System.err.println(metadata.getStateStr());
      if(metadata.isFinished()){
        System.exit(0);
      }
    }else if(type.equals(ScannerIOMetadata.EXCEPTION)){
      metadata.getException().printStackTrace();
    }
  }

  public static void main(String[] argv){
    try{
      app=new TwainExample(argv);
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}


