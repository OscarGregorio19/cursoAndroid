package goyo19.example.com.demo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamera;

    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextWeb = findViewById(R.id.editTextWeb);
        imgBtnPhone = findViewById(R.id.imageButtonPhone);
        imgBtnWeb = findViewById(R.id.imageButtonWeb);
        imgBtnCamera = findViewById(R.id.imageButtonCamera);

        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = editTextPhone.getText().toString();
                if (phone != null && !phone.isEmpty()) {
                    //comprobar version actual del telefono
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        //comprobar si acepto, denego o unca se le ha preguntado los permisos
                        if(CheckPermission(Manifest.permission.CALL_PHONE)) {
                            //ha aceptado
                            Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                            startActivity(intentCall);
                        } else {
                            // no acepto o nunca se le ha preguntado
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                                // no se ha preguntado aún
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            } else {
                                //ha denegado
                                Toast.makeText(ThirdActivity.this, "Has denegado el permiso", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                    } else {
                        OlderVersions(phone);
                    }

                } else {
                    Toast.makeText(ThirdActivity.this, "el numero no puede estar vacio", Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String phone) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the  access", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Botón para la dirección web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = editTextWeb.getText().toString();
                String email = "alejandrofpo@gmail.com";

                if (url != null && !url.isEmpty()) {
                    Intent intentWeb = new Intent();
                    intentWeb.setAction(Intent.ACTION_VIEW);
                    intentWeb.setData(Uri.parse("http://"+url));

                    // Contactos
                    Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people"));
                    // Email rápido
                    Intent intentMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+email));
                    // Email completo
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mail's title");
                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hi there, I love MyForm app, but... ");
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[] {"fernando@gmail.com", "antonio@gmail.com"});
                    //startActivity(Intent.createChooser(intentMail, "Elige cliente de correo"));

                    Intent intentPhone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:666111222")); // Teléfono 2, sin permisos requeridos

                    startActivity(intentMail);
                }
            }
        });

        imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abrir camara
                Intent intentCamara = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamara, PICTURE_FROM_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
            case PICTURE_FROM_CAMERA:
                if(resultCode == Activity.RESULT_OK) {
                    String result = data.toUri(0);
                    Toast.makeText(ThirdActivity.this, "Result: "+result,Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ThirdActivity.this, "There was an error with the picture, try again",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //comprobar si fue aceptada o denegada la peticion de permiso
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //SE CONCEDIO PERMISO
                        String phone = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) return;
                        startActivity(intentCall);
                    } else {
                        Toast.makeText(ThirdActivity.this, "You declined the  access", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    break;
        }

    }

    private boolean CheckPermission(String permission) {
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
