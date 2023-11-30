package com.example.ass1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private EditText taskNameEditText, taskDescriptionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        taskNameEditText = findViewById(R.id.edtext1);
        taskDescriptionEditText = findViewById(R.id.edmtext1);

        Button saveButton = findViewById(R.id.btsave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        Button btback = findViewById(R.id.btback);
        btback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // Method to save a new task
    private void saveTask() {
        String taskName = taskNameEditText.getText().toString();
        String taskDescription = taskDescriptionEditText.getText().toString();
        TaskUtils loadlist =new TaskUtils();
        // Check if the task name is not empty
        if (!TextUtils.isEmpty(taskName)) {
            List<Task> taskList = loadlist.loadTaskList(this);
            // If the task list is null, initialize a new ArrayList
            if (taskList == null) {
                taskList = new ArrayList<>();
            }
            taskList.add(new Task(taskName, taskDescription));

            SharedPreferences sharedPreferences = getSharedPreferences("TaskPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(taskList);
            editor.putString("taskList", json);
            editor.apply();// Apply the changes

            setResult(RESULT_OK); // Set the result to indicate a successful task addition
        }

        finish();
    }
}
