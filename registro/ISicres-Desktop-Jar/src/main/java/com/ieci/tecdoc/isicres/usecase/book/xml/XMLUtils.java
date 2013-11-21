package com.ieci.tecdoc.isicres.usecase.book.xml;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FFldDef;
import com.ieci.tecdoc.idoc.decoder.validation.idocarchdet.FieldFormat;
import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author LMVICENTE
 * @creationDate 31-may-2004 11:22:30
 * @version
 * @since
 */
public class XMLUtils implements Keys {

    /*******************************************************************************************************************
     * Attributes
     ******************************************************************************************************************/
	private static Logger _logger = Logger.getLogger(XMLUtils.class);

    /*******************************************************************************************************************
     * Constructors
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Public methods
     ******************************************************************************************************************/

    public static void addStyleValNode(Element parent, int left, int width, int top) {
        int newLeft = left + 2 + (width - left);
        parent.addElement(XML_STYLEVALIMG_TEXT).addText(formatStyleText(newLeft, top + 5));
    }

    public static void addStyleNode(Element parent, int left, int top, int width, int height) {
        parent.addElement(XML_STYLE_TEXT).addText(formatStyleText(left, top, width, height));
    }

    public static void addStyleNode(Element parent, int left, int top, int width, int height,
    		String fontName, int fontSize, int fontEnh, int fontColor, int style, int role) {
        parent.addElement(XML_STYLE_TEXT).addText(formatStyleText(left, top, width, height, fontName, fontSize, fontEnh, fontColor, style, role));
    }

    public static int convertWidth(int width) {
        return (width * C_X) / 4;
    }

    public static int convertHeight(int height) {
        return (height * C_Y) / 8;
    }

    public static String formatStyleText(int left, int top) {
        left = convertWidth(left);
        top = convertHeight(top);

        return MessageFormat.format(XML_XTYLE_FORMAT_2, new String[] { Integer.toString(left), Integer.toString(top + AMPL_TOP)});
    }

    public static String formatStyleText(int left, int top, int width, int height) {
        left = convertWidth(left);
        top = convertHeight(top);
        width = convertWidth(width);
        height = convertHeight(height);

        return MessageFormat.format(XML_XTYLE_FORMAT, new String[] { Integer.toString(left), Integer.toString(top + AMPL_TOP) ,
                Integer.toString(width - left), Integer.toString(height - top)});
    }

    public static String formatStyleText(int left, int top, int width,
			int height, String fontName, int fontSize, int fontEnh,
			int fontColor, int style, int role) {
		String result = null;
		String formatStyle = "";
		String formatColorStyle = "";
		String estilo = "";
		String alineamiento = null;
		String color = "#000000";
		String fontNameAux = fontName;
		int fontSizeAux = fontSize;

		int operator = 0x00FF;

		left = convertWidth(left);
		top = convertHeight(top);
		width = convertWidth(width);
		height = convertHeight(height);
		if (role != 5){

			if (fontNameAux == null || fontNameAux.equals("")) {
				fontNameAux = "MS Sans Serif";
			}
			if (fontSize == 0) {
				fontSizeAux = 8;
			}

			String aux = null;
			if (fontColor != 0) {
				int intR = fontColor & operator;
				String hexR = Integer.toHexString(intR);
				int intG = (fontColor >> 8) & operator;
				String hexG = Integer.toHexString(intG);
				int intB = (fontColor >> 16) & operator;
				String hexB = Integer.toHexString(intB);
				if (hexR.length() < 2) {
					hexR = "0" + hexR;
				}
				if (hexG.length() < 2) {
					hexG = "0" + hexG;
				}
				if (hexB.length() < 2) {
					hexB = "0" + hexB;
				}
				color = "#" + hexR + hexG + hexB;
			}

			if ((fontEnh & 1) == 1) {
				estilo += "font-weight:bold;";
			}
			if ((fontEnh & 2) == 2) {
				estilo += "font-style:italic;";
			}
			if ((fontEnh & 4) == 4) {
				estilo += "text-decoration:underline;";
			}

			if ((style & 0x0002) == 2) {
				alineamiento = "right";
			} else if ((style & 0x0001) == 1) {
				alineamiento = "center";
			} else {
				alineamiento = "left";
			}

			formatColorStyle = MessageFormat.format(XML_XTYLE_COLOR_FORMAT,
					new String[] { fontNameAux, Integer.toString(fontSizeAux), color,
							alineamiento, estilo });
		}
		formatStyle = MessageFormat.format(XML_XTYLE_FORMAT,
				new String[] { Integer.toString(left), Integer.toString(top + AMPL_TOP),
						Integer.toString(width - left),
						Integer.toString(height - top) });
		result = formatStyle + formatColorStyle;
		return result;
	}

    /**
     * Metodo que formatea la fecha a formato largo o corto segun las propiedades del campo

     * @param longFormatter - Formato largo de la fecha
     * @param shortFormatter - Formato corte de fecha
     * @param date - Fecha
     * @param axsf
     * @param fieldformat
     * @param fldId - Id del campo
     * @return
     */
	public static String getDateWithFormat(
			SimpleDateFormat longFormatter, SimpleDateFormat shortFormatter,
			Date date, AxSf axsf, FieldFormat fieldformat, int fldId) {
    	String result = null;

    	if(XMLUtils.getDataType(axsf, fieldformat, fldId) == 3){
    		//formato largo de fecha
    		result = longFormatter.format(date);
    	}else{
    		//formato corto de fecha
    		result = shortFormatter.format(date);
    	}

    	return result;
    }

    /**
     * Obtiene el formato del campo pasado como parametro
     * @param axsf
     * @param fieldFormat
     * @param fldId
     * @return
     */
    public static int getDataType(AxSf axsf, FieldFormat fieldFormat, int fldId) {
		int dateType = 0;
		FFldDef fldDef = null;
		int fldid = 0;
		if (fldId == 9) {
			dateType = 4;
		} else if (fldId == 6) {
			dateType = 5;
		} else {
			for (Iterator it = fieldFormat.getFlddefs().values().iterator(); it
					.hasNext();) {
				fldDef = (FFldDef) it.next();
				fldid = Integer.parseInt(fldDef.getColname().substring(
						XML_FLD_TEXT.length(), fldDef.getColname().length()));
				if (fldid == fldId) {
					if (fldDef.getType() == 1 || fldDef.getType() == 2) {
						dateType = 0;
						break;
					}
					if (fldDef.getType() == 3 || fldDef.getType() == 4) {
						dateType = 1;
						break;
					}
					if (fldDef.getType() == 5 || fldDef.getType() == 6) {
						dateType = 6;
						break;
					}
					if (fldDef.getType() == 7) {
						//formato de fecha corta
						dateType = 2;
						break;
					}
					if (fldDef.getType() == 9) {
						//formato de fecha larga
						dateType = 3;
						break;
					}
				}
			}
		}

		return dateType;
	}


    /**
     * Método que obtiene el formato de fecha para la bandeja de distribución
     *
     * @param locale - Idioma utilizado
     */
	public static SimpleDateFormat getDateFormatView(Locale locale) {
		SimpleDateFormat result;

		String dateFormatByDefault = RBUtil.getInstance(locale).getProperty(
				I18N_DATE_SHORTFORMAT);

		// verificamos si esta definido el patron
		String dateFormatString = RBUtil.getInstance(locale).getProperty(
				DATE_FORMAT_VIEW_DISTRIBUTION, dateFormatByDefault);

		//comprobamos si el formato no esta vacio
		if (StringUtils.isNotBlank(dateFormatString)) {
			//hay formato definido por tanto se asigna
			result = new SimpleDateFormat(dateFormatString);
		} else {
			//no existe formato definido por tanto se asocia el de por defecto
			result = new SimpleDateFormat(dateFormatByDefault);
		}

		return result;
	}

    /*******************************************************************************************************************
     * Protected methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Private methods
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Inner classes
     ******************************************************************************************************************/

    /*******************************************************************************************************************
     * Test brench
     ******************************************************************************************************************/

}

