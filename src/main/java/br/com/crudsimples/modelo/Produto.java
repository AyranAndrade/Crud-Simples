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
@Table(name="produto")
public class Produto implements Serializable 
{
@Id
@GeneratedValue
@Column(name="cd_produto")
private int codigo;
@Column(name="nm_produto",unique=true,length=30)
@NotNull(message="O nome do produto não pode ser nulo!")
@Size(min=3,max=30,message="O nome do produto deve ter entre 3 caracteres e 30 caracteres!")
private String nome;
@Column(name="nm_descricao_produto",length=200)
@Size(max=200,message="O tamanho máximo da descrição é de 200 caracteres!")
private String descricao;

    public Produto(){}

    public Produto(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}