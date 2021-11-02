// Name: Neil Bryan Caoile
// StID: 991590424
// Date: 2021-10-29

package ca.sheridancollege.caoilen.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ca.sheridancollege.caoilen.beans.Poll;
import ca.sheridancollege.caoilen.beans.User;
import ca.sheridancollege.caoilen.database.DatabaseAccess;



@Controller
public class PollController {
	
	@Autowired
	DatabaseAccess da;
	
	@GetMapping("/")
	public String index(Model model) {
				
		//A list that return the list of poll in the poll table
		model.addAttribute("pollList",da.getPollInfoList());
		
		//This object is used to store the id of the poll
		model.addAttribute("user", new User());
		
		return "index";
	}
	
	
	@PostMapping("/selectPoll")
	public String selectPoll(Model model, @ModelAttribute User user) {
		
		
		Poll selectedPoll = da.getPollById(user.getSelectedPollId());
		
		//This objects is use to assign value and print for the selection of answers
		//The value will be store to the object user
		model.addAttribute("selectedPoll",selectedPoll);
		
		//This object will store the answer of the user
		model.addAttribute("user", user);
		
		return "poll";
	}
	
	
	@PostMapping("/viewPoll")
	public String viewPoll(Model model, @ModelAttribute User user) {
		
		//update the vote on the table
		da.updateVote(user.getAnswer());
		
		
		//*I dont know why the user.getSelectedPollId is Null*
		//*Because the id is null I just have to use a loop and determine which poll it is*
		
		
		//A loop that get the poll base on the answer of user 
		Poll selectedPoll = null;
		for(Poll p: da.getPollInfoList()) {
			if( user.getAnswer().equals(p.getAnswer1()) ){
				selectedPoll = p;
				break;
			}else if( user.getAnswer().equals(p.getAnswer2()) ) {
				selectedPoll = p;
				break;
			}else if( user.getAnswer().equals(p.getAnswer3()) ) {
				selectedPoll = p;
				break;
			}
		}
		
		
		//Get the average of all votes
		float sum = (float) (selectedPoll.getVotes1()+selectedPoll.getVotes2() +selectedPoll.getVotes3());//Sum of all votes from the poll user selected	
		int average1 = ((int) (((float)selectedPoll.getVotes1() / sum) * 100.0)) ;
		int average2 = ((int) (((float)selectedPoll.getVotes2() / sum) * 100.0)) ;
		int average3 = ((int) (((float)selectedPoll.getVotes3() / sum) * 100.0)) ;
		
		
		model.addAttribute("selectedPoll",selectedPoll);
		model.addAttribute("average1",average1);
		model.addAttribute("average2",average2);
		model.addAttribute("average3",average3);
		return "view";
	}
	
	

}
