import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HadoopSim implements Runnable{

    private final int SIZE = 100; //Final integer for the size of the array of ints.
    private int count = 0; //Count of numbers in the array.
    private int sum = 0; //The sum of all numbers in the array.
    private double sumDiffsSq = 0.0; // the sum of the difference of squares.
    private double newMean = 0.0; //The new mean of the combined lists.
    private int[] intArray = new int[SIZE]; // The integer array to be used for the list.
    private File file; //The file to be read.
    private Scanner fileRead = null; //The scanner used to read the file.
    private FileInputStream fileIn = null; //The file input stream to be passed into the scanner.
    private String fileName; //The user provided filename.

    public HadoopSim(String name) {
        fileName = name;
        file = new File(fileName);
        try {
            //Sets the FileInputStream to read the file...
            fileIn = new FileInputStream(file);
            //Uses the Scanner to read the FileInputStream...
            fileRead = new Scanner(fileIn);
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: FIleNotFoundException.");
            System.exit(0);
        }
        while (fileRead.hasNextInt()) {
            intArray[count++] = fileRead.nextInt();
        }
    }

    //Setters and Getters...
    public void setSumDiffsSq() {
        double tempSum = 0; //Used to hold the temporary sum used in the process.

        for (int i = 0; i < count; i++) {
            double currentNum = Math.pow(((double)intArray[i] - newMean), 2); // Subtract the mean from each number in the set and square the differences.
            tempSum = tempSum + currentNum; // Sum up all of the squares of differences.
        }
        this.sumDiffsSq = tempSum;
    }
    public double getSumDiffsSq() {
        return sumDiffsSq;
    }
    public void setNewMean(double newMean) {
        this.newMean = newMean;
    }
    public double getNewMean() {
        return newMean;
    }
    public int getCount() {
        return count;
    }
    public int getSum() {
        return sum;
    }

    public void run(){
        for (int i = 0; i < intArray.length; i++){
            sum = sum + intArray[i];
        }
    }
}
