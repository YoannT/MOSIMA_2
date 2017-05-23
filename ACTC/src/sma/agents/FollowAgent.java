package sma.agents;

import sma.AbstractAgent;
import sma.actionsBehaviours.FollowBehaviour;
import sma.actionsBehaviours.NotSoLuckyLukeBehaviour;
import env.jme.Environment;
import env.jme.Situation;

public class FollowAgent extends AbstractAgent {
	/**
	 *
	 */
	private static final long serialVersionUID = 7545160765928961044L;
	
	/**
	 * True to create a friend, false otherwise 
	 */
	public boolean friendorFoe;
	
	protected void setup(){
		super.setup();
		
		//get the parameters given into the object[]. In the current case, the environment where the agent will evolve
		final Object[] args = getArguments();
		if(args[0]!=null && args[1]!=null){
			
			this.friendorFoe = ((boolean)args[1]);
			
			if (friendorFoe) {
				deployAgent((Environment) args[0]);
			} else {
				deployEnemy((Environment) args[0]);
			}
			
		}else{
			System.err.println("Malfunction during parameter's loading of agent"+ this.getClass().getName());
			System.exit(-1);
		}

		//this.doWait(6000);

		addBehaviour(new FollowBehaviour(this));
		addBehaviour(new NotSoLuckyLukeBehaviour(this));
		System.out.println("the player "+this.getLocalName()+ " is started. Tag (0==enemy): " + friendorFoe);
		
	}
	
	public void addSituations(Situation s){
	}
	
}
