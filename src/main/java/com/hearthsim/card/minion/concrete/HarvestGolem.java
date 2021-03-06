package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.event.deathrattle.DeathrattleSummonMinionAction;

public class HarvestGolem extends Minion {

    public HarvestGolem() {
        super();

        deathrattleAction_ = new DeathrattleSummonMinionAction(DamagedGolem.class, 1);
    }
}
