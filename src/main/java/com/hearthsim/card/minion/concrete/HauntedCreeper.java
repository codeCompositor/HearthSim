package com.hearthsim.card.minion.concrete;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.event.deathrattle.DeathrattleSummonMinionAction;

public class HauntedCreeper extends Minion {

    public HauntedCreeper() {
        super();

        deathrattleAction_ = new DeathrattleSummonMinionAction(SpectralSpider.class, 2);
    }

}
