package edu.kvcc.cis298.inclass3.inclass3.database;   // path for location of the file

            /**          * Created by dpantaleo on 11/23/2015.       */

            // Class to hold al the the info related to the structure of the database:


public class CrimeDbSchema {

                // get rest of comment from Davids....

   public static  final  class  CrimeTable {
       public static final String NAME = "crimes";       // name of table

       public static final class Cols {                         // static col names for the table

           public static final String UUID = "uuid";      //   fields (columns) within the name of the table.
           public static final String TITLE = "title";
           public static final String DATE = "data";
           public static final String SOLVED = "solved";

       }
   }
 }
