package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionSummonedInterface;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class Undertaker extends Minion implements MinionSummonedInterface {

    public Undertaker() {
        super();
    }

    /**
     * Whenever a minion with Deathrattle is summoned, gain +1 Attack
     * */
    @Override
    public HearthTreeNode minionSummonEvent(
            PlayerSide thisMinionPlayerSide,
            PlayerSide summonedMinionPlayerSide,
            Minion summonedMinion,
            HearthTreeNode boardState,
            Deck deckPlayer0,
            Deck deckPlayer1) {
        HearthTreeNode toRet = boardState;
        if (toRet != null && summonedMinion.hasDeathrattle() && thisMinionPlayerSide == summonedMinionPlayerSide) {
            this.addAttack((byte)1);
        }
        return toRet;
    }
}
