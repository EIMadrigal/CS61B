import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome1() {
        String[] words = {"", "a", "racecar", "aaaaab"};
        boolean[] expected = {true, true, true, false};
        boolean[] actual = new boolean[4];
        for (int i = 0; i < words.length; ++i) {
            actual[i] = palindrome.isPalindrome(words[i]);
        }
        assertArrayEquals(expected, actual);
    }

    @Test
    /**
     * since isPalindrome had been overloaded in Palindrome.java,
     * so here we have 2 tests.
     */
    public void testIsPalindrome2() {
        String[] words = {"flake", "a", "racecar", "aaabbb"};
        boolean[] expected = {true, true, false, true};
        boolean[] actual = new boolean[4];
        CharacterComparator cc = new OffByOne();
        for (int i = 0; i < words.length; ++i) {
            actual[i] = palindrome.isPalindrome(words[i], cc);
        }
        assertArrayEquals(expected, actual);
    }
}
