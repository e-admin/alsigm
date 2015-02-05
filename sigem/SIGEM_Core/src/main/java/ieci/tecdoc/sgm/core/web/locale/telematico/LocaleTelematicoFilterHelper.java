package ieci.tecdoc.sgm.core.web.locale.telematico;

import java.util.ArrayList;

import ieci.tecdoc.sgm.core.services.idioma.ConstantesIdioma;
import ieci.tecdoc.sgm.core.services.idioma.LectorIdiomas;
import ieci.tecdoc.sgm.core.web.locale.LocaleFilterHelper;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LocaleTelematicoFilterHelper extends LocaleFilterHelper {

	public void  doFilter(ServletRequest request, ServletResponse response){
		
		super.doFilter(request, response);
		if (request instanceof HttpServletRequest) {
			HttpSession session = ((HttpServletRequest)request).getSession();
			ArrayList idiomasDisponibles = (ArrayList)session.getAttribute(ConstantesIdioma.IDIOMAS_DISPONIBLES);
			if(idiomasDisponibles==null)
			{
				String mostrarIdiomas = LectorIdiomas.getMostrarComboIdiomas();
				if(mostrarIdiomas!=null && ConstantesIdioma.MOSTRAR_IDIOMAS.equals(mostrarIdiomas))
				{
					session.setAttribute(ConstantesIdioma.MOSTRAR_IDIOMAS_KEY, mostrarIdiomas);
					
					idiomasDisponibles = LectorIdiomas.getIdiomas();
					//Si no hay idiomas disponibles a través del lector de idiomas, no ponemos nada en la session
					//Para posteriormente no mostrar el combo de idiomas, ni guardarlo en el RT.
					if(idiomasDisponibles!=null && idiomasDisponibles.size()>0)
					{
						session.setAttribute(ConstantesIdioma.IDIOMAS_DISPONIBLES, idiomasDisponibles);
					}
				}
			}
			ArrayList idiomasPresentacionDisponibles = (ArrayList)session.getAttribute(ConstantesIdioma.IDIOMAS_PRESENTACION_DISPONIBLES);
			if(idiomasPresentacionDisponibles==null)
			{
				String mostrarIdiomasPresentacion = LectorIdiomas.getMostrarYGuardarIdiomaPresentacion();
					if(mostrarIdiomasPresentacion!=null && ConstantesIdioma.MOSTRAR_IDIOMAS.equals(mostrarIdiomasPresentacion))
					{
						session.setAttribute(ConstantesIdioma.MOSTRAR_IDIOMAS_PRESENTACION_KEY, mostrarIdiomasPresentacion);
					
					idiomasPresentacionDisponibles = LectorIdiomas.getIdiomasPresentacion();
					//Si no hay idiomas disponibles a través del lector de idiomas, no ponemos nada en la session
					//Para posteriormente no mostrar el combo de idiomas, ni guardarlo en el RT.
					if(idiomasPresentacionDisponibles!=null && idiomasPresentacionDisponibles.size()>0)
					{
						session.setAttribute(ConstantesIdioma.IDIOMAS_PRESENTACION_DISPONIBLES, idiomasPresentacionDisponibles);
					}
				}
			}
		}

	}
}
