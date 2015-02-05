/******************************************************************************************************/
/* EJECUTAR ESTOS ANTES PARA CONDICIONES DE FRACCIÓN DE SERIE QUE VAN EN LOS OTROS SCRITPS Versión 2.8.1 */
/******************************************************************************************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (ID,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.8.1',getdate());


-- Insertar nivel del cuadro
INSERT INTO ASGFNIVELCF ( ID, NOMBRE, TIPO, IDFICHADESCRPREF, IDFICHACLFDOCPREF,IDLVOLPREF, SUBTIPO )
VALUES( '00000000000000000000000000000016', 'Fracción de serie', 6, '6', NULL, NULL, 62);

-- Insertar Jerarquía entre Serie -> Fracción de Serie
INSERT INTO ASGFESTRUCTJNIVCF ( IDNIVELPADRE, TIPONIVELPADRE, IDNIVELHIJO,TIPONIVELHIJO )
VALUES ( '00000000000000000000000000000008', 4, '00000000000000000000000000000016', 5);

-- Insertar campo tabla de rango de expedientes
INSERT INTO ADCAMPOTBL(ID, NOMBRE, TIPONORMA, IDAREA, ETIQUETAXML, ETIQXMLFILA, DESCRIPCION )
VALUES ('102', 'Rangos de expedientes', 1, '1', 'Rangos_Expedientes', 'Rango_Expedientes', null);

-- Insertar campos de rangos y rangos normalizados
INSERT INTO ADCAMPODATO(ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML, DESCRIPCION)
VALUES ('201', 'Rango de expedientes: Desde', 1, 1, '1', '102', 1, 'Exp_Desde', null);

INSERT INTO ADCAMPODATO(ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML, DESCRIPCION)
VALUES ('202', 'Rango de expedientes: Hasta', 1, 1, '1', '102', 2, 'Exp_Hasta', null);

INSERT INTO ADCAMPODATO(ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML, DESCRIPCION)
VALUES ('210', 'Rango de expedientes: Desde Normalizado', 1, 1, '1', '102', 3, 'Exp_Desde_Norm', null);

INSERT INTO ADCAMPODATO(ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML, DESCRIPCION)
VALUES ('211', 'Rango de expedientes: Hasta Normalizado', 1, 1, '1', '102', 4, 'Exp_Hasta_Norm', null);

/********************************************
 * Asignar las tablas a las areas
 ********************************************/
--Area de mención de la identidad->Tabla Volumen y Forma Documental
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'7',2);

--Area de mención de la identidad->Volumen y Forma Documental: Cantidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'214',2);

/******************************************************************************************************/
/* Versión 2.5 */
/******************************************************************************************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (ID,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.5',getdate());
	GO

	-- Actualización de la ficha preferente del nivel de fracción de serie al correcto
	UPDATE ASGFNIVELCF SET IDFICHADESCRPREF = 6 WHERE ID = '00000000000000000000000000000016';
	GO

	-- Insertar tabla de validación Tipos de Documentación
	INSERT INTO ADCTLGTBLVLD (ID,NOMBRE,DESCRIPCION,USOINTERNO) VALUES(
	'ID_TBLVLD_TIPO_DOCUMENTACION','Tipos de Documentación',NULL,'S');
	GO

	-- Insertar valor Esencial en la tabla de validación Tipos de Documentación
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (139,'Esencial','ID_TBLVLD_TIPO_DOCUMENTACION');
	GO

	-- Insertar valor Gestión en la tabla de validación Tipos de Documentación
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (140,'Gestión','ID_TBLVLD_TIPO_DOCUMENTACION');
	GO

	--Modificar el campo VALOR para que sea de tipo texto largo en AGINFOSISTEMA.
	ALTER TABLE AGINFOSISTEMA ALTER COLUMN VALOR TEXT;
	GO

	-- Insertar tabla de validación Longitud
	INSERT INTO ADCTLGTBLVLD (ID,NOMBRE,DESCRIPCION,USOINTERNO) VALUES(
	'ID_TBLVLD_LONGITUD','Longitud','Longitud','N');
	GO

	-- Insertar valores en la tabla de validación Longitud
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (141,'mm','ID_TBLVLD_LONGITUD');
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (142,'dm','ID_TBLVLD_LONGITUD');
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (143,'cm','ID_TBLVLD_LONGITUD');
	INSERT INTO ADTEXTTBLVLD (ID,VALOR,IDTBLVLD) VALUES (144,'m','ID_TBLVLD_LONGITUD');
	GO

	--Cambiar el Nombre de la Columna ID por NOMBRE
	EXEC sp_rename
			@objname = 'AGINFOSISTEMA.ID',
			@newname = 'NOMBRE',
			@objtype = 'COLUMN';
	GO

	--Cambiar el Tamaño de la columna de Nombre a 32
	ALTER TABLE AGINFOSISTEMA ALTER COLUMN NOMBRE VARCHAR(32);
	GO

	--Insertar Valor de DATOS_GEOGRAFICOS
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION)
		VALUES ('DATOS_GEOGRAFICOS',
		'<datos_geograficos>
            <pais codigo="108" nombre="España"/>
            <provincia codigo="31" nombre="Navarra"/>
		</datos_geograficos>',
		getdate());

	--Insertar Valor de MAP_PAISES
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION)
		VALUES ('MAP_PAISES',
		'<lista_paises>
		<pais nombre="España" codigo="ES">
			<comunidad nombre="NAVARRA" codigo="NA"/>
		</pais>
		</lista_paises>',
		getdate());

	-- Añadido nuevo campo a la tabla de relaciones de entrega para indicar el nivel documental del que se está creando la relación de entrega
	ALTER TABLE ASGTRENTREGA ADD IDNIVELDOCUMENTAL VARCHAR(32);
	GO

	-- Actualizar en la tabla relación de entrega el nivel de unidad documental
	UPDATE ASGTRENTREGA SET IDNIVELDOCUMENTAL = '00000000000000000000000000000009';
	GO

    --Poner la columna IDNIVELDOCUMENTAL en ASGTRENTREGA para que no pueda ser nula.
    ALTER TABLE ASGTRENTREGA ALTER COLUMN IDNIVELDOCUMENTAL VARCHAR(32) NOT NULL;
	GO

	-- Crear tabla de asociacion de id de ficha con el XML de mapeo a descripción
	CREATE TABLE ASGTMAPDESCRUDOC (
		IDFICHA		VARCHAR (32)  NOT NULL,
		INFO		TEXT  NOT NULL
	);
	GO

	-- Crear el índice para esta tabla
	CREATE INDEX ADASGTMAPDESCRUDOC1 ON ASGTMAPDESCRUDOC (IDFICHA);
	GO

	-- Insertar XML de mapeo para la ficha de unidad documental
	INSERT INTO ASGTMAPDESCRUDOC
	VALUES ('1','<?xml version="1.0" encoding="ISO-8859-1" ?>
<MAP_UDOC_REL_A_DESCR>
   <DATOS_SIMPLES>
      <DATO TIPO="1" ID="14" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>
      </DATO>
		<DATO TIPO="3" ID="3" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>
      </DATO>
      <DATO TIPO="3" ID="4" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>
      </DATO>
		<DATO TIPO="5" ID="16" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>
		</DATO>
      <DATO TIPO="1" ID="15" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>
         <TRANSFORMA_VALOR>
            <VALOR ORG="S">Si</VALOR>
            <VALOR ORG="N">No</VALOR>
         </TRANSFORMA_VALOR>
      </DATO>
      <DATO TIPO="1" ID="1" TIPOPARAM="3">
         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM>
      </DATO>
   </DATOS_SIMPLES>
   <DATOS_TABLA>
		<TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">
            <DATO TIPO="5" ID="9" TIPOPARAM="1">
               <PARAM>IDENTIDAD</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>
                  <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>
                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>
               </DESCRIPTOR>
            </DATO>
				<DATO TIPO="1" ID="10" TIPOPARAM="1">
               <PARAM>NUM_IDENTIDAD</PARAM>
            </DATO>
            <DATO TIPO="1" ID="11" TIPOPARAM="1">
               <PARAM>ROL</PARAM>
            </DATO>
             <DATO TIPO="1" ID="12" TIPOPARAM="1">
               <PARAM>VALIDADO_EN_TERCEROS</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
            <DATO TIPO="1" ID="51" TIPOPARAM="1">
               <PARAM>ID_EN_TERCEROS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">
            <DATO TIPO="5" ID="2" TIPOPARAM="1">
               <PARAM>PAIS</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="39" TIPOPARAM="1">
               <PARAM>PROVINCIA</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
				<DATO TIPO="5" ID="40" TIPOPARAM="1">
               <PARAM>CONCEJO</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="41" TIPOPARAM="1">
               <PARAM>POBLACION</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="1" ID="42" TIPOPARAM="1">
               <PARAM>LOCALIZACION</PARAM>
            </DATO>
            <DATO TIPO="1" ID="212" TIPOPARAM="1">
               <PARAM>VALIDADO</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">
            <DATO TIPO="1" ID="5" TIPOPARAM="1">
               <PARAM>FORMATO</PARAM>
            </DATO>
            <DATO TIPO="1" ID="6" TIPOPARAM="1">
               <PARAM>TIPO</PARAM>
            </DATO>
            <DATO TIPO="4" ID="7" TIPOPARAM="1">
               <PARAM>NUM_DOCS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">
            <DATO TIPO="1" ID="19" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="43" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">
            <DATO TIPO="1" ID="49" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="50" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
   </DATOS_TABLA>
</MAP_UDOC_REL_A_DESCR>'
);
GO

	-- Insertar XML de mapeo para la ficha de fracción de serie
	INSERT INTO ASGTMAPDESCRUDOC
	VALUES ('6','<?xml version="1.0" encoding="ISO-8859-1" ?>
<MAP_UDOC_REL_A_DESCR>
   <DATOS_SIMPLES>
      <DATO TIPO="1" ID="14" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/EXPEDIENTE/SISTEMA_PRODUCTOR/NOMBRE</PARAM>
      </DATO>
		<DATO TIPO="3" ID="3" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_INICIAL</PARAM>
      </DATO>
      <DATO TIPO="3" ID="4" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/FECHA_FINAL</PARAM>
      </DATO>
		<DATO TIPO="5" ID="16" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PRODUCTOR/ID</PARAM>
		</DATO>
      <DATO TIPO="1" ID="15" TIPOPARAM="1">
         <PARAM>UNIDAD_DOCUMENTAL_TRANSFERENCIA/PUBLICACION_EN_SAC</PARAM>
         <TRANSFORMA_VALOR>
            <VALOR ORG="S">Si</VALOR>
            <VALOR ORG="N">No</VALOR>
         </TRANSFORMA_VALOR>
      </DATO>
      <DATO TIPO="1" ID="1" TIPOPARAM="3">
         <PARAM>transferencias.model.validacion.ObtencionValorIdentificacion</PARAM>
      </DATO>
   </DATOS_SIMPLES>
   <DATOS_TABLA>
		<TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/INTERESADOS/INTERESADO">
            <DATO TIPO="5" ID="9" TIPOPARAM="1">
               <PARAM>IDENTIDAD</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_INTERESADO</ID_LISTA>
                  <SIST_EXT TIPOPARAM="2">BDTERCEROS</SIST_EXT>
                  <ID_SIST_EXT TIPOPARAM="1">ID_EN_TERCEROS</ID_SIST_EXT>
               </DESCRIPTOR>
            </DATO>
				<DATO TIPO="1" ID="10" TIPOPARAM="1">
               <PARAM>NUM_IDENTIDAD</PARAM>
            </DATO>
            <DATO TIPO="1" ID="11" TIPOPARAM="1">
               <PARAM>ROL</PARAM>
            </DATO>
             <DATO TIPO="1" ID="12" TIPOPARAM="1">
               <PARAM>VALIDADO_EN_TERCEROS</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
            <DATO TIPO="1" ID="51" TIPOPARAM="1">
               <PARAM>ID_EN_TERCEROS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/EMPLAZAMIENTOS/EMPLAZAMIENTO">
            <DATO TIPO="5" ID="2" TIPOPARAM="1">
               <PARAM>PAIS</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PAIS</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="39" TIPOPARAM="1">
               <PARAM>PROVINCIA</PARAM>
            <DESCRIPTOR>
			<ID_LISTA>ID_LIST_PROVINCIA</ID_LISTA>
            </DESCRIPTOR>
            </DATO>
				<DATO TIPO="5" ID="40" TIPOPARAM="1">
               <PARAM>CONCEJO</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_CONCEJO</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="5" ID="41" TIPOPARAM="1">
               <PARAM>POBLACION</PARAM>
               <DESCRIPTOR>
                  <ID_LISTA>ID_LIST_POBLACION</ID_LISTA>
               </DESCRIPTOR>
            </DATO>
            <DATO TIPO="1" ID="42" TIPOPARAM="1">
               <PARAM>LOCALIZACION</PARAM>
            </DATO>
            <DATO TIPO="1" ID="212" TIPOPARAM="1">
               <PARAM>VALIDADO</PARAM>
               <TRANSFORMA_VALOR>
					<VALOR ORG="S">Si</VALOR>
					<VALOR ORG="N">No</VALOR>
               </TRANSFORMA_VALOR>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/VOLUMEN_Y_SOPORTE/SOPORTE">
            <DATO TIPO="1" ID="5" TIPOPARAM="1">
               <PARAM>FORMATO</PARAM>
            </DATO>
            <DATO TIPO="1" ID="6" TIPOPARAM="1">
               <PARAM>TIPO</PARAM>
            </DATO>
            <DATO TIPO="4" ID="7" TIPOPARAM="1">
               <PARAM>NUM_DOCS</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_FISICOS/DOCUMENTO">
            <DATO TIPO="1" ID="19" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="43" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
         <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/DOCUMENTOS/DOCUMENTOS_ELECTRONICOS/DOCUMENTO">
            <DATO TIPO="1" ID="49" TIPOPARAM="1">
               <PARAM>NOMBRE</PARAM>
            </DATO>
            <DATO TIPO="1" ID="50" TIPOPARAM="1">
               <PARAM>DESCRIPCION</PARAM>
            </DATO>
         </FILA>
      </TABLA>
      <TABLA>
          <FILA NODO="UNIDAD_DOCUMENTAL_TRANSFERENCIA/RANGOS/RANGO">
              <DATO TIPO="1" ID="201" TIPOPARAM="1">
                <PARAM>DESDE</PARAM>
              </DATO>
              <DATO TIPO="1" ID="202" TIPOPARAM="1">
                <PARAM>HASTA</PARAM>
              </DATO>
          </FILA>
      </TABLA>
   </DATOS_TABLA>
</MAP_UDOC_REL_A_DESCR>'
);
GO

	--Añadido nivel Raiz. Creada entrada para descendientes del nivel raiz. Id ID_NIVEL_RAIZ tipo 0
	INSERT INTO ASGFESTRUCTJNIVCF ( IDNIVELPADRE, TIPONIVELPADRE, IDNIVELHIJO,TIPONIVELHIJO ) VALUES ('ID_NIVEL_RAIZ', 0, '00000000000000000000000000000001', 1);

	-- Añadido nuevo campo a tabla ASGFSERIE
	ALTER TABLE ASGFSERIE ADD INFOFICHAUDOCLVOL text;
	GO

	-- Actualizar las series para meter en un xml los valores de IDFICHADESCRPREFUDOC, IDFICHACLFDOCPREFUDOC y IDLVOLPREFUDOC
	UPDATE ASGFSERIE
SET INFOFICHAUDOCLVOL =
'<INFO_FICHA_UDOC_LVOL>
 <NIVELES_DOCUMENTALES>
	<NIVEL>
		<ID_NIVEL>00000000000000000000000000000009</ID_NIVEL>
		<ID_FICHADESCRPREFUDOC>'+idfichadescrprefudoc+'</ID_FICHADESCRPREFUDOC>
		<ID_FICHACLFDOCPREFUDOC>'+idfichaclfdocprefudoc+'</ID_FICHACLFDOCPREFUDOC>
		<ID_LVOLPREFUDOC>'+idlvolprefudoc+'</ID_LVOLPREFUDOC>
	</NIVEL>
 </NIVELES_DOCUMENTALES>
</INFO_FICHA_UDOC_LVOL>'
WHERE IDFICHADESCRPREFUDOC IS NOT NULL AND IDFICHACLFDOCPREFUDOC IS NOT NULL
AND IDLVOLPREFUDOC IS NOT NULL;

	UPDATE ASGFSERIE
SET INFOFICHAUDOCLVOL =
'<INFO_FICHA_UDOC_LVOL>
 <NIVELES_DOCUMENTALES>
	<NIVEL>
		<ID_NIVEL>00000000000000000000000000000009</ID_NIVEL>
		<ID_LVOLPREFUDOC>'+idlvolprefudoc+'</ID_LVOLPREFUDOC>
	</NIVEL>
 </NIVELES_DOCUMENTALES>
</INFO_FICHA_UDOC_LVOL>'
WHERE IDFICHADESCRPREFUDOC IS NULL AND IDFICHACLFDOCPREFUDOC IS NULL
AND IDLVOLPREFUDOC IS NOT NULL;

	UPDATE ASGFSERIE
SET INFOFICHAUDOCLVOL =
'<INFO_FICHA_UDOC_LVOL>
 <NIVELES_DOCUMENTALES>
	<NIVEL>
		<ID_NIVEL>00000000000000000000000000000009</ID_NIVEL>
		<ID_FICHADESCRPREFUDOC>'+idfichadescrprefudoc+'</ID_FICHADESCRPREFUDOC>
		<ID_LVOLPREFUDOC>'+idlvolprefudoc+'</ID_LVOLPREFUDOC>
	</NIVEL>
 </NIVELES_DOCUMENTALES>
</INFO_FICHA_UDOC_LVOL>'
WHERE IDFICHADESCRPREFUDOC IS NOT NULL AND IDFICHACLFDOCPREFUDOC IS NULL
AND IDLVOLPREFUDOC IS NOT NULL;

	-- Eliminar columnas de la tabla ASGFSERIE
	ALTER TABLE ASGFSERIE DROP COLUMN IDFICHADESCRPREFUDOC, IDFICHACLFDOCPREFUDOC, IDLVOLPREFUDOC;
	GO

	-- Actualizar el idarchivo a los elementos que no son fondos o unidades o fracciones de serie
	UPDATE ASGFELEMENTOCF SET IDARCHIVO=NULL WHERE IDNIVEL NOT IN ('00000000000000000000000000000003','00000000000000000000000000000016','00000000000000000000000000000009') AND IDARCHIVO IS NOT NULL;
	GO

	-- Insertar el campo Emplazamiento Validado
	INSERT INTO ADCAMPODATO(ID,NOMBRE,TIPO,TIPONORMA,IDAREA,IDTBLPADRE,POSENTBL,ETIQUETAXML,DESCRIPCION) VALUES(
	'212','Emplazamiento_Validado',1,1,'1','3',6,'Validado',NULL);
	GO

	-- Insertar el campo Creación Registro Autoridad
	INSERT INTO ADCAMPODATO ( ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML,
	DESCRIPCION ) VALUES (
	'124', 'Fecha Creación Registro Autoridad', 3, 2, '12', NULL, NULL, 'Fecha_Registro_Autoridad', NULL);

	--Cambiar el Nombre del Dato 'Cantidad'  ->  'Volumen y Soporte: Cantidad'	ID='7'
	UPDATE ADCAMPODATO SET NOMBRE='Volumen y Soporte: Cantidad' WHERE ID='7';

	--Cambiar el Nombre del Dato 'Soporte Documental' -> 'Volumen y Soporte: Soporte Documental' ID='8'
	UPDATE ADCAMPODATO SET NOMBRE='Volumen y Soporte: Soporte Documental' WHERE ID='8';

	--Cambiar el Nombre del Dato 'Interesado_Identidad' por 'Interesado: Identidad' ID='9'
	UPDATE ADCAMPODATO SET NOMBRE='Interesado: Identidad' WHERE ID='9';

	--Cambiar el Nombre del Dato 'Interesado_NumIdentidad' por 'Interesado: Num. Identidad' ID='10'
	UPDATE ADCAMPODATO SET NOMBRE='Interesado: Num. Identidad' WHERE ID='10';

	--Cambiar el Nombre del Dato 'Interesado_Rol' por 'Interesado: Rol' ID='11'
	UPDATE ADCAMPODATO SET NOMBRE='Interesado: Rol' WHERE ID='11';

	--Cambiar el Nombre del Dato 'Interesado_Validado' por 'Interesado: Validado' ID='12'
	UPDATE ADCAMPODATO SET NOMBRE='Interesado: Validado' WHERE ID='12';

	--Cambiar el Nombre del Dato 'Interesado_IdTercero' por 'Interesado: Id de Terceros' ID='51'
	UPDATE ADCAMPODATO SET NOMBRE='Interesado: Id de Terceros' WHERE ID='51';

	--Cambiar el Nombre del Dato 'Emplazamiento_País' por 'Emplazamiento: País' ID='2'
	UPDATE ADCAMPODATO SET NOMBRE='Emplazamiento: País' WHERE ID='2';

	--Cambiar el Nombre del Dato 'Emplazamiento_Comunidad' por 'Emplazamiento: Provincia' ID='39'
	UPDATE ADCAMPODATO SET NOMBRE='Emplazamiento: Provincia' WHERE ID='39';

	--Cambiar el Nombre del Dato 'Emplazamiento_Concejo' por 'Emplazamiento: Municipio' ID='40'
	UPDATE ADCAMPODATO SET NOMBRE='Emplazamiento: Municipio' WHERE ID='40';

	--Cambiar el Nombre del Dato 'Emplazamiento_Población' por 'Emplazamiento: Población' ID='41'
	UPDATE ADCAMPODATO SET NOMBRE='Emplazamiento: Población' WHERE ID='41';

	--Cambiar el Nombre del Dato 'Emplazamiento_Localización' por 'Emplazamiento: Localización' ID='42'
	UPDATE ADCAMPODATO SET NOMBRE='Emplazamiento: Localización' WHERE ID='42';

	--Cambiar el Nombre del Dato 'UDoc_Identificador' por 'Identificación' ID='1'
	UPDATE ADCAMPODATO SET NOMBRE='Identificación' WHERE ID='1';

	--Cambiar el Nombre del Dato 'Fecha Inicial' por 'Fecha Extrema Inicial' ID='3'
	UPDATE ADCAMPODATO SET NOMBRE='Fecha Extrema Inicial'	WHERE ID='3';

	--Cambiar el Nombre del Dato 'Fecha Final' por 'Fecha Extrema Final' ID='4'
	UPDATE ADCAMPODATO SET NOMBRE='Fecha Extrema Final'	WHERE ID='4';

	--Cambiar el Nombre del Dato 'Nombre Sistema Productor' por 'Sistema Productor' ID='14'
	UPDATE ADCAMPODATO SET NOMBRE='Sistema Productor' WHERE ID='14';

	--Cambiar el Nombre del Dato 'ProductorSerie_Nombre' por 'Productor: Nombre'	ID='34'
	UPDATE ADCAMPODATO SET NOMBRE='Productor: Nombre' WHERE ID='34';

	--Cambiar el Nombre del Dato 'ProductorSerie_FechaIni' por 'Productor: Fecha Inicio' ID='36'
	UPDATE ADCAMPODATO SET NOMBRE='Productor: Fecha Inicio'	WHERE ID='36';

	--Cambiar el Nombre del Dato 'ProductorSerie_FechaFin' por 'Productor: Fecha Fin' ID='37'
	UPDATE ADCAMPODATO SET NOMBRE='Productor: Fecha Fin' WHERE ID='37';

	--Cambiar el Nombre del Dato 'Descripción Contenido' por 'Descripción de Contenido' ID='13'
	UPDATE ADCAMPODATO SET NOMBRE='Descripción de Contenido' WHERE ID='13';

	--Cambiar el Nombre del Dato 'DocumentoF_Nombre' por 'Documento Físico: Nombre' ID='19'
	UPDATE ADCAMPODATO SET NOMBRE='Documento Físico: Nombre' WHERE ID='19';

	--Cambiar el Nombre del Dato 'DocumentoF_Descripción' por 'Documento Físico: Descripción' ID='43'
	UPDATE ADCAMPODATO SET NOMBRE='Documento Físico: Descripción' WHERE ID='43';

	--Cambiar el Nombre del Dato 'DocumentoE_Nombre' por 'Documento Electrónico: Nombre' ID='49'
	UPDATE ADCAMPODATO SET NOMBRE='Documento Electrónico: Nombre' WHERE ID='49';

	--Cambiar el Nombre del Dato 'DocumentoE_Descripcion' por 'Documento Electrónico: Descripcion' ID='50'
	UPDATE ADCAMPODATO SET NOMBRE='Documento Electrónico: Descripcion' WHERE ID='50';

	--Cambiar el Nombre del Dato 'Valoración_FechaEliminación' por 'Valoración: FechaEliminación' ID='20'
	UPDATE ADCAMPODATO SET NOMBRE='Valoración: FechaEliminación' WHERE ID='20';

	--Cambiar el Nombre del Dato 'Valoración Serie' por 'Valoración de Serie' ID='46'
	UPDATE ADCAMPODATO SET NOMBRE='Valoración de Serie' WHERE ID='46';

	--Cambiar el Nombre del Dato 'Originales_Existen' por 'Originales: Existen' ID='27'
	UPDATE ADCAMPODATO SET NOMBRE='Originales: Existen'	WHERE ID='27';

	--Cambiar el Nombre del Dato 'Originales_Descripción/Ubicación' por 'Originales: Descripción/Ubicación' ID='28'
	UPDATE ADCAMPODATO SET NOMBRE='Originales: Descripción/Ubicación' WHERE ID='28';

	--Cambiar el Nombre del Dato 'Copias_Existen' por 'Copias: Existen' ID='29'
	UPDATE ADCAMPODATO SET NOMBRE='Copias: Existen'	WHERE ID='29';

	--Cambiar el Nombre del Dato 'Copias_Descripción/Ubicación' por 'Copias: Descripción/Ubicación' ID='30'
	UPDATE ADCAMPODATO SET NOMBRE='Copias: Descripción/Ubicación' WHERE ID='30';

	--Cambiar el Nombre del Dato 'Unidades Descripción Relacionadas' 'Unidades de Descripción Relacionadas' ID='31'
	UPDATE ADCAMPODATO SET NOMBRE='Unidades de Descripción Relacionadas' WHERE ID='31';

	--Cambiar el Nombre del Dato 'Nota Publicación' por 'Nota de Publicación' ID='32'
	UPDATE ADCAMPODATO SET NOMBRE='Nota de Publicación'	WHERE ID='32';

	--Cambiar el Nombre del Dato 'Nombre entidad relacionada' por 'Entidad relacionada: Nombre' ID='114'
	UPDATE ADCAMPODATO SET NOMBRE='Entidad relacionada: Nombre' WHERE ID='114';

	--Cambiar el Nombre del Dato 'Tipo de relación' por 'Entidad relacionada: Tipo de relación' ID='115'
	UPDATE ADCAMPODATO SET NOMBRE='Entidad relacionada: Tipo de relación' WHERE ID='115';

	--Cambiar el Nombre del Dato 'Descripción de relación' por 'Entidad relacionada: Descripción de relación' ID='116'
	UPDATE ADCAMPODATO SET NOMBRE='Entidad relacionada: Descripción de relación' WHERE ID='116';

	--Cambiar el Nombre del Dato 'Fecha inicio relación' por 'Entidad relacionada: Fecha inicio de relación' ID='117'
	UPDATE ADCAMPODATO SET NOMBRE='Entidad relacionada: Fecha inicio de relación' WHERE ID='117';

	--Cambiar el Nombre del Dato 'Fecha fin relación' por 'Entidad relacionada: Fecha fin de relación' ID='118'
	UPDATE ADCAMPODATO SET NOMBRE='Entidad relacionada: Fecha fin de relación' WHERE ID='118';

	--Cambiar el Nombre del Dato 'Identificador institución' por 'Identificador de institución' ID='120'
	UPDATE ADCAMPODATO SET NOMBRE='Identificador de institución' WHERE ID='120';

	--Cambiar el Nombre del Dato 'Desde' por 'Rango de expedientes: Desde' ID='201'
	UPDATE ADCAMPODATO SET NOMBRE='Rango de expedientes: Desde' WHERE ID='201';

	--Cambiar el Nombre del Dato 'Hasta' por 'Rango de expedientes: Hasta' ID='202'
	UPDATE ADCAMPODATO SET NOMBRE='Rango de expedientes: Hasta' WHERE ID='202';

	--Cambiar el Nombre del Dato 'Desde_Norm' por 'Rango de expedientes: Desde Normalizado' ID='210'
	UPDATE ADCAMPODATO SET NOMBRE='Rango de expedientes: Desde Normalizado'	WHERE ID='210';

	--Cambiar el Nombre del Dato 'Hasta_Norm' por 'Rango de expedientes: Hasta Normalizado' ID='211'
	UPDATE ADCAMPODATO SET NOMBRE='Rango de expedientes: Hasta Normalizado'	WHERE ID='211';

/**************************************************************************************
 *	ACTUALIZAR USO DE OBJETOS
 **************************************************************************************/

	-- Eliminar todos los objetos usados en la ficha para darlos de alta de nuevo
	DELETE FROM ADUSOOBJETO;

/**************************************************************************************
 *	FICHA DE UNIDAD DOCUMENTAL
 **************************************************************************************/

--Area de mención de la identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'1',1);

	--Dato:UDoc_Identificador
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',1,'1',1);

	--Campo Tabla:Emplazamientos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',5,'1',1);

		--Dato:Emplazamiento_País
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',1,'1',1);

		--Dato:Emplazamiento_Provincia
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('39',1,'1',1);

		--Dato:Emplazamiento_Concejo
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('40',1,'1',1);

		--Dato:Emplazamiento_Población
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('41',1,'1',1);

		--Dato:Emplazamiento_Localización
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('42',1,'1',1);

		--Dato:Emplazamiento_Validado
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('212',1,'1',1);

	--Dato:Fecha Inicial
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',1,'1',1);

	--Dato:Fecha Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',1,'1',1);

	--Campo Tabla:Volumen y Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',5,'1',1);

		--Dato:Soporte
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',1,'1',1);

		--Dato:Formato
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',1,'1',1);

		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',1,'1',1);

	--Campo Tabla:Interesados
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',5,'1',1);

		--Dato:Interesado_NumIdentidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',1,'1',1);

		--Dato:Interesado_Identidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',1,'1',1);

		--Dato:Interesado_Rol
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',1,'1',1);

		--Dato:Interesado_Validado
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',1,'1',1);

		--Dato:Interesado_IdTercero
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('51',1,'1',1);

	--Dato:Nombre Sistema Productor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('14',1,'1',1);

	--Dato:Publicación SAC
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('15',1,'1',1);

--Area de contexto
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'1',1);

	--Dato:Productor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('16',1,'1',1);

	--Dato:Historia Institucional
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('17',1,'1',1);

	--Dato:Historia Archivística
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('18',1,'1',1);

	--Dato:Ingreso por
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('203',1,'1',1);

	--Dato:Código de transferencia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('204',1,'1',1);

	--Dato:Referencias de ingreso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('205',1,'1',1);

	--Dato:Hoja de Entrega
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('206',1,'1',1);

--Area de alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'1',1);

	--Campo Tabla:Documentos Físicos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',5,'1',1);

		--Dato:Descripción Contenido
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',1,'1',1);

		--Dato:DocumentoF_Nombre
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('19',1,'1',1);

	--Dato:DocumentoF_Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('43',1,'1',1);

	--Campo Tabla:Documentos Electrónicos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',5,'1',1);

	--Dato:DocumentoE_Nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('49',1,'1',1);

	--Dato:DocumentoE_Descripcion
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('50',1,'1',1);

	--Dato:Valoración_FechaEliminación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('20',1,'1',1);

	--Dato:Nuevos ingresos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('21',1,'1',1);

--Area de condiciones de acceso y seguridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'1',1);

	--Dato:Condiciones de Acceso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('22',1,'1',1);

	--Dato:Condiciones de Reproducción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('23',1,'1',1);

	--Dato:Lenguas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('24',1,'1',1);

	--Dato:Características físicas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('25',1,'1',1);

	--Dato:Instrumentos de Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('26',1,'1',1);

--Area de documentación asociada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'1',1);

	--Dato:Originales_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('27',1,'1',1);

	--Dato:Originales_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('28',1,'1',1);

	--Dato:Copias_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('29',1,'1',1);

	--Dato:Copias_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('30',1,'1',1);

	--Dato:Unidades Descripción Relacionadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('31',1,'1',1);

	--Dato:Nota Publicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('32',1,'1',1);

--Area de notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'1',1);

	--Dato:Notas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('35',1,'1',1);

--Area de control de la descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'1',1);

	--Dato:Normas utilizadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('33',1,'1',1);

--Area de descriptores
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'1',1);

	--Dato:Descriptor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('44',1,'1',1);


--DESCRIPTOR

	--Descriptor:Organización
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_BDORGANIZACION',3,'1',1);

	--Descriptor:Provincia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_PROVINCIA',3,'1',1);

	--Descriptor:Municipio
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_CONCEJO',3,'1',1);

	--Descriptor:Institución
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_INSTITUCION',3,'1',1);

	--Descriptor:Interesados
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_INTERESADO',3,'1',1);

	--Descriptor:Organo
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_ORGANO',3,'1',1);

	--Descriptor:País
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_PAIS',3,'1',1);

	--Descriptor:Población
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_POBLACION',3,'1',1);


--TABLAS DE VALIDACION

	--Tabla de Validación:Formas de ingreso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_INGRESOS',2,'1',1);

	--Tabla de Validación:Lengua
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'1',1);

	--Tabla de Validación:Rol interesado
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_ROLES_INTERESADO',2,'1',1);

	--Tabla de Validación:Si/No
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SINO',2,'1',1);

	--Tabla de Validación:Formato
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_FORMATO',2,'1',1);

	--Tabla de Validación:Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SOPORTE',2,'1',1);

/**************************************************************************************
 *	FICHA DE SERIE*
 **************************************************************************************/

--Area de mención de la identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'2',1);

	--Dato:Fecha Inicial
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',1,'2',1);

	--Dato:Fecha Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',1,'2',1);

	--Campo Tabla:Volumen y Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',5,'2',1);

		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',1,'2',1);

		--Dato:Soporte Documental
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',1,'2',1);

		--Dato:Nombre Sistema Productor
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('14',1,'2',1);

		--Dato:Publicación SAC
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('15',1,'2',1);

--Area de contexto
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'2',1);

	--Campo Tabla:Productores de Serie
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',5,'2',1);

		--Dato:ProductorSerie_Nombre
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('34',1,'2',1);

		--Dato:ProductorSerie_FechaIni
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('36',1,'2',1);

		--Dato:ProductorSerie_FechaFin
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('37',1,'2',1);

	--Dato:Historia Institucional
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('17',1,'2',1);

	--Dato:Historia Archivística
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('18',1,'2',1);

--Area de alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'2',1);

	--Dato:Alcance y contenido
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('38',1,'2',1);

	--Dato:Valoración Serie
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('46',1,'2',1);

	--Dato:Admite Nuevos Ingresos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('47',1,'2',1);

--Area de condiciones de acceso y seguridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'2',1);

	--Dato:Condiciones de Acceso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('22',1,'2',1);

	--Dato:Condiciones de Reproducción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('23',1,'2',1);

	--Dato:Lenguas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('24',1,'2',1);

	--Dato:Características físicas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('25',1,'2',1);

	--Dato:Instrumentos de Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('26',1,'2',1);

--Area de documentación asociada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'2',1);

		--Dato:Originales_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('27',1,'2',1);

	--Dato:Originales_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('28',1,'2',1);

	--Dato:Copias_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('29',1,'2',1);

	--Dato:Copias_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('30',1,'2',1);

	--Dato:Nota Publicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('32',1,'2',1);


--Area de notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'2',1);

	--Dato:Notas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('35',1,'2',1);


--Area de control de la descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'2',1);

	--Dato:Normas utilizadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('33',1,'2',1);

--Area de descriptores
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'2',1);

	--Dato:Descriptor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('44',1,'2',1);


--DESCRIPTORES
	--Descriptor:Organización
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_BDORGANIZACION',3,'2',1);

	--Descriptor:Organización
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_ORGANO',3,'2',1);

	--Descriptor:Organización
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_INSTITUCION',3,'2',1);

--TABLAS DE VALIDACION

	--Tabla de Validación:Lengua
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'2',1);

	--Tabla de Validación:Si/No
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SINO',2,'2',1);

	--Tabla de Validación:Longitud
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LONGITUD',2,'2',1);

/**************************************************************************************
 *	FICHA DE CLASIFICADOR*
 **************************************************************************************/
 --Area de mención de la identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'3',1);

	--Dato:Fecha Inicial
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',1,'3',1);

	--Dato:Fecha Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',1,'3',1);

	--Tabla:Volumen y Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',5,'3',1);

		--Campo Tabla:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',1,'3',1);

		--Campo Tabla:Soporte Documental
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',1,'3',1);

		--Campo Tabla:Nombre Sistema Productor
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('14',1,'3',1);

		--Campo Tabla:Publicación SAC
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('15',1,'3',1);

--Area de contexto
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'3',1);

	--Dato:Productor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('16',1,'3',1);

	--Dato:Historia Institucional
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('17',1,'3',1);

	--Dato:Historia Archivística
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('18',1,'3',1);

--Area de alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'3',1);

	--Dato:Alcance y contenido
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('38',1,'3',1);

	--Dato:Admite Nuevos Ingresos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('47',1,'3',1);

	--Dato:Valoración en porcentaje
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('48',1,'3',1);

--Area de condiciones de acceso y seguridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'3',1);

	--Dato:Condiciones de Acceso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('22',1,'3',1);

	--Dato:Condiciones de Reproducción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('23',1,'3',1);

	--Dato:Lenguas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('24',1,'3',1);

	--Dato:Características físicas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('25',1,'3',1);

	--Dato:Instrumentos de Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('26',1,'3',1);

--Area de documentación asociada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'3',1);

	--Dato:Originales_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('27',1,'3',1);

	--Dato:Originales_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('28',1,'3',1);

	--Dato:Copias_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('29',1,'3',1);

	--Dato:Copias_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('30',1,'3',1);

	--Dato:Nota Publicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('32',1,'3',1);

--Area de notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'3',1);

	--Dato:Notas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('35',1,'3',1);


--Area de control de la descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'3',1);

	--Dato:Normas utilizadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('33',1,'3',1);

--Area de descriptores
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'3',1);

	--Dato:Descriptor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('44',1,'3',1);


--TABLAS DE VALIDACION

--Tabla de Validación:Lengua
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'3',1);

--Tabla de Validación:Si/No
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SINO',2,'3',1);


/**************************************************************************************
 *	FICHA DE FONDO*
 **************************************************************************************/
--Area de mención de la identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'4',1);

	--Dato:Fecha Inicial
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',1,'4',1);

	--Dato:Fecha Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',1,'4',1);

	--Campo Tabla:Volumen y Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',5,'4',1);

		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',1,'4',1);

		--Dato:Soporte Documental
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',1,'4',1);

		--Dato:Nombre Sistema Productor
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('14',1,'4',1);

		--Dato:Publicación SAC
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('15',1,'4',1);

--Area de contexto
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'4',1);

	--Dato:Productor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('16',1,'4',1);

	--Dato:Historia Institucional
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('17',1,'4',1);

	--Dato:Historia Archivística
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('18',1,'4',1);

--Area de alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'4',1);

	--Dato:Alcance y contenido
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('38',1,'4',1);

	--Dato:Admite Nuevos Ingresos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('47',1,'4',1);

	--Dato:Valoración en porcentaje
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('48',1,'4',1);

--Area de condiciones de acceso y seguridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'4',1);

	--Dato:Condiciones de Acceso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('22',1,'4',1);

	--Dato:Condiciones de Reproducción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('23',1,'4',1);

	--Dato:Lenguas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('24',1,'4',1);

	--Dato:Características físicas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('25',1,'4',1);

	--Dato:Instrumentos de Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('26',1,'4',1);

--Area de documentación asociada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'4',1);

	--Dato:Originales_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('27',1,'4',1);

	--Dato:Originales_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('28',1,'4',1);

	--Dato:Copias_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('29',1,'4',1);

	--Dato:Copias_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('30',1,'4',1);

	--Dato:Nota Publicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('32',1,'4',1);

--Area de notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'4',1);

	--Dato:Notas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('35',1,'4',1);

--Area de control de la descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'4',1);

	--Dato:Normas utilizadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('33',1,'4',1);

--Area de descriptores
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'4',1);

	--Dato:Descriptor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('44',1,'4',1);


--TABLAS DE VALIDACION

	--Tabla de Validación:Lengua
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'4',1);

	--Tabla de Validación:Si/No
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SINO',2,'4',1);


/**************************************************************************************
 *	FICHA DE ISAAR*
 **************************************************************************************/
 --Area de Identificación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'5',1);

	--Dato:Tipo de entidad
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('100',1,'5',1);

	--Dato:Forma autorizada de nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('101',1,'5',1);

	--Dato:Formas paralelas de nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('102',1,'5',1);

	--Dato:Formas normalizadas de nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('103',1,'5',1);

	--Dato:Otras formas de nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('104',1,'5',1);

	--Dato:Identificadores para instituciones
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('105',1,'5',1);

--Area de Descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'5',1);

	--Dato:Fechas de existencia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('106',1,'5',1);

	--Dato:Historia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('107',1,'5',1);

	--Dato:Lugar
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('108',1,'5',1);

	--Dato:Estatuto jurídico
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('109',1,'5',1);

	--Dato:Funciones, ocupaciones
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('110',1,'5',1);

	--Dato:Atribuciones/Fuentes Legales
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('111',1,'5',1);

	--Dato:Estructura interna/Genealogía
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('112',1,'5',1);

	--Dato:Contexto General
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('113',1,'5',1);

--Area de Relaciones
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'5',1);

	--Campo Tabla:Entidades Relacionadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('100',5,'5',1);

	--Dato:Nombre entidad relacionada
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('114',1,'5',1);

	--Dato:Tipo de relación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('115',1,'5',1);

	--Dato:Descripción de relación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('116',1,'5',1);

	--Dato:Fecha inicio relación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('117',1,'5',1);

--Area de Control
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'5',1);

	--Dato:Fecha fin relación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('118',1,'5',1);

	--Dato:Identificador de autoridad
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('119',1,'5',1);

	--Dato:Identificador institución
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('120',1,'5',1);

	--Dato:Reglas/Convenciones
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('121',1,'5',1);

	--Dato:Estado de elaboración
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('122',1,'5',1);

	--Dato:Nivel de detalle
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('123',1,'5',1);

	--Dato:Fecha creación registro autoridad
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('124',1,'5',1);

	--Dato:Lenguas y escrituras
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('127',1,'5',1);

	--Dato:Fuentes
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('128',1,'5',1);

	--Dato:Nota de mantenimiento
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('129',1,'5',1);

--Area Relación de instituciones, personas y familias, con documentos de archivo y otros recurso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'5',1);

	--Campo Tabla:Relaciones de entidades con otros recursos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('101',5,'5',1);

		--Dato:Título de recurso
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('130',1,'5',1);

		--Dato:Identificador de recurso
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('131',1,'5',1);

		--Dato:Típo de recurso
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('132',1,'5',1);

		--Dato:Naturaleza de la relación
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('133',1,'5',1);

		--Dato:Fecha de relación
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('134',1,'5',1);


--DESCRIPTORES
	--Descriptor:Municipio
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_CONCEJO',3,'5',1);

	--Descriptor:Geográfico
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_GEOGRAFICO',3,'5',1);

	--Descriptor:Población
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_POBLACION',3,'5',1);

--TABLAS DE VALIDACION
	--Tabla de Validación:Estado de elaboración
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_ESTADOELAB',2,'5',1);

	--Tabla de Validación:Lengua
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'5',1);

	--Tabla de Validación:Nivel de detalle
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_NDETALLE',2,'5',1);

	--Tabla de Validación:Tipo de relación de entidades
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_NRELACION',2,'5',1);

	--Tabla de Validación:Tipo de entidad
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_TIPOENTIDAD',2,'5',1);

	--Tabla de Validación:Tipo de recurso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_TIPORECURSO',2,'5',1);

	--Tabla de Validación:Tipo de relación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_TIPORELACION',2,'5',1);


/**************************************************************************************
 *	FICHA DE FRACCION DE SERIE*
 **************************************************************************************/
--Area de mención de la identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'6',1);

	--Dato:UDoc_Identificador
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',1,'6',1);

	--Campo Tabla:Rangos de expedientes
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('102',5,'6',1);

		--Dato:Desde
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('201',1,'6',1);

		--Dato:Hasta
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('202',1,'6',1);

	--Campo Tabla:Emplazamientos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',5,'6',1);

		--Dato:Emplazamiento_País
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',1,'6',1);

		--Dato:Emplazamiento_Comunidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('39',1,'6',1);

		--Dato:Emplazamiento_Concejo
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('40',1,'6',1);

		--Dato:Emplazamiento_Población
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('41',1,'6',1);

		--Dato:Emplazamiento_Localización
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('42',1,'6',1);

		--Dato:Emplazamiento_Validado
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('212',1,'6',1);

		--Dato:Fecha Inicial
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',1,'6',1);

		--Dato:Fecha Final
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',1,'6',1);

		--Campo Tabla:Volumen y Soporte
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',5,'6',1);

		--Dato:Soporte
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',1,'6',1);

		--Dato:Formato
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',1,'6',1);

		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',1,'6',1);

	--Campo Tabla:Interesados
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',5,'6',1);

		--Dato:Interesado_Identidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',1,'6',1);

		--Dato:Interesado_NumIdentidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',1,'6',1);

		--Dato:Interesado_Rol
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',1,'6',1);

		--Dato:Interesado_Validado
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',1,'6',1);

		--Dato:Interesado_IdTercero
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('51',1,'6',1);

		--Dato:Nombre Sistema Productor
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('14',1,'6',1);

		--Dato:Publicación SAC
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('15',1,'6',1);

--Area de contexto
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'6',1);

	--Dato:Productor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('16',1,'6',1);

	--Dato:Historia Institucional
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('17',1,'6',1);

	--Dato:Historia Archivística
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('18',1,'6',1);

	--Dato:Ingreso por
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('203',1,'6',1);

	--Dato:Código de transferencia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('204',1,'6',1);

	--Dato:Referencias de ingreso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('205',1,'6',1);

	--Dato:Hoja de Entrega
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('206',1,'6',1);

--Area de alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'6',1);

	--Dato:Descripción Contenido
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',1,'6',1);

	--Campo Tabla:Documentos Físicos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',5,'6',1);

	--Dato:DocumentoF_Nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('19',1,'6',1);

	--Dato:DocumentoF_Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('43',1,'6',1);

    --Campo Tabla:Documentos Electrónicos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',5,'6',1);

	--Dato:DocumentoE_Nombre
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('49',1,'6',1);

	--Dato:DocumentoE_Descripcion
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('50',1,'6',1);

	--Dato:Valoración_FechaEliminación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('20',1,'6',1);

	--Dato:Nuevos ingresos
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('21',1,'6',1);

--Area de condiciones de acceso y seguridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'6',1);

	--Dato:Condiciones de Acceso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('22',1,'6',1);

	--Dato:Condiciones de Reproducción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('23',1,'6',1);

	--Dato:Lenguas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('24',1,'6',1);

	--Dato:Características físicas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('25',1,'6',1);

	--Dato:Instrumentos de Descripción
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('26',1,'6',1);

--Area de documentación asociada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'6',1);

	--Dato:Originales_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('27',1,'6',1);

	--Dato:Originales_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('28',1,'6',1);

	--Dato:Copias_Existen
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('29',1,'6',1);

	--Dato:Copias_Descripción/Ubicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('30',1,'6',1);

	--Dato:Unidades Descripción Relacionadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('31',1,'6',1);

	--Dato:Nota Publicación
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('32',1,'6',1);

--Area de notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'6',1);

	--Dato:Notas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('35',1,'6',1);


--Area de control de la descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'6',1);

	--Dato:Normas utilizadas
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('33',1,'6',1);

--Area de descriptores
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'6',1);

	--Dato:Descriptor
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('44',1,'6',1);


--DESCRIPTORES

	--Descriptor:Provincia
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_PROVINCIA',3,'6',1);

	--Descriptor:Municipio
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_CONCEJO',3,'6',1);

	--Descriptor:Interesados
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_INTERESADO',3,'6',1);

	--Descriptor:País
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_PAIS',3,'6',1);

	--Descriptor:Población
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_POBLACION',3,'6',1);

	--Descriptor:Organización
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_BDORGANIZACION',3,'6',1);

	--Descriptor:Organo
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_ORGANO',3,'6',1);

	--Descriptor:Institución
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_LIST_INSTITUCION',3,'6',1);

--TABLAS DE VALIDACION

	--Tabla de Validación:Formato
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_FORMATO',2,'6',1);

	--Tabla de Validación:Formas de ingreso
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_INGRESOS',2,'6',1);

	--Tabla de Validación:Lengua
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_LENGUA',2,'6',1);

	--Tabla de Validación:Rol interesado
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_ROLES_INTERESADO',2,'6',1);

	--Tabla de Validación:Si/No
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SINO',2,'6',1);

	--Tabla de Validación:Soporte
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_SOPORTE',2,'6',1);

/********************************************
 * Asignar los campos a las tablas
 ********************************************/

--Area de mención de la identidad->UDoc_Identificador
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'1',2);

--Area de mención de la identidad->Emplazamiento_País
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'2',2);

--Area de mención de la identidad->Fecha Inicial
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'3',2);

--Area de mención de la identidad->Fecha Final
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'4',2);

--Area de mención de la identidad->Formato
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'5',2);

--Area de mención de la identidad->Soporte
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'6',2);

--Area de mención de la identidad->Cantidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'7',2);

--Area de mención de la identidad->Soporte Documental
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'8',2);

--Area de mención de la identidad->Interesado_Identidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'9',2);

--Area de mención de la identidad->Interesado_NumIdentidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'10',2);

--Area de mención de la identidad->Interesado_Rol
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'11',2);

--Area de mención de la identidad->Interesado_Validado
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'12',2);

--Area de mención de la identidad->Interesado_IdTercero
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'51',2);

--Area de alcance y contenido->Descripción Contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'13',2);

--Area de mención de la identidad->Nombre Sistema Productor
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'14',2);

--Area de mención de la identidad->Publicación SAC
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'15',2);

--Area de contexto->Productor
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'16',2);

--Area de contexto->Historia Institucional
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'17',2);

--Area de contexto->Historia Archivística
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'18',2);

--Area de alcance y contenido->DocumentoF_Nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'19',2);

--Area de alcance y contenido->Valoración_FechaEliminación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'20',2);

--Area de alcance y contenido->Nuevos ingresos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'21',2);

--Area de condiciones de acceso y seguridad->Condiciones de Acceso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'22',2);

--Area de condiciones de acceso y seguridad->Condiciones de Reproducción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'23',2);

--Area de condiciones de acceso y seguridad->Lenguas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'24',2);

--Area de condiciones de acceso y seguridad->Características físicas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'25',2);

--Area de condiciones de acceso y seguridad->Instrumentos de Descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('4',4,'26',2);

--Area de documentación asociada->Originales_Existen
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'27',2);

--Area de documentación asociada->Originales_Descripción/Ubicación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'28',2);

--Area de documentación asociada->Copias_Existen
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'29',2);

--Area de documentación asociada->Copias_Descripción/Ubicación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'30',2);

--Area de documentación asociada->Unidades Descripción Relacionadas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'31',2);

--Area de documentación asociada->Nota Publicación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('5',4,'32',2);

--Area de control de la descripción->Normas utilizadas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',4,'33',2);

--Area de contexto->ProductorSerie_Nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'34',2);

--Area de notas->Notas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('6',4,'35',2);

--Area de contexto->ProductorSerie_FechaIni
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'36',2);

--Area de contexto->ProductorSerie_FechaFin
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'37',2);

--Area de alcance y contenido->Alcance y contenido
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'38',2);

--Area de mención de la identidad->Emplazamiento_Comunidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'39',2);

--Area de mención de la identidad->Emplazamiento_Concejo
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'40',2);

--Area de mención de la identidad->Emplazamiento_Población
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'41',2);

--Area de mención de la identidad->Emplazamiento_Localización
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'42',2);

--Area de alcance y contenido->DocumentoF_Descripción
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'43',2);

--Area de descriptores->Descriptor
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('8',4,'44',2);

--Area de alcance y contenido->Valoración Serie
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'46',2);

--Area de alcance y contenido->Admite Nuevos Ingresos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'47',2);

--Area de alcance y contenido->Valoración en porcentaje
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'48',2);

--Area de alcance y contenido->DocumentoE_Nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'49',2);

--Area de alcance y contenido->DocumentoE_Descripcion
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'50',2);

--Area de Identificación->Tipo de entidad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'100',2);

--Area de Identificación->Forma autorizada de nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'101',2);

--Area de Identificación->Formas paralelas de nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'102',2);

--Area de Identificación->Formas normalizadas de nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'103',2);

--Area de Identificación->Otras formas de nombre
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'104',2);

--Area de Identificación->Identificadores para instituciones
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('9',4,'105',2);

--Area de Descripción->Fechas de existencia
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'106',2);

--Area de Descripción->Historia
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'107',2);

--Area de Descripción->Lugar
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'108',2);

--Area de Descripción->Estatuto jurídico
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'109',2);

--Area de Descripción->Funciones, ocupaciones
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'110',2);

--Area de Descripción->Atribuciones/Fuentes Legales
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'111',2);

--Area de Descripción->Estructura interna/Genealogía
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'112',2);

--Area de Descripción->Contexto General
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'113',2);

--Area de Relaciones->Nombre entidad relacionada
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'114',2);

--Area de Relaciones->Tipo de relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'115',2);

--Area de Relaciones->Descripción de relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'116',2);

--Area de Relaciones->Fecha inicio relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'117',2);

--Area de Relaciones->Fecha fin relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'118',2);

--Area de Control->Identificador de autoridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'119',2);

--Area de Control->Identificador institución
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'120',2);

--Area de Control->Reglas/Convenciones
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'121',2);

--Area de Control->Estado de elaboración
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'122',2);

--Area de Control->Nivel de detalle
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'123',2);

--Area de Control->Fecha Creación Registro Autoridad
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'124',2);

--Area de Control->Lenguas y escrituras
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'127',2);

--Area de Control->Fuentes
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'128',2);

--Area de Control->Nota de mantenimiento
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('12',4,'129',2);

--Relación de instituciones, personas y familias, con documentos de archivo y otros recursos->Título de recurso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'130',2);

--Relación de instituciones, personas y familias, con documentos de archivo y otros recursos->Identificador de recurso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'131',2);

--Relación de instituciones, personas y familias, con documentos de archivo y otros recursos->Tipo de recurso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'132',2);

--Relación de instituciones, personas y familias, con documentos de archivo y otros recursos->Naturaleza de la relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'133',2);

--Relación de instituciones, personas y familias, con documentos de archivo y otros recursos->Fecha de relación
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('13',4,'134',2);

--Area de mención de la identidad->Rango de expedientes: Desde
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'201',2);

--Area de mención de la identidad->Rango de expedientes: Hasta
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'202',2);

--Area de contexto->Ingreso por
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'203',2);

--Area de contexto->Código de transferencia
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'204',2);

--Area de contexto->Referencias de ingreso
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'205',2);

--Area de contexto->Hoja de Entrega
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'206',2);

--Area de mención de la identidad->Rango de expedientes: Desde Normalizado
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'210',2);

--Area de mención de la identidad->Rango de expedientes: Hasta Normalizado
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'211',2);

--Area de mención de la identidad->Emplazamiento_Validado
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'212',2);

/********************************************
 * Asignar las tablas a las areas
 ********************************************/

--Area de mención de la identidad->Tabla Volumen y Soporte
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'1',2);

--Area de mención de la identidad->Tabla Interesados
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'2',2);

--Area de mención de la identidad->Tabla Emplazamientos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'3',2);

--Area de mención de la identidad->Tabla Rangos de Expedientes
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'102',2);

--Area de contexto->Tabla Productores de Serie
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'5',2);

--Area de alcance y contenido->Tabla Documentos Físicos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'4',2);

--Area de alcance y contenido->Tabla Documentos Electrónicos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'6',2);

--Area de Relaciones->Tabla Entidades Relacionadas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'100',2);

--Area de Relaciones->Tabla Relaciones de entidades con otros recursos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'101',2);


/******************************************************************************************************/
/* Versión 2.5.1 */
/******************************************************************************************************/

-- Insertar la versión actual de bd
INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.5.1',getdate());
GO

-- Cambiar el tamaño a la columna titulo de unidades históricas
ALTER TABLE ASGFHISTUDOC ALTER COLUMN TITULOUDOC VARCHAR(1024);

-- Cambiar el tamaño a la columna titulo de unidades históricas
ALTER TABLE ASGFELIMSELUDOC ALTER COLUMN TITULOUDOC VARCHAR(1024);

-- Insertar el campo Interesado Principal
INSERT INTO ADCAMPODATO(ID,NOMBRE,TIPO,TIPONORMA,IDAREA,IDTBLPADRE,POSENTBL,ETIQUETAXML,DESCRIPCION) VALUES(
'213','Interesado: Principal',1,1,'1','2',6,'Principal',NULL);

--Dato:Interesado_Principal
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('213',1,'1',1);

--Area de mención de la identidad->Interesado_Principal
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'213',2);

--Nuevo campo en AGINFOSISTEMA con la clase utilizada para los informes itext en Navarra
--INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('MANEJADOR_ITEXT','common.reports.PdfReportPageEventHelperNavarra',getdate());

--Eliminar el Indice de la tabla ASGTMAPDESCRUDOC ya que tine nomenclatura incorrecta.
DROP INDEX ADASGTMAPDESCRUDOC1 ON ASGTMAPDESCRUDOC;

--Añadir el Indice.
CREATE UNIQUE INDEX ASGTMAPDESCRUDOC1 ON ASGTMAPDESCRUDOC (IDFICHA);

/***************************/
/* Versión 2.6             */
/***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.6',getdate());
	GO

	-- Crear una tabla para transferencias de unidades electrónicas
	CREATE TABLE dbo.ASGTUDOCSDF(
		ID VARCHAR(32) NOT NULL,
		IDRELENTREGA VARCHAR(32) NOT NULL,
		POSICION SMALLINT NOT NULL,
		ESTADOCOTEJO SMALLINT NOT NULL,
		NOTASCOTEJO VARCHAR(254),
		SIGNATURA VARCHAR(32),
		DESCRIPCION VARCHAR(254)
	);
	GO

	-- Crear los índices para la tabla de unidades electrónicas
	CREATE UNIQUE INDEX ASGTUDOCSDF1 ON ASGTUDOCSDF (ID);
	CREATE INDEX ASGTUDOCSDF2 ON ASGTUDOCSDF (IDRELENTREGA);
	GO

	-- Modificar índice sobre tabla de prórrogas para que no sea único
	DROP INDEX ASGPPRORROGA1 ON ASGPPRORROGA;
	GO

	CREATE INDEX ASGPPRORROGA1 ON ASGPPRORROGA (IDPRESTAMO,ESTADO);
	GO

	-- Insertar nuevo campo tabla para Volumen y Forma Documental
	INSERT INTO ADCAMPOTBL ( ID, NOMBRE, TIPONORMA, IDAREA, ETIQUETAXML, ETIQXMLFILA,
	DESCRIPCION ) VALUES ( '7', 'Volumen y Forma Documental', 1, '1', 'Volumenes_FormasDoc', 'Volumen_FormaDoc', NULL);
	GO

	-- Insertar nuevo campo cantidad para la tabla de Volúmenes y Formas Documentales
	INSERT INTO ADCAMPODATO ( ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML,
	DESCRIPCION ) VALUES ( '214', 'Volumen y Forma Documental: Cantidad', 4, 1, '1', '7', 1, 'Cantidad', NULL);
	GO

	-- Actualizar el nombre y el padre del campo 8 de la ficha de series, clasificadores de series y fondos
	UPDATE ADCAMPODATO SET NOMBRE = 'Volumen y Forma Documental: Soporte Documental', idtblpadre='7' WHERE ID = '8';
	GO

	-- Actualizar el idcampo del campo Cantidad a 214 para todos los elementos que la tenían excepto aquellos que
	-- tienen el nivel de unidad documental o fracción de serie
	UPDATE ADVCNUMCF SET IDCAMPO = '214' WHERE IDCAMPO = '7' AND IDELEMENTOCF NOT IN
	(
       SELECT ID FROM ASGFELEMENTOCF
		WHERE IDNIVEL IN ('00000000000000000000000000000009', '00000000000000000000000000000016')
	);
	GO

 /**************************************************************************************
 *	FICHA DE SERIE*
 **************************************************************************************/

	-- Insertar el uso de los nuevos campos en la ficha de series
	-- Campo Tabla:Volumen y Forma Documental
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',5,'2',1);
	GO

		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('214',1,'2',1);
		GO

	-- Eliminar el uso del campo 7 y tabla 1 en la ficha de series
	-- Uso de la tabla en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '1' AND TIPOOBJ=5 AND IDOBJUSUARIO='2' AND TIPOOBJUSUARIO=1;
	GO

	-- Uso del campo en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '7' AND TIPOOBJ=1 AND IDOBJUSUARIO='2' AND TIPOOBJUSUARIO=1;
	GO

	-- Tabla de Validación:Formas Documentales
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_FORMAS_DOCUMENTALES',2,'2',1);
	GO

/**************************************************************************************
 *	FICHA DE CLASIFICADOR
 **************************************************************************************/

	-- Insertar el uso de los nuevos campos en la ficha de clasificadores de series
	--Campo Tabla:Volumen y Forma Documental
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',5,'3',1);
	GO
		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('214',1,'3',1);
		GO

	-- Eliminar el uso del campo 7 y tabla 1 en la ficha de clasificadores de series
	-- Uso de la tabla en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '1' AND TIPOOBJ=5 AND IDOBJUSUARIO='3' AND TIPOOBJUSUARIO=1;
	GO

	-- Uso del campo en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '7' AND TIPOOBJ=1 AND IDOBJUSUARIO='3' AND TIPOOBJUSUARIO=1;
	GO

	-- Tabla de Validación:Formas Documentales
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_FORMAS_DOCUMENTALES',2,'3',1);
	GO

/**************************************************************************************
 *	FICHA DE FONDO*
 **************************************************************************************/

	--Campo Tabla:Volumen y Forma Documental
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('7',5,'4',1);
	GO
		--Dato:Cantidad
		INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('214',1,'4',1);
		GO

	-- Eliminar el uso del campo 7 y tabla 1 en la ficha de clasificadores de series
	-- Uso de la tabla en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '1' AND TIPOOBJ=5 AND IDOBJUSUARIO='4' AND TIPOOBJUSUARIO=1;
	GO

	-- Uso del campo en la ficha
	DELETE FROM ADUSOOBJETO WHERE IDOBJ = '7' AND TIPOOBJ=1 AND IDOBJUSUARIO='4' AND TIPOOBJUSUARIO=1;
	GO

	-- Tabla de Validación:Formas Documentales
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('ID_TBLVLD_FORMAS_DOCUMENTALES',2,'4',1);
	GO

/**************************************************************************************
 *	FIN FICHAS
 **************************************************************************************/

	-- Crear la tabla de unidades electrónicas en fondos
	CREATE TABLE dbo.ASGFUDOCSDF (
     IDUNIDADDOC     VARCHAR (32) NOT NULL,
     SIGNATURAUDOC   VARCHAR (32)  NOT NULL,
     IDUDOCRE        VARCHAR (32) NOT NULL,
     DESCRIPCION     VARCHAR (254),
     MARCASBLOQUEO INTEGER DEFAULT 0 NOT NULL
	);
	GO

	-- Crear los índices para la tabla de unidades electrónicas en fondos
	CREATE UNIQUE INDEX ASGFUDOCSDF1 ON ASGFUDOCSDF(IDUNIDADDOC);
	CREATE UNIQUE INDEX ASGFUDOCSDF2 ON ASGFUDOCSDF (IDUDOCRE);
	GO

	-- Crear un campo en la relación de entrega para indicar que no tiene documentos físicos
	ALTER TABLE ASGTRENTREGA ADD SINDOCSFISICOS CHAR (1);
	GO

	-- Insertar dos nuevos valores para la tabla de formatos
	INSERT INTO ADTEXTTBLVLD(ID, VALOR, IDTBLVLD)
	VALUES ('145', 'Expediente(s)', 'ID_TBLVLD_FORMATO');
	INSERT INTO ADTEXTTBLVLD(ID, VALOR, IDTBLVLD)
	VALUES ('146', 'Caja(s)', 'ID_TBLVLD_FORMATO');
	GO

	-- Actualizar los nombres para el resto de valores de la tabla de formatos
	UPDATE ADTEXTTBLVLD set VALOR = 'Carta(s)' WHERE ID = '112';
	UPDATE ADTEXTTBLVLD set VALOR = 'Documento(s)' WHERE ID = '113';
	UPDATE ADTEXTTBLVLD set VALOR = 'Fotografía(s)' WHERE ID = '114';
	GO

	-- Ampliados campos de justificación de Valoración de Serie a 1024
	ALTER TABLE ASGFVALSERIE ALTER COLUMN VALORCIENTJUST VARCHAR(1024);
	ALTER TABLE ASGFVALSERIE ALTER COLUMN VALORTECNJUST VARCHAR(1024);
	ALTER TABLE ASGFVALSERIE ALTER COLUMN VALORCULTJUST VARCHAR(1024);
	ALTER TABLE ASGFVALSERIE ALTER COLUMN REGIMENACCESOJUST VARCHAR(1024);
	GO

	-- Poner la tabla de longitud como tipo interno
	UPDATE ADCTLGTBLVLD SET USOINTERNO = 'S' WHERE ID = 'ID_TBLVLD_LONGITUD';
	GO

	-- Establecer la ficha descriptiva preferente de la lista de organización a la de la ficha ISAAR
	UPDATE ADCTLGLISTAD SET IDFICHADESCRPREF = 5 WHERE ID = 'ID_LIST_BDORGANIZACION';
	GO

/***************************/
/* Versión 2.7             */
/***************************/
	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.7',getdate());

	--Borrar Los Indices
	DROP INDEX ASGTUDOCSDF1 ON ASGTUDOCSDF;
	DROP INDEX ASGTUDOCSDF2 ON ASGTUDOCSDF;

	--Borrar la tabla de Documentos Electrónicos
	DROP TABLE ASGTUDOCSDF;

	--Crear la Tabla de Documentos Electrónicos
	CREATE TABLE dbo.ASGTUDOCSDF(
		ID VARCHAR(32) NOT NULL,
		IDRELENTREGA VARCHAR(32) NOT NULL,
		POSICION SMALLINT NOT NULL,
		ESTADOCOTEJO SMALLINT NOT NULL,
		NOTASCOTEJO VARCHAR(254),
	);

	--Crear Indices
	CREATE UNIQUE INDEX ASGTUDOCSDF1 ON ASGTUDOCSDF (ID);
	CREATE INDEX ASGTUDOCSDF2 ON ASGTUDOCSDF (IDRELENTREGA);


	--Borrar Los Indices
	DROP INDEX ASGFUDOCSDF1 ON ASGFUDOCSDF;
	DROP INDEX ASGFUDOCSDF2 ON ASGFUDOCSDF;

	--Borrar la tabla de ASGFUDOCSDF
	DROP TABLE ASGFUDOCSDF;

	--Crear Table ASGFUDOCSDF
	CREATE TABLE dbo.ASGFUDOCSDF (
		IDELEMENTOCF VARCHAR (32) NOT NULL,
		MARCASBLOQUEO SMALLINT DEFAULT 0 NOT NULL
	);

	--Crear el Índice Único por IDELEMENTOCF
	CREATE UNIQUE INDEX ASGFUDOCSDF1 ON ASGFUDOCSDF(IDELEMENTOCF);
	GO

	-- Actualizar el nivel de acceso de los descriptores que lo tienen de 0 a 1 (Había un error en la aplicación)
	UPDATE addescriptor SET nivelacceso = '1' WHERE nivelacceso = '0';
	GO

	-- Eliminar el índice único de ASGFORGPROD porque puede darse el caso de que se repita el mismo idOrgano con el mismo idInst y distinto idDescriptor
	DROP INDEX ASGFORGPROD2 ON ASGFORGPROD;
	GO

/***************************/
/* Versión 2.8             */
/***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.8',getdate());

	--Establecer el campo con NO NULO.
	ALTER TABLE ASGPCONSULTA ALTER COLUMN TEMA varchar(254) NULL;
	GO

	--Añadir el Campo SUBTIPO a la tabla de elementos del cuadro.
	ALTER TABLE ASGFELEMENTOCF ADD SUBTIPO SMALLINT NOT NULL DEFAULT 0;
	GO

	--Añadir el Campo SUBTIPO a la tabla de niveles de elementos del cuadro.
	ALTER TABLE ASGFNIVELCF ADD SUBTIPO SMALLINT NOT NULL DEFAULT 0;
	GO

	UPDATE ASGFELEMENTOCF SET SUBTIPO = 61 WHERE IDNIVEL = '00000000000000000000000000000009';
	GO

	UPDATE ASGFELEMENTOCF SET SUBTIPO = 62 WHERE IDNIVEL = '00000000000000000000000000000016';
	GO

	UPDATE ASGFNIVELCF SET SUBTIPO = 61 WHERE ID = '00000000000000000000000000000009'
	GO

	UPDATE ASGFNIVELCF SET SUBTIPO = 62 WHERE ID = '00000000000000000000000000000016'
	GO

	ALTER TABLE ASGFVALSERIE ADD REGIMENACCESOSUBTIPO smallint NULL;
	GO

	/************************************************************************************************************/
	/* Las sentencias a continuacion son para solucionar un problema detectado en las valoraciones				*/
	/* de serie. Por error en la version 2.5 se cambiaron los valores para los regimenes de acceso libre,		*/
	/* temporal y permanente.  En principio nos han asegurado que no se ha realizado ninguna valoracion;		*/
	/* pero aun asi puede presentarse ese caso, al ir a instalar la siguiente versión (2.8).					*/
	/*																											*/
	/* Se trata de cambiar los valores de las valoracion generadas con las versiones afectadas con el problema	*/
	/*	2.5 a anterior a 2.8 por los correctos																	*/
	/**********************************************************************************************************/
	/* cambiar  :								*/
	/* antes   libre=1 permanente=2 temporal=3  */
	/* despues libre=3 permanente=1 temporal=2  */
	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=100 WHERE REGIMENACCESOTIPO=1
	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=200 WHERE REGIMENACCESOTIPO=2
	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=300 WHERE REGIMENACCESOTIPO=3
	GO

	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=3 WHERE REGIMENACCESOTIPO=100
	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=1 WHERE REGIMENACCESOTIPO=200
	UPDATE ASGFVALSERIE SET REGIMENACCESOTIPO=2 WHERE REGIMENACCESOTIPO=300
	GO

	-- Crear un grupo para auditoría de administradores
	INSERT INTO ASCAGRUPO (ID, NOMBRE, IDARCHIVO, DESCRIPCION) VALUES ('1', 'GRUPO DE ADMINISTRADORES', NULL, 'Grupo de la aplicación para auditoría de administradores');
	GO

	-- Actualizar los datos del superusuario
	DELETE FROM ASCAUSRORG WHERE IDUSUARIO IN (SELECT ID FROM ASCAUSUARIO WHERE NOMBRE = 'SYSSUPERUSER');
	DELETE FROM ASCAUSRGRP WHERE IDUSUARIO IN (SELECT ID FROM ASCAUSUARIO WHERE NOMBRE = 'SYSSUPERUSER');
	DELETE FROM ASCAROLUSR WHERE IDUSUARIO IN (SELECT ID FROM ASCAUSUARIO WHERE NOMBRE = 'SYSSUPERUSER');
	UPDATE ASCAUSUARIO SET APELLIDOS=NULL, EMAIL=NULL, DIRECCION=NULL, TIPO='0', HABILITADO='S', FMAXVIGENCIA=NULL, IDUSRSEXTGESTOR=ID, IDUSRSISTORG=NULL, DESCRIPCION=NULL WHERE NOMBRE = 'SYSSUPERUSER';
	GO

	-- Eliminar de ASCAUSRORG la obligación de meter FECHAINI y FECHAFIN
	ALTER TABLE ASCAUSRORG ALTER COLUMN FECHAINI DATETIME NULL;
	GO

	ALTER TABLE ASCAUSRORG ALTER COLUMN FECHAFIN DATETIME NULL;
	GO