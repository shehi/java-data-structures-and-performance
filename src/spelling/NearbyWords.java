package spelling;

import java.util.*;

/**
 * @author UC San Diego Intermediate MOOC team
 */
public class NearbyWords implements SpellingSuggest {
    // THRESHOLD to determine how many words to look through when looking
    // for spelling suggestions (stops prohibitively long searching)
    // For use in the Optional Optimization in Part 2.
    private static final int THRESHOLD = 1000;

    Dictionary dict;

    public NearbyWords(Dictionary dict) {
        this.dict = dict;
    }

    /**
     * Return the list of Strings that are one modification away
     * from the input string.
     *
     * @param s         The original String
     * @param wordsOnly controls whether to return only words or any String
     * @return list of Strings which are nearby the original string
     */
    List<String> distanceOne(String s, boolean wordsOnly) {
        List<String> retList = new ArrayList<>();
        s = s.toLowerCase();
        insertions(s, retList, wordsOnly);
        substitution(s, retList, wordsOnly);
        deletions(s, retList, wordsOnly);
        return retList;
    }


    /**
     * Add to the currentList Strings that are one character mutation away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void substitution(String s, List<String> currentList, boolean wordsOnly) {
        // for each letter in the s and for all possible replacement characters
        for (int index = 0; index < s.length(); index++) {
            for (int charCode = (int) 'a'; charCode <= (int) 'z'; charCode++) {
                // use StringBuffer for an easy interface to permuting the
                // letters in the String
                StringBuffer sb = new StringBuffer(s);
                sb.setCharAt(index, (char) charCode);

                // if the item isn't in the list, isn't the original string, and
                // (if wordsOnly is true) is a real word, add to the list
                if (!currentList.contains(sb.toString()) &&
                        (!wordsOnly || dict.isWord(sb.toString())) &&
                        !s.equals(sb.toString())) {
                    currentList.add(sb.toString());
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character insertion away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void insertions(String s, List<String> currentList, boolean wordsOnly) {
        for (int k = 0; k <= s.length(); k++) {
            for (int m = 'a'; m <= 'z'; m++) {
                String subString = s.substring(0, k) + Character.toString((char) m) + s.substring(k);
                if (wordsOnly && !dict.isWord(subString)) {
                    continue;
                }
                if (!currentList.contains(subString)) {
                    currentList.add(subString);
                }
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param s           The original String
     * @param currentList is the list of words to append modified words
     * @param wordsOnly   controls whether to return only words or any String
     */
    public void deletions(String s, List<String> currentList, boolean wordsOnly) {
        for (int k = 0; k < s.length(); k++) {
            String subString = s.substring(0, k) + s.substring(k + 1);
            if (wordsOnly && !dict.isWord(subString)) {
                continue;
            }
            if (!currentList.contains(subString)) {
                currentList.add(subString);
            }
        }
    }

    /**
     * Add to the currentList Strings that are one character deletion away
     * from the input string.
     *
     * @param word           The misspelled word
     * @param numSuggestions is the maximum number of suggestions to return
     * @return the list of spelling suggestions
     */
    @Override
    public List<String> suggestions(String word, int numSuggestions) {
        LinkedList<String> queue = new LinkedList<>();     // String to explore
        HashSet<String> visited = new HashSet<>();   // to avoid exploring the same
        List<String> retList = new LinkedList<>();   // words to return

        queue.add(word);
        visited.add(word);

        while (!queue.isEmpty() && numSuggestions > 0) {
            String curr = queue.remove();
            List<String> neighbors = distanceOne(curr, true);
            for (String n : neighbors) {
                if (!visited.contains(n)) {
                    visited.add(n);
                    queue.add(n);
                    if (dict.isWord(n)) {
                        retList.add(n);
                        numSuggestions--;
                    }
                }
            }
        }

        return retList;

    }

    public static void main(String[] args) {
        String word = "i";
        // Pass NearbyWords any Dictionary implementation you prefer
        Dictionary d = new DictionaryHashSet();
        DictionaryLoader.loadDictionary(d, "data/dict.txt");
        NearbyWords w = new NearbyWords(d);
        List<String> l = w.distanceOne(word, true);
        System.out.println("One away word Strings for for \"" + word + "\" are:");
        System.out.println(l + "\n");

        word = "kangaro";
        List<String> suggest = w.suggestions(word, 10);
        System.out.println("Spelling Suggestions for \"" + word + "\" are:");
        System.out.println(suggest);
    }
}
