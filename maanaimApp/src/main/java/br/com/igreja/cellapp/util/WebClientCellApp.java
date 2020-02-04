package br.com.igreja.cellapp.util;

import android.content.Context;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.igreja.cellapp.R;

public class WebClientCellApp {

    public String getServerData(String param, Context c) throws IOException {

//        String[] split1 = EntityUtils.toString(new DefaultHttpClient().execute(get).getEntity())
//                  .split("frame src=\"");
//        String[] splitUrl1 = split1[1].split("\" name");
//        String urlUsuarios = splitUrl1[0];
//
//        HttpGet _get1 = new HttpGet(urlUsuarios);
//        _get1.setHeader(c.getString(R.string.accept), c.getString(R.string.application_json));
//        _get1.setHeader(c.getString(R.string.content_type), c.getString(R.string.application_json));
//
//        final HttpResponse _response1 = client.execute(_get1);
//        HttpEntity _resposta1 = _response1.getEntity();

        return getPageInfo(param, "GET");
    }

    public StringBuilder postServerData(String param, Context c, String id) throws IOException {

        HttpClient client = new DefaultHttpClient();
        final StringEntity se = new StringEntity(id); //id enviado ao servidor

        final HttpPost post = new HttpPost(param);
        post.setEntity(se);
        post.setHeader(c.getString(R.string.accept), c.getString(R.string.application_json));
        post.setHeader(c.getString(R.string.content_type), c.getString(R.string.application_json));

        final HttpResponse response1 = client.execute(post);
        HttpEntity resposta1 = response1.getEntity();
        final String respostaEmHTML = EntityUtils.toString(resposta1);

//        String[] split1 = respostaEmHTML.split("frame src=\"");
//        String[] splitUrl1 = split1[1].split("\" name");
//        String urlPostMembroPosrId = splitUrl1[0];

        final HttpPost post1 = new HttpPost(respostaEmHTML);
        post1.setEntity(se);
        post1.setHeader(c.getString(R.string.accept), c.getString(R.string.application_json));
        post1.setHeader(c.getString(R.string.content_type), c.getString(R.string.application_json));
        HttpResponse execute = client.execute(post1);

        StatusLine statusLine = execute.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        if (statusCode == 200) {

            final StringBuilder str = new StringBuilder();
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;

            while ((line = reader.readLine()) != null) {
                str.append(line);
            }

            return str;

        } else {

            StringBuilder sb = new StringBuilder();
            sb.append(c.getString(R.string.erro_obter_membro));
            return sb;
        }
    }

    public HttpPost postServerData(String param, Context c) throws IOException {

//        String[] split1 = respostaEmHTML.split("frame src=\"");
//        String[] splitUrl1 = split1[1].split("\" name");
//        String urlPostPresenca = splitUrl1[0];

        final HttpPost post = new HttpPost(getPageInfo(param, "GET"));

        return post;
    }

    public String postServerDataPresenca(String param, Context c, String form) throws IOException {

        HttpClient client = new DefaultHttpClient();
        final StringEntity se = new StringEntity(form); //id enviado ao servidor

        final HttpPost post = new HttpPost(param);
        post.setEntity(se);
        post.setHeader(c.getString(R.string.accept), c.getString(R.string.application_json));
        post.setHeader(c.getString(R.string.content_type), c.getString(R.string.application_json));

        final HttpResponse execute = client.execute(post);

        StatusLine statusLine = execute.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        if (statusCode == 200) {

            final StringBuilder str = new StringBuilder();
            HttpEntity entity = execute.getEntity();
            InputStream content = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;

            while ((line = reader.readLine()) != null) {
                str.append(line);
            }

            return str.toString();

        } else {

            StringBuilder sb = new StringBuilder();
            sb.append(c.getString(R.string.erro_obter_membro));
            return sb.toString();
        }

    }

    public int postServerData(String param, Context c, Integer id) throws IOException {

        WebClientCellApp wc = new WebClientCellApp();
        HttpClient client = new DefaultHttpClient();
        final String formDeleteMembro = String.valueOf(id);
        final StringEntity se = new StringEntity(formDeleteMembro);

//        String[] split1 = respostaEmHTML.split("frame src=\"");
//        String[] splitUrl1 = split1[1].split("\" name");
//        String urlPostDeleteMembros = splitUrl1[0];

        final HttpPost post = new HttpPost(getPageInfo(param, "GET"));
        post.setEntity(se);
        post.setHeader(c.getString(R.string.accept), c.getString(R.string.application_json));
        post.setHeader(c.getString(R.string.content_type), c.getString(R.string.application_json));
        HttpResponse execute = client.execute(post);

        StatusLine statusLine = execute.getStatusLine();
        return statusLine.getStatusCode();
    }

    public String getPageInfo(String site, String getpost){

        StringBuffer respostaEmJson = new StringBuffer("");
        try{
            URL url = new URL(site);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod(getpost); //GET ou POST
            connection.setDoInput(true);
            connection.connect();

            InputStream inputStream = connection.getInputStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = rd.readLine()) != null) {
                respostaEmJson.append(line);
            }

        } catch (IOException e) {
            // writing exception to log
            e.printStackTrace();
        }

        return respostaEmJson.toString();
    }
}
