package com.jayesh.tasks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jayesh.tasks.adapters.TaskAdapter;
import com.jayesh.tasks.models.Task;
import com.jayesh.tasks.models.User;
import com.jayesh.tasks.utils.TaskDbHelper;

public class MainActivity extends AppCompatActivity {
    private User user;
    private TaskDbHelper dbHelper;
    private RecyclerView recyclerViewTaskList;
    private MenuItem loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new TaskDbHelper(this);
        recyclerViewTaskList = findViewById(R.id.recyclerView_task_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewTaskList.setLayoutManager(layoutManager);
        login();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        loggedInUser = menu.findItem(R.id.action_logout);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Logout?")
                    .setPositiveButton("Logout", (dialog12, which) -> {
                        logout();
                    })
                    .setNegativeButton("Cancel", null)
                    .create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void login() {
        final EditText loginEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter User Name")
                .setView(loginEditText)
                .setPositiveButton("Login", (dialog1, which) -> {
                    String userName = String.valueOf(loginEditText.getText());
                    if(userName.equals("")){
                        login();
                    }
                    user = new User(userName, dbHelper);
                    user.loginUser();
                    loggedInUser.setTitle(user.getUserName());
                    showTasks();
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }

    private void logout() {
        loggedInUser.setTitle("");
        user.logoutUser();
        showTasks();
        login();
    }

    private void showTasks() {
        Task task = new Task(user.getId(), dbHelper);
        TaskAdapter adapter = new TaskAdapter(task.getTasks());
        recyclerViewTaskList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addNewTask(View view) {
        final EditText todoEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(todoEditText)
                .setPositiveButton("Add", (dialog1, which) -> {
                    String newTask = String.valueOf(todoEditText.getText());
                    Task task = new Task(newTask, user.getId(), dbHelper);
                    task.addTask();
                    showTasks();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.textView_task_title);
        String selectedTask = String.valueOf(taskTextView.getText());
        Task task = new Task(selectedTask, user.getId(), dbHelper);
        task.deleteTask();
        showTasks();
    }
}