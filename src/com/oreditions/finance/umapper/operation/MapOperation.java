/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.operation;

import com.oreditions.finance.umapper.exception.MappingException;
import com.oreditions.finance.umapper.util.CSVLine;
import java.util.*;

/**
 *
 * @author HO.OREY
 */
public class MapOperation extends Operation
{
    protected HashMap<String,String> map = null;
    protected Vector<Integer> scols = null, dcols = null;
    protected CSVLine def = null;
    
    public MapOperation(Vector<Integer> scols, Vector<Integer> dcols,
            HashMap<String,String> map, String defstring)
    {
        this.map = map;
        this.scols = scols;
        this.dcols = dcols;
        if (defstring != null)
            def = new CSVLine(defstring);
    }

    public void process(CSVLine input, CSVLine output) 
            throws MappingException
    {
        //get the source data
        String key = "";
        int n = scols.size();
        if (n<1)
            throw new RuntimeException("Index problem : should not occur");
        for (int i=0;i<n-1;i++)
        {
            key += input.getElementAt(scols.elementAt(i).intValue());
            key += ";";
        }
        key += input.getElementAt(scols.elementAt(n-1).intValue());
        //get the value
        String value = map.get(key);
        if ((value ==null)&&(def == null))
        {
            //line is not mapped : reject - managed at the controller level
            throw new MappingException(input);
        }
        
        //put the values in target line
        CSVLine temp = null;
        
        //first case : value is null, take default
        if (value==null)
            temp = def;
        //else take the mapped value
        else
            temp = new CSVLine(value);
        
        n = dcols.size();
        if (n<1)
            throw new RuntimeException("Index problem : should not occur");
        for (int i=0;i<n;i++)
        {
            output.setElementAt(dcols.elementAt(i).intValue(),
                    temp.getElementAt(i));
        }
        return;
    }

    public String serialize()
    {
        String temp = "Map operation: from columns ";
        int n = scols.size();
        for (int i=0;i<n;i++)
            temp += (scols.elementAt(i).intValue()+1) + " ";
        n = dcols.size();
        temp += "to ";
        for (int i=0;i<n;i++)
            temp += (dcols.elementAt(i).intValue()+1) + " ";
        
        return temp;
    }
    
}
