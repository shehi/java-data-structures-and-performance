package textgen;

import java.util.*;

public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

    // The list of words with their next words
    private List<ListNode> wordList;

    // The starting "word"
    private String starter;

    // The random number generator
    private Random rnGenerator;

    public MarkovTextGeneratorLoL(Random generator) {
        wordList = new LinkedList<>();
        starter = "";
        rnGenerator = generator;
    }

    private ListNode getNodeOfWord(String word) {
        for (ListNode current : wordList) {
            if (current.getWord().equals(word)) {
                return current;
            }
        }

        ListNode newNode = new ListNode(word);
        wordList.add(newNode);

        return newNode;
    }

    @Override
    public void train(String sourceText) {
        if (sourceText.isEmpty()) {
            return;
        }

        String[] words = sourceText.split("\\s+");

        starter = words[0];
        String prevWord = starter;
        for (int k = 1; k < words.length; k++) {
            String currentWord = words[k];
            ListNode wordNode = getNodeOfWord(prevWord);
            if (wordNode != null) {
                wordNode.addNextWord(currentWord);
            } else {
                wordNode = new ListNode(prevWord);
                wordNode.addNextWord(currentWord);
                wordList.add(wordNode);
            }
            prevWord = currentWord;
        }
        ListNode nodeOfLastWord = getNodeOfWord(words[words.length - 1]);
        nodeOfLastWord.addNextWord(starter);
    }

    /**
     * Generate the number of words requested.
     */
    @Override
    public String generateText(int numWords) {
        if (wordList.size() == 0 || numWords == 0) {
            return "";
        }

        String currentWord = starter;
        String output = starter;
        while (numWords > 1) {
            ListNode wordNode = getNodeOfWord(currentWord);
            String randomWord = wordNode.getRandomNextWord(rnGenerator);
            output += " " + randomWord;
            currentWord = randomWord;
            numWords--;
        }

        return output;
    }


    @Override
    public String toString() {
        String toReturn = "";
        for (ListNode n : wordList) {
            toReturn += n.toString();
        }
        return toReturn;
    }

    @Override
    public void retrain(String sourceText) {
        wordList.clear();
//        starter = "";
        train(sourceText);
    }

    public static void main(String[] args) {
        // feed the generator a fixed random value for repeatable behavior
        MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
        String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
        System.out.println(textString);
        gen.train(textString);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
        String textString2 = "You say yes, I say no, " +
                "You say stop, and I say go, go, go, " +
                "Oh no. You say goodbye and I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "I say high, you say low, " +
                "You say why, and I say I don't know. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "Why, why, why, why, why, why, " +
                "Do you say goodbye. " +
                "Oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello. " +
                "You say yes, I say no, " +
                "You say stop and I say go, go, go. " +
                "Oh, oh no. " +
                "You say goodbye and I say hello, hello, hello. " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello, " +
                "I don't know why you say goodbye, I say hello, hello, hello,";
        System.out.println(textString2);
        gen.retrain(textString2);
        System.out.println(gen);
        System.out.println(gen.generateText(20));
    }
}

/**
 * Links a word to the next words in the list
 * You should use this class in your implementation.
 */
class ListNode {
    // The word that is linking to the next words
    private String word;

    // The next words that could follow it
    private List<String> nextWords;

    ListNode(String word) {
        this.word = word;
        nextWords = new LinkedList<>();
    }

    public String getWord() {
        return word;
    }

    void addNextWord(String nextWord) {
        nextWords.add(nextWord);
    }

    String getRandomNextWord(Random generator) {
        int randomInt = generator.nextInt(nextWords.size());

        return nextWords.get(randomInt);
    }

    public String toString() {
        String toReturn = word + ": ";
        for (String s : nextWords) {
            toReturn += s + "->";
        }
        toReturn += "\n";
        return toReturn;
    }

}


