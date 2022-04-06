package com.example.vajronew.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.vajronew.model.Cart;
import com.example.vajronew.model.Product;

// adding annotation for our database entities and db version.
@Database(entities = {Product.class, Cart.class}, version = 2, exportSchema = false)
public abstract class ProductDatabase extends RoomDatabase {

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile ProductDatabase INSTANCE;
    // below line is to create abstract variable for dao.
    public abstract ProductDao Dao();

    // on below line we are getting instance for our database.
   public static ProductDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ProductDatabase.class, "product_db")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
