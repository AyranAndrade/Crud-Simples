package br.com.crudsimples.modelo;

import java.util.Collection;

/**
 *
 * @author Ayran
 * @param <T>
 */
public interface Dao<T>
{
    public void salva(T t);
    public void edita(T t);
    public void exclui(T t);
    public Collection<T> listaTodos();
}