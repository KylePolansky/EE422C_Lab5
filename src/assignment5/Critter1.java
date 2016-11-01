/* CRITTERS <Critter1.java>
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Brian Madina>
 * <bjm3348>
 * <16460>
 * <Kyle Polansky>
 * <KPP446>
 * <16480>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5;

/*
 * a fat critter that moves very seldom. It only walks and fights when it's hungry
 *  (energy level is below 100). It may walk when it isn't hungry in case it gets bored.
 *  It is also too lazy to reproduce and only does so when energy level is
 *  above 200. Also, it is too lazy to evolve.
 */

public class Critter1 extends Critter{
	private static final int REPRODUCE_ENERGY = 100;
	private static final int HUNGRY_ENERGY = 200;
	private static final int RANDOM_MOVE_PERCENT = 50;


	@Override
	public String toString() { return "1"; }

	private int dir;
	
	public Critter1() {
	
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String not_used) { 
		if(getEnergy() < 100 || not_used.equals("@"))//only fight if it's algae or hungry
		{return true;}
		else{return false;}
		
	}

	@Override
	public void doTimeStep() {
		/* take one step forward only if hungry*/
		if(getEnergy() <= HUNGRY_ENERGY)
		walk(dir);
		else{
			if(Critter.getRandomInt(100) < RANDOM_MOVE_PERCENT)
			{
				walk(dir);
			}
		}
		if (getEnergy() > REPRODUCE_ENERGY) {
			Critter1 child = new Critter1();
			reproduce(child, Critter.getRandomInt(8));
		}
				
		dir = Critter.getRandomInt(8);
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}

}


