package ru.job4j.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class UserStorage {
    final Map<Integer, User> store = new ConcurrentHashMap<>();

    @GuardedBy(value = "store")
    boolean add(User user) {
        store.put(user.id, user);
        return true;
    }

    @GuardedBy(value = "store")
    boolean update(User user) {
        store.put(user.id, user);
        return true;
    }

    @GuardedBy(value = "store")
    boolean delete(User user) {
        store.remove(user.id);
        return true;
    }

    @GuardedBy("this")
    synchronized void transfer(int fromId, int toId, int amount) {
        User from = store.get(fromId);
        User to   = store.get(toId);
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
    }
}
