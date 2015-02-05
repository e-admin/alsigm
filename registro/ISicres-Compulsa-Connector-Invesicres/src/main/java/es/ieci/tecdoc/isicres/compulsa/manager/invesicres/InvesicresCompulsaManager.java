/**
 *
 */
package es.ieci.tecdoc.isicres.compulsa.manager.invesicres;

import java.io.InputStream;
import java.io.OutputStream;

import com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO;
import com.ieci.tecdoc.common.compulsa.vo.ISicresReturnCompulsaVO;
import com.ieci.tecdoc.common.keys.CompulsaKeys;
import com.ieci.tecdoc.isicres.compulsa.manager.GenericCompulsaManager;

import es.ieci.tecdoc.isicres.compulsa.connector.invesicres.InvesicresCompulsaConnector;
import es.ieci.tecdoc.isicres.compulsa.connector.invesicres.vo.InvesicresCompulsaDatosEspecificosVO;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO;
import es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresBasicDatosEspecificosCompulsaVO;

/**
 * @author Iecisa
 * @version $Revision$ ($Author$)
 *
 */
public class InvesicresCompulsaManager extends GenericCompulsaManager {

	/**
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.isicres.compulsa.manager.GenericCompulsaManager#compulsarDocuments(com.ieci.tecdoc.common.compulsa.vo.ISicresCreateCompulsaVO)
	 */
	public ISicresReturnCompulsaVO compulsarDocuments(
			ISicresCreateCompulsaVO compulsaVO) {
		this.connector = new InvesicresCompulsaConnector();
		return super.compulsarDocuments(compulsaVO);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.ieci.tecdoc.isicres.compulsa.manager.GenericCompulsaManager#getDatosEspecificosCompulsa(es.ieci.tecdoc.isicres.compulsa.connector.vo.ISicresAbstractCompulsaVO)
	 */
	public ISicresAbstractCompulsaVO getDatosEspecificosCompulsa(
			ISicresAbstractCompulsaVO compulsaVO) {
		ISicresBasicDatosEspecificosCompulsaVO basicDatosEspecificos = (ISicresBasicDatosEspecificosCompulsaVO) compulsaVO
				.getDatosEspecificos();

		InvesicresCompulsaDatosEspecificosVO datosEspecificos = new InvesicresCompulsaDatosEspecificosVO();

		datosEspecificos.setSessionID((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_SESSIONID));

		datosEspecificos.setMargen(Float
				.parseFloat((String) basicDatosEspecificos.getValues().get(
						CompulsaKeys.KEY_MARGEN)));
		datosEspecificos.setPositionY(Float
				.parseFloat((String) basicDatosEspecificos.getValues().get(
						CompulsaKeys.KEY_POSITION_Y)));
		datosEspecificos.setFont((String) basicDatosEspecificos.getValues()
				.get(CompulsaKeys.KEY_FONT));
		datosEspecificos.setEncoding((String) basicDatosEspecificos.getValues()
				.get(CompulsaKeys.KEY_ENCODING));
		datosEspecificos.setFontSize(Float
				.parseFloat((String) basicDatosEspecificos.getValues().get(
						CompulsaKeys.KEY_FONT_SIZE)));
		datosEspecificos.setBand(Integer
				.parseInt((String) basicDatosEspecificos.getValues().get(
						CompulsaKeys.KEY_BAND)));
		datosEspecificos.setBandSize(Float
				.parseFloat((String) basicDatosEspecificos.getValues().get(
						CompulsaKeys.KEY_BAND_SIZE)));

		datosEspecificos.setBeginPath((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_PATH_BEGIN));
		datosEspecificos.setTempPath((String) basicDatosEspecificos.getValues()
				.get(CompulsaKeys.KEY_PATH_TEMP));
		datosEspecificos.setFondoPath((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_PATH_FONDO));
		datosEspecificos.setDatosPath((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_PATH_DATOS));

		datosEspecificos.setXadesFormat((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_XADES_FORMAT));
		datosEspecificos.setFuncName((String) basicDatosEspecificos.getValues()
				.get(CompulsaKeys.KEY_FIRMANTE));
		datosEspecificos.setCertificate((String) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_CERTIFICATE));
		datosEspecificos.setHash((String) basicDatosEspecificos.getValues()
				.get(CompulsaKeys.KEY_HASH));

		datosEspecificos.setInputStream((InputStream) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_INPUTSTREAM));
		datosEspecificos.setOutputStream((OutputStream) basicDatosEspecificos
				.getValues().get(CompulsaKeys.KEY_OUTPUTSTREAM));

		compulsaVO.setDatosEspecificos(datosEspecificos);

		return compulsaVO;
	}

}
