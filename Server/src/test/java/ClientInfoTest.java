import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


public class ClientInfoTest {

    ClientInfo c;
    @BeforeEach
    void setup() {
        c = new ClientInfo(0);
    }

    @Test
    void test_constructor() {
        ClientInfo d = new ClientInfo(0);
    }

    @Test
    void test_init_size() {
        assertEquals(3, c.getWords().size());
    }

    @Test
    void test_init_data() {
        for (int i = 0; i < 3; i++)
            assertEquals(null, c.getWords().get(i));
    }
}
