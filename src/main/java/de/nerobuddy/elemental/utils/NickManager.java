package de.nerobuddy.elemental.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author m_wei
 * @project Elemental
 * @created 04.09.2022 - 21:10
 */

public final class NickManager {

    private NickManager() {
    }

    private static final Map<UUID, String> NICK_NAMES = new HashMap<>();

    public static void addPlayerNick(final UUID uuid, final String nickName) {
        NICK_NAMES.put(uuid, nickName);
    }

    public static void removePlayerNick(final UUID uuid) {
        NICK_NAMES.remove(uuid);
    }

    public static Map<UUID, String> getNickNames() {
        return NICK_NAMES;
    }
}
