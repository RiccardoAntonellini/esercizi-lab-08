package it.unibo.deathnote.impl;

import it.unibo.deathnote.api.DeathNote;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * Implementazione della Death Note.
 * Permette di scrivere nomi, cause di morte e dettagli.
 */
public final class DeathnoteImpl implements DeathNote {
    private String lastWrittenName;
    private final Map<String, Death> deathNote;

    /**
     * Costruttore della DeathnoteImpl.
     * Inizializza la mappa interna per memorizzare le morti.
     */
    public DeathnoteImpl() {
        deathNote = new LinkedHashMap<>();
    }

    /*javadoc comment*/
    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber < 1 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("wrong rule index");
        }
        return RULES.get(ruleNumber - 1);
    }

    /*javadoc comment*/
    @Override
    public void writeName(final String name) {
        Objects.requireNonNull(name);
        lastWrittenName = name;
        deathNote.put(name, new Death());
    }

    /*javadoc comment*/
    @Override
    public boolean writeDeathCause(final String cause) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("no name written");
        }
        if (cause == null) {
            throw new IllegalArgumentException("null cause");
        }
        final Death olDeath = deathNote.get(lastWrittenName);
        final Death newDeath = olDeath.writeCause(cause);
        if (olDeath.equals(newDeath)) {
            return false;
        }
        deathNote.put(lastWrittenName, newDeath);
        return true;
    }

    /*javadoc comment*/
    @Override
    public boolean writeDetails(final String details) {
        if (lastWrittenName == null) {
            throw new IllegalStateException("no name written");
        }
        if (details == null) {
            throw new IllegalArgumentException("null details");
        }
        final Death olDeath = deathNote.get(lastWrittenName);
        final Death newDeath = olDeath.writeDetails(details);
        if (olDeath.equals(newDeath)) {
            return false;
        }
        deathNote.put(lastWrittenName, newDeath);
        return true;
    }

    /*javadoc comment*/
    @Override
    public String getDeathCause(final String name) {
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException("non c'è il nome nel deathnote");
        }
        return deathNote.get(name).getDeathCause();
    }

    /*javadoc comment*/
    @Override
    public String getDeathDetails(final String name) {
        if (!deathNote.containsKey(name)) {
            throw new IllegalArgumentException("non c'è il nome nel deathnote");
        }
        return deathNote.get(name).getDeathDetails();
    }

    /*javadoc comment*/
    @Override
    public boolean isNameWritten(final String name) {
        return deathNote.containsKey(name);
    }

    private static final class Death {
        private static final String DEFAULT_CAUSE = "heart attack";
        private static final long VALID_CAUSE_TIMEOUT = 4000;
        private static final long VALID_DETAILS_TIMEOUT = 6000 + VALID_CAUSE_TIMEOUT;
        private final long deathTime;
        private String deathCause;
        private String deathDetails;

        private int hash;

        private Death(final String deathCause, final String deathDetails, final long deathTime) {
            this.deathCause = deathCause;
            this.deathDetails = deathDetails;
            this.deathTime = deathTime;
        }

        Death() {
            this(DEFAULT_CAUSE, "", System.currentTimeMillis());
        }

        /*javadoc comment*/
        public String getDeathCause() {
            return this.deathCause;
        }

        /*javadoc comment*/
        public String getDeathDetails() {
            return this.deathDetails;
        }

        /*javadoc comment*/
        public Death writeCause(final String cause) {
            if (System.currentTimeMillis() < deathTime + VALID_CAUSE_TIMEOUT) {
                return new Death(cause, this.deathDetails, this.deathTime);
            }
            return this;
        }

        /*javadoc comment*/
        public Death writeDetails(final String details) {
            if (System.currentTimeMillis() < deathTime + VALID_DETAILS_TIMEOUT) {
                return new Death(this.deathCause, details, this.deathTime);
            }
            return this;
        }

        /*javadoc comment*/
        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Death)) {
                return false;
            }
            final Death d = (Death) obj;

            return Objects.equals(this.deathCause, d.deathCause) && Objects.equals(this.deathDetails, d.deathDetails)
            && this.deathTime == d.deathTime;
        }

        /*javadoc comment*/
        @Override
        public int hashCode() {
            if (hash == 0) {
                hash = Objects.hash(deathCause, deathDetails, deathTime);
            }
            return hash;
        }
    }
}
