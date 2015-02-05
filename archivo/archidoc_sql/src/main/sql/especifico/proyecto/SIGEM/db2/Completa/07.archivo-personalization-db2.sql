------------------------------------------------------------
-- SCRIPT DE PERSONALIZACIÓN PARA UNA ENTIDAD EN CONCRETO --
------------------------------------------------------------

-- Cambio de nombre del fondo principal (por defecto Archivo Municipal de Asturias)
UPDATE ASGFELEMENTOCF SET TITULO='NUEVO TITULO' WHERE ID='00000000000000000000000000000001';

-- Cambio de los datos de geográficos (por defecto <datos_geograficos><pais codigo="108" nombre="ESPAÑA"/><provincia codigo="33" nombre="ASTURIAS"/></datos_geograficos>)
UPDATE AGINFOSISTEMA SET FECHAACTUALIZACION=CURRENT TIMESTAMP,
VALOR='<datos_geograficos><pais codigo="108" nombre="ESPAÑA"/><provincia codigo="NUEVO_CODIGO" nombre="NUEVO_NOMBRE"/></datos_geograficos>'
WHERE NOMBRE='DATOS_GEOGRAFICOS';

-- Cambio de la lista de países y comunidades (por defecto <lista_paises><pais nombre="España" codigo="ES"><comunidad nombre="ASTURIAS" codigo="03"/></pais></lista_paises>)
UPDATE AGINFOSISTEMA SET FECHAACTUALIZACION=CURRENT TIMESTAMP,
VALOR='<lista_paises><pais nombre="España" codigo="ES"><comunidad nombre="NUEVO_NOMBRE" codigo="NUEVO_CODIGO"/></pais></lista_paises>'
WHERE NOMBRE='MAP_PAISES';

--Actualización de la lista de volúmenes de las series creadas
UPDATE ASGFSERIE SET INFOFICHAUDOCREPECM = '<INFO_FICHA_UDOC_REP_ECM version="1.2"><NIVELES_DOCUMENTALES><NIVEL><ID_NIVEL>00000000000000000000000000000009</ID_NIVEL><ID_FICHADESCRPREFUDOC>1</ID_FICHADESCRPREFUDOC><ID_REPECMPREFUDOC>6</ID_REPECMPREFUDOC><ACTUALIZAR_UDOCS>true</ACTUALIZAR_UDOCS></NIVEL><NIVEL><ID_NIVEL>00000000000000000000000000000016</ID_NIVEL><ID_FICHADESCRPREFUDOC>6</ID_FICHADESCRPREFUDOC><ID_REPECMPREFUDOC>6</ID_REPECMPREFUDOC><ACTUALIZAR_UDOCS>true</ACTUALIZAR_UDOCS></NIVEL></NIVELES_DOCUMENTALES></INFO_FICHA_UDOC_REP_ECM>';