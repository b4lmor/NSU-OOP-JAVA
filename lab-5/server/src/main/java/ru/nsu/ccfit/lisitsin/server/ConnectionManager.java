package ru.nsu.ccfit.lisitsin.server;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@UtilityClass
public class ConnectionManager {

    private static final Map<UUID, Connection> connections = new HashMap<>();

    public static void put(UUID id, Connection connection) {
        connections.put(id, connection);
    }

    public static Optional<Connection> get(UUID id) {
        return Optional.ofNullable(connections.get(id));
    }

    public static void remove(UUID id) {
        get(id).ifPresent(Connection::close);
        connections.remove(id);
    }

    public static Collection<Connection> values() {
        return connections.values();

    }public static Set<UUID> keySet() {
        return connections.keySet();
    }

}
