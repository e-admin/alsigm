--
-- PostgreSQL database dump
--

-- Started on 2009-06-25 17:18:18

SET client_encoding = 'LATIN9';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 306 (class 2612 OID 38593)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

--
-- TOC entry 264 (class 1247 OID 38573)
-- Dependencies: 3 1480
-- Name: tablefunc_crosstab_2; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tablefunc_crosstab_2 AS (
	row_name text,
	category_1 text,
	category_2 text
);


ALTER TYPE public.tablefunc_crosstab_2 OWNER TO postgres;

--
-- TOC entry 266 (class 1247 OID 38576)
-- Dependencies: 3 1481
-- Name: tablefunc_crosstab_3; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tablefunc_crosstab_3 AS (
	row_name text,
	category_1 text,
	category_2 text,
	category_3 text
);


ALTER TYPE public.tablefunc_crosstab_3 OWNER TO postgres;

--
-- TOC entry 304 (class 1247 OID 38579)
-- Dependencies: 3 1482
-- Name: tablefunc_crosstab_4; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tablefunc_crosstab_4 AS (
	row_name text,
	category_1 text,
	category_2 text,
	category_3 text,
	category_4 text
);


ALTER TYPE public.tablefunc_crosstab_4 OWNER TO postgres;

--
-- TOC entry 27 (class 1255 OID 38585)
-- Dependencies: 3
-- Name: connectby(text, text, text, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, integer, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, integer, text) OWNER TO postgres;

--
-- TOC entry 28 (class 1255 OID 38586)
-- Dependencies: 3
-- Name: connectby(text, text, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, integer) OWNER TO postgres;

--
-- TOC entry 29 (class 1255 OID 38587)
-- Dependencies: 3
-- Name: connectby(text, text, text, text, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, text, integer, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text_serial'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, text, integer, text) OWNER TO postgres;

--
-- TOC entry 30 (class 1255 OID 38588)
-- Dependencies: 3
-- Name: connectby(text, text, text, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text_serial'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, text, integer) OWNER TO postgres;

--
-- TOC entry 21 (class 1255 OID 38570)
-- Dependencies: 3
-- Name: crosstab(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text) OWNER TO postgres;

--
-- TOC entry 25 (class 1255 OID 38583)
-- Dependencies: 3
-- Name: crosstab(text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text, integer) OWNER TO postgres;

--
-- TOC entry 26 (class 1255 OID 38584)
-- Dependencies: 3
-- Name: crosstab(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab_hash'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text, text) OWNER TO postgres;

--
-- TOC entry 22 (class 1255 OID 38580)
-- Dependencies: 3 264
-- Name: crosstab2(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab2(text) RETURNS SETOF tablefunc_crosstab_2
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab2(text) OWNER TO postgres;

--
-- TOC entry 23 (class 1255 OID 38581)
-- Dependencies: 3 266
-- Name: crosstab3(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab3(text) RETURNS SETOF tablefunc_crosstab_3
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab3(text) OWNER TO postgres;

--
-- TOC entry 24 (class 1255 OID 38582)
-- Dependencies: 3 304
-- Name: crosstab4(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab4(text) RETURNS SETOF tablefunc_crosstab_4
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab4(text) OWNER TO postgres;

--
-- TOC entry 20 (class 1255 OID 38569)
-- Dependencies: 3
-- Name: normal_rand(integer, double precision, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION normal_rand(integer, double precision, double precision) RETURNS SETOF double precision
    AS '$libdir/tablefunc', 'normal_rand'
    LANGUAGE c STRICT;


ALTER FUNCTION public.normal_rand(integer, double precision, double precision) OWNER TO postgres;

--
-- TOC entry 1143 (class 3600 OID 38590)
-- Dependencies: 3
-- Name: default_spanish; Type: TEXT SEARCH DICTIONARY; Schema: public; Owner: postgres
--

CREATE TEXT SEARCH DICTIONARY default_spanish (
    TEMPLATE = pg_catalog.ispell,
    dictfile = 'es_es', afffile = 'es_es', stopwords = 'es_es' );


ALTER TEXT SEARCH DICTIONARY public.default_spanish OWNER TO postgres;

--
-- TOC entry 1160 (class 3602 OID 38589)
-- Dependencies: 3 1143 1140
-- Name: default_spanish; Type: TEXT SEARCH CONFIGURATION; Schema: public; Owner: postgres
--

CREATE TEXT SEARCH CONFIGURATION default_spanish (
    PARSER = pg_catalog."default" );

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR asciiword WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR word WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR numword WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR host WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR version WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR hword_numpart WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR hword_part WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR hword_asciipart WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR numhword WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR asciihword WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR hword WITH default_spanish, default_spanish, spanish_stem;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR file WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR "int" WITH simple;

ALTER TEXT SEARCH CONFIGURATION default_spanish
    ADD MAPPING FOR uint WITH simple;


ALTER TEXT SEARCH CONFIGURATION public.default_spanish OWNER TO postgres;

--
-- TOC entry 1753 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2009-06-25 17:18:18

--
-- PostgreSQL database dump complete
--

