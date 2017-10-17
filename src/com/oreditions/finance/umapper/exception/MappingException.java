/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.exception;

import com.oreditions.finance.umapper.util.CSVLine;

/**
 *
 * @author HO.OREY
 */
public class MappingException extends Exception
{
    
    protected CSVLine line = null;
    
    public MappingException(CSVLine line)
    {
        this.line = line;
    }
    
    public CSVLine getNonMappedLine()
    {
        return line;
    }
}
