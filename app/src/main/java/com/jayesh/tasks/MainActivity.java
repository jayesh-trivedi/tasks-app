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
import com.jayesh.tasks.models.TaskModel;
import com.jayesh.tasks.models.UserModel;
import com.jayesh.tasks.utils.TaskDbHelper;

public class MainActivity extends AppCompatActivity {
    private UserModel user;
    private TaskModel task;
    private TaskDbHelper dbHelper;
    private RecyclerView recyclerViewTaskList;
    private MenuItem loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new TaskDbHelper(this);
        user = new UserModel(dbHelper);
        task = new TaskModel(dbHelper);
        recyclerViewTaskList = findViewById(R.id.recyclerView_task_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
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
        switch (item.getItemId()) {
            case R.id.action_logout:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Logout?")
                        .setPositiveButton("Logout", (dialog12, which) -> {
                            login();
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void login(){
        final EditText loginEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Enter User Name")
                .setView(loginEditText)
                .setPositiveButton("Login", (dialog1, which) -> {
                    String userName = String.valueOf(loginEditText.getText());
                    user.loginUser(userName);
                    loggedInUser.setTitle(user.getUserName());
                    showTasks();
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }

    private void showTasks(){
        TaskAdapter adapter = new TaskAdapter(task.getTasks(user.getId()));
        recyclerViewTaskList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void addNewTask(View view){
        final EditText todoEditText = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Add Task")
                .setView(todoEditText)
                .setPositiveButton("Add", (dialog1, which) -> {
                    String newTask = String.valueOf(todoEditText.getText());
                    task.addTask(newTask, user.getId());
                    showTasks();
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    public void deleteTask(View view){
        View parent = (View) view.getParent();
        TextView taskTextView = parent.findViewById(R.id.textView_task_title);
        String selectedTask = String.valueOf(taskTextView.getText());
        task.deleteTask(selectedTask, user.getId());
        showTasks();
    }
}