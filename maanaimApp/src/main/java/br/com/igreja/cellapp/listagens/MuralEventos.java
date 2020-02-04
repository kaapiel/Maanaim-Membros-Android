package br.com.igreja.cellapp.listagens;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.adapter.ListaEventosAdapter;
import br.com.igreja.cellapp.dao.AvisosDAO;
import br.com.igreja.cellapp.model.Avisos;
import br.com.igreja.cellapp.util.Mensagens;
import br.com.igreja.cellapp.util.Parametros;
import br.com.igreja.cellapp.util.WebClientCellApp;

public class MuralEventos extends Activity{

	private ListView lista;
	private ListView listaHoje;
	private String stringExtra;
	private ProgressDialog progresso;
	private List<Avisos> listaDeAvisos;

	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mural_avisos);
		
		Intent intent = getIntent();
		stringExtra = intent.getStringExtra(getString(R.string.put_extra_push));

		lista = (ListView) findViewById(R.id.listaEventos);
		listaHoje = (ListView) findViewById(R.id.listaEventosHoje);
		TextView txtEventos = (TextView) findViewById(R.id.txtEventos);
		TextView txtEventosHoje = (TextView) findViewById(R.id.txtEventosHoje);
		
		txtEventos.setTextColor(getResources().getColor(R.color.laranja));
		txtEventosHoje.setTextColor(getResources().getColor(R.color.laranja));
		
		progresso = new ProgressDialog(this);
		progresso = ProgressDialog.show(this, getString(R.string.aguarde), getString(R.string.obter_noticias), false, true);
		progresso.setIcon(R.drawable.mapp_ic_anotacao); 
		progresso.setCancelable(false);

		if (stringExtra != null) {
			Mensagens m = new Mensagens(this);
			
			String[] splitIds = stringExtra.split("\"alert\":");
			
			String alert = splitIds[1];
			String[] split = alert.split("push_hash");
			String replaceAll = split[0].replaceAll("\"", "");
			String replaceAll2 = replaceAll.replaceAll(",", "");
			
			m.alertDialogMensagemOK(getString(R.string.aviso), replaceAll2, R.drawable.mapp_ic_anotacao).show();
			
		}
		final Mensagens m = new Mensagens(this);
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || 
				cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
			
			Thread thread = new Thread(){
				
				@Override
				public void run() {

					try{

						Gson gs = new Gson();
						JsonParser jp = new JsonParser();
						JsonArray membrosJSON = (JsonArray) jp.parse(new WebClientCellApp()
								.getServerData(Parametros.URL_GETAVISOS, MuralEventos.this)).getAsJsonObject().get(getString(R.string.json_avisos));

						listaDeAvisos = new ArrayList<Avisos>();
						
						for (JsonElement je : membrosJSON) {
							Avisos fromJson = gs.fromJson(je, Avisos.class);
							listaDeAvisos.add(fromJson);
						}
						
						AvisosDAO daoA = new AvisosDAO(MuralEventos.this);
						daoA.deleteAvisos();
						daoA.gravarAvisos(listaDeAvisos);
						final ArrayList<Avisos> listaDeAvisosComInternet = daoA.getListaDeAvisos(
								getString(R.string.param_data_evento)+" "+getString(R.string.desc));
						
						MuralEventos.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								Date dataHoje = new Date();
								
								final ArrayList<Avisos> listaDeAvisosComInternetSEMHOJE = new ArrayList<Avisos>();
								
								for (Avisos a : listaDeAvisosComInternet) {
									
									String dataEvento = a.getDataEvento();
									String[] split = dataEvento.split("-");
									
									int diaEvento = Integer.valueOf(split[2].substring(0, 2));
									int mesEvento = Integer.valueOf(split[1]);
									int anoEvento = Integer.valueOf(split[0]);
									
									if((dataHoje.getYear()+1900) != anoEvento || (dataHoje.getMonth()+1) != mesEvento ||
											dataHoje.getDate() != diaEvento){
							
										listaDeAvisosComInternetSEMHOJE.add(a);
									}
								}
								
								ListaEventosAdapter adapter = new ListaEventosAdapter(listaDeAvisosComInternetSEMHOJE, MuralEventos.this);
								lista.setAdapter(adapter);
								
								final ArrayList<Avisos> listaDeAvisosComInternetHOJE = new ArrayList<Avisos>();
								
								for (Avisos a : listaDeAvisosComInternet) {
									
									String dataEvento = a.getDataEvento();
									String[] split = dataEvento.split("-");
									
									int diaEvento = Integer.valueOf(split[2].substring(0, 2));
									int mesEvento = Integer.valueOf(split[1]);
									int anoEvento = Integer.valueOf(split[0]);
									
									if((dataHoje.getYear()+1900) == anoEvento && (dataHoje.getMonth()+1) == mesEvento &&
											dataHoje.getDate() == diaEvento){
							
										listaDeAvisosComInternetHOJE.add(a);
									}
								}
								
								ListaEventosAdapter adapter2 = new ListaEventosAdapter(listaDeAvisosComInternetHOJE, MuralEventos.this);
								listaHoje.setAdapter(adapter2);
								
								progresso.dismiss();
							}
						});
						
			      }catch (final Exception e){
			    	  
			    	  MuralEventos.this.runOnUiThread(new Runnable() {
						public void run() {

							Log.w("teste de resposta: ", e.toString());
							if(e.getCause().toString().contains("ETIMEDOUT")){
								m.toastMensagem(getString(R.string.erro_conexao), 350, 200, 0, R.drawable.mapp_ic_computador).show();
							}
						}
					});
			    	  
			    	e.printStackTrace();
			    	progresso.dismiss();
			      }
					super.run();
				}
			};
			thread.start();
			
        } else {
        	
	    m.alertDialogMensagemOK(getString(R.string.conexao_internet), getString(R.string.erro_eventos_internet), R.drawable.mapp_ic_emotion_poxa_vida).show();
	    
	    AvisosDAO daoA = new AvisosDAO(MuralEventos.this);
		ArrayList<Avisos> listaDeAvisosSemInternet = daoA.getListaDeAvisos(
				getString(R.string.param_data_evento)+getString(R.string.desc));
		
		Date dataHoje = new Date();
		
		final ArrayList<Avisos> listaDeAvisosSemInternetSEMHOJE = new ArrayList<Avisos>();
		
		for (Avisos a : listaDeAvisosSemInternet) {
			
			String dataEvento = a.getDataEvento();
			String[] split = dataEvento.split("-");
			
			int diaEvento = Integer.valueOf(split[2].substring(0, 2));
			int mesEvento = Integer.valueOf(split[1]);
			int anoEvento = Integer.valueOf(split[0]);
			
			if((dataHoje.getYear()+1900) != anoEvento || (dataHoje.getMonth()+1) != mesEvento ||
					dataHoje.getDate() != diaEvento){
	
				listaDeAvisosSemInternetSEMHOJE.add(a);
			}
		}
		
		ListaEventosAdapter adapter = new ListaEventosAdapter(listaDeAvisosSemInternetSEMHOJE, MuralEventos.this);
		lista.setAdapter(adapter);
		
		final ArrayList<Avisos> listaDeAvisosSemInternetHOJE = new ArrayList<Avisos>();
		
		for (Avisos a : listaDeAvisosSemInternet) {
			
			String dataEvento = a.getDataEvento();
			String[] split = dataEvento.split("-");
			
			int diaEvento = Integer.valueOf(split[2].substring(0, 2));
			int mesEvento = Integer.valueOf(split[1]);
			int anoEvento = Integer.valueOf(split[0]);
			
			if((dataHoje.getYear()+1900) == anoEvento && (dataHoje.getMonth()+1) == mesEvento &&
					dataHoje.getDate() == diaEvento){
	
				listaDeAvisosSemInternetHOJE.add(a);
			}
		}
		
		ListaEventosAdapter adapter2 = new ListaEventosAdapter(listaDeAvisosSemInternetHOJE, MuralEventos.this);
		listaHoje.setAdapter(adapter2);
		progresso.dismiss();
		
        }
		
		lista.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int posicao, long id) {
				
				Avisos a = (Avisos) parent.getItemAtPosition(posicao);
				Intent intent = new Intent(MuralEventos.this, FotoEvento.class);
				intent.putExtra(getString(R.string.param_foto_evento), a.getImagemEvento());
				startActivity(intent);
				overridePendingTransition(R.animator.foto_evento_anim_1, R.animator.foto_evento_anim_2);
				
			}
		});
		
		listaHoje.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int posicao, long id) {
				
				Avisos a = (Avisos) parent.getItemAtPosition(posicao);
				Intent intent = new Intent(MuralEventos.this, FotoEvento.class);
				intent.putExtra(getString(R.string.param_foto_evento), a.getImagemEvento());
				startActivity(intent);
				overridePendingTransition(R.animator.foto_evento_anim_1, R.animator.foto_evento_anim_2);
				
			}
		});
		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
}
