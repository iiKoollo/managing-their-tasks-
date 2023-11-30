package com.example.ass1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Task> taskList;
    private ArrayAdapter<Task> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        taskList = new ArrayList<>();
        taskList.addAll(TaskUtils.loadTaskList(this));

        // Use the built-in ArrayAdapter
        adapter = new ArrayAdapter<Task>(this, android.R.layout.simple_list_item_1, taskList) {

            @Override
            public View getView(int position,  View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                Task task = getItem(position);

                TextView taskNameTextView = convertView.findViewById(android.R.id.text1);
                taskNameTextView.setText(task != null ? task.getTaskName() : "");

                // Set background color based on task comple
                if (task != null && task.isCompleted()) {
                    convertView.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    convertView.setBackgroundColor(getResources().getColor(R.color.row));
                }

                return convertView;
            }
        };
        // Display tasks in ListView
        ListView listView = findViewById(R.id.lv1);
        listView.setAdapter(adapter);
        // Button to add a new task
        Button addButton = findViewById(R.id.button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, MainActivity2.class), 1);
            }
        });
        // to clicking on a task in the list
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Task clickedTask = taskList.get(position);
            openTaskDetails(clickedTask);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Update data after adding a new task
        if (requestCode == 1 && resultCode == RESULT_OK) {
            refreshData();
        }
    }

    private void openTaskDetails(Task task) {
        // Open the task details screen
        Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
        intent.putExtra("taskName", task.getTaskName());
        intent.putExtra("taskDescription", task.getTaskDescription());
        intent.putExtra("taskPosition", taskList.indexOf(task));
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update data on resume
        refreshData();

    }
    private void refreshData() {
        // Update data on tasklist
        taskList.clear();
        taskList.addAll(TaskUtils.loadTaskList(this));
        adapter.notifyDataSetChanged();
        updateRowColors();
    }

    private void updateRowColors() {// Update the colors of rows in the ListView based on task complet
        ListView listView = findViewById(R.id.lv1);

        for (int i = 0; i < listView.getChildCount(); i++) {
            View rowView = listView.getChildAt(i);
            TextView taskNameTextView = rowView.findViewById(android.R.id.text1);

            if (taskList != null && !taskList.isEmpty() && i < taskList.size()) {// Check if the task list is not empty
                Task task = taskList.get(i);

                if (task != null && task.isCompleted()) {// Set background color based on task complet
                    taskNameTextView.setBackgroundColor(getResources().getColor(R.color.green));
                } else {
                    taskNameTextView.setBackgroundColor(getResources().getColor(R.color.row));
                }
            }
        }
    }

}
