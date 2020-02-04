package br.com.igreja.cellapp.mainMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.model.RadioAndroid;
import br.com.igreja.cellapp.util.Parametros;

public class RadioMaanaim extends Activity{
	
	private boolean playPause;
	private Button btn;
	private boolean intialStage = true;
	private MediaPlayer mp;
	private TextView textTocando;
	private TextView textOnOff;
	private TimerTask doAsynchronousTask;
	private Timer timer = new Timer();
	private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null;
    protected PowerManager.WakeLock mWakeLock;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();

		setVolumeControlStream(AudioManager.STREAM_MUSIC);
		setContentView(R.layout.radio_maanaim);
		initControls(); //método de volume do audio
		
		TextView textRadio = (TextView) findViewById(R.id.textRadio);
		textTocando = (TextView) findViewById(R.id.textTocando);
		textOnOff = (TextView) findViewById(R.id.textOnOffRadio);
		Typeface type = Typeface.createFromAsset(getAssets(),"fonts/digital.ttf");
		textRadio.setTypeface(type);
		btn = (Button) findViewById(R.id.botaoPlayStop);
		btn.setBackgroundResource(R.drawable.play);
		textOnOff.setTypeface(type);

		doAsynchronousTask = new TimerTask() {

		    @Override
		    public void run() {
		    	ConnectivityManager cm = (ConnectivityManager) RadioMaanaim.this.getSystemService(Context.CONNECTIVITY_SERVICE);
		        if(cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || 
		     		cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()){

		        	try{
		        		
		        		HttpClient client = new DefaultHttpClient();
			    		HttpGet get = new HttpGet(Parametros.URL_DADOS_AUDIO);
			    				
						final HttpResponse response1 = client.execute(get);
						HttpEntity resposta = response1.getEntity();
						final String respostaEmJSON = EntityUtils.toString(resposta);
								
						Gson gs = new Gson();
						JsonParser jp = new JsonParser();
						
						JsonObject dadosRadioJSON = jp.parse(respostaEmJSON).getAsJsonObject();
								
						JsonElement je = dadosRadioJSON;
						final RadioAndroid ra = gs.fromJson(je, RadioAndroid.class);
						
						RadioMaanaim.this.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								textTocando.setText(ra.getPlaying());
								if(ra.getStatus().equals("online")){
									textOnOff.setText("ONLINE");
									textOnOff.setTextColor(getResources().getColor(R.color.lime));
								}
							}
						});
		        		
		        	} catch (Exception e){
		        		e.printStackTrace();
		        	}
		        }
		    }
		};
		timer.schedule(doAsynchronousTask, 0, 10000);
		
		mp = new MediaPlayer();
		mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    btn.setOnClickListener(pausePlay);	
		
	}
	private OnClickListener pausePlay = new OnClickListener() {

	    @Override
	    public void onClick(View v) {
	    	
	    	if (!playPause) {
	    		btn.setBackgroundResource(R.drawable.stop);
	            if (intialStage)
	                new Player()
	                        .execute(Parametros.URL_AUDIO_LIVE);
	            else {
	                if (!mp.isPlaying())
	                    mp.start();
	            }
	            playPause = true;
	        } else {
	            btn.setBackgroundResource(R.drawable.play);
	            if (mp.isPlaying())
	                mp.pause();
	            playPause = false;
	        }
	    }
	};
	
	class Player extends AsyncTask<String, Void, Boolean> {
	    private ProgressDialog progress;

	    @Override
	    protected Boolean doInBackground(String... params) {
	       
	    	Boolean prepared;
	        try {
	        	
	        	HttpClient client = new DefaultHttpClient();
	    		HttpGet get = new HttpGet(Parametros.URL_DADOS_AUDIO);
	    				
				final HttpResponse response1 = client.execute(get);
				HttpEntity resposta = response1.getEntity();
				final String respostaEmJSON = EntityUtils.toString(resposta);
						
				Gson gs = new Gson();
				JsonParser jp = new JsonParser();
				
				JsonObject dadosRadioJSON = (JsonObject) jp.parse(respostaEmJSON).getAsJsonObject();
						
				JsonElement je = dadosRadioJSON;
				final RadioAndroid ra = gs.fromJson(je, RadioAndroid.class);
				
				RadioMaanaim.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						textTocando.setText(ra.getPlaying());
						if(ra.getStatus().equals("online")){
							textOnOff.setText("ONLINE");
							textOnOff.setTextColor(getResources().getColor(R.color.lime));
						}
						
					}
				});

	        	mp.setDataSource(params[0]);
	            mp.setOnCompletionListener(new OnCompletionListener() {

	                @Override
	                public void onCompletion(MediaPlayer mp) {
	                    // TODO Auto-generated method stub
	                    intialStage = true;
	                    playPause=false;
	                    btn.setBackgroundResource(R.drawable.play);
	                    mp.stop();
	                    mp.reset();
	                }
	            });
	            mp.prepare();
	            prepared = true;
	        } catch (IllegalArgumentException e) {
	            // TODO Auto-generated catch block
	            Log.d("IllegarArgument", e.getMessage());
	            prepared = false;
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            // TODO Auto-generated catch block
	            prepared = false;
	            e.printStackTrace();
	        } catch (IllegalStateException e) {
	            // TODO Auto-generated catch block
	            prepared = false;
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            prepared = false;
	            e.printStackTrace();
	        }
	        return prepared;
	    }

	    @Override
	    protected void onPostExecute(Boolean result) {
	        // TODO Auto-generated method stub
	        super.onPostExecute(result);
	        if (progress.isShowing()) {
	            progress.cancel();
	        }
	        Log.d("Preparado", "//" + result);
	        mp.start();

	        intialStage = false;
	    }

	    public Player() {
	        progress = new ProgressDialog(RadioMaanaim.this);
	    }

	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        this.progress.setMessage(getString(R.string.buffer));
	        this.progress.show();

	    }
	}

	@Override
	protected void onPause() {
		super.onPause();
	    if (mp != null) {
	        mp.reset();
	        mp.release();
	        mp = null;
	    	btn.setBackgroundResource(R.drawable.play);
	    	playPause = false;
	    	intialStage = true;
	    	mp = new MediaPlayer();
			mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    }
	
	}
	
	private void initControls()
    {
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.barVolume);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));   


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0){
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0){
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2){
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e){
            e.printStackTrace();
        }
    }
	
	@Override
	protected void onStop() {
		super.onStop();
	}
	
	@SuppressLint("Wakelock")
	@Override
	protected void onDestroy() {
		this.mWakeLock.release();
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
}
