CREATE TEXT SEARCH CONFIGURATION default_spanish ( COPY = pg_catalog.spanish ); 

CREATE TEXT SEARCH DICTIONARY default_spanish (
    TEMPLATE = ispell,
    DictFile = es_es,
    AffFile = es_es,
    StopWords = es_es
);

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ALTER MAPPING FOR asciiword, asciihword, hword_asciipart,
                      word, hword, hword_part
    WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    DROP MAPPING FOR email, url, url_path, sfloat, float;


