
DECLARE

id2 number;
stmt varchar (512);
BEGIN
execute immediate 'SELECT id from spac_ct_jerarquias where nombre=''' || 'Sesion-Propuesta' || '''' into id2;
EXECUTE IMMEDIATE 'CREATE OR REPLACE VIEW SPAC_CT_JERARQUIA_' || id2 || ' AS SELECT SPAC_EXPEDIENTES.ID as id, SPAC_EXPEDIENTES.ID as id_padre , SPAC_EXPEDIENTES.ID as id_hija FROM SPAC_EXPEDIENTES WHERE ID=1';

END;
/