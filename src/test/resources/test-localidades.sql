DROP TABLE IF EXISTS SEQUENCE_TABLE;
DROP TABLE IF EXISTS localidades;
DROP TABLE IF EXISTS provincias;

CREATE TABLE SEQUENCE_TABLE (
	seq_name VARCHAR(200) NOT NULL,
	seq_value BIGINT NOT NULL,
	PRIMARY KEY(seq_name)
);

INSERT INTO SEQUENCE_TABLE VALUES ('provincia_seq', 5);
INSERT INTO SEQUENCE_TABLE VALUES ('localidad_seq', 1);


CREATE TABLE provincias (
	id_provincia INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	region CHAR(3) NOT NULL,
	PRIMARY KEY(id_provincia)
);

INSERT INTO provincias VALUES (1, 'SANTA FE', 'PAM');
INSERT INTO provincias VALUES (2, 'CORDOBA', 'PAM');
INSERT INTO provincias VALUES (3, 'ENTRE RIOS', 'PAM');
INSERT INTO provincias VALUES (4, 'CORRIENTES', 'PAM');
INSERT INTO provincias VALUES (5, 'FORMOSA', 'PAM');

CREATE TABLE localidades (
	id_localidad INT NOT NULL,
	nombre VARCHAR(300) NOT NULL,
	id_provincia INT NOT NULL,
	codigo_postal VARCHAR(10) NOT NULL,
	PRIMARY KEY(id_provincia),
	FOREIGN KEY (id_provincia) REFERENCES provincias (id_provincia)
);

INSERT INTO localidades VALUES (1, 'ROSARIO', 1, '2000');
