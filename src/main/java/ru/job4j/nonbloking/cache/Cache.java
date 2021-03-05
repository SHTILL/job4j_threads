package ru.job4j.nonbloking.cache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    @GuardedBy(value = "memory")
    public boolean add(@NotNull Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    @GuardedBy(value = "memory")
    public boolean update(@NotNull Base model) {
        Base stored = memory.get(model.getId());
        if (stored == null) {
            return false;
        }
        int storedVersion = stored.getVersion();
        if (storedVersion != model.getVersion()) {
            throw new OptimisticException("Versions are not equal");
        }
        storedVersion++;
        model = model.setVersion(storedVersion);
        return (memory.replace(model.getId(), model) != null);
    }

    @GuardedBy(value = "memory")
    public Base get(int id) {
        return memory.get(id);
    }

    @GuardedBy(value = "memory")
    public void delete(@NotNull Base model) {
        memory.remove(model.getId());
    }
}
