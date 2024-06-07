package com.example.supermercadogarcia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "produtos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUTOS = "produtos";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_PRECO = "preco";
    public static final String COLUMN_QUANTIDADE = "quantidade";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUTOS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NOME + " TEXT, " +
                    COLUMN_PRECO + " REAL, " +
                    COLUMN_QUANTIDADE + " INTEGER" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUTOS);
        onCreate(db);
    }

    public void addProduto(String nome, double preco, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_PRECO, preco);
        values.put(COLUMN_QUANTIDADE, quantidade);

        db.insert(TABLE_PRODUTOS, null, values);
        db.close();
    }

    public void removerProduto(int codigo, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_QUANTIDADE + " FROM " + TABLE_PRODUTOS +
                " WHERE " + COLUMN_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(codigo)});

        if (cursor.moveToFirst()) {
            int quantidadeColumnIndex = cursor.getColumnIndex(COLUMN_QUANTIDADE);
            if (quantidadeColumnIndex != -1) {
                int quantidadeAtual = cursor.getInt(quantidadeColumnIndex);
                int novaQuantidade = quantidadeAtual - quantidade;

                if (novaQuantidade < 0) {
                    novaQuantidade = 0;
                }

                ContentValues values = new ContentValues();
                values.put(COLUMN_QUANTIDADE, novaQuantidade);

                db.update(TABLE_PRODUTOS, values, COLUMN_ID + " = ?",
                        new String[]{String.valueOf(codigo)});
            }
        }

        cursor.close();
        db.close();
    }

    public void atualizarProduto(int codigo, String nome, double preco, int quantidade) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOME, nome);
        values.put(COLUMN_PRECO, preco);
        values.put(COLUMN_QUANTIDADE, quantidade);

        db.update(TABLE_PRODUTOS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(codigo)});
        db.close();
    }

    public List<Produto> listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Colunas que queremos recuperar
        String[] projection = {
                COLUMN_ID,
                COLUMN_NOME,
                COLUMN_PRECO,
                COLUMN_QUANTIDADE
        };

        // Ordenar por ID, se necessário
        String sortOrder = COLUMN_ID + " ASC";

        // Executar a consulta
        Cursor cursor = db.query(
                TABLE_PRODUTOS,  // Tabela para consultar
                projection,     // Colunas para retornar
                null,           // Cláusula WHERE (não precisamos de uma)
                null,           // Valores para a cláusula WHERE (não precisamos de nenhum)
                null,           // Agrupar as linhas (não precisamos de agrupamento)
                null,           // Filtrar por grupos de linhas (não precisamos de filtro)
                sortOrder       // Ordem de classificação
        );

        // Iterar sobre o cursor e adicionar produtos à lista
        while (cursor.moveToNext()) {
            int codigo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOME));
            double preco = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECO));
            int quantidade = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTIDADE));
            Produto produto = new Produto(codigo, nome, preco, quantidade);
            produtos.add(produto);
        }

        // Fechar o cursor e o banco de dados
        cursor.close();
        db.close();

        return produtos;
    }
}
