package by.xCaptin.collections.arraylist;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class MyArrayListTest {

    static String FILEPATH = "C:\\Users\\User\\IdeaProjects\\AstonTaskCollection\\src\\test\\java\\by\\xcaptin\\collections\\values";
    private static MyArrayList<Integer> myArrayList;
    static Collection<Integer> numberListTest;
    static int numberCapacityTest;
    static int[] numbersArrayForMethodAddTest;


    @Test
    void testCreateEmptyConstructor() {
        myArrayList = new MyArrayList<>();
        assertEquals(myArrayList.getCapacity(), myArrayList.getValues().length);
    }

    @Test
    void testCreateConstructorWithCollection() {
        fileStringReaderForCollection();
        myArrayList = new MyArrayList<>(numberListTest);
        assertEquals(numberListTest.size(), myArrayList.size());
    }

    @Test
    void testCreateConstructorWithCapacity() {
        fileElementsStringReaderForInteger();
        myArrayList = new MyArrayList<>(numberCapacityTest);
        assertEquals(numberCapacityTest, myArrayList.getValues().length);
    }

    @Test
    void testAddAndSize() {
        myArrayList = new MyArrayList<>();
        fileStringReaderForMethodAdd();

        int count = 0;
        for (int number : numbersArrayForMethodAddTest) {
            myArrayList.add(number);
            assertEquals(++count, myArrayList.size());
        }
    }

    @Test
    void testAddAtIndex() {
        int n = 7;
        Random random = new Random();
        testAddAndSize();
        int randomNumber = random.nextInt(myArrayList.size() - 1);
        myArrayList.add(n, randomNumber);
        assertEquals(numbersArrayForMethodAddTest[numbersArrayForMethodAddTest.length - 1], myArrayList.get(myArrayList.size() - 1));
        assertEquals(n, myArrayList.get(randomNumber));
    }


    @Test
    void testRemoveByIndex() {
        Random random = new Random();
        testAddAndSize();
        int size = myArrayList.size();
        int randomNumber = random.nextInt(myArrayList.size() - 1);
        myArrayList.remove(randomNumber);

        assertEquals(size - 1, myArrayList.size());
    }


    @Test
    void testRemoveByValue() {
        testAddAndSize();
        int value = Arrays.stream(numbersArrayForMethodAddTest).boxed()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("The array is empty"));

        myArrayList.remove(Integer.valueOf(value));

        assertEquals(numbersArrayForMethodAddTest.length - 1, myArrayList.size());
    }

    @Test
    void testGet() {
        Random random = new Random();
        testAddAndSize();
        int randomNumber = random.nextInt(myArrayList.size() - 1);

        assertEquals(numbersArrayForMethodAddTest[randomNumber], myArrayList.get(randomNumber));
    }

    @Test
    void testClear() {
        testAddAndSize();

        myArrayList.clear();
        assertEquals(0, myArrayList.size());
    }

    @Test
    void testSort() {
        testAddAndSize();

        myArrayList.sort();
        for (int i = 0; i < myArrayList.size(); i++) {
            System.out.print(myArrayList.get(i) + " ");
        }
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

    public static void fileElementsStringReaderForInteger() {
        String filePath = FILEPATH + "\\CapacityElement";

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line = bufferedReader.readLine();
            numberCapacityTest = stringToInt(line);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int stringToInt(String numberString) {
        return Integer.parseInt(numberString);
    }


    public static void fileStringReaderForMethodAdd() {
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

}




