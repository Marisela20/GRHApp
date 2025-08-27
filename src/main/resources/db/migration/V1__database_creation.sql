CREATE TABLE classification (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) DEFAULT NULL,
  total_points int(11) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE conclusions (
  id int(11) NOT NULL AUTO_INCREMENT,
  classification_id int(11) NOT NULL,
  description varchar(500) DEFAULT NULL,
  min_value int(11) DEFAULT NULL,
  max_value int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (classification_id) REFERENCES classification (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE role (
  name varchar(255) NOT NULL,
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE level_position (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE economic_area (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE organization (
  name varchar(255) NOT NULL,
  address varchar(255) DEFAULT NULL,
  phone varchar(255) DEFAULT NULL,
  creation_date varchar(11) DEFAULT NULL,
  economic_area_id int(11) NOT NULL,
  economic_activity varchar(255) DEFAULT NULL,
  quantity_employees int (11) DEFAULT NULL,
  worker varchar(255) DEFAULT NULL,
  worker_position varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  PRIMARY KEY (name),
  FOREIGN KEY (economic_area_id) REFERENCES economic_area (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user (
  username varchar(255) NOT NULL,
  name varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  organization_id varchar(255) DEFAULT NULL,
  role_id varchar(255) DEFAULT NULL,
  level_position_id int(11) DEFAULT NULL,
  first_time bit(1) DEFAULT 0,
  worker_position varchar(255) DEFAULT NULL,
  accept_policy bit(1) DEFAULT 0,
  PRIMARY KEY (username),
  FOREIGN KEY (organization_id) REFERENCES organization (name),
  FOREIGN KEY (role_id) REFERENCES role (name),
  FOREIGN KEY (level_position_id) REFERENCES level_position (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



CREATE TABLE survey (
  id int(11) NOT NULL AUTO_INCREMENT,
  completed_status bit(1) DEFAULT NULL,
  date datetime DEFAULT NULL,
  user_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES user (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE survey_item (
  id int(11) NOT NULL AUTO_INCREMENT,
  description varchar(255) DEFAULT NULL,
  classification_id int(11) DEFAULT NULL,
  father_id int(11) DEFAULT NULL,
  is_question bit(1) DEFAULT NULL,
  is_sublevel bit(1) DEFAULT NULL,
  section int(11) DEFAULT NULL,
  score int(11) DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (classification_id) REFERENCES classification (id),
  FOREIGN KEY (father_id) REFERENCES survey_item (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE survey_detail (
  answer_id int(11),
  question_id int(11) NOT NULL,
  survey_id int(11) NOT NULL,
  sub_answer_id int(11),
  sub_question_id int(11),
  PRIMARY KEY (survey_id,question_id,sub_question_id),
  FOREIGN KEY (sub_question_id) REFERENCES survey_item (id),
  FOREIGN KEY (answer_id) REFERENCES survey_item (id),
  FOREIGN KEY (sub_answer_id) REFERENCES survey_item (id),
  FOREIGN KEY (survey_id) REFERENCES survey (id),
  FOREIGN KEY (question_id) REFERENCES survey_item (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE survey_item_excluded (
  answer_id int(11) NOT NULL,
  question_id int(11) NOT NULL,
  classification_id int(11) DEFAULT NULL,
  PRIMARY KEY (answer_id,question_id),
  FOREIGN KEY (classification_id) REFERENCES classification (id),
  FOREIGN KEY (answer_id) REFERENCES survey_item (id),
  FOREIGN KEY (question_id) REFERENCES survey_item (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;