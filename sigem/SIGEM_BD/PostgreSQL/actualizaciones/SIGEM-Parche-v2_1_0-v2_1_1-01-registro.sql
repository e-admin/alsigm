-- Actualizamos los docuid de la tabla scr_pagerepository con los que se
-- relacionaban en la tabla scr_repositorydocs
UPDATE scr_pagerepository
   SET docuid = docs.docsuid
  FROM
	(select scr_repositorydocs.docuid as docsuid, trim(to_char(scr_repositorydocs.id, '99999999')) as docsid
	  from scr_pagerepository inner join scr_repositorydocs
	    on scr_pagerepository.docuid = trim(to_char(scr_repositorydocs.id, '99999999'))) docs
 WHERE docuid = docs.docsid;


-- Eliminamos la tabla scr_repositorydocs
DROP TABLE scr_repositorydocs;
