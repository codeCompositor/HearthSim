package com.hearthsim.test.minion;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.NorthshireCleric;
import com.hearthsim.card.spellcard.concrete.AncestralHealing;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.tree.CardDrawNode;
import com.hearthsim.util.tree.HearthTreeNode;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestNorthshireCleric {

    private HearthTreeNode board;
    private Deck deck;
    private static final byte mana = 2;
    private static final byte attack0 = 2;
    private static final byte health0 = 3;
    private static final byte health1 = 7;

    @Before
    public void setup() throws HSException {
        Card cards[] = new Card[10];
        for (int index = 0; index < 10; ++index) {
            cards[index] = new TheCoin();
        }

        deck = new Deck(cards);
        board = new HearthTreeNode(new BoardModel(deck, deck));

        Minion minion0_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion0_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);
        Minion minion1_0 = new Minion("" + 0, mana, attack0, health0, attack0, health0, health0);
        Minion minion1_1 = new Minion("" + 0, mana, attack0, (byte)(health1 - 1), attack0, health1, health1);

        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_0);
        board.data_.placeMinion(PlayerSide.CURRENT_PLAYER, minion0_1);

        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_0);
        board.data_.placeMinion(PlayerSide.WAITING_PLAYER, minion1_1);

        board.data_.getCurrentPlayer().setMana((byte)10);
        board.data_.getWaitingPlayer().setMana((byte)10);

        board.data_.getCurrentPlayer().setMaxMana((byte)10);
        board.data_.getWaitingPlayer().setMaxMana((byte)10);


    }

    @Test
    public void test_deepCopy() {


        Minion card1 = new NorthshireCleric();
        Minion card1_cloned = (Minion)card1.deepCopy();

        assertTrue(card1.equals(card1_cloned));
        assertTrue(card1_cloned.equals(card1));

        card1.setHealth((byte)(card1.getHealth() + 1));
        assertFalse(card1.equals(card1_cloned));
        assertFalse(card1_cloned.equals(card1));

        card1_cloned = (Minion)card1.deepCopy();
        assertTrue(card1.equals(card1_cloned));
        assertTrue(card1_cloned.equals(card1));

        card1.setAttack((byte)(card1.getTotalAttack() + 1));
        assertFalse(card1.equals(card1_cloned));
        assertFalse(card1_cloned.equals(card1));

        card1_cloned = (Minion)card1.deepCopy();
        assertTrue(card1.equals(card1_cloned));
        assertTrue(card1_cloned.equals(card1));

        card1.setDestroyOnTurnEnd(true);
        assertFalse(card1.equals(card1_cloned));
        assertFalse(card1_cloned.equals(card1));
        card1_cloned = (Minion)card1.deepCopy();
        assertTrue(card1.equals(card1_cloned));
        assertTrue(card1_cloned.equals(card1));

        card1.setDestroyOnTurnStart(true);
        assertFalse(card1.equals(card1_cloned));
        assertFalse(card1_cloned.equals(card1));
        card1_cloned = (Minion)card1.deepCopy();
        assertTrue(card1.equals(card1_cloned));
        assertTrue(card1_cloned.equals(card1));

    }

    @Test
    public void test1() throws HSException {
        NorthshireCleric fb = new NorthshireCleric();
        board.data_.placeCardHandCurrentPlayer(fb);

        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 0, board, deck, null);
        assertFalse(ret == null);

        assertEquals(ret.data_.getNumCards_hand(), 0);
        assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 3);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 2);
        assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), 3);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(1).getHealth(), health0);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(2).getHealth(), health1 - 1);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), health0);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), health1 - 1);



        AncestralHealing ah = new AncestralHealing();
        board.data_.placeCardHandCurrentPlayer(ah);
        theCard = board.data_.getCurrentPlayerCardHand(0);
        ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 1, board, deck, null);

        assertFalse(ret == null);
        assertEquals(ret.data_.getNumCards_hand(), 0);
        assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 3);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 2);
        assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), 3);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(1).getHealth(), health0);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(2).getHealth(), health1 - 1);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), health0);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), health1 - 1);




        ah = new AncestralHealing();
        board.data_.placeCardHandCurrentPlayer(ah);
        theCard = board.data_.getCurrentPlayerCardHand(0);
        ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 3, board, deck, null);

        assertFalse(ret == null);
        assertEquals(ret.data_.getNumCards_hand(), 0);
        assertTrue(ret instanceof CardDrawNode);
        assertEquals( ((CardDrawNode)ret).getNumCardsToDraw(), 1); //Northshire Cleric should have drawn a card, so 1 card now
        assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 3);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 2);
        assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), 3);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(1).getHealth(), health0);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(2).getHealth(), health1);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), health0);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), health1 - 1);

    }

    @Test
    public void test2() throws HSException {
        NorthshireCleric fb1 = new NorthshireCleric();
        NorthshireCleric fb2 = new NorthshireCleric();

        board.data_.placeCardHandCurrentPlayer(fb1);
        board.data_.placeCardHandCurrentPlayer(fb2);

        Card theCard = board.data_.getCurrentPlayerCardHand(0);
        HearthTreeNode ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 2, board, deck, null);

        theCard = board.data_.getCurrentPlayerCardHand(0);
        ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 3, board, deck, null);
        assertFalse(ret == null);

        assertEquals(ret.data_.getNumCards_hand(), 0);
        assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 4);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 2);
        assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), health0);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(1).getHealth(), health1 - 1);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(2).getHealth(), 3);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(3).getHealth(), 3);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), health0);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), health1 - 1);


        AncestralHealing ah = new AncestralHealing();
        board.data_.placeCardHandCurrentPlayer(ah);
        theCard = board.data_.getCurrentPlayerCardHand(0);
        ret = theCard.useOn(PlayerSide.CURRENT_PLAYER, 2, board, deck, null);

        assertFalse(ret == null);
        assertEquals(ret.data_.getNumCards_hand(), 0);
        assertTrue(ret instanceof CardDrawNode);
        assertEquals( ((CardDrawNode)ret).getNumCardsToDraw(), 2); //Two clerics, one heal means 2 new cards

        assertEquals(ret.data_.getCurrentPlayer().getNumMinions(), 4);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getNumMinions(), 2);
        assertEquals(ret.data_.getCurrentPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getWaitingPlayerHero().getHealth(), 30);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(0).getHealth(), health0);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(1).getHealth(), health1);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(2).getHealth(), 3);
        assertEquals(ret.data_.getCurrentPlayer().getMinions().get(3).getHealth(), 3);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(0).getHealth(), health0);
        assertEquals(PlayerSide.WAITING_PLAYER.getPlayer(ret).getMinions().get(1).getHealth(), health1 - 1);

    }
}
