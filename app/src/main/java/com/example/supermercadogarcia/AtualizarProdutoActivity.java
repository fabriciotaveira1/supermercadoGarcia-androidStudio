package com.example.supermercadogarcia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AtualizarProdutoActivity extends AppCompatActivity {

    private EditText editCodigo, editNome, editPreco, editQuantidade;
    private Button btnConfirmar, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_atualizar_produto);

        dbHelper = new DatabaseHelper(this);

        editCodigo = findViewById(R.id.editCodigo);
        editNome = findViewById(R.id.editNome);
        editPreco = findViewById(R.id.editPreco);
        editQuantidade = findViewById(R.id.editQuantidade);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnBack = findViewById(R.id.btnBack);

        btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarProduto();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void atualizarProduto() {
        String codigoStr = editCodigo.getText().toString();
        String nome = editNome.getText().toString();
        String precoStr = editPreco.getText().toString();
        String quantidadeStr = editQuantidade.getText().toString();

        if (codigoStr.isEmpty() || nome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int codigo = Integer.parseInt(codigoStr);
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);

            dbHelper.atualizarProduto(codigo, nome, preco, quantidade);

            Toast.makeText(this, "Produto atualizado com sucesso", Toast.LENGTH_SHORT).show();

            // Limpar os campos após atualizar
            editCodigo.setText("");
            editNome.setText("");
            editPreco.setText("");
            editQuantidade.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, insira valores válidos", Toast.LENGTH_SHORT).show();
        }
    }
}
