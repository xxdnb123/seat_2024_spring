INSERT INTO tbl_student (number, name, password, major, college)
VALUES ('101', '张三', '123', '计算机科学与技术', '信息工程学院');

INSERT INTO tbl_student (number, name, password, major, college, isBlacklisted, defaultCount)
VALUES ('102', '002', '123', '软件工程', '信息工程学院', true, 2);

INSERT INTO tbl_student (number, name, password, major, college, isBlacklisted, defaultCount)
VALUES ('103', '李四', '123', '应用数学', '数学学院', false, 1);

INSERT INTO tbl_student (number, name, password, major, college, isBlacklisted, defaultCount)
VALUES ('104', '王五', '123', '汉语言', '文学院', true, 2);

INSERT INTO tbl_student (number, name, password, major, college, isBlacklisted, defaultCount)
VALUES ('105', '王21', '123', '有机化学', '化学学院', false, 2);

INSERT INTO tbl_admin (name, password)
VALUES ('admin', '123');

INSERT INTO tbl_admin (name, password)
VALUES ('root', 'abc');

INSERT INTO tbl_study_room (campusId, regionId, buildingId, number, status, openTime, closeTime, availableSeatNumber)
VALUES (1, 101, 101001, 103, 1, '00:00:01', '23:59:00', 3);

INSERT INTO tbl_study_room (campusId, regionId, buildingId, number, status, openTime, closeTime, availableSeatNumber)
VALUES (2, 201, 201001, 204, 1, '10:00:00', '20:00:00', 2);

INSERT INTO tbl_study_room (campusId, regionId, buildingId, number, status, openTime, closeTime, availableSeatNumber)
VALUES (3, 301, 301002, 304, 1, '08:00:00', '22:00:00', 4);

INSERT INTO tbl_study_room (campusId, regionId, buildingId, number, status, openTime, closeTime, availableSeatNumber)
VALUES (1, 103, 103001, 201, 1, '10:00:00', '20:00:00', 2);


INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (1, 5, 2, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (1, 1, 2, false, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (1, 2, 1, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (2, 1, 1, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (2, 1, 2, false, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (3, 2, 1, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (3, 1, 1, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (3, 1, 2, false, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (3, 2, 1, true, 1);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (4, 1, 1, true, 0);

INSERT INTO tbl_seat (studyRoomId, seatColumn, seatRow, hasSocket, status)
VALUES (4, 1, 2, false, 1);


INSERT INTO tbl_reservation_record (studentId, studyRoomId, seatId, reserveTime, startTime, endTime, status, type)
VALUES (1, 1, 1, '2023-05-01 9:00:00', '2023-05-01 21:00:00', '2023-05-01 22:00:00', 2, 1);

INSERT INTO tbl_reservation_record (studentId, studyRoomId, seatId, reserveTime, startTime, endTime, status, type)
VALUES (2, 1, 2, '2023-06-08 13:00:00', '2023-06-08 21:00:00', '2023-06-08 22:00:00', 2, 0);

INSERT INTO tbl_reservation_record (studentId, studyRoomId, seatId, reserveTime, startTime, endTime, status, type)
VALUES (3, 2, 4, '2023-06-08 13:00:00', '2023-06-08 21:00:00', '2023-06-08 22:00:00', 0, 0)