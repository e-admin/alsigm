package es.ieci.tecdoc.sicres.terceros.web.validator;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 *
 * @author IECISA
 *
 */
public class NIEValidatorTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = new NIEValidator();
	}

	public void testNies() throws Exception {
		String[] nies = new String[] { "Y3650027X", "Z4377649H", "Z1060424Z",
				"X0307949W", "Z6256007N", "Y1272822Q", "Y2972878G",
				"X9442606W", "X1765076X", "X0304338W", "Y4064783F",
				"Y7237793Y", "Y4712941W", "Y4216990T", "X4265226Z",
				"Z3721444A", "X2304637Z", "Y7958863A", "Z0901018K",
				"X0178832F", "Z8308038Y", "X9427236L", "Z5661291F",
				"Z0393333S", "X9621315R", "Y0793952Y", "X6354132Z",
				"X2598194E", "X6599393A", "Y2400992J", "Z9892188P",
				"Y8580209G", "Z4592168Q", "Y6813277R", "Y1259033G",
				"Z6816166Y", "Y8064526G", "X4614511K", "Z9347586T", "Z3597814K" };

		for (int i = 0; i < nies.length; i++) {
			Errors errors = new BindException(nies[i], "");
			validator.validate(nies[i], errors);
			Assert.assertFalse(nies[i], errors.hasErrors());
		}
	}

	protected NIEValidator validator;
}
