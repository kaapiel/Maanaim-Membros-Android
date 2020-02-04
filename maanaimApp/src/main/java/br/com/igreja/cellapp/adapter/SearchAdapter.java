package br.com.igreja.cellapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.igreja.cellapp.R;

public class SearchAdapter extends CursorAdapter {

    private List<String> nomes;
    private TextView text;

    public SearchAdapter(Context context, Cursor cursor, List<String> nomes) {
        super(context, cursor, false);
        this.nomes = nomes;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        text.setText(nomes.get(cursor.getPosition()));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.lista_membros_layout, parent, false);
        text = (TextView) view.findViewById(R.id.nomeDoMembro);
        return view;

    }
}
