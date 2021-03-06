package com.hearthsim.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.hearthsim.card.minion.Minion;
import com.hearthsim.card.minion.concrete.BloodfenRaptor;

public class TestMinionResetCopy {

    BloodfenRaptor original;
    BloodfenRaptor copy;

    @Before
    public void setUp() throws Exception {
        original = new BloodfenRaptor();
        copy = new BloodfenRaptor();
    }

    @Test
    public void testResetAttack() {
        copy.setAttack((byte)20);
        Minion newCopy = copy.createResetCopy();
        assertEquals(original, newCopy);
    }

    @Test
    public void testResetDivineShield() {
        copy.setDivineShield(true);
        Minion newCopy = copy.createResetCopy();
        assertEquals(original, newCopy);
    }

    @Test
    public void testResetHealth() {
        copy.setHealth((byte)20);
        Minion newCopy = copy.createResetCopy();
        assertEquals(original, newCopy);
    }

    @Test
    public void testResetSpellDamage() {
        copy.setSpellDamage((byte)20);
        Minion newCopy = copy.createResetCopy();
        assertEquals(original, newCopy);
    }

    @Test
    public void testResetWindfury() {
        copy.setWindfury(true);
        Minion newCopy = copy.createResetCopy();
        assertEquals(original, newCopy);
    }
}
