package sma.actionsBehaviours;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jade.core.behaviours.TickerBehaviour;
import sma.AbstractAgent;

import com.jme3.math.Vector3f;

import env.jme.Situation;

public class RandomWalkBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean aecrit;

	public RandomWalkBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
		aecrit = false;
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
			((AbstractAgent)this.myAgent).randomAction(enemy);
		}

		if(ma.getEnemyLife()==0&&!aecrit){
			Situation sit = ma.observeAgents();
			aecrit = true;
			try(FileWriter fw = new FileWriter("apprentissage.csv", true);
					BufferedWriter bw = new BufferedWriter(fw);
					PrintWriter out = new PrintWriter(bw))
					{
				float deltaMax = sit.agentAltitude.y-sit.maxAltitude.y;
				float deltaMoy = sit.agentAltitude.y-sit.avgAltitude;

				String val = "Victoire,"+deltaMoy+","+sit.maxDepth+","+sit.fieldOfView+","+deltaMax+","+sit.consistency;
				out.println(val);
					} catch (IOException e) {
					}
		}

	}

	private boolean approximativeEqualsCoordinates(Vector3f a, Vector3f b) {
		return approximativeEquals(a.x, b.x) && approximativeEquals(a.z, b.z);
	}

	private boolean approximativeEquals(float a, float b) {
		return b-2.5 <= a && a <= b+2.5;
	}

}
