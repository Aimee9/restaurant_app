--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: cuisine; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE cuisine (
    cuisine_id integer NOT NULL,
    cuisine_type character varying
);


ALTER TABLE cuisine OWNER TO "Guest";

--
-- Name: cuisine_cuisine_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE cuisine_cuisine_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE cuisine_cuisine_id_seq OWNER TO "Guest";

--
-- Name: cuisine_cuisine_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE cuisine_cuisine_id_seq OWNED BY cuisine.cuisine_id;


--
-- Name: restaurants; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE restaurants (
    rest_id integer NOT NULL,
    cuisine_id integer,
    rest_name character varying,
    price character varying,
    hours character varying
);


ALTER TABLE restaurants OWNER TO "Guest";

--
-- Name: restaurants_rest_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE restaurants_rest_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE restaurants_rest_id_seq OWNER TO "Guest";

--
-- Name: restaurants_rest_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE restaurants_rest_id_seq OWNED BY restaurants.rest_id;


--
-- Name: reviews; Type: TABLE; Schema: public; Owner: Guest; Tablespace: 
--

CREATE TABLE reviews (
    review_id integer NOT NULL,
    reviewer character varying,
    review text,
    rest_id integer
);


ALTER TABLE reviews OWNER TO "Guest";

--
-- Name: reviews_review_id_seq; Type: SEQUENCE; Schema: public; Owner: Guest
--

CREATE SEQUENCE reviews_review_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE reviews_review_id_seq OWNER TO "Guest";

--
-- Name: reviews_review_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Guest
--

ALTER SEQUENCE reviews_review_id_seq OWNED BY reviews.review_id;


--
-- Name: cuisine_id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY cuisine ALTER COLUMN cuisine_id SET DEFAULT nextval('cuisine_cuisine_id_seq'::regclass);


--
-- Name: rest_id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY restaurants ALTER COLUMN rest_id SET DEFAULT nextval('restaurants_rest_id_seq'::regclass);


--
-- Name: review_id; Type: DEFAULT; Schema: public; Owner: Guest
--

ALTER TABLE ONLY reviews ALTER COLUMN review_id SET DEFAULT nextval('reviews_review_id_seq'::regclass);


--
-- Data for Name: cuisine; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY cuisine (cuisine_id, cuisine_type) FROM stdin;
19	foodtype1
20	foodtype2
21	foodtype3
\.


--
-- Name: cuisine_cuisine_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('cuisine_cuisine_id_seq', 21, true);


--
-- Data for Name: restaurants; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY restaurants (rest_id, cuisine_id, rest_name, price, hours) FROM stdin;
44	19	place1	$$	24/7
45	19	place2	$$	24/7
46	19	place 3	$$$	never
\.


--
-- Name: restaurants_rest_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('restaurants_rest_id_seq', 46, true);


--
-- Data for Name: reviews; Type: TABLE DATA; Schema: public; Owner: Guest
--

COPY reviews (review_id, reviewer, review, rest_id) FROM stdin;
\.


--
-- Name: reviews_review_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Guest
--

SELECT pg_catalog.setval('reviews_review_id_seq', 1, false);


--
-- Name: cuisine_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY cuisine
    ADD CONSTRAINT cuisine_pkey PRIMARY KEY (cuisine_id);


--
-- Name: restaurants_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY restaurants
    ADD CONSTRAINT restaurants_pkey PRIMARY KEY (rest_id);


--
-- Name: reviews_pkey; Type: CONSTRAINT; Schema: public; Owner: Guest; Tablespace: 
--

ALTER TABLE ONLY reviews
    ADD CONSTRAINT reviews_pkey PRIMARY KEY (review_id);


--
-- Name: public; Type: ACL; Schema: -; Owner: epicodus
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM epicodus;
GRANT ALL ON SCHEMA public TO epicodus;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

