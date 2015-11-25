package br.com.crudsimples.modelo;

import java.util.Collection;

/**
 *
 * @author Ayran
 */
public interface ProdutoDao extends Dao<Produto>
{
    public Collection<Produto> buscaPorNome(String nome);
    public boolean existeProdutoComEsteNome(String nome);
}