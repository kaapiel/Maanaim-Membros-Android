package br.com.igreja.cellapp.util;


import com.parse.Parse;
import com.parse.PushService;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.mainMenu.Inicio;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, getResources().getString(R.string.push_app_id), getResources().getString(R.string.push_client_key));
        PushService.setDefaultPushCallback(this, Inicio.class);
        //ParseInstallation.getCurrentInstallation().saveInBackground();
        //Log.d("INSTALATION ID: ", ParseInstallation.getCurrentInstallation().getObjectId());
    }
}