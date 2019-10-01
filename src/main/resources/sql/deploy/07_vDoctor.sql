CREATE VIEW Doctor AS 
	SELECT h.ID
	     , h.FirstName
         , h.LastName
	     , h.MiddleName
		 , s.Name as Specialization
	  FROM tHuman h
	  INNER JOIN tDoctor d ON d.ID = h.ID
	  LEFT JOIN tSpecialization s ON s.ID = d.SpecID
;
