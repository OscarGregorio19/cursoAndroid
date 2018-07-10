package goyo19.example.com.fruitworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import goyo19.example.com.fruitworld.adapter.FruitAdapter;
import goyo19.example.com.fruitworld.models.Fruit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private GridView gridView;
    private FruitAdapter adapterListView;
    private FruitAdapter adapterGridView;

    //items del option menu
    private MenuItem itemListView;
    private MenuItem itemGridView;

    private List<Fruit> frutas;

    private int opcionLista = 0;
    private int opcionGrid = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //logo en el action bar
        this.enforceIconBar();

        this.listView = findViewById(R.id.listView);
        this.gridView = findViewById(R.id.gridView);

        //cargando las frutas
        frutas = getAllFruits();

        // Adjuntando el mismo método click para ambos
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        //adapter, donde se cargan todas las frutas
        this.adapterListView = new FruitAdapter(this, R.layout.list_item, frutas);
        this.adapterGridView = new FruitAdapter(this, R.layout.grid_item, frutas);

        //enlazamos a nuestro list y grid con nuestro adaptador
        listView.setAdapter(adapterListView);
        gridView.setAdapter(adapterGridView);

        //registrar context menu para ambos
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    private void enforceIconBar() {
        getSupportActionBar().setIcon(R.mipmap.ic_fruit);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.clickFruit(frutas.get(position));
    }

    private void clickFruit(Fruit fruit) {
        // Diferenciamos entre las frutas conocidas y desconocidas
        if(fruit.getOrigin().equals("Unknown"))
            Toast.makeText(this, "Sorry, we don't have many info about " + fruit.getNombre(), Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "The best fruit from " + fruit.getOrigin() + " is " + fruit.getNombre(), Toast.LENGTH_SHORT).show();
    }

    //inflamos el layout de opciones addFruta y cambiar entre list y grid view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_bar_menu, menu);
        // Después de inflar, recogemos las referencias a los botones que nos interesan
        this.itemListView = menu.findItem(R.id.list_view);
        this.itemGridView = menu.findItem(R.id.grid_view);
        return true;
    }

    ////manejamos eventos click en el context menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item: //option añadir fruta
                addFruit(new Fruit("Frutas", R.mipmap.ic_fruit, "Unknown"));
                return true;
            case R.id.list_view:
                switchListGridView(opcionGrid);
                return true;
            case R.id.grid_view:
                switchListGridView(opcionLista);
                return true;
                default:
                    return super.onOptionsItemSelected(item);
        }
    }

    @Override //el metodo para mostrar la lista de opciones en este caso el de eliminar
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflamos el context menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        // Antes de inflar, le añadimos el header dependiendo del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.frutas.get(info.position).getNombre());
        // Inflamos
        inflater.inflate(R.menu.context_menu_fruits, menu);
    }

    private void switchListGridView(int opcion) {
        //metodo para cambiar de listView a gridview y visceversa
        if(opcion == opcionLista) {
            if(this.listView.getVisibility() == View.INVISIBLE) {
                //... escondemos el grid view y enseñamos el listView
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true); // escondo el icono
                //ponemos el gridView en cisible asi como el itemlista en visible
                this.listView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        } else if(opcion == opcionGrid){
            if(this.gridView.getVisibility() == View.INVISIBLE) {
                this.listView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                //ponemos el list vire en visible asi como el itemgrid en visible
                this.gridView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener info en el context menu del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_fruit:
                deleteFruit(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void addFruit(Fruit fruit) {
        frutas.add(fruit);
        //notificamos al adapter del cambio
        adapterListView.notifyDataSetChanged();
        adapterGridView.notifyDataSetChanged();
    }

    private void deleteFruit(int position) {
        this.frutas.remove(position);
        this.adapterListView.notifyDataSetChanged();
        this.adapterGridView.notifyDataSetChanged();
    }

    private List<Fruit> getAllFruits() {
        List<Fruit> lista = new ArrayList<Fruit>(){{
            add(new Fruit("apple", R.mipmap.ic_apple, "Madrid"));
            add(new Fruit("grapes", R.mipmap.ic_grapes, "Michoacan"));
            add(new Fruit("orange", R.mipmap.ic_orange, "Edo Mex"));
            add(new Fruit("pineapple", R.mipmap.ic_pineapple, "Coahuila"));
            add(new Fruit("raspberry", R.mipmap.ic_raspberry, "Veracruz"));
            add(new Fruit("strawberry", R.mipmap.ic_strawberry, "Barcelona"));
            add(new Fruit("watermelon", R.mipmap.ic_watermelon, "Colima"));
        }};
        return lista;
    }
}
