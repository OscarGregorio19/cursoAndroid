package goyo19.example.com.seccion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNombre;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //forzar y cargar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        editNombre = findViewById(R.id.editTextNombre);
        buttonNext = findViewById(R.id.botonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = editNombre.getText().toString();

                if(nombre != null && !nombre.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, EdadActivity.class);
                    intent.putExtra("name", nombre);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "El campo nombre no puede ir vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
