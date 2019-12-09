DROP TABLE IF EXISTS SEQUENCE_TABLE;
CREATE TABLE SEQUENCE_TABLE (
	seq_name VARCHAR(200) NOT NULL,
	seq_value BIGINT NOT NULL,
	PRIMARY KEY(seq_name)
);

INSERT INTO SEQUENCE_TABLE VALUES ('tipo_documento_seq', 1);


DROP TABLE IF EXISTS tipos_documentos;
CREATE TABLE tipos_documentos (
	id_tipodocumento INT NOT NULL auto_increment,
	nombre VARCHAR(200) NOT NULL,
	abreviatura VARCHAR(5) NOT NULL,
	validar_como_cuit BOOLEAN NOT NULL,
	PRIMARY KEY(id_tipodocumento)
);

INSERT INTO tipos_documentos VALUES (1, 'DOCUMENTO NACIONAL DE IDENTIDAD', 'DNI', 0);

