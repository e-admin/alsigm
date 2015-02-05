-- Creación del diccionario

DELETE FROM pg_ts_cfg WHERE ts_name = 'default_spanish';
INSERT INTO pg_ts_cfg (ts_name, prs_name, locale)
               VALUES ('default_spanish', 'default', 'es_ES@euro');

DELETE FROM pg_ts_dict WHERE dict_name = 'es_ispell';
INSERT INTO pg_ts_dict
  (SELECT 'es_ispell',dict_init,
                       'DictFile="/usr/local/pgsql/share/contrib/spanish.med",'
                       'AffFile="/usr/local/pgsql/share/contrib/spanish.aff",'
                       'StopFile="/usr/local/pgsql/share/contrib/spanish.stop"',
                       dict_lexize,
                       'Spanish Ispell'
                FROM pg_ts_dict
                WHERE dict_name = 'ispell_template');


DELETE FROM pg_ts_cfgmap WHERE ts_name = 'default_spanish';
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lhword', '{es_ispell }');
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lpart_hword', '{es_ispell}');
INSERT INTO pg_ts_cfgmap (ts_name, tok_alias, dict_name)
               VALUES ('default_spanish', 'lword', '{es_ispell}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'url', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'host', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'sfloat', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'uri', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'int', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'float', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'email', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'word', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlpart_hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'part_hword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'nlhword', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'file', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'uint', '{simple}');
INSERT INTO pg_ts_cfgmap
               VALUES ('default_spanish', 'version', '{simple}');