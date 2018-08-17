/* ClienteMaisContato.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.*;
import waba.fx.Color;
import waba.ui.*;

public class ClienteMaisEndereco extends Container
{
   private Edit  edTel1, edRamal1, edTel2, edRamal2, edTel3, edRamal3, edFax, edEmail;
   
   public void onStart()
   {
      Label l1 = new Label("(DDD)Tel.3: ");
      int xx = l1.getPreferredWidth() + 2;
      /*edDdd = new Edit("21");
      add( new Label("DDD: "), LEFT + 2, AFTER + 2 );
      add( edDdd );
      edDdd.setRect( xx, SAME, PREFERRED, PREFERRED );*/
      
      edTel1 = new Edit("2133445555");
      add( new Label("(DDD)Tel.1: "), LEFT + 2, AFTER + 2 );
      add( edTel1 );
      edTel1.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      edRamal1 = new Edit("");
      add( new Label("Ramal: "), AFTER + 2, SAME );
      add( edRamal1 );
      edRamal1.setRect( AFTER + 2, SAME, FILL, PREFERRED );
      
      edTel2 = new Edit("2133445555");
      add( new Label("(DDD)Tel.2: "), LEFT + 2, AFTER + 2 );
      add( edTel2 );
      edTel2.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      edRamal2 = new Edit("");
      add( new Label("Ramal: "), AFTER + 2, SAME );
      add( edRamal2 );
      edRamal2.setRect( AFTER + 2, SAME, FILL, PREFERRED );
      
      edTel3 = new Edit("2133445555");
      add( l1, LEFT+2, AFTER + 2 );
      add( edTel3 );
      edTel3.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      edRamal3 = new Edit("");
      add( new Label("Ramal: "), AFTER + 2, SAME );
      add( edRamal3 );
      edRamal3.setRect( AFTER + 2, SAME, FILL, PREFERRED );
      
      edFax = new Edit("XXXXX");
      add( new Label("FAX: "), LEFT + 2, AFTER + 2 );
      add( edFax);
      edFax.setRect( xx, SAME, FILL, PREFERRED);
      
      edEmail = new Edit("");
      add( new Label("E-mail: "), LEFT + 2, AFTER + 2);
      add( edEmail );
      edEmail.setRect( xx, SAME, FILL, PREFERRED );
   }
   
   public void setarEdit(Cliente c) 
   {
      //edDdd.setText(c.ddd);
      edTel1.setText( c.tel1 );
      edRamal1.setText(c.ramal1);
      edTel2.setText(c.tel2);
      edRamal2.setText(c.ramal2);
      edTel3.setText( c.tel3 );
      edRamal3.setText(c.ramal3);
      edFax.setText( c.fax );
      edEmail.setText( c.email );
   }
   
   public void buscarDoEdit(Cliente c)
   {
      //c.ddd = edDdd.getText();
      c.tel1 = edTel1.getText();
      c.ramal1 = edRamal1.getText();
      c.tel2 = edTel2.getText();
      c.ramal2 = edRamal2.getText();
      c.tel3 = edTel3.getText();
      c.ramal3 = edRamal3.getText();
      c.fax = edFax.getText();
      c.email = edEmail.getText();
   }
   
   public void apagarEdit()
   {
      //edDdd.setText("");
      edTel1.setText("");
      edRamal1.setText("");
      edTel2.setText("");
      edRamal2.setText("");
      edTel3.setText("");
      edRamal3.setText("");
      edFax.setText("");
      edEmail.setText("");
   }
}
