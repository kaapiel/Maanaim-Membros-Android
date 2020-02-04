package br.com.igreja.cellapp.mainMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import java.util.Random;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.activities.SelecionarVersao;
import br.com.igreja.cellapp.adapter.NavigationDrawerFragment;
import br.com.igreja.cellapp.dao.BibliaDAO;
import br.com.igreja.cellapp.listagens.MuralEventos;
import br.com.igreja.cellapp.util.Mensagens;
import br.com.igreja.cellapp.util.Parametros;

public class Inicio extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	private int[] iconDrawables =
		   {R.drawable.mapp_ic_oracao,
			R.drawable.mapp_ic_anotacao,
			R.drawable.mapp_ic_biblia,
			R.drawable.mapp_ic_ceia,
			R.drawable.mapp_ic_relogio,
			R.drawable.mapp_ic_anotacao,
		};
	
	private NavigationDrawerFragment mNavigationDrawerFragment;
	private UiLifecycleHelper uiHelper;
	private CharSequence mTitle;

	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i("STATUS DO LOGIN...", "Logando...");
	    } else if (state.isClosed()) {
	        Log.i("STATUS DO LOGIN...", "Deslogando...");
	    }
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};

	@SuppressLint("SetJavaScriptEnabled") 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inicio);
		uiHelper = new UiLifecycleHelper(Inicio.this, callback);
	    uiHelper.onCreate(savedInstanceState);

		String[] frases =
				{  getString(R.string.culto_vitoria),
						getString(R.string.tadel),
						getString(R.string.ctl),
						getString(R.string.ceia),
						getString(R.string.programacao_igreja)
				};

	    final Mensagens mensagens = new Mensagens(this);
	    
	    TextView textConexao = (TextView) findViewById(R.id.textConexao);
		WebView wv = (WebView) findViewById(R.id.webView1);
		WebSettings webSettings = wv.getSettings();
		webSettings.setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient());
		
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
    		wv.loadUrl(Parametros.URL_SITE);
        } else if(cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){
	        wv.loadUrl(Parametros.URL_SITE);
        } else {
	        textConexao.setText(getString(R.string.erro_sem_conexao_site));
        }

		Random rdm = new Random();
		int numeroRandomico = rdm.nextInt(frases.length);
		mensagens.alertDialogMensagemOK("\t\t\t\t\t\t\t\tLembrete", frases[numeroRandomico], iconDrawables[numeroRandomico]).show();
		
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.container,	
				PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		Mensagens m = new Mensagens(Inicio.this);
		ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		switch (number) {
			case 1:
				mTitle = getString(R.string.title_section1);
				break;
			case 2:

				if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ||
						cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {

					Intent intent = new Intent(Inicio.this, Mana.class);
					startActivity(intent);
					overridePendingTransition(R.animator.foto_evento_anim_1, R.animator.foto_evento_anim_2);

				} else {
					m.toastMensagem(getString(R.string.voce_nao_internet),
							350, 200, 1, R.drawable.mapp_ic_emotion_poxa_vida).show();
				}

				break;
			case 3:
				mTitle = getString(R.string.biblia);

				if (!new BibliaDAO(Inicio.this, getString(R.string.versaoARC)).existeDadosNoBD() &
						!new BibliaDAO(Inicio.this, getString(R.string.versaoACF)).existeDadosNoBD() &
						!new BibliaDAO(Inicio.this, getString(R.string.versaoARA)).existeDadosNoBD() &
						!new BibliaDAO(Inicio.this, getString(R.string.versaoNVI)).existeDadosNoBD() &
						!new BibliaDAO(Inicio.this, getString(R.string.versaoNTLH)).existeDadosNoBD()) {

					AlertDialog alert = m.alertDialogMensagemOK(getString(R.string.biblia), getString(R.string.nao_possui_biblia),
							R.drawable.mapp_ic_biblia);
					alert.show();
				} else {
					Intent irParaBiblia = new Intent(Inicio.this, SelecionarVersao.class);
					startActivity(irParaBiblia);
				}
				break;
			case 4:
				if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ||
						cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
					Intent irParayoutube = new Intent(Inicio.this, YouTubeMaanaim.class);
					startActivity(irParayoutube);
				} else {
					m.toastMensagem(getString(R.string.voce_nao_internet),
							350, 200, 1, R.drawable.mapp_ic_emotion_poxa_vida).show();
				}

				break;
			case 5:
				if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() ||
						cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
					Intent irParaRadio = new Intent(Inicio.this, RadioMaanaim.class);
					startActivity(irParaRadio);
				} else {
					m.toastMensagem(getString(R.string.voce_nao_internet),
							350, 200, 1, R.drawable.mapp_ic_emotion_poxa_vida).show();
				}
				break;
			case 6:

				if (FacebookDialog.canPresentShareDialog(getApplicationContext(),
						FacebookDialog.ShareDialogFeature.SHARE_DIALOG)) {
					// Publish the post using the Share Dialog
					FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(this)
							.setLink(getString(R.string.link_app))
							.setApplicationName(getString(R.string.app_name))
							.setDescription(getString(R.string.baixe_android))
							.setPicture(Parametros.URL_LOGO_MAANAIM)
							.build();
					uiHelper.trackPendingDialogCall(shareDialog.present());
				}

				break;
			case 7:

				Intent i = new Intent(Inicio.this, MuralEventos.class);
				startActivity(i);
				break;

			case 8:

				Intent intent = new Intent(Inicio.this, Sugestoes.class);
				startActivity(intent);
				break;

			case 9:

				Intent iv = new Intent(Inicio.this, Sobre.class);
				startActivity(iv);
				break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			
			getMenuInflater().inflate(R.menu.inicio, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}

	public static class PlaceholderFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";

	public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

			public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_inicio,
					container, false);
			return rootView;
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Inicio) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }

	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    
	    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
	        @Override
	        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
	            Log.e("Activity", String.format("Error: %s", error.toString()));
	        }

	        @Override
	        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
	            Log.i("Activity", "Success!");
	        }
	    });
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}

}
