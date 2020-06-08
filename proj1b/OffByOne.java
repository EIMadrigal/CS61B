public class OffByOne implements CharacterComparator {

    // return true if ASCII of |x - y| = 1
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (diff == 1 || diff == -1) {
            return true;
        }
        return false;
    }
}
