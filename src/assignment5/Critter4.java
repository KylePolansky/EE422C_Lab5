/* CRITTERS <Critter4.java>
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

/**
 * A critter that likes to run. Sometimes it tries to run more than it is allowed (ex more than once) and injures itself.
 * It does not care where it runs, it just picks a direction and goes.
 */

public class Critter4 extends Critter{

	private static final int RUN_PERCENT = 80;
	private static final int INJURE_PERCENT = 30;

	@Override
	public String toString() { return "4"; }

	private int dir;

	public Critter4() {
		dir = Critter.getRandomInt(8);
	}
	
	public boolean fight(String critterString) {
		//Only fight if it could die running next turn or if algae.
		return getEnergy() < Params.run_energy_cost * 2 || critterString.equals("@") ? true : false;
	}

	@Override
	public void doTimeStep() {
		int rand1 = Critter.getRandomInt(100), rand2 = Critter.getRandomInt(100);
		look(dir, true);
		//This critter loves to run
		if (rand1 < RUN_PERCENT) {
			run();
		}

		//May injure itself from running to much.
		if (rand2 < INJURE_PERCENT) {
			run();
		}
	}

	private void run() {
		run(dir);
		dir = Critter.getRandomInt(8);
	}

	@Override
	public CritterShape viewShape() {
		return CritterShape.STAR;
	}
}
