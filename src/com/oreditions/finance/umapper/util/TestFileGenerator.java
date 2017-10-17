package com.oreditions.finance.umapper.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;


/**
 *
 * @author HO.OREY
 */
public class TestFileGenerator
{
    public static void main(String args[])
    {
        String filename = "D:\\oreyboulot\\Dev\\java\\UMapper\\dist2\\aggsource.csv";
        boolean header = true;
        int ncols = 10;
        int nlines = 10;
        //ce champ doit être positionné en absolu (commence par 0)
        int amountfield = 6;

        try{
            long begin = System.currentTimeMillis();
            //Open file
            BufferedWriter w = new BufferedWriter(new FileWriter(filename));

            if (header)
            {
                String sheader = "";
                for (int i=0;i<(ncols-1);i++)
                    sheader += "Header-" + (i+1) + ";";
                sheader += "Header-" + ncols;
                w.write(sheader);
                w.newLine();
            }

            for (int j=0;j<nlines;j++)
            {
                String line = "";
                for (int i=0;i<(ncols-1);i++)
                {
                    if (i==amountfield)
                    {
                        double temp = Math.random();
                        temp = Math.round(temp * 10000 * 100)/100;
                        line += temp + ";";
                    }
                    else
                        line += "Value-" + (j+1) + "-" + (i+1) + ";";
                }
                line += "Value-" + (j+1) + "-" + ncols;
                w.write(line);
                w.newLine();
            }
            w.close();
            long end = System.currentTimeMillis() - begin;
            System.out.println("File of " + nlines + " lines generated in "
                    + end + " ms");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
