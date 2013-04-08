/***************************/
/* Versión 2.8.2           */
/***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.8.2',getdate());

/***************************/
/* Versión 2.9             */
/***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.9',getdate());

	-- Actualizar los id's de textos de la tabla de validación de ingresos
	UPDATE ADTEXTTBLVLD SET ID=147 WHERE VALOR='Transferencia' AND IDTBLVLD='ID_TBLVLD_INGRESOS';
	UPDATE ADTEXTTBLVLD SET ID=148 WHERE VALOR='Donación' AND IDTBLVLD='ID_TBLVLD_INGRESOS';
	UPDATE ADTEXTTBLVLD SET ID=149 WHERE VALOR='Depósito' AND IDTBLVLD='ID_TBLVLD_INGRESOS';

	-- Añadir un linked server al servicio de index server 'Web', que debe tener definido como
	-- uno de sus directorios la dirección de red del repositorio electrónico (Ej: \\10.228.20.176\repfs)
	-- con la información de usuario y contraseña rellenas.
	--EXEC sp_addlinkedserver FileSystem,
	--	  'Index Server',
	--	  'MSIDXS',
	--	  'Web'

	-- Añadir el campo de id de ficha utilizado durante el alta/relación
	ALTER TABLE ASGTRENTREGA ADD IDFICHA VARCHAR(32);
	GO

	----------------------------------------------------------------------------------------
	-- Crear las tablas para almacenar los campos descriptivos de unidades en alta/relación
	----------------------------------------------------------------------------------------
	CREATE TABLE dbo.ADVCFECHAUDOCRE (
		IDUDOCRE varchar(32) NOT NULL,
		IDCAMPO varchar(32) NOT NULL,
		VALOR varchar(64),
		FECHAINI datetime,
		FECHAFIN datetime,
		FORMATO varchar(16),
		SEP varchar(1),
		CALIFICADOR varchar(32),
		ORDEN int NOT NULL
	) ;
	GO

	CREATE TABLE dbo.ADVCNUMUDOCRE (
		IDUDOCRE varchar(32) NOT NULL,
		IDCAMPO varchar(32) NOT NULL,
		VALOR decimal(8,2) NOT NULL,
		ORDEN int NOT NULL,
		TIPOMEDIDA int,
		UNIDADMEDIDA varchar(16)
	) ;
	GO

	CREATE TABLE dbo.ADVCREFUDOCRE (
		IDUDOCRE varchar(32) NOT NULL,
		IDCAMPO varchar(32) NOT NULL,
		TIPOOBJREF int NOT NULL,
		IDOBJREF varchar(32) NOT NULL,
		ORDEN int NOT NULL
	) ;
	GO

	CREATE TABLE dbo.ADVCTEXTLUDOCRE (
		IDUDOCRE varchar(32) NOT NULL,
		IDCAMPO varchar(32) NOT NULL,
		VALOR text NOT NULL,
		ORDEN int NOT NULL
	) ;
	GO

	CREATE TABLE dbo.ADVCTEXTUDOCRE (
		IDUDOCRE varchar(32) NOT NULL,
		IDCAMPO varchar(32) NOT NULL,
		VALOR varchar(254) NOT NULL,
		ORDEN int NOT NULL
	) ;
	GO

	---------------------------------------------------------------------------------------------
	-- Índices de las tablas para almacenar los campos descriptivos de unidades en alta/relación
	---------------------------------------------------------------------------------------------
	--Tabla: ADVCFECHAUDOCRE
	CREATE UNIQUE INDEX ADVCFECHAUDOCRE1 ON ADVCFECHAUDOCRE (IDUDOCRE ASC, IDCAMPO ASC, ORDEN ASC) ;
	CREATE INDEX ADVCFECHAUDOCRE2 ON ADVCFECHAUDOCRE (IDUDOCRE ASC) ;
	CREATE INDEX ADVCFECHAUDOCRE3 ON ADVCFECHAUDOCRE (IDCAMPO ASC, FECHAINI ASC) ;
	CREATE INDEX ADVCFECHAUDOCRE4 ON ADVCFECHAUDOCRE (IDCAMPO ASC, FECHAFIN ASC) ;
	CREATE INDEX ADVCFECHAUDOCRE5 ON ADVCFECHAUDOCRE (IDCAMPO ASC, FECHAINI ASC, FECHAFIN ASC, CALIFICADOR ASC) ;

	--Tabla: ADVCNUMUDOCRE
	CREATE UNIQUE INDEX ADVCNUMUDOCRE1 ON ADVCNUMUDOCRE (IDUDOCRE ASC, IDCAMPO ASC, ORDEN ASC) ;
	CREATE INDEX ADVCNUMUDOCRE2 ON ADVCNUMUDOCRE (IDUDOCRE ASC) ;
	CREATE INDEX ADVCNUMUDOCRE3 ON ADVCNUMUDOCRE (IDCAMPO ASC, VALOR ASC) ;

	--Tabla: ADVCREFUDOCRE
	CREATE UNIQUE INDEX ADVCREFUDOCRE1 ON ADVCREFUDOCRE (IDUDOCRE ASC, IDCAMPO ASC, ORDEN ASC) ;
	CREATE INDEX ADVCREFUDOCRE2 ON ADVCREFUDOCRE (IDUDOCRE ASC) ;
	CREATE INDEX ADVCREFUDOCRE3 ON ADVCREFUDOCRE (TIPOOBJREF ASC, IDOBJREF ASC) ;
	CREATE INDEX ADVCREFUDOCRE4 ON ADVCREFUDOCRE (IDCAMPO ASC, IDOBJREF ASC) ;

	--Tabla: ADVCTEXTLUDOCRE
	CREATE UNIQUE INDEX ADVCTEXTLUDOCRE1 ON ADVCTEXTLUDOCRE (IDUDOCRE ASC, IDCAMPO ASC, ORDEN ASC) ;
	CREATE INDEX ADVCTEXTLUDOCRE2 ON ADVCTEXTLUDOCRE (IDUDOCRE ASC) ;

	--Tabla: ADVCTEXTUDOCRE
	CREATE UNIQUE INDEX ADVCTEXTUDOCRE1 ON ADVCTEXTUDOCRE (ORDEN ASC, IDCAMPO ASC, IDUDOCRE ASC) ;
	CREATE INDEX ADVCTEXTUDOCRE2 ON ADVCTEXTUDOCRE (IDUDOCRE ASC) ;

	--Modificar los nombres de los formatos para añadir el tipo.
	UPDATE ADFMTFICHA SET NOMBRE = NOMBRE + ' - Público' WHERE NIVELACCESO= 1;
	UPDATE ADFMTFICHA SET NOMBRE = NOMBRE + ' - Archivo' WHERE NIVELACCESO= 2;

	GO


/***************************/
/* Versión 2.9.1           */
/***************************/

	-- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','2.9.1',getdate());

	-- Crear la tabla de divisiones de fracciones de serie
	CREATE TABLE dbo.ASGFDIVISIONFS (
	   IDFS VARCHAR(32) NOT NULL,
	   IDFICHA VARCHAR(32) NULL,
	   IDNIVELDOCUMENTAL VARCHAR(32) NOT NULL,
	   IDUSRGESTOR VARCHAR(32) NOT NULL,
	   ESTADO SMALLINT NOT NULL,
	   INFO TEXT,
	   FECHAESTADO datetime NOT NULL);
	GO

	-- Crear la tabla de unidades documentales en división de fracción de serie
	CREATE TABLE dbo.ASGFUDOCENDIVISIONFS (
	   IDFS 			VARCHAR(32) NOT NULL,
	   IDUDOC 			VARCHAR(32) NOT NULL,
	   NUMEXP           VARCHAR (128),
	   FECHAEXTINI      DATETIME NOT NULL,
  	   FECHAEXTFIN      DATETIME NOT NULL,
  	   ASUNTO           VARCHAR (1024) NOT NULL,
  	   ORDEN			INT NOT NULL,
  	   VALIDADA 		CHAR(1) NOT NULL,
  	   INFO             TEXT);
  	 GO

  	-- Crear índices únicos para las tablas anteriores
  	--Tabla: ASGFDIVISIONFS
	CREATE UNIQUE INDEX ASGFDIVISIONFS1 ON ASGFDIVISIONFS (IDFS);

	--Tabla: ASGFUDOCENDIVISIONFS
	CREATE UNIQUE INDEX ASGFUDOCENDIVISIONFS1 ON ASGFUDOCENDIVISIONFS (IDFS, IDUDOC);

	----------------------------------------------------------------------------------------
	-- Modificar las tablas de descripción de unidades documentales en relaciones para
	-- incluir el tipo de unidad documental que van a describir:
	--		1 -> unidad documental en relación de entrega
	--		2 -> unidad documental en proceso de división de fracción de serie
	----------------------------------------------------------------------------------------
	ALTER TABLE dbo.ADVCFECHAUDOCRE ADD TIPOUDOC SMALLINT NOT NULL DEFAULT 1;
	GO

	ALTER TABLE dbo.ADVCNUMUDOCRE ADD TIPOUDOC SMALLINT NOT NULL DEFAULT 1;
	GO

	ALTER TABLE dbo.ADVCREFUDOCRE ADD TIPOUDOC SMALLINT NOT NULL DEFAULT 1;
	GO

	ALTER TABLE dbo.ADVCTEXTLUDOCRE ADD TIPOUDOC SMALLINT NOT NULL DEFAULT 1;
	GO

	ALTER TABLE dbo.ADVCTEXTUDOCRE ADD TIPOUDOC SMALLINT NOT NULL DEFAULT 1;
	GO

	--Añadir Campo de Observaciones a la tabla de préstamos
	ALTER TABLE ASGPPRESTAMO ADD OBSERVACIONES VARCHAR(254);
	GO

	--Añadir Campo de Observaciones a la tabla de Consultas
	ALTER TABLE ASGPCONSULTA ADD OBSERVACIONES VARCHAR(254);
	GO

	-- Actualizar los descriptores con el tipo de su lista padre
	-- SELECT 'UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id=''' + ID + ''') WHERE IDLISTA=''' + ID + ''' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id=''' + ID + ''' );' from ADCTLGLISTAD
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_BDORGANIZACION') WHERE IDLISTA='ID_LIST_BDORGANIZACION' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_BDORGANIZACION' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CARGO') WHERE IDLISTA='ID_LIST_CARGO' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CARGO' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CARRETERA') WHERE IDLISTA='ID_LIST_CARRETERA' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CARRETERA' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_COMUNIDAD') WHERE IDLISTA='ID_LIST_COMUNIDAD' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_COMUNIDAD' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CONCEJO') WHERE IDLISTA='ID_LIST_CONCEJO' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_CONCEJO' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_FAMILIA') WHERE IDLISTA='ID_LIST_FAMILIA' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_FAMILIA' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_GEOGRAFICO') WHERE IDLISTA='ID_LIST_GEOGRAFICO' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_GEOGRAFICO' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_INSTITUCION') WHERE IDLISTA='ID_LIST_INSTITUCION' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_INSTITUCION' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_INTERESADO') WHERE IDLISTA='ID_LIST_INTERESADO' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_INTERESADO' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_MONTE') WHERE IDLISTA='ID_LIST_MONTE' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_MONTE' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_ORGANO') WHERE IDLISTA='ID_LIST_ORGANO' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_ORGANO' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PAIS') WHERE IDLISTA='ID_LIST_PAIS' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PAIS' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PERSONA') WHERE IDLISTA='ID_LIST_PERSONA' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PERSONA' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_POBLACION') WHERE IDLISTA='ID_LIST_POBLACION' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_POBLACION' );
	UPDATE ADDESCRIPTOR SET TIPO = (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PROVINCIA') WHERE IDLISTA='ID_LIST_PROVINCIA' AND TIPO NOT IN (SELECT TIPODESCRIPTOR FROM ADCTLGLISTAD WHERE id='ID_LIST_PROVINCIA' );

	--Actualización del Tipo de descriptor de la lista de Organización.
	UPDATE ADCTLGLISTAD SET TIPODESCRIPTOR=1 WHERE ID='ID_LIST_BDORGANIZACION';
	GO

	--Añadir Campo de ID de Unidad de Instalación ubicada a la tabla de unidad de instalacion en relación de entrega
	ALTER TABLE ASGTUINSTALACIONRE ADD IDUIUBICADA VARCHAR(32);
	GO


/***************************/
/* Versión 3.0             */
/***************************/
-- Insertar la versión actual de bd
INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','3.0',getdate());

-- Plazos de transferencia en valoraciones
CREATE TABLE dbo.ASGFPZTRVALSERIE(
	IDVALSERIE varchar(32) NOT NULL,
	PLAZO smallint  NOT NULL,
	IDNIVELARCHORG varchar(32) NOT NULL,
	IDNIVELARCHDST varchar(32) NOT NULL,
	ORDEN smallint NOT NULL,
);

--Insertar el Campo Tabla Fechas de Existencia
INSERT INTO ADCAMPOTBL (ID,NOMBRE,TIPONORMA,IDAREA,ETIQUETAXML,ETIQXMLFILA , DESCRIPCION)
VALUES('104','Fechas de Existencia',2,10,'Fechas_Existencia','Fecha_Existencia_Fila', null);

	--Eliminar el Campo que ya existía.
	DELETE FROM ADCAMPODATO WHERE ID='106';

	--Insertar el campo Dato Fecha Existencia Inicial
	INSERT INTO ADCAMPODATO(ID, NOMBRE, TIPO, TIPONORMA, IDAREA, IDTBLPADRE, POSENTBL, ETIQUETAXML, DESCRIPCION)
	VALUES ('106', 'Fecha Existencia Inicial', 3, 2, '10', '104', 1, 'Fecha_Existencia_Inicial', null);

	--Insertar el campo Dato Fecha Existencia Final
	INSERT INTO ADCAMPODATO(ID,NOMBRE,TIPO,TIPONORMA,IDAREA,ETIQUETAXML, IDTBLPADRE,POSENTBL, DESCRIPCION)
	VALUES('218','Fecha Existencia Final',3,2,10,'Fecha_Existencia_Final','104',2, null);


--ADUSOOBJETO

--AÑADIR CAMPOS A LA FICHA
--Tabla: Fechas Existencia
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('104',5,'5',1);

	--Dato:Fecha Existencia Inicial
	--INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('106',1,'5',1);

	--Dato:Fecha Existencia Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('218',1,'5',1);


--Tabla: Fechas Existencia
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'104',3);

	--Dato:Fecha Existencia Inicial
	--INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',4,'106',2);

	--Dato:Fecha Existencia Final
	INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('10',1,'218',2);



/********************************************
 * Asignar las tablas a las areas
 ********************************************/

--Area de mención de la identidad->Tabla Volumen y Soporte
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'1',3);

--Area de mención de la identidad->Tabla Interesados
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'2',3);

--Area de mención de la identidad->Tabla Emplazamientos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'3',3);

--Area de mención de la identidad->Tabla Rangos de Expedientes
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'102',3);

--Area de contexto->Tabla Productores de Serie
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('2',4,'5',3);

--Area de alcance y contenido->Tabla Documentos Físicos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'4',3);

--Area de alcance y contenido->Tabla Documentos Electrónicos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('3',4,'6',3);

--Area de Relaciones->Tabla Entidades Relacionadas
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'100',3);

--Area de Relaciones->Tabla Relaciones de entidades con otros recursos
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('11',4,'101',3);

--Area de mención de la identidad->Tabla Volumen y Forma Documental
INSERT INTO ADUSOOBJETO (IDOBJ,TIPOOBJ,IDOBJUSUARIO,TIPOOBJUSUARIO)VALUES('1',4,'7',3);


--Permitir código nulo
ALTER TABLE ASGFELEMENTOCF ALTER COLUMN CODIGO VARCHAR(128);

--Permitir código de referencia nulo.
ALTER TABLE ASGFELEMENTOCF ALTER COLUMN CODREFERENCIA VARCHAR(1024);

-- Crear una columna para guardar el tipo de signaturacion
ALTER TABLE AGARCHIVO ADD TIPOSIGNATURACION INT DEFAULT 1 NOT NULL;

-- Crear una columna en el hueco para almacenar la numeracion del hueco en el archivo
ALTER TABLE ASGDHUECO ADD NUMERACION VARCHAR (16);

-- Crear una tabla para almacenar el número del siguiente hueco a crear para un archivo
-- en concreto
CREATE TABLE dbo.ASGDSIGNUMHUECO (
  IDARCHIVO VARCHAR(32),
  SIGNUMHUECO  INT NOT NULL
  ) ;

-- Tabla: ASGDSIGNUMHUECO
CREATE UNIQUE INDEX ASGDSIGNUMHUECO1 ON ASGDSIGNUMHUECO(IDARCHIVO, SIGNUMHUECO);

-- Tabla: ADTEXTTBLVLD (para evitar duplicados sobre las listas de valores)
--Eliminar Previamente los valores duplicados.
INSERT INTO ADTEXTTBLVLD SELECT ID+'-NEW',VALOR,IDTBLVLD from ADTEXTTBLVLD GROUP BY ID,VALOR,IDTBLVLD HAVING COUNT(ID)>1;
DELETE FROM ADTEXTTBLVLD WHERE ID IN (SELECT ID FROM ADTEXTTBLVLD GROUP BY ID HAVING COUNT(ID)>1);
UPDATE ADTEXTTBLVLD set ID=REPLACE(ID,'-NEW','') WHERE ID LIKE '%-NEW';

CREATE UNIQUE INDEX ADTEXTTBLVLD2 ON ADTEXTTBLVLD (ID);

-- Nuevo campo para marcas para huecos cuyo valor dependera de la numeración del hueco
ALTER TABLE ASGDHUECO ADD MARCAS smallint NOT NULL DEFAULT 0;

-- Nuevo procedimiento de comprobacion de marcas
set ANSI_NULLS ON
set QUOTED_IDENTIFIER ON
GO

/************************************************************************************************************************************/
--	devuelve el digito en la posicion indicada por el parametro @iPosicion, del numero binario resultante de convertir
--  el numero en base decimal que recibe como primer parametro (@iNumeroBase)
--
-- Ejemplos de uso:
--	El Número 473 en binario es							111011001
--	PRINT dbo.devolverMarca(473,10)								0
--	PRINT dbo.devolverMarca(473,9)								0
--	PRINT dbo.devolverMarca(473,8)								1
--	PRINT dbo.devolverMarca(473,7)								1
--	PRINT dbo.devolverMarca(473,6)								1
--	PRINT dbo.devolverMarca(473,5)								0
--	PRINT dbo.devolverMarca(473,4)								1
--	PRINT dbo.devolverMarca(473,3)								1
--	PRINT dbo.devolverMarca(473,2)								0
--	PRINT dbo.devolverMarca(473,1)								0
--	PRINT dbo.devolverMarca(473,0)								1
--	PRINT dbo.devolverMarca(473,-1)								0
--	PRINT dbo.devolverMarca(473,-2)								0
--		...				...									   ...
--	PRINT dbo.devolverMarca(473,-10)							0
/************************************************************************************************************************************/
CREATE FUNCTION [dbo].[DEVOLVERMARCA]
(	@iNumeroBase10		INT,
	@iPosicion			INT)	--la posicion del primer caracter por la derecha es la 0
RETURNS BIT
AS
BEGIN
	DECLARE @vNumeros		VARCHAR(10)
	SELECT @vNumeros= '0123456789'

	DECLARE @vNuevoNumero	VARCHAR(120)
	SELECT @vNuevoNumero = ''


	-- Algoritmo generico de conversion de un numero en su equivalente en otra base:
	-- 1) Se divide el numero original (en base-10) entre la nueva base sucesivamente, hasta que el cociente de esta division (entera) sea 0
	--    (en ese momento el numero a dividir, sera mas pequeño que la nueva base).
	-- 2) Se compone el numero final convertido, digito a digito, a partir de los restos de cada division, desde la ultima a la primera, componiendo el numero de derecha a izquierda.
	--
	-- Ej pasar a binario 6 (base-10)
	--	6/2=3	6%2=0
	--	3/2=1	3%2=1
	--	1/2=0	1%2=1	--paramos de dividir
	--
	-- ahora componemos el numero final de derecha a izquierda, desde el ultimo resto al primero
	-- 1 1 0
	-- resultado final	110

	WHILE @iNumeroBase10 <> 0
	BEGIN
		SELECT @vNuevoNumero = SUBSTRING(@vNumeros, (@iNumeroBase10 % 2) + 1, 1) + @vNuevoNumero
		SELECT @iNumeroBase10 = @iNumeroBase10 / 2
	END --While

	IF SUBSTRING(@vNuevoNumero, LEN(@vNuevoNumero)-@iPosicion, 1) = '1' BEGIN RETURN 1 END

	RETURN 0
END --Procedure
GO

/*****************************/
/* Versión 3.0.1             */
/*****************************/

-- Insertar la versión actual de bd
INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','3.1',getdate());

DROP INDEX ASGDSIGNUMHUECO.ASGDSIGNUMHUECO1;
ALTER TABLE ASGDSIGNUMHUECO ALTER COLUMN IDARCHIVO VARCHAR(32) NOT NULL;
ALTER TABLE ASGDSIGNUMHUECO ALTER COLUMN SIGNUMHUECO BIGINT NOT NULL;
CREATE UNIQUE INDEX ASGDSIGNUMHUECO1 ON ASGDSIGNUMHUECO(IDARCHIVO, SIGNUMHUECO);
GO

/***************************/
/* Versión 3.1             */
/***************************/

-- Crear la tabla de revisión de documentación
CREATE TABLE dbo.ASGPREVDOCUDOC (
  IDREVDOC			VARCHAR (32)  NOT NULL,
  IDUDOC          		VARCHAR (32)  NOT NULL,
  TITULO          		VARCHAR (1024)  NOT NULL,
  SIGNATURAUDOC   	VARCHAR (254)  NOT NULL,
  EXPEDIENTEUDOC  	VARCHAR (128),
  ESTADO          		SMALLINT    NOT NULL,
  FESTADO         		DATETIME          NOT NULL,
  OBSERVACIONES        	VARCHAR (1024),
  MOTIVORECHAZO 	VARCHAR (254),
  IDUSRGESTOR        	VARCHAR (32) NOT NULL,
  IDALTA			VARCHAR (32)
);

-- Crear los índices necesarios en ASGPREVDOCUDOC
CREATE UNIQUE INDEX ASGPREVDOCUDOC1 ON ASGPREVDOCUDOC (IDREVDOC);
CREATE INDEX ASGPREVDOCUDOC2 ON ASGPREVDOCUDOC (IDUDOC);
CREATE INDEX ASGPREVDOCUDOC3 ON ASGPREVDOCUDOC (IDUDOC, ESTADO);

ALTER TABLE ASCAGRUPO ADD INFO VARCHAR(1024) DEFAULT '';
GO

--Actualización de la vesión anterior
DELETE FROM AGINFOSISTEMA WHERE NOMBRE='VERSIONBD' AND VALOR LIKE '3.1';

--Cambiar la columna numexp a tamaño 256
ALTER TABLE ASGTUNIDADDOCRE ALTER COLUMN NUMEXP VARCHAR (256);
ALTER TABLE ASGFUNIDADDOC ALTER COLUMN NUMEXP VARCHAR (256);
ALTER TABLE ASGFHISTUDOC ALTER COLUMN NUMEXPUDOC VARCHAR (256);
ALTER TABLE ASGFUDOCENDIVISIONFS ALTER COLUMN NUMEXP VARCHAR (256);

-- Insertar la versión actual de bd
INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','3.1',getdate());
GO

/**************************/
/* Versión 3.2            */
/**************************/

ALTER TABLE ASGFELEMENTOCF ADD ORDPOS VARCHAR(32);
GO

SET ANSI_NULLS ON
SET QUOTED_IDENTIFIER ON
GO

CREATE FUNCTION [dbo].[CALCULARFINALCODREFPADRE]
(	@vCodReferencia		VARCHAR(255),
	@vCodRefFondo		VARCHAR(255),
	@vCodigo			VARCHAR(255),
	@vFinalCodRefPadreActual	VARCHAR(255),
	@vDelimitador		VARCHAR(255))
RETURNS VARCHAR(255)
AS
BEGIN
	-- algoritmo: Ej
	-- vCodReferencia			ES/NA/AJRP/CF1/CF2/F1/CS1/CS2/S1/U1
	-- vCodRefFondo				ES/NA/AJRP/CF1/CF2/F1
	-- vCodigo					U1
	-- vFinalCodRefPadreActual	CF1/CF2/F2/CS1/CS2/S1/U1
	-- vDelimitador				/

	-- valor retornado:
	--	sin lanzar excepciones:		CS1/CS2/S1
	--	si algo fala:				CF1/CF2/F2/CS1/CS2/S1/U1

	--reemplazar en la cadena la parte del fondo+delimitador por la cadena vacia
	DECLARE @vCodRefSinCodRefFondo	VARCHAR(255)
	DECLARE @iNumCars INT
	DECLARE @vReturnValue VARCHAR(255)

	-- El codigo de referencia del fondo nunca puede llegar nulo, si llega nulo => error
	IF(LEN(@vCodRefFondo)=0) BEGIN
		RETURN @vFinalCodRefPadreActual
	END

	-- El codigo de referencia nunca puede llegar nulo, si llega nulo => error
	IF(LEN(@vCodReferencia)=0) BEGIN
		RETURN @vFinalCodRefPadreActual
	END

	-- Si el codigo de referencia del fondo no esta contenido ,en el codigo de referencia => error
	IF(CHARINDEX(@vCodRefFondo,@vCodReferencia)=0) BEGIN
		RETURN @vFinalCodRefPadreActual
	END

	-- Quitamos la parte con el codigo de referencia del fondo, del codigo de referencia
	SELECT @vCodRefSinCodRefFondo = REPLACE(@vCodReferencia, @vCodRefFondo + @vDelimitador,'')

	-- codigo vacio
	IF(LEN(@vCodigo)=0) BEGIN
		RETURN @vCodRefSinCodRefFondo
	END

	-- quitamos el codigo al final de la cadena y el caracter delimitador
	SELECT @iNumCars=LEN(@vCodRefSinCodRefFondo)-LEN(@vCodigo)
	SELECT @vReturnValue=null

	IF (@iNumCars>0) BEGIN
		SELECT @vReturnValue=SUBSTRING(@vCodRefSinCodRefFondo,1,@iNumCars-1)
	END

	RETURN @vReturnValue
END --Procedure
GO

CREATE FUNCTION [dbo].[GETNUMERICPOSITIVE]
(	@Source		VARCHAR(40))
RETURNS BIGINT
AS
BEGIN
	DECLARE @ret	BIGINT;
	SELECT @ret = -1
	IF ISNUMERIC(@Source)='1'
	BEGIN
		select @ret = CONVERT (BIGINT,@Source)
	END

	RETURN @ret
END
GO

-- Insertar la versión actual de bd
INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','3.2',getdate());
GO
