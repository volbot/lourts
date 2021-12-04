package Data;

public class Stats {
    private double[] stats;

    public Stats() {
        stats = new double[]{0, 0, 0, 0, 0, 0};
    }

    public Stats(int intel, int wis, int str, int dex, int cha, int psi) {
        stats = new double[]{intel, wis, str, dex, cha, psi};
    }

    public int[] getStats() {
        int[] returns = new int[6];
        for (int i = 0; i < returns.length; i++) {
            returns[i] = (int) Math.floor(stats[i]);
        }
        return returns;
    }

    public int getStat(String stat) {
        switch (stat) {
            case "int":
                return (int) Math.floor(stats[0]);
            case "wis":
                return (int) Math.floor(stats[1]);
            case "str":
                return (int) Math.floor(stats[2]);
            case "dex":
                return (int) Math.floor(stats[3]);
            case "cha":
                return (int) Math.floor(stats[4]);
            case "psi":
                return (int) Math.floor(stats[5]);
        }
        return -1;
    }
}
