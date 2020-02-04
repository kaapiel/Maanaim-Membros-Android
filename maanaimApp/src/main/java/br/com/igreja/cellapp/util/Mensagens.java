package br.com.igreja.cellapp.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.igreja.cellapp.R;

public class Mensagens{
	
	private Activity a;
	
	public Mensagens(Activity a) {
		this.a = a;
	}

	public Toast toastMensagem(String msg, int x, int y, int duration, int imageDrawable){
		
		LayoutInflater inflater = a.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
		                               (ViewGroup) a.findViewById(R.id.toast_layout_root));
		ImageView imageToast = (ImageView) layout.findViewById(R.id.iconeToast);
		imageToast.setImageResource(imageDrawable);

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(a);
		toast.setGravity(Gravity.TOP, x, y);
		toast.setDuration(duration); //0 = short; 1 = long
		toast.setView(layout);
		
		return toast;
	}

	public Toast toastMensagem(String msg, int x, int y, int duration){

		LayoutInflater inflater = a.getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_layout,
				(ViewGroup) a.findViewById(R.id.toast_layout_root));

		TextView text = (TextView) layout.findViewById(R.id.text);
		text.setText(msg);

		Toast toast = new Toast(a);
		toast.setGravity(Gravity.TOP, x, y);
		toast.setDuration(duration); //0 = short; 1 = long
		toast.setView(layout);

		return toast;
	}

	public AlertDialog alertDialogMensagemOK(String title, String msg, int iconDrawable){
		
		AlertDialog.Builder alert = new AlertDialog.Builder(a);
		alert.setTitle(title);
		alert.setIcon(iconDrawable);
		
		alert.setMessage(msg);
		
		alert.setNeutralButton(a.getString(R.string.ok), null);
		AlertDialog alert2 = alert.create();
		alert2.show();
		
		return alert2;
	}
	
public <T> AlertDialog alertDialogMensagemSIMeNAO(String title, String msg, final Class<T> c){
		
	AlertDialog.Builder alert = new AlertDialog.Builder(a);
	alert.setTitle(title);
	alert.setIcon(R.drawable.ic_splash_preto);
	alert.setMessage(msg);
	alert.setPositiveButton(a.getString(R.string.sim), new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			ConnectivityManager cm = (ConnectivityManager) a.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || 
					cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
				Intent intent = new Intent(a, c);
				a.startActivity(intent);
	        } else {
		    toastMensagem(a.getString(R.string.voce_nao_internet), 350, 200, 0, R.drawable.mapp_ic_olhos).show();
	        }
		}
	});
	alert.setNegativeButton(a.getString(R.string.nao), null);
	AlertDialog alert2 = alert.create();
	
	return alert2;
	}
	
}
