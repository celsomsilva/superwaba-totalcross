/*
 * ClienteCreditos.java 
 */
/*
 * ClienteCreditos.java
 */
package vendas.ui.cliente;

//import vendas.ForcaDeVendas;
import vendas.bd.*;
import waba.ui.*;

public class ClienteCreditos extends Container
{
   private Edit edStatuscred, edLimitecred, edSaldocred;
   
   public void onStart()
   {      
      Label l1 = new Label("Status: ");
      int xx = l1.getPreferredWidth() + 2;
      
      edStatuscred = new Edit("xxxxxxxxxxxxxxx");
      add( l1, LEFT + 2, AFTER + 2 );
      add( edStatuscred);
      edStatuscred.setRect( xx, SAME, FILL, PREFERRED );
            
      edLimitecred = new Edit("");
      add( new Label("Limite: "), LEFT + 2, AFTER + 2);
      add( edLimitecred );
      edLimitecred.setRect( xx, SAME, FILL, PREFERRED );
      
      edSaldocred = new Edit("");
      add( new Label("Saldo: "), LEFT + 2, AFTER + 2);
      add(edSaldocred);
      edSaldocred.setRect( xx, SAME, FILL, PREFERRED );
      }
   
   public void setarEdit(Cliente c) 
   {
      edStatuscred.setText( c.statuscred );
      edLimitecred.setText( c.limitecred );
      edSaldocred.setText( c.saldocred );
   }
   
   public void buscarDoEdit(Cliente c)
   {
      c.statuscred = edStatuscred.getText();
      c.limitecred = edLimitecred.getText();
      c.saldocred = edSaldocred.getText();
   }
   
   public void apagarEdit()
   {
      edStatuscred.setText("");
      edLimitecred.setText("");
      edSaldocred.setText("");
    }

}
