package com.example.vajronew.database;


import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.vajronew.model.Cart;
import com.example.vajronew.model.Product;

import java.util.List;

@androidx.room.Dao
public interface ProductDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllProducts(List<Product> productList);

    @Query("SELECT * FROM product_detail")
    List<Product> getAllProducts();

    //select product by id
    @Query("SELECT * FROM product_detail WHERE id=:id")
    Product getProductByID(String id);

    //insert carts
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCart(Cart carts);

    @Query("SELECT * FROM cart_detail")
    List<Cart> getAllCarts();

    //delete all employee
    @Query("DELETE FROM product_detail")
    void deleteAllProducts();

    //select product by id
    @Query("DELETE FROM cart_detail WHERE id=:id")
    void deleteCartByID(String id);
}
