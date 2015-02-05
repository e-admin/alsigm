package ieci.tecdoc.sgm.catastro;

import ieci.tecdoc.sgm.catastro.exception.CatastroCodigosError;
import ieci.tecdoc.sgm.catastro.exception.CatastroExcepcion;

import com.localgis.ln.ISOALocalGISHttpBindingStub;
import com.localgis.model.ot.ArrayOfParcelaOT;
import com.localgis.model.ot.ParcelaOT;


public class CatastroManager {

	public static boolean validarReferenciaCatastral(String referenciaCatastral) throws CatastroExcepcion {
		try {
			ISOALocalGISHttpBindingStub sCatastro = CatastroFactory.getInstance().obtenerServicioCatastro();
			return sCatastro.validarReferencia(referenciaCatastral);
		} catch(Exception e) {
			throw new CatastroExcepcion(CatastroCodigosError.EC_VALIDAR_REFERENCIA_CATASTRAL, e.getCause());
		}
	}
	
	public static ParcelaOT[] consultarCatastro(String referenciaCatastral) throws CatastroExcepcion {
		try {
			ISOALocalGISHttpBindingStub sCatastro = CatastroFactory.getInstance().obtenerServicioCatastro();
			ArrayOfParcelaOT parcelas = sCatastro.consultarCatastro(referenciaCatastral);
			return (parcelas != null ? parcelas.getParcelaOT() : null);
		} catch(Exception e) {
			throw new CatastroExcepcion(CatastroCodigosError.EC_CONSULTAR_REFERENCIA_CATASTRAL, e.getCause());
		}
	}
	
}
