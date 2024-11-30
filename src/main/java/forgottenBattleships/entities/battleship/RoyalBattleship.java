package forgottenBattleships.entities.battleship;

public class RoyalBattleship extends BaseBattleship {

private static final int AMMUNITION = 100;
private static final int HIT_STRENGTH = 25;

    public RoyalBattleship(String name, int health) {
        super(name, health, AMMUNITION, HIT_STRENGTH);
    }


}
