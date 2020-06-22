import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        Map<Character, Integer> frequencyTable = new HashMap<>();
        for (char c : inputSymbols) {
            if (frequencyTable.containsKey(c)) {
                frequencyTable.put(c, frequencyTable.get(c) + 1);
            } else {
                frequencyTable.put(c, 1);
            }
        }
        return frequencyTable;
    }

    public static void main(String[] args) {
        // Read the file as 8 bit symbols
        char[] inputSymbols = FileUtils.readFile(args[0]);
        // Build frequency table
        Map<Character, Integer> freqTable = buildFrequencyTable(inputSymbols);

        // Use frequency table to construct a binary decoding trie
        BinaryTrie binaryTrie = new BinaryTrie(freqTable);

        /** Create a file called .huf that ObjectWriter ow will write to. */
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        // Write the binary decoding trie to the .huf file
        ow.writeObject(binaryTrie);

        // write the number of symbols to the .huf file
        ow.writeObject(inputSymbols.length);

        // Use binary trie to create lookup table for encoding
        Map<Character, BitSequence> lookupTable = binaryTrie.buildLookupTable();
        List<BitSequence> sequence = new ArrayList<>();
        for (char c : inputSymbols) {
            BitSequence bs = lookupTable.get(c);
            sequence.add(bs);
        }
        // Assemble all bit sequences into one huge bit sequence and write
        ow.writeObject(BitSequence.assemble(sequence));
    }
}
