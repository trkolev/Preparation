package forgottenBattleships.entities.battleship;

import static forgottenBattleships.common.ExceptionMessages.BATTLE_ZONE_NAME_NULL_OR_EMPTY;
import static forgottenBattleships.common.ExceptionMessages.SHIP_NAME_NULL_OR_EMPTY;

public abstract class BaseBattleship implements Battleship {

    private String name;
    private int health;
    private int ammunition;
    private int hitStrength;

    public BaseBattleship(String name, int health, int ammunition, int hitStrength) {
        setName(name);
        setHealth(health);
        setAmmunition(ammunition);
        setHitStrength(hitStrength);
    }

    public void setName(String name) {

        if (name == null || name.strip().isBlank()) {
            throw new NullPointerException(SHIP_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    public void setHealth(int health) {
        if (health < 0) {
            health = 0;
        }

        this.health = health;
    }

    public void setAmmunition(int ammunition) {
        if (ammunition < 0) {
            ammunition = 0;
        }

        this.ammunition = ammunition;
    }

    public void setHitStrength(int hitStrength) {
        this.hitStrength = hitStrength;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getAmmunition() {
        return ammunition;
    }

    @Override
    public int getHitStrength() {
        return hitStrength;
    }

    @Override
    public void takeDamage(Battleship battleship) {
       this.setHealth(this.health - battleship.getHitStrength());
    }

    @Override
    public void attack(Battleship battleship) {
        this.setAmmunition(this.ammunition - this.hitStrength);
    }
}
