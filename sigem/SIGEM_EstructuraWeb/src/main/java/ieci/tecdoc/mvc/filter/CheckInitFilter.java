/*
 * Created on 07-jul-2005
 *
 */
package ieci.tecdoc.mvc.filter;

import ieci.tecdoc.mvc.util.MvcDefs;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @author Antonio María
 *
 */
public class CheckInitFilter implements javax.servlet.Filter {

    private     static   Logger         logger   = Logger.getLogger(CheckInitFilter.class);
    
    FilterConfig filterConfig = null;
    private static boolean m_systemStarted = false;

    private ServletContext m_ctx;
    
    public void destroy() {
        this.filterConfig = null; 
    }

    public void init(FilterConfig filterConfig ) throws ServletException {
        this.filterConfig = filterConfig ;
        m_ctx = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest  request     = (HttpServletRequest)req;
        HttpServletResponse response    = (HttpServletResponse)res;
        
        boolean isSystemProperlyStarted = isSystemProperlyStarted();
        if (!isSystemProperlyStarted)
           response.sendRedirect(request.getContextPath() + MvcDefs.URL_APPLICATION_ERROR);
        
        chain.doFilter(request,response);
        
    }
    private boolean isSystemProperlyStarted()
    {

       logger.debug("Is System properly started?: "+m_systemStarted);
       boolean isProperlyStarted = true;
       if(m_systemStarted)
       {
          return isProperlyStarted;
       }
       if(m_ctx.getAttribute(MvcDefs.TOKEN_SYSTEM_PROPERLY_STARTED) != null)
       {
          isProperlyStarted = false;
       } else
       {
          m_systemStarted = true;
       }
       return isProperlyStarted;
    }


}
