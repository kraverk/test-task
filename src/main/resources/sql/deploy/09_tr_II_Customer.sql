CREATE TRIGGER tr_II_Customer INSTEAD OF INSERT ON Customer  
REFERENCING NEW ROW AS n
FOR EACH ROW 
BEGIN ATOMIC
	INSERT INTO tHuman(  FirstName,   LastName,   MiddleName) 
	            VALUES(n.FirstName, n.LastName, n.MiddleName );
				
	INSERT INTO tCustomer(id     , phone) 
	            VALUES(identity(), n.phone );
END;
