package br.com.igreja.cellapp.mainMenu;


import br.com.igreja.cellapp.R;
import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;

public class Sobre extends Activity{
	
	private String versionName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.versao);
	
		TextView txtVersao = (TextView) findViewById(R.id.txtVersao);
		
		try{
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			versionName = pInfo.versionName;
		} catch (Exception e){
			e.printStackTrace();			
		}
		
		txtVersao.setText(getString(R.string.versao)+": v"+versionName);
	}
}
