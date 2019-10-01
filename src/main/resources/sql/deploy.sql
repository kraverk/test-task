
drop table if exists tPrescription;

drop trigger if exists tr_II_Doctor;
drop trigger if exists tr_IU_Doctor;
drop trigger if exists tr_ID_Doctor;
drop procedure if exists GetSpecID; 

drop view  if exists Doctor;
drop table if exists tDoctor;
drop table if exists tSpecialization;

drop trigger if exists tr_II_Customer;
drop trigger if exists tr_IU_Customer;
drop trigger if exists tr_ID_Customer;

drop view  if exists Customer;
drop table if exists tCustomer;
drop table if exists tHuman;


CREATE TABLE tHuman(
	ID         BIGINT IDENTITY Primary key,
	FirstName  varchar(255),
	LastName   varchar(255),
	MiddleName varchar(255)
);

CREATE TABLE tCustomer(
	ID    BIGINT Primary key,
	Phone varchar(255),
	CONSTRAINT FkHumanCust FOREIGN KEY(ID) REFERENCES tHuman(ID) ON DELETE CASCADE
);

CREATE VIEW Customer AS 
	SELECT h.ID
	     , h.FirstName
         , h.LastName
	     , h.MiddleName
		 , c.Phone
	  from tHuman h
	  natural join tCustomer c
;

CREATE TRIGGER tr_II_Customer INSTEAD OF INSERT ON Customer  
REFERENCING NEW ROW AS n
FOR EACH ROW 
BEGIN ATOMIC
	INSERT INTO tHuman(  FirstName,   LastName,   MiddleName) 
	            VALUES(n.FirstName, n.LastName, n.MiddleName );
				
	INSERT INTO tCustomer(id     , phone) 
	            VALUES(identity(), n.phone );
END;

CREATE TRIGGER tr_IU_Customer INSTEAD OF UPDATE ON Customer  
REFERENCING OLD ROW AS d NEW ROW AS n
FOR EACH ROW 
BEGIN ATOMIC
	UPDATE tHuman 
	   SET FirstName  = n.FirstName
	     , LastName   = n.LastName
		 , MiddleName = n.MiddleName
	 WHERE tHuman.id  = d.id;
	 
	UPDATE tCustomer
	   SET Phone        = n.phone
	 WHERE tCustomer.id = d.id;
END;

CREATE TRIGGER tr_ID_Customer INSTEAD OF DELETE ON Customer  
REFERENCING OLD ROW AS d 
FOR EACH ROW 
BEGIN ATOMIC
	DELETE 
	  FROM tHuman
	 WHERE tHuman.id = d.id;
END;


--insert into customer (firstname, lastname, middlename, phone) values ('', '', '', '');

CREATE TABLE tSpecialization (
	ID    BIGINT IDENTITY Primary key,
	Name  varchar(255),
	CONSTRAINT ChkUniqueNameSpec UNIQUE (Name)
);

CREATE PROCEDURE GetSpecID(OUT SpecID BIGINT, IN pName VARCHAR(255))
MODIFIES SQL DATA
BEGIN ATOMIC
    DECLARE TempID BIGINT;
	SET TempID  = SELECT  min(s.ID) FROM tSpecialization s WHERE Name = pName;
	IF TempID IS NULL THEN 
		INSERT INTO tSpecialization (Name) VALUES (pName);
		SET TempID = IDENTITY();	
	END IF;
	SET SpecID = TempID;
END;


CREATE TABLE tDoctor(
	ID     BIGINT Primary key,
	SpecID BIGINT NULL,
	CONSTRAINT FkHumanDoct FOREIGN KEY(ID) REFERENCES tHuman(ID) ON DELETE CASCADE,
	CONSTRAINT FkSpecDoct  FOREIGN KEY(SpecID) REFERENCES tSpecialization(ID) ON DELETE SET NULL
);

CREATE VIEW Doctor AS 
	SELECT h.ID
	     , h.FirstName
         , h.LastName
	     , h.MiddleName
		 , s.Name as Specialization
	  FROM tHuman h
	  NATURAL JOIN tDoctor d
	  LEFT JOIN tSpecialization s ON s.ID = d.SpecID
;

CREATE TRIGGER tr_II_Doctor INSTEAD OF INSERT ON Doctor  
REFERENCING NEW ROW AS n
FOR EACH ROW 
BEGIN ATOMIC
	DECLARE vSpecID BIGINT;
	CALL GetSpecID( vSpecID, n.Specialization);
	
	INSERT INTO tHuman(  FirstName,   LastName,   MiddleName) 
	            VALUES(n.FirstName, n.LastName, n.MiddleName );
				
	INSERT INTO tDoctor(id       , SpecID) 
	            VALUES(identity(), vSpecID );
END;

CREATE TRIGGER tr_IU_Doctor INSTEAD OF UPDATE ON Doctor 
REFERENCING OLD ROW AS d NEW ROW AS n
FOR EACH ROW 
BEGIN ATOMIC
	DECLARE vSpecID BIGINT;
	CALL GetSpecID( vSpecID, n.Specialization);

	UPDATE tHuman 
	   SET FirstName  = n.FirstName
	     , LastName   = n.LastName
		 , MiddleName = n.MiddleName
	 WHERE tHuman.id  = d.id;
	 
	UPDATE tDoctor
	   SET SpecID     = vSpecID
	 WHERE tDoctor.id = d.id;
END;

CREATE TRIGGER tr_ID_Doctor INSTEAD OF DELETE ON Doctor  
REFERENCING OLD ROW AS d 
FOR EACH ROW 
BEGIN ATOMIC
	DELETE 
	  FROM tHuman
	 WHERE tHuman.id = d.id;
END;


--insert into Doctor (firstname, lastname, middlename, Specialization) values ('', '', '', 'test');

CREATE TABLE tPrescription (
	ID           BIGINT IDENTITY Primary key,
	Description  varchar(4096),
	CustomerID   BIGINT,
	DoctorID     BIGINT, 
	CreatedDate  TIMESTAMP DEFAULT LOCALTIMESTAMP, 
	ValidityDate DATE NULL,
	Priority     INT,
	CONSTRAINT FkPrescDoctor FOREIGN KEY (DoctorID) References tDoctor(ID) ON DELETE CASCADE,
	CONSTRAINT FkPrescCustom FOREIGN KEY (CustomerID) References tCustomer(ID) ON DELETE CASCADE	
); 

CREATE INDEX IE1_Prescription ON tPrescription (DoctorID);


--insert into tPrescription(Description, CustomerID, DoctorID, Priority) values ('123', 0, 0, 0);
