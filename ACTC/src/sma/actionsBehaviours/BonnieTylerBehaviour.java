package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;
import sma.actionsBehaviours.LegalActions.LegalAction;

import com.jme3.math.Vector3f;

import env.jme.Situation;

public class BonnieTylerBehaviour extends TickerBehaviour{
private static final long serialVersionUID = 1L;
	
	public int direction = 0;
	public int directionAvecLePointLePlusHaut;
	public Vector3f max = null;
	int dirClif;
	
	public LegalAction[] directions = {LegalAction.LOOKTO_EAST,
			LegalAction.LOOKTO_NORTHEAST,
			LegalAction.LOOKTO_NORTH,
			LegalAction.LOOKTO_NORTHWEST,
			LegalAction.LOOKTO_WEST,
			LegalAction.LOOKTO_SOUTHWEST,
			LegalAction.LOOKTO_SOUTH,
			LegalAction.LOOKTO_SOUTHEAST
	};
	
	public BonnieTylerBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 100);
		System.out.println("Mode toupie");
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		AbstractAgent ma = (AbstractAgent)this.myAgent;
		Situation sit;
		Vector3f pos = ma.getCurrentPosition();
		float max = pos.y;
		dirClif = -1;
		
		for (int i=0; i<8; i++){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ma.lookAt(directions[i]);
			sit = ma.observeAgents();
			Vector3f pointlepluselevé = sit.maxAltitude;
			if(sit.agents.size() != 0){
				ma.removeBehaviour(this);
				ma.addBehaviour(new FollowIfVisibleBehaviour(ma));
				return;
			}
			if(pointlepluselevé!=null&&pointlepluselevé.y>pos.y&&pointlepluselevé.y>max&!approximativeEqualsCoordinates(pos,pointlepluselevé)){
				max = pointlepluselevé.y;
				dirClif = i;
			}
		}
		
		if(dirClif>=0){
			ma.removeBehaviour(this);
			ma.lookAt(directions[dirClif]);
			ma.addBehaviour(new CliffhangerBehaviour(ma));
		}else{
			System.out.println("On est au sommet ?");
			ma.randomMove();
			ma.addBehaviour(new CliffhangerBehaviour(ma));
			ma.removeBehaviour(this);
		}
		
	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-3.5 <= a && a <= b+3.5;
	}
}
