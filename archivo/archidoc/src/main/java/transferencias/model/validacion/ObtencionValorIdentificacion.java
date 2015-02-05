package transferencias.model.validacion;

import java.util.List;

import common.db.DBEntityFactory;
import common.db.DbDataSource;

import deposito.db.IUDocEnUiDepositoDbEntity;
import deposito.vos.UDocEnUiDepositoVO;

/**
 * Lleva a cabo la obtencion de la identificacion de una unidad documental
 * 
 */
public class ObtencionValorIdentificacion extends BaseObtencionValor {

	public String obtenerValor(DbDataSource dataSource,
			String idUnidadDocumental) {

		IUDocEnUiDepositoDbEntity dbEntity = DBEntityFactory.getInstance(
				dataSource.getDbFactoryClass()).getUDocEnUiDepositoDbEntity(
				dataSource);
		List signaturasUdoc = dbEntity
				.getPartesUdocByIDUdocEnRelacionEntrega(idUnidadDocumental);
		String identificacionUdoc = null;
		if (signaturasUdoc != null && signaturasUdoc.size() > 0) {
			UDocEnUiDepositoVO primeraSignaturaUdoc = (UDocEnUiDepositoVO) signaturasUdoc
					.get(0);
			identificacionUdoc = primeraSignaturaUdoc.getIdentificacion();
		}
		return createSimpleDataValue(identificacionUdoc);
	}
}
