CREATE DATABASE IF NOT EXISTS magistra CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE magistra;

CREATE TABLE usuarios (
  id_usuario INT AUTO_INCREMENT PRIMARY KEY,
  nombres VARCHAR(80) NOT NULL,
  apellidos VARCHAR(100) NOT NULL,
  correo VARCHAR(150) NOT NULL UNIQUE,
  password_hash VARCHAR(255) NOT NULL,
  especialidad VARCHAR(120),
  region VARCHAR(100),
  fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  estado BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE categorias (
  id_categoria INT AUTO_INCREMENT PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL UNIQUE,
  descripcion VARCHAR(255),
  estado BOOLEAN NOT NULL DEFAULT TRUE
) ENGINE=InnoDB;

CREATE TABLE temas (
  id_tema INT AUTO_INCREMENT PRIMARY KEY,
  id_categoria INT NOT NULL,
  nombre VARCHAR(150) NOT NULL,
  descripcion VARCHAR(500),
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_tema_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria),
  CONSTRAINT uq_tema_categoria UNIQUE (id_categoria, nombre)
) ENGINE=InnoDB;

CREATE TABLE preguntas (
  id_pregunta INT AUTO_INCREMENT PRIMARY KEY,
  id_tema INT NOT NULL,
  enunciado TEXT NOT NULL,
  explicacion TEXT,
  dificultad ENUM('BASICA','INTERMEDIA','AVANZADA') NOT NULL DEFAULT 'INTERMEDIA',
  tipo VARCHAR(40) NOT NULL DEFAULT 'OPCION_MULTIPLE',
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_pregunta_tema FOREIGN KEY (id_tema) REFERENCES temas(id_tema),
  INDEX idx_pregunta_tema (id_tema, estado)
) ENGINE=InnoDB;

CREATE TABLE alternativas (
  id_alternativa INT AUTO_INCREMENT PRIMARY KEY,
  id_pregunta INT NOT NULL,
  texto VARCHAR(600) NOT NULL,
  correcta BOOLEAN NOT NULL DEFAULT FALSE,
  orden TINYINT NOT NULL,
  CONSTRAINT fk_alternativa_pregunta FOREIGN KEY (id_pregunta) REFERENCES preguntas(id_pregunta) ON DELETE CASCADE,
  CONSTRAINT uq_alternativa_orden UNIQUE (id_pregunta, orden)
) ENGINE=InnoDB;

CREATE TABLE intentos_practica (
  id_intento BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_tema INT NOT NULL,
  fecha_inicio DATETIME NOT NULL,
  fecha_fin DATETIME,
  limite_segundos INT NOT NULL DEFAULT 3600,
  tiempo_total_segundos INT NOT NULL DEFAULT 0,
  estado ENUM('EN_CURSO','FINALIZADO','CANCELADO') NOT NULL DEFAULT 'EN_CURSO',
  CONSTRAINT fk_intento_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
  CONSTRAINT fk_intento_tema FOREIGN KEY (id_tema) REFERENCES temas(id_tema),
  INDEX idx_intento_usuario_fecha (id_usuario, fecha_inicio),
  INDEX idx_intento_tema (id_tema)
) ENGINE=InnoDB;

CREATE TABLE respuestas_usuario (
  id_respuesta BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_intento BIGINT NOT NULL,
  id_pregunta INT NOT NULL,
  id_alternativa INT,
  correcta BOOLEAN NOT NULL,
  tiempo_segundos INT NOT NULL DEFAULT 0,
  fecha_respuesta DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_respuesta_intento FOREIGN KEY (id_intento) REFERENCES intentos_practica(id_intento) ON DELETE CASCADE,
  CONSTRAINT fk_respuesta_pregunta FOREIGN KEY (id_pregunta) REFERENCES preguntas(id_pregunta),
  CONSTRAINT fk_respuesta_alternativa FOREIGN KEY (id_alternativa) REFERENCES alternativas(id_alternativa),
  CONSTRAINT uq_respuesta_intento_pregunta UNIQUE (id_intento, id_pregunta)
) ENGINE=InnoDB;

CREATE TABLE resultados (
  id_resultado BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_intento BIGINT NOT NULL UNIQUE,
  correctas INT NOT NULL,
  incorrectas INT NOT NULL,
  total_preguntas INT NOT NULL,
  puntaje DECIMAL(6,2) NOT NULL,
  porcentaje DECIMAL(6,2) NOT NULL,
  tiempo_total_segundos INT NOT NULL,
  CONSTRAINT fk_resultado_intento FOREIGN KEY (id_intento) REFERENCES intentos_practica(id_intento) ON DELETE CASCADE
) ENGINE=InnoDB;

CREATE TABLE progreso (
  id_progreso BIGINT AUTO_INCREMENT PRIMARY KEY,
  id_usuario INT NOT NULL,
  id_tema INT NOT NULL,
  total_intentos INT NOT NULL DEFAULT 0,
  mejor_puntaje DECIMAL(6,2) NOT NULL DEFAULT 0,
  promedio_puntaje DECIMAL(6,2) NOT NULL DEFAULT 0,
  mejor_tiempo_segundos INT,
  fecha_actualizacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT fk_progreso_usuario FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
  CONSTRAINT fk_progreso_tema FOREIGN KEY (id_tema) REFERENCES temas(id_tema) ON DELETE CASCADE,
  CONSTRAINT uq_progreso_usuario_tema UNIQUE (id_usuario, id_tema)
) ENGINE=InnoDB;

CREATE TABLE recursos (
  id_recurso INT AUTO_INCREMENT PRIMARY KEY,
  id_categoria INT NULL,
  titulo VARCHAR(180) NOT NULL,
  descripcion VARCHAR(500),
  tipo ENUM('ENLACE','PDF','GUIA','VIDEO','OTRO') NOT NULL DEFAULT 'ENLACE',
  url VARCHAR(500) NOT NULL,
  estado BOOLEAN NOT NULL DEFAULT TRUE,
  CONSTRAINT fk_recurso_categoria FOREIGN KEY (id_categoria) REFERENCES categorias(id_categoria) ON DELETE SET NULL
) ENGINE=InnoDB;

INSERT INTO categorias(nombre, descripcion) VALUES
('Ascenso','Preparación para procesos de ascenso docente.'),
('Nombramiento','Preparación para procesos de nombramiento docente.');

INSERT INTO temas(id_categoria,nombre,descripcion) VALUES
(1,'Conocimientos Curriculares y Pedagógicos','Banco demostrativo de preguntas pedagógicas.'),
(1,'Gestión del aprendizaje','Preguntas de planificación, evaluación y mejora del aprendizaje.'),
(2,'Competencias pedagógicas','Preguntas de práctica para nombramiento docente.'),
(2,'Normativa y gestión educativa','Preguntas de práctica sobre gestión y normativa educativa.');

INSERT INTO recursos(id_categoria,titulo,descripcion,tipo,url) VALUES
(NULL,'Portal de recursos MAGISTRA','Acceso a materiales complementarios del proyecto.','ENLACE','https://docente-mag.lovable.app/#recursos');
