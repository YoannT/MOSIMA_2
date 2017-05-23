package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import org.jpl7.Query;

import sma.AbstractAgent;

import com.jme3.math.Vector3f;

import dataStructures.tuple.Tuple2;
import env.jme.Situation;

public class NotSoLuckyLukeBehaviour extends TickerBehaviour{

	private static final long serialVersionUID = 1L;
	File f = new File ("res.csv");

	public NotSoLuckyLukeBehaviour(final AbstractAgent myagent) {
		// TODO Auto-generated constructor stub
		super(myagent, 200);
	}

	public static void test(){
		//System.out.println("Test called :)");
	}

	public static boolean isNear(float distance){
		return (distance<35) ? true: false;
	}

	@Override
	protected void onTick() {

		AbstractAgent ma = (AbstractAgent)this.myAgent;
		Situation sit = ma.observeAgents();
		Vector3f currentpos  = ma.getCurrentPosition();

		if(sit.agents.size() > 0){
			/*on injecte les deux vector3 dans prolog, si on est inferieur à une distance on tire*/
			Tuple2<Vector3f, String> oa = sit.agents.get(0);
			if (oa!=null){
				Vector3f otheragentpos = oa.getFirst();
				//mettre le query dans l'agent
				String query = "consult('./ressources/prolog/agent/shoot.pl')";
				query = query+" ?: "+Query.hasSolution(query);

				float distance = otheragentpos.distance(currentpos);

				query="shoot("+distance+")";
				//System.out.println(query+" ?: "+Query.hasSolution(query));

				boolean res = Query.hasSolution(query);

				if (res){
					//System.out.println("Feu ! ");
					if(ma.getEnemyLife()==0){
						//System.out.println("déjà mort");
					}else{
						if(ma.shoot(oa.getSecond())){
							System.out.println("Touché !");
							if(ma.getEnemyLife()==0){
								try(FileWriter fw = new FileWriter("apprentissage.csv", true);
										BufferedWriter bw = new BufferedWriter(fw);
										PrintWriter out = new PrintWriter(bw))
										{
									float deltaMax = sit.maxAltitude.y - sit.agentAltitude.y;
									float deltaMoy = sit.avgAltitude - sit.agentAltitude.y;

									String val = "Victoire,"+deltaMoy+","+sit.maxDepth+","+sit.fieldOfView+","+deltaMax+","+sit.consistency;
									out.println(val);
										} catch (IOException e) {
										}
							}
						}
					}
				}
			}
		}

	}
}
