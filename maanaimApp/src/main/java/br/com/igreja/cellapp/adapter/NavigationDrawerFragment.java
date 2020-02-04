package br.com.igreja.cellapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.dao.BibliaDAO;
import br.com.igreja.cellapp.util.Mensagens;

public class NavigationDrawerFragment extends Fragment {

	private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";
    private NavigationDrawerCallbacks mCallbacks;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private RelativeLayout mDrawerRelativeLayout;
    private View mFragmentContainerView;
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
	private ProgressDialog progresso;
	private Mensagens mensagens;
	private ListView list;
	private String versionName;
	
	public NavigationDrawerFragment() {
    }

   @Override
   public void onResume() {
	super.onResume();
}
   
   @Override
   public void onDestroy() {
	super.onDestroy();
}
   
   @Override
   public void onPause() {
	super.onPause();
}
   
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
    	mDrawerRelativeLayout = (RelativeLayout) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
    	list = (ListView) mDrawerRelativeLayout.findViewById(R.id.ListTeste);
    	
    	ImageView fotoMembro = (ImageView) mDrawerRelativeLayout.findViewById(R.id.fotoDoMembroLogado);
    	TextView nomeMembro = (TextView) mDrawerRelativeLayout.findViewById(R.id.textViewNomeFragment);
    	TextView textVersao = (TextView) mDrawerRelativeLayout.findViewById(R.id.textViewVers);
    	
    	try{
			PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
			versionName = pInfo.versionName;
		} catch (Exception e){
			e.printStackTrace();			
		}
    	textVersao.setText(getString(R.string.versao)+": v"+versionName);
    	
    	String fotoByte = "";
		
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(700);
			fadeIn.setFillAfter(true);
			
			fotoMembro.startAnimation(fadeIn);
			fotoMembro.setImageResource(R.drawable.ic_semfoto);
							
    	list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        
        ListaFragmentAdapter adapter = new ListaFragmentAdapter(getActivity(), 
        		new String[]{getString(R.string.title_section1),getString(R.string.mana),getString(R.string.biblia),
        					 getString(R.string.youtube_maanaim),getString(R.string.radio),getString(R.string.Facebook_share),getString(R.string.mural),
        					 getString(R.string.sugestoes),getString(R.string.sobre),getString(R.string.action_exit),
        					 });
        list.setAdapter(adapter);
        
        list.setItemChecked(mCurrentSelectedPosition, true);
        return mDrawerRelativeLayout;
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
     
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); 
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).commit();
                }

                getActivity().supportInvalidateOptionsMenu(); 
            }
        };

        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (list != null) {
        	list.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        
    	if (mDrawerLayout != null && isDrawerOpen()) {
           
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

      if (item.getItemId() == R.id.baixarBiblia) {
    
    	final AlertDialog.Builder alert3 = new AlertDialog.Builder(getActivity());
		alert3.setTitle(getString(R.string.install_bible)+" - "+getString(R.string.offline));
		alert3.setIcon(R.drawable.ic_splash_preto);
		alert3.setMessage(getString(R.string.info_instalacao_biblia));
		alert3.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				CharSequence versoes[] = new CharSequence[] {getString(R.string.versaoARC), getString(R.string.versaoACF),
						getString(R.string.versaoARA), getString(R.string.versaoNVI), getString(R.string.versaoNTLH)};
				final Mensagens m = new Mensagens(getActivity());
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setTitle(getString(R.string.escolha_versao));
				builder.setItems(versoes, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						switch (which) {
							case 0:
								instalarBilibia(getString(R.string.versaoARC), "almeida_revista_e_corrigida.txt");
								break;
							case 1:
								instalarBilibia(getString(R.string.versaoACF), "almeida_corrigida_fiel.txt");
								break;
							case 2:
								instalarBilibia(getString(R.string.versaoARA), "almeida_revista_e_atualizada.txt");
								break;
							case 3:
								instalarBilibia(getString(R.string.versaoNVI), "nova_versao_internacional.txt");
								break;
							case 4:
								instalarBilibia(getString(R.string.versaoNTLH), "nova_traducao_na_linguagem_de_hoje.txt");
								break;
							default:
								break;
						}

					}
				});
				builder.show();
								
			}
			
		});
		
		alert3.setNegativeButton(getString(R.string.nao), null);
		AlertDialog alert4 = alert3.create();
		alert4.show();
		
		return true;
    }
        
        return super.onOptionsItemSelected(item);
    }
    
    public void instalarBilibia(final String versao, final String txtFileName){
    	
    	progresso = new ProgressDialog(getActivity());
		progresso.setTitle(getString(R.string.instalacao_da)+" "+ versao+" - "+getString(R.string.offline));
		progresso.setMessage(getString(R.string.instalando));
		progresso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progresso.setProgress(0);
		progresso.setMax(31105);
		progresso.show();
		progresso.setCancelable(false);

				Thread td = new Thread(){
					
					@Override
					public void run() {
						try {
							final BibliaDAO daoBi = new BibliaDAO(getActivity(), versao);
							if(!daoBi.existeDadosNoBD()){
							 
							AssetManager assetManager = getResources().getAssets();
							final InputStream inputStream = assetManager.open(txtFileName);
							InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
							final BufferedReader reader = new BufferedReader(inputStreamReader);
							
								String linha = "";
								
									int i=0;
									try {
										while ((linha = reader.readLine()) != null){
											i++;
											daoBi.parseVersiculo(linha, i);
											getActivity().runOnUiThread(new Runnable() {
												
												@Override
												public void run() {
													progresso.incrementProgressBy(1);
													switch (progresso.getProgress()) {
													case 1:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.genesis));
														break;
														
													case 1534:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.exodo));
														break;
														
													case 2747:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.levitico));
														break;
														
													case 3606:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.numeros));
														break;
														
													case 4894:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.deuteronomio));
														break;
														
													case 5853:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.josue));
														break;
														
													case 6511:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.juizes));
														break;
														
													case 7130:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.rute));
														break;
														
													case 7215:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_samuel));
														break;
														
													case 8138:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_samuel));
														break;
														
													case 8846:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_reis));
														break;
														
													case 9723:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_reis));
														break;
														
													case 10370:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_cronicas));
														break;
														
													case 11199:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_cronicas));
														break;
														
													case 12021:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.esdras));
														break;
														
													case 12330:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.neemias));
														break;
														
													case 12707:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ester));
														break;
														
													case 12992:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.jo));
														break;
														
													case 13944:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.salmos));
														break;
														
													case 16429:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.proverbios));
														break;
														
													case 17464:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.eclesiastes));
														break;
														
													case 17583:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.cantares));
														break;
														
													case 17713:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.isaias));
														break;
														
													case 19027:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.jeremias));
														break;
														
													case 20422:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.lamentacoes));
														break;
														
													case 20624:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ezequiel));
														break;
														
													case 21742:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.daniel));
														break;
														
													case 22099:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.oseias));
														break;
														
													case 22296:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.joel));
														break;
														
													case 22375:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.amos));
														break;
														
													case 22515:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.obadias));
														break;
														
													case 22548:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.jonas));
														break;
														
													case 22584:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.miqueias));
														break;
														
													case 22689:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.naum));
														break;
														
													case 22736:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.habacuque));
														break;
														
													case 22792:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.sofonias));
														break;
														
													case 22846:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ageu));
														break;
														
													case 22883:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.zacarias));
														break;
														
													case 23094:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.malaquias));
														break;
														
													case 23149:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.mateus));
														break;
														
													case 24220:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.marcos));
														break;
														
													case 25020:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.lucas));
														break;
														
													case 26170:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.joao));
														break;
														
													case 27090:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.atos));
														break;
														
													case 28000:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.romanos));
														break;
														
													case 28420:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_corintios));
														break;
														
													case 28827:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_corintios));
														break;
														
													case 29062:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.galatas));
														break;
														
													case 29322:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.efesios));
														break;
														
													case 29688:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.filipenses));
														break;
														
													case 29941:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.colossenses));
														break;
														
													case 30000:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_tessalonicenses));
														break;
														
													case 30150:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_tessalonicenses));
														break;
														
													case 30199:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_timoteo));
														break;
														
													case 30299:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_timoteo));
														break;
														
													case 30400:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.tito));
														break;
														
													case 30450:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.filemon));
														break;
														
													case 30500:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.hebreus));
														break;
														
													case 30550:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.tiago));
														break;
														
													case 30600:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_pedro));
														break;
														
													case 30650:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_pedro));
														break;
														
													case 30700:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.i_joao));
														break;
														
													case 30720:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.ii_joao));
														break;
														
													case 30800:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.iii_joao));
														break;
														
													case 30840:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.judas));
														break;
														
													case 30900:
														progresso.setMessage(getString(R.string.versiculo_install)+" "+getString(R.string.apocalipse));
														break;
													

													default:
														break;
													}
													
												}
											});
											
											if(progresso.getProgress() == progresso.getMax()){
												progresso.dismiss();
											}
										}
										
									} catch (IOException e) {
										e.printStackTrace();
										daoBi.deleteBiblia();
									}
							
							progresso.dismiss();
							
							getActivity().runOnUiThread(new Runnable() {
								
								@Override
								public void run() {
									
									mensagens = new Mensagens(getActivity());
									mensagens.alertDialogMensagemOK(getString(R.string.install_bible),
											getString(R.string.biblia_instalada),
											R.drawable.mapp_ic_biblia).show();
								}
							});
							
							
							} else {
							
								progresso.dismiss();
								
								getActivity().runOnUiThread(new Runnable() {
									
									@Override
									public void run() {

										mensagens = new Mensagens(getActivity());
										mensagens.alertDialogMensagemOK(getString(R.string.install_bible), 
												getString(R.string.biblia_ja_instalada),
												R.drawable.mapp_ic_emotion_timido).show();
										
									}
								});
								
							
							}
							
							} catch (Exception e) {
								e.printStackTrace();
								
								progresso.dismiss();
								
								getActivity().runOnUiThread(new Runnable() {
									
									@Override
									public void run() {
										
										mensagens = new Mensagens(getActivity());
										Toast toast = mensagens.toastMensagem(getString(R.string.erro_instalar_biblia),
												350, 200, 0, R.drawable.mapp_ic_biblia);
										toast.show();
										
										BibliaDAO daoBi = new BibliaDAO(getActivity(), versao);
										daoBi.deleteBiblia();
										
									}
								});
								
							}
						super.run();
					}
					
				};
				td.start();
    	
    }

    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    public static interface NavigationDrawerCallbacks {
        void onNavigationDrawerItemSelected(int position);
    }
}
