/* CRITTERS <Critter3.java>
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
 * A rock critter that sits in one position and doesn't do anything unless it runs out of energy
 * (ex. gets eroded), or another critter throws the rock away off the map during a fight.
 */
public class Critter3 extends Critter{

	@Override
	public String toString() { return "3"; }

	public boolean fight(String not_used) {
		return false;
	}

	@Override
	public void doTimeStep() {
		//Do nothing
	}

	@Override
	public CritterShape viewShape() {
		// TODO Auto-generated method stub
		return null;
	}
}
