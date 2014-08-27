package com.hearthsim.test.minion;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.Card;
import com.hearthsim.card.Deck;
import com.hearthsim.card.minion.Hero;
import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.BoulderfistOgre;
import com.hearthsim.card.minion.concrete.StranglethornTiger;
import com.hearthsim.card.spellcard.concrete.Silence;
import com.hearthsim.card.spellcard.concrete.TheCoin;
import com.hearthsim.exception.HSException;
import com.hearthsim.player.Player;
import com.hearthsim.player.playercontroller.ArtificialPlayer;
import com.hearthsim.util.boardstate.BoardState;
import com.hearthsim.util.tree.HearthTreeNode;

public class TestStranglethornTiger {


	private HearthTreeNode board;
	private Deck deck;

	@Before
	public void setup() {
		board = new HearthTreeNode(new BoardState());

		Minion minion0_0 = new BoulderfistOgre();
		Minion minion0_1 = new BoulderfistOgre();
		Minion minion1_1 = new StranglethornTiger();
		
		board.data_.placeCard_hand_p0(minion0_0);
		board.data_.placeCard_hand_p0(minion0_1);
				
		board.data_.placeCard_hand_p1(minion1_1);

		Card cards[] = new Card[10];
		for (int index = 0; index < 10; ++index) {
			cards[index] = new TheCoin();
		}
	
		deck = new Deck(cards);

		board.data_.setMana_p0((byte)8);
		board.data_.setMana_p1((byte)8);
		
		board.data_.setMaxMana_p0((byte)8);
		board.data_.setMaxMana_p1((byte)8);
		
		HearthTreeNode tmpBoard = new HearthTreeNode(board.data_.flipPlayers());
		try {
			tmpBoard.data_.getCard_hand_p0(0).useOn(0, tmpBoard.data_.getHero_p0(), tmpBoard, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board = new HearthTreeNode(tmpBoard.data_.flipPlayers());
		try {
			board.data_.getCard_hand_p0(0).useOn(0, board.data_.getHero_p0(), board, deck, null);
			board.data_.getCard_hand_p0(0).useOn(0, board.data_.getHero_p0(), board, deck, null);
		} catch (HSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		board.data_.resetMana();
		board.data_.resetMinions();
		
	}
	
	

	@Test
	public void test0() throws HSException {
		
		assertEquals(board.data_.getNumCards_hand(), 0);
		assertEquals(board.data_.getNumMinions_p0(), 2);
		assertEquals(board.data_.getNumMinions_p1(), 1);
		assertEquals(board.data_.getMana_p0(), 8);
		assertEquals(board.data_.getMana_p1(), 8);
		assertEquals(board.data_.getHero_p0().getHealth(), 30);
		assertEquals(board.data_.getHero_p1().getHealth(), 30);
		assertEquals(board.data_.getMinion_p0(0).getTotalHealth(), 7);
		assertEquals(board.data_.getMinion_p0(1).getTotalHealth(), 7);
		assertEquals(board.data_.getMinion_p1(0).getTotalHealth(), 5);

		assertEquals(board.data_.getMinion_p0(0).getTotalAttack(), 6);
		assertEquals(board.data_.getMinion_p0(1).getTotalAttack(), 6);
		assertEquals(board.data_.getMinion_p1(0).getTotalAttack(), 5);
	}
	
	
	@Test
	public void test1() throws HSException {
		
		//In this test, the Stranglethorn Tiger is stealthed, so player0 has no choice but to hit the enemy hero for 12 damage
		
		ArtificialPlayer ai0 = new ArtificialPlayer(
				0.9,
				0.9,
				1.0,
				1.0,
				1.0,
				0.1,
				0.1,
				0.1,
				0.5,
				0.5,
				0.0,
				0.5,
				0.0,
				0.0
				);
		
		Hero hero = new Hero();
		Player player0 = new Player("player0", hero, deck);
		Player player1 = new Player("player0", hero, deck);
		
		BoardState resBoard = ai0.playTurn(0, board.data_, player0, player1);

		assertEquals(resBoard.getNumCards_hand_p0(), 0);
		assertEquals(resBoard.getNumCards_hand_p1(), 0);
		assertEquals(resBoard.getNumMinions_p0(), 2);
		assertEquals(resBoard.getNumMinions_p1(), 1);
		assertEquals(resBoard.getMana_p0(), 8);
		assertEquals(resBoard.getMana_p1(), 8);
		assertEquals(resBoard.getHero_p0().getHealth(), 30);
		assertEquals(resBoard.getHero_p1().getHealth(), 18);
		assertEquals(resBoard.getMinion_p0(0).getTotalHealth(), 7);
		assertEquals(resBoard.getMinion_p0(1).getTotalHealth(), 7);
		assertEquals(resBoard.getMinion_p1(0).getTotalHealth(), 5);

		assertEquals(resBoard.getMinion_p0(0).getTotalAttack(), 6);
		assertEquals(resBoard.getMinion_p0(1).getTotalAttack(), 6);
		assertEquals(resBoard.getMinion_p1(0).getTotalAttack(), 5);
	}
	
	@Test
	public void test2() throws HSException {
		
		//In this test, player0 is given a Silence.  It can't use it though because it can't target the stealthed Stranglethorn Tiger.
		
		ArtificialPlayer ai0 = new ArtificialPlayer(
				0.9,
				0.9,
				1.0,
				1.0,
				1.0,
				0.1,
				0.1,
				0.1,
				0.5,
				0.5,
				0.0,
				0.5,
				0.0,
				0.0
				);
		
		Hero hero = new Hero();
		Player player0 = new Player("player0", hero, deck);
		Player player1 = new Player("player0", hero, deck);
		
		board.data_.placeCard_hand_p0(new Silence());
		
		BoardState resBoard = ai0.playTurn(0, board.data_, player0, player1);

		assertEquals(resBoard.getNumCards_hand_p0(), 1);
		assertEquals(resBoard.getNumCards_hand_p1(), 0);
		assertEquals(resBoard.getNumMinions_p0(), 2);
		assertEquals(resBoard.getNumMinions_p1(), 1);
		assertEquals(resBoard.getMana_p0(), 8);
		assertEquals(resBoard.getMana_p1(), 8);
		assertEquals(resBoard.getHero_p0().getHealth(), 30);
		assertEquals(resBoard.getHero_p1().getHealth(), 18);
		assertEquals(resBoard.getMinion_p0(0).getTotalHealth(), 7);
		assertEquals(resBoard.getMinion_p0(1).getTotalHealth(), 7);
		assertEquals(resBoard.getMinion_p1(0).getTotalHealth(), 5);

		assertEquals(resBoard.getMinion_p0(0).getTotalAttack(), 6);
		assertEquals(resBoard.getMinion_p0(1).getTotalAttack(), 6);
		assertEquals(resBoard.getMinion_p1(0).getTotalAttack(), 5);
	}
	
	@Test
	public void test3() throws HSException {

		//In this test, player1 goes first.  It uses the Stranglethorn Tiger to attack the hero, which removes stealth from 
		// the tiger.  Then, player0 plays a turn in which it is able to kill the tiger and hit the player1's hero for 6.  
		
		ArtificialPlayer ai0 = new ArtificialPlayer(
				0.9,
				0.9,
				1.0,
				1.0,
				1.0,
				0.1,
				0.1,
				0.1,
				0.5,
				0.5,
				0.0,
				0.5,
				0.0,
				0.0
				);
		
		Hero hero = new Hero();
		Player player0 = new Player("player0", hero, deck);
		Player player1 = new Player("player1", hero, deck);
		
		BoardState resBoard0 = ai0.playTurn(0, board.data_.flipPlayers(), player1, player0, 2000000000);
		BoardState resBoard1 = ai0.playTurn(0, resBoard0.flipPlayers(), player0, player1, 2000000000);

		assertEquals(resBoard1.getNumCards_hand_p1(), 0);
		assertEquals(resBoard1.getNumCards_hand_p1(), 0);
		assertEquals(resBoard1.getNumMinions_p0(), 2);
		assertEquals(resBoard1.getNumMinions_p1(), 0);
		assertEquals(resBoard1.getMana_p0(), 8);
		assertEquals(resBoard1.getMana_p1(), 8);
		assertEquals(resBoard1.getHero_p0().getHealth(), 25);
		assertEquals(resBoard1.getHero_p1().getHealth(), 24);
		assertEquals(resBoard1.getMinion_p0(0).getTotalHealth(), 7);
		assertEquals(resBoard1.getMinion_p0(1).getTotalHealth(), 2);

		assertEquals(resBoard1.getMinion_p0(0).getTotalAttack(), 6);
		assertEquals(resBoard1.getMinion_p0(1).getTotalAttack(), 6);
	}
}