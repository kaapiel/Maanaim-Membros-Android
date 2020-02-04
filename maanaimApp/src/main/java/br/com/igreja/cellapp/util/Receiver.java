package br.com.igreja.cellapp.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParsePushBroadcastReceiver;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.listagens.MuralEventos;

/**
 * Created by Gabriel Aguido on 21/07/2015.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {

        Bundle mBundle = intent.getExtras();
        String mData = mBundle.getString(context.getString(R.string.pacote_parse));

        Intent i = new Intent(context, MuralEventos.class);
        i.putExtra(context.getString(R.string.put_extra_push), mData);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
