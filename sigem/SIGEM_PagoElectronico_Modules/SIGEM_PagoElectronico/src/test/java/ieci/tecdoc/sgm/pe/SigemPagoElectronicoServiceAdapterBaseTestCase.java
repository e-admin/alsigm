package ieci.tecdoc.sgm.pe;

import ieci.tecdoc.sgm.core.test.SigemBaseTestCase;

public class SigemPagoElectronicoServiceAdapterBaseTestCase extends SigemBaseTestCase{
	protected static final String ENTIDAD="000";
	protected static final PagoElectronicoManager manager=new PagoElectronicoManager();
	
	public SigemPagoElectronicoServiceAdapterBaseTestCase(){
		super();
		try{ setUp(); } 
		catch(Exception e){ fail("Error al inicializar spring JNDI Datasources: "+e); }
	}
	
	public void testDummy(){
		
	}
}
