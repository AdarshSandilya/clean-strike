import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@PrepareForTest({RuleManager.class})
@RunWith(PowerMockRunner.class)
public class GameTest {

    private Game game;
    private ArrayList<Player> players;

    @Before
    public void setUp() {
        players = new ArrayList<>();
        Player player_one = new Player("player_one");
        Player player_two = new Player("player_two");
        players.add(player_one);
        players.add(player_two);

        Board board = new Board();

        game = new Game(players, board);
    }

    @Test
    public void game_should_count_as_a_lose() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();

        Turn turn = mock(Turn.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);

        game.play(turn);
        verify(mockedPLayer).addLose();
    }


    @Test
    public void game_should_not_count_as_a_lose_if_a_coin_is_pocketed() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();

        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);

        game.play(turn);
        verify(mockedPLayer, never()).addLose();
    }

    @Test
    public void game_should_count_a_foul_if_player_loses_a_point() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.calculatePoints(ArgumentMatchers.any(ArrayList.class), ArgumentMatchers.any(ArrayList.class))).thenReturn(-1);

        game.play(turn);
        verify(mockedPLayer).addFoul();
    }

    @Test
    public void game_should_not_count_a_foul_if_player_gets_a_point() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.calculatePoints(ArgumentMatchers.any(ArrayList.class), ArgumentMatchers.any(ArrayList.class))).thenReturn(1);

        game.play(turn);
        verify(mockedPLayer, never()).addFoul();
    }

    @Test
    public void play_should_reset_no_of_loses_if_lose_streak_exhausted() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.isEligibleForLoseFoul(anyInt())).thenReturn(true);

        game.play(turn);
        verify(mockedPLayer).resetLoseStreak();
    }

    @Test
    public void play_should_not_reset_no_of_loses_if_number_of_loses_is_less_than_3() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.isEligibleForLoseFoul(anyInt())).thenReturn(false);

        game.play(turn);
        verify(mockedPLayer, never()).resetLoseStreak();
    }


    @Test
    public void play_should_reset_no_of_fouls_if_number_of_fouls_exhausted() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.isFoul(anyInt())).thenReturn(true);

        game.play(turn);
        verify(mockedPLayer).resetNoOfFouls();
    }

    @Test
    public void play_should_not_reset_no_of_fouls_if_number_of_fouls_is_not_exhausted() {
        ArrayList<Coin> coins = new ArrayList<>();
        ArrayList<Coin> defunctedCoins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Turn turn = mock(Turn.class);
        mockStatic(RuleManager.class);
        Player mockedPLayer = mock(Player.class);

        when(turn.getCurrentPlayer()).thenReturn(mockedPLayer);
        when(turn.getPocketedCoins()).thenReturn(coins);
        when(turn.getDefunctedCoins()).thenReturn(defunctedCoins);
        when(RuleManager.isFoul(anyInt())).thenReturn(false);

        game.play(turn);
        verify(mockedPLayer, never()).resetNoOfFouls();
    }

    @Test
    public void isOver_should_return_true_if_there_are_no_coins_on_the_board() {
        Board board = mock(Board.class);
        Game game = new Game(players, board);
        when(board.areCoinsExhausted()).thenReturn(true);

        assertTrue(game.isOver());
    }

    @Test
    public void isOver_should_return_false_if_there_are_coins_on_the_board() {
        Board board = mock(Board.class);
        Game game = new Game(players, board);
        when(board.areCoinsExhausted()).thenReturn(false);

        assertFalse(game.isOver());
    }


    @Test
    public void winner_should_return_nullable_if_game_is_not_over_yet() {
        ArrayList<Coin> coins = new ArrayList<>();
        coins.add(Coin.BLACK);

        Board board = mock(Board.class);
        Game game = new Game(players, board);

        when(board.getCurrentCoins()).thenReturn(coins);
        Optional<Player> winner = game.winner();

        assertFalse(winner.isPresent());
    }

    @Test
    public void winner_should_return_winner_if_game_is_over() {
        Player firstPlayer = players.get(0);
        mockStatic(RuleManager.class);
        Board board = mock(Board.class);
        Game game = new Game(players, board);

        when(board.areCoinsExhausted()).thenReturn(true);
        when(RuleManager.calculateWinnerBetween(any(), any())).thenReturn(firstPlayer);
        when(RuleManager.isGameDraw(anyInt(), anyInt())).thenReturn(false);

        Optional<Player> winner = game.winner();

        assertTrue(winner.isPresent());
        assertEquals(winner.get().getName(), firstPlayer.getName());
    }

    @Test
    public void isDraw_should_return_false_if_game_is_not_over_yet() {
        mockStatic(RuleManager.class);
        Board board = mock(Board.class);
        Game game = new Game(players, board);

        when(RuleManager.isGameDraw(anyInt(), anyInt())).thenReturn(true);
        when(board.areCoinsExhausted()).thenReturn(false);

        assertFalse(game.isDraw());
    }

    @Test
    public void isDraw_should_return_true_if_game_is_draw() {
        Board board = mock(Board.class);
        mockStatic(RuleManager.class);
        Game game = new Game(players, board);

        when(RuleManager.isGameDraw(anyInt(), anyInt())).thenReturn(true);
        when(board.areCoinsExhausted()).thenReturn(true);

        assertTrue(game.isDraw());
    }

    @Test
    public void isDraw_should_return_false_if_game_is_not_draw() {
        Board board = mock(Board.class);
        mockStatic(RuleManager.class);
        when(RuleManager.isGameDraw(anyInt(), anyInt())).thenReturn(false);
        when(board.areCoinsExhausted()).thenReturn(true);

        assertFalse(game.isDraw());
    }
}