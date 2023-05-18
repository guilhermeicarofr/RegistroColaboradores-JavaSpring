--
-- PostgreSQL database dump
--

-- Dumped from database version 14.7 (Ubuntu 14.7-0ubuntu0.22.04.1)
-- Dumped by pg_dump version 14.7 (Ubuntu 14.7-0ubuntu0.22.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: colaborador; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.colaborador (
    id integer NOT NULL,
    cpf text NOT NULL,
    nome text NOT NULL,
    admissao text NOT NULL,
    funcao text NOT NULL,
    remuneracao text NOT NULL
);


ALTER TABLE public.colaborador OWNER TO postgres;

--
-- Name: colaborador_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.colaborador_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.colaborador_id_seq OWNER TO postgres;

--
-- Name: colaborador_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.colaborador_id_seq OWNED BY public.colaborador.id;


--
-- Name: subordinacao; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.subordinacao (
    id integer NOT NULL,
    gerente integer NOT NULL,
    subordinado integer NOT NULL
);


ALTER TABLE public.subordinacao OWNER TO postgres;

--
-- Name: subordinacao_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.subordinacao_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.subordinacao_id_seq OWNER TO postgres;

--
-- Name: subordinacao_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.subordinacao_id_seq OWNED BY public.subordinacao.id;


--
-- Name: colaborador id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador ALTER COLUMN id SET DEFAULT nextval('public.colaborador_id_seq'::regclass);


--
-- Name: subordinacao id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subordinacao ALTER COLUMN id SET DEFAULT nextval('public.subordinacao_id_seq'::regclass);


--
-- Data for Name: colaborador; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.colaborador (id, cpf, nome, admissao, funcao, remuneracao) FROM stdin;
2	900.060.101-20	Jose	31/02/2016	diretor	20000,00
3	900.160.101-20	Jorge	31/02/2016	diretor	20000,00
4	900.164.101-20	Jordan	31/02/2016	diretor	20000,00
5	900.164.101-29	Gregory	31/02/2016	gerente	20000,00
6	901.164.101-29	Guilherme	31/02/2019	gerente	20000,00
7	931.164.101-29	Giovanni	31/02/2019	gerente	20000,00
8	936.164.101-29	Gustavo	31/02/2010	gerente	20000,00
9	936.164.101-21	Gabriel	31/02/2020	gerente	20000,00
10	936.161.101-21	Teodoro	31/02/2021	techlead	2500,00
11	936.168.101-21	Tadeu	31/08/2021	techlead	2500,00
12	936.125.101-21	Daniel	12/01/2023	desenvolvedor	2000,00
1	900.060.100-20	Joao	31/02/2019	presidente	20000,00
\.


--
-- Data for Name: subordinacao; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.subordinacao (id, gerente, subordinado) FROM stdin;
4	2	5
7	3	6
8	4	7
9	4	8
10	4	9
11	5	10
12	5	11
13	10	12
14	1	2
15	1	3
16	1	4
\.


--
-- Name: colaborador_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.colaborador_id_seq', 12, true);


--
-- Name: subordinacao_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.subordinacao_id_seq', 16, true);


--
-- Name: colaborador colaborador_cpf_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_cpf_key UNIQUE (cpf);


--
-- Name: colaborador colaborador_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.colaborador
    ADD CONSTRAINT colaborador_pk PRIMARY KEY (id);


--
-- Name: subordinacao subordinacao_pk; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subordinacao
    ADD CONSTRAINT subordinacao_pk PRIMARY KEY (id);


--
-- Name: subordinacao subordinacao_subordinado_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subordinacao
    ADD CONSTRAINT subordinacao_subordinado_key UNIQUE (subordinado);


--
-- Name: subordinacao subordinacao_fk0; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subordinacao
    ADD CONSTRAINT subordinacao_fk0 FOREIGN KEY (gerente) REFERENCES public.colaborador(id);


--
-- Name: subordinacao subordinacao_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.subordinacao
    ADD CONSTRAINT subordinacao_fk1 FOREIGN KEY (subordinado) REFERENCES public.colaborador(id);


--
-- PostgreSQL database dump complete
--

