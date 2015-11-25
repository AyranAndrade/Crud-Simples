package br.com.modelo;

import br.com.crudsimples.modelo.Cliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ayran
 */
public class ClienteTest {
    
    public ClienteTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void cpfComLetrasENumeros()
    {
    Cliente c=new Cliente("Joao","123.456.789/01");
    assertEquals("12345678901",c.getCpf());
    }
    
    @Test
    public void cpfComLetrasENumerosMasComTamanhoIncorreto()
    {
    Cliente c=new Cliente("Joao","123.456.79/01");
    assertNull(c.getCpf());
    }
    
    @Test
    public void cpfSomenteNumeros()
    {
    Cliente c=new Cliente("Joao","12345679010");
    assertEquals("12345679010",c.getCpf());
    }
    
    @Test
    public void cpfSomenteNumerosMasComTamanhoIncorreto()
    {
    Cliente c=new Cliente("Joao","1234567010");
    assertNull(c.getCpf());    
    }
    
    @Test
    public void cpfComLetrasENumerosComUmPontoAMais()
    {
    Cliente c=new Cliente("Joao","123.456..9/01");
    assertNull(c.getCpf());    
    }
}