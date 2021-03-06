package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionUntargetableBattlecry;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class DarkscaleHealer extends Minion implements MinionUntargetableBattlecry {

    public DarkscaleHealer() {
        super();
    }

    /**
     * Battlecry: Heals friendly characters for 2
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
        toRet = toRet.data_.getCurrentPlayerHero().takeHeal((byte)2, PlayerSide.CURRENT_PLAYER, toRet, deckPlayer0, deckPlayer1);
        for (Minion minion : PlayerSide.CURRENT_PLAYER.getPlayer(toRet).getMinions()) {
            toRet = minion.takeHeal((byte)2, PlayerSide.CURRENT_PLAYER, toRet, deckPlayer0, deckPlayer1);
        }
        return toRet;
    }

}
