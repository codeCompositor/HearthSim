package com.hearthsim.card.minion;

import org.json.JSONObject;

@Deprecated
public class Murloc extends Minion {

    public Murloc() {
        super();

        this.tribe = MinionTribe.MURLOC;
    }

    public Murloc(String name, byte mana, byte attack, byte health, byte baseAttack, byte baseHealth, byte maxHealth) {
        super(name, mana, attack, health, baseAttack, baseHealth, maxHealth);

        this.tribe = MinionTribe.MURLOC;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = super.toJSON();
        json.put("type", "Murloc");
        return json;
    }
}
