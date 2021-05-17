import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

class RuleManagerTest {
    private ArrayList<Coin> pocketedCoins;
    private ArrayList<Coin> defunctedCoins;

    @BeforeEach
    void setUp() {
        pocketedCoins = new ArrayList<>();
        defunctedCoins = new ArrayList<>();
    }

    @Test
    void should_return_0_if_there_are_no_pocketed_coins() {
        assertEquals(0, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void should_return_1_if_there_is_only_one_black_coin_pocketed() {
        pocketedCoins.add(Coin.BLACK);
        assertEquals(1, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void should_return_3_if_there_is_any_red_coin_pocketed() {
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.RED);
        assertEquals(3, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void should_return_2_if_there_are_two_black_coin_pocketed() {
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.BLACK);
        assertEquals(2, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void should_return_2_if_there_are_more__two_black_coin_pocketed() {
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.BLACK);

        assertEquals(2, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void should_return_minus_one_if_striker_is_pocketed() {
        pocketedCoins.add(Coin.STRIKER);
        assertEquals(-1, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }


    @Test
    void should_return_minus_two_if_any_coin_is_defuncted() {
        defunctedCoins.add(Coin.STRIKER);
        assertEquals(-2, RuleManager.calculatePoints(pocketedCoins, defunctedCoins));
    }

    @Test
    void isFoul_should_retrun_true_if_no_of_fouls_is_3() {
        assertTrue(RuleManager.isFoul(3));
    }

    @Test
    void isFoul_should_retrun_true_if_no_of_fouls_is_not_3() {
        assertFalse(RuleManager.isFoul(2));
    }

    @Test
    void isEligibleForLoseFoul_should_retrun_true_if_no_of_fouls_is_3() {
        assertTrue(RuleManager.isEligibleForLoseFoul(3));
    }

    @Test
    void isEligibleForLoseFoul_should_retrun_true_if_no_of_fouls_is_not_3() {
        assertFalse(RuleManager.isEligibleForLoseFoul(2));
    }

    // when lose streak foul is not applicable

    @Test
    void calculatePointsWithFouls_should_decrement_given_point_by_1_if_foul_is_applicable() {
        int finalPoints = RuleManager.calculatePointsWithFouls(1, true, false);
        assertEquals(0, finalPoints);
    }


    @Test
    void calculatePointsWithFouls_should_not_decrement_if_foul_is_not_applicable() {
        int finalPoints = RuleManager.calculatePointsWithFouls(1, false, false);
        assertEquals(1, finalPoints);
    }

    // when point foul is not applicable
    @Test
    void calculatePointsWithFouls_should_decrement_given_point_by_1_if_lose_streak_foul_is_applicable() {
        int finalPoints = RuleManager.calculatePointsWithFouls(1, false, true);
        assertEquals(0, finalPoints);
    }

    @Test
    void calculatePointsWithFouls_should_not_decrement_if_lose_streak_foul_is__not_applicable() {
        int finalPoints = RuleManager.calculatePointsWithFouls(1, false, false);
        assertEquals(1, finalPoints);
    }

    // when both fouls are applicable

    @Test
    void calculatePointsWithFouls_should_decrement_given_point_by_2_if_lose_streak_and_point_foul_both_are_applicable() {
        int finalPoints = RuleManager.calculatePointsWithFouls(2, true, true);
        assertEquals(0, finalPoints);
    }

    // when there is no foul at all
    @Test
    void calculatePointsWithFouls_should_not_decrement_if_there_is_no_foul() {
        int finalPoints = RuleManager.calculatePointsWithFouls(2, false, false);
        assertEquals(2, finalPoints);
    }

    @Test
    void calculateWinnerBetween_should_return_winner_that_player_who_got_max_point_and_point_difference_is_atleast_3() {
        Player playerOne = mock(Player.class);
        Player playerTwo = mock(Player.class);
        String playerOneName = "winner";

        when(playerOne.getPoints()).thenReturn(5);
        when(playerTwo.getPoints()).thenReturn(2);

        when(playerOne.getName()).thenReturn(playerOneName);

        Player winner = RuleManager.calculateWinnerBetween(playerOne, playerTwo);
        assertEquals(winner.getName(), playerOneName);
    }


    @Test
    void isGameDraw_should_return_true_if_both_player_has_less_than_5_points() {
        assertTrue(RuleManager.isGameDraw(4,3));
    }

    @Test
    void isGameDraw_should_return_true_if_one_player_has_at_least_5_points_but_difference_bw_both_is_less_than_3() {
        assertTrue(RuleManager.isGameDraw(6,4));
    }

    @Test
    void isGameDraw_should_return_false_if_one_player_has_at_least_5_points_and_difference_bw_both_is_at_least_3() {
        assertFalse(RuleManager.isGameDraw(6,3));
        assertFalse(RuleManager.isGameDraw(7,2));
    }

    @Test
    void getRemovalCoins_should_return_all__defuncted_coins_to_be_removed_from_the_board() {
        ArrayList<Coin> pocketedCoins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        defunctedCoins.add(Coin.BLACK);
        defunctedCoins.add(Coin.RED);

        ArrayList<Coin> removalCoins = RuleManager.getRemovalCoins(defunctedCoins, pocketedCoins);
        assertEquals(defunctedCoins, removalCoins);
    }

    @Test
    void getRemovalCoins_should_return_only_red_coin_if_multiple_coins_are_pocketed_along_with_red() {
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        ArrayList<Coin> pocketedCoins = new ArrayList<>();
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.BLACK);
        pocketedCoins.add(Coin.RED);

        ArrayList<Coin> expectedCoins = new ArrayList<>();
        expectedCoins.add(Coin.RED);

        ArrayList<Coin> removalCoins = RuleManager.getRemovalCoins(defunctedCoins, pocketedCoins);
        assertEquals(expectedCoins, removalCoins);
    }

    @Test
    void getRemovalCoins_should_return_only_that_single_coin_if_only_one_coin_is_pocketed_except_striker() {
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        ArrayList<Coin> pocketedCoins = new ArrayList<>();
        pocketedCoins.add(Coin.BLACK);

        ArrayList<Coin> expectedCoins = new ArrayList<>();
        expectedCoins.add(Coin.BLACK);

        ArrayList<Coin> removalCoins = RuleManager.getRemovalCoins(defunctedCoins, pocketedCoins);
        assertEquals(1, expectedCoins.size());
        assertEquals(expectedCoins, removalCoins);
    }
}