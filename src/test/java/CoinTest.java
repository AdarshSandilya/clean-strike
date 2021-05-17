

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoinTest {

    @Test
    public void should_return_1_for_black_coin() {
        assertEquals(1, Coin.BLACK.getPoint());
    }

    @Test
    public void should_return_2_for_red_coin() {
        assertEquals(3, Coin.RED.getPoint());
    }

    @Test
    public void should_return_minus_1_for_striker() {
        assertEquals(-1, Coin.STRIKER.getPoint());
    }
}