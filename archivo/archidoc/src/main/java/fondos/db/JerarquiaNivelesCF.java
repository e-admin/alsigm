package fondos.db;

import java.util.HashMap;
import java.util.Map;

import fondos.vos.INivelCFVO;

/**
 * @author luisanve
 * 
 */
public class JerarquiaNivelesCF {
	/*
	 * quiza sea mejor que sea clase interna de NivelCFDBEntity
	 */
	static Map nivelesCF = new HashMap();

	static INivelCFVO getNivel(String idNivel) {
		return (INivelCFVO) nivelesCF.get(idNivel);
	}

	static void addNivelCF(INivelCFVO nivelCF) {
		nivelesCF.put(nivelCF.getId(), nivelCF);
	}

}
