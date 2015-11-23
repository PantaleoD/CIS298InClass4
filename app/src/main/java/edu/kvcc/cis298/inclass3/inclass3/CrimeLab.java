package edu.kvcc.cis298.inclass3.inclass3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.kvcc.cis298.inclass3.inclass3.database.CrimeBaseHelper;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema;
import edu.kvcc.cis298.inclass3.inclass3.database.CrimeDbSchema.CrimeTable;

/**
 * Created by dbarnes on 10/28/2015.
 */
public class CrimeLab {

    //Static variable to hold the instance of CrimeLab
    //Rather than returning a new instance of CrimeLab,
    //we will return this variable that holds our instance.
    private static CrimeLab sCrimeLab;

    //A variable to of TYPE List, which is an interface, to hold
    //A list of TYPE Crime.
 // DEL w/Ch14 db:        private List<Crime> mCrimes;

    private  Context mcontext;          // private var for context (dealing w/ db)
    private SQLiteDatabase mDatabase;   // private var for db that is crimelab will use

    //This is the method that will be used to get an instance of
    //CrimeLab. It will check to see if the current instance in the
    //variable is null, and if it is, it will create a new one using
    //the private constuctor. If it is NOT null, it will just return
    //the instance that exists.
    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    //This is the constuctor. It is private rather than public.
    //It is private because we don't want people to be able to
    //create a new instance from outside classes. If they want
    //to make an instance, we want them to use the get method
    //declared right above here.
    private CrimeLab(Context context) {      // private constructor of singleton (where we are!) & useing the context

                    // use the context in conjunction w/ the CrimeBaseHelper class we wrote in
                    //  to get the writable database

                    // We didn't write the getWriteableDatabase function that is being called.
                    //   It came from the parent class the CrimeBaseHelper inherits from
        mcontext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mcontext)
                .getWritableDatabase();            // when called - will either use or create the db (from CrimeBaseHelper class)

        //Instanciate a new ArrayList, which is a child class that
        //Implements the Interface List. Because ArrayList is a child
        //of List, we can store it in the mCrimes variable which is of
        //type List, and not ArrayList. (Polymorphism)
        // DEL w/Ch14 db:         mCrimes = new ArrayList<>();

        //for loop to populate our arraylist with some dummy data.    // // DEL w/Ch14 db:
     /*   for (int i=0; i < 100; i++) {
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0);
            mCrimes.add(crime);
        }                   */
    }

    //              Method to add a new crime to the db. The method will get called when
    //              a user clicks on the add button of the toolbar

    public void addCrime(Crime c) {             // added with chpt 13 - on the toolbar

        // DEL w/Ch14 db:         mCrimes.add(c);
        //          get the content values that we would like to stick into the db by sending
        //          it the crime that needs to be translated
        ContentValues values = getContentValues(c);

        //          Call the insert method of our clss level version of the CrimeBaseHelper class.
        //              We did not write the insert method.It came from the parent class of CrimeBaseHelper.
        //              We are just using it.
        mDatabase.insert(CrimeTable.NAME, null, values);

    }

    // pg 265 ch 14:
        public  void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();     // gets the UUID out of the crime as a string. This will be used in the
                                            // WHERE clause of the SQL to find the row we want to update.

            //  create the content values that will be used ot do the update of the model
        ContentValues values = getContentValues(crime);


            //  Update a specific creime with teh values from teh content values for a
            //      crime that has the UUID of the one in the uuidString.

            //  The update method has the following signature:
            //      first parm:  Table Name
            //      2nd parm:  Values to update
            //      3rd parm:   WHERE clasue to know which row to update
            //      4th parm:   String array of parameters to bind to the ?'s in the WHERE clause

            //  The finished SQL would like something like:
            //      UPDATE Crimes WHERE uuid = ?  SET (parm 1, parm 2, ....) VALUES (value1, value2, ...);
            //          Where the ? will become the value in the uuidString

            // this  uses the update method of android to 'fill in the blanks' for a SQl stmt to be executed
        mDatabase.update(CrimeTable.NAME, values, CrimeTable.Cols.UUID + " = ? ",   new String[] {uuidString});


            // ALTERNAIVE CODE:
            //      mDatabase.execSQL(UPDATE Crimes WHERE.... bla bla bla..actual SQL command)
            // so takes in the where clause and also the array but this line does the work for us
    }





    //Getter to get the crimes
    public List<Crime> getCrimes() {
        // DEL w/Ch14 db:         return mCrimes;
        return  new ArrayList<>();
    }

    //Method to get a specific crime based on the
    //UUID that is passed in.
    public Crime getCrime(UUID id) {

        //This is a foreach loop to go through all of the crimes
        //at each iteration the current crime will be called 'crime'.
/* DEL w/Ch14 db:          for (Crime crime : mCrimes) {
            //If we find a match, return it.
                     if (crime.getId().equals(id)) {
                          return crime;
                     }
                  }                     */
        //no match, return null.
        return null;
    }

    // CHPT 14 added. Will use key, id pairs.  will make crime and return values the return to the database
    // static method to do the work o ftaking in a crime and creating a contentValues object that can be used
    //  to insert the crime into the database.  The ContentValues class operates as a hash table, or "Key => Value"
    //      array. The key refers to the column name of the db and the value refers to the value we would like to put
    //      into the db.

    private  static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();                             // makes a new ContentValues object
        //                                          puts each field (casting as needed)
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());            // changed from Data Object to a Timestamp.
        //                                                              Thats why we are calling the getTime method at the end.
        //                                              the db can only store the data as a timestamp

        //  if true: first value is used, otherwise 2nd value.   one line vs if else command
        //          used here to change true/false to 1 / 0 since that is how the db will store a boolean
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);   // ternary condition  1 or 0 - eliminated IF for (crime.isSolved)

        return  values;
    }
}
