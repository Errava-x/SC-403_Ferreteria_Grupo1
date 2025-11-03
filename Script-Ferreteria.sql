CREATE DATABASE ferreteria;
USE ferreteria;

CREATE TABLE categoria(
	id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    estado boolean NOT NULL
);

CREATE TABLE producto (
  id_producto INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion VARCHAR(300),
  precio DECIMAL(5,2) NOT NULL,
  existencias INT NOT NULL,
  id_categoria INT NOT NULL,
  FOREIGN KEY (id_categoria) REFERENCES Categoria(id_categoria),
  imagen_ruta TEXT,
  estado boolean NOT NULL
);

CREATE USER 'usuario_prueba'@'localhost' IDENTIFIED BY 'Usuar1o_Clave.';
GRANT ALL PRIVILEGES ON ferreteria.* TO 'usuario_prueba'@'localhost';

INSERT INTO categoria (nombre, estado) VALUES
('Herramientas', 1);

COMMIT;

