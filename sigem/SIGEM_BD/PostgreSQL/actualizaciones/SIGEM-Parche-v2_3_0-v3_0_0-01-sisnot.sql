ALTER TABLE SN_PROCEDIMIENTO ADD ALTA_ASUNTO_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD ALTA_MENSAJE_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD ALTA_MENSAJE_SMS VARCHAR(160);
ALTER TABLE SN_PROCEDIMIENTO ADD BAJA_ASUNTO_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD BAJA_MENSAJE_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD BAJA_MENSAJE_SMS VARCHAR(160);
ALTER TABLE SN_PROCEDIMIENTO ADD MODIF_ASUNTO_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD MODIF_MENSAJE_EMAIL VARCHAR(4000);
ALTER TABLE SN_PROCEDIMIENTO ADD MODIF_MENSAJE_SMS VARCHAR(160);

ALTER TABLE SN_NOTIFICACION ADD ACUSE_RECIBO BYTEA;

UPDATE sn_tipo_correspondencia SET tipo_correspondencia='NOT1' WHERE tipo_correspondencia='NOTSIGEM';
UPDATE sn_tipo_correspondencia SET tipo_correspondencia='NOT1CIFR' WHERE tipo_correspondencia='COMSIGEM';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-4' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Subvención' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Subvención' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-4' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Subvención' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Subvención' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-4' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Subvención' WHERE cod_procedimiento='PCD-4';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Subvención' WHERE cod_procedimiento='PCD-4';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 013' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Solicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 013' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja olicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 013' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Solicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Solicitud de Tarjeta de Estacionamiento para Minúsvalidos' WHERE cod_procedimiento='013';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-3' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-3' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-3' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Solicitud de Reclamación, queja y sugerencias' WHERE cod_procedimiento='PCD-3';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-11' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-11' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-11' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Solicitud de Certificado Urbanístico' WHERE cod_procedimiento='PCD-11';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-15' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-15' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-15' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Solicitud de Acometida de Agua' WHERE cod_procedimiento='PCD-15';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-16' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Reclamación Tributos' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Reclamación Tributos' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-16' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Reclamación Tributos' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Reclamación Tributos' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-16' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Reclamación Tributos' WHERE cod_procedimiento='PCD-16';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Reclamación Tributos' WHERE cod_procedimiento='PCD-16';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 023' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Licencia de Vado' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Licencia de Vado' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 023' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Licencia de Vado' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Licencia de Vado' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 023' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Licencia de Vado' WHERE cod_procedimiento='023';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Licencia de Vado' WHERE cod_procedimiento='023';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-5' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-5' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-5' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Licencia de Obra Menor' WHERE cod_procedimiento='PCD-5';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 024' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 024' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 024' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='024';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 026' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Licencia de Apertura de Actividad No Clasificada' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 026' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Licencia de Apertura de Actividad Clasificada' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Licencia de Apertura de Actividad Clasificada' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 026' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Licencia de Apertura de Actividad Clasificada' WHERE cod_procedimiento='026';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Licencia de Apertura de Actividad Clasificada' WHERE cod_procedimiento='026';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 019' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Expediente Sancionador' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Expediente Sancionador' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 019' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Expediente Sancionador' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Expediente Sancionador' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 019' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Expediente Sancionador' WHERE cod_procedimiento='019';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Expediente Sancionador' WHERE cod_procedimiento='019';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento PCD-10' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Contrato Negociado' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Contrato Negociado' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento PCD-10' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Contrato Negociado' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Contrato Negociado' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento PCD-10' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Contrato Negociado' WHERE cod_procedimiento='PCD-10';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Contrato Negociado' WHERE cod_procedimiento='PCD-10';

UPDATE sn_procedimiento SET ALTA_ASUNTO_EMAIL='Alta procedimiento 025' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET ALTA_MENSAJE_EMAIL='Alta de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET ALTA_MENSAJE_SMS='Alta Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET BAJA_ASUNTO_EMAIL='Baja procedimiento 025' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET BAJA_MENSAJE_EMAIL='Baja de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET BAJA_MENSAJE_SMS='Baja Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET MODIF_ASUNTO_EMAIL='Modificación procedimiento 025' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET MODIF_MENSAJE_EMAIL='Modificación de suscripción a procedimiento Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';
UPDATE sn_procedimiento SET MODIF_MENSAJE_SMS='Modificación Cambio de Titularidad de Licencia de Apertura' WHERE cod_procedimiento='025';


