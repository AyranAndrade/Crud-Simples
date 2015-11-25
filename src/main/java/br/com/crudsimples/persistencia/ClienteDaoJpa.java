package br.com.crudsimples.persistencia;

import br.com.crudsimples.modelo.Cliente;
import br.com.crudsimples.modelo.ClienteDao;
import br.com.crudsimples.modelo.Produto;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteDaoJpa implements ClienteDao 
{
@PersistenceContext
private EntityManager manager;

    @Override
    public Cliente buscaPorCpf(String cpf) 
    {
      try
      {
      return (Cliente)manager.createQuery("SELECT c FROM Cliente c WHERE c.cpf LIKE :cpf")
              .setParameter("cpf",cpf+"%").getSingleResult();
      }
      catch(NoResultException | NonUniqueResultException ex)
      {
      return null;
      }
    }

    @Override
    public Collection<Cliente> buscaPorNome(String nome) 
    {
    return manager.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome")
            .setParameter("nome",nome+"%").setMaxResults(15).getResultList();
    }

    @Override
    public void compra(Cliente c, Collection<Produto> p)
    {
      p.stream().forEach((produto)->
      {
      manager.createNativeQuery("insert into item_produto_cliente values"
              + "(:cliente,:produto,current_timestamp())")
              .setParameter("cliente",c.getCodigo())
              .setParameter("produto",produto.getCodigo()).executeUpdate();
      });
    }

    @Override
    public void salva(Cliente t)
    {
    manager.persist(t);
    }

    @Override
    public void edita(Cliente t) 
    {
    manager.merge(t);
    }

    @Override
    public void exclui(Cliente t) 
    {
    /* 
       O comum seria fazer
       
       manager.remove(manager.getReference(Cliente.class,t.getCodigo()));
       
       mas eu nã fiz assim. Por que?
       Porque eu estou fazendo duas queries (uma para buscar no banco e outra 
       para deletar) por causa do Hibernate. Ou seja, estas duas queries são 
       consequências do framework que estou usando. Não acho justo penalizar o 
       banco de dados com mais queries do que deveria receber neste método por 
       conta do Hibernate. Por isso abdico do framework neste método em 
       benefício de uma instrução nativa.
    */
    manager.createNativeQuery("delete from compra where cd_cliente=:cliente")
            .setParameter("cliente",t.getCodigo()).executeUpdate();
    manager.createNativeQuery("delete from cliente where cd_cliente=:cliente")
            .setParameter("cliente",t.getCodigo()).executeUpdate();
    }

    @Override
    public Collection<Cliente> listaTodos() 
    {
    return manager.createQuery("SELECT c FROM Cliente c",Cliente.class)
            .setMaxResults(15).getResultList();
    }

    @Override
    public boolean existeEsteCpf(String cpf) 
    {
    return ((long)manager.createQuery("SELECT COUNT(c) FROM Cliente c WHERE "
            + "c.cpf=:cpf").setParameter("cpf",cpf).getSingleResult())!=0;
    }
}