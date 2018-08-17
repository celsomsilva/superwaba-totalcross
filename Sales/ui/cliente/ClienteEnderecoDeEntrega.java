/*
 * ClienteEnderecoDeEntrega.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.ForcaDeVendas;
import waba.fx.Color;
import waba.ui.*;

public class ClienteEnderecoDeEntrega extends Container
{
   protected Edit edEnderecoent, edBairroent, edCidadeent, edCepent;
   protected ComboBox combEstadoent;
   
   public void onStart()
   { 
      Label l1 = new Label("Endereço: ");
      int xx = l1.getPreferredWidth() + 2;
      setBackColor( Color.WHITE );
      edEnderecoent = new Edit("");
      add( l1, LEFT+2, AFTER+2 );
      add( edEnderecoent );
      edEnderecoent.setRect( xx, SAME, FILL, PREFERRED );
      
      edBairroent = new Edit("xxxxxxxxx");
      add( new Label("Bairro: "), LEFT + 2, AFTER + 2 );
      add( edBairroent, AFTER, SAME );
      edBairroent.setRect( xx, SAME, FILL, PREFERRED );
      
      edCidadeent = new Edit("xxxxxxxxx");
      add( new Label("Cidade: "), LEFT + 2, AFTER + 2 );
      add( edCidadeent, AFTER, SAME );
      edCidadeent.setRect( xx, SAME, FILL, PREFERRED );
     
      edCepent = new Edit("XXXXXXXX");
      add( new Label("CEP: "), LEFT + 2, AFTER + 2);
      add( edCepent );
      edCepent.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      combEstadoent = new ComboBox(ForcaDeVendas.estados);
      add( new Label("UF: "), AFTER + 2, SAME );
      add( combEstadoent, AFTER + 2,SAME );
      
   }
   
   public void setarEdit(Cliente c) 
   {
      edEnderecoent.setText( c.enderecoent );
      edCidadeent.setText( c.cidadeent );
      combEstadoent.select( c.estadoent );
      edCepent.setText( c.cepent );
   }
   
   public void buscarDoEdit(Cliente c)
   {
      c.enderecoent = edEnderecoent.getText();
      c.cidadeent = edCidadeent.getText();
      c.estadoent = (String)combEstadoent.getSelectedItem();
      c.cepent = edCepent.getText();
   }
   
   public void apagarEdit()
   {
      edEnderecoent.setText("");
      edCidadeent.setText("");
      combEstadoent.select(0);
      edCepent.setText("");
   }
}
