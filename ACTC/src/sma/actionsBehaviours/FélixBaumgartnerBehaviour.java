package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;
import outils.Chrono;
import sma.AbstractAgent;

import com.jme3.math.Vector3f;

public class FélixBaumgartnerBehaviour extends TickerBehaviour{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	float anciennePosition = 0;
	boolean landed = false;
	int cpt = 0;
	
	public FélixBaumgartnerBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 100);
	}

	@Override
	protected void onTick() {
		AbstractAgent ma = (AbstractAgent)myAgent;
		Vector3f positionCourante = ((AbstractAgent)myAgent).getCurrentPosition();

		//if(positionCourante!=null){
			if(positionCourante.y<-1){
				if(anciennePosition<=(positionCourante.y)&&(ma.getDestination()==null||cpt>100)){
				//if(anciennePosition<=(positionCourante.y)){
//					try {
//						Thread.sleep(5000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					
					ma.removeBehaviour(this);
					ma.addBehaviour(new RainManBehaviour(ma));
					ma.addBehaviour(new BonnieTylerBehaviour(ma));
					//ma.addBehaviour(new CliffhangerBehaviour(ma));
					//ma.addBehaviour(new FollowIfVisibleBehaviour(ma));
					ma.addBehaviour(new NotSoLuckyLukeBehaviour(ma));
				}else{
					anciennePosition=positionCourante.y;
					cpt++;
				}
			}
		//}
	}
}
