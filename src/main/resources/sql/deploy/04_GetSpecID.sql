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
