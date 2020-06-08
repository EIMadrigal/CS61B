public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> wordDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); ++i) {
            Character c = word.charAt(i);
            wordDeque.addLast(c);
        }
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque);
    }

    private boolean isPalindrome(Deque<Character> wordDeque) {
        if (wordDeque.isEmpty() || wordDeque.size() == 1) {
            return true;
        }
        Character left = wordDeque.removeFirst();
        Character right = wordDeque.removeLast();
        return left == right && isPalindrome(wordDeque);
    }

    /**
     * return true if the word is a palindrome according to the character comparison test
     * provided by the CharacterComparator passed in as argument cc.
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> wordDeque = wordToDeque(word);
        return isPalindrome(wordDeque, cc);
    }

    private boolean isPalindrome(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.isEmpty() || wordDeque.size() == 1) {
            return true;
        }
        Character left = wordDeque.removeFirst();
        Character right = wordDeque.removeLast();
        return cc.equalChars(left, right) && isPalindrome(wordDeque, cc);
    }
}
