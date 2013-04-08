/*
 * Created on 01-jun-2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ieci.tecdoc.isicres.usecase.distribution.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import com.ieci.tecdoc.isicres.desktopweb.Keys;
import com.ieci.tecdoc.isicres.desktopweb.utils.RBUtil;

/**
 * @author 79426599
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DistributionSearchFields  implements Keys{

	private String fieldsDistName[] = {"STATE", "DIST_DATE", "STATE_DATE", "@ORIG", "@DEST"};
	private String fieldsRegName[] = {"@FLD1", "@FLD2", "@FLD7", "@FLD8", "@FLD16", "@FLD17", "@FLD9"};
	private String fieldsDistLabel[] = new String[4];
	private String fieldsRegLabel[] = new String[7];
	private String fieldsDistType[] = {"5", "2", "2", "1", "1"};
	private String fieldsRegType[] = {"0", "2", "1","1", "1", "0", "0"};
	private String fieldsDistValidation[] = {"0", "0", "0", "9", "10"};
	private String fieldsRegValidation[] = {"0", "0", "1", "1", "6", "0", "0"};
	
	private List result = new ArrayList();
	private String dataBaseType = null;
	
	public DistributionSearchFields(Integer typeSearch, Integer typeDist, Locale locale, String dataBaseType){
		this.dataBaseType = dataBaseType;
		getFieldsDistRegLabel(typeSearch, typeDist, locale);
		createSearchFields(typeSearch, typeDist, locale);
	}
	
	private void createSearchFields(Integer typeSearch, Integer typeDist, Locale locale){
		
		DistributionFields distributionFields = null;
		Map operators = new HashMap();
		
		if (typeSearch.intValue() == 1){
			int remove = 0;
			if (typeDist.intValue() == 1){
				remove = 4;
			} else {
				remove = 3;
			}
			for (int i = 0; i < fieldsDistName.length; i++){
				if (i != remove){
					operators = getOperators(new Integer(fieldsDistType[i]), new Integer(fieldsDistValidation[i]), locale);
					if (fieldsDistLabel.length == i){
						int k = i;
						distributionFields = new DistributionFields(fieldsDistName[i], fieldsDistLabel[k-1], new Integer(fieldsDistType[i]), new Integer(fieldsDistValidation[i]));
					} else {
						distributionFields = new DistributionFields(fieldsDistName[i], fieldsDistLabel[i], new Integer(fieldsDistType[i]), new Integer(fieldsDistValidation[i]));
					}
					distributionFields.setOperators(operators);
					result.add(distributionFields);
				}
			}
		}
		if (typeSearch.intValue() == 2){
			for (int i = 0; i < fieldsRegLabel.length; i++){
				operators = getOperators(new Integer(fieldsRegType[i]), new Integer(fieldsRegValidation[i]), locale);
				distributionFields = new DistributionFields(fieldsRegName[i], fieldsRegLabel[i], new Integer(fieldsRegType[i]), new Integer(fieldsRegValidation[i]));
				distributionFields.setOperators(operators);
				result.add(distributionFields);
			}
		}
		
	}
	
	private void getFieldsDistRegLabel(Integer typeSearch, Integer typeDist, Locale locale){
		
		DistributionFields distributionFields = null;
		String key= null;
		
		if (typeSearch.intValue() == 1){
			int remove = 0;
			if (typeDist.intValue() == 1){
				remove = 4;
			} else {
				remove = 3;
			}
			for (int i = 0; i < fieldsDistName.length; i++){
				key = BOOKUSECASE_DISTRIBUTIONSEARCH_DIST_LABEL_ROW;
				key = key + i;
				if (i != remove){
					if (fieldsDistLabel.length == i){
						int k = i;
						fieldsDistLabel[k-1] = RBUtil.getInstance(locale).getProperty(key);
					} else {
						fieldsDistLabel[i] = RBUtil.getInstance(locale).getProperty(key);
						
					}
				}
			}
		}
		if (typeSearch.intValue() == 2){
			for (int i = 0; i < fieldsRegName.length; i++){
				key = BOOKUSECASE_DISTRIBUTIONSEARCH_REG_LABEL_ROW;
				key = key + i;
						fieldsRegLabel[i] = RBUtil.getInstance(locale).getProperty(key);
			}
		}
		
	}
	
	private Map getOperators(Integer type, Integer validation, Locale locale){
		Map operator = new TreeMap();
		
		if (type.intValue() == 5 && validation.intValue() == 0){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
		}
		if (type.intValue() == 0 && validation.intValue() == 0){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_BEGIN_BY_VALUE), "LIKE1");
			operator.put(3 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_END_WITH_VALUE), "LIKE2");
			operator.put(4 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_LIKE_TEXT_VALUE), "LIKE");
			operator.put(5 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
			operator.put(6 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_GREATER_TEXT_VALUE), ">");
			operator.put(7 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_LESSER_TEXT_VALUE), "<");
			operator.put(8 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_GREATER_EQUAL_TEXT_VALUE), ">=");
			operator.put(9 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_LESSER_EQUAL_TEXT_VALUE), "<=");
		}
		if (type.intValue() == 2 && validation.intValue() == 0){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), ">=AND<=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<OR>");
			operator.put(3 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_AFTER_TO_VALUE), ">23");
			operator.put(4 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_BEFORE_TO_VALUE), "<00");
			operator.put(5 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_ON_MONTH_VALUE), "MOUNTH");
			operator.put(6 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_ON_YEAR_VALUE), "YEAR");
		}
		if (type.intValue() == 1 && (validation.intValue() == 9 || validation.intValue() == 10)){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
		}
		if (type.intValue() == 1 && validation.intValue() == 6){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
		}
		if (type.intValue() == 1 && validation.intValue() == 1){
			operator.put(1 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_EQUAL_TEXT_VALUE), "=");
			operator.put(2 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_NOT_EQUAL_TEXT_VALUE), "<>");
			operator.put(3 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_BEGIN_BY_VALUE), "SCR_ORGS");
			if (dataBaseType.equals("ORACLE")){
				operator.put(3 + RBUtil.getInstance(locale).getProperty(I18N_QUERY_DEPEND_OF_VALUE), "SCR_ORGS_CONNECT");
			}
		}
		
		return operator;
	
	}
	/**
	 * @return Returns the result.
	 */
	public List getResult() {
		return result;
	}
	
	public static void main (String[] argv){
		DistributionSearchFields distributionSearchFields = new DistributionSearchFields(new Integer(2), new Integer(1), new Locale("es", "ES", ""),"ORACLE");
	}
}
