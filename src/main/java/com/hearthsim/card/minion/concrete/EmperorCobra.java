package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class EmperorCobra extends Minion {

    public EmperorCobra() {
        super();
    }

    @Override
    protected HearthTreeNode attack_core(PlayerSide targetMinionPlayerSide, Minion targetMinion,
            HearthTreeNode boardState, Deck deckPlayer0, Deck deckPlayer1, boolean singleRealizationOnly) throws HSException {

        if (targetMinion instanceof Hero)
            return super.attack_core(targetMinionPlayerSide, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);

        HearthTreeNode toRet = boardState;
        byte origAttack = targetMinion.getTotalAttack();
        toRet = targetMinion.takeDamage((byte)99, PlayerSide.CURRENT_PLAYER, targetMinionPlayerSide,
                toRet, deckPlayer0, deckPlayer1, false, false);
        toRet = this.takeDamage(origAttack, targetMinionPlayerSide, PlayerSide.CURRENT_PLAYER, toRet, deckPlayer0,
                deckPlayer1, false, false);
        if (windFury_ && !hasWindFuryAttacked_)
            hasWindFuryAttacked_ = true;
        else
            hasAttacked_ = true;
        return toRet;
    }
}
