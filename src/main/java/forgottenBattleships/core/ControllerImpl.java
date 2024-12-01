package forgottenBattleships.core;

import forgottenBattleships.entities.battleship.BaseBattleship;
import forgottenBattleships.entities.battleship.Battleship;
import forgottenBattleships.entities.battleship.PirateBattleship;
import forgottenBattleships.entities.battleship.RoyalBattleship;
import forgottenBattleships.entities.battlezone.BattleZone;
import forgottenBattleships.entities.battlezone.BattleZoneImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static forgottenBattleships.common.ConstantMessages.*;
import static forgottenBattleships.common.ExceptionMessages.*;

public class ControllerImpl implements Controller {

    private Collection<BattleZone> battleZones;

    public ControllerImpl() {
        battleZones = new ArrayList<>();
    }


    @Override
    public String addBattleZone(String battleZoneName, int capacity) {

        BattleZone battleZone = getBattleZoneByName(battleZoneName);

        if (battleZone != null) {
            throw new IllegalArgumentException(BATTLE_ZONE_EXISTS);
        }

        battleZones.add(new BattleZoneImpl(battleZoneName, capacity));
        return String.format(SUCCESSFULLY_ADDED_BATTLE_ZONE, battleZoneName);
    }

    @Override
    public BattleZone getBattleZoneByName(String battleZoneName) {

        BattleZone battleZone = battleZones.stream()
                .filter(b -> b.getName().equals(battleZoneName))
                .findFirst()
                .orElse(null);
        return battleZone;
    }

    @Override
    public String addBattleshipToBattleZone(String battleZoneName, String shipType, String shipName, int health) {

        BattleZone currentBattleZone = getBattleZoneByName(battleZoneName);

        if (currentBattleZone == null) {
            throw new NullPointerException(BATTLE_ZONE_DOES_NOT_EXISTS);
        }

        switch (shipType) {

            case "RoyalBattleship" -> {
                if (getBattleZoneByName(battleZoneName).getBattleshipByName(shipName) != null) {
                    throw new IllegalArgumentException(SHIP_EXISTS);
                }
                getBattleZoneByName(battleZoneName).addBattleship(new RoyalBattleship(shipName, health));
            }
            case "PirateBattleship" -> {
                if (getBattleZoneByName(battleZoneName).getBattleshipByName(shipName) != null) {
                    throw new IllegalArgumentException(SHIP_EXISTS);
                }
                getBattleZoneByName(battleZoneName).addBattleship(new PirateBattleship(shipName, health));
            }

            default -> throw new IllegalArgumentException(INVALID_SHIP_TYPE);
        }
        return String.format(SUCCESSFULLY_ADDED_SHIP, shipType, shipName, battleZoneName);
    }

    @Override
    public String startBattle(String battleZoneName, String attackingShip, String shipUnderAttack) {

        BattleZone currentBattlezone = getBattleZoneByName(battleZoneName);
        Battleship attacker = currentBattlezone.getBattleshipByName(attackingShip);
        Battleship victim = currentBattlezone.getBattleshipByName(shipUnderAttack);

        if (attacker == null || victim == null) {
            throw new IllegalArgumentException(INSUFFICIENT_COUNT);
        }

        if (attacker.getAmmunition() > 0) {
            attacker.attack(victim);
            victim.takeDamage(attacker);
        }

        if (victim.getHealth() <= 0) {
            currentBattlezone.removeBattleShip(victim);
        }

        List<String> nameList = new ArrayList<>();
        currentBattlezone.getShips().stream().forEach(ship -> nameList.add(ship.getName()));

        String names = String.join(", ", nameList);


        return String.format(BATTLE_CONTINUES, battleZoneName) + names;
    }

    @Override
    public String getStatistics() {

        StringBuilder sb = new StringBuilder();

        for (BattleZone battleZone : battleZones) {

            sb.append(String.format(SHIPS_IN_BATTLE_ZONE, battleZone.getName()));

            if (battleZone.getShips().size() < 2){
                Battleship battleship = battleZone.getShips().iterator().next();
                sb.append(System.lineSeparator());
                sb.append(SHIP_WINS.formatted(battleship.getName()));
            }else{
                battleZone.getShips().forEach(ship -> {
                    sb.append(System.lineSeparator());
                    sb.append(SHIP_INFO.formatted(ship.getName(), ship.getHealth(), ship.getAmmunition()));
                });
            }
            sb.append(System.lineSeparator());
        }


        return sb.toString().trim();
    }
}
