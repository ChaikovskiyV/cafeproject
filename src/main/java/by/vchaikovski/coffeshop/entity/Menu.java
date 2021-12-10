package by.vchaikovski.coffeshop.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Menu {
    private static AtomicBoolean create = new AtomicBoolean(false);
    private static ReentrantLock lock = new ReentrantLock();
    private static Menu instance;
    private final Map<Good, Integer> goods;

    private Menu() {
        goods = new HashMap<>();
    }

    public static Menu getInstance() {
        if (!create.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new Menu();
                    create.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Map<Good, Integer> getGoodList() {
        return goods;
    }

    @Override
    public String toString() {
        return new StringBuilder("Menu{")
                .append(goods)
                .append('}')
                .toString();
    }
}