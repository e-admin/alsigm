package uk.co.mmscomputing.imageio.jpeg;


// [1] Joerg Anders, TU Chemnitz, Fakultaet fuer Informatik, GERMANY
// ja@informatik.tu-chemnitz.de
// http://rnvs.informatik.tu-chemnitz.de/~jan/MPEG/HTML/IDCT.html [last accessed 2005-11-23]

public class JPEGFastDCTInputStream extends JPEGDCTInputStream{

  public JPEGFastDCTInputStream(JPEGHuffmanInputStream dc,JPEGHuffmanInputStream ac,int[] qt,int bps){
    super(dc,ac,qt,bps);
  }

  static private final int CONST_BITS = 11;

  static private int compute(double val){return (int)(val*(1<<CONST_BITS));}

  static private final int VAL_BITS   = 11;
  static private final int ALLBITS    = CONST_BITS + VAL_BITS;
  static private final int TWO        = CONST_BITS + 1;

  static private final int C6      = compute(2.0*Math.sin(Math.PI/8.0));
  static private final int C4C6    = compute(2.0*Math.sqrt(2.0)*Math.sin(Math.PI/8.0));
  static private final int C4      = compute(Math.sqrt(2.0));
  static private final int Q       = compute(2.0*(Math.cos(Math.PI/8.0)-Math.sin(Math.PI/8.0)));
  static private final int C4Q     = compute(2.0*Math.sqrt(2.0)*(Math.cos(Math.PI/8.0)-Math.sin(Math.PI/8.0)));
  static private final int R       = compute(2.0*(Math.cos(Math.PI/8.0)+Math.sin(Math.PI/8.0)));
  static private final int C4R     = compute(2.0*Math.sqrt(2.0)*(Math.cos(Math.PI/8.0)+Math.sin(Math.PI/8.0)));

//  protected int matr1[]=new int[DCTBlockSize];  // declared in JPEGDCTInputStream
  protected int matr2[]=new int[DCTBlockSize];
  @SuppressWarnings("unused")
  public void inverseDCT(int[] buffer){
    
	int tmp0, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7;
    int plus8, plus16, plus24, plus32, plus40, plus48, plus56;
    int co1, co2, co3, co5, co6, co7, co35, co17;
    int n0, n1, n2, n3;
    int m1, m2, m3, m4, m5, m6, m7, tmp;
    int l0 = 0, l1 = 0, l2 = 0, l3 = 0;
    int g0, g1, g2, g3;
    int i, j, p;

    for (p = j = 0; j < 64; j+=8) {
      matr1[p++] = buffer[j+0];
      matr1[p++] = buffer[j+4];
      matr1[p++] = (co2 = buffer[j+2])-(co6 = buffer[j+6]);
      matr1[p++] = co2+co6;
      matr1[p++] =-(co3=buffer[j+3])+(co5=buffer[j+5]); 
      matr1[p++] = (co17=(co1=buffer[j+1]+(co7=buffer[j+7])))-(co35=co3+co5);
      matr1[p++] = co1-co7;
      matr1[p++] = co17+co35;
    }

    for (p = i = 0; i < 8; i++) {
      switch(i) {
      case 0:
      case 1:
      case 3:
      case 7:
        tmp4 = (co3=matr1[24+i])-(co5=matr1[40+i]);
        tmp6 = (co1=matr1[ 8+i])-(co7=matr1[56+i]);
        tmp = C6 * (tmp6-tmp4);
        matr2[p++] =  matr1[i  ] << CONST_BITS;
        matr2[p++] =  matr1[32+i] << CONST_BITS;
        matr2[p++] =  ((co2=matr1[16+i])-(co6=matr1[48+i]))*C4;
        matr2[p++] =  (co2+co6) << CONST_BITS;
        matr2[p++] =  Q*tmp4-tmp;
        matr2[p++] =  ((co17=co1 + co7)-(co35=co3+co5))*C4;
        matr2[p++] =  R*tmp6-tmp;
        matr2[p++] =  (co17+co35) << CONST_BITS;
        break;
      case 2:
      case 5:
        tmp4 = (co3=matr1[24+i])-(co5=matr1[40+i]);
        tmp6 = (co1=matr1[ 8+i])-(co7=matr1[56+i]);
        tmp = C4C6 * (tmp6-tmp4);
        matr2[p++] = C4*matr1[i  ];
        matr2[p++] = C4*matr1[i+32];
        matr2[p++] = ((co2=matr1[16+i])-(co6=matr1[48+i])) << TWO;
        matr2[p++] = C4*(co2+co6);
        matr2[p++] = C4Q*tmp4-tmp;
        matr2[p++] = ((co17 =co1+co7)-(co35=co3+co5)) << TWO;
        matr2[p++] = C4R*tmp6-tmp;
        matr2[p++] = C4* (co17+co35);
        break;
      case 4:
        matr2[p++] = matr1[   i];
        matr2[p++] = matr1[32+i];
        matr2[p++] = (co2=matr1[16+i])-(co6=matr1[48+i]);
        matr2[p] = co2+co6;
        l0 = l2 = -(co3=matr1[24+i])+(co5=matr1[40+i]);
        p += 2;
        matr2[p] = (co17 =(co1=matr1[ 8+i]) + (co7=matr1[56+i]))-(co35=co3+co5);
        l3 = -( l1 = co1-co7);
        p += 2;
        matr2[p++] = co17+co35;
        break;
      case 6:
        matr2[p++] = matr1[   i];
        matr2[p++] = matr1[32+i];
        matr2[p++] = (co2=matr1[16+i])-(co6=matr1[48+i]);
        matr2[p] = co2+co6;
        l1 += (tmp4 = -(co3=matr1[24+i])+(co5=matr1[40+i]));
        l3 += tmp4;
        p += 2;
        matr2[p] = (co17 =(co1=matr1[ 8+i]) + (co7=matr1[56+i]))-(co35=co3+co5);
        l2 += (tmp6 = co1-co7);
        l0 -= tmp6;
        p += 2;
        matr2[p++] = co17+co35;
        break;
      }
    }

    g0 = C4*(l0+l1);
    g1 = C4*(l0-l1);
    g2 = l2 << TWO;
    g3 = l3 << TWO;

    matr2[36] = g0+g2;
    matr2[38] = g1+g3;
    matr2[52] = g1-g3;
    matr2[54] = g2-g0;

    tmp = C6*(matr2[32]+matr2[48]);
    matr2[32] = -Q*matr2[32]-tmp;
    matr2[48] =  R*matr2[48]-tmp;

    tmp = C6*(matr2[33] + matr2[49]);
    matr2[33] = -Q*matr2[33]-tmp;
    matr2[49] =  R*matr2[49]-tmp;

    tmp = C4C6 * (matr2[34] + matr2[50]);
    matr2[34] = -C4Q*matr2[34]-tmp;
    matr2[50] =  C4R*matr2[50]-tmp;

    tmp = C6*(matr2[35] + matr2[51]);
    matr2[35] = -Q*matr2[35]-tmp;
    matr2[51] =  R*matr2[51]-tmp;

    tmp = C4C6 * (matr2[37] + matr2[53]);
    matr2[37] = -C4Q*matr2[37]-tmp;
    matr2[53] =  C4R*matr2[53]-tmp;

    tmp = C6*(matr2[39] + matr2[55]);
    matr2[39] = -Q*matr2[39]-tmp;
    matr2[55] =  R*matr2[55]-tmp;

    for (p=i = 0; i < 8; i++,p+=8) {
      matr1[p] = (tmp4 = (n3 = matr2[p]+matr2[p+1]) + matr2[p+3]) + matr2[p+7];
      matr1[p+3] = (tmp6=n3-matr2[p+3])-(tmp7=matr2[p+4]-(tmp1=(tmp2=matr2[p+6]-matr2[p+7])-matr2[p+5]));
      matr1[p+4] = tmp6+tmp7;
      matr1[p+1] = (tmp3=(n1=matr2[p]-matr2[p+1])+(n2=matr2[p+2]-matr2[p+3]))+tmp2;
      matr1[p+2] = (tmp5=n1-n2)-tmp1;
      matr1[p+5] = tmp5+tmp1;
      matr1[p+6] = tmp3-tmp2;
      matr1[p+7] = tmp4-matr2[p+7];
    }
    plus8 = 8; plus16 = 16; plus24 = 24; plus32 = 32; plus40 = 40; plus48 = 48; plus56 = 56;
    for (p = i = 0; p < 64; p+=8) {
      buffer[p] = ((tmp4 = (n3 = matr1[i]+matr1[plus8]) +matr1[plus24]) + matr1[plus56]) >> ALLBITS;
      buffer[p+3] = ((tmp6=n3-matr1[plus24])-(tmp7=matr1[plus32++]-(tmp1=(tmp2=matr1[plus48++]-matr1[plus56])-matr1[plus40++])))>>ALLBITS;
      buffer[p+4] = (tmp6+tmp7) >> ALLBITS;
      buffer[p+1] = ((tmp3 = (n1 = matr1[i++]-matr1[plus8++])+ (n2 = matr1[plus16++]-matr1[plus24++]))+tmp2) >> ALLBITS;
      buffer[p+2] = ((tmp5 = n1-n2) -tmp1) >> ALLBITS;
      buffer[p+5] = (tmp5+tmp1) >> ALLBITS;
      buffer[p+6] = (tmp3-tmp2) >> ALLBITS;
      buffer[p+7] = (tmp4-matr1[plus56++]) >> ALLBITS;
    }
  }

  static void normalize(int qt[]){   // need to normalize quantization tables; called in JPEGInputStream
    double d;

    for(int j=0;j<DCTSize;j++){
      for(int i=0;i<DCTSize;i++){
        d=(double)qt[ZigZagTable[j*DCTSize+i]];
        if((i==0)&&(j==0)){
          d/=8.0;
        }else if((i==0)||(j==0)){
          d/=8.0/Math.sqrt(2.0);
        }else{
          d/=4.0;
        }
        qt[ZigZagTable[j*DCTSize+i]]=(int)(d*(1<<VAL_BITS)*Math.cos(Math.PI*i/16.0)* Math.cos(Math.PI*j/16.0)+0.5);
      }
    }
  }
}

