CREATE VIEW Customer AS 
	SELECT h.ID
	     , h.FirstName
             , h.LastName
	     , h.MiddleName
	     , c.Phone
	  from tHuman as h
	  inner join tCustomer as c on c.ID = h.ID
;
