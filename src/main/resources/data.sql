INSERT INTO Employees(address, age, benefit, city, email, first_name, last_name, salary, start_job_date) VALUES
    ('Grójecka 28',18,0,'Warszawa','dolek@wp.pl','Piotr','JPawlak',1000,'2015-06-12'),
    ('Zielona 28',28,1,'Kraków','ek@wp.pl','Paweł','Kaczyński',2000,'2012-03-01'),
    ('Grójecka 28',45,1,'Warszawa','zlek@wp.pl','Anna','JPawlak',3000,'2019-12-12'),
    ('Marymoncka 28',39,1,'Gdynia','tek@wp.pl','Katarzyna','Gierszałt',4000,GETDATE()),
    ('Koszykowa 28',31,1,'Warszawa','plk@wp.pl','Maciej','Józefowicz',5000,'2019-12-12'),
    ('Rybickiego 128',29,1,'Zamość','tlk@wp.pl','Genowefa','Pigwa',6000,'2019-12-12'),
    ('Szucha 8',18,0,'Warszawa','aat@wp.pl','Piotr','Złomczyński',1500,'2019-12-12');

INSERT INTO Cars(employee_id, brand, model, registration_date) VALUES
    (6,'Subaru','Forester',GETDATE()),
    (2,'Ford','Fiesta',GETDATE()),
    (3,'Fiat','126jpa',GETDATE()),
    (4,'Subaru','Forester',GETDATE()),
    (5,'Subaru','Forester',GETDATE());

