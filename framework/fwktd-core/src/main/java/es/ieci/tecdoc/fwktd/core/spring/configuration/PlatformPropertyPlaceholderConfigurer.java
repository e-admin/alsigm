package es.ieci.tecdoc.fwktd.core.spring.configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
* <p>A small extension to org.springframework.beans.factory.config.PlatformPropertyPlaceholderConfigurer
* that, when asked to replace the placeholder "somevar", will first look for
* a property named "platformprefix.somevar", or failing a match will then look for
* the property "somevar".</p>
*
* <p>The "platformprefix" part of the placholder is derived from the Java system
* property "os.name". For convenience the "os.name" value is mapped to a
* prefix that is easier to type. For example, on Windows XP the value of "os.name"
* is "Windows XP" and this class maps "platformprefix" to "win".</p>
*
* <p>This class has a default set of mappings (see DEFAULT_PLATFORM_PREFIX_MAPPINGS)
* which can be overridden by setting the property platformPrefixMappings.</p>
*
* <p>See http://lopica.sourceforge.net/os.html for an extensive list of
* platform names used by the os.name Java system property.</p>
*/
public class PlatformPropertyPlaceholderConfigurer
extends PropertyPlaceholderConfigurer implements InitializingBean {

private static final Map DEFAULT_PLATFORM_PREFIX_MAPPINGS;

static {
    DEFAULT_PLATFORM_PREFIX_MAPPINGS = new HashMap();
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("AIX", "aix");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Digital Unix", "dec");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("FreeBSD", "bsd");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("HP-UX", "hp");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Irix", "irix");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Linux", "linux");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Mac OS", "mac");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Mac OS X", "mac");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("MPE/iX", "mpe");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Netware 4.11", "netware");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("OpenVMS", "vms");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("OS/2", "os2");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("OS/390", "os390");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("OSF1", "osf1");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Solaris", "sun");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("SunOS", "sun");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows 2000", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows 2003", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows 95", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows 98", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows CE", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows Me", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows NT", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows NT (unknown)", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows Vista", "win");
    DEFAULT_PLATFORM_PREFIX_MAPPINGS.put("Windows XP", "win");
}

private Map platformPrefixMappings;

private String platformPrefix;

private Map<String, String> resolvedProps;

/**
 * Override the default platform prefix mappings.
 *
 * @param platformPrefixMappings
 */
public void setPlatformPrefixMappings(Map platformPrefixMappings) {
    this.platformPrefixMappings = platformPrefixMappings;
}

@Override
protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
    Properties props) throws BeansException {
    super.processProperties(beanFactoryToProcess, props);
    resolvedProps = new HashMap<String, String>();
    for (Object key : props.keySet()) {
        String keyStr = key.toString();
//        resolvedProps.put(keyStr, this.parseStringValue(props.getProperty(keyStr), props,
//        		new HashSet()));
        resolvedProps.put(keyStr, resolvePlaceholder(keyStr, props));
    }
}

public Map<String, String> getResolvedProps() {
    return Collections.unmodifiableMap(resolvedProps);
}
/**
 * Attempt to determine the prefix to use for this platform.
 * First check any user defined prefix mappings. If no match
 * then check the default platform mappings.
 */
public void afterPropertiesSet() throws Exception {
    String platform = System.getProperty("os.name");
    if (platformPrefixMappings != null) {
        platformPrefix = (String) platformPrefixMappings.get(platform);
    }
    if (platformPrefix == null) {
        platformPrefix = (String) DEFAULT_PLATFORM_PREFIX_MAPPINGS.get(platform);
    }
}

/**
 * Override the PropertyPlaceholderConfigurer.resolvePlaceholder(...) method
 * to first look for a placeholder with the platform prefix.
 */
protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode) {
    String propVal = null;
    if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE) {
        if (platformPrefix != null) {
            propVal = resolveSystemProperty(platformPrefix + "." + placeholder);
        }
        if (propVal == null) {
            propVal = resolveSystemProperty(placeholder);
        }
    }
    if (propVal == null) {
        if (platformPrefix != null) {
            propVal = resolvePlaceholder(platformPrefix + "." + placeholder, props);
        }
        if (propVal == null) {
            propVal = resolvePlaceholder(placeholder, props);
        }
    }
    if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK) {
        if (platformPrefix != null) {
            propVal = resolveSystemProperty(platformPrefix + "." + placeholder);
        }
        if (propVal == null) {
            propVal = resolveSystemProperty(placeholder);
        }
    }
    return propVal;
}
}