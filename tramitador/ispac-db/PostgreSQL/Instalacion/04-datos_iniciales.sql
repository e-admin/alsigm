--
-- Entidades
--
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, fecha) VALUES (1, 1, 'SPAC_EXPEDIENTES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Datos del Expediente', 'SPAC_SQ_ID_EXPEDIENTES', now());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, fecha) VALUES (2, 1, 'SPAC_DT_DOCUMENTOS', 'ID', 'NUMEXP', 'NOMBRE', NULL, 'Datos del Documento', 'SPAC_SQ_ID_DTDOCUMENTOS', now());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, fecha) VALUES (3, 1, 'SPAC_DT_INTERVINIENTES', 'ID', 'NUMEXP', 'NOMBRE', NULL, 'Datos del Participante', 'SPAC_SQ_ID_DTINTERVINIENTES', now());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (4, 0, 'SPAC_AVISOS_ELECTRONICOS', 'ID_AVISO', 'ID_EXPEDIENTE', 'ID_EXPEDIENTE', NULL, 'Datos de Avisos Electrónicos', 'SPAC_SQ_ID_AVISOS_ELECTRONICOS');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (5, 0, 'SPAC_TRAMITACIONES_AGRUPADAS', 'ID', NULL, NULL, NULL, 'Datos de Tramitaciones agrupadas', 'SPAC_SQ_TRAMITACION_AGRUPADA');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (6, 0, 'SPAC_CT_ENTIDADES', 'ID', NULL, NULL, NULL, 'Catálogo de Entidades', 'SPAC_SQ_ID_CTENTIDADES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, fecha) VALUES (7, 1, 'SPAC_DT_TRAMITES', 'ID', 'NUMEXP', 'NOMBRE', NULL, 'Datos de Trámites', 'SPAC_SQ_ID_DTTRAMITES', now());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, fecha) VALUES (8, 1, 'SPAC_REGISTROS_ES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Registros de Entrada/Salida vinculados con un expediente', 'SPAC_SQ_ID_REGISTROSES', now());
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (14, 0, 'SPAC_CT_PROCEDIMIENTOS', 'ID', NULL, NULL, NULL, 'Catálogo de Procedimientos', '<SIN SECUENCIA>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (23, 0, 'SPAC_P_TRAMITES', 'ID', NULL, NULL, NULL, 'Tabla de Trámites por procedimiento', 'SPAC_SQ_ID_PTRAMITES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (39, 0, 'SPAC_CT_APLICACIONES', 'ID', NULL, NULL, NULL, 'Formularios de Entidades', 'SPAC_SQ_ID_CTAPLICACIONES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (40, 0, 'SPAC_CT_TPDOC', 'ID', NULL, NULL, NULL, 'Catálogo de Tipos de Documentos', 'SPAC_SQ_ID_CTTPDOC');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (41, 0, 'SPAC_EXP_RELACIONADOS', 'ID', NULL, NULL, NULL, 'Tabla que marca la relación entre expedientes', 'SPAC_SQ_ID_EXP_RELACIONADO');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (51, 0, 'SPAC_TRAMITES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Trámites de Procedimiento', 'SPAC_SQ_ID_TRAMITES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (52, 0, 'SPAC_FASES', 'ID', 'NUMEXP', 'NUMEXP', NULL, 'Fases de Procedimiento', 'SPAC_SQ_ID_FASES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (57, 0, 'SPAC_P_FASES', 'ID', 'NUMEXP', NULL, NULL, 'Fases por procedimiento', 'SPAC_SQ_ID_PFASES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (61, 0, 'SPAC_CT_PCDORG', 'ID', NULL, NULL, NULL, 'Productores de procedimientos', 'SPAC_SQ_ID_CTPCDORG');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (101, 0, 'SPAC_GESTION_APLICACIONES', 'ID', NULL, NULL, NULL, 'Tabla de Aplicaciones de Gestion', '<SIN SECUENCIA>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (106, 0, 'SPAC_VARS', 'ID', NULL, NULL, NULL, 'Variables globales del sistema', 'SPAC_SQ_ID_VARS');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (107, 0, 'SPAC_INFOTRAMITE', 'ID', 'NUMEXP', NULL, NULL, 'Información asociada a un trámite vivo', 'SPAC_SQ_ID_INFOTRAMITE');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (115, 0, 'SPAC_CT_ENTIDADES_RESOURCES', 'ID', NULL, NULL, NULL, 'Tabla de Recursos para los formularios', 'SPAC_SQ_ID_ENTIDADESRESOURCES');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (160, 0, 'SPAC_CALENDARIOS', 'ID', NULL, NULL, NULL, 'Tabla de Calendarios', '<SIN_SECUENCIA>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (161, 0, 'SPAC_V_AVISOS_ELECTRONICOS','ID_AVISO','ID_EXPEDIENTE','ID_EXPEDIENTE',null,'Datos de Avisos Electrónicos','<SIN_SENCUENCIA>');


-- Tablas de validación
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (104, 3, 'SPAC_TBL_001', 'ID', NULL, NULL, NULL, 'Tabla de validación para Unidades de Plazo', 'SPAC_SQ_SPAC_TBL_001', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (105, 3, 'SPAC_TBL_002', 'ID', NULL, NULL, NULL, 'Tabla de validación para Roles de Terceros', 'SPAC_SQ_SPAC_TBL_002', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>4</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (108, 2, 'SPAC_TBL_007', 'ID', NULL, NULL, NULL, 'Tabla de Validación de Tipos de Relaciones entre Expedientes', 'SPAC_SQ_SPAC_TBL_007', '<entity version=''1.00''><editable>S</editable><dropable>N</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>64</size></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size></field><field id=''4''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (109, 3, 'SPAC_TBL_003', 'ID', NULL, NULL, NULL, 'Tabla de validación para Formas de Terminación', 'SPAC_SQ_SPAC_TBL_003', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (110, 3, 'SPAC_TBL_004', 'ID', NULL, NULL, NULL, 'Tabla de validación para Estados Administrativos', 'SPAC_SQ_SPAC_TBL_004', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (111, 3, 'SPAC_TBL_005', 'ID', NULL, NULL, NULL, 'Tabla de validación para Tipos de Dirección de Notificación', 'SPAC_SQ_SPAC_TBL_005', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (112, 3, 'SPAC_TBL_006', 'ID', NULL, NULL, NULL, 'Tabla de validación para Estados de Notificación', 'SPAC_SQ_SPAC_TBL_006', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (113, 3, 'SPAC_VLDTBL_SIST_PRODUCTORES', 'ID', NULL, NULL, NULL, 'Tabla de validación para Sistemas Productores', 'SPAC_SQ_SPAC_VLDTBL_SIST_PROD', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (114, 3, 'SPAC_TBL_008', 'ID', NULL, NULL, NULL, 'Tabla de validación para Estados de Firma', 'SPAC_SQ_SPAC_TBL_008', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>8</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (136, 2, 'SPAC_TBL_009', 'ID', NULL, NULL, NULL, 'Tabla de validación para Valores SI/NO', 'SPAC_SQ_SPAC_TBL_009', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''4''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (185, 3, 'SPAC_VLDTBL_ACTS_FUNCS', 'ID', NULL, NULL, NULL, 'Tabla de validación para Actividad / Función', 'SPAC_SQ_SPAC_VLDTBL_ACTS_FUNCS', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (187, 3, 'SPAC_VLDTBL_MATS_COMP', 'ID', NULL, NULL, NULL, 'Tabla de validación para Materias / Competencia', 'SPAC_SQ_SPAC_VLDTBL_MATS_COMP', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (188, 3, 'SPAC_VLDTBL_FORMA_INIC', 'ID', NULL, NULL, NULL, 'Tabla de validación para Formas de Iniciación', 'SPAC_SQ_SPAC_VLDTBL_FORMA_INIC', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (189, 3, 'SPAC_VLDTBL_EFEC_SLNCIO', 'ID', NULL, NULL, NULL, 'Tabla de validación para Efecto del Silencio', 'SPAC_SQ_SPAC_VLDTBL_EFEC_SLNC', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>1</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (190, 3, 'SPAC_VLDTBL_RECURSOS', 'ID', NULL, NULL, NULL, 'Tabla de validación para Recursos', 'SPAC_SQ_SPAC_VLDTBL_RECURSOS', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (191, 3, 'SPAC_VLDTBL_SIT_TLM', 'ID', NULL, NULL, NULL, 'Tabla de validación para Situación Telemática', 'SPAC_SQ_SPAC_VLDTBL_SIT_TLM', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>2</size></field><field id=''3''><logicalName><![CDATA[sustituto]]></logicalName><physicalName>sustituto</physicalName><type>0</type><size>64</size></field><field id=''4''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (194, 2, 'SPAC_VLDTBL_TIPOREG', 'ID', NULL, NULL, NULL, 'Tipo de validación para Tipo de Registro', 'SPAC_SQ_SPAC_VLDTBL_TIPOREG', '<entity version=''1.00''><editable>S</editable><status>0</status><fields><field id=''1''><logicalName><![CDATA[id]]></logicalName><physicalName>id</physicalName><type>3</type></field><field id=''2''><logicalName><![CDATA[valor]]></logicalName><physicalName>valor</physicalName><type>0</type><size>10</size></field><field id=''3''><logicalName><![CDATA[vigente]]></logicalName><physicalName>vigente</physicalName><type>2</type><size>1</size></field><field id=''4''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (195, 2, 'SPAC_VLDTBL_TIPOS_DOCS', 'ID', NULL, NULL, NULL, 'Tabla de Validación para Tipos de Documentos', 'SPAC_SQ_SPAC_VLDTBL_TIPOS_DOCS', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>64</size></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size></field><field id=''4''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (196, 2, 'SPAC_VLDTBL_TIPOS_TRAM', 'ID', NULL, NULL, NULL, 'Tabla de Validación de Tipos de Trámites', 'SPAC_SQ_SPAC_VLDTBL_TIPOS_TRAM', '<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>32</size></field><field id=''3''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size></field><field id=''4''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable></field></fields></entity>');
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk, definicion) VALUES (197, 3, 'SPAC_VLDTBL_TIPO_NOTIF', 'ID', NULL, NULL, NULL, 'Tabla de validación para tipos de notificación', 'SPAC_SQ_SPAC_VLDTBL_TIPO_NOTIF','<entity version=''1.00''><editable>N</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Identificador inequívoco del valor]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>valor</physicalName><descripcion><![CDATA[Valor que se almacena en el campo validado]]></descripcion><type>0</type><size>2</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>sustituto</physicalName><descripcion><![CDATA[Texto que se muestra en la lista de selección]]></descripcion><type>0</type><size>64</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>vigente</physicalName><descripcion><![CDATA[Indica si el valor está en vigencia para ser seleccionado]]></descripcion><type>2</type><size>1</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>orden</physicalName><descripcion><![CDATA[Indica el orden del valor]]></descripcion><type>2</type><size>10</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields></entity>');

SELECT pg_catalog.setval('spac_sq_id_ctentidades', 1000, true);

--
-- Formularios
--
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (1, 'Expediente', 'Expediente', 'ieci.tdw.ispac.ispacmgr.app.ExpedientEntityApp', '/forms/expedientForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''VALIDATION''><table>SPAC_TBL_003</table><relation type=''FLD''><primary-field>FORMATERMINACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_004</table><relation type=''FLD''><primary-field>ESTADOADM</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_005</table><relation type=''FLD''><primary-field>TIPODIRECCIONINTERESADO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_002</table><relation type=''FLD''><primary-field>ROLTITULAR</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 1, 'SPAC_EXPEDIENTES');
UPDATE spac_ct_entidades SET frm_edit = 1 WHERE id = 1;
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (2, 'Documento Trámite', 'Documento asociado a trámite', 'ieci.tdw.ispac.ispacmgr.app.DocumentTaskEntityApp', '/forms/taskForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''COMPOSITE''><table>SPAC_DT_TRAMITES</table><relation type=''FLD''><primary-field>ID_TRAMITE</primary-field><secondary-field>ID_TRAM_EXP</secondary-field></relation></entity><entity type=''VALIDATION'' primaryTable=''SPAC_DT_TRAMITES''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_006</table><relation type=''FLD''><primary-field>ESTADONOTIFICACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_008</table><relation type=''FLD''><primary-field>ESTADOFIRMA</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', NULL, 2, 'SPAC_DT_DOCUMENTOS');
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (3, 'Listado Intervinientes', 'Lista de intervinientes', 'ieci.tdw.ispac.ispaclib.app.GenericEntityListEntityApp', '/forms/thirdForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><list-order>NOMBRE</list-order><entity type=''VALIDATION''><table>SPAC_TBL_005</table><relation type=''FLD''><primary-field>TIPO_DIRECCION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_002</table><relation type=''FLD''><primary-field>ROL</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', '/ispac/digester/thirdsformatter.xml', 3, 'SPAC_DT_INTERVINIENTES');
UPDATE spac_ct_entidades SET frm_edit = 3 WHERE id = 3;
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (4, 'Trámite Documentos', 'Trámite con lista documentos asociados ', 'ieci.tdw.ispac.ispacmgr.app.TaskDocumentListEntityApp', '/forms/taskForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><entity type=''DETAIL''><table>SPAC_DT_DOCUMENTOS</table><relation type=''FLD''><primary-field>ID_TRAM_EXP</primary-field><secondary-field>ID_TRAMITE</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_001</table><relation type=''FLD''><primary-field>UPLAZO</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', '/ispac/digester/taskformatter.xml', 7, 'SPAC_DT_TRAMITES');
UPDATE spac_ct_entidades SET frm_edit = 4 WHERE id = 7;
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre)
	VALUES (5, 'Listado Documentos', 'Lista de documentos', 'ieci.tdw.ispac.ispacmgr.app.ListDocumentsEntityApp', '/forms/documentListForm.jsp', '<?xml version=''1.0'' encoding=''ISO-8859-1''?><parameters><list-order>NOMBRE</list-order><entity type=''VALIDATION''><table>SPAC_TBL_006</table><relation type=''FLD''><primary-field>ESTADONOTIFICACION</primary-field><secondary-field>VALOR</secondary-field></relation></entity><entity type=''VALIDATION''><table>SPAC_TBL_008</table><relation type=''FLD''><primary-field>ESTADOFIRMA</primary-field><secondary-field>VALOR</secondary-field></relation></entity></parameters>', '/ispac/digester/documentsformatter.xml', 2, 'SPAC_DT_DOCUMENTOS');
UPDATE spac_ct_entidades SET frm_edit = 5 WHERE id = 2;
INSERT INTO spac_ct_aplicaciones (id, nombre, descripcion, clase, pagina, parametros, formateador, ent_principal_id, ent_principal_nombre,xml_formateador)
	VALUES (6, 'Registros E/S', 'Registros E/S', 'ieci.tdw.ispac.ispacmgr.app.RegistroESEntityApp', '/forms/forms/registrosES.jsp', NULL, '/ispac/digester/documentsformatter.xml', 8, 'SPAC_REGISTROS_ES','<?xml version="1.0" encoding="ISO-8859-1"?><beanformatter>	<!-- Ponemos 2 veces la propiedad Nombre, una para mostrarla solo en html (media=''html'') y la otra para la exportacion (media= ''xml excel csv pdf'') --><propertyfmt type="BeanPropertySimpleFmt" property=''ID'' titleKey='''' fieldType=''ICON_LINK'' columnClass=''width10percent'' media=''html'' /><propertyfmt type="BeanPropertySimpleFmt"  property=''NREG'' titleKey=''SPAC_REGISTROS_ES:NREG'' fieldType=''LIST'' columnClass=''width20percent'' headerClass=''sortable'' sortable=''true'' /><propertyfmt type="BeanPropertyDateFmt" property=''FREG'' titleKey=''SPAC_REGISTROS_ES:FREG'' fieldType=''LIST'' columnClass=''width20percent'' format=''dd/MM/yyyy'' headerClass=''sortable'' sortable=''true'' /><propertyfmt type="BeanPropertySimpleFmt" property=''INTERESADO'' titleKey=''SPAC_REGISTROS_ES:INTERESADO'' fieldType=''LIST'' columnClass=''width40percent'' headerClass=''sortable'' sortable=''true'' /><propertyfmt type="BeanPropertySimpleFmt" property=''TP_REG'' titleKey=''SPAC_REGISTROS_ES:TP_REG'' fieldType=''LIST'' columnClass=''width10percent'' headerClass=''sortable'' sortable=''true'' /></beanformatter>');
UPDATE spac_ct_entidades SET frm_edit = 6 WHERE id = 8;
SELECT pg_catalog.setval('spac_sq_id_ctaplicaciones', 10, true);

--
-- Formularios de búsqueda
--
INSERT INTO spac_ct_frmbusqueda (id, descripcion, autor, fecha, tipo)
	VALUES (nextval('spac_sq_id_ctfrmbusqueda'), 'BÚSQUEDA', 'IECISA', current_timestamp, 1);
update spac_ct_frmbusqueda set frm_bsq='<?xml version=''1.0'' encoding=''ISO-8859-1''?><?xml-stylesheet type=''text/xsl'' href=''SearchForm.xsl''?><queryform displaywidth=''95%'' defaultSort=''2''><!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA--><entity type=''main'' identifier=''1''><!--INICIO CUERPO BUSQUEDA-->	<tablename>spac_expedientes</tablename>	<description>DATOS DEL EXPEDIENTE</description>	<field type=''query'' order=''01''>		<columnname>ID_PCD</columnname>		<description>search.form.procedimiento</description>		<datatype>integer</datatype>		<controltype size=''30''  maxlength=''30'' tipo=''validate'' table=''SPAC_P_PROCEDIMIENTOS'' field=''spac_expedientes:ID_PCD'' label=''NOMBRE''  value=''ID'' clause=''WHERE ESTADO IN (2,3) AND TIPO = 1'' orderBy=''NOMBRE''>text</controltype>	</field>	<field type=''query'' order=''04''>		<columnname>NUMEXP</columnname>		<description>search.form.numExpediente</description>		<datatype>string</datatype>		<operators>		 	<operator><name>&gt;</name></operator>			<operator><name>&lt;</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''05''>		<columnname>ASUNTO</columnname>		<description>search.form.asunto</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''06''>		<columnname>NREG</columnname>		<description>search.form.numRegistro</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>	<field type=''query'' order=''07''>		<columnname>IDENTIDADTITULAR</columnname>		<description>search.form.interesadoPrincipal</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype> 	</field>	<field type=''query'' order=''08''>		<columnname>NIFCIFTITULAR</columnname>		<description>search.form.ifInteresadoPrincipal</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>' where descripcion='BÚSQUEDA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<field type=''query'' order=''09''>	<columnname>FAPERTURA</columnname><description>search.form.fechaApertura</description><datatype>date</datatype> 		<controltype size=''22'' maxlength=''22''>text</controltype>	</field>	<field type=''query'' order=''10''>		<columnname>ESTADOADM</columnname>		<description>search.form.estadoAdm</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''20'' maxlength=''20'' tipo=''validate'' table=''SPAC_TBL_004'' field=''spac_expedientes:ESTADOADM'' label=''SUSTITUTO'' value=''VALOR'' orderBy=''VALOR''>text</controltype>	</field>	<field type=''query'' order=''11''>     		<columnname>HAYRECURSO</columnname>		<description>search.form.recurso</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''2'' maxlength=''2''>text</controltype>	</field>	<field type=''query'' order=''12''>		<columnname>CIUDAD</columnname>		<description>search.form.ciudad</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype>	</field>		<field type=''query'' order=''13''>		<columnname>DOMICILIO</columnname>		<description>search.form.domicilio</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype cols=''100'' rows=''5''>textarea</controltype>	</field><!--FIN CUERPO BUSQUEDA--></entity><!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA--><!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA--><entity type=''secondary'' identifier=''52''>	<tablename>spac_fases</tablename>	<bindfield>NUMEXP</bindfield>	<field type=''query'' order=''02''>		<columnname>ID_FASE</columnname>		<description>search.form.fases</description>		<datatype>stringList</datatype>        <binding>in (select ID FROM SPAC_P_FASES WHERE ID_CTFASE IN(@VALUES))</binding>		<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_FASES'' field=''spac_fases:ID_FASE'' label=''NOMBRE''  value=''id'' orderBy=''NOMBRE''>text</controltype>		</field></entity><entity type=''secondary'' identifier=''51''>		<tablename>spac_tramites</tablename>		<field type=''query'' order=''03''>			<columnname>ID_TRAMITE</columnname>	<description>search.form.tramites</description><datatype>stringList</datatype><binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>			<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_TRAMITES'' field=''spac_tramites:ID_TRAMITE'' label=''NOMBRE''  value=''id'' orderBy=''NOMBRE''>text</controltype>		</field>'  where descripcion='BÚSQUEDA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<bindfield>NUMEXP</bindfield></entity><!--FIN SEGUNDA ENTIDAD DE BUSQUEDA--><!--INICIO CUERPO RESULTADO--><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_FASES.ID'' readOnly=''false'' dataType=''string'' fieldType=''CHECKBOX'' fieldLink=''SPAC_FASES.ID'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NUMEXP'' readOnly=''false'' dataType=''string'' titleKey=''search.numExp'' fieldType=''LINK'' fieldLink=''SPAC_EXPEDIENTES.NUMEXP'' comparator=''ieci.tdw.ispac.ispacweb.comparators.NumexpComparator'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NREG'' readOnly=''false'' dataType=''string'' titleKey=''search.numRegistro'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NREG'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.interesado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.nifInteresado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.FAPERTURA'' readOnly=''false'' dataType=''date'' titleKey=''search.fechaApertura'' fieldType=''DATE'' fieldLink=''SPAC_EXPEDIENTES.FAPERTURA'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.ESTADOADM'' readOnly=''false'' dataType=''string'' titleKey=''search.estadoAdministrativo'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.ESTADOADM'' validatetable=''SPAC_TBL_004'' substitute=''SUSTITUTO'' showproperty=''SPAC_TBL_004.SUSTITUTO''  value=''VALOR''/><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.HAYRECURSO'' readOnly=''false'' dataType=''string'' titleKey=''search.recurrido'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.HAYRECURSO'' /><!--FIN CUERPO RESULTADO--><!--INICIO ACCIONES--><action title=''Cerrar expedientes'' path=''/closeProcess.do'' titleKey=''ispac.action.expedients.close'' /><action title=''Avanzar fases'' path=''/closeStage.do'' titleKey=''ispac.action.stages.next'' /><action title=''Retroceder fases'' path=''/redeployPreviousStage.do'' titleKey=''ispac.action.stages.return'' /><!--FIN ACCIONES--></queryform>'  where descripcion='BÚSQUEDA';

--
-- Formularios de busqueda de ejemplo para busqueda de tramites
--
INSERT INTO spac_ct_frmbusqueda (id, descripcion, autor, fecha, tipo)
	VALUES (nextval('spac_sq_id_ctfrmbusqueda'), 'BÚSQUEDA TRÁMITES ABIERTOS', 'IECISA', current_timestamp, 1);
update spac_ct_frmbusqueda set frm_bsq='<?xml version=''1.0'' encoding=''ISO-8859-1''?><?xml-stylesheet type=''text/xsl'' href=''SearchForm.xsl''?><queryform displaywidth=''95%'' defaultSort=''2'' responsability=''task''><!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA--><entity type=''main'' identifier=''1''><!--INICIO CUERPO BUSQUEDA-->	<tablename>spac_expedientes</tablename>	<description>DATOS DEL EXPEDIENTE</description>	<field type=''query'' order=''01''>		<columnname>ID_PCD</columnname>		<description>search.form.procedimiento</description>		<datatype>integer</datatype>		<controltype size=''30''  maxlength=''30'' tipo=''validate'' table=''SPAC_P_PROCEDIMIENTOS'' field=''spac_expedientes:ID_PCD'' label=''NOMBRE''  value=''ID'' clause=''WHERE ESTADO IN (2,3) AND TIPO = 1'' orderBy=''NOMBRE''>text</controltype>	</field>	<field type=''query'' order=''04''>		<columnname>NUMEXP</columnname>		<description>search.form.numExpediente</description>		<datatype>string</datatype>		<operators>		 	<operator><name>&gt;</name></operator>			<operator><name>&lt;</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''05''>		<columnname>ASUNTO</columnname>		<description>search.form.asunto</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''06''>		<columnname>NREG</columnname>		<description>search.form.numRegistro</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>	<field type=''query'' order=''07''>		<columnname>IDENTIDADTITULAR</columnname>		<description>search.form.interesadoPrincipal</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype> 	</field>	<field type=''query'' order=''08''>		<columnname>NIFCIFTITULAR</columnname>		<description>search.form.ifInteresadoPrincipal</description>		<datatype>string</datatype>		<operators>			' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>	<field type=''query'' order=''09''>		<columnname>FAPERTURA</columnname>		<description>search.form.fechaApertura</description>		<datatype>date</datatype> 		<controltype size=''22'' maxlength=''22''>text</controltype>	</field>	<field type=''query'' order=''10''>		<columnname>ESTADOADM</columnname>		<description>search.form.estadoAdm</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''20'' maxlength=''20'' tipo=''validate'' table=''SPAC_TBL_004'' field=''spac_expedientes:ESTADOADM'' label=''SUSTITUTO'' value=''VALOR'' orderBy=''VALOR''>text</controltype>	</field>	<field type=''query'' order=''11''>     		<columnname>HAYRECURSO</columnname>		<description>search.form.recurso</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''2'' maxlength=''2''>text</controltype>	</field>	<field type=''query'' order=''12''>		<columnname>CIUDAD</columnname>		<description>search.form.ciudad</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype>	</field>		<field type=''query'' order=''13''>		<columnname>DOMICILIO</columnname>		<description>search.form.domicilio</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>      <operator><name>Contiene(Index)</name></operator>		</operators>		<controltype cols=''100'' rows=''5''>textarea</controltype>	</field><!--FIN CUERPO BUSQUEDA-->' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '</entity><!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA--><!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA	obligatoria en la busqueda para que se relacione con un JOIN --><entity type=''required'' identifier=''51''>	<tablename>spac_tramites</tablename>	<bindfield>NUMEXP</bindfield>	<field type=''query'' order=''03''>		<columnname>ID_TRAMITE</columnname>		<description>search.form.tramites</description>		<datatype>stringList</datatype>		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>		<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_TRAMITES'' field=''spac_tramites:ID_TRAMITE'' label=''NOMBRE''  value=''id'' orderBy=''NOMBRE''>text</controltype>	</field></entity><!--FIN SEGUNDA ENTIDAD DE BUSQUEDA--><!--INICIO CUERPO RESULTADO--><!--propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_TRAMITES.ID'' readOnly=''false'' dataType=''string'' fieldType=''CHECKBOX'' fieldLink=''SPAC_TRAMITES.ID'' /--><!-- Necesario para obtener en la consulta la propiedad de SPAC_TRAMITES.ID que se necesita para generar el enlace del LINKPARAM --><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_TRAMITES.ID'' readOnly=''false'' dataType=''string'' fieldType=''NONE'' fieldLink=''SPAC_DT_TRAMITES.ID'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_TRAMITES.NOMBRE'' readOnly=''false'' dataType=''string'' titleKey=''search.task'' fieldType=''LINKPARAM'' fieldLink=''SPAC_TRAMITES.NOMBRE'' styleClass="tdlink" url=''showTask.do'' >	<linkParam paramId="taskId" property="SPAC_TRAMITES.ID"/> </propertyfmt>' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NUMEXP'' readOnly=''false'' dataType=''string'' titleKey=''search.numExp'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NUMEXP'' comparator=''ieci.tdw.ispac.ispacweb.comparators.NumexpComparator'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NREG'' readOnly=''false'' dataType=''string'' titleKey=''search.numRegistro'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NREG'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.interesado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.nifInteresado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.FAPERTURA'' readOnly=''false'' dataType=''date'' titleKey=''search.fechaApertura'' fieldType=''DATE'' fieldLink=''SPAC_EXPEDIENTES.FAPERTURA'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.ESTADOADM'' readOnly=''false'' dataType=''string'' titleKey=''search.estadoAdministrativo'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.ESTADOADM'' validatetable=''SPAC_TBL_004'' substitute=''SUSTITUTO'' showproperty=''SPAC_TBL_004.SUSTITUTO''  value=''VALOR''/><!--propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.HAYRECURSO'' ' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || 'readOnly=''false'' dataType=''string'' titleKey=''search.recurrido'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.HAYRECURSO'' /--><!--FIN CUERPO RESULTADO--><!--INICIO ACCIONES--><!--FIN ACCIONES--></queryform>' where descripcion='BÚSQUEDA TRÁMITES ABIERTOS' and autor='IECISA';

INSERT INTO spac_ct_frmbusqueda (id, descripcion, autor, fecha, tipo)
	VALUES (nextval('spac_sq_id_ctfrmbusqueda'), 'BÚSQUEDA TRÁMITES', 'IECISA', current_timestamp, 1);
update spac_ct_frmbusqueda set frm_bsq='<?xml version=''1.0'' encoding=''ISO-8859-1''?><?xml-stylesheet type=''text/xsl'' href=''SearchForm.xsl''?><queryform displaywidth=''95%'' defaultSort=''2''><!--INICIO ENTIDAD PRINCIPAL DE BUSQUEDA--><entity type=''main'' identifier=''1''><!--INICIO CUERPO BUSQUEDA-->	<tablename>spac_expedientes</tablename>	<description>DATOS DEL EXPEDIENTE</description>	<field type=''query'' order=''01''>		<columnname>ID_PCD</columnname>		<description>search.form.procedimiento</description>		<datatype>integer</datatype>		<controltype size=''30''  maxlength=''30'' tipo=''validate'' table=''SPAC_P_PROCEDIMIENTOS'' field=''spac_expedientes:ID_PCD'' label=''NOMBRE''  value=''ID'' clause=''WHERE ESTADO IN (2,3) AND TIPO = 1'' orderBy=''NOMBRE''>text</controltype>	</field>	<field type=''query'' order=''04''>		<columnname>NUMEXP</columnname>		<description>search.form.numExpediente</description>		<datatype>string</datatype>		<operators>		 	<operator><name>&gt;</name></operator>			<operator><name>&lt;</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''05''>		<columnname>ASUNTO</columnname>		<description>search.form.asunto</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''30'' maxlength=''30''>text</controltype>	</field>	<field type=''query'' order=''06''>		<columnname>NREG</columnname>		<description>search.form.numRegistro</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>	<field type=''query'' order=''07''>		<columnname>IDENTIDADTITULAR</columnname>		<description>search.form.interesadoPrincipal</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype> 	</field>	<field type=''query'' order=''08''>		<columnname>NIFCIFTITULAR</columnname>		' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<description>search.form.ifInteresadoPrincipal</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''16'' maxlength=''16''>text</controltype>	</field>	<field type=''query'' order=''09''>		<columnname>FAPERTURA</columnname>		<description>search.form.fechaApertura</description>		<datatype>date</datatype> 		<controltype size=''22'' maxlength=''22''>text</controltype>	</field>	<field type=''query'' order=''10''>		<columnname>ESTADOADM</columnname>		<description>search.form.estadoAdm</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''20'' maxlength=''20'' tipo=''validate'' table=''SPAC_TBL_004'' field=''spac_expedientes:ESTADOADM'' label=''SUSTITUTO'' value=''VALOR''  orderBy=''VALOR''>text</controltype>	</field>	<field type=''query'' order=''11''>     		' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<columnname>HAYRECURSO</columnname>		<description>search.form.recurso</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''2'' maxlength=''2''>text</controltype>	</field>	<field type=''query'' order=''12''>		<columnname>CIUDAD</columnname>		<description>search.form.ciudad</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator><name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>		</operators>		<controltype size=''50'' maxlength=''50''>text</controltype>	</field>		<field type=''query'' order=''13''>		<columnname>DOMICILIO</columnname>		<description>search.form.domicilio</description>		<datatype>string</datatype>		<operators>			<operator><name>=</name></operator>			<operator><name>&gt;</name></operator>			<operator><name>&gt;=</name></operator>			<operator><name>&lt;</name></operator>			<operator>' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || '<name>&lt;=</name></operator> 			<operator><name>Contiene(Like)</name></operator>      <operator><name>Contiene(Index)</name></operator>		</operators>		<controltype cols=''100'' rows=''5''>textarea</controltype>	</field><!--FIN CUERPO BUSQUEDA--></entity><!--FIN ENTIDAD PRINCIPAL DE BUSQUEDA--><!--INICIO SEGUNDA ENTIDAD DE BUSQUEDA	obligatoria en la busqueda para que se relacione con un JOIN --><entity type=''required'' identifier=''51''>	<tablename>spac_dt_tramites</tablename>	<bindfield>NUMEXP</bindfield>	<field type=''query'' order=''03''>		<columnname>ID_TRAM_PCD</columnname>		<description>search.form.tramites</description>		<datatype>stringList</datatype>		<binding>in (select ID FROM SPAC_P_TRAMITES WHERE ID_CTTRAMITE IN(@VALUES))</binding>		<controltype height=''75px'' tipo=''list'' table=''SPAC_CT_TRAMITES'' field=''spac_dt_tramites:ID_TRAM_PCD'' label=''NOMBRE''  value=''id'' orderBy=''NOMBRE''>text</controltype>	</field></entity><!--FIN SEGUNDA ENTIDAD DE BUSQUEDA--><!--INICIO CUERPO RESULTADO--><!--propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_DT_TRAMITES.ID_TRAM_EXP'' readOnly=''false'' dataType=''string'' fieldType=''CHECKBOX'' fieldLink=''SPAC_DT_TRAMITES.ID_TRAM_EXP'' /--><!-- Necesario para obtener en la consulta la propiedad de SPAC_DT_TRAMITES.ID_TRAM_EXP que se necesita para generar el enlace del LINKPARAM --><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_DT_TRAMITES.ID_TRAM_EXP'' readOnly=''false'' ' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || 'dataType=''string'' fieldType=''NONE'' fieldLink=''SPAC_DT_TRAMITES.ID_TRAM_EXP'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_DT_TRAMITES.NOMBRE'' readOnly=''false'' dataType=''string'' titleKey=''search.task'' fieldType=''LINKPARAM'' fieldLink=''SPAC_DT_TRAMITES.NOMBRE'' styleClass="tdlink" url=''showTask.do'' >	<linkParam paramId="taskId" property="SPAC_DT_TRAMITES.ID_TRAM_EXP"/> </propertyfmt><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_DT_TRAMITES.ESTADO'' readOnly=''false'' dataType=''string'' titleKey=''search.state'' fieldType=''TASK_STATE'' fieldLink=''SPAC_DT_TRAMITES.ESTADO'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NUMEXP'' readOnly=''false'' dataType=''string'' titleKey=''search.numExp'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NUMEXP'' comparator=''ieci.tdw.ispac.ispacweb.comparators.NumexpComparator'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NREG'' readOnly=''false'' dataType=''string'' titleKey=''search.numRegistro'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NREG'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.interesado'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.IDENTIDADTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' readOnly=''false'' dataType=''string'' titleKey=''search.nifInteresado'' ' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';
update spac_ct_frmbusqueda set frm_bsq = frm_bsq || 'fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.NIFCIFTITULAR'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.FAPERTURA'' readOnly=''false'' dataType=''date'' titleKey=''search.fechaApertura'' fieldType=''DATE'' fieldLink=''SPAC_EXPEDIENTES.FAPERTURA'' /><propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.ESTADOADM'' readOnly=''false'' dataType=''string'' titleKey=''search.estadoAdministrativo'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.ESTADOADM'' validatetable=''SPAC_TBL_004'' substitute=''SUSTITUTO'' showproperty=''SPAC_TBL_004.SUSTITUTO''  value=''VALOR''/><!--propertyfmt type=''BeanPropertySimpleFmt'' property=''SPAC_EXPEDIENTES.HAYRECURSO'' readOnly=''false'' dataType=''string'' titleKey=''search.recurrido'' fieldType=''LIST'' fieldLink=''SPAC_EXPEDIENTES.HAYRECURSO'' /--><!--FIN CUERPO RESULTADO--><!--INICIO ACCIONES--><!--FIN ACCIONES--></queryform>' where descripcion='BÚSQUEDA TRÁMITES' and autor='IECISA';

--
-- Reglas
--
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (1, 'TestPcdId', 'Comprueba si unifica el procedimiento actual que el pasado como parámetro en el contexto de la regla', 'ieci.tdw.ispac.api.rule.procedures.TestPcdId', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (8, 'DocsRectificationTagRule', 'Regla creada para ser llamada desde un tag en una plantilla. Retorna una cadena que contiene un listado de documentos a subsanar. ', 'ieci.tdw.ispac.api.rule.docs.tags.DocsRectificationTagRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (10, 'SetFechaCierreRule', 'Establece la fecha de cierre en el expediente', 'ieci.tdw.ispac.api.rule.procedures.SetFechaCierreRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (11, 'FechaActual', 'Calcula la fecha actual en formato de presentación', 'ieci.tdw.ispac.api.rule.docs.CurrentDateRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (13, 'PropertySubstituteRule', 'Obtiene el sustituto de código/valor obteniéndolo de una tabla de validación', 'ieci.tdw.ispac.api.rule.docs.tags.PropertySubstituteRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (14, 'OnSetInterestedRule', 'Creación de hito informativo al establecer Interesado principal', 'ieci.tdw.ispac.api.rule.entities.OnSetInterestedRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (15, 'ListadoMultiplesRegistros', 'Obtención de n campos de n registros', 'ieci.tdw.ispac.api.rule.docs.tags.ListadoMultiplesRegistrosRule', 1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (17, 'OnlyOneTaskInstanceRule','No se permite más de una instancia del trámite','ieci.tdw.ispac.api.rule.tasks.OnlyOneInstanceInitRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (18,'PropertyRegSubstitute', 'Obtiene el valor de un campo validado para el registro actual','ieci.tdw.ispac.api.rule.docs.tags.PropertyRegSubstituteRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (19,'PropertyReg', 'Obtiene el valor de un campo no validado para el registro actual','ieci.tdw.ispac.api.rule.docs.tags.PropertyRegRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (24,'GenerateAlertEndTaskPcdRespRule','Genera un aviso electrónico para el responsable del procedimiento informando de la finalización de un trámite','ieci.tdw.ispac.api.rule.tasks.GenerateAlertEndTaskPcdRespRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (25,'GenerateAlertEndTaskDelegateTramitadorRule','Genera un aviso electrónico para el usutario tramitador último que delego el trámite, informando de la finalización de un trámite','ieci.tdw.ispac.api.rule.tasks.GenerateAlertEndTaskDelegateTramitadorRule',1);
INSERT INTO spac_ct_reglas (id, nombre, descripcion, clase, tipo) VALUES (26,'GenerateAlertEndTaskInitTramitadorRule','Genera un aviso electrónico para el usuario tramitador que inició el trámite, informando de la finalización de un trámite','ieci.tdw.ispac.api.rule.tasks.GenerateAlertEndTaskInitTramitadorRule',1);


SELECT pg_catalog.setval('spac_sq_id_ctreglas', 100, true);

--
-- Tipos de documento
--
		-- SE PASAN AL SCRIPT DE PROTOTIPOS
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (1, 'Acuerdo Consejo de Gobierno', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (2, 'Admisión a trámite', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (3, 'Alegaciones', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (4, 'Archivo del expediente', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (5, 'Comunicación al interesado', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (6, 'Comunicación de apertura', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (7, 'Decreto de concesión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (9, 'Emisión oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (10, 'Emisión oficio no admisión', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (12, 'Notificación oficio de respuesta', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (13, 'Propuesta de resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (14, 'Remisión documentación', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'ENTRADA', NULL);
		--INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (16, 'Notificación resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'SALIDA', NULL);

INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (8, 'Informe', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (11, 'Notificación', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (15, 'Subsanación Solicitud', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-08 00:00:00', 'SALIDA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (17, 'Resolución', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', NULL, NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (18, 'Solicitud Registro', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (19, 'Justificante', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'ENTRADA', NULL);
INSERT INTO spac_ct_tpdoc (id, nombre, cod_tpdoc, tipo, autor, descripcion, fecha, tiporeg, observaciones) VALUES (20, 'Anexo a Solicitud', NULL, NULL, 'SYSSUPERUSER', NULL, '2007-10-09 00:00:00', 'ENTRADA', NULL);


SELECT pg_catalog.setval('spac_sq_id_cttpdoc', 21, true);

--
-- Trámites
--
		-- SE PASAN AL SCRIPT DE PROTOTIPOS
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (1, 'Admisión a trámite', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (2, 'Alegaciones', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (3, 'Archivo del expediente', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (4, 'Comunicación al interesado', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (5, 'Comunicación de apertura', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (6, 'Decreto de concesión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (8, 'Emisión oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (9, 'Emisión oficio no admisión', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (11, 'Notificación oficio de respuesta', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (12, 'Propuesta de resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (13, 'Remisión documentación', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (15, 'Acuerdo Consejo de Gobierno', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
		--INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (17, 'Notificación resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-09 00:00:00', 'SYSSUPERUSER');


INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (16, 'Resolución', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (7, 'Informe', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (10, 'Notificación', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');
INSERT INTO spac_ct_tramites (id, nombre, cod_tram, descripcion, tipo, ordenacion, observaciones, fcreacion, autor) VALUES (14, 'Subsanación Solicitud', NULL, NULL, NULL, NULL, NULL, '2007-10-08 00:00:00', 'SYSSUPERUSER');


SELECT pg_catalog.setval('spac_sq_id_cttramites', 17, true);

--
-- Procedimiento básico
--
INSERT INTO spac_ct_procedimientos (id, cod_pcd, nombre, id_padre, titulo, objeto, asunto, act_func, mtrs_comp, sit_tlm, url, interesado, tp_rel, org_rsltr, forma_inic, plz_resol, unid_plz, finicio, ffin, efec_silen, fin_via_admin, recursos, fcatalog, autor, estado, nversion, observaciones, lugar_present, cnds_ecnmcs, ingresos, f_cbr_pgo, infr_sanc, calendario, dscr_tram, normativa, cnd_particip, documentacion, grupos_delegacion, cod_sistema_productor, mapeo_rt) VALUES (1, 'PCD-1', 'Procedimiento Administrativo Básico', -1, 'Procedimiento Administrativo Básico', NULL, 'Procedimiento Administrativo Básico', NULL, NULL, NULL, NULL, NULL, 'INT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '04', NULL);

--
-- Fases
--
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (1, 'Fase Inicio', 1, NULL, now(), NULL, NULL, NULL);
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (2, 'Fase Instrucción', 2, NULL, now(), NULL, NULL, NULL);
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (3, 'Fase Terminación', 3, NULL, now(), NULL, NULL, NULL);
INSERT INTO spac_ct_fases (id, nombre, orden, cod_fase, falta, descripcion, observaciones, autor) VALUES (4, 'Fase Archivo', 4, NULL, now(), NULL, NULL, NULL);
SELECT pg_catalog.setval('spac_sq_id_ctfases', 5, true);

--
-- Trámites asociados a las fases
--
		-- SE PASAN AL SCRIPT DE PROTOTIPOS
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (1, 1, 1);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (2, 1, 5);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (4, 2, 2);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (6, 2, 12);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (7, 2, 15);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (9, 2, 8);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (10, 2, 11);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (11, 3, 3);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (12, 3, 4);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (13, 3, 6);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (14, 3, 9);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (16, 3, 13);
		--INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (18, 3, 17);


INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (3, 1, 14);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (8, 2, 7);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (5, 2, 10);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (15, 3, 10);
INSERT INTO spac_ct_fstr (id, id_fase, id_tramite) VALUES (17, 3, 16);


SELECT pg_catalog.setval('spac_sq_id_ctfstr', 19, true);

--
-- Tipos de documentos asociados a los trámites
--
		-- SE PASAN AL SCRIPT DE PROTOTIPOS
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (1, 1, 2);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (2, 2, 3);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (3, 4, 5);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (5, 5, 6);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (6, 6, 7);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (8, 8, 9);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (9, 12, 13);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (10, 13, 14);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (12, 15, 1);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (13, 11, 12);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (14, 9, 10);
		--INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (16, 17, 16);



INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (7, 7, 8);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (11, 14, 15);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (15, 10, 11);
INSERT INTO spac_ct_trtd (id, id_tramite, id_tpdoc) VALUES (17, 16, 17);


SELECT pg_catalog.setval('spac_sq_id_cttrtd', 18, true);


--
-- Variables de configuración
--
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ID_TASK_SOLICITUD_SUBSANACION', '14', NULL);
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ESTADOADM_DOC_COMPLETA', 'DC', NULL);
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ESTADOADM_DOC_INCOMPLETA', 'DI', NULL);
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ESTADO_ADM_INICIAL', 'PR', NULL);
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'DEFAULT_CALENDAR_ID', '1', 'Identificador del calendario por defecto para el calculo de plazos');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'ID_STAGE_ARCHIVO', '4', 'Identificador de la fase Archivo en el catálogo');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'LANGUAGES', 'es;gl;eu;ca', 'Idiomas soportados');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'STAMP_CONFIG', '<stamp width="4500" height="4000"><image width="250" height="100"><rectangle x="0" y="0" width="250" height="100" color="#000000"/><rectangle x="2" y="2" width="245" height="95" color="#0000FF"/><string x="20" y="25" color="#0000FF"><labels><label locale="ca">NOM DE L''ORGANITZACIO</label><label locale="eu">ERAKUNDEAREN IZENA</label><label locale="es">NOMBRE DE ORGANIZACIÓN</label><label locale="gl">NOME DE ORGANIZACIÓN</label></labels></string><string x="20" y="45" color="#0000FF"><labels><label locale="ca">REGISTRE DE ${TP_REG}</label><label locale="eu">ERREGISTROA ${TP_REG}</label><label locale="es">REGISTRO DE ${TP_REG}</label><label locale="gl">REXISTRO DE ${TP_REG}</label></labels></string><string x="20" y="65" color="#0000FF"><labels><label locale="ca">NUMERO: ${NREG}</label><label locale="eu">ZENBAKIA: ${NREG}</label><label locale="es">NÚMERO: ${NREG}</label><label locale="gl">NÚMERO: ${NREG}</label></labels></string><string x="20" y="85" color="#0000FF"><labels><label locale="ca">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label><label locale="eu">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label><label locale="es">FECHA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label><label locale="gl">DATA: ${FREG("dd/MM/yyyy HH:mm:ss")}</label></labels></string></image></stamp>', 'Configuración del sello de documentos');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'INBOX_WEB_COMPONENTS', '<?xml version="1.0" encoding="UTF-8"?><components><component id="sucesos" class="ieci.tdw.ispac.ispacmgr.components.worklist.NoticesComponent"/><!--<component id="fases" class="ieci.tdw.ispac.ispacmgr.components.worklist.StagesComponent"/>--><component id="fasesTree" class="ieci.tdw.ispac.ispacmgr.components.worklist.TreeStagesComponent"/><!--<component id="tramites" class="ieci.tdw.ispac.ispacmgr.components.worklist.TasksComponent"/>--><component id="tramitesTree" class="ieci.tdw.ispac.ispacmgr.components.worklist.TreeTasksComponent"/><!--<component id="tramitesCerrados" class="ieci.tdw.ispac.ispacmgr.components.worklist.ClosedTasksComponent"/>--><component id="tramitesCerradosTree" class="ieci.tdw.ispac.ispacmgr.components.worklist.TreeClosedTasksComponent"/></components>','Componentes de la bandeja de entrada');
INSERT INTO spac_vars (id, nombre, valor, descripcion) VALUES (nextval('spac_sq_id_vars'), 'INTRAY_AUTO_ARCHIVING', 'false','Autoarchivado de los registros distribuidos');
--SELECT pg_catalog.setval('spac_sq_id_vars', 8, true);

--
-- Procedimientos
--
INSERT INTO spac_p_procedimientos (id, id_pcd_bpm, nversion, nombre, estado, id_group, ts_crt, ts_upd) VALUES (1, 1, 1, 'Procedimiento Administrativo Básico', 2, 1, now(), now());
SELECT pg_catalog.setval('spac_sq_id_pprocedimientos', 2, true);

--
-- Nodos del procedimiento básico
--
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (1, 1, 1, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (2, 2, 1, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (3, 3, 1, 1, NULL);
INSERT INTO spac_p_nodos (id, id_nodo_bpm, id_pcd, tipo, g_info) VALUES (4, 4, 1, 1, NULL);
SELECT pg_catalog.setval('spac_sq_id_pnodos', 5, true);

--
-- Fases asociadas al procedimiento básico
--
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (1, 1, 1, 'Fase Inicio', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (2, 2, 1, 'Fase Instrucción', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (3, 3, 1, 'Fase Terminación', NULL, NULL);
INSERT INTO spac_p_fases (id, id_ctfase, id_pcd, nombre, id_resp, id_resp_sec) VALUES (4, 4, 1, 'Fase Archivo', NULL, NULL);
--SELECT pg_catalog.setval('spac_sq_id_pfases', 5, true);

--
-- Tipos de documentos asociados a las fases de un procedimiento
--
--INSERT INTO spac_p_fstd (id, id_fase, id_tpdoc) VALUES (1, 1, 242);
--SELECT pg_catalog.setval('spac_sq_id_pfstd',10, true);

--
-- Flujos del procedimiento básico
--
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (1, 1, 1, 1, 2);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (2, 2, 1, 2, 3);
INSERT INTO spac_p_flujos (id, id_flujo_bpm, id_pcd, id_origen, id_destino) VALUES (3, 3, 1, 3, 4);
SELECT pg_catalog.setval('spac_sq_id_pflujos', 4, true);

--
-- Trámites asociados a las fases del procedimiento básico
--
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (1, 1, 14, 1, 1, 'Subsanación Solicitud', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (2, 2, 7, 1, 2, 'Informe', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (3, 3, 10, 1, 3, 'Notificación', 0, NULL, NULL, null);
INSERT INTO spac_p_tramites (id, id_tramite_bpm, id_cttramite, id_pcd, id_fase, nombre, libre, id_resp, id_resp_sec, id_pcd_sub) VALUES (4, 4, 16, 1, 3, 'Resolución', 0, NULL, NULL, null);
SELECT pg_catalog.setval('spac_sq_id_ptramites', 5, true);

--
-- Plantillas de documentos
--
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (1, 2, '2007-10-08 18:46:19', 'Admisión a trámite', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (2, 6, '2007-10-08 18:48:10', 'Comunicación de apertura', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (3, 9, '2007-10-08 18:49:49', 'Emisión oficio de respuesta', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (4, 12, '2007-10-08 18:52:06', 'Notificación oficio de respuesta', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (6, 14, '2007-10-08 18:57:52', 'Remisión documentación', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (7, 5, '2007-10-08 18:59:48', 'Comunicación al interesado', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (8, 10, '2007-10-08 19:01:59', 'Emisión oficio no admisión', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (11, 13, '2007-10-09 09:32:51', 'Propuesta de resolución', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (12, 1, '2007-10-09 09:34:00', 'Acuerdo Consejo de Gobierno', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (13, 3, '2007-10-09 09:38:39', 'Alegaciones', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (18, 7, '2007-10-09 13:41:07', 'Decreto de concesión', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (26, 2, '2007-10-09 12:56:09', 'Admisión a trámite', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (27, 6, '2007-10-09 12:57:10', 'Comunicación de apertura', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (28, 9, '2007-10-09 12:58:42', 'Emisión oficio de respuesta', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (29, 12, '2007-10-09 12:59:54', 'Notificación oficio de respuesta', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (30, 14, '2007-10-09 13:01:26', 'Remisión documentación', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (31, 5, '2007-10-09 13:02:25', 'Comunicación al interesado', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (32, 10, '2007-10-09 13:12:19', 'Oficio de no admisión', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (33, 11, '2007-10-09 13:27:23', 'Notificación', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (34, 8, '2007-10-09 16:37:13', 'Emisión informe técnico', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (35, 8, '2007-10-09 16:37:44', 'Emisión informe jurídico', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (36, 13, '2007-10-09 16:41:28', 'Propuesta de resolución', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (37, 3, '2007-10-09 16:43:18', 'Alegaciones', '', NULL, NULL, NULL);
--INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (38, 16, '2007-10-09 16:51:22', 'Notificación resolución', '', NULL, NULL, NULL);


INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (5, 8, '2007-10-08 18:56:11', 'Informe', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (10, 15, '2007-10-09 09:31:50', 'Subsanación Solicitud', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (25, 11, '2007-10-09 12:54:34', 'Notificación', '', NULL, NULL, NULL);
INSERT INTO spac_p_plantdoc (id, id_tpdoc, fecha, nombre, expresion, mimetype, path, estacion) VALUES (39, 17, '2007-10-09 16:53:34', 'Resolución', '', NULL, NULL, NULL);

SELECT pg_catalog.setval('spac_sq_id_pplantillas', 40, true);

--
-- Eventos para el procedimiento básico
--
INSERT INTO spac_p_eventos (id_obj, tp_obj, evento, orden, id_regla) VALUES (1, 5, 12, 1, 14);

--
-- Entidades para el procedimiento básico
--
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (1, 1, 1, 0, 1, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (2, 7, 1, 1, 2, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (3, 3, 1, 1, 3, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (4, 2, 1, 1, 4, NULL);
INSERT INTO spac_p_entidades (id, id_ent, id_pcd, tp_rel, orden, frm_edit) VALUES (5, 8, 1, 1, 5, NULL);
SELECT pg_catalog.setval('spac_sq_id_pentidades', 6, true);

--
-- Formularios asociados a los trámites
--
INSERT INTO spac_p_frmtramites (id, id_ent, id_tramite, frm_edit) VALUES (1, 2, 0, 2);
SELECT pg_catalog.setval('spac_sq_id_pfrmtramites', 2, true);

--
-- Calendario 2007
--
INSERT INTO spac_calendarios (id, nombre, calendario) VALUES (nextval('spac_sq_id_calendarios'), 'Calendario', '<?xml version="1.0" encoding="ISO-8859-1"?>
<calendar>
	<name>Calendario para pruebas</name>
	<holydays>
	   <week>
	   		<day>sabado</day>
	   		<day>domingo</day>
	   </week>
	   <anual>
	   		<holyday>
	   			<name>Jueves Santo</name>
	   			<date>05/04/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Viernes Santo</name>
	   			<date>06/04/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>1º de Mayo</name>
	   			<date>01/05/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Fiesta Nacional</name>
	   			<date>15/08/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>El Pilar</name>
	   			<date>12/10/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Todos los santos</name>
	   			<date>01/11/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Día de la Constitución</name>
	   			<date>06/12/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Navidad</name>
	   			<date>25/12/2007</date>
	   		</holyday>
	   		<holyday>
	   			<name>Año Nuevo</name>
	   			<date>01/01/2008</date>
	   		</holyday>
	   </anual>
	</holydays>
</calendar>
');

--
-- Expediente
--
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_pcd</physicalName>
		<type>3</type>
	</field>
	<field id=''2''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
	<field id=''3''>
		<physicalName>referencia_interna</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
	<field id=''4''>
		<physicalName>nreg</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''5''>
		<physicalName>freg</physicalName>
		<type>6</type>
	</field>
	<field id=''6''>
		<physicalName>estadoinfo</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''7''>
		<physicalName>festado</physicalName>
		<type>6</type>
	</field>
	<field id=''8''>
		<physicalName>nifciftitular</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''9''>
		<physicalName>idtitular</physicalName>
		<type>3</type>
	</field>
	<field id=''10''>
		<physicalName>domicilio</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''11''>
		<physicalName>ciudad</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''12''>
		<physicalName>regionpais</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''13''>
		<physicalName>cpostal</physicalName>
		<type>0</type>
		<size>5</size>
	</field>
	<field id=''14''>
		<physicalName>identidadtitular</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''15''>
		<physicalName>tipopersona</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''16''>
		<physicalName>roltitular</physicalName>
		<type>0</type>
		<size>4</size>
	</field>
	<field id=''17''>
		<physicalName>asunto</physicalName>
		<type>0</type>
		<size>512</size>
	</field>
	<field id=''18''>
		<physicalName>finicioplazo</physicalName>
		<type>6</type>
	</field>
	<field id=''19''>
		<physicalName>poblacion</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''20''>
		<physicalName>municipio</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''21''>
		<physicalName>localizacion</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''22''>
		<physicalName>exprelacionados</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''23''>
		<physicalName>codprocedimiento</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''24''>
		<physicalName>nombreprocedimiento</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''25''>
		<physicalName>plazo</physicalName>
		<type>3</type>
	</field>
	<field id=''26''>
		<physicalName>uplazo</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''27''>
		<physicalName>formaterminacion</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''28''>
		<physicalName>utramitadora</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''29''>
		<physicalName>funcionactividad</physicalName>
		<type>0</type>
		<size>80</size>
	</field>
	<field id=''30''>
		<physicalName>materias</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''31''>
		<physicalName>servpresactuaciones</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''32''>
		<physicalName>tipodedocumental</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''33''>
		<physicalName>palabrasclave</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''34''>
		<physicalName>fcierre</physicalName>
		<type>6</type>
	</field>
	<field id=''35''>
		<physicalName>estadoadm</physicalName>
		<type>0</type>
		<size>128</size>
	</field>
	<field id=''36''>
		<physicalName>hayrecurso</physicalName>
		<type>0</type>
		<size>2</size>
	</field>
	<field id=''37''>
		<physicalName>efectosdelsilencio</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''38''>
		<physicalName>fapertura</physicalName>
		<type>6</type>
	</field>
	<field id=''39''>
		<physicalName>observaciones</physicalName>
		<type>0</type>
		<size>256</size>
	</field>
	<field id=''40''>
		<physicalName>idunidadtramitadora</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''41''>
		<physicalName>idproceso</physicalName>
		<type>3</type>
	</field>
	<field id=''42''>
		<physicalName>tipodireccioninteresado</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''43''>
		<physicalName>nversion</physicalName>
		<type>0</type>
		<size>16</size>
	</field>
	<field id=''44''>
		<physicalName>idseccioniniciadora</physicalName>
		<type>0</type>
		<size>64</size>
	</field>
	<field id=''45''>
		<physicalName>seccioniniciadora</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''46''>
		<physicalName>tfnofijo</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''47''>
		<physicalName>tfnomovil</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''48''>
		<physicalName>direcciontelematica</physicalName>
		<type>0</type>
		<size>60</size>
	</field>
	<field id=''49''>
		<physicalName>iddireccionpostal</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''50''>
		<physicalName>id</physicalName>
		<type>3</type>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>27</fieldId>
		<table>SPAC_TBL_003</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>35</fieldId>
		<table>SPAC_TBL_004</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
		</validation>
	<validation id=''3''>
		<fieldId>42</fieldId>
		<table>SPAC_TBL_005</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''4''>
		<fieldId>16</fieldId>
		<table>SPAC_TBL_002</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''5''>
		<fieldId>17</fieldId>
		<table/>
		<tableType/>
		<class/>
		<mandatory>S</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 1;

--
-- es
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE', 'Datos del Expediente');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL', 'Información adicional');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'NUMEXP', 'Nº Expediente');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FAPERTURA', 'Fecha Apertura');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FINICIOPLAZO', 'Fecha Inicio Plazo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'ASUNTO', 'Asunto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'NREG', 'Nº de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FREG', 'Fecha de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FORMATERMINACION', 'Forma Terminación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'ESTADOADM', 'Estado Administrativo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SECCIONINICIADORA', 'Sección Iniciadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SEP_INTERESADO_PRINCIPAL', 'INTERESADO PRINCIPAL');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_VALIDADO', 'Validado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_NO_VALIDADO', 'No Validado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'NIFCIFTITULAR', 'NIF/CIF');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDENTIDADTITULAR', 'Identidad');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'DOMICILIO', 'Dirección Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'CIUDAD', 'Ciudad');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'CPOSTAL', 'Código Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'REGIONPAIS', 'Región/País');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'TFNOFIJO', 'Tfno. Fijo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'TFNOMOVIL', 'Tfno. Móvil');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'DIRECCIONTELEMATICA', 'Dirección Telemática');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'TIPODIRECCIONINTERESADO', 'Tipo Dirección Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'ROLTITULAR', 'Relación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'UTRAMITADORA', 'Sección Tramitadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FCIERRE', 'Fecha Cierre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'HAYRECURSO', 'Recurso');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SEP_TERRITORIO', 'TERRITORIO');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'POBLACION', 'Población');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'MUNICIPIO', 'Municipio');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LOCALIZACION', 'Localización');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SPAC_EXPEDIENTES', 'Expediente');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'ID_PCD', 'Id. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'CODPROCEDIMIENTO', 'Cód. de Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'NOMBREPROCEDIMIENTO', 'Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FUNCIONACTIVIDAD', 'Función/Actividad');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDTITULAR', 'Id. de Interesado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'TIPOPERSONA', 'Tipo de Persona');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'EXPRELACIONADOS', 'Expedientes Relacionados');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'REFERENCIA_INTERNA', 'Referencia Interna');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'ESTADOINFO', 'Información de Estado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'FESTADO', 'Fecha de Estado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'PLAZO', 'Plazo de Resolución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'UPLAZO', 'Unidad del Plazo de Resolución');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'MATERIAS', 'Materias');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SERVPRESACTUACIONES', 'Serv. Pres. Actuaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'TIPODEDOCUMENTAL', 'Tipo de Documental');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'PALABRASCLAVE', 'Palabras Clave');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'EFECTOSDELSILENCIO', 'Efecto del Silencio Administrativo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDUNIDADTRAMITADORA', 'Id. de Sección Tramitadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDPROCESO', 'Id. de Proceso');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'NVERSION', 'Versión');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDSECCIONINICIADORA', 'Id. de Sección Iniciadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_LIBRE', 'Libre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACION');

--
-- ca
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'ASUNTO', 'Assumpte');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'CIUDAD', 'Ciutat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'CODPROCEDIMIENTO', 'Cod. de Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'CPOSTAL', 'Codi Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'DIRECCIONTELEMATICA', 'Adreça Telemàtica');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'DOMICILIO', 'Adreça Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'EFECTOSDELSILENCIO', 'Efecte del Silenci Administratiu');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'ESTADOADM', 'Estat Administratiu');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'ESTADOINFO', 'Informació d''Estat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'EXPRELACIONADOS', 'Expedients Relacionats');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FAPERTURA', 'Data d''Apertura');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FCIERRE', 'Data Tancament');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FESTADO', 'Data d''Estat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FINICIOPLAZO', 'Data d''inici Termini');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FORMATERMINACION', 'Forma de Terminació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FREG', 'Data de Registre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'FUNCIONACTIVIDAD', 'Funció/Activitat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'HAYRECURSO', 'Recurs');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDDIRECCIONPOSTAL', 'Id. d''Adreça Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDENTIDADTITULAR', 'Identitat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'ID_PCD', 'Id. de Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDPROCESO', 'Id. de Procés');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDSECCIONINICIADORA', 'de Secció Iniciadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDTITULAR', 'Id. d''Interessat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'IDUNIDADTRAMITADORA', 'Id. de Secció Tramitadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'LBL_LIBRE', 'Lliure');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'LBL_NO_VALIDADO', 'No Validat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'LBL_VALIDADO', 'Validat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'LOCALIZACION', 'Localització');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'MATERIAS', 'Matèries');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'MUNICIPIO', 'Municipi');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'NIFCIFTITULAR', 'NIF/CIF');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'NOMBREPROCEDIMIENTO', 'Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'NREG', 'Núm. de Registre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'NUMEXP', 'Núm. d''Expedient');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'NVERSION', 'Versió');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'OBSERVACIONES', 'Observacions');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'PALABRASCLAVE', 'Paraules Clau');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'PLAZO', 'Termini de Resolució');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'POBLACION', 'Població');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'REFERENCIA_INTERNA', 'Referència Interna');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'REGIONPAIS', 'Regió/País');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'ROLTITULAR', 'Relació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SECCIONINICIADORA', 'Secció Iniciadora');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SEP_DIRECCIONES', 'ADREÇA NOTIFICACIÓ');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SEP_INTERESADO_PRINCIPAL', 'INTERESSAT PRINCIPAL');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SEP_TERRITORIO', 'TERRITORI');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SERVPRESACTUACIONES', 'Serv. Pres. Actuacions');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SPAC_EXPEDIENTES', 'Expedient');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE', 'Dades de l''Expedient');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL', 'Informació addicional');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'TFNOFIJO', 'Tfn. Fix');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'TFNOMOVIL', 'Tfn. Mòbil');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'TIPODEDOCUMENTAL', 'Tipus de Documental');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'TIPODIRECCIONINTERESADO', 'Tipus Adreça Notificació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'TIPOPERSONA', 'Tipus de Persona');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'UPLAZO', 'Unitat del Termini de Resolució');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 1, 'ca', 'UTRAMITADORA', 'Secció Tramitadora');


--
-- Formulario para expedientes
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<script>

	function acceptRegistryInput(){

		setReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NREG)'' ]);

		if (document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)''].value != '''') {

			checkRadioById(''validatedThirdParty'');
			setReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NIFCIFTITULAR)'' ]);
			setReadOnlyIdentidad(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)'' ]);
		}
		else {
			checkRadioById(''notValidatedThirdParty'');
			setNotReadOnly(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NIFCIFTITULAR)'' ]);
			setNotReadOnlyIdentidad(document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)'' ]);
		}
		hideInfo();


}

	function cancelRegistryInput(){

		hideInfo();
	}

	function delete_EXPEDIENT_SEARCH_INPUT_REGISTRY(){

	  jConfirm(''<bean:message key="msg.delete.data.register"/>'', ''<bean:message key="common.confirm"/>'' , ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'', function(r) {
		if(r){
			nreg = document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:NREG)'' ];
	 		setNotReadOnly(nreg);
	 		nreg.value = '''';
	 		nreg.focus();

			document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:FREG)'' ].value = '''';
		}

	});


	 	ispac_needToConfirm = true;
	}




</script>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center" onclick="showTab(1)">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE)" />
</nobr>
</td>
<td width="5px"><img height="1" width="5px" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
<td class="unselect" id="tdlink2" align="center" onclick="document.defaultForm.name = ''Expedientes'';if (validateExpedientes(document.defaultForm)){showTab(2);}">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>

<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_DATOS_EXPEDIENTE" style="position: relative; height: 710px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NUMEXP)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NUMEXP" style="position: absolute; top: 20px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:NUMEXP)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="30" tabindex="101">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 54px; left: 391px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FINICIOPLAZO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FINICIOPLAZO" style="position: absolute; top: 51px; left: 505px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FINICIOPLAZO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="103">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 149px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NREG)" />:</nobr>
</div>

<div id="data_SPAC_EXPEDIENTES:NREG" style="position: absolute; top: 146px; left: 170px;">
</nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NREG)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="25"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NREG"
				  target="workframe"
			  	  action="searchInputRegistry.do"
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.intray"
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NREG"
			  	  width="500"
			  	  height="300"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_INPUT_REGISTRY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.register" tabindex="105">

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:ASUNTO)" property="ASUNTO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:FREG)" property="FREG"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NREG" id="JAVASCRIPT" property="accept_EXPEDIENT_SEARCH_INPUT_REGISTRY"/>

	</ispac:htmlTextImageFrame>
</c:when>
<c:otherwise>
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NREG)" readonly="false" styleClass="input" size="25" maxlength="16" tabindex="105"/>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 151px; left: 392px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FREG)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FREG" style="position: absolute; top: 148px; left: 505px;">
</nobr>
<c:choose>
<c:when test="${!empty sicresConnectorClass}">
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:when>
<c:otherwise>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FREG)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="106">
</ispac:htmlTextCalendar>
</c:otherwise>
</c:choose>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL" style="position: absolute; top: 285px; left: 10px; width: 620px" class="textbar">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_INTERESADO_PRINCIPAL)" /></nobr>
<hr class="formbar"/>
</div>
<c:url value="searchThirdParty.do" var="_searchThirdParty">
	<c:param name="search">1</c:param>
</c:url>

<jsp:useBean id="_searchThirdParty" type="java.lang.String"/>
<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
</nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR"
				  target="workframe"
			  	  action="searchThirdParty.do"
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:NIFCIFTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty"
				  tabindex="112"
				  >

		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_NIFCIFTITULAR" id="JAVASCRIPT" property="return_EXPEDIENT_SEARCH_THIRD_PARTY"/>

	</ispac:htmlTextImageFrame>

		<ispac:imageframe
					  id="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do"
					  image="img/icon_barra_3.gif"
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS"
					  >
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>
		<ispac:imageframe
					  id="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do"
					  image="img/icon_barra_2.gif"
					  showFrame="true"
					  inputField="SPAC_EXPEDIENTES:IDTITULAR"
					  jsShowFrame="show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS"
					  >

			<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_EXPEDIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_EXPEDIENTES:DOMICILIO)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'' ,  ''<bean:message key="common.alert"/>'' , ''<bean:message key="common.message.ok"/> '', ''<bean:message key="common.message.cancel"/>'');}
				}

			}

			function show_SEARCH_SPAC_EXPEDIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_EXPEDIENTES:IDTITULAR)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.interested.notSelected"/>'', ''<bean:message key="common.alert"/>'' , ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');}
			}
			//-->
		</script>



</nobr>
</div>


<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>

</c:when>
<c:otherwise>
<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO" style="position: absolute; top: 324px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 324px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 357px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:NIFCIFTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:NIFCIFTITULAR" style="position: absolute; top: 354px; left: 170px;">
	<ispac:htmlText property="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" readonly="false" styleClass="input" size="34" maxlength="16" tabindex="112"/>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:IDENTIDADTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="113"
				  >


		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
	</ispac:htmlTextareaImageFrame>
</div>

<div id="label_SPAC_EXPEDIENTES:SEP_DIRECCIONES" style="position: absolute; top: 422px; left: 10px; width: 200px" class="textbar1">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_EXPEDIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 10px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" tabindex="110" disabled="true"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 447px; left: 100px;"  class="formsTitleB">
</nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" checked="checked" disabled="true" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LBL_LIBRE)" /></nobr>
</div>



</c:otherwise>
</c:choose>

<div id="label_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 480px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DOMICILIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DOMICILIO" style="position: absolute; top: 477px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:DOMICILIO)" readonly="true" propertyReadonly="readonly" styleClass="textareaDir" styleClassReadonly="textareaDirRO" rows="2" cols="85" tabindex="114" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 514px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CIUDAD)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CIUDAD" style="position: absolute; top: 511px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CIUDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="30" maxlength="64" tabindex="115">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 547px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:REGIONPAIS)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:REGIONPAIS" style="position: absolute; top: 544px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:REGIONPAIS)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="64" tabindex="117">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 514px; left: 370px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:CPOSTAL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:CPOSTAL" style="position: absolute; top: 511px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:CPOSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="5" tabindex="116">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 390px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:IDENTIDADTITULAR" style="position: absolute; top: 387px; left: 170px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="true"
			  	  inputField="SPAC_EXPEDIENTES:IDENTIDADTITULAR"
			  	  jsDelete="delete_EXPEDIENT_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="113"
				  >


		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPOPERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:NIFCIFTITULAR)" property="NIFCIFTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDTITULAR)" property="IDTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDENTIDADTITULAR)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DOMICILIO)" property="DOMICILIO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CPOSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:CIUDAD)" property="CIUDAD"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:REGIONPAIS)" property="REGIONPAIS"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOFIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TFNOMOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_IDENTIDADTITULAR" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
	</ispac:htmlTextareaImageFrame>
</div>
<div id="label_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 690px; left: 10px;" class="formsTitleB">
</nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ROLTITULAR)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ROLTITULAR" style="position: absolute; top: 687px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ROLTITULAR)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="122">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(SPAC_EXPEDIENTES:ROLTITULAR)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ROLTITULAR" id="property(ROLTITULAR_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 83px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ASUNTO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ASUNTO" style="position: absolute; top: 80px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:ASUNTO)" readonly="false" propertyReadonly="readonly" styleClass="textareaAsunto" styleClassReadonly="textareaAsuntoRO" rows="4" cols="85" tabindex="104">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 181px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FORMATERMINACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FORMATERMINACION" style="position: absolute; top: 178px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:FORMATERMINACION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_003" image="img/search-mg.gif" titleKeyLink="select.termination" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="107">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(SPAC_EXPEDIENTES:FORMATERMINACION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_FORMATERMINACION" id="property(FORMATERMINACION_SPAC_TBL_003:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 214px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:ESTADOADM)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:ESTADOADM" style="position: absolute; top: 211px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:ESTADOADM)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_004" image="img/search-mg.gif" titleKeyLink="select.estadoadm" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="108">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(SPAC_EXPEDIENTES:ESTADOADM)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_ESTADOADM" id="property(ESTADOADM_SPAC_TBL_004:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 52px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FAPERTURA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FAPERTURA" style="position: absolute; top: 49px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FAPERTURA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="102">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 655px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO" style="position: absolute; top: 652px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="34" id="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="121">
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(SPAC_EXPEDIENTES:TIPODIRECCIONINTERESADO)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_TIPODIRECCIONINTERESADO" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 247px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SECCIONINICIADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:SECCIONINICIADORA" style="position: absolute; top: 244px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:SECCIONINICIADORA)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="250" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 583px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOFIJO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOFIJO" style="position: absolute; top: 580px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOFIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="118">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 583px; left: 370px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:TFNOMOVIL)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:TFNOMOVIL" style="position: absolute; top: 580px; left: 470px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:TFNOMOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="119">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 620px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 617px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="120">
</ispac:htmlText>
</div>
</div>
</div>

<!-- BLOQUE2 DE CAMPOS -->
<div style="display:none" id="block2">
<div id="dataBlock_SPAC_EXPEDIENTES_TAB_INFORMACION_ADICIONAL" style="position: relative; height: 280px; width: 600px;">
<div id="label_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 23px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:UTRAMITADORA)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:UTRAMITADORA" style="position: absolute; top: 20px; left: 170px;">
<html:hidden property="property(SPAC_EXPEDIENTES:IDUNIDADTRAMITADORA)" />
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_EXPEDIENTES:UTRAMITADORA)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="50"
				  id="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA"
				  target="workframe"
				  action="producersWizard.do"
				  image="img/search-mg.gif"
				  titleKeyLink="select.unidadTramitadora"
				  showFrame="true" tabindex="201">
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA" id="property(SPAC_EXPEDIENTES:UTRAMITADORA)" property="NAME"/>
		<ispac:parameter name="SEARCH_SPAC_EXPEDIENTES_UTRAMITADORA" id="property(SPAC_EXPEDIENTES:IDUNIDADTRAMITADORA)" property="ID"/>
	</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 58px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:FCIERRE)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:FCIERRE" style="position: absolute; top: 55px; left: 170px;">
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_EXPEDIENTES:FCIERRE)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" tabindex="202">
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 58px; left: 306px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:HAYRECURSO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:HAYRECURSO" style="position: absolute; top: 55px; left: 364px;">
<ispac:htmlCheckbox property="property(SPAC_EXPEDIENTES:HAYRECURSO)" readonly="false" propertyReadonly="readonly" valueChecked="SI" styleClassCheckbox="inputSelectP" tabindex="203"></ispac:htmlCheckbox>
</div>
<div id="label_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 93px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:OBSERVACIONES)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:OBSERVACIONES" style="position: absolute; top: 90px; left: 170px;">
<ispac:htmlTextarea property="property(SPAC_EXPEDIENTES:OBSERVACIONES)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="85" tabindex="204">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_EXPEDIENTES:SEP_TERRITORIO" style="position: absolute; top: 140px; left: 10px; width: 620px" class="textbar">
<nobr><bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:SEP_TERRITORIO)" />
<hr class="formbar"/>
</div>
<div id="label_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 183px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:POBLACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:POBLACION" style="position: absolute; top: 180px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:POBLACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="205">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 213px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:MUNICIPIO)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:MUNICIPIO" style="position: absolute; top: 210px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:MUNICIPIO)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="64" tabindex="206">
</ispac:htmlText>
</div>
<div id="label_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 243px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_EXPEDIENTES:LOCALIZACION)" />:</nobr>
</div>
<div id="data_SPAC_EXPEDIENTES:LOCALIZACION" style="position: absolute; top: 240px; left: 170px;">
<ispac:htmlText property="property(SPAC_EXPEDIENTES:LOCALIZACION)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" maxlength="256" tabindex="207">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = 1
WHERE id = 1;

--
-- Documento
--
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''2''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''3''>
		<physicalName>fdoc</physicalName>
		<type>6</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''4''>
		<physicalName>nombre</physicalName>
		<type>0</type>
		<size>100</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''5''>
		<physicalName>autor</physicalName>
		<type>0</type>
		<size>250</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''6''>
		<physicalName>id_fase</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''7''>
		<physicalName>id_tramite</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''8''>
		<physicalName>id_tpdoc</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''9''>
		<physicalName>tp_reg</physicalName>
		<type>0</type>
		<size>16</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''10''>
		<physicalName>nreg</physicalName>
		<type>0</type>
		<size>16</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''11''>
		<physicalName>freg</physicalName>
		<type>6</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''12''>
		<physicalName>infopag</physicalName>
		<type>0</type>
		<size>100</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''13''>
		<physicalName>id_fase_pcd</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''14''>
		<physicalName>id_tramite_pcd</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''15''>
		<physicalName>estado</physicalName>
		<type>0</type>
		<size>16</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''16''>
		<physicalName>origen</physicalName>
		<type>0</type>
		<size>128</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''17''>
		<physicalName>descripcion</physicalName>
		<type>0</type>
		<size>250</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''18''>
		<physicalName>origen_id</physicalName>
		<type>0</type>
		<size>20</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''19''>
		<physicalName>destino</physicalName>
		<type>0</type>
		<size>250</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''20''>
		<physicalName>autor_info</physicalName>
		<type>0</type>
		<size>250</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''21''>
		<physicalName>estadofirma</physicalName>
		<type>0</type>
		<size>2</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''22''>
		<physicalName>id_notificacion</physicalName>
		<type>0</type>
		<size>64</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''23''>
		<physicalName>estadonotificacion</physicalName>
		<type>0</type>
		<size>2</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''24''>
		<physicalName>destino_id</physicalName>
		<type>0</type>
		<size>20</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''25''>
		<physicalName>fnotificacion</physicalName>
		<type>6</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''26''>
		<physicalName>faprobacion</physicalName>
		<type>6</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''27''>
		<physicalName>origen_tipo</physicalName>
		<type>0</type>
		<size>1</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''28''>
		<physicalName>destino_tipo</physicalName>
		<type>0</type>
		<size>1</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''29''>
		<physicalName>id_plantilla</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''30''>
		<physicalName>bloqueo</physicalName>
		<type>0</type>
		<size>2</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''31''>
		<physicalName>repositorio</physicalName>
		<type>0</type>
		<size>8</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''32''>
		<physicalName>extension</physicalName>
		<type>0</type>
		<size>64</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''33''>
		<physicalName>ffirma</physicalName>
		<type>6</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''34''>
		<physicalName>infopag_rde</physicalName>
		<type>0</type>
		<size>256</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''35''>
		<physicalName>id_reg_entidad</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''36''>
		<physicalName>id_entidad</physicalName>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
		</field>
	<field id=''37''>
		<physicalName>extension_rde</physicalName>
		<descripcion><![CDATA[Extensión del documento firmado]]></descripcion>
		<type>0</type>
		<size>64</size>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
<field id=''38''>
		<physicalName>id_proceso_firma</physicalName>
		<descripcion><![CDATA[Datos identificativos del proceso de firma externo en el que está inmerso el documento]]></descripcion>
		<type>1</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	<field id=''39''>
		<physicalName>id_circuito</physicalName>
		<descripcion><![CDATA[Identificador del circuito utilizado para enviar al portafirmas]]></descripcion>
		<type>3</type>
		<nullable>S</nullable>
		<documentarySearch>N</documentarySearch>
		<multivalue>N</multivalue>
	</field>
	</fields>
	<validations>
		<validation id=''1''>
			<fieldId>23</fieldId>
			<table>SPAC_TBL_006</table>
			<tableType>3</tableType>
			<class/>
			<mandatory>N</mandatory>
		</validation>
		<validation id=''2''>
			<fieldId>21</fieldId>
			<table>SPAC_TBL_008</table>
			<tableType>3</tableType>
			<class/>
			<mandatory>N</mandatory>
		</validation>
	</validations>
</entity>'
WHERE id = 2;

--
-- es
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'SPAC_DT_DOCUMENTOS', 'Documento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'NOMBRE', 'Nombre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'FAPROBACION', 'Fecha Aprobación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'FNOTIFICACION', 'Fecha Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'DESCRIPCION', 'Descripción');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ORIGEN', 'Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'DESTINO', 'Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'NREG', 'Nº de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'FREG', 'Fecha de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'TP_REG', 'Tipo de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'DOCUMENTO', 'Documento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'FDOC', 'Fecha Generación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ESTADONOTIFICACION', 'Estado Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ESTADOFIRMA', 'Estado de Firma');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'FFIRMA', 'Fecha Estado de Firma');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_NOTIFICACION', 'Id. de Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'AUTOR', 'Autor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_FASE', 'Id. de Fase');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_TRAMITE', 'Id. de Trámite');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_TPDOC', 'Id. de Tipo de Documento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'INFOPAG', 'Referencia Gestor Documental');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_FASE_PCD', 'Id. de Fase en el Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_TRAMITE_PCD', 'Id. de Trámite en el Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'AUTOR', 'Autor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ESTADO', 'Estado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ORIGEN_ID', 'Id. de Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'AUTOR_INFO', 'Información del Autor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'DESTINO_ID', 'Id. de Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ORIGEN_TIPO', 'Tipo del Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'DESTINO_TIPO', 'Tipo del Destino');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_PLANTILLA', 'Id. de Plantilla');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'BLOQUEO', 'Estado de Bloqueo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'REPOSITORIO', 'Repositorio');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'EXTENSION', 'Extensión');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'INFOPAG_RDE', 'Referencia Gestor Documental de Documentos Electrónicos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_REG_ENTIDAD', 'Id. Reg. Entidad');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'es', 'ID_ENTIDAD', 'Id. Entidad');

--
-- ca
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'AUTOR', 'Autor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'AUTOR_INFO', 'Informació de l''Autor');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'BLOQUEO', 'Estat de Bloqueig');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'DESCRIPCION', 'Descripció');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'DESTINO', 'Destinació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'DESTINO_ID', 'Id. de Destinació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'DESTINO_TIPO', 'Tipus de la Destinació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'DOCUMENTO', 'Document');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ESTADO', 'Estat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ESTADOFIRMA', 'Estat de Signatura');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ESTADONOTIFICACION', 'Estat Notificació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'EXTENSION', 'Extensió');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'EXTENSION_RDE', 'Extensió documento signat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'FAPROBACION', 'Data Aprovació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'FDOC', 'Data Generació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'FFIRMA', 'Data Estat de Signatura');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'FNOTIFICACION', 'Data Notificació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'FREG', 'Data de Registre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_ENTIDAD', 'Id. Entitat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_FASE', 'Id. de Fase');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_FASE_PCD', 'Id. de Fase en el Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_NOTIFICACION', 'Id. de Notificació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_PLANTILLA', 'Id. de Plantilla');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_REG_ENTIDAD', 'Id. Reg. Entitat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_TPDOC', 'Id. de Tipus de Document');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_TRAMITE', 'Id. de Tràmit');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ID_TRAMITE_PCD', 'Id. de Tràmit en el Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'INFOPAG', 'Referencia Gestor Documental');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'INFOPAG_RDE', 'Referencia Gestor Documental de Documents Electrònics');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'NOMBRE', 'Nom');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'NREG', 'Nº de Registre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ORIGEN', 'Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ORIGEN_ID', 'Id. de Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'ORIGEN_TIPO', 'Tipus de l''Origen');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'REPOSITORIO', 'Repositori');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'SPAC_DT_DOCUMENTOS', 'Document');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 2, 'ca', 'TP_REG', 'Tipus de Registre');


--
-- Interviniente
--
UPDATE spac_ct_entidades SET definicion = '<entity version=''1.00''>
<editable>S</editable>
<dropable>N</dropable>
<status>0</status>
<fields>
	<field id=''1''>
		<physicalName>id_ext</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''2''>
		<physicalName>rol</physicalName>
		<type>0</type>
		<size>4</size>
	</field>
	<field id=''3''>
		<physicalName>tipo</physicalName>
		<type>3</type>
	</field>
	<field id=''4''>
		<physicalName>tipo_persona</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''5''>
		<physicalName>ndoc</physicalName>
		<type>0</type>
		<size>12</size>
	</field>
	<field id=''6''>
		<physicalName>nombre</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''7''>
		<physicalName>dirnot</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''8''>
		<physicalName>email</physicalName>
		<type>0</type>
		<size>250</size>
	</field>
	<field id=''9''>
		<physicalName>c_postal</physicalName>
		<type>0</type>
		<size>10</size>
	</field>
	<field id=''10''>
		<physicalName>localidad</physicalName>
		<type>0</type>
		<size>150</size>
	</field>
	<field id=''11''>
		<physicalName>caut</physicalName>
		<type>0</type>
		<size>150</size>
	</field>
	<field id=''12''>
		<physicalName>tfno_fijo</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''13''>
		<physicalName>tfno_movil</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''14''>
		<physicalName>tipo_direccion</physicalName>
		<type>0</type>
		<size>1</size>
	</field>
	<field id=''15''>
		<physicalName>direcciontelematica</physicalName>
		<type>0</type>
		<size>60</size>
	</field>
	<field id=''16''>
		<physicalName>iddireccionpostal</physicalName>
		<type>0</type>
		<size>32</size>
	</field>
	<field id=''17''>
		<physicalName>id</physicalName>
		<type>3</type>
	</field>
	<field id=''18''>
		<physicalName>numexp</physicalName>
		<type>0</type>
		<size>30</size>
	</field>
</fields>
<validations>
	<validation id=''1''>
		<fieldId>6</fieldId>
		<table/>
		<tableType/>
		<class/>
		<mandatory>S</mandatory>
	</validation>
	<validation id=''2''>
		<fieldId>14</fieldId>
		<table>SPAC_TBL_005</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
	<validation id=''3''>
		<fieldId>2</fieldId>
		<table>SPAC_TBL_002</table>
		<tableType>3</tableType>
		<class/>
		<mandatory>N</mandatory>
	</validation>
</validations>
</entity>'
WHERE id = 3;

--
-- es
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'SPAC_DT_INTERVINIENTES', 'Participantes');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_VALIDADO', 'Validado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_NO_VALIDADO', 'No Validado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'NDOC', 'NIF/CIF');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'NOMBRE', 'Nombre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'DIRNOT', 'Dirección Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LOCALIDAD', 'Ciudad');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'C_POSTAL', 'Código Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'CAUT', 'Región/País');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'TFNO_FIJO', 'Tfno. Fijo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'TFNO_MOVIL', 'Tfno. Móvil');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'DIRECCIONTELEMATICA', 'Dirección Telemática');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'TIPO_DIRECCION', 'Tipo Dirección Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'ROL', 'Relación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'NUMDOCUMENTO', 'Nº Documento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LISTA', 'LISTADO DE PARTICIPANTES');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'IMAGEN', 'user');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE', 'Datos de Participante');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'ID_EXT', 'Referencia Externa');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'TIPO', 'Tipo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'TIPO_PERSONA', 'Tipo de Persona');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'EMAIL', 'E-Mail');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'IDDIRECCIONPOSTAL', 'Id. de Dirección Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'SEP_DIRECCIONES', 'DIRECCION NOTIFICACION');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'es', 'LBL_LIBRE', 'Libre');

--
-- ca
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'CAUT', 'Regió/País');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'C_POSTAL', 'Codi Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'DIRECCIONTELEMATICA', 'Adreça Telemàtica');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'DIRNOT', 'Adreça Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'EMAIL', 'E-Mail');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'IDDIRECCIONPOSTAL', 'Id. de Adreça Postal');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'ID_EXT', 'Referència Externa');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'IMAGEN', 'user');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LBL_CONFIRMADA', 'Verificada');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LBL_LIBRE', 'Lliure');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LBL_NO_VALIDADO', 'No Validat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LBL_VALIDADO', 'Validat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LISTA', 'LLISTAT DE PARTICIPANTS');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'LOCALIDAD', 'Ciutat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'NDOC', 'NIF/CIF');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'NOMBRE', 'Nom');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'NUMDOCUMENTO', 'Nº Document');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'ROL', 'Relació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'SEP_DIRECCIONES', 'ADREÇA NOTIFICACIÓ');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'SPAC_DT_INTERVINIENTES', 'Participants');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE', 'Dades de Participant');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'TFNO_FIJO', 'Tfn. Fix');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'TFNO_MOVIL', 'Tfno. Mòbil');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'TIPO', 'Tipus');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'TIPO_DIRECCION', 'Tipus Adreça Notificació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 3, 'ca', 'TIPO_PERSONA', 'Tipus de Persona');


--
-- Formulario para intervinientes
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>

<!-- DEFINICION DE LAS ANCLAS A LOS BLOQUES DE CAMPOS -->
<table border="0" cellspacing="0" cellpadding="0">
<tr>
<td class="select" id="tdlink1" align="center">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE)" />
</nobr>
</td>
</tr>
</table>

<table width="100%" border="0" class="formtable" cellspacing="0" cellpadding="0">
<tr>
<td><img height="8" src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
<tr>
<td>
<c:url value="searchThirdParty.do" var="_searchThirdParty">
	<c:param name="search">1</c:param>
</c:url>

<jsp:useBean id="_searchThirdParty" type="java.lang.String"/>
<!-- BLOQUE1 DE CAMPOS -->
<div style="display:block" id="block1">
<div id="dataBlock_SPAC_DT_INTERVINIENTES_TAB_DATOS_PARTICIPANTE" style="position: relative; height: 400px; width: 600px;">

<c:choose>
<c:when test="${!empty thirdPartyAPIClass}">
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" onclick="javascript: clickValidatedThirdParty();" checked="checked" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" onclick="javascript: clickNotValidatedThirdParty();" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
<nobr>
	<ispac:htmlTextImageFrame property="property(SPAC_DT_INTERVINIENTES:NDOC)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  size="34"
				  maxlength="16"
			  	  id="THIRD_SEARCH_THIRD_PARTY"
				  target="workframe"
			  	  action="searchThirdParty.do"
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="false"
			  	  inputField="SPAC_DT_INTERVINIENTES:NDOC"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.participantes.title.delete.data.thirdParty"
			  	  jsExecuteFrame="selectThirdParty"
				  tabindex="112"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY" id="JAVASCRIPT" property="return_THIRD_SEARCH_THIRD_PARTY"/>

	</ispac:htmlTextImageFrame>

		<ispac:imageframe
					  id="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS"
					  target="workframe"
					  action="searchPostalAddressesThirdParty.do"
					  image="img/icon_barra_3.gif"
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS"
					  >
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>

		</ispac:imageframe>

		<ispac:imageframe
					  id="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS"
					  target="workframe"
					  action="searchElectronicAddressesThirdParty.do"
					  image="img/icon_barra_2.gif"
					  showFrame="true"
					  inputField="SPAC_DT_INTERVINIENTES:ID_EXT"
					  jsShowFrame="show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS"
					  >

			<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		</ispac:imageframe>

		<script language=''JavaScript'' type=''text/javascript''><!--
			function show_SEARCH_SPAC_DT_INTERVINIENTES_POSTAL_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				direccion = document.defaultForm.elements[ ''property(SPAC_DT_INTERVINIENTES:DIRNOT)'' ];
				if (direccion.readOnly) {
					idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
					if (idtitular.value != '''') {
						showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
					}
					else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'' , ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'', ''<bean:message key="common.message.cancel"/>'');}
				}
			}

			function show_SEARCH_SPAC_DT_INTERVINIENTES_ELECTRONIC_ADDRESS(target, action, width, height, msgConfirm, doSubmit, needToConfirm) {
				idtitular = document.forms[0].elements[ ''property(SPAC_DT_INTERVINIENTES:ID_EXT)'' ];
				if (idtitular.value != '''') {
					showFrame(target, action, width, height, msgConfirm, doSubmit, needToConfirm);
				}
				else{jAlert(''<bean:message key="terceros.thirdnotSelected"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'', ''<bean:message key="common.message.cancel"/>'');}
			}
			//-->
		</script>
</nobr>






</div>


<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_DT_INTERVINIENTES:NOMBRE)"
				  readonly="true"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="THIRD_SEARCH_THIRD_PARTY_NOMBRE"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="true"
			  	  inputField="SPAC_DT_INTERVINIENTES:NOMBRE"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="104"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NDOC)" property="NIFCIFTITULAR"/>

	</ispac:htmlTextareaImageFrame>
</div>
</c:when>
<c:otherwise>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO" style="position: absolute; top: 5px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="validatedThirdParty" disabled="true" tabindex="101"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO" style="position: absolute; top: 5px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationThirdParty" id="notValidatedThirdParty" checked="checked" disabled="true" tabindex="102"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 37px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NDOC)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NDOC" style="position: absolute; top: 34px; left: 175px;">
	<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:NDOC)" readonly="false" styleClass="input" size="30" maxlength="12" tabindex="103"/>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 67px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:NOMBRE)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:NOMBRE" style="position: absolute; top: 64px; left: 175px;">
<ispac:htmlTextareaImageFrame property="property(SPAC_DT_INTERVINIENTES:NOMBRE)"
				  readonly="false"
				  readonlyTag="false"
				  propertyReadonly="readonly"
				  styleClass="input"
				  styleClassReadonly="inputReadOnly"
				  rows="2"
				  cols="70"
			  	  id="THIRD_SEARCH_THIRD_PARTY_NOMBRE"
				  target="workframe"
			  	  action=''<%=_searchThirdParty%>''
			  	  image="img/search-mg.gif"
			  	  titleKeyLink="search.thirdparty"
			  	  showFrame="true"
			  	  inputField="SPAC_DT_INTERVINIENTES:NOMBRE"
			  	  jsDelete="delete_THIRD_SEARCH_THIRD_PARTY"
			  	  titleKeyImageDelete="forms.exp.title.delete.data.thirdParty"
				  tabindex="104"
				  >

		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_PERSONA)" property="TIPOPERSONA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:ID_EXT)" property="IDTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NOMBRE)" property="IDENTIDADTITULAR"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRNOT)" property="DOMICILIO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" property="CPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" property="CIUDAD"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:CAUT)" property="REGIONPAIS"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" property="TFNOFIJO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" property="TFNOMOVIL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:IDDIRECCIONPOSTAL)" property="IDDIRECCIONPOSTAL"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" property="DIRECCIONTELEMATICA"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="TIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(TIPODIRECCIONINTERESADO_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTOTIPODIRECCIONINTERESADO"/>
		<ispac:parameter name="THIRD_SEARCH_THIRD_PARTY_NOMBRE" id="property(SPAC_DT_INTERVINIENTES:NDOC)" property="NIFCIFTITULAR"/>

	</ispac:htmlTextareaImageFrame>
</div>
</c:otherwise>
</c:choose>


<div id="label_SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES" style="position: absolute; top: 103px; left: 10px; width: 200px" class="textbar1">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:SEP_DIRECCIONES)" /></nobr>
<hr class="formbar1"/>
</div>


<div id="label_SPAC_DT_INTERVINIENTES:LBL_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 10px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="validatedPostalAddressThirdParty" onclick="javascript: clickValidatedPostalAddressThirdParty();" checked="checked" tabindex="110"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_CONFIRMADA)" /></nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LBL_NO_VALIDADO_POSTALADDRESS" style="position: absolute; top: 128px; left: 100px;"  class="formsTitleB">
<nobr>
<input type="radio" name="validationPostalAddressThirdParty" id="notValidatedPostalAddressThirdParty" onclick="javascript: clickNotValidatedPostalAddressThirdParty();" tabindex="111"/>
&nbsp;<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LBL_LIBRE)" /></nobr>
</div>






<div id="label_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 371px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:ROL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:ROL" style="position: absolute; top: 368px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:ROL)" />
<nobr>
<ispac:htmlTextImageFrame property="property(ROL_SPAC_TBL_002:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="50" id="SEARCH_SPAC_DT_INTERVINIENTES_ROL" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_002" image="img/search-mg.gif" titleKeyLink="select.rol" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="113">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(SPAC_DT_INTERVINIENTES:ROL)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_ROL" id="property(ROL_SPAC_TBL_002:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 153px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRNOT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRNOT" style="position: absolute; top: 150px; left: 175px;">
<ispac:htmlTextarea property="property(SPAC_DT_INTERVINIENTES:DIRNOT)" readonly="true" propertyReadonly="readonly" styleClass="textareaDir" styleClassReadonly="textareaDirRO" rows="2" cols="80" tabindex="105">
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 240px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:C_POSTAL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:C_POSTAL" style="position: absolute; top: 237px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:C_POSTAL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="10" maxlength="10" tabindex="108">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 189px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:LOCALIDAD)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:LOCALIDAD" style="position: absolute; top: 186px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:LOCALIDAD)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="106">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 216px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:CAUT)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:CAUT" style="position: absolute; top: 213px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:CAUT)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="150" tabindex="107">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 267px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_FIJO" style="position: absolute; top: 264px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_FIJO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="109">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 293px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TFNO_MOVIL" style="position: absolute; top: 290px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:TFNO_MOVIL)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" maxlength="32" tabindex="110">
</ispac:htmlText>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 345px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:TIPO_DIRECCION" style="position: absolute; top: 342px; left: 175px;">
<html:hidden property="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" />
<nobr>
<ispac:htmlTextImageFrame property="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="35" id="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" target="workframe" action="selectSubstitute.do?entity=SPAC_TBL_005" image="img/search-mg.gif" titleKeyLink="select.tipoDireccionNotificacion" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" tabindex="112">
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(SPAC_DT_INTERVINIENTES:TIPO_DIRECCION)" property="VALOR" />
<ispac:parameter name="SEARCH_SPAC_DT_INTERVINIENTES_TIPO_DIRECCION" id="property(TIPO_DIRECCION_SPAC_TBL_005:SUSTITUTO)" property="SUSTITUTO" />
</ispac:htmlTextImageFrame>
</nobr>
</div>
<div id="label_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 319px; left: 10px;" class="formsTitleB">
<nobr>
<bean:write name="defaultForm" property="entityApp.label(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" />:</nobr>
</div>
<div id="data_SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA" style="position: absolute; top: 316px; left: 175px;">
<ispac:htmlText property="property(SPAC_DT_INTERVINIENTES:DIRECCIONTELEMATICA)" readonly="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="60" tabindex="111">
</ispac:htmlText>
</div>
</div>
</div>
</td>
</tr>
<tr>
<td height="15"><img src=''<ispac:rewrite href="img/pixel.gif"/>''/></td>
</tr>
</table>'
, frm_version = 1
WHERE id = 3;

--
-- Trámite
--

update SPAC_CT_ENTIDADES SET DEFINICION ='<entity version=''1.00''><editable>S</editable><dropable>N</dropable><status>0</status><fields><field id=''1''>	<physicalName>nombre</physicalName><type>0</type><size>250</size></field><field id=''2''><physicalName>estado</physicalName><type>2</type>		<size>1</size>	</field>	<field id=''3''>		<physicalName>id_tram_pcd</physicalName>		<type>3</type>	</field><field id=''4''>		<physicalName>id_fase_pcd</physicalName>		<type>3</type>	</field>	<field id=''5''>		<physicalName>id_fase_exp</physicalName>		<type>3</type>	</field>	<field id=''6''>		<physicalName>id_tram_exp</physicalName>		<type>3</type>	</field>	<field id=''7''>		<physicalName>id_tram_ctl</physicalName><type>3</type></field>	<field id=''8''>		<physicalName>num_acto</physicalName><type>0</type>	<size>16</size>	</field><field id=''9''>		<physicalName>cod_acto</physicalName>		<type>0</type>	<size>16</size>	</field><field id=''10''>	<physicalName>estado_info</physicalName><type>0</type><size>100</size></field>' WHERE ID=7;
update  SPAC_CT_ENTIDADES SET DEFINICION = DEFINICION || '<field id=''11''>		<physicalName>fecha_inicio</physicalName>		<type>13</type>	</field>	<field id=''12''>		<physicalName>fecha_cierre</physicalName>		<type>6</type>	</field>	<field id=''13''>		<physicalName>fecha_limite</physicalName>		<type>6</type>	</field>	<field id=''14''>		<physicalName>fecha_fin</physicalName>		<type>6</type>	</field>	<field id=''15''>		<physicalName>fecha_inicio_plazo</physicalName>		<type>6</type>	</field>	<field id=''16''>		<physicalName>plazo</physicalName>		<type>3</type>	</field>	<field id=''17''>		<physicalName>uplazo</physicalName>		<type>0</type>		<size>1</size>	</field>	<field id=''18''>		<physicalName>observaciones</physicalName>		<type>0</type>		<size>254</size>	</field>	<field id=''19''>		<physicalName>descripcion</physicalName>		<type>0</type>		<size>100</size>	</field>	<field id=''20''>		<physicalName>und_resp</physicalName>		<type>0</type>		<size>250</size></field>' WHERE ID=7;
update  SPAC_CT_ENTIDADES SET DEFINICION = DEFINICION || '<field id=''21''>	<physicalName>fase_pcd</physicalName><type>0</type>		<size>250</size>	</field>	<field id=''22''>		<physicalName>id_subproceso</physicalName>		<type>3</type>	</field>	<field id=''23''><physicalName>id_resp_closed</physicalName><type>0</type><size>250</size></field><field id=''24''>		<physicalName>id</physicalName>		<type>3</type>	</field>	<field id=''25''>		<physicalName>numexp</physicalName>		<type>0</type>		<size>30</size>	</field></fields><validations>	<validation id=''1''>		<fieldId>17</fieldId>		<table>SPAC_TBL_001</table>		<tableType>3</tableType>		<class/>		<mandatory>N</mandatory>	</validation></validations></entity>' WHERE ID=7;


--
-- es
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'SPAC_DT_TRAMITES', 'Trámite');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'TRAMITE', 'TRÁMITE');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'INICIADO', 'Iniciado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'UND_RESP', 'Departamento Responsable');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'TRAMRESPONSABLE', 'Tramitador Responsable');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FECHA_INICIO_PLAZO', 'Fecha Inicio Plazo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'PLAZO', 'Plazo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'UPLAZO', 'Uds. Plazo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FECHA_LIMITE', 'Fecha Alarma');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'NOMBRE', 'Trámite');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ESTADO', 'Estado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_TRAM_PCD', 'Id. del Trámite en el Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_FASE_PCD', 'Id. de la Fase en el Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_FASE_EXP', 'Id. de la Fase Activa');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_TRAM_EXP', 'Id. del Trámite Activo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_TRAM_CTL', 'Id. del Trámite en el Catálogo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'NUM_ACTO', 'Nº de Acto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'COD_ACTO', 'Cód. de Acto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ESTADO_INFO', 'Información de Estado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FECHA_INICIO', 'Fecha de Creación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FECHA_CIERRE', 'Fecha de Cierre');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FECHA_FIN', 'Fecha de Finalización');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'OBSERVACIONES', 'Observaciones');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'DESCRIPCION', 'Descripción');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'FASE_PCD', 'Fase en el Procedimiento');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'es', 'ID_SUBPROCESO', 'Id. de Subproceso');

--
-- ca
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'COD_ACTO', 'Cod. d''Acte');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'DESCRIPCION', 'Descripció');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ESTADO', 'Estat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ESTADO_INFO', 'Informació d''Estat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FASE_PCD', 'Fase en el Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FECHA_CIERRE', 'Data de Tancament');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FECHA_FIN', 'Data de Finalització');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FECHA_INICIO', 'Data de Creació');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FECHA_INICIO_PLAZO', 'Data Inicio Termini');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'FECHA_LIMITE', 'Data Alarma');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_FASE_EXP', 'Id. de la Fase Activa');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_FASE_PCD', 'Id. de la Fase en el Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_SUBPROCESO', 'Id. de Subproceso');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_TRAM_CTL', 'Id. del Tràmit al Catàleg');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_TRAM_EXP', 'Id. del Tràmit Actiu');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'ID_TRAM_PCD', 'Id. del Tràmit en el Procediment');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'INICIADO', 'Iniciat');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'NOMBRE', 'Tràmit');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'NUM_ACTO', 'Nº d''Acte');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'OBSERVACIONES', 'Observacions');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'PLAZO', 'Termini');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'SPAC_DT_TRAMITES', 'Tràmit');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'TRAMITE', 'TRÀMIT');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'TRAMRESPONSABLE', 'Tramitador Responsable');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'UND_RESP', 'Departament Responsable');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 7, 'ca', 'UPLAZO', 'Uts. Termini');



--
-- Registros Entrada/Salida
--
update SPAC_CT_ENTIDADES SET DEFINICION ='<entity version=''1.00''><editable>S</editable><dropable>S</dropable><status>0</status><fields><field id=''1''><physicalName>id</physicalName><descripcion><![CDATA[Campos Clave de la entidad (Uso interno del sistema)]]></descripcion><type>3</type><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''2''><physicalName>numexp</physicalName><descripcion><![CDATA[Campo que relaciona la entidad con un número de expediente (Uso interno del sistema)]]></descripcion><type>0</type><size>30</size><nullable>N</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''3''><physicalName>NREG</physicalName><type>0</type><size>32</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''4''><physicalName>FREG</physicalName><type>6</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''5''><physicalName>ASUNTO</physicalName><type>1</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''6''><physicalName>ID_INTERESADO</physicalName><type>0</type><size>32</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''7''><physicalName>INTERESADO</physicalName><type>0</type><size>128</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''8''><physicalName>TP_REG</physicalName><type>0</type><size>16</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''9''><physicalName>ID_TRAMITE</physicalName><type>3</type><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field><field id=''10''><physicalName>ORIGINO_EXPEDIENTE</physicalName><type>0</type><size>2</size><nullable>S</nullable><documentarySearch>N</documentarySearch><multivalue>N</multivalue></field></fields><indexes><index id=''11''><name><![CDATA[IND_1332834253]]></name><fields><field idField=''2''/></fields></index></indexes><validations><validation id=''1''><fieldId>8</fieldId><table>SPAC_VLDTBL_TIPOREG</table><tableType>2</tableType><class/><mandatory>N</mandatory><hierarchicalId/><hierarchicalName/></validation><validation id=''2''><fieldId>10</fieldId><table>SPAC_TBL_009</table><tableType>2</tableType><class/><mandatory>N</mandatory><hierarchicalId/><hierarchicalName/></validation></validations></entity>' WHERE ID=8;


-- Recursos
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'SPAC_REGISTROS_ES','Registros E/S');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'NREG','Número de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'FREG','Fecha de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'ASUNTO','Asunto');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'ID_INTERESADO','Identificador interesado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'INTERESADO','Interesado');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'TP_REG','Tipo de Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'ID_TRAMITE','Identificador de trámite');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 8, 'es', 'ORIGINO_EXPEDIENTE','Dio origen al expediente');


--
-- Formulario
--
UPDATE spac_ct_aplicaciones SET frm_jsp = '<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/ispac-util.tld" prefix="ispac" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c1" %>


<script>

		function show_SEARCH_SPAC_REGISTROS_ES_NREG(target, action, width, height) {
			registerType = document.defaultForm[''property(SPAC_REGISTROS_ES:TP_REG)''].value
			if (registerType == '''' || registerType == ''NINGUNO''){
				jAlert(''<bean:message key="registro.tipoRegistro.invalido"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');
				return false;
			}
			registerNumber = document.defaultForm[''property(SPAC_REGISTROS_ES:NREG)''].value
			if (registerNumber == ''''){
				jAlert(''<bean:message key="registro.numeroRegistro.vacio"/>'', ''<bean:message key="common.alert"/>'', ''<bean:message key="common.message.ok"/>'' , ''<bean:message key="common.message.cancel"/>'');
				return false;
			}

			action = action + ''&registerType=''+registerType;
			showFrame(target, action, width, height) ;
		}

</script>

<c1:set var="sicresConnectorClass" value="${ISPACConfiguration.SICRES_CONNECTOR_CLASS}" />
<c1:set var="_key"><bean:write name="defaultForm" property="entityApp.property(SPAC_REGISTROS_ES:ID)" /></c1:set>

<c1:set var="aux0"><bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:SPAC_REGISTROS_ES)" /></c1:set><jsp:useBean id="aux0" type="java.lang.String"/>
<ispac:tabs>
<ispac:tab  title=''<%=aux0%>''><div id="dataBlock_SPAC_REGISTROS_ES" style="position: relative; height: 160px; width: 600px">

<html:hidden property="property(SPAC_REGISTROS_ES:ID_INTERESADO)"/>
<html:hidden property="property(SPAC_REGISTROS_ES:ID_TRAMITE)"/>

<div id="label_SPAC_REGISTROS_ES:TP_REG" style="position: absolute; top: 10px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:TP_REG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:TP_REG" style="position: absolute; top: 10px; left: 160px; width:100% ;" >
<nobr>

		<c:choose>
			<c:when test="${_key == ''''}">
				<ispac:htmlTextImageFrame property="property(SPAC_REGISTROS_ES:TP_REG)" readonly="true" readonlyTag="false" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="20" imageTabIndex="true" id="SEARCH_SPAC_REGISTROS_ES_TP_REG" target="workframe" action="selectValue.do?entity=SPAC_VLDTBL_TIPOREG" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="false" showFrame="true" width="640" height="480" >
				<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_TP_REG" id="property(SPAC_REGISTROS_ES:TP_REG)" property="VALOR" />
				</ispac:htmlTextImageFrame>
			</c:when>
			<c:otherwise>
				<ispac:htmlText property="property(SPAC_REGISTROS_ES:TP_REG)"
					styleClass="input"
					size="20"
					readonly=''true''
					/>
			</c:otherwise>
		</c:choose>

</nobr>
</div>


<div id="label_SPAC_REGISTROS_ES:NREG" style="position: absolute; top: 10px; left: 310px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:NREG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:NREG" style="position: absolute; top: 10px; left: 430px; width:100% ;" >
		<c:choose>
			<c:when test="${!empty sicresConnectorClass && _key == ''''}">
				<script>	
					function accept_SEARCH_SPAC_REGISTROS_ES_NREG(){
					  var element;
					  var elements;
					  var i;

					  element = document.getElementById(''waitOperationInProgress'');
					  if (element != null)
					  {
						// Deshabilitar el scroll
						document.body.style.overflow = "hidden";
					  
						element.style.position = "absolute";
						
						element.style.height = document.body.scrollHeight + 1200;
						element.style.width = document.body.clientWidth + 1200;
						element.style.left = -600;
						element.style.top = -1000;

						element.style.display = "block";
						
						if (isIE())
						{
						  elements = document.getElementsByTagName("SELECT");
							
						  for (i = 0; i < elements.length; i++)
						  {
							elements[i].style.visibility = "hidden";
						  }
						}
					  }
					}

				</script>
				<ispac:htmlTextImageFrame
					property="property(SPAC_REGISTROS_ES:NREG)"
					id="SEARCH_SPAC_REGISTROS_ES_NREG"
					readonlyTag="false"
					styleClassLink="search"
					styleClassReadonly="inputReadOnly"
					styleClass="input"
					size="25"
					maxlength="16"
					action="selectTaskRegisterES.do"
					image="img/search-mg.gif"
					titleKeyLink="search.intray"
					inputField="SPAC_REGISTROS_ES:NREG"
					width="500"
					height="300"
					titleKeyImageDelete="forms.exp.title.delete.data.register"
					target="workframe"
					showFrame="true"
					readonly="false"
					showDelete="false"
					jsShowFrame="show_SEARCH_SPAC_REGISTROS_ES_NREG"
				>
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:FREG)" property="FREG" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ASUNTO)" property="ASUNTO" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ID_INTERESADO)" property="IDTITULAR" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:INTERESADO)" property="IDENTIDADTITULAR" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ID_TRAMITE)" property="ID_TRAMITE" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" property="ORIGINO_EXPEDIENTE" />
					<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_NREG" id="JAVASCRIPT" property="accept_SEARCH_SPAC_REGISTROS_ES_NREG" />
				</ispac:htmlTextImageFrame>
			</c:when>
			<c:otherwise>
				<ispac:htmlText property="property(SPAC_REGISTROS_ES:NREG)"
					styleClass="input" size="20"
					readonly=''true'' />
			</c:otherwise>
		</c:choose>
</div>
<div id="label_SPAC_REGISTROS_ES:FREG" style="position: absolute; top: 35px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:FREG)" />:</div>
<div id="data_SPAC_REGISTROS_ES:FREG" style="position: absolute; top: 35px; left: 160px; width:100% ;" >
<nobr>
<ispac:htmlTextCalendar property="property(SPAC_REGISTROS_ES:FREG)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="14" maxlength="10" image=''<%= buttoncalendar %>'' format="dd/mm/yyyy" enablePast="true" >
</ispac:htmlTextCalendar>
</nobr>
</div>
<div id="label_SPAC_REGISTROS_ES:ASUNTO" style="position: absolute; top: 60px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:ASUNTO)" />:</div>
<div id="data_SPAC_REGISTROS_ES:ASUNTO" style="position: absolute; top: 60px; left: 160px; width:100% ;" >
<ispac:htmlTextarea property="property(SPAC_REGISTROS_ES:ASUNTO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" rows="2" cols="80" >
</ispac:htmlTextarea>
</div>
<div id="label_SPAC_REGISTROS_ES:INTERESADO" style="position: absolute; top: 100px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:INTERESADO)" />:</div>
<div id="data_SPAC_REGISTROS_ES:INTERESADO" style="position: absolute; top: 100px; left: 160px; width:100% ;" >
<ispac:htmlText property="property(SPAC_REGISTROS_ES:INTERESADO)" readonly="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="80" maxlength="128" >
</ispac:htmlText>
</div>
<div id="label_SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE" style="position: absolute; top: 125px; left: 10px; width: 150px;" class="formsTitleB">
<bean:write name="defaultForm" property="entityApp.label(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" />:</div>
<div id="data_SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE" style="position: absolute; top: 125px; left: 160px; width:100% ;" >
<nobr>
<ispac:htmlTextImageFrame property="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" readonly="true" readonlyTag="true" propertyReadonly="readonly" styleClass="input" styleClassReadonly="inputReadOnly" size="5" imageTabIndex="true" id="SEARCH_SPAC_REGISTROS_ES_ORIGINO_EXPEDIENTE" target="workframe" action="selectValue.do?entity=SPAC_TBL_009" image="img/search-mg.gif" titleKeyLink="title.link.data.selection" imageDelete="img/borrar.gif" titleKeyImageDelete="title.delete.data.selection" styleClassDeleteLink="tdlink" confirmDeleteKey="msg.delete.data.selection" showDelete="true" showFrame="true" width="640" height="480" >
<ispac:parameter name="SEARCH_SPAC_REGISTROS_ES_ORIGINO_EXPEDIENTE" id="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" property="VALOR" />
</ispac:htmlTextImageFrame>
</nobr>
</div>

<div id="label_SPAC_REGISTROS_ES:ID_TRAMITE" style="position: absolute; top: 150px; left: 10px; width: 150px;" class="formsTitleB">
<logic:notEmpty name="defaultForm" property="property(SPAC_REGISTROS_ES:ID_TRAMITE)">
	<bean:define id="_stageId" name="stageId"/>
	<c:url value="showTask.do" var="_link">
		<c:param name="stagePcdIdActual" value=''${requestScope.stagePcdId}''/>
		<c:param name="taskId"><bean:write name="defaultForm" property="entityApp.property(SPAC_REGISTROS_ES:ID_TRAMITE)"/></c:param>
		<%-- id en spac_dt_tramites --%>
		<c:param name="key"><bean:write name="defaultForm" property="entityApp.property(KEY)"/></c:param>
	</c:url>
	<a href=''<c:out value="${_link}"/>'' class="formaction">
		<nobr><bean:message key="registro.task.link"/></nobr>
	</a>
</logic:notEmpty>
</div>
</div>
</ispac:tab></ispac:tabs>

<script language=''JavaScript'' type=''text/javascript''><!--
	document.getElementById(''blockSave'').style.display=''none'';
	<%--
	Se permite eliminar el vinculo del apunto de registro con el expediente en los casos en los que el apunte de registro no anexa documentos, es decir, no incorpora
	documentos al expediente,ya que de esta manera no va a poder ser eliminado desde un documento al no crearse.
	--%>
	object = document.getElementById(''blockDelete'');
	if(object != null && object != ''undefined''){
		valueBlockDelete = ''none'';
		<logic:empty name="defaultForm" property="property(SPAC_REGISTROS_ES:ID_TRAMITE)">
			<logic:equal name="defaultForm" property="property(SPAC_REGISTROS_ES:ORIGINO_EXPEDIENTE)" value="NO">
				valueBlockDelete = ''block'';
			</logic:equal>
		</logic:empty>
		object.style.display=valueBlockDelete;
	}

//-->
</script>'
, frm_version = 1
WHERE id = 6;


--
-- Recursos para las Tablas de Validación
--
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 104, 'es', 'SPAC_TBL_001', 'Unidades de plazo');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 105, 'es', 'SPAC_TBL_002', 'Roles de Terceros');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 109, 'es', 'SPAC_TBL_003', 'Formas de Terminación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 110, 'es', 'SPAC_TBL_004', 'Estados Administrativos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 111, 'es', 'SPAC_TBL_005', 'Tipos de Direccion de Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 112, 'es', 'SPAC_TBL_006', 'Tipos de Estados de Notificación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 113, 'es', 'SPAC_VLDTBL_SIST_PRODUCTORES', 'Sistemas Productores');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 114, 'es', 'SPAC_TBL_008', 'Estados de Firma');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 136, 'es', 'SPAC_TBL_009', 'Si/No');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 185, 'es', 'SPAC_VLDTBL_ACTS_FUNCS', 'Actividad/Función');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 187, 'es', 'SPAC_VLDTBL_MATS_COMP', 'Materias/Competencia');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 188, 'es', 'SPAC_VLDTBL_FORMA_INIC', 'Formas de Iniciación');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 189, 'es', 'SPAC_VLDTBL_EFEC_SLNCIO', 'Efecto del Silencio');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 190, 'es', 'SPAC_VLDTBL_RECURSOS', 'Recursos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 191, 'es', 'SPAC_VLDTBL_SIT_TLM', 'Situación Telemática');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 194, 'es', 'SPAC_VLDTBL_TIPOREG', 'Tipo Registro');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 195, 'es', 'SPAC_VLDTBL_TIPOS_DOCS', 'Tipos de Documentos');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 196, 'es', 'SPAC_VLDTBL_TIPOS_TRAM', 'Tipos de Trámites');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 108, 'es', 'SPAC_TBL_007', 'Tipos de Relaciones entre Expedientes');
INSERT INTO spac_ct_entidades_resources (id, id_ent, idioma, clave, valor) VALUES (nextval('spac_sq_id_entidadesresources'), 197, 'es', 'SPAC_VLDTBL_TIPO_NOTIF', 'Tipos de Notificación');

--SELECT pg_catalog.setval('spac_sq_id_entidadesresources', 300, true);


--
-- Tabla de información de versión
--

INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONBD', '6.4', current_timestamp);
INSERT INTO spac_infosistema (id, codigo, valor, fecha_actualizacion) VALUES (nextval('spac_sq_id_infosistema'), 'VERSIONAPP', '6.4', current_timestamp);


--
-- Tablas de validación
--

-- Tabla de validación para Unidades de Plazo
INSERT INTO spac_tbl_001 (id, valor, sustituto, vigente, orden) VALUES (1, '4', 'AÑOS', 1, 1);
INSERT INTO spac_tbl_001 (id, valor, sustituto, vigente, orden) VALUES (2, '2', 'DIAS LABORABLES', 1, 2);
INSERT INTO spac_tbl_001 (id, valor, sustituto, vigente, orden) VALUES (3, '3', 'MESES', 1, 3);
INSERT INTO spac_tbl_001 (id, valor, sustituto, vigente, orden) VALUES (4, '1', 'DIAS NATURALES', 1, 4);
SELECT pg_catalog.setval('spac_sq_spac_tbl_001', 5, true);

-- Tabla de validación para Roles de Terceros
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (1, 'INT', 'INTERESADO', 1, 1);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (2, 'REPR', 'REPRESENTANTE', 1, 2);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (3, 'LIC', 'LICITADOR', 1, 3);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (4, 'DENT', 'DENUNCIANTE', 1, 4);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (5, 'DENO', 'DENUNCIADO', 1, 5);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (6, 'PROP', 'PROPIETARIO', 1, 6);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (7, 'BENF', 'BENEFICIARIO', 1, 7);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (8, 'PPON', 'PROPONENTE', 1, 8);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (9, 'COMP', 'COMPETENTE', 1, 9);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (10, 'ARRE', 'ARRENDATARIO', 1, 10);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (11, 'EXPR', 'EXPROPIADOR', 1, 11);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (12, 'PRET', 'PRESIDENTE TITULAR', 1, 12);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (13, 'PRES', 'PRESIDENTE SUPLENTE', 1, 13);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (14, 'SECT', 'SECRETARIO TITULAR', 1, 14);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (15, 'SECS', 'SECRETARIO SUPLENTE', 1, 15);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (16, 'VOCT', 'VOCAL TITULAR', 1, 16);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (17, 'VOCS', 'VOCAL SUPLENTE', 1, 17);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (18, 'REPT', 'REPRESENTANTE SINDICAL TITULAR', 1, 18);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (19, 'REPS', 'REPRESENTANTE SINDICAL SUPLENTE', 1, 19);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (20, 'CATI', 'CAMBIO TITULAR', 1, 20);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (21, 'INTL', 'INTERLOCUTOR', 1, 21);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (22, 'ASET', 'ASESOR ESPECIALISTA TITULAR', 1, 22);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (23, 'ASES', 'ASESOR ESPECIALISTA SUPLENTE', 1, 23);
INSERT INTO spac_tbl_002 (id, valor, sustituto, vigente, orden) VALUES (24, 'COL', 'COLABORADOR', 1, 24);
SELECT pg_catalog.setval('spac_sq_spac_tbl_002', 25, true);

-- Tabla de validación para Formas de Terminación
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (1, 'A', 'ARCHIVAR', 1, 1);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (2, 'C', 'CADUCIDAD', 1, 2);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (3, 'D', 'DESESTIMAR', 1, 3);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (4, 'E', 'ESTIMAR', 1, 4);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (5, 'O', 'RENUNCIA/DESISTIMIENTO', 1, 5);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (6, 'S', 'SILENCIO', 1, 6);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (7, 'U', 'ACUERDO', 1, 7);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (8, 'L', 'DESIERTO', 1, 8);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (9, 'R', 'RESUELTO', 1, 9);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (10, 'J', 'EJECUTADO', 1, 10);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (11, 'F', 'ERROR', 1, 11);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (12, 'I', 'INADMITIR', 1, 12);
INSERT INTO spac_tbl_003 (id, valor, sustituto, vigente, orden) VALUES (13, 'H', 'INHIBICION', 1, 13);
SELECT pg_catalog.setval('spac_sq_spac_tbl_003', 14, false);

-- Tabla de validación para Estados Administrativos
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (1, 'PR', 'PRESENTACION', 1, 1);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (2, 'DI', 'DOCUMENTACION INCOMPLETA', 1, 2);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (3, 'DC', 'DOCUMENTACION COMPLETA', 1, 3);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (4, 'ES', 'ESTUDIO', 1, 4);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (5, 'DE', 'DESISTIMIENTO', 1, 5);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (6, 'VE', 'VISITADO Y EN ESTUDIO', 1, 6);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (7, 'IL', 'INFORMADO Y LISTO PARA COMISION', 1, 7);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (8, 'RS', 'RESOLUCION', 1, 8);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (9, 'RC', 'RECHAZADO', 1, 9);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (10, 'AP', 'APROBADO', 1, 10);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (11, 'RN', 'RENUNCIA', 1, 11);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (12, 'EJ', 'EJECUCION', 1, 12);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (13, 'CT', 'CERTIFICADO', 1, 13);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (14, 'PA', 'PAGADO', 1, 14);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (15, 'SA', 'SOLICITUD DE COBRO CON AVAL', 1, 15);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (16, 'SC', 'SOLICITUD DE COBRO CUMPLIMIENTO DE OBLIGACIONES', 1, 16);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (17, 'AR', 'ARCHIVADO', 1, 17);
INSERT INTO spac_tbl_004 (id, valor, sustituto, vigente, orden) VALUES (18, 'CR', 'CERRADO', 1, 18);
SELECT pg_catalog.setval('spac_sq_spac_tbl_004', 19, false);

-- Tabla de validación para Direcciones de Notificación
INSERT INTO spac_tbl_005 (id, valor, sustituto, vigente, orden) VALUES (1, 'P', 'Postal', 1, 1);
INSERT INTO spac_tbl_005 (id, valor, sustituto, vigente, orden) VALUES (2, 'T', 'Telemática', 1, 2);
SELECT pg_catalog.setval('spac_sq_spac_tbl_005', 3, false);

-- Tabla de validación para Estados de Notificación
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (1, 'PE', 'Pendiente', 1, 1);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (2, 'PR', 'En Proceso', 1, 2);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (3, 'OK', 'OK', 1, 3);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (4, 'CA', 'Caducada', 1, 4);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (5, 'RE', 'Rechazada', 1, 5);
INSERT INTO spac_tbl_006 (id, valor, sustituto, vigente, orden) VALUES (6, 'ER', 'Error', 1, 6);
SELECT pg_catalog.setval('spac_sq_spac_tbl_006', 7, false);

-- Tabla de validación para Estados de Firma
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (1, '00', 'Sin firma', 1, 1);
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (2, '01', 'Pendiente firma', 1, 2);
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (3, '02', 'Firmado', 1, 3);
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (4, '03', 'Firmado con reparos', 1, 4);
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (5, '04', 'Rechazado', 1, 5);
INSERT INTO spac_tbl_008 (id, valor, sustituto, vigente, orden) VALUES (6, '05', 'Pendiente circuito de firma', 1, 6);
SELECT pg_catalog.setval('spac_sq_spac_tbl_008', 7, false);

-- Tabla de validación para Valores SI/NO
INSERT INTO spac_tbl_009 (id, valor, vigente, orden) VALUES (1, 'SI', 1, 1);
INSERT INTO spac_tbl_009 (id, valor, vigente, orden) VALUES (2, 'NO', 1, 2);
SELECT pg_catalog.setval('spac_sq_spac_tbl_009', 3, true);

-- Tabla de validación para actividades y funciones
INSERT INTO spac_vldtbl_acts_funcs (id, valor, sustituto, vigente, orden) VALUES (1, 'SU', 'SUBVENCIÓN', 1, 1);
INSERT INTO spac_vldtbl_acts_funcs (id, valor, sustituto, vigente, orden) VALUES (2, 'CO', 'CONTRATACIÓN', 0, 2);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_acts_funcs', 3, true);

-- Tabla de validación para efectos del silencio
INSERT INTO spac_vldtbl_efec_slncio (id, valor, sustituto, vigente, orden) VALUES (1, 'E', 'ESTIMATORIO', 1, 1);
INSERT INTO spac_vldtbl_efec_slncio (id, valor, sustituto, vigente, orden) VALUES (2, 'D', 'DESESTIMATORIO', 1, 2);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_efec_slnc', 3, true);

-- Tabla de validación para formas de inicio
INSERT INTO spac_vldtbl_forma_inic (id, valor, sustituto, vigente, orden) VALUES (1, 'I', 'A INSTANCIA DE PARTE', 1, 1);
INSERT INTO spac_vldtbl_forma_inic (id, valor, sustituto, vigente, orden) VALUES (2, 'O', 'DE OFICIO', 1, 2);
INSERT INTO spac_vldtbl_forma_inic (id, valor, sustituto, vigente, orden) VALUES (3, 'A', 'AMBAS', 1, 3);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_forma_inic', 4, true);

-- Tabla de validación para materias
INSERT INTO spac_vldtbl_mats_comp (id, valor, sustituto, vigente, orden) VALUES (1, 'DE', 'DEPORTES', 1, 1);
INSERT INTO spac_vldtbl_mats_comp (id, valor, sustituto, vigente, orden) VALUES (2, 'CU', 'CULTURA', 1, 2);
INSERT INTO spac_vldtbl_mats_comp (id, valor, sustituto, vigente, orden) VALUES (3, 'VI', 'VIVIENDA', 1, 3);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_mats_comp', 4, true);

-- Tabla de validación para recursos
INSERT INTO spac_vldtbl_recursos (id, valor, sustituto, vigente, orden) VALUES (1, 'SU', 'SUPLICA', 1, 1);
INSERT INTO spac_vldtbl_recursos (id, valor, sustituto, vigente, orden) VALUES (2, 'CA', 'CONTENCIOSO ADMINISTRATIVO', 1, 2);
INSERT INTO spac_vldtbl_recursos (id, valor, sustituto, vigente, orden) VALUES (3, 'PR', 'POTESTATIVO DE RESOLUCIÓN', 1, 3);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_recursos', 4, true);

-- Tabla de validación para los sistemas productores de los procedimientos
INSERT INTO SPAC_VLDTBL_SIST_PRODUCTORES (id, valor, sustituto, vigente, orden) VALUES (1, '00', 'Ninguno', 1, 1);
INSERT INTO SPAC_VLDTBL_SIST_PRODUCTORES (id, valor, sustituto, vigente, orden) VALUES (2, '04', 'iSPAC', 1, 2);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_sist_prod', 3, false);

-- Tabla de validación para los sistemas de teletramitación
INSERT INTO spac_vldtbl_sit_tlm (id, valor, sustituto, vigente, orden) VALUES (1, 'NO', 'NADA', 1, 1);
INSERT INTO spac_vldtbl_sit_tlm (id, valor, sustituto, vigente, orden) VALUES (2, 'CO', 'CONSULTA', 1, 2);
INSERT INTO spac_vldtbl_sit_tlm (id, valor, sustituto, vigente, orden) VALUES (3, 'TL', 'TELETRAMITACIÓN', 1, 3);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_sit_tlm', 4, true);

-- Tabla de validación para los tipos de registro
INSERT INTO spac_vldtbl_tiporeg (id, valor, vigente, orden) VALUES (1, 'ENTRADA', 1, 1);
INSERT INTO spac_vldtbl_tiporeg (id, valor, vigente, orden) VALUES (2, 'SALIDA', 1, 2);
INSERT INTO spac_vldtbl_tiporeg (id, valor, vigente, orden) VALUES (3, 'NINGUNO', 1, 3);
SELECT pg_catalog.setval('spac_sq_spac_vldtbl_tiporeg', 4, true);

-- Tabla de validación para los tipos de documentos
INSERT INTO spac_vldtbl_tipos_docs (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_docs'), 'Documentos de Decisión', 1, 1);
INSERT INTO spac_vldtbl_tipos_docs (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_docs'), 'Documentos de Juicio', 1, 2);
INSERT INTO spac_vldtbl_tipos_docs (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_docs'), 'Documentos de Transmisión', 1, 3);
INSERT INTO spac_vldtbl_tipos_docs (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_docs'), 'Documentos de Constancia', 1, 4);

-- Tabla de validación para los tipos de trámites
INSERT INTO spac_vldtbl_tipos_tram (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_tram'), 'Constancia', 1, 1);
INSERT INTO spac_vldtbl_tipos_tram (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_tram'), 'Juicio', 1, 2);
INSERT INTO spac_vldtbl_tipos_tram (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_tram'), 'Decisión', 1, 3);
INSERT INTO spac_vldtbl_tipos_tram (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_vldtbl_tipos_tram'), 'Transmisión', 1, 4);

-- Tabla de validación para los tipos de relaciones entre expediente
INSERT INTO spac_tbl_007 (id, valor, vigente, orden) VALUES (nextval('spac_sq_spac_tbl_007'), 'Solicitud/Convocatoria', 1, 1);

--Tabla de validacion tipos de notificación
INSERT INTO spac_vldtbl_tipo_notif (id, valor, sustituto, vigente, orden) VALUES (1, 'EM', 'E-MAIL', 1, 1);
INSERT INTO spac_vldtbl_tipo_notif (id, valor, sustituto, vigente, orden) VALUES (2, 'SM', 'SMS', 1, 2);
SELECT pg_catalog.setval('spac_sq_vldtbl_tipo_notif', 3, true);
----------------
-- PUBLICADOR --
----------------

-- pub_acciones
INSERT INTO pub_acciones (id, nombre, clase, activa, descripcion, tipo) VALUES (1, 'TestAction', 'ieci.tdw.ispac.ispacpublicador.business.action.test.TestAction', 1, 'Acción de Prueba', 1);
SELECT pg_catalog.setval('pub_sq_id_acciones', 2, true);

-- pub_aplicaciones_hitos
INSERT INTO pub_aplicaciones_hitos (id, nombre, activa) VALUES (1, 'PUBLICADOR', 1);
SELECT pg_catalog.setval('pub_sq_id_aplicaciones_hitos', 2, false);

-- pub_condiciones
INSERT INTO pub_condiciones (id, nombre, clase, descripcion) VALUES (1, 'TestCondition', 'ieci.tdw.ispac.ispacpublicador.business.condition.test.TestCondition', 'Condición de prueba');
SELECT pg_catalog.setval('pub_sq_id_condiciones', 2, false);

-- Inicializar el contador del número de expediente
INSERT INTO spac_numexp_contador(anio, contador, formato, id_pcd) VALUES (2008, 1, 'EXP$YR$/$NM{6}$', -1);

---
--- Insertamos los informes iniciales
---


insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo, filas, columnas)
values(nextval('spac_sq_id_ctinformes'), 'Etiqueta',
 'Informe Etiquetas 4x2',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="etiqueta"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="588"
		 columnSpacing="0"
		 leftMargin="5"
		 rightMargin="2"
		 topMargin="5"
		 bottomMargin="2"
		 whenNoDataType="NoPages"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="NUM_EXP" isForPrompting="true" class="java.lang.String"/>
	<parameter name="IMAGES_REPOSITORY_PATH" isForPrompting="true" class="java.lang.String"/>
	<parameter name="POSICION" isForPrompting="true" class="java.lang.String"/>
	<queryString><![CDATA[SELECT
     numexp  ,
     asunto ,
     fapertura ,
     utramitadora
FROM
     spac_expedientes
WHERE
     numexp = $P{NUM_EXP}]]></queryString>

	<field name="numexp" class="java.lang.String"/>
	<field name="asunto" class="java.lang.String"/>
	<field name="fapertura" class="java.sql.Timestamp"/>
	<field name="utramitadora" class="java.lang.String"/>

	<variable name="field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Num. Exp"]]></variableExpression>
	</variable>
	<variable name="es.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Num. Exp"]]></variableExpression>
	</variable>
	<variable name="ca.field.numexp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<variableExpression><![CDATA["Num. Exp"]]></variableExpression>
	</variable>
	<variable name="eu.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Esp.-zk"]]></variableExpression>
	</variable>
	<variable name="gl.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Num. Exp"]]></variableExpression>
	</variable>
	<variable name="field.desc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Desc."]]></variableExpression>
	</variable>
	<variable name="es.field.desc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Desc."]]></variableExpression>
	</variable>
	<variable name="ca.field.desc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Desc."]]></variableExpression>
	</variable>
	<variable name="eu.field.desc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Desk."]]></variableExpression>
	</variable>
	<variable name="gl.field.desc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Desc."]]></variableExpression>
	</variable>
	<variable name="field.finicio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Inicio"]]></variableExpression>
	</variable>
	<variable name="es.field.finicio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Inicio"]]></variableExpression>
	</variable>
	<variable name="ca.field.finicio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. inici"]]></variableExpression>
	</variable>
	<variable name="eu.field.finicio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Hasiera-data"]]></variableExpression>
	</variable>
	<variable name="gl.field.finicio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. inicio"]]></variableExpression>
	</variable>
	<variable name="field.utramitadora" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Tramit."]]></variableExpression>
	</variable>
	<variable name="es.field.utramitadora" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Tramit."]]></variableExpression>
	</variable>
	<variable name="ca.field.utramitadora" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Tramit."]]></variableExpression>
	</variable>
	<variable name="eu.field.utramitadora" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["U. T."]]></variableExpression>
	</variable>
	<variable name="gl.field.utramitadora" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Tramit."]]></variableExpression>
	</variable>
	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<variableExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"field.numexp", "field.desc", "field.finicio", "field.utramitadora"},
		new String[]{"", "es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{field.numexp}, $V{field.desc},$V{field.finicio},$V{field.utramitadora}},
				new String[]{$V{es.field.numexp}, $V{es.field.desc},$V{es.field.finicio},$V{es.field.utramitadora}},
				new String[]{$V{ca.field.numexp}, $V{ca.field.desc},$V{ca.field.finicio},$V{ca.field.utramitadora}},
				new String[]{$V{eu.field.numexp}, $V{eu.field.desc},$V{eu.field.finicio},$V{eu.field.utramitadora}},
				new String[]{$V{gl.field.numexp}, $V{gl.field.desc},$V{gl.field.finicio},$V{gl.field.utramitadora}}
			      }
	    )]]></variableExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</title>
		<pageHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageHeader>
		<columnHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnHeader>
		<detail>
			<band height="745"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="668"
						width="185"
						height="20"
						key="textField-58"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="648"
						width="185"
						height="20"
						key="textField-53"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="354"
						y="494"
						width="185"
						height="20"
						key="staticText-101"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="474"
						width="185"
						height="20"
						key="textField-51"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="297"
						width="185"
						height="20"
						key="textField-56"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="277"
						width="185"
						height="20"
						key="textField-49"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="95"
						width="185"
						height="20"
						key="textField-54"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="75"
						width="185"
						height="20"
						key="textField-47"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="35"
						width="65"
						height="20"
						key="staticText-107"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="35"
						width="120"
						height="20"
						key="textField-1">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="55"
						width="65"
						height="20"
						key="staticText-108">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="55"
						width="120"
						height="20"
						key="textField-2">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="75"
						width="185"
						height="20"
						key="staticText-109"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="75"
						width="120"
						height="20"
						key="textField-3"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="95"
						width="185"
						height="20"
						key="staticText-110"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="95"
						width="120"
						height="20"
						key="textField-4"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("1"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="35"
						width="120"
						height="20"
						key="textField-5">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="55"
						width="120"
						height="20"
						key="textField-6">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="75"
						width="120"
						height="20"
						key="textField-7"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="95"
						width="120"
						height="20"
						key="textField-8"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="55"
						width="65"
						height="20"
						key="textField-40">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="35"
						width="65"
						height="20"
						key="textField-33">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("2"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="237"
						width="65"
						height="20"
						key="textField-34">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="237"
						width="120"
						height="20"
						key="textField-9">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="257"
						width="65"
						height="20"
						key="textField-41">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="257"
						width="120"
						height="20"
						key="textField-10">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="277"
						width="185"
						height="20"
						key="textField-48"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="277"
						width="120"
						height="20"
						key="textField-11"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="297"
						width="185"
						height="20"
						key="textField-55"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="297"
						width="120"
						height="20"
						key="textField-12"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("3"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="237"
						width="120"
						height="20"
						key="textField-13">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="257"
						width="120"
						height="20"
						key="textField-14">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="277"
						width="120"
						height="20"
						key="textField-15"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="297"
						width="120"
						height="20"
						key="textField-16"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="257"
						width="65"
						height="20"
						key="textField-42">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="237"
						width="65"
						height="20"
						key="textField-35">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("4"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="434"
						width="65"
						height="20"
						key="textField-36">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="434"
						width="120"
						height="20"
						key="textField-17">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="454"
						width="65"
						height="20"
						key="textField-43">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="454"
						width="120"
						height="20"
						key="textField-18">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="474"
						width="185"
						height="20"
						key="textField-50"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="474"
						width="120"
						height="20"
						key="textField-19"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<staticText>
					<reportElement
						x="55"
						y="494"
						width="185"
						height="20"
						key="staticText-122"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<text><![CDATA[O. Tramit.]]></text>
				</staticText>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="494"
						width="120"
						height="20"
						key="textField-20"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("5"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="434"
						width="120"
						height="20"
						key="textField-21">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="454"
						width="120"
						height="20"
						key="textField-22">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000" bottomPadding="5"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="474"
						width="120"
						height="20"
						key="textField-23"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="494"
						width="120"
						height="20"
						key="textField-24"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="454"
						width="65"
						height="20"
						key="textField-44">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="434"
						width="65"
						height="20"
						key="textField-37">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("6"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="608"
						width="65"
						height="20"
						key="textField-38">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="608"
						width="120"
						height="20"
						key="textField-25">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="628"
						width="65"
						height="20"
						key="textField-45">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="628"
						width="120"
						height="20"
						key="textField-26">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="648"
						width="185"
						height="20"
						key="textField-52"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.finicio")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="648"
						width="120"
						height="20"
						key="textField-27"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="55"
						y="668"
						width="185"
						height="20"
						key="textField-57"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.utramitadora")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="120"
						y="668"
						width="120"
						height="20"
						key="textField-28"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("7"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="608"
						width="120"
						height="20"
						key="textField-29">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="628"
						width="120"
						height="20"
						key="textField-30">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="648"
						width="120"
						height="20"
						key="textField-31"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[ieci.tdw.ispac.ispaclib.utils.DateUtil.format($F{fapertura})]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="419"
						y="668"
						width="120"
						height="20"
						key="textField-32"
						positionType="Float">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{utramitadora}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="628"
						width="65"
						height="20"
						key="textField-46">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.desc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="354"
						y="608"
						width="65"
						height="20"
						key="textField-39">
							<printWhenExpression><![CDATA[new Boolean($P{POSICION}.equals("8"))]]></printWhenExpression>
						</reportElement>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="11" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>' ,
current_timestamp, 1, 4, 2);

insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'), 'Ficha Expediente',
 'Informe Ficha Expediente',
'<?xml version="1.0" encoding="ISO-8859-1"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="fichaExpediente"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Landscape"
		 pageWidth="842"
		 pageHeight="595"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="20"
		 rightMargin="20"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="NoPages"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="ISO-8859-1" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="NUM_EXP" isForPrompting="true" class="java.lang.String"/>
	<parameter name="IMAGES_REPOSITORY_PATH" isForPrompting="true" class="java.lang.String"/>
	<queryString><![CDATA[SELECT
     spac_expedientes.numexp,
     spac_expedientes.asunto,
     spac_expedientes.poblacion,
     spac_expedientes.localizacion,
     spac_expedientes.municipio,
     spac_expedientes.fapertura,
     spac_expedientes.fcierre,
     spac_expedientes.identidadtitular,
     spac_expedientes.nifciftitular,
      spac_dt_intervinientes.nombre ,
     spac_dt_intervinientes.ndoc,
    spac_dt_intervinientes.localidad,
   spac_dt_intervinientes.email,
   spac_dt_intervinientes.tfno_fijo,
   spac_dt_intervinientes.tfno_movil
FROM
SPAC_EXPEDIENTES left outer join
     SPAC_DT_INTERVINIENTES ON SPAC_EXPEDIENTES.numexp = SPAC_DT_INTERVINIENTES.numexp
WHERE
     spac_expedientes.numexp = $P{NUM_EXP}]]></queryString>

	<field name="numexp" class="java.lang.String"/>
	<field name="asunto" class="java.lang.String"/>
	<field name="poblacion" class="java.lang.String"/>
	<field name="localizacion" class="java.lang.String"/>
	<field name="municipio" class="java.lang.String"/>
	<field name="fapertura" class="java.sql.Timestamp"/>
	<field name="fcierre" class="java.sql.Timestamp"/>
	<field name="identidadtitular" class="java.lang.String"/>
	<field name="nifciftitular" class="java.lang.String"/>
	<field name="nombre" class="java.lang.String"/>
	<field name="ndoc" class="java.lang.String"/>
	<field name="localidad" class="java.lang.String"/>
	<field name="email" class="java.lang.String"/>
	<field name="tfno_fijo" class="java.lang.String"/>
	<field name="tfno_movil" class="java.lang.String"/>

	<variable name="title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Ficha del expediente"]]></variableExpression>
	</variable>
	<variable name="es.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Ficha del expediente"]]></variableExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Fitxa de l''expedient"]]></variableExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Espedientearen fitxa"]]></variableExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Ficha do expediente"]]></variableExpression>
	</variable>
	<variable name="field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Expediente"]]></variableExpression>
	</variable>
	<variable name="es.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Expediente"]]></variableExpression>
	</variable>
	<variable name="ca.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Expedient"]]></variableExpression>
	</variable>
	<variable name="eu.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Espediente-zk"]]></variableExpression>
	</variable>
	<variable name="gl.field.numexp" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Expediente"]]></variableExpression>
	</variable>
	<variable name="field.asunto" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Asunto"]]></variableExpression>
	</variable>
	<variable name="es.field.asunto" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Asunto"]]></variableExpression>
	</variable>
	<variable name="ca.field.asunto" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Assumpte"]]></variableExpression>
	</variable>
	<variable name="eu.field.asunto" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Gaia"]]></variableExpression>
	</variable>
	<variable name="gl.field.asunto" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Asunto"]]></variableExpression>
	</variable>
	<variable name="field.fapertura" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Apertura"]]></variableExpression>
	</variable>
	<variable name="es.field.fapertura" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Apertura"]]></variableExpression>
	</variable>
	<variable name="ca.field.fapertura" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. d''obertura"]]></variableExpression>
	</variable>
	<variable name="eu.field.fapertura" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Irekitze-data"]]></variableExpression>
	</variable>
	<variable name="gl.field.fapertura" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Apertura"]]></variableExpression>
	</variable>
	<variable name="field.fcierre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Cierre"]]></variableExpression>
	</variable>
	<variable name="es.field.fcierre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["F. Cierre"]]></variableExpression>
	</variable>
	<variable name="ca.field.fcierre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. de tancament"]]></variableExpression>
	</variable>
	<variable name="eu.field.fcierre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Ixte-data"]]></variableExpression>
	</variable>
	<variable name="gl.field.fcierre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["D. Peche"]]></variableExpression>
	</variable>
	<variable name="title.titular" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Titular"]]></variableExpression>
	</variable>
	<variable name="es.title.titular" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Titular"]]></variableExpression>
	</variable>
	<variable name="ca.title.titular" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Titular"]]></variableExpression>
	</variable>
	<variable name="eu.title.titular" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Titularra"]]></variableExpression>
	</variable>
	<variable name="gl.title.titular" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Titular"]]></variableExpression>
	</variable>
	<variable name="field.nombre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nombre"]]></variableExpression>
	</variable>
	<variable name="es.field.nombre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nombre"]]></variableExpression>
	</variable>
	<variable name="ca.field.nombre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nom"]]></variableExpression>
	</variable>
	<variable name="eu.field.nombre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Izena"]]></variableExpression>
	</variable>
	<variable name="gl.field.nombre" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nome"]]></variableExpression>
	</variable>
	<variable name="field.ndoc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Doc"]]></variableExpression>
	</variable>
	<variable name="es.field.ndoc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Doc"]]></variableExpression>
	</variable>
	<variable name="ca.field.ndoc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Doc"]]></variableExpression>
	</variable>
	<variable name="eu.field.ndoc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Dok"]]></variableExpression>
	</variable>
	<variable name="gl.field.ndoc" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Nº Doc"]]></variableExpression>
	</variable>
	<variable name="title.participantes" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Listado de Participantes"]]></variableExpression>
	</variable>
	<variable name="es.title.participantes" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Listado de Participantes"]]></variableExpression>
	</variable>
	<variable name="ca.title.participantes" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Llista de Participants"]]></variableExpression>
	</variable>
	<variable name="eu.title.participantes" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Parte-hartzaileen zerrenda"]]></variableExpression>
	</variable>
	<variable name="gl.title.participantes" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Listado do Participantes"]]></variableExpression>
	</variable>
	<variable name="field.municipio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Municipio"]]></variableExpression>
	</variable>
	<variable name="es.field.municipio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Municipio"]]></variableExpression>
	</variable>
	<variable name="ca.field.municipio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Municipi"]]></variableExpression>
	</variable>
	<variable name="eu.field.municipio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Udalerria"]]></variableExpression>
	</variable>
	<variable name="gl.field.municipio" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Municipio"]]></variableExpression>
	</variable>
	<variable name="field.localizacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localización"]]></variableExpression>
	</variable>
	<variable name="es.field.localizacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localización"]]></variableExpression>
	</variable>
	<variable name="ca.field.localizacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localització"]]></variableExpression>
	</variable>
	<variable name="eu.field.localizacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Aurkitzea"]]></variableExpression>
	</variable>
	<variable name="gl.field.localizacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localización"]]></variableExpression>
	</variable>
	<variable name="field.poblacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Población"]]></variableExpression>
	</variable>
	<variable name="es.field.poblacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Población"]]></variableExpression>
	</variable>
	<variable name="ca.field.poblacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Població"]]></variableExpression>
	</variable>
	<variable name="eu.field.poblacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Populazioa"]]></variableExpression>
	</variable>
	<variable name="gl.field.poblacion" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Poboación"]]></variableExpression>
	</variable>
	<variable name="field.localidad" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localidad"]]></variableExpression>
	</variable>
	<variable name="es.field.localidad" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localidad"]]></variableExpression>
	</variable>
	<variable name="ca.field.localidad" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localitat"]]></variableExpression>
	</variable>
	<variable name="eu.field.localidad" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Herria"]]></variableExpression>
	</variable>
	<variable name="gl.field.localidad" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Localidade"]]></variableExpression>
	</variable>
	<variable name="field.email" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["E-mail"]]></variableExpression>
	</variable>
	<variable name="es.field.email" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["E-mail"]]></variableExpression>
	</variable>
	<variable name="ca.field.email" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["E-mail"]]></variableExpression>
	</variable>
	<variable name="eu.field.email" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["E-mail"]]></variableExpression>
	</variable>
	<variable name="gl.field.email" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["E-mail"]]></variableExpression>
	</variable>
	<variable name="field.tfnofijo" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfno Fijo"]]></variableExpression>
	</variable>
	<variable name="es.field.tfnofijo" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfno Fijo"]]></variableExpression>
	</variable>
	<variable name="ca.field.tfnofijo" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfn Fix"]]></variableExpression>
	</variable>
	<variable name="eu.field.tfnofijo" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tel Finkoa"]]></variableExpression>
	</variable>
	<variable name="gl.field.tfnofijo" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tel Fixo"]]></variableExpression>
	</variable>
	<variable name="field.tfnomovil" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfno Móvil"]]></variableExpression>
	</variable>
	<variable name="es.field.tfnomovil" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfno Móvil"]]></variableExpression>
	</variable>
	<variable name="ca.field.tfnomovil" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tfn Móbil"]]></variableExpression>
	</variable>
	<variable name="eu.field.tfnomovil" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tel. Mugikorra"]]></variableExpression>
	</variable>
	<variable name="gl.field.tfnomovil" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Tel Móbil"]]></variableExpression>
	</variable>
	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "field.numexp", "field.asunto", "field.fapertura", "field.fcierre", "field.municipio", "title.titular", "field.nombre", "field.ndoc", "title.participantes", "field.localizacion", "field.poblacion", "field.localidad", "field.email", "field.tfnofijo", "field.tfnomovil"},
		new String[]{"", "es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{title},$V{field.numexp},$V{field.asunto},$V{field.fapertura},$V{field.fcierre},$V{field.municipio},$V{title.titular},$V{field.nombre},$V{field.ndoc},$V{title.participantes},$V{field.localizacion},$V{field.poblacion},$V{field.localidad},$V{field.email},$V{field.tfnofijo},$V{field.tfnomovil}},
						new String[]{$V{es.title},$V{es.field.numexp},$V{es.field.asunto},$V{es.field.fapertura},$V{es.field.fcierre},$V{es.field.municipio},$V{es.title.titular},$V{es.field.nombre},$V{es.field.ndoc},$V{es.title.participantes},$V{es.field.localizacion},$V{es.field.poblacion},$V{es.field.localidad},$V{es.field.email},$V{es.field.tfnofijo},$V{es.field.tfnomovil}},
						new String[]{$V{ca.title},$V{ca.field.numexp},$V{ca.field.asunto},$V{ca.field.fapertura},$V{ca.field.fcierre},$V{ca.field.municipio},$V{ca.title.titular},$V{ca.field.nombre},$V{ca.field.ndoc},$V{ca.title.participantes},$V{ca.field.localizacion},$V{ca.field.poblacion},$V{ca.field.localidad},$V{ca.field.email},$V{ca.field.tfnofijo},$V{ca.field.tfnomovil}},
						new String[]{$V{eu.title},$V{eu.field.numexp},$V{eu.field.asunto},$V{eu.field.fapertura},$V{eu.field.fcierre},$V{eu.field.municipio},$V{eu.title.titular},$V{eu.field.nombre},$V{eu.field.ndoc},$V{eu.title.participantes},$V{eu.field.localizacion},$V{eu.field.poblacion},$V{eu.field.localidad},$V{eu.field.email},$V{eu.field.tfnofijo},$V{eu.field.tfnomovil}},
						new String[]{$V{gl.title},$V{gl.field.numexp},$V{gl.field.asunto},$V{gl.field.fapertura},$V{gl.field.fcierre},$V{gl.field.municipio},$V{gl.title.titular},$V{gl.field.nombre},$V{gl.field.ndoc},$V{gl.title.participantes},$V{gl.field.localizacion},$V{gl.field.poblacion},$V{gl.field.localidad},$V{gl.field.email},$V{gl.field.tfnofijo},$V{gl.field.tfnomovil}}
			      }
	    )]]></initialValueExpression>
	</variable>

		<group  name="numexp" >
			<groupExpression><![CDATA[$F{numexp}]]></groupExpression>
			<groupHeader>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="139"
						height="27"
						forecolor="#FFFFFF"
						backcolor="#000000"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.numexp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="139"
						y="0"
						width="663"
						height="27"
						forecolor="#FFFFFF"
						backcolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{numexp}]]></textFieldExpression>
				</textField>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="asunto" >
			<groupExpression><![CDATA[$F{asunto}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="139"
						height="27"
						backcolor="#CCCCFF"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.asunto")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="139"
						y="0"
						width="663"
						height="27"
						backcolor="#CCCCFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{asunto}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="fapertura" >
			<groupExpression><![CDATA[$F{fapertura}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="139"
						height="27"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.fapertura")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="0"
						width="663"
						height="27"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.sql.Timestamp"><![CDATA[$F{fapertura}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="fcierre" >
			<groupExpression><![CDATA[$F{fcierre}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="139"
						height="27"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.fcierre")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="0"
						width="663"
						height="27"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.sql.Timestamp"><![CDATA[$F{fcierre}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="municipio" >
			<groupExpression><![CDATA[$F{municipio}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="140"
						y="-1"
						width="662"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{municipio}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="0"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.municipio")]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="localizacion" >
			<groupExpression><![CDATA[$F{localizacion}]]></groupExpression>
			<groupHeader>
			<band height="28"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="141"
						y="-1"
						width="661"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{localizacion}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="-1"
						y="-1"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.localizacion")]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="poblacion" >
			<groupExpression><![CDATA[$F{poblacion}]]></groupExpression>
			<groupHeader>
			<band height="45"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="139"
						y="-1"
						width="663"
						height="29"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{poblacion}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="-1"
						width="139"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="16"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.poblacion")]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="27"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="Titular" >
			<groupExpression><![CDATA[$V{PAGE_NUMBER}]]></groupExpression>
			<groupHeader>
			<band height="50"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="1"
						y="0"
						width="799"
						height="30"
						backcolor="#CCCCCC"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="18" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title.titular")]]></textFieldExpression>
				</textField>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="nifciftitular" >
			<groupExpression><![CDATA[$F{nifciftitular}]]></groupExpression>
			<groupHeader>
			<band height="64"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="28"
						width="136"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.ndoc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="138"
						y="28"
						width="662"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nifciftitular}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="137"
						y="0"
						width="663"
						height="28"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{identidadtitular}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="0"
						width="136"
						height="28"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.nombre")]]></textFieldExpression>
				</textField>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<group  name="Lista participantes" >
			<groupExpression><![CDATA[$P{REPORT_PARAMETERS_MAP}]]></groupExpression>
			<groupHeader>
			<band height="57"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Opaque"
						x="1"
						y="0"
						width="799"
						height="30"
						backcolor="#CCCCCC"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="18" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title.participantes")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="3"
						y="30"
						width="122"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.ndoc")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="265"
						y="30"
						width="139"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.localidad")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="404"
						y="30"
						width="133"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.email")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="536"
						y="30"
						width="139"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.tfnofijo")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="675"
						y="30"
						width="124"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.tfnomovil")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="126"
						y="30"
						width="139"
						height="20"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="14" isBold="false"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "field.nombre")]]></textFieldExpression>
				</textField>
			</band>
			</groupHeader>
			<groupFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
			</groupFooter>
		</group>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="53"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="8"
						width="800"
						height="1"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="51"
						width="800"
						height="1"
						key="line"
						positionType="FixRelativeToBottom"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="65"
						y="13"
						width="593"
						height="35"
						key="staticText"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="26" isBold="true"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</pageHeader>
		<columnHeader>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnHeader>
		<detail>
			<band height="30"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="126"
						y="0"
						width="137"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="0"
						width="122"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{ndoc}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="265"
						y="0"
						width="139"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{localidad}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="382"
						y="0"
						width="130"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{email}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="516"
						y="0"
						width="142"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{tfno_fijo}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="675"
						y="0"
						width="124"
						height="20"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{tfno_movil}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="26"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="516"
						y="6"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="342"
						y="6"
						width="170"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font size="10"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>' ,
current_timestamp ,1);

insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'),
'Expedientes ABIERTOS por procedimiento',
'Expedientes que se encuentran abiertos agrupados por procedimiento',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="expsAbiertosPorPcd"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="30"
		 rightMargin="30"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="AllSectionsNoDetail"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<queryString><![CDATA[SELECT P.NOMBRE, COUNT(*) AS COUNT
FROM 	SPAC_P_PROCEDIMIENTOS P,
	SPAC_PROCESOS R
WHERE 	P.ID = R.ID_PCD
    AND R.ESTADO = 1
GROUP BY P.NOMBRE]]></queryString>

	<field name="nombre" class="java.lang.String"/>
	<field name="count" class="java.lang.Integer"/>

	<variable name="title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes Abiertos por procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="es.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes Abiertos por procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[ca]Expedientes Abiertos por procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[eu]Expedientes Abiertos por procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[gl]Expedientes Abiertos por procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>
	</variable>
	<variable name="column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="es.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedients"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Espedienteak"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>


	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.exp"},
		new String[]{"","es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{title}, $V{column.pcd},$V{column.exp}},
				new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.exp}},
				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.exp}},
				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.exp}},
				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.exp}}
			      }
	    )
]]></initialValueExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="50"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="48"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="11"
						width="533"
						height="29"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center" verticalAlignment="Middle">
						<font size="25"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="9"  isSplitAllowed="true" >
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="8"
						width="535"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
		</pageHeader>
		<columnHeader>
			<band height="17"  isSplitAllowed="true" >
				<rectangle>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="535"
						height="17"
						forecolor="#000000"
						backcolor="#808080"
						key="rectangle"/>
					<graphicElement stretchType="NoStretch" pen="None"/>
				</rectangle>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="15"
						width="535"
						height="2"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#FFFFFF"
						key="column.pcd"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="0"
						width="106"
						height="17"
						forecolor="#FFFFFF"
						key="column.exp-1"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.exp")]]></textFieldExpression>
				</textField>
			</band>
		</columnHeader>
		<detail>
			<band height="17"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="16"
						width="535"
						height="0"
						forecolor="#808080"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#000000"
						key="procedimiento"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="-1"
						width="106"
						height="18"
						key="expedientes"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.Integer"><![CDATA[$F{count}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="326"
						y="3"
						width="170"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Transparent"
						x="499"
						y="3"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="5"
						width="534"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="6"
						width="209"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
current_timestamp,
3);

insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'),
'Expedientes CERRADOS por procedimiento',
'Expedientes que se encuentran cerrados agrupados por procedimiento',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="expsCerradosPorPcd"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="30"
		 rightMargin="30"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="AllSectionsNoDetail"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<queryString><![CDATA[SELECT P.NOMBRE, COUNT(*)
FROM 	SPAC_P_PROCEDIMIENTOS P,
	SPAC_PROCESOS R
WHERE 	P.ID = R.ID_PCD
    AND R.ESTADO = 2
GROUP BY P.NOMBRE]]></queryString>

	<field name="nombre" class="java.lang.String"/>
	<field name="count" class="java.lang.Integer"/>

	<variable name="es.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["Expedientes Cerrados por procedimiento"]]></variableExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["[ca]Expedientes Cerrados por procedimiento"]]></variableExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["[eu]Expedientes Cerrados por procedimiento"]]></variableExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="None" calculation="Nothing">
		<variableExpression><![CDATA["[gl]Expedientes Cerrados por procedimiento"]]></variableExpression>
	</variable>
	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>
	</variable>
	<variable name="es.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedients"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Espedienteak"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>

	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.exp"},
		new String[]{"es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.exp}},
				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.exp}},
				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.exp}},
				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.exp}}
			      }
	    )
]]></initialValueExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="50"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="48"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="11"
						width="533"
						height="29"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center" verticalAlignment="Middle">
						<font size="25"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="9"  isSplitAllowed="true" >
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="8"
						width="535"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
		</pageHeader>
		<columnHeader>
			<band height="17"  isSplitAllowed="true" >
				<rectangle>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="535"
						height="17"
						forecolor="#000000"
						backcolor="#808080"
						key="rectangle"/>
					<graphicElement stretchType="NoStretch" pen="None"/>
				</rectangle>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="15"
						width="535"
						height="2"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#FFFFFF"
						key="column.pcd"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="0"
						width="106"
						height="17"
						forecolor="#FFFFFF"
						key="column.exp-1"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.exp")]]></textFieldExpression>
				</textField>
			</band>
		</columnHeader>
		<detail>
			<band height="17"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="16"
						width="535"
						height="0"
						forecolor="#808080"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#000000"
						key="procedimiento"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="-1"
						width="106"
						height="18"
						key="expedientes"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.Integer"><![CDATA[$F{count}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="326"
						y="3"
						width="170"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Transparent"
						x="499"
						y="3"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="5"
						width="534"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="6"
						width="209"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
current_timestamp,
3);


insert into spac_ct_informes (id, nombre, descripcion, frm_params, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'),
'Expedientes iniciados en un período de tiempo por procedimiento',
'Expedientes iniciados en un período de tiempo indicado por parámetro agrupados por procedimiento',
'<?xml version="1.0" encoding="ISO-8859-1"?>
<?xml-stylesheet type="text/xsl" href="ReportParamsForm.xsl"?>
<reportform>
	<fields>
		<field>
			<columnname>INTERVALO_FECHAS</columnname>
			<description>field.label.finicio</description>
			<datatype>interval</datatype>
			<controltype size="25" maxlength="21">text</controltype>
		</field>
	</fields>
	<resources>
		<resource isdefault="true" locale="es" key="field.label.finicio" value="Fecha inicio"/>
		<resource locale="ca" key="field.label.finicio" value="Data d''inici"/>
		<resource locale="gl" key="field.label.finicio" value="Data inicio"/>
		<resource locale="eu" key="field.label.finicio" value="Hasiera-data"/>
	</resources>
</reportform>',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="expsIniciadosPorPcdeIntervalo"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="30"
		 rightMargin="30"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="AllSectionsNoDetail"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="INTERVALO_FECHAS_INIT_DATE" isForPrompting="true" class="java.lang.String"/>
	<parameter name="INTERVALO_FECHAS_END_DATE" isForPrompting="true" class="java.lang.String"/>
	<parameter name="INTERVALO_FECHAS" isForPrompting="true" class="java.lang.String">
		<defaultValueExpression ><![CDATA[new String("SYSDATE - 31 AND SYSDATE")]]></defaultValueExpression>
	</parameter>
	<queryString><![CDATA[SELECT P.NOMBRE, COUNT(*) AS COUNT
FROM 	SPAC_P_PROCEDIMIENTOS P,
	SPAC_PROCESOS R
WHERE 	P.ID = R.ID_PCD
	AND R.FECHA_INICIO BETWEEN $P!{INTERVALO_FECHAS}
GROUP BY P.NOMBRE]]></queryString>

	<field name="nombre" class="java.lang.String"/>
	<field name="count" class="java.lang.Integer"/>

	<variable name="es.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes iniciados por procedimiento en el intervalo "]]></initialValueExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[ca]Expedientes iniciados por procedimiento en el intervalo "]]></initialValueExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[eu]Expedientes iniciados por procedimiento en el intervalo "]]></initialValueExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[gl]Expedientes iniciados por procedimiento en el intervalo "]]></initialValueExpression>
	</variable>
	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>
	</variable>
	<variable name="es.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedients"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Espedienteak"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.exp"},
		new String[]{"es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.exp}},
				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.exp}},
				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.exp}},
				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.exp}}
			      }
	    )
]]></initialValueExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="50"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="48"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="534"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="5"
						y="7"
						width="526"
						height="38"
						key="textField-1"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center" verticalAlignment="Middle">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title") + $P{INTERVALO_FECHAS_INIT_DATE} + " - " + $P{INTERVALO_FECHAS_END_DATE}]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="9"  isSplitAllowed="true" >
			</band>
		</pageHeader>
		<columnHeader>
			<band height="18"  isSplitAllowed="true" >
				<rectangle>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="535"
						height="17"
						forecolor="#000000"
						backcolor="#808080"
						key="rectangle"/>
					<graphicElement stretchType="NoStretch" pen="None"/>
				</rectangle>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="0"
						width="534"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="15"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="1"
						width="418"
						height="14"
						forecolor="#FFFFFF"
						key="staticText"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="1"
						width="117"
						height="16"
						forecolor="#FFFFFF"
						key="staticText"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.exp")]]></textFieldExpression>
				</textField>
			</band>
		</columnHeader>
		<detail>
			<band height="17"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="16"
						width="534"
						height="0"
						forecolor="#808080"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="418"
						height="17"
						forecolor="#000000"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="418"
						y="0"
						width="117"
						height="17"
						forecolor="#000000"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.Integer"><![CDATA[$F{count}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="326"
						y="4"
						width="170"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Transparent"
						x="499"
						y="4"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="535"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="6"
						width="209"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
current_timestamp,
3);

insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)values(nextval('spac_sq_id_ctinformes'),'Media de documentos por expediente según procedimiento','Media de documentos generados en los expedientes agrupados por procedimiento','', current_timestamp, 3);
UPDATE spac_ct_informes SET xml= xml || '<?xml version="1.0" encoding="UTF-8"  ?><!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd"><jasperReport		 name="avgDocsPcd"		 columnCount="1"		 printOrder="Vertical"		 orientation="Portrait"		 pageWidth="595"		 pageHeight="842"		 columnWidth="535"		 columnSpacing="0"		 leftMargin="30"		 rightMargin="30"		 topMargin="20"		 bottomMargin="20"		 whenNoDataType="AllSectionsNoDetail"		 isTitleNewPage="false"		 isSummaryNewPage="false">	<property name="ireport.scriptlethandling" value="0" />	<property name="ireport.encoding" value="UTF-8" />	<import value="java.util.*" />	<import value="net.sf.jasperreports.engine.*" />	<import value="net.sf.jasperreports.engine.data.*" />	<queryString><![CDATA[SELECT NOMBRE, AVG(CONTADOR) AS AVG FROM (	SELECT P.NOMBRE, E.NUMEXP, COUNT(D.ID) AS CONTADOR FROM SPAC_EXPEDIENTES E LEFT OUTER JOIN SPAC_DT_DOCUMENTOS D 		ON E.NUMEXP = D.NUMEXP,		SPAC_P_PROCEDIMIENTOS P	WHERE  E.ID_PCD = P.ID	GROUP BY P.NOMBRE, E.NUMEXP) TEMPORAL GROUP BY NOMBRE ORDER BY NOMBRE]]></queryString>	<field name="nombre" class="java.lang.String"/>	<field name="avg" class="java.math.BigDecimal"/>	<variable name="es.title" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Media de documentos por expediente y procedimiento"]]></initialValueExpression>	</variable>	<variable name="ca.title" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["[ca]Media de documentos por expediente y procedimiento"]]></initialValueExpression>	</variable>	<variable name="eu.title" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["[eu]Media de documentos por expediente y procedimiento"]]></initialValueExpression>	</variable>	<variable name="gl.title" class="java.lang.String" resetType="Report" calculation="Nothing">'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<initialValueExpression><![CDATA["[gl]Media de documentos por expediente y procedimiento"]]></initialValueExpression>	</variable>	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>	</variable>	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>	</variable>	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>	</variable>	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>	</variable>	<variable name="es.column.doc" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Documentos"]]></initialValueExpression>	</variable>	<variable name="ca.column.doc" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Documents"]]></initialValueExpression>	</variable>	<variable name="eu.column.doc" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Dokumentuak"]]></initialValueExpression>	</variable>	<variable name="gl.column.doc" class="java.lang.String" resetType="Report" calculation="Nothing">		<initialValueExpression><![CDATA["Documentos"]]></initialValueExpression>	</variable><variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.doc"},			new String[]{"es", "ca", "eu", "gl"},				new String[][]{	new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.doc}}, 				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.doc}}, 				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.doc}}, 				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.doc}}			      }	    )]]></initialValueExpression>	</variable>		<background>			<band height="0"  isSplitAllowed="true" >			</band>		</background>		<title>			<band height="50"  isSplitAllowed="true" >				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="0"						y="5"						width="534"						height="40"						key="staticText"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement textAlignment="Center" verticalAlignment="Middle">						<font size="18" isBold="true"/>					</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>				</textField>				<line direction="TopDown">'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<reportElement						x="0"						y="48"						width="534"						height="0"						forecolor="#000000"						key="line"						positionType="FixRelativeToBottom"/>					<graphicElement stretchType="NoStretch" pen="2Point"/>				</line>				<line direction="TopDown">					<reportElement						x="0"						y="3"						width="534"						height="0"						forecolor="#000000"						key="line"/>					<graphicElement stretchType="NoStretch" pen="2Point"/>				</line>			</band>		</title>		<pageHeader>			<band height="9"  isSplitAllowed="true" >			</band>		</pageHeader><columnHeader>			<band height="20"  isSplitAllowed="true" >				<rectangle radius="0" >					<reportElement						mode="Opaque"						x="1"						y="1"						width="534"						height="17"						forecolor="#000000"						backcolor="#999999"						key="element-22"/>					<graphicElement stretchType="NoStretch" pen="Thin"/>				</rectangle>				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="0"						y="1"						width="440"						height="16"						forecolor="#FFFFFF"						key="element-90"/>'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" leftPadding="2" rightBorder="None" rightBorderColor="#000000" rightPadding="2" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement>						<font  size="12"/>					</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '</textField>				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="440"						y="1"						width="94"						height="16"						forecolor="#FFFFFF"						key="element-90"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" leftPadding="2" rightBorder="None" rightBorderColor="#000000" rightPadding="2" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement textAlignment="Center">						<font size="12"/>					</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.doc")]]></textFieldExpression>				</textField>			</band>		</columnHeader>		<detail>			<band height="19"  isSplitAllowed="true" >				<line direction="TopDown">					<reportElement						x="0"						y="17"						width="535"						height="0"						forecolor="#808080"						key="line"						positionType="FixRelativeToBottom"/>					<graphicElement stretchType="NoStretch" pen="Thin"/>				</line>				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<reportElement						x="0"						y="1"						width="440"						height="15"						key="textField"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" leftPadding="2" rightBorder="None" rightBorderColor="#000000" rightPadding="2" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement>						<font size="12"/>					</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>				</textField>				<textField isStretchWithOverflow="true" pattern="#,##0.00" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="440"						y="1"						width="94"						height="15"						key="textField"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" leftPadding="2" rightBorder="None" rightBorderColor="#000000" rightPadding="2" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement textAlignment="Right">						<font size="12"/>					</textElement>				<textFieldExpression   class="java.math.BigDecimal"><![CDATA[$F{avg}]]></textFieldExpression>				</textField>			</band>		</detail><columnFooter>			<band height="0"  isSplitAllowed="true" >			</band>'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '</columnFooter>		<pageFooter>			<band height="27"  isSplitAllowed="true" >				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="325"						y="4"						width="170"						height="19"						key="textField"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement textAlignment="Right">						<font size="10"/>'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>				</textField>				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="499"						y="4"						width="36"						height="19"						forecolor="#000000"						backcolor="#FFFFFF"						key="textField"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement textAlignment="Left" verticalAlignment="Top" rotation="None" lineSpacing="Single">						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" pdfEncoding ="CP1252" isStrikeThrough="false" />					</textElement>				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>				</textField>				<line direction="TopDown">					<reportElement						x="0"						y="1"						width="535"						height="0"						forecolor="#000000"						key="line"/>					<graphicElement stretchType="NoStretch" pen="2Point"/>				</line>'  where nombre='Media de documentos por expediente según procedimiento';
UPDATE spac_ct_informes SET xml= xml || '<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >					<reportElement						x="1"						y="6"						width="209"						height="19"						key="textField"/>					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>					<textElement>						<font size="10"/>					</textElement>				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>				</textField>			</band>		</pageFooter>		<summary>			<band height="0"  isSplitAllowed="true" >			</band>		</summary></jasperReport>'  where nombre='Media de documentos por expediente según procedimiento';



insert into spac_ct_informes (id, nombre, descripcion, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'),
'Media de tiempo en tramitar expedientes por procedimiento',
'Media de tiempo en tramitar los expedientes agrupados porprocedimiento',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="avgTramitarExpPorPcd"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="30"
		 rightMargin="30"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="AllSectionsNoDetail"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<queryString><![CDATA[SELECT NOMBRE, AVG(INTERVALO) AS MEDIA FROM
(SELECT P.NOMBRE, A.ID_EXP, A.HITO,A.FECHA, C.ID_EXP, C.HITO, C.FECHA, C.FECHA - A.FECHA AS INTERVALO
FROM 	SPAC_HITOS A INNER JOIN SPAC_HITOS C
ON A.ID_EXP = C.ID_EXP AND A.ID_FASE = 0 AND A.HITO = 1 AND C.ID_FASE = 0 AND C.HITO = 2, SPAC_PROCESOS R, SPAC_P_PROCEDIMIENTOS P
WHERE A.ID_EXP IN (SELECT ID_EXP FROM SPAC_HITOS WHERE ID_FASE = 0 AND HITO = 1)
AND C.ID_EXP IN (SELECT ID_EXP FROM SPAC_HITOS WHERE ID_FASE = 0 AND HITO = 2)
AND A.ID_EXP = R.ID AND R.ID_PCD = P.ID
) TEMPORAL
GROUP BY NOMBRE]]></queryString>

	<field name="nombre" class="java.lang.String"/>
	<field name="media" class="java.lang.String"/>

	<variable name="es.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Media de tiempo en tramitar expedientes"]]></initialValueExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[ca]Media de tiempo en tramitar expedientes"]]></initialValueExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[eu]Media de tiempo en tramitar expedientes"]]></initialValueExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[gl]Media de tiempo en tramitar expedientes"]]></initialValueExpression>
	</variable>
	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>
	</variable>
	<variable name="es.column.media" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Media"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.media" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Mitjana"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.media" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Medio"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.media" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Media"]]></initialValueExpression>
	</variable>
	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.media"},
		new String[]{"es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.media}},
				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.media}},
				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.media}},
				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.media}}
			      }
	    )
]]></initialValueExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="50"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="48"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="11"
						width="533"
						height="29"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center" verticalAlignment="Middle">
						<font size="25"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="9"  isSplitAllowed="true" >
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="8"
						width="535"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
		</pageHeader>
		<columnHeader>
			<band height="17"  isSplitAllowed="true" >
				<rectangle>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="535"
						height="17"
						forecolor="#000000"
						backcolor="#808080"
						key="rectangle"/>
					<graphicElement stretchType="NoStretch" pen="None"/>
				</rectangle>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="15"
						width="535"
						height="2"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#FFFFFF"
						key="column.pcd"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="0"
						width="106"
						height="17"
						forecolor="#FFFFFF"
						key="column.exp-1"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.media")]]></textFieldExpression>
				</textField>
			</band>
		</columnHeader>
		<detail>
			<band height="17"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="16"
						width="535"
						height="0"
						forecolor="#808080"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="429"
						height="17"
						forecolor="#000000"
						key="procedimiento"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="429"
						y="-1"
						width="106"
						height="18"
						key="expedientes"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{media}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="326"
						y="3"
						width="170"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Transparent"
						x="499"
						y="3"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="5"
						width="534"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="6"
						width="209"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
current_timestamp,
3);


insert into spac_ct_informes (id, nombre, descripcion, frm_params, xml, fecha, tipo)
values(nextval('spac_sq_id_ctinformes'),
'Responsabilidad. Fases asignadas a responsable',
'Fases cuyo responsable es un usuario, grupo o departamento a seleccionar como parámetro de entrada',
'<?xml version="1.0" encoding="ISO-8859-1"?>
<?xml-stylesheet type="text/xsl" href="ReportParamsForm.xsl"?>
<reportform>
	<fields>
		<field order="01">
			<columnname>RESP</columnname>
			<description>field.label.responsible</description>
			<datatype>objectDirectory</datatype>
			<controltype size="30" maxlength="30">text</controltype>
		</field>
	</fields>
	<resources>
		<resource isdefault="true" locale="es" key="field.label.responsible" value="Responsable"/>
		<resource locale="ca" key="field.label.responsible" value="Responsable"/>
		<resource locale="gl" key="field.label.responsible" value="Responsable"/>
		<resource locale="eu" key="field.label.responsible" value="Arduraduna"/>
	</resources>
</reportform>',
'<?xml version="1.0" encoding="UTF-8"  ?>
<!-- Created with iReport - A designer for JasperReports -->
<!DOCTYPE jasperReport PUBLIC "//JasperReports//DTD Report Design//EN" "http://jasperreports.sourceforge.net/dtds/jasperreport.dtd">
<jasperReport
		 name="respStages"
		 columnCount="1"
		 printOrder="Vertical"
		 orientation="Portrait"
		 pageWidth="595"
		 pageHeight="842"
		 columnWidth="535"
		 columnSpacing="0"
		 leftMargin="30"
		 rightMargin="30"
		 topMargin="20"
		 bottomMargin="20"
		 whenNoDataType="AllSectionsNoDetail"
		 isTitleNewPage="false"
		 isSummaryNewPage="false">
	<property name="ireport.scriptlethandling" value="0" />
	<property name="ireport.encoding" value="UTF-8" />
	<import value="java.util.*" />
	<import value="net.sf.jasperreports.engine.*" />
	<import value="net.sf.jasperreports.engine.data.*" />

	<parameter name="RESP" isForPrompting="true" class="java.lang.String"/>
	<parameter name="NAME_RESP" isForPrompting="true" class="java.lang.String"/>
	<queryString><![CDATA[SELECT PR.NOMBRE AS NOMBRE_PCD, P.NOMBRE AS NOMBRE_FASE, COUNT(*) AS COUNT
FROM SPAC_FASES F, SPAC_P_FASES P, SPAC_P_PROCEDIMIENTOS PR
WHERE F.ID_RESP = $P{RESP}
AND F.ID_FASE = P.ID
AND F.ID_PCD = PR.ID
GROUP BY PR.NOMBRE, P.ID, P.NOMBRE
ORDER BY PR.NOMBRE, P.ID]]></queryString>

	<field name="nombre_pcd" class="java.lang.String"/>
	<field name="nombre_fase" class="java.lang.String"/>
	<field name="count" class="java.lang.String"/>

	<variable name="es.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Fases cuya responsabilidad corresponde a ''" + $P{NAME_RESP} + "''"]]></initialValueExpression>
	</variable>
	<variable name="ca.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[ca]Fases cuya responsabilidad corresponde a ''" + $P{NAME_RESP} + "''"]]></initialValueExpression>
	</variable>
	<variable name="eu.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[eu]Fases cuya responsabilidad corresponde a ''" + $P{NAME_RESP} + "''"]]></initialValueExpression>
	</variable>
	<variable name="gl.title" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["[gl]Fases cuya responsabilidad corresponde a ''" + $P{NAME_RESP} + "''"]]></initialValueExpression>
	</variable>
	<variable name="es.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedimiento"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procediment"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Prozedura"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.pcd" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Procedemento"]]></initialValueExpression>
	</variable>
	<variable name="es.column.fase" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Fase"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.fase" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Fase"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.fase" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Fasea"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.fase" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Fase"]]></initialValueExpression>
	</variable>
	<variable name="es.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>
	<variable name="ca.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedients"]]></initialValueExpression>
	</variable>
	<variable name="eu.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Espedienteak"]]></initialValueExpression>
	</variable>
	<variable name="gl.column.exp" class="java.lang.String" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA["Expedientes"]]></initialValueExpression>
	</variable>	<variable name="properties" class="ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties" resetType="Report" calculation="Nothing">
		<initialValueExpression><![CDATA[new ieci.tdw.ispac.ispaclib.reports.JasperReportsProperties (new String[]{"title", "column.pcd", "column.fase", "column.exp"},
		new String[]{"es", "ca", "eu", "gl"},
		new String[][]{	new String[]{$V{es.title}, $V{es.column.pcd},$V{es.column.fase},$V{es.column.exp}},
				new String[]{$V{ca.title}, $V{ca.column.pcd},$V{ca.column.fase},$V{ca.column.exp}},
				new String[]{$V{eu.title}, $V{eu.column.pcd},$V{eu.column.fase},$V{eu.column.exp}},
				new String[]{$V{gl.title}, $V{gl.column.pcd},$V{gl.column.fase},$V{gl.column.exp}}
			      }
	    )
]]></initialValueExpression>
	</variable>
		<background>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</background>
		<title>
			<band height="50"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="48"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="3"
						width="535"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="11"
						width="533"
						height="29"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center" verticalAlignment="Middle">
						<font size="18"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "title")]]></textFieldExpression>
				</textField>
			</band>
		</title>
		<pageHeader>
			<band height="9"  isSplitAllowed="true" >
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="8"
						width="535"
						height="1"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
			</band>
		</pageHeader>
		<columnHeader>
			<band height="17"  isSplitAllowed="true" >
				<rectangle>
					<reportElement
						mode="Opaque"
						x="0"
						y="0"
						width="535"
						height="17"
						forecolor="#000000"
						backcolor="#808080"
						key="rectangle"/>
					<graphicElement stretchType="NoStretch" pen="None"/>
				</rectangle>
				<line direction="BottomUp">
					<reportElement
						x="0"
						y="15"
						width="535"
						height="2"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="312"
						height="17"
						forecolor="#FFFFFF"
						key="column.pcd"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.pcd")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="448"
						y="0"
						width="87"
						height="17"
						forecolor="#FFFFFF"
						key="column.exp-1"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Center">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.exp")]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="313"
						y="0"
						width="134"
						height="17"
						forecolor="#FFFFFF"
						key="column.pcd-1"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{properties}.getProperty($P{REPORT_LOCALE}, "column.fase")]]></textFieldExpression>
				</textField>
			</band>
		</columnHeader>
		<detail>
			<band height="17"  isSplitAllowed="true" >
				<line direction="TopDown">
					<reportElement
						x="0"
						y="16"
						width="535"
						height="0"
						forecolor="#808080"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="true" pattern="" isBlankWhenNull="true" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="0"
						y="0"
						width="312"
						height="17"
						forecolor="#000000"
						key="procedimiento"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="12" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre_pcd}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="448"
						y="-1"
						width="87"
						height="18"
						key="expedientes"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font size="12"/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{count}]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="true" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="313"
						y="0"
						width="134"
						height="16"
						key="textField"
						positionType="Float"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$F{nombre_fase}]]></textFieldExpression>
				</textField>
			</band>
		</detail>
		<columnFooter>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</columnFooter>
		<pageFooter>
			<band height="27"  isSplitAllowed="true" >
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="326"
						y="3"
						width="170"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement textAlignment="Right">
						<font/>
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA[$V{PAGE_NUMBER} + "/"]]></textFieldExpression>
				</textField>
				<textField isStretchWithOverflow="false" pattern="" isBlankWhenNull="false" evaluationTime="Report" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						mode="Transparent"
						x="499"
						y="3"
						width="36"
						height="19"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font size="10" isBold="false" isItalic="false" isUnderline="false" isPdfEmbedded ="false" isStrikeThrough="false" />
					</textElement>
				<textFieldExpression   class="java.lang.String"><![CDATA["" + $V{PAGE_NUMBER}]]></textFieldExpression>
				</textField>
				<line direction="TopDown">
					<reportElement
						x="0"
						y="5"
						width="534"
						height="0"
						forecolor="#000000"
						backcolor="#FFFFFF"
						key="line"/>
					<graphicElement stretchType="NoStretch"/>
				</line>
				<textField isStretchWithOverflow="false" isBlankWhenNull="false" evaluationTime="Now" hyperlinkType="None"  hyperlinkTarget="Self" >
					<reportElement
						x="1"
						y="6"
						width="209"
						height="19"
						forecolor="#000000"
						key="textField"/>
					<box topBorder="None" topBorderColor="#000000" leftBorder="None" leftBorderColor="#000000" rightBorder="None" rightBorderColor="#000000" bottomBorder="None" bottomBorderColor="#000000"/>
					<textElement>
						<font/>
					</textElement>
				<textFieldExpression   class="java.util.Date"><![CDATA[new Date()]]></textFieldExpression>
				</textField>
			</band>
		</pageFooter>
		<summary>
			<band height="0"  isSplitAllowed="true" >
			</band>
		</summary>
</jasperReport>',
current_timestamp,
3);

--Se define spac_ayudas como entidad no visible , el id que utilizamos debe ser por debajo de 1000 y que no este ya en uso
INSERT INTO spac_ct_entidades (id, tipo, nombre, campo_pk, campo_numexp, schema_expr, frm_edit, descripcion, sec_pk) VALUES (120, 0, 'SPAC_AYUDAS', 'ID', NULL, NULL, NULL, 'AYUDAS', 'SPAC_SQ_ID_AYUDAS');


--Ayudas por defecto para el tramitador

INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Bandeja Entrada', 0, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Frm Busqueda por defecto', 1, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Estado Expediente', 2, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Procedimiento por defecto', 3, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Trámite', 4, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Trámite', 5, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Plazos vencidos', 6, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Registros distribuidos', 7, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Resultado Busqueda ES por defecto', 8, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Avisos Electronicos', 9, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Registro Distribuido', 10, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Lista Expedientes', 11, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Circuito Firma', 12, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Iniciar Expediente', 13, -1, null,'');

--Ayudas  para el catálogo
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Catálogo Procedimientos', 31, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo procedimiento', 32, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Fases', 33, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Trámites', 34, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento >Fases > Trámites', 35, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Resumen', 36, -1,null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Procedimiento > Creado', 37, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso', 38, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo subproceso > Actividades', 39, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso > Resumen', 40, -1, null ,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Nuevo Subproceso > Creado', 41, -1, null,'');
INSERT INTO spac_ayudas( id, nombre, tipo_obj, id_obj, idioma, contenido) VALUES (nextval('spac_sq_id_ayudas'), 'Editor Gráfico', 42, -1, null,'');




---Actualizamos el campo contenido para todas las ayudas

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>BANDEJA ENTRADA</h4></div><p>La pantalla que se muestra es la pantalla principal o Bandeja de Entrada para la
Gestión de Expedientes.</p><p>Se podrán realizar las siguientes acciones:<p><ul><li>Acceder a expedientes de los que es responsable, para continuar
su ramitación (<b>bandeja de entrada: Expedientes en su lista de trabajo</b>)</li><p><li>Realizar trámites concretos de un expediente, del que
es responsable (<b>bandeja de entrada: Trámites abiertos en sus expedientes</b>)</li><p><li>Localizar expedientes que estén o no en tramitación
o no sean responsabilidad suya, pero tenga derecho de consulta sobre ellos, mediante el menú de la izquierda: <b>Búsqueda Avanzada</b></li>
<p><li>Iniciar un Expediente: menú de la izquierda: <B>Iniciar Expediente</B></li><p><li>Realizar utilidades relacionadas con el Portafirmas.
Desde el menú de la izquierda: <b>Portafirmas</b></li><p><li>Consultar los avisos electrónicos de tramitación (<b>bandeja de entrada:
Información de sucesos que le afectan</b>)</li><p><li>Consultar los plazos vencidos (<b>bandeja de entrada: Tiene n plazos vencidos de su
interés</b></li></ul>'
where IDIOMA IS NULL AND TIPO_OBJ=0;


update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>BÚSQUEDA AVANZADA</h4>
</div>
<P>Desde esta pantalla se pueden realizar búsquedas sobre los expedientes sobre los que usted tiene permisos.
<P>Pueden existir varios formularios de búsqueda distintos. Permiten buscar por diferentes campos de los expedientes.
<P>Para cambiar de formulario de búsqueda seleccionarlo en el combo contiguo a <B> ''Formularios de consulta''</B>.
<P>Una vez seleccionado el formulario deseado, cumplimentar los datos conocidos a buscar. En algunos campos se puede seleccionar el operador de búsqueda entre los siguientes:
<p><center>
<table border "2">
<tr><td>></td>
<td>Mayor que</td> </tr>
<tr><td><</td><td>Menor que</td></tr>
<tr><td>>=</td><td>Mayor o igual que</td></tr>
<tr><td><=</td><td>Menor o igual que</td></tr>
<tr><td>=</td><td>Igual que</td></tr>
</table>
</center>
<p>Si el campo a buscar es un texto y no se conoce el texto exacto, se puede introducir el carácter <b>*</B> como carácter comodín.
<p>Ejemplo:
<p>Si buscamos sobre el campo asunto el siguiente texto, el resultado será el siguiente:
<p><center>
<table border "2">
<tr><td>Subvención*</td>
<td>Asuntos que comiencen con la palabra Subvención</td> </tr>
<tr><td>*Subvención</td><td>Asuntos que finalicen con la palabra Subvención</td></tr>
<tr><td>*subvención*</td><td>Asuntos que contengan la palabra Subvención</td></tr>
</table>
</center>
<p>Una vez introducidos los criterios de búsqueda, hacer clic en la opción buscar. La aplicación mostrará una lista con los expedientes encontrados.'
where IDIOMA IS NULL AND TIPO_OBJ=1;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>DISEÑADOR GRÁFICO</h4>
</div>
El <b>Diseñador Gráfico</b> es la herramienta que se proporciona para visualizar los procedimientos y facilitar la tarea al diseñador. Esta herramienta permite a los responsables de procesos y métodos de la organización
	crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.
	<br><br>

	Desde la tramitación podremos observar el grado de avance del expediente comprobando que fases hemos
	ya realizado en cual estamos y las que nos quedan por realizar , en las fases ya realizadas o en la actual
	se podrá consultar los tramites que se han realizado asi como el detalle de cada una de las instancias de los
	trámites, y en caso de ser un subproceso podremos navegar hasta el mismo.
	<h6>Barra de herramientas</h6>
		<div>
		 Nombre del procedimiento : Estado
		 Leyenda con el significado de los colores que puede tener cada una de las fases
		 Imagen de una impresora que nos permite imprimir lo que en ese momento tengamos en la pantalla
		 Icono de ayuda
		</div>

	<br/><br/>

	<h6>Editor de condiciones </h6>
	<div>
	 Un flujo puede tener asociado una condición para ello se mostrará sobre el flujo un icono con un ?, pulsado sobre la flecha con el
	 botón derecho del ratón se visualizará un listado de las condiciones Java (Reglas) como sobre BBDD (SQL) que el usuario haya asociado
	 al flujo , una condicion sql se podrá visualizar, pulsando sobre el nombre de la misma.'
where IDIOMA IS NULL AND TIPO_OBJ=2;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>FORMULARIO EXPEDIENTE</h4></div><div class="titulo_ayuda"><label class="popUpInfo" >Información del procedimiento</label>
</div><p>La pantalla que se muestra es la del &#147;expediente abierto&#148;, es decir, es como si se abriera una carpeta física
con un expediente en formato papel y tuviera unos separadores por: <b>Expediente, Datos específicos, Participantes y Documentos</b>.</p>
<p><ul><li><b>Expediente</b>: Se muestran los datos principales del expediente, en la zona superior, y del Interesado principal, en la zona
 inferior.</li><p><li><b>Datos específicos</b>:se muestran los datos particulares del procedimiento que se está tramitando.</li>
<p><li><b>Participantes</b>: se muestra el formulario para dar de alta o consultar los datos de las personas relacionadas con el Expediente.
</li><p><li><b>Documentos</b>: consta de dos zonas, la superior donde se ven, en este caso los documentos con todos sus campos y se podrá
acceder a abrir su imagen y la parte inferior en donde se muestran en formato tabla, todos los documentos del expediente.
<p>Los documentos se añaden al expediente, generalmente, desde los trámites de cada fase, pero hay expedientes que se diseñan de manera que
se le pueden añadir documentos desde esta pestaña de Documentos.</p><p>Para ver todos los campos de información de un documento y sus imágenes, se seleccionará de la lista inferior, pulsando sobre su nombre. Se comprobará que sus datos se muestran en la parte superior. Seleccionando el botón <b>Ver documento</b> (situado debajo de las pestañas), se abrirá el documento.</p></li>
</ul><p>Desde el menú de la izquierda de la pantalla se podrán realizar las siguientes acciones:</p>
<p><ul><li>Pulsando el botón <b>Acciones</b> se podrá <b>Delegar fase</b> o <b>Clonar expediente</b>.</li>
<p><li>Crear un <b>Nuevo trámite</b> pulsando el botón del mismo nombre.</li>
<p><li>Pulsar el botón <b>Avanzar fase</b> para que el expediente que hay en pantalla avance de fase.</li>
<p><li>Pulsar <b> Trámites</b>  para visualizar los trámites por los que ha pasado el expediente.</li>
<p><li>Pulsar <b>Expedientes relacionados</b> para visualizar los expedientes relacionados.</li>
</ul><div class="titulo_ayuda"><label class="popUpInfo" >Información del trámite</label></div>
<p>Debajo de la etiqueta <b>Trámite/Documento</b>, hay una línea informativa en donde se visualiza: el nombre del trámite actual y la fecha
interna de iniciación.<p>Debajo de esa línea se encuentran los siguientes campos del Trámite:
<p><ul><li><b>Departamento responsable</b>:campo que rellena la aplicación con el valor del departamento que realiza el trámite</li>
<p><li><b>Tramitador responsable</b>:campo que rellena la aplicación, con el nombre del usuario que lo realiza</li>
<p><li><b>Fecha inicio plazo</b>:campo que rellenará el usuario, seleccionando una fecha desde el  icono calendario</li>
<p><li><b>Plazo</b>:un número de unidades: días, meses, años para el plazo</li>
<p><li><b>Unidades de Plazo</b>:se seleccionará el icono Lupa y se mostrarán en una nueva ventana, las distintas unidades de plazo existentes,
de las cuales se seleccionará el valor correspondiente</li><p><li><b>Fecha Alarma</b>:este campo lo cumplimenta la aplicación y será el que se
 utilice para avisar al usuario tramitador, que le vence un plazo</li></ul><p>En la parte inferior de la pantalla,bajo la etiqueta: <B>DOCUMENTOS ADJUNTOS</b>, se encuentra la zona de documentos del trámite, en donde se visualizarán los documentos del trámite si los hubiera.
<p>Desde esta pantalla se podrán realizar las siguientes acciones para <b>Generar documentos</b>:<p><ul><li>Pulsar <b>Desde plantilla</b>
 para seleccionar una plantilla de la lista de plantillas asociadas al trámite<p>El usuario pulsará sobre la que corresponda y se mostrará el
documento normalizado, con los datos del expediente incluidos en él
<p>Este documento, a no ser que se defina en el trámite de otra manera, es modificable, por si se quiere complementar con algún dato,
cambiar una expresión, etc.</li><p><li>Pulsar <b>Anexar fichero</b> para selección del tipo de documento que se va a anexar en el trámite</li>
</ul><p>Tanto si se genera un documento desde una plantilla o anexando un fichero, en la parte inferior de la pantalla de trámites se muestra
el trámite con el fichero anexado y en la parte izquierda el nuevo trámite en la lista de trámites.<p>Para borrar un documento, en un trámite
 abierto, si se ha generado o anexado por error. Se seleccionará en la pantalla anterior el documento que se quiera borrar y se pulsará
<b>Borrar documento</b>.<p>Para finalizar el trámite se pulsará <b>Terminar trámite</b>.
<p>Un trámite se puede eliminar siempre que se encuentre abierto, una vez realizado, no es ni modificable. Para borrar un trámite,
se seleccionará el botón de la pantalla de realización de trámites: <b>Eliminar trámite</b>.
<p>Para delegar un trámite se pulsará el botón <b>Delegar</b> y se seleccionará el Destinatario.'
where IDIOMA IS NULL AND TIPO_OBJ=3;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>LISTA DE TRÁMITES</h4></div><P>Muestra la lista de trámites de un tipo que se encuentran sin terminar. En la lista se puede ver
 el expediente y el procedimiento al que pertenecen, así como la fecha de inicio del trámite y la fecha límite si la tiene.
<P>Para situarse sobre el trámite hacer clic sobre el nombre del trámite. Abrirá el expediente que lo contiene y situará el
 contexto de la aplicación en el trámite.<P>La lista de trámites se puede exportar a ficheros de tipo: Excell, pdf, XML o csv.
Para ello simplemente pulsar sobre el link correspondiente en la parte inferior de la lista. Este fichero no queda almacenado
en el sistema, pero usted tiene la opción de guardar una copia del documento en su ordenador pulsando en la opción <b>''Guardar''</b>
de la aplicación correspondiente en cada caso.'
where  IDIOMA IS NULL AND TIPO_OBJ=4;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO TRÁMITE</h4></div><p>En esta pantalla se muestra la lista de trámites que se pueden realizar
en la fase actual en el expediente con el que está trabajando.</p><p>La última columna de la derecha muestra si ya se han creado
trámites de ese tipo en el expediente.</p><p>Para crear un trámite nuevo hacer clic sobre el nombre del trámite.</p>'
where IDIOMA IS NULL AND TIPO_OBJ=5;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>PLAZOS</h4></div>
<P>Desde esta pantalla se puede realizar un seguimiento de los plazos vencidos. Muestra la lista de expedientes que tienen un plazo vencido.
Por cada uno de ellos muestra el número de expediente, tipo de procedimiento, tipo de plazo y fecha de vencimiento.<P>Para moverse al
expediente, hacer clic sobre el número de expediente.<P>Un plazo asociado a un trámite, dejará de aparecer en esta lista al terminar
el trámite.<P>Desde esta pantalla se pueden buscar los plazos que vencieron o vencerán entre dos fechas. Para ello introducir la fecha
 inicial y final en la parte superior y hacer clic sobre <B>''Consultar''</B>. La fecha se ha de introducir en formato <B>dd/mm/aaaa</B>, o
 bien se puede seleccionar del calendario pulsando en el icono contiguo al campo fecha.<P>La lista de plazos se puede exportar a ficheros
 de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link correspondiente en la parte inferior de la lista.
Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar una copia del documento en su ordenador pulsando
en la opción ''Guardar como'' de la aplicación correspondiente en cada caso.'
where IDIOMA IS NULL AND TIPO_OBJ=6;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA DE REGISTROS DISTRIBUIDOS</h4></div><P>Muestra la lista de registros distribuidos
 accesibles por el usuario tramitador.<P>Para situarse sobre el registro distribuido hacer clic sobre el número de registro.
<P>La lista de registros distribuidos se puede exportar a ficheros de tipo: Excel, PDF, XML o CSV. Para ello simplemente pulsar sobre
 el link correspondiente en la parte inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de
 guardar una copia del documento en su ordenador pulsando en la opción <B>''Guardar como''</B> de la aplicación correspondiente en cada caso.'
where IDIOMA IS NULL AND TIPO_OBJ=7;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion">
<h4>LISTA DE EXPEDIENTES</h4>
</div>
<p>Muestra una lista de expedientes según el criterio de búsqueda seleccionado, a través de un formulario de búsqueda.</p>
<p>Cada fila de la lista se corresponde a un expediente, y muestra una información básica de este, como es el número, interesado principal,
 estado administrativo en el que se encuentra, etcétera.</p>
<P>Para entrar dentro de un expediente, hacer clic sobre el número de expediente. </p>
<P>La lista de expedientes se puede exportar a ficheros de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link
 correspondiente en la parte inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar
una copia del documento en su ordenador pulsando en la opción ''Guardar como'' de la aplicación correspondiente en cada caso.</p>'
where IDIOMA IS NULL AND TIPO_OBJ=8;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA DE EXPEDIENTES</h4></div><P>Esta pantalla le muestra una lista con los avisos
electrónicos relacionados con expedientes de nuestra lista de trabajo. Estos avisos pueden provenir de otras aplicaciones, o por ejemplo
nos puede indicar que se ha creado un expediente nuevo proveniente de registro telemático.<P>En la parte superior de la pantalla, se
indica el número de avisos totales de su bandeja, justo debajo le indica los mensajes que se están mostrando en la pantalla actual. Desde
 esta misma fila, se pude mover por las distintas páginas de avisos, así como ir a la primera y última página.
<P>Un aviso puede tener tres estados: pendiente, en curso o finalizado. La lista sólo muestra los avisos pendientes (sin leer) o en curso.
<P>Para cambiar el estado de un aviso, de pendiente a en curso, hacer clic sobre la opción <B>''Recibir''</B>.
<P>Para cambiar el estado de un aviso a finalizado, pulsar sobre la opción <B>''Finalizar''</B>. Un aviso al pasar a este estado,
desaparecerá de nuestra bandeja de avisos.<P>Los avisos están relacionados siempre con un expediente de nuestra lista de trabajo.
Para que la aplicación nos mueva al expediente, pulsar sobre el número de expediente en la lista de avisos.<P>La lista de avisos
 se puede exportar a ficheros de tipo: Excell, pdf, XML o csv. Para ello simplemente pulsar sobre el link correspondiente en la parte
 inferior de la lista. Este fichero no queda almacenado en el sistema, pero usted tiene la opción de guardar una copia del documento
en su ordenador pulsando en la opición ''Guardar como'' de la aplicación correspondiente en cada caso.'
where IDIOMA IS NULL AND TIPO_OBJ=9;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>REGISTRO DISTRIBUIDO</h4></div><P>Muestra la información de un registro distribuido
 dividida en dos pestañas: Distribución y Registro de Entrada.<P>En la pestaña "Distribución", se muestra la información referente al
 registro distribuido en sí:<ul><li><b>Estado:</b> Estado del registro distribuido.</li><li><b>Fecha distribución:</b> Fecha de
 distribución del registro de entrada.</li><li><b>Mensaje:</b> Mensaje de la distribución.</li></ul><P>En la pestaña
"Registro de Entrada", se muestra la información referente al registro de entrada relacionado con la distribución:
<ul><li><b>Número registro:</b> Número de registro de entrada.</li><li><b>Remitente:</b> Remitente del registro de entrada.</li>
<li><b>Asunto:</b> Asunto del registro de entrada.</li></ul><P>Las acciones que se pueden realizar sobre el registro distribuido
dependen del estado del mismo:<ul><li>Si el estado es <b>pendiente</b>, las acciones que se muestran son:<ul><li><b>Aceptar</b>: Acepta
 el registro distribuido para seguir trabajando con él.</li><li><b>Rechazar</b>: Rechaza el registro distribuido.</li></ul></li><li>
Si el estado es <b>aceptado</b>, las acciones serán:<ul><li><b>Archivar</b>: Archiva el registro distribuido.</li>
<li><b>Iniciar Expediente</b>: Muestra la lista de procedimientos para iniciar un expediente a partir de la información del registro
 distribuido.</li><li><b>Anexar Expediente</b>: Muestra un formulario de búsqueda para seleccionar un expediente donde anexar los
documentos del registro distribuido.</li></ul></li></ul>'
where IDIOMA IS NULL AND TIPO_OBJ=10;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>LISTA EXPEDIENTES</h4></div><P>En esta pantalla se visualizarán los expedientes
 que se encuentren en una fase, para un procedimiento determinado.<p>Para visualizar los datos de un expediente se pulsará sobre
 el <b>Número de expediente</b> correspondiente.<p>La lista de expedientes podrá ser exportada a <b>cvs, Excel, xml y pdf</b>
<p>Seleccionando uno o varios expedientes marcando los checks correspondientes de la izquierda de cada número de expediente
y pulsando sobre <b>Acciones</b> del menú de la izquierda de la pantalla se podrá:<p><li><b>Terminar fases</b></li>
<p><li><b>Delegar fases</b></li><p><li><b>Iniciar tramitación agrupada</b></li>'
where IDIOMA IS NULL AND TIPO_OBJ=11;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>PORTAFIRMAS</h4></div><P>Muestra una lista con los documentos pendientes de firmar
 electrónicamente. Por cada documento indica el expediente al que pertenece, el autor y el estado.<P>Para firmar uno o varios
documentos desde esta pantalla, selecciónelos en la parte izquierda de la lista, luego pulsar en la opción <B>''Acciones''</B> del menú de
 la izquierda de la aplicación, y dentro de este, en la opción <B>''Firmar''</B>.<P>Siga las indicaciones de pantalla para realizar la firma
correctamente.'
where IDIOMA IS NULL AND TIPO_OBJ=12;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>INICIAR EXPEDIENTE</h4></div><p>Esta pantalla muestra la lista de procedimientos
 sobre los cuales se pueden crear nuevos expedientes.<p><p>Para crear un nuevo expediente, hacer clic sobre el nombre del
procedimiento.<p><p>Si considera que no aparece en la lista algún procedimiento, hágalo saber a su administrador de sistemas,
es probable que no tenga asignados permisos sobre él.<p>'
where IDIOMA IS NULL AND TIPO_OBJ=13;

update SPAC_AYUDAS
set CONTENIDO='<a class="enlace" name="inicio"></a><div class="cabecera_seccion"><h4>CATÁLOGO DE PROCEDIMIENTOS</h4></div>
<div class="titulo_ayuda"><label class="popUpInfo" >Información del catálogo de procedimientos</label></div>
<p>El <b>Catálogo de Procedimientos</b> es la herramienta de administración de procedimientos administrativos
que proporciona invesFlow. Esta herramienta permite a los responsables de procesos y métodos de la organización
crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.</p>
<p>El catálogo está compuesto  por el conjunto de <b>entidades</b> que identifican y caracterizan los procedimientos
administrativos de la organización; así como por el conjunto de objetos que permiten estructurar y modelar
dichos procesos siguiendo un modelo conceptual basado en <b>esquemas de tramitación</b> que se articulan con <b>fases,
trámites y documentos</b>, conformando flujos semireglados.</p>
<p>El Catálogo contiene adicionalmente por cada objeto información de interés recogida en <b>fichas catalográficas</b>.
Por ejemplo para un procedimiento administrativo se podrá recoger información de la legislación aplicable,
órgano  o departamento que resuelve, plazos de resolución, forma de iniciación, efecto del silencio
administrativo, etc. </p>
<p>Las principales funciones que permite el catálogo de procedimientos son:</p><ul>
<li>Creación y mantenimiento de los elementos que conforman los procedimientos:
<ul> <li><a class="enlace" href="#ctstagesList">Fases</a>, <a class="enlace" href="#cttasksList">Trámites</a>,
<a class="enlace" href="#cttpdocsList">Tipos de documentos</a>,<a class="enlace" href="#plantillas">Plantillas</a>,
<a class="enlace" href="#batchSign">Procesos de Firma</a>, etc.</li> </ul> </li> <br/>
<li>Construcción y mantenimiento de <a class="enlace" href="#ctproceduresTree">Procedimientos</a>:
<ul><li>Alta de nuevos procedimientos por creación o clonación.</li> <li>Modificación de procedimientos existente.</li>
<li>Mantenimiento de las fichas catalográficas.</li></ul></li><br/><li>Creación y mantenimiento de entidades de negocio
 asociadas a los procedimientos: <ul> <li><a class="enlace" href="#entities">Entidades</a>, <a class="enlace" href="#validationTables">
Tablas de Validación</a>,<a class="enlace" href="#rules">Reglas</a><a class="enlace" href="#searchForms">Formularios de Búsqueda</a>,
<a class="enlace" href="#calendars">Calendarios</a>,<a class="enlace" href="#reports">Informes</a>,<a class="enlace" href="#varSystem">
Variables del sistema</a>,y <a class="enlace" href="#helps">Ayudas</a>.</li></ul></li> <br/>
<li>Gestión del componente de <a class="enlace" href="#publisher">publicación</a>.</li></ul> <div id="ctstagesList">
<br/><a class="enlace" href="#inicio">Inicio</a><br/><br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Fases</label>
</div><br/>En el inventario de fases se pueden ver todas las fases dadas de alta en el sistema.<br><br>Pulsando sobre el <b>nombre</b>
de la fase se pueden ver las <b>propiedades</b> de ésta:<br><br><br>El nombre de la fase será el que vea el tramitador de un procedimiento
 desde la herramienta de tramitación.<br><br>Mediante el botón ''<b>Trámites asociados</b>'' de la parte superior del formulario se
 pueden ver los trámites que se han asociado a la fase mediante el catálogo. Desde esta opción se pueden asociar nuevos trámites a la fase.
<br><br>Para asociar trámites a una fase en un procedimiento en concreto, previamente esos trámites han de estar asociados a la fase en el
inventario.<br><br>Desde la opción ''<b>Ver uso</b>'' se verán los procedimientos que hacen uso de la fase actual.<br><br>
Se pueden modificar los datos de una fase mediante el formulario visto en la pantalla anterior. <br><br>Pulsando ''Aceptar'' estos cambios
se guardan en el sistema.<br/></div><div id="cttasksList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda">
<label class="popUpInfo" >Inventario >> Trámites</label></div><br/>En el inventario de trámites pueden ver todos los trámites dados de alta
en el sistema.<br><br>Pulsando sobre el <b>nombre</b> del trámite se pueden ver las propiedades del mismo:<br><br><br>
De los campos del trámite cabe destacar el nombre. Se corresponde con la etiqueta que verá el usuario desde el tramitador de expedientes.
<br><br>Se describen a continuación las opciones de la parte superior del formulario del trámite:<br><br><li><b>Aceptar</b>: guarda en base
 de datos los cambios que se introduzcan en el formulario.<br><br><li><b>Cancelar</b>: cancela los cambios, en caso de existir, y vuelve
 a la pantalla de inventario de trámites.<br><br><li><b>Documentos asociados</b>: muestra la relación de documentos asociados al trámite actual.
Desde la pantalla de documentos asociados se pueden además asociar nuevos tipos de documentos al trámite.<br><br><br>
Para que un tipo de documento se vea desde un procedimiento dentro de un trámite, dicho trámite ha de tener el documento asociado desde
esta opción.<br><br><li><b>Ver uso</b>: muestra la lista de procedimientos que tienen el trámite asociado, así como la fase en la que están.
<br><br><li><b>Eliminar</b>: permite eliminar un trámite del catálogo de trámites. No permitirá borrar trámites que se encuentren en uso.
<br/></div><div id="cttpdocsList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda">
<label class="popUpInfo" >Inventario >> Tipos de documentos</label></div><br>En el inventario de tipos de documentos se pueden ver todos
los tipos de documentos dados de alta en el sistema.<br><br>Pulsando sobre el <b>nombre</b> del tipo de documento se pueden ver las
propiedades del mismo.<br><br><br>Se detallan a continuación las opciones de la parte superior del formulario de edición de un tipo de
documento:<br><br><li><b>Aceptar</b>: guarda en base de datos los cambios realizados en el formulario.<br><br><li><b>Cancelar</b>:
cancela los cambios realizados en el formulario, si se han producido, y vuelve a la pantalla de lista de tipos de documentos.
<br><br><li><b>Plantillas</b>: desde esta opción se pueden ver las plantillas asociadas a un tipo de documento, así como asociar plantillas
nuevas. <br><br><br>Las plantillas asociadas desde esta opción, se verán desde todos los procedimientos que tengan asociados los trámites que
contengan el tipo de documento actual. Para asociar un tipo de documento a un procedimiento en concreto se usará la opción ''Crear plantilla''
dentro del contexto del procedimiento.<br><br><li><b>Ver uso</b>: muestra los trámites que tienen asociado este tipo de documento.
<br><br><li><b>Eliminar</b>: permite eliminar el tipo de documento. No permitirá borrar tipos de documento que estén en uso.
<br><br><div class="aviso">Al borrar un tipo de documento borrará las plantillas asociadas a este.</div></div><div id="plantillas">
<br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Plantillas</label>
</div><br/>En el inventario de <b>Plantillas de documentos</b> se puede ver la lista de todas  las plantillas de documentos dadas de alta en
el sistema, independientemente del tipo documental al que estén asociadas.En el listado se indica, para cada plantilla, el tipo de documento
al que esta asociada, y si es específica o no. Desde esta pantalla se podrán crear nuevas plantillas (sólo de tipo  genérico), modificar las
existentes, o eliminarlas.Para ver la ficha de una plantilla se debe pulsar sobre el <b>nombre</b>.
Las opciones de esta pantalla son similares a las descritas en <a class="enlace" href="#cttpdocsList">Tipos de documentos</a><br/><br/>
</div><div id="ctsubprocesosList"><br/><a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >
Inventario >> Subprocesos</label></div><br/>En el inventario de <br>Subprocesos</b> se puede ver la lista de subprocesos dados de alta en el
sistema.Se podrán crear nuevos subprocesos, modificar los existentes, o eliminar aquellos que no estén en uso.Pulsando sobre el <b>nombre</b>
de un subproceso, podremos acceder al mismo.Las acciones que se pueden realizar sobre un subproceso son:<ul><li><b>Editor gráfico</b>:
Permite visualizar el subproceso con sus actividades y flujos.</li><li><b>Mostrar información extendida</b>: Recarga la parte izquierda de la
 ventana mostrando información acerca del flujo existente entre las actividades.</li><li><b>Clonar</b>: Crea un nuevo subproceso a partir del
actual.</li><li><b>Añadir actividad</b>: En el caso de que el subproceso esté en estado borrador se ofrece la posibilidad de añadir nuevas
actividades. </li></ul>En caso contrario esta opción no estaría visible.</li>Para poder asociar un subproceso a un trámite, debe estar en
estado vigente.En la pestaña de ficha podremos realizar las siguientes acciones:<ul><li><b>Eliminar Borrador</b>: Se eliminará la versión
actual del subproceso, volviendo a una versión anterior,si es que existiera o eliminando definitivamente el subproceso en caso contrario.
</li><li><b>Pasar a vigente</b>: Para asociar un subproceso a un trámite es necesario que esté en estado vigente,si aún está en estado borrador
 mediante esta opción se pasa a vigente.</li><li><b>Eliminar</b>: Cuando un subproceso está en estado vigente lo podremos eliminar siempre y
cuando no este asociado a ningún trámite.</li></ul>En la pestaña de propiedades se muestra el listado de versiones del subproceso e información
del subproceso.El nombre del subproceso será lo que verá el usuario en la selección del subproceso asociado a un trámite.En la pestaña de
eventos se muestra el listado de eventos asociados al subproceso.</div><div id="batchSign"><br/><a class="enlace" href="#inicio">Inicio</a>
<br/><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Procesos de firma</label></div><br/>En el <b>inventario de procesos de
firma</b> se pueden ver todos los procesos de firma dados de alta en el sistema.Estos procesos serán usados en el sistema tramitador para
establecer el circuito de firma que deben seguir determinados documentos.<br/><br/>Para definir un proceso de firma, deberemos seleccionar
los <b>firmantes</b>, y establecer su orden concreto en el circuito; podremos añadir, eliminar o sustituir firmantes.Un firmante será un
elemento de la <b>organización</b> del sistema: usuario, departamento...</div><div id="ctproceduresTree"><a class="enlace" href="#inicio">
Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >Inventario >> Procedimientos</label></div><br/>En la parte central de la
pantalla muestra el árbol de procedimientos, organizado por familias de procedimientos. Desde esta pantalla se podrán crear nuevas familias,
o acceder a la información de cada procedimiento.<br/><br/>Pulsando sobre el nombre del procedimiento se accede a la siguiente información:
<br/><ul> <li><a class="enlace" href="#pcdFlow">Esquema de tramitación</a>:<br/><br/><ul> <li>Fases, Trámites, Tipos de documentos y
Plantillas</li></ul></li><br/><li>Información específica de cada elemento del esquema de tramitación:<br/><br/><ul><li><a class="enlace"
href="#pcdCard">Procedimiento</a></li><br/><li><a class="enlace" href="#stgCard">Fase</a></li><br/><li><a class="enlace" href="#tskCard">
Trámite</a></li><br/><li><a class="enlace" href="#tpdcCard">Tipo de documento</a></li><br/><li><a class="enlace" href="#tmpltCard">Plantilla</a>
</li><br/></ul></li></ul><br/><br/></div><div id="entities"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label
class="popUpInfo" >Componentes >> Entidades</label><br/>Para la parametrización de procedimientos administrativos se cuenta con
<b>Entidades</b>, que permiten almacenar información estructurada de los distintos expedientes.<br><br>Existen una serie de entidades
precargadas que son comunes a todos los procedimientos, aunque adicionalmente se pueden crear nuevas entidades para cubrir los datos
específicos de de cada caso.<br><br>En el <b>catálogo de Entidades</b> se muestran todas las entidades dadas de alta en el sistema.
<br><br>Al <b>crear</b> una entidad desde el catálogo de procedimientos se creará una tabla en base de datos, con los campos introducidos
 por el usuario, más una serie de campos de control requeridospor el sistema.<br><br>Pulsando sobre el <b>nombre</b> de la entidad, se
accede a las <b>propiedades</b> de la misma:<br><br><ul><li><b>Datos generales de la entidad</b>:</li><br><br><ul>
<li>Nombre: Nombre que se utilizará en invesFlow para hacer referencia a la entidad.<li>Tabla BD</li>: Indica el nombre de la tabla en la
base de datos.<li>Descripción: Objetivo de la entidad.</ul><br><br>
<li><b>Campos de la entidad</b>: en esta ventana se visualizan los distintos campos de la entidad. Para crear uno nuevo pulsar la opción
''Añadir''.<br><br><ul><li>Etiqueta</li>: Nombre que tendrá el dato en los formularios <li>Columna BD</li>: Nombre que tendrá la columna en
la base de datos.<li>Descripción</li>: Objetivo del campo. <li>Tipo</li>: Tipo de campo, seleccionable de una lista de valores disponibles.
<li>Tamaño</li>: Tamaño del campo, en caso de seleccionar un campo de tipo texto o decimal <li>Precisión</li>: Precisión del campo, en caso
de seleccionar un campo de tipo decimal</ul><br><br><li><b>Índices de entidad</b>: si se desea se pueden crear índices para algunos de los
campos creados en el apartado anterior. Será interesante crear un índice sobre un campo cuando este se utilice mucho en las búsquedas y esté
 previsto que el volumen de datos de la entidad sea elevado.<br><br><ul><li>Clave única</li>: indica que el valor de un campo no se pueda
 repetir.</ul><br><br><li><b>Validaciones de la entidad</b>: se utiliza esta opción cuando el valor de un campo está validado contra una serie
 de valores normalizados, en lugar de permitir que el usuario lo introduzca a mano. La validación se realiza contra tablas de validación,
creadas y mantenidas en el propio sistema:<br><br><ul><li>Tabla</li>: se indica la tabla sobre la que se realizará la validación
</ul><br>Se ha de indicar además si es obligatorio que el campo tenga algún valor, o si porel contrario puede estar vacío.
<br><br><br><li><b>Formularios de la entidad</b>:Los formularios son empleados en el tramitador para que el usuario visualice e introduzca
datos para una o varias entidades.<br><br>Un formulario inicialmente tiene una entidad principal, y se crea asociado a la misma,
aunque puede combinar datos de varias de ellas.<br><br>Pulsando sobre el <b>nombre</b> de un formulario se pueden ver las <b>propiedades</b>
de ésta:<br><br><ul><li>Nombre</li>: nombre con que se identificará el formulario<br><br><li>Descripción</li>: nombre que verá el usuario en el
 tramitador de expedientes en la pestaña correspondiente al formulario.<br><br><li>Clase</li>: este campo viene cumplimentado por el sistema,
 aunque se permite cambiar. Indica la clase que va a implementar el formulario.<br><br><li>Parámetros</li>: Se pueden indicar, por ejemplo,
entidades secundarias para el formulario o validaciones sobre los campos de la entidad, necesarias para la clase definida anteriormente.
<br><br>Por defecto vienen definidas las validaciones usadas en los campos validados de la entidad, para que en la visualización del formulario
se muestren los controles de validación correspondientes.<br><br><li>Formateador</li>: este campo permite definir qué campos se van a
visualizar cuando se muestren listas de entidades. Las listas de entidades se muestran cuando la relación de la entidad con el expediente es
de n a 1.<br><br><li>Ubicación en servidor</li>: Indica la ruta donde se almacena el formulario en el servidor; se deberá
modificar este valor si se desea almancenarlo en otra ubicación.</ul><br>Al crear una entidad, automáticamente se crea un <b>formulario por
 defecto</b> o <b>formulario de entidad</b>que muestra todos los campos de esta, y que se podrá utilizar desde los procedimientos. <br><br>
Todo formulario se puede modificar, y se pueden crear cuantos formularios se deseen por entidad;que se basarán en el ''formulario de entidad''
 creado por defecto.<br><br><div class="aviso">Si se hacen cambios en la estructura de la entidad (nuevos campos o validaciones), se deberá
regenerar el formulario de la entidad, y posteriormente regenerar los formularios creados a partir del mismo; si se han hecho cambios de diseño
 o código se perderán, por lo que se recomienda guardar el diseño y/o código de los mismos para volver a introducir los cambios en los
formularios regenerados.</div><br>Para <b>modificar un formulario</b>, desde la lista de formularios de una entidad, se pulsa sobre la
<b>ruta del formulario</b>. Se mostrará una ventana que permite descargar el formulario, tanto su diseño .html como su código .jsp, a un
directorio local.<br><br><div class="aviso">Sobre el diseño del formulario en formato .html, se podrá editar y modificar con cualquier editor
 de formularios, el aspecto de los campos, incluso introducir nuevos elementos, pero no se pueden cambiar los nombres ni tipos de los campos
existentes.<br>Igualmente se podrá modificar el código .jsp, para por ejemplo, introducir nuevos controles en el formulario, o validaciones
javascript.</div><br>Una vez modificado el formulario, se puede <b>actualizar</b> en el catálogo desde la misma ventana que se descargó.
Se pulsará ''Examinar'' para localizar el formulario en el disco duro local, y ''Actualizar'' para subirlo al servidor y sustituir el
existente. El sistema notificará con una alarma si se han modificado datos de la estructura y no puede actualizar correctamente el formulario.
<br><br>Una vez hechos los cambios, el formulario estará disponible para ser usado en el tramitador.</ul></div></div><div id="validationTables">
<a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >Componentes >> Tablas de validación</label>
</div><br/>En el <b>catálogo de Tablas de Validación</b> se muestran todas las tablas de validación dadas de alta en el sistema. Las tablas de
validación se usarán en las entidades, para garantizar que los valores de un determinado campo serán elegidos entre unos determinados,
definidos en dicha tabla de 	validación. <br><br>Al crear una tabla de validación, se creará en base da datos la tabla física
correspondiente.<br><br><br>Hay disponibles dos tipos de tablas de validación: <b>simples</b> y <b>con sustituto</b>: <br><br>
Las <b>tablas de validación simples</b> son listas de valores posibles para un campo.<br><br>Las <b>tablas de validación con sustituto</b>
recogen pares valor-sustituto; el campo valor será almacenado en base de datos, mientras que en los formularios aparecerá el sustituto
correspondiente,para un campo con este tipo de validación.<br><br>Al dar de alta una tabla de validación se pedirá la siguiente información:
<br><br><ul><li><b>Tabla</b>: nombre de la tabla en base de datos</li><br><br><li><b>Nombre</b>: nombre en la aplicación para la tabla de
validación . Para crear uno nuevo pulsar la opción ''Añadir''.<br><br><li><b>Tipo</b>: simple o con sustituto. <br><br>	<li><b>Tamaño campo
 VALOR</b>: tamaño de dicho campo, por defecto será un texto de 10 caractéres.	<br><br>		<li><b>Tamaño campo SUSTITUTO</b>: tamaño de
dicho campo, en las tablas con sustituto.	<br><br>	</ul>	Una vez creada una tabla, se puede visualizar su información, y añadir,
modificar o eliminar <b>valores</b>	a la misma, así como marcar un valor como <b>vigente</b>. Un valor no vigente no se visualizará	entre los
disponibles para la validación de un campo.	</div>	<div id="rules">		<a class="enlace" href="#inicio">Inicio</a>
<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Reglas</label>		</div>		<br/>
En el <b>catálogo de Reglas</b> se muestran todas las reglas dadas de alta en el sistema.	<br>
Una regla es una <b>clase java</b> que implementa el interfaz <b>IRule</b>, definido en la documentación del sistema.	<br><br>
Para dar de alta una regla, anteriormente hay que <b>implementar</b> su clase java de acuerdo a la estructura definida en la documentación,
y almacenarla en una ruta accesible desde el servidor de aplicaciones.	<br><br>		Una vez realizado esto, desde el catálogo de reglas,
pulsar <b>Nueva regla</b>, que mostrará una pantalla 	donde se solicitará la siguiente información:	<br><br>	<ul>
<li><b>Nombre</b>:</li> nombre de la regla. No puede tener espacios en blanco, ya que será 	invocada desde los tag de documento.
<br><br>	<li><b>Descripción</b>: indicaciones de la funcionalidad.	<br><br><li><b>Clase</b>: indica la ruta donde se ha dejado la clase
 en el servidor. 	<br><br>	</ul>	<br>		<div class="aviso">	Se debe consultar el manual de usuario del Catálogo de Procedimientos
 para obtener la definición del interfaz IRule.	</div><br>	</div>	<div id="searchForms">		<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Formularios de búsqueda</label>		</div>		<br/>		En el <b>catálogo de Formularios de búsqueda</b> se muestran todas los formularios de búsqueda 	dados de alta en el sistema. Estos formularios se podrán seleccionar en el sistema tramitador	para realizar las búsquedas, y en ellos definiremos sobre qué campos de las entidades de los	procedimientos queremos aplicar los criterios.	<br><br>
Para crear un formulario de búsqueda deberemos importar un xml con su <b>definición</b>, o bien	incorporarla directamente en el campo de texto correspondiente.	<br><br>	</div>			<div id="calendars">		<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Calendarios</label>		</div>		<br/>		Para el cálculo de plazos de ejecución de los distintos elementos de la tramitación (expediente, fase y trámite), 		cuando sea necesario descontar los días inhábiles, es necesario hacer uso del calendario correspondiente asociado		al órgano de tramitación. Para ello se proporciona una gestión de días considerados no laborables de la semana y		días festivos anuales agrupados en un calendario. 		La creación de un nuevo calendario es posible realizarle desde cero, añadiendo los festivos e identificando los		días no laborables de la semana o clonando un calendario ya existente.
En cualquier de estos casos, una vez creado		se pueden añadir los considerados festivos fijos, que son aquellos días que se consideran que
son festivos para 		todos los años y para todas las provincias, localidades, etc.	</div>			<div id="reports">
<a class="enlace" href="#inicio">Inicio</a>			<div class="titulo_ayuda">	<label class="popUpInfo" >Componentes >> Informes</label>
</div>		<br/>		En el <b>catálogo de Infomes</b> se muestra el listado de informes dados de alta en el sistema.	Los informes podrán
ser de cuatro tipos:		<ul>		<li>Específicos: informes que, para estar disponibles, han de asociarse explícitamente a un
procedimiento, bien a nivel general, o bien en el contexto de una de sus fases o trámites. </li><li>Genéricos: informes que estarán
disponibles en todos los procedimientos, desde cualquiera 		de sus fases y trámites.</li><li>Globales: informes que estarán disponibles
 en la página inicial del tramitador.</li>		<li>Búsqueda: informes que estarán disponibles al mostrar el resultado de la búsqueda, si el
 informe ha sido asociado al formulario de búsqueda.</li>		Los informes se darán de alta para su posterior uso en la definición de los
procedimientos, 		formulario de búsqueda o en la bandeja de entrada.</div><div id="varSystem"><a class="enlace" href="#inicio">Inicio</a>
<div class="titulo_ayuda">			<label class="popUpInfo" >Componentes >> Componentes >> Variables Sistema</label></div><br/>
En el <b> catálogo de variables de sistema</b>, se definen las variables que  almacenarán información de configuración para el correcto
 funcionamiento del sistema	</div><div id="helps"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda">	<label
class="popUpInfo" >Componentes >> Ayudas</label></div><br/>En el <b>catálogo de Ayudas</b> se muestra el listado de ayudas dadas de alta en
el sistema.		Pulsando sobre el <b>nombre</b> de la ayuda se pueden ver las propiedades del mismo:<br><br><br>De los campos de la ayuda el
más destacado es <b>Contenido</b> ya que el texto que aquí se indique		será el que el usuario visualize cuando pulse sobre el icono de
ayuda.	</div>	<div id="pcdFlow"><a class="enlace" href="#inicio">Inicio</a><div class="titulo_ayuda"><label class="popUpInfo" >
Procedimiento >> Esquema de tramitación</label></div><br/>El esquema de tramitación de un procedimiento se conforma con una serie de fases
secuenciales, cada una	con unos determinados trámites asociados. Esta información se define en el momento de la creación del 	procedimiento;
no así los tipos de documentos que son genéricos por trámite. Las plantillas para cada	tipo de documento pueden ser genéricas (creadas a
través del inventario), o específicas para uno o varios	procedimientos.	<br><br>		Seleccionando un elemento del esquema de tramitación,
veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; así como las acciones
posibles para cada contexto:	<br><br>		<li><b>Procedimiento</b></li>: 	<br><br>	<b>Clonar procedimiento</b>	<br><br>
Se creará un procedimiento clonado del actual, pudiendo en el proceso modificarse la asociación	de fases y trámites. Así mismo se
puede definir si el nuevo procedimiento conformará una nueva 	familia, o será hermano o hijo del actual.	<br><br><li><b>Fase</b></li>
<br><br><b>Añadir trámite</b><br><br>Se permite añadir un trámite a la fase actual, seleccionando entre aquellos definidos en el
inventario para dicha fase.	<br><br>		<li><b>Trámite</b></li>	<br><br>	<b>Eliminar trámite</b>	<br><br>Se permite desasociar el
trámite actual de su fase correspondiente dentro del contexto del procedimiento. 	Esta acción no implica la eliminación del trámite, que
 quedará asociado a la misma fase en el inventario.	<br><br>		<li><b>Tipo de documento</b></li>	<br><br>
En el árbol del procedimiento se verán las plantillas dependiendo del tipo de documento seleccionado: 	se mostrarán tanto las genéricas
como las específicas al procedimiento. <br><br>		<b>Crear plantillas asociadas a un procedimiento</b>	<br><br>
Para crear plantillas asociadas a un procedimiento, hay que situarse en el árbol del 	procedimiento, en un tipo de documento concreto,
y pulsar ''Crear plantilla''. 	<br><br>		Esta acción creará una plantilla accesible únicamente desde el procedimiento actual,
aunque	se podrá gestionar desde el inventario de tipos de documentos, y asociar a otros procedimientos	a través de la opción
"Añadir plantilla".	<br><br>		<b>Añadir a un procedimiento una plantilla existente</b><br><br>Además de poder crear plantillas
específicas para un procedimiento también se pueden asociar otras 	ya existentes en otros procedimientos.<br><br>Para ello desde el árbol
del procedimiento, pulsar sobre el tipo de documento. En la parte superior 	pulsar la opción ''Añadir plantilla''.<br><br>El sistema mostrará
 las plantillas asociadas a ese tipo de documento en otros procedimientos. 	Seleccionarndo el deseado se añadirá al prodecimiento actual.<br>
<br>	<div class="aviso">		Al asociar una plantilla a varios procedimientos, hay que tener en cuenta que cualquier modificación a dicha
 plantilla afectará a todos los procedimientos implicados.	</div><br>	<li><b>Plantilla</b></li><br><br><b>Eliminar Plantilla</b><br><br>
Para las plantillas específicas, se permite eliminar la asociación al procedimiento actual.	<br><br><div class="aviso">	Si se elimina una
plantilla asociada únicamente a un procedimiento, esta plantilla se borrará		físicamente.	</div><br>	</div>	<div id="pcdCard"><br/>
<a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >Esquema del Procedimiento >> Fichas>> Procedimiento
</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su información específica (ficha catalográfica,
propiedades...) desplegada en la parte derecha de la pantalla; dicha información se clasifica en función de	su naturaleza en distintas
apartados que describiremos a continuación.<br><br><ul><li><b>Procedimiento</b>: <br><br><ul><li><b>Ficha</b></li><br><br>El procedimiento
tiene un formulario asociado con los datos de éste. Estos datos se corresponden en su mayoría a 	datos normativos y de clasificación.
Cabe destacar de cara a la tramitación:	<br><br>		<ul>	<li><b>Datos Normativos -> Documentación a aportar</b></li>: se trata de un
campo en el que cada línea se 	corresponde con un documento que el ciudadano ha de aportar para solicitar un expediente de este
tipo de procedimiento. Esta información permite que el tramitador realice la subsanación de forma sencilla.	<br><br>	</ul>
Un procedimiento en estado <b>Borrador</b> deberá ser <b>pasado a Vigente</b> para poder ser usado	desde el tramitador de expedientes.
Si por el contrario no se desea conservar el procedimiento, 	podrá ser eliminado siempre que esté en estado de borrador.
<br><br>Para un procedimiento <b>Vigente</b>, podremos crear un borrador que posteriormente podremos modificar	y a su vez pasar a vigente:
la versión actual pasaría en ese caso a ser <b>histórica</b>.	<br><br>		<li><b>Propiedades</b></li>	<br><br>
Se deberá establecer el responsable de un procedimiento para, al iniciar un expediente desde el tramitador, poder establecer la persona,
 grupo o departamento responsable de su tramitación.	<br><br>		Desde esta pantalla podremos acceder a las distintas versiones
existentes del procedimiento actual.	<br><br>	<li><b>Eventos</b></li>	<br><br>	Mediante la pestaña <b>Eventos</b>,
se podrán asociar reglas a distintos momentos de la tramitación. 	Las reglas a asociar se dan de alta en el propio catálogo,
Componentes >> Reglas. <br><br>	Los eventos posibles de un expediente son los siguientes:	<br><br>	<ul>	<li><b>Iniciar</b></li>:
se dispara este evento cuando se inicia un expediente	<br><br>		<li><b>Terminar</b></li>: al cerrar la última fase del expediente.
<br><br>		<li><b>Reubicar</b></li>: al devolver una fase.	<br><br>		<li><b>Expirar plazo</b></li>: cuando se vence el plazo de
resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza al crear el expediente cuando calcula el
responsable.	<br><br>	<li><b>Calcular número de expediente</b></li>: al iniciar el expediente en la acción de calcular el número
de expediente.	<br><br>	</ul>	 Se podrá indicar el orden de ejecución de las reglas, así como borrar la asociación de eventos y reglas
al procedimiento.<br><br><li><b>Entidades</b></li><br><br>Si el procedimiento necesita datos de negocio específicos, se han de crear las
entidades necesarias, 	y asociarlas una vez creado el procedimiento mediante la opción <b>Agregar</b>.<br><br>Por defecto el procedimiento
 incluirá las entidades: expedientes, trámites, intervinientes y documentos. 	Si el procedimiento se ha clonado a partir de otro, además
 incluirá las entidades específicas que tuviera éste.	<br><br>	A cada una de las entidades se le debe asignar un formulario que será
usado en la visualización de un 	expediente de este tipo en la tramitación. Al agregar una entidad, se le asigna el formulario por
defecto correspondiente, pudiendo ser modificado por el usuario.	<br><br>	<li><b>Permisos</b></li><br><br>Para que un procedimiento
 sea instanciable en el tramitador por un usuario o grupo de usuarios, hay que 	darles el permiso correspondiente. <br><br> Esto se gestiona
 desde la pestaña ''Permisos'': 	la pantalla muestra los permisos que ya están asignados (el caso de crear el procedimiento a partir de 	otro,
heredará los permisos del procedimiento original), y permite eliminarlos o añadir nuevos.<br><br>Para asignar nuevos permisos se debe pulsar
 la opción <b>Asignar permisos</b>:	<br><br>	Desde aquí se pueden asignar permisos a departamentos, grupos y usuarios. Pulsando sobre
 <b>''Organización''</b>	se accede a los departamentos de la organización.	<br><br>		Pulsando "<b>Grupos</b>", se accede a los
 grupos de la organización. 	<br><br>		En ambos casos, para bajar a un nivel inferior, se debe pulsar sobre el nombre del
grupo/departamento. 	Cuando se haya situado en el deseado, se debe pulsar ''Seleccionar'', y seleccionar la opción "Iniciar 	expedientes".
	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un expediente. Esto hará que el tramitador envíe
alarmas cuando el 	expediente se exceda del plazo de resolución.	<br><br>	Se puede definir el plazo bien sobre la fecha de inicio de
 un expediente, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las entidades asociadas al procedimiento, o sobre
una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>	<div id="stgCard">		<br/>
<a class="enlace" href="#inicio">Inicio</a>		<br/>			<div class="titulo_ayuda">			<label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Fase</label>		</div>		<br/>		Seleccionando un elemento del esquema de tramitación,
veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información
se clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br>		<ul>	<li><b>Fase</b>:
	<br><br>	<ul>	<li><b>Ficha</b></li>	<br><br>	Se muestran los datos identificativos de la fase, recogidos del inventario de fases.
	<br><br>		<li><b>Propiedades</b></li>	<br><br>	Se podrá indicar el responsable de la fase para, al iniciarse en el tramitador
esta fase para un expediente, se establezca una persona, grupo o departamento 	como responsable de la tramitación.	<br><br><li><b>Eventos</b>
</li>	<br><br>	Mediante la pestaña <b>Eventos</b>, se podrán asociar reglas a distintos momentos de la tramitación. Las reglas a asociar
 se dan de alta en el propio catálogo, Componentes >> Reglas. <br><br>	Los eventos posibles para una fase son los siguientes:	<br><br>
<ul>	<li><b>Iniciar</b></li>: se dispara este evento cuando se inicia la fase	<br><br>		<li><b>Terminar</b></li>:
al cerrar la fase	<br><br>		<li><b>Reubicar</b></li>: al devolver una fase.	<br><br>		<li><b>Expirar plazo</b></li>:
cuando se vence el plazo de resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza, al crear la fase,
cuando se calcula el responsable.	<br><br>	<li><b>Utilizar plantilla</b></li>: al utilizar una plantilla para un documento.<br><br><li>
<b>Crear documento</b></li>: al crear un documento.	<br><br></ul>Se podrá indicar el orden de ejecución de las reglas, así como borrar la
asociación de eventos y reglas	 a la fase.	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un expediente.
Esto hará que el tramitador envíe alarmas cuando el 	expediente se exceda del plazo de resolución.<br><br>Se puede definir el plazo bien
sobre la fecha de inicio de la fase, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las entidades asociadas al
procedimiento, o sobre una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>	<div id="tskCard"><br/>
<a class="enlace" href="#inicio">Inicio</a>	<br/><div class="titulo_ayuda">	<label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Trámite</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su
información específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información se
clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br><ul><li><b>Trámite</b>:
<br><br><ul><li><b>Ficha</b></li><br><br>Se muestran los datos identificativos del trámite, recogidos del inventario de trámites.
<br><br>		<li><b>Propiedades</b></li>	<br><br>		Se podrá modificar el nombre del trámite en el contexto del procedimiento
 actual; este	nombre se verá como etiqueta del trámite dentro del tramitador.	<br><br>	Se podrá indicar el responsable del trámite para,
 al iniciarse en el tramitador	este trámite para un expediente, se establezca una persona, grupo o departamento 	como responsable de la
tramitación.	<br><br>		<li><b>Eventos</b></li>	<br><br>	Mediante la pestaña <b>Eventos</b>, se podrán asociar reglas a distintos
momentos de la tramitación. 	Las reglas a asociar se dan de alta en el propio catálogo, Componentes >> Reglas. <br><br>	Los eventos
posibles para un trámite son los siguientes:	<br><br>	<ul>	<li><b>Iniciar</b></li>: se dispara este evento cuando se inicia el
trámite	<br><br>		<li><b>Terminar</b></li>: al cerrar el trámite	<br><br>		<li><b>Expirar plazo</b></li>: cuando se vence el
plazo de resolución del expediente.	<br><br>		<li><b>Calcular responsable</b></li>: se lanza, al crear la fase, cuando se calcula el
responsable.	<br><br>	<li><b>Utilizar plantilla</b></li>: al utilizar una plantilla para un documento.	<br><br>	<li><b>Crear
 documento</b></li>: al crear un documento.	<br><br>	</ul>	 Se podrá indicar el orden de ejecución de las reglas, así como borrar la
 asociación de eventos y reglas	 al trámite.	<br><br>		<li><b>Plazos</b></li>	<br><br>	Indica el plazo de resolución de un
expediente. Esto hará que el tramitador envíe alarmas cuando el 	expediente se exceda del plazo de resolución.	<br><br>	Se puede
 definir el plazo bien sobre la fecha de inicio del trámite, bien sobre el valor de una fecha 	concreta elegida entre las fechas de las
entidades asociadas al procedimiento, o sobre una fecha 	obtenida a partir de una regla.	<br><br>		</ul>	</li>	</ul>	</div>
	<div id="tpdcCard">		<br/>		<a class="enlace" href="#inicio">Inicio</a>		<br/>			<div class="titulo_ayuda">
<label class="popUpInfo" >Esquema del Procedimiento >> Fichas >> Tipo Documento</label>		</div>		<br/>		Seleccionando un
elemento del esquema de tramitación, veremos su información específica (ficha catalográfica,	propiedades...) desplegada en la parte
derecha de la pantalla; dicha información se clasifica en función de	su naturaleza en distintas apartados que describiremos a continuación.
 	<br><br>		<ul>	<li><b>Tipo de documento</b>: 	<br><br>	<ul>	<li><b>Ficha</b></li>	<br><br>	Se muestran los datos
identificativos del tipo de documento, recogidos del inventario de tipos.<br><br></ul></li></ul></div><div id="tmpltCard"><br/>
<a class="enlace" href="#inicio">Inicio</a><br/><div class="titulo_ayuda"><label class="popUpInfo" >
Esquema del Procedimiento >> Fichas >> Plantilla</label></div><br/>Seleccionando un elemento del esquema de tramitación, veremos su información
 específica (ficha catalográfica,	propiedades...) desplegada en la parte derecha de la pantalla; dicha información se clasifica en función
 de	su naturaleza en distintas apartados que describiremos a continuación. 	<br><br>		<ul>	<li><b>Plantilla</b>: <br><br><ul><li><b>
Ficha</b></li>	<br><br>	Se muestran los datos identificativos de la plantilla, recogidos del inventario de plantillas.	<br><br>Se podrá
editar la plantilla para introducir cambios en la misma.		</ul>	</li>	</ul>	</div><div id="publisher">	<br/>	<a class="enlace"
href="#inicio">Inicio</a>	<br/>	<div class="titulo_ayuda">	<label class="popUpInfo" >Publicador</label>	</div>	<br/>	invesFlow
dispone de un componente denominado <b>Publicador</b>, encargado de comunicarse con sistemas externos	de forma asíncrona. Un uso común de
dicho publicador es el envío de información de la tramitación de un	expediente a un sistema externo, por ejemplo un sistema de información al
ciudadano.	<br><br>	La lógica de dicho publicador se recoge en <b>reglas</b>, que recogen la acción que se ejecutará, para un evento
de tramitación determinado si se cumple una determinada condición. Tanto acciones como condiciones son	clases java que se han de implementar
 y posteriormente dar de alta en el catálogo.<br><br>El publicador se compone de los siguientes elementos:<ul><li><b>Acciones</b></li><br><br>
	Son clases java que realizan una acción concreta, que irán asociadas a determinadas reglas, y que ejecutará el publicador si se dispara una
 de dichas reglas.	<br><br>	<li><b>Condiciones</b></li><br><br>	Son clases de java que realizan una comprobación, y que determinarán si se
 ejecuta o no	la acción de la regla disparada.	<br><br>		<li><b>Aplicaciones</b></li><br><br>	Contexto o sistema externo destino
de la publicación de una regla y que recogerá la información 	publicada; las reglas se clasificarán en función de dicha aplicación.<br><br>
	<li><b>Reglas</b></li><br><br>	Una regla recoge la acción que se desea ejecutar para un evento determinado. La regla vendrá	asociado a
un contexto Procedimiento/Fase/Trámite/Tipo de documento, y su ejecución vendrá deteminada por el cumplimiento de una determinada condición.
	<br><br>	Se deberá indicar para cada regla:	<br><br>	<ul>	<li><b>Procedimiento</b>: procedimiento sobre el que se aplica la regla.
 Este campo es obligatorio.	</li><br>	<li><b>Fase</b>: fase sobre la que se aplica la regla.	</li><br>	<li><b>Trámite</b>: trámite sobre
el que se aplica la regla.	</li><br>		<li><b>Tipo de documento</b>: tipo de documento sobre el que se aplica la regla.	</li><br>
	<li><b>Evento</b>: momento en el que se disparará la ejecución de la regla. Este campo es obligatorio.</li><br><li><b>Info</b>: Información
 adicional sobre determinados eventos.	</li><br>			<li><b>Acción</b>: acción que se ejecutará al disparar la regla, seleccionada
 entre las definidas en	el sistema.	</li><br>			<li><b>Condición</b>: condición que se ejecutará para determinar si se dispara la
regla, seleccionada 	entre las definidas en el sistema.	</li><br>			<li><b>Atributos</b>: xml de configuración para la clase
java que implementa la acción de la regla.	Para más detalle consultar la documentación técnica.	</li><br>			<li><b>Aplicación</b>:
 aplicación contexto de la regla, seleccionada entre las definidas en el sistema.	</li><br></ul><br><li><b>Hitos erróneos</b></li>
<br><br>Desde esta opción se permite realizar el seguimientos de los errores que se han producido en la publicación.Un hito erróneo podrá
 ser reactivado para que el publicador vuelva a tratarlo.	</div>'
where IDIOMA IS NULL AND TIPO_OBJ=31;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO</h4></div>Aparece una ventana emergente que le irá guiando en la creación
 del procedimiento.<br><br>En la primera pantalla debe introducir el nombre del procedimiento. 	<br><br>	Pulsar ''Siguiente'' para continuar
<br><br>'
where IDIOMA IS NULL AND TIPO_OBJ=32;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > SELECCIÓN DE FASES</h4></div>Seleccionar las fases que va a tener el
procedimiento, en el listado de Fases disponibles, y luego pulsar la opción ''Añadir''.<br><br>El orden de las fases se puede modificar mediante
 las opciones ''Subir'' y ''Bajar''. <br><br>Para continuar pulsar ''Siguiente'''
where IDIOMA IS NULL AND TIPO_OBJ=33;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > FASES > TRÁMITES POR FASE</h4></div>Por cada una de las fases habrá que
 asociar los trámites. <br>Para ello pulsar en la opción ''Asignar trámites''. <br><br>Una vez completados todos los trámites de todas las fases,
pulsar ''Siguiente''. '
where IDIOMA IS NULL AND TIPO_OBJ=34;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > FASES > SELECCIÓN DE TRÁMITES POR FASE</h4></div>
Seleccionar los trámites que va a tener la fase, en el listado de Trámites disponibles, y luego pulsar la opción ''Añadir''.
<br><br>	El orden de los trámites se puede modificar mediante las opciones ''Subir'' y ''Bajar''. <br><br>Una vez
 seleccionados todos los trámites de la fase, pulsar ''Volver'' para situarse en la pantalla anterior.'
where IDIOMA IS NULL AND TIPO_OBJ=35;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > RESUMEN CREACIÓN</h4></div>El sistema muestra una pantalla resumen de
las fases y trámites del procedimiento. 	<br><br>	Si todo es correcto, pulsar ''Crear procedimiento''.'
where IDIOMA IS NULL AND TIPO_OBJ=36;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO PROCEDIMIENTO > RESULTADO CREACIÓN</h4></div>El sistema informa del resultado de la
creación del procedimiento. 	<br><br>	Para consultar y editar el nuevo procedimiento creado, pulsar ''Ver nuevo procedimiento''.'
where IDIOMA IS NULL AND TIPO_OBJ=37;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO</h4></div>Aparece una ventana emergente que le irá guiando en la creación
 del subproceso. <br><br>En la primera pantalla debe introducir el nombre del subproceso. <br><br>Pulsar ''Siguiente'' para continuar<br><br>'
where IDIOMA IS NULL AND TIPO_OBJ=38;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > SELECCIÓN DE ACTIVIDADES</h4></div>Seleccionar las actividades que va a
tener el subproceso, en el listado de Actividades disponibles, y luego pulsar la opción ''Añadir''.<br><br>El orden de las actividades se puede
 modificar mediante las opciones ''Subir'' y ''Bajar''. <br><br>Para continuar pulsar ''Siguiente''.'
where IDIOMA IS NULL AND TIPO_OBJ=39;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > RESUMEN CREACIÓN</h4></div>El sistema muestra una pantalla resumen de las
 actividades del subproceso. <br><br>.Si todo es correcto, pulsar ''Crear subproceso''.'
where IDIOMA IS NULL AND TIPO_OBJ=40;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>NUEVO SUBPROCESO > RESULTADO CREACIÓN</h4></div>El sistema informa del resultado de la
 creación del subproceso. 	<br><br>	Para consultar y editar el nuevo subproceso creado, pulsar ''Ver nuevo subproceso''.'
where IDIOMA IS NULL AND TIPO_OBJ=41;

update SPAC_AYUDAS
set CONTENIDO='<div class="cabecera_seccion"><h4>DISEÑADOR GRÁFICO</h4></div>El <b>Diseñador Gráfico</b> es la herramienta que proporciona
 InvesFlow para visualizar los procedimientos y facilitar la tarea al diseñador. Esta herramienta permite a los responsables de procesos y
métodos de la organización 	crear y mantener el conjunto de <b>procedimientos administrativos</b> que se desean automatizar con la herramienta.
<br><br><h3>Barra de herramientas</h3><div>Nombre del procedimiento : Estado Si Estado=Borrador, se le permitirá al usuario:Insertar una nueva
 <b>Fase</b>, arrastrando la imagen  que tiene una F .Insertar un nuevo <b>nodo de sincronización de tipo And</b>, arrastrando la imagen con
 que tiene una Y. Insertar un nuevo <b>nodo de sincronización de tipo Or</b>, arrastando la imagen que tiene una O .Insertar nuevos
 <b>flujos</b>, haciendo click sobre la imagen de la punta de flecha , si se hace click con el botón izquierdo sólo estará habilitado
para realizar una conexión, si se hace click con el botón derecho del ratón se crearan todos los flujos que el usuario desee hasta que se
pulse con un click sobre la imagen de la punta de flecha.Para realizar una conexión una vez hayamos habilitado la flecha seleccionaremos
con un click la pareja de elementos que formen parte del nuevo flujo.La imagen de una impresora que nos permite imprimir lo que en ese momento
tengamos en la pantalla.El icono de ayuda nos muestra la ayuda del diseñador gráfico</div><br/><br/><h3>Editor de condiciones </h3>	<div>
Un flujo puede tener asociado una condición para ello se mostrará sobre el flujo un icono con un ?, pulsado sobre la flecha con el
botón derecho del ratón se visualizará un listado de las condiciones Java (Reglas) como sobre BBDD (SQL) que el usuario haya asociado
 al flujo , una condicion sql se podrá visualizar, pulsando sobre el nombre de la misma.Para modificar la condición el procedimiento
deberá estar en estado borrador y se podrán añadir y eliminar condiciones o modificar una condición sql.Estructura de una condición:
 <ul><li>	 Nombre de la condición: Nombre que el usuario quiere darle a la condición SQL</li><li>	 Cuerpo de la condición: Se representa
 con una tabla que tendrá cada una de las condiciones simples que en su conjunto forman el cuerpo de la condición.</li><li>	 Condición simple:
 <ul><li>Selector parentesis</li><li>Selector de condición: Siempre y cuando no sea la primera condición simple.</li><li>
 Operando 1: Esta compuesto de dos selectores uno se corresponde con las entidades definidas para el procedimiento en el que estamos,
cuando seleccionamos una entidad se carga en el otro selector los campos que tiene la entidad seleccionada.
Operador:  Selector con el que indicaremos la operación a realizar en la condición simple, en función del selector seleccionado se
mostrarán los operandos o no.</li><li>Operando 2: Por defecto se mostrará un checkbox sin chequear y una caja de
texto para que el usuario la rellene si desea realizar la comprobación con un dato introducido por él, en caso contrario,
 es decir que se quiera realizar la comprobación contra un campo de la bbdd se chequeará el checkbox y aparecerán dos
selectores, siendo el primero una lista de las entidades,una vez que el usuario seleccione una, se cargará los campos de
la entidad seleccionada y que se correspondan en tipo con el campo del operando1 seleccionado, si no hay se ha seleccionado nada en el
 operando 1 , no se cargará ningún campo.<li></li>Operando 3: Mismo funcionamiento que el operando 2. </li></ul></li></ul></div>'
where IDIOMA IS NULL AND TIPO_OBJ=42;

