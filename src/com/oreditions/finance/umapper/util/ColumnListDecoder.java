/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.util;

import java.util.*;

/**
 *
 * @author HO.OREY
 */
public class ColumnListDecoder
{
    public static Vector<Integer> decode(String line, String separator)
    {
        if ((line==null) || (separator==null))
            throw new RuntimeException("Error 1 in decoding line, " +
                    "should not happen");
        if ((line.length()==0)||(separator.length()!=1))
            throw new RuntimeException("Error 2 in decoding line, " +
                    "should not happen");

        StringTokenizer st = new StringTokenizer(line, separator);
        Vector<Integer> out = new Vector<Integer>();
        String token = null;
        while (st.hasMoreTokens())
        {
            //ex: C2,C3,C5
            token = st.nextToken();
            //we correct the index by -1 because the columns are 
            //numbered starting by one and the position in the int vector is
            //numbered from 0
            int index = new Integer(token.substring(1)).intValue() -1;
            out.add(new Integer(index));
        }
        return out;
    }

    public static void main(String[] args)
    {
        Vector<Integer> a = null, b = null;
        a = ColumnListDecoder.decode("C1", ",");
        b = ColumnListDecoder.decode("C1,C7,C9", ",");

        Integer a1 = new Integer(3), a2 = new Integer(5), a3 = new Integer(7);
        Vector<Integer> vect = new Vector<Integer>(), dest = null, dest2 = null;
        vect.add(a1);
        vect.add(a2);
        vect.add(a3);
        dest = ColumnListDecoder.addColumnToIndexes(vect, 6);
        int n = dest.size();
        dest2 = ColumnListDecoder.addColumnToIndexes(vect, 9);

    }

    public static Vector<Integer> addColumnToIndexes(Vector<Integer> source,
            int newcolumn)
    {
        Vector<Integer> dest = new Vector<Integer>();
        int nbs = source.size(), temp = 0;
        boolean inserted = false;
        for (int i=0;i<nbs;i++)
        {
            temp = source.elementAt(i).intValue();
            if (temp>newcolumn)
            {
                dest.add(new Integer(newcolumn));
                dest.add(new Integer(temp));
                inserted = true;
            }
            else
                dest.add(new Integer(temp));
        }
        //it is at the end
        if (!inserted)
            dest.add(new Integer(newcolumn));
        return dest;
    }

}
