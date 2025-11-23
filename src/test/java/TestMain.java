import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ua.ucu.apps.CoinHandler;
import ua.ucu.apps.Group;
import ua.ucu.apps.Signature;

public class TestMain {
    @Test
    void testCoinHandler() {
        CoinHandler h10 = new CoinHandler(10) {};
        CoinHandler h5 = new CoinHandler(5) {};
        CoinHandler h1 = new CoinHandler(1) {};

        h10.setNext(h5);
        h5.setNext(h1);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        int targetAmount = 28;
        h10.handle(targetAmount);

        String output = outContent.toString();
        assertTrue(output.contains("Use 2 coin(s) of 10"), "Should use 2 coins of 10");
        assertTrue(output.contains("Use 1 coin(s) of 5"), "Should use 1 coin of 5");
        assertTrue(output.contains("Use 3 coin(s) of 1"), "Should use 3 coins of 1");
    }

    @Test
    void testStampingApi() {
        Group<Integer> nestedGroup = new Group<>();
        Signature<Integer> sig1 = new Signature<>(x -> {});
        nestedGroup.addTask(sig1);

        Group<Integer> group = new Group<>();
        Signature<Integer> sigOuter = new Signature<>(x -> {});
        group.addTask(nestedGroup).addTask(sigOuter);

        group.apply(5);

        assertNotNull(sig1.getHeader("groupId"), "Nested signature should have groupId");
        assertNotNull(sigOuter.getHeader("groupId"), "Outer signature should have groupId");
    }
}
