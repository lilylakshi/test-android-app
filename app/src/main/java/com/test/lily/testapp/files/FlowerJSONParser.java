package com.test.lily.testapp.files;

import com.test.lily.testapp.models.Flower;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lily on 31/07/2015.
 */
public class FlowerJSONParser {

    public static List<Flower> parseFeed(String content) {

        try {
            JSONArray jsonArray = new JSONArray(content);
            List<Flower> flowerList = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = new JSONObject();
                obj = jsonArray.getJSONObject(i);
                Flower flower = new Flower();

                flower.setProductId(obj.getInt(Flower.PRODUCT_ID));
                flower.setName(obj.getString(Flower.NAME));
                flower.setCategory(obj.getString(Flower.CATEGORY));
                flower.setInstructions(obj.getString(Flower.INSTRUCTIONS));
                flower.setPhoto(obj.getString(Flower.PHOTO));
                flower.setPrice(obj.getDouble(Flower.PRICE));

                flowerList.add(flower);
            }

            return flowerList;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }
}
