DROP DATABASE IF EXISTS db_control_academico_in5bm;
CREATE DATABASE db_control_academico_in5bm;
USE db_control_academico_in5bm;

 /*
 * Estudiantes: 
 * Michael Steven Salvatierra Ramirez 2021150
 * Bill Abel Bickson Rangel 2018187
 * Grado: 5to perito en Informatica 
 * Seccion y grupo: IN5BM Grupo 2 (Lunes)
 * Catedratico: Lic. Jorge Luis Perez Canto
 */
 
DROP TABLE IF EXISTS alumnos;
CREATE TABLE IF NOT EXISTS alumnos(
	carne VARCHAR(7) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15),
    nombre3 VARCHAR(15),
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15),
    CONSTRAINT pk_alumnos PRIMARY KEY (carne)
);

DROP TABLE IF EXISTS instructores;
CREATE TABLE IF NOT EXISTS instructores(
	id INT AUTO_INCREMENT NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15),
    nombre3 VARCHAR(15),
    apellido1 VARCHAR(15) NOT NULL,
    apellido2 VARCHAR(15),
    direccion VARCHAR(45),
    email VARCHAR(45) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    fecha_nacimiento DATE,
    CONSTRAINT  pk_instructores PRIMARY KEY (id)
);

DROP TABLE IF EXISTS salones;
CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45),
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR(15),
    nivel INT(15),
    CONSTRAINT pk_salones PRIMARY KEY(codigo_salon)
);

DROP TABLE IF EXISTS carreras_tecnicas;
CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6) NOT NULL,
    carrera VARCHAR(45) NOT NULL,
    grado VARCHAR(10) NOT NULL,
    seccion CHAR(1) NOT NULL,
    jornada VARCHAR(10) NOT NULL,
    CONSTRAINT pk_carreras_tecnicas PRIMARY KEY(codigo_tecnico)
);

DROP TABLE IF EXISTS horarios;
CREATE TABLE IF NOT EXISTS horarios(
	id INT AUTO_INCREMENT NOT NULL,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT(1),
    martes TINYINT(1),
    miercoles TINYINT(1),
    jueves TINYINT(1),
    viernes TINYINT(1),
    CONSTRAINT pk_horarios PRIMARY KEY (id)
);

DROP TABLE IF EXISTS cursos;
CREATE TABLE IF NOT EXISTS cursos(
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255),
    ciclo YEAR,
    cupo_maximo INT,
    cupo_minimo INT,
    carrera_tecnica_id VARCHAR(128) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    CONSTRAINT pk_cursos
		PRIMARY KEY (id),
    CONSTRAINT fk_carrera_tecnica_id
		FOREIGN KEY (carrera_tecnica_id)
        REFERENCES carreras_tecnicas(codigo_tecnico)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_horario_id
		FOREIGN KEY (horario_id)
        REFERENCES horarios(id)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_instructor_id
		FOREIGN KEY (instructor_id)
        REFERENCES instructores(id)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_salon_id
		FOREIGN KEY (salon_id)
        REFERENCES salones(codigo_salon)
	ON DELETE CASCADE ON UPDATE CASCADE
);

DROP TABLE IF EXISTS asignaciones_alumnos;
CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id INT AUTO_INCREMENT NOT NULL,
    alumno_id VARCHAR(7) NOT NULL,
    curso_id INT NOT NULL,
    fecha_asignacion DATETIME,
    CONSTRAINT pk_asignaciones_alumnos 
		PRIMARY KEY (id),
	CONSTRAINT fk_alumno_id
		FOREIGN KEY (alumno_id)
        REFERENCES alumnos(carne)
	ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_cursos_id
		FOREIGN KEY (curso_id)
        REFERENCES cursos(id)
	ON DELETE CASCADE ON UPDATE CASCADE
);

#----------------------------INSERCIONES EN TABLAS--------------------------#

-- 1 INSERT ALUMNO
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create( 
	IN _carne  VARCHAR(7),
	IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
    )
	BEGIN
		INSERT INTO alumnos (carne, nombre1, nombre2, nombre3, apellido1, apellido2) VALUES (_carne, _nombre1, _nombre2, _nombre3, _apellido1, _apellido2);
	END $$
DELIMITER ;

-- 2 INSERT INSTRUCTOR
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create( 
	IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE
    )
	BEGIN
		INSERT INTO instructores(nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono,fecha_nacimiento) 
        VALUES ( _nombre1, _nombre2, _nombre3, _apellido1, _apellido2, _direccion,_email, _telefono,_fecha_nacimiento);
	END $$
DELIMITER ;

-- 3 INSERT SALONES
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create( 
	IN _codigo_salon  VARCHAR(5),
	IN _descripcion VARCHAR(45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
    )
	BEGIN 
    INSERT salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel) 
        VALUES (_codigo_salon, _descripcion, _capacidad_maxima, _edificio, _nivel);
	END $$
DELIMITER ;

-- 4 INSERT CARRERAS TECNICAS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create( 
	IN _codigo_tecnico VARCHAR(6),
	IN _carrera VARCHAR(45),
    IN _grado VARCHAR(10),
    IN _seccion CHAR(1),
    IN _jornada VARCHAR(10)
    )
	BEGIN
		INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada) 
        VALUES (_codigo_tecnico, _carrera, _grado, _seccion, _jornada);
	END $$
DELIMITER ;

-- 5 INSERT HORARIOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create( 
	IN _horario_inicio  TIME,
	IN _horario_final TIME,
    IN _lunes TINYINT(1),
    IN _martes TINYINT(1),
    IN _miercoles TINYINT(1),
    IN _jueves TINYINT(1),
    IN _viernes TINYINT(1)
    )
	BEGIN
		INSERT INTO horarios(horario_inicio, horario_final , lunes, martes, miercoles, jueves, viernes) 
        VALUES (_horario_inicio, _horario_final ,_lunes, _martes, _miercoles, _jueves, _viernes);
	END $$
DELIMITER ;

-- 6 INSERT CURSOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create( 
	IN _nombre_curso  VARCHAR(255),
	IN _ciclo YEAR,
    IN _cupo_maximo INT,
    IN _cupo_minimo INT,
    IN _carrera_tecnica_id VARCHAR(128),
    IN _horario_id INT,
    IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
    )
	BEGIN
		INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id ,horario_id, instructor_id, salon_id) 
        VALUES (_nombre_curso, _ciclo, _cupo_maximo, _cupo_minimo, _carrera_tecnica_id, _horario_id, _instructor_id, _salon_id);
	END $$
DELIMITER ;

-- 7 INSERT ASIGNACIONES_ALUMNOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_create $$
CREATE PROCEDURE sp_asignaciones_create( 
	IN _alumno_id VARCHAR(16),
    IN _curso_id INT,
    IN _fecha_asignacion DATETIME
    )
	BEGIN
		INSERT INTO asignaciones_alumnos( alumno_id, curso_id, fecha_asignacion) 
        VALUES ( _alumno_id, _curso_id, _fecha_asignacion);
	END $$
DELIMITER ;

#----------------------------LECTURA EN TABLAS--------------------------#
-- 8 LECTURA DE TABLA ALUMNO
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
	BEGIN
		SELECT al.carne, al.nombre1, al.nombre2, al.nombre3, al.apellido1, al.apellido2 FROM alumnos AS al;
	END $$
DELIMITER ;

-- 9 LECTURA DE TABLA INSTRUCTOR
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
	BEGIN
		SELECT ins.id , ins.nombre1, ins.nombre2, ins.nombre3, ins.apellido1, ins.apellido2, ins.direccion ,ins.email, ins.telefono ,ins.fecha_nacimiento FROM instructores AS ins;
	END $$
DELIMITER ;

-- 10 LECTURA TABLA SALONES
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
	BEGIN 
		SELECT sa.codigo_salon, sa.descripcion, sa.capacidad_maxima, sa.edificio, sa.nivel FROM SALONES AS sa;
	END $$
DELIMITER ;

-- 11 LECTURA DE TABLA CARRERAS TECNICAS 
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
	BEGIN
		SELECT ca.codigo_tecnico, ca.carrera, ca.grado, ca.seccion, ca.jornada FROM carreras_tecnicas AS ca;
	END $$
DELIMITER ;

-- 12 LECTURA DE TABLA HORARIOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
	BEGIN
		SELECT ho.id, ho.horario_inicio, ho.horario_final , ho.lunes, ho.martes, ho.miercoles, ho.jueves, ho.viernes FROM horarios AS ho;
	END $$
DELIMITER ;

-- 13 LECTURA DE TABLA CURSOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
	BEGIN
		SELECT cu.id, cu.nombre_curso, cu.ciclo, cu.cupo_maximo,cu.cupo_minimo, cu.carrera_tecnica_id, cu.horario_id, cu.instructor_id, cu.salon_id FROM cursos AS cu;
	END $$
DELIMITER ;

-- 14 LECTURA DE ASIGNACIONES_ALUMNOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_read $$
CREATE PROCEDURE sp_asignaciones_read()
	BEGIN
		SELECT au.id, au.alumno_id, au.curso_id, au.fecha_asignacion FROM asignaciones_alumnos AS au;
	END $$
DELIMITER ;

#----------------------------ACTUALIZACION EN TABLAS--------------------------#
-- 15 ACTUALIZACION DE TABLA ALUMNO
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne  VARCHAR(7),
	IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
    )
	BEGIN
		UPDATE alumnos AS al SET al.carne = _carne , al.nombre1 = _nombre1, al.nombre2 = _nombre2, al.nombre3 = _nombre3, al.apellido1 = _apellido1, al.apellido2 = _apellido2 
        WHERE al.carne = _carne;
	END $$
DELIMITER ;

-- 16 ACTUALIZACION INSTRUCTOR
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$
CREATE PROCEDURE sp_instructores_update( 
	IN _id  VARCHAR(7),
	IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15),
    IN _direccion VARCHAR(45),
    IN _email VARCHAR(45),
    IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE
    )
	BEGIN
		UPDATE instructores AS ins SET ins.id = _id , ins.nombre1 = _nombre1, ins.nombre2 = _nombre2, ins.nombre3 = _nombre3, 
        ins.apellido1 = _apellido1 , ins.apellido2 = _apellido2 , ins.direccion = _direccion, ins.email = _email, ins.telefono = _telefono ,ins.fecha_nacimiento = _fecha_nacimiento
        WHERE ins.id = _id;
	END $$
DELIMITER ;

-- 17 ACTUALIZACION SALONES
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$
CREATE PROCEDURE sp_salones_update( 
	IN _codigo_salon  VARCHAR(5),
	IN _descripcion VARCHAR(45),
    IN _capacidad_maxima INT,
    IN _edificio VARCHAR(15),
    IN _nivel INT
    )
	BEGIN 
    UPDATE salones AS sa SET sa.codigo_salon = _codigo_salon, sa.descripcion = _descripcion, sa.capacidad_maxima = _capacidad_maxima, sa.edificio = _edificio, sa.nivel = _nivel
    WHERE sa.codigo_salon = _codigo_salon;
	END $$
DELIMITER ;

-- 18 ACTUALIZACION DE CARRERAS TECNICAS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$
CREATE PROCEDURE sp_carreras_tecnicas_update( 
	IN _codigo_tecnico VARCHAR(5),
	IN _carrera VARCHAR(45),
    IN _grado VARCHAR(10),
    IN _seccion CHAR(1),
    IN _jornada VARCHAR(10)
    )
	BEGIN
		UPDATE carreras_tecnicas AS ca SET ca.codigo_tecnico = _codigo_tecnico, ca.carrera = _carrera, ca.grado = _grado, ca.seccion = _seccion, ca.jornada = _jornada
        WHERE ca.codigo_tecnico = _codigo_tecnico;
	END $$
DELIMITER ;

-- 19 ACTUALIZACION HORARIOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update( 
	IN _id INT,
	IN _horario_inicio  TIME,
	IN _horario_final TIME,
    IN _lunes TINYINT(1),
    IN _martes TINYINT(1),
    IN _miercoles TINYINT(1),
    IN _jueves TINYINT(1),
    IN _viernes TINYINT(1)
    )
	BEGIN
		UPDATE horarios AS ho SET ho.horario_inicio = _horario_inicio , ho.horario_final = _horario_final, ho.lunes = _lunes , ho.martes = _martes , ho.miercoles = _miercoles, ho.jueves = _jueves, ho.viernes = _viernes
        WHERE ho.id = _id;
	END $$
DELIMITER ;

-- 20 ACTUALIZACION CURSOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$
CREATE PROCEDURE sp_cursos_update( 
	IN _id INT,
	IN _nombre_curso  VARCHAR(255),
	IN _ciclo VARCHAR(45),
    IN _cupo_maximo INT,
    IN _cupo_minimo INT,
    IN _carrera_tecnica_id VARCHAR(128),
    IN _horario_id INT,
    IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
    )
	BEGIN
		UPDATE cursos AS cu SET cu.nombre_curso = _nombre_curso, cu.ciclo = _ciclo, cu.cupo_maximo = _cupo_maximo, cu.cupo_minimo = _cupo_minimo, cu.carrera_tecnica_id = _carrera_tecnica_id, cu.horario_id = _horario_id, cu.instructor_id = _instructor_id, cu.salon_id = _salon_id
        WHERE cu.id = _id;
	END $$
DELIMITER ;

-- 21 ACTUALIZACION ASIGNACIONES_ALUMNOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_update $$
CREATE PROCEDURE sp_asignaciones_update( 
	 IN _id INT,
	IN _alumno_id VARCHAR(45),
    IN _curso_id INT,
    IN _fecha_asignacion DATETIME
    )
	BEGIN
		UPDATE asignaciones_alumnos AS au SET au.alumno_id = _alumno_id, au.curso_id = _curso_id , au.fecha_asignacion = _fecha_asignacion
        WHERE _id = id;
	END $$
DELIMITER ;

#----------------------------DELETE EN TABLAS--------------------------#
-- 22 DELETE EN TABLA ALUMNO
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(IN _carne VARCHAR(7))
	BEGIN
		DELETE FROM  alumnos WHERE carne = _carne;
	END $$
DELIMITER ;

-- 23 DELETE INSTRUCTOR
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete (IN _id  VARCHAR(7))
	BEGIN
		DELETE FROM instructores WHERE id=_id;
	END $$
DELIMITER ;

-- 24 DELETE SALONES
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(IN _codigo_salon  VARCHAR(5))
	BEGIN 
    DELETE FROM salones WHERE codigo_salon = _codigo_salon;
    END $$
DELIMITER ;

-- 25 DELETE DE CARRERAS TECNICAS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete$$
CREATE PROCEDURE sp_carreras_tecnicas_delete( 
	IN _codigo_tecnico VARCHAR(5)
    )
	BEGIN
		DELETE FROM carreras_tecnicas WHERE codigo_tecnico = _codigo_tecnico;
	END $$
DELIMITER ;

-- 26 DELETE HORARIOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete(IN _id  VARCHAR(5))
	BEGIN
		DELETE FROM horarios WHERE id = _id;
	END $$
DELIMITER ;

-- 27 DELETE CURSOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(IN _id INT)
	BEGIN
		DELETE FROM cursos WHERE id = _id;
	END $$
DELIMITER ;

-- 28 DELETE ASIGNACIONES_ALUMNOS
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_delete $$
CREATE PROCEDURE sp_asignaciones_delete(IN _id VARCHAR(5))
	BEGIN
		DELETE FROM asignaciones_alumnos WHERE id = _id;
	END $$
DELIMITER ;


#----------------------------LECTURA EN TABLAS BY ID--------------------------#
-- 29 LECTURA DE TABLA ALUMNO BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by$$
CREATE PROCEDURE sp_alumnos_read_by(IN _carne VARCHAR(7) )
	BEGIN
		SELECT al.carne, al.nombre1, al.nombre2, al.nombre3, al.apellido1, al.apellido2 FROM alumnos AS al WHERE al.carne = _carne ;
	END $$
DELIMITER ;

-- 30 LECTURA DE TABLA INSTRUCTOR BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by $$
CREATE PROCEDURE sp_instructores_read_by(IN _id  int )
	BEGIN
		SELECT ins.id , ins.nombre1, ins.nombre2, ins.nombre3, ins.apellido1, ins.apellido2, ins.direccion, ins.email, ins.telefono, ins.fecha_nacimiento FROM instructores AS ins WHERE ins.id=_id;
	END $$
DELIMITER ;

-- 31 LECTURA TABLA SALONES BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by $$
CREATE PROCEDURE sp_salones_read_by(IN _codigo_salon  VARCHAR(5) )
	BEGIN 
		SELECT sa.codigo_salon, sa.descripcion, sa.capacidad_maxima, sa.edificio, sa.nivel FROM SALONES AS sa WHERE sa.codigo_salon = _codigo_salon;
	END $$
DELIMITER ;

-- 32 LECTURA DE TABLA CARRERAS TECNICAS BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by(IN _codigo_tecnico VARCHAR(5))
	BEGIN
		SELECT ca.codigo_tecnico, ca.carrera, ca.grado, ca.seccion, ca.jornada FROM carreras_tecnicas AS ca WHERE ca.codigo_tecnico = _codigo_tecnico;
	END $$
DELIMITER ;

-- 33 LECTURA DE TABLA HORARIOS BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by $$
CREATE PROCEDURE sp_horarios_read_by(IN _id INT)
	BEGIN
		SELECT ho.id,ho.horario_inicio, ho.horario_final , ho.lunes, ho.martes, ho.miercoles, ho.jueves, ho.viernes FROM horarios AS ho WHERE ho.id = _id;
	END $$
DELIMITER ;

-- 34 LECTURA DE TABLA CURSOS BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by $$
CREATE PROCEDURE sp_cursos_read_by(IN _id INT)
	BEGIN
		SELECT cu.id ,cu.nombre_curso, cu.ciclo, cu.cupo_maximo,cu.cupo_minimo, cu.carrera_tecnica_id, cu.horario_id, cu.instructor_id, cu.salon_id FROM cursos AS cu WHERE cu.id = _id;
	END $$
DELIMITER ;

-- 35 LECTURA DE ASIGNACIONES_ALUMNOS BY
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_read_by $$
CREATE PROCEDURE sp_asignaciones_read_by(IN _id INT)
	BEGIN
		SELECT au.id, au.alumno_id, au.curso_id, au.fecha_asignacion FROM asignaciones_alumnos AS au WHERE au.id = _id;
	END $$
DELIMITER ;



-- LLAMADA A LOS PROCEDIMIENTOS ALMACENAOS

CALL sp_alumnos_create("2021150","Jose","Carlos","Gabriel","Hernandez","Rodriguez");
CALL sp_alumnos_create("2021151","Gerardo","Andres","","Bazan","Alvarado");
CALL sp_alumnos_create("2021152","Pablo","Emanuel","","Perez","Gallardo");
CALL sp_alumnos_create("2021153","Ricardo","Fabian","","Valenzuela","Torres");
CALL sp_alumnos_create("2021154","Daniel","","","Pelaez","Corrijo");
CALL sp_alumnos_create("2021155","Juan","","","Arreaza","");
CALL sp_alumnos_create("2021156","Pedro","Fernando","","Barahona","");
CALL sp_alumnos_create("2021157","Lucas","Alfredo","","Obregon","Rodriguez");
CALL sp_alumnos_create("2021158","Santiago","","","El mayor","Caballeros");
CALL sp_alumnos_create("2021159","David","","","Del Cid","");
SELECT * FROM alumnos;

CALL sp_instructores_create("Jose","Carlos","Gabriel","Hernandez","Rodriguez","51-Av. B 3-65 Z-10","josehernandez@gmail.com","43217834","1994-05-14");
CALL sp_instructores_create("Samuel","Carlos","","Zambrano","Enriquez","2-45 Calle 12 Z.12","samuelenriquez@gmail.com","98786513","1970-05-11");
CALL sp_instructores_create("Roberto","","","Wallace","","","rwallace@gmail.com","58973456",null);
CALL sp_instructores_create("William","Axel","","Ratzan","Lopez","","williamratzan@gmail.com","89786572",null);
CALL sp_instructores_create("Marcos","","","Guatzin","","","marcosguatzin@gmail.com","56874530","1985-09-13");
CALL sp_instructores_create("Miguel","Carlos","","Masilla","Barranquilla","","miguelmas@gmail.com","56036502","1977-03-19");
CALL sp_instructores_create("Diego","","","Velasquez","","","diegovelas@gmail.com","21854308",null);
CALL sp_instructores_create("Estuardo","Pablo","","Flores","Conde","","estuardopablo@gmail.com","47129043","1981-05-25");
CALL sp_instructores_create("Esteban","","","Santa Maria","","","estebansant@gmail.com","49836574","1987-02-27");
CALL sp_instructores_create("Hugo","Oliver","","Rivera","De la plata","","riverplatahugo@gmail.com","56852312",null);
SELECT * FROM instructores;

CALL sp_salones_create("C29","Salon de Informatica",30,"Edificio C",2);
CALL sp_salones_create("E4","Salon de Mecanica",30,"Edificio E",1);
CALL sp_salones_create("C27","Salon de Informatica",30,"Edificio C",2);
CALL sp_salones_create("C24","Salon de Electronica",25,"Edificio C",2);
CALL sp_salones_create("A3","Salon de Dibujo",20,"Edificio A",2);
CALL sp_salones_create("B2","Salon de Quimica",15,"Edificio B",1);
CALL sp_salones_create("A9","Salon de Matematica",30,"Edificio A",1);
CALL sp_salones_create("B1","Salon de Sociales",25,"Edificio B",3);
CALL sp_salones_create("A5","Salon de Estadistica",20,"Edificio A",2);
CALL sp_salones_create("B8","Salon de Literatura",25,"Edificio B",1);
CALL sp_salones_create("A10","Salon de Ingles",25,"Edificio A",3);
SELECT * FROM salones;

CALL sp_carreras_tecnicas_create("IN5BM","Informatica","5to perito","E","Matutina");
CALL sp_carreras_tecnicas_create("ME4AM","Mecanica","4to perito","C","Matutina");
CALL sp_carreras_tecnicas_create("IN5AV","Informatica","5to perito","A","Vespertina");
CALL sp_carreras_tecnicas_create("DI5CM","Dibujo","5to perito","C","Matutina");
CALL sp_carreras_tecnicas_create("EL6V","Electronica","6to perito","B","Vespertina");
CALL sp_carreras_tecnicas_create("EL5AM","Electronica","5to perito","A","Matutina");
CALL sp_carreras_tecnicas_create("ME6BM","Mecanica","6to perito","B","Matutina");
CALL sp_carreras_tecnicas_create("DI4CV","Dibujo","4to perito","C","Vespertina");
CALL sp_carreras_tecnicas_create("IN6EM","Informatica","6to perito","E","Matutina");
CALL sp_carreras_tecnicas_create("EL5BM","Electronica","6to perito","B","Vespertina");
SELECT * FROM carreras_tecnicas;

CALL sp_horarios_create('12:00','4:00',1,0,0,0,1);
CALL sp_horarios_create('7:00','8:00',0,1,0,0,1);
CALL sp_horarios_create('9:00','11:00',0,0,0,1,1);
CALL sp_horarios_create('10:00','12:00',1,1,0,0,1);
CALL sp_horarios_create('7:00','9:00',1,0,1,0,1);
CALL sp_horarios_create('3:00','5:00',1,0,0,0,0);
CALL sp_horarios_create('1:00','3:00',1,0,1,0,1);
CALL sp_horarios_create('4:00','5:00',0,0,0,1,1);
CALL sp_horarios_create('8:00','11:00',1,0,0,1,1);
CALL sp_horarios_create('1:00','2:00',0,1,0,0,1);
SELECT * FROM horarios;

CALL sp_cursos_create("Taller II",'2022',15,30,"IN5BM",1,1,"C29");
CALL sp_cursos_create("Mecanica I",'2022',15,30,"ME4AM",2,2,"E4");
CALL sp_cursos_create("Taller I",'2022',15,30,"IN5AV",3,3,"C27");
CALL sp_cursos_create("Dibujo II",'2022',15,30,"DI5CM",4,4,"A3");
CALL sp_cursos_create("Electronica III",'2022',15,30,"EL6V",5,5,"C24");
CALL sp_cursos_create("Quimica I",'2022',15,30,"EL5AM",6,6,"B2");
CALL sp_cursos_create("Matematica III",'2022',15,30,"ME6BM",7,7,"A9");
CALL sp_cursos_create("Sociales I",'2022',15,30,"DI4CV",8,8,"B1");
CALL sp_cursos_create("Estadistica I",'2022',15,30,"IN6EM",9,9,"A5");
CALL sp_cursos_create("Literatura II",'2022',15,30,"EL5BM",10,10,"B8");
SELECT * FROM cursos;

CALL sp_asignaciones_create('2021150',1, DATE(current_date()));
CALL sp_asignaciones_create('2021151',2, DATE(current_date()));
CALL sp_asignaciones_create('2021152',3, DATE(current_date()));
CALL sp_asignaciones_create('2021153',4, DATE(current_date()));
CALL sp_asignaciones_create('2021154',5, DATE(current_date()));
CALL sp_asignaciones_create('2021155',6, DATE(current_date()));
CALL sp_asignaciones_create('2021156',7, DATE(current_date()));
CALL sp_asignaciones_create('2021157',8, DATE(current_date()));
CALL sp_asignaciones_create('2021158',9, DATE(current_date()));
CALL sp_asignaciones_create('2021159',10, DATE(current_date()));
SELECT * FROM asignaciones_alumnos;