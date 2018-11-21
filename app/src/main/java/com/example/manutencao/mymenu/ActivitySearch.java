package com.example.manutencao.mymenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manutencao.mymenu.Model.Categoria;
import com.example.manutencao.mymenu.Model.Origem;
import com.example.manutencao.mymenu.Model.Receita;
import com.example.manutencao.mymenu.WebService.AcessaWSTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.R.layout.simple_list_item_1;

public class ActivitySearch extends Activity {

    private EditText etPesquisa;
    private Spinner spOrigem;
    private Spinner spCategoria;
    private Button btProcurar;
    private ListView lvResultPesquisa;
    private List<String> listaOrigem = new ArrayList<>();
    private List<String> listaCategoria = new ArrayList<>();
    private List<Receita> listaResultado = new ArrayList<>();

    private String ORIGEM = null;
    private String CATEGORIA = null;

    String URL = "http://192.168.43.38:8080/vraptor-blank-project/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etPesquisa = findViewById(R.id.etPesquisa);
        spOrigem = findViewById(R.id.spOrigem);
        spCategoria = findViewById(R.id.spCategoria);
        btProcurar = findViewById(R.id.btProcurar);
        lvResultPesquisa = findViewById(R.id.lvResultPesquisa);


        populaSpinnerOrigem();
        populaSpinnerCategoria();

        spOrigem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ORIGEM = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                CATEGORIA = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPesquisa.getText().length()>0 && ORIGEM != null && CATEGORIA != null){
                    AcessaWSTask task = new AcessaWSTask();
                    try {
                        String dados = task.execute(URL + "procura/" + etPesquisa.getText() + "/" + ORIGEM +"/"+ CATEGORIA ).get();
                        DocumentBuilderFactory factory;
                        DocumentBuilder builder;
                        Document doc=null;

                        factory=DocumentBuilderFactory.newInstance();
                        builder = factory.newDocumentBuilder();
                        doc = builder.parse(new InputSource(new StringReader(dados)));

                        NodeList receita = doc.getElementsByTagName("receita");
                        for (int i = 0; i < receita.getLength(); i++) {
                            String titulo = ((Element) receita.item(i)).getElementsByTagName("titulo").item(0).getTextContent();
                            String categoriaDesc = ((Element) receita.item(i)).getElementsByTagName("descricao").item(0).getTextContent();
                            String origemDesc = ((Element) receita.item(i)).getElementsByTagName("descricao").item(1).getTextContent();

                            listaResultado.add(new Receita(titulo, new Categoria(categoriaDesc), new Origem(origemDesc)));
                        }

                        ArrayAdapter<Receita> receitaArrayAdapter = new ArrayAdapter<Receita>(ActivitySearch.this, android.R.layout.simple_list_item_1, listaResultado);

                        lvResultPesquisa.setAdapter(receitaArrayAdapter);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivitySearch.this, "InterruptedException", Toast.LENGTH_LONG).show();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                        Toast.makeText(ActivitySearch.this, "ExecutionException", Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        Toast.makeText(ActivitySearch.this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"É necessário Digitar algo para\nRealizar a Pesquisa.",Toast.LENGTH_LONG).show();
                }
            }
        });


        lvResultPesquisa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                receitaActivity(listaResultado.get(i));
            }
        });
    }

    private void populaSpinnerOrigem(){
        AcessaWSTask task = new AcessaWSTask();
        try {
            String dados = task.execute(URL + "origem").get();
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document doc=null;

            factory=DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(dados)));

            NodeList nodes = doc.getElementsByTagName("descricao");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                listaOrigem.add(element.getTextContent());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaOrigem);
            ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            spOrigem.setAdapter(spinnerArrayAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(ActivitySearch.this, "InterruptedException", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(ActivitySearch.this, "ExecutionException", Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(ActivitySearch.this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void populaSpinnerCategoria(){
        AcessaWSTask task = new AcessaWSTask();
        try {
            String dados = task.execute(URL + "categoria").get();
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document doc=null;

            factory=DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(dados)));

            NodeList nodes = doc.getElementsByTagName("descricao");
            for (int i = 0; i < nodes.getLength(); i++) {
                Element element = (Element) nodes.item(i);
                listaCategoria.add(element.getTextContent());
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCategoria);
            ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
            spCategoria.setAdapter(spinnerArrayAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(ActivitySearch.this, "InterruptedException", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(ActivitySearch.this, "ExecutionException", Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(ActivitySearch.this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void receitaActivity(Receita receita){
        Intent intent = new Intent(this, ReceitaActivity.class);
        intent.putExtra("titulo", receita.getTitulo());
        intent.putExtra("origem", receita.getOrigem().getDescricao());
        intent.putExtra("categoria", receita.getCategoria().getDescricao());
        startActivity(intent);
    }
}
