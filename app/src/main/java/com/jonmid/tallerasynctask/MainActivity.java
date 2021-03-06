package com.jonmid.tallerasynctask;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar cargador;
    Button boton;
    List<Post> mysPost;
    ListView LIST;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    /*TextView texto;
    TextView texto2;
    TextView texto3;
    TextView texto4;
    TextView texto5;
    TextView texto6;
    TextView texto7;
    */



        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargador = (ProgressBar) findViewById(R.id.cargador);
        boton = (Button) findViewById(R.id.boton);
        LIST = (ListView) findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        /*texto = (TextView) findViewById(R.id.texto);
        texto2= (TextView) findViewById(R.id.texto2);
        texto3= (TextView) findViewById(R.id.texto3);
        texto4 = (TextView) findViewById(R.id.texto4);
        texto5= (TextView) findViewById(R.id.texto5);
        texto6= (TextView) findViewById(R.id.texto6);
        texto7= (TextView) findViewById(R.id.texto7);*/


    }

    public Boolean isOnLine(){
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    public void onClickButton(View view){
        if (isOnLine()){
            MyTask task = new MyTask();
            task.execute("https://jsonplaceholder.typicode.com/posts");
        }else {
            Toast.makeText(this, "Sin conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarDatos(){

        ArrayList<String> lista = new ArrayList<>();
        if(mysPost != null){
            for (Post post:mysPost){

            lista.add(post.getTitle());
            }
            ArrayAdapter<String> adaptador= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,lista);
            LIST.setAdapter(adaptador);
            /*
                for (Post post:mysPost){
                    if(post.getId() ==1){
                        texto.append(post.getTitle());
                    }else if (post.getId() ==2){
                        texto2.append(post.getTitle());
                    }else if (post.getId() ==3){
                        texto3.append(post.getTitle());
                    }else if (post.getId() ==4){
                        texto4.append(post.getTitle());
                    }else if (post.getId() ==5){
                        texto5.append(post.getTitle());
                    }else if (post.getId() ==6){
                        texto6.append(post.getTitle());
                    }
                    else if (post.getId() ==7){
                        texto7.append(post.getTitle());
                    }
                }
        */

        }


    }

    private class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargador.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = null;
            try {
                content = HttpManager.getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                mysPost = JsonParser.parse(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cargarDatos();
            cargador.setVisibility(View.GONE);
        }
    }
}
