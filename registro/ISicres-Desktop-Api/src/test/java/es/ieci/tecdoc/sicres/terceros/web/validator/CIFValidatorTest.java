package es.ieci.tecdoc.sicres.terceros.web.validator;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CIFValidatorTest extends TestCase {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		validator = new CIFValidator();
	}

	public void testCifs() throws Exception {
		String[] cifsValidos = new String[] { "V4548593E", "D9273408F",
				"G6022478I", "N4854610E", "C3472690A", "B05054168",
				"E67917633", "J1235093J", "P6807758E", "V74438797",
				"Q8688580C", "V03776044", "J1598602I", "A97932206",
				"U3931108I", "V6937648A", "V3520044C", "V60317963",
				"N0605478G", "G07204894", "C3262799D", "F6505411F",
				"B21098033", "R75729434", "E33053042", "J72292592",
				"R43068691", "M25820812", "K0278432J", "G14634679",
				"N6653856B", "G51910859", "R58773433", "K8574983F",
				"Q5876520G", "S9627029C", "N6343929C", "K8261887G",
				"V41721929", "J5182968G", "Q7754627C", "M93900736",
				"P3102429B", "P3380485G", "C45848371", "N0541249I",
				"W2550651J", "K2013214H", "K4810974H", "N5093175G",
				"K1615084I", "W6902513H", "F2048445G", "L0216060D",
				"Q3686252B", "H54180278", "F8514675A" };
		String[] cifsInvalidos = new String[] { "Q7855018C" };

		// Validamos cifs correctos
		for (int i = 0; i < cifsValidos.length; i++) {
			Errors errors = new BindException(cifsValidos[i], "");
			validator.validate(cifsValidos[i], errors);
			Assert.assertFalse(cifsValidos[i], errors.hasErrors());
		}

		// Validamos que los cifs incorrectos no cumplen con el formato esperado
		for (int i = 0; i < cifsInvalidos.length; i++) {
			Errors errors = new BindException(cifsValidos[i], "");
			validator.validate(cifsInvalidos[i], errors);
			Assert.assertTrue(cifsInvalidos[i], errors.hasErrors());
		}
	}

	protected Validator validator;
}
