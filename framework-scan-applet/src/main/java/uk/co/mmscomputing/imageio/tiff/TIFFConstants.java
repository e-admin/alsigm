package uk.co.mmscomputing.imageio.tiff;

interface TIFFConstants{

  // Directory Entry Types

  static final int BYTE      = 1;      // An 8-bit unsigned integer
  static final int ASCII     = 2;      // 8-bit ASCII codes terminated with a null (hex 0) character
  static final int SHORT     = 3;      // A 16-bit (2-byte) unsigned integer
  static final int LONG      = 4;      // A 32-bit (4-byte) unsigned integer
  static final int RATIONAL  = 5;      // Two LONG values. The first represents the numerator of a fraction, the second the denominator

  static final int SBYTE     = 6;      // An 8-bit signed integer
  static final int UNDEFINED = 7;      // byte
  static final int SSHORT    = 8;      // A 16-bit (2-byte) signed integer
  static final int SLONG     = 9;      // A 32-bit (4-byte) signed integer
  static final int SRATIONAL = 10;     // Two SLONG values. The first represents the numerator of a fraction, the second the denominator

  static final int FLOAT     = 11;     // float
  static final int DOUBLE    = 12;     // double

  // Directory Entry Tags

  static final int NewSubfileType       = 254;     // LONG   1
  static final int SubfileType          = 255;     // SHORT  1
  static final int ImageWidth           = 256;     // SHORT/LONG  1
  static final int ImageLength          = 257;     // SHORT/LONG  1
  static final int BitsPerSample        = 258;     // SHORT  Samples per pixel
  static final int Compression          = 259;     // SHORT  1
  static final int PhotometricInterpretation = 262;// SHORT  1
  static final int Thresholding         = 263;     // SHORT  1
  static final int CellWidth            = 264;     // SHORT  1
  static final int CellLength           = 265;     // SHORT  1
  static final int FillOrder            = 266;     // SHORT  1
  static final int DocumentName         = 269;     // ASCII   ?
  static final int ImageDescription     = 270;     // ASCII   ?
  static final int Make                 = 271;     // ASCII   ?
  static final int Model                = 272;     // ASCII   ?
  static final int StripOffsets         = 273;     // SHORT/LONG
                                                   // Tag length = StripsPerlmage for PlanarConfig = 1
                                                   // SamplesPerPixel * StripsPerlmage for PlanarConfig = 2
  static final int Orientation          = 274;     // SHORT  1
  static final int SamplesPerPixel      = 277;     // SHORT  1
  static final int RowsPerStrip         = 278;     // SHORT/LONG  1
  static final int StripByteCounts      = 279;     // SHORT/LONG 
                                                   // Tag length = StripsPerlmage for PlanarConfig = 1
                                                   // SamplesPerPixel * StripsPerImage for PlanarConfig = 2
  static final int MinSampleValue       = 280;     // SHORT  SamplesPerPixel
  static final int MaxSampleValue       = 281;     // SHORT  SamplesPerPixel
  static final int XResolution          = 282;     // RATIONAL  1
  static final int YResolution          = 283;     // RATIONAL  1
  static final int PlanarConfiguration  = 284;     // SHORT  1
  static final int PageName             = 285;     // ASCII   ?
  static final int XPosition            = 286;     // RATIONAL  1
  static final int YPosition            = 287;     // RATIONAL  1
  static final int FreeOffsets          = 288;     // LONG  ?
  static final int FreeByteCounts       = 289;     // LONG  ?
  static final int GrayResponseUnit     = 290;     // SHORT  1
  static final int GrayResponseCurve    = 291;     // SHORT  2**BitsPerSample
  static final int T4Options            = 292;     // LONG   1
  static final int T6Options            = 293;     // LONG   1
  static final int ResolutionUnit       = 296;     // SHORT  1
  static final int PageNumber           = 297;     // SHORT  2
  static final int ColorResponseCurve   = 301;     // SHORT  3*(2**BitsPerSample)
  static final int Software             = 305;     // ASCII   ?
  static final int DateTime             = 306;     // ASCII   20 "YYYY:MM:DD HH:MM:SSO"
  static final int Artist               = 315;     // ASCII   ?
  static final int HostComputer         = 316;     // ASCII   ?
  static final int Predictor            = 317;     // SHORT  1
  static final int WhitePoint           = 318;     // RATIONAL  2
  static final int PrimaryChromaticities= 319;     // RATIONAL  6
  static final int ColorMap             = 320;     // SHORT  3*(2**BitsPerSample)
  static final int HalftoneHints        = 321;     // SHORT  2
  static final int TileWidth            = 322;     // SHORT/LONG 1
  static final int TileLength           = 323;     // SHORT/LONG 1
  static final int TileOffsets          = 324;     // LONG  tiles per image
  static final int TileByteCounts       = 325;     // BYTE/SHORT  tiles per image
  static final int InkSet               = 332;     // SHORT  1
  static final int InkNames             = 333;     // ASCII   ?
  static final int NumberOfInks         = 334;     // SHORT  1
  static final int DotRange             = 336;     // SHORT/LONG  2*NumberOfInks
  static final int TargetPrinter        = 337;     // ASCII   ?
  static final int ExtraSamples         = 338;     // SHORT   Number of additional colour components
  static final int SampleFormat         = 339;     // SHORT   SamplesPerPixel
  static final int SMinSampleValue      = 340;     // ?       SamplesPerPixel
  static final int SMaxSampleValue      = 341;     // ?       SamplesPerPixel
  static final int TransferRange        = 342;     // SHORT   6
  static final int JPEGTables           = 347;     // ?       n=number of bytes in tables datastream

  static final int JPEGProc             = 512;     // SHORT   1
  static final int JPEGInterchangeFormat= 513;     // LONG    1  // offset
  static final int JPEGInterchangeFormatLength= 514;//LONG    1  // byte count
  static final int JPEGRestartInterval  = 515;     // SHORT   1
  static final int JPEGLosslessPredictors=517;     // SHORT   SamplesPerPixel
  static final int JPEGPointTransforms  = 518;     // SHORT   SamplesPerPixel
  static final int JPEGQTables          = 519;     // LONG    SamplesPerPixel
  static final int JPEGDCTables         = 520;     // LONG    SamplesPerPixel
  static final int JPEGACTables         = 521;     // LONG    SamplesPerPixel

  static final int YCbCrCoefficients    = 529;     // RATIONAL  3
  static final int YCbCrSubSampling     = 530;     // SHORT   2
  static final int YCbCrPositioning     = 531;     // SHORT   1
  static final int ReferenceBlackWhite  = 532;     // LONG    2*BitsPerSample

  static final int Copyright            = 33432;   // ASCII   ?

  static final int ExifIFD              = 34665;   // LONG    1 Offset to Exif IFD

  static final int ImageSourceData      = 37724;   // UNDEFINED ?


  // Compression

  static final int NOCOMPRESSION        = 1;       // No compression but bytes are tightly packed
  static final int CCITTGROUP3MODHUFFMAN= 2;       // CCITT Group 3 1-Dimensional modified Huffman RLE
  static final int CCITTFAXT4           = 3;       // Facsimile compatible CCITT Group 3; T4
  static final int CCITTFAXT6           = 4;       // Facsimile compatible CCITT Group 4; T6
  static final int LZW                  = 5;       // LZW (Lempel-Ziv & Welch) 
  static final int JPEGDeprecated       = 6;       // JPEG obsolete old style 
  static final int JPEG                 = 7;       // JPEG [TIFF TechNote2 on JPEG-in-TIFF]
  static final int PACKBITS             = 32773;   // PackBits (Macintosh) 0x8005

  // Photometric Interpretation

  static final int WhiteIsZero          = 0;
  static final int BlackIsZero          = 1;
  static final int RGB                  = 2;
  static final int PaletteColor         = 3;
  static final int TransparencyMask     = 4;
  static final int CMYK                 = 5;
  static final int YCbCr                = 6;
  static final int CIELab               = 8;
  static final int ICCLab               = 9;

  // Resolution Unit

  static final int NoUnit               = 1;
  static final int Inch                 = 2;
  static final int CM                   = 3;

  // Fill Order

  static final int LowColHighBit        = 1;
  static final int LowColLowBit         = 2;

  // mmsc mode constants

  static final int compNone             = 0;
  static final int compBaselineMH       = 1;
  static final int compT4MH             = 2;
  static final int compT4MR             = 3;
  static final int compT6MMR            = 4;
  static final int compPackBits         = 5;
  static final int compLZW              = 6;
  static final int compJPEG             = 7;

}

// http://partners.adobe.com/public/developer/en/tiff/TIFFphotoshop.pdf
