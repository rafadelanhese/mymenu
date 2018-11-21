package com.example.manutencao.mymenu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manutencao.mymenu.Model.Receita;
import com.example.manutencao.mymenu.WebService.AcessaWSTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ReceitaActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private EditText etReceita;

    String URL = "http://192.168.43.38:8080/vraptor-blank-project/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receita);

        etReceita = findViewById(R.id.etReceita);
        tvTitulo = findViewById(R.id.tvTitulo);

        Intent intent = getIntent();
        String TITULO = intent.getStringExtra("titulo");
        String ORIGEM = intent.getStringExtra("origem");
        String CATEGORIA = intent.getStringExtra("categoria");

        AcessaWSTask task = new AcessaWSTask();
        try {
            String dados = task.execute(URL + "procura/" + TITULO + "/" + ORIGEM +"/"+ CATEGORIA ).get();
            DocumentBuilderFactory factory;
            DocumentBuilder builder;
            Document doc=null;

            factory=DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            doc = builder.parse(new InputSource(new StringReader(dados)));

            Element element = (Element) doc.getElementsByTagName("modoPreparo").item(0);
            Element ingredientesElement = (Element) doc.getElementsByTagName("ingredientes").item(0);
            Element tituloElement = (Element) doc.getElementsByTagName("titulo").item(0);

            tvTitulo.setText(tituloElement.getTextContent());
            etReceita.setText("INGREDIENTES: "+ingredientesElement.getTextContent()+"\n\nMODO DE PREPARO: "+element.getTextContent());

        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(ReceitaActivity.this, "InterruptedException", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(ReceitaActivity.this, "ExecutionException", Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(ReceitaActivity.this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
