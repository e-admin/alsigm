package es.ieci.tecdoc.isicres.api.business.dao.legacy.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.utils.BBDDUtils;

import es.ieci.tecdoc.isicres.api.business.dao.ReportsDAO;
import es.ieci.tecdoc.isicres.api.business.vo.ReportVO;
import es.ieci.tecdoc.isicres.api.business.vo.UsuarioVO;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoLibroEnum;
import es.ieci.tecdoc.isicres.api.business.vo.enums.TipoPlantillaInformeEnum;

public class ReportsDAOImpl implements ReportsDAO {

	public ReportVO getReportInfo(UsuarioVO usuario, String reportId) {
		Statement statement = null;
		Connection connection = null;
		ResultSet resultSet = null;
		ReportVO report = null;

		StringBuffer query = new StringBuffer(
				"SELECT id,report,description,type_report,type_arch,all_arch,all_ofics,all_perfs FROM SCR_REPORTS WHERE ID=")
				.append(reportId);
		try {
			connection = BBDDUtils.getConnection(usuario
					.getConfiguracionUsuario().getIdEntidad());
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query.toString());
			if (resultSet.next()) {
				report = new ReportVO();

				report.setId(resultSet.getString(1));
				report.setName(resultSet.getString(2));
				report.setDescription(resultSet.getString(3));
				report.setReportType(TipoPlantillaInformeEnum.getEnum(resultSet
						.getInt(4)));
				report.setBookType(TipoLibroEnum.getEnum(resultSet.getInt(5)));
				report.setCanBeUsedByAllBooks(resultSet.getBoolean(6));
				report.setCanBeUsedByAllOffices(resultSet.getBoolean(7));
				report.setCanBeUsedByAllProfiles(resultSet.getBoolean(8));
			}
		} catch (Exception e) {
			StringBuffer sb = new StringBuffer(
					"No se ha podido comprobar la existencia del informe con identificador [")
					.append(reportId).append("]");
			logger.warn(sb.toString(), e);
		} finally {
			BBDDUtils.close(statement);
			BBDDUtils.close(resultSet);
			BBDDUtils.close(connection);
		}

		return report;
	}

	// Members
	protected static final Logger logger = Logger
			.getLogger(ReportsDAOImpl.class);
}
