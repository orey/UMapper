/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.oreditions.finance.umapper.exception;

/**
 *
 * @author HO.OREY
 */
public class AggregationException extends Exception
{
    protected int i=0;

    public static final int KEY_PROBLEM = 1;
    public static final int AMOUNT_PROBLEM = 2;

    public AggregationException(int i, String message)
    {
        super(message);
    }

    public int getProblemId()
    {
        return i;
    }

}
