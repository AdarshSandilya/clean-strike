import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTest {

    private Player player;

    @Before
    public void setUp() {
        player = new Player("sample_name");
    }

    @Test
    public void getName_should_return_player_name(){
        String playerName = "sample_name";
        assertEquals(player.getName(), playerName);
    }

    @Test
    public void getPoints_should_return_player_point() {
        assertEquals(0, player.getPoints());
    }

    @Test
    public void addPoints_should_add_given_numbers_to_the_existing_point() {
        player.addPoints(2);
        assertEquals(2, player.getPoints());

        player.addPoints(-1);
        assertEquals(1, player.getPoints());
    }

    @Test
    public void getNumberOfFouls_should_return_current_fouls_for_player() {
        assertEquals(0, player.getNumberOfFouls());
    }

    @Test
    public void addFoul_should_increment_fouls_of_player_by_1() {
        player.addFoul();
        assertEquals(1, player.getNumberOfFouls());

        player.addFoul();
        assertEquals(2, player.getNumberOfFouls());

    }

    @Test
    public void getNumberOfLoses_should_return_number_of_loses() {
        assertEquals(0, player.getNumberOfFouls());
    }

    @Test
    public void addLose_should_increment_number_of_loses_by_1() {
        player.addLose();
        player.addLose();

        assertEquals(2,player.getNumberOfLoses());
    }
}