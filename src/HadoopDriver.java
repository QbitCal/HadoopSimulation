import java.util.Scanner;

/**
 * This program simulates the Hadoop Big Data technique by
 * using Threads to process data in smaller segments.
 *
 *
 * @author Quenten Calvano
 * @version 10/19/2020
 */

public class HadoopDriver {

    public static void main(String[] args) {

        int totalSum = 0; //The variable to hold the sum of each thread.
        int totalCount = 0; //The variable to hold the count of each thread.
        double overallMean = 0; // The final mean of the results of the totalSum and totalCount.

        Scanner scan1 = new Scanner(System.in);//Scanner for the first file name.
        Scanner scan2 = new Scanner(System.in);//Scanner for the second file name.
        Scanner scan3 = new Scanner(System.in);//Scanner for the third file name.

        String file1;//String for the first file name.
        String file2;//String for the second file name.
        String file3;//String for the third file name.

        //Take user input...
        System.out.println("Enter the name of the first file: ");
        file1 = scan1.nextLine();
        System.out.println("Enter the name of the second file: ");
        file2 = scan2.nextLine();
        System.out.println("Enter the name of the third file: ");
        file3 = scan3.nextLine();

        HadoopSim hadoop1 = new HadoopSim(file1);//Create the first HadoopSim Object.
        HadoopSim hadoop2 = new HadoopSim(file2);//Create the second HadoopSim Object.
        HadoopSim hadoop3 = new HadoopSim(file3);//Create the third HadoopSim Object.

        Thread thread1 = new Thread(hadoop1);//Create a thread for hadoop1.
        Thread thread2 = new Thread(hadoop2);//Create a thread for hadoop2.
        Thread thread3 = new Thread(hadoop3);//Create a thread for hadoop3.

        try {
            //Execute the threads...
            thread1.start();
            thread2.start();
            thread3.start();
        }catch (IllegalThreadStateException e) {
            System.out.println("ERROR: IllegalThreadStateException.");
            System.exit(0);
        }
        try {
            //Make the threads wait until the others are finished...
            thread1.join();
            thread2.join();
            thread3.join();
        } catch(InterruptedException e){
            System.out.println("ERROR: InterruptedException.");
            System.exit(0);
        } catch (IllegalThreadStateException e) {
            System.out.println("ERROR: IllegalThreadStateException.");
            System.exit(0);
        }

        //Print the counts and sums...
        System.out.println("Task 1 returns: Count = " + hadoop1.getCount() + ", Sum = " + hadoop1.getSum());
        System.out.println("Task 2 returns: Count = " + hadoop2.getCount() + ", Sum = " + hadoop2.getSum());
        System.out.println("Task 3 returns: Count = " + hadoop3.getCount() + ", Sum = " + hadoop3.getSum());

        //Find the total sum, total count, and overall mean...
        totalSum = hadoop1.getSum() + hadoop2.getSum() + hadoop3.getSum();
        totalCount = hadoop1.getCount() + hadoop2.getCount() + hadoop3.getCount();
        overallMean = (double)totalSum / (double)totalCount;

        //Print the new mean...
        System.out.println("Driver newMean: " + overallMean);

        //Set the new mean for each of the HadoopSim objects...
        hadoop1.setNewMean(overallMean);
        hadoop2.setNewMean(overallMean);
        hadoop3.setNewMean(overallMean);

        //Use the HadoopSim Class to find the values to be added together (differences of squares)...
        hadoop1.setSumDiffsSq();
        hadoop2.setSumDiffsSq();
        hadoop3.setSumDiffsSq();
        double diffSquare1 = hadoop1.getSumDiffsSq(); //The difference of squares for hadoop1.
        double diffSquare2 = hadoop2.getSumDiffsSq(); //The difference of squares for hadoop2.
        double diffSquare3 = hadoop3.getSumDiffsSq(); //The difference of squares for hadoop3.
        double totalDiffSquare = diffSquare1 + diffSquare2 + diffSquare3; //Sum of the Differences of squares of each.
        double stanDeviation = Math.sqrt(totalDiffSquare / ((double)totalCount - 1.0)); //The final standard deviation of the numbers.

        //Print the descriptive output for results...
        System.out.println("Driver sumDiffsSq: " + diffSquare1 + " " + diffSquare2 + " " + diffSquare3);
        System.out.println("Totals: Sum = " + totalSum + ", Count = " + totalCount + ", sumDiffsSq = " + totalDiffSquare);
        System.out.printf("Average = %.2f\n", overallMean);
        System.out.printf("Standard Deviation = %.2f", stanDeviation);

    }
}
