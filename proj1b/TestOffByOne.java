import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {

    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        Character[] c1 = {'a', 'r', '&', 'a'};
        Character[] c2 = {'b', 'q', '%', 'B'};
        boolean[] expected = {true, true, true, false};
        boolean[] actual = new boolean[4];
        for (int i = 0; i < c1.length; ++i) {
            actual[i] = offByOne.equalChars(c1[i], c2[i]);
        }
        assertArrayEquals(expected, actual);
    }
}
