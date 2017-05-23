package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

import com.jme3.math.Vector3f;

import env.jme.Situation;

public class FollowIfVisibleBehaviour extends TickerBehaviour{
private static final long serialVersionUID = 1L;
	
	public FollowIfVisibleBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
		System.out.println("modeSuiveur");
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		AbstractAgent ma = (AbstractAgent)this.myAgent;
		Situation sit = ma.observeAgents();
		Vector3f currentpos  = ma.getCurrentPosition();
		Vector3f dest = ma.getDestination();
		
		if(sit.agents.size() > 0){
			ma.moveTo(sit.agents.get(0).getFirst());
			//ma.removeBehaviour(ma.be);
		}else{
			ma.addBehaviour(new CliffhangerBehaviour(ma));
			ma.removeBehaviour(this);
		}
		
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}
}
