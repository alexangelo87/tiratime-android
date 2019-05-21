package br.com.alex.tiratime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Times2Activity extends AppCompatActivity {

    private ArrayList<String> listNomes = new ArrayList<>();
    private Collection<String> colecaoNomes;
    private ArrayAdapter<String> adapter;
    final String [] arrayNomes = {};//array de nomes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times2);
        ListView lista = (ListView) findViewById(R.id.listaResultado);
        Bundle getDados = getIntent().getExtras();
        colecaoNomes = getDados.getStringArrayList("arrayNomes");
        listNomes = new ArrayList<>(Arrays.asList(arrayNomes));

        int index =1;
        for (String nome: colecaoNomes) {
            listNomes.add( index + " - " + nome);
            index++;
        }

        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,this.listNomes);
        lista.setAdapter(adapter);
    }
}
