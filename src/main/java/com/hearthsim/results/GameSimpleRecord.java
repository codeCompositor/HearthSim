package com.hearthsim.results;

import java.util.List;

import com.hearthsim.card.minion.Hero;
import com.hearthsim.model.BoardModel;
import com.hearthsim.model.PlayerModel;
import com.hearthsim.model.PlayerSide;
import com.hearthsim.util.HearthActionBoardPair;

import org.json.JSONObject;

public class GameSimpleRecord implements GameRecord {

    int maxTurns_;
    byte[][][] numMinions_;
    byte[][][] numCards_;
    byte[][][] heroHealth_;
    byte[][][] heroArmor_;

    public GameSimpleRecord() {
        this(50);
    }

    public GameSimpleRecord(int maxTurns) {
        maxTurns_ = maxTurns;
        numMinions_ = new byte[2][maxTurns][2];
        numCards_ = new byte[2][maxTurns][2];
        heroHealth_ = new byte[2][maxTurns][2];
        heroArmor_ = new byte[2][maxTurns][2];
    }

    @Override
    public void put(int turn, PlayerSide activePlayerSide, BoardModel board, List<HearthActionBoardPair> plays) {
        PlayerModel playerModel = board.modelForSide(activePlayerSide);

        int currentPlayerId = playerModel.getPlayerId();
        int waitingPlayerId = board.modelForSide(activePlayerSide.getOtherPlayer()).getPlayerId();

        numMinions_[currentPlayerId][turn][currentPlayerId] = (byte)board.getCurrentPlayer().getNumMinions();
        numMinions_[currentPlayerId][turn][waitingPlayerId] = (byte)board.getWaitingPlayer().getNumMinions();

        numCards_[currentPlayerId][turn][currentPlayerId] = (byte)board.getNumCardsHandCurrentPlayer();
        numCards_[currentPlayerId][turn][waitingPlayerId] = (byte)board.getNumCardsHandWaitingPlayer();

        Hero currentPlayerHero = board.getCurrentPlayerHero();
        heroHealth_[currentPlayerId][turn][currentPlayerId] = currentPlayerHero.getHealth();
        Hero waitingPlayerHero = board.getWaitingPlayerHero();
        heroHealth_[currentPlayerId][turn][waitingPlayerId] = waitingPlayerHero.getHealth();

        heroArmor_[currentPlayerId][turn][currentPlayerId] = currentPlayerHero.getArmor();
        heroArmor_[currentPlayerId][turn][waitingPlayerId] = waitingPlayerHero.getArmor();
    }

    @Override
    public int getRecordLength(int playerId) {
        return maxTurns_;
    }

    @Override
    public int getNumMinions(int playerId, int turn, int currentPlayerId) {
        return numMinions_[currentPlayerId][turn][playerId];
    }

    @Override
    public int getNumCardsInHand(int playerId, int turn, int currentPlayerId) {
        return numCards_[currentPlayerId][turn][playerId];
    }

    @Override
    public int getHeroHealth(int playerId, int turn, int currentPlayerId) {
        return heroHealth_[currentPlayerId][turn][playerId];
    }

    @Override
    public int getHeroArmor(int playerId, int turn, int currentPlayerId) {
        return heroArmor_[currentPlayerId][turn][playerId];
    }

    @Override
    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

}
