package ru.job4j.pool;

public class TrackerSingleLazy {
    private TrackerSingleLazy() {
    }

    public static TrackerSingleLazy getInstance() {
        return Holder.INSTANCE;
    }

    private static final class Holder {
        private static final TrackerSingleLazy INSTANCE;
        static {
            System.out.println("Lazy");
            INSTANCE = new TrackerSingleLazy();
        }
    }

    public static void main(String[] args) {
    }
}
