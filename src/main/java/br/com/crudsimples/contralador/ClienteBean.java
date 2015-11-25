package br.com.crudsimples.contralador;

import br.com.crudsimples.modelo.Cliente;
import br.com.crudsimples.modelo.ClienteDao;
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
public class ClienteBean 
{
private final ClienteDao dao;
private String nome;
private String cpf;
private final Validator validator;
private Collection<Cliente> lista;
private Cliente cliente;//Para editar e excluir

    @Autowired
    public ClienteBean(ClienteDao dao) 
    {
    this.dao=dao;
    validator=Validation.buildDefaultValidatorFactory().getValidator();
    lista=dao.listaTodos();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    @Transactional
    public void novo()
    {
    Cliente c=new Cliente(nome,cpf);
      if(valida(c))
      {
      dao.salva(c);
      lista.add(c);
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_INFO,"Sucesso","O cliente foi salvo com "
                    + "sucesso!"));
      limpa();
      }
    }
    
    @Transactional
    public void edita()
    {
      if(valida(cliente))
      {
      dao.edita(cliente);
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_INFO,"Sucesso","O cliente foi editado com "
                    + "sucesso!"));
      limpa();
      }    
    }
    
    private boolean valida(Cliente c)
    {
    Set<ConstraintViolation<Cliente>> v=validator.validate(c);
      if(!v.isEmpty())
      {
      FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_ERROR,"Erro",v.stream().findFirst().get()
                    .getMessage()));
      return false;
      }
      else
      {
        if(dao.existeEsteCpf(c.getCpf()))
        {
        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage
            (FacesMessage.SEVERITY_ERROR,"Erro","Este CPF já existe em nosso "
                    + "sistema!"));    
        return false;
        }
      }
    return true;
    }
    
    private void limpa()
    {
    nome=null;
    cpf=null;
    cliente=new Cliente();
    }
    
    public Collection<Cliente> lista()
    {
    return lista;
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
    
    public void clienteParaEditarOuExcluir(Cliente c)
    {
    cliente=c;
    }
    
    @Transactional
    public void exclui()
    {
    dao.exclui(cliente);
    lista.remove(cliente);
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}