package br.com.softwareminas.cadastrav2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";

    // Colunas da tabela
    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_EMAIL = "EMAIL";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criação da tabela
        String createTableSQL = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_EMAIL + " TEXT)";
        db.execSQL(createTableSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Atualiza a tabela se houver uma nova versão do banco de dados
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Método para inserir dados
    public boolean insertData(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1; // Retorna true se a inserção foi bem-sucedida
    }

    // Método para obter todos os dados
    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase(); // Use readable para evitar bloqueios
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // Método para atualizar dados
    public boolean updateData(String id, String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_EMAIL, email);

        // Atualiza a linha correspondente ao ID fornecido
        int result = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{id});
        return result > 0; // Retorna true se a atualização foi bem-sucedida
    }

    // Método para excluir dados
    public boolean deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Exclui a linha correspondente ao ID fornecido e retorna true se excluído
        return db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id}) > 0;
    }
}
