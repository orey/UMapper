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
public abstract class Operation
{
    public abstract void process(CSVLine input, CSVLine output)
            throws MappingException;
    
    public abstract String serialize();
    
}
