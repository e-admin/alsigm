<xsl:stylesheet
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format"
	version="1.0">

  	<!-- Import de cabecera especifica -->
  	<xsl:import href="./docbook.xsl"/>

  	<!-- Import de cabecera especifica -->
  	<xsl:import href="./header.xsl"/>

  	<!-- Import de pie de pagina especifico -->
  	<xsl:import href="./footer.xsl"/>

  	<!-- Import de opciones de capitulo -->
  	<xsl:import href="./chapter.xsl"/>

  	<!-- Import de opciones de las diferentes secciones -->
  	<xsl:import href="./section.xsl"/>

</xsl:stylesheet>