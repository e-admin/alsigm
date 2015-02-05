package ieci.tecdoc.sgm.usuario.admin.struts;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.validator.Resources;

public class CustomValidatorRules {

	public static boolean validateTwoFields(Object bean, ValidatorAction va,
			Field field, ActionErrors errors, HttpServletRequest request) {
		String value = getValueAsString(bean, field.getProperty());
		String sProperty2 = field.getVarValue("secondProperty");
		String value2 = getValueAsString(bean, sProperty2);

		if (!GenericValidator.isBlankOrNull(value)) {
			try {
				if (!value.equals(value2)) {
					errors.add(field.getKey(),
					Resources.getActionError(request, va, field));
					return false;
				}
			} catch (Exception e) {
				errors.add(field.getKey(),
				Resources.getActionError(request, va, field));
				return false;
			}
		}

		return true;
	}
	
    public static String getValueAsString(Object bean, String property) {
        Object value = null;

        try {
            value = PropertyUtils.getProperty(bean, property);

        } catch(IllegalAccessException e) {
            Log log = LogFactory.getLog(CustomValidatorRules.class);
            log.error(e.getMessage(), e);
        } catch(InvocationTargetException e) {
            Log log = LogFactory.getLog(CustomValidatorRules.class);
            log.error(e.getMessage(), e);
        } catch(NoSuchMethodException e) {
            Log log = LogFactory.getLog(CustomValidatorRules.class);
            log.error(e.getMessage(), e);
        }

        if (value == null) {
            return null;
        }

        if (value instanceof String[]) {
            return ((String[]) value).length > 0 ? value.toString() : "";

        } else if (value instanceof Collection) {
            return ((Collection) value).isEmpty() ? "" : value.toString();

        } else {
            return value.toString();
        }

    }

}
