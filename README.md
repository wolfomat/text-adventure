# text-adventure
Repository for Spring Boot based Text Adventure Toolkit (Player, Editor)

!!!! Content will be added. This is currently just a initial commit !!!!

There are many things missing.
If you are curious, install a mariadb-database, using currently version 10.4 and create following:

# SQL SCRIPT
    -- COIN SERVICE
    -- DB Anlegen : ok
    CREATE DATABASE quest_data
        CHARACTER SET = 'utf8'
        COLLATE = 'utf8_general_ci';

    -- USER erstellen : ok
    CREATE USER 'questuser'@'localhost';

    -- PRIVLIEGIEN : ok
    GRANT ALL PRIVILEGES ON quest_data.* To 'questuser'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL ON quest_data.* TO 'questuser'@'localhost';
    FLUSH PRIVILEGES;

# END SQL SCRIPT

Editor will be available at:
http://localhost:8080/index.xhtml

Player currently not tested / updated!

Good luck - official and new content will be added.
