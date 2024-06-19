-- Luego de haber creado la base de datos como indica el lab8
USE EmpresaMSQL;

CREATE TABLE Departamento (
    IDDpto INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Telefono VARCHAR(15),
    Fax VARCHAR(15)
);

CREATE TABLE Ingeniero (
    IDIng INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Especialidad VARCHAR(100),
    Cargo VARCHAR(100)
);

CREATE TABLE Proyecto (
    IDProy INT PRIMARY KEY,
    Nombre VARCHAR(100),
    Fec_Inicio DATE,
    Fec_Termino DATE,
    Ingeniero INT,
    IDDpto INT,
    FOREIGN KEY (Ingeniero) REFERENCES Ingeniero(IDIng),
    FOREIGN KEY (IDDpto) REFERENCES Departamento(IDDpto)
);

-- Índice primario ya está implícito con PRIMARY KEY
-- Crear índices secundarios si es necesario
CREATE INDEX idx_Ingeniero ON Proyecto(Ingeniero);
CREATE INDEX idx_IDDpto ON Proyecto(IDDpto);

-- Inserción preparada para Departamento
PREPARE insertDept FROM 'INSERT INTO Departamento (IDDpto, Nombre, Telefono, Fax) VALUES (?, ?, ?, ?)';
SET @IDDpto = 1, @Nombre = 'Departamento A', @Telefono = '123456789', @Fax = '987654321';
EXECUTE insertDept USING @IDDpto, @Nombre, @Telefono, @Fax;

-- Inserción preparada para Ingeniero
PREPARE insertIng FROM 'INSERT INTO Ingeniero (IDIng, Nombre, Especialidad, Cargo) VALUES (?, ?, ?, ?)';
SET @IDIng = 1, @Nombre = 'Ingeniero A', @Especialidad = 'Software', @Cargo = 'Jefe de Proyecto';
EXECUTE insertIng USING @IDIng, @Nombre, @Especialidad, @Cargo;

-- Inserción preparada para Proyecto
PREPARE insertProy FROM 'INSERT INTO Proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, Ingeniero, IDDpto) VALUES (?, ?, ?, ?, ?, ?)';
SET @IDProy = 1, @Nombre = 'Proyecto X', @Fec_Inicio = '2024-01-01', @Fec_Termino = '2024-12-31', 
@Ingeniero = 1, @IDDpto = 1;
EXECUTE insertProy USING @IDProy, @Nombre, @Fec_Inicio, @Fec_Termino, @Ingeniero, @IDDpto;


DELIMITER //

CREATE PROCEDURE AddProyecto(
    IN pIDProy INT,
    IN pNombre VARCHAR(100),
    IN pFec_Inicio DATE,
    IN pFec_Termino DATE,
    IN pIngeniero INT,
    IN pIDDpto INT
)
BEGIN
    INSERT INTO Proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, Ingeniero, IDDpto) 
    VALUES (pIDProy, pNombre, pFec_Inicio, pFec_Termino, pIngeniero, pIDDpto);
END //

DELIMITER ;

-- Añadir restricciones de unicidad y otras restricciones si es necesario
ALTER TABLE Proyecto
ADD CONSTRAINT chk_fechas CHECK (Fec_Inicio <= Fec_Termino);

-- Ejemplo de transacción
START TRANSACTION;

-- Insertar datos en las tablas
INSERT INTO Departamento (IDDpto, Nombre, Telefono, Fax) 
VALUES (2, 'Departamento B', '123456780', '987654320');
INSERT INTO Ingeniero (IDIng, Nombre, Especialidad, Cargo) 
VALUES (2, 'Ingeniero B', 'Civil', 'Jefe de Sección');
INSERT INTO Proyecto (IDProy, Nombre, Fec_Inicio, Fec_Termino, Ingeniero, IDDpto) 
VALUES (2, 'Proyecto Y', '2024-02-01', '2024-11-30', 2, 2);

-- Confirmar transacción
COMMIT;

