package de.nerobuddy.elemental.exceptions;

/**
 * @author m_wei
 * @project Elemental
 * @created 01.09.2022 - 23:05
 */

public class PlayerNotFoundException extends Exception {

    private final String playerName;
    public PlayerNotFoundException(String arg) {
        this.playerName = arg;
    }

    public String getPlayerName() {
        return playerName;
    }
}
