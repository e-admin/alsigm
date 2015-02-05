package uk.co.mmscomputing.io;

public interface Base64Table{

/*
  rfc1341: MIME (Multipurpose Internet Mail Extensions)


               Value Encoding  Value  Encoding   Value  Encoding   Value
            Encoding
                   0 A            17 R            34 i            51 z
                   1 B            18 S            35 j            52 0
                   2 C            19 T            36 k            53 1
                   3 D            20 U            37 l            54 2
                   4 E            21 V            38 m            55 3
                   5 F            22 W            39 n            56 4
                   6 G            23 X            40 o            57 5
                   7 H            24 Y            41 p            58 6
                   8 I            25 Z            42 q            59 7
                   9 J            26 a            43 r            60 8
                  10 K            27 b            44 s            61 9
                  11 L            28 c            45 t            62 +
                  12 M            29 d            46 u            63 /
                  13 N            30 e            47 v
                  14 O            31 f            48 w         (pad) =
                  15 P            32 g            49 x
                  16 Q            33 h            50 y


*/


  static final byte[] encodeTable={
    (byte)'A', (byte)'B', (byte)'C', (byte)'D', (byte)'E', (byte)'F', (byte)'G', (byte)'H', 
    (byte)'I', (byte)'J', (byte)'K', (byte)'L', (byte)'M', (byte)'N', (byte)'O', (byte)'P', (byte)'Q', 
    (byte)'R', (byte)'S', (byte)'T', (byte)'U', (byte)'V', (byte)'W', (byte)'X', (byte)'Y', 
    (byte)'Z', (byte)'a', (byte)'b', (byte)'c', (byte)'d', (byte)'e', (byte)'f', (byte)'g', (byte)'h', 
    (byte)'i', (byte)'j', (byte)'k', (byte)'l', (byte)'m', (byte)'n', (byte)'o', (byte)'p', 
    (byte)'q', (byte)'r', (byte)'s', (byte)'t', (byte)'u', (byte)'v', (byte)'w', (byte)'x', (byte)'y', 
    (byte)'z', (byte)'0', (byte)'1', (byte)'2', (byte)'3', (byte)'4', (byte)'5', (byte)'6', 
    (byte)'7', (byte)'8', (byte)'9', (byte)'+', (byte)'/'
  };

  static final byte equalSign = (byte)'=';
}