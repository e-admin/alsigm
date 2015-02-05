package es.ieci.tecdoc.fwktd.applets.scan.utils;

import java.util.HashMap;

import es.ieci.tecdoc.fwktd.applets.scan.key.ColorKeys;
import es.ieci.tecdoc.fwktd.applets.scan.key.OrientationKeys;
import es.ieci.tecdoc.fwktd.applets.scan.key.PaperSizeKeys;

public class Capabilities {
	@SuppressWarnings("unchecked")
	
	public static HashMap getPaperSizeCombo(int e, int i){
		HashMap map = new HashMap();			
		map.put(PaperSizeKeys.None[e],PaperSizeKeys.None[i]);
		map.put(PaperSizeKeys.A4_Carta[e],PaperSizeKeys.A4_Carta[i]);
		map.put(PaperSizeKeys.B5_Carta[e],PaperSizeKeys.B5_Carta[i]);
		map.put(PaperSizeKeys.US_Carta[e],PaperSizeKeys.US_Carta[i]);
		map.put(PaperSizeKeys.US_Legal[e],PaperSizeKeys.US_Legal[i]);
		map.put(PaperSizeKeys.A5[e],PaperSizeKeys.A5[i]);		
		map.put(PaperSizeKeys.B4[e],PaperSizeKeys.B4[i]);
		map.put(PaperSizeKeys.B6[e],PaperSizeKeys.B6[i]);
		map.put(PaperSizeKeys.US_Ledger[e],PaperSizeKeys.US_Ledger[i]);
		map.put(PaperSizeKeys.US_EXECUTIVE[e],PaperSizeKeys.US_EXECUTIVE[i]);
		map.put(PaperSizeKeys.A3[e],PaperSizeKeys.A3[i]);
		map.put(PaperSizeKeys.B3[e],PaperSizeKeys.B3[i]);				
		map.put(PaperSizeKeys.A6[e],PaperSizeKeys.A6[i]);
		map.put(PaperSizeKeys.C4[e],PaperSizeKeys.C4[i]);
		map.put(PaperSizeKeys.C5[e],PaperSizeKeys.C5[i]);
		map.put(PaperSizeKeys.C6[e],PaperSizeKeys.C6[i]);		
		map.put(PaperSizeKeys.A0_4[e],PaperSizeKeys.A0_4[i]);				
		map.put(PaperSizeKeys.A0_2[e],PaperSizeKeys.A0_2[i]);
		map.put(PaperSizeKeys.A0[e],PaperSizeKeys.A0[i]);
		map.put(PaperSizeKeys.A1[e],PaperSizeKeys.A1[i]);
		map.put(PaperSizeKeys.A2[e],PaperSizeKeys.A2[i]);
		map.put(PaperSizeKeys.A4[e],PaperSizeKeys.A4[i]);
		map.put(PaperSizeKeys.A7[e],PaperSizeKeys.A7[i]);
		map.put(PaperSizeKeys.A8[e],PaperSizeKeys.A8[i]);
		map.put(PaperSizeKeys.A9[e],PaperSizeKeys.A9[i]);
		map.put(PaperSizeKeys.A10[e],PaperSizeKeys.A10[i]);		
		map.put(PaperSizeKeys.ISOB0[e],PaperSizeKeys.ISOB0[i]);
		map.put(PaperSizeKeys.ISOB1[e],PaperSizeKeys.ISOB1[i]);
		map.put(PaperSizeKeys.ISOB2[e],PaperSizeKeys.ISOB2[i]);		
		map.put(PaperSizeKeys.ISOB3[e],PaperSizeKeys.ISOB3[i]);
		map.put(PaperSizeKeys.ISOB4[e],PaperSizeKeys.ISOB4[i]);
		map.put(PaperSizeKeys.ISOB5[e],PaperSizeKeys.ISOB5[i]);
		map.put(PaperSizeKeys.ISOB6[e],PaperSizeKeys.ISOB6[i]);
		map.put(PaperSizeKeys.ISOB7[e],PaperSizeKeys.ISOB7[i]);
		map.put(PaperSizeKeys.ISOB8[e],PaperSizeKeys.ISOB8[i]);				
		map.put(PaperSizeKeys.ISOB9[e],PaperSizeKeys.ISOB9[i]);
		map.put(PaperSizeKeys.ISOB10[e],PaperSizeKeys.ISOB10[i]);		
		map.put(PaperSizeKeys.JISB0[e],PaperSizeKeys.JISB0[i]);
		map.put(PaperSizeKeys.JISB1[e],PaperSizeKeys.JISB1[i]);		
		map.put(PaperSizeKeys.JISB2[e],PaperSizeKeys.JISB2[i]);				
		map.put(PaperSizeKeys.JISB3[e],PaperSizeKeys.JISB3[i]);
		map.put(PaperSizeKeys.JISB4[e],PaperSizeKeys.JISB4[i]);
		map.put(PaperSizeKeys.JISB5[e],PaperSizeKeys.JISB5[i]);
		map.put(PaperSizeKeys.JISB6[e],PaperSizeKeys.JISB6[i]);
		map.put(PaperSizeKeys.JISB7[e],PaperSizeKeys.JISB7[i]);
		map.put(PaperSizeKeys.JISB8[e],PaperSizeKeys.JISB8[i]);
		map.put(PaperSizeKeys.JISB9[e],PaperSizeKeys.JISB9[i]);
		map.put(PaperSizeKeys.JISB10[e],PaperSizeKeys.JISB10[i]);
		map.put(PaperSizeKeys.C0[e],PaperSizeKeys.C0[i]);
		map.put(PaperSizeKeys.C1[e],PaperSizeKeys.C1[i]);		
		map.put(PaperSizeKeys.C2[e],PaperSizeKeys.C2[i]);
		map.put(PaperSizeKeys.C3[e],PaperSizeKeys.C3[i]);
		map.put(PaperSizeKeys.C7[e],PaperSizeKeys.C7[i]);
		map.put(PaperSizeKeys.C8[e],PaperSizeKeys.C8[i]);
		map.put(PaperSizeKeys.C9[e],PaperSizeKeys.C9[i]);
		map.put(PaperSizeKeys.C10[e],PaperSizeKeys.C10[i]);				
		map.put(PaperSizeKeys.USEXECUTIVE[e],PaperSizeKeys.USEXECUTIVE[i]);
		map.put(PaperSizeKeys.BUSINESSCARD[e],PaperSizeKeys.BUSINESSCARD[i]);					
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap getOrientationCombo(int e, int i){
		HashMap map = new HashMap();
		map.put(OrientationKeys.Auto[e], OrientationKeys.Auto[i]);
		map.put(OrientationKeys.Auto_imagen[e], OrientationKeys.Auto_imagen[i]);
		map.put(OrientationKeys.Auto_texto[e], OrientationKeys.Auto_texto[i]);
		map.put(OrientationKeys.Horizontal[e], OrientationKeys.Horizontal[i]);
		map.put(OrientationKeys.Rot_180[e], OrientationKeys.Rot_180[i]);
		map.put(OrientationKeys.Rot_90[e], OrientationKeys.Rot_90[i]);
		map.put(OrientationKeys.Vertical[e], OrientationKeys.Vertical[i]);
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap getColorCombo(int e, int i){
		HashMap map = new HashMap();
		map.put(ColorKeys.LAB[e], ColorKeys.LAB[i]);
		map.put(ColorKeys.TWPT_BW[e], ColorKeys.TWPT_BW[i]);	
		map.put(ColorKeys.TWPT_GRAY[e], ColorKeys.TWPT_GRAY[i]);	
		map.put(ColorKeys.TWPT_RGB[e], ColorKeys.TWPT_RGB[i]);	
		map.put(ColorKeys.TWPT_PALETTE[e], ColorKeys.TWPT_PALETTE[i]);	
		map.put(ColorKeys.TWPT_CMY[e], ColorKeys.TWPT_CMY[i]);	
		map.put(ColorKeys.TWPT_CMYK[e], ColorKeys.TWPT_CMYK[i]);	
		map.put(ColorKeys.TWPT_YUV[e], ColorKeys.TWPT_YUV[i]);	
		map.put(ColorKeys.TWPT_YUVK[e], ColorKeys.TWPT_YUVK[i]);	
		map.put(ColorKeys.TWPT_CIEXYZ[e], ColorKeys.TWPT_CIEXYZ[i]);	
		map.put(ColorKeys.TWPT_SRGB[e], ColorKeys.TWPT_SRGB[i]);	
		map.put(ColorKeys.TWPT_SCRGB[e], ColorKeys.TWPT_SCRGB[i]);
		map.put(ColorKeys.TWPT_BGR[e], ColorKeys.TWPT_BGR[i]);	
		map.put(ColorKeys.TWPT_CIELAB[e], ColorKeys.TWPT_CIELAB[i]);	
		map.put(ColorKeys.TWPT_CIELUV[e], ColorKeys.TWPT_CIELUV[i]);	
		map.put(ColorKeys.TWPT_YCBCR[e], ColorKeys.TWPT_YCBCR[i]);	
		map.put(ColorKeys.INFRARED[e], ColorKeys.INFRARED[i]);	
		return map;
	}
}
