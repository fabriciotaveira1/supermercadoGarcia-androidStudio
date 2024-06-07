package com.example.supermercadogarcia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);

        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnRemoveProduct = findViewById(R.id.btnRemoveProduct);
        Button btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        Button btnListProducts = findViewById(R.id.btnListProducts);

        btnAddProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AdicionarProdutoActivity.class);
            startActivity(intent);
        });

        btnRemoveProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, RemoverProdutoActivity.class);
            startActivity(intent);
        });

        btnUpdateProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, AtualizarProdutoActivity.class);
            startActivity(intent);
        });

        btnListProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ListarProdutosActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}