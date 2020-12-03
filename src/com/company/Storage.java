package com.company;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Storage {

    private List<Circle> circleList = new ArrayList<Circle>();
    private List<Cone> coneList = new ArrayList<Cone>();
    private  static Random rnd = new Random();

    public void AddFigure(Circle circle){
        circleList.add(circle);
    }

    public void AddFigure(Cone cone){
        coneList.add(cone);
    }

    public List GetFigureList(Class<?> figureClass){
        if(figureClass == Circle.class)
            return circleList;
        else if(figureClass == Cone.class)
            return coneList;
        else {
            System.out.println("Error! Figure storage doesn't contains a class (" + figureClass.toString() + ") !");
            return null;
        }
    }

    public void fillListRandomly(Class<?> figureClass, int count){
        if(figureClass == Circle.class){
            for(int i = 0; i < count; i++){
                Circle circle = getRandomCircle();
                AddFigure(circle);
            }
        }
        else if(figureClass == Cone.class){
            for(int i = 0; i < count; i++) {
                Cone cone = getRandomCone();
                AddFigure(cone);
            }
        }
        else{
            System.out.println("Error! Figure storage doesn't contains a class (" + figureClass.toString() + ") !");
        }
    }

    public void printList(Class<?> figureClass){
        List list = GetFigureList(figureClass);
        if(list == null || list.isEmpty() || !(list.get(0) instanceof IFigure))
        {
            System.out.println("Error! Can't print a figures list!");
        }
        else
        {
            int counter = 0;
            for (Object f : list)
            {
                counter++;
                System.out.println((counter) + ".\n" + ((IFigure)f).getInfo() + "\n");
            }
        }
    }

    public void clear()
    {
        circleList.clear();
        coneList.clear();
    }

    public void Save(String fileName){
        try(BufferedWriter out = new BufferedWriter(new FileWriter(fileName)))
        {
            if(circleList != null){
                for(Circle sqr : circleList){
                    out.write(String.valueOf(sqr.Radius));
                    out.newLine();
                }

                out.write(";");
                out.newLine();
            }

            if(coneList != null){
                for(Cone prd : coneList){
                    out.write(String.valueOf(prd.Radius));
                    out.newLine();
                    out.write(String.valueOf(prd.Height));
                }

                out.write(";");
                out.newLine();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Load(String fileName){

        this.clear();

        try(Scanner snr = new Scanner(new FileReader(fileName)))
        {
            while(snr.hasNextLine())
            {
                try
                {
                    float f = Float.parseFloat(snr.nextLine());
                    circleList.add(new Circle(f));
                }
                catch (NumberFormatException e)
                {
                    break;
                }
            }
            while(snr.hasNextLine())
            {
                try
                {
                    float s = Float.parseFloat(snr.nextLine());
                    float h = Float.parseFloat(snr.nextLine());
                    coneList.add(new Cone(s, h));
                }
                catch (NumberFormatException e)
                {
                    break;
                }
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Circle getRandomCircle()
    {
        return new Circle((double)rnd.nextInt(20) + 1);
    }

    public static Cone getRandomCone()
    {
        return new Cone((float)rnd.nextInt(20) + 1, (float)rnd.nextInt(20) + 1);
    }

    public void Backup(String mainFileName){
        File f = new File(mainFileName);

        if(!f.exists())
            return;

        String backupName = "save-";
        backupName += new SimpleDateFormat("dd.MM.yyyy-HH.mm.ss").format(new Date());
        backupName += Main.FILES_EXTENSION;

        Load(mainFileName);
        Save(backupName);
        clear();
    }

    public static File[] GetSavesList(String directory, String extension){
        File dir = new File(directory);
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(extension);
            }
        });

        return files;
    }

}
