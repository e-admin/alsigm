package common.vos;

import common.Constants;
import common.util.StringUtils;

public class ConsultaConnectBy extends BaseVO {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String withClause = new String();
	private String sqlClause = new String();

	private String selectClause = new String();
	private String selectCountClause = new String();
	private String fromClause = new String();
	private String whereClause = new String();
	private String groupByClause = new String();

	private String selectSumClause;

	public ConsultaConnectBy(String selectClause, String fromClause,
			String whereClause, String groupByClause, String ordeByClause) {
		super();
		this.selectClause = selectClause;
		this.fromClause = fromClause;
		this.whereClause = whereClause;
		this.groupByClause = groupByClause;
		this.ordeByClause = ordeByClause;
	}

	public ConsultaConnectBy(String selectClause, String fromClause,
			String whereClause) {
		super();
		this.selectClause = selectClause;
		this.fromClause = fromClause;
		this.whereClause = whereClause;
		this.groupByClause = null;
		this.ordeByClause = null;
	}

	public String getSelectClause() {
		return selectClause;
	}

	public void setSelectClause(String selectClause) {
		this.selectClause = selectClause;
	}

	public String getFromClause() {
		return fromClause;
	}

	public void setFromClause(String fromClause) {
		this.fromClause = fromClause;
	}

	public String getWhereClause() {
		if (whereClause == null)
			return Constants.STRING_EMPTY;
		return whereClause;
	}

	public void setWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}

	public String getGroupByClause() {
		if (groupByClause == null)
			return Constants.STRING_EMPTY;
		return groupByClause;
	}

	public void setGroupByClause(String groupByClause) {
		this.groupByClause = groupByClause;
	}

	public String getOrdeByClause() {
		if (ordeByClause == null)
			return Constants.STRING_EMPTY;
		return ordeByClause;
	}

	public void setOrdeByClause(String ordeByClause) {
		this.ordeByClause = ordeByClause;
	}

	private String ordeByClause;

	public ConsultaConnectBy() {
		this.sqlClause = Constants.BLANK;
		this.withClause = Constants.BLANK;

	}

	public ConsultaConnectBy(String sqlClause, String withClause) {
		super();
		this.sqlClause = sqlClause;
		this.withClause = withClause;
	}

	public ConsultaConnectBy(String sqlClause) {
		super();
		this.sqlClause = sqlClause;
		this.withClause = Constants.BLANK;
	}

	/**
	 * @return the withClause
	 */
	public String getWithClause() {
		if (withClause == null)
			this.withClause = Constants.BLANK;
		return withClause;
	}

	/**
	 * @param withClause
	 *            the withClause to set
	 */
	public void setWithClause(String withClause) {
		this.withClause = withClause;
	}

	/**
	 * @return the sqlClause
	 */
	public String getSqlClause() {
		return sqlClause;
	}

	/**
	 * @param sqlClause
	 *            the sqlClause to set
	 */
	public void setSqlClause(String sqlClause) {
		this.sqlClause = sqlClause;
	}

	public String getSqlCompleta() {
		return withClause + Constants.STRING_SPACE + getSqlCompuesta();
	}

	public String getSqlCompuesta() {
		StringBuffer sqlCompleta = new StringBuffer().append(getSelectClause())
				.append(getFromClause()).append(getWhereClause())
				.append(getGroupByClause()).append(getOrdeByClause());

		return sqlCompleta.toString();

	}

	public String getSqlCount() {

		StringBuffer sqlCount = new StringBuffer();

		if (StringUtils.isNotEmpty(getWithClause())) {
			sqlCount.append(getWithClause());
		}

		sqlCount.append(getSelectCountClause()).append(getFromClause())
				.append(getWhereClause()).append(getGroupByClause());

		return sqlCount.toString();
	}

	public String getSqlSum() {

		StringBuffer sqlCount = new StringBuffer();

		if (StringUtils.isNotEmpty(getWithClause())) {
			sqlCount.append(getWithClause());
		}

		sqlCount.append(getSelectCountClause()).append(getFromClause())
				.append(getWhereClause()).append(getGroupByClause());

		return sqlCount.toString();
	}

	public String getSqlCompuestaWith() {
		String sql = getSqlCompuesta();
		if (StringUtils.isEmpty(sql)) {
			sql = getSqlClause();
		}

		return withClause + Constants.STRING_SPACE + sql;
	}

	public void setSelectCountClause(String selectCountClause) {
		this.selectCountClause = selectCountClause;
	}

	public String getSelectCountClause() {
		if (StringUtils.isNotEmpty(selectCountClause)) {
			return selectCountClause;
		} else {
			return selectClause;
		}
	}

	public void setSelectSumClause(String selectSumClause) {
		this.selectSumClause = selectSumClause;
	}

	public String getSelectSumClause() {
		if (StringUtils.isNotEmpty(selectSumClause)) {
			return selectSumClause;
		} else {
			return selectClause;
		}
	}
}
