package de.nerobuddy.elemental.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:10
 */

public class NickManager {

    private static final Map<UUID, String> nickNames = new HashMap<>();

    public static void addPlayerNick(UUID uuid, String nickName) {
        nickNames.put(uuid, nickName);
    }

    public static void removePlayerNick(UUID uuid) {
        nickNames.remove(uuid);
    }

    public static Map<UUID, String> getNickNames() {
        return nickNames;
    }
}
