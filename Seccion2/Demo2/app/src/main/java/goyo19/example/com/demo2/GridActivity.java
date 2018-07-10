package goyo19.example.com.demo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    private List<String> names;
    private GridView gridView;
    private MyAdapter myAdapter;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graid);

        gridView = findViewById(R.id.gridView);

        names = new ArrayList<String>();
        for(int i=0; i<9; i++) {
            names.add("Nombre: "+i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(GridActivity.this, "Clicket "+names.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        //enlazamos con nuestro adaptador
        myAdapter = new MyAdapter(this,R.layout.grid_item, names);
        gridView.setAdapter(myAdapter);

        registerForContextMenu(gridView);
    }

    //inflamos el layaout de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    //manejamos evento click en el menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                names.add("added n°"+(++counter));
                //notificamos al adaptador del cambio
                myAdapter.notifyDataSetChanged();
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    //inflamos layaout de context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(names.get(info.position));

        inflater.inflate(R.menu.context_menu, menu);
    }

    //manejamos eventos click en el context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_item:
                //borramos item clickeado
                names.remove(info.position);
                //notificamos al adaptador del cambio
                myAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}
