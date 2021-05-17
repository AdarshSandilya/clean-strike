import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TurnTest {

    private String playerName;
    private Turn turn;
    private ArrayList<Coin> pocketedCoins;
    private ArrayList<Coin> defunctedCoins;

    @BeforeEach
    void setUp() {
        playerName = "playerName";
        Player player = new Player(playerName);
        pocketedCoins = new ArrayList<>();
        defunctedCoins = new ArrayList<>();
        turn = new Turn(player, pocketedCoins, defunctedCoins);
    }

    @Test
    void getCurrentPlayer_should_retrun_player() {
        assertEquals(playerName, turn.getCurrentPlayer().getName());
    }

    @Test
    void getPocketedCoins_should_return_all_pocketed_coins_for_a_player() {
        ArrayList<Coin> expectedCoins = new ArrayList<>();

        pocketedCoins.add(Coin.BLACK);
        expectedCoins.add(Coin.BLACK);

        assertEquals(expectedCoins, turn.getPocketedCoins());
    }

    @Test
    void getDefunctedCoins_should_return_all_defuncted_coins_for_a_player() {
        ArrayList<Coin> expectedCoins = new ArrayList<>();

        defunctedCoins.add(Coin.BLACK);
        expectedCoins.add(Coin.BLACK);

        assertEquals(expectedCoins, turn.getDefunctedCoins());
    }
}