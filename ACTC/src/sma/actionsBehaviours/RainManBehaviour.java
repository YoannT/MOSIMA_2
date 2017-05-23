package sma.actionsBehaviours;

import jade.core.behaviours.TickerBehaviour;

import java.util.Arrays;
import java.util.List;

import outils.Weka;
import sma.AbstractAgent;
import sma.actionsBehaviours.LegalActions.LegalAction;

import com.jme3.math.Vector3f;

import env.jme.Situation;

public class RainManBehaviour  extends TickerBehaviour{
	
	
	/* Ce comportement tournera toujours en fond de manière à analyser la qualité des points
	 * 
	 * Pendant un tick = On tourne et analyse les différents pdv
	 * 
	 * 
	 */
	
	public int direction;
	public int directionAvecLePointLePlusHaut;
	public Vector3f max;
	public Situation[] situations;
	public float a1 = 1;
	public float a2 = 1;
	public float a3 = 1;
	public float a4 = 1;
	public float a5 = 1;
	public float a6 = 1;
	
	public LegalAction[] directions = {LegalAction.LOOKTO_EAST,
			LegalAction.LOOKTO_NORTHEAST,
			LegalAction.LOOKTO_NORTH,
			LegalAction.LOOKTO_NORTHWEST,
			LegalAction.LOOKTO_WEST,
			LegalAction.LOOKTO_SOUTHWEST,
			LegalAction.LOOKTO_SOUTH,
			LegalAction.LOOKTO_SOUTHEAST
	};

	public RainManBehaviour(final AbstractAgent myagent) {
		super(myagent,1000);
	}

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		situations = new Situation[8];
		AbstractAgent ma = (AbstractAgent)this.myAgent;
		direction=0;
		max=null;
		directionAvecLePointLePlusHaut = 0;
		
		while(direction<=directions.length-1){
			Situation scourante = ma.observeAgents();
			situations[direction]=scourante;
			ma.lookAt(directions[direction]);
			direction+=1;
		}
		
		//ma.addSituations(evalBestSituation(situations));
		ma.addSituations(evalBestSituationWeka(situations));
		
		List<Situation> listSit = ma.getSituations();
		if(listSit!=null){
			//System.out.println(listSit.size());
		}
	}

	public Situation evalBestSituation(Situation[] situations){
		Situation bestSituation = situations[0];
		float valBS = evalSituation(bestSituation);
		for(Situation s : Arrays.copyOfRange(situations, 1, situations.length)){
			if(valBS<evalSituation(s)){
				valBS=evalSituation(s);
				bestSituation=s;
			}
		}
		return bestSituation;
	}
	
	public Situation evalBestSituationWeka(Situation[] situations){
		Situation bestSituation = situations[0];
		double valBS = evalSituationWeka(bestSituation);
		for(Situation s : Arrays.copyOfRange(situations, 1, situations.length)){
			if(valBS<evalSituationWeka(s)){
				valBS=evalSituationWeka(s);
				bestSituation=s;
			}
		}
		return bestSituation;
	}
	
	public float evalSituation(Situation situation){
		/*
		 * 
		 * Il faut trouver une meilleure fonction d'évaluation
		 * Voire apprendre sur les paramètres 
		 */
		if(situation!=null){
			float avgAlt = situation.avgAltitude;
			float fov = situation.fieldOfView;
			float maxDepth = situation.maxDepth;
			float agentAlt = situation.agentAltitude.y;
			float agentx = situation.agentAltitude.x;
			float agentz = situation.agentAltitude.z;
			double consistency = situation.consistency;
			//System.out.println(agentAlt+" "+agentx+" "+agentz);
			return a1*avgAlt+a2*fov+a3*maxDepth+a4*agentAlt;
		}else{
			return Float.MIN_VALUE;
		}
	}
	
	public double evalSituationWeka(Situation situation){
		if(situation!=null){
			Weka w = new Weka();
			float maxDepth = situation.maxDepth;
			double d1 = maxDepth;
			float fov = situation.fieldOfView;
			double d2 = fov;
			float DeltaMoy = situation.agentAltitude.y-situation.avgAltitude;
			double detroit = DeltaMoy;
			float DeltaMax = situation.agentAltitude.y-situation.maxAltitude.y;
			double d4 = DeltaMax;
			String[] tab = {d1+"",d2+"",detroit+"",d4+""}; 
			double val = 0;
			try {
				val = w.isPI(tab);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(val);
			
			return val;
		}else{
			System.out.println("herre");
			return Float.MIN_VALUE;
		}
	}
}
