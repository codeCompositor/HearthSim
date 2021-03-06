package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.MinionWithEnrage;

public class AngryChicken extends MinionWithEnrage {

    public AngryChicken() {
        super();
    }

    @Override
    public void enrage() {
        attack_ = (byte)(attack_ + 5);
    }

    @Override
    public void pacify() {
        attack_ = (byte)(attack_ - 5);
    }
}
