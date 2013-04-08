
--
-- Información de versión
--
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '6.3', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '6.3', current_timestamp);

--
-- Creación de tabla SPAC_CT_INFORMES_ORG 
--

CREATE SEQUENCE spac_sq_id_ctinformes_org INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE spac_ct_informes_org
(
  id          integer                           NOT NULL,
  id_informe  integer,
  uid_usr     character varying(32)
);


ALTER TABLE ONLY spac_ct_informes_org
	ADD CONSTRAINT pk_spac_ct_informes_org PRIMARY KEY (id);

	

--
--Se añade campo parametros a SPAC_CT_INFORMES y campo visibilidad ,
--

ALTER TABLE SPAC_CT_INFORMES ADD COLUMN frm_params text;
ALTER TABLE SPAC_CT_INFORMES ADD COLUMN visibilidad smallint DEFAULT 0;

--
-- Para aquellos informes ya existentes se establecerá a pública
--
UPDATE SPAC_CT_INFORMES SET VISIBILIDAD=1;


---
--- Gestion de Ayudas
---

CREATE SEQUENCE spac_sq_id_ayudas INCREMENT BY 1 NO MAXVALUE NO MINVALUE CACHE 1;

CREATE TABLE spac_ayudas
(
	id 			integer 				NOT NULL,
	nombre		character varying(100),
	tipo_obj 	integer,
	id_obj 		integer,
	idioma 		character varying(5),
	contenido   text					
);

ALTER TABLE ONLY spac_ayudas
	ADD CONSTRAINT pk_spac_ayudas PRIMARY KEY (id);

--Se define spac_ayudas como entidad no visible , el id que utilizamos debe ser por debajo de 1000 y que no este ya en uso
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (120, 0, 'SPAC_AYUDAS', 'ID', NULL, NULL, NULL, 'AYUDAS', 'SPAC_SQ_ID_AYUDAS');


--Ayudas por defecto para el tramitador

INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Bandeja Entrada', 0, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Frm Busqueda por defecto', 1, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Estado Expediente', 2, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Procedimiento por defecto', 3, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Trámite', 4, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Trámite', 5, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Plazos vencidos', 6, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Registros distribuidos', 7, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Resultado Busqueda ES por defecto', 8, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Avisos Electronicos', 9, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Registro Distribuido', 10, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Expedientes', 11, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Circuito Firma', 12, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Iniciar Expediente', 13, -1, null,'');

--Ayudas  para el catálogo
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Catálogo Procedimientos', 31, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo procedimiento', 32, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Fases', 33, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Trámites', 34, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento >Fases > Trámites', 35, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Resumen', 36, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Creado', 37, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso', 38, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo subproceso > Actividades', 39, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso > Resumen', 40, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso > Creado', 41, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Editor Gráfico', 42, -1, null,'');





---Actualizamos el campo contenido para todas las ayudas

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>BANDEJA ENTRADA</h4></div><p>La pantalla que se muestra es la pantalla principal o Bandeja de Entrada para la 
Gestión de Expedientes.</p><p>Se podrán realizar las siguientes acciones:<p><ul><li>Acceder a expedientes de los que es responsable, para continuar
su ramitación (<b>bandeja de entrada: Expedientes en su lista de trabajo</b>)</li><p><li>Realizar trámites concretos de un expediente, del que 
es responsable (<b>bandeja de entrada: Trámites abiertos en sus expedientes</b>)</li><p><li>Localizar expedientes que estén o no en tramitación
o no sean responsabilidad suya, pero tenga derecho de consulta sobre ellos, mediante el menú de la izquierda: <b>Búsqueda Avanzada</b></li>
<p><li>Iniciar un Expediente: menú de la izquierda: <B>Iniciar Expediente</B></li><p><li>Realizar utilidades relacionadas con el Portafirmas.
Desde el menú de la izquierda: <b>Portafirmas</b></li><p><li>Consultar los avisos electrónicos de tramitación (<b>bandeja de entrada: 
Información de sucesos que le afectan</b>)</li><p><li>Consultar los plazos vencidos (<b>bandeja de entrada: Tiene n plazos vencidos de su 
interés</b></li></ul>'
where IDIOMA IS NULL AND TIPO_OBJ=0;


update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>BÚSQUEDA AVANZADA</h4>
</div>
<P>Desde esta pantalla se pueden realizar búsquedas sobre los expedientes sobre los que usted tiene permisos.
<P>Pueden existir varios formularios de búsqueda distintos. Permiten buscar por diferentes campos de los expedientes.
<P>Para cambiar de formulario de búsqueda seleccionarlo en el combo contiguo a <B> ''Formularios de consulta''</B>.
<P>Una vez seleccionado el formulario deseado, cumplimentar los datos conocidos a buscar. En algunos campos se puede seleccionar el operador de búsqueda entre los siguientes:
<p><center>
<table border "2">
<tr><td>></td>
<td>Mayor que</td> </tr>
<tr><td><</td><td>Menor que</td></tr>
<tr><td>>=</td><td>Mayor o igual que</td></tr>
<tr><td><=</td><td>Menor o igual que</td></tr>
<tr><td>=</td><td>Igual que</td></tr>
</table>
</center>
<p>Si el campo a buscar es un texto y no se conoce el texto exacto, se puede introducir el carácter <b>*</B> como carácter comodín.
<p>Ejemplo:
<p>Si buscamos sobre el campo asunto el siguiente texto, el resultado será el siguiente:
<p><center>
<table border "2">
<tr><td>Subvención*</td>
<td>Asuntos que comiencen con la palabra Subvención</td> </tr>
<tr><td>*Subvención</td><td>Asuntos que finalicen con la palabra Subvención</td></tr>
<tr><td>*subvención*</td><td>Asuntos que contengan la palabra Subvención</td></tr>
</table>
</center>
<p>Una vez introducidos los criterios de búsqueda, hacer clic en la opción buscar. La aplicación mostrará una lista con los expedientes encontrados.' 
where IDIOMA IS NULL AND TIPO_OBJ=1;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>DISEÑADOR GRÁFICO</h4>
</div>
El <b>Diseñador Gráfico</b> es la herramienta que se proporciona para visualizar los procedimientos y facilitar la tarea al diseñador. Esta herramienta permite a los responsables de procesos y métodos de la organización 
	crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.
	<br><br>
	
	Desde la tramitación podremos observar el grado de avance del expediente comprobando que fases hemos
	ya realizado en cual estamos y las que nos quedan por realizar , en las fases ya realizadas o en la actual
	se podrá consultar los tramites que se han realizado asi como el detalle de cada una de las instancias de los
	trámites, y en caso de ser un subproceso podremos navegar hasta el mismo.
	<h6>Barra de herramientas</h6>
		<div>
		 Nombre del procedimiento : Estado  
		 Leyenda con el significado de los colores que puede tener cada una de las fases
		 Imagen de una impresora que nos permite imprimir lo que en ese momento tengamos en la pantalla
		 Icono de ayuda 
		</div>
	
	<br/><br/>
	
	<h6>Editor de condiciones </h6>
	<div>
	 Un flujo puede tener asociado una condición para ello se mostrará sobre el flujo un icono con un ?, pulsado sobre la flecha con el 
	 botón derecho del ratón se visualizará un listado de las condiciones Java (Reglas) como sobre BBDD (SQL) que el usuario haya asociado
	 al flujo , una condicion sql se podrá visualizar, pulsando sobre el nombre de la misma.'
where IDIOMA IS NULL AND TIPO_OBJ=2;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>FORMULARIO EXPEDIENTE</h4></div>
<p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física 
con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p>
<p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona
 inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li>
<p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.
</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá 
acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.
<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que 
se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li>
</ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p>
<p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li>
<p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li>
<p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li>
<p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ha pasado el expediente.</li>
<p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li>
</ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div>
<p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha 
interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:
<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li>
<p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li>
<p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li>
<p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li>
<p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una nueva ventana, las distintas unidades de plazo existentes, 
de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se
 utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.
<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b>
 para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el 
documento normalizado, con los datos del expediente incluidos en él
<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, 
cambiar una expresión, etc.</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li>
</ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra 
el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite
 abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará 
<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.
<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, 
se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.
<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.' 
where IDIOMA IS NULL AND TIPO_OBJ=3;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>LISTA DE TRÁMITES</h4></div><P>Muestra la lista de trámites de un tipo que se encuentran sin terminar. En la lista se puede ver
 el expediente y el procedimiento al que pertenecen, así como la fecha de inicio del trámite y la fecha límite si la tiene.
<P>Para situarse sobre el trámite hacer clic sobre el nombre del trámite. Abrirá el expediente que lo contiene y situará el
 contexto de la aplicación en el trámite.<P>La lista de trámites se puede exportar a ficheros de tipo: Excell, pdf, XML o csv. 
Para ello simplemente pulsar sobre el link correspondiente en la parte inferior de la lista. Este fichero no queda almacenado 
en el sistema, pero usted tiene la opción de guardar una copia del documento en su ordenador pulsando en la opción <b>''Guardar''</b> 
de la aplicación correspondiente en cada caso.'
where  IDIOMA IS NULL AND TIPO_OBJ=4;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO TRÁMITE</h4></div><p>En esta pantalla se muestra la lista de trámites que se pueden realizar 
en la fase actual en el expediente con el que está trabajando.</p><p>La última columna de la derecha muestra si ya se han creado 
trámites de ese tipo en el expediente.</p><p>Para crear un trámite nuevo hacer clic sobre el nombre del trámite.</p>'
where IDIOMA IS NULL AND TIPO_OBJ=5;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>PLAZOS</h4></div>
<P>Desde esta pantalla se puede realizar un seguimiento de los plazos vencidos. Muestra la lista de expedientes que tienen un plazo vencido.
Por cada uno de ellos muestra el número de expediente, tipo de procedimiento, tipo de plazo y fecha de vencimiento.<P>Para moverse al
expediente, hacer clic sobre el número de expediente.<P>Un plazo asociado a un trámite, dejará de aparecer en esta lista al terminar 
el trámite.<P>Desde esta pantalla se pueden buscar los plazos que vencieron o vencerán entre dos fechas. Para ello introducir la fecha
 inicial y final en la parte superior y hacer clic sobre <B>''Consultar''</B>. La fecha se ha de introducir en formato <B>dd/mm/aaaa</B>, o
 bien se puede seleccionar del calendario pulsando en el icono contiguo al campo fecha.<P>La lista de plazos se puede exportar a ficheros
 de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link correspondiente en la parte inferior de la lista. 
Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar una copia del documento en su ordenador pulsando 
en la opción ''Guardar como'' de la aplicación correspondiente en cada caso.' 
where IDIOMA IS NULL AND TIPO_OBJ=6;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA DE REGISTROS DISTRIBUIDOS</h4></div><P>Muestra la lista de registros distribuidos
 accesibles por el usuario tramitador.<P>Para situarse sobre el registro distribuido hacer clic sobre el número de registro.
<P>La lista de registros distribuidos se puede exportar a ficheros de tipo: Excel, PDF, XML o CSV. Para ello simplemente pulsar sobre
 el link correspondiente en la parte inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de
 guardar una copia del documento en su ordenador pulsando en la opción <B>''Guardar como''</B> de la aplicación correspondiente en cada caso.' 
where IDIOMA IS NULL AND TIPO_OBJ=7;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>LISTA DE EXPEDIENTES</h4>
</div>
<p>Muestra una lista de expedientes según el criterio de búsqueda seleccionado, a través de un formulario de búsqueda.</p>
<p>Cada fila de la lista se corresponde a un expediente, y muestra una información básica de este, como es el número, interesado principal,
 estado administrativo en el que se encuentra, etcétera.</p>
<P>Para entrar dentro de un expediente, hacer clic sobre el número de expediente. </p>
<P>La lista de expedientes se puede exportar a ficheros de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link
 correspondiente en la parte inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar 
una copia del documento en su ordenador pulsando en la opción ''Guardar como'' de la aplicación correspondiente en cada caso.</p>' 
where IDIOMA IS NULL AND TIPO_OBJ=8;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA DE EXPEDIENTES</h4></div><P>Esta pantalla le muestra una lista con los avisos 
electrónicos relacionados con expedientes de nuestra lista de trabajo. Estos avisos pueden provenir de otras aplicaciones, o por ejemplo 
nos puede indicar que se ha creado un expediente nuevo proveniente de registro telemático.<P>En la parte superior de la pantalla, se 
indica el número de avisos totales de su bandeja, justo debajo le indica los mensajes que se están mostrando en la pantalla actual. Desde
 esta misma fila, se pude mover por las distintas páginas de avisos, así como ir a la primera y última página.
<P>Un aviso puede tener tres estados: pendiente, en curso o finalizado. La lista sólo muestra los avisos pendientes (sin leer) o en curso.
<P>Para cambiar el estado de un aviso, de pendiente a en curso, hacer clic sobre la opción <B>''Recibir''</B>.
<P>Para cambiar el estado de un aviso a finalizado, pulsar sobre la opción <B>''Finalizar''</B>. Un aviso al pasar a este estado, 
desaparecerá de nuestra bandeja de avisos.<P>Los avisos están relacionados siempre con un expediente de nuestra lista de trabajo. 
Para que la aplicación nos mueva al expediente, pulsar sobre el número de expediente en la lista de avisos.<P>La lista de avisos
 se puede exportar a ficheros de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link correspondiente en la parte
 inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar una copia del documento 
en su ordenador pulsando en la opición ''Guardar como'' de la aplicación correspondiente en cada caso.' 
where IDIOMA IS NULL AND TIPO_OBJ=9;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>REGISTRO DISTRIBUIDO</h4></div><P>Muestra la información de un registro distribuido
 dividida en dos pestañas: Distribución y Registro de Entrada.<P>En la pestaña "Distribución", se muestra la información referente al
 registro distribuido en sí:<ul><li><b>Estado:</b> Estado del registro distribuido.</li><li><b>Fecha distribución:</b> Fecha de
 distribución del registro de entrada.</li><li><b>Mensaje:</b> Mensaje de la distribución.</li></ul><P>En la pestaña 
"Registro de Entrada", se muestra la información referente al registro de entrada relacionado con la distribución:
<ul><li><b>Número registro:</b> Número de registro de entrada.</li><li><b>Remitente:</b> Remitente del registro de entrada.</li>
<li><b>Asunto:</b> Asunto del registro de entrada.</li></ul><P>Las acciones que se pueden realizar sobre el registro distribuido 
dependen del estado del mismo:<ul><li>Si el estado es <b>pendiente</b>, las acciones que se muestran son:<ul><li><b>Aceptar</b>: Acepta
 el registro distribuido para seguir trabajando con él.</li><li><b>Rechazar</b>: Rechaza el registro distribuido.</li></ul></li><li>
Si el estado es <b>aceptado</b>, las acciones serán:<ul><li><b>Archivar</b>: Archiva el registro distribuido.</li>
<li><b>Iniciar Expediente</b>: Muestra la lista de procedimientos para iniciar un expediente a partir de la información del registro
 distribuido.</li><li><b>Anexar Expediente</b>: Muestra un formulario de búsqueda para seleccionar un expediente donde anexar los 
documentos del registro distribuido.</li></ul></li></ul>' 
where IDIOMA IS NULL AND TIPO_OBJ=10;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA EXPEDIENTES</h4></div><P>En esta pantalla se visualizarán los expedientes
 que se encuentren en una fase, para un procedimiento determinado.<p>Para visualizar los datos de un expediente se pulsará sobre
 el <b>Número de expediente</b> correspondiente.<p>La lista de expedientes podrá ser exportada a <b>cvs, Excel, xml y pdf</b> 
<p>Seleccionando uno o varios expedientes marcando los checks correspondientes de la izquierda de cada número de expediente 
y pulsando sobre <b>Acciones</b> del menú de la izquierda de la pantalla se podrá:<p><li><b>Terminar fases</b></li>
<p><li><b>Delegar fases</b></li><p><li><b>Iniciar tramitación agrupada</b></li>' 
where IDIOMA IS NULL AND TIPO_OBJ=11;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>PORTAFIRMAS</h4></div><P>Muestra una lista con los documentos pendientes de firmar
 electrónicamente. Por cada documento indica el expediente al que pertenece, el autor y el estado.<P>Para firmar uno o varios 
documentos desde esta pantalla, selecciónelos en la parte izquierda de la lista, luego pulsar en la opción <B>''Acciones''</B> del menú de
 la izquierda de la aplicación, y dentro de este, en la opción <B>''Firmar''</B>.<P>Siga las indicaciones de pantalla para realizar la firma 
correctamente.'
where IDIOMA IS NULL AND TIPO_OBJ=12;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>INICIAR EXPEDIENTE</h4></div><p>Esta pantalla muestra la lista de procedimientos
 sobre los cuales se pueden crear nuevos expedientes.<p><p>Para crear un nuevo expediente, hacer clic sobre el nombre del 
procedimiento.<p><p>Si considera que no aparece en la lista algún procedimiento, hágalo saber a su administrador de sistemas, 
es probable que no tenga asignados permisos sobre él.<p>' 
where IDIOMA IS NULL AND TIPO_OBJ=13;

update SPAC_AYUDAS
set CONTENIDO='<a class="enlace" name="inicio"></a><div class="cabecera_seccion"><h4>CATÁLOGO DE PROCEDIMIENTOS</h4></div>
<div class="titulo_ayuda"><label class="popUpInfo" >Información del catálogo de procedimientos</label></div>
<p>El <b>Catálogo de Procedimientos</b> es la herramienta de administración de procedimientos administrativos 
que proporciona SIGEM. Esta herramienta permite a los responsables de procesos y métodos de la organización 
crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.</p>
<p>El catálogo está compuesto  por el conjunto de <b>entidades</b> que identifican y caracterizan los procedimientos 
administrativos de la organización; así como por el conjunto de objetos que permiten estructurar y modelar 
dichos procesos siguiendo un modelo conceptual basado en <b>esquemas de tramitación</b> que se articulan con <b>fases, 
trámites y documentos</b>, conformando flujos semireglados.</p>
<p>El Catálogo contiene adicionalmente por cada objeto información de interés recogida en <b>fichas catalográficas</b>. 
Por ejemplo para un procedimiento administrativo se podrá recoger información de la legislación aplicable, 
órgano  o departamento que resuelve, plazos de resolución, forma de iniciación, efecto del silencio 
administrativo, etc. </p>
<p>Las principales funciones que permite el catálogo de procedimientos son:</p><ul> 
<li>Creación y mantenimiento de los elementos que conforman los procedimientos:
<ul> <li><a class="enlace" href="#ctstagesList">Fases</a>, <a class="enlace" href="#cttasksList">Trámites</a>,
<a class="enlace" href="#cttpdocsList">Tipos de documentos</a>,<a class="enlace" href="#plantillas">Plantillas</a>,
<a class="enlace" href="#batchSign">Procesos de Firma</a>, etc.</li> </ul> </li> <br/>
<li>Construcción y mantenimiento de <a class="enlace" href="#ctproceduresTree">Procedimientos</a>:
<ul><li>Alta de nuevos procedimientos por creación o clonación.</li> <li>Modificación de procedimientos existente.</li> 
<li>Mantenimiento de las fichas catalográficas.</li></ul></li><br/><li>Creación y mantenimiento de entidades de negocio
 asociadas a los procedimientos: <ul> <li><a class="enlace" href="#entities">Entidades</a>, <a class="enlace" href="#validationTables">
Tablas de Validación</a>,<a class="enlace" href="#rules">Reglas</a><a class="enlace" href="#searchForms">Formularios de Búsqueda</a>,
<a class="enlace" href="#calendars">Calendarios</a>,<a class="enlace" href="#reports">Informes</a>,<a class="enlace" href="#varSystem">
Variables del sistema</a>,y <a class="enlace" href="#helps">Ayudas</a>.</li></ul></li> <br/>
<li>Gestión del componente de <a class="enlace" href="#publisher">publicación</a>.</li></ul> <div id="ctstagesList">
<br/><a class="enlace" href="#inicio">Inicio</a><br/><br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Fases</label>
</div><br/>En el inventario de fases se pueden ver todas las fases dadas de alta en el sistema.<br><br>Pulsando sobre el <b>nombre</b> 
de la fase se pueden ver las <b>propiedades</b> de ésta:<br><br><br>El nombre de la fase será el que vea el tramitador de un procedimiento
 desde la herramienta de tramitación.<br><br>Mediante el botón ''<b>Trámites asociados</b>'' de la parte superior del formulario se
 pueden ver los trámites que se han asociado a la fase mediante el catálogo. Desde esta opción se pueden asociar nuevos trámites a la fase.
<br><br>Para asociar trámites a una fase en un procedimiento en concreto, previamente esos trámites han de estar asociados a la fase en el 
inventario.<br><br>Desde la opción ''<b>Ver uso</b>'' se verán los procedimientos que hacen uso de la fase actual.<br><br>
Se pueden modificar los datos de una fase mediante el formulario visto en la pantalla anterior. <br><br>Pulsando ''Aceptar'' estos cambios 
se guardan en el sistema.<br/></div><div id="cttasksList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda">
<label class="popUpInfo" >Inventario >> Trámites</label></div><br/>En el inventario de trámites pueden ver todos los trámites dados de alta 
en el sistema.<br><br>Pulsando sobre el <b>nombre</b> del trámite se pueden ver las propiedades del mismo:<br><br><br>
De los campos del trámite cabe destacar el nombre. Se corresponde con la etiqueta que verá el usuario desde el tramitador de expedientes.
<br><br>Se describen a continuación las opciones de la parte superior del formulario del trámite:<br><br><li><b>Aceptar</b>: guarda en base
 de datos los cambios que se introduzcan en el formulario.<br><br><li><b>Cancelar</b>: cancela los cambios, en caso de existir, y vuelve
 a la pantalla de inventario de trámites.<br><br><li><b>Documentos asociados</b>: muestra la relación de documentos asociados al trámite actual. 
Desde la pantalla de documentos asociados se pueden además asociar nuevos tipos de documentos al trámite.<br><br><br>
Para que un tipo de documento se vea desde un procedimiento dentro de un trámite, dicho trámite ha de tener el documento asociado desde 
esta opción.<br><br><li><b>Ver uso</b>: muestra la lista de procedimientos que tienen el trámite asociado, así como la fase en la que están.
<br><br><li><b>Eliminar</b>: permite eliminar un trámite del catálogo de trámites. No permitirá borrar trámites que se encuentren en uso.
<br/></div><div id="cttpdocsList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda">
<label class="popUpInfo" >Inventario >> Tipos de documentos</label></div><br>En el inventario de tipos de documentos se pueden ver todos 
los tipos de documentos dados de alta en el sistema.<br><br>Pulsando sobre el <b>nombre</b> del tipo de documento se pueden ver las 
propiedades del mismo.<br><br><br>Se detallan a continuación las opciones de la parte superior del formulario de edición de un tipo de 
documento:<br><br><li><b>Aceptar</b>: guarda en base de datos los cambios realizados en el formulario.<br><br><li><b>Cancelar</b>: 
cancela los cambios realizados en el formulario, si se han producido, y vuelve a la pantalla de lista de tipos de documentos.
<br><br><li><b>Plantillas</b>: desde esta opción se pueden ver las plantillas asociadas a un tipo de documento, así como asociar plantillas 
nuevas. <br><br><br>Las plantillas asociadas desde esta opción, se verán desde todos los procedimientos que tengan asociados los trámites que 
contengan el tipo de documento actual. Para asociar un tipo de documento a un procedimiento en concreto se usará la opción ''Crear plantilla''
dentro del contexto del procedimiento.<br><br><li><b>Ver uso</b>: muestra los trámites que tienen asociado este tipo de documento.
<br><br><li><b>Eliminar</b>: permite eliminar el tipo de documento. No permitirá borrar tipos de documento que estén en uso. 
<br><br><div class="aviso">Al borrar un tipo de documento borrará las plantillas asociadas a este.</div></div><div id="plantillas">
<br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Plantillas</label>
</div><br/>En el inventario de <b>Plantillas de documentos</b> se puede ver la lista de todas  las plantillas de documentos dadas de alta en 
el sistema, independientemente del tipo documental al que estén asociadas.En el listado se indica, para cada plantilla, el tipo de documento 
al que esta asociada, y si es específica o no. Desde esta pantalla se podrán crear nuevas plantillas (sólo de tipo  genérico), modificar las
existentes, o eliminarlas.Para ver la ficha de una plantilla se debe pulsar sobre el <b>nombre</b>.
Las opciones de esta pantalla son similares a las descritas en <a class="enlace" href="#cttpdocsList">Tipos de documentos</a><br/><br/>
</div><div id="ctsubprocesosList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >
Inventario >> Subprocesos</label></div><br/>En el inventario de <br>Subprocesos</b> se puede ver la lista de subprocesos dados de alta en el 
sistema.Se podrán crear nuevos subprocesos, modificar los existentes, o eliminar aquellos que no estén en uso.Pulsando sobre el <b>nombre</b> 
de un subproceso, podremos acceder al mismo.Las acciones que se pueden realizar sobre un subproceso son:<ul><li><b>Editor gráfico</b>: 
Permite visualizar el subproceso con sus actividades y flujos.</li><li><b>Mostrar información extendida</b>: Recarga la parte izquierda de la
 ventana mostrando información acerca del flujo existente entre las actividades.</li><li><b>Clonar</b>: Crea un nuevo subproceso a partir del 
actual.</li><li><b>Añadir actividad</b>: En el caso de que el subproceso esté en estado borrador se ofrece la posibilidad de añadir nuevas 
actividades. </li></ul>En caso contrario esta opción no estaría visible.</li>Para poder asociar un subproceso a un trámite, debe estar en 
estado vigente.En la pestaña de ficha podremos realizar las siguientes acciones:<ul><li><b>Eliminar Borrador</b>: Se eliminará la versión 
actual del subproceso, volviendo a una versión anterior,si es que existiera o eliminando definitivamente el subproceso en caso contrario. 
</li><li><b>Pasar a vigente</b>: Para asociar un subproceso a un trámite es necesario que esté en estado vigente,si aún está en estado borrador
 mediante esta opción se pasa a vigente.</li><li><b>Eliminar</b>: Cuando un subproceso está en estado vigente lo podremos eliminar siempre y 
cuando no este asociado a ningún trámite.</li></ul>En la pestaña de propiedades se muestra el listado de versiones del subproceso e información 
del subproceso.El nombre del subproceso será lo que verá el usuario en la selección del subproceso asociado a un trámite.En la pestaña de 
eventos se muestra el listado de eventos asociados al subproceso.</div><div id="batchSign"><br/><a class="enlace" href="#inicio">Inicio</a>
<br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Procesos de firma</label></div><br/>En el <b>inventario de procesos de 
firma</b> se pueden ver todos los procesos de firma dados de alta en el sistema.Estos procesos serán usados en el sistema tramitador para 
establecer el circuito de firma que deben seguir determinados documentos.<br/><br/>Para definir un proceso de firma, deberemos seleccionar 
los <b>firmantes</b>, y establecer su orden concreto en el circuito; podremos añadir, eliminar o sustituir firmantes.Un firmante será un 
elemento de la <b>organización</b> del sistema: usuario, departamento...</div><div id="ctproceduresTree"><a class="enlace" href="#inicio">
Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Procedimientos</label></div><br/>En la parte central de la 
pantalla muestra el árbol de procedimientos, organizado por familias de procedimientos. Desde esta pantalla se podrán crear nuevas familias, 
o acceder a la información de cada procedimiento.<br/><br/>Pulsando sobre el nombre del procedimiento se accede a la siguiente información:
<br/><ul> <li><a class="enlace" href="#pcdFlow">Esquema de tramitación</a>:<br/><br/><ul> <li>Fases, Trámites, Tipos de documentos y 
Plantillas</li></ul></li><br/><li>Información específica de cada elemento del esquema de tramitación:<br/><br/><ul><li><a class="enlace" 
href="#pcdCard">Procedimiento</a></li><br/><li><a class="enlace" href="#stgCard">Fase</a></li><br/><li><a class="enlace" href="#tskCard">
Trámite</a></li><br/><li><a class="enlace" href="#tpdcCard">Tipo de documento</a></li><br/><li><a class="enlace" href="#tmpltCard">Plantilla</a>
</li><br/></ul></li></ul><br/><br/></div><div id="entities"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label 
class="popUpInfo" >Componentes >> Entidades</label><br/>Para la parametrización de procedimientos administrativos se cuenta con 
<b>Entidades</b>, que permiten almacenar información estructurada de los distintos expedientes.<br><br>Existen una serie de entidades 
precargadas que son comunes a todos los procedimientos, aunque adicionalmente se pueden crear nuevas entidades para cubrir los datos 
específicos de de cada caso.<br><br>En el <b>catálogo de Entidades</b> se muestran todas las entidades dadas de alta en el sistema.
<br><br>Al <b>crear</b> una entidad desde el catálogo de procedimientos se creará una tabla en base de datos, con los campos introducidos
 por el usuario, más una serie de campos de control requeridospor el sistema.<br><br>Pulsando sobre el <b>nombre</b> de la entidad, se 
accede a las <b>propiedades</b> de la misma:<br><br><ul><li><b>Datos generales de la entidad</b>:</li><br><br><ul>
<li>Nombre: Nombre que se utilizará en SIGEM para hacer referencia a la entidad.<li>Tabla BD</li>: Indica el nombre de la tabla en la 
base de datos.<li>Descripción: Objetivo de la entidad.</ul><br><br>
<li><b>Campos de la entidad</b>: en esta ventana se visualizan los distintos campos de la entidad. Para crear uno nuevo pulsar la opción 
''Añadir''.<br><br><ul><li>Etiqueta</li>: Nombre que tendrá el dato en los formularios <li>Columna BD</li>: Nombre que tendrá la columna en 
la base de datos.<li>Descripción</li>: Objetivo del campo. <li>Tipo</li>: Tipo de campo, seleccionable de una lista de valores disponibles.
<li>Tamaño</li>: Tamaño del campo, en caso de seleccionar un campo de tipo texto o decimal <li>Precisión</li>: Precisión del campo, en caso 
de seleccionar un campo de tipo decimal</ul><br><br><li><b>Índices de entidad</b>: si se desea se pueden crear índices para algunos de los 
campos creados en el apartado anterior. Será interesante crear un índice sobre un campo cuando este se utilice mucho en las búsquedas y esté
 previsto que el volumen de datos de la entidad sea elevado.<br><br><ul><li>Clave única</li>: indica que el valor de un campo no se pueda
 repetir.</ul><br><br><li><b>Validaciones de la entidad</b>: se utiliza esta opción cuando el valor de un campo está validado contra una serie
 de valores normalizados, en lugar de permitir que el usuario lo introduzca a mano. La validación se realiza contra tablas de validación, 
creadas y mantenidas en el propio sistema:<br><br><ul><li>Tabla</li>: se indica la tabla sobre la que se realizará la validación
</ul><br>Se ha de indicar además si es obligatorio que el campo tenga algún valor, o si porel contrario puede estar vacío.
<br><br><br><li><b>Formularios de la entidad</b>:Los formularios son empleados en el tramitador para que el usuario visualice e introduzca 
datos para una o varias entidades.<br><br>Un formulario inicialmente tiene una entidad principal, y se crea asociado a la misma, 
aunque puede combinar datos de varias de ellas.<br><br>Pulsando sobre el <b>nombre</b> de un formulario se pueden ver las <b>propiedades</b> 
de ésta:<br><br><ul><li>Nombre</li>: nombre con que se identificará el formulario<br><br><li>Descripción</li>: nombre que verá el usuario en el
 tramitador de expedientes en la pestaña correspondiente al formulario.<br><br><li>Clase</li>: este campo viene cumplimentado por el sistema,
 aunque se permite cambiar. Indica la clase que va a implementar el formulario.<br><br><li>Parámetros</li>: Se pueden indicar, por ejemplo, 
entidades secundarias para el formulario o validaciones sobre los campos de la entidad, necesarias para la clase definida anteriormente.
<br><br>Por defecto vienen definidas las validaciones usadas en los campos validados de la entidad, para que en la visualización del formulario 
se muestren los controles de validación correspondientes.<br><br><li>Formateador</li>: este campo permite definir qué campos se van a 
visualizar cuando se muestren listas de entidades. Las listas de entidades se muestran cuando la relación de la entidad con el expediente es 
de n a 1.<br><br><li>Ubicación en servidor</li>: Indica la ruta donde se almacena el formulario en el servidor; se deberá
modificar este valor si se desea almancenarlo en otra ubicación.</ul><br>Al crear una entidad, automáticamente se crea un <b>formulario por
 defecto</b> o <b>formulario de entidad</b>que muestra todos los campos de esta, y que se podrá utilizar desde los procedimientos. <br><br>
Todo formulario se puede modificar, y se pueden crear cuantos formularios se deseen por entidad;que se basarán en el ''formulario de entidad''
 creado por defecto.<br><br><div class="aviso">Si se hacen cambios en la estructura de la entidad (nuevos campos o validaciones), se deberá 
regenerar el formulario de la entidad, y posteriormente regenerar los formularios creados a partir del mismo; si se han hecho cambios de diseño
 o código se perderán, por lo que se recomienda guardar el diseño y/o código de los mismos para volver a introducir los cambios en los 
formularios regenerados.</div><br>Para <b>modificar un formulario</b>, desde la lista de formularios de una entidad, se pulsa sobre la 
<b>ruta del formulario</b>. Se mostrará una ventana que permite descargar el formulario, tanto su diseño .html como su código .jsp, a un 
directorio local.<br><br><div class="aviso">Sobre el diseño del formulario en formato .html, se podrá editar y modificar con cualquier editor
 de formularios, el aspecto de los campos, incluso introducir nuevos elementos, pero no se pueden cambiar los nombres ni tipos de los campos 
existentes.<br>Igualmente se podrá modificar el código .jsp, para por ejemplo, introducir nuevos controles en el formulario, o validaciones 
javascript.</div><br>Una vez modificado el formulario, se puede <b>actualizar</b> en el catálogo desde la misma ventana que se descargó. 
Se pulsará ''Examinar'' para localizar el formulario en el disco duro local, y ''Actualizar'' para subirlo al servidor y sustituir el 
existente. El sistema notificará con una alarma si se han modificado datos de la estructura y no puede actualizar correctamente el formulario.
<br><br>Una vez hechos los cambios, el formulario estará disponible para ser usado en el tramitador.</ul></div></div><div id="validationTables">
<a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >Componentes >> Tablas de validación</label>
</div><br/>En el <b>catálogo de Tablas de Validación</b> se muestran todas las tablas de validación dadas de alta en el sistema. Las tablas de 
validación se usarán en las entidades, para garantizar que los valores de un determinado campo serán elegidos entre unos determinados, 
definidos en dicha tabla de 	validación. <br><br>Al crear una tabla de validación, se creará en base da datos la tabla física 
correspondiente.<br><br><br>Hay disponibles dos tipos de tablas de validación: <b>simples</b> y <b>con sustituto</b>: <br><br>
Las <b>tablas de validación simples</b> son listas de valores posibles para un campo.<br><br>Las <b>tablas de validación con sustituto</b> 
recogen pares valor-sustituto; el campo valor será almacenado en base de datos, mientras que en los formularios aparecerá el sustituto 
correspondiente,para un campo con este tipo de validación.<br><br>Al dar de alta una tabla de validación se pedirá la siguiente información:
<br><br><ul><li><b>Tabla</b>: nombre de la tabla en base de datos</li><br><br><li><b>Nombre</b>: nombre en la aplicación para la tabla de 
validación . Para crear uno nuevo pulsar la opción ''Añadir''.<br><br><li><b>Tipo</b>: simple o con sustituto. <br><br>	<li><b>Tamaño campo
 VALOR</b>: tamaño de dicho campo, por defecto será un texto de 10 caractéres.	<br><br>		<li><b>Tamaño campo SUSTITUTO</b>: tamaño de 
dicho campo, en las tablas con sustituto.	<br><br>	</ul>	Una vez creada una tabla, se puede visualizar su información, y añadir, 
modificar o eliminar <b>valores</b>	a la misma, así como marcar un valor como <b>vigente</b>. Un valor no vigente no se visualizará	entre los 
disponibles para la validación de un campo.	</div>	<div id="rules">		<a class="enlace" href="#inicio">Inicio</a>		
<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Reglas</label>		</div>		<br/>		
En el <b>catálogo de Reglas</b> se muestran todas las reglas dadas de alta en el sistema.	<br>		
Una regla es una <b>clase java</b> que implementa el interfaz <b>IRule</b>, definido en la documentación del sistema.	<br><br>	
Para dar de alta una regla, anteriormente hay que <b>implementar</b> su clase java de acuerdo a la estructura definida en la documentación, 
y almacenarla en una ruta accesible desde el servidor de aplicaciones.	<br><br>		Una vez realizado esto, desde el catálogo de reglas, 
pulsar <b>Nueva regla</b>, que mostrará una pantalla 	donde se solicitará la siguiente información:	<br><br>	<ul>
<li><b>Nombre</b>:</li> nombre de la regla. No puede tener espacios en blanco, ya que será 	invocada desde los tag de documento.
<br><br>	<li><b>Descripción</b>: indicaciones de la funcionalidad.	<br><br><li><b>Clase</b>: indica la ruta donde se ha dejado la clase
 en el servidor. 	<br><br>	</ul>	<br>		<div class="aviso">	Se debe consultar el manual de usuario del Catálogo de Procedimientos
 para obtener la definición del interfaz IRule.	</div><br>	</div>	<div id="searchForms">		<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Formularios de búsqueda</label>		</div>		<br/>		En el <b>catálogo de Formularios de búsqueda</b> se muestran todas los formularios de búsqueda 	dados de alta en el sistema. Estos formularios se podrán seleccionar en el sistema tramitador	para realizar las búsquedas, y en ellos definiremos sobre qué campos de las entidades de los	procedimientos queremos aplicar los criterios.	<br><br>	
Para crear un formulario de búsqueda deberemos importar un xml con su <b>definición</b>, o bien	incorporarla directamente en el campo de texto correspondiente.	<br><br>	</div>			<div id="calendars">		<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Calendarios</label>		</div>		<br/>		Para el cálculo de plazos de ejecución de los distintos elementos de la tramitación (expediente, fase y trámite), 		cuando sea necesario descontar los días inhábiles, es necesario hacer uso del calendario correspondiente asociado		al órgano de tramitación. Para ello se proporciona una gestión de días considerados no laborables de la semana y		días festivos anuales agrupados en un calendario. 		La creación de un nuevo calendario es posible realizarle desde cero, añadiendo los festivos e identificando los		días no laborables de la semana o clonando un calendario ya existente. 
En cualquier de estos casos, una vez creado		se pueden añadir los considerados festivos fijos, que son aquellos días que se consideran que 
son festivos para 		todos los años y para todas las provincias, localidades, etc.	</div>			<div id="reports">		
<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">	<label class="popUpInfo" >Componentes >> Informes</label>	
</div>		<br/>		En el <b>catálogo de Infomes</b> se muestra el listado de informes dados de alta en el sistema.	Los informes podrán 
ser de cuatro tipos:		<ul>		<li>Específicos: informes que, para estar disponibles, han de asociarse explícitamente a un 
procedimiento, bien a nivel general, o bien en el contexto de una de sus fases o trámites. </li><li>Genéricos: informes que estarán 
disponibles en todos los procedimientos, desde cualquiera 		de sus fases y trámites.</li><li>Globales: informes que estarán disponibles
 en la página inicial del tramitador.</li>		<li>Búsqueda: informes que estarán disponibles al mostrar el resultado de la búsqueda, si el
 informe ha sido asociado al formulario de búsqueda.</li>		Los informes se darán de alta para su posterior uso en la definición de los 
procedimientos, 		formulario de búsqueda o en la bandeja de entrada.</div><div id="varSystem"><a class="enlace" href="#inicio">Inicio</a>
<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Componentes >> Variables Sistema</label></div><br/>
En el <b> catálogo de variables de sistema</b>, se definen las variables que  almacenarán información de configuración para el correcto
 funcionamiento del sistema	</div><div id="helps"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda">	<label 
class="popUpInfo" >Componentes >> Ayudas</label></div><br/>En el <b>catálogo de Ayudas</b> se muestra el listado de ayudas dadas de alta en 
el sistema.		Pulsando sobre el <b>nombre</b> de la ayuda se pueden ver las propiedades del mismo:<br><br><br>De los campos de la ayuda el 
más destacado es <b>Contenido</b> ya que el texto que aquí se indique		será el que el usuario visualize cuando pulse sobre el icono de 
ayuda.	</div>	<div id="pcdFlow"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >
Procedimiento >> Esquema de tramitación</label></div><br/>El esquema de tramitación de un procedimiento se conforma con una serie de fases 
secuenciales, cada una	con unos determinados trámites asociados. Esta información se define en el momento de la creación del 	procedimiento; 
no así los tipos de documentos que son genéricos por trámite. Las plantillas para cada	tipo de documento pueden ser genéricas (creadas a 
través del inventario), o específicas para uno o varios	procedimientos.	<br><br>		Seleccionando un elemento del esquema de tramitación, 
veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; así como las acciones 
posibles para cada contexto:	<br><br>		<li><b>Procedimiento</b></li>: 	<br><br>	<b>Clonar procedimiento</b>	<br><br>
Se creará un procedimiento clonado del actual, pudiendo en el proceso modificarse la asociación	de fases y trámites. Así mismo se
puede definir si el nuevo procedimiento conformará una nueva 	familia, o será hermano o hijo del actual.	<br><br><li><b>Fase</b></li>
<br><br><b>Añadir trámite</b><br><br>Se permite añadir un trámite a la fase actual, seleccionando entre aquellos definidos en el	
inventario para dicha fase.	<br><br>		<li><b>Trámite</b></li>	<br><br>	<b>Eliminar trámite</b>	<br><br>Se permite desasociar el 
trámite actual de su fase correspondiente dentro del contexto del procedimiento. 	Esta acción no implica la eliminación del trámite, que
 quedará asociado a la misma fase en el inventario.	<br><br>		<li><b>Tipo de documento</b></li>	<br><br>	
En el árbol del procedimiento se verán las plantillas dependiendo del tipo de documento seleccionado: 	se mostrarán tanto las genéricas 
como las específicas al procedimiento. <br><br>		<b>Crear plantillas asociadas a un procedimiento</b>	<br><br>		
Para crear plantillas asociadas a un procedimiento, hay que situarse en el árbol del 	procedimiento, en un tipo de documento concreto, 
y pulsar ''Crear plantilla''. 	<br><br>		Esta acción creará una plantilla accesible únicamente desde el procedimiento actual, 
aunque	se podrá gestionar desde el inventario de tipos de documentos, y asociar a otros procedimientos	a través de la opción 
"Añadir plantilla".	<br><br>		<b>Añadir a un procedimiento una plantilla existente</b><br><br>Además de poder crear plantillas 
específicas para un procedimiento también se pueden asociar otras 	ya existentes en otros procedimientos.<br><br>Para ello desde el árbol 
del procedimiento, pulsar sobre el tipo de documento. En la parte superior 	pulsar la opción ''Añadir plantilla''.<br><br>El sistema mostrará
 las plantillas asociadas a ese tipo de documento en otros procedimientos. 	Seleccionarndo el deseado se añadirá al prodecimiento actual.<br>
<br>	<div class="aviso">		Al asociar una plantilla a varios procedimientos, hay que tener en cuenta que cualquier modificación a dicha
 plantilla afectará a todos los procedimientos implicados.	</div><br>	<li><b>Plantilla</b></li><br><br><b>Eliminar Plantilla</b><br><br>
Para las plantillas específicas, se permite eliminar la asociación al procedimiento actual.	<br><br><div class="aviso">	Si se elimina una 
plantilla asociada únicamente a un procedimiento, esta plantilla se borrará		físicamente.	</div><br>	</div>	<div id="pcdCard"><br/>
<a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >Esquema del Procedimiento >> Fichas>> Procedimiento
</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su información específica (ficha catalográfica,
propiedades...) desplegada en la parte derecha de la pantalla; dicha información se clasifica en función de	su naturaleza en distintas 
apartados que describiremos a continuación.<br><br><ul><li><b>Procedimiento</b>: <br><br><ul><li><b>Ficha</b></li><br><br>El procedimiento 
tiene un formulario asociado con los datos de éste. Estos datos se corresponden en su mayoría a 	datos normativos y de clasificación. 
Cabe destacar de cara a la tramitación:	<br><br>		<ul>	<li><b>Datos Normativos -> Documentación a aportar</b></li>: se trata de un 
campo en el que cada línea se 	corresponde con un documento que el ciudadano ha de aportar para solicitar un expediente de este 	
tipo de procedimiento. Esta información permite que el tramitador realice la subsanación de forma sencilla.	<br><br>	</ul>	
Un procedimiento en estado <b>Borrador</b> deberá ser <b>pasado a Vigente</b> para poder ser usado	desde el tramitador de expedientes. 
Si por el contrario no se desea conservar el procedimiento, 	podrá ser eliminado siempre que esté en estado de borrador.
<br><br>Para un procedimiento <b>Vigente</b>, podremos crear un borrador que posteriormente podremos modificar	y a su vez pasar a vigente: 
la versión actual pasaría en ese caso a ser <b>histórica</b>.	<br><br>		<li><b>Propiedades</b></li>	<br><br>
Se deberá establecer el responsable de un procedimiento para, al iniciar un expediente desde el tramitador, poder establecer la persona,
 grupo o departamento responsable de su tramitación.	<br><br>		Desde esta pantalla podremos acceder a las distintas versiones 
existentes del procedimiento actual.	<br><br>	<li><b>Eventos</b></li>	<br><br>	Mediante la pestaña <b>Eventos</b>, 
se podrán asociar reglas a distintos momentos de la tramitación. 	Las reglas a asociar se dan de alta en el propio catálogo, 
Componentes >> Reglas. <br><br>	Los eventos posibles de un expediente son los siguientes:	<br><br>	<ul>	<li><b>Iniciar</b></li>: 
se dispara este evento cuando se inicia un expediente	<br><br>		<li><b>Terminar</b></li>: al cerrar la última fase del expediente.	
<br><br>		<li><b>Reubicar</b></li>: al devolver una fase.	<br><br>		<li><b>Expirar plazo</b></li>: cuando se vence el plazo de 
resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza al crear el expediente cuando calcula el 
responsable.	<br><br>	<li><b>Calcular número de expediente</b></li>: al iniciar el expediente en la acción de calcular el número 
de expediente.	<br><br>	</ul>	 Se podrá indicar el orden de ejecución de las reglas, así como borrar la asociación de eventos y reglas
al procedimiento.<br><br><li><b>Entidades</b></li><br><br>Si el procedimiento necesita datos de negocio específicos, se han de crear las 
entidades necesarias, 	y asociarlas una vez creado el procedimiento mediante la opción <b>Agregar</b>.<br><br>Por defecto el procedimiento
 incluirá las entidades: expedientes, trámites, intervinientes y documentos. 	Si el procedimiento se ha clonado a partir de otro, además
 incluirá las entidades específicas que tuviera éste.	<br><br>	A cada una de las entidades se le debe asignar un formulario que será 
usado en la visualización de un 	expediente de este tipo en la tramitación. Al agregar una entidad, se le asigna el formulario por 	
defecto correspondiente, pudiendo ser modificado por el usuario.	<br><br>	<li><b>Permisos</b></li><br><br>Para que un procedimiento
 sea instanciable en el tramitador por un usuario o grupo de usuarios, hay que 	darles el permiso correspondiente. <br><br> Esto se gestiona
 desde la pestaña ''Permisos'': 	la pantalla muestra los permisos que ya están asignados (el caso de crear el procedimiento a partir de 	otro, 
heredará los permisos del procedimiento original), y permite eliminarlos o añadir nuevos.<br><br>Para asignar nuevos permisos se debe pulsar
 la opción <b>Asignar permisos</b>:	<br><br>	Desde aquí se pueden asignar permisos a departamentos, grupos y usuarios. Pulsando sobre
 <b>''Organización''</b>	se accede a los departamentos de la organización.	<br><br>		Pulsando "<b>Grupos</b>", se accede a los
 grupos de la organización. 	<br><br>		En ambos casos, para bajar a un nivel inferior, se debe pulsar sobre el nombre del 
grupo/departamento. 	Cuando se haya situado en el deseado, se debe pulsar ''Seleccionar'', y seleccionar la opción "Iniciar 	expedientes".
	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un expediente. Esto hará que el tramitador envíe 
alarmas cuando el 	expediente se exceda del plazo de resolución.	<br><br>	Se puede definir el plazo bien sobre la fecha de inicio de
 un expediente, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las entidades asociadas al procedimiento, o sobre 
una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>	<div id="stgCard">		<br/>		
<a class="enlace" href="#inicio">Inicio</a>		<br/>			<div class="titulo_ayuda">			<label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Fase</label>		</div>		<br/>		Seleccionando un elemento del esquema de tramitación, 
veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información 
se clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br>		<ul>	<li><b>Fase</b>: 
	<br><br>	<ul>	<li><b>Ficha</b></li>	<br><br>	Se muestran los datos identificativos de la fase, recogidos del inventario de fases.
	<br><br>		<li><b>Propiedades</b></li>	<br><br>	Se podrá indicar el responsable de la fase para, al iniciarse en el tramitador	
esta fase para un expediente, se establezca una persona, grupo o departamento 	como responsable de la tramitación.	<br><br><li><b>Eventos</b>
</li>	<br><br>	Mediante la pestaña <b>Eventos</b>, se podrán asociar reglas a distintos momentos de la tramitación. Las reglas a asociar
 se dan de alta en el propio catálogo, Componentes >> Reglas. <br><br>	Los eventos posibles para una fase son los siguientes:	<br><br>	
<ul>	<li><b>Iniciar</b></li>: se dispara este evento cuando se inicia la fase	<br><br>		<li><b>Terminar</b></li>: 
al cerrar la fase	<br><br>		<li><b>Reubicar</b></li>: al devolver una fase.	<br><br>		<li><b>Expirar plazo</b></li>: 
cuando se vence el plazo de resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza, al crear la fase, 
cuando se calcula el responsable.	<br><br>	<li><b>Utilizar plantilla</b></li>: al utilizar una plantilla para un documento.<br><br><li>
<b>Crear documento</b></li>: al crear un documento.	<br><br></ul>Se podrá indicar el orden de ejecución de las reglas, así como borrar la 
asociación de eventos y reglas	 a la fase.	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un expediente.
Esto hará que el tramitador envíe alarmas cuando el 	expediente se exceda del plazo de resolución.<br><br>Se puede definir el plazo bien 
sobre la fecha de inicio de la fase, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las entidades asociadas al 
procedimiento, o sobre una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>	<div id="tskCard"><br/>	
<a class="enlace" href="#inicio">Inicio</a>	<br/><div class="titulo_ayuda">	<label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Trámite</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su 
información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información se 
clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br><ul><li><b>Trámite</b>: 
<br><br><ul><li><b>Ficha</b></li><br><br>Se muestran los datos identificativos del trámite, recogidos del inventario de trámites.
<br><br>		<li><b>Propiedades</b></li>	<br><br>		Se podrá modificar el nombre del trámite en el contexto del procedimiento
 actual; este	nombre se verá como etiqueta del trámite dentro del tramitador.	<br><br>	Se podrá indicar el responsable del trámite para,
 al iniciarse en el tramitador	este trámite para un expediente, se establezca una persona, grupo o departamento 	como responsable de la 
tramitación.	<br><br>		<li><b>Eventos</b></li>	<br><br>	Mediante la pestaña <b>Eventos</b>, se podrán asociar reglas a distintos 
momentos de la tramitación. 	Las reglas a asociar se dan de alta en el propio catálogo, Componentes >> Reglas. <br><br>	Los eventos 
posibles para un trámite son los siguientes:	<br><br>	<ul>	<li><b>Iniciar</b></li>: se dispara este evento cuando se inicia el 
trámite	<br><br>		<li><b>Terminar</b></li>: al cerrar el trámite	<br><br>		<li><b>Expirar plazo</b></li>: cuando se vence el 
plazo de resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza, al crear la fase, cuando se calcula el
responsable.	<br><br>	<li><b>Utilizar plantilla</b></li>: al utilizar una plantilla para un documento.	<br><br>	<li><b>Crear
 documento</b></li>: al crear un documento.	<br><br>	</ul>	 Se podrá indicar el orden de ejecución de las reglas, así como borrar la
 asociación de eventos y reglas	 al trámite.	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un 
expediente. Esto hará que el tramitador envíe alarmas cuando el 	expediente se exceda del plazo de resolución.	<br><br>	Se puede
 definir el plazo bien sobre la fecha de inicio del trámite, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las 
entidades asociadas al procedimiento, o sobre una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>
	<div id="tpdcCard">		<br/>		<a class="enlace" href="#inicio">Inicio</a>		<br/>			<div class="titulo_ayuda">			
<label class="popUpInfo" >Esquema del Procedimiento >> Fichas >> Tipo Documento</label>		</div>		<br/>		Seleccionando un 
elemento del esquema de tramitación, veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte 
derecha de la pantalla; dicha información se clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación.
 	<br><br>		<ul>	<li><b>Tipo de documento</b>: 	<br><br>	<ul>	<li><b>Ficha</b></li>	<br><br>	Se muestran los datos 
identificativos del tipo de documento, recogidos del inventario de tipos.<br><br></ul></li></ul></div><div id="tmpltCard"><br/>	
<a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Plantilla</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su información
 específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información se clasifica en función
 de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br>		<ul>	<li><b>Plantilla</b>: <br><br><ul><li><b>
Ficha</b></li>	<br><br>	Se muestran los datos identificativos de la plantilla, recogidos del inventario de plantillas.	<br><br>Se podrá 
editar la plantilla para introducir cambios en la misma.		</ul>	</li>	</ul>	</div><div id="publisher">	<br/>	<a class="enlace" 
href="#inicio">Inicio</a>	<br/>	<div class="titulo_ayuda">	<label class="popUpInfo" >Publicador</label>	</div>	<br/>	SIGEM 
dispone de un componente denominado <b>Publicador</b>, encargado de comunicarse con sistemas externos	de forma asíncrona. Un uso común de 
dicho publicador es el envío de información de la tramitación de un	expediente a un sistema externo, por ejemplo un sistema de información al 
ciudadano.	<br><br>	La lógica de dicho publicador se recoge en <b>reglas</b>, que recogen la acción que se ejecutará, para un evento 
de tramitación determinado si se cumple una determinada condición. Tanto acciones como condiciones son	clases java que se han de implementar
 y posteriormente dar de alta en el catálogo.<br><br>El publicador se compone de los siguientes elementos:<ul><li><b>Acciones</b></li><br><br>
	Son clases java que realizan una acción concreta, que irán asociadas a determinadas reglas, y que ejecutará el publicador si se dispara una
 de dichas reglas.	<br><br>	<li><b>Condiciones</b></li><br><br>	Son clases de java que realizan una comprobación, y que determinarán si se
 ejecuta o no	la acción de la regla disparada.	<br><br>		<li><b>Aplicaciones</b></li><br><br>	Contexto o sistema externo destino 
de la publicación de una regla y que recogerá la información 	publicada; las reglas se clasificarán en función de dicha aplicación.<br><br>
	<li><b>Reglas</b></li><br><br>	Una regla recoge la acción que se desea ejecutar para un evento determinado. La regla vendrá	asociado a 
un contexto Procedimiento/Fase/Trámite/Tipo de documento, y su ejecución vendrá deteminada por el cumplimiento de una determinada condición. 
	<br><br>	Se deberá indicar para cada regla:	<br><br>	<ul>	<li><b>Procedimiento</b>: procedimiento sobre el que se aplica la regla.
 Este campo es obligatorio.	</li><br>	<li><b>Fase</b>: fase sobre la que se aplica la regla.	</li><br>	<li><b>Trámite</b>: trámite sobre 
el que se aplica la regla.	</li><br>		<li><b>Tipo de documento</b>: tipo de documento sobre el que se aplica la regla.	</li><br>	
	<li><b>Evento</b>: momento en el que se disparará la ejecución de la regla. Este campo es obligatorio.</li><br><li><b>Info</b>: Información
 adicional sobre determinados eventos.	</li><br>			<li><b>Acción</b>: acción que se ejecutará al disparar la regla, seleccionada
 entre las definidas en	el sistema.	</li><br>			<li><b>Condición</b>: condición que se ejecutará para determinar si se dispara la 
regla, seleccionada 	entre las definidas en el sistema.	</li><br>			<li><b>Atributos</b>: xml de configuración para la clase 
java que implementa la acción de la regla.	Para más detalle consultar la documentación técnica.	</li><br>			<li><b>Aplicación</b>:
 aplicación contexto de la regla, seleccionada entre las definidas en el sistema.	</li><br></ul><br><li><b>Hitos erróneos</b></li>
<br><br>Desde esta opción se permite realizar el seguimientos de los errores que se han producido en la publicación.Un hito erróneo podrá
 ser reactivado para que el publicador vuelva a tratarlo.	</div>'
where IDIOMA IS NULL AND TIPO_OBJ=31;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO</h4></div>Aparece una ventana emergente que le irá guiando en la creación
 del procedimiento.<br><br>En la primera pantalla debe introducir el nombre del procedimiento. 	<br><br>	Pulsar ''Siguiente'' para continuar
<br><br>'
where IDIOMA IS NULL AND TIPO_OBJ=32;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > SELECCIÓN DE FASES</h4></div>Seleccionar las fases que va a tener el 
procedimiento, en el listado de Fases disponibles, y luego pulsar la opción ''Añadir''.<br><br>El orden de las fases se puede modificar mediante
 las opciones ''Subir'' y ''Bajar''. <br><br>Para continuar pulsar ''Siguiente'''
where IDIOMA IS NULL AND TIPO_OBJ=33;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > FASES > TRÁMITES POR FASE</h4></div>Por cada una de las fases habrá que
 asociar los trámites. <br>Para ello pulsar en la opción ''Asignar trámites''. <br><br>Una vez completados todos los trámites de todas las fases, 
pulsar ''Siguiente''. ' 
where IDIOMA IS NULL AND TIPO_OBJ=34;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > FASES > SELECCIÓN DE TRÁMITES POR FASE</h4></div>
Seleccionar los trámites que va a tener la fase, en el listado de Trámites disponibles, y luego pulsar la opción ''Añadir''.
<br><br>	El orden de los trámites se puede modificar mediante las opciones ''Subir'' y ''Bajar''. <br><br>Una vez
 seleccionados todos los trámites de la fase, pulsar ''Volver'' para situarse en la pantalla anterior.' 
where IDIOMA IS NULL AND TIPO_OBJ=35;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > RESUMEN CREACIÓN</h4></div>El sistema muestra una pantalla resumen de 
las fases y trámites del procedimiento. 	<br><br>	Si todo es correcto, pulsar ''Crear procedimiento''.'
where IDIOMA IS NULL AND TIPO_OBJ=36;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > RESULTADO CREACIÓN</h4></div>El sistema informa del resultado de la 
creación del procedimiento. 	<br><br>	Para consultar y editar el nuevo procedimiento creado, pulsar ''Ver nuevo procedimiento''.'
where IDIOMA IS NULL AND TIPO_OBJ=37;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO</h4></div>Aparece una ventana emergente que le irá guiando en la creación
 del subproceso. <br><br>En la primera pantalla debe introducir el nombre del subproceso. <br><br>Pulsar ''Siguiente'' para continuar<br><br>'
where IDIOMA IS NULL AND TIPO_OBJ=38;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > SELECCIÓN DE ACTIVIDADES</h4></div>Seleccionar las actividades que va a 
tener el subproceso, en el listado de Actividades disponibles, y luego pulsar la opción ''Añadir''.<br><br>El orden de las actividades se puede
 modificar mediante las opciones ''Subir'' y ''Bajar''. <br><br>Para continuar pulsar ''Siguiente''.'
where IDIOMA IS NULL AND TIPO_OBJ=39;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > RESUMEN CREACIÓN</h4></div>El sistema muestra una pantalla resumen de las
 actividades del subproceso. <br><br>.Si todo es correcto, pulsar ''Crear subproceso''.' 
where IDIOMA IS NULL AND TIPO_OBJ=40;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > RESULTADO CREACIÓN</h4></div>El sistema informa del resultado de la
 creación del subproceso. 	<br><br>	Para consultar y editar el nuevo subproceso creado, pulsar ''Ver nuevo subproceso''.'
where IDIOMA IS NULL AND TIPO_OBJ=41;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>DISEÑADOR GRÁFICO</h4></div>El <b>Diseñador Gráfico</b> es la herramienta que proporciona
 SIGEM para visualizar los procedimientos y facilitar la tarea al diseñador. Esta herramienta permite a los responsables de procesos y 
métodos de la organización 	crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.	
<br><br><h3>Barra de herramientas</h3><div>Nombre del procedimiento : Estado Si Estado=Borrador, se le permitirá al usuario:Insertar una nueva
 <b>Fase</b>, arrastrando la imagen  que tiene una F .Insertar un nuevo <b>nodo de sincronización de tipo And</b>, arrastrando la imagen con
 que tiene una Y. Insertar un nuevo <b>nodo de sincronización de tipo Or</b>, arrastando la imagen que tiene una O .Insertar nuevos
 <b>flujos</b>, haciendo click sobre la imagen de la punta de flecha , si se hace click con el botón izquierdo sólo estará habilitado 
para realizar una conexión, si se hace click con el botón derecho del ratón se crearan todos los flujos que el usuario desee hasta que se 
pulse con un click sobre la imagen de la punta de flecha.Para realizar una conexión una vez hayamos habilitado la flecha seleccionaremos 
con un click la pareja de elementos que formen parte del nuevo flujo.La imagen de una impresora que nos permite imprimir lo que en ese momento 
tengamos en la pantalla.El icono de ayuda nos muestra la ayuda del diseñador gráfico</div><br/><br/><h3>Editor de condiciones </h3>	<div>
Un flujo puede tener asociado una condición para ello se mostrará sobre el flujo un icono con un ?, pulsado sobre la flecha con el 
botón derecho del ratón se visualizará un listado de las condiciones Java (Reglas) como sobre BBDD (SQL) que el usuario haya asociado
 al flujo , una condicion sql se podrá visualizar, pulsando sobre el nombre de la misma.Para modificar la condición el procedimiento 
deberá estar en estado borrador y se podrán añadir y eliminar condiciones o modificar una condición sql.Estructura de una condición:
 <ul><li>	 Nombre de la condición: Nombre que el usuario quiere darle a la condición SQL</li><li>	 Cuerpo de la condición: Se representa
 con una tabla que tendrá cada una de las condiciones simples que en su conjunto forman el cuerpo de la condición.</li><li>	 Condición simple:
 <ul><li>Selector parentesis</li><li>Selector de condición: Siempre y cuando no sea la primera condición simple.</li><li>	
 Operando 1: Esta compuesto de dos selectores uno se corresponde con las entidades definidas para el procedimiento en el que estamos, 
cuando seleccionamos una entidad se carga en el otro selector los campos que tiene la entidad seleccionada.	 	
Operador:  Selector con el que indicaremos la operación a realizar en la condición simple, en función del selector seleccionado se	 
mostrarán los operandos o no.</li><li>Operando 2: Por defecto se mostrará un checkbox sin chequear y una caja de 
texto para que el usuario la rellene si desea realizar la comprobación con un dato introducido por él, en caso contrario,
 es decir que se quiera realizar la comprobación contra un campo de la bbdd se chequeará el checkbox y aparecerán dos 
selectores, siendo el primero una lista de las entidades,una vez que el usuario seleccione una, se cargará los campos de 
la entidad seleccionada y que se correspondan en tipo con el campo del operando1 seleccionado, si no hay se ha seleccionado nada en el
 operando 1 , no se cargará ningún campo.<li></li>Operando 3: Mismo funcionamiento que el operando 2. </li></ul></li></ul></div>'
where IDIOMA IS NULL AND TIPO_OBJ=42;

INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Procedimiento de Reclamaciones, quejas y sugerencias', 3, 3, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</B></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Admisión a Trámite</td></tr><tr><td colspan=3></td><td>Comunicación de apertura</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Emisión Oficio de Respuesta</td></tr><tr><td colspan=3></td><td>Notificación Oficio de Respuesta</td></tr><tr><td colspan=3></td><td>Emisión Informe</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Remisión Documentación</td></tr><tr><td colspan=3></td><td>Comunicación al interesado</td></tr><tr><td colspan=3></td><td>Emisión Oficio No Admisión</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Archivo de Expediente</td></tr><tr><td colspan=3><b>Resolución</b></td><td>Responsable Área de Atención al Cliente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'desde la entrada de la Solicitud en el Registro</td></tr><tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr><tr><td colspan=3><b>Efectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Reclamación, Queja o Sugerencia</td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3></td></tr><tr><td><b>Fecha Vigor</b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=3;

INSERT INTO spac_ayudas (id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Procedimiento de Concesión de subvención', 3, 4, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=2><b>Nombre</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>TSUB</td><td colspan=2>Subvención genérica</td></tr><tr><td colspan=2><b>Familia de Procedimientos</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>Subvenciones</td><td colspan=2>Procedimiento de concesión de una Subvención</td></tr><tr><td colspan=2><b>Organismo al que pertenece</b></td><td colspan=2>Unidad organizativa concesionaria de la Subvención</td></tr><tr><td colspan=2><b>Organismo competente</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Organismo que tramita</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Organismo que resuelve</b></td><td colspan=2>Área de Concesión de Subvenciones</td></tr><tr><td colspan=2><b>Objeto</b></td>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<td colspan=2>Procedimiento administrativo para la concesión de una Subvención</td></tr><tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</b></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Solicitud Subsanación</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Propuesta de Resolución</td></tr><tr><td colspan=3></td><td>Acuerdo Consejo Gobierno</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Alegaciones</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Decreto de concesión</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Archivo de Expediente</td></tr><tr><td colspan=3><b>Resolución</b></td><td>Responsable Área de Atención al Cliente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses desde la entrada de la Solicitud en el Registro</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr><tr><td colspan=3><b>fectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Subvención</td></tr><tr><td colspan=3></td><td>Documentos relativos a la Subvención </td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3></td></tr><tr><td><b>Fecha Vigor</b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=4;

INSERT INTO spac_ayudas (id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Procedimiento de Obras menores', 3, 5, NULL, '');
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FORMULARIO EXPEDIENTE</h4></div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p><p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li><p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li></ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p><p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li><p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li><p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li><p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'ha pasado el expediente.</li><p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li></ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div><p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li><p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li><p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li><p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li><p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una ' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'nueva ventana, las distintas unidades de plazo existentes, de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b> para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el documento normalizado, con los datos del expediente incluidos en él<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato, cambiar una expresión, etc.' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li></ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite, se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.</p>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<div class="cabecera_seccion"><h4>FICHA DEL PROCEDIMIENTO</h4></div><table><tr><td colspan=2><b>Nombre</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>TLOM</td><td colspan=2>Licencia de Obra Menor</td></tr><tr><td colspan=2><b>Familia de Procedimientos</b></td><td colspan=2><b>Descripción</b></td></tr><tr><td colspan=2>Urbanismo</td><td colspan=2>Procedimiento de Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo al que pertenece</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo competente</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo que tramita</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b>Organismo que resuelve</b></td><td colspan=2>Urbanismo y Área Técnica</td></tr><tr><td colspan=2><b<Objeto</b></td><td colspan=2>Procedimiento administrativo para la concesión de Licencia de Obra Menor</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=4><b>DATOS TÉCNICOS DEL PROCEDIMIENTO</b></td></tr><tr><td><b>FASES</b></td><td><b>Inicio</b></td><td>Trámites</td><td>Solicitud subsanación</td></tr><tr><td></td><td><b>Instrucción</b></td><td>Trámites</td><td>Emisión informe</td></tr><tr><td colspan=3></td><td>Propuesta de resolución</td></tr><tr><td colspan=3></td><td>Notificación</td></tr><tr><td colspan=3></td><td>Alegaciones</td></tr><tr><td></td><td><b>Terminación</b></td><td>Trámites</td><td>Archivo del expediente</td></tr><tr><td colspan=3></td><td>Resolución expediente</td></tr><tr><td colspan=3></td><td>Notificación resolución</td></tr><tr><td colspan=3><b>Resolución</b></td><td>TTE. Alcalde delegado de Urbanismo por delegación del Alcalde-Presidente</td></tr><tr><td colspan=3><b>Plazo máximo de Notificación</b></td><td>3 meses desde la entrada de la Solicitud en el Registro</td></tr><tr><td colspan=3><b>Tipo de Tramitación</b></td><td>Presencial y Telemática</td></tr>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;
UPDATE SPAC_AYUDAS set CONTENIDO=CONTENIDO||'<tr><td colspan=3><b>Efectos del Silencio Administrativo</b></td><td>Estimatorio, excepto que su concesión sea contraria a la legislación urbanística o se concedan al solicitante o a terceros facultades relativas al dominio o al servicio público</td></tr><tr><td colspan=3><b>Documentación Requerida</b></td><td>Solicitud de la Licencia de Obra Menor</td></tr><tr><td colspan=3></td><td>Nota Simple Registral o</td></tr><tr><td colspan=3></td><td>Certificado de la Comisión Local de Casco Histórico (vivienda en casco histórico)</td></tr><tr><td colspan=4><b>NORMATIVA APLICABLE</b></td></tr><tr><td><b>Título</b></td><td colspan=3>Ejemplo: Plan General o Ley </td></tr><tr><td><b>Ámbito</b></td><td colspan=3>de Ordenación Urbana de ...</td></tr><tr><td><b>Fecha Vigor<b></td><td></td><td><b>Fecha publicación</b></td><td></td></table>' where IDIOMA IS NULL AND TIPO_OBJ=3 AND ID_OBJ=5;


--Se amplia el tamaño del campo NREG a 64
ALTER TABLE spac_dt_documentos ALTER COLUMN nreg TYPE character varying(64);
ALTER TABLE spac_expedientes ALTER COLUMN nreg TYPE character varying(64);


--Se añade una columna más a spac_procesos para indicar la fecha de eliminacion
ALTER TABLE SPAC_PROCESOS ADD COLUMN fecha_eliminacion timestamp without time zone;

--Se añade una columna más a spac_procesos spac_fases y spac_tramites para guardar el estado anterior 
ALTER TABLE SPAC_PROCESOS ADD COLUMN estado_anterior smallint;
ALTER TABLE SPAC_FASES ADD COLUMN estado_anterior smallint;
ALTER TABLE SPAC_TRAMITES ADD COLUMN estado_anterior smallint;

--
-- Se añaden dos columnas más a spac_procesos spac_fases y spac_tramites para guardar el nombre del responsable 
-- y el nombre del responsable secundario.
--
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP CHARACTER VARYING(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP CHARACTER VARYING (250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP CHARACTER VARYING (250);
ALTER TABLE SPAC_PROCESOS ADD COLUMN RESP_SEC CHARACTER VARYING(250);
ALTER TABLE SPAC_FASES ADD COLUMN RESP_SEC CHARACTER VARYING (250);
ALTER TABLE SPAC_TRAMITES ADD COLUMN RESP_SEC CHARACTER VARYING (250);


--
-- Actualización de la vista de plazos
--

CREATE OR REPLACE VIEW spac_deadline AS 
SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Expediente' AS descripcion, 1 AS tipo
   FROM spac_procesos obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Fase' AS descripcion, 2 AS tipo
   FROM spac_fases obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.tipo=1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, obj.nombre AS descripcion, 3 AS tipo
   FROM spac_tramites obj, spac_ct_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.fecha_limite IS NOT NULL
UNION 
 SELECT obj.numexp, obj.fecha_limite, procedimiento.nombre AS nombre_pcd, obj.id_resp, obj.id AS ID_OBJETO, 'Resolución Actividad' AS descripcion, 4 AS tipo
   FROM spac_fases obj, spac_p_procedimientos procedimiento
  WHERE obj.id_pcd = procedimiento.id AND obj.estado = 1 AND obj.tipo=2 AND obj.fecha_limite IS NOT NULL;


--
-- Creación de índices
--

CREATE INDEX spac_expedientes_ix_numexp ON spac_expedientes (numexp);
CREATE INDEX spac_dt_documentos_ix_numexp ON spac_dt_documentos (numexp);
CREATE INDEX spac_dt_tramites_ix_numexp ON spac_dt_tramites (numexp);
CREATE INDEX spac_dt_tramites_ix_idtramexp ON  spac_dt_tramites (id_tram_exp);
CREATE INDEX spac_dt_int_ix_numexp ON  spac_dt_intervinientes (numexp);

CREATE INDEX spac_procesos_ix_numexp ON spac_procesos (numexp);
CREATE INDEX spac_procesos_ix_idresp ON spac_procesos (id_resp);
CREATE INDEX spac_fases_ix_numexp ON spac_fases (numexp);
CREATE INDEX spac_fases_ix_idresp ON spac_fases (id_resp);
CREATE INDEX SPAC_tramites_ix_numexp ON SPAC_tramites (numexp);
CREATE INDEX SPAC_tramites_ix_idresp ON SPAC_tramites (id_resp);


