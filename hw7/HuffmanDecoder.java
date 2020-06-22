public class HuffmanDecoder {
    public static void main(String[] args) {
        // Read the Huffman coding trie
        ObjectReader or = new ObjectReader(args[0]);
        BinaryTrie binaryTrie = (BinaryTrie) or.readObject();

        // read the number of symbols
        int numSymbol = (int) or.readObject();

        // Read the massive bit sequence corresponding to the original txt
        BitSequence bs = (BitSequence) or.readObject();

        char[] output = new char[numSymbol];

        for (int i = 0; i < numSymbol; ++i) {
            // Perform a longest prefix match on the massive sequence
            Match match = binaryTrie.longestPrefixMatch(bs);

            // Record the symbol in some data structure
            output[i] = match.getSymbol();

            // Create a new bit sequence containing the remaining unmatched bits
            bs = bs.allButFirstNBits(match.getSequence().length());
        }

        // Write the symbols in some data structure to the specified file
        FileUtils.writeCharArray(args[1], output);
    }
}
