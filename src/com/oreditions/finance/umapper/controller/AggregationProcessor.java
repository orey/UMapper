/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.controller;

import com.oreditions.finance.aggregation.AggregationMap;
import java.io.*;
import com.oreditions.finance.umapper.exception.*;
import com.oreditions.finance.umapper.util.CSVLine;
import com.oreditions.finance.umapper.util.ColumnListDecoder;
import com.oreditions.finance.umapper.util.CommandFileGrammar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author HO.OREY
 */
public class AggregationProcessor implements Process
{
    protected String aggsource = null;
    protected String aggtarget = null;
    protected String aggkey = null;
    protected Vector<Integer> indexes = null;
    protected int aggamountint = 0;

    //this variable contains the amount column
    protected Vector<Integer> fullindexes = null;
    //this index is the index of the amount in the fullindexes vector
    protected int relativeamountindex = -1;

    protected BufferedReader reader = null;
    protected BufferedWriter writer = null;

    protected boolean header = false;
    protected boolean verbose = false;

    protected final static String DEFAULT_AGGTARGET = "aggregated_output.csv";

    public AggregationProcessor(String aggsource,
            String aggtarget,
            String aggkey,
            String aggamount,
            boolean header,
            boolean verbose) throws IOException, AggregationException
    {
        //source file
        if (aggsource==null)
            throw new RuntimeException("No source file for aggregation. Should " +
                    "not occur...");
        else
            this.aggsource = aggsource;

        //target file
        if (aggtarget==null)
        {
            System.err.println("Warning : no file name for aggregation output" +
                    "file. Providing a default name : " + DEFAULT_AGGTARGET);
            aggtarget = DEFAULT_AGGTARGET;
        }
        else
            this.aggtarget = aggtarget;

        //agg key
        if (aggkey==null)
            throw new AggregationException(AggregationException.KEY_PROBLEM,
                    "No key defined for aggregation");
        else
            this.aggkey = aggkey;
        indexes = ColumnListDecoder.decode(aggkey, CommandFileGrammar.SEP4);


        //agg amount
        if (aggamount==null)
            throw new AggregationException(AggregationException.AMOUNT_PROBLEM,
                    "No amount column defined for aggregation");
        else
            //transform in real indexes beginning by 0
            this.aggamountint = Integer.parseInt(aggamount.substring(1)) - 1;

        //construction of the full index
        fullindexes = ColumnListDecoder.addColumnToIndexes(indexes, aggamountint);
        

        //header mgt
        this.header = header;

        //open the files
        reader = new BufferedReader(new FileReader(aggsource));
        writer = new BufferedWriter(new FileWriter(aggtarget));

    }

    public int process()
    {
        //we will do a lot of stuff in memory - warning about the
        //potential memory explosion
        String line = null;
        try{
            //manage header : if there is a header, we can write it immediately.
            if (header)
            {
                line = reader.readLine();
                CSVLine head = new CSVLine(line).extractData(fullindexes);
                writer.write(head.serializeCSVLine());
                writer.newLine();
                writer.flush();
            }

            //define memory
            AggregationMap mem = new AggregationMap(indexes, fullindexes);
            CSVLine templine = null;

            //@todo: rewrite the full code in comment
            //get all lines
            while ((line = reader.readLine())!= null)
            {
                //Step 1 : extract the data
                CSVLine lineline = new CSVLine(line);
                templine = lineline.extractData(fullindexes);

                //Step 2 : generate the key

                String key = lineline.getAggregatedColumns(indexes);

                //Step 3 : get a potential element in the map matching the key
                /*CSVLine aggline = mem.get(key);
                if (aggline == null)
                {
                    //there is no key found
                    boolean check = mem.containsKey(key);
                    if (check)
                        throw new RuntimeException("Aggregator Processor: " +
                                "strange case, should not happen");
                    mem.put(key, templine);
                }
                else
                {
                    //there is a key found
                    aggline.aggregateLine(templine, aggamountint);
                }*/
            }
            //dump the memory
            /*int n = mem.size();
            Collection<CSVLine> col = mem.values();
            for (CSVLine toto : col)
            {
                writer.write(toto.serializeCSVLine());
                writer.newLine();
                writer.flush();
            }*/
            writer.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.err.println("Error in AggregationProcessor");
            return 1;
        }
        return 0;
    }

}
