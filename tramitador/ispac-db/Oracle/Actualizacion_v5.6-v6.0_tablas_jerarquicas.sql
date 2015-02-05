-- TABLAS JERARQUICAS
-- ¡¡¡¡¡Este script crea la tabla usada para gestionar las tablas jerarquicas desde el catalogo. 
--Ejecutarlo solo si no se estaba haciendo uso de ello, es decir no existe la tabla SPAC_CT_JERARQUIAS!!!!
  
-- Si ya se estaba haciendo uso de tablas jerarquicas, habra que modificar de forma manual las existentes:
-- 1) En spac_ct_jerarquias se introduce un nuevo campo llamado 'tipo' cuyos posibles
-- valores son: 
--	1 --> la estructura que establace la relacion entre padres e hijos es una TABLA, 
--  2 --> la estructura que establace la relacion entre padres e hijos es una VISTA
-- Este campo se utiliza para saber si se permiten añadir nuevas entradas (si es una TABLA) o no (si es una VISTA)

-- 2) Las tablas o vistas donde se establecen las relaciones ahora se nombran de la siguiente forma: SPAC_CT_JERARQUICA_<ID> ej:SPAC_CT_JERARQUICA_1,
-- antes era SPAC_CT_JERARQUIA_<ID> donde <ID> ocupa 3 caracteres, ej:  SPAC_CT_JERARQUICA_001
-- 3) Estas tablas o vistas van a contener un nuevo campo que se utilizara como clave primaria, quedando la estructura de la siguiente forma:
-- spac_ct_jerarquia_1(id,id_padre,id_hija), donde id es el nuevo campo
-- 4) Para alimentar la clave primaria se necesitaria una secuencia cuyo nombre seria: SPAC_SQ_<HASHCODE_NOMBRE_TABLA>.
-- Para obtener el hascode la tabla hacer uso de HashCode.class disponible en \Actualizacion_v5.6-v6.0_Files de la siguiente forma: 
-- java HashCode <nombre tabla>, ej: java HashCode SPAC_CT_JERARQUIA_1

-- 5) Crear la secuencia y actualizar el valor inicial de la secuencia siendo el número de tablas jerárquicas +1
---CREATE SEQUENCE SPAC_SQ_ID_CTJERARQUIAS	START WITH <numtablasJerarquicas+1> INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;

---6)Modificar los frm de entidades que hagan uso de las tablas jerarquicas de tal manera que donde se invocaba para el obtener el listado
---- de valores usando el parámetro hierarchicalId ahora se debe utilizar el hierarchicalName, cambiando a su vez el valor de ese parámetro,
---- donde antes en el hierarchicalId se pasaba el identificador de la jerarquía (campo ID en SPAC_CT_JERARQUIAS) ahora en hirarchicalName se debera pasar 
---  el nombre de la jerárquica (campo NOMBRE en SPAC_CT_JERARQUIAS).

-- ==============================================================


-- Si no se estaba haciendo uso de tablas jerarquicas, ejecutar las siguientes sentencias para dar de alta la tabla donde se gestionan

CREATE TABLE SPAC_CT_JERARQUIAS(
  ID NUMBER NOT NULL,
  ID_ENTIDAD_PADRE NUMBER,
  ID_ENTIDAD_HIJA NUMBER,
  NOMBRE VARCHAR2(64),
  DESCRIPCION VARCHAR2(4000),
  TIPO NUMBER(1) DEFAULT 1
);
ALTER TABLE SPAC_CT_JERARQUIAS ADD CONSTRAINT PK_SPAC_CT_JERARQUIAS PRIMARY KEY (ID);
CREATE SEQUENCE SPAC_SQ_ID_CTJERARQUIAS	START WITH 1 INCREMENT BY 1 MINVALUE 1 NOCACHE  NOCYCLE  ORDER ;
