package com.example.alexandramolina.tarea6;

import android.widget.ImageView;

/**
 * Created by alexandramolina on 23/5/18.
 */

public class Item {

    String itemName;
    String itemPrice;
    String itemDes;
    String itemPic;

    public Item(){

    }

    public Item(String itemName, String itemPrice, String itemDes, String itemPic) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDes = itemDes;
        this.itemPic = itemPic;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }
}