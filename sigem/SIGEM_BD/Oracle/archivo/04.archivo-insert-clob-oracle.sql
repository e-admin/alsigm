-- Sigem  Fecha : 17/10/2012


--FICHAS

--Ficha: 'ISAD(G) Nivel de Descripción Unidad Documental'

DECLARE

ficha1 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de Identidad -->      <Id_Area_Asociada>1</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Codigo Referencia -->          <Id>-1</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"> <!--Identificacion -->          <Id>1</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Numero expediente -->          <Id>-2</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Especial"><!--Titulo -->          <Id>-3</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='1';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('1','ISAD(G) Nivel de Descripción Unidad Documental',1,6,61,ficha1);
END;
/

DECLARE

ficha1_1 clob :='          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Tabla"><!-- Emplazamiento -->          <Id>3</Id>          <Editable>S</Editable>          <Informacion_Especifica>            <Validacion>              <Sistema_Externo>2</Sistema_Externo>            </Validacion>          </Informacion_Especifica>          <Columnas>            <Campo Tipo="Dato"><!--Pais -->              <Id>2</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Provincia -->              <Id>39</Id>              <Tipo>5</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_1 WHERE ID='1';
END;
/

DECLARE

ficha1_2 clob :='              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Concejo -->              <Id>40</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_2 WHERE ID='1';
END;
/

DECLARE

ficha1_3 clob :='            <Campo Tipo="Dato"><!--Poblacion -->              <Id>41</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Localizacion -->              <Id>42</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Validado -->              <Id>212</Id>              <Tipo>1</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_3 WHERE ID='1';
END;
/

DECLARE

ficha1_4 clob :='              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Fecha inicial -->          <Id>3</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_4 WHERE ID='1';
END;
/

DECLARE

ficha1_5 clob :='                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fecha Final -->          <Id>4</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_5 WHERE ID='1';
END;
/

DECLARE

ficha1_6 clob :='        <Campo Tipo="Especial"><!--Nivel de descripcion -->          <Id>-4</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!--Volumen y soporte -->          <Id>1</Id>          <Editable>S</Editable>          <Columnas>	    	<Campo Tipo="Dato"><!--Soporte -->              <Id>6</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Formato -->              <Id>5</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_6 WHERE ID='1';
END;
/

DECLARE

ficha1_7 clob :='                <Validacion>                  <Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Cantidad -->              <Id>7</Id>              <Tipo>4</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Numerico>1</Tipo_Numerico>                <Validacion>                  <Rango Minimo="0" Maximo=""></Rango>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Tabla"><!--Interesados -->          <Id>2</Id>          <Editable>S</Editable>           <Informacion_Especifica>            <Validacion>              <Sistema_Externo>1</Sistema_Externo>            </Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_7 WHERE ID='1';
END;
/

DECLARE

ficha1_8 clob :='          </Informacion_Especifica>          <Columnas>			 <Campo Tipo="Dato"><!--Principal -->              <Id>213</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Identidad -->              <Id>9</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>                </Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_8 WHERE ID='1';
END;
/

DECLARE

ficha1_9 clob :='              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Numero de identidad -->              <Id>10</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Rol -->              <Id>11</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Validado -->              <Id>12</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_9 WHERE ID='1';
END;
/

DECLARE

ficha1_10 clob :='              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--IdTercero-->              <Id>51</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Nombre sistema productor -->          <Id>14</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_10 WHERE ID='1';
END;
/

DECLARE

ficha1_11 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Publicacion SAC -->          <Id>15</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>No</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Contexto -->      <Id_Area_Asociada>2</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Productor -->          <Id>16</Id>          <Tipo>5</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_11 WHERE ID='1';
END;
/

DECLARE

ficha1_12 clob :='                  <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia institucional -->          <Id>17</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia archivistica -->          <Id>18</Id>          <Tipo>2</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Ingreso por -->          <Id>203</Id>          <Tipo>1</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_12 WHERE ID='1';
END;
/

DECLARE

ficha1_13 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Código de transferencia -->          <Id>204</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Referencias de ingreso -->          <Id>205</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_13 WHERE ID='1';
END;
/

DECLARE

ficha1_14 clob :='          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Hoja de Entrega: -->          <Id>206</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Alcance y Contenido -->      <Id_Area_Asociada>3</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descripcion Contenido -->          <Id>13</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>        </Campo>        <Campo Tipo="Tabla"><!--Alcance y contenido -->          <Id>4</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre del documento -->              <Id>19</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_14 WHERE ID='1';
END;
/

DECLARE

ficha1_15 clob :='              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Descripcion del documento -->              <Id>43</Id>              <Tipo>2</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Tabla"><!--Documentos electronicos -->          <Id>6</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre del documento -->              <Id>49</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_15 WHERE ID='1';
END;
/

DECLARE

ficha1_16 clob :='              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Descripcion del documento -->              <Id>50</Id>              <Tipo>2</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->          <Id>20</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_16 WHERE ID='1';
END;
/

DECLARE

ficha1_17 clob :='                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nuevos ingresos -->          <Id>21</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>2</Tipo_Referencia>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Condiciones de Acceso y Seguridad -->      <Id_Area_Asociada>4</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Condiciones de acceso -->          <Id>22</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_17 WHERE ID='1';
END;
/

DECLARE

ficha1_18 clob :='          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->          <Id>23</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas -->          <Id>24</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->          <Id>25</Id>          <Tipo>2</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_18 WHERE ID='1';
END;
/

DECLARE

ficha1_19 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Instrumentos descripcion -->          <Id>26</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Documentacion Asociada -->      <Id_Area_Asociada>5</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Existencia de originales -->          <Id>27</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_19 WHERE ID='1';
END;
/

DECLARE

ficha1_20 clob :='            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->          <Id>28</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Existencia de copias -->          <Id>29</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->          <Id>30</Id>          <Tipo>2</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_20 WHERE ID='1';
END;
/

DECLARE

ficha1_21 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Unidades relacionadas -->          <Id>31</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>2</Tipo_Referencia>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nota publicacion -->          <Id>32</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Notas --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_21 WHERE ID='1';
END;
/

DECLARE

ficha1_22 clob :='      <Id_Area_Asociada>6</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Notas -->          <Id>35</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de control de la descripcion -->      <Id_Area_Asociada>7</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Normas utilizadas -->          <Id>33</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>ISAD(G)</Valor_Inicial>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descriptores -->      <Id_Area_Asociada>8</Id_Area_Asociada>      <Campos> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_22 WHERE ID='1';
END;
/

DECLARE

ficha1_23 clob :='        <Campo Tipo="Dato"><!--Descriptor -->          <Id>44</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>  </Informacion_Campos>  <Eventos>    <Evento>        <Tipo>2</Tipo>        <Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>    </Evento>    <Evento>        <Tipo>2</Tipo>        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>    </Evento>  </Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha1_23 WHERE ID='1';
END;
/

--Ficha: 'ISAD(G) Nivel de Descripción Serie'

DECLARE

ficha2 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de Identidad -->      <Id_Area_Asociada>1</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Codigo Referencia -->          <Id>-1</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Titulo -->          <Id>-3</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Dato"><!--Fecha inicial -->          <Id>3</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='2';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('2','ISAD(G) Nivel de Descripción Serie',1,4,0,ficha2);
END;
/

DECLARE

ficha2_1 clob :='                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fecha Final -->          <Id>4</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_1 WHERE ID='2';
END;
/

DECLARE

ficha2_2 clob :='          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Nivel de descripcion -->            <Id>-4</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!--Volumen y soporte -->          <Id>7</Id>          <Editable>N</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Cantidad -->              <Id>214</Id>              <Tipo>4</Tipo>              <Multivalor>S</Multivalor>              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Numerico>1</Tipo_Numerico>                <Validacion>                    <Rango Minimo="1" Maximo="10000000"></Rango>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Soporte Documental -->              <Id>8</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_2 WHERE ID='2';
END;
/

DECLARE

ficha2_3 clob :='              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Nombre sistema productor -->          <Id>14</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Publicacion SAC -->          <Id>15</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>No</Valor_Inicial>          <Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_3 WHERE ID='2';
END;
/

DECLARE

ficha2_4 clob :='            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Contexto -->      <Id_Area_Asociada>2</Id_Area_Asociada>      <Campos>        <Campo Tipo="Tabla"><!--Productores -->          <Id>5</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre productor -->              <Id>34</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                      <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_4 WHERE ID='2';
END;
/

DECLARE

ficha2_5 clob :='              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Fecha inicio -->              <Id>36</Id>              <Tipo>3</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Fecha fin -->              <Id>37</Id>              <Tipo>3</Tipo>              <Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_5 WHERE ID='2';
END;
/

DECLARE

ficha2_6 clob :='              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Historia institucional -->          <Id>17</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_6 WHERE ID='2';
END;
/

DECLARE

ficha2_7 clob :='        <Campo Tipo="Dato"><!--Historia archivistica -->          <Id>18</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Alcance y Contenido -->      <Id_Area_Asociada>3</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Alcance y contenido -->          <Id>38</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Valoración Serie -->          <Id>46</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_7 WHERE ID='2';
END;
/

DECLARE

ficha2_8 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->          <Id>47</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Condiciones de Acceso y Seguridad -->      <Id_Area_Asociada>4</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Condiciones de acceso -->          <Id>22</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_8 WHERE ID='2';
END;
/

DECLARE

ficha2_9 clob :='        </Campo>        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->          <Id>23</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas -->          <Id>24</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->          <Id>25</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_9 WHERE ID='2';
END;
/

DECLARE

ficha2_10 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Instrumentos descripcion -->          <Id>26</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Documentacion Asociada -->      <Id_Area_Asociada>5</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Existencia de originales -->          <Id>27</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_10 WHERE ID='2';
END;
/

DECLARE

ficha2_11 clob :='        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->          <Id>28</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Existencia de copias -->          <Id>29</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->          <Id>30</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_11 WHERE ID='2';
END;
/

DECLARE

ficha2_12 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nota publicacion -->          <Id>32</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Notas -->      <Id_Area_Asociada>6</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Notas -->          <Id>35</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de control de la descripcion -->      <Id_Area_Asociada>7</Id_Area_Asociada>      <Campos> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_12 WHERE ID='2';
END;
/

DECLARE

ficha2_13 clob :='        <Campo Tipo="Dato"><!--Normas utilizadas -->          <Id>33</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>ISAD(G)</Valor_Inicial>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descriptores -->      <Id_Area_Asociada>8</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descriptor -->          <Id>44</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_13 WHERE ID='2';
END;
/

DECLARE

ficha2_14 clob :='  </Informacion_Campos>  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosSerieImpl</Clase_Generar_Automaticos>  <Eventos>    <Evento>        <Tipo>2</Tipo>        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>    </Evento>  </Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha2_14 WHERE ID='2';
END;
/

--Ficha: 'ISAD(G) Nivel de Descripción Clasificador Serie'

DECLARE

ficha3 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de Identidad -->      <Id_Area_Asociada>1</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Codigo Referencia -->          <Id>-1</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Titulo -->          <Id>-3</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Dato"><!--Fecha inicial -->          <Id>3</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='3';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('3','ISAD(G) Nivel de Descripción Clasificador Serie',1,3,0,ficha3);
END;
/

DECLARE

ficha3_1 clob :='                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fecha Final -->          <Id>4</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" />                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_1 WHERE ID='3';
END;
/

DECLARE

ficha3_2 clob :='        <Campo Tipo="Especial"><!--Nivel de descripcion -->          <Id>-4</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!--Volumen y soporte documental -->          <Id>7</Id>          <Editable>N</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Cantidad -->              <Id>214</Id>              <Tipo>4</Tipo>              <Multivalor>S</Multivalor>              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Soporte Documental -->              <Id>8</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_2 WHERE ID='3';
END;
/

DECLARE

ficha3_3 clob :='                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Sistemas productores -->          <Id>14</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Publicacion SAC -->          <Id>15</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>Si</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_3 WHERE ID='3';
END;
/

DECLARE

ficha3_4 clob :='    <Area> <!--Area de Contexto -->      <Id_Area_Asociada>2</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Productor -->          <Id>16</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia institucional -->          <Id>17</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia archivistica -->          <Id>18</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_4 WHERE ID='3';
END;
/

DECLARE

ficha3_5 clob :='          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Alcance y Contenido -->      <Id_Area_Asociada>3</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Alcance y contenido -->          <Id>38</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Valoración porcentaje -->          <Id>48</Id>          <Tipo>4</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Numerico>2</Tipo_Numerico>            <Validacion>              <Rango Minimo="0" Maximo="100"></Rango>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_5 WHERE ID='3';
END;
/

DECLARE

ficha3_6 clob :='        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->          <Id>47</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>Si</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Condiciones de Acceso y Seguridad -->      <Id_Area_Asociada>4</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Condiciones de acceso -->          <Id>22</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Condiciones de reproduccion --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_6 WHERE ID='3';
END;
/

DECLARE

ficha3_7 clob :='          <Id>23</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas -->          <Id>24</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->          <Id>25</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_7 WHERE ID='3';
END;
/

DECLARE

ficha3_8 clob :='        </Campo>        <Campo Tipo="Dato"><!--Instrumentos descripcion -->          <Id>26</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Documentacion Asociada -->      <Id_Area_Asociada>5</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Existencia de originales -->          <Id>27</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->          <Id>28</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_8 WHERE ID='3';
END;
/

DECLARE

ficha3_9 clob :='          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Existencia de copias -->          <Id>29</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->          <Id>30</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_9 WHERE ID='3';
END;
/

DECLARE

ficha3_10 clob :='        <Campo Tipo="Dato"><!--Nota publicacion -->          <Id>32</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Notas -->      <Id_Area_Asociada>6</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Notas -->          <Id>35</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de control de la descripcion -->      <Id_Area_Asociada>7</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Normas utilizadas -->          <Id>33</Id>          <Tipo>1</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_10 WHERE ID='3';
END;
/

DECLARE

ficha3_11 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>ISAD(G)</Valor_Inicial>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descriptores -->      <Id_Area_Asociada>8</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descriptor -->          <Id>44</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>  </Informacion_Campos> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_11 WHERE ID='3';
END;
/

DECLARE

ficha3_12 clob :='  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosClasificadorSeriesImpl</Clase_Generar_Automaticos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha3_12 WHERE ID='3';
END;
/

--Ficha: 'ISAD(G) Nivel de Descripción Fondo'

DECLARE

ficha4 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de Identidad -->      <Id_Area_Asociada>1</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Codigo Referencia -->          <Id>-1</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Titulo -->          <Id>-3</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Dato"><!--Fecha inicial -->          <Id>3</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='4';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('4','ISAD(G) Nivel de Descripción Fondo',1,2,0,ficha4);
END;
/

DECLARE

ficha4_1 clob :='                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fecha Final -->          <Id>4</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" />                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_1 WHERE ID='4';
END;
/

DECLARE

ficha4_2 clob :='        <Campo Tipo="Especial"><!--Nivel de descripcion -->          <Id>-4</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!--Volumen y soporte documental -->          <Id>7</Id>          <Editable>N</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Cantidad -->              <Id>214</Id>              <Tipo>4</Tipo>              <Multivalor>S</Multivalor>              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Soporte Documental -->              <Id>8</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>N</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_FORMAS_DOCUMENTALES</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_2 WHERE ID='4';
END;
/

DECLARE

ficha4_3 clob :='                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Sistemas productores -->          <Id>14</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Publicacion SAC -->          <Id>15</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>Si</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_3 WHERE ID='4';
END;
/

DECLARE

ficha4_4 clob :='    <Area> <!--Area de Contexto -->      <Id_Area_Asociada>2</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Productor -->          <Id>16</Id>          <Tipo>5</Tipo>          <Multivalor>N</Multivalor>          <Editable>N</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_FAMILIA,ID_LIST_PERSONA,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia institucional -->          <Id>17</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_4 WHERE ID='4';
END;
/

DECLARE

ficha4_5 clob :='        <Campo Tipo="Dato"><!--Historia archivistica -->          <Id>18</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Alcance y Contenido -->      <Id_Area_Asociada>3</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Alcance y contenido -->          <Id>38</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Valoración porcentaje -->          <Id>48</Id>          <Tipo>4</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_5 WHERE ID='4';
END;
/

DECLARE

ficha4_6 clob :='          <Informacion_Especifica>            <Tipo_Numerico>2</Tipo_Numerico>            <Validacion>              <Rango Minimo="0" Maximo="100"></Rango>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Admite nuevos ingresos -->          <Id>47</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>Si</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Condiciones de Acceso y Seguridad -->      <Id_Area_Asociada>4</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Condiciones de acceso -->          <Id>22</Id>          <Tipo>2</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_6 WHERE ID='4';
END;
/

DECLARE

ficha4_7 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->          <Id>23</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas -->          <Id>24</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_7 WHERE ID='4';
END;
/

DECLARE

ficha4_8 clob :='        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->          <Id>25</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Instrumentos descripcion -->          <Id>26</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Documentacion Asociada -->      <Id_Area_Asociada>5</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Existencia de originales -->          <Id>27</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_8 WHERE ID='4';
END;
/

DECLARE

ficha4_9 clob :='          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->          <Id>28</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Existencia de copias -->          <Id>29</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_9 WHERE ID='4';
END;
/

DECLARE

ficha4_10 clob :='        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->          <Id>30</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nota publicacion -->          <Id>32</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Notas -->      <Id_Area_Asociada>6</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Notas -->          <Id>35</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_10 WHERE ID='4';
END;
/

DECLARE

ficha4_11 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de control de la descripcion -->      <Id_Area_Asociada>7</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Normas utilizadas -->          <Id>33</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>ISAD(G)</Valor_Inicial>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descriptores -->      <Id_Area_Asociada>8</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descriptor -->          <Id>44</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_11 WHERE ID='4';
END;
/

DECLARE

ficha4_12 clob :='            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>  </Informacion_Campos>  <Clase_Generar_Automaticos>descripcion.model.automaticos.ADReglaGenDatosFondoImpl</Clase_Generar_Automaticos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha4_12 WHERE ID='4';
END;
/

--Ficha: 'ISAAR(CPF)'

DECLARE

ficha5 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de identificación-->      <Id_Area_Asociada>9</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Nombre -->          <Id>-5</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Tipo de entidad-->          <Id>100</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_TIPOENTIDAD</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"> <!--Formas(s) autorizada(s) del nombre-->          <Id>101</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='5';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('5','ISAAR(CPF)',2,7,0,ficha5);
END;
/

DECLARE

ficha5_1 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Formas paralelas de nombre-->          <Id>102</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Formas normalizadas del nombre según otras reglas-->          <Id>103</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Otras formas de nombre-->          <Id>104</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_1 WHERE ID='5';
END;
/

DECLARE

ficha5_2 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Identificadores para instituciones-->          <Id>105</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descripción-->      <Id_Area_Asociada>10</Id_Area_Asociada>      <Campos>         <Campo Tipo="Tabla"><!--Fechas de Existencia-->          <Id>104</Id>          <Editable>S</Editable>          <Columnas>        	<Campo Tipo="Dato"><!--Fecha Inicio existencia-->          		<Id>106</Id>          		<Tipo>3</Tipo>          		<Multivalor>S</Multivalor>          		<Editable>S</Editable>          		<Obligatorio>N</Obligatorio>          		<Informacion_Especifica>            		<Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_2 WHERE ID='5';
END;
/

DECLARE

ficha5_3 clob :='              			<Formatos>                			<Formato Tipo="AAAA" Sep="" />                			<Formato Tipo="MMAAAA" Sep="/" />                			<Formato Tipo="DDMMAAAA" Sep="/" />                			<Formato Tipo="AAAAMM" Sep="/" />                			<Formato Tipo="AAAAMMDD" Sep="/" />                			<Formato Tipo="S" Sep="" />              			</Formatos>            		</Validacion>          		</Informacion_Especifica>          		<Descripcion></Descripcion>        	</Campo>	        <Campo Tipo="Dato"><!--Fecha Final existencia-->    	      	<Id>218</Id>        	  	<Tipo>3</Tipo>          		<Multivalor>S</Multivalor>          		<Editable>S</Editable>          		<Obligatorio>N</Obligatorio>          		<Informacion_Especifica>	            	<Validacion>	              		<Formatos>	                		<Formato Tipo="AAAA" Sep="" />	                		<Formato Tipo="MMAAAA" Sep="/" />	                		<Formato Tipo="DDMMAAAA" Sep="/" />	                		<Formato Tipo="AAAAMM" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_3 WHERE ID='5';
END;
/

DECLARE

ficha5_4 clob :='                			<Formato Tipo="AAAAMMDD" Sep="/" />	                		<Formato Tipo="S" Sep="" />	              		</Formatos>	            	</Validacion>          		</Informacion_Especifica>          		<Descripcion></Descripcion>        	</Campo>           </Columnas>		</Campo>        <Campo Tipo="Dato"> <!--Historia-->          <Id>107</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lugar(es)-->          <Id>108</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_4 WHERE ID='5';
END;
/

DECLARE

ficha5_5 clob :='              <Ids_Listas_Descriptoras>ID_LIST_CONCEJO,ID_LIST_POBLACION,ID_LIST_GEOGRAFICO</Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Estatuto jurídico-->          <Id>109</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Funciones, ocupaciones y actividades-->          <Id>110</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Atribución(es) / Fuente(s) legal(es)-->          <Id>111</Id>          <Tipo>2</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_5 WHERE ID='5';
END;
/

DECLARE

ficha5_6 clob :='          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Estructura(s) interna(s) / Genealogía-->          <Id>112</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Contexto general-->          <Id>113</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de relaciones-->      <Id_Area_Asociada>11</Id_Area_Asociada>      <Campos> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_6 WHERE ID='5';
END;
/

DECLARE

ficha5_7 clob :='        <Campo Tipo="Tabla"><!--Entidades relacionadas-->          <Id>100</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre/Identificador-->              <Id>114</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>3</Tipo_Referencia>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"> <!--Naturaleza de la relación-->              <Id>115</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_NRELACION</Id_TblVld>                </Validacion>              </Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_7 WHERE ID='5';
END;
/

DECLARE

ficha5_8 clob :='              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Descripción-->              <Id>116</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--F. Inicio-->              <Id>117</Id>              <Tipo>3</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_8 WHERE ID='5';
END;
/

DECLARE

ficha5_9 clob :='                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--F. Fin-->              <Id>118</Id>              <Tipo>3</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_9 WHERE ID='5';
END;
/

DECLARE

ficha5_10 clob :='          </Columnas>        </Campo>      </Campos>    </Area>    <Area> <!--Area de control-->      <Id_Area_Asociada>12</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Identificador del registro de la autoridad-->          <Id>119</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"> <!--Identificador de la institución-->          <Id>120</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Reglas y/o convenciones-->          <Id>121</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_10 WHERE ID='5';
END;
/

DECLARE

ficha5_11 clob :='          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Estado de elaboración-->          <Id>122</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_ESTADOELAB</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nivel de detalle-->          <Id>123</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_NDETALLE</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_11 WHERE ID='5';
END;
/

DECLARE

ficha5_12 clob :='        </Campo>        <Campo Tipo="Dato"><!--Fecha creación registro autoridad-->          <Id>124</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" />                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas y escrituras-->          <Id>127</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_12 WHERE ID='5';
END;
/

DECLARE

ficha5_13 clob :='            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fuentes-->          <Id>128</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Notas de mantenimiento-->          <Id>129</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Relación de instituciones, personas y familias con documentos de archivo y otros recursos-->      <Id_Area_Asociada>13</Id_Area_Asociada> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_13 WHERE ID='5';
END;
/

DECLARE

ficha5_14 clob :='      <Campos>        <Campo Tipo="Tabla"><!--Recursos relacionados-->          <Id>101</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Título-->              <Id>130</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"> <!--Identificador-->              <Id>131</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Tipo-->              <Id>132</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_14 WHERE ID='5';
END;
/

DECLARE

ficha5_15 clob :='              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_TIPORECURSO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Naturaleza-->              <Id>133</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_TIPORELACION</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Fecha-->              <Id>134</Id>              <Tipo>3</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_15 WHERE ID='5';
END;
/

DECLARE

ficha5_16 clob :='              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>      </Campos>    </Area>  </Informacion_Campos>  <Clase_Generar_Automaticos/>  <Eventos></Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha5_16 WHERE ID='5';
END;
/

--Ficha: 'ISAD(G) Nivel de Descripción Fracción de Serie'

DECLARE

ficha6 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Informacion_Campos>    <Area> <!--Area de Identidad -->      <Id_Area_Asociada>1</Id_Area_Asociada>      <Campos>        <Campo Tipo="Especial"><!--Codigo Referencia -->          <Id>-1</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"> <!--Identificacion -->          <Id>1</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!-- Rangos de expedientes -->          <Id>102</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Desde -->              <Id>201</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='6';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('6','ISAD(G) Nivel de Descripción Fracción de Serie',1,6,62,ficha6);
END;
/

DECLARE

ficha6_1 clob :='              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Hasta -->              <Id>202</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Especial"><!--Titulo -->          <Id>-3</Id>          <Descripcion></Descripcion>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Tipo>1</Tipo>        </Campo>        <Campo Tipo="Tabla"><!-- Emplazamiento -->          <Id>3</Id>          <Editable>S</Editable>          <Informacion_Especifica>            <Validacion>              <Sistema_Externo>2</Sistema_Externo>            </Validacion>          </Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_1 WHERE ID='6';
END;
/

DECLARE

ficha6_2 clob :='          <Columnas>            <Campo Tipo="Dato"><!--Pais -->              <Id>2</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Provincia -->              <Id>39</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_2 WHERE ID='6';
END;
/

DECLARE

ficha6_3 clob :='                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Concejo -->              <Id>40</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Poblacion -->              <Id>41</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_3 WHERE ID='6';
END;
/

DECLARE

ficha6_4 clob :='                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Localizacion -->              <Id>42</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Validado -->              <Id>212</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion>              </Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_4 WHERE ID='6';
END;
/

DECLARE

ficha6_5 clob :='              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Fecha inicial -->          <Id>3</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Fecha Final -->          <Id>4</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_5 WHERE ID='6';
END;
/

DECLARE

ficha6_6 clob :='          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>                <Validacion>                  <Formatos>                    <Formato Tipo="AAAA" Sep="" />                    <Formato Tipo="MMAAAA" Sep="/" />                    <Formato Tipo="DDMMAAAA" Sep="/" />                    <Formato Tipo="AAAAMM" Sep="/" />                	<Formato Tipo="AAAAMMDD" Sep="/" />                    <Formato Tipo="S" Sep="" />                  </Formatos>                </Validacion>              </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Especial"><!--Nivel de descripcion -->          <Id>-4</Id>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Tabla"><!--Volumen y soporte -->          <Id>1</Id>          <Editable>S</Editable>          <Columnas>	    	<Campo Tipo="Dato"><!--Soporte -->              <Id>6</Id>              <Tipo>1</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_6 WHERE ID='6';
END;
/

DECLARE

ficha6_7 clob :='              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Formato -->              <Id>5</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Cantidad -->              <Id>7</Id>              <Tipo>4</Tipo>              <Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_7 WHERE ID='6';
END;
/

DECLARE

ficha6_8 clob :='              <Editable>S</Editable>              <Obligatorio>S</Obligatorio>              <Informacion_Especifica>                <Tipo_Numerico>1</Tipo_Numerico>                <Validacion>                  <Rango Minimo="0" Maximo=""></Rango>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>         <Campo Tipo="Tabla"><!--Interesados -->          <Id>2</Id>          <Editable>S</Editable>           <Informacion_Especifica>            <Validacion>              <Sistema_Externo>1</Sistema_Externo>            </Validacion>          </Informacion_Especifica>          <Columnas>			 <Campo Tipo="Dato"><!--Principal -->              <Id>213</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_8 WHERE ID='6';
END;
/

DECLARE

ficha6_9 clob :='                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Identidad -->              <Id>9</Id>              <Tipo>5</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Tipo_Referencia>1</Tipo_Referencia>                <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Numero de identidad -->              <Id>10</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_9 WHERE ID='6';
END;
/

DECLARE

ficha6_10 clob :='              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Rol -->              <Id>11</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>                </Validacion>              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Validado -->              <Id>12</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica>                <Validacion>                  <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>                </Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_10 WHERE ID='6';
END;
/

DECLARE

ficha6_11 clob :='              </Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--IdTercero-->              <Id>51</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Nombre sistema productor -->          <Id>14</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Publicacion SAC -->          <Id>15</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_11 WHERE ID='6';
END;
/

DECLARE

ficha6_12 clob :='          <Obligatorio>N</Obligatorio>          <Valor_Inicial>No</Valor_Inicial>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Contexto -->      <Id_Area_Asociada>2</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Productor -->          <Id>16</Id>          <Tipo>5</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>S</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>                  <Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_12 WHERE ID='6';
END;
/

DECLARE

ficha6_13 clob :='        <Campo Tipo="Dato"><!--Historia institucional -->          <Id>17</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Historia archivistica -->          <Id>18</Id>          <Tipo>2</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Ingreso por -->          <Id>203</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion>              <Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_13 WHERE ID='6';
END;
/

DECLARE

ficha6_14 clob :='            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Código de transferencia -->          <Id>204</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Referencias de ingreso -->          <Id>205</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Hoja de Entrega: -->          <Id>206</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_14 WHERE ID='6';
END;
/

DECLARE

ficha6_15 clob :='          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Alcance y Contenido -->      <Id_Area_Asociada>3</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descripcion Contenido -->          <Id>13</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>        </Campo>        <Campo Tipo="Tabla"><!--Alcance y contenido -->          <Id>4</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre del documento -->              <Id>19</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_15 WHERE ID='6';
END;
/

DECLARE

ficha6_16 clob :='            <Campo Tipo="Dato"><!--Descripcion del documento -->              <Id>43</Id>              <Tipo>2</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Tabla"><!--Documentos electronicos -->          <Id>6</Id>          <Editable>S</Editable>          <Columnas>            <Campo Tipo="Dato"><!--Nombre del documento -->              <Id>49</Id>              <Tipo>1</Tipo>              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>            <Campo Tipo="Dato"><!--Descripcion del documento -->              <Id>50</Id>              <Tipo>2</Tipo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_16 WHERE ID='6';
END;
/

DECLARE

ficha6_17 clob :='              <Multivalor>S</Multivalor>              <Editable>S</Editable>              <Obligatorio>N</Obligatorio>              <Informacion_Especifica></Informacion_Especifica>              <Descripcion></Descripcion>            </Campo>          </Columnas>        </Campo>        <Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->          <Id>20</Id>          <Tipo>3</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Formatos>                <Formato Tipo="AAAA" Sep="" />                <Formato Tipo="MMAAAA" Sep="/" />                <Formato Tipo="DDMMAAAA" Sep="/" />                <Formato Tipo="AAAAMM" Sep="/" />                <Formato Tipo="AAAAMMDD" Sep="/" />                <Formato Tipo="S" Sep="" />              </Formatos>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_17 WHERE ID='6';
END;
/

DECLARE

ficha6_18 clob :='        </Campo>        <Campo Tipo="Dato"><!--Nuevos ingresos -->          <Id>21</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>2</Tipo_Referencia>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Condiciones de Acceso y Seguridad -->      <Id_Area_Asociada>4</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Condiciones de acceso -->          <Id>22</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Condiciones de reproduccion -->          <Id>23</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_18 WHERE ID='6';
END;
/

DECLARE

ficha6_19 clob :='          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Lenguas -->          <Id>24</Id>          <Tipo>1</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Caracteristicas fisicas -->          <Id>25</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Instrumentos descripcion -->          <Id>26</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_19 WHERE ID='6';
END;
/

DECLARE

ficha6_20 clob :='          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de Documentacion Asociada -->      <Id_Area_Asociada>5</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Existencia de originales -->          <Id>27</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->          <Id>28</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_20 WHERE ID='6';
END;
/

DECLARE

ficha6_21 clob :='          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Existencia de copias -->          <Id>29</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Validacion>              <Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->          <Id>30</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Unidades relacionadas -->          <Id>31</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_21 WHERE ID='6';
END;
/

DECLARE

ficha6_22 clob :='          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>2</Tipo_Referencia>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>        <Campo Tipo="Dato"><!--Nota publicacion -->          <Id>32</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de Notas -->      <Id_Area_Asociada>6</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Notas -->          <Id>35</Id>          <Tipo>2</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_22 WHERE ID='6';
END;
/

DECLARE

ficha6_23 clob :='          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area><!--Area de control de la descripcion -->      <Id_Area_Asociada>7</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Normas utilizadas -->          <Id>33</Id>          <Tipo>1</Tipo>          <Multivalor>N</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Valor_Inicial>ISAD(G)</Valor_Inicial>          <Informacion_Especifica></Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>    <Area> <!--Area de descriptores -->      <Id_Area_Asociada>8</Id_Area_Asociada>      <Campos>        <Campo Tipo="Dato"><!--Descriptor -->          <Id>44</Id>          <Tipo>5</Tipo>          <Multivalor>S</Multivalor>          <Editable>S</Editable>          <Obligatorio>N</Obligatorio>          <Informacion_Especifica>            <Tipo_Referencia>1</Tipo_Referencia>            <Validacion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_23 WHERE ID='6';
END;
/

DECLARE

ficha6_24 clob :='              <Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>            </Validacion>          </Informacion_Especifica>          <Descripcion></Descripcion>        </Campo>      </Campos>    </Area>  </Informacion_Campos>  <Eventos>    <Evento>        <Tipo>2</Tipo>        <Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>    </Evento>    <Evento>        <Tipo>2</Tipo>        <Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>    </Evento>  	<Evento>  		<Tipo>3</Tipo>  		<Clase>descripcion.model.eventos.rangos.EventoNormalizarRangosFichaImpl</Clase>  	</Evento>  	<Evento>  		<Tipo>4</Tipo>  		<Clase>descripcion.model.eventos.rangos.EventoEliminarRangosNormalizadosFichaImpl</Clase>  	</Evento>  </Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha6_24 WHERE ID='6';
END;
/

--Ficha: 'ISAD(G) Nivel de Descripción Unidad Documental ENI'

DECLARE

ficha7 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">	<Informacion_Campos>		<Area> <!--Area de Identidad -->			<Id_Area_Asociada>1</Id_Area_Asociada>			<Campos>				<Campo Tipo="Especial"><!--Codigo Referencia -->					<Id>-1</Id>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"> <!--Identificacion -->					<Id>1</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Especial"><!--Numero expediente -->					<Id>-2</Id>					<Descripcion></Descripcion>					<Editable>S</Editable>					<Tipo>1</Tipo>				</Campo>				<Campo Tipo="Especial"><!--Titulo -->					<Id>-3</Id>					<Descripcion></Descripcion>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Tipo>1</Tipo>				</Campo>				<Campo Tipo="Tabla"><!-- Emplazamiento -->					<Id>3</Id>					<Editable>S</Editable> ';

BEGIN
DELETE FROM ADFICHA WHERE ID='10';
INSERT INTO ADFICHA (ID,NOMBRE,TIPONORMA,TIPONIVEL,SUBTIPONIVEL,DEFINICION)VALUES('10','ISAD(G) Nivel de Descripción Unidad Documental ENI',1,6,61,ficha7);
END;
/

DECLARE

ficha7_1 clob :='					<Informacion_Especifica>						<Validacion>							<Sistema_Externo>2</Sistema_Externo>						</Validacion>					</Informacion_Especifica>					<Columnas>						<Campo Tipo="Dato"><!--Pais -->							<Id>2</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_PAIS</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Provincia -->							<Id>39</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_PROVINCIA</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_1 WHERE ID='10';
END;
/

DECLARE

ficha7_2 clob :='							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Concejo -->							<Id>40</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_CONCEJO</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Poblacion -->							<Id>41</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_POBLACION</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Localizacion --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_2 WHERE ID='10';
END;
/

DECLARE

ficha7_3 clob :='							<Id>42</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Validado -->							<Id>212</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Fecha inicial -->					<Id>3</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" /> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_3 WHERE ID='10';
END;
/

DECLARE

ficha7_4 clob :='								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Fecha Final -->					<Id>4</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" />								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Especial"><!--Nivel de descripcion -->					<Id>-4</Id>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Tabla"><!--Volumen y soporte -->					<Id>1</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_4 WHERE ID='10';
END;
/

DECLARE

ficha7_5 clob :='					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Soporte -->							<Id>6</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SOPORTE</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Formato -->							<Id>5</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_FORMATO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Cantidad -->							<Id>7</Id>							<Tipo>4</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_5 WHERE ID='10';
END;
/

DECLARE

ficha7_6 clob :='							<Informacion_Especifica>								<Tipo_Numerico>1</Tipo_Numerico>								<Validacion>									<Rango Minimo="0" Maximo=""></Rango>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Tabla"><!--Interesados -->					<Id>2</Id>					<Editable>S</Editable>					<Informacion_Especifica>						<Validacion>							<Sistema_Externo>1</Sistema_Externo>						</Validacion>					</Informacion_Especifica>					<Columnas>						<Campo Tipo="Dato"><!--Principal -->							<Id>213</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Identidad -->							<Id>9</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_6 WHERE ID='10';
END;
/

DECLARE

ficha7_7 clob :='							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Tipo_Referencia>1</Tipo_Referencia>								<Validacion>									<Ids_Listas_Descriptoras>ID_LIST_INTERESADO</Ids_Listas_Descriptoras>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Numero de identidad -->							<Id>10</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Rol -->							<Id>11</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_ROLES_INTERESADO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_7 WHERE ID='10';
END;
/

DECLARE

ficha7_8 clob :='						</Campo>						<Campo Tipo="Dato"><!--Validado -->							<Id>12</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--IdTercero -->							<Id>51</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Nombre sistema productor -->					<Id>14</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_8 WHERE ID='10';
END;
/

DECLARE

ficha7_9 clob :='				<Campo Tipo="Dato"><!--Publicacion SAC -->					<Id>15</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Valor_Inicial>No</Valor_Inicial>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Versión NTI -->					<Id>1001</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Estado -->					<Id>1002</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Tabla"><!--Firmas -->					<Id>105</Id> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_9 WHERE ID='10';
END;
/

DECLARE

ficha7_10 clob :='					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Tipo de Firma -->							<Id>1003</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Valor_CSV -->							<Id>1004</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Definición de Generación CSV -->							<Id>1005</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Identificador interno Firma (ENLACE) --> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_10 WHERE ID='10';
END;
/

DECLARE

ficha7_11 clob :='							<Id>1006</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Contexto -->			<Id_Area_Asociada>2</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Productor -->					<Id>16</Id>					<Tipo>5</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>S</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Ids_Listas_Descriptoras>ID_LIST_BDORGANIZACION,ID_LIST_ORGANO,ID_LIST_INSTITUCION</Ids_Listas_Descriptoras>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Historia institucional -->					<Id>17</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_11 WHERE ID='10';
END;
/

DECLARE

ficha7_12 clob :='					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Historia archivistica -->					<Id>18</Id>					<Tipo>2</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Ingreso por -->					<Id>203</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Id_TblVld>ID_TBLVLD_INGRESOS</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Código de transferencia -->					<Id>204</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_12 WHERE ID='10';
END;
/

DECLARE

ficha7_13 clob :='					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Referencias de ingreso -->					<Id>205</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Hoja de Entrega: -->					<Id>206</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Alcance y Contenido -->			<Id_Area_Asociada>3</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Descripcion Contenido -->					<Id>13</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>				</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_13 WHERE ID='10';
END;
/

DECLARE

ficha7_14 clob :='				<Campo Tipo="Tabla"><!--Alcance y contenido -->					<Id>4</Id>					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Nombre del documento -->							<Id>19</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Descripcion del documento -->							<Id>43</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>					</Columnas>				</Campo>				<Campo Tipo="Tabla"><!--Documentos electronicos -->					<Id>6</Id>					<Editable>S</Editable>					<Columnas>						<Campo Tipo="Dato"><!--Nombre del documento -->							<Id>49</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_14 WHERE ID='10';
END;
/

DECLARE

ficha7_15 clob :='							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Descripcion del documento -->							<Id>50</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Versión NTI -->							<Id>1011</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Órgano -->							<Id>1012</Id>							<Tipo>5</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_15 WHERE ID='10';
END;
/

DECLARE

ficha7_16 clob :='							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Fecha de Captura -->							<Id>1013</Id>							<Tipo>3</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica>								<Validacion>									<Formatos>										<Formato Tipo="AAAA" Sep="" />										<Formato Tipo="MMAAAA" Sep="/" />										<Formato Tipo="DDMMAAAA" Sep="/" />										<Formato Tipo="AAAAMM" Sep="/" />										<Formato Tipo="AAAAMMDD" Sep="/" />										<Formato Tipo="S" Sep="" />									</Formatos>								</Validacion>							</Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Origen -->							<Id>1014</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_16 WHERE ID='10';
END;
/

DECLARE

ficha7_17 clob :='							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Estado Elaboración -->							<Id>1015</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Nombre Formato -->							<Id>1016</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!--Documento Electrónico: Tipo Documental -->							<Id>1017</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_17 WHERE ID='10';
END;
/

DECLARE

ficha7_18 clob :='						<Campo Tipo="Dato"><!-- Documento Electrónico: Tipo Firma -->							<Id>1018</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Valor CSV -->							<Id>1019</Id>							<Tipo>2</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónco: Definición Generación								CSV -->							<Id>1020</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_18 WHERE ID='10';
END;
/

DECLARE

ficha7_19 clob :='						<Campo Tipo="Dato"><!-- Documento Electronico: Identificador Documento								Origen -->							<Id>1021</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Id Interno (ENLACE) -->							<Id>1022</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo>						<Campo Tipo="Dato"><!-- Documento Electrónico: Identificador interno								Firma (ENLACE) -->							<Id>1023</Id>							<Tipo>1</Tipo>							<Multivalor>S</Multivalor>							<Editable>S</Editable>							<Obligatorio>N</Obligatorio>							<Informacion_Especifica></Informacion_Especifica>							<Descripcion></Descripcion>						</Campo> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_19 WHERE ID='10';
END;
/

DECLARE

ficha7_20 clob :='					</Columnas>				</Campo>				<Campo Tipo="Dato"><!--Valoracion_FechaEliminacion -->					<Id>20</Id>					<Tipo>3</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Formatos>								<Formato Tipo="AAAA" Sep="" />								<Formato Tipo="MMAAAA" Sep="/" />								<Formato Tipo="DDMMAAAA" Sep="/" />								<Formato Tipo="AAAAMM" Sep="/" />								<Formato Tipo="AAAAMMDD" Sep="/" />								<Formato Tipo="S" Sep="" />							</Formatos>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Nuevos ingresos -->					<Id>21</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>2</Tipo_Referencia>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_20 WHERE ID='10';
END;
/

DECLARE

ficha7_21 clob :='		<Area><!--Area de Condiciones de Acceso y Seguridad -->			<Id_Area_Asociada>4</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Condiciones de acceso -->					<Id>22</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Condiciones de reproduccion -->					<Id>23</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Lenguas -->					<Id>24</Id>					<Tipo>1</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_LENGUA</Id_TblVld>						</Validacion>					</Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_21 WHERE ID='10';
END;
/

DECLARE

ficha7_22 clob :='					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Caracteristicas fisicas -->					<Id>25</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Instrumentos descripcion -->					<Id>26</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area><!--Area de Documentacion Asociada -->			<Id_Area_Asociada>5</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Existencia de originales -->					<Id>27</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_22 WHERE ID='10';
END;
/

DECLARE

ficha7_23 clob :='						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Originales, descripcion y ubicacion -->					<Id>28</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Existencia de copias -->					<Id>29</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Validacion>							<Id_TblVld>ID_TBLVLD_SINO</Id_TblVld>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Copias, descripcion y ubicacion -->					<Id>30</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_23 WHERE ID='10';
END;
/

DECLARE

ficha7_24 clob :='					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Unidades relacionadas -->					<Id>31</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>2</Tipo_Referencia>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>				<Campo Tipo="Dato"><!--Nota publicacion -->					<Id>32</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de Notas -->			<Id_Area_Asociada>6</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Notas -->					<Id>35</Id>					<Tipo>2</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_24 WHERE ID='10';
END;
/

DECLARE

ficha7_25 clob :='				</Campo>			</Campos>		</Area>		<Area><!--Area de control de la descripcion -->			<Id_Area_Asociada>7</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Normas utilizadas -->					<Id>33</Id>					<Tipo>1</Tipo>					<Multivalor>N</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Valor_Inicial>ISAD(G)</Valor_Inicial>					<Informacion_Especifica></Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area>		<Area> <!--Area de descriptores -->			<Id_Area_Asociada>8</Id_Area_Asociada>			<Campos>				<Campo Tipo="Dato"><!--Descriptor -->					<Id>44</Id>					<Tipo>5</Tipo>					<Multivalor>S</Multivalor>					<Editable>S</Editable>					<Obligatorio>N</Obligatorio>					<Informacion_Especifica>						<Tipo_Referencia>1</Tipo_Referencia>						<Validacion>							<Ids_Listas_Descriptoras></Ids_Listas_Descriptoras>						</Validacion>					</Informacion_Especifica>					<Descripcion></Descripcion>				</Campo>			</Campos>		</Area> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_25 WHERE ID='10';
END;
/

DECLARE

ficha7_26 clob :='	</Informacion_Campos>	<Eventos>		<Evento>			<Tipo>2</Tipo>			<Clase>descripcion.model.eventos.interesados.EventoComprobarInteresadoPrincipal</Clase>		</Evento>		<Evento>			<Tipo>2</Tipo>			<Clase>descripcion.model.eventos.fechas.EventoValidarFechasExtremas</Clase>		</Evento>	</Eventos></Definicion_Ficha> ';

BEGIN
UPDATE ADFICHA SET DEFINICION=DEFINICION || ficha7_26 WHERE ID='10';
END;
/

--FORMATOS

--Formato: 'Consulta de ficha de unidad documental - Público'

DECLARE

fmt1 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='1';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('1','1',1,1,'Consulta de ficha de unidad documental - Público',fmt1);
END;
/

DECLARE

fmt1_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_1 WHERE ID='1';
END;
/

DECLARE

fmt1_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_2 WHERE ID='1';
END;
/

DECLARE

fmt1_3 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_3 WHERE ID='1';
END;
/

DECLARE

fmt1_4 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_4 WHERE ID='1';
END;
/

DECLARE

fmt1_5 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta>		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_5 WHERE ID='1';
END;
/

DECLARE

fmt1_6 clob :='		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_6 WHERE ID='1';
END;
/

DECLARE

fmt1_7 clob :='						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_7 WHERE ID='1';
END;
/

DECLARE

fmt1_8 clob :='							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_8 WHERE ID='1';
END;
/

DECLARE

fmt1_9 clob :='							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_9 WHERE ID='1';
END;
/

DECLARE

fmt1_10 clob :='					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_10 WHERE ID='1';
END;
/

DECLARE

fmt1_11 clob :='							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_11 WHERE ID='1';
END;
/

DECLARE

fmt1_12 clob :='						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_12 WHERE ID='1';
END;
/

DECLARE

fmt1_13 clob :='					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_13 WHERE ID='1';
END;
/

DECLARE

fmt1_14 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_14 WHERE ID='1';
END;
/

DECLARE

fmt1_15 clob :='							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_15 WHERE ID='1';
END;
/

DECLARE

fmt1_16 clob :='					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_16 WHERE ID='1';
END;
/

DECLARE

fmt1_17 clob :='				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt1_17 WHERE ID='1';
END;
/

--Formato: 'Edición de ficha de unidad documental - Público'

DECLARE

fmt2 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='2';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('2','1',2,1,'Edición de ficha de unidad documental - Público',fmt2);
END;
/

DECLARE

fmt2_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_1 WHERE ID='2';
END;
/

DECLARE

fmt2_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_2 WHERE ID='2';
END;
/

DECLARE

fmt2_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_3 WHERE ID='2';
END;
/

DECLARE

fmt2_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_4 WHERE ID='2';
END;
/

DECLARE

fmt2_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_5 WHERE ID='2';
END;
/

DECLARE

fmt2_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_6 WHERE ID='2';
END;
/

DECLARE

fmt2_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_7 WHERE ID='2';
END;
/

DECLARE

fmt2_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_8 WHERE ID='2';
END;
/

DECLARE

fmt2_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_9 WHERE ID='2';
END;
/

DECLARE

fmt2_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_10 WHERE ID='2';
END;
/

DECLARE

fmt2_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_11 WHERE ID='2';
END;
/

DECLARE

fmt2_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_12 WHERE ID='2';
END;
/

DECLARE

fmt2_13 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_13 WHERE ID='2';
END;
/

DECLARE

fmt2_14 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_14 WHERE ID='2';
END;
/

DECLARE

fmt2_15 clob :='				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_15 WHERE ID='2';
END;
/

DECLARE

fmt2_16 clob :='								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_16 WHERE ID='2';
END;
/

DECLARE

fmt2_17 clob :='			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_17 WHERE ID='2';
END;
/

DECLARE

fmt2_18 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt2_18 WHERE ID='2';
END;
/

--Formato: 'Consulta de ficha de serie - Público'

DECLARE

fmt3 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha inicial:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='3';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('3','2',1,1,'Consulta de ficha de serie - Público',fmt3);
END;
/

DECLARE

fmt3_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_1 WHERE ID='3';
END;
/

DECLARE

fmt3_2 clob :='          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistema Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_2 WHERE ID='3';
END;
/

DECLARE

fmt3_3 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>5</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Productores"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Productor"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="34"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_3 WHERE ID='3';
END;
/

DECLARE

fmt3_4 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Inicio"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="36">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="37">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_4 WHERE ID='3';
END;
/

DECLARE

fmt3_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="46">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_5 WHERE ID='3';
END;
/

DECLARE

fmt3_6 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_6 WHERE ID='3';
END;
/

DECLARE

fmt3_7 clob :='          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_7 WHERE ID='3';
END;
/

DECLARE

fmt3_8 clob :='    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_8 WHERE ID='3';
END;
/

DECLARE

fmt3_9 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_9 WHERE ID='3';
END;
/

DECLARE

fmt3_10 clob :='          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_10 WHERE ID='3';
END;
/

DECLARE

fmt3_11 clob :='      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt3_11 WHERE ID='3';
END;
/

--Formato: 'Edición de ficha de serie - Público'

DECLARE

fmt4 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha inicial:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='4';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('4','2',2,1,'Edición de ficha de serie - Público',fmt4);
END;
/

DECLARE

fmt4_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_1 WHERE ID='4';
END;
/

DECLARE

fmt4_2 clob :='          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistema Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_2 WHERE ID='4';
END;
/

DECLARE

fmt4_3 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>5</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Productores"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Productor"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="34"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_3 WHERE ID='4';
END;
/

DECLARE

fmt4_4 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Inicio"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="36">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="37">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_4 WHERE ID='4';
END;
/

DECLARE

fmt4_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="46">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_5 WHERE ID='4';
END;
/

DECLARE

fmt4_6 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_6 WHERE ID='4';
END;
/

DECLARE

fmt4_7 clob :='          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_7 WHERE ID='4';
END;
/

DECLARE

fmt4_8 clob :='    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_8 WHERE ID='4';
END;
/

DECLARE

fmt4_9 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_9 WHERE ID='4';
END;
/

DECLARE

fmt4_10 clob :='          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_10 WHERE ID='4';
END;
/

DECLARE

fmt4_11 clob :='      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt4_11 WHERE ID='4';
END;
/

--Formato: 'Consulta de ficha de clasificador de series - Público'

DECLARE

fmt5 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='5';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('5','3',1,1,'Consulta de ficha de clasificador de series - Público',fmt5);
END;
/

DECLARE

fmt5_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_1 WHERE ID='5';
END;
/

DECLARE

fmt5_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_2 WHERE ID='5';
END;
/

DECLARE

fmt5_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_3 WHERE ID='5';
END;
/

DECLARE

fmt5_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_4 WHERE ID='5';
END;
/

DECLARE

fmt5_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_5 WHERE ID='5';
END;
/

DECLARE

fmt5_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_6 WHERE ID='5';
END;
/

DECLARE

fmt5_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_7 WHERE ID='5';
END;
/

DECLARE

fmt5_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_8 WHERE ID='5';
END;
/

DECLARE

fmt5_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_9 WHERE ID='5';
END;
/

DECLARE

fmt5_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_10 WHERE ID='5';
END;
/

DECLARE

fmt5_11 clob :='      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt5_11 WHERE ID='5';
END;
/

--Formato: 'Edición de ficha de clasificador de series - Público'

DECLARE

fmt6 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='6';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('6','3',2,1,'Edición de ficha de clasificador de series - Público',fmt6);
END;
/

DECLARE

fmt6_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_1 WHERE ID='6';
END;
/

DECLARE

fmt6_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_2 WHERE ID='6';
END;
/

DECLARE

fmt6_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_3 WHERE ID='6';
END;
/

DECLARE

fmt6_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_4 WHERE ID='6';
END;
/

DECLARE

fmt6_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_5 WHERE ID='6';
END;
/

DECLARE

fmt6_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_6 WHERE ID='6';
END;
/

DECLARE

fmt6_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_7 WHERE ID='6';
END;
/

DECLARE

fmt6_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_8 WHERE ID='6';
END;
/

DECLARE

fmt6_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_9 WHERE ID='6';
END;
/

DECLARE

fmt6_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_10 WHERE ID='6';
END;
/

DECLARE

fmt6_11 clob :='      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt6_11 WHERE ID='6';
END;
/

--Formato: 'Consulta de ficha de fondo - Público'

DECLARE

fmt7 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='7';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('7','4',1,1,'Consulta de ficha de fondo - Público',fmt7);
END;
/

DECLARE

fmt7_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_1 WHERE ID='7';
END;
/

DECLARE

fmt7_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_2 WHERE ID='7';
END;
/

DECLARE

fmt7_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_3 WHERE ID='7';
END;
/

DECLARE

fmt7_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_4 WHERE ID='7';
END;
/

DECLARE

fmt7_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_5 WHERE ID='7';
END;
/

DECLARE

fmt7_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_6 WHERE ID='7';
END;
/

DECLARE

fmt7_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_7 WHERE ID='7';
END;
/

DECLARE

fmt7_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_8 WHERE ID='7';
END;
/

DECLARE

fmt7_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_9 WHERE ID='7';
END;
/

DECLARE

fmt7_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_10 WHERE ID='7';
END;
/

DECLARE

fmt7_11 clob :='      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt7_11 WHERE ID='7';
END;
/

--Formato: 'Edición de ficha de fondo - Público'

DECLARE

fmt8 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='8';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('8','4',2,1,'Edición de ficha de fondo - Público',fmt8);
END;
/

DECLARE

fmt8_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_1 WHERE ID='8';
END;
/

DECLARE

fmt8_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_2 WHERE ID='8';
END;
/

DECLARE

fmt8_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_3 WHERE ID='8';
END;
/

DECLARE

fmt8_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_4 WHERE ID='8';
END;
/

DECLARE

fmt8_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_5 WHERE ID='8';
END;
/

DECLARE

fmt8_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_6 WHERE ID='8';
END;
/

DECLARE

fmt8_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_7 WHERE ID='8';
END;
/

DECLARE

fmt8_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_8 WHERE ID='8';
END;
/

DECLARE

fmt8_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_9 WHERE ID='8';
END;
/

DECLARE

fmt8_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_10 WHERE ID='8';
END;
/

DECLARE

fmt8_11 clob :='      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt8_11 WHERE ID='8';
END;
/

--Formato: 'Consulta de ficha ISAAR - Público'

DECLARE

fmt9 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de identificación"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-5">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Tipo de entidad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="100">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='9';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('9','5',1,1,'Consulta de ficha ISAAR - Público',fmt9);
END;
/

DECLARE

fmt9_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="101">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="102">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="103">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="104">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_1 WHERE ID='9';
END;
/

DECLARE

fmt9_2 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="105">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>          <Elemento Tipo="Tabla">           	<Id_CampoTbl>104</Id_CampoTbl>          	<Etiqueta>            	<Titulo Predeterminado="Fechas de Existencia"></Titulo>            	<Estilo></Estilo>          	</Etiqueta>          <Tabla_Elementos>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Inicio"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="106"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_2 WHERE ID='9';
END;
/

DECLARE

fmt9_3 clob :='	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Final"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="218">	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>         </Tabla_Elementos>         </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="107">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lugar(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="108"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_3 WHERE ID='9';
END;
/

DECLARE

fmt9_4 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="109">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="110">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="111">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_4 WHERE ID='9';
END;
/

DECLARE

fmt9_5 clob :='            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="112">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Contexto general:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="113">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de relaciones"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>100</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Entidades relacionadas"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_5 WHERE ID='9';
END;
/

DECLARE

fmt9_6 clob :='              <Etiqueta>                <Titulo Predeterminado="Nombre/Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="114">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="115">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="116">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Inicio"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_6 WHERE ID='9';
END;
/

DECLARE

fmt9_7 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="117">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="118">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_7 WHERE ID='9';
END;
/

DECLARE

fmt9_8 clob :='            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="119">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificador de la institución:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="120">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="121">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estado de elaboración:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="122"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_8 WHERE ID='9';
END;
/

DECLARE

fmt9_9 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de detalle:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="123">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="127">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fuentes:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="128">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_9 WHERE ID='9';
END;
/

DECLARE

fmt9_10 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="129">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>101</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Recursos relacionados"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Título"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="130">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_10 WHERE ID='9';
END;
/

DECLARE

fmt9_11 clob :='            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="131">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Tipo"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="132">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="133">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_11 WHERE ID='9';
END;
/

DECLARE

fmt9_12 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="134">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt9_12 WHERE ID='9';
END;
/

--Formato: 'Edición de ficha ISAAR - Público'

DECLARE

fmt10 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de identificación"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-5">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Tipo de entidad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="100">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='10';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('10','5',2,1,'Edición de ficha ISAAR - Público',fmt10);
END;
/

DECLARE

fmt10_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="101">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="102">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="103">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="104">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_1 WHERE ID='10';
END;
/

DECLARE

fmt10_2 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="105">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>          <Elemento Tipo="Tabla">           	<Id_CampoTbl>104</Id_CampoTbl>          	<Etiqueta>            	<Titulo Predeterminado="Fechas de Existencia"></Titulo>            	<Estilo></Estilo>          	</Etiqueta>          <Tabla_Elementos>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Inicio"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="106"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_2 WHERE ID='10';
END;
/

DECLARE

fmt10_3 clob :='	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Final"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="218">	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>         </Tabla_Elementos>         </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="107">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lugar(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="108"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_3 WHERE ID='10';
END;
/

DECLARE

fmt10_4 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="109">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="110">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="111">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_4 WHERE ID='10';
END;
/

DECLARE

fmt10_5 clob :='            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="112">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Contexto general:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="113">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de relaciones"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>100</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Entidades relacionadas"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_5 WHERE ID='10';
END;
/

DECLARE

fmt10_6 clob :='              <Etiqueta>                <Titulo Predeterminado="Nombre/Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="114">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="115">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="116">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Inicio"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_6 WHERE ID='10';
END;
/

DECLARE

fmt10_7 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="117">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="118">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_7 WHERE ID='10';
END;
/

DECLARE

fmt10_8 clob :='            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="119">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificador de la institución:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="120">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="121">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estado de elaboración:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="122"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_8 WHERE ID='10';
END;
/

DECLARE

fmt10_9 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de detalle:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="123">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="127">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fuentes:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="128">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_9 WHERE ID='10';
END;
/

DECLARE

fmt10_10 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="129">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>101</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Recursos relacionados"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Título"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="130">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_10 WHERE ID='10';
END;
/

DECLARE

fmt10_11 clob :='            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="131">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Tipo"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="132">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="133">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_11 WHERE ID='10';
END;
/

DECLARE

fmt10_12 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="134">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt10_12 WHERE ID='10';
END;
/

--Formato: 'Consulta de ficha de unidad documental - Archivo'

DECLARE

fmt11 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='11';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('11','1',1,2,'Consulta de ficha de unidad documental - Archivo',fmt11);
END;
/

DECLARE

fmt11_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_1 WHERE ID='11';
END;
/

DECLARE

fmt11_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_2 WHERE ID='11';
END;
/

DECLARE

fmt11_3 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_3 WHERE ID='11';
END;
/

DECLARE

fmt11_4 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_4 WHERE ID='11';
END;
/

DECLARE

fmt11_5 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta>		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_5 WHERE ID='11';
END;
/

DECLARE

fmt11_6 clob :='		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_6 WHERE ID='11';
END;
/

DECLARE

fmt11_7 clob :='						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_7 WHERE ID='11';
END;
/

DECLARE

fmt11_8 clob :='							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_8 WHERE ID='11';
END;
/

DECLARE

fmt11_9 clob :='							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_9 WHERE ID='11';
END;
/

DECLARE

fmt11_10 clob :='					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_10 WHERE ID='11';
END;
/

DECLARE

fmt11_11 clob :='							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_11 WHERE ID='11';
END;
/

DECLARE

fmt11_12 clob :='						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_12 WHERE ID='11';
END;
/

DECLARE

fmt11_13 clob :='					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_13 WHERE ID='11';
END;
/

DECLARE

fmt11_14 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_14 WHERE ID='11';
END;
/

DECLARE

fmt11_15 clob :='							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_15 WHERE ID='11';
END;
/

DECLARE

fmt11_16 clob :='					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_16 WHERE ID='11';
END;
/

DECLARE

fmt11_17 clob :='				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_17 WHERE ID='11';
END;
/

DECLARE

fmt11_18 clob :='						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt11_18 WHERE ID='11';
END;
/

--Formato: 'Edición de ficha de unidad documental - Archivo'

DECLARE

fmt12 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>S</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='12';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('12','1',2,2,'Edición de ficha de unidad documental - Archivo',fmt12);
END;
/

DECLARE

fmt12_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_1 WHERE ID='12';
END;
/

DECLARE

fmt12_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_2 WHERE ID='12';
END;
/

DECLARE

fmt12_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_3 WHERE ID='12';
END;
/

DECLARE

fmt12_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_4 WHERE ID='12';
END;
/

DECLARE

fmt12_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_5 WHERE ID='12';
END;
/

DECLARE

fmt12_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_6 WHERE ID='12';
END;
/

DECLARE

fmt12_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_7 WHERE ID='12';
END;
/

DECLARE

fmt12_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_8 WHERE ID='12';
END;
/

DECLARE

fmt12_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_9 WHERE ID='12';
END;
/

DECLARE

fmt12_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_10 WHERE ID='12';
END;
/

DECLARE

fmt12_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_11 WHERE ID='12';
END;
/

DECLARE

fmt12_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>																																							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_12 WHERE ID='12';
END;
/

DECLARE

fmt12_13 clob :='						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_13 WHERE ID='12';
END;
/

DECLARE

fmt12_14 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_14 WHERE ID='12';
END;
/

DECLARE

fmt12_15 clob :='			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_15 WHERE ID='12';
END;
/

DECLARE

fmt12_16 clob :='								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_16 WHERE ID='12';
END;
/

DECLARE

fmt12_17 clob :='		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_17 WHERE ID='12';
END;
/

DECLARE

fmt12_18 clob :='							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_18 WHERE ID='12';
END;
/

DECLARE

fmt12_19 clob :='		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt12_19 WHERE ID='12';
END;
/

--Formato: 'Consulta de ficha de serie - Archivo'

DECLARE

fmt13 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha inicial:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='13';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('13','2',1,2,'Consulta de ficha de serie - Archivo',fmt13);
END;
/

DECLARE

fmt13_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_1 WHERE ID='13';
END;
/

DECLARE

fmt13_2 clob :='          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistema Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_2 WHERE ID='13';
END;
/

DECLARE

fmt13_3 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>5</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Productores"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Productor"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="34"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_3 WHERE ID='13';
END;
/

DECLARE

fmt13_4 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Inicio"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="36">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="37">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_4 WHERE ID='13';
END;
/

DECLARE

fmt13_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="46">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_5 WHERE ID='13';
END;
/

DECLARE

fmt13_6 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_6 WHERE ID='13';
END;
/

DECLARE

fmt13_7 clob :='          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_7 WHERE ID='13';
END;
/

DECLARE

fmt13_8 clob :='    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_8 WHERE ID='13';
END;
/

DECLARE

fmt13_9 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_9 WHERE ID='13';
END;
/

DECLARE

fmt13_10 clob :='          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_10 WHERE ID='13';
END;
/

DECLARE

fmt13_11 clob :='      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=4]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_11 WHERE ID='13';
END;
/

DECLARE

fmt13_12 clob :='            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt13_12 WHERE ID='13';
END;
/

--Formato: 'Edición de ficha de serie - Archivo'

DECLARE

fmt14 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha inicial:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='14';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('14','2',2,2,'Edición de ficha de serie - Archivo',fmt14);
END;
/

DECLARE

fmt14_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_1 WHERE ID='14';
END;
/

DECLARE

fmt14_2 clob :='          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistema Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_2 WHERE ID='14';
END;
/

DECLARE

fmt14_3 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>5</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Productores"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Productor"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="34"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_3 WHERE ID='14';
END;
/

DECLARE

fmt14_4 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Inicio"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="36">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="37">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_4 WHERE ID='14';
END;
/

DECLARE

fmt14_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="46">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_5 WHERE ID='14';
END;
/

DECLARE

fmt14_6 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_6 WHERE ID='14';
END;
/

DECLARE

fmt14_7 clob :='          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_7 WHERE ID='14';
END;
/

DECLARE

fmt14_8 clob :='    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_8 WHERE ID='14';
END;
/

DECLARE

fmt14_9 clob :='                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_9 WHERE ID='14';
END;
/

DECLARE

fmt14_10 clob :='          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_10 WHERE ID='14';
END;
/

DECLARE

fmt14_11 clob :='      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=4]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_11 WHERE ID='14';
END;
/

DECLARE

fmt14_12 clob :='            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt14_12 WHERE ID='14';
END;
/

--Formato: 'Consulta de ficha de clasificador de series - Archivo'

DECLARE

fmt15 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='15';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('15','3',1,2,'Consulta de ficha de clasificador de series - Archivo',fmt15);
END;
/

DECLARE

fmt15_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_1 WHERE ID='15';
END;
/

DECLARE

fmt15_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_2 WHERE ID='15';
END;
/

DECLARE

fmt15_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_3 WHERE ID='15';
END;
/

DECLARE

fmt15_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_4 WHERE ID='15';
END;
/

DECLARE

fmt15_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_5 WHERE ID='15';
END;
/

DECLARE

fmt15_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_6 WHERE ID='15';
END;
/

DECLARE

fmt15_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_7 WHERE ID='15';
END;
/

DECLARE

fmt15_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_8 WHERE ID='15';
END;
/

DECLARE

fmt15_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_9 WHERE ID='15';
END;
/

DECLARE

fmt15_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=3]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_10 WHERE ID='15';
END;
/

DECLARE

fmt15_11 clob :='    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt15_11 WHERE ID='15';
END;
/

--Formato: 'Edición de ficha de clasificador de series - Archivo'

DECLARE

fmt16 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='16';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('16','3',2,2,'Edición de ficha de clasificador de series - Archivo',fmt16);
END;
/

DECLARE

fmt16_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_1 WHERE ID='16';
END;
/

DECLARE

fmt16_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_2 WHERE ID='16';
END;
/

DECLARE

fmt16_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_3 WHERE ID='16';
END;
/

DECLARE

fmt16_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_4 WHERE ID='16';
END;
/

DECLARE

fmt16_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_5 WHERE ID='16';
END;
/

DECLARE

fmt16_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_6 WHERE ID='16';
END;
/

DECLARE

fmt16_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_7 WHERE ID='16';
END;
/

DECLARE

fmt16_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_8 WHERE ID='16';
END;
/

DECLARE

fmt16_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_9 WHERE ID='16';
END;
/

DECLARE

fmt16_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=3]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_10 WHERE ID='16';
END;
/

DECLARE

fmt16_11 clob :='    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt16_11 WHERE ID='16';
END;
/

--Formato: 'Consulta de ficha de fondo - Archivo'

DECLARE

fmt17 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='17';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('17','4',1,2,'Consulta de ficha de fondo - Archivo',fmt17);
END;
/

DECLARE

fmt17_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_1 WHERE ID='17';
END;
/

DECLARE

fmt17_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_2 WHERE ID='17';
END;
/

DECLARE

fmt17_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_3 WHERE ID='17';
END;
/

DECLARE

fmt17_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_4 WHERE ID='17';
END;
/

DECLARE

fmt17_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_5 WHERE ID='17';
END;
/

DECLARE

fmt17_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_6 WHERE ID='17';
END;
/

DECLARE

fmt17_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_7 WHERE ID='17';
END;
/

DECLARE

fmt17_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_8 WHERE ID='17';
END;
/

DECLARE

fmt17_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_9 WHERE ID='17';
END;
/

DECLARE

fmt17_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=2]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_10 WHERE ID='17';
END;
/

DECLARE

fmt17_11 clob :='    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt17_11 WHERE ID='17';
END;
/

--Formato: 'Edición de ficha de fondo - Archivo'

DECLARE

fmt18 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Automaticos>S</Automaticos>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de mención de la identidad"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Código de Referencia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-1">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Título:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-3">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='18';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('18','4',2,2,'Edición de ficha de fondo - Archivo',fmt18);
END;
/

DECLARE

fmt18_1 clob :='            <Titulo Predeterminado="Fecha inicial:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="3">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fecha final:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="4">            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de Descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-4">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="TablaTextual">          <Id_CampoTbl>7</Id_CampoTbl>          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_1 WHERE ID='18';
END;
/

DECLARE

fmt18_2 clob :='            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Cantidad"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="214">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Soporte"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="8">                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Sistemas Productores:"></Titulo>            <Estilo></Estilo>          </Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_2 WHERE ID='18';
END;
/

DECLARE

fmt18_3 clob :='          <Campo Id="14">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Publicación SAC:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="15">            <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de contexto"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Productor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="16">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_3 WHERE ID='18';
END;
/

DECLARE

fmt18_4 clob :='            <Titulo Predeterminado="Historia institucional:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="17">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia archivística:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="18">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de alcance y contenido"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Alcance y contenido:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="38">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_4 WHERE ID='18';
END;
/

DECLARE

fmt18_5 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Valoración porcentaje:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="48">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Admite nuevos ingresos:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="47">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de condiciones de acceso y seguridad"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de acceso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="22"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_5 WHERE ID='18';
END;
/

DECLARE

fmt18_6 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Condiciones de reproducción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="23">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="24">            <ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Características físicas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="25">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_6 WHERE ID='18';
END;
/

DECLARE

fmt18_7 clob :='            <Titulo Predeterminado="Instrumentos de descripción:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="26">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de documentación asociada"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Originales"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="27">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_7 WHERE ID='18';
END;
/

DECLARE

fmt18_8 clob :='            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="28">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="Cabecera">          <Etiqueta>            <Titulo Predeterminado="Copias"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Cabecera_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Existencia:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="29">                <ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_8 WHERE ID='18';
END;
/

DECLARE

fmt18_9 clob :='                <Titulo Predeterminado="Descripción:"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="30">                <Estilo></Estilo>              </Campo>            </Elemento>          </Cabecera_Elementos>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nota de publicación:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="32">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de notas"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="35">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_9 WHERE ID='18';
END;
/

DECLARE

fmt18_10 clob :='        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control de la descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Normas utilizadas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="33">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Forma de ingreso:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Relaciones de Entrega"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=2]]></Url>          </Vinculo>        </Elemento>      </Seccion_Elementos>    </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_10 WHERE ID='18';
END;
/

DECLARE

fmt18_11 clob :='    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descriptores"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Descriptor:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="44">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt18_11 WHERE ID='18';
END;
/

--Formato: 'Consulta de ficha ISAAR - Archivo'

DECLARE

fmt19 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de identificación"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-5">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Tipo de entidad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="100">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='19';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('19','5',1,2,'Consulta de ficha ISAAR - Archivo',fmt19);
END;
/

DECLARE

fmt19_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="101">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="102">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="103">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="104">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_1 WHERE ID='19';
END;
/

DECLARE

fmt19_2 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="105">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>          <Elemento Tipo="Tabla">           	<Id_CampoTbl>104</Id_CampoTbl>          	<Etiqueta>            	<Titulo Predeterminado="Fechas de Existencia"></Titulo>            	<Estilo></Estilo>          	</Etiqueta>          <Tabla_Elementos>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Inicio"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="106"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_2 WHERE ID='19';
END;
/

DECLARE

fmt19_3 clob :='	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Final"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="218">	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>         </Tabla_Elementos>         </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="107">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lugar(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="108"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_3 WHERE ID='19';
END;
/

DECLARE

fmt19_4 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="109">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="110">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="111">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_4 WHERE ID='19';
END;
/

DECLARE

fmt19_5 clob :='            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="112">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Contexto general:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="113">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de relaciones"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>100</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Entidades relacionadas"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_5 WHERE ID='19';
END;
/

DECLARE

fmt19_6 clob :='              <Etiqueta>                <Titulo Predeterminado="Nombre/Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="114">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="115">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="116">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Inicio"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_6 WHERE ID='19';
END;
/

DECLARE

fmt19_7 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="117">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="118">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_7 WHERE ID='19';
END;
/

DECLARE

fmt19_8 clob :='            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="119">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificador de la institución:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="120">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="121">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estado de elaboración:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="122"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_8 WHERE ID='19';
END;
/

DECLARE

fmt19_9 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de detalle:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="123">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Fechas de control:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Auditoría"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40002]]></Url>          </Vinculo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="127">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_9 WHERE ID='19';
END;
/

DECLARE

fmt19_10 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fuentes:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="128">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="129">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>101</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Recursos relacionados"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_10 WHERE ID='19';
END;
/

DECLARE

fmt19_11 clob :='            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Título"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="130">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="131">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Tipo"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="132">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_11 WHERE ID='19';
END;
/

DECLARE

fmt19_12 clob :='              <Etiqueta>                <Titulo Predeterminado="Naturaleza"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="133">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="134">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt19_12 WHERE ID='19';
END;
/

--Formato: 'Edición de ficha ISAAR - Archivo'

DECLARE

fmt20 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">  <Editable>N</Editable>  <Elementos>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de identificación"></Titulo>        <Estilo></Estilo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="-5">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Tipo de entidad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="100">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas(s) autorizada(s) del nombre:"></Titulo> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='20';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('20','5',2,2,'Edición de ficha ISAAR - Archivo',fmt20);
END;
/

DECLARE

fmt20_1 clob :='            <Estilo></Estilo>          </Etiqueta>          <Campo Id="101">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas paralelas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="102">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Formas normalizadas del nombre según otras reglas:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="103">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Otras formas de nombre:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="104">            <Estilo></Estilo>          </Campo>        </Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_1 WHERE ID='20';
END;
/

DECLARE

fmt20_2 clob :='        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificadores para instituciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="105">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de descripción"></Titulo>      </Etiqueta>      <Seccion_Elementos>          <Elemento Tipo="Tabla">           	<Id_CampoTbl>104</Id_CampoTbl>          	<Etiqueta>            	<Titulo Predeterminado="Fechas de Existencia"></Titulo>            	<Estilo></Estilo>          	</Etiqueta>          <Tabla_Elementos>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Inicio"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="106"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_2 WHERE ID='20';
END;
/

DECLARE

fmt20_3 clob :='	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>	        <Elemento Tipo="EtiquetaDato">	          <Etiqueta>	            <Titulo Predeterminado="Fecha Final"></Titulo>	            <Estilo></Estilo>	          </Etiqueta>	          <Campo Id="218">	            <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>	            <Estilo></Estilo>	          </Campo>	        </Elemento>         </Tabla_Elementos>         </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Historia:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="107">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lugar(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="108"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_3 WHERE ID='20';
END;
/

DECLARE

fmt20_4 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estatuto jurídico:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="109">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Funciones, ocupaciones y actividades:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="110">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Atribución(es) / Fuente(s) legal(es):"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="111">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_4 WHERE ID='20';
END;
/

DECLARE

fmt20_5 clob :='            <Titulo Predeterminado="Estructura(s) interna(s) / Genealogía:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="112">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Contexto general:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="113">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de relaciones"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>100</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Entidades relacionadas"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_5 WHERE ID='20';
END;
/

DECLARE

fmt20_6 clob :='              <Etiqueta>                <Titulo Predeterminado="Nombre/Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="114">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Naturaleza de la relación"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="115">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Descripción"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="116">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Inicio"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_6 WHERE ID='20';
END;
/

DECLARE

fmt20_7 clob :='                <Estilo></Estilo>              </Etiqueta>              <Campo Id="117">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="F. Fin"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="118">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Area de control"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="EtiquetaDato">          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_7 WHERE ID='20';
END;
/

DECLARE

fmt20_8 clob :='            <Titulo Predeterminado="Identificador del registro de la autoridad:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="119">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Identificador de la institución:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="120">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Reglas y/o convenciones:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="121">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Estado de elaboración:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="122"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_8 WHERE ID='20';
END;
/

DECLARE

fmt20_9 clob :='            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Nivel de detalle:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="123">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="Hipervinculo">          <Etiqueta>            <Titulo Predeterminado="Fechas de control:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Vinculo>            <Titulo Predeterminado="Auditoría"></Titulo>            <Estilo></Estilo>            <Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40002]]></Url>          </Vinculo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Lenguas y escrituras:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="127">            <Estilo></Estilo>          </Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_9 WHERE ID='20';
END;
/

DECLARE

fmt20_10 clob :='        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Fuentes:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="128">            <Estilo></Estilo>          </Campo>        </Elemento>        <Elemento Tipo="EtiquetaDato">          <Etiqueta>            <Titulo Predeterminado="Notas de mantenimiento:"></Titulo>            <Estilo></Estilo>          </Etiqueta>          <Campo Id="129">            <Estilo></Estilo>          </Campo>        </Elemento>      </Seccion_Elementos>    </Elemento>    <Elemento Tipo="Seccion">      <Desplegada>S</Desplegada>      <Etiqueta>        <Titulo Predeterminado="Relación de instituciones, personas y familias con documentos de archivo y otros recursos"></Titulo>      </Etiqueta>      <Seccion_Elementos>        <Elemento Tipo="Tabla">          <Id_CampoTbl>101</Id_CampoTbl>          <Etiqueta>            <Titulo Predeterminado="Recursos relacionados"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_10 WHERE ID='20';
END;
/

DECLARE

fmt20_11 clob :='            <Estilo></Estilo>          </Etiqueta>          <Tabla_Elementos>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Título"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="130">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Identificador"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="131">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Tipo"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="132">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_11 WHERE ID='20';
END;
/

DECLARE

fmt20_12 clob :='              <Etiqueta>                <Titulo Predeterminado="Naturaleza"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="133">                <Estilo></Estilo>              </Campo>            </Elemento>            <Elemento Tipo="EtiquetaDato">              <Etiqueta>                <Titulo Predeterminado="Fecha"></Titulo>                <Estilo></Estilo>              </Etiqueta>              <Campo Id="134">                <ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>                <Estilo></Estilo>              </Campo>            </Elemento>          </Tabla_Elementos>        </Elemento>      </Seccion_Elementos>    </Elemento>  </Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt20_12 WHERE ID='20';
END;
/

--Formato: 'Consulta de ficha de fracción de serie - Público'

DECLARE

fmt21 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="Tabla"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='21';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('21','6',1,1,'Consulta de ficha de fracción de serie - Público',fmt21);
END;
/

DECLARE

fmt21_1 clob :='							<Id_CampoTbl>102</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Rangos de expedientes:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Desde">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="201">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Hasta">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="202">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_1 WHERE ID='21';
END;
/

DECLARE

fmt21_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_2 WHERE ID='21';
END;
/

DECLARE

fmt21_3 clob :='									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_3 WHERE ID='21';
END;
/

DECLARE

fmt21_4 clob :='									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_4 WHERE ID='21';
END;
/

DECLARE

fmt21_5 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_5 WHERE ID='21';
END;
/

DECLARE

fmt21_6 clob :='				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_6 WHERE ID='21';
END;
/

DECLARE

fmt21_7 clob :='								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_7 WHERE ID='21';
END;
/

DECLARE

fmt21_8 clob :='							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_8 WHERE ID='21';
END;
/

DECLARE

fmt21_9 clob :='							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_9 WHERE ID='21';
END;
/

DECLARE

fmt21_10 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_10 WHERE ID='21';
END;
/

DECLARE

fmt21_11 clob :='											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_11 WHERE ID='21';
END;
/

DECLARE

fmt21_12 clob :='							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_12 WHERE ID='21';
END;
/

DECLARE

fmt21_13 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_13 WHERE ID='21';
END;
/

DECLARE

fmt21_14 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_14 WHERE ID='21';
END;
/

DECLARE

fmt21_15 clob :='								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_15 WHERE ID='21';
END;
/

DECLARE

fmt21_16 clob :='							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_16 WHERE ID='21';
END;
/

DECLARE

fmt21_17 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_17 WHERE ID='21';
END;
/

DECLARE

fmt21_18 clob :='	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt21_18 WHERE ID='21';
END;
/

--Formato: 'Edición de ficha de fracción de serie - Público'

DECLARE

fmt22 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="Tabla"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='22';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('22','6',2,1,'Edición de ficha de fracción de serie - Público',fmt22);
END;
/

DECLARE

fmt22_1 clob :='							<Id_CampoTbl>102</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Rangos de expedientes:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Desde">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="201">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Hasta">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="202">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_1 WHERE ID='22';
END;
/

DECLARE

fmt22_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_2 WHERE ID='22';
END;
/

DECLARE

fmt22_3 clob :='									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_3 WHERE ID='22';
END;
/

DECLARE

fmt22_4 clob :='											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_4 WHERE ID='22';
END;
/

DECLARE

fmt22_5 clob :='								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_5 WHERE ID='22';
END;
/

DECLARE

fmt22_6 clob :='							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_6 WHERE ID='22';
END;
/

DECLARE

fmt22_7 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_7 WHERE ID='22';
END;
/

DECLARE

fmt22_8 clob :='					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_8 WHERE ID='22';
END;
/

DECLARE

fmt22_9 clob :='								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_9 WHERE ID='22';
END;
/

DECLARE

fmt22_10 clob :='			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_10 WHERE ID='22';
END;
/

DECLARE

fmt22_11 clob :='									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_11 WHERE ID='22';
END;
/

DECLARE

fmt22_12 clob :='											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_12 WHERE ID='22';
END;
/

DECLARE

fmt22_13 clob :='					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_13 WHERE ID='22';
END;
/

DECLARE

fmt22_14 clob :='						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_14 WHERE ID='22';
END;
/

DECLARE

fmt22_15 clob :='						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_15 WHERE ID='22';
END;
/

DECLARE

fmt22_16 clob :='							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_16 WHERE ID='22';
END;
/

DECLARE

fmt22_17 clob :='				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_17 WHERE ID='22';
END;
/

DECLARE

fmt22_18 clob :='						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt22_18 WHERE ID='22';
END;
/

--Formato: 'Consulta de ficha de fracción de serie - Archivo'

DECLARE

fmt23 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="Tabla"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='23';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('23','6',1,2,'Consulta de ficha de fracción de serie - Archivo',fmt23);
END;
/

DECLARE

fmt23_1 clob :='							<Id_CampoTbl>102</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Rangos de expedientes:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Desde">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="201">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Hasta">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="202">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_1 WHERE ID='23';
END;
/

DECLARE

fmt23_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_2 WHERE ID='23';
END;
/

DECLARE

fmt23_3 clob :='									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_3 WHERE ID='23';
END;
/

DECLARE

fmt23_4 clob :='									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_4 WHERE ID='23';
END;
/

DECLARE

fmt23_5 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_5 WHERE ID='23';
END;
/

DECLARE

fmt23_6 clob :='				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_6 WHERE ID='23';
END;
/

DECLARE

fmt23_7 clob :='								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_7 WHERE ID='23';
END;
/

DECLARE

fmt23_8 clob :='							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_8 WHERE ID='23';
END;
/

DECLARE

fmt23_9 clob :='							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_9 WHERE ID='23';
END;
/

DECLARE

fmt23_10 clob :='				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_10 WHERE ID='23';
END;
/

DECLARE

fmt23_11 clob :='											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_11 WHERE ID='23';
END;
/

DECLARE

fmt23_12 clob :='							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_12 WHERE ID='23';
END;
/

DECLARE

fmt23_13 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_13 WHERE ID='23';
END;
/

DECLARE

fmt23_14 clob :='						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_14 WHERE ID='23';
END;
/

DECLARE

fmt23_15 clob :='								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_15 WHERE ID='23';
END;
/

DECLARE

fmt23_16 clob :='							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_16 WHERE ID='23';
END;
/

DECLARE

fmt23_17 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_17 WHERE ID='23';
END;
/

DECLARE

fmt23_18 clob :='						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt23_18 WHERE ID='23';
END;
/

--Formato: 'Edición de ficha de fracción de serie - Archivo'

DECLARE

fmt24 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="Tabla"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='24';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('24','6',2,2,'Edición de ficha de fracción de serie - Archivo',fmt24);
END;
/

DECLARE

fmt24_1 clob :='							<Id_CampoTbl>102</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Rangos de expedientes:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Desde">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="201">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Hasta">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="202">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_1 WHERE ID='24';
END;
/

DECLARE

fmt24_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_2 WHERE ID='24';
END;
/

DECLARE

fmt24_3 clob :='									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_3 WHERE ID='24';
END;
/

DECLARE

fmt24_4 clob :='											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_4 WHERE ID='24';
END;
/

DECLARE

fmt24_5 clob :='								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_5 WHERE ID='24';
END;
/

DECLARE

fmt24_6 clob :='							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_6 WHERE ID='24';
END;
/

DECLARE

fmt24_7 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_7 WHERE ID='24';
END;
/

DECLARE

fmt24_8 clob :='					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_8 WHERE ID='24';
END;
/

DECLARE

fmt24_9 clob :='								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_9 WHERE ID='24';
END;
/

DECLARE

fmt24_10 clob :='			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_10 WHERE ID='24';
END;
/

DECLARE

fmt24_11 clob :='									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_11 WHERE ID='24';
END;
/

DECLARE

fmt24_12 clob :='											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_12 WHERE ID='24';
END;
/

DECLARE

fmt24_13 clob :='					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_13 WHERE ID='24';
END;
/

DECLARE

fmt24_14 clob :='						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_14 WHERE ID='24';
END;
/

DECLARE

fmt24_15 clob :='						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_15 WHERE ID='24';
END;
/

DECLARE

fmt24_16 clob :='							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_16 WHERE ID='24';
END;
/

DECLARE

fmt24_17 clob :='				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_17 WHERE ID='24';
END;
/

DECLARE

fmt24_18 clob :='						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt24_18 WHERE ID='24';
END;
/

--Formato: 'Consulta de ficha de unidad documental ENI - Público'

DECLARE

fmt25 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='29';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('29','10',1,1,'Consulta de ficha de unidad documental ENI - Público',fmt25);
END;
/

DECLARE

fmt25_1 clob :='							<Etiqueta>								<Titulo Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_1 WHERE ID='29';
END;
/

DECLARE

fmt25_2 clob :='										<Titulo Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_2 WHERE ID='29';
END;
/

DECLARE

fmt25_3 clob :='						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_3 WHERE ID='29';
END;
/

DECLARE

fmt25_4 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_4 WHERE ID='29';
END;
/

DECLARE

fmt25_5 clob :='					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_5 WHERE ID='29';
END;
/

DECLARE

fmt25_6 clob :='								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_6 WHERE ID='29';
END;
/

DECLARE

fmt25_7 clob :='			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo> <!--<Iconos> <Icono valor="1" src="/imagenes/1.gif"> <Iconos> -->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_7 WHERE ID='29';
END;
/

DECLARE

fmt25_8 clob :='					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_8 WHERE ID='29';
END;
/

DECLARE

fmt25_9 clob :='						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_9 WHERE ID='29';
END;
/

DECLARE

fmt25_10 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_10 WHERE ID='29';
END;
/

DECLARE

fmt25_11 clob :='										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_11 WHERE ID='29';
END;
/

DECLARE

fmt25_12 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="Hipervinculo">									<Etiqueta>										<Titulo Predeterminado="Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Vinculo>										<Titulo Predeterminado="Enlace"></Titulo>										<Estilo></Estilo>										<Url><![CDATA[../../action/documento?method=retrieve&tipoObjeto=1&descripcion=1]]></Url>										<Target>_self</Target>										<Parametro>id</Parametro>										<Id_Campo_Value>1022</Id_Campo_Value>									</Vinculo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_12 WHERE ID='29';
END;
/

DECLARE

fmt25_13 clob :='						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_13 WHERE ID='29';
END;
/

DECLARE

fmt25_14 clob :='			<Etiqueta>				<Titulo Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_14 WHERE ID='29';
END;
/

DECLARE

fmt25_15 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_15 WHERE ID='29';
END;
/

DECLARE

fmt25_16 clob :='						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_16 WHERE ID='29';
END;
/

DECLARE

fmt25_17 clob :='				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_17 WHERE ID='29';
END;
/

DECLARE

fmt25_18 clob :='				<Titulo Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt25_18 WHERE ID='29';
END;
/

--Formato: 'Edición de ficha de unidad documental ENI - Público'

DECLARE

fmt26 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='30';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('30','10',2,1,'Edición de ficha de unidad documental ENI - Público',fmt26);
END;
/

DECLARE

fmt26_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_1 WHERE ID='30';
END;
/

DECLARE

fmt26_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_2 WHERE ID='30';
END;
/

DECLARE

fmt26_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_3 WHERE ID='30';
END;
/

DECLARE

fmt26_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_4 WHERE ID='30';
END;
/

DECLARE

fmt26_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_5 WHERE ID='30';
END;
/

DECLARE

fmt26_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_6 WHERE ID='30';
END;
/

DECLARE

fmt26_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_7 WHERE ID='30';
END;
/

DECLARE

fmt26_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_8 WHERE ID='30';
END;
/

DECLARE

fmt26_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_9 WHERE ID='30';
END;
/

DECLARE

fmt26_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_10 WHERE ID='30';
END;
/

DECLARE

fmt26_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_11 WHERE ID='30';
END;
/

DECLARE

fmt26_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_12 WHERE ID='30';
END;
/

DECLARE

fmt26_13 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_13 WHERE ID='30';
END;
/

DECLARE

fmt26_14 clob :='					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_14 WHERE ID='30';
END;
/

DECLARE

fmt26_15 clob :='					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_15 WHERE ID='30';
END;
/

DECLARE

fmt26_16 clob :='					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_16 WHERE ID='30';
END;
/

DECLARE

fmt26_17 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_17 WHERE ID='30';
END;
/

DECLARE

fmt26_18 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_18 WHERE ID='30';
END;
/

DECLARE

fmt26_19 clob :='				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt26_19 WHERE ID='30';
END;
/

--Formato: 'Consulta de ficha de unidad documental ENI - Archivo'

DECLARE

fmt27 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>N</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='31';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('31','10',1,2,'Consulta de ficha de unidad documental ENI - Archivo',fmt27);
END;
/

DECLARE

fmt27_1 clob :='							<Etiqueta>								<Titulo Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_1 WHERE ID='31';
END;
/

DECLARE

fmt27_2 clob :='										<Titulo Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="212">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_2 WHERE ID='31';
END;
/

DECLARE

fmt27_3 clob :='						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo></Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_3 WHERE ID='31';
END;
/

DECLARE

fmt27_4 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_4 WHERE ID='31';
END;
/

DECLARE

fmt27_5 clob :='					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="TablaTextual">					<Id_CampoTbl>1</Id_CampoTbl>					<Etiqueta>						<Titulo Predeterminado="Volumen y Soporte:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Mostrar_Cabeceras>N</Mostrar_Cabeceras>					<Tabla_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Cantidad"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="7">								<Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>								<Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_5 WHERE ID='31';
END;
/

DECLARE

fmt27_6 clob :='								<Titulo Predeterminado="Formato"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="5">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Soporte"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="6">								<Estilo></Estilo>							</Campo>						</Elemento>					</Tabla_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_6 WHERE ID='31';
END;
/

DECLARE

fmt27_7 clob :='			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo> <!--<Iconos> <Icono valor="1" src="/imagenes/1.gif"> <Iconos> -->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_7 WHERE ID='31';
END;
/

DECLARE

fmt27_8 clob :='					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_8 WHERE ID='31';
END;
/

DECLARE

fmt27_9 clob :='						</Elemento>					</Cabecera_Elementos>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_9 WHERE ID='31';
END;
/

DECLARE

fmt27_10 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_10 WHERE ID='31';
END;
/

DECLARE

fmt27_11 clob :='										<Titulo Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_11 WHERE ID='31';
END;
/

DECLARE

fmt27_12 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="Hipervinculo">									<Etiqueta>										<Titulo Predeterminado="Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Vinculo>										<Titulo Predeterminado="Enlace"></Titulo>										<Estilo></Estilo>										<Url><![CDATA[../../action/documento?method=retrieve&tipoObjeto=1&descripcion=1]]></Url>										<Target>_self</Target>										<Parametro>id</Parametro>										<Id_Campo_Value>1022</Id_Campo_Value>									</Vinculo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_12 WHERE ID='31';
END;
/

DECLARE

fmt27_13 clob :='						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_13 WHERE ID='31';
END;
/

DECLARE

fmt27_14 clob :='			<Etiqueta>				<Titulo Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Características físicas:">						</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_14 WHERE ID='31';
END;
/

DECLARE

fmt27_15 clob :='						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_15 WHERE ID='31';
END;
/

DECLARE

fmt27_16 clob :='						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_16 WHERE ID='31';
END;
/

DECLARE

fmt27_17 clob :='				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_17 WHERE ID='31';
END;
/

DECLARE

fmt27_18 clob :='				<Titulo Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_18 WHERE ID='31';
END;
/

DECLARE

fmt27_19 clob :='						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt27_19 WHERE ID='31';
END;
/

--Formato: 'Edición de ficha de unidad documental ENI - Archivo'

DECLARE

fmt28 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_FmtFicha Version="5.0">	<Editable>S</Editable>	<Elementos>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de mención de la identidad">				</Titulo>				<Estilo></Estilo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Código de Referencia:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-1">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Título"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Asunto:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-3">								<Estilo></Estilo>								<Multilinea>S</Multilinea>							</Campo>						</Elemento> ';

BEGIN
DELETE FROM ADFMTFICHA WHERE ID='32';
INSERT INTO ADFMTFICHA (ID,IDFICHA,TIPO,NIVELACCESO,NOMBRE,DEFINICION)VALUES('32','10',2,2,'Edición de ficha de unidad documental ENI - Archivo',fmt28);
END;
/

DECLARE

fmt28_1 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Número de expediente:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="-2">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>3</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Emplazamiento:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="País"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="2">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Provincia">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="39">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_1 WHERE ID='32';
END;
/

DECLARE

fmt28_2 clob :='								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Municipio">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="40">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Población">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="41">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Localización">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="42">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_2 WHERE ID='32';
END;
/

DECLARE

fmt28_3 clob :='									</Etiqueta>									<Campo Id="212">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>2</Id_CampoTbl>							<Etiqueta>								<Titulo Predeterminado="Interesados:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Principal">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="213">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="9">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_3 WHERE ID='32';
END;
/

DECLARE

fmt28_4 clob :='											Predeterminado="Num. Identidad">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="10">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Rol"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="11">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Validado">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="12">										<Estilo>width:100%</Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="IdTercero">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="51">										<Estilo>width:100%</Estilo>									</Campo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_4 WHERE ID='32';
END;
/

DECLARE

fmt28_5 clob :='								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha inicial:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="3">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Fecha final:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="4">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Nivel de Descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="-4">						<Estilo></Estilo>					</Campo>				</Elemento>		        <Elemento Tipo="TablaTextual">		          <Id_CampoTbl>1</Id_CampoTbl>		          <Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_5 WHERE ID='32';
END;
/

DECLARE

fmt28_6 clob :='		            <Titulo Predeterminado="Volumen y Soporte:"></Titulo>		            <Estilo></Estilo>		          </Etiqueta>		          <Mostrar_Cabeceras>N</Mostrar_Cabeceras>		          <Tabla_Elementos>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Cantidad"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="7">		                <Mostrar_Tipo_Numero>N</Mostrar_Tipo_Numero>		                <Mostrar_Unidad_Numero>N</Mostrar_Unidad_Numero>		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato">		              <Etiqueta>		                <Titulo Predeterminado="Formato"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="5">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		            <Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_6 WHERE ID='32';
END;
/

DECLARE

fmt28_7 clob :='		              <Etiqueta>		                <Titulo Predeterminado="Soporte"></Titulo>		                <Estilo></Estilo>		              </Etiqueta>		              <Campo Id="6">		                <Estilo></Estilo>		              </Campo>		            </Elemento>		          </Tabla_Elementos>		        </Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Sistema Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="14">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Publicación SAC:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="15">						<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de contexto"></Titulo>			</Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_7 WHERE ID='32';
END;
/

DECLARE

fmt28_8 clob :='			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Productor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="16">						<Estilo></Estilo>						<!--<Iconos>							<Icono valor="1" src="/imagenes/1.gif">							<Iconos>-->					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia institucional:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="17">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Historia archivística:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="18">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_8 WHERE ID='32';
END;
/

DECLARE

fmt28_9 clob :='							<Etiqueta>								<Titulo Predeterminado="Ingreso por:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="203">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Código de transferencia:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="204">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Referencias de ingreso:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="205">								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo									Predeterminado="Hoja de Entrega:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="206">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_9 WHERE ID='32';
END;
/

DECLARE

fmt28_10 clob :='				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de alcance y contenido"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Descripción Contenido:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="13">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Alcance y contenido"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="Tabla">							<Id_CampoTbl>4</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Físicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_10 WHERE ID='32';
END;
/

DECLARE

fmt28_11 clob :='										<Estilo></Estilo>									</Etiqueta>									<Campo Id="19">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="43">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>						<Elemento Tipo="Tabla">							<Id_CampoTbl>6</Id_CampoTbl>							<Etiqueta>								<Titulo									Predeterminado="Documentos Electrónicos:">								</Titulo>								<Estilo></Estilo>							</Etiqueta>							<Tabla_Elementos>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo											Predeterminado="Nombre">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="49">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_11 WHERE ID='32';
END;
/

DECLARE

fmt28_12 clob :='										<Titulo											Predeterminado="Descripción">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="50">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Fecha Captura"></Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1013">										<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Origen">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1014">										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Tipo Documental">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1017"> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_12 WHERE ID='32';
END;
/

DECLARE

fmt28_13 clob :='										<Estilo></Estilo>									</Campo>								</Elemento>								<Elemento Tipo="EtiquetaDato">									<Etiqueta>										<Titulo Predeterminado="Valor CSV Documento">										</Titulo>										<Estilo></Estilo>									</Etiqueta>									<Campo Id="1019">										<Estilo></Estilo>									</Campo>								</Elemento>							</Tabla_Elementos>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Archivos Digitales:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Archivos"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/documentos?actionToPerform=homeUDoc&tipo=1&contentForwardName=verDescripcionUnidadDocumental]]></Url>						<Target>_self</Target>					</Vinculo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Valoración, selección, eliminación:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_13 WHERE ID='32';
END;
/

DECLARE

fmt28_14 clob :='					</Etiqueta>					<Campo Id="20">						<ValorSeleccionPorDefecto>DDMMAAAA</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nuevos ingresos:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="21">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de condiciones de acceso y seguridad">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de acceso:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="22">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Condiciones de reproducción:">						</Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_14 WHERE ID='32';
END;
/

DECLARE

fmt28_15 clob :='					</Etiqueta>					<Campo Id="23">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Lenguas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="24">						<ValorSeleccionPorDefecto>Castellano</ValorSeleccionPorDefecto>						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Características físicas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="25">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Instrumentos de descripción:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="26">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_15 WHERE ID='32';
END;
/

DECLARE

fmt28_16 clob :='					Predeterminado="Area de documentación asociada">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Originales"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="27">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="28">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="Cabecera">					<Etiqueta>						<Titulo Predeterminado="Copias"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Cabecera_Elementos> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_16 WHERE ID='32';
END;
/

DECLARE

fmt28_17 clob :='						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Existencia:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="29">								<ValorSeleccionPorDefecto>No</ValorSeleccionPorDefecto>								<Estilo></Estilo>							</Campo>						</Elemento>						<Elemento Tipo="EtiquetaDato">							<Etiqueta>								<Titulo Predeterminado="Descripción:"></Titulo>								<Estilo></Estilo>							</Etiqueta>							<Campo Id="30">								<Estilo></Estilo>							</Campo>						</Elemento>					</Cabecera_Elementos>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo							Predeterminado="Unidades relacionadas:">						</Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="31">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Nota de publicación:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="32">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_17 WHERE ID='32';
END;
/

DECLARE

fmt28_18 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de notas"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Notas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="35">						<Estilo></Estilo>					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo					Predeterminado="Area de control de la descripción">				</Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Normas utilizadas:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="33">						<Estilo></Estilo>					</Campo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Forma de ingreso:"></Titulo>						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_18 WHERE ID='32';
END;
/

DECLARE

fmt28_19 clob :='					</Etiqueta>					<Vinculo>						<Titulo							Predeterminado="Relaciones de Entrega">						</Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/gestionRelaciones?method=verRelaciones&tipo=6]]></Url>					</Vinculo>				</Elemento>				<Elemento Tipo="Hipervinculo">					<Etiqueta>						<Titulo Predeterminado="Control de cambios:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Vinculo>						<Titulo Predeterminado="Auditoría"></Titulo>						<Estilo></Estilo>						<Url><![CDATA[../../action/auditoriaBuscarExtendida?method=buscar&kind=1&tipo=40001]]></Url>					</Vinculo>				</Elemento>			</Seccion_Elementos>		</Elemento>		<Elemento Tipo="Seccion">			<Desplegada>S</Desplegada>			<Etiqueta>				<Titulo Predeterminado="Area de descriptores"></Titulo>			</Etiqueta>			<Seccion_Elementos>				<Elemento Tipo="EtiquetaDato">					<Etiqueta>						<Titulo Predeterminado="Descriptor:"></Titulo>						<Estilo></Estilo>					</Etiqueta>					<Campo Id="44">						<Estilo></Estilo> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_19 WHERE ID='32';
END;
/

DECLARE

fmt28_20 clob :='					</Campo>				</Elemento>			</Seccion_Elementos>		</Elemento>	</Elementos></Definicion_FmtFicha> ';

BEGIN
UPDATE ADFMTFICHA SET DEFINICION=DEFINICION || fmt28_20 WHERE ID='32';
END;
/

--CLASIFICADORES

--Clasificador: 'Ficha de Series'

DECLARE

cls1 clob :='<?xml version="1.0" encoding="ISO-8859-1"?><Definicion_Ficha Version="5.0">  <Clasificadores_Predeterminados>    <Clasificador>      <Nombre>Estudios de Valoración</Nombre>      <Tipo>1</Tipo>      <Descripcion>Clasificador para los documentos de las distintas valoraciones de la serie.</Descripcion>    </Clasificador>    <Clasificador>      <Nombre>Actas de Destrucción</Nombre>      <Tipo>1</Tipo>      <Descripcion>Clasificador para las actas de la destrucción de unidades documentales.</Descripcion>    </Clasificador>  </Clasificadores_Predeterminados></Definicion_Ficha> ';

BEGIN
DELETE FROM ADOCFICHACLF WHERE ID='ID_FICHA_DOCS_SERIES';
INSERT INTO ADOCFICHACLF (ID,NOMBRE,DEFINICION)VALUES('ID_FICHA_DOCS_SERIES','Ficha de Series',cls1);
END;
/

--MAPEOS

--Mapeo: '1'

DECLARE

map1 clob :='<?xml version="1.0" encoding="ISO-8859-1" ?><MAP_UDOC_REL_A_DESCR Version="5.0">   <DATOS_SIMPLES>      <DATO TIPO="1" ID="14" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>      </DATO>   	<DATO TIPO="3" ID="3" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>      </DATO>      <DATO TIPO="3" ID="4" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>      </DATO>	   <DATO TIPO="5" ID="16" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>	   </DATO>      <DATO TIPO="1" ID="15" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>         <TRANSFORMA_VALOR>	         <VALOR ORG="S">Si</VALOR>	         <VALOR ORG="N">No</VALOR>         </TRANSFORMA_VALOR>      </DATO>      <DATO TIPO="1" ID="1" TIPOPARAM="3">         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM> ';

BEGIN
DELETE FROM ASGTMAPDESCRUDOC WHERE IDFICHA='1';
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO)VALUES('1',map1);
END;
/

DECLARE

map1_1 clob :='      </DATO>   </DATOS_SIMPLES>   <DATOS_TABLA>   	<TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">            <DATO TIPO="5" ID="9" TIPOPARAM="1">      		   <PARAM>IDENTIDAD</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>      			   <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>               </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="1" ID="10" TIPOPARAM="1">      		   <PARAM>NUM_IDENTIDAD</PARAM>   	      </DATO>            <DATO TIPO="1" ID="11" TIPOPARAM="1">      		   <PARAM>ROL</PARAM>   	      </DATO>    		   <DATO TIPO="1" ID="12" TIPOPARAM="1">      		   <PARAM>VALIDADO_EN_TERCEROS</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>           <DATO TIPO="1" ID="51" TIPOPARAM="1"> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_1 WHERE IDFICHA='1';
END;
/

DECLARE

map1_2 clob :='      		   <PARAM>ID_EN_TERCEROS</PARAM>   	      </DATO>    	  <DATO TIPO="1" ID="213" TIPOPARAM="1">      		   <PARAM>PRINCIPAL</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">            <DATO TIPO="5" ID="2" TIPOPARAM="1">      		   <PARAM>PAIS</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>            <DATO TIPO="5" ID="39" TIPOPARAM="1">      		   <PARAM>PROVINCIA</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="5" ID="40" TIPOPARAM="1">      		   <PARAM>CONCEJO</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>               </DESCRIPTOR>   	      </DATO> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_2 WHERE IDFICHA='1';
END;
/

DECLARE

map1_3 clob :='            <DATO TIPO="5" ID="41" TIPOPARAM="1">      		   <PARAM>POBLACION</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>               </DESCRIPTOR>   	      </DATO>            <DATO TIPO="1" ID="42" TIPOPARAM="1">      		   <PARAM>LOCALIZACION</PARAM>   	      </DATO>            <DATO TIPO="1" ID="212" TIPOPARAM="1">      		   <PARAM>VALIDADO</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      	</DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">            <DATO TIPO="1" ID="5" TIPOPARAM="1">               <PARAM>FORMATO</PARAM>            </DATO>            <DATO TIPO="1" ID="6" TIPOPARAM="1">               <PARAM>TIPO</PARAM>            </DATO>            <DATO TIPO="4" ID="7" TIPOPARAM="1">               <PARAM>NUM_DOCS</PARAM> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_3 WHERE IDFICHA='1';
END;
/

DECLARE

map1_4 clob :='            </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">            <DATO TIPO="1" ID="19" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="43" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">            <DATO TIPO="1" ID="49" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="50" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>   </DATOS_TABLA></MAP_UDOC_REL_A_DESCR> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map1_4 WHERE IDFICHA='1';
END;
/

--Mapeo: '6'

DECLARE

map2 clob :='<?xml version="1.0" encoding="ISO-8859-1" ?><MAP_UDOC_REL_A_DESCR Version="5.0">   <DATOS_SIMPLES>      <DATO TIPO="1" ID="14" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>      </DATO>   	<DATO TIPO="3" ID="3" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>      </DATO>      <DATO TIPO="3" ID="4" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>      </DATO>	   <DATO TIPO="5" ID="16" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>	   </DATO>      <DATO TIPO="1" ID="15" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>         <TRANSFORMA_VALOR>	         <VALOR ORG="S">Si</VALOR>	         <VALOR ORG="N">No</VALOR>         </TRANSFORMA_VALOR>      </DATO>      <DATO TIPO="1" ID="1" TIPOPARAM="3">         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM> ';

BEGIN
DELETE FROM ASGTMAPDESCRUDOC WHERE IDFICHA='6';
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO)VALUES('6',map2);
END;
/

DECLARE

map2_1 clob :='      </DATO>   </DATOS_SIMPLES>   <DATOS_TABLA>   	<TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">            <DATO TIPO="5" ID="9" TIPOPARAM="1">      		   <PARAM>IDENTIDAD</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>      			   <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>               </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="1" ID="10" TIPOPARAM="1">      		   <PARAM>NUM_IDENTIDAD</PARAM>   	      </DATO>            <DATO TIPO="1" ID="11" TIPOPARAM="1">      		   <PARAM>ROL</PARAM>   	      </DATO>    		   <DATO TIPO="1" ID="12" TIPOPARAM="1">      		   <PARAM>VALIDADO_EN_TERCEROS</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>            <DATO TIPO="1" ID="51" TIPOPARAM="1"> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map2_1 WHERE IDFICHA='6';
END;
/

DECLARE

map2_2 clob :='      		   <PARAM>ID_EN_TERCEROS</PARAM>   	      </DATO>    	  <DATO TIPO="1" ID="213" TIPOPARAM="1">      		   <PARAM>PRINCIPAL</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">            <DATO TIPO="5" ID="2" TIPOPARAM="1">      		   <PARAM>PAIS</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>            <DATO TIPO="5" ID="39" TIPOPARAM="1">      		   <PARAM>PROVINCIA</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="5" ID="40" TIPOPARAM="1">      		   <PARAM>CONCEJO</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>               </DESCRIPTOR>   	      </DATO> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map2_2 WHERE IDFICHA='6';
END;
/

DECLARE

map2_3 clob :='            <DATO TIPO="5" ID="41" TIPOPARAM="1">      		   <PARAM>POBLACION</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>               </DESCRIPTOR>   	      </DATO>            <DATO TIPO="1" ID="42" TIPOPARAM="1">      		   <PARAM>LOCALIZACION</PARAM>   	      </DATO>            <DATO TIPO="1" ID="212" TIPOPARAM="1">      		   <PARAM>VALIDADO</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      	</DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">            <DATO TIPO="1" ID="5" TIPOPARAM="1">               <PARAM>FORMATO</PARAM>            </DATO>            <DATO TIPO="1" ID="6" TIPOPARAM="1">               <PARAM>TIPO</PARAM>            </DATO>            <DATO TIPO="4" ID="7" TIPOPARAM="1">               <PARAM>NUM_DOCS</PARAM> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map2_3 WHERE IDFICHA='6';
END;
/

DECLARE

map2_4 clob :='            </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">            <DATO TIPO="1" ID="19" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="43" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">            <DATO TIPO="1" ID="49" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="50" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>      <TABLA>          <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/RANGOS/RANGO">              <DATO TIPO="1" ID="201" TIPOPARAM="1">	             <PARAM>DESDE</PARAM>              </DATO>              <DATO TIPO="1" ID="202" TIPOPARAM="1"> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map2_4 WHERE IDFICHA='6';
END;
/

DECLARE

map2_5 clob :='	             <PARAM>HASTA</PARAM>	      </DATO>          </FILA>      </TABLA>   </DATOS_TABLA></MAP_UDOC_REL_A_DESCR> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map2_5 WHERE IDFICHA='6';
END;
/

--Mapeo: '10'

DECLARE

map3 clob :='<?xml version="1.0" encoding="ISO-8859-1" ?><MAP_UDOC_REL_A_DESCR Version="5.0">   <DATOS_SIMPLES>      <DATO TIPO="1" ID="14" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>      </DATO>   	<DATO TIPO="3" ID="3" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>      </DATO>      <DATO TIPO="3" ID="4" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>      </DATO>	   <DATO TIPO="5" ID="16" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>	   </DATO>      <DATO TIPO="1" ID="15" TIPOPARAM="1">         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>         <TRANSFORMA_VALOR>	         <VALOR ORG="S">Si</VALOR>	         <VALOR ORG="N">No</VALOR>         </TRANSFORMA_VALOR>      </DATO>      <DATO TIPO="1" ID="1" TIPOPARAM="3">         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM> ';

BEGIN
DELETE FROM ASGTMAPDESCRUDOC WHERE IDFICHA='10';
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO)VALUES('10',map3);
END;
/

DECLARE

map3_1 clob :='      </DATO>   </DATOS_SIMPLES>   <DATOS_TABLA>   	<TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">            <DATO TIPO="5" ID="9" TIPOPARAM="1">      		   <PARAM>IDENTIDAD</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>      			   <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>               </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="1" ID="10" TIPOPARAM="1">      		   <PARAM>NUM_IDENTIDAD</PARAM>   	      </DATO>            <DATO TIPO="1" ID="11" TIPOPARAM="1">      		   <PARAM>ROL</PARAM>   	      </DATO>    		   <DATO TIPO="1" ID="12" TIPOPARAM="1">      		   <PARAM>VALIDADO_EN_TERCEROS</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>           <DATO TIPO="1" ID="51" TIPOPARAM="1"> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map3_1 WHERE IDFICHA='10';
END;
/

DECLARE

map3_2 clob :='      		   <PARAM>ID_EN_TERCEROS</PARAM>   	      </DATO>    	  <DATO TIPO="1" ID="213" TIPOPARAM="1">      		   <PARAM>PRINCIPAL</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">            <DATO TIPO="5" ID="2" TIPOPARAM="1">      		   <PARAM>PAIS</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>            <DATO TIPO="5" ID="39" TIPOPARAM="1">      		   <PARAM>PROVINCIA</PARAM>		   <DESCRIPTOR>			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>		   </DESCRIPTOR>   	      </DATO>      		<DATO TIPO="5" ID="40" TIPOPARAM="1">      		   <PARAM>CONCEJO</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>               </DESCRIPTOR>   	      </DATO> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map3_2 WHERE IDFICHA='10';
END;
/

DECLARE

map3_3 clob :='            <DATO TIPO="5" ID="41" TIPOPARAM="1">      		   <PARAM>POBLACION</PARAM>               <DESCRIPTOR>                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>               </DESCRIPTOR>   	      </DATO>            <DATO TIPO="1" ID="42" TIPOPARAM="1">      		   <PARAM>LOCALIZACION</PARAM>   	      </DATO>            <DATO TIPO="1" ID="212" TIPOPARAM="1">      		   <PARAM>VALIDADO</PARAM>               <TRANSFORMA_VALOR>      	         <VALOR ORG="S">Si</VALOR>      	         <VALOR ORG="N">No</VALOR>               </TRANSFORMA_VALOR>   	      	</DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">            <DATO TIPO="1" ID="5" TIPOPARAM="1">               <PARAM>FORMATO</PARAM>            </DATO>            <DATO TIPO="1" ID="6" TIPOPARAM="1">               <PARAM>TIPO</PARAM>            </DATO>            <DATO TIPO="4" ID="7" TIPOPARAM="1">               <PARAM>NUM_DOCS</PARAM> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map3_3 WHERE IDFICHA='10';
END;
/

DECLARE

map3_4 clob :='            </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">            <DATO TIPO="1" ID="19" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="43" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>      <TABLA>         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">            <DATO TIPO="1" ID="49" TIPOPARAM="1">      		   <PARAM>NOMBRE</PARAM>   	      </DATO>            <DATO TIPO="1" ID="50" TIPOPARAM="1">      		   <PARAM>DESCRIPCION</PARAM>   	      </DATO>         </FILA>      </TABLA>   </DATOS_TABLA></MAP_UDOC_REL_A_DESCR> ';

BEGIN
UPDATE ASGTMAPDESCRUDOC SET INFO=INFO || map3_4 WHERE IDFICHA='10';
END;
/