package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionUntargetableBattlecry;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class SilverHandKnight extends Minion implements MinionUntargetableBattlecry {

    public SilverHandKnight() {
        super();
    }

    /**
     * Battlecry: Summon a Squire
     */
    @Override
    public HearthTreeNode useUntargetableBattlecry_core(
            Minion minionPlacementTarget,
            HearthTreeNode boardState,
            Deck deckPlayer0,
            Deck deckPlayer1,
            boolean singleRealizationOnly
        ) throws HSException {
        HearthTreeNode toRet = boardState;
        if (toRet != null && PlayerSide.CURRENT_PLAYER.getPlayer(toRet).getNumMinions() < 7) {
            Minion mdragon = new Squire();
            toRet = mdragon.summonMinion(PlayerSide.CURRENT_PLAYER, this, boardState, deckPlayer0, deckPlayer1, false, singleRealizationOnly);
        }
        return toRet;
    }

}
