package  our.game.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <ul>
 * <li>Get Integer: Use Input.get(Input.INT) and cast the return value to an int</li>
 * <li>Get Double: Use Input.get(Input.DOUBLE) and cast the return value to a double</li>
 * <li>Get String: Use Input.get(Input.STRING) and cast the return value to a String</li>
 * <li>Get Boolean: Use Input.get(Input.BOOLEAN) and cast the return value to a boolean</li>
 * <li>Get Enterpress: Use Input.get(Input.CONFIRM) and watch and learn :D</li>
 * </ul>
 * 
 * Append a String as a second argument to display instead of a default one.
 */
public class Input {

    public static final int STRING = 0;
    public static final int INT = 1;
    public static final int DOUBLE = 2;
    public static final int BOOLEAN = 3;
    public static final int CONFIRM = 4;

    public static final String VERSION = "1.5";

    public static final String UPDATE = "* press enter to continue!";

    private static Scanner scn = null;

    private static ArrayList<String> trueValues = null;

    /***
     * Gets User input. :)  (Modifier set to true)
     * @param type The type to return.
     * @return Returns user input of received type
     * @throws IllegalArgumentException if an unsupportet type is submitted.
     */
    public static Object get(int type) throws IllegalArgumentException {
        return get(type, null, true);
    }

    /***
     * Gets User input. :)
     * @param type The type to return.
     * @param mod Modifier flag used for ignoreCase for Boolean Type and if negative numbers should be allowed!
     * @return Returns user input of received type
     * @throws IllegalArgumentException if an unsupportet type is submitted.
     */
    public static Object get(int type, boolean mod) throws IllegalArgumentException {
        return get(type, null, mod);
    }

    /***
     * Gets User input. :)  (Modifier set to true)
     * @param type The type to return.
     * @param msg Custom message for user input.
     * @return Returns user input of received type
     * @throws IllegalArgumentException if an unsupportet type is submitted.
     */
    public static Object get(int type, String msg) throws IllegalArgumentException {
        return get(type, msg, true);
    }

     /***
     * Gets User input. :)
     * @param type The type to return.
     * @param msg Custom message for user input.
     * @param mod Modifier flag used for ignoreCase for Boolean Type and if negative numbers should be allowed!
     * @return Returns user input of received type
     * @throws IllegalArgumentException if an unsupportet type is submitted.
     */
    public static Object get(int type, String msg, boolean mod) throws IllegalArgumentException {
        
        if (trueValues == null) {
            // Generate default True Values
            trueValues = new ArrayList<String>();
            trueValues.add("Y");
            trueValues.add("J");
            trueValues.add("TRUE");
            trueValues.add("1");
            trueValues.add("YES");
        }

        if(scn == null) scn = new Scanner(System.in);

        switch(type) {
            case STRING:
                String x = null;
                if(msg == null) msg = "Geben Sie einen String ein: ";
                while(x == null) {
                    try {
                        System.out.println(msg);
                        x = scn.nextLine();
                    } catch(Exception ex) {
                        System.out.println("Falsche Eingabe! [String erwartet!]");
                        x = null;
                    }
                }
                return x;
            case INT:
                int x1 = 0;
                if(msg == null) msg = "Geben Sie einen Integer ein: ";
                while(true) {
                    try {
                        System.out.println(msg);
                        x1 = Integer.parseInt(scn.nextLine());
                        if(!mod) {
                            if(x1 < 0) throw new IllegalArgumentException();
                        }
                        break;
                    } catch(Exception ex) {
                        if(!mod) {
                            System.out.println("Falsche Eingabe! [Positiver Integer erwartet!]");
                        }else {
                            System.out.println("Falsche Eingabe! [Integer erwartet!]");
                        }
                        x1 = 0;
                    }
                }
                return x1;
            case DOUBLE:
                double x2 = 0;
                if(msg == null) msg = "Geben Sie eine double ein: ";
                while(true) {
                    try {
                        System.out.println(msg);
                        x2 = Double.parseDouble(scn.nextLine());
                        if(!mod) {
                            if(x2 < 0) throw new IllegalArgumentException();
                        }
                        break;
                    } catch(Exception ex) {
                        if(!mod) {
                            System.out.println("Falsche Eingabe! [Positive Double erwartet!]");
                        }else {
                            System.out.println("Falsche Eingabe! [Double erwartet!]");
                        }
                        x2 = 0;
                    }
                }
                return x2;
            case BOOLEAN:
                String x3 = null;
                boolean x3r = false;
                if(msg == null) msg = "Wählen sie zwischen wahr und falsch - Werte die wahr sind, sind: ["+Arrays.toString(trueValues.toArray())+"] - alle anderen Werte sind falsch: ";
                while(x3 == null) {
                    try {
                        System.out.println(msg);
                        x3 = scn.nextLine();
                        x3r = getBooleanByValues(x3, trueValues, mod);
                    } catch(Exception ex) {
                        System.out.println("Falsche Eingabe! [Boolean erwartet! - "+Arrays.toString(trueValues.toArray())+"]");
                        x3 = null;
                    }
                }
                return x3r;
            case CONFIRM:
                if(msg != null && !msg.equals("")) System.out.println(msg);
                scn.nextLine();
                return null;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Calls Input.get(Input.CONFIRM) -> waits for Enter
     */
    public static void confirm() {
        get(CONFIRM, null, true);
    }

    /**
     * Calls Input.get(Input.CONFIRM) -> waits for Enter
     */
    public static void confirm(String msg) {
        get(CONFIRM, msg, true);
    }

    /**
     * Closes the Scanner instance. This should be called after all input has been read!
     */
    public static void close() {
        scn.close();
        scn = null;
    }

    /**
     * Adds a new true value to be true
     * @param val the value to be added
     */
    public static void addTrueValue(String val) {
        if (!trueValues.contains(val))
            trueValues.add(val);
    }

    /**
     * Removes a true value to be true
     * @param value to be removed
     */
    public static void removeTrueValue(String val) {
        if (trueValues.contains(val))
            trueValues.remove(val);
    }

    /**
     * Clears all trueValues however one has to be added because it mustn't be empty!
     * @param oneValue trueValue to add
     */
    public static void clearTrueValues(String oneValue) {
        trueValues = new ArrayList<String>();
        trueValues.add(oneValue);
    }

    /**
     * sets the new ArrayList<String> as trueValues.
     * @param tv ArrayList<String> containing trueValues
     * @throws IllegalArgumentException if ArrayList is Empty
     */
    public static void setTrueValues(ArrayList<String> tv) throws IllegalArgumentException {
        if (tv.isEmpty()) throw new IllegalArgumentException();
        trueValues = tv;
    }

    /**
     * 
     * @param comp String to compare truth to
     * @param trueVals  ArrayList<String> with all values that are defined as "true"
     * @param ignoreCase Set if case should be ignored
     * @return true if trueVals contains comp
     * @throws IllegalArgumentException if comp is null or empty
     */
    public static boolean getBooleanByValues(String comp, ArrayList<String> trueVals, boolean ignoreCase) throws IllegalArgumentException {
        if (comp == null || comp.equals("")) throw new IllegalArgumentException();
        for(String s : trueVals) {
            if (ignoreCase) {
                if (s.equalsIgnoreCase(comp)) return true;
            } else {
                if (s.equals(comp)) return true;
            }
        }
        return false;
    }

    // With love from Auri <3

}