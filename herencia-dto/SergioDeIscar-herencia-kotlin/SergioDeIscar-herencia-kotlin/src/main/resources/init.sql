CREATE TABLE IF NOT EXISTS tPersona(
    nIdPersona INTEGER PRIMARY KEY AUTOINCREMENT,
    cNombre TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS tProfesor(
    nIdProfesor INTEGER PRIMARY KEY,
    cModulo TEXT NULL,
    FOREIGN KEY (nIdProfesor) REFERENCES tPersona(nIdPersona)
);

CREATE TABLE IF NOT EXISTS tAlumno(
    nIdAlumno INTEGER PRIMARY KEY,
    nEdad INTEGER NULL,
    FOREIGN KEY (nIdAlumno) REFERENCES tPersona(nIdPersona)
);