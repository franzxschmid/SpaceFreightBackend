
drop table if exists part;

create table part
(
    id       INT AUTO_INCREMENT,
    name     VARCHAR,
    visible  boolean,
    primary key (id)
);



INSERT INTO part(name,  visible)
VALUES ('Beatmunsgerät', true),
       ('Staubsauger',  true),
       ('Fernrohr',  true),
       ('Headset',  true),
       ('Luftpumpe', true),
       ('Kamera',  true),
       ('Recorder',  true),
       ('Lautsprecher',  true),
       ('Musik-Anlage', true),
       ('Video-Recorder',true);
commit;
select * from part;


