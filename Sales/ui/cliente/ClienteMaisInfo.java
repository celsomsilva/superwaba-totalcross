/* ClienteMaisInfo.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.*;
import waba.fx.Color;
import waba.ui.*;

public class ClienteMaisInfo extends Container
{ 
   private Edit edTransportador, edAgente, edAtendente;
   private ComboBox combRamo,combCheckout,combRegiao,combCondfrete;
   
   public void onStart()
   {      
      Label l1 = new Label("Transportador: ");
      int xx = l1.getPreferredWidth() + 2;
      setBackColor( Color.WHITE );

      combCheckout = new ComboBox(ForcaDeVendas.checkout);
      add( new Label("Checkout: "), LEFT + 2, AFTER + 2 );
      add( combCheckout,AFTER + 2,SAME );
      combCheckout.setRect(xx,SAME,PREFERRED,PREFERRED);
      
      edTransportador = new Edit("");
      add( l1, LEFT + 2, AFTER + 2 );
      add( edTransportador);
      edTransportador.setRect( xx, SAME, FILL, PREFERRED );
      
      combRegiao = new ComboBox(ForcaDeVendas.regiao);
      add( new Label("R.Nilsen: "), LEFT + 2, AFTER + 2 );
      add( combRegiao,AFTER + 2,SAME );
      combRegiao.setRect(xx,SAME,PREFERRED,PREFERRED);
      
      combCondfrete = new ComboBox(ForcaDeVendas.condfrete);
      add( new Label("Cond.frete: "), LEFT + 2, AFTER + 2 );
      add( combCondfrete,AFTER + 2,SAME );
      combCondfrete.setRect(xx,SAME,PREFERRED,PREFERRED);
      combCondfrete.select(1);
      
      edAgente = new Edit("");
      add( new Label("Agente: "), LEFT + 2, AFTER + 2);
      add(edAgente);
      edAgente.setRect( xx, SAME, FILL, PREFERRED );
            
      combRamo = new ComboBox(ForcaDeVendas.ramo);
      add( new Label("Ramo: "), LEFT + 2, AFTER + 2 );
      add( combRamo,AFTER + 2,SAME );
      combRamo.setRect(xx,SAME,PREFERRED,PREFERRED);
      
      edAtendente = new Edit("");
      add( new Label("Atendente: "), LEFT + 2, AFTER + 2);
      add(edAtendente);
      edAtendente.setRect( xx, SAME, FILL, PREFERRED);
   }
   
   public void setarEdit(Cliente c) 
   {
      combCheckout.select( c.checkout );
      edTransportador.setText( c.transportador );
      combRegiao.select( c.regiao );
      combCondfrete.select( c.condfrete);
      edAgente.setText( c.agente );
      combRamo.select( c.ramo );
      edAtendente.setText(c.atendente);
   }
   
   public void buscarDoEdit(Cliente c)
   {
      c.checkout = (String)combCheckout.getSelectedItem();
      c.transportador = edTransportador.getText();
      c.regiao = (String)combRegiao.getSelectedItem();
      c.condfrete = (String)combCondfrete.getSelectedItem();
      c.agente = edAgente.getText();
      c.ramo = (String)combRamo.getSelectedItem();
      c.atendente = edAtendente.getText();
   }
   
   public void apagarEdit()
   {
      combCheckout.select(0);
      edTransportador.setText("");
      combRegiao.select(0);
      combCondfrete.select(1);
      edAgente.setText("");
      combRamo.select(0);
      edAtendente.setText("");
   }
}