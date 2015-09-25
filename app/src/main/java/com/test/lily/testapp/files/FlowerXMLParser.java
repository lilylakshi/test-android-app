package com.test.lily.testapp.files;

import com.test.lily.testapp.models.Flower;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lily on 30/07/2015.
 */
public class FlowerXMLParser {

    public static List<Flower> parseFeed(String content) {

        try {
            boolean inDataItemTag = false;
            String currentTagName = "";
            Flower flower = null;
            List<Flower> flowerList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(content));

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {

                switch (eventType) {

                    case XmlPullParser.START_TAG:
                        currentTagName = parser.getName();
                        if(currentTagName.equals("product")) {
                            inDataItemTag = true;
                            flower = new Flower();
                            flowerList.add(flower);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        if(inDataItemTag && flower != null) {
                            switch (currentTagName) {
                                case Flower.PRODUCT_ID:
                                    flower.setProductId(Integer.parseInt(parser.getText()));
                                    break;
                                case Flower.CATEGORY:
                                    flower.setCategory(parser.getText());
                                    break;
                                case Flower.INSTRUCTIONS:
                                    flower.setInstructions(parser.getText());
                                    break;
                                case Flower.NAME:
                                    flower.setName(parser.getText());
                                    break;
                                case Flower.PHOTO:
                                    flower.setPhoto(parser.getText());
                                    break;
                                case Flower.PRICE:
                                    flower.setPhoto(parser.getText());
                                    break;
                            }
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("product")) {
                            inDataItemTag = false;
                        }
                        currentTagName = "";
                        break;
                }

                eventType = parser.next();
            }

            return flowerList;

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
