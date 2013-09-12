/******************************************************************************************************/
/* Version 2.5 */
/******************************************************************************************************/

--MAPEOS DE FICHAS
-- Insertar XML de mapeo para la ficha de unidad documental
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO) VALUES ('1','<xmlmapeos/unidadDocumental.xml>');

/******************************************************************************************************/
/* Version 2.8.1 - En las versiones anteriores, faltaban las fichas de fracción de serie */
/******************************************************************************************************/

INSERT INTO ADFICHA ( ID, NOMBRE, DEFINICION, TIPONORMA, TIPONIVEL,
DESCRIPCION ) VALUES (
'6','ISAD(G) Nivel de Descripción Fracción de Serie','(CLOB)',1,6, NULL);
INSERT INTO ADFMTFICHA ( ID, NOMBRE, DEFINICION, IDFICHA, TIPO, NIVELACCESO, IDLCA,
DESCRIPCION ) VALUES (
'21', 'Consulta de Ficha de Fracción de Serie', '(CLOB)', '6', 1, 1, NULL, NULL);
INSERT INTO ADFMTFICHA ( ID, NOMBRE, DEFINICION, IDFICHA, TIPO, NIVELACCESO, IDLCA,
DESCRIPCION ) VALUES (
'22', 'Edición de ficha de Fracción de Serie', '(CLOB)', '6', 2, 1, NULL, NULL);
INSERT INTO ADFMTFICHA ( ID, NOMBRE, DEFINICION, IDFICHA, TIPO, NIVELACCESO, IDLCA,
DESCRIPCION ) VALUES (
'23', 'Consulta de Ficha de Fracción de Serie', '(CLOB)', '6', 1, 2, NULL, NULL);
INSERT INTO ADFMTFICHA ( ID, NOMBRE, DEFINICION, IDFICHA, TIPO, NIVELACCESO, IDLCA,
DESCRIPCION ) VALUES (
'24', 'Edición de ficha de Fracción de Serie', '(CLOB)', '6', 2, 2, NULL, NULL);

-- Insertar XML de mapeo para la ficha de fraccion de serie
INSERT INTO ASGTMAPDESCRUDOC (IDFICHA,INFO) VALUES ('6','<xml-mapeos/fraccionSerie.xml>');
