/*
 * ClienteEnderecoDeCobranca.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.ForcaDeVendas;
import waba.fx.Color;
import waba.ui.*;

public class ClienteEnderecoDeCobranca extends Container
{
   protected Edit edEnderecocob, edBairrocob, edCidadecob, edCepcob;
   protected ComboBox combEstadocob;
   
   public void onStart()
   {  
      Label l1 = new Label("Endereço: ");
      int xx = l1.getPreferredWidth() + 2;
      setBackColor( Color.WHITE );
      edEnderecocob = new Edit("");
      add( l1, LEFT+2, AFTER+2 );
      add( edEnderecocob );
      edEnderecocob.setRect( xx, SAME, FILL, PREFERRED );
      
      edBairrocob = new Edit("xxxxxxxxx");
      add( new Label("Bairro: "), LEFT + 2, AFTER + 2 );
      add( edBairrocob, AFTER, SAME );
      edBairrocob.setRect( xx, SAME, FILL, PREFERRED );
      
      edCidadecob = new Edit("xxxxxxxxx");
      add( new Label("Cidade: "), LEFT + 2, AFTER + 2 );
      add( edCidadecob, AFTER, SAME );
      edCidadecob.setRect( xx, SAME, FILL, PREFERRED );
     
      edCepcob = new Edit("XXXXXXXX");
      add( new Label("CEP: "), LEFT + 2, AFTER + 2);
      add( edCepcob );
      edCepcob.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      combEstadocob = new ComboBox(ForcaDeVendas.estados);
      add( new Label("UF: "), AFTER + 2, SAME );
      add( combEstadocob, AFTER + 2,SAME );
      
   }
   
   public void setarEdit(Cliente c) 
   {
      edEnderecocob.setText( c.enderecocob );
      edCidadecob.setText( c.cidadecob );
      combEstadocob.select( c.estadocob );
      edCepcob.setText( c.cepcob );
   }
   
   public void buscarDoEdit(Cliente c)
   {
      c.enderecocob = edEnderecocob.getText();
      c.cidadecob = edCidadecob.getText();
      c.estadocob = (String)combEstadocob.getSelectedItem();
      c.cepcob = edCepcob.getText();
   }
   
   public void apagarEdit()
   {
      edEnderecocob.setText("");
      edCidadecob.setText("");
      combEstadocob.select(0);
      edCepcob.setText("");
   }
   
   

}
