package br.com.crudsimples.controlador;

import br.com.crudsimples.modelo.Produto;
import br.com.crudsimples.modelo.ProdutoDao;
import java.util.Collection;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 *
 * @author Ayran
 */
@Controller
public class ProdutoBean 
{
private final ProdutoDao dao;
private String nome;
private String descricao;
private final Validator validator;
private Collection<Produto> lista;
private Produto produto;//Para editar e excluir

    @Autowired
    public ProdutoBean(ProdutoDao dao) 
    {
    this.dao = dao;
    validator=Validation.buildDefaultValidatorFactory().getValidator();
    lista=dao.listaTodos();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public Collection<Produto> lista()
    {
    return lista;
    }
    
    @Transactional
    public void novo()
    {
    Produto p=new Produto(nome,descricao);
      if(valida(p))
      {
      dao.salva(p);
      lista.add(p);
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_INFO,"Sucesso","O produto foi adicionado com"
                    + " sucesso!"));
      limpa();
      }
    }
    
    private void limpa()
    {
    nome=null;
    descricao=null;
    produto=new Produto();
    }
    
    @Transactional
    public void edita()
    {
      if(valida(produto))
      {
      dao.edita(produto);
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_INFO,"Sucesso","O produto foi editado com "
                    + "sucesso!"));
      limpa();
      }         
    }
    
    @Transactional
    public void exclui()
    {
    dao.exclui(produto);
    lista.remove(produto);    
    }
    
    private boolean valida(Produto p)
    {
    Set<ConstraintViolation<Produto>> v=validator.validate(p);
      if(!v.isEmpty())
      {
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_ERROR,"Erro",v.stream().findFirst().get()
                    .getMessage()));
      return false;
      }
      else
      {
        if(dao.existeProdutoComEsteNome(p.getNome()))
        {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_ERROR,"Erro","Um produto com este nome já "
                    + "está cadastrado!"));    
        return false;
        }
      }
    return true;
    }    

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
    
    public void procura()
    {
      if(nome.equals(""))    
      {
      lista=dao.listaTodos();
      }
      else
      {
      lista=dao.buscaPorNome(nome);
      }
    }
    
    public void produtoParaEditarOuExcluir(Produto p)
    {
    produto=p;
    }    
}