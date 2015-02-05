------------------------------------
-- ACTUALIZACION PARA EL REGISTRO --
------------------------------------

-- Tamanio de los campos al igual que estan en el tramite
ALTER TABLE sgmrtregistro ALTER COLUMN organo varchar(16) NOT NULL;

--------------------------------
-- ACTUALIZACION PARA EL PAGO --
--------------------------------

-- Modificar los registros de pagos manejados desde la consulta del registro telematico
-- para cambiar el identificador de las autoliquidaciones, por el correcto 
UPDATE sgmctpago
SET autoliquidacion_id='200'
WHERE autoliquidacion_id='100';