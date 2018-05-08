--
-- File generated with SQLiteStudio v3.1.1 on niedz. kwi 15 15:07:45 2018
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Examined
CREATE TABLE Examined (Id INTEGER PRIMARY KEY UNIQUE, Genotype STRING NOT NULL, Rank STRING NOT NULL);

-- Table: Flagella
CREATE TABLE Flagella (Id INTEGER PRIMARY KEY UNIQUE, Alpha INT NOT NULL, Beta INT NOT NULL, Number INT NOT NULL);

-- Table: Toughness
CREATE TABLE Toughness (Id INTEGER PRIMARY KEY UNIQUE, Beta INT NOT NULL, Gamma INT NOT NULL, Rank STRING NOT NULL);

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
