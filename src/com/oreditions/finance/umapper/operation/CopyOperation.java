/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.operation;

import com.oreditions.finance.umapper.exception.MappingException;
import com.oreditions.finance.umapper.util.CSVLine;

/**
 *
 * @author HO.OREY
 */
public class CopyOperation extends Operation
{
    protected int source = -1, destination = -1;
    
    public CopyOperation(int source, int destination)
    {
        this.source = source;
        this.destination = destination;
    
    }
    
    public void process(CSVLine input, CSVLine output) 
            throws MappingException
    {
        //get the value at source
        String value = input.getElementAt(source);
        output.setElementAt(destination, value);
        return;
    }
    
    public String serialize()
    {
        return "Copy operation: from column " + (source+1) +
                " to column " + (destination+1);
    }

}
