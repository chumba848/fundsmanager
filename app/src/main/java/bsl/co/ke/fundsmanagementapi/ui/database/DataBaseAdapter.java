package bsl.co.ke.fundsmanagementapi.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bsl.co.ke.fundsmanagementapi.ui.model.Trader;
import bsl.co.ke.fundsmanagementapi.ui.model.User;

public class DataBaseAdapter {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    //the user table
    // TODO: Create public field for each column in your table.
    // SQL Statement to create a new database.
    // Variable to hold the database instance
    public static SQLiteDatabase db;
    // Database open/upgrade helper
    private static DatabaseHelper dbHelper;
    String ok = "OK";
    // Context of the application using the database.
    private Context context = null;

    public DataBaseAdapter(Context _context) {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //local brand Stocked

    // Method to openthe Database
    public DataBaseAdapter open() throws SQLException {
        db = dbHelper.getWritableDatabase();
        return this;

    }

    // Method to close the Database
    public void close() {

        db.close();
    }

    // method returns an Instance of the Database
    public SQLiteDatabase getDatabaseInstance() {
        return db;
    }


    // Database Name


    // User table name
    // Database Version

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    public static final String TABLE_C_BPARTNER = "c_bpartner";

    public static final String TABLE_USER = "user";
    public static final String TABLE_TRADERS = "traders";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "trader_name";
    private static final String COLUMN_USER_NUMBER = "trader_number";
    private static final String COLUMN_USER_PIN = "user_Pin";
    private static final String COLUMN_SS_FUNCTION = "function";
    private static final String COLUMN_SS_TYPE_ACCOUNT = "type_of_account";
    private static final String COLUMN_PHONE_NO = "phone_number";
    private static final String COLUMN_RECIPIENT = "recipient";
    private static final String COLUMN_PAYMENT_MODE = "mode_of_payment";
    private static final String COLUMN_AMOUNT = "amount";


    private static final String COLUMN_TRADER_ID = "user_id";
    private static final String COLUMN_TRADER_USER_NAME = "trader_name";
    private static final String COLUMN_TRADER_NUMBER = "trader_number";

    // create table sql query
    public static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_NUMBER + " TEXT,"
            + COLUMN_USER_PIN + " TEXT,"
            + COLUMN_SS_FUNCTION + " TEXT,"
            + COLUMN_SS_TYPE_ACCOUNT + " TEXT,"
            + COLUMN_PHONE_NO + " TEXT,"
            + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_PAYMENT_MODE + " TEXT,"
            + COLUMN_AMOUNT + " TEXT" + ")";

    public static final String CREATE_USER_TABLE_TRADERS = "CREATE TABLE " + TABLE_TRADERS + "("
            + COLUMN_TRADER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_TRADER_USER_NAME + " TEXT,"
            + COLUMN_TRADER_NUMBER + " TEXT" + ")";

    public void addUser(User user) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_NUMBER, user.getIdno());
        values.put(COLUMN_USER_PIN, user.getPassword());
        values.put(COLUMN_SS_FUNCTION, user.getSelectionfunction());
        values.put(COLUMN_SS_TYPE_ACCOUNT, user.getTypeofaccount());
        values.put(COLUMN_PHONE_NO, user.getPhoneNumber());
        values.put(COLUMN_RECIPIENT, user.getRecipient());
        values.put(COLUMN_PAYMENT_MODE, user.getPaymentmode());
        values.put(COLUMN_AMOUNT, user.getAmount());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public void addtrader(Trader trader) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TRADER_USER_NAME, trader.getName());
        values.put(COLUMN_TRADER_NUMBER, trader.getNumber());


        // Inserting Row
        db.insert(TABLE_TRADERS, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_NUMBER,
                COLUMN_USER_NAME,
                COLUMN_USER_PIN
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        db = dbHelper.getWritableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,id_number,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setIdno(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NUMBER)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PIN)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_NUMBER, user.getIdno());
        values.put(COLUMN_USER_PIN, user.getPassword());
        values.put(COLUMN_SS_FUNCTION, user.getSelectionfunction());
        values.put(COLUMN_SS_TYPE_ACCOUNT, user.getTypeofaccount());
        values.put(COLUMN_PHONE_NO, user.getPhoneNumber());
        values.put(COLUMN_RECIPIENT, user.getRecipient());
        values.put(COLUMN_PAYMENT_MODE, user.getPaymentmode());
        values.put(COLUMN_AMOUNT, user.getAmount());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        db = dbHelper.getWritableDatabase();

        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        db = dbHelper.getWritableDatabase();

        // selection criteria
        String selection = COLUMN_USER_NUMBER + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE id_number = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        db = dbHelper.getWritableDatabase();

        // selection criteria
        String selection = COLUMN_USER_NUMBER + " = ?" + " AND " + COLUMN_USER_PIN + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE id_number = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


}
