/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.aggregation;

import com.oreditions.finance.umapper.util.CSVLine;
import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author HO.OREY
 */
public class AggregationMap
{
    protected HashMap<String, CSVLine> map = new HashMap<String, CSVLine>();
    protected Vector<Integer> colsforkey = null, fullcols=null;
    protected int relativeamountindex = -1;
    protected int absoluteamountindex = -1;

    public AggregationMap(Vector<Integer> colsforkey,
            Vector<Integer> fullcols)
    {
        this.colsforkey = colsforkey;
        this.fullcols = fullcols;
        calculateAmountIndexes();
    }

    public void addAbsoluteElement(CSVLine line)
    {
        //work in absolute mode
        
    }

    public void addRelativeElement(CSVLine line)
    {
        //work in relative mode
        
    }

    protected void calculateAmountIndexes()
    {
        //the index is the difference between the two
        int n = colsforkey.size();
        for (int i=0;i<n;i++)
        {
            if (fullcols.get(i).intValue()!=colsforkey.get(i).intValue())
            {
                relativeamountindex = i;
                absoluteamountindex = fullcols.get(i);
                return;
            }
        }
        //It was not found by the loop. It is the last one
        relativeamountindex = fullcols.size();
        absoluteamountindex = fullcols.get(fullcols.size()-1);
    }
}
