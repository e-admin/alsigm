package uk.co.mmscomputing.imageio.bmp;

public interface BMPConstants{
  static final public int BI_RGB       =0x00;
  static final public int BI_RLE8      =0x01;
  static final public int BI_RLE4      =0x02;
  static final public int BI_BITFIELDS =0x03;
  static final public int BI_JPEG      =0x04;
  static final public int BI_PNG       =0x05;

  static final public String[] compressionTypeNames = {
    "BI_RGB", "BI_RLE8", "BI_RLE4", "BI_BITFIELDS", "BI_JPEG", "BI_PNG"
  };
}