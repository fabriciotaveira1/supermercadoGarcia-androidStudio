package com.example.supermercadogarcia;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RemoverProdutoActivity extends AppCompatActivity {

    private EditText editCodigo, editQuantidade;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_remover_produto);

        dbHelper = new DatabaseHelper(this);

        editCodigo = findViewById(R.id.editCodigo);
        editQuantidade = findViewById(R.id.editQuantidade);
        Button btnConfirmar = findViewById(R.id.btnConfirmar);
        Button btnBack = findViewById(R.id.btnBack);

        btnConfirmar.setOnClickListener(v -> removerProduto());

        btnBack.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void removerProduto() {
        String codigoStr = editCodigo.getText().toString();
        String quantidadeStr = editQuantidade.getText().toString();

        if (codigoStr.isEmpty() || quantidadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            int quantidade = Integer.parseInt(quantidadeStr);

            dbHelper.removerProduto(codigo, quantidade);

            Toast.makeText(this, "Produto removido com sucesso", Toast.LENGTH_SHORT).show();

            // Limpar os campos após remover
            editCodigo.setText("");
            editQuantidade.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show();
        }
    }
}
