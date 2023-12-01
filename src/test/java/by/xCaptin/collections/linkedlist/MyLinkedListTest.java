package by.xCaptin.collections.linkedlist;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    static String FILEPATH = "src/main/resources/values";
    private static MyLinkedList<Integer> myLinkedListInteger;
    private static MyLinkedList<String> myLinkedListString;
    private static MyLinkedList<Double> myLinkedListDouble;
    static Collection<Integer> numberListTest;

    static int[] numbersArrayForMethodAddTest;
    static String[] lettersArrayForMethodAddTest;
    static double[] doubleArrayForMethodAddTest;

    @Test
    void testCreateEmptyConstructor() {
        myLinkedListInteger = new MyLinkedList<>();
        assertEquals(0, myLinkedListInteger.size());
    }

    @Test
    void testCreateConstructorWithCollection() {
        fileStringReaderForCollection();
        myLinkedListInteger = new MyLinkedList<>(numberListTest);
        assertEquals(numberListTest.size(), myLinkedListInteger.size());
    }

    @Test
    void testAddForInteger() {
        myLinkedListInteger = new MyLinkedList<>();
        fileElementsStringReaderForMethodAdd();

        for (int number : numbersArrayForMethodAddTest) {
            myLinkedListInteger.add(number);
        }

        assertEquals(Arrays.toString(numbersArrayForMethodAddTest), Arrays.toString(myLinkedListInteger.toArray()));
    }

    @Test
    void testAddForString() {
        myLinkedListString = new MyLinkedList<>();
        fileLettersStringReaderForMethodAdd();

        for (String letter : lettersArrayForMethodAddTest) {
            myLinkedListString.add(letter);
        }

        assertEquals(Arrays.toString(lettersArrayForMethodAddTest), Arrays.toString(myLinkedListString.toArray()));
    }

    @Test
    void testAddForDouble() {
        myLinkedListDouble = new MyLinkedList<>();
        fileFractionalElementsStringReaderForMethodAdd();

        for (double number : doubleArrayForMethodAddTest) {
            myLinkedListDouble.add(number);
        }

        assertEquals(Arrays.toString(doubleArrayForMethodAddTest), Arrays.toString(myLinkedListDouble.toArray()));
    }

    @Test
    void testAddAtIndex() {
        int number = 128;
        Random random = new Random();
        testAddForInteger();
        int randomNumber = random.nextInt(myLinkedListInteger.size() - 1);

        myLinkedListInteger.add(number, randomNumber);

        assertNotEquals(Arrays.toString(numbersArrayForMethodAddTest), Arrays.toString(myLinkedListInteger.toArray()));
        assertEquals(number, myLinkedListInteger.get(randomNumber));

    }

    @Test
    void testAddAll() {
        fileStringReaderForCollection();
        fileElementsStringReaderForMethodAdd();
        myLinkedListInteger = new MyLinkedList<>();
        myLinkedListInteger.addAll(numberListTest);

        assertEquals(numbersArrayForMethodAddTest.length, myLinkedListInteger.size());
    }

    @Test
    void testAddAndRemoveByIndex() {
        Random random = new Random();
        testAddForInteger();
        int randomNumber = random.nextInt(myLinkedListInteger.size() - 1);
        myLinkedListInteger.remove(randomNumber);

        assertNotEquals(Arrays.toString(numbersArrayForMethodAddTest), Arrays.toString(myLinkedListInteger.toArray()));
        assertNotEquals(numbersArrayForMethodAddTest[randomNumber], myLinkedListInteger.get(randomNumber));
    }

    @Test
    void testAddAndRemoveByElement() {
        Random random = new Random();

        testAddForString();

        int randomNumber = random.nextInt(myLinkedListString.size() - 1);

        String letterBeforeImplementation = myLinkedListString.get(randomNumber);
        myLinkedListString.remove(letterBeforeImplementation);
        String letterAfterImplementation = myLinkedListString.get(randomNumber);

        assertEquals(lettersArrayForMethodAddTest.length - 1, myLinkedListString.size());
        assertNotEquals(letterBeforeImplementation, letterAfterImplementation);
    }

    @Test
    void testAddAndGetRandomIndex() {
        Random random = new Random();

        testAddForDouble();

        int randomNumber = random.nextInt(myLinkedListDouble.size() - 1);
        assertEquals(myLinkedListDouble.get(randomNumber), myLinkedListDouble.get(randomNumber));

    }

    @Test
    void testAddAndClearAllElements() {
        int checkSize = 0;

        testAddForInteger();

        myLinkedListInteger.clear();
        assertEquals(checkSize, myLinkedListInteger.size());
    }

    @Test
    void testAddAndCheckSize() {
        testAddForDouble();

        assertEquals(doubleArrayForMethodAddTest.length, myLinkedListDouble.size());
    }


    @Test
    void testAddAndSortElements() {
        testAddForInteger();

        myLinkedListInteger.sort();

        for (int i = 0; i < myLinkedListInteger.size(); i++) {
            System.out.print(myLinkedListInteger.get(i) + " ");
        }
        assertEquals(Arrays.stream(numbersArrayForMethodAddTest).min()
                .orElseThrow(NoSuchElementException::new), myLinkedListInteger.get(0));
        assertEquals(Arrays.stream(numbersArrayForMethodAddTest).max()
                .orElseThrow(NoSuchElementException::new), myLinkedListInteger.get(myLinkedListInteger.size() - 1));
    }

    public static void fileStringReaderForCollection() {
        String filePath = FILEPATH + "\\ListOfElements";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line) {
        numberListTest = stringToCollection(line);
    }

    private static Collection<Integer> stringToCollection(String numbersString) {
        Collection<Integer> numberList = new ArrayList<>();

        StringTokenizer tokenizer = new StringTokenizer(numbersString);
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            int number = Integer.parseInt(token);
            numberList.add(number);
        }

        return numberList;
    }

    public static void fileElementsStringReaderForMethodAdd() {
        String filePath = FILEPATH + "\\ListOfElements";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            numbersArrayForMethodAddTest = stringToIntArray(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] stringToIntArray(String numbersString) {
        String[] numberStrings = numbersString.split(" ");
        int[] numbersArray = new int[numberStrings.length];

        for (int i = 0; i < numberStrings.length; i++) {
            numbersArray[i] = Integer.parseInt(numberStrings[i]);
        }

        return numbersArray;
    }


    public static void fileLettersStringReaderForMethodAdd() {
        String filePath = FILEPATH + "\\ListOfLetters";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            lettersArrayForMethodAddTest = line.split(" ");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void fileFractionalElementsStringReaderForMethodAdd() {
        String filePath = FILEPATH + "\\ListOfFractionalElement";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            doubleArrayForMethodAddTest = stringToDoubleArray(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double[] stringToDoubleArray(String numbersString) {
        String[] numberStrings = numbersString.split(" ");
        double[] numbersArray = new double[numberStrings.length];

        for (int i = 0; i < numberStrings.length; i++) {
            numbersArray[i] = Double.parseDouble(numberStrings[i]);
        }

        return numbersArray;
    }
}
