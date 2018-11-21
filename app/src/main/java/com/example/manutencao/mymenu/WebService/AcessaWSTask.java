package com.example.manutencao.mymenu.WebService;

import android.os.AsyncTask;

/**
 * Created by Rafael Delanhese on 19/05/2018.
 */

public class AcessaWSTask extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... url) {
        String dados = AcessaWS.consumir(url[0]);
        return dados;
    }
}
