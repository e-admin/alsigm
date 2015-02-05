package es.ieci.tecdoc.isicres.api.intercambioregistral.business.spring;


import org.springframework.context.ApplicationContext;

/** 
 * Clase que encapsula el contexto de aplicación de Spring.
 * @author Iecisa 
 * @version $Revision$ 
 *
 */
public class AppContext {

    private static ApplicationContext ctx;

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically
     * loaded during Spring-Initialization.
     */
    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application.
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
} 
