CREATE TABLE tSpecialization (
	ID    BIGINT IDENTITY Primary key,
	Name  varchar(255),
	CONSTRAINT ChkUniqueNameSpec UNIQUE (Name)
);
