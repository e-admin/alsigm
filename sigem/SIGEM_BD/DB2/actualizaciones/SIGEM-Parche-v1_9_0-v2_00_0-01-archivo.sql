-- /***************************/
-- /* Versi�n 4.0             */
-- /***************************/

	-- Insertar la versi�n actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','4.0',CURRENT TIMESTAMP);

	-- A�adir fecha de creaci�n a la unidad de instalaci�n
	ALTER TABLE ASGDUINSTALACION ADD COLUMN FCREACION TIMESTAMP NOT NULL DEFAULT CURRENT TIMESTAMP;

	-- Crear la tabla de unidades de instalaci�n hist�ricas
	CREATE TABLE ASGDHISTUINSTALACION(
		ID VARCHAR(32) NOT NULL,
		IDARCHIVO VARCHAR(32) NOT NULL,
		IDFORMATO VARCHAR(32) NOT NULL,
		SIGNATURAUI	VARCHAR(16) NOT NULL,
		IDENTIFICACION VARCHAR(254)	NOT NULL,
		FELIMINACION TIMESTAMP NOT NULL,
		MOTIVO	SMALLINT NOT NULL
	);

	CREATE UNIQUE INDEX ASGDHISTUINST1 ON ASGDHISTUINSTALACION(ID);
	CREATE INDEX ASGDHISTUINST2 ON ASGDHISTUINSTALACION(SIGNATURAUI);
	CREATE INDEX ASGDHISTUINST3 ON ASGDHISTUINSTALACION(ID,FELIMINACION);
	CREATE INDEX ASGDHISTUINST4 ON ASGDHISTUINSTALACION(IDARCHIVO);

	-- A�adir la columna visibilidad a los motivos de consulta
	ALTER TABLE ASGPMTVCONSULTA ADD COLUMN VISIBILIDAD SMALLINT;

	--Si el tipo de entidad es 1 (Investigador), tiene visibilidad 3 (Ambos)
	UPDATE ASGPMTVCONSULTA SET VISIBILIDAD = 3 WHERE TIPOENTIDAD=1;

	-- Crear identificador para los motivos de consulta
	ALTER TABLE ASGPMTVCONSULTA ADD ID VARCHAR(32);

	-- Actualizar los registros antiguos con el nuevo ID de motivo de consulta
	UPDATE ASGPMTVCONSULTA SET ID = SUBSTR('u' || CAST (TIPOENTIDAD AS CHAR(1)) || CAST (TIPOCONSULTA AS CHAR(1)) || SUBSTR(MOTIVO,1,1) || '000000000000000000000000000000000',1,32);
	CREATE TABLE ASGPMTVCONSULTA2 (
    	ID 			VARCHAR(32) NOT NULL,
		TIPOENTIDAD SMALLINT NOT NULL,
		MOTIVO VARCHAR(254) NOT NULL,
		TIPOCONSULTA SMALLINT NOT NULL,
    	VISIBILIDAD SMALLINT) ;

    INSERT INTO ASGPMTVCONSULTA2 SELECT ID,TIPOENTIDAD,MOTIVO,TIPOCONSULTA,VISIBILIDAD FROM ASGPMTVCONSULTA;
	DROP TABLE ASGPMTVCONSULTA;
	RENAME TABLE ASGPMTVCONSULTA2 TO ASGPMTVCONSULTA;

	CREATE INDEX ASGPMTVCONSULTA1 ON ASGPMTVCONSULTA (TIPOCONSULTA, TIPOENTIDAD);
	CREATE UNIQUE INDEX ASGPMTVCONSULTA2 ON ASGPMTVCONSULTA(ID);

	-- A�adir a las consultas el identificador de motivo
	ALTER TABLE ASGPCONSULTA ADD COLUMN IDMOTIVO VARCHAR(32);

	-- Actualizar registros anteriores en consultas con el identificador del motivo que les corresponde
	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = ASGPCONSULTA.TIPO
	);

	-- La pantalla de consultas tiene un error y no actualiza los motivos cuando se
	-- cambia el usuario tramitador, deber�a hacerlo ya que los motivos dependen de
	-- si la consulta es directa (tramitador = usuario conectado) o no
	-- (tramitador != usuario conectado)
	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = 1
	) WHERE IDMOTIVO IS NULL AND TIPO=2;

	UPDATE ASGPCONSULTA SET IDMOTIVO = (
		SELECT ASGPMTVCONSULTA.ID FROM ASGPMTVCONSULTA ASGPMTVCONSULTA
		WHERE ASGPMTVCONSULTA.MOTIVO = ASGPCONSULTA.MOTIVO
		AND ASGPMTVCONSULTA.TIPOENTIDAD = ASGPCONSULTA.TIPOENTCONSULTORA
		AND ASGPMTVCONSULTA.TIPOCONSULTA = 2
	) WHERE IDMOTIVO IS NULL AND TIPO=1;

	-- Establecer el id de motivo como no nulo
	CREATE TABLE ASGPCONSULTA2 (
		ID VARCHAR(32) NOT NULL,
		ANO VARCHAR(4) NOT NULL,
		ORDEN INTEGER NOT NULL,
		TIPOENTCONSULTORA SMALLINT NOT NULL,
		NORGCONSULTOR VARCHAR(254),
		NUSRCONSULTOR VARCHAR(254) NOT NULL,
		FINICIALRESERVA TIMESTAMP,
		FENTREGA TIMESTAMP,
		FMAXFINCONSULTA TIMESTAMP,
		ESTADO SMALLINT NOT NULL,
		FESTADO TIMESTAMP NOT NULL,
		MOTIVO VARCHAR(254) NOT NULL,
		IDARCHIVO VARCHAR(32) NOT NULL,
		IDUSRSOLICITANTE VARCHAR(32) NOT NULL,
		INFORMACION VARCHAR(1024),
		TEMA VARCHAR(254),
		TIPO SMALLINT NOT NULL,
		TIPOENTREGA VARCHAR(254),
		DATOSAUTORIZADO VARCHAR(254),
		DATOSSOLICITANTE VARCHAR(512),
		OBSERVACIONES VARCHAR(254),
	    IDMOTIVO VARCHAR(32) NOT NULL) ;

	INSERT INTO ASGPCONSULTA2 SELECT ID,ANO,ORDEN,TIPOENTCONSULTORA,NORGCONSULTOR,NUSRCONSULTOR,FINICIALRESERVA,FENTREGA,FMAXFINCONSULTA,ESTADO,FESTADO,MOTIVO,IDARCHIVO,IDUSRSOLICITANTE,INFORMACION,TEMA,TIPO,TIPOENTREGA,DATOSAUTORIZADO,DATOSSOLICITANTE,OBSERVACIONES,IDMOTIVO FROM ASGPCONSULTA;
	DROP TABLE ASGPCONSULTA;
	RENAME TABLE ASGPCONSULTA2 TO ASGPCONSULTA;

	CREATE UNIQUE INDEX ASGPCONSULTA1 ON ASGPCONSULTA (ID);
	CREATE INDEX ASGPCONSULTA2 ON ASGPCONSULTA (NUSRCONSULTOR);
	CREATE INDEX ASGPCONSULTA3 ON ASGPCONSULTA (IDUSRSOLICITANTE);
	CREATE INDEX ASGPCONSULTA4 ON ASGPCONSULTA (ESTADO);

	-- Crear tabla de motivos de pr�stamo
	CREATE TABLE ASGPMTVPRESTAMO (
	  ID            VARCHAR(32) NOT NULL,
	  TIPOUSUARIO   SMALLINT    NOT NULL,
	  MOTIVO        VARCHAR (254)  NOT NULL,
	  VISIBILIDAD   SMALLINT);

	CREATE UNIQUE INDEX ASGPMTVPRESTAMO1 ON ASGPMTVPRESTAMO(ID);

	-- Insertar motivos de prestamo para poder actualizar registros anteriores
	INSERT INTO ASGPMTVPRESTAMO VALUES ('n0000000000000000000000000000001',1,'Motivo Interno',3);
	INSERT INTO ASGPMTVPRESTAMO VALUES ('n0000000000000000000000000000002',2,'Motivo Externo',3);

	-- A�adir a la tabla de pr�stamos el identificador del motivo
	ALTER TABLE ASGPPRESTAMO ADD COLUMN IDMOTIVO VARCHAR(32);

	-- Actualizar los pr�stamos con el identificador de motivo que corresponde en cada caso
	UPDATE ASGPPRESTAMO SET IDMOTIVO = 'n0000000000000000000000000000001' where IDUSRSOLICITANTE IS NOT NULL;
	UPDATE ASGPPRESTAMO SET IDMOTIVO = 'n0000000000000000000000000000002' where IDUSRSOLICITANTE IS NULL;

	-- Establecer el identificador de motivo en pr�stamos como no nulo
	CREATE TABLE ASGPPRESTAMO2 (
		ID VARCHAR(32) NOT NULL,
		ANO VARCHAR(4) NOT NULL,
		ORDEN INTEGER NOT NULL,
		NORGSOLICITANTE VARCHAR(254) NOT NULL,
		NUSRSOLICITANTE VARCHAR(254) NOT NULL,
		IDUSRSOLICITANTE VARCHAR(32),
		FINICIALRESERVA TIMESTAMP,
		IDORGSOLICITANTE VARCHAR(32),
		FENTREGA TIMESTAMP,
		FMAXFINPRESTAMO TIMESTAMP,
		ESTADO SMALLINT NOT NULL,
		FESTADO TIMESTAMP NOT NULL,
		IDARCHIVO VARCHAR(32) NOT NULL,
		IDUSRGESTOR VARCHAR(32) NOT NULL,
		NUMRECLAMACIONES INTEGER NOT NULL,
		FRECLAMACION1 TIMESTAMP,
		FRECLAMACION2 TIMESTAMP,
		TIPOENTREGA VARCHAR(254),
		DATOSAUTORIZADO VARCHAR(254),
		DATOSSOLICITANTE VARCHAR(512),
		OBSERVACIONES VARCHAR(254),
	  IDMOTIVO VARCHAR(32) NOT NULL);

	INSERT INTO ASGPPRESTAMO2 SELECT ID,ANO,ORDEN,NORGSOLICITANTE,NUSRSOLICITANTE,IDUSRSOLICITANTE,FINICIALRESERVA,IDORGSOLICITANTE,FENTREGA,FMAXFINPRESTAMO,ESTADO,FESTADO,IDARCHIVO,IDUSRGESTOR,NUMRECLAMACIONES,FRECLAMACION1,FRECLAMACION2,TIPOENTREGA,DATOSAUTORIZADO,DATOSSOLICITANTE,OBSERVACIONES,IDMOTIVO FROM ASGPPRESTAMO;
	DROP TABLE ASGPPRESTAMO;
	RENAME TABLE ASGPPRESTAMO2 TO ASGPPRESTAMO;

	CREATE UNIQUE INDEX ASGPPRESTAMO1 ON ASGPPRESTAMO (ID);
	CREATE INDEX ASGPPRESTAMO2 ON ASGPPRESTAMO (NUSRSOLICITANTE);
	CREATE INDEX ASGPPRESTAMO3 ON ASGPPRESTAMO (IDUSRSOLICITANTE);
	CREATE INDEX ASGPPRESTAMO4 ON ASGPPRESTAMO (ESTADO);

	-- A�adir identificador a los motivos de rechazo
	ALTER TABLE ASGPMTVRECHAZO ADD ID VARCHAR(32);

	-- Crear un identificador para los motivos de rechazo
	UPDATE ASGPMTVRECHAZO SET ID = SUBSTR('u' || CAST (TIPOSOLICITUD AS CHAR(1)) || SUBSTR(MOTIVO,1,1) || LTRIM(RTRIM(SUBSTR(MOTIVO,LENGTH(MOTIVO),LENGTH(MOTIVO)))) || '0000000000000000000000000000000000',1,32);

	CREATE TABLE ASGPMTVRECHAZO2 (
	    ID 			 VARCHAR(32) NOT NULL,
		TIPOSOLICITUD SMALLINT NOT NULL,
		MOTIVO VARCHAR(254) NOT NULL) ;

	INSERT INTO ASGPMTVRECHAZO2 SELECT ID,TIPOSOLICITUD,MOTIVO FROM ASGPMTVRECHAZO;
	DROP TABLE ASGPMTVRECHAZO;
	RENAME TABLE ASGPMTVRECHAZO2 TO ASGPMTVRECHAZO;

	CREATE INDEX ASGPMTVRECHAZO1 ON ASGPMTVRECHAZO (TIPOSOLICITUD);
	CREATE UNIQUE INDEX ASGPMTVRECHAZO2 ON ASGPMTVRECHAZO(ID);

	-- Crear en prorrogas un identificador de motivo
	ALTER TABLE ASGPPRORROGA ADD IDMOTIVO VARCHAR(32);

	UPDATE ASGPPRORROGA SET IDMOTIVO = (
		SELECT ASGPMTVRECHAZO.ID FROM ASGPMTVRECHAZO ASGPMTVRECHAZO
		WHERE ASGPMTVRECHAZO.MOTIVO = ASGPPRORROGA.MOTIVORECHAZO
		AND ASGPMTVRECHAZO.TIPOSOLICITUD = 3
	);

	-- A�adir identificador de motivo de rechazo a las solicitudes
	ALTER TABLE ASGPSOLICITUDUDOC ADD IDMOTIVO VARCHAR(32);

	-- Actualizar los motivos de rechazo de prestamos y consultas
	UPDATE ASGPSOLICITUDUDOC SET IDMOTIVO = (
		SELECT ASGPMTVRECHAZO.ID FROM ASGPMTVRECHAZO ASGPMTVRECHAZO
		WHERE ASGPMTVRECHAZO.MOTIVO = ASGPSOLICITUDUDOC.MOTIVORECHAZO
		AND ASGPMTVRECHAZO.TIPOSOLICITUD = ASGPSOLICITUDUDOC.TIPOSOLICITUD
	);