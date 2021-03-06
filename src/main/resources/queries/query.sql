create database capias;

-- Table RUle --
-- 1. Naming --
-- TB_#{Domain}
--    ex. TB_PRODUCT
--  	  TB_CUSTOMER

show databases;
use capias;

----------------------------------  TB_PRODUCT  -----------------------------------------

CREATE TABLE capias.TB_PRODUCT
	(ID int(10) auto_increment primary key,		-- 등록번호
    TYPE varchar(10) not null,								-- 하드웨어/소프트웨어/기타
	CATEGORY varchar(10) not null,						-- 상품분류
	NAME varchar(128) not null, 							-- 상품명
    PRICE int(16) not null default 0,						-- 가격
    COST int(10) not null default 0,						-- 원가
	RATED int(2) not null default 0,						-- 제한등급
	STOCK int(8) not null default 0,						-- 재고
	RELEASE_DATE date not null,							-- 발매일자
	REGIST_DATE timestamp not null,					-- 등록일자
	DISCOUNT_RATE int(3) not null default 0 	-- 할인율 (COST * (100 - DISCOUNT_RATE))
	);

SELECT * FROM capias.TB_PRODUCT;

----------------------------------  TB_MEMBER  -----------------------------------------

CREATE TABLE capias.TB_MEMBER
	(ID int(10) auto_increment primary key,		-- 사번
	DEPARTMENT varchar(16) not null,				-- 부서
	POSITION varchar(16) not null,						-- 직책
    PHOTO varchar(1024) not null,						-- 사진
	NAME varchar(64) not null, 							-- 이름
    EMAIL varchar(64) not null unique key,		-- 이메일
    PASSWORD varchar(64) not null,					-- 비밀번호
    FAIL_COUNT int(1) default 0,					-- 비밀번호 틀린 횟수
    STATUS varchar(8) default "ACTIVE",				-- 상태 (ACTIVE / LOCKED / EXPIRED)
    ROLE varchar(8) not null,						-- 권한
    PHONE varchar(16),											-- 연락처
    EMERGENCY_CONTACT varchar(16),				-- 비상연락처
	BIRTHDAY date not null,									-- 생일	
	HIRE_DATE date not null,									-- 입사일
	LAST_LOGIN_TIME datetime 						-- 마지막 로그인 시간
	);


SELECT * FROM capias.TB_MEMBER;
select * from tb_law;
select * from capias.tb_history;


----------------------------------  TB_MENU  -----------------------------------------
select * from capias.tb_menu;

insert into capias.tb_menu values(1, '', '직원 관리', 0);
insert into capias.tb_menu values(2, '', '매매 관리', 0);
insert into capias.tb_menu values(3, '', '카테고리 관리', 0);







INSERT INTO TB_LAW VALUES(1001,6,10,12,14,20,42,15);
INSERT INTO TB_LAW VALUES(1000,2,8,19,22,32,42,39);
INSERT INTO TB_LAW VALUES(999,1,3,9,14,18,28,34);
INSERT INTO TB_LAW VALUES(998,13,17,18,20,42,45,41);
INSERT INTO TB_LAW VALUES(997,4,7,14,16,24,44,20);
INSERT INTO TB_LAW VALUES(996,6,11,15,24,32,39,28);
INSERT INTO TB_LAW VALUES(995,1,4,13,29,38,39,7);
INSERT INTO TB_LAW VALUES(994,1,3,8,24,27,35,28);
INSERT INTO TB_LAW VALUES(993,6,14,16,18,24,42,44);
INSERT INTO TB_LAW VALUES(992,12,20,26,33,44,45,24);
INSERT INTO TB_LAW VALUES(991,13,18,25,31,33,44,38);
INSERT INTO TB_LAW VALUES(990,2,4,25,26,36,37,28);
INSERT INTO TB_LAW VALUES(989,17,18,21,27,29,33,26);
INSERT INTO TB_LAW VALUES(988,2,13,20,30,31,41,27);
INSERT INTO TB_LAW VALUES(987,2,4,15,23,29,38,7);
INSERT INTO TB_LAW VALUES(986,7,10,16,28,41,42,40);
INSERT INTO TB_LAW VALUES(985,17,21,23,30,34,44,19);
INSERT INTO TB_LAW VALUES(984,3,10,23,35,36,37,18);
INSERT INTO TB_LAW VALUES(983,13,23,26,31,35,43,15);
INSERT INTO TB_LAW VALUES(982,5,7,13,20,21,44,33);
INSERT INTO TB_LAW VALUES(981,27,36,37,41,43,45,32);
INSERT INTO TB_LAW VALUES(980,3,13,16,23,24,35,14);
INSERT INTO TB_LAW VALUES(979,7,11,16,21,27,33,24);
INSERT INTO TB_LAW VALUES(978,1,7,15,32,34,42,8);
INSERT INTO TB_LAW VALUES(977,2,9,10,14,22,44,16);
INSERT INTO TB_LAW VALUES(976,4,12,14,25,35,37,2);
INSERT INTO TB_LAW VALUES(975,7,8,9,17,22,24,5);
INSERT INTO TB_LAW VALUES(974,1,2,11,16,39,44,32);
INSERT INTO TB_LAW VALUES(973,22,26,31,37,41,42,24);
INSERT INTO TB_LAW VALUES(972,3,6,17,23,37,39,26);
INSERT INTO TB_LAW VALUES(971,2,6,17,18,21,26,7);
INSERT INTO TB_LAW VALUES(970,9,11,16,21,28,36,5);
INSERT INTO TB_LAW VALUES(969,3,9,10,29,40,45,7);
INSERT INTO TB_LAW VALUES(968,2,5,12,14,24,39,33);
INSERT INTO TB_LAW VALUES(967,1,6,13,37,38,40,9);
INSERT INTO TB_LAW VALUES(966,1,21,25,29,34,37,36);
INSERT INTO TB_LAW VALUES(965,2,13,25,28,29,36,34);
INSERT INTO TB_LAW VALUES(964,6,21,36,38,39,43,30);
INSERT INTO TB_LAW VALUES(963,6,12,19,23,34,42,35);
INSERT INTO TB_LAW VALUES(962,1,18,28,31,34,43,40);
INSERT INTO TB_LAW VALUES(961,11,20,29,31,33,42,43);
INSERT INTO TB_LAW VALUES(960,2,18,24,30,32,45,14);
INSERT INTO TB_LAW VALUES(959,1,14,15,24,40,41,35);
INSERT INTO TB_LAW VALUES(958,2,9,10,16,35,37,1);
INSERT INTO TB_LAW VALUES(957,4,15,24,35,36,40,1);
INSERT INTO TB_LAW VALUES(956,10,11,20,21,25,41,40);
INSERT INTO TB_LAW VALUES(955,4,9,23,26,29,33,8);
INSERT INTO TB_LAW VALUES(954,1,9,26,28,30,41,32);
INSERT INTO TB_LAW VALUES(953,7,9,22,27,37,42,34);
INSERT INTO TB_LAW VALUES(952,4,12,22,24,33,41,38);
INSERT INTO TB_LAW VALUES(951,2,12,30,31,39,43,38);
INSERT INTO TB_LAW VALUES(950,3,4,15,22,28,40,10);
INSERT INTO TB_LAW VALUES(949,14,21,35,36,40,44,30);
INSERT INTO TB_LAW VALUES(948,13,18,30,31,38,41,5);
INSERT INTO TB_LAW VALUES(947,3,8,17,20,27,35,26);
INSERT INTO TB_LAW VALUES(946,9,18,19,30,34,40,20);
INSERT INTO TB_LAW VALUES(945,9,10,15,30,33,37,26);
INSERT INTO TB_LAW VALUES(944,2,13,16,19,32,33,42);
INSERT INTO TB_LAW VALUES(943,1,8,13,36,44,45,39);
INSERT INTO TB_LAW VALUES(942,10,12,18,35,42,43,39);
INSERT INTO TB_LAW VALUES(941,12,14,25,27,39,40,35);
INSERT INTO TB_LAW VALUES(940,3,15,20,22,24,41,11);
INSERT INTO TB_LAW VALUES(939,4,11,28,39,42,45,6);
INSERT INTO TB_LAW VALUES(938,4,8,10,16,31,36,9);
INSERT INTO TB_LAW VALUES(937,2,10,13,22,29,40,26);
INSERT INTO TB_LAW VALUES(936,7,11,13,17,18,29,43);
INSERT INTO TB_LAW VALUES(935,4,10,20,32,38,44,18);
INSERT INTO TB_LAW VALUES(934,1,3,30,33,36,39,12);
INSERT INTO TB_LAW VALUES(933,23,27,29,31,36,45,37);
INSERT INTO TB_LAW VALUES(932,1,6,15,36,37,38,5);
INSERT INTO TB_LAW VALUES(931,14,15,23,25,35,43,32);
INSERT INTO TB_LAW VALUES(930,8,21,25,38,39,44,28);
INSERT INTO TB_LAW VALUES(929,7,9,12,15,19,23,4);
INSERT INTO TB_LAW VALUES(928,3,4,10,20,28,44,30);
INSERT INTO TB_LAW VALUES(927,4,15,22,38,41,43,26);
INSERT INTO TB_LAW VALUES(926,10,16,18,20,25,31,6);
INSERT INTO TB_LAW VALUES(925,13,24,32,34,39,42,4);
INSERT INTO TB_LAW VALUES(924,3,11,34,42,43,44,13);
INSERT INTO TB_LAW VALUES(923,3,17,18,23,36,41,26);
INSERT INTO TB_LAW VALUES(922,2,6,13,17,27,43,36);
INSERT INTO TB_LAW VALUES(921,5,7,12,22,28,41,1);
INSERT INTO TB_LAW VALUES(920,2,3,26,33,34,43,29);
INSERT INTO TB_LAW VALUES(919,9,14,17,18,42,44,35);
INSERT INTO TB_LAW VALUES(918,7,11,12,31,33,38,5);
INSERT INTO TB_LAW VALUES(917,1,3,23,24,27,43,34);
INSERT INTO TB_LAW VALUES(916,6,21,22,32,35,36,17);
INSERT INTO TB_LAW VALUES(915,2,6,11,13,22,37,14);
INSERT INTO TB_LAW VALUES(914,16,19,24,33,42,44,27);
INSERT INTO TB_LAW VALUES(913,6,14,16,21,27,37,40);
INSERT INTO TB_LAW VALUES(912,5,8,18,21,22,38,10);
INSERT INTO TB_LAW VALUES(911,4,5,12,14,32,42,35);
INSERT INTO TB_LAW VALUES(910,1,11,17,27,35,39,31);
INSERT INTO TB_LAW VALUES(909,7,24,29,30,34,35,33);
INSERT INTO TB_LAW VALUES(908,3,16,21,22,23,44,30);
INSERT INTO TB_LAW VALUES(907,21,27,29,38,40,44,37);
INSERT INTO TB_LAW VALUES(906,2,5,14,28,31,32,20);
INSERT INTO TB_LAW VALUES(905,3,4,16,27,38,40,20);
INSERT INTO TB_LAW VALUES(904,2,6,8,26,43,45,11);
INSERT INTO TB_LAW VALUES(903,2,15,16,21,22,28,45);
INSERT INTO TB_LAW VALUES(902,7,19,23,24,36,39,30);
INSERT INTO TB_LAW VALUES(901,5,18,20,23,30,34,21);
INSERT INTO TB_LAW VALUES(900,7,13,16,18,35,38,14);
INSERT INTO TB_LAW VALUES(899,8,19,20,21,33,39,37);
INSERT INTO TB_LAW VALUES(898,18,21,28,35,37,42,17);
INSERT INTO TB_LAW VALUES(897,6,7,12,22,26,36,29);
INSERT INTO TB_LAW VALUES(896,5,12,25,26,38,45,23);
INSERT INTO TB_LAW VALUES(895,16,26,31,38,39,41,23);
INSERT INTO TB_LAW VALUES(894,19,32,37,40,41,43,45);
INSERT INTO TB_LAW VALUES(893,1,15,17,23,25,41,10);
INSERT INTO TB_LAW VALUES(892,4,9,17,18,26,42,36);
INSERT INTO TB_LAW VALUES(891,9,13,28,31,39,41,19);
INSERT INTO TB_LAW VALUES(890,1,4,14,18,29,37,6);
INSERT INTO TB_LAW VALUES(889,3,13,29,38,39,42,26);
INSERT INTO TB_LAW VALUES(888,3,7,12,31,34,38,32);
INSERT INTO TB_LAW VALUES(887,8,14,17,27,36,45,10);
INSERT INTO TB_LAW VALUES(886,19,23,28,37,42,45,2);
INSERT INTO TB_LAW VALUES(885,1,3,24,27,39,45,31);
INSERT INTO TB_LAW VALUES(884,4,14,23,28,37,45,17);
INSERT INTO TB_LAW VALUES(883,9,18,32,33,37,44,22);
INSERT INTO TB_LAW VALUES(882,18,34,39,43,44,45,23);
INSERT INTO TB_LAW VALUES(881,4,18,20,26,27,32,9);
INSERT INTO TB_LAW VALUES(880,7,17,19,23,24,45,38);
INSERT INTO TB_LAW VALUES(879,1,4,10,14,15,35,20);
INSERT INTO TB_LAW VALUES(878,2,6,11,16,25,31,3);
INSERT INTO TB_LAW VALUES(877,5,17,18,22,23,43,12);
INSERT INTO TB_LAW VALUES(876,5,16,21,26,34,42,24);
INSERT INTO TB_LAW VALUES(875,19,22,30,34,39,44,36);
INSERT INTO TB_LAW VALUES(874,1,15,19,23,28,42,32);
INSERT INTO TB_LAW VALUES(873,3,5,12,13,33,39,38);
INSERT INTO TB_LAW VALUES(872,2,4,30,32,33,43,29);
INSERT INTO TB_LAW VALUES(871,2,6,12,26,30,34,38);
INSERT INTO TB_LAW VALUES(870,21,25,30,32,40,42,31);
INSERT INTO TB_LAW VALUES(869,2,6,20,27,37,39,4);
INSERT INTO TB_LAW VALUES(868,12,17,28,41,43,44,25);
INSERT INTO TB_LAW VALUES(867,14,17,19,22,24,40,41);
INSERT INTO TB_LAW VALUES(866,9,15,29,34,37,39,12);
INSERT INTO TB_LAW VALUES(865,3,15,22,32,33,45,2);
INSERT INTO TB_LAW VALUES(864,3,7,10,13,25,36,32);
INSERT INTO TB_LAW VALUES(863,16,21,28,35,39,43,12);
INSERT INTO TB_LAW VALUES(862,10,34,38,40,42,43,32);
INSERT INTO TB_LAW VALUES(861,11,17,19,21,22,25,24);
INSERT INTO TB_LAW VALUES(860,4,8,18,25,27,32,42);
INSERT INTO TB_LAW VALUES(859,8,22,35,38,39,41,24);
INSERT INTO TB_LAW VALUES(858,9,13,32,38,39,43,23);
INSERT INTO TB_LAW VALUES(857,6,10,16,28,34,38,43);
INSERT INTO TB_LAW VALUES(856,10,24,40,41,43,44,17);
INSERT INTO TB_LAW VALUES(855,8,15,17,19,43,44,7);
INSERT INTO TB_LAW VALUES(854,20,25,31,32,36,43,3);
INSERT INTO TB_LAW VALUES(853,2,8,23,26,27,44,13);
INSERT INTO TB_LAW VALUES(852,11,17,28,30,33,35,9);
INSERT INTO TB_LAW VALUES(851,14,18,22,26,31,44,40);
INSERT INTO TB_LAW VALUES(850,16,20,24,28,36,39,5);
INSERT INTO TB_LAW VALUES(849,5,13,17,29,34,39,3);
INSERT INTO TB_LAW VALUES(848,1,2,16,22,38,39,34);
INSERT INTO TB_LAW VALUES(847,12,16,26,28,30,42,22);
INSERT INTO TB_LAW VALUES(846,5,18,30,41,43,45,13);
INSERT INTO TB_LAW VALUES(845,1,16,29,33,40,45,6);
INSERT INTO TB_LAW VALUES(844,7,8,13,15,33,45,18);
INSERT INTO TB_LAW VALUES(843,19,21,30,33,34,42,4);
INSERT INTO TB_LAW VALUES(842,14,26,32,36,39,42,38);
INSERT INTO TB_LAW VALUES(841,5,11,14,30,33,38,24);
INSERT INTO TB_LAW VALUES(840,2,4,11,28,29,43,27);
INSERT INTO TB_LAW VALUES(839,3,9,11,12,13,19,35);
INSERT INTO TB_LAW VALUES(838,9,14,17,33,36,38,20);
INSERT INTO TB_LAW VALUES(837,2,25,28,30,33,45,6);
INSERT INTO TB_LAW VALUES(836,1,9,11,14,26,28,19);
INSERT INTO TB_LAW VALUES(835,9,10,13,28,38,45,35);
INSERT INTO TB_LAW VALUES(834,6,8,18,35,42,43,3);
INSERT INTO TB_LAW VALUES(833,12,18,30,39,41,42,19);
INSERT INTO TB_LAW VALUES(832,13,14,19,26,40,43,30);
INSERT INTO TB_LAW VALUES(831,3,10,16,19,31,39,9);
INSERT INTO TB_LAW VALUES(830,5,6,16,18,37,38,17);
INSERT INTO TB_LAW VALUES(829,4,5,31,35,43,45,29);
INSERT INTO TB_LAW VALUES(828,4,7,13,29,31,39,18);
INSERT INTO TB_LAW VALUES(827,5,11,12,29,33,44,14);
INSERT INTO TB_LAW VALUES(826,13,16,24,25,33,36,42);
INSERT INTO TB_LAW VALUES(825,8,15,21,31,33,38,42);
INSERT INTO TB_LAW VALUES(824,7,9,24,29,34,38,26);
INSERT INTO TB_LAW VALUES(823,12,18,24,26,39,40,15);
INSERT INTO TB_LAW VALUES(822,9,18,20,24,27,36,12);
INSERT INTO TB_LAW VALUES(821,1,12,13,24,29,44,16);
INSERT INTO TB_LAW VALUES(820,10,21,22,30,35,42,6);
INSERT INTO TB_LAW VALUES(819,16,25,33,38,40,45,15);
INSERT INTO TB_LAW VALUES(818,14,15,25,28,29,30,3);
INSERT INTO TB_LAW VALUES(817,3,9,12,13,25,43,34);
INSERT INTO TB_LAW VALUES(816,12,18,19,29,31,39,7);
INSERT INTO TB_LAW VALUES(815,17,21,25,26,27,36,4);
INSERT INTO TB_LAW VALUES(814,2,21,28,38,42,45,30);
INSERT INTO TB_LAW VALUES(813,11,30,34,35,42,44,27);
INSERT INTO TB_LAW VALUES(812,1,3,12,14,16,43,10);
INSERT INTO TB_LAW VALUES(811,8,11,19,21,36,45,25);
INSERT INTO TB_LAW VALUES(810,5,10,13,21,39,43,11);
INSERT INTO TB_LAW VALUES(809,6,11,15,17,23,40,39);
INSERT INTO TB_LAW VALUES(808,15,21,31,32,41,43,24);
INSERT INTO TB_LAW VALUES(807,6,10,18,25,34,35,33);
INSERT INTO TB_LAW VALUES(806,14,20,23,31,37,38,27);
INSERT INTO TB_LAW VALUES(805,3,12,13,18,31,32,42);
INSERT INTO TB_LAW VALUES(804,1,10,13,26,32,36,9);
INSERT INTO TB_LAW VALUES(803,5,9,14,26,30,43,2);
INSERT INTO TB_LAW VALUES(802,10,11,12,18,24,42,27);
INSERT INTO TB_LAW VALUES(801,17,25,28,37,43,44,2);
INSERT INTO TB_LAW VALUES(800,1,4,10,12,28,45,26);
INSERT INTO TB_LAW VALUES(799,12,17,23,34,42,45,33);
INSERT INTO TB_LAW VALUES(798,2,10,14,22,32,36,41);
INSERT INTO TB_LAW VALUES(797,5,22,31,32,39,45,36);
INSERT INTO TB_LAW VALUES(796,1,21,26,36,40,41,5);
INSERT INTO TB_LAW VALUES(795,3,10,13,26,34,38,36);
INSERT INTO TB_LAW VALUES(794,6,7,18,19,30,38,13);
INSERT INTO TB_LAW VALUES(793,10,15,21,35,38,43,31);
INSERT INTO TB_LAW VALUES(792,2,7,19,25,29,36,16);
INSERT INTO TB_LAW VALUES(791,2,10,12,31,33,42,32);
INSERT INTO TB_LAW VALUES(790,3,8,19,27,30,41,12);
INSERT INTO TB_LAW VALUES(789,2,6,7,12,19,45,38);
INSERT INTO TB_LAW VALUES(788,2,10,11,19,35,39,29);
INSERT INTO TB_LAW VALUES(787,5,6,13,16,27,28,9);
INSERT INTO TB_LAW VALUES(786,12,15,16,20,24,30,38);
INSERT INTO TB_LAW VALUES(785,4,6,15,25,26,33,40);
INSERT INTO TB_LAW VALUES(784,3,10,23,24,31,39,22);
INSERT INTO TB_LAW VALUES(783,14,15,16,17,38,45,36);
INSERT INTO TB_LAW VALUES(782,6,18,31,34,38,45,20);
INSERT INTO TB_LAW VALUES(781,11,16,18,19,24,39,43);
INSERT INTO TB_LAW VALUES(780,15,17,19,21,27,45,16);
INSERT INTO TB_LAW VALUES(779,6,12,19,24,34,41,4);
INSERT INTO TB_LAW VALUES(778,6,21,35,36,37,41,11);
INSERT INTO TB_LAW VALUES(777,6,12,17,21,34,37,18);
INSERT INTO TB_LAW VALUES(776,8,9,18,21,28,40,20);
INSERT INTO TB_LAW VALUES(775,11,12,29,33,38,42,17);
INSERT INTO TB_LAW VALUES(774,12,15,18,28,34,42,9);
INSERT INTO TB_LAW VALUES(773,8,12,19,21,31,35,44);
INSERT INTO TB_LAW VALUES(772,5,6,11,14,21,41,3);
INSERT INTO TB_LAW VALUES(771,6,10,17,18,21,29,30);
INSERT INTO TB_LAW VALUES(770,1,9,12,23,39,43,34);
INSERT INTO TB_LAW VALUES(769,5,7,11,16,41,45,4);
INSERT INTO TB_LAW VALUES(768,7,27,29,30,38,44,4);
INSERT INTO TB_LAW VALUES(767,5,15,20,31,34,42,22);
INSERT INTO TB_LAW VALUES(766,9,30,34,35,39,41,21);
INSERT INTO TB_LAW VALUES(765,1,3,8,12,42,43,33);
INSERT INTO TB_LAW VALUES(764,7,22,24,31,34,36,15);
INSERT INTO TB_LAW VALUES(763,3,8,16,32,34,43,10);
INSERT INTO TB_LAW VALUES(762,1,3,12,21,26,41,16);
INSERT INTO TB_LAW VALUES(761,4,7,11,24,42,45,30);
INSERT INTO TB_LAW VALUES(760,10,22,27,31,42,43,12);
INSERT INTO TB_LAW VALUES(759,9,33,36,40,42,43,32);
INSERT INTO TB_LAW VALUES(758,5,9,12,30,39,43,24);
INSERT INTO TB_LAW VALUES(757,6,7,11,17,33,44,1);
INSERT INTO TB_LAW VALUES(756,10,14,16,18,27,28,4);
INSERT INTO TB_LAW VALUES(755,13,14,26,28,30,36,37);
INSERT INTO TB_LAW VALUES(754,2,8,17,24,29,31,32);
INSERT INTO TB_LAW VALUES(753,2,17,19,24,37,41,3);
INSERT INTO TB_LAW VALUES(752,4,16,20,33,40,43,7);
INSERT INTO TB_LAW VALUES(751,3,4,16,20,28,44,17);
INSERT INTO TB_LAW VALUES(750,1,2,15,19,24,36,12);
INSERT INTO TB_LAW VALUES(749,12,14,24,26,34,45,41);
INSERT INTO TB_LAW VALUES(748,3,10,13,22,31,32,29);
INSERT INTO TB_LAW VALUES(747,7,9,12,14,23,28,17);
INSERT INTO TB_LAW VALUES(746,3,12,33,36,42,45,25);
INSERT INTO TB_LAW VALUES(745,1,2,3,9,12,23,10);
INSERT INTO TB_LAW VALUES(744,10,15,18,21,34,41,43);
INSERT INTO TB_LAW VALUES(743,15,19,21,34,41,44,10);
INSERT INTO TB_LAW VALUES(742,8,10,13,36,37,40,6);
INSERT INTO TB_LAW VALUES(741,5,21,27,34,44,45,16);
INSERT INTO TB_LAW VALUES(740,4,8,9,16,17,19,31);
INSERT INTO TB_LAW VALUES(739,7,22,29,33,34,35,30);
INSERT INTO TB_LAW VALUES(738,23,27,28,38,42,43,36);
INSERT INTO TB_LAW VALUES(737,13,15,18,24,27,41,11);
INSERT INTO TB_LAW VALUES(736,2,11,17,18,21,27,6);
INSERT INTO TB_LAW VALUES(735,5,10,13,27,37,41,4);
INSERT INTO TB_LAW VALUES(734,6,16,37,38,41,45,18);
INSERT INTO TB_LAW VALUES(733,11,24,32,33,35,40,13);



