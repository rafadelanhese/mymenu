package com.example.manutencao.mymenu;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manutencao.mymenu.WebService.AcessaWSTask;

import org.json.JSONException;
import org.kobjects.xml.XmlReader;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.concurrent.ExecutionException;
import java.util.logging.XMLFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    private TextView tvTitulo;
    private SearchView svPesquisa;
    private EditText etReceita;

    //--
    String METHOD_NAME = "receitas";
    String URL = "http://192.168.43.38:8080/vraptor-blank-project/";
    SoapPrimitive resultString=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svPesquisa = findViewById(R.id.svPesquisa);
        etReceita = findViewById(R.id.etReceita);
        tvTitulo = findViewById(R.id.tvTitulo);

        AcessaWSTask task = new AcessaWSTask();
        try {
            String dados = task.execute(URL + METHOD_NAME).get();
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
            Toast.makeText(MainActivity.this, "InterruptedException", Toast.LENGTH_LONG).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "ExecutionException", Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            Toast.makeText(MainActivity.this, "Exception: "+e.toString(), Toast.LENGTH_LONG).show();
        }

        svPesquisa.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivitySearch.class));
            }
        });
    }
}
