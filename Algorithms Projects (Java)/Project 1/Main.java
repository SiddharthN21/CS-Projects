import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

//Tester class for visible testcases
public class Main {

    static double totalMysteryX = 0;
    static double totalXStack = 0;
    static double totalEliminateK = 0;
    static double totalSumTwoNumbers = 0;


    public static File[] extractNames(String part, String type) {
        File folder;
        File[] listOfFiles;
        folder = new File("Tests/"+part+"/input/"+type);
        listOfFiles = folder.listFiles();
        if (listOfFiles == null)
            listOfFiles = new File[0];
        return listOfFiles;
    }
    public static String nameret(File filename) {
        String[] components = filename.toString().split(Pattern.quote(File.separator));
        return components[components.length - 1];
    }

    public static void testEliminateK(File []fileNames,String type) throws IOException {
        if (fileNames == null || fileNames.length < 1) {
            System.out.println("EliminateK: couldn't find " + type + " test files");
            return;
        }

        for(int testIdx = 0; testIdx < fileNames.length; testIdx++) {
            File filen = fileNames[testIdx];
            EliminateK jpsus = new EliminateK();
            String fname = nameret(filen);
            Scanner expected = new Scanner(new File("Tests/EliminateK/expected/"+type+"/"+fname));
            Scanner scanner = new Scanner(filen);
            while (scanner.hasNextLine())
            {
                //just one line
                String line = scanner.nextLine();
                String []components = line.split(" ");
                int survivor = jpsus.computeSurvivor(Integer.parseInt(components[0]),Integer.parseInt(components[1]));
                String expsurvivor = expected.nextLine();
                if(Integer.parseInt(expsurvivor) == survivor)
                {
                    totalEliminateK += 1;
                }
                else
                {
                    System.out.println("EliminateK: failed " + type + " test " + (testIdx + 1));
                    System.out.println("\tReturned: " + survivor + ", expected: " + expsurvivor);
                }
            }
            scanner.close();
            expected.close();
        }
    }

    private static void checkDoubleList(SumTwoNumbers.Node head, String number) throws RuntimeException {
        String wrongValues = "Wrong values in doubly-linked list";
        String badLinks = "Incorrect links in doubly-linked list";

        // Check for correct value
        SumTwoNumbers.Node curr = head;
        SumTwoNumbers.Node tail = curr;
        for (char c : number.toCharArray()) {
            if (curr == null || curr.key != Character.getNumericValue(c))
                throw new RuntimeException(wrongValues);
            tail = curr;
            curr = curr.next;
        }

        totalSumTwoNumbers += 1;

        // Check for correct links
        if (head != null && head.previous != null)
            throw new RuntimeException(badLinks);
        if (tail != null && tail.next != null)
            throw new RuntimeException(badLinks);
        curr = head;
        while (curr != tail) {
            if (curr.next == null || curr.next.previous != curr)
                throw new RuntimeException(badLinks);
            curr = curr.next;
        }
        while (curr != head) {
            if (curr.previous == null || curr.previous.next != curr)
                throw new RuntimeException(badLinks);
            curr = curr.previous;
        }

        totalSumTwoNumbers += 1;
    }
    private static void checkSingleList(SumTwoNumbers.Node head, String number) throws RuntimeException {
        String wrongValues = "Wrong values in singly-linked list";
        String badLinks = "Incorrect links in singly-linked list";

        // Check for correct value
        SumTwoNumbers.Node curr = head;
        SumTwoNumbers.Node tail = curr;
        for (char c : number.toCharArray()) {
            if (curr == null || curr.key != Character.getNumericValue(c))
                throw new RuntimeException(wrongValues);
            tail = curr;
            curr = curr.next;
        }

        totalSumTwoNumbers += 4;

        // Check for correct links
        if (tail != null && tail.next != null)
            throw new RuntimeException(badLinks);
        curr = head;
        while (curr != tail) {
            if (curr.previous != null)
                throw new RuntimeException(badLinks);
            curr = curr.next;
        }

        totalSumTwoNumbers += 2;
    }
    public static void testSumTwoNumbers(File []fileNames, String type) throws IOException {
        if (fileNames == null || fileNames.length < 1) {
            System.out.println("SumTwoNumbers: couldn't find " + type + " test files");
            return;
        }

        for(int testIdx = 0; testIdx < fileNames.length; testIdx++) {
            File filen = fileNames[testIdx];
            String fname = nameret(filen);
            Scanner expected = new Scanner(new File("Tests/SumTwoNumbers/expected/"+type+"/"+fname));
            SumTwoNumbers sn = new SumTwoNumbers();
            Scanner scanner = new Scanner(filen);
            while (scanner.hasNextLine()) {
                String num1 = scanner.nextLine();
                String num2 = scanner.nextLine();
                SumTwoNumbers.Node addres = sn.add(num1,num2);
                String expsurvivor = expected.nextLine();

                boolean failed = false;
                String failedString = "SumTwoNumbers: failed " + type + " test " + (testIdx + 1);

                // Check first argument
                try {
                    checkDoubleList(sn.num1_list, num1);
                } catch (RuntimeException e) {
                    System.out.println(failedString);
                    failed = true;
                    System.out.println("\t" + e.getMessage());
                }

                // Check second argument
                try {
                    checkDoubleList(sn.num2_list, num2);
                } catch (RuntimeException e) {
                    if (!failed) {
                        System.out.println(failedString);
                        failed = true;
                    }
                    System.out.println("\t" + e.getMessage());
                }

                // Check return value
                try {
                    checkSingleList(addres, expsurvivor);
                } catch (RuntimeException e) {
                    if (!failed) {
                        System.out.println(failedString);
                        failed = true;
                    }
                    System.out.println("\t" + e.getMessage());
                }
            }
            scanner.close();
            expected.close();
        }
    }

    public static void testMysteryX(File []fileNames, String type) throws IOException {
        if (fileNames == null || fileNames.length < 1) {
            System.out.println("MysteryX: couldn't find " + type + " test files");
            return;
        }

        for(int testIdx = 0; testIdx < fileNames.length; testIdx++) {
            File filen = fileNames[testIdx];
            String fname = nameret(filen);
            Scanner expected = new Scanner(new File("Tests/MysteryX/expected/"+type+"/"+fname));
            MysteryX obmys = new MysteryX();
            Scanner scanner = new Scanner(filen);

            int resexp;
            int ival;

            int totalDFOps = 0;
            int totalDROps = 0;
            int totalGFOps = 0;
            int totalGROps = 0;
            int failedDFOps = 0;
            int failedDROps = 0;
            int failedGFOps = 0;
            int failedGROps = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                String[] tokens = line.split(" ");
                if (tokens.length < 1)
                    throw new IOException("Malformed input");

                switch (tokens[0]) {
                case "insertFront":
                    if (tokens.length < 2)
                        throw new IOException("Not enough arguments for insertFront");
                    obmys.insertFront(Integer.parseInt(tokens[1]));
                    break;

                case "insertRear":
                    if (tokens.length < 2)
                        throw new IOException("Not enough arguments for insertRear");
                    obmys.insertRear(Integer.parseInt(tokens[1]));;
                    break;

                case "deleteFront":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obmys.deleteFront();
                    if (ival != resexp)
                        failedDFOps += 1;
                    totalDFOps += 1;
                    break;

                case "deleteRear":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obmys.deleteRear();
                    if (ival != resexp)
                        failedDROps += 1;
                    totalDROps += 1;
                    break;

                case "getFront":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obmys.getFront();
                    if (ival != resexp)
                        failedGFOps += 1;
                    totalGFOps += 1;
                    break;

                case "getRear":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obmys.getRear();
                    if (ival != resexp)
                        failedGROps += 1;
                    totalGROps += 1;
                    break;

                default:
                    throw new IOException("Unrecognized command " + tokens[0]);
                }
            }

            int totalFailed = failedDFOps + failedDROps + failedGFOps + failedGROps;
            int totalOps = totalDFOps + totalDROps + totalGFOps + totalGROps;

            if (totalFailed > 0)
                System.out.println("MysteryX: failed " + type + " test " + (testIdx + 1));
            if (failedDFOps > 0)
                System.out.println("\tFailed " + failedDFOps + " / " + totalDFOps + " deleteFront operations");
            if (failedDROps > 0)
                System.out.println("\tFailed " + failedDROps + " / " + totalDROps + " deleteRear operations");
            if (failedGFOps > 0)
                System.out.println("\tFailed " + failedGFOps + " / " + totalGFOps + " getFront operations");
            if (failedGROps > 0)
                System.out.println("\tFailed " + failedGROps + " / " + totalGROps + " getRear operations");

            totalMysteryX += 1.0 - (float) totalFailed / (float) totalOps;

            scanner.close();
            expected.close();
        }
    }

    public static void testXStack(File []fileNames,String type) throws IOException {
        if (fileNames == null || fileNames.length < 1) {
            System.out.println("XStack: couldn't find " + type + " test files");
            return;
        }

        for(int testIdx = 0; testIdx < fileNames.length; testIdx++) {
            File filen = fileNames[testIdx];
            XStack obstack = new XStack();
            String fname = nameret(filen);
            Scanner expected = new Scanner(new File("Tests/XStack/expected/"+type+"/"+fname));
            Scanner scanner = new Scanner(filen);

            int resexp;
            int ival;

            int totalPopOps = 0;
            int totalPeekOps = 0;
            int failedPopOps = 0;
            int failedPeekOps = 0;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().strip();
                String[] tokens = line.split(" ");
                if (tokens.length < 1)
                    throw new IOException("Malformed input");

                switch (tokens[0]) {
                case "push":
                    if (tokens.length < 2)
                        throw new IOException("Not enough arguments for push");
                    obstack.push(Integer.parseInt(tokens[1]));
                    break;

                case "pop":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obstack.pop();
                    if (ival != resexp)
                        failedPopOps += 1;
                    totalPopOps += 1;
                    break;

                case "peek":
                    resexp = Integer.parseInt(expected.nextLine().strip());
                    ival = obstack.peek();
                    if (ival != resexp)
                        failedPeekOps += 1;
                    totalPeekOps += 1;
                    break;

                default:
                    throw new IOException("Unrecognized command " + tokens[0]);
                }
            }

            int totalFailed = failedPopOps + failedPeekOps;
            int totalOps = totalPopOps + totalPeekOps;

            if (totalFailed > 0)
                System.out.println("XStack: failed " + type + " test " + (testIdx + 1));
            if (failedPopOps > 0)
                System.out.println("\tFailed " + failedPopOps + " / " + totalPopOps + " pop operations");
            if (failedPeekOps > 0)
                System.out.println("\tFailed " + failedPeekOps + " / " + totalPeekOps + " peek operations");

            totalXStack += 1.0 - (float) totalFailed / (float) totalOps;

            scanner.close();
            expected.close();
        }
    }

    public static void main(String[] args) throws IOException {

        File[] listOfFilesEliminateKSmall, listOfFilesEliminateKMedium, listOfFilesEliminateKLarge;
        File[] listOfFilesSumTwoNumbersSmall, listOfFilesSumTwoNumbersMedium, listOfFilesSumTwoNumbersLarge;
        File[] listOfFilesMysteryXSmall, listOfFilesMysteryXMedium, listOfFilesMysteryXLarge;
        File[] listOfFilesXStackSmall, listOfFilesXStackMedium, listOfFilesXStackLarge;


        /*PART 1 Testing - Small, Medium, Large*/
        listOfFilesEliminateKSmall = extractNames("EliminateK","small");
        listOfFilesEliminateKMedium = extractNames("EliminateK","medium");
        listOfFilesEliminateKLarge = extractNames("EliminateK","large");
        /*Call tests*/
        System.out.println("Testing EliminateK");
        testEliminateK(listOfFilesEliminateKSmall,"small");
        testEliminateK(listOfFilesEliminateKMedium,"medium");
        testEliminateK(listOfFilesEliminateKLarge,"large");
        System.out.println();


        /*PART 2 Testing - Small, Medium, Large*/
        listOfFilesSumTwoNumbersSmall = extractNames("SumTwoNumbers","small");
        listOfFilesSumTwoNumbersMedium = extractNames("SumTwoNumbers","medium");
        listOfFilesSumTwoNumbersLarge = extractNames("SumTwoNumbers","large");
        /*Call tests*/
        System.out.println("Testing SumTwoNumbers");
        testSumTwoNumbers(listOfFilesSumTwoNumbersSmall,"small");
        testSumTwoNumbers(listOfFilesSumTwoNumbersMedium,"medium");
        testSumTwoNumbers(listOfFilesSumTwoNumbersLarge,"large");
        System.out.println();


        /*PART 3 Testing - Small, Medium, Large*/
        listOfFilesMysteryXSmall = extractNames("MysteryX","small");
        listOfFilesMysteryXMedium = extractNames("MysteryX","medium");
        listOfFilesMysteryXLarge = extractNames("MysteryX","large");
        /*Call tests*/
        System.out.println("Testing MysteryX");
        testMysteryX(listOfFilesMysteryXSmall,"small");
        testMysteryX(listOfFilesMysteryXMedium,"medium");
        testMysteryX(listOfFilesMysteryXLarge,"large");
        System.out.println();


        /*PART 4 Testing - Small, Medium, Large*/
        listOfFilesXStackSmall = extractNames("XStack","small");
        listOfFilesXStackMedium = extractNames("XStack","medium");
        listOfFilesXStackLarge = extractNames("XStack","large");
        /*Call tests*/
        System.out.println("Testing XStack");
        testXStack(listOfFilesXStackSmall,"small");
        testXStack(listOfFilesXStackMedium,"medium");
        testXStack(listOfFilesXStackLarge,"large");
        System.out.println();


        // Calculate scores
        int weightEliminateK = 15;
        int pointsPerTestEliminateK = 1;
        int numTestsEliminateK = listOfFilesEliminateKSmall.length + listOfFilesEliminateKMedium.length + listOfFilesEliminateKLarge.length;
        int maxEliminateK = pointsPerTestEliminateK * numTestsEliminateK;
        int scoreEliminateK = (int) Math.floor(totalEliminateK / maxEliminateK * weightEliminateK);

        int weightSumTwoNumbers = 30;
        int pointsPerTestSumTwoNumbers = 10;
        int numTestsSumTwoNumbers = listOfFilesSumTwoNumbersSmall.length + listOfFilesSumTwoNumbersMedium.length + listOfFilesSumTwoNumbersLarge.length;
        int maxSumTwoNumbers = pointsPerTestSumTwoNumbers * numTestsSumTwoNumbers;
        int scoreSumTwoNumbers = (int) Math.floor(totalSumTwoNumbers / maxSumTwoNumbers * weightSumTwoNumbers);

        int weightMysteryX = 40;
        int pointsPerTestMysteryX = 1;
        int numTestsMysteryX = listOfFilesMysteryXSmall.length + listOfFilesMysteryXMedium.length + listOfFilesMysteryXLarge.length;
        int maxMysteryX = pointsPerTestMysteryX * numTestsMysteryX;
        int scoreMysteryX = (int) Math.floor(totalMysteryX / maxMysteryX * weightMysteryX);

        int weightXStack = 15;
        int pointsPerTestXStack = 1;
        int numTestsXStack = listOfFilesXStackSmall.length + listOfFilesXStackMedium.length + listOfFilesXStackLarge.length;
        int maxXStack = pointsPerTestXStack * numTestsXStack;
        int scoreXStack = (int) Math.floor(totalXStack / maxXStack * weightXStack);

        /*Print totals*/
        System.out.println("Total score for EliminateK:    " + scoreEliminateK + " / " + weightEliminateK);
        System.out.println("Total score for SumTwoNumbers: " + scoreSumTwoNumbers + " / " + weightSumTwoNumbers);
        System.out.println("Total score for MysteryX:      " + scoreMysteryX + " / " + weightMysteryX);
        System.out.println("Total score for XStack:        " + scoreXStack + " / " + weightXStack);
    }
}
