import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class ServerMessageTest {

    ServerMessage m;
    @BeforeEach
    void setup() {
        m = new ServerMessage();
    }

    @Test
    void test_constructor() {
        ServerMessage b = new ServerMessage();
    }

    @Test
    void test_init_size() {
        assertEquals(3, m.getWins().size());
        assertEquals(3, m.getAttemptsLeft().size());
        assertEquals(3, m.getCorrectChars().size());
        assertEquals(3, m.getGuessesLeft().size());
    }
}
