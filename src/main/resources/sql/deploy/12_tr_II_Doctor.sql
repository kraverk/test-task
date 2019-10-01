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
