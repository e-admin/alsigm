package es.ieci.tecdoc.isicres.api.business.manager.impl;

import junit.framework.TestCase;
import es.ieci.tecdoc.isicres.api.business.manager.ContextoAplicacionManager;
import es.ieci.tecdoc.isicres.api.business.vo.BaseLibroVO;
import es.ieci.tecdoc.isicres.api.business.vo.LibroEntradaVO;

public class ContextoAplicacionManagerImplTest extends TestCase {

	protected ContextoAplicacionManager ctx=new ContextoAplicacionManagerImpl();
	
	public void libroTest(){
		
		BaseLibroVO libro=ctx.getLibroActual();
		assertNull(libro);
		
		libro = new LibroEntradaVO();
		String comentario="libro de entrada comentario";
		libro.setComentario(comentario);
		
		ctx.setLibroActual(libro);
		
		BaseLibroVO libroContexto= ctx.getLibroActual();
		
		assertNotNull(libroContexto);
		assertEquals(libroContexto, libro);
		assertEquals(libroContexto.getComentario(), comentario);
				
		
		
	}
}
