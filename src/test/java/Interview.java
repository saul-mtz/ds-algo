import java.lang.Integer;
import java.util.*;

import java.util.Collections;

class Interview {

	public static void main(String[] args) {
        arrayList();

        printName("Stack");
        stack();

        printName("java.util.HashMap");
        hashMap();

        printName("java.util.HashTee");
        treeMap();

        printName("String");
        string();
	}

    private static void printName(String path) {
        System.out.println(path);
    }

    /**
     * ArrayList tests
     */
    private static void arrayList() {
        printName("java.util.ArrayList");

        List<Integer> numbers = new ArrayList<Integer>();
        Random random = new Random();


        int testBinarySearchNumber = 2;
        numbers.add(testBinarySearchNumber);
        for (int i = 0; i < 10; i ++) {
            // number from 0 to 9
            numbers.add(random.nextInt(10));
        }

        System.out.println("ArrayList: " + numbers);

        Collections.sort(numbers);
        System.out.println("Sorted: " + numbers);

        System.out.println(testBinarySearchNumber + " index: " + Collections.binarySearch(numbers, testBinarySearchNumber));
        System.out.println("10 not found: " + Collections.binarySearch(numbers, 10));

        StringBuilder stringBuilder = new StringBuilder("[");
        for (Integer number : numbers) {
            stringBuilder.append(number);
            stringBuilder.append(", ");
        }

        // use delete to get rid of the whole substring
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        System.out.println("Iterate with for each: " + stringBuilder);

        stringBuilder = new StringBuilder("[");
        Iterator<Integer> it = numbers.iterator();

        while (it.hasNext()) {
            stringBuilder.append(it.next());
            stringBuilder.append(", ");
        }

        // delete the chars one by one
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        stringBuilder.append("]");

        System.out.println("Iterate with Iterator: " + stringBuilder);

        stringBuilder = new StringBuilder("[");
        final StringBuilder finalStringBuilder = stringBuilder;
        numbers.forEach((number) -> {
            finalStringBuilder.append(number);
            finalStringBuilder.append(", ");
        });
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
        stringBuilder.append("]");
        System.out.println("Iterate with forEach:  " + stringBuilder);

        System.out.println("Minimum: " + Collections.min(numbers));
        System.out.println("Maximum: " + Collections.max(numbers));

        Collections.reverse(numbers);
        System.out.println("Reversed: " + numbers);

        Collections.shuffle(numbers);
        System.out.println("Shuffled: " + numbers);

        numbers.addAll(Arrays.asList(11, 12));
        System.out.println("Add array 1: " + numbers);
        numbers.addAll(Arrays.asList(new Integer[]{13, 14}));
        System.out.println("Add array 2: " + numbers);
        System.out.println("To array: " + numbers.toArray());

        System.out.println();
    }

    private static void stack() {
        Stack stack = new Stack <String>();

        stack.push("Two");
        stack.push("Three");
        stack.push("One");

        System.out.println("Stack Test: " + stack);

        System.out.println("Peek: " + stack.peek() + ", " + stack);
        System.out.println("Pop : " + stack.pop() + ", " + stack);

        stack.forEach((str) -> {
            System.out.println(str);
        });

        System.out.println();
    }

    private static void hashMap() {
        Map<String, String> table = new HashMap<String, String>();
        table.put("One", "One val");
        table.put("Two", "Two val");
        table.put("Three", "Three val");

        System.out.println("HashMap:" + table);

        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, String> entry : table.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        System.out.println("Iterate with foreach entrySet(): " + sb + "}");

        sb = new StringBuilder("{");
        for (String key : table.keySet()) {
            sb.append(key);
            sb.append("=");
            sb.append(table.get(key));
            sb.append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());
        System.out.println("Iterate with foreach keySet():   " + sb + "}");

        sb = new StringBuilder("{");
        final StringBuilder sbFinal = sb;
        table.forEach((key, value) -> {
            sbFinal.append(key);
            sbFinal.append("=");
            sbFinal.append(value);
            sbFinal.append(", ");
        });
        sb.delete(sb.length() - 2, sb.length());
        System.out.println("Iterate with forEach         :   " + sb + "}");

        table.remove("One");
        System.out.println("'One' key removed: " + table);
        System.out.println("Contains key Two?:  " + table.containsKey("Two"));
        System.out.println("Contains key Four?: " + table.containsKey("Four"));
        System.out.println("Contains value 'One val'?: " + table.containsValue("One val"));
        System.out.println("Contains value 'One Val'?: " + table.containsValue("One Val"));
        System.out.println();
    }

    private static void treeMap() {
        Map<String, String> table = new TreeMap<String, String>();
        table.put("One", "One val");
        table.put("Two", "Two val");
        table.put("Three", "Three val");

        System.out.println("TreeMap:" + table);
        System.out.println();
    }

    private static void string() {
        String testStr = "Hola, Java";

        System.out.println("Test String: " + testStr);
        System.out.println("Find character 'i'                       : " + testStr.contains("i"));
        System.out.printf("Find character 'a': %b, at index       : %d\n", -1 != testStr.indexOf('a'), testStr.indexOf('a'));
        System.out.printf("Contains substring 'ola'?: %b, at index: %d\n", testStr.contains("ola"), testStr.indexOf("ola"));
        System.out.println("Substring(2)                             : " + testStr.substring(2));
        System.out.println("Substring(2,4)                           : " + testStr.substring(2, 4));
        System.out.println("Reversed                                 : " + new StringBuilder(testStr).reverse());
        System.out.println("Split by ','                             : " + Arrays.asList(testStr.split(",")));
        System.out.println("Delete first 'a'                         : " + new StringBuilder(testStr).deleteCharAt(testStr.indexOf('a')));
        System.out.println("Delete last 'a'                          : " + new StringBuilder(testStr).deleteCharAt(testStr.lastIndexOf('a')));
        System.out.println("Add H at beginning                       : " + new StringBuilder(testStr).insert(0, "H"));
    }

}
