DROP TABLE IF EXISTS tbl_student;
CREATE TABLE tbl_student
(
    id            INT         NOT NULL auto_increment,
    number        VARCHAR(16) NOT NULL,
    name          VARCHAR(16) NOT NULL,
    password      VARCHAR(16) NOT NULL,
    major         VARCHAR(16) NOT NULL,
    college       VARCHAR(16) NOT NULL,
    isBlacklisted BOOLEAN default false,
    defaultCount  INT     default 0,
    PRIMARY KEY (id),
    UNIQUE (number)
);

DROP TABLE IF EXISTS tbl_admin;
CREATE TABLE tbl_admin
(
    id       INT         NOT NULL auto_increment,
    name     VARCHAR(16) NOT NULL,
    password VARCHAR(16) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

DROP TABLE IF EXISTS tbl_study_room;
CREATE TABLE tbl_study_room
(
    id                  INT  NOT NULL auto_increment,
    campusId            INT  NOT NULL,
    regionId            INT  NOT NULL,
    buildingId          INT  NOT NULL,
    number              INT  NOT NULL,
    status              INT  NOT NULL,
    openTime            TIME NOT NULL,
    closeTime           TIME NOT NULL,
    availableSeatNumber INT default 50,
    PRIMARY KEY (id)
);


DROP TABLE IF EXISTS tbl_seat;
CREATE TABLE tbl_seat
(
    id          INT NOT NULL auto_increment,
    studyRoomId INT NOT NULL,
    seatColumn  INT NOT NULL,
    seatRow     INT NOT NULL,
    hasSocket   BOOLEAN default false,
    status      INT     default 0,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS tbl_reservation_record;
CREATE TABLE tbl_reservation_record
(
    id          INT      NOT NULL auto_increment,
    studentId   INT      NOT NULL,
    studyRoomId INT      NOT NULL,
    seatId      INT      NOT NULL,
    reserveTime DATETIME NOT NULL,
    startTime   DATETIME NOT NULL,
    endTime     DATETIME NOT NULL,
    status      INT      NOT NULL,
    type        INT      NOT NULL,
    PRIMARY KEY (id)
);
