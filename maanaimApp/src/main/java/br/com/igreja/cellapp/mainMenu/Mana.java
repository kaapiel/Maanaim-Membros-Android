package br.com.igreja.cellapp.mainMenu;

import java.util.Calendar;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.util.Mensagens;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.DatePicker;

public class Mana extends Activity{
	
	private int mYear;
	private int mMonth;
	private int mDay;
	private String data = "";
	private Mensagens m = new Mensagens(this);
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mana);
		
		m.toastMensagem(getString(R.string.escolha_data_mana), 350, 200, 0, R.drawable.mapp_ic_pao).show();
		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		 
		DatePickerDialog dpd = new DatePickerDialog(this,
		        new DatePickerDialog.OnDateSetListener() {
		 
					@Override
		            public void onDateSet(DatePicker view, int year,
		                    int monthOfYear, int dayOfMonth) {
						if (dayOfMonth < 10){
							data = "0"+dayOfMonth;
						} else {
							data = ""+dayOfMonth;
						}
						
						if((monthOfYear + 1) < 10){
							data += "0"+(monthOfYear+1);
						} else {
							data = String.valueOf((monthOfYear+1));
						}
						
						Log.v(getString(R.string.data)+": ", data);
						selecionarMana(data);
		            }
					
					
					
		        }, mYear, mMonth, mDay);
		dpd.setCancelable(false);
		dpd.show();
	    
		
		
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	public void selecionarMana(String data){
		try{
			String LinkTo = "http://maanaimosasco.com.br/manas/"+data+".pdf";
			WebView mWebView = (WebView) findViewById(R.id.webManaView);
			WebSettings settings = mWebView.getSettings();
			settings.setJavaScriptEnabled(true);
			mWebView.getSettings().setPluginState(PluginState.ON);
			mWebView.loadUrl("https://docs.google.com/gview?embedded=true&url="+LinkTo);
		} catch (Exception e){
			m.alertDialogMensagemOK(getString(R.string.aviso), getString(R.string.erro_mana), R.drawable.mapp_ic_pao).show();
		}
	}

}
