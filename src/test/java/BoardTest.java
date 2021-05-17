import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void getCurrentCoins_should_give_all_coins_on_board() {
        assertEquals(11, board.getCurrentCoins().size());
    }

    @Test
    void removeCoins_should_remove_given_coins_from_the_board() {
        ArrayList<Coin> removalCoins = new ArrayList<>();
        removalCoins.add(Coin.BLACK);
        removalCoins.add(Coin.BLACK);

        int expectedCount = board.getCurrentCoins().size() - removalCoins.size();
        int expectedBlackCoinCount = Collections.frequency(board.getCurrentCoins(), Coin.BLACK) - removalCoins.size();

        board.removeCoins(removalCoins);

        int currentBlackCoinCount = Collections.frequency(board.getCurrentCoins(), Coin.BLACK);
        assertEquals(expectedCount, board.getCurrentCoins().size());
        assertEquals(expectedBlackCoinCount, currentBlackCoinCount);
    }

    @Test
    void removeCoins_should_not_remove_any_coin_if_removal_coins_are_empty() {
        ArrayList<Coin> removalCoins = new ArrayList<>();
        int expectedCount = board.getCurrentCoins().size();

        board.removeCoins(removalCoins);
        assertEquals(expectedCount, board.getCurrentCoins().size());
    }

    @Test
    void areCoinsExhausted_should_return_true_if_there_is_no_coin_except_striker_on_the_board() {
        ArrayList<Coin> removalCoins = board.getCurrentCoins();
        removalCoins.remove(Coin.STRIKER);
        board.removeCoins(removalCoins);

        assertTrue(board.areCoinsExhausted());
    }

    @Test
    void areCoinsExhausted_should_return_false_if_there_are_other_coins_also_with_striker_on_the_board() {
        assertFalse(board.areCoinsExhausted());
    }
}