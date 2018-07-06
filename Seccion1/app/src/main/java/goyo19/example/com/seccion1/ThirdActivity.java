package goyo19.example.com.seccion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    String nombre = "";
    int edad = 0;
    int opcion = 0;

    ImageButton btnCheck;
    Button btnCompartir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recogemos el nombre del activity anterior
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            nombre = bundle.getString("name");
            edad = bundle.getInt("edad");
            opcion = bundle.getInt("opcion");
        }

        btnCheck = findViewById(R.id.botonCheck);
        btnCompartir = findViewById(R.id.botonCompartir);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ThirdActivity.this, mensaje(nombre, edad, opcion),  Toast.LENGTH_SHORT).show();
                btnCheck.setVisibility(View.INVISIBLE);
                btnCompartir.setVisibility(View.VISIBLE);
            }
        });

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, mensaje(nombre, edad, opcion));
                startActivity(intent);
            }
        });


    }

    private String mensaje(String nombre, int edad, int opcion) {
        if (opcion == EdadActivity.SALUDO) {
            return "Hola " + nombre + ", ¿Cómo llevas esos " + edad + " años? #MyForm";
        } else {
            return "Espero verte pronto " + nombre + ", antes que cumplas " + (edad + 1) + ".. #MyForm";
        }
    }
}
