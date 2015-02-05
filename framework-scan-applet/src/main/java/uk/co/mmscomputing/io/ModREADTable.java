package uk.co.mmscomputing.io;

public interface ModREADTable{
 static final int P    =0;
 static final int H    =1;

 static final int VL3  =2;
 static final int VL2  =3;
 static final int VL1  =4;
 static final int V0   =5;
 static final int VR1  =6;
 static final int VR2  =7;
 static final int VR3  =8;

 static final int HX   =9;
 static final int EOFB =10;

 static final int codes[][] = {
	{ 0x0001,  V0,   1 },
	{ 0x0002, VL1,   3 },
	{ 0x0004,   H,   3 },
	{ 0x0006, VR1,   3 },
	{ 0x0008,   P,   4 },
	{ 0x0010, VL2,   6 },
	{ 0x0030, VR2,   6 },
	{ 0x0020, VL3,   7 },
	{ 0x0060, VR3,   7 },
	{ 0x00800800, EOFB, 24},  // two EOLs T.6
 };
}
