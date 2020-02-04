package br.com.igreja.cellapp.mainMenu;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.dao.BibliaDAO;
import br.com.igreja.cellapp.model.Biblia;
import br.com.igreja.cellapp.util.ClipBoardBiblia;
import br.com.igreja.cellapp.util.Mensagens;

public class BibliaMaanaim extends Activity {

    private ScrollView scroll;
    private ProgressDialog progresso;
    private TextView textCap;
    private TextView textoBliblia;
    private String[] livros;

    String[] capitulos = {"Cap", "50", "40", "27", "36", "34", "24", "21", "4", "31", "24"    //capitulos por livro
            , "22", "25", "29", "36", "10", "13", "10", "42", "150", "31"
            , "12", "8", "66", "52", "5", "48", "12", "14", "3", "9"
            , "1", "4", "7", "3", "3", "3", "2", "14", "4", "28"
            , "16", "24", "21", "28", "16", "16", "13", "6", "6", "4"
            , "4", "5", "3", "6", "4", "3", "1", "13", "5", "5", "3", "5", "1", "1", "1", "22"};

    final Mensagens mensagens = new Mensagens(this);
    private String stringVersao;
    private Spinner spinnerLivros;
    private Spinner spinnerCaps;
    private ActionMode aMode;
    private int mTouchX;
    private int mTouchY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.biblia_maanaim);

        livros = new String[]{getString(R.string.livro), getString(R.string.genesis), getString(R.string.exodo),
                getString(R.string.levitico), getString(R.string.numeros), getString(R.string.deuteronomio), getString(R.string.josue),
                getString(R.string.juizes), getString(R.string.rute), getString(R.string.i_samuel), getString(R.string.ii_samuel),
                getString(R.string.i_reis), getString(R.string.ii_reis), getString(R.string.i_cronicas), getString(R.string.ii_cronicas),
                getString(R.string.esdras), getString(R.string.neemias), getString(R.string.ester), getString(R.string.jo),
                getString(R.string.salmos), getString(R.string.proverbios), getString(R.string.eclesiastes), getString(R.string.cantares),
                getString(R.string.isaias), getString(R.string.jeremias), getString(R.string.lamentacoes), getString(R.string.ezequiel),
                getString(R.string.daniel), getString(R.string.oseias), getString(R.string.joel), getString(R.string.amos),
                getString(R.string.obadias), getString(R.string.jonas), getString(R.string.miqueias), getString(R.string.naum),
                getString(R.string.habacuque), getString(R.string.sofonias), getString(R.string.ageu), getString(R.string.zacarias),
                getString(R.string.malaquias), getString(R.string.mateus), getString(R.string.marcos), getString(R.string.lucas),
                getString(R.string.joao), getString(R.string.atos), getString(R.string.romanos), getString(R.string.i_corintios),
                getString(R.string.ii_corintios), getString(R.string.galatas), getString(R.string.efesios), getString(R.string.filipenses),
                getString(R.string.colossenses), getString(R.string.i_tessalonicenses), getString(R.string.ii_tessalonicenses),
                getString(R.string.i_timoteo), getString(R.string.ii_timoteo), getString(R.string.tito), getString(R.string.filemon),
                getString(R.string.hebreus), getString(R.string.tiago), getString(R.string.i_pedro), getString(R.string.ii_pedro),
                getString(R.string.i_joao), getString(R.string.ii_joao), getString(R.string.iii_joao), getString(R.string.judas),
                getString(R.string.apocalipse)};

        ActionBar bar = getActionBar();
        bar.setDisplayShowCustomEnabled(true);
        bar.setCustomView(R.layout.custom_action_bar);

        Intent intent = getIntent();
        stringVersao = (String) intent.getSerializableExtra(getString(R.string.versao_minusculo));

        spinnerLivros = (Spinner) findViewById(R.id.spinner_livros);
        spinnerCaps = (Spinner) findViewById(R.id.spinner_caps);
        final ImageButton botaoBuscar = (ImageButton) findViewById(R.id.botao_buscar);
        ImageButton botaoVoltarCapitulo = (ImageButton) findViewById(R.id.BotaoVoltarCapitulo);
        ImageButton botaoAvancarCapitulo = (ImageButton) findViewById(R.id.BotaoAvancarCapitulo);

        textoBliblia = (TextView) findViewById(R.id.textView);
        scroll = (ScrollView) findViewById(R.id.scrollBiblia);

        textoBliblia.setFocusable(true);
        textoBliblia.setTextIsSelectable(true);

        textCap = (TextView) findViewById(R.id.textCap);

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, livros);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivros.setAdapter(arrayAdapter);

        spinnerLivros.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (position == 0) {

                } else {

                    String capitulo = capitulos[position];
                    int parseInt = Integer.parseInt(capitulo);
                    ArrayList<Integer> arrayList = new ArrayList<Integer>();


                    for (int i = 0; i < parseInt; i++) {
                        arrayList.add(i + 1);
                    }

                    ArrayAdapter<Integer> arrayAdapter2 = new ArrayAdapter<Integer>(BibliaMaanaim.this,
                            android.R.layout.simple_spinner_item, arrayList);
                    arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCaps.setAdapter(arrayAdapter2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        botaoBuscar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    escolherTexto(spinnerLivros.getSelectedItem().toString(), spinnerCaps.getSelectedItem().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    mensagens.toastMensagem(getString(R.string.erro_tente_novamente), 350, 200, 1, R.drawable.mapp_ic_biblia).show();
                }

            }
        });

        textoBliblia.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return validationLongDoubleClick();
            }
        });

        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            public boolean onDoubleTap(MotionEvent e) {
                return validationLongDoubleClick();
            }
        });

        textoBliblia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });


        botaoAvancarCapitulo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {
                    if (spinnerCaps.getSelectedItem().toString() == String.valueOf(spinnerCaps.getCount())) {
                        String livroSpinner = spinnerLivros.getSelectedItem().toString();
                        if (livroSpinner == getString(R.string.apocalipse)) {
                            return;
                        }

                        for (int i = 0; i < livros.length; i++) {
                            if (livros[i].toString() == livroSpinner) {
                                spinnerLivros.setSelection(i + 1);
                            }
                        }

                        try {
                            escolherTexto(spinnerLivros.getSelectedItem().toString(), "1");
                        } catch (Exception e) {
                            mensagens.toastMensagem(getString(R.string.erro_tente_novamente), 350, 200, 1, R.drawable.mapp_ic_biblia).show();
                        }


                    } else {

                        String capituloEmString = spinnerCaps.getSelectedItem().toString();
                        int capituloEmInteger = Integer.parseInt(capituloEmString);
                        String capituloParaAvancar = String.valueOf(capituloEmInteger + 1);
                        spinnerCaps.setSelection(capituloEmInteger); //index do spinner

                        escolherTexto(spinnerLivros.getSelectedItem().toString(), capituloParaAvancar);

                    }

                } catch (Exception e) {
                    mensagens.toastMensagem(getString(R.string.erro_tente_novamente), 350, 200, 1,
                            R.drawable.mapp_ic_biblia).show();
                }

            }

        });

        botaoVoltarCapitulo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                try {

                    if (spinnerCaps.getSelectedItem().toString() == String.valueOf(1)) {

                        String livroSpinner = spinnerLivros.getSelectedItem().toString();

                        int capituloAnterior = 0;
                        for (int i = 0; i < livros.length; i++) {
                            if (livros[i].toString() == livroSpinner) {
                                spinnerLivros.setSelection(i - 1);
                                capituloAnterior = Integer.valueOf(capitulos[i - 1]);
                            }
                        }

                        spinnerCaps.setSelection(spinnerCaps.getLastVisiblePosition());
                        escolherTexto(spinnerLivros.getSelectedItem().toString(), String.valueOf(capituloAnterior));

                    } else {

                        String capituloEmString = spinnerCaps.getSelectedItem().toString();
                        int capituloEmInteger = Integer.parseInt(capituloEmString);
                        String capituloParaRetroceder = String.valueOf(capituloEmInteger - 1);
                        spinnerCaps.setSelection(capituloEmInteger - 2); //index do spinner

                        escolherTexto(spinnerLivros.getSelectedItem().toString(), capituloParaRetroceder);
                    }

                } catch (Exception e) {
                    mensagens.toastMensagem(getString(R.string.erro_tente_novamente), 350, 200, 1, R.drawable.mapp_ic_biblia).show();
                }

            }
        });

        super.onCreate(savedInstanceState);
    }

    public void escolherTexto(final String spinnerLivros, final String spinnerCaps) {

        textoBliblia.setText(""); //Limpa o texto que ja estava escrito.
        final BibliaDAO daoBi = new BibliaDAO(BibliaMaanaim.this, stringVersao);

        progresso = ProgressDialog.show(BibliaMaanaim.this, getString(R.string.aguarde),
                getString(R.string.carregando_cap), false, true);
        progresso.setIcon(R.drawable.ic_splash_preto);
        progresso.show();

        Thread thread1 = new Thread() {
            public void run() {
                try {
                    String string = spinnerLivros;
                    String livro = "";

                    String comAcentos = getString(R.string.letras_com_acento);
                    String semAcentos = getString(R.string.letras_sem_acento);

                    //tira os acentos da string extraida do spinner
                    for (int i = 0; i < comAcentos.length(); i++) {
                        string = string.replace(comAcentos.charAt(i), semAcentos.charAt(i));
                    }

                    livro = string.toUpperCase(Locale.getDefault());
                    try {
                        final Typeface type = Typeface.createFromAsset(getAssets(), "fonts/ftl.ttf");
                        final ArrayList<Biblia> biblia = daoBi.getVersiculosARC(livro, spinnerCaps);
                        BibliaMaanaim.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textCap.setTypeface(type);
                                textCap.setText(getString(R.string.cap) + " " + spinnerCaps);
                            }
                        });

                        for (final Biblia versiculo : biblia) {
                            BibliaMaanaim.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textoBliblia.setTypeface(type);
                                    textoBliblia.append(versiculo.getnVersiculo() + ". " + versiculo.getVersiculo() + "\n\n");
                                    scroll.fullScroll(View.FOCUS_UP);
                                }
                            });
                        }

                        textoBliblia.getText().toString().replaceAll("\n\n", "\n");

                    } catch (Exception e) {
                        e.printStackTrace();
                        BibliaMaanaim.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                Toast toastMensagem = mensagens.toastMensagem(getString(R.string.escolha_livro_cap), 350, 200, 0, R.drawable.mapp_ic_biblia);
                                toastMensagem.show();

                            }
                        });
                    }

                    progresso.dismiss();
                    daoBi.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    progresso.dismiss();
                    daoBi.close();

                }

                super.run();
            }
        };
        thread1.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    class ActionBarCallBack implements ActionMode.Callback {

        private String capitulo;

        public ActionBarCallBack(String cap) {
            this.capitulo = cap;
        }

        public ActionBarCallBack() {

        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            ClipBoardBiblia cbb = new ClipBoardBiblia();
            switch (item.getItemId()) {
                case R.id.item_copiar:
                    Mensagens m = new Mensagens(BibliaMaanaim.this);
                    cbb.copyToClipboard(BibliaMaanaim.this, "1. Aquele que habita no esconderijo do Altissimo..." + "\nBíblia Maanaim", capitulo);
                    m.toastMensagem(cbb.readFromClipboard(BibliaMaanaim.this), 0, 50, 0, R.drawable.mapp_ic_anotacao).show();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            //mode.getMenuInflater().inflate(R.menu.contextual_menu, menu);
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.contextual_menu, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            aMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // TODO Auto-generated method stub
            mode.setTitle("Versículo selecionado");
            return false;
        }
    }

    public boolean validationLongDoubleClick() {

        if (aMode != null) {
            return false;
        }
        aMode = BibliaMaanaim.this.startActionMode(new ActionBarCallBack(spinnerLivros.getSelectedItem().toString()
                + " " + spinnerCaps.getSelectedItem().toString()));
        return true;
    }
}
