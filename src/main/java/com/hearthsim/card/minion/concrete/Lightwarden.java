package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.MinionHealedInterface;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class Lightwarden extends Minion implements MinionHealedInterface {

    public Lightwarden() {
        super();
    }

    @Override
    public HearthTreeNode minionHealedEvent(
            PlayerSide thisMinionPlayerSide,
            PlayerSide healedMinionPlayerSide,
            Minion healedMinion,
            HearthTreeNode boardState,
            Deck deckPlayer0,
            Deck deckPlayer1) {
        this.addAttack((byte)2);
        return boardState;
    }
}
