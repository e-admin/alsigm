//
// FileName: AxSfEntity.java
//
package com.ieci.tecdoc.common.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.naming.NamingException;

import org.apache.log4j.Logger;

import com.ieci.tecdoc.common.entity.dao.DBEntityDAO;
import com.ieci.tecdoc.common.entity.dao.DBEntityDAOFactory;
import com.ieci.tecdoc.common.isicres.AxPK;
import com.ieci.tecdoc.common.isicres.AxSf;
import com.ieci.tecdoc.common.isicres.AxSfIn;
import com.ieci.tecdoc.common.isicres.AxSfOut;
import com.ieci.tecdoc.common.isicres.AxSfQuery;
import com.ieci.tecdoc.common.isicres.AxSfQueryResults;
import com.ieci.tecdoc.common.isicres.Keys;
import com.ieci.tecdoc.common.keys.ServerKeys;
import com.ieci.tecdoc.common.utils.BBDDUtils;
import com.ieci.tecdoc.common.utils.Repository;

/**
 * @author 79426599
 *
 */

public class AxSfEntity extends AbstractAx implements ServerKeys {

	private static final Logger log = Logger.getLogger(AxSfEntity.class);

	protected static String FDRID_FIELD = "fdrid";

	protected static String FLD1_FIELD = "fld1";

	protected static String AXSF_FINDBY_SENTENCE = "SELECT fdrid FROM {0} WHERE fdrid=?";

	protected static String AXSF_DELETE_SENTENCE = "DELETE FROM {0} WHERE fdrid=?";

	private AxSf axsf = new AxSf();

	private boolean loaded = false;

	public AxSfEntity() {
	}

	protected String getFinalTableName() {
		return "SF";
	}

	public void isNotLoaded() {
		loaded = false;
	}

	public void loadEntityPrimaryKey(AxPK axPK) {
		setPkType(axPK.getType());
		setId(axPK.getId());
		if (axPK.getAxsf() != null) {
			axsf = axPK.getAxsf();
			loaded = true;
		}
	}

	protected void assignLoad(ResultSet rs, String entidad) throws SQLException {
		AxSf axsfWithPrecisions = BBDDUtils.getTableSchemaFromDatabase(pkType,
				entidad);

		String name = null;
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			name = (String) it.next();
			int type = axsfWithPrecisions.getAttributeSQLType(name).intValue();

			if (log.isDebugEnabled()) {
				if (rs.getObject(name) != null) {
					log.debug("AxSfEntity load att name [" + name + "] value ["
							+ rs.getObject(name) + "] class ["
							+ rs.getObject(name).getClass().getName() + "]");
				} else {
					log.debug("AxSfEntity load att name [" + name + "] value ["
							+ rs.getObject(name) + "] class [null]");
				}
			}
			if (type == Types.TIMESTAMP || type == Types.DATE) {
				axsf.addAttributeValue(name, BBDDUtils.getDateFromTimestamp(rs
						.getTimestamp(name)));
			} else {
				axsf.addAttributeValue(name, rs.getObject(name));
			}
		}
	}

	protected void assignStore(PreparedStatement ps, String entidad)
			throws SQLException {
		AxSf axsfWithPrecisions = BBDDUtils.getTableSchemaFromDatabase(pkType,
				entidad);

		String name = null;
		int index = 1;
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			name = (String) it.next();

			if (!name.equals(FDRID_FIELD) && !name.equals(FLD1_FIELD)) {
				int type = axsf.getAttributeSQLType(name).intValue();

				if (log.isDebugEnabled()) {
					log.debug("AxSfEntity assignStore name [" + name
							+ "] value [" + axsf.getAttributeValue(name)
							+ "] sql type [" + type + "] java class ["
							+ axsf.getAttributeClass(name) + "] sql scale ["
							+ axsf.getAttributeSQLScale(name) + "]");
				}

				int scale = 0;
				if (type == Types.TIMESTAMP || type == Types.DATE) {
					if (axsf.getAttributeValue(name) == null) {
						ps.setNull(index++, Types.DATE);
					} else {
						ps.setTimestamp(index++, BBDDUtils
								.getTimestamp((Date) axsf
										.getAttributeValue(name)));
					}
				} else {
					scale = 0;
					if (axsf.getAttributeValue(name) == null) {
						ps.setNull(index++, type);
					} else {
						if (axsfWithPrecisions.getAttributeClass(name).equals(
								String.class)) {
							Integer precision = (Integer) axsfWithPrecisions
									.getPrecisions().get(name);
							// De esta forma se introducen los campos String
							// solamente con su tamaño máximo
							if (axsf.getAttributeValueAsString(name).length() > precision
									.intValue()) {
								ps.setObject(index++, axsf
										.getAttributeValueAsString(name)
										.substring(0, precision.intValue()),
										type, scale);
								// ps.setString(index++,
								// axsf.getAttributeValueAsString(name).substring(0,
								// precision.intValue()));
							} else {
								ps.setObject(index++, axsf
										.getAttributeValue(name), type, scale);
							}
						} else {
							ps.setObject(index++, axsf.getAttributeValue(name),
									type, scale);
						}
					}
				}
			}
		}
	}

	protected void assignStore(PreparedStatement ps, String entidad,
			String dataBaseType) throws SQLException {

		AxSf axsfWithPrecisions = BBDDUtils.getTableSchemaFromDatabase(pkType,
				entidad);

		String name = null;
		int index = 1;
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			name = (String) it.next();

			if (!name.equals(FDRID_FIELD) && !name.equals(FLD1_FIELD)) {
				int type = axsf.getAttributeSQLType(name).intValue();

				if (log.isDebugEnabled()) {
					log.debug("AxSfEntity assignStore name [" + name
							+ "] value [" + axsf.getAttributeValue(name)
							+ "] sql type [" + type + "] java class ["
							+ axsf.getAttributeClass(name) + "] sql scale ["
							+ axsf.getAttributeSQLScale(name) + "]");
				}

				int scale = 0;
				if (type == Types.TIMESTAMP || type == Types.DATE) {
					if (axsf.getAttributeValue(name) == null) {
						ps.setNull(index++, Types.DATE);
					} else {
						ps.setTimestamp(index++, BBDDUtils
								.getTimestamp((Date) axsf
										.getAttributeValue(name)));
					}
				} else {
					scale = 0;
					if (axsf.getAttributeValue(name) == null) {
						ps.setNull(index++, type);
					} else {
						if (axsfWithPrecisions.getAttributeClass(name).equals(
								String.class)) {
							Integer precision = (Integer) axsfWithPrecisions
									.getPrecisions().get(name);
							// De esta forma se introducen los campos String
							// solamente con su tamaño máximo
							if (axsf.getAttributeValueAsString(name).length() > precision
									.intValue()) {
								ps.setObject(index++, axsf
										.getAttributeValueAsString(name)
										.substring(0, precision.intValue()),
										type, scale);
								// ps.setString(index++,
								// axsf.getAttributeValueAsString(name).substring(0,
								// precision.intValue()));
							} else {
								ps.setObject(index++, axsf
										.getAttributeValue(name), type, scale);
							}
						} else {
							if (DBEntityDAO.SQLSERVER_TYPE
									.equalsIgnoreCase(dataBaseType)
									&& ((Types.REAL == type) || (Types.DOUBLE == type))) {
								scale = 2;
							}
							ps.setObject(index++, axsf.getAttributeValue(name),
									type, scale);
						}
					}
				}
			}
		}
	}

	public void load(AxPK axPK, String entidad) throws Exception {
		loadEntityPrimaryKey(axPK);

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity load: " + getPrimaryKey() + " .." + axsf);
		}

		try {
			loadFromDatabase(entidad);
		} catch (Exception e) {
			log.fatal("load PK [" + getPrimaryKey() + "]", e);
			throw new Exception(e.getMessage());
		}

		StringBuffer sentence = new StringBuffer();
		sentence.append("SELECT ");
		sentence.append(getAttributesSenteceNames());
		sentence.append(" FROM {0} WHERE fdrid =?");

		load(sentence.toString(), axPK, entidad);
	}

	public void store(String entidad) throws Exception {
		// loadEntityContextPrimaryKey();

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity store: " + getPrimaryKey() + " .." + axsf);
		}

		if (axsf.isModified()) {
			try {
				loadFromDatabase(entidad);
			} catch (Exception e) {
				log.fatal("store. PK [" + getPrimaryKey() + "]", e);
				throw new Exception(e.getMessage());
			}

			StringBuffer sentence = new StringBuffer();
			sentence.append("UPDATE {0} SET ");
			sentence.append(getAttributesSentenceUpdate());
			sentence.append(" WHERE fdrid=?");

			store(sentence.toString(), getAttributesSentenceUpdateCount() + 1,
					entidad);
		}
	}

	public void store(String entidad, String dataBaseType) throws Exception {
		// loadEntityContextPrimaryKey();

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity store: " + getPrimaryKey() + " .." + axsf);
		}

		if (axsf.isModified()) {
			try {
				loadFromDatabase(entidad);
			} catch (Exception e) {
				log.fatal("store. PK [" + getPrimaryKey() + "]", e);
				throw new Exception(e.getMessage());
			}

			StringBuffer sentence = new StringBuffer();
			sentence.append("UPDATE {0} SET ");
			sentence.append(getAttributesSentenceUpdate());
			sentence.append(" WHERE fdrid=?");

			store(sentence.toString(), getAttributesSentenceUpdateCount() + 1,
					entidad, dataBaseType);
		}
	}

	public AxPK create(String type, AxSf axSfp, String entidad)
			throws Exception {
		setPkType(type);

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity create: " + getPrimaryKey() + " .." + axsf);
		}

		AxSf axsfWithPrecisions = null;
		try {
			loadFromDatabase(entidad);
			axsfWithPrecisions = BBDDUtils.getTableSchemaFromDatabase(pkType,
					entidad);
		} catch (Exception e) {
			log.fatal("create. PK [" + getPrimaryKey() + "]", e);
			throw new Exception(e.getMessage());
		}

		axsf.getAttributesNames().clear();
		axsf.getAttributesValues().clear();

		String name = null;
		Object value = null;
		for (Iterator it = axSfp.getAttributesNames().iterator(); it.hasNext();) {
			name = (String) it.next();
			value = axSfp.getAttributeValue(name);
			if (name.equals(FDRID_FIELD)) {
				setId(((Integer) value).intValue());
			}
			if (value != null) {
				axsf.setAttributeValue(name, value);
			}
		}

		StringBuffer sentence = new StringBuffer();
		sentence.append("INSERT INTO {0} (");
		sentence.append(getAttributesSenteceNames());
		sentence.append(") VALUES (");
		sentence.append(getAttributesSentenceQuestionTag());
		sentence.append(")");

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence.toString()));

			int index = 1;
			for (Iterator it = axsf.getAttributesNames().iterator(); it
					.hasNext();) {
				name = (String) it.next();
				if (axsf.getAttributeValue(name) != null) {
					int t = axsf.getAttributeSQLType(name).intValue();
					int scale = 0;

					if (log.isDebugEnabled()) {
						log.debug("AxSfEntity create name [" + name
								+ "] value [" + axsf.getAttributeValue(name)
								+ "] sql type [" + t + "] java class ["
								+ axsf.getAttributeClass(name)
								+ "] sql scale ["
								+ axsf.getAttributeSQLScale(name) + "]");
					}

					if (t == Types.TIMESTAMP || t == Types.DATE) {
						if (axsf.getAttributeValue(name) != null
								&& axsf.getAttributeValue(name) instanceof Date) {
							ps.setTimestamp(index++, BBDDUtils
									.getTimestamp((Date) axsf
											.getAttributeValue(name)));
						} else if (axsf.getAttributeValue(name) != null
								&& axsf.getAttributeValue(name) instanceof Timestamp) {
							ps.setTimestamp(index++, BBDDUtils
									.getTimestamp((Timestamp) axsf
											.getAttributeValue(name)));
						} else {
							ps.setTimestamp(index++, null);
						}
					} else {
						scale = 0;
						if (axsf.getAttributeValue(name) == null) {
							ps.setNull(index++, t);
						} else {
							if (axsfWithPrecisions.getAttributeClass(name)
									.equals(String.class)) {
								Integer precision = (Integer) axsfWithPrecisions
										.getPrecisions().get(name);
								// De esta forma se introducen los campos String
								// solamente con su tamaño máximo
								if (axsf.getAttributeValueAsString(name)
										.length() > precision.intValue()) {
									ps
											.setObject(
													index++,
													axsf
															.getAttributeValueAsString(
																	name)
															.substring(
																	0,
																	precision
																			.intValue()),
													t, scale);
									// ps.setString(index++,
									// axsf.getAttributeValueAsString(name).substring(0,
									// precision.intValue()));
								} else {
									ps.setObject(index++, axsf
											.getAttributeValue(name), t, scale);
								}
							} else {
								ps.setObject(index++, axsf
										.getAttributeValue(name), t, scale);
							}
						}
					}
				}
			}

			ps.executeUpdate();

			return getPrimaryKey();
		} catch (SQLException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new SQLException();
		} catch (NamingException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new NamingException();
		} catch (Throwable ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			log.error("excepción no contemplada................. [" + name
					+ "]");
			log.error("axsf => " + axsf);
			log.error("*************************************");
			throw new Exception();
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxPK create(String type, AxSf axSfp, String entidad,
			String dataBaseType) throws Exception {
		setPkType(type);

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity create: " + getPrimaryKey() + " .." + axsf);
		}

		AxSf axsfWithPrecisions = null;
		try {
			loadFromDatabase(entidad);
			axsfWithPrecisions = BBDDUtils.getTableSchemaFromDatabase(pkType,
					entidad);
		} catch (Exception e) {
			log.fatal("create. PK [" + getPrimaryKey() + "]", e);
			throw new Exception(e.getMessage());
		}

		axsf.getAttributesNames().clear();
		axsf.getAttributesValues().clear();

		String name = null;
		Object value = null;
		for (Iterator it = axSfp.getAttributesNames().iterator(); it.hasNext();) {
			name = (String) it.next();
			value = axSfp.getAttributeValue(name);
			if (name.equals(FDRID_FIELD)) {
				setId(((Integer) value).intValue());
			}
			if (value != null) {
				axsf.setAttributeValue(name, value);
			}
		}

		StringBuffer sentence = new StringBuffer();
		sentence.append("INSERT INTO {0} (");
		sentence.append(getAttributesSenteceNames());
		sentence.append(") VALUES (");
		sentence.append(getAttributesSentenceQuestionTag());
		sentence.append(")");

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(sentence.toString()));

			int index = 1;
			for (Iterator it = axsf.getAttributesNames().iterator(); it
					.hasNext();) {
				name = (String) it.next();
				if (axsf.getAttributeValue(name) != null) {
					int attributeType = axsf.getAttributeSQLType(name)
							.intValue();
					int scale = 0;

					if (log.isDebugEnabled()) {
						log.debug("AxSfEntity create name [" + name
								+ "] value [" + axsf.getAttributeValue(name)
								+ "] sql type [" + attributeType
								+ "] java class ["
								+ axsf.getAttributeClass(name)
								+ "] sql scale ["
								+ axsf.getAttributeSQLScale(name) + "]");
					}

					if (attributeType == Types.TIMESTAMP
							|| attributeType == Types.DATE) {
						if (axsf.getAttributeValue(name) != null
								&& axsf.getAttributeValue(name) instanceof Date) {
							ps.setTimestamp(index++, BBDDUtils
									.getTimestamp((Date) axsf
											.getAttributeValue(name)));
						} else if (axsf.getAttributeValue(name) != null
								&& axsf.getAttributeValue(name) instanceof Timestamp) {
							ps.setTimestamp(index++, BBDDUtils
									.getTimestamp((Timestamp) axsf
											.getAttributeValue(name)));
						} else {
							ps.setTimestamp(index++, null);
						}
					} else {
						scale = 0;
						if (axsf.getAttributeValue(name) == null) {
							ps.setNull(index++, attributeType);
						} else {
							if (axsfWithPrecisions.getAttributeClass(name)
									.equals(String.class)) {
								Integer precision = (Integer) axsfWithPrecisions
										.getPrecisions().get(name);
								// De esta forma se introducen los campos String
								// solamente con su tamaño máximo
								if (axsf.getAttributeValueAsString(name)
										.length() > precision.intValue()) {
									String attributeValue = axsf
											.getAttributeValueAsString(name)
											.substring(0, precision.intValue());
									ps.setObject(index++, attributeValue,
											attributeType, scale);
									// ps.setString(index++,
									// axsf.getAttributeValueAsString(name).substring(0,
									// precision.intValue()));
								} else {
									ps.setObject(index++, axsf
											.getAttributeValue(name),
											attributeType, scale);
								}
							} else {
								if (DBEntityDAO.SQLSERVER_TYPE
										.equalsIgnoreCase(dataBaseType)
										&& ((Types.REAL == attributeType) || (Types.DOUBLE == attributeType))) {
									scale = 2;
								}
								ps.setObject(index++, axsf
										.getAttributeValue(name),
										attributeType, scale);
							}
						}
					}
				}
			}

			ps.executeUpdate();

			return getPrimaryKey();
		} catch (SQLException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new SQLException();
		} catch (NamingException ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			throw new NamingException();
		} catch (Throwable ex) {
			log
					.error("Error en método create.PK [" + getPrimaryKey()
							+ "]", ex);
			log.error("excepción no contemplada................. [" + name
					+ "]");
			log.error("axsf => " + axsf);
			log.error("*************************************");
			throw new Exception();
		} finally {
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}
	}

	public AxPK findByPrimaryKey(AxPK pk, String entidad) throws Exception {
		setPkType(pk.getType());
		setId(pk.getId());

		if (log.isDebugEnabled()) {
			log.debug("AxSfEntity findByPrimaryKey: " + getPrimaryKey() + " .."
					+ axsf);
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(getSentence(AXSF_FINDBY_SENTENCE));
			ps.setInt(1, getId());
			rs = ps.executeQuery();

			if (!rs.next()) {
				throw new Exception();
			}
		} catch (SQLException ex) {
			log.fatal("findByPrimaryKey. PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("findByPrimaryKey. PK [" + getPrimaryKey() + "]", ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		AxPK result = new AxPK(pk.getType(), pk.getId());
		// result.setAxsf(pk.getAxsf());

		return result;
	}

	public List findLastRegisterForUser(AxSf axsfP, Integer bookID,
			Integer userId, String filter, String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List results = new ArrayList();
		String sentence = null;

		String type = bookID.toString();
		String tableName = getTableName(type);
		int lastFdrid = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.lastRegister(null, userId, bookID, entidad);

		try {
			if (lastFdrid == 0) {
				con = BBDDUtils.getConnection(entidad);
				sentence = DBEntityDAOFactory.getCurrentDBEntityDAO()
						.findAxSFLastForUserSENTENCE(tableName, filter, true);
				ps = con.prepareStatement(sentence);

				rs = ps.executeQuery();
				int fdrid = 0;
				if (rs.next()) {
					fdrid = rs.getInt(FDRID_FIELD);
					if (fdrid != 0) {
						results.add(new AxPK(type, fdrid, null));// (AxSf)
					}
					// axsfP.clone()));
				}
			} else {
				results.add(new AxPK(type, lastFdrid, null));// (AxSf)
			}
		} catch (SQLException ex) {
			log.fatal(
					"ejbFindLastRegisterForUser de un BMP. PK \n " + sentence,
					ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal(
					"ejbFindLastRegisterForUser de un BMP. PK \n " + sentence,
					ex);
			throw new Exception(ex);
		} finally {
			if (lastFdrid == 0) {
				BBDDUtils.close(rs);
				BBDDUtils.close(ps);
				BBDDUtils.close(con);
			}
		}

		return results;
	}

	public Collection findByRowAxSfQuery(AxSfQuery axsfQuery, AxSf axsfP,
			AxSfQueryResults axsfQueryResults, int row, String filter,
			String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map results = new TreeMap();

		int size = 0;
		int end = row + axsfQueryResults.getPageSize() - 1;
		int begin = row;

		begin = adjustBegin(begin, axsfQueryResults);
		end = adjustEnd(end, axsfQueryResults);

		String type = axsfQuery.getBookId().toString();
		String tableName = getTableName(type);
		String sentence = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.findAxSFAllSENTENCE(axsfP, tableName, axsfQuery, begin, end,
						filter, true);

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(sentence,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			DBEntityDAOFactory.getCurrentDBEntityDAO()
					.assignAxSFPreparedStatement(axsfQuery, axsfP, ps);

			rs = ps.executeQuery();
			rs.absolute(begin);
			int fdrid = 0;
			int index = begin;
			rs.previous();
			while (rs.next() && index <= end) {
				fdrid = rs.getInt(FDRID_FIELD);
				results.put(new Integer(size++), new AxPK(type, fdrid, null));// (AxSf)
				// axsfP.clone()));
				index++;
			}
		} catch (SQLException ex) {
			log.fatal("findByAxSfQuery. Sentence [" + sentence + "] PK \n "
					+ axsfQuery, ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("findByAxSfQuery. Sentence [" + sentence + "] PK \n "
					+ axsfQuery, ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		adjustAxSFQueryResults(axsfQueryResults, size, begin, end);

		return results.values();
	}

	public Collection findByAxSfQuery(AxSfQuery axsfQuery, AxSf axsfP,
			AxSfQueryResults axsfQueryResults, String navigationType,
			String filter, String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map results = new TreeMap();

		int size = 0;
		int begin = 0;
		int end = 0;

		if (navigationType.equals(Keys.QUERY_FIRST_PAGE)) {
			begin = 1;
			end = axsfQueryResults.getPageSize();
		} else if (navigationType.equals(Keys.QUERY_LAST_PAGE)) {
			begin = axsfQueryResults.getTotalQuerySize()
					- axsfQueryResults.getPageSize() + 1;
			end = axsfQueryResults.getTotalQuerySize();
		} else if (navigationType.equals(Keys.QUERY_NEXT_PAGE)) {
			begin = axsfQueryResults.getCurrentLastRow() + 1;
			end = axsfQueryResults.getCurrentLastRow()
					+ axsfQueryResults.getPageSize();
		} else if (navigationType.equals(Keys.QUERY_PREVIOUS_PAGE)) {
			begin = axsfQueryResults.getCurrentFirstRow()
					- axsfQueryResults.getPageSize();
			end = axsfQueryResults.getCurrentFirstRow() - 1;
		} else if(navigationType.equals(Keys.QUERY_ALL)){
			begin = 1;
			end = axsfQueryResults.getTotalQuerySize();
		}

		begin = adjustBegin(begin, axsfQueryResults);
		end = adjustEnd(end, axsfQueryResults);

		String type = axsfQuery.getBookId().toString();
		String tableName = getTableName(type);
		String sentence = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.findAxSFAllSENTENCE(axsfP, tableName, axsfQuery, begin, end,
						filter, true);

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(sentence,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			DBEntityDAOFactory.getCurrentDBEntityDAO()
					.assignAxSFPreparedStatement(axsfQuery, axsfP, ps);

			rs = ps.executeQuery();
			rs.setFetchSize(axsfQueryResults.getPageSize());
			rs.absolute(begin);
			int fdrid = 0;
			int index = begin;
			rs.previous();
			while (rs.next() && index <= end) {
				fdrid = rs.getInt(FDRID_FIELD);
				results.put(new Integer(size++), new AxPK(type, fdrid, null));// (AxSf)
				// axsfP.clone()));
				index++;
			}
		} catch (SQLException ex) {
			log.fatal("findByAxSfQuery. Sentence [" + sentence + "] PK \n "
					+ axsfQuery, ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("findByAxSfQuery. Sentence [" + sentence + "] PK \n "
					+ axsfQuery, ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		adjustAxSFQueryResults(axsfQueryResults, size, begin, end);

		return results.values();
	}

	public int calculateQuerySize(AxSfQuery axsfQuery, AxSf axsfP,
			String filter, String entidad) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result = 0;

		String type = axsfQuery.getBookId().toString();
		String tableName = getTableName(type);
		String sentence = DBEntityDAOFactory.getCurrentDBEntityDAO()
				.findAxSFAllSizeSENTENCE(axsfP, tableName, axsfQuery, filter,
						false);

		try {
			con = BBDDUtils.getConnection(entidad);
			ps = con.prepareStatement(sentence);

			DBEntityDAOFactory.getCurrentDBEntityDAO()
					.assignAxSFPreparedStatement(axsfQuery, axsfP, ps);

			rs = ps.executeQuery();
			rs.next();
			result = rs.getInt(1);
		} catch (SQLException ex) {
			log.fatal("findByAxSfQuery de un BMP. Sentence [" + sentence
					+ "] PK \n " + axsfQuery, ex);
			throw new Exception(ex);
		} catch (NamingException ex) {
			log.fatal("findByAxSfQuery de un BMP. Sentence [" + sentence
					+ "] PK \n " + axsfQuery, ex);
			throw new Exception(ex);
		} finally {
			BBDDUtils.close(rs);
			BBDDUtils.close(ps);
			BBDDUtils.close(con);
		}

		return result;
	}

	public void loadFromDatabase(String entidad) throws NamingException,
			SQLException, ClassNotFoundException {
		if (!loaded) {
			axsf = BBDDUtils.getTableSchemaFromDatabase(getPkType(), entidad);
			loaded = true;
		} else if (axsf.getAttributesSQLTypes() == null) {
			axsf = BBDDUtils.getTableSchemaFromDatabase(getPkType(), entidad);
			loaded = true;
		} else if (axsf.getAttributesSQLTypes().isEmpty()) {
			axsf = BBDDUtils.getTableSchemaFromDatabase(getPkType(), entidad);
			loaded = true;
		}
	}

	public boolean checkAxSF(Integer bookID, String registerNumber,
			String entidad) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Connection connection = null;
		boolean exist = false;
		try {
			connection = BBDDUtils.getConnection(entidad);
			statement = connection.prepareStatement(getCheckAxSFSentence(bookID
					.toString()));
			statement.setString(1, registerNumber);
			resultSet = statement.executeQuery();

			exist = false;
			while (resultSet.next()) {
				resultSet.getInt(1);
				if (resultSet.wasNull()) {
					exist = false;
				} else {
					exist = true;
				}
			}
		} catch (SQLException e) {
		} catch (Throwable e) {
		} finally {
			BBDDUtils.close(resultSet);
			BBDDUtils.close(statement);
			BBDDUtils.close(connection);
		}
		return exist;

	}

	public String getAttributesSenteceNames() {
		StringBuffer buffer = new StringBuffer();

		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			buffer.append(it.next().toString());
			buffer.append(",");
		}

		return buffer.substring(0, buffer.length() - 1);
	}

	public String getAttributesSentenceQuestionTag() {
		StringBuffer buffer = new StringBuffer();

		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			it.next();
			buffer.append("?,");
		}

		return buffer.substring(0, buffer.length() - 1);
	}

	public int getAttributesSentenceUpdateCount() {
		int count = 0;
		String att = null;
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			att = it.next().toString();
			if (!att.equals(FDRID_FIELD) && !att.equals(FLD1_FIELD)) {// &&
				// axsf.getAttributeValue(att)
				// !=
				// null) {
				count++;
			}
		}
		return count;
	}

	public String getAttributesSentenceUpdate() {
		StringBuffer buffer = new StringBuffer();

		String att = null;
		for (Iterator it = axsf.getAttributesNames().iterator(); it.hasNext();) {
			att = it.next().toString();
			if (!att.equals(FDRID_FIELD) && !att.equals(FLD1_FIELD)) { // &&
				// axsf.getAttributeValue(att)
				// !=
				// null)
				// {
				buffer.append(att);
				buffer.append("=?,");
			}
		}

		return buffer.substring(0, buffer.length() - 1);
	}

	public AxSf getAxSf(String entidad) {
		AxSf newAxsf = null;

		if (Repository.getInstance(entidad).isInBook(getPkType())
				.booleanValue()) {
			newAxsf = new AxSfIn();
			if (axsf instanceof AxSfIn) {
				((AxSfIn) newAxsf).setFld13(((AxSfIn) axsf).getFld13());
				((AxSfIn) newAxsf).setFld16(((AxSfIn) axsf).getFld16());
			}
		} else {
			newAxsf = new AxSfOut();
			if (axsf instanceof AxSfOut) {
				((AxSfOut) newAxsf).setFld12(((AxSfOut) axsf).getFld12());
			}
		}

		newAxsf.setFld5(axsf.getFld5());
		newAxsf.setFld7(axsf.getFld7());
		newAxsf.setFld8(axsf.getFld8());
		newAxsf.setAttributesNames(new ArrayList(axsf.getAttributesNames()));
		newAxsf.setAttributesClasses(new HashMap(axsf.getAttributesClasses()));
		newAxsf.setAttributesValues(new HashMap(axsf.getAttributesValues()));

		return newAxsf;
	}

	public AxSf getAxSf(Integer bookID, String entidad) {
		AxSf newAxsf = null;

		if (Repository.getInstance(entidad).isInBook(bookID).booleanValue()) {
			newAxsf = new AxSfIn();
			((AxSfIn) newAxsf).setFld13(((AxSfIn) axsf).getFld13());
			((AxSfIn) newAxsf).setFld16(((AxSfIn) axsf).getFld16());
		} else {
			newAxsf = new AxSfOut();
			((AxSfOut) newAxsf).setFld12(((AxSfOut) axsf).getFld12());
		}

		newAxsf.setFld5(axsf.getFld5());
		newAxsf.setFld7(axsf.getFld7());
		newAxsf.setFld8(axsf.getFld8());
		newAxsf.setAttributesNames(new ArrayList(axsf.getAttributesNames()));
		newAxsf.setAttributesClasses(new HashMap(axsf.getAttributesClasses()));
		newAxsf.setAttributesValues(new HashMap(axsf.getAttributesValues()));

		return newAxsf;
	}

	public void setAxSf(AxSf axsfOld) {
		axsf.setAttributesNames(new ArrayList(axsfOld.getAttributesNames()));
		axsf.setAttributesClasses(new HashMap(axsfOld.getAttributesClasses()));
		axsf.setAttributesValues(new HashMap(axsfOld.getAttributesValues()));
		axsf.setFld5(axsfOld.getFld5());
		axsf.setFld7(axsfOld.getFld7());
		axsf.setFld8(axsfOld.getFld8());
	}

	/***************************************************************************
	 * Private methods
	 **************************************************************************/

	private void adjustAxSFQueryResults(AxSfQueryResults axsfQueryResults,
			int size, int begin, int end) {
		axsfQueryResults.setCurrentResultsSize(size);
		axsfQueryResults.setCurrentFirstRow(begin);
		if (size == axsfQueryResults.getPageSize()) {
			axsfQueryResults.setCurrentLastRow(end);
		} else {
			axsfQueryResults.setCurrentLastRow(begin + size - 1);
		}
	}

	private int adjustBegin(int begin, AxSfQueryResults axsfQueryResults) {
		if (begin <= 0) {
			return 1;
		}
		if (begin > axsfQueryResults.getTotalQuerySize()) {
			return axsfQueryResults.getTotalQuerySize();
		}
		return begin;
	}

	private int adjustEnd(int end, AxSfQueryResults axsfQueryResults) {
		if (end <= 0) {
			return 1;
		}
		if (end > axsfQueryResults.getTotalQuerySize()) {
			return axsfQueryResults.getTotalQuerySize();
		}
		if (end < axsfQueryResults.getPageSize()) {
			return axsfQueryResults.getPageSize();
		}
		return end;
	}

	private static String getCheckAxSFSentence(String bookId) {
		String stat = "SELECT FDRID FROM " + "A" + bookId + "SF"
				+ " WHERE FLD1=?";
		return stat;
	}


}