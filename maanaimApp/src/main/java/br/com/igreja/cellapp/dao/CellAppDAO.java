package br.com.igreja.cellapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CellAppDAO extends SQLiteOpenHelper{

	private static int VERSION = 137;
	private static String DATABASE = "CellApp";

	public CellAppDAO(Context context) {
		super(context, DATABASE, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		String ddl1 = "CREATE TABLE Membro (idmembro INTEGER NOT NULL, " +
				"idcelula INTEGER NOT NULL, nascimento TEXT, foto TEXT, nome TEXT NOT NULL," +
				"fone1 TEXT, fone2 TEXT, cep TEXT, numero TEXT, email TEXT, logradouro TEXT, " +
				"bairro TEXT, cidade TEXT, apelido TEXT, rg TEXT, cpf TEXT, dataBatismo TEXT," +
				"dataCadastro TEXT, sexo TEXT, uf TEXT, complemento TEXT, ativo TEXT);";
		db.execSQL(ddl1);

		String ddl2 = "CREATE TABLE Celula (idcelula INTEGER PRIMARY KEY UNIQUE NOT NULL, " +
				"nome TEXT, dataCriacao TEXT, subRegiao TEXT, regiao TEXT, idCelulaOrigem INTEGER, descricao TEXT, ativo TEXT);";
		db.execSQL(ddl2);

		String ddl3 = "CREATE TABLE Usuario (idusuario INTEGER PRIMARY KEY NOT NULL, " +
				"idmembro INTEGER, nome TEXT, usuario TEXT NOT NULL, senha TEXT NOT NULL);";
		db.execSQL(ddl3);
		
		String ddl4 = "CREATE TABLE Lider (idcelula INTEGER, idmembro INTEGER);";
		db.execSQL(ddl4);
		
		String ddl5 = "CREATE TABLE BibliaARC (idversiculo INTEGER, livro TEXT, capitulo TEXT, " +
				"nversiculo TEXT, versiculo TEXT);";
		db.execSQL(ddl5);
		
		String ddl6 = "CREATE TABLE BibliaARA (idversiculo INTEGER, livro TEXT, capitulo TEXT, " +
				"nversiculo TEXT, versiculo TEXT);";
		db.execSQL(ddl6);
		
		String ddl7 = "CREATE TABLE BibliaACF (idversiculo INTEGER, livro TEXT, capitulo TEXT, " +
				"nversiculo TEXT, versiculo TEXT);";
		db.execSQL(ddl7);
		
		String ddl8 = "CREATE TABLE Reuniao (cep TEXT, complemento TEXT, dataReuniao TEXT, " +
				"horaReuniao TEXT, idCelula INTEGER, idEstado INTEGER, idReuniao INTEGER, numeroLocal TEXT);";
		db.execSQL(ddl8);
		
		String ddl9 = "CREATE TABLE Avisos (idaviso INTEGER NOT NULL, " +
				"titulo TEXT, imagemEvento TEXT, dataEvento TEXT, dataPublicacao TEXT);";
		db.execSQL(ddl9);
		
		String ddl10 = "CREATE TABLE BibliaNVI (idversiculo INTEGER, livro TEXT, capitulo TEXT, " +
				"nversiculo TEXT, versiculo TEXT);";
		db.execSQL(ddl10);
		
		String ddl11 = "CREATE TABLE BibliaNTLH (idversiculo INTEGER, livro TEXT, capitulo TEXT, " +
				"nversiculo TEXT, versiculo TEXT);";
		db.execSQL(ddl11);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		String ddl1 = "DROP TABLE IF EXISTS Celula";
		db.execSQL(ddl1);
		
		String ddl2 = "DROP TABLE IF EXISTS Membro";
		db.execSQL(ddl2);
		
		String ddl3 = "DROP TABLE IF EXISTS Usuario";
		db.execSQL(ddl3);
		
		String ddl4 = "DROP TABLE IF EXISTS Lider";
		db.execSQL(ddl4);
		
		String ddl5 = "DROP TABLE IF EXISTS BibliaARC";
		db.execSQL(ddl5);
		
		String ddl6 = "DROP TABLE IF EXISTS BibliaARA";
		db.execSQL(ddl6);
		
		String ddl7 = "DROP TABLE IF EXISTS BibliaACF";
		db.execSQL(ddl7);
		
		String ddl8 = "DROP TABLE IF EXISTS Reuniao";
		db.execSQL(ddl8);
		
		String ddl9 = "DROP TABLE IF EXISTS Avisos";
		db.execSQL(ddl9);
		
		String ddl10 = "DROP TABLE IF EXISTS BibliaNVI";
		db.execSQL(ddl10);
		
		String ddl11 = "DROP TABLE IF EXISTS BibliaNTLH";
		db.execSQL(ddl11);
		
		this.onCreate(db);
	}
	
}
