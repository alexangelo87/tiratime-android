package br.com.alex.tiratime;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private TextView TextViewNomes;
    private ArrayList<String> nomes;
    private ArrayAdapter<String> adapter;
    private ListView listaNomes;
    private  Button btnSortear;
    private int posicaoRemover;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//Toolbar
        setSupportActionBar(toolbar);//Add a toolbar

        String [] arrayNomes = {};//array de nomes
        nomes = new ArrayList<>(Arrays.asList(arrayNomes));//arraylist de nomes que se comporta como array
        adapter = new ArrayAdapter<>(contexto(),android.R.layout.simple_list_item_1,nomes);
        listaNomes = (ListView) findViewById(R.id.listViewNomes);//listview
        listaNomes.setAdapter(adapter);
        //listaNomes.setOnItemClickListener(mMessageClickedHandler);//evento de clique no item da lista
        btnSortear = (Button) findViewById(R.id.btnSortear);//botão de sorteio dos times


        FloatingActionButton addJogador = (FloatingActionButton) findViewById(R.id.addJogador);//add jogador


        addJogador.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                if (getNomeJogadores().trim().length()>1)
                    nomes.add(getNomeJogadores());
                else
                    alert("Insira um nome!");
                adapter.notifyDataSetChanged();
                // Checa se a view está com foco para esconder o teclado
                view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                TextViewNomes.setText("");
                //Se houver elementos na lista o botão de sorteio aparece
                if(nomes.size()>0 && getNumJogadores()>0 && nomes.size()>=getNumJogadores())
                    btnSortear.setVisibility(View.VISIBLE);
            }
        });

        //long press in list view
        listaNomes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                menuPopup(view);
                posicaoRemover = position;
                return false;
            }
        });

        //ação do botão de sortear
        btnSortear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nomes.size()>0 && getNumJogadores()>0 && nomes.size()>=getNumJogadores()) {
                    Intent intent = new Intent(contexto(), TimesActivity.class);
                    Bundle dados = new Bundle();
                    dados.putInt("numJogadores", getNumJogadores());
                    dados.putStringArrayList("arrayNomes", nomes);
                    intent.putExtras(dados);
                    startActivity(intent);
                }else{
                    alert("Verifique a quantidade de jogadores por time");
                }
            }
        });
    }

    //eventos de lista
    /*private AdapterView.OnItemClickListener mMessageClickedHandler = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            alert("Item clicado " + String.valueOf(position));
        }
    };*/


    //pega nome do jogador
    private String getNomeJogadores(){
        TextViewNomes = (TextView) findViewById(R.id.editTextNome);
        return TextViewNomes.getText().toString();
    }

    //pega total de jogadores por time
    private int getNumJogadores(){
        TextView TextViewnumJogadores = (TextView) findViewById(R.id.editTextNumJogadores);
        if(TextViewnumJogadores.getText().toString().trim().length()>0)
            return Integer.parseInt(TextViewnumJogadores.getText().toString());
        else
            return 0;
    }

    //pega o contexto
    private Context contexto(){
        return this;
    }

    //função de Toast
    private void alert(String texto){
        Toast.makeText(contexto(),texto,Toast.LENGTH_LONG).show();
    }

    //MENU POPUP
    private void menuPopup(View view){
        PopupMenu popup = new PopupMenu(contexto(),view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_popup);
        popup.show();
    }

    //Eventos do menu POPUP
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()){
            case R.id.deletar:
                nomes.remove(posicaoRemover);//remove o nome
                adapter.notifyDataSetChanged();//atualiza a lista
                //Lógica para esconder o botão de sorteio
                if(nomes.size()==0 || getNumJogadores()==0 || nomes.size()<getNumJogadores())
                    btnSortear.setVisibility(View.INVISIBLE);
                return true;
            default:
                return false;
        }
    }
}
