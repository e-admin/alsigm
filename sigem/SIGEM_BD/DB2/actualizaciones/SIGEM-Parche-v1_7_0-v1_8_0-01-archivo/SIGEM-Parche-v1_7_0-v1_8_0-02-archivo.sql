
CREATE FUNCTION CALCFINCODREFPADRE(
				vCodReferencia VARCHAR(255),
				vCodRefFondo  VARCHAR(255),
				vCodigo VARCHAR(255),
				vFinalCodRefPadreActual VARCHAR(255),
				vDelimitador VARCHAR(255)) 
				    RETURNS VARCHAR(255)
		LANGUAGE  SQL		
		DETERMINISTIC NO EXTERNAL ACTION CONTAINS SQL
		BEGIN ATOMIC
		DECLARE vCodRefSinCodRefFondo	VARCHAR(255);
    DECLARE iNumCars INT;
    DECLARE vReturnValue VARCHAR(255);
		
		SET vReturnValue = '';
		
	-- algoritmo: Ej
	-- vCodReferencia			ES/NA/AJRP/CF1/CF2/F1/CS1/CS2/S1/U1
	-- vCodRefFondo				ES/NA/AJRP/CF1/CF2/F1
	-- vCodigo					U1
	-- vFinalCodRefPadreActual	CF1/CF2/F2/CS1/CS2/S1/U1
	-- vDelimitador				/

	-- valor retornado:
	--	sin lanzar excepciones:		CS1/CS2/S1
	--	si algo fala:				CF1/CF2/F2/CS1/CS2/S1/U1

		--reemplazar en la cadena la parte del fondo+delimitador por la cadena vacia
		-- El codigo de referencia del fondo nunca puede llegar nulo, si llega nulo => error
		
		IF(LENGTH(vCodRefFondo)=0) THEN 
			RETURN vFinalCodRefPadreActual;
		END IF;
	
		-- El codigo de referencia nunca puede llegar nulo, si llega nulo => error
		IF(LENGTH(vCodReferencia)=0) THEN 
			RETURN vFinalCodRefPadreActual;
		END IF;
		
		-- Si el codigo de referencia del fondo no esta contenido ,en el codigo de referencia => error
		IF(LOCATE(vCodRefFondo,vCodReferencia)=0) THEN 
			RETURN vFinalCodRefPadreActual;
		END IF;
		
		-- Quitamos la parte con el codigo de referencia del fondo, del codigo de referencia
		SET vCodRefSinCodRefFondo = REPLACE(vCodReferencia, vCodRefFondo || vDelimitador,'');
	
		-- codigo vacio
		IF(LENGTH(vCodigo)=0) THEN
			RETURN vCodRefSinCodRefFondo;
		END IF;
		
		-- quitamos el codigo al final de la cadena y el caracter delimitador
		SET iNumCars=LENGTH(vCodRefSinCodRefFondo)-LENGTH(vCodigo);
	
		IF (iNumCars>0) THEN
	 		SET vReturnValue=SUBSTR(vCodRefSinCodRefFondo,1,iNumCars-1);
		END IF;
	
		RETURN vReturnValue;
 END;

CREATE FUNCTION ISNUMERIC ( SOURCE VARCHAR(40) )
  RETURNS BIGINT
  LANGUAGE SQL
  DETERMINISTIC
  NO EXTERNAL ACTION
  READS SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
RETURN
	CASE WHEN translate(source,'','0123456789.-+') <> '' THEN 0
	WHEN posstr(ltrim(source),'-') > 1	OR posstr(ltrim(source),'+') > 1 THEN 0
	WHEN length(rtrim(ltrim(translate(source,'','0123456789 .')))) > 1	OR length(rtrim(ltrim(translate(source,'','0123456789-+')))) > 1 THEN 0
	WHEN posstr(ltrim(rtrim(translate(source,'','-+'))),' ') > 0 THEN 0
	WHEN translate(source,'','.-+') = '' THEN 0
	ELSE 1
END;

CREATE FUNCTION GETNUMERICPOSITIVE ( SOURCE VARCHAR(40) )
  RETURNS BIGINT
  LANGUAGE SQL
  DETERMINISTIC
  NO EXTERNAL ACTION
  READS SQL DATA
  CALLED ON NULL INPUT
  INHERIT SPECIAL REGISTERS
RETURN
CASE
WHEN ISNUMERIC(Source)=1 THEN BIGINT(Source)
ELSE -1
END;

DROP FUNCTION TS_FMT;

CREATE FUNCTION TS_FMT ( 
	TS TIMESTAMP , 
	FMT VARCHAR(30) ) 
	RETURNS VARCHAR(70)   
LANGUAGE SQL
DETERMINISTIC
BEGIN ATOMIC
DECLARE RES VARCHAR(70);
FOR V1 AS SELECT SUBSTR ( DIGITS ( DAY ( TS ) ) , 9 ) AS DD , SUBSTR ( DIGITS ( MONTH ( TS ) ) , 9 ) AS MM , RTRIM ( CHAR ( YEAR ( TS ) ) ) AS YYYY , SUBSTR ( DIGITS ( HOUR ( TS ) ) , 9 ) AS HH , SUBSTR ( DIGITS ( MINUTE ( TS ) ) , 9 ) AS MI , SUBSTR ( DIGITS ( SECOND ( TS ) ) , 9 ) AS SS , RTRIM ( CHAR ( MICROSECOND ( TS ) ) ) AS NNNNNN FROM SYSIBM . SYSDUMMY1 DO
	IF FMT = 'yyyymmdd' THEN 
		SET RES = YYYY || MM || DD ; 
	ELSEIF FMT = 'yyyy/mm/dd' THEN 
		SET RES = YYYY || '/' || MM || '/' || DD ; 
	ELSEIF FMT = 'yyyy-mm-dd' THEN 
		SET RES = YYYY || '-' || MM || '-' || DD ; 
	ELSEIF FMT = 'mm/dd/yyyy' THEN 
		SET RES = MM || '/' || DD || '/' || YYYY ; 
	ELSEIF FMT = 'dd/mm/yyyy' THEN 
		SET RES = DD || '/' || MM || '/' || YYYY ; 
	ELSEIF FMT = 'dd/mm/' THEN 
		SET RES = DD || '/' || MM || '/' ; 
	ELSEIF FMT = 'mm/dd/' THEN 
		SET RES = MM || '/' || DD || '/' ; 
	ELSEIF FMT = 'yyyy' THEN 
		SET RES = YYYY ; 
	ELSEIF FMT = 'yyyy/dd/mm hh:mi:ss' THEN 
		SET RES = YYYY || '/' || MM || '/' || DD || ' ' || HH || ':' || MI || ':' || SS ; 
	ELSEIF FMT = 'dd/mm/yyyy hh:mi:ss' THEN 
		SET RES = DD || '/' || MM || '/' || YYYY || ' ' || HH || ':' || MI || ':' || SS ; 
	ELSEIF FMT = 'nnnnnn' THEN 
		SET RES = NNNNNN ; 
	ELSE 
		SET RES = 'formato ' || FMT || ' no reconocido.' ; 
	END IF ; 
END FOR;
RETURN RES;

END;