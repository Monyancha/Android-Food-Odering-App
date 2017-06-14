package com.example.mayankaggarwal.dcare.utils;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by mayankaggarwal on 14/06/17.
 */


public class MergerSort {

    private JsonArray array;
    private JsonArray tempMergArr;
    private int length;

    public JsonArray sort(JsonArray inputArr) {
        if (inputArr.size() == 1) {
            return inputArr;
        }
//        Log.d("tagg",inputArr.toString());
        this.array = inputArr;
        this.length = inputArr.size();
        this.tempMergArr = new JsonArray();
        doMergeSort(0, length - 1);
        return inputArr;
    }

    private void doMergeSort(int lowerIndex, int higherIndex) {

        if (lowerIndex < higherIndex) {
            int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
            // Below step sorts the left side of the array
            doMergeSort(lowerIndex, middle);
            // Below step sorts the right side of the array
            doMergeSort(middle + 1, higherIndex);
            // Now merge both sides
            mergeParts(lowerIndex, middle, higherIndex);
        }
    }

    private void mergeParts(int lowerIndex, int middle, int higherIndex) {

        for (int i = lowerIndex; i <= higherIndex; i++) {
            if (tempMergArr.size() <= i) {
                tempMergArr.add(array.get(i));
            } else {
                tempMergArr.set(i, array.get(i));
            }
        }
        int i = lowerIndex;
        int j = middle + 1;
        int k = lowerIndex;
        while (i <= middle && j <= higherIndex) {
            JsonObject tempi = tempMergArr.get(i).getAsJsonObject().get("order").getAsJsonObject();
            Long order_code_i = Long.parseLong(tempi.get("orderstate_assigned_timestamp").getAsString());
            JsonObject tempj = tempMergArr.get(j).getAsJsonObject().get("order").getAsJsonObject();
            Long order_code_j = Long.parseLong(tempj.get("orderstate_assigned_timestamp").getAsString());
            if (order_code_i <= order_code_j) {
                array.set(k, tempMergArr.get(i));
                i++;
            } else {
                array.set(k, tempMergArr.get(j));
                j++;
            }
            k++;
        }
        while (i <= middle) {
            array.set(k, tempMergArr.get(i));
            k++;
            i++;
        }

    }
}
