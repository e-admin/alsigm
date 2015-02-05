package ieci.tecdoc.sgm.admsistema.proceso.accionmultientidad;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.api.item.IItem;
import ieci.tdw.ispac.api.item.IItemCollection;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tecdoc.sgm.admsistema.action.accionesmultientidad.CompararImportarReglasAccionConfiguracion;
import ieci.tecdoc.sgm.admsistema.proceso.IProcessManager;
import ieci.tecdoc.sgm.admsistema.vo.AccionMultientidadVO;

import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Clase para comparar/importar reglas de distintas entidades.
 *
 * @author IECISA
 *
 */
public class CompararImportarReglasAccionEjecucion extends TramitacionAccionEjecucionBase implements IProcessManager  {

	/**
	 * Logger de la clase.
	 */
	private static final Logger logger = Logger.getLogger(CompararImportarReglasAccionEjecucion.class);

	public static final String ACTION_SUFIX_DIR = "_CompararImportarReglas";
	public static final String ACTION_PREFIX_LOG_NAME = "COMPARE_IMPORT_RULES_LOGGER_";

	/**
	 * Opción para importar (true) o comparar (false)
	 */
	private boolean importar = false;

	/**
	 * Constructor.
	 */
	public CompararImportarReglasAccionEjecucion() {
		// En la clase base se establece el currentDate
		super();
	}

	/**
	 * Ejecución de la acción multientidad.
	 *
	 * {@inheritDoc}
	 * @see ieci.tecdoc.sgm.admsistema.proceso.IProcessManager#execute(java.util.Map)
	 */
	public boolean execute(Map options) throws Exception {

		if (logger.isInfoEnabled()) {
			logger.info("Inicio de Accion Multientidad para Comparar/Importar Reglas entre Entidades");
		}

		try {
			// Establecer la fecha actual
			options.put("CURRENT_DATE", getCurrentDate());

			// Configurar el Logger para la acción
			configureProcessActionLogger(options, ACTION_SUFIX_DIR, ACTION_PREFIX_LOG_NAME);

			AccionMultientidadVO accionMultientidadVO = (AccionMultientidadVO) options.get(EjecutarAccion.PARAM_ACCION_MULTIENTIDAD_VO);

			// Opción configurada para importar (true) o comparar (false)
			if (CompararImportarReglasAccionConfiguracion.ID_IMPORTAR.equals(accionMultientidadVO.getOpcion())) {
				importar = true;
			}

			// Mensaje informativo en el inicio con la funcionalidad a realizar y las entidades seleccionadas
			generateInitInfoLog(accionMultientidadVO);

			// Entidades de origen
			String[] entidadesOrigen = accionMultientidadVO.getEntidadesOrigen();
			for (int i = 0; i < entidadesOrigen.length; i++) {

				// Entidad de origen a procesar
				String entidadOrigen = entidadesOrigen[i];

				ClientContext context = null;
				getProcessActionLogger().info("Comparacion/importacion de las reglas de la entidad origen '" + entidadOrigen + "'");
				IItemCollection entidadOrigenCTRules = null;

				try {
					// Establecer la entidad para el acceso a BD
					setOrganizationUserInfo(entidadOrigen);

					// Crear el contexto de tramitación para la entidad de origen
					context = createClientContext(null);

					entidadOrigenCTRules = context.getAPI().getCatalogAPI().getCTRules();
				} catch (ISPACInfo iie) {
					getProcessActionLogger().warn("No se han podido obtener las reglas de la entidad origen '" + entidadOrigen + "': " + iie.getExtendedMessage(context.getLocale()), iie);
				} catch (ISPACException ie) {
					getProcessActionLogger().error("Error al obtener las reglas de la entidad origen '" + entidadOrigen + "': " + ie.getExtendedMessage(context.getLocale()), ie);
					getProcessActionLogger().error(ie.fillInStackTrace());
					if (ie.getMessage().indexOf("TransactionContainer.release") > 1){
						getProcessActionLogger().info("El error puede ser debido a que no se ha declarado, y/o establecido en la aplicacion de Administracion, el DataSource de la BD de 'tramitador' para la entidad '" + entidadOrigen + "'.");
					}
					setOk(false);
				} catch (Exception e) {
					getProcessActionLogger().error("Error al obtener las reglas de la entidad origen '" + entidadOrigen + "': " + e.getMessage(), e);
					getProcessActionLogger().error(e.fillInStackTrace());
					setOk(false);
				} finally {
					if (context != null) {
						context.releaseAllConnections();
					}
				}

				if (entidadOrigenCTRules != null) {

					if (!entidadOrigenCTRules.toList().isEmpty()) {

						// Entidades de destino
						String[] entidadesDestino = accionMultientidadVO.getEntidadesDestino();
						for (int j = 0; j < entidadesDestino.length; j++) {

							// Entidad de destino a procesar
							String entidadDestino = entidadesDestino[j];

							getProcessActionLogger().info("En la entidad destino '" + entidadDestino + "'");
							IItemCollection entidadDestinoCTRules = null;

							try {
								// Establecer la entidad para el acceso a BD
								setOrganizationUserInfo(entidadDestino);

								// Crear el contexto de tramitación para la entidad de destino
								context = createClientContext(null);

								try {
									entidadDestinoCTRules = context.getAPI().getCatalogAPI().getCTRules();
								} catch (ISPACInfo iie) {
									getProcessActionLogger().warn("No se han podido obtener las reglas de la entidad destino '" + entidadDestino + "': " + iie.getExtendedMessage(context.getLocale()), iie);
								} catch (ISPACException ie) {
									getProcessActionLogger().error("Error al obtener las reglas de la entidad destino '" + entidadDestino + "': " + ie.getExtendedMessage(context.getLocale()), ie);
									getProcessActionLogger().error(ie.fillInStackTrace());
									if (ie.getMessage().indexOf("TransactionContainer.release") > 1){
										getProcessActionLogger().info("El error puede ser debido a que no se ha declarado, y/o establecido en la aplicacion de Administracion, el DataSource de la BD de 'tramitador' para la entidad '" + entidadDestino + "'.");
									}
									setOk(false);
								} catch (Exception e) {
									getProcessActionLogger().error("Error al obtener las reglas de la entidad destino '" + entidadDestino + "': " + e.getMessage(), e);
									getProcessActionLogger().error(e.fillInStackTrace());
									setOk(false);
								}

								if (entidadDestinoCTRules != null) {

									Map mapEntidadDestinoCTRules = entidadDestinoCTRules.toMap("CLASE");

									// Recorrer las reglas de la entidad origen para comprobar si su clase asociada ya existe
									// entre las clases asociadas a las reglas de la entidad destino
									entidadOrigenCTRules.reset();
									while (entidadOrigenCTRules.next()) {

										// Regla de la entidad origen
										IItem entidadOrigenCTRule = (IItem) entidadOrigenCTRules.value();
										String clase = entidadOrigenCTRule.getString("CLASE");
										String nombre = entidadOrigenCTRule.getString("NOMBRE");

										if (mapEntidadDestinoCTRules.containsKey(clase)) {
											getProcessActionLogger().info("La clase '" + clase + "' asociada a la regla '" + nombre + "' de la entidad origen '"  + entidadOrigen + "' ya existe como clase asociada a una regla en la entidad destino '" + entidadDestino + "'.");
										} else {
											getProcessActionLogger().info("La clase '" + clase + "' asociada a la regla '" + nombre + "' de la entidad origen '"  + entidadOrigen + "' no existe como clase asociada a una regla en la entidad destino '" + entidadDestino + "'.");

											// Opción de importar seleccionada?
											if (importar) {

												// Crear la regla de la entidad origen en la entidad destino
												importRule(context, entidadOrigenCTRule, nombre, clase, entidadOrigen, entidadDestino);
											}
										}
							    	}
								}
							} catch (ISPACException ie) {
								getProcessActionLogger().error("Error al comparar/importar las reglas de la entidad origen '" + entidadOrigen + "' en la entidad destino '" + entidadDestino + "': " + ie.getExtendedMessage(context.getLocale()), ie);
								getProcessActionLogger().error(ie.fillInStackTrace());
								setOk(false);
							} finally {
								if (context != null) {
									context.releaseAllConnections();
								}
							}
						}
					} else {
						getProcessActionLogger().info("En la entidad origen '" + entidadOrigen + "' no se han definido reglas.");
					}
				}
			}

			if (isOk()) {
				getProcessActionLogger().info("[FIN] El proceso de comparacion/importacion de reglas entre las entidades seleccionadas ha finalizado correctamente");
			} else  {
				getProcessActionLogger().warn("[FIN] El proceso de comparacion/importacion de reglas entre las entidades seleccionadas ha finalizado con ERRORES");
			}
		} finally {
			releaseLogger(getProcessActionLogger());
		}

		if (logger.isInfoEnabled()) {
			logger.info("Fin de Accion Multientidad para Comparar/Importar Reglas entre Entidades");
		}

		return isOk();
	}

	/**
	 * Importar la regla de la entidad origen en la entidad destino.
	 *
	 * @param context Contexto de tramitación de la entidad destino.
	 * @param entidadOrigenCTRule Regla de la entidad origen.
	 * @param nombre Nombre de la regla de la entidad origen.
	 * @param clase Clase de la regla de la entidad origen.
	 * @param entidadOrigen Identificador de la entidad origen.
	 * @param entidadDestino Identificador de la entidad destino.
	 */
	protected void importRule(ClientContext context, IItem entidadOrigenCTRule, String nombre, String clase, String entidadOrigen, String entidadDestino) {

		try {
			IItem newCTRule = context.getAPI().getCatalogAPI().createCTEntity(ICatalogAPI.ENTITY_CT_RULE);

			newCTRule.set("NOMBRE", nombre);
			newCTRule.set("CLASE", clase);
			newCTRule.set("DESCRIPCION", entidadOrigenCTRule.get("DESCRIPCION"));
			newCTRule.set("TIPO", entidadOrigenCTRule.get("TIPO"));

			newCTRule.store(context);

			getProcessActionLogger().info("Importacion realizada correctamente: la regla '" + nombre + "' con clase '" + clase + "' de la entidad origen '"  + entidadOrigen + "' se ha importado en la entidad destino '" + entidadDestino + "'.");
		} catch (ISPACInfo iie) {
			getProcessActionLogger().warn("No se ha podido importar la regla '" + nombre + "' con clase '" + clase + "' de la entidad origen '"  + entidadOrigen + "' en la entidad destino '" + entidadDestino + "': " + iie.getExtendedMessage(context.getLocale()), iie);
		} catch (ISPACException ie) {
			getProcessActionLogger().error("Error al importar la regla '" + nombre + "' con clase '" + clase + "' de la entidad origen '"  + entidadOrigen + "' en la entidad destino '" + entidadDestino + "': " + ie.getExtendedMessage(context.getLocale()), ie);
			getProcessActionLogger().error(ie.fillInStackTrace());
		} catch (Exception e) {
			getProcessActionLogger().error("Error al importar la regla '" + nombre + "' con clase '" + clase + "' de la entidad origen '"  + entidadOrigen + "' en la entidad destino '" + entidadDestino + "': " + e.getMessage(), e);
			getProcessActionLogger().error(e.fillInStackTrace());
			setOk(false);
		}
	}

	/**
	 * Generar en el Log del proceso la información con la configuración de la acción a ejecutar.
	 *
	 * @param accionMultientidadVO Configuración seleccionada para la acción multientidad.
	 */
	protected void generateInitInfoLog(AccionMultientidadVO accionMultientidadVO) {

		StringBuffer msgInfo = new StringBuffer();

		// Mensaje informativo en el inicio con la funcionalidad a realizar y las entidades seleccionadas
		msgInfo.append("CompararImportarReglasAccionEjecucion - Inicio del proceso de ");
		if (importar) {
			msgInfo.append("IMPORTACION");
		} else {
			msgInfo.append("COMPARACION");
		}
		msgInfo.append(" de reglas entre la/s entidad/es de origen ");
		String[] entidadesOrigen = accionMultientidadVO.getEntidadesOrigen();
		msgInfo.append(entidadesOrigen[0]);
		for (int i = 1; i < entidadesOrigen.length; i++) {
			msgInfo.append(", ");
			msgInfo.append(entidadesOrigen[i]);
		}
		msgInfo.append(" y la/s entidad/es de destino ");
		String[] entidadesDestino = accionMultientidadVO.getEntidadesDestino();
		msgInfo.append(entidadesDestino[0]);
		for (int i = 1; i < entidadesDestino.length; i++) {
			msgInfo.append(", ");
			msgInfo.append(entidadesDestino[i]);
		}
		msgInfo.append(".");

		getProcessActionLogger().info(msgInfo.toString());
	}
}
