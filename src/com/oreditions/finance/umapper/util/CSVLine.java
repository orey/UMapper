/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.util;

import java.text.NumberFormat;
import java.util.*;

/**
 *
 * @author HO.OREY
 */
public class CSVLine
{
    protected static final String SEP = ";";
    protected static final String VOID = "";
    protected Vector<String> data = null;
    protected boolean createdbyline = false;
    protected String datastring = null;
    protected int colnum = 0;
    
    public CSVLine(String source)
    {
        createdbyline=true;
        datastring = source;
        data = new Vector<String>();
        decodeCSVLine();
    }
    
    public CSVLine(int colnum)
    {
        this.colnum = colnum;
        data = new Vector<String>();
        //initialize for further control
        for (int i=0;i<colnum;i++)
            data.add(null);
        
    }

    public int getColumnNumber()
    {
        return colnum;
    }
    
    public Vector<String> getData()
    {
        return data;
    }
    
    protected void decodeCSVLine()
    {
        StringTokenizer st = new StringTokenizer(datastring,SEP,true);
        boolean lastwassep = false;
        String token = null;
        int i= 0;
        while (st.hasMoreTokens())
        {
            token = st.nextToken();
            //first value of the line is void
            if ((token.equals(SEP)) && (i==0))
            {
                data.add(VOID);
                lastwassep = true;
                i++;
            }
            //case of two seps one after the other
            else if ((token.equals(SEP))&&(lastwassep))
            {
                data.add(VOID);
                lastwassep = true;
                i++;
            }
            //separator but last was data
            else if (token.equals(SEP))
            {
                //do nothing
                lastwassep = true;
                i++;
            }
            else
            {
                data.add(token);
                lastwassep=false;
                i++;
            }
        }
        //last case : the last char is a SEP
        if (lastwassep)
        {
            data.add(VOID);
            i++;
        }
        colnum = data.size();
    }
    
    public String serializeCSVLine()
    {
        int s = data.size();
        String temp = "";
        if (s==0)
            throw new RuntimeException("Error : void CSV line. Should not append...");
        for (int i=0;i<s-1;i++)
        {
            temp += data.elementAt(i);
            temp += SEP;
        }
        temp += data.elementAt(s-1);
        return temp;
    }
    
    public String getElementAt(int i)
    {
        return data.get(i);
    }
    
    public void setElementAt(int i, String value)
    {
        data.setElementAt(value, i);
    }
    
    public CSVLine extractData(Vector<Integer> indexes)
    {
        int n = indexes.size();
        if ((n<=0) || (indexes==null))
            throw new RuntimeException("Void array of indexes. Should not occur.");

        CSVLine output = new CSVLine(n);
        String temp = null;
        for (int i=0;i<n;i++)
        {
            //We are in real indexes beginning by 0
            temp = this.getElementAt(indexes.elementAt(i));
            output.setElementAt(i, temp);
        }
        return output;
    }

    public String getAggregatedColumns(Vector<Integer> indexes)
    {
        int n = indexes.size();
        if ((n<=0) || (indexes==null))
            throw new RuntimeException("Void array of indexes. Should not occur.");

        String result = "";

        //Real data indexes start by 0
        for (int i=0;i<n-1;i++)
            result += this.getElementAt(indexes.elementAt(i)) + CommandFileGrammar.SEP0;
        result += this.getElementAt(indexes.elementAt(n-1));
        return result;
    }

    public Vector<CSVLine> splitLineAtIndex(int i)
    {
        //first : check if i is meaningful
        if ((i>=colnum)||(i<1))
        {
            System.err.println("Cannot split the line at index " + i);
            throw new RuntimeException();
        }

        // aaa;bbb;ccc;ddd
        // i = 3
        //split must be aaa;bbb;ccc & ddd
        //               0   1   2     3
        String sleft = "", sright = "";
        for (int j=0;j<i-1;j++)
        {
            sleft += getElementAt(j);
            sleft += ";";
        }
        sleft += getElementAt(i-1);
        for (int j=i;j<colnum-1;j++)
        {
            sright += getElementAt(j);
            sright += ";";
        }
        sright += getElementAt(colnum-1);
        Vector<CSVLine> toto = new Vector<CSVLine>();
        toto.insertElementAt(new CSVLine(sleft),0);
        toto.insertElementAt(new CSVLine(sright), 1);
        return toto;
    }

    /**
     * The in line is the original line because the amount index is
     * related to the original line. The out line is the aggregated line
     * that should stored somexhere in a map.
     * @param in
     * @param amountindex
     */
    public void aggregateLine(CSVLine in, CSVLine out, int amountindex)
    {
        //check is there are the same nb of columns
        if (in.getColumnNumber() != this.getColumnNumber())
            throw new RuntimeException("Invalid column number");

        //only the field amount is looked at
        double total = Double.parseDouble(in.getElementAt(amountindex)) +
            Double.parseDouble(this.getElementAt(amountindex));
        
        NumberFormat form = NumberFormat.getCurrencyInstance();
        String value = form.format(total);
        this.setElementAt(amountindex, value);

    }

}
