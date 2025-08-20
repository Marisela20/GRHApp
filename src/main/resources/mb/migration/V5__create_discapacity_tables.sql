-- Encuesta principal
CREATE TABLE IF NOT EXISTS discapacity_survey (
                                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                                  name VARCHAR(255) NOT NULL,
                                                  description TEXT,
                                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Preguntas por encuesta
CREATE TABLE IF NOT EXISTS discapacity_question (
                                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                                    survey_id INT NOT NULL,
                                                    question_text TEXT NOT NULL,
                                                    question_type VARCHAR(50),
                                                    section INT DEFAULT 1,
                                                    order_num INT DEFAULT 0,
                                                    FOREIGN KEY (survey_id) REFERENCES discapacity_survey(id) ON DELETE CASCADE
);

-- Opciones de respuesta (si aplica)
CREATE TABLE IF NOT EXISTS discapacity_answer_option (
                                                         id INT PRIMARY KEY AUTO_INCREMENT,
                                                         question_id INT NOT NULL,
                                                         text TEXT NOT NULL,
                                                         score INT DEFAULT 0,
                                                         FOREIGN KEY (question_id) REFERENCES discapacity_question(id) ON DELETE CASCADE
);

-- Usuarios que responden
CREATE TABLE IF NOT EXISTS survey_user (
                                           id INT PRIMARY KEY AUTO_INCREMENT,
                                           name VARCHAR(255),
                                           email VARCHAR(255),
                                           organization VARCHAR(255)
);

-- Respuestas registradas
CREATE TABLE IF NOT EXISTS discapacity_response (
                                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                                    survey_id INT NOT NULL,
                                                    question_id INT NOT NULL,
                                                    answer_option_id INT,
                                                    free_text TEXT,
                                                    user_id INT,
                                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                                    FOREIGN KEY (survey_id) REFERENCES discapacity_survey(id) ON DELETE CASCADE,
                                                    FOREIGN KEY (question_id) REFERENCES discapacity_question(id) ON DELETE CASCADE,
                                                    FOREIGN KEY (answer_option_id) REFERENCES discapacity_answer_option(id) ON DELETE SET NULL,
                                                    FOREIGN KEY (user_id) REFERENCES survey_user(id) ON DELETE SET NULL
);
