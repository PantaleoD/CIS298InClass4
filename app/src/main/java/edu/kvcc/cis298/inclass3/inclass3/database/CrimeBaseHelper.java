package edu.kvcc.cis298.inclass3.inclass3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**    * Created by dpantaleo on 11/23/2015.  */
public class CrimeBaseHelper extends SQLiteOpenHelper{
    //  Setup a name fo the database
    private  static final  int VERSION = 1;         // you can change this number but never auto changed... this is just to show where
                            //                      this starts when 'released'
    private  static final String DATABASE_NAME = "crimeBase.db";

    //                      Constructor - to get this class instanciated
    public CrimeBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
                            // This is the method that will get called if the database needs
                            //      to be created.
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute a "raw" sqllite statement to create the table that will store
        //          the data for our application.It would be safer to use some other layer of
        //          abstraction to hand this such as an ORM (object relational mapper), but we are
        //          doing it the quick and dirty way.  (translate between db and sql)
        db.execSQL("create table" + CrimeTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            CrimeTable.Cols.UUID + ", " +
            CrimeTable.Cols.TITLE + ", " +
            CrimeTable.Cols.DATE + ", " +
            CrimeTable.Cols.SOLVED +        ")");           // this is SQL & probably best not to do this here!!! hardcode screen but
                                            // but create the string first!
        //                                      the above is the create a table
    }
                            // This is the method that will get called if the database already
                            //   exists, bit is not the same version as what is specified above. This methos will do the work of
                            // migrating the db to the most current and correct version for the app.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // we're not going to use this method
    }
}
