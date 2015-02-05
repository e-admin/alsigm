package es.ieci.tecdoc.sicres.terceros.web.validator;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import junit.framework.Assert;
import junit.framework.TestCase;

public class NIFValidatorTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = new NIFValidator();
	}

	public void testNifs() throws Exception {
		String[] nifs = new String[] { "88445995R", "16335606V", "28991175C",
				"40196830Y", "46779391J", "63609096C", "85985000Y",
				"69393770X", "13459414K", "59641045K", "12805227T",
				"60998727J", "09882198H", "83004105A", "47394778N",
				"46875335R", "96113631N", "94288380H", "09715874F",
				"24668795F", "75596388B", "35297936C", "66683849H",
				"77957439C", "85134061K", "92522722R", "90825257M",
				"01257114A", "09806826V", "26921810B", "12345678Z" };
		String[] specialNifs = new String[] { "K2013214H", "K4810974H",
				"K0278432J", "L0216060D" };

		for (int i = 0; i < nifs.length; i++) {
			Errors errors = new BindException(nifs[i], "");
			validator.validate(nifs[i], errors);
			Assert.assertFalse(nifs[i], errors.hasErrors());
		}

		for (int i = 0; i < specialNifs.length; i++) {
			Errors errors = new BindException(specialNifs[i], "");
			validator.validate(specialNifs[i], errors);
			Assert.assertFalse(specialNifs[i], errors.hasErrors());
		}
	}

	protected Validator validator;
}
