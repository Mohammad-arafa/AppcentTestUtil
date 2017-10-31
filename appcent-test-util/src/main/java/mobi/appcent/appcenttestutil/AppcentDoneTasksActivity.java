package mobi.appcent.appcenttestutil;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AppcentDoneTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appcent_done_tasks);

        ListView lvTasks = (ListView) findViewById(R.id.lvTasks);

        setTitle("Done Tasks");
        ArrayAdapter<String> tasksAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, readFromTXT());
        lvTasks.setAdapter(tasksAdapter);

    }
    public List<String> readFromTXT(){
        AssetManager am = getAssets();
        List<String> tasks = new ArrayList<>();
        try {

            InputStream inputStream = am.open("tasks.txt");

            InputStreamReader inputreader = new InputStreamReader(inputStream);
            BufferedReader buffreader = new BufferedReader(inputreader);
            String line;
            while ((line = buffreader.readLine()) != null) {
                tasks.add(line);
            }
            buffreader.close();
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
            throw new IllegalArgumentException("valid tasks.txt is required in the right target");
        }
        return tasks;
    }
}
