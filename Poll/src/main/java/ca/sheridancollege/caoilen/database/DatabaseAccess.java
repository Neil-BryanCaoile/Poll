package ca.sheridancollege.caoilen.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.caoilen.beans.Poll;



@Repository
public class DatabaseAccess {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	
	//A method that get return a poll based on its id
	public Poll getPollById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "SELECT * FROM poll WHERE id = :id";
		namedParameters.addValue("id", id);
		
		Poll poll = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Poll>(Poll.class)).get(0);
		System.out.print(poll.getTitle() + " Poll wass selected.");
		return poll;
	}
	
	//A method that update the vote number in the table poll
	//Method will identify which poll
	//Method will identify which answer is for the votes
	//answer1 = votes1
	//answer2 = votes2
	//answer3 = votes3
	public void updateVote (String answer) {
				
		Long updatedVotes = 0L;
		String query =" ";
		//Used to specify parameters
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		
		Long id = 0L;
		String title = null;
		String question = null;
		String answer1 = null;
		String answer2= null;
		String answer3= null;
		Long votes1= null;
		Long votes2= null;
		Long votes3= null;
		
		for(Poll p: getPollInfoList()) {
			title = p.getTitle();
			question = p.getQuestion();
			answer1 = p.getAnswer1();
			answer2 = p.getAnswer2();
			answer3 = p.getAnswer3();
			votes1 =p.getVotes1();
			votes2 =p.getVotes2();
			votes3 =p.getVotes3();
			
			
			if( answer.equals(p.getAnswer1()) ){
				id = p.getId();
				updatedVotes =  getPollById(p.getId()).getVotes1() + 1L;
				query = "INSERT INTO poll(title,question,answer1,answer2,answer3,votes1,votes2,votes3) "
						+ "VALUES (:title,:question,:answer1,:answer2,:answer3,"
						+ ":updatedVotes,:votes2,:votes3)";
				
				namedParameters.addValue("votes2", votes2);
				namedParameters.addValue("votes3", votes3);
				
				break;
				
			}else if( answer.equals(p.getAnswer2()) ) {
				id = p.getId();
				updatedVotes =  getPollById(p.getId()).getVotes2() + 1L;
				query = "INSERT INTO poll(title,question,answer1,answer2,answer3,votes1,votes2,votes3) "
						+ "VALUES (:title,:question,:answer1,:answer2,:answer3,"
						+ ":votes1,:updatedVotes,:votes3)";
				
				namedParameters.addValue("votes1", votes1);
				namedParameters.addValue("votes3", votes3);
				
				break;
			}else if( answer.equals(p.getAnswer3()) ) {
				id = p.getId();
				updatedVotes =  getPollById(p.getId()).getVotes3() + 1L;
				
				query = "INSERT INTO poll(title,question,answer1,answer2,answer3,votes1,votes2,votes3) "
						+ "VALUES (:title,:question,:answer1,:answer2,:answer3,"
						+ ":votes1,:votes2,:updatedVotes)";
				
				namedParameters.addValue("votes1", votes1);
				namedParameters.addValue("votes2", votes2);
				break;
			}
		}
		
		

		
		namedParameters.addValue("updatedVotes", updatedVotes);
		namedParameters.addValue("title", title);
		namedParameters.addValue("question", question);
		namedParameters.addValue("answer1", answer1);
		namedParameters.addValue("answer2", answer2);
		namedParameters.addValue("answer3", answer3);
		
		deletePollById(id);
		
		//Insert Student to DB
		int rowsAffected =jdbc.update(query, namedParameters);
				
				
		//Check to see if inserted
		if(rowsAffected > 0) {
			System.out.println("A student was inserted into DB");	
		}
	}
	
	
	//A method that delete a poll by its id	
	private void deletePollById(Long id) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();
		String query = "DELETE FROM poll WHERE id = :id";
		
		namedParameters.addValue("id", id);
		
		int rowsAffected = jdbc.update(query, namedParameters);
		
		if (rowsAffected > 0)
			System.out.println("Deleted student " + id + " from database");
	}
	
	
	//A method that will return list of polls from the table
	public List<Poll> getPollInfoList() {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();	
		String query = "SELECT * FROM poll";
		
		return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Poll>(Poll.class));
	}
	

}
