package br.com.softwareminas.cadastrav2;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import br.com.softwareminas.cadastrav2.DatabaseHelper;
import br.com.softwareminas.cadastrav2.R;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editEmail;
    Button btnAdd, btnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonView);

        addUser();
        viewAllUsers();
    }

    public void addUser() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDb.insertData(editName.getText().toString(), editEmail.getText().toString());
                if (isInserted)
                    Toast.makeText(MainActivity.this, "Usuário cadastrado", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Erro ao cadastrar", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void viewAllUsers() {
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDb.getAllData();
                if (res.getCount() == 0) {
                    // mostra mensagem
                    showMessage("Erro", "Nenhum dado encontrado");
                    return;
                }

                StringBuilder buffer = new StringBuilder();
                while (res.moveToNext()) {
                    buffer.append("ID: ").append(res.getString(0)).append("\n");
                    buffer.append("Nome: ").append(res.getString(1)).append("\n");
                    buffer.append("Email: ").append(res.getString(2)).append("\n\n");
                }

                // mostrar todos os dados
                showMessage("Usuários Cadastrados", buffer.toString());
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
