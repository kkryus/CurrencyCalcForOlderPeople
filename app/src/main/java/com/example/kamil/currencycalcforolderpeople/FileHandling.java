package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FileHandling {
    private Context context;

    public FileHandling(Context context)
    {
        this.context = context;
    }

    public void saveStringToFile(String content) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(context.getString(R.string.currenciesFileName), Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void saveStringToFile(String content, String fileName) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readJSONFromFile() {
        try {
            FileInputStream fis = context.openFileInput(context.getString(R.string.currenciesFileName));
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e("foo", e.getMessage());
        }
        return null;
    }
}


