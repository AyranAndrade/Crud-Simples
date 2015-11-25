package br.com.crudsimples.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Ayran
 */
@Entity
@Table(name="cliente")
public class Cliente implements Serializable 
{
@Id
@GeneratedValue
@Column(name="cd_cliente")
private int codigo;
@Column(name="nm_cliente",length=60)
@NotNull(message="O nome do cliente não pode ser nulo!")
@Size(min=3,max=60,message="O nome do cliente deve ter entre 3 e 60 caracteres!")
private String nome;
@Column(name="cd_cpf_cliente",length=11,unique=true)
@NotNull(message="O CPF digitado é nulo, ou então, você o digitou errado!")
private String cpf;

    public Cliente(int codigo, String nome, String cpf) {
        this.codigo = codigo;
        this.nome = nome;
        setCpf(cpf);
    }

    public Cliente(){}

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        setCpf(cpf);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public void setCpf(String cpf) 
    {
      try
      {
      long l=Long.parseLong(cpf);
        if(cpf.length()==11)
        {
        this.cpf=cpf;    
        }
      }
      catch(NumberFormatException ex)
      {
        if(cpf!=null && cpf.length()!=0) 
        {
        this.cpf=cpf.replaceAll("[^0-9]+","");
          if(this.cpf.length()!=11)
          {
          this.cpf=null;
          }
        }
      }
    }
}