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
