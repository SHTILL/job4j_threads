package ru.job4j.pool;

public class TrackerSingleEnergetic {
    private static final TrackerSingleEnergetic INSTANCE;
    static {
        System.out.print("Energetic");
        INSTANCE = new TrackerSingleEnergetic();
    }

    private TrackerSingleEnergetic() {
    }

    public static TrackerSingleEnergetic getInstance() {
        return INSTANCE;
    }

    public static void main(String[] args) {

    }
}
