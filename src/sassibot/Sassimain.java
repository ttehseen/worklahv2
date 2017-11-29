/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sassibot;

public class Sassimain {
	
	public Answers answer;
	public String message;
	
	public void StringMain(Answers answer, String message){
		this.answer = answer;
		this.message = message;
	}

	public String getResponse() {
		if (message.contains("why")){
			return (answer.getWhy()); 
		}
		else if (message.contains("how")){
			return (answer.getHow());
		}
		else if (message.contains("what")){
			return (answer.getWhat());
		}
		else {
			return ("Have a good day. But not too good.");
		}
	}
    
}
