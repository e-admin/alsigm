-- /***************************/
-- /* Actualización 4.0->4.2             */
-- /***************************/

    -- Insertar la versión actual de bd
	INSERT INTO AGINFOSISTEMA (NOMBRE,VALOR,FECHAACTUALIZACION) VALUES ('VERSIONBD','4.0->4.2',now());

	-- Establecer el id de motivo en consultas como nulo
	ALTER TABLE ASGPCONSULTA ALTER COLUMN IDMOTIVO TYPE VARCHAR(32);
	ALTER TABLE ASGPCONSULTA ALTER COLUMN IDMOTIVO DROP NOT NULL;

	-- Establecer el id de motivo en consultas como nulo
	ALTER TABLE ASGPCONSULTA ALTER COLUMN MOTIVO TYPE VARCHAR(32);
	ALTER TABLE ASGPCONSULTA ALTER COLUMN MOTIVO DROP NOT NULL;

	-- Establecer el id de motivo en préstamos como nulo
	ALTER TABLE ASGPPRESTAMO ALTER COLUMN IDMOTIVO TYPE VARCHAR(32);
	ALTER TABLE ASGPPRESTAMO ALTER COLUMN IDMOTIVO DROP NOT NULL;