package ieci.tdw.ispac.ispacweb.security;

import ieci.tdw.ispac.api.ISecurityAPI;
import ieci.tdw.ispac.api.errors.ISPACException;
import ieci.tdw.ispac.ispaclib.context.ClientContext;
import ieci.tdw.ispac.ispaclib.resp.Responsible;
import ieci.tdw.ispac.ispaclib.util.ISPACConfiguration;
import ieci.tdw.ispac.ispaclib.utils.ArrayUtils;
import ieci.tdw.ispac.ispaclib.utils.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

/**
 * UserCredentials
 *
 *
 */
public class UserCredentials implements Serializable {

	private static final long serialVersionUID = -5536705120257393311L;
	
	private static final Logger LOGGER = Logger.getLogger(UserCredentials.class);

	/**
	 * Conjunto de funciones asignadas al usuario.
	 */
	private Set<Integer> functionSet = null;
    
	/**
	 * Indica si el usuario tiene permiso de administración total del catálogo
	 */
	private boolean catalogAdministrator = false;    

    /**
     * Constructor.
     * @param ctx Contexto de cliente
     * @throws ISPACException
     */
	public UserCredentials(ClientContext ctx) throws ISPACException {
		load(ctx);
	}

	/**
	 * Carga las credenciales del usuario.
	 * 
	 * @param ctx Contexto de cliente
	 * @throws ISPACException
	 */
	@SuppressWarnings("unchecked")
	public void load(ClientContext ctx) throws ISPACException {
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Cargando las credenciales del usuario: [" + ctx.getUser() + "]");
		}
		
		// Información del usuario conectado
		Responsible user = ctx.getUser();
		
		this.functionSet = (Set<Integer>) ctx.getAPI().getRespManagerAPI()
				.getFunctionsSet(user.getRespList());

		// Comprobar si el usuario es administrador del catálogo
		checkCatalogAdministrador(ctx);
    }

    public boolean isCatalogAdministrator() {
		return catalogAdministrator;
	}

	public boolean containsAnyFunction(List<String> functions) {
    	
    	boolean result = false;

		if (!CollectionUtils.isEmpty(functions)) {
			for (String function : functions) {
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Comprobando la función [" + function + "]");
				}
				
				int functionCode = getFunctionCode(function);
				if (functionSet.contains(Integer.valueOf(functionCode))) {

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("El usuario tiene la función [" + function + "]");
					}

					result = true;
					break;
				}
			}
		}    	
    	
    	return result; 
    }

    public boolean containsAnyFunction(int[] functionCodes) {
    	
    	boolean result = false;

		if (!ArrayUtils.isEmpty(functionCodes)) {
			for (int function : functionCodes) {
				
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info("Comprobando la función [" + function + "]");
				}
				
				if (functionSet.contains(Integer.valueOf(function))) {

					if (LOGGER.isInfoEnabled()) {
						LOGGER.info("El usuario tiene la función [" + function + "]");
					}

					result = true;
					break;
				}
			}
		}    	
    	
    	return result; 
    }

    public boolean containsFunction(String function) {
    	
    	boolean result = false;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Comprobando la función [" + function + "]");
		}

		int functionCode = getFunctionCode(function);
		if (functionSet.contains(Integer.valueOf(functionCode))) {
			result = true;
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("El usuario tiene la función [" + function + "]: " + result);
		}

    	return result; 
    }

    public boolean containsFunction(int functionCode) {
    	
    	boolean result = false;

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Comprobando la función [" + functionCode + "]");
		}

		if (functionSet.contains(Integer.valueOf(functionCode))) {
			result = true;
		}

		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("El usuario tiene la función [" + functionCode + "]: " + result);
		}

    	return result; 
    }

    private int getFunctionCode(String function) {
    	
    	int functionCode = -1;
    	
    	if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("Obteniendo el código para la función [" + function + "]");
    	}
    	
    	if (StringUtils.isNotBlank(function)) {
			try {
				Field  field = ISecurityAPI.class.getField(function.trim());
				functionCode = field.getInt(null);
			} catch (Exception e) {
				LOGGER.warn("Error al obtener el código de la función: [" + function + "]", e);
			}
    	}
    	
    	if (LOGGER.isInfoEnabled()) {
    		LOGGER.info("Código para la función [" + function + "]: " + functionCode);
    	}
    	
    	return functionCode;
    }
    
    
    private void checkCatalogAdministrador(ClientContext ctx) throws ISPACException {

		// SuperUsuario por defecto
		String superUser = ISPACConfiguration.getInstance().get(ISPACConfiguration.SUPERUSER);
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Superusuario configurado [" + superUser + "]");
		}

		// Información del usuario conectado
		Responsible user = ctx.getUser();
		
		if ( ((user != null) && StringUtils.equalsIgnoreCase(user.getName(), superUser))
				|| containsFunction(ISecurityAPI.FUNC_ENTERCATALOG)) {
			
			this.catalogAdministrator = true;
		}
		
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Administrador del catálogo: [" + this.catalogAdministrator + "]");
		}
    }
}
