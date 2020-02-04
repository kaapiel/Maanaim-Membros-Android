package br.com.igreja.cellapp.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.igreja.cellapp.model.Avisos;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class AvisosDAO extends CellAppDAO{

	public AvisosDAO(Context context) {
		super(context);
	}

	public void gravarAvisos(List<Avisos> avisos) {
		
		for (Avisos a : avisos) {
		ContentValues values = new ContentValues();
		values.put("idaviso", a.getId());
		values.put("titulo", a.getTitulo());
		values.put("imagemEvento", a.getImagemEvento());
		values.put("dataEvento", String.valueOf(a.getDataEvento()));
		values.put("dataPublicacao", String.valueOf(a.getDataPublicacao()));
			
		getWritableDatabase().insert("Avisos", null, values);
		}
		
	}

	public ArrayList<Avisos> getListaDeAvisos(String orderBy) {
		String[] colunas = {"idaviso", "titulo", "imagemEvento", "dataEvento", "dataPublicacao"};
		Cursor cursor = getWritableDatabase().query("Avisos", colunas , null, null, null, null, orderBy, "6");
		ArrayList<Avisos> avisos = new ArrayList<Avisos>();

		while (cursor.moveToNext()){
			Avisos aviso = new Avisos();
		
			aviso.setId(cursor.getInt(0));
			aviso.setTitulo(cursor.getString(1));
			aviso.setImagemEvento(cursor.getString(2));
			aviso.setDataEvento(cursor.getString(3));
			aviso.setDataPublicacao(cursor.getString(4));
		
		avisos.add(aviso);
		}
		return avisos;
	}
	
	public void deleteAvisos(){
		
		getWritableDatabase().delete("Avisos", null, null);
	}
	
}
