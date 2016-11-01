package assignment5;

import java.util.ArrayList;
import java.util.List;

public abstract class Critter {
	/* NEW FOR PROJECT 5 */
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 
	
	private static String myPackage;
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	// Gets the package name.  This assumes that Critter and its subclasses are all in the same package.
	static {
		myPackage = Critter.class.getPackage().toString().split(" ")[1];
	}
	
	protected String look(int direction, boolean steps) {return "";}
	
	/* rest is unchanged from Project 4 */
	
	
	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }
	
	private int energy = 0;
	protected int getEnergy() { return energy; }

	private int x_coord;
	private int y_coord;
	private int lastMoved = 0; //Critter timeStep
	private static int timeStep = 1; //world timeStep
	private static boolean areFighting = false; //If critters are currently in the fighting stage

	/**
	 * Move critter in the direction specified the amount of steps, using wrap around math
	 * @param direction the direction to move
	 * @param amount the number of steps to move
	 */
	private void move(int direction, int amount) {
		int newX = x_coord, newY = y_coord;

		//Don't move if previously moved in this step
		if (this.lastMoved == timeStep) {
			return;
		}

		//Calculate new direction
		switch (direction) {
			case 0:
				newX = (x_coord + amount) % Params.world_width;
				break;
			case 1:
				newX = (x_coord + amount) % Params.world_width;
				newY = (y_coord + amount) % Params.world_height;
				break;
			case 2:
				newY = (y_coord + amount) % Params.world_height;
				break;
			case 3:
				newX = (x_coord - amount) % Params.world_width;
				newY = (y_coord + amount) % Params.world_height;
				break;
			case 4:
				newX = (x_coord - amount) % Params.world_width;
				break;
			case 5:
				newX = (x_coord - amount) % Params.world_width;
				newY = (y_coord - amount) % Params.world_height;
				break;
			case 6:
				newY = (y_coord - amount) % Params.world_height;
				break;
			case 7:
				newX = (x_coord + amount) % Params.world_width;
				newY = (y_coord - amount) % Params.world_height;
				break;
			default:
				//Something went wrong
				break;
		}

		//If fighting, cannot move to a spot otherwise occupied
		if (areFighting) {
			for (Critter c : population) {
				if (c.x_coord == newX && c.y_coord == newY) {
					return;
				}
			}
		}

		//Update critter
		this.x_coord = newX;
		this.y_coord = newY;
		this.lastMoved = timeStep;
	}

	/**
	 * Move 1 step in direction
	 * @param direction the direction to move
	 */
	protected final void walk(int direction) {
		move(direction, 1);
		energy -= Params.walk_energy_cost;
	}

	/**
	 * Move 2 steps in direction
	 * @param direction the direction to move
	 */
	protected final void run(int direction) {
		move(direction, 2);
		energy -= Params.run_energy_cost;
	}

	/**
	 * Reproduce if there is enough energy
	 * @param offspring the new baby critter
	 * @param direction the direction that the baby should move
	 */
	protected final void reproduce(Critter offspring, int direction) {
		//Ensure there is enough energy
		if(this.energy < Params.min_reproduce_energy) {
			return;
		}

		//Calculate new energy values
		int originalEnergy = this.energy;
		offspring.energy = originalEnergy / 2;
		this.energy = originalEnergy / 2 + originalEnergy % 2;

		//Add offspring one position adjacent from parent
		offspring.x_coord = this.x_coord;
		offspring.y_coord = this.y_coord;
		offspring.move(direction, 1);

		//Add to babies list to be added at the end of the world cycle
		babies.add(offspring);
	}

	/**
	 * To be implemented by Critters, what happens during each timeStep.
	 */
	public abstract void doTimeStep();

	/**
	 * To be implemented by Critters, how to react in a fight.
	 * @param oponent the opponent you are fighting
	 * @return whether or not to fight
	 */
	public abstract boolean fight(String oponent);

	/**
	 * create and initialize a Critter subclass.
	 * critter_class_name must be the unqualified name of a concrete subclass of Critter, if not,
	 * an InvalidCritterException must be thrown.
	 * (Java weirdness: Exception throwing does not work properly if the parameter has lower-case instead of
	 * upper. For example, if craig is supplied instead of Craig, an error is thrown instead of
	 * an Exception.)
	 * @param critter_class_name must be the fully qualified name
	 * @throws InvalidCritterException if the critter_class_name is not found
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {

		try {
			//Create new critter using reflection
			Class cls;
			try {
				cls = Class.forName(critter_class_name);
			} catch (Exception e) {
				cls = Class.forName(myPackage + "." + critter_class_name);
			}
			Object o = cls.getConstructor().newInstance();
			Critter c = (Critter) o;

			//Set default values
			int xPos = getRandomInt(Params.world_width);
			int yPos = getRandomInt(Params.world_height);
			int initEnergy = Params.start_energy;

			c.x_coord = xPos;
			c.y_coord = yPos;
			c.energy = initEnergy;

			//Add to arrary
			population.add(c);
		}
		catch (java.lang.NoClassDefFoundError e) {
			throw new InvalidCritterException(new String());
		}
		catch (Exception e) {
			throw new InvalidCritterException(new String());
		}
	}

	/**
	 * Gets a list of critters of a specific type.
	 * @param critter_class_name What kind of Critter is to be listed.  Unqualified class name.
	 * @return List of Critters.
	 * @throws InvalidCritterException if the critter_class_name is not found
	 */
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		try {
			Class cls;
			try {
				cls = Class.forName(critter_class_name);
			} catch (Exception e) {
				cls = Class.forName(myPackage + "." + critter_class_name);
			}

			for (Critter c : population) {
				if (cls.isInstance(c)) {
					result.add(c);
				}
			}
		}
		catch (java.lang.NoClassDefFoundError e) {
			throw new InvalidCritterException(new String());
		}
		catch (Exception e) {
			throw new InvalidCritterException(new String());
		}
		return result;
	}

	/**
	 * Prints out how many Critters of each type there are on the board.
	 * @param critters List of Critters.
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	/* the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here.
	 *
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setX_coord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setY_coord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}

		protected int getX_coord() {
			return super.x_coord;
		}

		protected int getY_coord() {
			return super.y_coord;
		}


		/*
		 * This method getPopulation has to be modified by you if you are not using the population
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.
		 */
		protected static List<Critter> getPopulation() {
			return population;
		}

		/*
		 * This method getBabies has to be modified by you if you are not using the babies
		 * ArrayList that has been provided in the starter code.  In any case, it has to be
		 * implemented for grading tests to work.  Babies should be added to the general population
		 * at either the beginning OR the end of every timestep.
		 */
		protected static List<Critter> getBabies() {
			return babies;
		}
	}

	/**
	 * Clear the world of all critters, dead and alive
	 */
	public static void clearWorld() {
		population = new ArrayList <Critter>();
	}

	/**
	 * Removes all the dead critters in the worldmo
	 */
	private static void removeDeadCritters() {
		for (int i = 0; i < population.size(); i++) {
			if (population.get(i).energy <= 0) {
				population.remove(i);
				i--;
			}
		}
	}

	/**
	 * Gets pairs of critters that are at the same position. This is not a very efficient method, but it gets the job done.
	 * @param startCritter the index in the population to start with. Used to bypass previously check Critters
	 * @return an ArrayList of 2 critter positions that are at the same position, or null if there are no conflicts
	 */
	private static ArrayList<Integer> getEncounter(int startCritter) {
		for (int firstCritterIdx = startCritter; firstCritterIdx < population.size(); firstCritterIdx ++) {
			int firstCritterX = population.get(firstCritterIdx).x_coord;
			int firstCritterY = population.get(firstCritterIdx).y_coord;
			for (int secondCritterIdx = firstCritterIdx + 1; secondCritterIdx < population.size(); secondCritterIdx++) {
				int secondCritterX = population.get(secondCritterIdx).x_coord;
				int secondCritterY = population.get(secondCritterIdx).y_coord;

				if (firstCritterX == secondCritterX && firstCritterY == secondCritterY) {
					//Conflict
					ArrayList<Integer> al = new ArrayList<>();
					al.add(firstCritterIdx);
					al.add(secondCritterIdx);
					return al;
				}
			}
		}
		return null;
	}

	/**
	 * Resolve a conflict between 2 critters, usually involving a fight and ending in someone fleeing or dying
	 * @param c1 the first critter
	 * @param c2 the second critter
	 */
	private static void resolveEncounter(Critter c1, Critter c2) {
		boolean aFight = c1.fight(c2.toString());
		boolean bFight = c2.fight(c1.toString());

		//If both alive and in the same position
		if (c1.getEnergy() > 0 && c2.getEnergy() > 0 && c1.x_coord == c2.x_coord && c1.y_coord == c2.y_coord) {
			int randA = 0, randB = 0;
			if (aFight) {
				randA =	getRandomInt(c1.getEnergy());
			}
			if (bFight) {
				randB = getRandomInt(c2.getEnergy());
			}

			if (randA >= randB) {
				//A wins
				c1.energy += c2.getEnergy() / 2;
				c2.energy = 0;
			}
			else {
				//B wins
				c2.energy += c1.getEnergy() / 2;
				c1.energy = 0;
			}
		}
		removeDeadCritters();
	}

	/**
	 * Simulate one world time step
	 */
	public static void worldTimeStep() {
		//Move
		for(int i = 0; i < population.size(); i++)
		{
			population.get(i).doTimeStep();
			population.get(i).energy -= Params.rest_energy_cost;
		}
		removeDeadCritters();

		//Resolve encounters
		areFighting = true;
		int encounterCheck = 0;
		while (encounterCheck < population.size()) {
			ArrayList<Integer> al = getEncounter(encounterCheck);
			if (al == null) {
				break;
			}
			encounterCheck = al.get(0);
			resolveEncounter(population.get(al.get(0)), population.get(al.get(1)));
		}
		areFighting = false;

		//Remove dead critters
		removeDeadCritters();

		//Add babies to the world
		population.addAll(babies);
		babies = new ArrayList<>();

		//Add Algae to world
		for (int i = 0; i < Params.refresh_algae_count; i++) {
			try {
				makeCritter(myPackage + ".Algae");
			}
			catch (InvalidCritterException e) {
				//This shouldn't happen
			}
		}
		timeStep++;
	}

	/**
	 * Print out a grid of the world
	 */
	public static void displayWorld() {
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++)
		{
			System.out.print("-");
		}
		System.out.println("+");
		for(int i = 0; i < Params.world_height; i++)
		{
			System.out.print("|");
			for(int j = 0; j < Params.world_width; j++)
			{
				int printFlag = 0;
				for(int k = 0; k < population.size(); k++)
				{
					if(population.get(k).y_coord == i && population.get(k).x_coord == j)
					{
						System.out.print(population.get(k).toString());
						printFlag = 1;
						break; // breaks out of this one for-loop
					}

				}
				if(printFlag == 0)
				{
					System.out.print(" ");
				}
			}
			System.out.println("|");
		}
		System.out.print("+");
		for(int i = 0; i < Params.world_width; i++)
		{
			System.out.print("-");
		}
		System.out.print("+");
		System.out.println();

	}
}