package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;
import outils.Chrono;
import sma.AbstractAgent;

import com.jme3.math.Vector3f;

import env.jme.Situation;

public class CliffhangerBehaviour extends TickerBehaviour{
	
private static final long serialVersionUID = 1L;

	Vector3f previousPos;
	long time = System.currentTimeMillis()/ 1000L;
	boolean first = true;
	
	/*Avec ce behaviour l'agent va chercher à accéder au point visible le plus haut */
	
	public CliffhangerBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
		System.out.println("mode escalade");
	}
	
	public static void test(){
		//System.out.println("Test called :)");
	}
	
	public static boolean isNear(float distance){
		return (distance<100) ? true: false;
	}

	@Override
	protected void onTick() {
		
		long courant = System.currentTimeMillis()/ 1000L;

		AbstractAgent ma = (AbstractAgent)this.myAgent;
		
		if(courant-time>3){
			time = courant;
			ma.removeBehaviour(this);
			ma.addBehaviour(new BonnieTylerBehaviour(ma));
		}
		
		Vector3f currentpos  = ma.getCurrentPosition();
		Situation sit = ma.observeAgents();
		Vector3f pointlepluselevé = sit.maxAltitude;
		
		Vector3f dest = ma.getDestination();
		
		/* On sauvegarde la distance parcourue
		 * 
		 * Si on arrive au point avant un certain temps on passe en mode observation
		 * 
		 * Sinon on cherche un nouveau point plus haut aux alentours
		 * 
		 * Si on est arrivés sur le point le plus haut on cherche un nouveau point à aller dans notre liste de points d'intérêts ?
		 * */
		
		if(sit.agents.size() > 0){
			//ma.moveTo(sit.agents.get(0).getFirst());
			ma.removeBehaviour(this);
			ma.addBehaviour(new FollowIfVisibleBehaviour(ma));
			return;
		}
		
		// Si c'est la première fois qu'on arrive on ne vas pas chercher le point le plus haut tout de suite
		if(dest==null||approximativeEqualsCoordinates(dest,currentpos)){
			if(pointlepluselevé!=null && (pointlepluselevé.y>=currentpos.y)){
				ma.moveTo(pointlepluselevé);
			}else{
				//on passe en mode observation
				ma.removeBehaviour(this);
				ma.addBehaviour(new BonnieTylerBehaviour(ma));
	
			}
		}

	}
	
	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}
	
	private boolean approximativeEquals(float a, float b) {
		return b-1.0 <= a && a <= +1.0;
	}
}
