package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;

public class Demolisher extends Minion {

    public Demolisher() {
        super();
    }

    @Override
    public HearthTreeNode startTurn(PlayerSide thisMinionPlayerIndex, HearthTreeNode boardModel, Deck deckPlayer0, Deck deckPlayer1) throws HSException {
        HearthTreeNode toRet = boardModel;
        if (thisMinionPlayerIndex == PlayerSide.CURRENT_PLAYER) {
            PlayerModel waitingPlayer = PlayerSide.WAITING_PLAYER.getPlayer(toRet);
            Minion targetMinion = toRet.data_.getCharacter(PlayerSide.WAITING_PLAYER, (int)(Math.random()*(waitingPlayer.getNumMinions() + 1)));
            toRet = targetMinion.takeDamage((byte)2, PlayerSide.CURRENT_PLAYER, PlayerSide.WAITING_PLAYER, toRet, deckPlayer0, deckPlayer1, false, false);
        }
        return super.startTurn(thisMinionPlayerIndex, toRet, deckPlayer0, deckPlayer1);
    }
}
