package br.com.igreja.cellapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.igreja.cellapp.R;

public class ListaFragmentAdapter extends BaseAdapter{

	private Activity activity;
	private String[] menus;
	private int[] icones = {R.drawable.mapp_ic_igreja, R.drawable.mapp_ic_pao, R.drawable.mapp_ic_biblia, R.drawable.reunioes,
			R.drawable.celula, R.drawable.mapp_ic_familia, R.drawable.ponteiro, R.drawable.yt, R.drawable.mapp_ic_radio, 
			R.drawable.facebook, R.drawable.mapp_ic_alfinete, R.drawable.mapp_ic_ok, R.drawable.ic_launcher, R.drawable.sair };
	
	public ListaFragmentAdapter(Activity activity, String[] menus) {
		this.activity = activity;
		this.menus = menus;
	}
	
	@Override
	public int getCount() {
		return menus.length;
	}

	@Override
	public Object getItem(int position) {
		return menus[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = activity.getLayoutInflater();
		View listagem = inflater.inflate(R.layout.lista_fragment_layout, null);
		
		TextView nomeMenu = (TextView) listagem.findViewById(R.id.nomedomenu);
		ImageView icone = (ImageView) listagem.findViewById(R.id.iconeOpcoes);
		icone.setImageResource(icones[position]);
		
		nomeMenu.setTextColor(activity.getResources().getColor(R.color.gray));
		nomeMenu.setTextSize(17);
		nomeMenu.setText(menus[position]);
		
		return listagem;
	}

}
