package com.ieci.tecdoc.common.adapter;

/**
 * Interfaz que han de implementar los sistemas de terceros que se deseen
 * integrar con la aplicacion
 * 
 * 
 */
public interface PersonValidation {

	/**
	 * Operación de busqueda.
	 * 
	 * @param xmlSearchParameters
	 *            es un xml que contiene criterios de búsqueda de una persona
	 *            física o jurídica
	 * @return xml con la información de la o las personas físicas o juridicas
	 *         encontradas
	 * @throws Exception
	 * 
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>	&lt;xs:element name="Criterios"&gt; <br/></code>
	 * <code> 		&lt;xs:complexType&gt; <br/></code>
	 * <code> 			&lt;xs:sequence&gt; <br/></code>
	 * <code> 				&lt;xs:element name="ClausulaWhere" type="xs:string" minOccurs="0" msdata:Ordinal="1" /&gt; <br/></code>
	 * <code>				&lt;xs:element name="ClausulaOrder" type="xs:string" minOccurs="0" msdata:Ordinal="2" /&gt; <br/></code>
	 * <code> 				&lt;xs:element name="Criterio" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code> 					&lt;xs:complexType&gt; <br/></code>
	 * <code>						&lt;xs:sequence&gt; <br/></code>
	 * <code>							&lt;xs:element name="Campo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>							&lt;xs:element name="Operador" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>							&lt;xs:element name="Valor" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>						&lt;/xs:sequence&gt; <br/></code>
	 * <code>					&lt;/xs:complexType&gt; <br/></code>
	 * <code>				&lt;/xs:element&gt; <br/></code>
	 * <code>			&lt;/xs:sequence&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="tipoPersona" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="inicio" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="rango" type="xs:string" /&gt; <br/></code>
	 * <code>		&lt;/xs:complexType&gt; <br/></code>
	 * <code>	&lt;/xs:element&gt; <br/></code>
	 * <code>	&lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>		&lt;xs:complexType&gt; <br/></code>
	 * <code>			&lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>				&lt;xs:element ref="Criterios" /&gt; <br/></code>
	 * <code>			&lt;/xs:choice&gt; <br/></code>
	 * <code>		&lt;/xs:complexType&gt; <br/></code>
	 * <code>	&lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Personas: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="Personas" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code> &lt;xs:element name="Personas" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>   &lt;xs:complexType&gt; <br/></code>
	 * <code>     &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>       &lt;xs:element name="Persona"&gt; <br/></code>
	 * <code>         &lt;xs:complexType&gt; <br/></code>
	 * <code>           &lt;xs:sequence&gt; <br/></code>
	 * <code>             &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="Apellido1" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="Apellido2" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="TipoDoc" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>             &lt;xs:element name="NIF" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>           &lt;/xs:sequence&gt; <br/></code>
	 * <code>   	      &lt;xs:attribute name="inicio" type="xs:string" /&gt; <br/></code>
	 * <code>           &lt;xs:attribute name="fin" type="xs:string" /&gt; <br/></code>
	 * <code>            &lt;xs:attribute name="total" type="xs:string" /&gt; <br/></code>
	 * <code>	          &lt;xs:attribute name="rango" type="xs:string" /&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code> &lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code> &lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code> &lt;Criterios sesionId="65" personType=”1” inicio=”0” rango=”10”&gt; <br/></code>
	 * <code>		&lt;Criterio&gt; <br/></code>
	 * <code>			&lt;Campo&gt;NIF&lt;/Campo&gt; <br/></code>
	 * <code>			&lt;Operador&gt;Empieza por&lt;/Operador&gt; <br/></code>
	 * <code>			&lt;Valor&gt;57829000j&lt;/Valor&gt; <br/></code>
	 * <code>		&lt;/Criterio&gt; <br/></code>
	 * <code> &lt;/Criterios&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Personas: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Personas inicio=”0” fin=”2” total=”2” rango=”20”&gt; <br/></code>
	 * <code>		&lt;Persona&gt; <br/></code>
	 * <code>			&lt;Id&gt;12345&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Nombre&gt;Manuel&lt;/Nombre&gt; <br/></code>
	 * <code>			&lt;Apellido1&gt;Manuel&lt;/Apellido1&gt; <br/></code>
	 * <code>			&lt;Apellido2&gt;Manuel&lt;/Apellido2&gt; <br/></code>
	 * <code>			&lt;TipoDoc&gt;N&lt;/TipoDoc&gt; <br/></code>
	 * <code>			&lt;NIF&gt;45234567K&lt;/NIF&gt; <br/></code>
	 * <code>		&lt;/Persona&gt; <br/></code>
	 * <code>		&lt;Persona&gt; <br/></code>
	 * <code>			&lt;Id&gt;12645&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Nombre&gt;José&lt;/Nombre&gt; <br/></code>
	 * <code>			&lt;Apellido1&gt;López&lt;/Apellido1&gt; <br/></code>
	 * <code>			&lt;Apellido2&gt;García&lt;/Apellido2&gt; <br/></code>
	 * <code>			&lt;TipoDoc&gt;N&lt;/TipoDoc&gt; <br/></code>
	 * <code>			&lt;NIF&gt;55214867J&lt;/NIF&gt; <br/></code>
	 * <code>		&lt;/Persona&gt; <br/></code>
	 * <code>&lt;/Personas&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String search(String xmlSearchParameters) throws Exception;

	/**
	 * Método que cuenta el numero de personas fisicas o juridicas que coinciden
	 * con los criterios indicados
	 * 
	 * @param xmlSearchParameters
	 *            xml que contiene criterios de búsqueda de una persona física o
	 *            jurídica
	 * @return número de personas encontradas que cumplan el criterio de
	 *         busqueda.
	 * @throws Exception
	 * 
	 * <h4>Schema del xml de Criterios de busqueda:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>	&lt;xs:element name="Criterios"&gt; <br/></code>
	 * <code> 		&lt;xs:complexType&gt; <br/></code>
	 * <code> 			&lt;xs:sequence&gt; <br/></code>
	 * <code> 				&lt;xs:element name="ClausulaWhere" type="xs:string" minOccurs="0" msdata:Ordinal="1" /&gt; <br/></code>
	 * <code>				&lt;xs:element name="ClausulaOrder" type="xs:string" minOccurs="0" msdata:Ordinal="2" /&gt; <br/></code>
	 * <code> 				&lt;xs:element name="Criterio" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code> 					&lt;xs:complexType&gt; <br/></code>
	 * <code>						&lt;xs:sequence&gt; <br/></code>
	 * <code>							&lt;xs:element name="Campo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>							&lt;xs:element name="Operador" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>							&lt;xs:element name="Valor" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>						&lt;/xs:sequence&gt; <br/></code>
	 * <code>					&lt;/xs:complexType&gt; <br/></code>
	 * <code>				&lt;/xs:element&gt; <br/></code>
	 * <code>			&lt;/xs:sequence&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="tipoPersona" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="inicio" type="xs:string" /&gt; <br/></code>
	 * <code>			&lt;xs:attribute name="rango" type="xs:string" /&gt; <br/></code>
	 * <code>		&lt;/xs:complexType&gt; <br/></code>
	 * <code>	&lt;/xs:element&gt; <br/></code>
	 * <code>	&lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>		&lt;xs:complexType&gt; <br/></code>
	 * <code>			&lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>				&lt;xs:element ref="Criterios" /&gt; <br/></code>
	 * <code>			&lt;/xs:choice&gt; <br/></code>
	 * <code>		&lt;/xs:complexType&gt; <br/></code>
	 * <code>	&lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote>
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <blockquote>
	 * <code> &lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code> &lt;Criterios sesionId="65" personType=”1” inicio=”0” rango=”10”&gt; <br/></code>
	 * <code>		&lt;Criterio&gt; <br/></code>
	 * <code>			&lt;Campo&gt;NIF&lt;/Campo&gt; <br/></code>
	 * <code>			&lt;Operador&gt;Empieza por&lt;/Operador&gt; <br/></code>
	 * <code>			&lt;Valor&gt;57829000j&lt;/Valor&gt; <br/></code>
	 * <code>		&lt;/Criterio&gt; <br/></code>
	 * <code> &lt;/Criterios&gt; <br/></code>
	 * </blockquote>
	 */
	public Integer count(String xmlSearchParameters) throws Exception;

	/**
	 * Método que devuelve las direcciones de una persona física o jurídica
	 * 
	 * @param xmlParamId
	 *            identificador de la persona
	 * @return xml con las direcciones de la persona física o jurídica
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <br/>
	 * 
	 * <li> Lista de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="Domicilios" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Domicilios" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="Domicilio"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Poblacion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="CodPostal" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Provincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;2345&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Domicilios&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Calle Mayor 5 4º&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87658&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Plaza España 25 1º A&lt;/TipoVia&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;0&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>&lt;/Domicilios&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getAddresses(String xmlParamId) throws Exception;

	/**
	 * Método que devuelves las direcciones de un tipo indicado de una persona
	 * física o jurídica
	 * 
	 * 
	 * @param xmlParamId
	 *            identificador de la persona
	 * @param typeAddress
	 *            identificador del tipo de direccion
	 * @return xml con las direcciones del tipo indicado de la persona física o
	 *         jurídica
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote> </blockquote> </li>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * <li> Lista de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="Domicilios" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Domicilios" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="Domicilio"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Poblacion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="CodPostal" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Provincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;2345&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Domicilios&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Calle Mayor 5 4º&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87658&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Plaza España 25 1º A&lt;/TipoVia&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;0&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>&lt;/Domicilios&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getAddresses(String xmlParamId, int typeAddress)
			throws Exception;

	/**
	 * Metodo que devuelve las provincias
	 * 
	 * @param xmlParamId
	 *            xml que contiene los datos de session y la entidad en la que
	 *            vamos a buscar
	 * @return xml con las provincias
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Provincias: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="Provincias" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Provincias" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="Provincia"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Codigo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;2345&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Provincias: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Provincias&gt; <br/></code>
	 * <code>	&lt;Provincia&gt; <br/></code>
	 * <code>		&lt;Id&gt;57&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;28&lt;/Codigo&gt;  <br/></code>
	 * <code>		&lt;Nombre&gt;Madrid&lt;/Nombre&gt;  <br/></code>
	 * <code>	&lt;/Provincia&gt; <br/></code>
	 * <code>	&lt;Provincia&gt; <br/></code>
	 * <code>		&lt;Id&gt;58&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;29&lt;/Codigo&gt; <br/></code>
	 * <code>		&lt;Nombre&gt;Málaga&lt;/Nombre&gt; <br/></code>
	 * <code>	&lt;/Provincia&gt; <br/></code>
	 * <code>&lt;/Provincias&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getProvicies(String xmlParamId) throws Exception;

	/**
	 * Método que devuelve las ciudades
	 * 
	 * @param xmlParamId
	 *            identificador de la provincia
	 * @return xml con las ciudades
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Ciudades: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="Ciudades" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Ciudades" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="Ciudad"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Codigo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="IdProvincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * 
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;2345&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Ciudades: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Ciudades&gt; <br/></code>
	 * <code>	&lt;Ciudad&gt; <br/></code>
	 * <code>		&lt;Id&gt;57&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;28017&lt;/Codigo&gt; <br/></code>
	 * <code>		&lt;Nombre&gt;Madrid&lt;/Nombre&gt; <br/></code>
	 * <code>		&lt;IdProvincia&gt;28&lt;/IdProvincia&gt; <br/></code>
	 * <code>	&lt;/Ciudad&gt; <br/></code>
	 * <code>	&lt;Ciudad&gt; <br/></code>
	 * <code>		&lt;Id&gt;58&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;29001&lt;/Codigo&gt; <br/></code>
	 * <code>		&lt;Nombre&gt;Málaga&lt;/Nombre&gt; <br/></code>
	 * <code>		&lt;IdProvincia&gt;29&lt;/IdProvincia&gt; <br/></code>
	 * <code>	&lt;/Ciudad&gt; <br/></code>
	 * <code>&lt;/Ciudades&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getCities(String xmlParamId) throws Exception;

	/**
	 * Método para la creación de personas físicas y jurídicas.
	 * 
	 * @param xmlPersonInfo
	 *            xml que contendrá la información de las personas que se han de
	 *            crear
	 * @return identificador de la persona creada
	 * @throws Exception
	 * 
	 * <h4>Schema del xml:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Persona"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido1" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido2" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="TipoDoc" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="NIF" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Domicilios" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Domicilio" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Poblacion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="CodPostal" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Provincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>        &lt;xs:element name="EDirecciones" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="EDireccion" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="bloqueado" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="Persona" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * 
	 * </blockquote>
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Persona sesionId="65" bloqueada=”0”&gt; <br/></code>
	 * <code>	&lt;Id&gt;12345&lt;/Id&gt; <br/></code>
	 * <code>	&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>	&lt;Nombre&gt;Manuel&lt;/Nombre&gt; <br/></code>
	 * <code>	&lt;Apellido1&gt;Tavares&lt;/Apellido1&gt; <br/></code>
	 * <code>	&lt;Apellido2&gt;Casanueva&lt;/Apellido2&gt; <br/></code>
	 * <code>	&lt;TipoDoc&gt;2&lt;/TipoDoc&gt; <br/></code>
	 * <code>	&lt;NIF&gt;45234567K&lt;/NIF&gt; <br/></code>
	 * <code>	&lt;Domicilios&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Calle Mayor 5 4º&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>	&lt;/Domicilios&gt; <br/></code>
	 * <code>	&lt;EDirecciones&gt; <br/></code>
	 * <code>		&lt;EDireccion&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;609765234&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>		&lt;EDireccion eliminar=”1”&gt; <br/></code>
	 * <code>			&lt;Id&gt;87658&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;mmmm@uuu.com&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;3&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;0&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>	&lt;/EDirecciones&gt; <br/></code>
	 * <code>&lt;/Persona&gt; <br/></code>
	 * </blockquote>
	 */
	public String create(String xmlPersonInfo) throws Exception;

	/**
	 * Método para la modificación de personas físicas y jurídicas
	 * 
	 * @param xmlPersonInfo
	 *            xml que contendrá la información de las personas que se han de
	 *            modificar.
	 * @return identificador de la persona modificada.
	 * @throws Exception
	 * 
	 * <h4>Schema del xml:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Persona"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido1" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido2" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="TipoDoc" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="NIF" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Domicilios" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Domicilio" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Poblacion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="CodPostal" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Provincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>        &lt;xs:element name="EDirecciones" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="EDireccion" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="bloqueado" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="Persona" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote>
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Persona sesionId="65" bloqueada=”0”&gt; <br/></code>
	 * <code>	&lt;Id&gt;12345&lt;/Id&gt; <br/></code>
	 * <code>	&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>	&lt;Nombre&gt;Manuel&lt;/Nombre&gt; <br/></code>
	 * <code>	&lt;Apellido1&gt;Tavares&lt;/Apellido1&gt; <br/></code>
	 * <code>	&lt;Apellido2&gt;Casanueva&lt;/Apellido2&gt; <br/></code>
	 * <code>	&lt;TipoDoc&gt;2&lt;/TipoDoc&gt; <br/></code>
	 * <code>	&lt;NIF&gt;45234567K&lt;/NIF&gt; <br/></code>
	 * <code>	&lt;Domicilios&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Calle Mayor 5 4º&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>	&lt;/Domicilios&gt; <br/></code>
	 * <code>	&lt;EDirecciones&gt; <br/></code>
	 * <code>		&lt;EDireccion&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;609765234&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>		&lt;EDireccion eliminar=”1”&gt; <br/></code>
	 * <code>			&lt;Id&gt;87658&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;mmmm@uuu.com&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;3&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;0&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>	&lt;/EDirecciones&gt; <br/></code>
	 * <code>&lt;/Persona&gt; <br/></code>
	 * </blockquote>
	 */
	public String update(String xmlPersonInfo) throws Exception;

	/**
	 * Con esta operación se pretende recuperar la información completa
	 * correspondiente a una sola persona física o jurídica.
	 * 
	 * @param xmlParamId
	 *            identificador de la persona
	 * @return xml con información de la persona ( nombre , apellidos Nif, Cif,
	 *         direcciones, tipo, etc.).
	 * @throws Exception
	 * 
	 * <h4>Schema del xml:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="Persona"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Nombre" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido1" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Apellido2" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="TipoDoc" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="NIF" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>        &lt;xs:element name="Domicilios" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Domicilio" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Poblacion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="CodPostal" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Provincia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>        &lt;xs:element name="EDirecciones" minOccurs="0" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="EDireccion" minOccurs="1" maxOccurs="unbounded"&gt; <br/></code>
	 * <code>                &lt;xs:complexType&gt; <br/></code>
	 * <code>                  &lt;xs:sequence&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Direccion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Tipo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                    &lt;xs:element name="Preferencia" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>                  &lt;/xs:sequence&gt; <br/></code>
	 * <code>                &lt;/xs:complexType&gt; <br/></code>
	 * <code>                &lt;xs:attribute name="eliminar" type="xs:string" /&gt; <br/></code>
	 * <code>              &lt;/xs:element&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="bloqueado" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="Persona" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote>
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;Persona sesionId="65" bloqueada=”0”&gt; <br/></code>
	 * <code>	&lt;Id&gt;12345&lt;/Id&gt; <br/></code>
	 * <code>	&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>	&lt;Nombre&gt;Manuel&lt;/Nombre&gt; <br/></code>
	 * <code>	&lt;Apellido1&gt;Tavares&lt;/Apellido1&gt; <br/></code>
	 * <code>	&lt;Apellido2&gt;Casanueva&lt;/Apellido2&gt; <br/></code>
	 * <code>	&lt;TipoDoc&gt;2&lt;/TipoDoc&gt; <br/></code>
	 * <code>	&lt;NIF&gt;45234567K&lt;/NIF&gt; <br/></code>
	 * <code>	&lt;Domicilios&gt; <br/></code>
	 * <code>		&lt;Domicilio&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;Calle Mayor 5 4º&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Poblacion&gt;23&lt;/Poblacion&gt; <br/></code>
	 * <code>			&lt;CodPostal&gt;45678&lt;/CodPostal&gt; <br/></code>
	 * <code>			&lt;Provincia&gt;1&lt;/Provincia&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/Domicilio&gt; <br/></code>
	 * <code>	&lt;/Domicilios&gt; <br/></code>
	 * <code>	&lt;EDirecciones&gt; <br/></code>
	 * <code>		&lt;EDireccion&gt; <br/></code>
	 * <code>			&lt;Id&gt;87657&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;609765234&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;1&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;1&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>		&lt;EDireccion eliminar=”1”&gt; <br/></code>
	 * <code>			&lt;Id&gt;87658&lt;/Id&gt; <br/></code>
	 * <code>			&lt;Direccion&gt;mmmm@uuu.com&lt;/Direccion&gt;  <br/></code>
	 * <code>			&lt;Tipo&gt;3&lt;/Tipo&gt; <br/></code>
	 * <code>			&lt;Preferencia&gt;0&lt;/Preferencia&gt; <br/></code>
	 * <code>		&lt;/EDireccion&gt; <br/></code>
	 * <code>	&lt;/EDirecciones&gt; <br/></code>
	 * <code>&lt;/Persona&gt; <br/></code>
	 * </blockquote>
	 */
	public String getInfo(String xmlParamId) throws Exception;

	/**
	 * Obtenemos los tipos de documento de indentificacion de terceros para el
	 * tipo de tercero indicado
	 * 
	 * @param xmlParamId
	 *            identificador del tipo de tercero
	 * @return xml con la informacion de los tipos de documento
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Tipos de Documento: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="TipoDocumentos" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="TipoDocumentos" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="TipoDocumento"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Descripcion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="TipoPersona" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Codigo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * 
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;2345&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Tipos de documento: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;TipoDocumentos&gt; <br/></code>
	 * <code>	&lt;TipoDocumento&gt; <br/></code>
	 * <code>		&lt;Id&gt;2&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Descripcion&gt;NIF&lt;/Descipncion&gt; <br/></code>
	 * <code>		&lt;TipoPersona&gt;1&lt;/TipoPersona&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;N&lt;/Codigo&gt; <br/></code>
	 * <code>	&lt;/TipoDocumento&gt; <br/></code>
	 * <code>	&lt;TipoDocumento&gt; <br/></code>
	 * <code>		&lt;Id&gt;5&lt;/Id&gt; <br/></code>
	 * <code>		&lt;Descripcion&gt;Otros&lt;/Descipncion&gt; <br/></code>
	 * <code>		&lt;TipoPersona&gt;0&lt;/TipoPersona&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;X&lt;/Codigo&gt; <br/></code>
	 * <code>	&lt;/TipoDocumento&gt; <br/></code>
	 * <code>&lt;/TipoDocumentos&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getDocsType(String xmlParamId) throws Exception;

	/**
	 * Obtenemos los tipos de direcciones telematicas de terceros
	 * 
	 * @param xmlParamId
	 *            xml con los datos necesarios para la busqueda
	 * @return xml con los tipos de direccion
	 * @throws Exception
	 * 
	 * <h4>Schema de los xml</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="NewDataSet" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="ParamId"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:sequence&gt; <br/></code>
	 * <code>        &lt;xs:element name="Id" type="xs:string" minOccurs="0" msdata:Ordinal="0" /&gt; <br/></code>
	 * <code>      &lt;/xs:sequence&gt; <br/></code>
	 * <code>      &lt;xs:attribute name="sesionId" type="xs:string" /&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>  &lt;xs:element name="NewDataSet" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element ref="ParamId" /&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Tipos de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;xs:schema id="TipoDirecciones" xmlns="" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:msdata="urn:schemas-microsoft-com:xml-msdata"&gt; <br/></code>
	 * <code>  &lt;xs:element name="TipoDirecciones" msdata:IsDataSet="true" msdata:Locale="es-ES"&gt; <br/></code>
	 * <code>    &lt;xs:complexType&gt; <br/></code>
	 * <code>      &lt;xs:choice maxOccurs="unbounded"&gt; <br/></code>
	 * <code>        &lt;xs:element name="TipoDireccion"&gt; <br/></code>
	 * <code>          &lt;xs:complexType&gt; <br/></code>
	 * <code>            &lt;xs:sequence&gt; <br/></code>
	 * <code>              &lt;xs:element name="Id" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Descripcion" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>              &lt;xs:element name="Codigo" type="xs:string" minOccurs="0" /&gt; <br/></code>
	 * <code>            &lt;/xs:sequence&gt; <br/></code>
	 * <code>          &lt;/xs:complexType&gt; <br/></code>
	 * <code>        &lt;/xs:element&gt; <br/></code>
	 * <code>      &lt;/xs:choice&gt; <br/></code>
	 * <code>    &lt;/xs:complexType&gt; <br/></code>
	 * <code>  &lt;/xs:element&gt; <br/></code>
	 * <code>&lt;/xs:schema&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 * 
	 * 
	 * <h4>Ejemplo de uso:</h4>
	 * <ul>
	 * <li> Criterios de busqueda: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="utf-8"?&gt; <br/></code>
	 * <code>&lt;ParamId sesionId="65" entidad="000"&gt; <br/></code>
	 * <code>		&lt;Id&gt;&lt;/Id&gt; <br/></code>
	 * <code>&lt;/ParamId&gt; <br/></code>
	 * </blockquote> </li>
	 * <li> Lista de Tipos de Direcciones: <br/> <blockquote>
	 * <code>&lt;?xml version="1.0" encoding="UTF-8"?&gt; <br/></code>
	 * <code>&lt;TipoDirecciones&gt; <br/></code>
	 * <code>	&lt;TipoDireccion&gt; <br/></code>
	 * <code>		&lt;IdTel&gt;1&lt;/IdTel&gt; <br/></code>
	 * <code>		&lt;Descripcion&gt;&lt;![CDATA[Teléfono (fijo)]]&gt;&lt;/Descripcion&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;TF&lt;/Codigo&gt; <br/></code>
	 * <code>	&lt;/TipoDireccion&gt; <br/></code>
	 * <code>	&lt;TipoDireccion&gt; <br/></code>
	 * <code>		&lt;IdTel&gt;2&lt;/IdTel&gt; <br/></code>
	 * <code>		&lt;Descripcion&gt;&lt;![CDATA[Correo electrónico]]&gt;&lt;/Descripcion&gt; <br/></code>
	 * <code>		&lt;Codigo&gt;CE&lt;/Codigo&gt; <br/></code>
	 * <code>	&lt;/TipoDireccion&gt; <br/></code>
	 * <code>&lt;/TipoDirecciones&gt; <br/></code>
	 * </blockquote> </li>
	 * </ul>
	 */
	public String getAddressesType(String xmlParamId) throws Exception;

}
