/*
 * ClienteEndereco.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import waba.ui.*;
import vendas.*;

public class ClienteEndereco extends Container
{
private Edit edNome, edCargo, edEndereco, edBairro, edCidade, edCep;
private ComboBox combEstado;
   
   public void onStart()
   {      
      Label l1 = new Label("Endereço: ");
      int xx = l1.getPreferredWidth() + 2;
      edNome = new Edit("");
      add( new Label("Contato: "), LEFT+2, TOP + 2 );
      add( edNome );
      edNome.setRect( xx, SAME, FILL, PREFERRED );
      
      edCargo = new Edit("xxxxxxxxxxxxxxx");
      add( new Label("Cargo: "), LEFT + 2, AFTER + 2 );
      add( edCargo );
      edCargo.setRect( xx, SAME, FILL, PREFERRED );
      
      edEndereco = new Edit("");
      add( l1, LEFT + 2, AFTER + 2 );
      add( edEndereco);
      edEndereco.setRect(xx,SAME,FILL,PREFERRED);
      
      edBairro = new Edit("");
      add(new Label("Bairro: "), LEFT + 2, AFTER + 2);
      add( edBairro);
      edBairro.setRect(xx,SAME,FILL,PREFERRED);
      
      edCidade = new Edit("");
      add( new Label("Cidade: "), LEFT + 2, AFTER + 2 );
      add( edCidade);
      edCidade.setRect(xx,SAME,FILL,PREFERRED);
      
      edCep = new Edit("XXXXXXXX");
      add( new Label("CEP: "), LEFT + 2, AFTER + 2 );
      add( edCep );
      edCep.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      combEstado = new ComboBox(ForcaDeVendas.estados);
      add( new Label("UF: "), AFTER + 2, SAME );
      add( combEstado,AFTER + 2,SAME );    
      
   }
   
   public void setarEdit(Cliente c) 
   {
      edNome.setText( c.cnome );
      edCargo.setText( c.cargo );
      edEndereco.setText( c.endereco );
      edBairro.setText( c.bairro );
      edCidade.setText( c.cidade );
      edCep.setText( c.cep );
      combEstado.select(c.estado);
   }
   
   public void buscarDoEdit(Cliente c)
   {
      c.cnome = edNome.getText();
      c.cargo = edCargo.getText();
      c.endereco = edEndereco.getText();
      c.bairro = edBairro.getText();
      c.cidade = edCidade.getText();
      c.cep = edCep.getText();
      c.estado = (String)combEstado.getSelectedItem();
   }
   
   public void apagarEdit()
   {
      edNome.setText("");
      edCargo.setText("");
      edEndereco.setText("");
      edBairro.setText("");
      edCidade.setText("");
      edCep.setText("");
      combEstado.select(0);
   }
}
