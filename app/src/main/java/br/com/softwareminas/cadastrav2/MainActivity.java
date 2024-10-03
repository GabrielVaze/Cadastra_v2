package br.com.softwareminas.cadastrav2;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editEmail, editId;
    Button btnAdd, btnView, btnEdit, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        editName = findViewById(R.id.editTextName);
        editEmail = findViewById(R.id.editTextEmail);
        editId = findViewById(R.id.editTextId);
        btnAdd = findViewById(R.id.buttonAdd);
        btnView = findViewById(R.id.buttonView);
        btnEdit = findViewById(R.id.buttonEdit);
        btnDelete = findViewById(R.id.buttonDelete);

        addUser();
        viewAllUsers();
        editUser();
        deleteUser();
    }

    public void addUser() {
        btnAdd.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            if (validateInput(name, email)) {
                boolean isInserted = myDb.insertData(name, email);
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Usuário cadastrado", Toast.LENGTH_LONG).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao cadastrar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void viewAllUsers() {
        btnView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ViewUsersActivity.class);
            startActivity(intent);
        });
    }

    public void editUser() {
        btnEdit.setOnClickListener(v -> {
            String id = editId.getText().toString().trim();
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            if (validateInput(name, email)) {
                boolean isUpdated = myDb.updateData(id, name, email);
                if (isUpdated) {
                    Toast.makeText(MainActivity.this, "Usuário atualizado", Toast.LENGTH_LONG).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao atualizar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void deleteUser() {
        btnDelete.setOnClickListener(v -> {
            String id = editId.getText().toString().trim();
            if (!id.isEmpty()) {
                boolean isDeleted = myDb.deleteData(id);
                if (isDeleted) {
                    Toast.makeText(MainActivity.this, "Usuário excluído", Toast.LENGTH_LONG).show();
                    clearFields();
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao excluir", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Por favor, insira um ID válido", Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean validateInput(String name, String email) {
        if (name.isEmpty()) {
            Toast.makeText(this, "Por favor, insira um nome", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um email válido", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void clearFields() {
        editName.setText("");
        editEmail.setText("");
        editId.setText("");
    }
}
