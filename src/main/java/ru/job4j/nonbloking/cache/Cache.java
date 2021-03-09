package ru.job4j.nonbloking.cache;

import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(@NotNull Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(@NotNull Base model) {
        return memory.computeIfPresent(model.getId(), (id, stored) -> {
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            model.increaseVersion();
            return model;
        }) != null;
    }

    public Base get(int id) {
        return memory.get(id);
    }

    public boolean delete(@NotNull Base model) {
        return memory.remove(model.getId()) != null;
    }
}
