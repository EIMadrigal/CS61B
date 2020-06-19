public class RadixSortLowSpeed {
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
        int N = asciis.length;
        String[] sorted = new String[N];
        System.arraycopy(asciis, 0, sorted, 0, N);
        int maxLen = Integer.MIN_VALUE;
        for (String ascii : asciis) {
            maxLen = Math.max(maxLen, ascii.length());
        }

        String[][] buckets = new String[R][N];

        for (int d = maxLen - 1; d >= 0; --d) {
            int n = 0;
            int[] cnt = new int[R];
            // allocation
            for (int i = 0; i < N; ++i) {
                int curChar = d < sorted[i].length() ? (int) sorted[i].charAt(d) : 0;
                buckets[curChar][cnt[curChar]++] = sorted[i];
            }

            // collection
            for (int j = 0; j < R; ++j) {
                for (int k = 0; k < cnt[j]; ++k) {
                    sorted[n++] = buckets[j][k];
                }
            }
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
        // Optional LSD helper method for required LSD radix sort
        return;
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
        String[] asciis = {"12", "1"};
        for (String s : sort(asciis)) {
            System.out.println(s);
        }
    }
}
