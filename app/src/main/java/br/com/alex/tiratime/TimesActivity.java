package br.com.alex.tiratime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TimesActivity extends AppCompatActivity {

    private Collection<String> colecaoNomes;
    private double numJogadoresPorTime;
    private int qtdTimes;
    private double qtdJogadores;
    private Intent intent;
    private ArrayList<String> nomes = new ArrayList<>();
    int numIntJogadoresPorTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_times);
        TextView titulo =  (TextView) findViewById(R.id.textQtdTimes);
        Bundle getDados = getIntent().getExtras();
        numJogadoresPorTime = (double) getDados.getInt("numJogadores");
        colecaoNomes = getDados.getStringArrayList("arrayNomes");
        qtdJogadores = (double) colecaoNomes.size();
        LinearLayout timesLayout = (LinearLayout) findViewById(R.id.layoutTimes);

        qtdTimes = (int) Math.ceil(qtdJogadores / numJogadoresPorTime); //arredonda pra cima

        titulo.setText("TOTAL DE TIMES SORTEADOS: "+ qtdTimes);

        Button[] button = new Button[qtdTimes];

        for (String nome: colecaoNomes) {
            nomes.add(nome);
        }

        Collections.shuffle(nomes);

        numIntJogadoresPorTime = (int) numJogadoresPorTime;

        // Cria os botões para visualização dos times
        for (int i = 0; i< qtdTimes; i++){

            button[i] = new Button(this);
            button[i].setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            button[i].setText("TIME " + (i + 1));
            button[i].setPadding(16,16,16,16);
            button[i].setId(i);
            timesLayout.addView(button[i]);

        }

        timesLayout.setOrientation(LinearLayout.VERTICAL);

        for(int i=0; i<qtdTimes; i++){
            button[i].setOnClickListener(montaTime());
        }

    }

    private View.OnClickListener montaTime(){
        return new Button.OnClickListener(){
            public void onClick(View view){
                int i = view.getId();
                int min = numIntJogadoresPorTime*i;
                int max = min +numIntJogadoresPorTime;
                ArrayList<String> nomesLista = new ArrayList<String>();
                for (String nome : nomes.subList(min,max)) {
                    nomesLista.add(nome);
                }
                intent = new Intent(contexto(),Times2Activity.class);
                Bundle dados = new Bundle();
                dados.putStringArrayList("arrayNomes",nomesLista);
                intent.putExtras(dados);
                startActivity(intent);
                nomesLista.clear();
            }
        };
    }

    private Context contexto(){
        return this;
    }

    private void alert(String texto){
        Toast.makeText(getApplicationContext(),texto,Toast.LENGTH_SHORT).show();
    }

}
