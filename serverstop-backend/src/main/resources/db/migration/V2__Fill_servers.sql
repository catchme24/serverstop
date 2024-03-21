ALTER TABLE server
ADD status varchar(255);

INSERT INTO server (id, created_at, server_start, domain, server_rate, chronicle, status) VALUES
(600, '2023-08-02T13:15:32', '2023-08-24T18:00:00', 'L2EVE.COM', 'x1', 'Interlude', 'normal'),
(601, '2023-08-02T13:15:32', '2023-08-27T18:00:00', 'SCRYDE.NET', 'x5', 'Interlude', 'vip');