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
