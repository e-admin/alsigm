-- Actualizamos los docuid de la tabla scr_pagerepository con los que se
-- relacionaban en la tabla scr_repositorydocs
UPDATE scr_pagerepository page
   SET docuid = (
                 select docs.docuid as docsuid
                   from scr_repositorydocs docs
                  where page.docuid = rtrim(ltrim(char(docs.id)))
                )
 WHERE exists (
                  select 1
                   from scr_repositorydocs docs
                  where page.docuid = rtrim(ltrim(char(docs.id)))
                 )


-- Eliminamos la tabla scr_repositorydocs
DROP TABLE scr_repositorydocs;
