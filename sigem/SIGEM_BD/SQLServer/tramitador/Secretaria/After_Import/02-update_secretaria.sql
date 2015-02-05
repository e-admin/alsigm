

CREATE PROCEDURE createViewJerarquiaByName
	@name VARCHAR(255)  ,
	@id INT OUTPUT AS
	DECLARE @stmt		VARCHAR(512)
	SET @stmt='SELECT id from spac_ct_jerarquias where nombre=''' + @name + ''''
	execute sp_executesql @stmt,@id  output
	SET @stmt='CREATE OR REPLACE VIEW SPAC_CT_JERARQUIA_'+ @id +' AS SELECT SEC_PROPUESTA_SESION.ID AS id, SEC_SESION_PLENARIA.ID AS id_padre ,  SEC_PROPUESTA_SESION.ID_PROPUESTA AS id_hija FROM SEC_PROPUESTA_SESION , SEC_SESION_PLENARIA WHERE SEC_SESION_PLENARIA.NUMEXP=SEC_PROPUESTA_SESION.NUMEXP;'
	EXEC ( @stmt)
	RETURN @id;

---Jerarquía
---====================================================
exec  createViewJerarquiaByName 'Sesion-Propuesta', 1;