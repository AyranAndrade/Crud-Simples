package br.com.crudsimples.modelo;

import java.util.Collection;

/**
 *
 * @author Ayran
 */
public interface ClienteDao extends Dao<Cliente>
{
    public Cliente buscaPorCpf(String cpf);
    public Collection<Cliente> buscaPorNome(String nome);
    public void compra(Cliente c,Collection<Produto> p);
    public boolean existeEsteCpf(String cpf);
}