package org.vay3t.android.contador;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends Activity {

    public int contador;
    TextView textoResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textoResultado = (TextView)findViewById(R.id.contadorTexto);
        contador = 0;

        textoResultado.setText("" + contador);

        EventoTeclado teclado = new EventoTeclado();
        EditText n_reseteo = (EditText)findViewById(R.id.n_reseteo);

        n_reseteo.setOnEditorActionListener(teclado);
    }

    /*
    // persistencia de datos cuando se cambia de lado la pantalla
    public void onSaveInstanceState(Bundle estado){
        estado.putInt("cuenta",contador);
        super.onSaveInstanceState(estado);
    }

    public void onRestoreInstanceState(Bundle estado){
        super.onRestoreInstanceState(estado);
        contador = estado.getInt("cuenta");
        textoResultado.setText("" + contador);
    }
    */


    // persistencia de datos despues de cerrar
    public void onPause(){
        super.onPause();

        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor miEditor = datos.edit();

        miEditor.putInt("cuenta",contador);
        miEditor.apply();

    }

    public void onResume(){
        super.onResume();

        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);

        contador = datos.getInt("cuenta",0);

        textoResultado.setText("" + contador);

    }


    class EventoTeclado implements TextView.OnEditorActionListener{

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_DONE){
                reseteaContador(null);
            }
            return false;
        }
    }

    public void incrementaContador(View vista){
        contador++;
        textoResultado.setText("" + contador);
    }

    public void decrementaContador(View vista){
        contador--;
        if(contador < 0){
            CheckBox negativos = (CheckBox)findViewById(R.id.negativos);
            if(!negativos.isChecked()){
                contador = 0;
            }
        }
        textoResultado.setText("" + contador);
    }

    public void reseteaContador(View vista){
        EditText numero_reset = (EditText)findViewById(R.id.n_reseteo);
        try{
            contador = Integer.parseInt(numero_reset.getText().toString());
        }catch(Exception e){
            contador = 0;
        }

        numero_reset.setText("");

        InputMethodManager miteclado = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        miteclado.hideSoftInputFromWindow(numero_reset.getWindowToken(),0);
    }
}
