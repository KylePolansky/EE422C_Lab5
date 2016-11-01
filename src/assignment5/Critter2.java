/* CRITTERS <Critter2.java>
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
 * The Jock critter is a more athletic critter. Since it is 
 * healthier than the average critter, it runs everywhere it goes. It also fights everything, as it 
 * believes it is better than everything. However, it does not fight other jocks. It also reproduces a lot more often than the average critter
 * as jocks tend to partake in more inappropriate acts than the average critter and tends to not
 * be as smart about it. Since it is not the sharpest tool in the shed, it also can't seem to figure 
 * out how to move diagonally.It is a critter that thinks itself as so perfect that evolution isn't necessary
 */
public class Critter2 extends Critter{

	private int dir;
	public String toString() { return "2"; }
	public Critter2() {
		dir = ((Critter.getRandomInt(8) / 2) * 2);//cannot move diagonally
	}
	public boolean fight(String not_used) {
		if(!not_used.equals("2"))
		{return true;}
		return false;
		
	}

	@Override
	public void doTimeStep() {
		/* run two step forward */
		run(dir);
		
		if (getEnergy() > 2*Params.min_reproduce_energy) {
			Critter2 child = new Critter2();
			
			reproduce(child, Critter.getRandomInt(8));
		}
		
		dir = ((Critter.getRandomInt(8) / 2) * 2);//cannot move diagonally
	}
	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}

}
