package br.com.crudsimples.persistencia;

import br.com.crudsimples.modelo.Produto;
import br.com.crudsimples.modelo.ProdutoDao;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ProdutoDaoJpa implements ProdutoDao 
{
@PersistenceContext
private EntityManager manager;

    @Override
    public Collection<Produto> buscaPorNome(String nome) 
    {
    return manager.createQuery("SELECT p FROM Produto p WHERE p.nome LIKE :nome")
            .setMaxResults(15).setParameter("nome",nome+"%").getResultList();
    }

    @Override
    public void salva(Produto t) 
    {
    manager.persist(t);
    }

    @Override
    public void edita(Produto t) 
    {
    manager.merge(t);
    }

    @Override
    public void exclui(Produto t) 
    {
    /* 
       O comum seria fazer
       
       manager.remove(manager.getReference(Produto.class,t.getCodigo()));
       
       mas eu nã fiz assim. Por que?
       Porque eu estou fazendo duas queries (uma para buscar no banco e outra 
       para deletar) por causa do Hibernate. Ou seja, estas duas queries são 
       consequências do framework que estou usando. Não acho justo penalizar o 
       banco de dados com mais queries do que deveria receber neste método por 
       conta do Hibernate. Por isso abdico do framework neste método em 
       benefício de uma instrução nativa.
    */
    manager.createNativeQuery("delete from compra where cd_produto=:produto")
            .setParameter("produto",t.getCodigo()).executeUpdate();
    manager.createNativeQuery("delete from produto where cd_produto=:produto")
            .setParameter("produto",t.getCodigo()).executeUpdate();
    }

    @Override
    public Collection<Produto> listaTodos() 
    {
    return manager.createQuery("SELECT p FROM Produto p").getResultList();
    }

    @Override
    public boolean existeProdutoComEsteNome(String nome) 
    {
    return ((long)manager.createQuery("SELECT COUNT(p) FROM Produto p WHERE "
            + "p.nome=:nome").setParameter("nome",nome).getSingleResult()!=0);
    }
}