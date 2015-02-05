--
-- PostgreSQL database dump
--

-- Started on 2008-11-14 13:23:25

SET client_encoding = 'LATIN9';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 1810 (class 0 OID 0)
-- Dependencies: 5
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'Standard public schema';


--
-- TOC entry 417 (class 2612 OID 205395)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 392 (class 0 OID 0)
-- Name: gtsq; Type: SHELL TYPE; Schema: public; Owner: postgres
--

CREATE TYPE gtsq;


--
-- TOC entry 19 (class 1255 OID 205397)
-- Dependencies: 5 392
-- Name: gtsq_in(cstring); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_in(cstring) RETURNS gtsq
    AS '$libdir/tsearch2', 'gtsq_in'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsq_in(cstring) OWNER TO postgres;

--
-- TOC entry 20 (class 1255 OID 205398)
-- Dependencies: 5 392
-- Name: gtsq_out(gtsq); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_out(gtsq) RETURNS cstring
    AS '$libdir/tsearch2', 'gtsq_out'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsq_out(gtsq) OWNER TO postgres;

--
-- TOC entry 391 (class 1247 OID 205396)
-- Dependencies: 5 20 19
-- Name: gtsq; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE gtsq (
    INTERNALLENGTH = 8,
    INPUT = gtsq_in,
    OUTPUT = gtsq_out,
    ALIGNMENT = int4,
    STORAGE = plain
);


ALTER TYPE public.gtsq OWNER TO postgres;

--
-- TOC entry 395 (class 0 OID 0)
-- Name: gtsvector; Type: SHELL TYPE; Schema: public; Owner: postgres
--

CREATE TYPE gtsvector;


--
-- TOC entry 21 (class 1255 OID 205401)
-- Dependencies: 5 395
-- Name: gtsvector_in(cstring); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_in(cstring) RETURNS gtsvector
    AS '$libdir/tsearch2', 'gtsvector_in'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsvector_in(cstring) OWNER TO postgres;

--
-- TOC entry 22 (class 1255 OID 205402)
-- Dependencies: 5 395
-- Name: gtsvector_out(gtsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_out(gtsvector) RETURNS cstring
    AS '$libdir/tsearch2', 'gtsvector_out'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsvector_out(gtsvector) OWNER TO postgres;

--
-- TOC entry 394 (class 1247 OID 205400)
-- Dependencies: 22 5 21
-- Name: gtsvector; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE gtsvector (
    INTERNALLENGTH = variable,
    INPUT = gtsvector_in,
    OUTPUT = gtsvector_out,
    ALIGNMENT = int4,
    STORAGE = plain
);


ALTER TYPE public.gtsvector OWNER TO postgres;

--
-- TOC entry 398 (class 0 OID 0)
-- Name: tsquery; Type: SHELL TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tsquery;


--
-- TOC entry 23 (class 1255 OID 205405)
-- Dependencies: 5 398
-- Name: tsquery_in(cstring); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_in(cstring) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_in'
    LANGUAGE c STRICT;


ALTER FUNCTION public.tsquery_in(cstring) OWNER TO postgres;

--
-- TOC entry 24 (class 1255 OID 205406)
-- Dependencies: 5 398
-- Name: tsquery_out(tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_out(tsquery) RETURNS cstring
    AS '$libdir/tsearch2', 'tsquery_out'
    LANGUAGE c STRICT;


ALTER FUNCTION public.tsquery_out(tsquery) OWNER TO postgres;

--
-- TOC entry 397 (class 1247 OID 205404)
-- Dependencies: 5 24 23
-- Name: tsquery; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tsquery (
    INTERNALLENGTH = variable,
    INPUT = tsquery_in,
    OUTPUT = tsquery_out,
    ALIGNMENT = int4,
    STORAGE = plain
);


ALTER TYPE public.tsquery OWNER TO postgres;

--
-- TOC entry 401 (class 0 OID 0)
-- Name: tsvector; Type: SHELL TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tsvector;


--
-- TOC entry 25 (class 1255 OID 205409)
-- Dependencies: 5 401
-- Name: tsvector_in(cstring); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_in(cstring) RETURNS tsvector
    AS '$libdir/tsearch2', 'tsvector_in'
    LANGUAGE c STRICT;


ALTER FUNCTION public.tsvector_in(cstring) OWNER TO postgres;

--
-- TOC entry 26 (class 1255 OID 205410)
-- Dependencies: 5 401
-- Name: tsvector_out(tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_out(tsvector) RETURNS cstring
    AS '$libdir/tsearch2', 'tsvector_out'
    LANGUAGE c STRICT;


ALTER FUNCTION public.tsvector_out(tsvector) OWNER TO postgres;

--
-- TOC entry 400 (class 1247 OID 205408)
-- Dependencies: 25 26 5
-- Name: tsvector; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tsvector (
    INTERNALLENGTH = variable,
    INPUT = tsvector_in,
    OUTPUT = tsvector_out,
    ALIGNMENT = int4,
    STORAGE = extended
);


ALTER TYPE public.tsvector OWNER TO postgres;

--
-- TOC entry 403 (class 1247 OID 205413)
-- Dependencies: 1452
-- Name: statinfo; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE statinfo AS (
	word text,
	ndoc integer,
	nentry integer
);


ALTER TYPE public.statinfo OWNER TO postgres;

--
-- TOC entry 404 (class 1247 OID 205415)
-- Dependencies: 1453
-- Name: tablefunc_crosstab_2; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tablefunc_crosstab_2 AS (
	row_name text,
	category_1 text,
	category_2 text
);


ALTER TYPE public.tablefunc_crosstab_2 OWNER TO postgres;

--
-- TOC entry 405 (class 1247 OID 205417)
-- Dependencies: 1454
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
-- TOC entry 406 (class 1247 OID 205419)
-- Dependencies: 1455
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
-- TOC entry 407 (class 1247 OID 205421)
-- Dependencies: 1456
-- Name: tokenout; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tokenout AS (
	tokid integer,
	token text
);


ALTER TYPE public.tokenout OWNER TO postgres;

--
-- TOC entry 408 (class 1247 OID 205423)
-- Dependencies: 1457
-- Name: tokentype; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tokentype AS (
	tokid integer,
	alias text,
	descr text
);


ALTER TYPE public.tokentype OWNER TO postgres;

--
-- TOC entry 409 (class 1247 OID 205425)
-- Dependencies: 1458
-- Name: tsdebug; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE tsdebug AS (
	ts_name text,
	tok_type text,
	description text,
	token text,
	dict_name text[],
	tsvector tsvector
);


ALTER TYPE public.tsdebug OWNER TO postgres;

--
-- TOC entry 27 (class 1255 OID 205426)
-- Dependencies: 5
-- Name: _get_parser_from_curcfg(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION _get_parser_from_curcfg() RETURNS text
    AS $$ select prs_name from pg_ts_cfg where oid = show_curcfg() $$
    LANGUAGE sql IMMUTABLE STRICT;


ALTER FUNCTION public._get_parser_from_curcfg() OWNER TO postgres;

--
-- TOC entry 28 (class 1255 OID 205427)
-- Dependencies: 400 5 400 400
-- Name: concat(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION concat(tsvector, tsvector) RETURNS tsvector
    AS '$libdir/tsearch2', 'concat'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.concat(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 29 (class 1255 OID 205428)
-- Dependencies: 5
-- Name: connectby(text, text, text, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, integer, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, integer, text) OWNER TO postgres;

--
-- TOC entry 30 (class 1255 OID 205429)
-- Dependencies: 5
-- Name: connectby(text, text, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, integer) OWNER TO postgres;

--
-- TOC entry 31 (class 1255 OID 205430)
-- Dependencies: 5
-- Name: connectby(text, text, text, text, text, integer, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, text, integer, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text_serial'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, text, integer, text) OWNER TO postgres;

--
-- TOC entry 32 (class 1255 OID 205431)
-- Dependencies: 5
-- Name: connectby(text, text, text, text, text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION connectby(text, text, text, text, text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'connectby_text_serial'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.connectby(text, text, text, text, text, integer) OWNER TO postgres;

--
-- TOC entry 33 (class 1255 OID 205432)
-- Dependencies: 5
-- Name: crosstab(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text) OWNER TO postgres;

--
-- TOC entry 34 (class 1255 OID 205433)
-- Dependencies: 5
-- Name: crosstab(text, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text, integer) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text, integer) OWNER TO postgres;

--
-- TOC entry 35 (class 1255 OID 205434)
-- Dependencies: 5
-- Name: crosstab(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab(text, text) RETURNS SETOF record
    AS '$libdir/tablefunc', 'crosstab_hash'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab(text, text) OWNER TO postgres;

--
-- TOC entry 36 (class 1255 OID 205435)
-- Dependencies: 5 404
-- Name: crosstab2(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab2(text) RETURNS SETOF tablefunc_crosstab_2
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab2(text) OWNER TO postgres;

--
-- TOC entry 37 (class 1255 OID 205436)
-- Dependencies: 405 5
-- Name: crosstab3(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab3(text) RETURNS SETOF tablefunc_crosstab_3
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab3(text) OWNER TO postgres;

--
-- TOC entry 38 (class 1255 OID 205437)
-- Dependencies: 5 406
-- Name: crosstab4(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION crosstab4(text) RETURNS SETOF tablefunc_crosstab_4
    AS '$libdir/tablefunc', 'crosstab'
    LANGUAGE c STABLE STRICT;


ALTER FUNCTION public.crosstab4(text) OWNER TO postgres;

--
-- TOC entry 39 (class 1255 OID 205438)
-- Dependencies: 5
-- Name: dex_init(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dex_init(internal) RETURNS internal
    AS '$libdir/tsearch2', 'dex_init'
    LANGUAGE c;


ALTER FUNCTION public.dex_init(internal) OWNER TO postgres;

--
-- TOC entry 40 (class 1255 OID 205439)
-- Dependencies: 5
-- Name: dex_lexize(internal, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION dex_lexize(internal, internal, integer) RETURNS internal
    AS '$libdir/tsearch2', 'dex_lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.dex_lexize(internal, internal, integer) OWNER TO postgres;

--
-- TOC entry 41 (class 1255 OID 205440)
-- Dependencies: 397 5 400
-- Name: exectsq(tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION exectsq(tsvector, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'exectsq'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.exectsq(tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 1812 (class 0 OID 0)
-- Dependencies: 41
-- Name: FUNCTION exectsq(tsvector, tsquery); Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON FUNCTION exectsq(tsvector, tsquery) IS 'boolean operation with text index';


--
-- TOC entry 42 (class 1255 OID 205441)
-- Dependencies: 400 397 5
-- Name: get_covers(tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_covers(tsvector, tsquery) RETURNS text
    AS '$libdir/tsearch2', 'get_covers'
    LANGUAGE c STRICT;


ALTER FUNCTION public.get_covers(tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 43 (class 1255 OID 205442)
-- Dependencies: 397 5
-- Name: gin_extract_tsquery(tsquery, internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gin_extract_tsquery(tsquery, internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gin_extract_tsquery'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gin_extract_tsquery(tsquery, internal, internal) OWNER TO postgres;

--
-- TOC entry 44 (class 1255 OID 205443)
-- Dependencies: 5 400
-- Name: gin_extract_tsvector(tsvector, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gin_extract_tsvector(tsvector, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gin_extract_tsvector'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gin_extract_tsvector(tsvector, internal) OWNER TO postgres;

--
-- TOC entry 45 (class 1255 OID 205444)
-- Dependencies: 5 397
-- Name: gin_ts_consistent(internal, internal, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gin_ts_consistent(internal, internal, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'gin_ts_consistent'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gin_ts_consistent(internal, internal, tsquery) OWNER TO postgres;

--
-- TOC entry 46 (class 1255 OID 205445)
-- Dependencies: 5
-- Name: gtsq_compress(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_compress(internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsq_compress'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_compress(internal) OWNER TO postgres;

--
-- TOC entry 47 (class 1255 OID 205446)
-- Dependencies: 391 5
-- Name: gtsq_consistent(gtsq, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_consistent(gtsq, internal, integer) RETURNS boolean
    AS '$libdir/tsearch2', 'gtsq_consistent'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_consistent(gtsq, internal, integer) OWNER TO postgres;

--
-- TOC entry 48 (class 1255 OID 205447)
-- Dependencies: 5
-- Name: gtsq_decompress(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_decompress(internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsq_decompress'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_decompress(internal) OWNER TO postgres;

--
-- TOC entry 49 (class 1255 OID 205448)
-- Dependencies: 5
-- Name: gtsq_penalty(internal, internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_penalty(internal, internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsq_penalty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsq_penalty(internal, internal, internal) OWNER TO postgres;

--
-- TOC entry 50 (class 1255 OID 205449)
-- Dependencies: 5
-- Name: gtsq_picksplit(internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_picksplit(internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsq_picksplit'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_picksplit(internal, internal) OWNER TO postgres;

--
-- TOC entry 51 (class 1255 OID 205450)
-- Dependencies: 391 391 5
-- Name: gtsq_same(gtsq, gtsq, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_same(gtsq, gtsq, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsq_same'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_same(gtsq, gtsq, internal) OWNER TO postgres;

--
-- TOC entry 52 (class 1255 OID 205451)
-- Dependencies: 5
-- Name: gtsq_union(bytea, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsq_union(bytea, internal) RETURNS integer[]
    AS '$libdir/tsearch2', 'gtsq_union'
    LANGUAGE c;


ALTER FUNCTION public.gtsq_union(bytea, internal) OWNER TO postgres;

--
-- TOC entry 53 (class 1255 OID 205452)
-- Dependencies: 5
-- Name: gtsvector_compress(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_compress(internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsvector_compress'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_compress(internal) OWNER TO postgres;

--
-- TOC entry 54 (class 1255 OID 205453)
-- Dependencies: 394 5
-- Name: gtsvector_consistent(gtsvector, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_consistent(gtsvector, internal, integer) RETURNS boolean
    AS '$libdir/tsearch2', 'gtsvector_consistent'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_consistent(gtsvector, internal, integer) OWNER TO postgres;

--
-- TOC entry 55 (class 1255 OID 205454)
-- Dependencies: 5
-- Name: gtsvector_decompress(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_decompress(internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsvector_decompress'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_decompress(internal) OWNER TO postgres;

--
-- TOC entry 56 (class 1255 OID 205455)
-- Dependencies: 5
-- Name: gtsvector_penalty(internal, internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_penalty(internal, internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsvector_penalty'
    LANGUAGE c STRICT;


ALTER FUNCTION public.gtsvector_penalty(internal, internal, internal) OWNER TO postgres;

--
-- TOC entry 57 (class 1255 OID 205456)
-- Dependencies: 5
-- Name: gtsvector_picksplit(internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_picksplit(internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsvector_picksplit'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_picksplit(internal, internal) OWNER TO postgres;

--
-- TOC entry 58 (class 1255 OID 205457)
-- Dependencies: 5 394 394
-- Name: gtsvector_same(gtsvector, gtsvector, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_same(gtsvector, gtsvector, internal) RETURNS internal
    AS '$libdir/tsearch2', 'gtsvector_same'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_same(gtsvector, gtsvector, internal) OWNER TO postgres;

--
-- TOC entry 59 (class 1255 OID 205458)
-- Dependencies: 5
-- Name: gtsvector_union(internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION gtsvector_union(internal, internal) RETURNS integer[]
    AS '$libdir/tsearch2', 'gtsvector_union'
    LANGUAGE c;


ALTER FUNCTION public.gtsvector_union(internal, internal) OWNER TO postgres;

--
-- TOC entry 60 (class 1255 OID 205459)
-- Dependencies: 397 5
-- Name: headline(oid, text, tsquery, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(oid, text, tsquery, text) RETURNS text
    AS '$libdir/tsearch2', 'headline'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(oid, text, tsquery, text) OWNER TO postgres;

--
-- TOC entry 61 (class 1255 OID 205460)
-- Dependencies: 5 397
-- Name: headline(oid, text, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(oid, text, tsquery) RETURNS text
    AS '$libdir/tsearch2', 'headline'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(oid, text, tsquery) OWNER TO postgres;

--
-- TOC entry 62 (class 1255 OID 205461)
-- Dependencies: 397 5
-- Name: headline(text, text, tsquery, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(text, text, tsquery, text) RETURNS text
    AS '$libdir/tsearch2', 'headline_byname'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(text, text, tsquery, text) OWNER TO postgres;

--
-- TOC entry 63 (class 1255 OID 205462)
-- Dependencies: 5 397
-- Name: headline(text, text, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(text, text, tsquery) RETURNS text
    AS '$libdir/tsearch2', 'headline_byname'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(text, text, tsquery) OWNER TO postgres;

--
-- TOC entry 64 (class 1255 OID 205463)
-- Dependencies: 5 397
-- Name: headline(text, tsquery, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(text, tsquery, text) RETURNS text
    AS '$libdir/tsearch2', 'headline_current'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(text, tsquery, text) OWNER TO postgres;

--
-- TOC entry 65 (class 1255 OID 205464)
-- Dependencies: 5 397
-- Name: headline(text, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION headline(text, tsquery) RETURNS text
    AS '$libdir/tsearch2', 'headline_current'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.headline(text, tsquery) OWNER TO postgres;

--
-- TOC entry 66 (class 1255 OID 205465)
-- Dependencies: 5 400
-- Name: length(tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION length(tsvector) RETURNS integer
    AS '$libdir/tsearch2', 'tsvector_length'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.length(tsvector) OWNER TO postgres;

--
-- TOC entry 67 (class 1255 OID 205466)
-- Dependencies: 5
-- Name: lexize(oid, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION lexize(oid, text) RETURNS text[]
    AS '$libdir/tsearch2', 'lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.lexize(oid, text) OWNER TO postgres;

--
-- TOC entry 68 (class 1255 OID 205467)
-- Dependencies: 5
-- Name: lexize(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION lexize(text, text) RETURNS text[]
    AS '$libdir/tsearch2', 'lexize_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.lexize(text, text) OWNER TO postgres;

--
-- TOC entry 69 (class 1255 OID 205468)
-- Dependencies: 5
-- Name: lexize(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION lexize(text) RETURNS text[]
    AS '$libdir/tsearch2', 'lexize_bycurrent'
    LANGUAGE c STRICT;


ALTER FUNCTION public.lexize(text) OWNER TO postgres;

--
-- TOC entry 70 (class 1255 OID 205469)
-- Dependencies: 5
-- Name: normal_rand(integer, double precision, double precision); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION normal_rand(integer, double precision, double precision) RETURNS SETOF double precision
    AS '$libdir/tablefunc', 'normal_rand'
    LANGUAGE c STRICT;


ALTER FUNCTION public.normal_rand(integer, double precision, double precision) OWNER TO postgres;

--
-- TOC entry 71 (class 1255 OID 205470)
-- Dependencies: 397 5
-- Name: numnode(tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION numnode(tsquery) RETURNS integer
    AS '$libdir/tsearch2', 'tsquery_numnode'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.numnode(tsquery) OWNER TO postgres;

--
-- TOC entry 72 (class 1255 OID 205471)
-- Dependencies: 5 407
-- Name: parse(oid, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION parse(oid, text) RETURNS SETOF tokenout
    AS '$libdir/tsearch2', 'parse'
    LANGUAGE c STRICT;


ALTER FUNCTION public.parse(oid, text) OWNER TO postgres;

--
-- TOC entry 73 (class 1255 OID 205472)
-- Dependencies: 407 5
-- Name: parse(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION parse(text, text) RETURNS SETOF tokenout
    AS '$libdir/tsearch2', 'parse_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.parse(text, text) OWNER TO postgres;

--
-- TOC entry 74 (class 1255 OID 205473)
-- Dependencies: 407 5
-- Name: parse(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION parse(text) RETURNS SETOF tokenout
    AS '$libdir/tsearch2', 'parse_current'
    LANGUAGE c STRICT;


ALTER FUNCTION public.parse(text) OWNER TO postgres;

--
-- TOC entry 75 (class 1255 OID 205474)
-- Dependencies: 397 5
-- Name: plainto_tsquery(oid, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plainto_tsquery(oid, text) RETURNS tsquery
    AS '$libdir/tsearch2', 'plainto_tsquery'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.plainto_tsquery(oid, text) OWNER TO postgres;

--
-- TOC entry 76 (class 1255 OID 205475)
-- Dependencies: 397 5
-- Name: plainto_tsquery(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plainto_tsquery(text, text) RETURNS tsquery
    AS '$libdir/tsearch2', 'plainto_tsquery_name'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.plainto_tsquery(text, text) OWNER TO postgres;

--
-- TOC entry 77 (class 1255 OID 205476)
-- Dependencies: 5 397
-- Name: plainto_tsquery(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plainto_tsquery(text) RETURNS tsquery
    AS '$libdir/tsearch2', 'plainto_tsquery_current'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.plainto_tsquery(text) OWNER TO postgres;

--
-- TOC entry 78 (class 1255 OID 205477)
-- Dependencies: 5
-- Name: prsd_end(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION prsd_end(internal) RETURNS void
    AS '$libdir/tsearch2', 'prsd_end'
    LANGUAGE c;


ALTER FUNCTION public.prsd_end(internal) OWNER TO postgres;

--
-- TOC entry 79 (class 1255 OID 205478)
-- Dependencies: 5
-- Name: prsd_getlexeme(internal, internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION prsd_getlexeme(internal, internal, internal) RETURNS integer
    AS '$libdir/tsearch2', 'prsd_getlexeme'
    LANGUAGE c;


ALTER FUNCTION public.prsd_getlexeme(internal, internal, internal) OWNER TO postgres;

--
-- TOC entry 80 (class 1255 OID 205479)
-- Dependencies: 5
-- Name: prsd_headline(internal, internal, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION prsd_headline(internal, internal, internal) RETURNS internal
    AS '$libdir/tsearch2', 'prsd_headline'
    LANGUAGE c;


ALTER FUNCTION public.prsd_headline(internal, internal, internal) OWNER TO postgres;

--
-- TOC entry 81 (class 1255 OID 205480)
-- Dependencies: 5
-- Name: prsd_lextype(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION prsd_lextype(internal) RETURNS internal
    AS '$libdir/tsearch2', 'prsd_lextype'
    LANGUAGE c;


ALTER FUNCTION public.prsd_lextype(internal) OWNER TO postgres;

--
-- TOC entry 82 (class 1255 OID 205481)
-- Dependencies: 5
-- Name: prsd_start(internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION prsd_start(internal, integer) RETURNS internal
    AS '$libdir/tsearch2', 'prsd_start'
    LANGUAGE c;


ALTER FUNCTION public.prsd_start(internal, integer) OWNER TO postgres;

--
-- TOC entry 83 (class 1255 OID 205482)
-- Dependencies: 397 5
-- Name: querytree(tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION querytree(tsquery) RETURNS text
    AS '$libdir/tsearch2', 'tsquerytree'
    LANGUAGE c STRICT;


ALTER FUNCTION public.querytree(tsquery) OWNER TO postgres;

--
-- TOC entry 84 (class 1255 OID 205483)
-- Dependencies: 400 5 397
-- Name: rank(real[], tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank(real[], tsvector, tsquery) RETURNS real
    AS '$libdir/tsearch2', 'rank'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank(real[], tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 85 (class 1255 OID 205484)
-- Dependencies: 400 5 397
-- Name: rank(real[], tsvector, tsquery, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank(real[], tsvector, tsquery, integer) RETURNS real
    AS '$libdir/tsearch2', 'rank'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank(real[], tsvector, tsquery, integer) OWNER TO postgres;

--
-- TOC entry 86 (class 1255 OID 205485)
-- Dependencies: 400 5 397
-- Name: rank(tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank(tsvector, tsquery) RETURNS real
    AS '$libdir/tsearch2', 'rank_def'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank(tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 87 (class 1255 OID 205486)
-- Dependencies: 400 5 397
-- Name: rank(tsvector, tsquery, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank(tsvector, tsquery, integer) RETURNS real
    AS '$libdir/tsearch2', 'rank_def'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank(tsvector, tsquery, integer) OWNER TO postgres;

--
-- TOC entry 88 (class 1255 OID 205487)
-- Dependencies: 400 5 397
-- Name: rank_cd(real[], tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank_cd(real[], tsvector, tsquery) RETURNS real
    AS '$libdir/tsearch2', 'rank_cd'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank_cd(real[], tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 89 (class 1255 OID 205488)
-- Dependencies: 400 5 397
-- Name: rank_cd(real[], tsvector, tsquery, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank_cd(real[], tsvector, tsquery, integer) RETURNS real
    AS '$libdir/tsearch2', 'rank_cd'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank_cd(real[], tsvector, tsquery, integer) OWNER TO postgres;

--
-- TOC entry 90 (class 1255 OID 205489)
-- Dependencies: 400 5 397
-- Name: rank_cd(tsvector, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank_cd(tsvector, tsquery) RETURNS real
    AS '$libdir/tsearch2', 'rank_cd_def'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank_cd(tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 91 (class 1255 OID 205490)
-- Dependencies: 397 5 400
-- Name: rank_cd(tsvector, tsquery, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rank_cd(tsvector, tsquery, integer) RETURNS real
    AS '$libdir/tsearch2', 'rank_cd_def'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rank_cd(tsvector, tsquery, integer) OWNER TO postgres;

--
-- TOC entry 92 (class 1255 OID 205491)
-- Dependencies: 5
-- Name: reset_tsearch(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION reset_tsearch() RETURNS void
    AS '$libdir/tsearch2', 'reset_tsearch'
    LANGUAGE c STRICT;


ALTER FUNCTION public.reset_tsearch() OWNER TO postgres;

--
-- TOC entry 93 (class 1255 OID 205492)
-- Dependencies: 397 5 397
-- Name: rewrite(tsquery, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rewrite(tsquery, text) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_rewrite'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rewrite(tsquery, text) OWNER TO postgres;

--
-- TOC entry 94 (class 1255 OID 205493)
-- Dependencies: 397 5 397 397 397
-- Name: rewrite(tsquery, tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rewrite(tsquery, tsquery, tsquery) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_rewrite_query'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rewrite(tsquery, tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 95 (class 1255 OID 205494)
-- Dependencies: 397 5 397 399
-- Name: rewrite_accum(tsquery, tsquery[]); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rewrite_accum(tsquery, tsquery[]) RETURNS tsquery
    AS '$libdir/tsearch2', 'rewrite_accum'
    LANGUAGE c;


ALTER FUNCTION public.rewrite_accum(tsquery, tsquery[]) OWNER TO postgres;

--
-- TOC entry 96 (class 1255 OID 205495)
-- Dependencies: 5 397 397
-- Name: rewrite_finish(tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rewrite_finish(tsquery) RETURNS tsquery
    AS '$libdir/tsearch2', 'rewrite_finish'
    LANGUAGE c;


ALTER FUNCTION public.rewrite_finish(tsquery) OWNER TO postgres;

--
-- TOC entry 97 (class 1255 OID 205496)
-- Dependencies: 400 5 397
-- Name: rexectsq(tsquery, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION rexectsq(tsquery, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'rexectsq'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.rexectsq(tsquery, tsvector) OWNER TO postgres;

--
-- TOC entry 1813 (class 0 OID 0)
-- Dependencies: 97
-- Name: FUNCTION rexectsq(tsquery, tsvector); Type: COMMENT; Schema: public; Owner: postgres
--

COMMENT ON FUNCTION rexectsq(tsquery, tsvector) IS 'boolean operation with text index';


--
-- TOC entry 98 (class 1255 OID 205497)
-- Dependencies: 5
-- Name: set_curcfg(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curcfg(integer) RETURNS void
    AS '$libdir/tsearch2', 'set_curcfg'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curcfg(integer) OWNER TO postgres;

--
-- TOC entry 99 (class 1255 OID 205498)
-- Dependencies: 5
-- Name: set_curcfg(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curcfg(text) RETURNS void
    AS '$libdir/tsearch2', 'set_curcfg_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curcfg(text) OWNER TO postgres;

--
-- TOC entry 100 (class 1255 OID 205499)
-- Dependencies: 5
-- Name: set_curdict(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curdict(integer) RETURNS void
    AS '$libdir/tsearch2', 'set_curdict'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curdict(integer) OWNER TO postgres;

--
-- TOC entry 101 (class 1255 OID 205500)
-- Dependencies: 5
-- Name: set_curdict(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curdict(text) RETURNS void
    AS '$libdir/tsearch2', 'set_curdict_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curdict(text) OWNER TO postgres;

--
-- TOC entry 102 (class 1255 OID 205501)
-- Dependencies: 5
-- Name: set_curprs(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curprs(integer) RETURNS void
    AS '$libdir/tsearch2', 'set_curprs'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curprs(integer) OWNER TO postgres;

--
-- TOC entry 103 (class 1255 OID 205502)
-- Dependencies: 5
-- Name: set_curprs(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION set_curprs(text) RETURNS void
    AS '$libdir/tsearch2', 'set_curprs_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.set_curprs(text) OWNER TO postgres;

--
-- TOC entry 104 (class 1255 OID 205503)
-- Dependencies: 400 5 400
-- Name: setweight(tsvector, "char"); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION setweight(tsvector, "char") RETURNS tsvector
    AS '$libdir/tsearch2', 'setweight'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.setweight(tsvector, "char") OWNER TO postgres;

--
-- TOC entry 105 (class 1255 OID 205504)
-- Dependencies: 5
-- Name: show_curcfg(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION show_curcfg() RETURNS oid
    AS '$libdir/tsearch2', 'show_curcfg'
    LANGUAGE c STRICT;


ALTER FUNCTION public.show_curcfg() OWNER TO postgres;

--
-- TOC entry 106 (class 1255 OID 205505)
-- Dependencies: 5
-- Name: snb_en_init(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION snb_en_init(internal) RETURNS internal
    AS '$libdir/tsearch2', 'snb_en_init'
    LANGUAGE c;


ALTER FUNCTION public.snb_en_init(internal) OWNER TO postgres;

--
-- TOC entry 107 (class 1255 OID 205506)
-- Dependencies: 5
-- Name: snb_lexize(internal, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION snb_lexize(internal, internal, integer) RETURNS internal
    AS '$libdir/tsearch2', 'snb_lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.snb_lexize(internal, internal, integer) OWNER TO postgres;

--
-- TOC entry 108 (class 1255 OID 205507)
-- Dependencies: 5
-- Name: snb_ru_init_koi8(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION snb_ru_init_koi8(internal) RETURNS internal
    AS '$libdir/tsearch2', 'snb_ru_init_koi8'
    LANGUAGE c;


ALTER FUNCTION public.snb_ru_init_koi8(internal) OWNER TO postgres;

--
-- TOC entry 109 (class 1255 OID 205508)
-- Dependencies: 5
-- Name: snb_ru_init_utf8(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION snb_ru_init_utf8(internal) RETURNS internal
    AS '$libdir/tsearch2', 'snb_ru_init_utf8'
    LANGUAGE c;


ALTER FUNCTION public.snb_ru_init_utf8(internal) OWNER TO postgres;

--
-- TOC entry 110 (class 1255 OID 205509)
-- Dependencies: 5
-- Name: spell_init(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION spell_init(internal) RETURNS internal
    AS '$libdir/tsearch2', 'spell_init'
    LANGUAGE c;


ALTER FUNCTION public.spell_init(internal) OWNER TO postgres;

--
-- TOC entry 111 (class 1255 OID 205510)
-- Dependencies: 5
-- Name: spell_lexize(internal, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION spell_lexize(internal, internal, integer) RETURNS internal
    AS '$libdir/tsearch2', 'spell_lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.spell_lexize(internal, internal, integer) OWNER TO postgres;

--
-- TOC entry 112 (class 1255 OID 205511)
-- Dependencies: 5 403
-- Name: stat(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION stat(text) RETURNS SETOF statinfo
    AS '$libdir/tsearch2', 'ts_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.stat(text) OWNER TO postgres;

--
-- TOC entry 113 (class 1255 OID 205512)
-- Dependencies: 403 5
-- Name: stat(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION stat(text, text) RETURNS SETOF statinfo
    AS '$libdir/tsearch2', 'ts_stat'
    LANGUAGE c STRICT;


ALTER FUNCTION public.stat(text, text) OWNER TO postgres;

--
-- TOC entry 114 (class 1255 OID 205513)
-- Dependencies: 400 5 400
-- Name: strip(tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION strip(tsvector) RETURNS tsvector
    AS '$libdir/tsearch2', 'strip'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.strip(tsvector) OWNER TO postgres;

--
-- TOC entry 115 (class 1255 OID 205514)
-- Dependencies: 5
-- Name: syn_init(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION syn_init(internal) RETURNS internal
    AS '$libdir/tsearch2', 'syn_init'
    LANGUAGE c;


ALTER FUNCTION public.syn_init(internal) OWNER TO postgres;

--
-- TOC entry 116 (class 1255 OID 205515)
-- Dependencies: 5
-- Name: syn_lexize(internal, internal, integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION syn_lexize(internal, internal, integer) RETURNS internal
    AS '$libdir/tsearch2', 'syn_lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.syn_lexize(internal, internal, integer) OWNER TO postgres;

--
-- TOC entry 117 (class 1255 OID 205516)
-- Dependencies: 5
-- Name: thesaurus_init(internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION thesaurus_init(internal) RETURNS internal
    AS '$libdir/tsearch2', 'thesaurus_init'
    LANGUAGE c;


ALTER FUNCTION public.thesaurus_init(internal) OWNER TO postgres;

--
-- TOC entry 118 (class 1255 OID 205517)
-- Dependencies: 5
-- Name: thesaurus_lexize(internal, internal, integer, internal); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION thesaurus_lexize(internal, internal, integer, internal) RETURNS internal
    AS '$libdir/tsearch2', 'thesaurus_lexize'
    LANGUAGE c STRICT;


ALTER FUNCTION public.thesaurus_lexize(internal, internal, integer, internal) OWNER TO postgres;

--
-- TOC entry 119 (class 1255 OID 205518)
-- Dependencies: 5 397
-- Name: to_tsquery(oid, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsquery(oid, text) RETURNS tsquery
    AS '$libdir/tsearch2', 'to_tsquery'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsquery(oid, text) OWNER TO postgres;

--
-- TOC entry 120 (class 1255 OID 205519)
-- Dependencies: 5 397
-- Name: to_tsquery(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsquery(text, text) RETURNS tsquery
    AS '$libdir/tsearch2', 'to_tsquery_name'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsquery(text, text) OWNER TO postgres;

--
-- TOC entry 121 (class 1255 OID 205520)
-- Dependencies: 5 397
-- Name: to_tsquery(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsquery(text) RETURNS tsquery
    AS '$libdir/tsearch2', 'to_tsquery_current'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsquery(text) OWNER TO postgres;

--
-- TOC entry 122 (class 1255 OID 205521)
-- Dependencies: 400 5
-- Name: to_tsvector(oid, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsvector(oid, text) RETURNS tsvector
    AS '$libdir/tsearch2', 'to_tsvector'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsvector(oid, text) OWNER TO postgres;

--
-- TOC entry 123 (class 1255 OID 205522)
-- Dependencies: 5 400
-- Name: to_tsvector(text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsvector(text, text) RETURNS tsvector
    AS '$libdir/tsearch2', 'to_tsvector_name'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsvector(text, text) OWNER TO postgres;

--
-- TOC entry 124 (class 1255 OID 205523)
-- Dependencies: 5 400
-- Name: to_tsvector(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION to_tsvector(text) RETURNS tsvector
    AS '$libdir/tsearch2', 'to_tsvector_current'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.to_tsvector(text) OWNER TO postgres;

--
-- TOC entry 125 (class 1255 OID 205524)
-- Dependencies: 5 408
-- Name: token_type(integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION token_type(integer) RETURNS SETOF tokentype
    AS '$libdir/tsearch2', 'token_type'
    LANGUAGE c STRICT;


ALTER FUNCTION public.token_type(integer) OWNER TO postgres;

--
-- TOC entry 126 (class 1255 OID 205525)
-- Dependencies: 408 5
-- Name: token_type(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION token_type(text) RETURNS SETOF tokentype
    AS '$libdir/tsearch2', 'token_type_byname'
    LANGUAGE c STRICT;


ALTER FUNCTION public.token_type(text) OWNER TO postgres;

--
-- TOC entry 127 (class 1255 OID 205526)
-- Dependencies: 5 408
-- Name: token_type(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION token_type() RETURNS SETOF tokentype
    AS '$libdir/tsearch2', 'token_type_current'
    LANGUAGE c STRICT;


ALTER FUNCTION public.token_type() OWNER TO postgres;

--
-- TOC entry 128 (class 1255 OID 205527)
-- Dependencies: 409 5
-- Name: ts_debug(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION ts_debug(text) RETURNS SETOF tsdebug
    AS $_$
select 
        m.ts_name,
        t.alias as tok_type,
        t.descr as description,
        p.token,
        m.dict_name,
        strip(to_tsvector(p.token)) as tsvector
from
        parse( _get_parser_from_curcfg(), $1 ) as p,
        token_type() as t,
        pg_ts_cfgmap as m,
        pg_ts_cfg as c
where
        t.tokid=p.tokid and
        t.alias = m.tok_alias and 
        m.ts_name=c.ts_name and 
        c.oid=show_curcfg() 
$_$
    LANGUAGE sql STRICT;


ALTER FUNCTION public.ts_debug(text) OWNER TO postgres;

--
-- TOC entry 129 (class 1255 OID 205528)
-- Dependencies: 5
-- Name: tsearch2(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsearch2() RETURNS "trigger"
    AS '$libdir/tsearch2', 'tsearch2'
    LANGUAGE c;


ALTER FUNCTION public.tsearch2() OWNER TO postgres;

--
-- TOC entry 130 (class 1255 OID 205529)
-- Dependencies: 397 5 397
-- Name: tsq_mcontained(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsq_mcontained(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsq_mcontained'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsq_mcontained(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 131 (class 1255 OID 205530)
-- Dependencies: 397 5 397
-- Name: tsq_mcontains(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsq_mcontains(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsq_mcontains'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsq_mcontains(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 132 (class 1255 OID 205531)
-- Dependencies: 397 5 397 397
-- Name: tsquery_and(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_and(tsquery, tsquery) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_and'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_and(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 133 (class 1255 OID 205532)
-- Dependencies: 397 5 397
-- Name: tsquery_cmp(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_cmp(tsquery, tsquery) RETURNS integer
    AS '$libdir/tsearch2', 'tsquery_cmp'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_cmp(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 134 (class 1255 OID 205533)
-- Dependencies: 397 5 397
-- Name: tsquery_eq(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_eq(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_eq'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_eq(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 135 (class 1255 OID 205534)
-- Dependencies: 397 5 397
-- Name: tsquery_ge(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_ge(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_ge'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_ge(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 136 (class 1255 OID 205535)
-- Dependencies: 397 5 397
-- Name: tsquery_gt(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_gt(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_gt'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_gt(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 137 (class 1255 OID 205536)
-- Dependencies: 397 5 397
-- Name: tsquery_le(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_le(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_le'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_le(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 138 (class 1255 OID 205537)
-- Dependencies: 397 5 397
-- Name: tsquery_lt(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_lt(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_lt'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_lt(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 139 (class 1255 OID 205538)
-- Dependencies: 5 397 397
-- Name: tsquery_ne(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_ne(tsquery, tsquery) RETURNS boolean
    AS '$libdir/tsearch2', 'tsquery_ne'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_ne(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 140 (class 1255 OID 205539)
-- Dependencies: 5 397 397
-- Name: tsquery_not(tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_not(tsquery) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_not'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_not(tsquery) OWNER TO postgres;

--
-- TOC entry 141 (class 1255 OID 205540)
-- Dependencies: 5 397 397 397
-- Name: tsquery_or(tsquery, tsquery); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsquery_or(tsquery, tsquery) RETURNS tsquery
    AS '$libdir/tsearch2', 'tsquery_or'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsquery_or(tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 142 (class 1255 OID 205541)
-- Dependencies: 400 5 400
-- Name: tsvector_cmp(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_cmp(tsvector, tsvector) RETURNS integer
    AS '$libdir/tsearch2', 'tsvector_cmp'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_cmp(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 143 (class 1255 OID 205542)
-- Dependencies: 5 400 400
-- Name: tsvector_eq(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_eq(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_eq'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_eq(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 144 (class 1255 OID 205543)
-- Dependencies: 400 5 400
-- Name: tsvector_ge(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_ge(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_ge'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_ge(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 145 (class 1255 OID 205544)
-- Dependencies: 400 5 400
-- Name: tsvector_gt(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_gt(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_gt'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_gt(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 146 (class 1255 OID 205545)
-- Dependencies: 400 5 400
-- Name: tsvector_le(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_le(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_le'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_le(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 147 (class 1255 OID 205546)
-- Dependencies: 400 5 400
-- Name: tsvector_lt(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_lt(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_lt'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_lt(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 148 (class 1255 OID 205547)
-- Dependencies: 400 400 5
-- Name: tsvector_ne(tsvector, tsvector); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION tsvector_ne(tsvector, tsvector) RETURNS boolean
    AS '$libdir/tsearch2', 'tsvector_ne'
    LANGUAGE c IMMUTABLE STRICT;


ALTER FUNCTION public.tsvector_ne(tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 418 (class 1255 OID 205548)
-- Dependencies: 399 96 95 5 397
-- Name: rewrite(tsquery[]); Type: AGGREGATE; Schema: public; Owner: postgres
--

CREATE AGGREGATE rewrite(tsquery[]) (
    SFUNC = rewrite_accum,
    STYPE = tsquery,
    FINALFUNC = rewrite_finish
);


ALTER AGGREGATE public.rewrite(tsquery[]) OWNER TO postgres;

--
-- TOC entry 1085 (class 2617 OID 205549)
-- Dependencies: 397 397 5 140
-- Name: !!; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR !! (
    PROCEDURE = tsquery_not,
    RIGHTARG = tsquery
);


ALTER OPERATOR public.!! (NONE, tsquery) OWNER TO postgres;

--
-- TOC entry 1086 (class 2617 OID 205550)
-- Dependencies: 397 132 397 5 397
-- Name: &&; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR && (
    PROCEDURE = tsquery_and,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = &&,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.&& (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1087 (class 2617 OID 205553)
-- Dependencies: 5 147 400 400
-- Name: <; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR < (
    PROCEDURE = tsvector_lt,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = >,
    NEGATOR = >=,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.< (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1088 (class 2617 OID 205556)
-- Dependencies: 138 397 397 5
-- Name: <; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR < (
    PROCEDURE = tsquery_lt,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = >,
    NEGATOR = >=,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.< (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1089 (class 2617 OID 205557)
-- Dependencies: 146 5 400 400
-- Name: <=; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR <= (
    PROCEDURE = tsvector_le,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = >=,
    NEGATOR = >,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.<= (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1090 (class 2617 OID 205558)
-- Dependencies: 397 5 397 137
-- Name: <=; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR <= (
    PROCEDURE = tsquery_le,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = >=,
    NEGATOR = >,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.<= (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1091 (class 2617 OID 205560)
-- Dependencies: 148 400 5 400
-- Name: <>; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR <> (
    PROCEDURE = tsvector_ne,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = <>,
    NEGATOR = =,
    RESTRICT = neqsel,
    JOIN = neqjoinsel
);


ALTER OPERATOR public.<> (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1092 (class 2617 OID 205562)
-- Dependencies: 5 139 397 397
-- Name: <>; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR <> (
    PROCEDURE = tsquery_ne,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = <>,
    NEGATOR = =,
    RESTRICT = neqsel,
    JOIN = neqjoinsel
);


ALTER OPERATOR public.<> (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1093 (class 2617 OID 205564)
-- Dependencies: 5 130 397 397
-- Name: <@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR <@ (
    PROCEDURE = tsq_mcontained,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = @>,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.<@ (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1094 (class 2617 OID 205559)
-- Dependencies: 5 400 400 143
-- Name: =; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR = (
    PROCEDURE = tsvector_eq,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = =,
    NEGATOR = <>,
    RESTRICT = eqsel,
    JOIN = eqjoinsel,
    SORT1 = <,
    SORT2 = <,
    LTCMP = <,
    GTCMP = >
);


ALTER OPERATOR public.= (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1095 (class 2617 OID 205561)
-- Dependencies: 397 397 5 134
-- Name: =; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR = (
    PROCEDURE = tsquery_eq,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = =,
    NEGATOR = <>,
    RESTRICT = eqsel,
    JOIN = eqjoinsel,
    SORT1 = <,
    SORT2 = <,
    LTCMP = <,
    GTCMP = >
);


ALTER OPERATOR public.= (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1096 (class 2617 OID 205551)
-- Dependencies: 400 5 145 400
-- Name: >; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR > (
    PROCEDURE = tsvector_gt,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = <,
    NEGATOR = <=,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.> (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1097 (class 2617 OID 205554)
-- Dependencies: 136 5 397 397
-- Name: >; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR > (
    PROCEDURE = tsquery_gt,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = <,
    NEGATOR = <=,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.> (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1098 (class 2617 OID 205552)
-- Dependencies: 400 400 144 5
-- Name: >=; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR >= (
    PROCEDURE = tsvector_ge,
    LEFTARG = tsvector,
    RIGHTARG = tsvector,
    COMMUTATOR = <=,
    NEGATOR = <,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.>= (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1099 (class 2617 OID 205555)
-- Dependencies: 5 135 397 397
-- Name: >=; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR >= (
    PROCEDURE = tsquery_ge,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = <=,
    NEGATOR = <,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.>= (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1100 (class 2617 OID 205566)
-- Dependencies: 131 397 397 5
-- Name: @; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @ (
    PROCEDURE = tsq_mcontains,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = ~,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@ (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1101 (class 2617 OID 205563)
-- Dependencies: 131 5 397 397
-- Name: @>; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @> (
    PROCEDURE = tsq_mcontains,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = <@,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@> (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1103 (class 2617 OID 205567)
-- Dependencies: 5 400 397 41
-- Name: @@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @@ (
    PROCEDURE = exectsq,
    LEFTARG = tsvector,
    RIGHTARG = tsquery,
    COMMUTATOR = @@,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@@ (tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 1102 (class 2617 OID 205568)
-- Dependencies: 5 97 400 397
-- Name: @@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @@ (
    PROCEDURE = rexectsq,
    LEFTARG = tsquery,
    RIGHTARG = tsvector,
    COMMUTATOR = @@,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@@ (tsquery, tsvector) OWNER TO postgres;

--
-- TOC entry 1105 (class 2617 OID 205569)
-- Dependencies: 5 400 397 41
-- Name: @@@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @@@ (
    PROCEDURE = exectsq,
    LEFTARG = tsvector,
    RIGHTARG = tsquery,
    COMMUTATOR = @@@,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@@@ (tsvector, tsquery) OWNER TO postgres;

--
-- TOC entry 1104 (class 2617 OID 205570)
-- Dependencies: 5 397 400 97
-- Name: @@@; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR @@@ (
    PROCEDURE = rexectsq,
    LEFTARG = tsquery,
    RIGHTARG = tsvector,
    COMMUTATOR = @@@,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.@@@ (tsquery, tsvector) OWNER TO postgres;

--
-- TOC entry 1106 (class 2617 OID 205571)
-- Dependencies: 28 400 400 400 5
-- Name: ||; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR || (
    PROCEDURE = concat,
    LEFTARG = tsvector,
    RIGHTARG = tsvector
);


ALTER OPERATOR public.|| (tsvector, tsvector) OWNER TO postgres;

--
-- TOC entry 1107 (class 2617 OID 205572)
-- Dependencies: 5 397 141 397 397
-- Name: ||; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR || (
    PROCEDURE = tsquery_or,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = ||,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.|| (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1108 (class 2617 OID 205565)
-- Dependencies: 397 397 5 130
-- Name: ~; Type: OPERATOR; Schema: public; Owner: postgres
--

CREATE OPERATOR ~ (
    PROCEDURE = tsq_mcontained,
    LEFTARG = tsquery,
    RIGHTARG = tsquery,
    COMMUTATOR = @,
    RESTRICT = contsel,
    JOIN = contjoinsel
);


ALTER OPERATOR public.~ (tsquery, tsquery) OWNER TO postgres;

--
-- TOC entry 1211 (class 2616 OID 205573)
-- Dependencies: 1105 43 45 400 1103 44 5
-- Name: gin_tsvector_ops; Type: OPERATOR CLASS; Schema: public; Owner: postgres
--

CREATE OPERATOR CLASS gin_tsvector_ops
    DEFAULT FOR TYPE tsvector USING gin AS
    STORAGE text ,
    OPERATOR 1 @@(tsvector,tsquery) ,
    OPERATOR 2 @@@(tsvector,tsquery) RECHECK ,
    FUNCTION 1 bttextcmp(text,text) ,
    FUNCTION 2 gin_extract_tsvector(tsvector,internal) ,
    FUNCTION 3 gin_extract_tsquery(tsquery,internal,internal) ,
    FUNCTION 4 gin_ts_consistent(internal,internal,tsquery);


ALTER OPERATOR CLASS public.gin_tsvector_ops USING gin OWNER TO postgres;

--
-- TOC entry 1212 (class 2616 OID 205574)
-- Dependencies: 1100 51 50 49 48 46 52 47 1108 1093 1101 391 397 5
-- Name: gist_tp_tsquery_ops; Type: OPERATOR CLASS; Schema: public; Owner: postgres
--

CREATE OPERATOR CLASS gist_tp_tsquery_ops
    DEFAULT FOR TYPE tsquery USING gist AS
    STORAGE gtsq ,
    OPERATOR 7 @>(tsquery,tsquery) RECHECK ,
    OPERATOR 8 <@(tsquery,tsquery) RECHECK ,
    OPERATOR 13 @(tsquery,tsquery) RECHECK ,
    OPERATOR 14 ~(tsquery,tsquery) RECHECK ,
    FUNCTION 1 gtsq_consistent(gtsq,internal,integer) ,
    FUNCTION 2 gtsq_union(bytea,internal) ,
    FUNCTION 3 gtsq_compress(internal) ,
    FUNCTION 4 gtsq_decompress(internal) ,
    FUNCTION 5 gtsq_penalty(internal,internal,internal) ,
    FUNCTION 6 gtsq_picksplit(internal,internal) ,
    FUNCTION 7 gtsq_same(gtsq,gtsq,internal);


ALTER OPERATOR CLASS public.gist_tp_tsquery_ops USING gist OWNER TO postgres;

--
-- TOC entry 1213 (class 2616 OID 205575)
-- Dependencies: 55 53 1103 54 59 394 400 5 58 57 56
-- Name: gist_tsvector_ops; Type: OPERATOR CLASS; Schema: public; Owner: postgres
--

CREATE OPERATOR CLASS gist_tsvector_ops
    DEFAULT FOR TYPE tsvector USING gist AS
    STORAGE gtsvector ,
    OPERATOR 1 @@(tsvector,tsquery) RECHECK ,
    FUNCTION 1 gtsvector_consistent(gtsvector,internal,integer) ,
    FUNCTION 2 gtsvector_union(internal,internal) ,
    FUNCTION 3 gtsvector_compress(internal) ,
    FUNCTION 4 gtsvector_decompress(internal) ,
    FUNCTION 5 gtsvector_penalty(internal,internal,internal) ,
    FUNCTION 6 gtsvector_picksplit(internal,internal) ,
    FUNCTION 7 gtsvector_same(gtsvector,gtsvector,internal);


ALTER OPERATOR CLASS public.gist_tsvector_ops USING gist OWNER TO postgres;

--
-- TOC entry 1214 (class 2616 OID 205576)
-- Dependencies: 1095 5 397 1088 1090 1099 1097 133
-- Name: tsquery_ops; Type: OPERATOR CLASS; Schema: public; Owner: postgres
--

CREATE OPERATOR CLASS tsquery_ops
    DEFAULT FOR TYPE tsquery USING btree AS
    OPERATOR 1 <(tsquery,tsquery) ,
    OPERATOR 2 <=(tsquery,tsquery) ,
    OPERATOR 3 =(tsquery,tsquery) ,
    OPERATOR 4 >=(tsquery,tsquery) ,
    OPERATOR 5 >(tsquery,tsquery) ,
    FUNCTION 1 tsquery_cmp(tsquery,tsquery);


ALTER OPERATOR CLASS public.tsquery_ops USING btree OWNER TO postgres;

--
-- TOC entry 1215 (class 2616 OID 205577)
-- Dependencies: 142 1096 1098 1094 1089 1087 400 5
-- Name: tsvector_ops; Type: OPERATOR CLASS; Schema: public; Owner: postgres
--

CREATE OPERATOR CLASS tsvector_ops
    DEFAULT FOR TYPE tsvector USING btree AS
    OPERATOR 1 <(tsvector,tsvector) ,
    OPERATOR 2 <=(tsvector,tsvector) ,
    OPERATOR 3 =(tsvector,tsvector) ,
    OPERATOR 4 >=(tsvector,tsvector) ,
    OPERATOR 5 >(tsvector,tsvector) ,
    FUNCTION 1 tsvector_cmp(tsvector,tsvector);


ALTER OPERATOR CLASS public.tsvector_ops USING btree OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1459 (class 1259 OID 205578)
-- Dependencies: 5
-- Name: hilosequences; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE hilosequences (
    sequencename character varying(50) NOT NULL,
    highvalues integer NOT NULL
);


ALTER TABLE public.hilosequences OWNER TO postgres;

--
-- TOC entry 1460 (class 1259 OID 205580)
-- Dependencies: 5
-- Name: jms_message_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE jms_message_log (
    messageid integer NOT NULL,
    destination character varying(255) NOT NULL,
    txid integer,
    txop character(1),
    lateclone character(1),
    messageblob bytea
);


ALTER TABLE public.jms_message_log OWNER TO postgres;

--
-- TOC entry 1461 (class 1259 OID 205585)
-- Dependencies: 5
-- Name: jms_reference_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE jms_reference_log (
    messageid integer NOT NULL,
    destination character varying(256) NOT NULL,
    txid integer,
    txop character(1),
    redelivered character(1),
    redelivers integer
);


ALTER TABLE public.jms_reference_log OWNER TO postgres;

--
-- TOC entry 1462 (class 1259 OID 205587)
-- Dependencies: 5
-- Name: jms_transaction_log; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE jms_transaction_log (
    txid integer
);


ALTER TABLE public.jms_transaction_log OWNER TO postgres;

--
-- TOC entry 1463 (class 1259 OID 205589)
-- Dependencies: 5
-- Name: timers; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE timers (
    timerid character varying(80) NOT NULL,
    targetid character varying(80) NOT NULL,
    initialdate timestamp without time zone NOT NULL,
    timerinterval bigint,
    instancepk bytea,
    info bytea
);


ALTER TABLE public.timers OWNER TO postgres;

--
-- TOC entry 1802 (class 0 OID 205578)
-- Dependencies: 1459
-- Data for Name: hilosequences; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY hilosequences (sequencename, highvalues) FROM stdin;
general	0
\.


--
-- TOC entry 1803 (class 0 OID 205580)
-- Dependencies: 1460
-- Data for Name: jms_message_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY jms_message_log (messageid, destination, txid, txop, lateclone, messageblob) FROM stdin;
\.


--
-- TOC entry 1804 (class 0 OID 205585)
-- Dependencies: 1461
-- Data for Name: jms_reference_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY jms_reference_log (messageid, destination, txid, txop, redelivered, redelivers) FROM stdin;
\.


--
-- TOC entry 1805 (class 0 OID 205587)
-- Dependencies: 1462
-- Data for Name: jms_transaction_log; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY jms_transaction_log (txid) FROM stdin;
\.


--
-- TOC entry 1806 (class 0 OID 205589)
-- Dependencies: 1463
-- Data for Name: timers; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY timers (timerid, targetid, initialdate, timerinterval, instancepk, info) FROM stdin;
\.


--
-- TOC entry 1795 (class 2606 OID 205595)
-- Dependencies: 1459 1459
-- Name: hilo_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY hilosequences
    ADD CONSTRAINT hilo_pk PRIMARY KEY (sequencename);


--
-- TOC entry 1797 (class 2606 OID 205597)
-- Dependencies: 1460 1460 1460
-- Name: jms_message_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY jms_message_log
    ADD CONSTRAINT jms_message_log_pkey PRIMARY KEY (messageid, destination);


--
-- TOC entry 1799 (class 2606 OID 205599)
-- Dependencies: 1461 1461 1461
-- Name: jms_reference_log_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY jms_reference_log
    ADD CONSTRAINT jms_reference_log_pkey PRIMARY KEY (messageid, destination);


--
-- TOC entry 1801 (class 2606 OID 205601)
-- Dependencies: 1463 1463 1463
-- Name: timers_pk; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY timers
    ADD CONSTRAINT timers_pk PRIMARY KEY (timerid, targetid);


--
-- TOC entry 1811 (class 0 OID 0)
-- Dependencies: 5
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2008-11-14 13:23:27

--
-- PostgreSQL database dump complete
--

