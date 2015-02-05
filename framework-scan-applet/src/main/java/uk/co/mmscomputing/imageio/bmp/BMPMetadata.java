package uk.co.mmscomputing.imageio.bmp;

import java.awt.image.*;
import org.w3c.dom.Node;
import javax.imageio.metadata.*;

// This is imitating Sun's BMPMetadata class which is available from jvm 1.5
// according to the "BMP Metadata Format Specification"

public class BMPMetadata extends IIOMetadata implements BMPConstants{

  private static final String formatName="javax_imageio_bmp_1.0";

  private int width,height,bitsPerPixel,compression,imageSize;
  private int xPixelsPerMeter,yPixelsPerMeter,colorsUsed,colorsImportant;
  private int redMask,greenMask,blueMask;
  private IndexColorModel icm;

  public BMPMetadata(){
    super(true,formatName,"uk.co.mmscomputing.imageio.bmp.BMPMetadata",null,null);
    xPixelsPerMeter = 2953;            // 75 dpi
    yPixelsPerMeter = 2953;            // 75 dpi
  }

  public void setWidth(int w){width=w;}
  public void setHeight(int h){height=h;}
  public void setBitsPerPixel(int v){bitsPerPixel=v;}
  public void setCompression(int v){compression=v;}
  public void setImageSize(int v){imageSize=v;}

  public void setXPixelsPerMeter(int v){xPixelsPerMeter=v;}
  public void setYPixelsPerMeter(int v){yPixelsPerMeter=v;}

  // There are (1000/25.4) in/m = 39.370079 in/m, so, dpi * 39.370079 = dots/meter (PelsPerMeter).

  public void setXDotsPerInch(int v){xPixelsPerMeter=(int)Math.round(((double)v)*1000.0/25.4);}
  public void setYDotsPerInch(int v){yPixelsPerMeter=(int)Math.round(((double)v)*1000.0/25.4);}

  public void setColorsUsed(int v){colorsUsed=v;}
  public void setColorsImportant(int v){colorsImportant=v;}
  public void setRedMask(int v){redMask=v;}
  public void setGreenMask(int v){greenMask=v;}
  public void setBlueMask(int v){blueMask=v;}

  public void setIndexColorModel(IndexColorModel icm){
    if(icm!=null){colorsUsed=icm.getMapSize();}  
    this.icm=icm;
  }

  public int getXPixelsPerMeter(){return xPixelsPerMeter;}
  public int getYPixelsPerMeter(){return yPixelsPerMeter;}


  public int getXDotsPerInch(){return (int)Math.round(((double)xPixelsPerMeter)*25.4/1000.0);}
  public int getYDotsPerInch(){return (int)Math.round(((double)yPixelsPerMeter)*25.4/1000.0);}

  public IndexColorModel getIndexColorModel(){return icm;}

  public boolean isReadOnly(){ return false;}

  public Node getAsTree(String fn){
    if(fn.equals(formatName)){
      return getNativeTree();
    }else if(fn.equals(IIOMetadataFormatImpl.standardMetadataFormatName)){
      return getStandardTree();
    }
    throw new IllegalArgumentException(getClass().getName()+".mergeTree:\n\tUnknown format: "+fn);
  }

  public void mergeTree(String formatName,Node root){
    throw new IllegalStateException(getClass().getName()+".mergeTree:\n\tFunction not supported.");
  }

  public void reset(){
    throw new IllegalStateException(getClass().getName()+".reset:\n\tFunction not supported.");
  }

  private Node getNativeTree(){
    IIOMetadataNode root=new IIOMetadataNode(formatName);
    addChildNode(root,"BMPVersion","BMP v. 3.x");
    addChildNode(root,"Width",new Integer(width));
    addChildNode(root,"Height",new Integer(height));
    addChildNode(root,"BitsPerPixel",new Integer(bitsPerPixel));
    addChildNode(root,"Compression",new Integer(compression));
    addChildNode(root,"ImageSize",new Integer(imageSize));

    IIOMetadataNode node=addChildNode(root,"PixelsPerMeter",null);
    addChildNode(node,"X",new Integer(xPixelsPerMeter));
    addChildNode(node,"Y",new Integer(yPixelsPerMeter));

    addChildNode(root,"ColorsUsed",new Integer(colorsUsed));
    addChildNode(root,"ColorsImportant",new Integer(colorsImportant));

    if(icm!=null){
      node=addChildNode(root,"Palette",null);
      for(int i=0;i<colorsUsed;i++){
        IIOMetadataNode entry=addChildNode(node,"PaletteEntry",null);
        addChildNode(entry,"Red",new Byte((byte)icm.getRed(i)));
        addChildNode(entry,"Green",new Byte((byte)icm.getGreen(i)));
        addChildNode(entry,"Blue",new Byte((byte)icm.getBlue(i)));        
      }      
    }
    return root;
  }

  protected IIOMetadataNode getStandardChromaNode(){
    IIOMetadataNode node=new IIOMetadataNode("Chroma");
    if(icm!=null){
      IIOMetadataNode subnode=new IIOMetadataNode("Palette");
      node.appendChild(subnode);
      for(int i=0;i<colorsUsed;i++){
        IIOMetadataNode entry=new IIOMetadataNode("PaletteEntry");
        subnode.appendChild(entry);
        entry.setAttribute("index",""+i);
        entry.setAttribute("red",""+icm.getRed(i));
        entry.setAttribute("green",""+icm.getGreen(i));
        entry.setAttribute("blue",""+icm.getBlue(i));
        entry.setAttribute("alpha","255");
      }
    }
    return node;
  }

  protected IIOMetadataNode getStandardCompressionNode(){
    IIOMetadataNode node=new IIOMetadataNode("Compression");
    IIOMetadataNode subnode=new IIOMetadataNode("CompressionTypeName");
    subnode.setAttribute("value",compressionTypeNames[compression]);
    node.appendChild(subnode);
    return node;
  }

  private String countBits(int mask){    // masks: i.e. 0x00FF0000, 0x0000FF00, 0x000003E0, 0x0000001F
    int c=0;                             // counter consecutive bits
    while(mask>0){
      if((mask&0x01)==0x01){c++;}
      mask>>>=1;
    }
    return (c==0)?"":""+c;
  }

  protected IIOMetadataNode getStandardDataNode(){
    IIOMetadataNode node=new IIOMetadataNode("Data");
    String bits = "";
    if(bitsPerPixel == 24){
      bits = "8 8 8";
    }else if((bitsPerPixel == 16)||(bitsPerPixel == 32)){
      bits += countBits(redMask)+" "+countBits(greenMask)+" "+countBits(blueMask);
    }else if(bitsPerPixel <= 8){
      bits += bitsPerPixel;
    }
    IIOMetadataNode subnode=new IIOMetadataNode("BitsPerSample");
    subnode.setAttribute("value",bits);
    node.appendChild(subnode);
    
    return node;
  }

  protected IIOMetadataNode getStandardDimensionNode(){
    if((yPixelsPerMeter>0.0)&&(xPixelsPerMeter>0.0)){
      IIOMetadataNode node=new IIOMetadataNode("Dimension");

      double value=yPixelsPerMeter/xPixelsPerMeter;
      IIOMetadataNode subnode=new IIOMetadataNode("PixelAspectRatio");
      subnode.setAttribute("value",""+value);
      node.appendChild(subnode);
      
      value=1000.0/xPixelsPerMeter;                    // need pixels per millimeter
      subnode=new IIOMetadataNode("HorizontalPhysicalPixelSpacing");
      subnode.setAttribute("value",""+value);
      node.appendChild(subnode);

      value=1000.0/yPixelsPerMeter;                    // need pixels per millimeter
      subnode=new IIOMetadataNode("VerticalPhysicalPixelSpacing");
      subnode.setAttribute("value",""+value);
      node.appendChild(subnode);

      return node;
    }
    return null;
  }

  private String objectToString(Object obj){
    return obj.toString();
  }

  private IIOMetadataNode addChildNode(IIOMetadataNode root,String name,Object object){
    IIOMetadataNode child=new IIOMetadataNode(name);
    if(object!=null){
      child.setUserObject(object);
      child.setNodeValue(objectToString(object));
    }
    root.appendChild(child);
    return child;
  }

}

/*
     <javax_imageio_1.0>
         <Compression>
             <CompressionTypeName value="BI_RGB"></CompressionTypeName>
         </Compression>
         <Data>
             <BitsPerSample value="8 8 8 "></BitsPerSample>
         </Data>
         <Dimension>
             <PixelAspectRatio value="1.0"></PixelAspectRatio>
             <HorizontalPhysicalPixelSpacing value="0"></HorizontalPhysicalPixelSpacing>
             <VerticalPhysicalPixelSpacing value="0"></VerticalPhysicalPixelSpacing>
         </Dimension>
     </javax_imageio_1.0>
 
     <javax_imageio_bmp_1.0>
         <BMPVersion>BMP v. 3.x</BMPVersion>
         <Width>1700</Width>
         <Height>2338</Height>
         <BitsPerPixel>24</BitsPerPixel>
         <Compression>0</Compression>
         <ImageSize>0</ImageSize>
         <PixelsPerMeter>
             <X>2834</X>
             <Y>2834</Y>
         </PixelsPerMeter>
         <ColorsUsed>0</ColorsUsed>
         <ColorsImportant>0</ColorsImportant>
     </javax_imageio_bmp_1.0>
*/