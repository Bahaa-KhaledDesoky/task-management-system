INSERT INTO `taskmanagement`.`roles`(`id`,`description`,`rolename`)VALUES(1,"all action","ADMIN");
INSERT INTO `taskmanagement`.`roles`(`id`,`description`,`rolename`)VALUES(2,"all projects action","ProjectsManager");
INSERT INTO `taskmanagement`.`roles`(`id`,`description`,`rolename`)VALUES(3,"all task action","TasksManager");
INSERT INTO `taskmanagement`.`roles`(`id`,`description`,`rolename`)VALUES(4,"some action on user tasks ","USER");
INSERT INTO `taskmanagement`.`users` (`id`, `password`, `username`, `phone`) VALUES ('1', 'admin', 'admin', '0000000000000');
INSERT INTO `taskmanagement`.`users_roles`(`user_id`,`role_id`)VALUES(1,1);