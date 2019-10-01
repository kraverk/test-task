CREATE TABLE tCustomer(
	ID    BIGINT Primary key,
	Phone varchar(255),
	CONSTRAINT FkHumanCust FOREIGN KEY(ID) REFERENCES tHuman(ID) ON DELETE CASCADE
);
