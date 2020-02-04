package br.com.igreja.cellapp.dao;

import android.content.Context;
import android.database.Cursor;

public class BancoDAO extends CellAppDAO{

	public BancoDAO(Context context) {
		super(context);
	}

	public boolean existeDadosNoBD() {
		
		String[] colunas = {"idcelula", "nome"};
		Cursor cursor = getWritableDatabase().query("Celula", colunas , null, null, null, null, null);
		
		if (cursor.moveToFirst()){
			return true;
		}
	return false;
		
	}
	
	public void deleteBD(){
		
		getWritableDatabase().delete("Membro", null, null);
		getWritableDatabase().delete("Celula", null, null);
		getWritableDatabase().delete("Usuario", null, null);
		getWritableDatabase().delete("Lider", null, null);
		getWritableDatabase().delete("Avisos", null, null);
	}
	
}
