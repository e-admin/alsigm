package descripcion.db;

import org.junit.Assert;
import org.junit.Test;

import common.manager.ArchidocDBBaseTest;

import descripcion.vos.DescriptorVO;

public class DescriptorDBEntityImplTest extends ArchidocDBBaseTest {

	@Override
	protected IDescriptorDBEntity getDAO() {
		return getManager().getDescriptorDBEntity();
	}


	@Test
	public void getDescriptorById(){
		DescriptorVO descriptor = getDAO().getDescriptor(idDescriptor);
		Assert.assertNotNull(descriptor);
		Assert.assertEquals(idDescriptor, descriptor.getId());
	}

	//@Test
	public void getProductorSerieByCodigoOrgano(){
		DescriptorVO descriptor = getDAO().getProductorSerieByCodigoOrgano(idSerie,idListaDescriptor,codigoOrgano);
		Assert.assertNotNull(descriptor);

		Assert.assertEquals(idDescriptor, descriptor.getId());
		Assert.assertEquals(idListaDescriptor, descriptor.getIdLista());
	}

	//@Test
	public void getProductorSerieByNombreOrgano(){
		DescriptorVO descriptor = getDAO().getProductorSerieByNombreOrgano(idSerie, idListaDescriptor, nombreDescriptor);
		Assert.assertNotNull(descriptor);
		Assert.assertEquals(nombreDescriptor, descriptor.getNombre());
	}

}
