CREATE TABLE FLIGHT (
	FLIGHTID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	ARRIVAL_TIME TIME,
	ARRIVAL_DATE DATE,
	DEPARTURE_TIME TIME,
	DEPARTURE_DATE DATE,
	CONSTRAINT FLIGHT_PK PRIMARY KEY(FLIGHTID)
);

--COMMON TABLE
--DROP TABLE AIRPORT;
CREATE TABLE AIRPORT(
	APCODE CHAR(3) NOT NULL,
	APNAME VARCHAR(50) NOT NULL,
	CITY VARCHAR(25), --your team needs this column
	COUNTRY VARCHAR(25), --your team needs this column
	CONSTRAINT AIRPORT_PK PRIMARY KEY(APCODE)
);

--THIS TABLE IS USED FOR FLIGHT CREATION SCENARIO
--DROP TABLE ROUTE;
CREATE TABLE ROUTE (
	ROUTEID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	APCODE_SRC CHAR(3) NOT NULL,
	APCODE_DST CHAR(3) NOT NULL,
	CONSTRAINT ROUTE_PK PRIMARY KEY (ROUTEID),
	CONSTRAINT ROUTE_FK1 FOREIGN KEY (APCODE_SRC) REFERENCES AIRPORT(APCODE) ON DELETE CASCADE,
	CONSTRAINT ROUTE_FK2 FOREIGN KEY (APCODE_DST) REFERENCES AIRPORT(APCODE) ON DELETE CASCADE
);

--COMMON TABLES
--DROP TABLE FLIGHTSEGMENT;
CREATE TABLE FLIGHTSEGMENT (
	FLIGHTNR INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	--CREW_ID INTEGER, --your team(booking ticket) might needs this column
	--APID INTEGER, --your team might needs this column
	ARRIVAL_DATE DATE,
	DEPARTURE_TIME TIME,
	DEPARTURE_DATE DATE,
	ARRIVAL_TIME TIME,
	SOURCE_CITY VARCHAR(25), --your team needs this column
	DESTINATION_CITY VARCHAR(25), --your team needs this column
    ROUTEID INTEGER,
	CONSTRAINT FLIGHTSEGMENT_PK PRIMARY KEY(FLIGHTNR),
	CONSTRAINT FLIGHTSEGMENT_ROUTE_FK1 FOREIGN KEY(ROUTEID) REFERENCES ROUTE(ROUTEID)	
);

--THIS TABLE IS USED FOR FLIGHT CREATION SCENARIO
--DROP TABLE FLIGHT_CONSISTS_OF;
CREATE TABLE FLIGHT_CONSISTS_OF(
	FLIGHTID INTEGER NOT NULL,
	FLIGHTNR INTEGER NOT NULL,
	CONSTRAINT FLOWN_BY_AIRPLANE_PK PRIMARY KEY(FLIGHTID, FLIGHTNR),
	CONSTRAINT FLOWN_BY_AIRPLANE_FK1 FOREIGN KEY(FLIGHTID) REFERENCES FLIGHT(FLIGHTID),
	CONSTRAINT FLOWN_BY_AIRPLANE_FK2 FOREIGN KEY(FLIGHTNR) REFERENCES FLIGHTSEGMENT(FLIGHTNR)
);

CREATE INDEX I01 ON FLIGHTSEGMENT(ROUTEID);
CREATE INDEX I02 ON FLIGHT_CONSISTS_OF(FLIGHTID,FLIGHTNR);
CREATE INDEX I03 ON ROUTE(APCODE_SRC,APCODE_DST);

--THIS TABLE IS USED FOR FLIGHT BOOKING SCENARIO
--DROP TABLE AGENT;
CREATE TABLE AGENT(
	AGENTID INTEGER NOT NULL,
	LNAME VARCHAR(25), 
	FNAME VARCHAR(25),
	ADDRESS VARCHAR(32), 
	CONTACTNO VARCHAR(15), 
	COUNTRY VARCHAR(25), 
	CONSTRAINT AGENT_PK PRIMARY KEY(AGENTID)
); 

--THIS TABLE IS USED FOR FLIGHT BOOKING SCENARIO
--DROP TABLE CUSTOMER;
CREATE TABLE CUSTOMER(
	CUSTOMERID INTEGER NOT NULL,
	LNAME VARCHAR(25), 
	FNAME VARCHAR(25),
	DOB DATE, 
	SEX CHAR(3),
	ADDRESS VARCHAR(32), 
	CONTACTNO VARCHAR(15), 
	COUNTRY VARCHAR(25),
    PASSPORTNO VARCHAR(32),
	CONSTRAINT CUSTOMER_PK PRIMARY KEY(CUSTOMERID)
); 

--THIS TABLE IS USED FOR FLIGHT BOOKING SCENARIO
--DROP TABLE CONTACTS;
CREATE TABLE CONTACTS(
	CUSTOMERID INTEGER NOT NULL,
	AGENTID INTEGER NOT NULL,
	CURRENCY VARCHAR(15), 
	STATUS VARCHAR(15), 
	MODEOFPAYMENT VARCHAR(15), 
	AMOUNT DOUBLE,
	CONSTRAINT CONTACTS_PK PRIMARY KEY(CUSTOMERID, AGENTID),
	CONSTRAINT CONTACTS_AGENT_FK1 FOREIGN KEY(AGENTID) REFERENCES AGENT(AGENTID),
	CONSTRAINT CONTACTS_CUSTOMER_FK2 FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(CUSTOMERID)
); 

--THIS TABLE IS USED FOR FLIGHT BOOKING SCENARIO
--DROP TABLE TRANSACTIONS;
CREATE TABLE TRANSACTIONS(
	TID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    AGENTID INTEGER NOT NULL,
	CUSTOMERID INTEGER NOT NULL,
    FLIGHTID INTEGER NOT NULL,
	T_TIMESTAMP TIMESTAMP, 
	T_STATUS VARCHAR(15),
	CURRENCY VARCHAR(15),
	MODEOFPAYMENT VARCHAR(15), 
	AMOUNT DOUBLE, 
    TYPEOFTRANSACTION VARCHAR(15),
    CONSTRAINT TRANSACTIONS_PK PRIMARY KEY(TID),
	CONSTRAINT TRANSACTIONS_CUST_FK1 FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(CUSTOMERID),
	CONSTRAINT TRANSACTIONS_AGENT_FK2 FOREIGN KEY(AGENTID) REFERENCES AGENT(AGENTID),
	CONSTRAINT TRANSACTIONS_FLIGHT_FK3 FOREIGN KEY(FLIGHTID) REFERENCES FLIGHT(FLIGHTID)
);

--THIS TABLE IS USED FOR FLIGHT BOOKING SCENARIO
--DROP TABLE TICKET;
CREATE TABLE TICKET(
    TICKETID INTEGER NOT NULL,
	FLIGHTID INTEGER NOT NULL,
	CUSTOMERID INTEGER NOT NULL,
	NOOFCHILDREN INTEGER,
	TOTALFARE DOUBLE,
	DEPARTURE_TIME TIME,
	DEPARTURE_DATE DATE,
	ARRIVAL_DATE DATE,
	ARRIVAL_TIME TIME,
	CURRENCY VARCHAR(15),
	APCODE_SRC CHAR(3),
	APCODE_DST CHAR(3),
	CONSTRAINT TICKET_PK PRIMARY KEY(TICKETID),
	CONSTRAINT TICKET_CUST_FK1 FOREIGN KEY(CUSTOMERID) REFERENCES CUSTOMER(CUSTOMERID),
	CONSTRAINT TICKET_FLIGHT_FK2 FOREIGN KEY(FLIGHTID) REFERENCES FLIGHT(FLIGHTID)
);

CREATE INDEX I04 ON TRANSACTIONS(AGENTID ASC, FLIGHTID ASC);
CREATE INDEX I05 ON TICKET(CUSTOMERID,FLIGHTID);

insert into AIRPORT( apcode,apname)
values ('FRA','Frankfurt International Airport'),
('MUC','Munich Airport'),
('TXL','Berlin-Tegel Airport'),
('HAM','Hamburg Airport'),
('KHI','Karachi International Airport'),
('IKA','Imam Khomeini Airport'),
('IGI','Indira Gandhi International Airport'),
('DXB','Dubai International Airport'),
('MUS','Muscat International Airport');

insert into ROUTE(APCODE_SRC,APCODE_DST)
values('FRA','MUC'),
('FRA','TXL'),
('FRA','HAM'),
('FRA','IKA'),
('FRA','IGI'),
('FRA','DXB'),
('DXB','KHI'),
('MUC','DXB'),
('FRA','MUS'),
('MUS','KHI');


insert into FLIGHT(DEPARTURE_DATE,DEPARTURE_TIME,ARRIVAL_DATE,ARRIVAL_TIME )
values ('02/02/2013','09:00:00','02/02/2013','21:00:00');

--PICK THE SOURCE AND DESTINATION AND GET THE ROUTE

insert into FLIGHTSEGMENT(DEPARTURE_DATE,DEPARTURE_TIME,ARRIVAL_DATE,ARRIVAL_TIME,ROUTEID)
values('02/02/2013','09:00:00','02/02/2013','21:00:00',6),
('02/02/2013','09:00:00','02/02/2013','21:00:00',7);


insert into FLIGHT_CONSISTS_OF(FLIGHTID,FLIGHTNR)
values(1,1),
(1,2);

insert into AGENT(AGENTID,LNAME, FNAME)
values(1,'BOSCH', 'ROBERT');

