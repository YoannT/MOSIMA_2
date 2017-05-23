package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import env.jme.Situation;

public class FollowBehaviour extends TickerBehaviour{
private static final long serialVersionUID = 1L;
	
	public FollowBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		AbstractAgent ma = (AbstractAgent)this.myAgent;

		Vector3f currentpos  = ma.getCurrentPosition();
		Vector3f dest = ma.getDestination();
		
		if (dest==null || approximativeEqualsCoordinates(currentpos, dest)) {
			ma.randomMove();
			String enemy = "";
			if (this.myAgent.getLocalName().equals("Player1")) {
				enemy = "Player2";
			}
			else {
				enemy = "Player1";
			}
			ma.randomAction(enemy);
			
			
		}
		else{
			if(ma.observeAgents().agents.size() > 0){
				ma.moveTo(ma.observeAgents().agents.get(0).getFirst());
			}
			/*System.out.println(ma.getCurrentPosition().toString());
			
			if(ma!=null&&ma.observeAgents()!=null){
				Situation sit = ma.observeAgents();
				if (sit.agents.size()>0){
					for (Tuple2<Vector3f, String> t : sit.agents){
						System.out.println(t.getSecond());
					}
				}
			}*/
		}
		
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}
}
