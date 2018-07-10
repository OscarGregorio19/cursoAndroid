package goyo19.example.com.demo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //listview, gridview y adapter
    private ListView listView;
    private List<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lista);

        names = new ArrayList<String>();
        for(int i=0; i<15; i++) {
            names.add("Nombre: "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Clicket "+names.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        //enlazamos con nuestro adaptador
        MyAdapter myAdapter = new MyAdapter(this,R.layout.list_item, names);
        listView.setAdapter(myAdapter);
    }
}


