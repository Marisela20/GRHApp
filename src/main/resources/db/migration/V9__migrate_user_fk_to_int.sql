-- 1) Asegura que la tabla `user` tenga un ID entero autoincrement como PK
ALTER TABLE `user`
    ADD COLUMN IF NOT EXISTS `id` INT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY IF NOT EXISTS (`id`);

-- 2) En la tabla survey (esquema grhdatabase), crea una columna temporal INT para el nuevo FK
ALTER TABLE `grhdatabase`.`survey`
    ADD COLUMN IF NOT EXISTS `user_id_int` INT NULL;

-- 3) Rellena user_id_int usando el username guardado actualmente
--    Caso A: si existe la columna snake_case `user_id`
UPDATE `grhdatabase`.`survey` s
    JOIN `user` u ON u.username = s.user_id
SET s.user_id_int = u.id
WHERE s.user_id_int IS NULL
  AND EXISTS (SELECT 1
              FROM information_schema.columns
              WHERE table_schema = 'grhdatabase'
                AND table_name = 'survey'
                AND column_name = 'user_id');

--    Caso B: si existe la columna camelCase `userId`
UPDATE `grhdatabase`.`survey` s
    JOIN `user` u ON u.username = s.userId
SET s.user_id_int = u.id
WHERE s.user_id_int IS NULL
  AND EXISTS (SELECT 1
              FROM information_schema.columns
              WHERE table_schema = 'grhdatabase'
                AND table_name = 'survey'
                AND column_name = 'userId');

-- 4) Vuelve obligatoria la nueva columna
ALTER TABLE `grhdatabase`.`survey`
    MODIFY COLUMN `user_id_int` INT NOT NULL;

-- 5) Elimina la(s) columna(s) vieja(s) de texto si existen
ALTER TABLE `grhdatabase`.`survey`
    DROP COLUMN IF EXISTS `user_id`,
DROP COLUMN IF EXISTS `userId`;

-- 6) Renombra la columna nueva al nombre que tu entidad usa (tu código usa "userId")
ALTER TABLE `grhdatabase`.`survey`
    CHANGE COLUMN `user_id_int` `userId` INT NOT NULL;

-- 7) Crea la FK al nuevo ID entero
ALTER TABLE `grhdatabase`.`survey`
    ADD CONSTRAINT `fk_survey_user_id`
        FOREIGN KEY (`userId`) REFERENCES `user`(`id`);
