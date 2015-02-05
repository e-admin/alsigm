package uk.co.mmscomputing.imageio.jpeg;

public interface JPEGConstants{

  // [1] p.32

  // 'byte stuffing'  = 0x0000;

  static final int TEM    = 0x0001;          // Temporary private use in arithmetic coding

  // Reserved 0x0002 - 0x00BF

  static final int SOF0   = 0x00C0;          // Start of Frame BaseLine sequential DCT, non differential, Huffman coding
  static final int SOF1   = 0x00C1;          // Start of Frame Extended sequential DCT, non differential, Huffman coding
  static final int SOF2   = 0x00C2;          // Start of Frame Progressive DCT, non differential, Huffman coding
  static final int SOF3   = 0x00C3;          // Start of Frame Lossless sequential, non differential, Huffman coding
  static final int DHT    = 0x00C4;          // Define Huffman Tables
  static final int SOF5   = 0x00C5;          // Start of Frame Sequential DCT, differential, Huffman coding
  static final int SOF6   = 0x00C6;          // Start of Frame Progressive DCT, differential, Huffman coding
  static final int SOF7   = 0x00C7;          // Start of Frame Lossless sequential, differential, Huffman coding

  static final int JPG    = 0x00C8;          // JPEG Extensions
  static final int SOF9   = 0x00C9;          // Start of Frame Extended sequential DCT, non differential, arithmetic coding
  static final int SOF10  = 0x00CA;          // Start of Frame Progressive DCT, non differential, arithmetic coding
  static final int SOF11  = 0x00CB;          // Start of Frame Lossless sequential, non differential, arithmetic coding
  static final int DAC    = 0x00CC;          // Define Arithmetic Conditioning
  static final int SOF13  = 0x00CD;          // Start of Frame Extended sequential DCT, differential, arithmetic coding
  static final int SOF14  = 0x00CE;          // Start of Frame Progressive DCT, differential, arithmetic coding
  static final int SOF15  = 0x00CF;          // Start of Frame Lossless sequential, differential, arithmetic coding


  static final int RST0   = 0x00D0;          // Restart Interval Termination
  static final int RST1   = 0x00D1;          // Restart Interval Termination
  static final int RST2   = 0x00D2;          // Restart Interval Termination
  static final int RST3   = 0x00D3;          // Restart Interval Termination
  static final int RST4   = 0x00D4;          // Restart Interval Termination
  static final int RST5   = 0x00D5;          // Restart Interval Termination
  static final int RST6   = 0x00D6;          // Restart Interval Termination
  static final int RST7   = 0x00D7;          // Restart Interval Termination

  static final int SOI    = 0x00D8;          // Start Of Image
  static final int EOI    = 0x00D9;          // End Of Image
  static final int SOS    = 0x00DA;          // Start of Scan
  static final int DQT    = 0x00DB;          // Define Quantization Tables
  static final int DNL    = 0x00DC;          // Define Number of Lines
  static final int DRI    = 0x00DD;          // Define Restart Interval
  static final int DHP    = 0x00DE;          // Define hierarchical progression
  static final int EXP    = 0x00DF;          // Expand reference component(s)


  static final int APP0   = 0x00E0;          // Application JFIF, EXIF ?
  static final int APP1   = 0x00E1;          // Application 
  static final int APP2   = 0x00E2;          // Application 
  static final int APP3   = 0x00E3;          // Application 
  static final int APP4   = 0x00E4;          // Application 
  static final int APP5   = 0x00E5;          // Application 
  static final int APP6   = 0x00E6;          // Application 
  static final int APP7   = 0x00E7;          // Application 

  static final int APP8   = 0x00E8;          // Application 
  static final int APP9   = 0x00E9;          // Application 
  static final int APP10  = 0x00EA;          // Application 
  static final int APP11  = 0x00EB;          // Application 
  static final int APP12  = 0x00EC;          // Application 
  static final int APP13  = 0x00ED;          // Application 
  static final int APP14  = 0x00EE;          // Application Adobe ?
  static final int APP15  = 0x00EF;          // Application 

  static final int JPG0   = 0x00F0;          // JPEG Extensions
  static final int JPG1   = 0x00F1;          // JPEG Extensions
  static final int JPG2   = 0x00F2;          // JPEG Extensions
  static final int JPG3   = 0x00F3;          // JPEG Extensions
  static final int JPG4   = 0x00F4;          // JPEG Extensions
  static final int JPG5   = 0x00F5;          // JPEG Extensions
  static final int JPG6   = 0x00F6;          // JPEG Extensions
  static final int JPG7   = 0x00F7;          // JPEG Extensions
  static final int JPG8   = 0x00F8;          // JPEG Extensions
  static final int JPG9   = 0x00F9;          // JPEG Extensions
  static final int JPG10  = 0x00FA;          // JPEG Extensions
  static final int JPG11  = 0x00FB;          // JPEG Extensions
  static final int JPG12  = 0x00FC;          // JPEG Extensions
  static final int JPG13  = 0x00FD;          // JPEG Extensions
  static final int COM    = 0x00FE;          // Comment

  static final int MARK   = 0x00FF;

  static final int DCTSize      =  8;
  static final int DCTBlockSize = 64;

  static final int[] ZigZagTable={           // [1] p.30
     0, 1, 5, 6,14,15,27,28,
     2, 4, 7,13,16,26,29,42,
     3, 8,12,17,25,30,41,43,
     9,11,18,24,31,40,44,53,
    10,19,23,32,39,45,52,54,
    20,22,33,38,46,51,55,60,
    21,34,37,47,50,56,59,61,
    35,36,48,49,57,58,62,63
  };

  static final int[] IZigZagTable={
     0, 1, 8,16, 9, 2, 3,10,
    17,24,32,25,18,11, 4, 5,
    12,19,26,33,40,48,41,34,
    27,20,13, 6, 7,14,21,28,
    35,42,49,56,57,50,43,36,
    29,22,15,23,30,37,44,51,
    58,59,52,45,38,31,39,46,
    53,60,61,54,47,55,62,63
  };

  public static final byte[] HLDCTable={      // Huffman Luminance DC Coefficients [1] p.158
    0,1,5,1,1,1,1,1,1,0,0,0,0,0,0,0,          // BITS
    0,1,2,3,4,5,6,7,8,9,10,11                 // HUFFVAL
  };

  public static final byte[] HCDCTable={      // Huffman chrominance DC Coefficients [1] p.158
    0,3,1,1,1,1,1,1,1,1,1,0,0,0,0,0,          // BITS
    0,1,2,3,4,5,6,7,8,9,10,11                 // HUFFVAL
  };

  public static final byte[] HLACTable={      // Huffman Luminance AC Coefficients [1] p.158
    0,2,1,3,3,2,4,3,5,5,4,4,0,0,1,125,        // BITS
    1,2,3,0,4,17,5,18,33,49,65,6,19,81,97,7,  // HUFFVAL
    34,113,20,50,-127,-111,-95,8,35,66,-79,-63,21,82,-47,-16,
    36,51,98,114,-126,9,10,22,23,24,25,26,37,38,39,40,
    41,42,52,53,54,55,56,57,58,67,68,69,70,71,72,73,
    74,83,84,85,86,87,88,89,90,99,100,101,102,103,104,105,
    106,115,116,117,118,119,120,121,122,-125,-124,-123,-122,-121,-120,-119,
    -118,-110,-109,-108,-107,-106,-105,-104,-103,-102,-94,-93,-92,-91,-90,-89,
    -88,-87,-86,-78,-77,-76,-75,-74,-73,-72,-71,-70,-62,-61,-60,-59,
    -58,-57,-56,-55,-54,-46,-45,-44,-43,-42,-41,-40,-39,-38,-31,-30,
    -29,-28,-27,-26,-25,-24,-23,-22,-15,-14,-13,-12,-11,-10,-9,-8,
    -7,-6
  };

  public static final byte[] HCACTable={      // Huffman chrominance AC Coefficients [1] p.159
    0,2,1,2,4,4,3,4,7,5,4,4,0,1,2,119,        // BITS
    0,1,2,3,17,4,5,33,49,6,18,65,81,7,97,113, // HUFFVAL
    19,34,50,-127,8,20,66,-111,-95,-79,-63,9,35,51,82,-16,
    21,98,114,-47,10,22,36,52,-31,37,-15,23,24,25,26,38,
    39,40,41,42,53,54,55,56,57,58,67,68,69,70,71,72,
    73,74,83,84,85,86,87,88,89,90,99,100,101,102,103,104,
    105,106,115,116,117,118,119,120,121,122,-126,-125,-124,-123,-122,-121,
    -120,-119,-118,-110,-109,-108,-107,-106,-105,-104,-103,-102,-94,-93,-92,-91,
    -90,-89,-88,-87,-86,-78,-77,-76,-75,-74,-73,-72,-71,-70,-62,-61,
    -60,-59,-58,-57,-56,-55,-54,-46,-45,-44,-43,-42,-41,-40,-39,-38,
    -30,-29,-28,-27,-26,-25,-24,-23,-22,-14,-13,-12,-11,-10,-9,-8,
    -7,-6
  };

  public static final int[] LQT={             // [1]p.143 in zigzag order
     16, 11, 12, 14, 12, 10, 16, 14,
     13, 14, 18, 17, 16, 19, 24, 40,
     26, 24, 22, 22, 24, 49, 35, 37,
     29, 40, 58, 51, 61, 60, 57, 51,
     56, 55, 64, 72, 92, 78, 64, 68,
     87, 69, 55, 56, 80,109, 81, 87,
     95, 98,103,104,103, 62, 77,113,
    121,112,100,120, 92,101,103, 99
  };

  public static final int[] CQT={
    17,18,18,24,21,24,47,26,
    26,47,99,66,56,66,99,99,
    99,99,99,99,99,99,99,99,
    99,99,99,99,99,99,99,99,
    99,99,99,99,99,99,99,99,
    99,99,99,99,99,99,99,99,
    99,99,99,99,99,99,99,99,
    99,99,99,99,99,99,99,99
  };

  public static final int[] LQT2={            
     8, 6, 6, 7, 6, 5, 8, 7,
     7, 7, 9, 9, 8,10,12,20,
    13,12,11,11,12,25,18,19,
    15,20,29,26,31,30,29,26,
    28,28,32,36,46,39,32,34,
    44,35,28,28,40,55,41,44,
    48,49,52,52,52,31,39,57,
    61,56,50,60,46,51,52,50
  };

  public static final int[] CQT2={
     9, 9, 9,12,11,12,24,13,
    13,24,50,33,28,33,50,50,
    50,50,50,50,50,50,50,50,
    50,50,50,50,50,50,50,50,
    50,50,50,50,50,50,50,50,
    50,50,50,50,50,50,50,50,
    50,50,50,50,50,50,50,50,
    50,50,50,50,50,50,50,50
  };
}

// [1]'JPEG' : ISO/IEC IS 10918-1
//             ITU-T Recommendation T.81
// http://www.w3.org/Graphics/JPEG/itu-t81.pdf
