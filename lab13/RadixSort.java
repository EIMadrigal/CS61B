/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    private static final int R = 256;

    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        int maxLen = Integer.MIN_VALUE;
        for (String ascii : asciis) {
            maxLen = Math.max(maxLen, ascii.length());
        }

        String[] sorted = asciis.clone();
        // based on counting sort, just count sort every digit
        for (int d = maxLen - 1; d >= 0; --d) {
            sortHelperLSD(sorted, d);
        }
        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        int[] cnt = new int[R];

        for (String s : asciis) {
            int curChar = index < s.length() ? (int) s.charAt(index) : 0;
            ++cnt[curChar];
        }

        int[] starts = new int[R];
        int pos = 0;
        for (int i = 0; i < starts.length; i += 1) {
            starts[i] = pos;
            pos += cnt[i];
        }

        String[] sorted = new String[asciis.length];
        for (String s : asciis) {
            int curChar = index < s.length() ? (int) s.charAt(index) : 0;
            int place = starts[curChar];
            sorted[place] = s;
            starts[curChar] += 1;
        }
        System.arraycopy(sorted, 0, asciis, 0, asciis.length);
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] asciis = {"12", "1", "bc"};
        for (String s : sort(asciis)) {
            System.out.println(s);
        }
    }
}
