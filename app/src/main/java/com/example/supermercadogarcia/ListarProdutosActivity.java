package com.example.supermercadogarcia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class ListarProdutosActivity extends AppCompatActivity {

    private LinearLayout tabelaProdutos;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_produtos);

        dbHelper = new DatabaseHelper(this);
        tabelaProdutos = findViewById(R.id.tabelaProdutos);

        listarProdutos();
    }

    private void listarProdutos() {
        // Limpar a tabela de produtos antes de preencher novamente
        tabelaProdutos.removeAllViews();

        try {
            // Obter a lista de produtos do banco de dados
            List<Produto> produtos = dbHelper.listarProdutos();

            // Preencher a tabela com os produtos
            for (Produto produto : produtos) {
                adicionarLinhaProduto(produto);
            }
        } catch (Exception e) {
            // Logar o erro e mostrar uma mensagem de erro
            e.printStackTrace();
            Toast.makeText(this, "Erro ao listar produtos", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("DefaultLocale")
    private void adicionarLinhaProduto(Produto produto) {
        // Inflar o layout de linha de produto
        LinearLayout layout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_linha_produto, null);

        // Preencher os campos com os dados do produto
        TextView txtCodigo = layout.findViewById(R.id.txtCodigo);
        TextView txtNome = layout.findViewById(R.id.txtNome);
        TextView txtPreco = layout.findViewById(R.id.txtPreco);
        TextView txtQuantidade = layout.findViewById(R.id.txtQuantidade);

        txtCodigo.setText(String.valueOf(produto.getCodigo()));
        txtNome.setText(String.valueOf(produto.getNome()));
        txtPreco.setText(String.format("%.2f", produto.getPreco())); // Formatar preço com duas casas decimais
        txtQuantidade.setText(String.valueOf(produto.getQuantidade()));

        // Adicionar a linha de produto à tabela
        tabelaProdutos.addView(layout);
    }
}
