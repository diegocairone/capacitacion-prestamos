DROP TABLE IF EXISTS tipos_documentos;
CREATE TABLE tipos_documentos (
	id_tipodocumento INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	abreviatura VARCHAR(5) NOT NULL,
	validar_como_cuit BOOLEAN NOT NULL,
	PRIMARY KEY(id_tipodocumento)
);

INSERT INTO tipos_documentos VALUES (1, 'DOCUMENTO NACIONAL DE IDENTIDAD', 'DNI', 0);


DROP TABLE IF EXISTS provincias;
CREATE TABLE provincias (
	id_provincia INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	region CHAR(3) NOT NULL,
	PRIMARY KEY(id_provincia)
);

INSERT INTO provincias VALUES (1, 'SANTA FE', 'PAM');

DROP TABLE IF EXISTS localidades;
CREATE TABLE localidades (
	id_localidad INT NOT NULL,
	nombre VARCHAR(300) NOT NULL,
	id_provincia INT NOT NULL,
	codigo_postal VARCHAR(10) NOT NULL,
	PRIMARY KEY(id_localidad),
	FOREIGN KEY (id_provincia) REFERENCES provincias (id_provincia)
);

INSERT INTO localidades VALUES (1, 'ROSARIO', 1, '2000');

-- PERSONAS: UN USUARIO Y UN BENEFICIARIO

DROP TABLE IF EXISTS personas;
CREATE TABLE personas (
	id_tipodocumento INT NOT NULL,
	numero_documento BIGINT NOT NULL,
	nombre_apellido VARCHAR(400) NOT NULL,
	fecha_nacimiento DATE NOT NULL,
	genero CHAR(1) NOT NULL,
	es_argentino BOOLEAN NOT NULL,
	correo_electronico VARCHAR(300),
	foto_cara BINARY NULL,
	id_localidad INT NOT NULL,
	codigo_postal VARCHAR(10) NOT NULL,
	PRIMARY KEY(id_tipodocumento, numero_documento),
	FOREIGN KEY (id_tipodocumento) REFERENCES tipos_documentos (id_tipodocumento),
	FOREIGN KEY (id_localidad) REFERENCES localidades (id_localidad)
);

INSERT INTO personas VALUES
(1, 1, 'USUARIO',      '1970-01-01', 'M', 1, NULL, NULL, 1, '2000'),
(1, 2, 'BENEFICIARIO', '1970-01-01', 'M', 1, NULL, NULL, 1, '2000');


-- USUARIO

DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
	id_tipodocumento INT NOT NULL,
	numero_documento BIGINT NOT NULL,
	nombre_usuario VARCHAR(50) NOT NULL,
	hashed_pwd VARCHAR(200) NOT NULL,
	PRIMARY KEY(id_tipodocumento, numero_documento),
	FOREIGN KEY (id_tipodocumento, numero_documento) REFERENCES personas (id_tipodocumento, numero_documento)
);

INSERT INTO usuarios VALUES
(1, 1, 'nom.usr', '19700101');


-- LINEAS DE PRESTAMOS

DROP TABLE IF EXISTS lineas;
CREATE TABLE lineas (
	linea_id INT NOT NULL,
	nombre VARCHAR(200) NOT NULL,
	sistema_amortizacion CHAR(1) NOT NULL,
	tasa_tipo CHAR(1) NOT NULL,
	tasa_modulo INT NOT NULL,
	tasa_min DECIMAL(18,2) NOT NULL,
	tasa_max DECIMAL(18,2) NOT NULL,	
	amortizacion_periodo INT NOT NULL,
	amortizacion_unidad CHAR(1) NOT NULL,
	cuotas_min INT NOT NULL,
	cuotas_max INT NOT NULL,
	capital_min DECIMAL(18,2) NOT NULL,
	capital_max DECIMAL(18,2) NOT NULL,
	fecha_alta DATE NOT NULL,
	usuario_tipo_documento_id INT NOT NULL,
	usuario_numero_documento BIGINT NOT NULL,
	PRIMARY KEY(linea_id),
	FOREIGN KEY (usuario_tipo_documento_id, usuario_numero_documento) REFERENCES usuarios (id_tipodocumento, numero_documento)
);


INSERT INTO lineas VALUES
(1, 'TEST', 'F', 'E', 365, 10, 50, 30, 'D', 10, 20, 10000, 200000, '2020-03-06', 1, 1);

-- PRESTAMOS

DROP TABLE IF EXISTS prestamos;
CREATE TABLE prestamos (
	prestamo_id BIGINT NOT NULL,
	id_tipodocumento INT NOT NULL,
	numero_documento BIGINT NOT NULL,
	linea_id INT NOT NULL,
	sistema_amortizacion CHAR(1) NOT NULL,
	fecha_alta DATE NOT NULL,
	fecha_primer_vto DATE NOT NULL,
	tasa_efectiva DECIMAL(19,2) NOT NULL,
	tasa_modulo INT NOT NULL,
	amortizacion_periodo INT NOT NULL,
	amortizacion_unidad CHAR(1) NOT NULL,
	capital_prestado DECIMAL(19,2) NOT NULL,
	total_intereses DECIMAL(19,2) NOT NULL,
	total_cuotas INT NOT NULL,
	usuario_tipo_documento_id INT NOT NULL,
	usuario_numero_documento BIGINT NOT NULL,
	PRIMARY KEY(prestamo_id),
	FOREIGN KEY (linea_id) REFERENCES lineas (linea_id),
	FOREIGN KEY (usuario_tipo_documento_id, usuario_numero_documento) REFERENCES usuarios (id_tipodocumento, numero_documento)
);

DROP TABLE IF EXISTS prestamos_cuotas;
CREATE TABLE prestamos_cuotas (
	prestamo_id BIGINT NOT NULL,
	nro_cuota INT NOT NULL,
	fecha_vencimiento DATE NOT NULL,
	importe_capital DECIMAL(19,2) NOT NULL,
	importe_intereses DECIMAL(19,2) NOT NULL,
	importe_total DECIMAL(19,2) NOT NULL,
	saldo_capital DECIMAL(19,2) NOT NULL,
	PRIMARY KEY(prestamo_id, nro_cuota),
	FOREIGN KEY (prestamo_id) REFERENCES prestamos (prestamo_id)
);
