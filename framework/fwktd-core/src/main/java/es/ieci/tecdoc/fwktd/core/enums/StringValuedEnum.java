package es.ieci.tecdoc.fwktd.core.enums;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.enums.Enum;

/**
 * Abstract superclass for type-safe enums with string values suitable for use in switch statements.
 *
 * @author Iecisa
 * @version $Revision$
 *
 */
public class StringValuedEnum extends Enum {

	private static final long serialVersionUID = 1221422239628168937L;

	/**
     * The value contained in enum.
     */
    private final String value;


    /**
     * Constructor for enum item.
     *
     * @param name  the name of enum item
     * @param value  the value of enum item
     */
    protected StringValuedEnum(String name, String value) {
        super(name);
        this.value = value;
    }

    /**
     * <p>Gets an <code>Enum</code> object by class and value.</p>
     *
     * <p>This method loops through the list of <code>Enum</code>,
     * thus if there are many <code>Enum</code>s this will be
     * slow.</p>
     *
     * @param enumClass  the class of the <code>Enum</code> to get
     * @param value  the value of the <code>Enum</code> to get
     * @return the enum object, or null if the enum does not exist
     * @throws IllegalArgumentException if the enum class is <code>null</code>
     */
    @SuppressWarnings("rawtypes")
	public static Enum getEnum(Class enumClass, String value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("The Enum Class must not be null");
        }
        List list = Enum.getEnumList(enumClass);
        for (Iterator it = list.iterator(); it.hasNext();) {
            StringValuedEnum enumeration = (StringValuedEnum) it.next();
            if (enumeration.getValue().equals(value)) {
                return enumeration;
            }
        }
        return null;
    }

    /**
     * <p>Get value of enum item.</p>
     *
     * @return the enum item's value.
     */
    public final String getValue() {
        return value;
    }

    /**
     * <p>Tests for order.</p>
     *
     * <p>The default ordering is numeric by value, but this
     * can be overridden by subclasses.</p>
     *
     * <p>NOTE: From v2.2 the enums must be of the same type.
     * If the parameter is in a different class loader than this instance,
     * reflection is used to compare the values.</p>
     *
     * @see java.lang.Comparable#compareTo(Object)
     * @param other  the other object to compare to
     * @return -ve if this is less than the other object, +ve if greater than,
     *  <code>0</code> of equal
     * @throws ClassCastException if other is not an <code>Enum</code>
     * @throws NullPointerException if other is <code>null</code>
     */
    public int compareTo(Object other) {
        if (other == this) {
            return 0;
        }
        if (other.getClass() != this.getClass()) {
            if (other.getClass().getName().equals(this.getClass().getName())) {
                return value.compareTo(getValueInOtherClassLoader(other));
            }
            throw new ClassCastException(
                    "Different enum class '" + ClassUtils.getShortClassName(other.getClass()) + "'");
        }
        return value.compareTo(((StringValuedEnum) other).value);
    }

    /**
     * <p>Use reflection to return an objects value.</p>
     *
     * @param other  the object to determine the value for
     * @return the value
     */
    private String getValueInOtherClassLoader(Object other) {
        try {
            Method mth = other.getClass().getMethod("getValue", (Class[]) null);
            return (String) mth.invoke(other, (Object[]) null);
        } catch (NoSuchMethodException e) {
            // ignore - should never happen
        } catch (IllegalAccessException e) {
            // ignore - should never happen
        } catch (InvocationTargetException e) {
            // ignore - should never happen
        }
        throw new IllegalStateException("This should not happen");
    }

    /**
     * <p>Human readable description of this <code>Enum</code> item.</p>
     *
     * @return String in the form <code>type[name=value]</code>, for example:
     *  <code>JavaVersion[Java 1.0=100]</code>. Note that the package name is
     *  stripped from the type name.
     */
    public String toString() {
        if (iToString == null) {
            String shortName = ClassUtils.getShortClassName(getEnumClass());
            iToString = shortName + "[" + getName() + "=" + getValue() + "]";
        }
        return iToString;
    }
}
