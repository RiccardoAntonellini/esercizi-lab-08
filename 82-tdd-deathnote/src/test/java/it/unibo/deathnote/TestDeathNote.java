package it.unibo.deathnote;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathnoteImpl;

import static it.unibo.deathnote.api.DeathNote.RULES;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TestDeathNote {
    private static final String MESSAGGIO_ILLEGALSTATE = "Attesa IllegalStateException";
    private static final String MESSAGGIO_ILLEGALARGUMENT = "Attesa IllegalArgumentException";
    private static final long INVALID_CAUSE_TIME = 4100;
    private static final long INVALID_DETAILS_TIME = 6000 + INVALID_CAUSE_TIME;
    private static final String RICCARDO_ANTONELLINI = "Riccardo Antonellini";
    private static final String SILVIO_BERLUSCONI = "Silvio Berlusconi";
    private static final String KARTING = "karting accident";
    private DeathNote deaths;

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @BeforeEach
    void testInitialization() {
        deaths = new DeathnoteImpl();
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void negativeRulesIndex() {
        try {
            deaths.getRule(0);
            fail(MESSAGGIO_ILLEGALARGUMENT);
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }

        try {
            deaths.getRule(-1);
            fail(MESSAGGIO_ILLEGALARGUMENT);
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }

        try {
            deaths.getRule(RULES.size() + 1);
            fail(MESSAGGIO_ILLEGALARGUMENT);
        } catch (final IllegalArgumentException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void nullRulesTesting() {
            for (final String s : RULES) {
                assertNotNull(s);
                assertFalse(s.isBlank());
            }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void deathVerifying() {
        assertFalse(deaths.isNameWritten(RICCARDO_ANTONELLINI));
        deaths.writeName(RICCARDO_ANTONELLINI);
        assertTrue(deaths.isNameWritten(RICCARDO_ANTONELLINI));
        assertFalse(deaths.isNameWritten(SILVIO_BERLUSCONI));
        assertFalse(deaths.isNameWritten(""));
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void causeWritingTesting() throws InterruptedException {
        try {
            deaths.writeDeathCause("causa di morte di prova");
            fail(MESSAGGIO_ILLEGALSTATE);
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
        deaths.writeName(SILVIO_BERLUSCONI);
        assertEquals(deaths.getDeathCause(SILVIO_BERLUSCONI), "heart attack");
        deaths.writeName(RICCARDO_ANTONELLINI);
        assertTrue(deaths.writeDeathCause(KARTING));
        assertEquals(deaths.getDeathCause(RICCARDO_ANTONELLINI), KARTING);
        sleep(INVALID_CAUSE_TIME);
        assertFalse(deaths.writeDeathCause("gooning"));
        assertEquals(deaths.getDeathCause(RICCARDO_ANTONELLINI), KARTING);
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    void detailsWritingTesting() throws InterruptedException {
        try {
            deaths.writeDetails("dettagli della morte di prova");
            fail(MESSAGGIO_ILLEGALSTATE);
        } catch (final IllegalStateException e) {
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isBlank());
            assertFalse(e.getMessage().isEmpty());
        }
        deaths.writeName(SILVIO_BERLUSCONI);
        assertEquals(deaths.getDeathDetails(SILVIO_BERLUSCONI), "");
        assertTrue(deaths.writeDetails("ran for too long"));
        assertEquals(deaths.getDeathDetails(SILVIO_BERLUSCONI), "ran for too long");
        deaths.writeName(RICCARDO_ANTONELLINI);
        sleep(INVALID_DETAILS_TIME);
        assertFalse(deaths.writeDetails("gooned too hard it exploded"));
        assertEquals(deaths.getDeathDetails(RICCARDO_ANTONELLINI), "");
    }
}
