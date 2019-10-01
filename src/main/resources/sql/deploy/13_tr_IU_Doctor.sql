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
