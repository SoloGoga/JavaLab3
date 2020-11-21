package com.company;

import java.io.File;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static int A, B;
    private static Random random = new Random();
    private static Storage banka = new Storage();

    public static final String MAIN_FILE_NAME = "last_save.lab";
    public static final String FILES_EXTENSION = ".lab";

    public static void main(String[] args) {

        System.out.println("Available files:");
        File[] saves = Storage.GetSavesList(".", FILES_EXTENSION);
        int counter = 0;
        for (File f : saves)
        {
            ++counter;
            System.out.println(counter + ". " + f.getName());
        }

        banka.Backup(MAIN_FILE_NAME);

        boolean flag = counter > 0;
        if(!flag)
        {
            flag = true;
            System.out.println("No suitable files found\n");
        }
        else
        {
            System.out.println("\nEnter a number of file to load. Enter '0' to skip.");
            int f = readInteger(0, counter);
            if (f != 0)
            {
                //load file
                banka.Load(saves[f - 1].getName());
                flag = false;
            }
            else flag = true;
        }

        if(flag)
        {
            //handle user input
            System.out.println("Enter B value");
            B = readInteger();
            System.out.println("Enter A value");
            A = readInteger();

            //fill storage with figures
            banka.fillListRandomly(Circle.class, B);
            banka.fillListRandomly(Cone.class, A);
        }

        System.out.println("Triangles list:");
        banka.printList(Circle.class);
        System.out.println("Prisms list:");
        banka.printList(Cone.class);

        Task(Circle.class);
        Task(Cone.class);

        banka.Save(MAIN_FILE_NAME);
        System.out.println("Data saved to file '" + MAIN_FILE_NAME + "'");
    }

    public static void Task(Class<?> figureClass)
    {
        if(figureClass == Circle.class)
        {
            System.out.println("Processing the Triangles...");
            List<Circle> list = banka.GetFigureList(figureClass);
            if(list != null)
            {
                float averageCircle = 0;
                for (Circle tr : list)
                {
                    averageCircle += tr.getArea();
                }
                averageCircle /= list.size();
                System.out.println("Average square equals " + averageCircle);

                int lowerSquareValuesCount = 0;
                for (Circle tr : list)
                {
                    if (tr.getArea() < averageCircle) lowerSquareValuesCount++;
                }
                System.out.println("The amount of Triangles with lower square values equals " + lowerSquareValuesCount + "\n");
            }
        }
        else if(figureClass == Cone.class)
        {
            System.out.println("Processing the Prisms...");
            List<Cone> list = banka.GetFigureList(figureClass);
            if(list != null)
            {
                double maxConeVolume = 0;
                int coneNumber = 1;
                for(Cone cone : list){
                    if(maxConeVolume < cone.GetVolume()) {
                        maxConeVolume = cone.GetVolume();
                        coneNumber++;
                    }
                }
                System.out.println("Cone numbered " + coneNumber + " has the largest area: " + maxConeVolume);
            }
        }
        else
        {
            System.out.println("Error! No task for class <" + figureClass.toString() + ">");
        }
    }

    public static int readInteger()
    {
        return readInteger(1, 9999);
    }

    public static int readInteger(int minValue, int maxValue) {
        Scanner input = new Scanner(System.in);
        while (true) {
            try {
                int result = Integer.parseInt(input.next());
                if (result >= minValue) {
                    if (result <= maxValue) return result;
                    else {
                        System.out.println("Value must be less than " + (maxValue + 1));
                    }
                } else {
                    System.out.println("Value must be greater than " + (minValue - 1));
                }
            } catch (Exception e) {
                System.out.println("Enter a number, please");
            }
        }
    }
}
