package com.hearthsim.event.deathrattle;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import com.hearthsim.util.tree.RandomEffectNode;

/**
 * Created by oyachai on 1/12/15.
 */
public class DeathrattleMindControl extends DeathrattleAction {

    public DeathrattleMindControl() {
    }

    @Override
    public HearthTreeNode performAction(Card origin,
                                        PlayerSide playerSide,
                                        HearthTreeNode boardState,
                                        Deck deckPlayer0,
                                        Deck deckPlayer1,
                                        boolean singleRealizationOnly) throws HSException {
        HearthTreeNode toRet = super.performAction(origin, playerSide, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
        if (toRet != null) {
            if (singleRealizationOnly) {
                int numTargets = 0;
                for (Minion targetMinion : playerSide.getOtherPlayer().getPlayer(toRet).getMinions()) {
                    if (targetMinion.isAlive())
                        ++numTargets;
                }
                if (numTargets > 0) {
                    Minion targetMinion = playerSide.getOtherPlayer().getPlayer(toRet).getMinions().get((int)(Math.random() * numTargets));
                    while (!targetMinion.isAlive())
                        targetMinion = playerSide.getOtherPlayer().getPlayer(toRet).getMinions().get((int)(Math.random() * numTargets));
                    toRet.data_.removeMinion(targetMinion);
                    toRet.data_.placeMinion(playerSide, targetMinion);
                    if (targetMinion.getCharge()) {
                        if (!targetMinion.canAttack()) {
                            targetMinion.hasAttacked(false);
                        }
                    } else {
                        targetMinion.hasAttacked(true);
                    }
                }
            } else {
                int numTargets = 0;
                for (Minion targetMinion : playerSide.getOtherPlayer().getPlayer(toRet).getMinions()) {
                    if (targetMinion.isAlive())
                        ++numTargets;
                }
                if (numTargets > 0) {
                    toRet = new RandomEffectNode(boardState, null);
                    int dyingMinionIndex = playerSide.getPlayer(toRet).getMinions().indexOf((Minion)origin);
                    for (int indx = 0; indx < playerSide.getOtherPlayer().getPlayer(toRet).getMinions().size(); ++indx) {
                        if (playerSide.getOtherPlayer().getPlayer(toRet).getMinions().get(indx).isAlive()) {
                            HearthTreeNode cNode = new HearthTreeNode(toRet.data_.deepCopy());
                            Minion targetMinion = playerSide.getOtherPlayer().getPlayer(cNode).getMinions().get(indx);
                            Minion dyingMinion = cNode.data_.getMinion(playerSide, dyingMinionIndex);
                            cNode.data_.removeMinion(targetMinion);
                            cNode.data_.placeMinion(playerSide, targetMinion);
                            if (targetMinion.getCharge()) {
                                if (!targetMinion.canAttack()) {
                                    targetMinion.hasAttacked(false);
                                }
                            } else {
                                targetMinion.hasAttacked(true);
                            }
                            targetMinion.hasBeenUsed(true);
                            //Ugly, but we have to remove the dead sylvanas explicitly here
                            cNode.data_.removeMinion(dyingMinion);
                            if (cNode != null) {
                                toRet.addChild(cNode);
                            }
                        }
                    }
                }
            }

        }
        return toRet;
    }


    @Override
    public boolean equals(Object other) {
        if (!super.equals(other)) {
            return false;
        }

        if (!(other instanceof DeathrattleMindControl))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return 1;
    }

}
