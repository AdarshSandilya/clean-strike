public class Player {
    private String name;
    private int points;
    private int numberOfFouls;
    private int numberOfLoses;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void addPoints(int point) {
        this.points += point;
    }

    void addFoul() {
        this.numberOfFouls++;
    }

    void addLose() {
        this.numberOfLoses++;
    }

    public int getPoints() {
        return points;
    }

    int getNumberOfFouls() {
        return numberOfFouls;
    }

    int getNumberOfLoses() {
        return numberOfLoses;
    }

    public void resetLoseStreak() {
        this.numberOfLoses = 0;
    }

    public void resetNoOfFouls() {
        this.numberOfFouls = 0;
    }
}
