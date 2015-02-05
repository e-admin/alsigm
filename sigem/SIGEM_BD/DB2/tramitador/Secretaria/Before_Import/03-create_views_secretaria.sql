


create  view SEC_TBL_VIEW_ORGANO_CARGO   as
SELECT  cast ( ORG.ID*100 +CARGO.ID as integer) AS ID ,
cast(  (ORG.VALOR || '-' || CARGO.VALOR ) as varchar(5)) AS VALOR ,
cast(  (ORG.SUSTITUTO || '-' || CARGO.SUSTITUTO ) as varchar(129)) AS SUSTITUTO ,
cast (1 as smallint) as VIGENTE , cast (ORG.ORDEN *100 +CARGO.ORDEN as  smallint)AS ORDEN
FROM  SEC_VLDTBL_ORGANO_GOBIERNO  ORG , SEC_VLDTBL_CARGO_ORGANO CARGO;


create  view SEC_TBL_VIEW_AREA_CARGO   as
SELECT  cast ( AREA.ID*100+CARGO.ID as integer) AS ID,
cast(  (AREA.VALOR || '-' || CARGO.VALOR ) as varchar(5)) AS VALOR ,
cast(  (AREA.SUSTITUTO || '-' || CARGO.SUSTITUTO ) as varchar(129)) AS SUSTITUTO,
cast (1 as smallint) AS VIGENTE , cast (AREA.ORDEN*100 +CARGO.ORDEN as  smallint) AS ORDEN
FROM  SEC_VLDTBL_ORGANO_GOBIERNO  AREA , SEC_VLDTBL_CARGO_ORGANO CARGO;

















