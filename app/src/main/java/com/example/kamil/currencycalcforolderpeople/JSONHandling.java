package com.example.kamil.currencycalcforolderpeople;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONHandling {

    public static JSONArray getRates(String json)
    {
        try {
            JSONArray arr = new JSONArray(json);
            JSONObject jsonobject = arr.getJSONObject(0);
            return jsonobject.getJSONArray("rates");
        }
        catch(Exception e)
        {

        }
        return null;
    }
}
