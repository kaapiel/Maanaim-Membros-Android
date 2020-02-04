package br.com.igreja.cellapp.activities;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.dao.BibliaDAO;
import br.com.igreja.cellapp.mainMenu.BibliaMaanaim;
import br.com.igreja.cellapp.util.Mensagens;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SelecionarVersao extends Activity{

	@SuppressWarnings("unused")
	private RadioButton radioButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecionar_versao);
		
		final Mensagens mensagens = new Mensagens(this);
		
		final Typeface type2 = Typeface.createFromAsset(getAssets(),"fonts/ftl.ttf");
		
		final RadioGroup radios = (RadioGroup) findViewById(R.id.radios);
		Button botaoIr = (Button) findViewById(R.id.BotaoIrVersao);
		TextView ARC = (TextView) findViewById(R.id.ARC);
		TextView ARA = (TextView) findViewById(R.id.ARA);
		TextView ACF = (TextView) findViewById(R.id.ACF);
		TextView NVI = (TextView) findViewById(R.id.NVI);
		TextView NTLH = (TextView) findViewById(R.id.NTLH);
		
		ARC.setTypeface(type2);
		ARA.setTypeface(type2);
		ACF.setTypeface(type2);
		NVI.setTypeface(type2);
		NTLH.setTypeface(type2);
		
		botaoIr.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

			int selectedId = radios.getCheckedRadioButtonId();
 			radioButton = (RadioButton) findViewById(selectedId);
 			
 			switch (selectedId) {
			case R.id.ARC:
				BibliaDAO bibliaDAO = new BibliaDAO(SelecionarVersao.this, getString(R.string.versaoARC));
				
				if(bibliaDAO.existeDadosNoBD()){
					Intent biblia = new Intent(SelecionarVersao.this, BibliaMaanaim.class);
					biblia.putExtra(getString(R.string.versao_minusculo), getString(R.string.versaoARC));
					startActivity(biblia);
				} else {
					Toast toastMensagem = mensagens.toastMensagem(getString(R.string.aviso_vers_arc),
							100, 550, 0, R.drawable.mapp_ic_biblia);
					toastMensagem.show();
				}
				
				break;
			case R.id.ARA:
				BibliaDAO bibliaDAO2 = new BibliaDAO(SelecionarVersao.this, getString(R.string.versaoARA));
				
				if(bibliaDAO2.existeDadosNoBD()){
					Intent biblia = new Intent(SelecionarVersao.this, BibliaMaanaim.class);
					biblia.putExtra(getString(R.string.versao_minusculo), getString(R.string.versaoARA));
					startActivity(biblia);
				} else {
					Toast toastMensagem = mensagens.toastMensagem(getString(R.string.aviso_vers_ara),
							100, 550, 0, R.drawable.mapp_ic_biblia);
					toastMensagem.show();
				}
				
				break;
			case R.id.ACF:
				BibliaDAO bibliaDAO3 = new BibliaDAO(SelecionarVersao.this, getString(R.string.versaoACF));
				
				if(bibliaDAO3.existeDadosNoBD()){
					Intent biblia = new Intent(SelecionarVersao.this, BibliaMaanaim.class);
					biblia.putExtra(getString(R.string.versao_minusculo), getString(R.string.versaoACF));
					startActivity(biblia);
				} else {
					Toast toastMensagem = mensagens.toastMensagem(getString(R.string.aviso_vers_acf),
							100, 550, 0, R.drawable.mapp_ic_biblia);
					toastMensagem.show();
				}
				break;
			case R.id.NVI:
				BibliaDAO bibliaDAO4 = new BibliaDAO(SelecionarVersao.this, getString(R.string.versaoNVI));
				
				if(bibliaDAO4.existeDadosNoBD()){
					Intent biblia = new Intent(SelecionarVersao.this, BibliaMaanaim.class);
					biblia.putExtra(getString(R.string.versao_minusculo), getString(R.string.versaoNVI));
					startActivity(biblia);
				} else {
					Toast toastMensagem = mensagens.toastMensagem(getString(R.string.aviso_vers_nvi),
							100, 550, 0, R.drawable.mapp_ic_biblia);
					toastMensagem.show();
				}
				break;
			case R.id.NTLH:
				BibliaDAO bibliaDAO5 = new BibliaDAO(SelecionarVersao.this, getString(R.string.versaoNTLH));
				
				if(bibliaDAO5.existeDadosNoBD()){
					Intent biblia = new Intent(SelecionarVersao.this, BibliaMaanaim.class);
					biblia.putExtra(getString(R.string.versao_minusculo), getString(R.string.versaoNTLH));
					startActivity(biblia);
				} else {
					Toast toastMensagem = mensagens.toastMensagem(getString(R.string.aviso_vers_ntlh),
							100, 550, 0, R.drawable.mapp_ic_biblia);
					toastMensagem.show();
				}
				break;
			default:
				break;
 				}
			}
		});
	}
}