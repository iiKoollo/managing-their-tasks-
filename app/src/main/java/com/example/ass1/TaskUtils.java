package com.example.ass1;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TaskUtils {
    private static final String TASK_PREFS = "TaskPrefs";
    // Method to load the list of tasks from SharedPreferences
    public static List<Task> loadTaskList(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TASK_PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("taskList", null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(json, type);
    }
    // Method to save the list of tasks to SharedPreferences
    public static void saveTaskList(Context context, List<Task> taskList) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(TASK_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("taskList", json);
        editor.apply();
    }
}
