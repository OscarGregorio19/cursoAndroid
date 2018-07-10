package goyo19.example.com.seccion1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EdadActivity extends AppCompatActivity {

    private RadioButton radioSaludo;
    private RadioButton radioDespedida;
    private Button botonNext;
    private SeekBar seekBarEdad;
    private TextView textoEdad;

    private String nombre = "";
    public static final int SALUDO = 1;
    public static final int DESPEDIDA = 2;
    private int edad = 20;
    private final int edadMin = 16;
    private final int edadMax = 60;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edad);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recogemos el nombre del activity anterior
        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            nombre = bundle.getString("name");
        }

        radioSaludo = findViewById(R.id.radioSaludo);
        radioDespedida = findViewById(R.id.radioDespedida);
        botonNext = findViewById(R.id.botonNextEdad);
        seekBarEdad = findViewById(R.id.seekBarEdad);
        textoEdad = findViewById(R.id.textViewEdad);

        //evento para obtener el valor de seekBar
        seekBarEdad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int currentAge, boolean b) {
                edad = currentAge;
                textoEdad.setText(edad+"");
                //Toast.makeText(EdadActivity.this, ""+edad,  Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //no hacemos nada en este metodo, pero  debe existir
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                edad = seekBar.getProgress();
                textoEdad.setText(edad+"");

                if(edad > edadMax) {
                    botonNext.setVisibility(View.INVISIBLE);
                    Toast.makeText(EdadActivity.this, "La edad maxima es de "+edadMax,  Toast.LENGTH_SHORT).show();
                } else if(edad < edadMin) {
                    botonNext.setVisibility(View.INVISIBLE);
                    Toast.makeText(EdadActivity.this, "La edad minima es de "+edadMin,  Toast.LENGTH_SHORT).show();
                } else {
                    botonNext.setVisibility(View.VISIBLE);
                }

            }
        });

        botonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //valor del radioButton
                int opcion = (radioSaludo.isChecked()) ? SALUDO:DESPEDIDA;
                Intent intent = new Intent(EdadActivity.this, ThirdActivity.class);
                intent.putExtra("name", nombre);
                intent.putExtra("edad", edad);
                intent.putExtra("opcion", opcion);
                startActivity(intent);

                //Toast.makeText(EdadActivity.this, ""+opcion,  Toast.LENGTH_SHORT).show();
            }
        });

    }
}
