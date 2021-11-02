CREATE TABLE poll(

	id LONG PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(255),
	question VARCHAR(255),
	answer1 VARCHAR(255),
	answer2 VARCHAR(255),
	answer3 VARCHAR(255),
	votes1 LONG,
	votes2 LONG,
	votes3 LONG
);

