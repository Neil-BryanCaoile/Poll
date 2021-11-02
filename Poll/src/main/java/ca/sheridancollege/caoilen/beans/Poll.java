package ca.sheridancollege.caoilen.beans;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Poll {
	//Fields
	private Long id;
	private String title;
	private String question;
	
	
	//Selection of answers
	private String answer1;
	private String answer2;
	private String answer3;

	//Votes of each answers
	private Long votes1;
	private Long votes2;
	private Long votes3;

	//Date of the current 
	private Date date;
	
	

}

