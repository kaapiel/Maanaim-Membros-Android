package br.com.igreja.cellapp.adapter;

import java.util.Date;
import java.util.List;

import br.com.igreja.cellapp.R;
import br.com.igreja.cellapp.model.Avisos;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListaEventosAdapter extends BaseAdapter{

	private List<Avisos> avisos;
	private Activity activity;
	
	public ListaEventosAdapter(List<Avisos> avisos, Activity activity) {
		this.avisos = avisos;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		return avisos.size();
	}

	@Override
	public Object getItem(int position) {
		return avisos.get(position);
	}

	@Override
	public long getItemId(int position) {
		Avisos a = avisos.get(position);
		return a.getId();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		final Avisos a = avisos.get(position);
		LayoutInflater inflater = activity.getLayoutInflater();
		View listagem = inflater.inflate(R.layout.lista_eventos_layout, null);
		
		TextView txtOcorridoOuNao = (TextView) listagem.findViewById(R.id.ocorridoOuNao);
		
		//2015-03-11T00:00:00-03:00
		Date dataHoje = new Date();
		String dataEvento = a.getDataEvento();
		
		String[] split = dataEvento.split("-");
		
		int diaEvento = Integer.valueOf(split[2].substring(0, 2));
		int mesEvento = Integer.valueOf(split[1]);
		int anoEvento = Integer.valueOf(split[0]);
		
		if(dataHoje.getDate() > diaEvento){
			txtOcorridoOuNao.setText("REALIZADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.vermelho));
		} else if(dataHoje.getDate() < diaEvento){
			txtOcorridoOuNao.setText("AGENDADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.amarelo));
		}
		
		if((dataHoje.getMonth()+1) > mesEvento){
			txtOcorridoOuNao.setText("REALIZADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.vermelho));
		} else if((dataHoje.getMonth()+1) < mesEvento){
			txtOcorridoOuNao.setText("AGENDADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.amarelo));
		} 
		
		if((dataHoje.getYear()+1900) > anoEvento){
			txtOcorridoOuNao.setText("REALIZADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.vermelho));
		} else if((dataHoje.getYear()+1900) < anoEvento){
			txtOcorridoOuNao.setText("AGENDADO");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.amarelo));
		}
		
		if((dataHoje.getYear()+1900) == anoEvento && (dataHoje.getMonth()+1) == mesEvento &&
				dataHoje.getDate() == diaEvento){
			txtOcorridoOuNao.setText("HOJE");
			txtOcorridoOuNao.setTextColor(activity.getResources().getColor(R.color.verde));
		}
			
		TextView txtDataEvento = (TextView) listagem.findViewById(R.id.dataEvento);
		txtDataEvento.setText(diaEvento+"/"+mesEvento+"/"+anoEvento);
		final ImageView imgEvento = (ImageView) listagem.findViewById(R.id.imagemEvento);
		
		String fotoByte = a.getImagemEvento();
		
		if (fotoByte == null){
					
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(700);
			fadeIn.setFillAfter(true);
			
			imgEvento.startAnimation(fadeIn);
			imgEvento.setImageResource(R.drawable.mapp_ic_clear);
							
		} else {
							
			byte[] decode = Base64.decode(fotoByte, Base64.DEFAULT);
			Bitmap bm = BitmapFactory.decodeByteArray(decode, 0, decode.length);
			Options op = new BitmapFactory.Options();
			op.inScaled = false;
			Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bm, 500, 500, true);
							
			AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
			fadeIn.setDuration(700);
			fadeIn.setFillAfter(true);
			
			imgEvento.startAnimation(fadeIn);
			imgEvento.setImageBitmap(createScaledBitmap);
							
		}
		
		TextView tituloDoEvento = (TextView) listagem.findViewById(R.id.tituloEvento);
		tituloDoEvento.setText(a.getTitulo());
		
		return listagem;
	}

}
