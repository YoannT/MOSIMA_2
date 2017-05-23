package sma;

import jade.core.Agent;

import java.util.List;
import java.util.Random;

import sma.actionsBehaviours.LegalActions.LegalAction;

import com.jme3.math.Vector3f;

import env.EnvironmentManager;
import env.jme.Environment;
import env.jme.Situation;

public class AbstractAgent extends Agent implements EnvironmentManager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Environment realEnv;
	
	
	public AbstractAgent() {
		registerO2AInterface(EnvironmentManager.class, this);
	}

	public Vector3f getCurrentPosition() {
		return this.realEnv.getCurrentPosition(getLocalName());
	}
	
	public Vector3f getDestination() {
		return this.realEnv.getDestination(getLocalName());
	}

	public Situation observeAgents() {
		return this.realEnv.observe(getLocalName(), 10);
	}
	
	public void lookAt(LegalAction direction) {
		this.realEnv.lookAt(getLocalName(), direction);
	}

	public boolean moveTo(Vector3f myDestination) {
		return this.realEnv.moveTo(getLocalName(), myDestination);
	}
	
	public boolean cardinalMove(LegalAction direction) {
		return this.realEnv.cardinalMove(getLocalName(), direction);
	}

	public boolean randomMove() {
		return this.realEnv.randomMove(getLocalName());
	}

	public boolean shoot(String target) {
		return this.realEnv.shoot(getLocalName(), target);
	}
	
	public void addSituations(Situation s){
	}
	
	public List<Situation> getSituations(){
		return null;
	}
	
	public void randomAction(String target) {
		int randint = new Random().nextInt(LegalAction.values().length);
		LegalAction[] actions = LegalAction.values();
		LegalAction action = actions[randint];
		System.out.println(getLocalName()+"'s action :"+action);
		if (randint==0) {
			shoot(target);
		}
		else if (randint < 9) {
			cardinalMove(action);
		}
		else {
			lookAt(action);
		}
	}
	
	
	public int getLife(){
		return this.realEnv.getLife("Player1");
	}
	
	public int getEnemyLife(){
		return this.realEnv.getLife("Player2");
	}

	/**
	 * Deploy an agent tagged as a player
	 */
	public void deployAgent(Environment env) {
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "player");
	}

	/**
	 * Deploy an agent tagged as an enemy
	 */
	public void deployEnemy(Environment env) {
		this.realEnv = env;
		this.realEnv.deployAgent(getLocalName(), "enemy");
	}

	protected void setup() {
		super.setup();
	}
}
