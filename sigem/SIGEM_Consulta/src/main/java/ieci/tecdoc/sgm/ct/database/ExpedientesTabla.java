/**
 * @author Javier Septién Arceredillo
 *
 * Fecha de Creación: 11-may-2007
 */

package ieci.tecdoc.sgm.ct.database;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import ieci.tecdoc.sgm.base.datetime.DateTimeUtil;
import ieci.tecdoc.sgm.base.dbex.DbConnection;
import ieci.tecdoc.sgm.base.dbex.DbUtil;
import ieci.tecdoc.sgm.ct.database.datatypes.ExpedienteImpl;


/**
 * Gestiona el acceso (inserciones, modificaciones, etc.) a la tabla de
 * expedientes.
 *
 */
public class ExpedientesTabla extends ExpedienteImpl {

	private static final String NOMBRE_TABLA = "SGM_CTINFOEXP";

	private static final String CN_NUMERO = "CNUMEXP";

	private static final String CN_PROCEDIMIENTO = "CPROC";

	private static final String CN_FECHAINICIO = "CFHINICIO";

	private static final String CN_NUMEROREGISTRO = "CNUMREGINI";

	private static final String CN_FECHAREGISTRO = "CFHREGINI";

	private static final String CN_INFORMACIONAUXILIAR = "CINFOAUX";

	private static final String CN_APORTACION = "CAPORTACION";

	private static final String CN_CODIGOPRESENTACION = "CCODPRES";

	private static final String CN_ESTADO = "ESTADO";

	private static final String NOMBRES_COLUMNAS = CN_NUMERO + "," + CN_PROCEDIMIENTO
			+ "," + CN_FECHAINICIO + "," + CN_NUMEROREGISTRO + "," + CN_FECHAREGISTRO
			+ "," + CN_INFORMACIONAUXILIAR + "," + CN_APORTACION + "," + CN_CODIGOPRESENTACION
			+ "," + CN_ESTADO;

	private static final String NOMBRES_COLUMNAS_CON_ALIAS = "B." + CN_NUMERO + ",B." + CN_PROCEDIMIENTO
	+ ",B." + CN_FECHAINICIO + ",B." + CN_NUMEROREGISTRO + ",B." + CN_FECHAREGISTRO
	+ ",B." + CN_INFORMACIONAUXILIAR + ",B." + CN_APORTACION + ",B." + CN_CODIGOPRESENTACION
	+ ",B." + CN_ESTADO;

	/**
	 * Constructor de la clase ExpedientesTable
	 */
	public ExpedientesTabla() {
	}

	/**
	 * Devuelve el nombre de la tabla
	 *
	 * @return String Nombre de la tabla
	 */
	public String getNombreTabla() {

		return NOMBRE_TABLA;
	}

	/**
	 * Devuelve los nombres de las columnas
	 *
	 * @return String Nombres de las columnas
	 */
	public String getNombresColumnas() {

		return NOMBRES_COLUMNAS;
	}

	/**
	 * Devuelve los nombres de las columnas con un prefijo de la forma:
	 * 'A.Nombrecolumna'
	 *
	 * @return String Nombres de las columnas
	 */
	public String getNombresColumnasConAlias() {

		return NOMBRES_COLUMNAS_CON_ALIAS;
	}

	/**
	 * Devuelve el nombre de la columna Numero
	 *
	 * @return String Nombre de la columna Numero
	 */
	public String getNumeroColumnName() {
		return CN_NUMERO;
	}

	/**
	 * Devuelve el nombre de la columA Procedimiento
	 *
	 * @return String Nombre de la columna Procedimiento
	 */
	public String getNombreColumnaProcedimiento() {
		return CN_PROCEDIMIENTO;
	}

	/**
	 * Devuelve el nombre de la columna FechaInicio
	 *
	 * @return String Nombre de la columna FechaInicio
	 */
	public String getNombreColumnaFechaInicio() {
		return CN_FECHAINICIO;
	}

	/**
	 * Devuelve el nombre de la columna NumeroRegistro
	 *
	 * @return String Nombre de la columna NumeroRegistro
	 */
	public String getNombreColumnaNumeroRegistro() {
		return CN_NUMEROREGISTRO;
	}

	/**
	 * Devuelve el nombre de la columna InformacionAuxiliar
	 *
	 * @return String Nombre de la columna InformacionAuxiliar
	 */
	public String getNombreColumnaInformacionAuxiliar() {
		return CN_INFORMACIONAUXILIAR;
	}

	/**
	 * Devuelve el nombre de la columna FechaRegistro
	 *
	 * @return String Nombre de la columna FechaRegistro
	 */
	public String getNombreColumnaFechaRegistro() {
		return CN_FECHAREGISTRO;
	}

	/**
	 * Devuelve el nombre de la columna Aportacion
	 *
	 * @return String Nombre de la columna Aportacion
	 */
	public String getNombreColumnaAportacion() {
		return CN_APORTACION;
	}

	/**
	 * Devuelve el nombre de la columna Estado
	 *
	 * @return String Nombre de la columna Estado
	 */
	public String getNombreColumnaEstado() {
		return CN_ESTADO;
	}

	/**
	 * Devuelve el nombre de la columna CodigoPresentacion
	 *
	 * @return String Nombre de la columna CodigoPresentacion
	 */
	public String getNombreColumnaCodigoPresentacion() {
		return CN_CODIGOPRESENTACION;
	}

	/**
	 * Devuelve la clausula de consulta por el numero de expediente
	 *
	 * @param numero
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaPorNumero(String numero) {
		String clausula;

		clausula = "WHERE " + CN_NUMERO + " = '" + DbUtil.replaceQuotes(numero) + "'";

		return clausula;
	}

	/**
	 * Devuelve la clausula mixta de consulta por el numero de expediente
	 *
	 * @param numero
	 *            Valor del campo numero de expediente
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaMixtaPorNumero(String numero) {

		String clausula;

		clausula = " AND B." + CN_NUMERO + " = '" + DbUtil.replaceQuotes(numero) + "'";

		return clausula;
	}

	/**
	 * Devuelve la clausula de ordenacion por fecha cuando se
	 * compone una sentencia de busqueda SQL contra la tabla de Expedientes
	 *
	 * @return String Clausula de ordenacion por fecha
	 */

	public String getOrdenacionPorFechaInicio() {
		String clausula;

		clausula = " ORDER BY " + CN_FECHAINICIO;

		return clausula;
	}


	/**
	 * Devuelve la clausula de consulta cuando la fecha de inicio del expediente
	 * esta comprendida entre las dos fechas que entran como parametro
	 *
	 * @param fechaDesde Fecha desde la que se busca.
	 * @param fechaHasta Fecha hasta que se busca.
	 *
	 * @return String Clausula de consulta entre fechas
	 */

	public String getClausulaPorFechas(String fechaDesde, String fechaHasta, DbConnection dbConn) {
		 Date dateDesde;
		 Date dateHasta;
		 String clausula;
	  try{
	     dateDesde = DateTimeUtil.getDate(fechaDesde, "yyyy-MM-dd");
	     dateHasta = DateTimeUtil.getDate(fechaHasta, "yyyy-MM-dd");

		 clausula = " AND " + CN_FECHAINICIO + " >= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateDesde, true)+ " AND " + CN_FECHAINICIO + " <= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateHasta, true) + "";
	  }
	  catch (Exception e){
		  clausula = " AND " + CN_FECHAINICIO + " >= '" + DbUtil.replaceQuotes(fechaDesde) + "' AND " + CN_FECHAINICIO + " <= '" + DbUtil.replaceQuotes(fechaHasta) + "'";
	  }
	  return clausula;
	}

	/**
	 * Devuelve la clausula de consulta cuando la fecha de inicio es igual
	 * a la fecha de busqueda
	 *
	 * @param fecha Fecha de Busqueda
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorFecha(String fecha, DbConnection dbConn) {
		 Date date;

		 String clausula;
	  try{
	     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
	     clausula = " AND " + CN_FECHAINICIO + " = " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

	  }
	  catch (Exception e){
		  clausula = " AND " + CN_FECHAINICIO + " = '" + DbUtil.replaceQuotes(fecha) + "'";
	  }
	  return clausula;

	}

	/**
	 * Devuelve la clausula de consulta cuando la fecha de inicio de expediente es posterior
	 * que la fecha de busqueda
	 *
	 * @param fecha Fecha de Busqueda
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorFechaMayorQue(String fecha, DbConnection dbConn) {

		 Date date;

			String clausula;
		  try{
		     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
		     clausula = " AND " + CN_FECHAINICIO + " > " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

		  }
		  catch (Exception e){
			 clausula = " AND " + CN_FECHAINICIO + " > '" + DbUtil.replaceQuotes(fecha) + "'";
		  }
		  return clausula;

	}

	/**
	 * Devuelve la clausula de consulta cuando la fecha de inicio de expediente es anterior
	 * que la fecha de busqueda
	 *
	 * @param fecha Fecha de Busqueda
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorFechaMenorQue(String fecha, DbConnection dbConn) {

		 Date date;
		 String clausula;
		  try{
		     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
		     clausula = " AND " + CN_FECHAINICIO + " < " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

		  }
		  catch (Exception e){
			  clausula = " AND " + CN_FECHAINICIO + " < '" + DbUtil.replaceQuotes(fecha) + "'";
		  }
		  return clausula;
	}

	/**
	 * Devuelve la clausula de consulta por estado
	 *
	 * @param estado Código del estado del expediente
	 *
	 * @return String Clausula de consulta
	 */
	public String getClausulaPorEstado(String estado) {

		String clausula="";
		if(StringUtils.isNotEmpty(estado))
		{
			clausula = " AND " + CN_ESTADO + " = '" + DbUtil.replaceQuotes(estado) + "'";
		}

		return clausula;
	}

	/**
	 * Devuelve la clausula de consulta por el NIF de un interesado
	 *
	 * @param NIF
	 *            identificador del interesado
	 * @return String Clausula de consulta por numero
	 */
	public String getClausulaMixta(String NIF) {

		InteresadosTabla interesadosTabla = new InteresadosTabla();
		String nombreColumnaNumeroExpediente = interesadosTabla.getNombreColumnaNumeroExpediente();
		StringBuffer clausula = new StringBuffer();

		if (StringUtils.isNotEmpty(NIF)) {
			clausula.append(interesadosTabla.getClausulaPorNIF(NIF))
					.append(" AND ");
		} else {
			clausula.append("WHERE ");
		}

		clausula.append(" A.")
				.append(nombreColumnaNumeroExpediente)
				.append(" = B.")
				.append(CN_NUMERO);

		return clausula.toString();
	}


	public String getTablaMixta() {

		InteresadosTabla interesadosTabla = new InteresadosTabla();
		String nombreTablas =  interesadosTabla.getNombreTabla() + "  A," + this.getNombreTabla() + " B";

		return nombreTablas;
	}

	/**
	 * Devuelve la clausula de consulta por expediente (con alias de tablas)
	 *
	 * @param expediente Código del expediente
	 *
	 * @return String Clausula de consulta
	 */
	  public String getClausulaMixtaPorExpediente(String expediente)
	  {
			String clausula ="";

			if(StringUtils.isNotEmpty(expediente))
			{
				clausula = " AND B." + CN_NUMERO + " = '" + DbUtil.replaceQuotes(expediente) + "'";
			}

			return clausula;
	  }

		/**
		 * Devuelve la clausula de consulta por procedimiento
		 *
		 * @param procedimiento Texto del procedimiento
		 *
		 * @return String Clausula de consulta
		 */
	  public String getClausulaPorProcedimiento(String procedimiento)
	  {
		  String clausula ="";
		  	if(StringUtils.isNotEmpty(procedimiento))
			{
		  		clausula = " AND UPPER(" + CN_PROCEDIMIENTO + ") LIKE UPPER('%" + DbUtil.replaceQuotes(procedimiento) + "%')";
			}
			return clausula;
	  }

		/**
		 * Devuelve la clausula de consulta por número de registro inicial
		 *
		 * @param numRegistroInicial Número de registro inicial
		 *
		 * @return String Clausula de consulta
		 */
	  public String getClausulaPorNumRegistroInicial(String numRegistroInicial)
	  {
		  String clausula ="";
		  	if(StringUtils.isNotEmpty(numRegistroInicial))
			{
			  clausula = " AND " + CN_NUMEROREGISTRO + " = '" + DbUtil.replaceQuotes(numRegistroInicial) + "'";
			}
			return clausula;
	  }

		/**
		 * Devuelve la clausula de consulta por fechas de registro
		 *
		 * @param fecha Fecha a buscar.En caso de venir el parámetro fechaHasta, esta fecha haría de fechaDesde
		  * @param fechaHasta Fecha para buscar entre rangos cuando el operadorConsulta es "entre"
		  * @param operador Operación a realizar en la búsqueda (<, >, =, entre)
		 *
		 * @return String Clausula de consulta
		 */
	  public String getClausulaPorFechaRegistroInicial(String fecha,String fechaHasta,String operador,DbConnection dbConn)
	  {
		  String clausula = "";
		  Date date = null;
		  if("=".equals(operador) && StringUtils.isNotEmpty(fecha))
		  {
			  try{
				     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
				     clausula = " AND " + CN_FECHAREGISTRO + " = " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

				  }
				  catch (Exception e){
					  clausula = " AND " + CN_FECHAREGISTRO + " = '" + DbUtil.replaceQuotes(fecha) + "'";
				  }
		  }
		  else if("<".equals(operador) && StringUtils.isNotEmpty(fecha))
		  {
			  try{
				     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
				     clausula = " AND " + CN_FECHAREGISTRO + " < " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

				  }
				  catch (Exception e){
					  clausula = " AND " + CN_FECHAREGISTRO + " < '" + DbUtil.replaceQuotes(fecha) + "'";
				  }
		  }
		  else if(">".equals(operador) && StringUtils.isNotEmpty(fecha))
		  {
			  try{
				     date = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
				     clausula = " AND " + CN_FECHAREGISTRO + " > " + DbUtil.getNativeDateTimeSyntax(dbConn, date, true) + "";

				  }
				  catch (Exception e){
					 clausula = " AND " + CN_FECHAREGISTRO + " > '" + DbUtil.replaceQuotes(fecha) + "'";
				  }
		  }
		  else if("entre".equals(operador) && StringUtils.isNotEmpty(fecha) && StringUtils.isNotEmpty(fechaHasta))
		  {
			  Date dateDesde =null;
			  Date dateHasta =null;
			  try{
				     dateDesde = DateTimeUtil.getDate(fecha, "yyyy-MM-dd");
				     dateHasta = DateTimeUtil.getDate(fechaHasta, "yyyy-MM-dd");

					 clausula = " AND " + CN_FECHAREGISTRO + " >= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateDesde, true)+ " AND " + CN_FECHAREGISTRO + " <= " + DbUtil.getNativeDateTimeSyntax(dbConn, dateHasta, true) + "";
				  }
				  catch (Exception e){
					  clausula = " AND " + CN_FECHAREGISTRO + " >= '" + DbUtil.replaceQuotes(fecha) + "' AND " + CN_FECHAREGISTRO + " <= '" + DbUtil.replaceQuotes(fechaHasta) + "'";
				  }
		  }

		  return clausula;
	  }
}