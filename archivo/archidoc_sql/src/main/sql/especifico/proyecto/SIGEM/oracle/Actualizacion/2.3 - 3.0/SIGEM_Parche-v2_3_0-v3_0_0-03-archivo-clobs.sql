-- Sigem  Fecha : 17/10/2012


--Ficha: 'ISAD(G) Nivel de Descripción Unidad Documental ENI'

DECLARE

ficha1 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">	<Informacion_Campos>		<Area> <!--Area de Identidad -->			<Id_Area_Asociada>1</Id_Area_Asociada>			<Campos>				<Campo Tipo="Especial"><!--Codigo Referencia -->					<Id>-1</Id>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"> <!--Identificacion -->					<Id>1</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Especial"><!--Numero expediente -->					<Id>-2</Id>					<Descripcion></Descripcion>					<Editable>S</Editable>					<Tipo>1</Tipo>				</Campo>				<Campo Tipo="Especial"><!--Titulo -->					<Id>-3</Id>					<Descripcion></Descripcion>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Tipo>1</Tipo>				</Campo>				<Campo Tipo="Tabla"><!-- Emplazamiento -->					<Id>3</Id>					<Editable>S</Editable> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='10';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('10','ISAD(G) Nivel de Descripción Unidad Documental ENI',1,6,61,ficha1);
END;
/

DECLARE

ficha1_1 clob :='					<Informacion_Especifica>						<Validacion>							<Sistema_Externo>2</Sistema_Externo>						</Validacion>					</Informacion_Especifica>					<Columnas>						<Campo Tipo="Dato"><!--Pais -->							<Id>2</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Provincia -->							<Id>39</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_1 WHERE ID='10';
END;
/

DECLARE

ficha1_2 clob :='							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Concejo -->							<Id>40</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Poblacion -->							<Id>41</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Localizacion --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_2 WHERE ID='10';
END;
/

DECLARE

ficha1_3 clob :='							<Id>42</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Validado -->							<Id>212</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Fecha inicial -->					<Id>3</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_3 WHERE ID='10';
END;
/

DECLARE

ficha1_4 clob :='								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Fecha Final -->					<Id>4</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" />								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Especial"><!--Nivel de descripcion -->					<Id>-4</Id>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Tabla"><!--Volumen y soporte -->					<Id>1</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_4 WHERE ID='10';
END;
/

DECLARE

ficha1_5 clob :='					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Soporte -->							<Id>6</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Formato -->							<Id>5</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Cantidad -->							<Id>7</Id>							<Tipo>4</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_5 WHERE ID='10';
END;
/

DECLARE

ficha1_6 clob :='							<Informacion_Especifica>								<Tipo_Numerico>1</Tipo_Numerico>								<Validacion>									<Rango Minimo="0" Maximo=""></Rango>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Tabla"><!--Interesados -->					<Id>2</Id>					<Editable>S</Editable>					<Informacion_Especifica>						<Validacion>							<Sistema_Externo>1</Sistema_Externo>						</Validacion>					</Informacion_Especifica>					<Columnas>						<Campo Tipo="Dato"><!--Principal -->							<Id>213</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Identidad -->							<Id>9</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_6 WHERE ID='10';
END;
/

DECLARE

ficha1_7 clob :='							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Numero de identidad -->							<Id>10</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Rol -->							<Id>11</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_7 WHERE ID='10';
END;
/

DECLARE

ficha1_8 clob :='						</Campo>						<Campo Tipo="Dato"><!--Validado -->							<Id>12</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--IdTercero -->							<Id>51</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Nombre sistema productor -->					<Id>14</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_8 WHERE ID='10';
END;
/

DECLARE

ficha1_9 clob :='				<Campo Tipo="Dato"><!--Publicacion SAC -->					<Id>15</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Valor_Inicial>No</Valor_Inicial>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Versión NTI -->					<Id>1001</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Estado -->					<Id>1002</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Tabla"><!--Firmas -->					<Id>105</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_9 WHERE ID='10';
END;
/

DECLARE

ficha1_10 clob :='					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Tipo de Firma -->							<Id>1003</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Valor_CSV -->							<Id>1004</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Definición de Generación CSV -->							<Id>1005</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Identificador interno Firma (ENLACE) --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_10 WHERE ID='10';
END;
/

DECLARE

ficha1_11 clob :='							<Id>1006</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Contexto -->			<Id_Area_Asociada>2</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Productor -->					<Id>16</Id>					<Tipo>5</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Historia institucional -->					<Id>17</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_11 WHERE ID='10';
END;
/

DECLARE

ficha1_12 clob :='					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Historia archivistica -->					<Id>18</Id>					<Tipo>2</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Ingreso por -->					<Id>203</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Código de transferencia -->					<Id>204</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_12 WHERE ID='10';
END;
/

DECLARE

ficha1_13 clob :='					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Referencias de ingreso -->					<Id>205</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Hoja de Entrega: -->					<Id>206</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Alcance y Contenido -->			<Id_Area_Asociada>3</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Descripcion Contenido -->					<Id>13</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>				</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_13 WHERE ID='10';
END;
/

DECLARE

ficha1_14 clob :='				<Campo Tipo="Tabla"><!--Alcance y contenido -->					<Id>4</Id>					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Nombre del documento -->							<Id>19</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Descripcion del documento -->							<Id>43</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Tabla"><!--Documentos electronicos -->					<Id>6</Id>					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Nombre del documento -->							<Id>49</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_14 WHERE ID='10';
END;
/

DECLARE

ficha1_15 clob :='							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Descripcion del documento -->							<Id>50</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Versión NTI -->							<Id>1011</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Órgano -->							<Id>1012</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_15 WHERE ID='10';
END;
/

DECLARE

ficha1_16 clob :='							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Fecha de Captura -->							<Id>1013</Id>							<Tipo>3</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Formatos>										<Formato Tipo="AAAA" Sep="" />										<Formato Tipo="MMAAAA" Sep="/" />										<Formato Tipo="DDMMAAAA" Sep="/" />										<Formato Tipo="AAAAMM" Sep="/" />										<Formato Tipo="AAAAMMDD" Sep="/" />										<Formato Tipo="S" Sep="" />									</Formatos>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Origen -->							<Id>1014</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_16 WHERE ID='10';
END;
/

DECLARE

ficha1_17 clob :='							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Estado Elaboración -->							<Id>1015</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Nombre Formato -->							<Id>1016</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Tipo Documental -->							<Id>1017</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_17 WHERE ID='10';
END;
/

DECLARE

ficha1_18 clob :='						<Campo Tipo="Dato"><!-- Documento Electrónico: Tipo Firma -->							<Id>1018</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Valor CSV -->							<Id>1019</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónco: Definición Generación								CSV -->							<Id>1020</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_18 WHERE ID='10';
END;
/

DECLARE

ficha1_19 clob :='						<Campo Tipo="Dato"><!-- Documento Electronico: Identificador Documento								Origen -->							<Id>1021</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Id Interno (ENLACE) -->							<Id>1022</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Identificador interno								Firma (ENLACE) -->							<Id>1023</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_19 WHERE ID='10';
END;
/

DECLARE

ficha1_20 clob :='					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->					<Id>20</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" />								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Nuevos ingresos -->					<Id>21</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>2</Tipo_Referencia>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_20 WHERE ID='10';
END;
/

DECLARE

ficha1_21 clob :='		<Area><!--Area de Condiciones de Acceso y Seguridad -->			<Id_Area_Asociada>4</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Condiciones de acceso -->					<Id>22</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Condiciones de reproduccion -->					<Id>23</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Lenguas -->					<Id>24</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>						</Validacion>					</Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_21 WHERE ID='10';
END;
/

DECLARE

ficha1_22 clob :='					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Caracteristicas fisicas -->					<Id>25</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Instrumentos descripcion -->					<Id>26</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area><!--Area de Documentacion Asociada -->			<Id_Area_Asociada>5</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Existencia de originales -->					<Id>27</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_22 WHERE ID='10';
END;
/

DECLARE

ficha1_23 clob :='						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->					<Id>28</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Existencia de copias -->					<Id>29</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->					<Id>30</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_23 WHERE ID='10';
END;
/

DECLARE

ficha1_24 clob :='					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Unidades relacionadas -->					<Id>31</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>2</Tipo_Referencia>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Nota publicacion -->					<Id>32</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Notas -->			<Id_Area_Asociada>6</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Notas -->					<Id>35</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_24 WHERE ID='10';
END;
/

DECLARE

ficha1_25 clob :='				</Campo>			</Campos>		</Area>		<Area><!--Area de control de la descripcion -->			<Id_Area_Asociada>7</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Normas utilizadas -->					<Id>33</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Valor_Inicial>ISAD(G)</Valor_Inicial>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de descriptores -->			<Id_Area_Asociada>8</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Descriptor -->					<Id>44</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_25 WHERE ID='10';
END;
/

DECLARE

ficha1_26 clob :='	</Informacion_Campos>	<Eventos>		<Evento>			<Tipo>2</Tipo>			<Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>		</Evento>		<Evento>			<Tipo>2</Tipo>			<Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>		</Evento>	</Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_26 WHERE ID='10';
END;
/

--Formato: 'Consulta de ficha de unidad documental ENI - Público'

DECLARE

fmt1 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='29';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('29','10',1,1,'Consulta de ficha de unidad documental ENI - Público',fmt1);
END;
/

DECLARE

fmt1_1 clob :='							<Etiqueta>								<Titulo Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_1 WHERE ID='29';
END;
/

DECLARE

fmt1_2 clob :='										<Titulo Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_2 WHERE ID='29';
END;
/

DECLARE

fmt1_3 clob :='						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_3 WHERE ID='29';
END;
/

DECLARE

fmt1_4 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_4 WHERE ID='29';
END;
/

DECLARE

fmt1_5 clob :='					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_5 WHERE ID='29';
END;
/

DECLARE

fmt1_6 clob :='								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_6 WHERE ID='29';
END;
/

DECLARE

fmt1_7 clob :='			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo> <!--<Iconos> <Icono valor="1" src="/imagenes/1.gif"> <Iconos> -->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_7 WHERE ID='29';
END;
/

DECLARE

fmt1_8 clob :='					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_8 WHERE ID='29';
END;
/

DECLARE

fmt1_9 clob :='						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_9 WHERE ID='29';
END;
/

DECLARE

fmt1_10 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_10 WHERE ID='29';
END;
/

DECLARE

fmt1_11 clob :='										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_11 WHERE ID='29';
END;
/

DECLARE

fmt1_12 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="Hipervinculo">									<Etiqueta>										<Titulo Predeterminado="Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Vinculo>										<Titulo Predeterminado="Enlace"></Titulo>										<Estilo></Estilo>										<Url><![CDATA[../../action/documento?method=retrieve&tipoObjeto=1&descripcion=1]]></Url>										<Target>_self</Target>										<Parametro>id</Parametro>										<Id_Campo_Value>1022</Id_Campo_Value>									</Vinculo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_12 WHERE ID='29';
END;
/

DECLARE

fmt1_13 clob :='						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_13 WHERE ID='29';
END;
/

DECLARE

fmt1_14 clob :='			<Etiqueta>				<Titulo Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_14 WHERE ID='29';
END;
/

DECLARE

fmt1_15 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_15 WHERE ID='29';
END;
/

DECLARE

fmt1_16 clob :='						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_16 WHERE ID='29';
END;
/

DECLARE

fmt1_17 clob :='				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_17 WHERE ID='29';
END;
/

DECLARE

fmt1_18 clob :='				<Titulo Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_18 WHERE ID='29';
END;
/

--Formato: 'Edición de ficha de unidad documental ENI - Público'

DECLARE

fmt2 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='30';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('30','10',2,1,'Edición de ficha de unidad documental ENI - Público',fmt2);
END;
/

DECLARE

fmt2_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_1 WHERE ID='30';
END;
/

DECLARE

fmt2_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_2 WHERE ID='30';
END;
/

DECLARE

fmt2_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_3 WHERE ID='30';
END;
/

DECLARE

fmt2_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_4 WHERE ID='30';
END;
/

DECLARE

fmt2_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_5 WHERE ID='30';
END;
/

DECLARE

fmt2_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_6 WHERE ID='30';
END;
/

DECLARE

fmt2_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_7 WHERE ID='30';
END;
/

DECLARE

fmt2_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_8 WHERE ID='30';
END;
/

DECLARE

fmt2_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_9 WHERE ID='30';
END;
/

DECLARE

fmt2_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_10 WHERE ID='30';
END;
/

DECLARE

fmt2_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_11 WHERE ID='30';
END;
/

DECLARE

fmt2_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_12 WHERE ID='30';
END;
/

DECLARE

fmt2_13 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_13 WHERE ID='30';
END;
/

DECLARE

fmt2_14 clob :='					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_14 WHERE ID='30';
END;
/

DECLARE

fmt2_15 clob :='					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_15 WHERE ID='30';
END;
/

DECLARE

fmt2_16 clob :='					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_16 WHERE ID='30';
END;
/

DECLARE

fmt2_17 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_17 WHERE ID='30';
END;
/

DECLARE

fmt2_18 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_18 WHERE ID='30';
END;
/

DECLARE

fmt2_19 clob :='				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_19 WHERE ID='30';
END;
/

--Formato: 'Consulta de ficha de unidad documental ENI - Archivo'

DECLARE

fmt3 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='31';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('31','10',1,2,'Consulta de ficha de unidad documental ENI - Archivo',fmt3);
END;
/

DECLARE

fmt3_1 clob :='							<Etiqueta>								<Titulo Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_1 WHERE ID='31';
END;
/

DECLARE

fmt3_2 clob :='										<Titulo Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_2 WHERE ID='31';
END;
/

DECLARE

fmt3_3 clob :='						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_3 WHERE ID='31';
END;
/

DECLARE

fmt3_4 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_4 WHERE ID='31';
END;
/

DECLARE

fmt3_5 clob :='					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_5 WHERE ID='31';
END;
/

DECLARE

fmt3_6 clob :='								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_6 WHERE ID='31';
END;
/

DECLARE

fmt3_7 clob :='			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo> <!--<Iconos> <Icono valor="1" src="/imagenes/1.gif"> <Iconos> -->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_7 WHERE ID='31';
END;
/

DECLARE

fmt3_8 clob :='					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_8 WHERE ID='31';
END;
/

DECLARE

fmt3_9 clob :='						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_9 WHERE ID='31';
END;
/

DECLARE

fmt3_10 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_10 WHERE ID='31';
END;
/

DECLARE

fmt3_11 clob :='										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_11 WHERE ID='31';
END;
/

DECLARE

fmt3_12 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="Hipervinculo">									<Etiqueta>										<Titulo Predeterminado="Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Vinculo>										<Titulo Predeterminado="Enlace"></Titulo>										<Estilo></Estilo>										<Url><![CDATA[../../action/documento?method=retrieve&tipoObjeto=1&descripcion=1]]></Url>										<Target>_self</Target>										<Parametro>id</Parametro>										<Id_Campo_Value>1022</Id_Campo_Value>									</Vinculo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_12 WHERE ID='31';
END;
/

DECLARE

fmt3_13 clob :='						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_13 WHERE ID='31';
END;
/

DECLARE

fmt3_14 clob :='			<Etiqueta>				<Titulo Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_14 WHERE ID='31';
END;
/

DECLARE

fmt3_15 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_15 WHERE ID='31';
END;
/

DECLARE

fmt3_16 clob :='						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_16 WHERE ID='31';
END;
/

DECLARE

fmt3_17 clob :='				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_17 WHERE ID='31';
END;
/

DECLARE

fmt3_18 clob :='				<Titulo Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_18 WHERE ID='31';
END;
/

DECLARE

fmt3_19 clob :='						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_19 WHERE ID='31';
END;
/

--Formato: 'Edición de ficha de unidad documental ENI - Archivo'

DECLARE

fmt4 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>S</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='32';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('32','10',2,2,'Edición de ficha de unidad documental ENI - Archivo',fmt4);
END;
/

DECLARE

fmt4_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_1 WHERE ID='32';
END;
/

DECLARE

fmt4_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_2 WHERE ID='32';
END;
/

DECLARE

fmt4_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_3 WHERE ID='32';
END;
/

DECLARE

fmt4_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_4 WHERE ID='32';
END;
/

DECLARE

fmt4_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_5 WHERE ID='32';
END;
/

DECLARE

fmt4_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_6 WHERE ID='32';
END;
/

DECLARE

fmt4_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_7 WHERE ID='32';
END;
/

DECLARE

fmt4_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_8 WHERE ID='32';
END;
/

DECLARE

fmt4_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_9 WHERE ID='32';
END;
/

DECLARE

fmt4_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_10 WHERE ID='32';
END;
/

DECLARE

fmt4_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_11 WHERE ID='32';
END;
/

DECLARE

fmt4_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_12 WHERE ID='32';
END;
/

DECLARE

fmt4_13 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_13 WHERE ID='32';
END;
/

DECLARE

fmt4_14 clob :='					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_14 WHERE ID='32';
END;
/

DECLARE

fmt4_15 clob :='					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_15 WHERE ID='32';
END;
/

DECLARE

fmt4_16 clob :='					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_16 WHERE ID='32';
END;
/

DECLARE

fmt4_17 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_17 WHERE ID='32';
END;
/

DECLARE

fmt4_18 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_18 WHERE ID='32';
END;
/

DECLARE

fmt4_19 clob :='					</Etiqueta>					<Vinculo>						<Titulo							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_19 WHERE ID='32';
END;
/

DECLARE

fmt4_20 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_20 WHERE ID='32';
END;
/

--Mapeo: '10'

DECLARE

map1 clob :='<?xml version="1.0" encoding="ISO-8859-1" ?><MAP_UDOC_REL_A_DESCR Version="5.0">   <DATOS_SIMPLES>      <DATO TIPO="1" ID="14" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>      </DATO>   	<DATO TIPO="3" ID="3" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>      </DATO>      <DATO TIPO="3" ID="4" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>      </DATO>	   <DATO TIPO="5" ID="16" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>	   </DATO>      <DATO TIPO="1" ID="15" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>         <TRANSFORMA_VALOR>	         <VALOR ORG="S">Si</VALOR>	         <VALOR ORG="N">No</VALOR>         </TRANSFORMA_VALOR>      </DATO>      <DATO TIPO="1" ID="1" TIPOPARAM="3">         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM> ';

BEGIN
DELETE FROM ASGTMAPDESCRUDOC WHERE IDFICHA='10';
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO)VALUES('10',map1);
END;
/

DECLARE

map1_1 clob :='      </DATO>   </DATOS_SIMPLES>   <DATOS_TABLA>   	<TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">            <DATO TIPO="5" ID="9" TIPOPARAM="1">      		   <PARAM>IDENTIDAD</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>      			   <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>               </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="1" ID="10" TIPOPARAM="1">      		   <PARAM>NUM_IDENTIDAD</PARAM>   	      </DATO>            <DATO TIPO="1" ID="11" TIPOPARAM="1">      		   <PARAM>ROL</PARAM>   	      </DATO>    		   <DATO TIPO="1" ID="12" TIPOPARAM="1">      		   <PARAM>VALIDADO_EN_TERCEROS</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>           <DATO TIPO="1" ID="51" TIPOPARAM="1"> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_1 WHERE IDFICHA='10';
END;
/

DECLARE

map1_2 clob :='      		   <PARAM>ID_EN_TERCEROS</PARAM>   	      </DATO>    	  <DATO TIPO="1" ID="213" TIPOPARAM="1">      		   <PARAM>PRINCIPAL</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">            <DATO TIPO="5" ID="2" TIPOPARAM="1">      		   <PARAM>PAIS</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>            <DATO TIPO="5" ID="39" TIPOPARAM="1">      		   <PARAM>PROVINCIA</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="5" ID="40" TIPOPARAM="1">      		   <PARAM>CONCEJO</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>               </DESCRIPTOR>   	      </DATO> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_2 WHERE IDFICHA='10';
END;
/

DECLARE

map1_3 clob :='            <DATO TIPO="5" ID="41" TIPOPARAM="1">      		   <PARAM>POBLACION</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>               </DESCRIPTOR>   	      </DATO>            <DATO TIPO="1" ID="42" TIPOPARAM="1">      		   <PARAM>LOCALIZACION</PARAM>   	      </DATO>            <DATO TIPO="1" ID="212" TIPOPARAM="1">      		   <PARAM>VALIDADO</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      	</DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">            <DATO TIPO="1" ID="5" TIPOPARAM="1">               <PARAM>FORMATO</PARAM>            </DATO>            <DATO TIPO="1" ID="6" TIPOPARAM="1">               <PARAM>TIPO</PARAM>            </DATO>            <DATO TIPO="4" ID="7" TIPOPARAM="1">               <PARAM>NUM_DOCS</PARAM> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_3 WHERE IDFICHA='10';
END;
/

DECLARE

map1_4 clob :='            </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">            <DATO TIPO="1" ID="19" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="43" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">            <DATO TIPO="1" ID="49" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="50" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>   </DATOS_TABLA></MAP_UDOC_REL_A_DESCR> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_4 WHERE IDFICHA='10';
END;
/