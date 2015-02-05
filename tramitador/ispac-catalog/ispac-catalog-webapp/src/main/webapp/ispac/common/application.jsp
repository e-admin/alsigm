<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>

<logic:present name="application">
	<tiles:insert beanName="application" flush="true"/>
</logic:present>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>


