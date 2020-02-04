package br.com.igreja.cellapp.mainMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.util.Mensagens;

public class Sugestoes extends Activity{

	private Mensagens mensagens =  new Mensagens(this);
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sugestoes);
	
		Button botaoEnviar = (Button) findViewById(R.id.botaoEnviarSugestao);
		Button botaoCancelar = (Button) findViewById(R.id.botaoCancelarSugestao);
		final EditText editTextSugestao = (EditText) findViewById(R.id.editTextSugestao);
		
		botaoCancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent i = new Intent(Sugestoes.this, Inicio.class);
				startActivity(i);
			}
		});
		
		botaoEnviar.setOnClickListener(new OnClickListener() {
			

			@Override
			public void onClick(View v) {

				Intent email = new Intent(Intent.ACTION_SEND);
				email.setType("message/rfc822");
				email.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.email_admin)});
				email.putExtra(Intent.EXTRA_SUBJECT, "[Aplicativo Maanaim] - [Sugestão]");
				email.putExtra(Intent.EXTRA_TEXT   , editTextSugestao.getText().toString()+"\n\n\n\nVisitante\nEnviado pelo aplicativo Maanaim.");

				try {
				    startActivity(Intent.createChooser(email, getString(R.string.enviar_email)));
				} catch (android.content.ActivityNotFoundException ex) {
					mensagens = new Mensagens(Sugestoes.this);
					mensagens.alertDialogMensagemOK(getString(R.string.email), getString(R.string.nao_ha_app_email)+". "+ getString(R.string.sugestao_pc)+
							getString(R.string.email_admin), R.drawable.mapp_ic_email);
				}
				
	            startActivity(email);
				
			}
		});
	
	}

}
