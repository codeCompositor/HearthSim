package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionUntargetableBattlecry;
import com.hearthsim.exception.HSException;
import com.hearthsim.util.tree.HearthTreeNode;

public class BloodsailRaider extends Minion implements MinionUntargetableBattlecry {

    public BloodsailRaider() {
        super();
    }

    /**
     * Battlecry: Gain Attack equal to the Attack of your weapon
     */
    @Override
    public HearthTreeNode useUntargetableBattlecry_core(
        Minion minionPlacementTarget,
        HearthTreeNode boardState,
        Deck deckPlayer0,
        Deck deckPlayer1,
        boolean singleRealizationOnly
    ) throws HSException {
        if (boardState.data_.getCurrentPlayerHero().getWeapon() != null) {
            this.addAttack(boardState.data_.getCurrentPlayerHero().getWeapon().getWeaponDamage());
        }
        return boardState;
    }
}
