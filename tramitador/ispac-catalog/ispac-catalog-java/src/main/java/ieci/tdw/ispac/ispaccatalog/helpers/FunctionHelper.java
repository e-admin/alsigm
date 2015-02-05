package ieci.tdw.ispac.ispaccatalog.helpers;

import ieci.tdw.ispac.api.ICatalogAPI;
import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.api.errors.ISPACInfo;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispacweb.security.UserCredentials;
import ieci.tdw.ispac.ispacweb.security.UserCredentialsHelper;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

/** 
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class FunctionHelper {

	/**
	 * Comprueba si el usuario conectado tiene la función indicada.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente
	 * @param functionCode
	 *            Función requerida
	 * @return true si el usuario tiene asignada la función, false en caso
	 *         contrario.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static boolean userHasFunction(HttpServletRequest request,
			ClientContext ctx, int functionCode) throws ISPACException {
		return userHasFunctions(request, ctx, new int[] { functionCode });
	}

	/**
	 * Comprueba si el usuario conectado tiene las funciones indicadas.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente
	 * @param functionCodes
	 *            Funciones requeridas
	 * @return true si el usuario tiene asignadas las funciones, false en caso
	 *         contrario.
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static boolean userHasFunctions(HttpServletRequest request,
			ClientContext ctx, int[] functionCodes) throws ISPACException {

		// Obtener las credenciales del usuario conectado
		UserCredentials userCredentials = UserCredentialsHelper
				.getUserCredentials(request, ctx);

		// Comprobar si el usuario es administrador o tiene las funciones
		// adecuadas
		return userCredentials.isCatalogAdministrator()
				|| userCredentials.containsAnyFunction(functionCodes);
	}

	/**
	 * Comprueba que el usuario conectado tenga asignadas las funciones
	 * indicadas. Si no las tiene, lanza una ISPACInfo.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente
	 * @param functionCodes
	 *            Funciones requeridas
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static void checkFunctions(HttpServletRequest request,
			ClientContext ctx, int[] functionCodes) throws ISPACException {

		// Comprobar si el usuario es administrador o tiene las funciones
		// adecuadas
		if (!userHasFunctions(request, ctx, functionCodes)) {
			throw new ISPACInfo("exception.function.required");
		}
	}

	/**
	 * Comprueba que el usuario conectado tenga asignadas las funciones
	 * adecuadas para consultar la entidad.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente
	 * @param entityId
	 *            Identificador de la entidad
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static void checkReadOnlyFunctions(HttpServletRequest request,
			ClientContext ctx, int entityId) throws ISPACException {

		int[] requiredFunctions = null;
		
		switch (entityId) {
		
			case ICatalogAPI.ENTITY_CT_PROCEDURE:
			case ICatalogAPI.ENTITY_P_PROCEDURE:
			case ICatalogAPI.ENTITY_CT_PCDORG:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_PROCEDURES_READ,
						ISecurityAPI.FUNC_INV_PROCEDURES_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_CT_STAGE:
			case ICatalogAPI.ENTITY_P_STAGE:
			case ICatalogAPI.ENTITY_CT_STAGETASK:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_STAGES_READ,
						ISecurityAPI.FUNC_INV_STAGES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TASK:
			case ICatalogAPI.ENTITY_P_TASK:
			case ICatalogAPI.ENTITY_CT_TASKTYPEDOC:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_TASKS_READ,
						ISecurityAPI.FUNC_INV_TASKS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TYPEDOC:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_DOCTYPES_READ,
						ISecurityAPI.FUNC_INV_DOCTYPES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TEMPLATE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_TEMPLATES_READ,
						ISecurityAPI.FUNC_INV_TEMPLATES_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_SIGNPROCESS:
			case ICatalogAPI.ENTITY_SIGNPROCESS_HEADER:
			case ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_READ,
						ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_P_SUBPROCEDURE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_SUBPROCESS_READ,
						ISecurityAPI.FUNC_INV_SUBPROCESS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_ENTITY:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_ENTITIES_READ,
						ISecurityAPI.FUNC_COMP_ENTITIES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_RULE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_RULES_READ,
						ISecurityAPI.FUNC_COMP_RULES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_SEARCHFORM:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_SEARCH_FORMS_READ,
						ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_INFORMES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_REPORTS_READ,
						ISecurityAPI.FUNC_COMP_REPORTS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_SYSTEM_VARS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_SYSTEM_VARS_READ,
						ISecurityAPI.FUNC_COMP_SYSTEM_VARS_EDIT};
				break;

			case ICatalogAPI.ENTITY_CT_HELPS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_HELPS_READ,
						ISecurityAPI.FUNC_COMP_HELPS_EDIT};
				break;

			case ICatalogAPI.ENTITY_PUB_ACTIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_ACTIONS_READ,
						ISecurityAPI.FUNC_PUB_ACTIONS_EDIT};
				break;

			case ICatalogAPI.ENTITY_PUB_APPLICATIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_APPLICATIONS_READ,
						ISecurityAPI.FUNC_PUB_APPLICATIONS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_CONDITIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_CONDITIONS_READ,
						ISecurityAPI.FUNC_PUB_CONDITIONS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_MILESTONES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_MILESTONES_READ,
						ISecurityAPI.FUNC_PUB_MILESTONES_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_RULES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_RULES_READ,
						ISecurityAPI.FUNC_PUB_RULES_EDIT };
				break;
		}

		if (!ArrayUtils.isEmpty(requiredFunctions)) {
			checkFunctions(request, ctx, requiredFunctions);
		}
	}

	/**
	 * Comprueba que el usuario conectado tenga asignadas las funciones
	 * adecuadas para editar la entidad.
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param ctx
	 *            Contexto de cliente
	 * @param entityId
	 *            Identificador de la entidad
	 * @throws ISPACException
	 *             si ocurre algún error.
	 */
	public static void checkEditFunctions(HttpServletRequest request,
			ClientContext ctx, int entityId) throws ISPACException {

		int[] requiredFunctions = null;
		
		switch (entityId) {
		
			case ICatalogAPI.ENTITY_CT_PROCEDURE:
			case ICatalogAPI.ENTITY_P_PROCEDURE:
			case ICatalogAPI.ENTITY_CT_PCDORG:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_PROCEDURES_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_CT_STAGE:
			case ICatalogAPI.ENTITY_P_STAGE:
			case ICatalogAPI.ENTITY_CT_STAGETASK:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_STAGES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TASK:
			case ICatalogAPI.ENTITY_P_TASK:
			case ICatalogAPI.ENTITY_CT_TASKTYPEDOC:
				
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_TASKS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TYPEDOC:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_DOCTYPES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_TEMPLATE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_TEMPLATES_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_SIGNPROCESS:
			case ICatalogAPI.ENTITY_SIGNPROCESS_HEADER:
			case ICatalogAPI.ENTITY_SIGNPROCESS_DETAIL:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_SIGN_CIRCUITS_EDIT };
				break;
				
			case ICatalogAPI.ENTITY_P_SUBPROCEDURE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_INV_SUBPROCESS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_ENTITY:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_ENTITIES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_RULE:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_RULES_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_SEARCHFORM:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_SEARCH_FORMS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_INFORMES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_REPORTS_EDIT };
				break;

			case ICatalogAPI.ENTITY_SPAC_CALENDARIOS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_CALENDARS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_SYSTEM_VARS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_SYSTEM_VARS_EDIT };
				break;

			case ICatalogAPI.ENTITY_CT_HELPS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_COMP_HELPS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_ACTIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_ACTIONS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_APPLICATIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_APPLICATIONS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_CONDITIONS:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_CONDITIONS_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_MILESTONES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_MILESTONES_EDIT };
				break;

			case ICatalogAPI.ENTITY_PUB_RULES:
				requiredFunctions = new int[] { 
						ISecurityAPI.FUNC_PUB_RULES_EDIT };
				break;
		}

		if (!ArrayUtils.isEmpty(requiredFunctions)) {
			checkFunctions(request, ctx, requiredFunctions);
		}
	}
}
