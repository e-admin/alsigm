package es.ieci.tecdoc.isicres.admin.core.beans.definicion;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

import es.ieci.tecdoc.isicres.admin.core.beans.FiltroImpl;
import es.ieci.tecdoc.isicres.admin.core.exception.ISicresAdminDAOException;
import es.ieci.tecdoc.isicres.admin.core.manager.DBSessionManager;
import es.ieci.tecdoc.isicres.admin.estructura.beans.ArchiveFld;
import es.ieci.tecdoc.isicres.admin.rpadmin.manager.ISicresRPAdminLibroManager;

public class DefinicionFilterFecha extends DefinicionFilterField {

	public static final Logger logger = Logger
			.getLogger(DefinicionFilterFecha.class);
	public static DateFormat formatterSQL = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat formatterDate = new SimpleDateFormat("dd/MM/yyyy");

	public DefinicionFilterFecha(int tipoLibro) {
		super(tipoLibro);
	}

	public FiltroImpl decode(String[] cadenas) {
		ArchiveFld field = ISicresRPAdminLibroManager.getCampo(tipoLibro, cadenas[0]);
		FiltroImpl filtro = new FiltroImpl();
		filtro.setCampo(field.getId());
		filtro.setOperador(DefinicionFilterField.textToIdFilter(cadenas[1]));
		if(cadenas.length>3){
			filtro.setValor(cadenas[3]);
		}else{
			filtro.setValor("");
		}

		if (cadenas.length > 4)
			filtro.setNexo(DefinicionFilterField.textToIdNexo(cadenas[4]));
		else
			filtro.setNexo("");
		return filtro;
	}

	public String encodeFilter(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getName());
		sb.append("|");
		sb.append(DefinicionFilterField.idFilterToTexto(filtro.getOperador()));
		sb.append("|");
		sb.append("0");
		sb.append("|");
		sb.append(filtro.getValor());
		sb.append("|");
		sb.append(DefinicionFilterField.idNexoToTexto(filtro.getNexo()));
		sb.append("|");
		sb.append("$");
		return sb.toString();
	}

	public String encodeWhere(FiltroImpl filtro, String entidad)
			throws ISicresAdminDAOException, SQLException {
		StringBuffer sb = new StringBuffer();

		if (filtro.getOperador() == FiltroImpl.OPERADOR_IGUAL_A) {
			sb.append("(");
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			mayorQueInicioDia(sb, filtro.getValor());
			sb.append(" AND ");
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			menorQueFinDia(sb, filtro.getValor());
			sb.append(")");
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_DISTINTO_DE) {
			sb.append("(");
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			menorQueInicioDia(sb, filtro.getValor());
			sb.append(" OR ");
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			mayorQueFinDia(sb, filtro.getValor());
			sb.append(")");
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_POSTERIOR_A) {
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			mayorQueFinDia(sb, filtro.getValor());
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_ANTERIOR_A) {
			sb.append(ISicresRPAdminLibroManager
					.getCampo(tipoLibro, filtro.getCampo()).getColName());
			menorQueInicioDia(sb, filtro.getValor());
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_EN_LA_SEMANA) {
			sb.append(getInWeekClausule(filtro, entidad));
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_EN_EL_MES) {
			sb.append(getInMonthClausule(filtro, entidad));
		} else if (filtro.getOperador() == FiltroImpl.OPERADOR_EN_EL_AÑO) {
			sb.append(getInYearClausule(filtro, entidad));
		}

		sb.append(" ").append(filtro.getNexo()).append(" ");

		return sb.toString();
	}

	public int[] getOperators() {
		return new int[] { FiltroImpl.OPERADOR_IGUAL_A,
				FiltroImpl.OPERADOR_DISTINTO_DE,
				FiltroImpl.OPERADOR_POSTERIOR_A,
				FiltroImpl.OPERADOR_ANTERIOR_A,
				FiltroImpl.OPERADOR_EN_LA_SEMANA,
				FiltroImpl.OPERADOR_EN_EL_MES, FiltroImpl.OPERADOR_EN_EL_AÑO };
	}

	private void mayorQueInicioDia(StringBuffer sb, String valor) {
		sb.append(" >= {TS '").append(parseDate(valor)).append(" 00:00:00'}");
	}

	private void menorQueFinDia(StringBuffer sb, String valor) {
		sb.append(" <= {TS '").append(parseDate(valor)).append(" 23:59:59'}");
	}

	private void mayorQueFinDia(StringBuffer sb, String valor) {
		sb.append(" > {TS '").append(parseDate(valor)).append(" 23:59:59'}");
	}

	private void menorQueInicioDia(StringBuffer sb, String valor) {
		sb.append(" < {TS '").append(parseDate(valor)).append(" 00:00:00'}");
	}

	private String parseDate(String valor) {
		try {
			return formatterSQL.format(formatterDate.parse(valor));
		} catch (ParseException e) {
			logger.error("Error parseando fecha en filtros", e);
		}
		return "";
	}

	private int getWeek(String valor) {
		try {
			Calendar calendario = GregorianCalendar.getInstance();
			calendario.setTime(formatterDate.parse(valor));
			return calendario.get(Calendar.WEEK_OF_YEAR);
		} catch (ParseException e) {
			logger.error("Error parseando fecha en filtros", e);
		}
		return 0;
	}

	private int getMonth(String valor) {
		try {
			Calendar calendario = GregorianCalendar.getInstance();
			calendario.setTime(formatterDate.parse(valor));
			return calendario.get(Calendar.MONTH) + 1;
		} catch (ParseException e) {
			logger.error("Error parseando fecha en filtros", e);
		}
		return 0;
	}

	private int getYear(String valor) {
		try {
			Calendar calendario = GregorianCalendar.getInstance();
			calendario.setTime(formatterDate.parse(valor));
			return calendario.get(Calendar.YEAR);
		} catch (ParseException e) {
			logger.error("Error parseando fecha en filtros", e);
		}
		return 0;
	}

	private String getInWeekClausule(FiltroImpl filtro, String entidad)
			throws ISicresAdminDAOException, SQLException {
		int dbType = DBSessionManager.getDataBase(entidad);
		String whereClausule = "";

		switch (dbType) {
		case 1:
			whereClausule = getInWeekClausuleSqlServer(filtro);
			break;
		case 2:
			whereClausule = getInWeekClausuleOracle(filtro);
			break;
		case 4:
			whereClausule = getInWeekClausulePostgres(filtro);
			break;
		case 5:
			whereClausule = getInWeekClausuleDB2(filtro);
			break;
		default:
			break;
		}

		return whereClausule;
	}

	private String getInMonthClausule(FiltroImpl filtro, String entidad)
			throws ISicresAdminDAOException, SQLException {
		int dbType = DBSessionManager.getDataBase(entidad);
		String whereClausule = "";

		switch (dbType) {
		case 1:
			whereClausule = getInMonthClausuleSqlServer(filtro);
			break;
		case 2:
			whereClausule = getInMonthClausuleOracle(filtro);
			break;
		case 4:
			whereClausule = getInMonthClausulePostgres(filtro);
			break;
		case 5:
			whereClausule = getInMonthClausuleDB2(filtro);
			break;
		default:
			break;
		}

		return whereClausule;
	}

	private String getInYearClausule(FiltroImpl filtro, String entidad)
			throws ISicresAdminDAOException, SQLException {
		int dbType = DBSessionManager.getDataBase(entidad);
		String whereClausule = "";

		switch (dbType) {
		case 1:
			whereClausule = getInYearClausuleSqlServer(filtro);
			break;
		case 2:
			whereClausule = getInYearClausuleOracle(filtro);
			break;
		case 4:
			whereClausule = getInYearClausulePostgres(filtro);
			break;
		case 5:
			whereClausule = getInYearClausuleDB2(filtro);
			break;
		default:
			break;
		}

		return whereClausule;
	}

	private String getInWeekClausuleSqlServer(FiltroImpl filtro) {
		// semana: (DATEPART(week,Fld2) = 18) AND (DATEPART(year,Fld2) = 2009 )
		StringBuffer sb = new StringBuffer();

		sb.append("(DATEPART(week,");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getWeek(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(DATEPART(year,");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInWeekClausuleOracle(FiltroImpl filtro) {
		// semana: to_char(fld2, 'IW') = 27 AND to_char(fld2, 'YYYY') = 2009
		StringBuffer sb = new StringBuffer();

		sb.append("(to_char(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(",'IW') = ");
		sb.append(getWeek(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(to_char(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(",'YYYY') = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInWeekClausulePostgres(FiltroImpl filtro) {
		// semana: (EXTRACT(WEEK from fld2) = 27) AND (EXTRACT(YEAR from Fld2))
		// = 2009
		StringBuffer sb = new StringBuffer();

		sb.append("(EXTRACT(WEEK from ");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getWeek(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(EXTRACT(YEAR from ");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInWeekClausuleDB2(FiltroImpl filtro) {
		// semana: (WEEK(Fld2) = 16) AND (YEAR(Fld2) = 2009 )
		StringBuffer sb = new StringBuffer();

		sb.append("(WEEK(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getWeek(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(YEAR(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInMonthClausuleSqlServer(FiltroImpl filtro) {
		// semana: (DATEPART(month,Fld2) = 18) AND (DATEPART(year,Fld2) = 2009 )
		StringBuffer sb = new StringBuffer();

		sb.append("(DATEPART(month,");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getMonth(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(DATEPART(year,");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInMonthClausuleOracle(FiltroImpl filtro) {
		// semana: to_char(fld2, 'MM') = 27 AND to_char(fld2, 'YYYY') = 2009
		StringBuffer sb = new StringBuffer();

		sb.append("(to_char(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(",'MM') = ");
		sb.append(getMonth(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(to_char(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(",'YYYY') = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInMonthClausulePostgres(FiltroImpl filtro) {
		// semana: (EXTRACT(MONTH from fld2) = 27) AND (EXTRACT(YEAR from Fld2))
		// = 2009
		StringBuffer sb = new StringBuffer();

		sb.append("(EXTRACT(MONTH from ");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getMonth(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(EXTRACT(YEAR from ");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInMonthClausuleDB2(FiltroImpl filtro) {
		// semana: (MONTH(Fld2) = 16) AND (YEAR(Fld2) = 2009 )
		StringBuffer sb = new StringBuffer();

		sb.append("(MONTH(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getMonth(filtro.getValor()));
		sb.append(") AND ");
		sb.append("(YEAR(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInYearClausuleSqlServer(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();

		sb.append("(DATEPART(year,");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInYearClausuleOracle(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();

		sb.append("(to_char(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(",'YYYY') = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInYearClausulePostgres(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();

		sb.append("(EXTRACT(YEAR from ");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

	private String getInYearClausuleDB2(FiltroImpl filtro) {
		StringBuffer sb = new StringBuffer();

		sb.append("(YEAR(");
		sb.append(ISicresRPAdminLibroManager.getCampo(tipoLibro, filtro.getCampo())
				.getColName());
		sb.append(") = ");
		sb.append(getYear(filtro.getValor()));
		sb.append(" ) ");

		return sb.toString();
	}

}
