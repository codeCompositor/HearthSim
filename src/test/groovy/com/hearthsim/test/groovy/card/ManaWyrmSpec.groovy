package com.hearthsim.test.groovy.card

import com.hearthsim.card.Card
import com.hearthsim.card.Deck
import com.hearthsim.card.minion.concrete.ManaWyrm
import com.hearthsim.card.spellcard.concrete.TheCoin
import com.hearthsim.model.BoardModel
import com.hearthsim.Game
import com.hearthsim.test.helpers.BoardModelBuilder
import com.hearthsim.util.tree.HearthTreeNode

import static com.hearthsim.model.PlayerSide.CURRENT_PLAYER
import static com.hearthsim.model.PlayerSide.WAITING_PLAYER
import static org.junit.Assert.*

class ManaWyrmSpec extends CardSpec {

    def "playing a spell card with a Mana Wyrm on the field"() {
        
        def startingBoard = new BoardModelBuilder().make {
            currentPlayer {
                hand([ManaWyrm, TheCoin])
                mana(9)
            }
            waitingPlayer {
                field([[minion: ManaWyrm]]) //This Questing Adventurer should not be buffed
                mana(9)
            }
        }

        def root = new HearthTreeNode(startingBoard)

        def copiedBoard = startingBoard.deepCopy()
        def target = root.data_.getCharacter(CURRENT_PLAYER, 0)
        def manaWyrm = root.data_.getCurrentPlayerCardHand(0)
        def ret = manaWyrm.useOn(CURRENT_PLAYER, target, root, null, null)

        def board2 = new HearthTreeNode(root.data_.deepCopy())
        def theCoin = board2.data_.getCurrentPlayerCardHand(0)
        def coinTarget = board2.data_.getCharacter(CURRENT_PLAYER, 0)
        def ret2 = theCoin.useOn(CURRENT_PLAYER, coinTarget, board2, null, null)
        
        expect:
        assertFalse(ret == null);
        assertFalse(ret2 == null);
        assertBoardDelta(copiedBoard, ret.data_) {
            currentPlayer {
                playMinion(ManaWyrm)
                mana(8)
                numCardsUsed(1)
            }
        }
        assertBoardDelta(copiedBoard, ret2.data_) {
            currentPlayer {
                playMinion(ManaWyrm)
                removeCardFromHand(TheCoin)
                mana(9)
                updateMinion(0, [deltaAttack: 1])
                numCardsUsed(2)
            }
        }

    }
}
