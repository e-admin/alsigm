-- Actualizamos los docuid de la tabla scr_pagerepository con los que se
-- relacionaban en la tabla scr_repositorydocs

UPDATE scr_pagerepository page
   SET docuid = (
                 select docs.docuid as docsuid
                   from scr_repositorydocs docs
                  where page.docuid = trim(to_char(docs.id, '99999999'))
                )
 WHERE exists (
                  select 1
                   from scr_repositorydocs docs
                  where page.docuid = trim(to_char(docs.id, '99999999'))
                 )


-- Eliminamos la tabla scr_repositorydocs
DROP TABLE scr_repositorydocs;