package com.example.ass1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class TaskDetailsActivity extends AppCompatActivity {
    private boolean taskCompleted = false;
    private String taskName;
    private String taskDescription;
    private int taskPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        // return task details from the intent
        taskName = getIntent().getStringExtra("taskName");
        taskDescription = getIntent().getStringExtra("taskDescription");
        taskPosition = getIntent().getIntExtra("taskPosition", -1);
        // Load the task list from storage
        List<Task> taskList = TaskUtils.loadTaskList(this);
        if (taskPosition >= 0 && taskPosition < taskList.size()) {
            taskCompleted = taskList.get(taskPosition).isCompleted();

            TextView taskNameTextView = findViewById(R.id.taskName);
            TextView taskDescriptionTextView = findViewById(R.id.taskDescriptionTextView);

            taskNameTextView.setText(taskName);
            taskDescriptionTextView.setText(taskDescription);

            Button btBack = findViewById(R.id.btbackd);
            Button btDelete = findViewById(R.id.btdel);
            Button btSaveChanges = findViewById(R.id.btSavec);
            CheckBox checkBox = findViewById(R.id.checkBox);

            TextView taskNameEditText = findViewById(R.id.taskName);
            TextView taskDescriptionEditText = findViewById(R.id.taskDescriptionTextView);

            taskNameEditText.setText(taskName);
            taskDescriptionEditText.setText(taskDescription);
            // Back button click listener
            btBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            // Delete button click listener
            btDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTask();
                    setResult(RESULT_OK);
                    finish();
                }
            });
            // Save changes button click listener
            btSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskName = taskNameEditText.getText().toString();
                    taskDescription = taskDescriptionEditText.getText().toString();
                    saveChanges();
                    setResult(RESULT_OK);
                    finish();
                }
            });
            // Checkbox click listener
            checkBox.setChecked(taskCompleted);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    taskCompleted = !taskCompleted;
                    updateColors();
                }
            });

            updateColors();
        } else {
            finish();
        }
    }
    // Method to delete the selected task
    private void deleteTask() {
        List<Task> taskList = TaskUtils.loadTaskList(TaskDetailsActivity.this);
        if (taskPosition >= 0 && taskPosition < taskList.size()) {
            taskList.remove(taskPosition);
            TaskUtils.saveTaskList(TaskDetailsActivity.this, taskList);
        }
    }
    // Method to save changes made to the task
    private void saveChanges() {
        List<Task> taskList = TaskUtils.loadTaskList(this);
        Task updatedTask = taskList.get(taskPosition);
        updatedTask.setTaskName(taskName);
        updatedTask.setTaskDescription(taskDescription);
        updatedTask.setCompleted(taskCompleted);
        TaskUtils.saveTaskList(this, taskList);
    }
    // Method to update UI colors
    private void updateColors() {
        CheckBox checkBox = findViewById(R.id.checkBox);
        if (taskCompleted) {
            checkBox.setBackgroundColor(getResources().getColor(R.color.green));
        } else {
            checkBox.setBackgroundColor(getResources().getColor(R.color.dark));
        }
    }
}
