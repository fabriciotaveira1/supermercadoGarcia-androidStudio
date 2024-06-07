package com.example.supermercadogarcia;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdicionarProdutoActivity extends AppCompatActivity {

    private EditText editNome, editPreco, editQuantidade;
    private Button btnConfirmar, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Verificar se EdgeToEdge é necessário e implementado corretamente
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adicionar_produto);

        dbHelper = new DatabaseHelper(this);

        editNome = findViewById(R.id.editNome);
        editPreco = findViewById(R.id.editPreco);
        editQuantidade = findViewById(R.id.editQuantidade);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnBack = findViewById(R.id.btnBack);

        btnConfirmar.setOnClickListener(v -> adicionarProduto());

        btnBack.setOnClickListener(v -> finish());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void adicionarProduto() {
        String nome = editNome.getText().toString();
        String precoStr = editPreco.getText().toString();
        String quantidadeStr = editQuantidade.getText().toString();

        if (nome.isEmpty() || precoStr.isEmpty() || quantidadeStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double preco = Double.parseDouble(precoStr);
            int quantidade = Integer.parseInt(quantidadeStr);

            // Adicionando produto ao banco de dados
            dbHelper.addProduto(nome, preco, quantidade);

            Toast.makeText(this, "Produto adicionado com sucesso", Toast.LENGTH_SHORT).show();

            // Limpar os campos após adicionar
            editNome.setText("");
            editPreco.setText("");
            editQuantidade.setText("");
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Preço ou quantidade inválidos", Toast.LENGTH_SHORT).show();
        }
    }
}
