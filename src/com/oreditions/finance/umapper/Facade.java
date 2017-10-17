/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * @todo : management of the aggregation
 */

package com.oreditions.finance.umapper;

import com.oreditions.finance.umapper.controller.Controller;

/**
 *
 * @author HO.OREY
 */
public class Facade
{
    /**
     * Constants
     */
    protected static final String CPRE = "-c";
    protected static final String VERBOSE = "-verbose";
    
    /**
     * Usage method
     * Return exit(0)
     */
    protected static void Usage()
    {
        System.out.println("=================================");
        System.out.println("UConverter usage :");
        System.out.println("uconverter -c commands.run -verbose");
        System.out.println("Please, refer to documentation for " +
                "command file syntax");
        System.out.println("=================================");
        System.exit(0);
    }
    
    /**
     * Main entry point of the package
     * @param args : the command line arguments
     */
    public static void main(String[] args) 
    {
        switch (args.length)
        {
            case 2:
                if (!(args[0].equals(CPRE)))
                    Usage();
                run(args[1],false);
                break;
            case 3:
                //case 1: verbose is first
                if ((args[0].equals(VERBOSE))&&(args[1].equals(CPRE)))
                    run(args[2],true);
                else if ((args[2].equals(VERBOSE))&&(args[0].equals(CPRE)))
                    run(args[1],true);
                else
                    Usage();
                break;
            default:
                Usage();
        }
    }
    
    /**
     * Skeleton of the run treatment
     * @param commandfile
     * @param verbose
     */
    protected static void run(String commandfile, boolean verbose)
    {
        if (verbose) System.out.println("Facade 1: Creating controller");
        Controller controller = new Controller(commandfile,verbose);
        if (verbose) System.out.println("Facade 1: Controller created OK");

        if (verbose) System.out.println("Facade 2: Parsing the control file");
        controller.parseCommandFile();
        if (verbose) System.out.println("Facade 2: Control file parsed OK");

        if (verbose) System.out.println("Facade 3: Processing files");
        controller.process();
        if (verbose) System.out.println("Facade 3: Files processed OK");

        if (verbose) System.out.println("Facade 4: Closing files");
        controller.closeFiles();
        if (verbose) System.out.println("Facade 4: Files closed OK");

        System.exit(0);
    }

}
