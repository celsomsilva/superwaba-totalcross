/* ClienteGeral.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;

public class ClienteGeral extends Container
{   
   private Edit edCnpjcpf, edNome, edInscr;
   RadioGroup rgTipo;
   private Radio radiof,radioj;
   private ComboBox combCanal,combRegiaogeo,combCondpag;
   
   public void onStart()
   {
      Label l1 = new Label("CNPJ/CPF: ");
      Label l2 = new Label("Região Geográfica: ");
      int xx = l1.getPreferredWidth() + 2;
      setBackColor( Color.WHITE );
      edCnpjcpf = new Edit("xxxxxxx");
      add( l1, LEFT + 2, TOP + 2 );
      add( edCnpjcpf);
      edCnpjcpf.setRect(xx,SAME,FILL,PREFERRED);
      
      rgTipo=new RadioGroup();
      add( new Label("Tipo: "), LEFT + 2, AFTER + 2 );
      add(radiof=new Radio("Física",rgTipo),AFTER,SAME);
      radiof.setRect( xx, SAME, PREFERRED, PREFERRED );
      add(radioj=new Radio("Jurídica",rgTipo),AFTER,SAME);
      radioj.setChecked(true);
      
      edNome = new Edit("xxxxxx xxxx");
      add( new Label("Nome: "), LEFT+2, AFTER+2 );
      add( edNome );
      edNome.setRect( xx, SAME, FILL, PREFERRED );
      
      edInscr = new Edit("");
      add( new Label("I.E.: "), LEFT + 2, AFTER + 2 );
      add( edInscr);
      edInscr.setRect(xx,SAME,FILL,PREFERRED);
      
      xx = l2.getPreferredWidth() + 2;
      combCanal = new ComboBox(ForcaDeVendas.canal);
      add( new Label("Canal: "), LEFT + 2, AFTER + 2 );
      add( combCanal, AFTER + 2, SAME );
      combCanal.setRect(xx,SAME,PREFERRED,PREFERRED);
      
      combRegiaogeo = new ComboBox(ForcaDeVendas.regiaogeo);
      add( l2, LEFT + 2, AFTER + 2 );
      add( combRegiaogeo,AFTER + 2,SAME );
      combRegiaogeo.setRect(xx,SAME,PREFERRED,PREFERRED);

      combCondpag = new ComboBox(ForcaDeVendas.condpag);
      add( new Label("Pagamento: "), LEFT + 2, AFTER + 2 );
      add( combCondpag, AFTER, SAME );
      combCondpag.setRect(xx,SAME,PREFERRED,PREFERRED);
      
      
    }
    
   public void setarEdit(Cliente c) 
   {
      edCnpjcpf.setText( c.cnpjcpf );
      if (c.tipo.equals("Física"))
         rgTipo.setSelectedIndex(0);
      else 
         rgTipo.setSelectedIndex(1);
      edNome.setText( c.nome );
      edInscr.setText( c.inscr );
      combCanal.select( c.canal );
      combRegiaogeo.select( c.regiaogeo );
      combCondpag.select( c.condpag );
    }
   
   public void buscarDoEdit(Cliente c)
   {
      c.cnpjcpf = edCnpjcpf.getText();
      if(rgTipo.getSelectedIndex()==0)
         c.tipo = "Física";
      else
         c.tipo = "Jurídica";
      c.nome = edNome.getText();
      c.inscr = edInscr.getText();
      c.canal = (String)combCanal.getSelectedItem();
      c.regiaogeo = (String)combRegiaogeo.getSelectedItem();
      c.condpag = (String)combCondpag.getSelectedItem();
   }
   
   public void apagarEdit()
   {
      edCnpjcpf.setText("");
      radiof.setChecked(false);
      radioj.setChecked(true);
      edNome.setText("");
      edInscr.setText("");
      combCanal.select(0);
      combRegiaogeo.select(0);
      combCondpag.select(0);
   }
}