
INSERT INTO sn_procedimiento (cod_procedimiento, tipo_procedimiento, estado, descripcion, dir_url, fecha_inicio_vigencia, categoria, org_emisor) 
VALUES ('PCD-4', 'A', '1', 'Subsanacion', 'http://192.168.8.4:8080/portal', current_timestamp, 'Tramitacion', 'SIGEM');

UPDATE sn_sistema_emisor SET descripcion='Organismo Emisor SIGEM';
DELETE FROM sn_sistema_emisor WHERE cod_emisor='SISNOT';

UPDATE sn_tipo_correspondencia SET tipo_correspondencia='NOTSIGEM' WHERE encriptar='t';
UPDATE sn_tipo_correspondencia SET descripcion='CIFRADAS' WHERE encriptar='t';
UPDATE sn_tipo_correspondencia SET tipo_correspondencia='SINCIFRAR' WHERE tipo_correspondencia='COMSIGEM';

