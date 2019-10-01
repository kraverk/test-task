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
