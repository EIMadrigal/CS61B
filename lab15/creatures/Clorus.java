package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.HugLifeUtils;
import huglife.Occupant;

import java.awt.Color;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature {

    /** creates clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    /** creates a clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    public String name() {
        return "clorus";
    }

    @Override
    public void move() {
        this.energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        this.energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        this.energy *= 0.5;
        return new Clorus(this.energy);
    }

    @Override
    public void stay() {
        this.energy -= 0.01;
    }

    @Override
    public Color color() {
        return color(34, 0, 231);
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.size() > 0) {
            for (Direction neighbor : neighbors.keySet()) {
                Occupant occupant = neighbors.get(neighbor);
                if (occupant.name().equals("plip")) {
                    Direction moveDir = HugLifeUtils.randomEntry(plips);
                    return new Action(Action.ActionType.ATTACK, moveDir);
                } else if (this.energy >= 1.0) {
                    Direction moveDir = HugLifeUtils.randomEntry(empties);
                    return new Action(Action.ActionType.REPLICATE, moveDir);
                } else {
                    Direction moveDir = HugLifeUtils.randomEntry(empties);
                    return new Action(Action.ActionType.MOVE, moveDir);
                }
            }
        }
        return new Action(Action.ActionType.STAY);
    }
}
