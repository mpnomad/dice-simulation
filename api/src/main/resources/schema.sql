-- DROP TABLE config_param IF EXISTS;
CREATE TABLE config_param (
    `id` int(20) primary key auto_increment,
    `code` varchar(128),
    `type` varchar(128),
    `value` varchar (128),
    `description` varchar(255)
);

-- DROP TABLE simulation IF EXISTS;
CREATE TABLE simulation (
  `id` int(20) primary key auto_increment,
  `roll_count` int(10),
  `dice_count` tinyint(4),
  `side_count` tinyint(4)
);

-- DROP TABLE roll IF EXISTS;
CREATE TABLE roll (
    `id` int(20) primary key auto_increment,
    `roll_count` int(10),
    `roll_total` int(20),
    `simulation_id` int(20) not null,
    key `fk_roll_simulation_id_ref_idx` (`simulation_id`),
    constraint `fk_simulation_id_ref_idx` foreign key (`simulation_id`) references `simulation` (`id`) on delete no action on update no action
);

-- DROP TABLE roll IF EXISTS;
CREATE TABLE dice (
    `id` int(20) primary key auto_increment,
    `roll_id` int(20) not null,
    `sideCount` int(4),
    `diceCount` int(4),
    `rollValue` int(20) not null,
    key `fk_dice_roll_id_ref_idx` (`roll_id`),
constraint `fk_roll_id_ref_idx` foreign key (`roll_id`) references `roll` (`id`) on delete no action on update no action
);

