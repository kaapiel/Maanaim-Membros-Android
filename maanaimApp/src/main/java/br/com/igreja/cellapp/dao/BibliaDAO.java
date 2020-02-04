package br.com.igreja.cellapp.dao;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.igreja.cellapp.model.Biblia;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class BibliaDAO extends CellAppDAO{

	private String versao;
	private Context context;

	public BibliaDAO(Context context, String versao) {
		super(context);

		this.context = context;
		this.versao = versao;
	}

	public void gravaBiblia(Biblia b){
		
		ContentValues values = new ContentValues();
		
		values.put("idversiculo", b.getId());
		values.put("livro", b.getLivro());
		values.put("capitulo", b.getCapitulo());
		values.put("nversiculo", b.getnVersiculo());
		values.put("versiculo", b.getVersiculo());
		
		getWritableDatabase().insert(versao, null, values);
		
	}

	public boolean existeDadosNoBD() {
		
		String[] colunas = {"idversiculo", "livro", "capitulo", "nversiculo", "versiculo"};
		Cursor cursor = getWritableDatabase().query(versao, colunas , null, null, null, null, null);
		
		if (cursor.moveToFirst()){
			return true;
		}
	return false;
		
	}

	public void parseVersiculo(String linha, Integer id) {
	
		Biblia b = new Biblia();
		String string = "";
		String versiculo = "";

		String regex = "([a-zA-Z0-9\\p{L}\\.\\s\\;\\?\\,\\-\\!\\:\\_\\)\\(])+";
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(linha);
	    
	    //tira da string tudo o que não foi encontrado no regex;
	    while (m.find()) {
	   		string += m.group();
		}
	    
	    //separa a string por <space> (já sem os "[" e os "]");
	    String[] split = string.split(" ");
	    //pega a string, e onde tem [cap:x] e deixa apenas o número (x);
	    String split2 = split[1].replace("cap:", "");
	    //pega a string, e onde tem [nver:x] e deixa apenas o número (x);
	    String split3 = split[2].replace("nver:", "");
	   
	    //o tamanho da string tem o tamanho 3 (index do for) e guarda na variavel versiculo o que encontra
	    for (int i=3; i<split.length;i++){
	    	versiculo += split[i]+" ";
	    }

		b.setId(id);
	    b.setLivro(split[0]);
	    b.setCapitulo(Integer.valueOf(split2));
	    b.setnVersiculo(Integer.valueOf(split3));
	    b.setVersiculo(versiculo);
	    
	    this.gravaBiblia(b);
		
	}
	
	public void deleteBiblia(){
		getWritableDatabase().delete(versao, null, null);
		
	}
		
		public ArrayList<Biblia> getVersiculosARC(String livro, String capitulo){
			
			String[] colunas = {"idversiculo", "livro", "capitulo", "nversiculo", "versiculo"};
			Cursor cursor = getWritableDatabase().query(versao, colunas , "livro=? AND capitulo=?", new String[]{livro, capitulo}, null, null, "idversiculo");
			ArrayList<Biblia> arrayList = new ArrayList<Biblia>();
			
			while (cursor.moveToNext()){
				Biblia bv = new Biblia();
				
				bv.setLivro(cursor.getString(1));
				bv.setCapitulo(cursor.getInt(2));
				bv.setnVersiculo(cursor.getInt(3));
				bv.setVersiculo(cursor.getString(4));
				
				arrayList.add(bv);
			}
			return arrayList;			
		}
	
}
