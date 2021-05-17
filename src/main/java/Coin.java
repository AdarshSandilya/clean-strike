public enum Coin {
    BLACK(1), RED(3), STRIKER(-1);

    private final int value;

    Coin(int point) {
        this.value = point;
    }

    public int getPoint() {
        return value;
    }
}
