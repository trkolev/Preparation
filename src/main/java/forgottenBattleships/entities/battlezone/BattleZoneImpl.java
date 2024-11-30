package forgottenBattleships.entities.battlezone;

import forgottenBattleships.entities.battleship.Battleship;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static forgottenBattleships.common.ExceptionMessages.*;

public class BattleZoneImpl implements BattleZone {

    private String name;
    private int capacity;
    private Collection<Battleship> ships;

    public BattleZoneImpl(String name, int capacity) {
        setName(name);
        setCapacity(capacity);
        this.ships = new ArrayList<>();
    }

    public void setName(String name) {

        if (name == null || name.strip().isBlank()) {
            throw new NullPointerException(BATTLE_ZONE_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getCapacity() {
        return this.capacity;
    }

    @Override
    public void addBattleship(Battleship ship) {

        if(ships.size() >= capacity){
            throw new IllegalArgumentException (NOT_ENOUGH_CAPACITY);
        }

        if(ship.getHealth() <= 0){
            throw new IllegalArgumentException(SHIP_HEALTH_NULL_OR_EMPTY);
        }

        this.ships.add(ship);
    }

    @Override
    public Battleship getBattleshipByName(String battleshipName) {
        return ships.stream()
                .filter(s -> s.getName().equals(battleshipName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeBattleShip(Battleship ship) {
        ships.remove(ship);
    }

    @Override
    public Collection<Battleship> getShips() {
        return this.ships;
    }
}
