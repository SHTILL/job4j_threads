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
        if (user == null) {
            return false;
        }
        return (store.putIfAbsent(user.id, user) == null);
    }

    synchronized boolean update(User user) {
        if (user == null) {
            return false;
        }
        return (store.replace(user.id, user) != null);
    }

    synchronized boolean delete(User user) {
        if (user == null) {
            return false;
        }
        return (store.remove(user.id) != null);
    }

    synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = store.get(fromId);
        User to   = store.get(toId);
        if ((to == null) || (from == null) || (from.getAmount() < amount)) {
            return false;
        }
        from.setAmount(from.getAmount() - amount);
        to.setAmount(to.getAmount() + amount);
        return true;
    }
}