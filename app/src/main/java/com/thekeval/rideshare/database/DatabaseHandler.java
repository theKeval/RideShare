package com.thekeval.rideshare.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.thekeval.rideshare.model.RiderModel;

public class DatabaseHandler extends SQLiteOpenHelper {

    // DB attributes
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "RideShareDB";
    private static final String TABLE_NAME_RIDER = "Riders";
    private static final String TABLE_NAME_RIDE = "Ride";
    private static final String TABLE_NAME_RIDE_RIDER_RELATION = "Ride_Riders_Relation";
    private static final String TABLE_NAME_VEHICLE = "Vehicle";

    // Rider properties
    private static final String RIDER_ID = "rider_id";
    private static final String NAME = "name";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String CONTACT_NO = "contact_no";
    // private static final String RIDE_OWNER = "ride_owner";

    // Ride Properties
    private static final String RIDE_ID = "ride_id";
    private static final String FROM_LOCATION = "from_location";
    private static final String DESTINATION_LOCATION = "destination_location";
    private static final String START_TIME = "start_time";
    private static final String RIDE_DURATION_MINUTES = "ride_duration_minutes";
    private static final String HALT_ALLOWED = "halt_allowed";
    private static final String RIDE_OWNER_ID = "ride_owner_id";    // this is rider id who is the owner of the ride - driver
    private static final String AVAILABLE_SEATS = "available_seats";
    private static final String IS_COMPLETE = "is_complete";

    // Vehicle Properties
    private static final String VIN = "vin";
    private static final String MANUFACTURER = "manufacturer";
    private static final String MODEL = "model";
    private static final String MANUFACTURING_YEAR = "manufacturing_year";
    private static final String VEHICLE_OWNER_ID = "vehicle_owner_id";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query_createTable_rider =
                "CREATE TABLE " + TABLE_NAME_RIDER + " (" +
                        RIDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        NAME + " TEXT," +
                        PASSWORD + " TEXT," +
                        EMAIL + " TEXT," +
                        CONTACT_NO + " TEXT" +
                        // RIDE_OWNER + " INTEGER" +
                        ")";

        String query_createTable_ride =
                "CREATE TABLE " + TABLE_NAME_RIDE + " (" +
                        RIDE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        FROM_LOCATION + " TEXT," +
                        DESTINATION_LOCATION + " TEXT," +
                        START_TIME + " DATETIME," +
                        RIDE_DURATION_MINUTES + " TEXT," +
                        HALT_ALLOWED + " INTEGER," +
                        RIDE_OWNER_ID + " INTEGER," +
                        AVAILABLE_SEATS + " INTEGER," +
                        IS_COMPLETE + " INTEGER" +
                        ")";

        String query_createTable_rideRider =
                "CREATE TABLE " + TABLE_NAME_RIDE_RIDER_RELATION + " (" +
                        RIDE_ID + " INTEGER," +
                        RIDER_ID + " INTEGER" +
                        ")";

        String query_createTable_vehicle =
                "CREATE TABLE " + TABLE_NAME_VEHICLE + " (" +
                        VIN + " NUMBER PRIMARY KEY NOT NULL," +
                        MANUFACTURER + " TEXT," +
                        MODEL + " TEXT," +
                        MANUFACTURING_YEAR + " TEXT," +
                        VEHICLE_OWNER_ID + " NUMBER" +
                        ")";

        db.execSQL(query_createTable_rider);
        db.execSQL(query_createTable_ride);
        db.execSQL(query_createTable_rideRider);
        db.execSQL(query_createTable_vehicle);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RIDER );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RIDE );
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_VEHICLE );

        // Create tables again
        onCreate(db);
    }


    public boolean addRider(RiderModel rider) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, rider.name);
        contentValues.put(PASSWORD, rider.password);
        contentValues.put(EMAIL, rider.email);
        contentValues.put(CONTACT_NO, rider.contact_no);
        // contentValues.put(RIDE_OWNER, rider.ride_owner);

        long res = db.insert(TABLE_NAME_RIDER, null, contentValues);
        return  (res != -1);
    }

//    public booleanaddRide(RideModel ride) {
//
//    }


//    public boolean updatePlayer(String playerName, int highestScore) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(SCORE, highestScore);
//        long res = db.update(TABLE_NAME, contentValues, NAME+"=?", new String[] {playerName});
//        return (res != -1);
//    }
//
//    public PlayerModel getPlayer(String name) {
//        String query = "SELECT * FROM " + TABLE_NAME  + " WHERE name like '" + name + "'";
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery(query, null);
//        // res.moveToFirst();
//
//        PlayerModel player = null;
//        if (res.moveToFirst()) {
//            player = new PlayerModel(res.getString(0), res.getString(1), res.getInt(2));
//        }
//
//        // res.close();
//        return player;
//
//    }
//
//    public ArrayList<PlayerModel> getTop3Players() {
//        ArrayList<PlayerModel> top3Players = new ArrayList<>();
//        String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + SCORE + " DESC LIMIT 3";
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor res = db.rawQuery(query, null);
//
//        if (res.moveToFirst()) {
//            while (!res.isAfterLast()) {
//                PlayerModel _player = new PlayerModel(res.getString(0), res.getString(1), res.getInt(2));
//                top3Players.add(_player);
//                res.moveToNext();
//            }
//        }
//
//        // res.close();
//        return  top3Players;
//
//    }


//    private void addJson(String jSon) {
//        String insertQuery =
//                "INSERT INTO " + TABLE_NAME + " (json)" +
//                        "VALUES (" + jSon + ")";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(insertQuery);
//    }
//
//    private void updateJson(String jSon) {
//        String updateQuery =
//                "UPDATE " + TABLE_NAME + " SET json='" + jSon + "'";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.execSQL(updateQuery);
//    }
//
//    private String getJson() {
//        String jSon = "";
//
//        String getQuery =
//                "SELECT * from " + TABLE_NAME;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(getQuery, null);
//
//        if (cursor.moveToFirst()) {
//            jSon = cursor.getString(0);
//        }
//
//        return jSon;
//    }



}
