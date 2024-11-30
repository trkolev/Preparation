package forgottenBattleships.entities.battleship;

public class PirateBattleship extends BaseBattleship {

    private static final int AMMUNITION = 80;
    private static final int HIT_STRENGTH = 10;

    public PirateBattleship(String name, int health) {
        super(name, health, AMMUNITION, HIT_STRENGTH);
    }
}
