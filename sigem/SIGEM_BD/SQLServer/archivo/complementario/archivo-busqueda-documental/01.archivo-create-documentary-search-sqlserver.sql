DECLARE @nombreCatalogo VARCHAR(50);
SET @nombreCatalogo = 'ARCHIVOCTLG';

execute sp_fulltext_database 'enable';
-- Descomentar si la base de datos estaba anteriormente creada con búsqueda documental
-- execute sp_fulltext_service 'clean_up';
-- execute sp_fulltext_catalog @nombreCatalogo, 'drop';
execute sp_fulltext_catalog @nombreCatalogo, 'create';


execute sp_fulltext_table  'ADVCTEXTCF','create',@nombreCatalogo,'ID_ADVCTEXTCF';
execute sp_fulltext_column 'ADVCTEXTCF', 'VALOR', 'add', '3082';
execute sp_fulltext_table 'ADVCTEXTCF','start_change_tracking';
execute sp_fulltext_table 'ADVCTEXTCF','start_background_updateindex';

execute sp_fulltext_table 'ADVCTEXTLCF','create',@nombreCatalogo,'ID_ADVCTEXTLCF';
execute sp_fulltext_column 'ADVCTEXTLCF', 'VALOR', 'add', '3082';  
execute sp_fulltext_table 'ADVCTEXTLCF','start_change_tracking'; 
execute sp_fulltext_table 'ADVCTEXTLCF','start_background_updateindex'; 

execute sp_fulltext_table 'ADVCTEXTDESCR','create',@nombreCatalogo,'ID_ADVCTEXTDESCR'; 
execute sp_fulltext_column 'ADVCTEXTDESCR', 'VALOR', 'add', '3082';  
execute sp_fulltext_table 'ADVCTEXTDESCR','start_change_tracking'; 
execute sp_fulltext_table 'ADVCTEXTDESCR','start_background_updateindex'; 


execute sp_fulltext_table 'ADVCTEXTLDESCR','create',@nombreCatalogo,'ID_ADVCTEXTLDESCR'; 
execute sp_fulltext_column 'ADVCTEXTLDESCR', 'VALOR', 'add', '3082';  
execute sp_fulltext_table 'ADVCTEXTLDESCR','start_change_tracking'; 
execute sp_fulltext_table 'ADVCTEXTLDESCR','start_background_updateindex'; 

execute sp_fulltext_table 'ASGFELEMENTOCF','create',@nombreCatalogo,'ASGFELEMENTOCF1'; 
execute sp_fulltext_column 'ASGFELEMENTOCF', 'TITULO', 'add', '3082';  
execute sp_fulltext_table 'ASGFELEMENTOCF','start_change_tracking'; 
execute sp_fulltext_table 'ASGFELEMENTOCF','start_background_updateindex'; 


execute sp_fulltext_table 'ADDESCRIPTOR','create',@nombreCatalogo,'ID_ADDESCRIPTOR'; 
execute sp_fulltext_column 'ADDESCRIPTOR', 'NOMBRE', 'add', '3082';  
execute sp_fulltext_table 'ADDESCRIPTOR','start_change_tracking'; 
execute sp_fulltext_table 'ADDESCRIPTOR','start_background_updateindex'; 