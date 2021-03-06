package com.hearthsim.card.spellcard;

import com.hearthsim.card.Deck;
import com.hearthsim.card.ImplementedCardList;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.HearthTreeNode;
import org.json.JSONObject;

public class SpellDamage extends SpellCard {

    protected byte damage_;

    public SpellDamage() {
        super();
    }

    @Deprecated
    public SpellDamage(byte baseManaCost, byte damage, boolean hasBeenUsed) {
        super(baseManaCost, hasBeenUsed);
        damage_ = damage;
    }

    @Override
    public void initFromImplementedCard(ImplementedCardList.ImplementedCard implementedCard) {
        super.initFromImplementedCard(implementedCard);

        this.damage_ = (byte) implementedCard.spellEffect;
    }

    public byte getAttack() {
        return damage_;
    }

    @Override
    public boolean equals(Object other) {

        if (!super.equals(other)) {
            return false;
        }

        if (this.damage_ != ((SpellDamage)other).damage_) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + damage_;
        return result;
    }

    /**
     * Attack using this spell
     *
     * @param targetMinionPlayerSide
     * @param targetMinion The target minion
     * @param boardState The BoardState before this card has performed its action. It will be manipulated and returned.
     * @param deckPlayer0 The deck of player0
     * @return The boardState is manipulated and returned
     */
    public HearthTreeNode attack(PlayerSide targetMinionPlayerSide, Minion targetMinion, HearthTreeNode boardState,
            Deck deckPlayer0, Deck deckPlayer1) throws HSException {
        return targetMinion.takeDamage(damage_, PlayerSide.CURRENT_PLAYER, targetMinionPlayerSide, boardState,
                deckPlayer0, deckPlayer1, true, false);
    }

    public HearthTreeNode attackAllMinionsOnSide(PlayerSide targetMinionPlayerSide, HearthTreeNode boardState,
            Deck deckPlayer0, Deck deckPlayer1) throws HSException {
        if (boardState != null) {
            for (Minion minion : targetMinionPlayerSide.getPlayer(boardState).getMinions()) {
                boardState = this.attack(targetMinionPlayerSide, minion, boardState, deckPlayer0, deckPlayer1);
            }
        }
        return boardState;
    }

    /**
     * Use the card on the given target
     * This is the core implementation of card's ability
     *
     * @param side
     * @param boardState The BoardState before this card has performed its action. It will be manipulated and returned.
     * @return The boardState is manipulated and returned
     */
    @Override
    protected HearthTreeNode use_core(PlayerSide side, Minion targetMinion, HearthTreeNode boardState,
            Deck deckPlayer0, Deck deckPlayer1, boolean singleRealizationOnly) throws HSException {
        HearthTreeNode toRet = super.use_core(side, targetMinion, boardState, deckPlayer0, deckPlayer1, singleRealizationOnly);
        toRet = this.attack(side, targetMinion, toRet, deckPlayer0, deckPlayer1);
        return toRet;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("type", "SpellDamage");
        return json;
    }
}
