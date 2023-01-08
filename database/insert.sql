#insert.sql for event calendar

#delete existing entries
DELETE FROM event;
DELETE FROM competitor;
DELETE FROM sport;
DELETE FROM venue;

#insert test data for event_calendar
INSERT INTO `sport` (`sport_id`, `name`)
VALUES
(1, 'Football'),
(2, 'Tennis'),
(3, 'Ice Hockey'),
(4, 'Formula 1');

INSERT INTO `competitor`
(`competitor_id`,
`name`,
`_sport_id`)
VALUES
(1, 'SK Sturm Graz', 1),
(2, 'FC Red Bull Salzburg', 1),
(3, 'FC Bayern München', 1),
(4, 'Real Madrid', 1),
(5, 'FC Barcelona', 1),
(6, 'Roger Federer', 2),
(7, 'Dominic Thiem', 2),
(8, 'Rafael Nadal', 2),
(9, 'Novak Djokovic', 2),
(10, 'KAC', 3),
(11, 'Capitals', 3),
(12, 'VSV', 3),
(13, 'Black Wings Linz', 3),
(14, 'Graz 99ers', 3);

INSERT INTO `venue`
(`venue_id`,
`name`)
VALUES
(1, 'Eissportzentrum Klagenfurt'),
(2, 'Steffl Arena'),
(3, 'Stadthalle Villach'),
(4, 'Eissportzentrum Klagenfurt'),
(5, 'LINZ AG Eisarena');

INSERT INTO `event`
(`event_id`,
`_sport_id`,
`date_time`,
`description`,
`_competitor1_id`,
`_competitor2_id`,
`_venue_id`)
VALUES
(1, 3, '2023-01-14 20:00:00', null, 10, 11, 1),
(2, 3, '2023-01-15 20:00:00', null, 12, 13, 3),
(3, 3, '2023-01-22 19:45:00', null, 10, 11, 2),
(4, 3, '2023-01-22 18:00:00', null, 13, 12, 5),
(5, 1, '2023-02-07 21:00:00', null, 3, 2, null),
(6, 1, '2023-02-08 17:00:00', null, 1, 4, null),
(7, 1, '2023-01-29 19:00:00', null, 3, 4, null),
(8, 1, '2023-01-29 19:00:00', null, 1, 2, null),
(9, 2, '2023-01-29 11:00:00', null, 7, 8, null),
(10, 2, '2023-01-27 09:00:00', null, 6, 7, null),
(11, 4, '2023-03-05 16:00:00', 'Bahrain Grand Prix', null, null, null);
