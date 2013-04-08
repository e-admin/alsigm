------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

--
-- Cambio del tipo de las columnas de fecha en las tablas que almacenan los hitos del expediente
--
ALTER TABLE sgm_cthitoestexp ALTER cfecha TYPE timestamp without time zone;
ALTER TABLE sgm_cthitohistexp ALTER cfecha TYPE timestamp without time zone;

-- Tamanio de los campos al igual que estan en el tramite
ALTER TABLE sgmrtregistro ALTER organo TYPE character varying(16);

--------------------------------
-- ACTUALIZACION PARA EL PAGO --
--------------------------------

-- Modificar los registros de pagos manejados desde la consulta del registro telematico
-- para cambiar el identificador de las autoliquidaciones, por el correcto 
UPDATE sgmctpago
SET autoliquidacion_id='200'
WHERE autoliquidacion_id='100';