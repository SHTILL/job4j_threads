package ru.job4j.synchronization;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> store = new HashMap<>();

    synchronized boolean add(User user) {
        store.putIfAbsent(user.id, user);
        return true;
    }

    synchronized boolean update(User user) {
        store.replace(user.id, user);
        return true;
    }

    synchronized boolean delete(User user) {
        store.remove(user.id);
        return true;
    }

    synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = store.get(fromId);
        if (from == null) {
            return false;
        }
        User to   = store.get(toId);
        if (to == null) {
            return false;
        }
        if (from.getAmount() < amount) {
            return false;
        }
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
        return true;
    }
}