DECLARE @nombreCatalogo VARCHAR(50);
SET @nombreCatalogo = 'ARCHIVOCTLG';

execute sp_fulltext_table 'ADVCTEXTCF','drop';
execute sp_fulltext_table 'ADVCTEXTLCF','drop';
execute sp_fulltext_table 'ADVCTEXTDESCR','drop';
execute sp_fulltext_table 'ADVCTEXTLDESCR','drop';
execute sp_fulltext_table 'ASGFELEMENTOCF','drop';
execute sp_fulltext_table 'ADDESCRIPTOR','drop';

execute sp_fulltext_catalog @nombreCatalogo, 'drop';
GO
