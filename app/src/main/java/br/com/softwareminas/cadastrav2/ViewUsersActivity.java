package br.com.softwareminas.cadastrav2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewUsersActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView textViewUsers;
    Button btnClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        myDb = new DatabaseHelper(this);
        textViewUsers = findViewById(R.id.textViewUsers);
        btnClose = findViewById(R.id.btnClose); // Referência ao botão "Fechar"

        displayUsers();

        // Adiciona o listener para fechar a atividade
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Fecha a atividade
            }
        });
    }

    private void displayUsers() {
        Cursor res = myDb.getAllData();
        StringBuilder buffer = new StringBuilder();
        while (res.moveToNext()) {
            buffer.append("ID: ").append(res.getString(0)).append("\n");
            buffer.append("Nome: ").append(res.getString(1)).append("\n");
            buffer.append("Email: ").append(res.getString(2)).append("\n\n");
        }
        textViewUsers.setText(buffer.toString());
    }
}
