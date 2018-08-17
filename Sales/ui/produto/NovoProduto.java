/*
 * NovoProduto.java
 */
/*package vendas.ui.produto;

import vendas.*;
import vendas.bd.*;
import waba.ui.*;
import waba.util.Date;
import waba.fx.*;
import waba.sys.*;

public class NovoProduto extends Container
{
   public static Produto enviaProduto;
   private Button btnBack;
   private Edit edData, edNome, edCodigo, edDescr, edPreco, edFamilia, edIpi;

   public void onStart()
   {
      Button.commonGap = 1;
      Label l = new Label("Descrição: ");
      int xx = l.getPreferredWidth() + 2;

      // cria e adiciona os edit´s 
      add(new Label("Data: "), LEFT + 2, TOP + 2);
      edData = new Edit("xx/xx/xxxx");
      add(edData, xx, SAME); 
      edData.setMode(Edit.DATE);
      edData.setText(new Date().toString());
     
      add(new Label("Nome: "), LEFT + 2, AFTER + 2);
      edNome = new Edit("");
      add(edNome);
      edNome.setRect(xx,SAME,FILL,PREFERRED);

      add(new Label("Code: "), LEFT + 2, AFTER + 2);
      edCodigo = new Edit("");
      add(edCodigo);
      edCodigo.setRect(xx,SAME,FILL,PREFERRED);

      add(l, LEFT + 2, AFTER + 2);
      edDescr = new Edit("");
      add(edDescr);
      edDescr.setRect(xx,SAME,FILL,PREFERRED);

      add(new Label("Família: "), LEFT + 2, AFTER + 2);
      edFamilia = new Edit("");
      add(edFamilia);
      edFamilia.setRect(xx,SAME,FILL,PREFERRED);
      
      add(new Label("Preço: "), LEFT + 2, AFTER + 2);
      edPreco = new Edit("10000.000");
      edPreco.setMode(Edit.CURRENCY);
      add(edPreco, xx, SAME);
      
      add(new Label("IPI: "), AFTER + 2, SAME);
      edIpi = new Edit("100");
      edIpi.setMode(Edit.CURRENCY);
      add(edIpi,AFTER,SAME);
      
      add(btnBack = new Button("Voltar"), BEFORE - 2, SAME);

      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         btnBack.setBackColor(clrBack);
         btnBack.setForeColor(clrFore);
      }
      
      setaEdit( );
   }

   
   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target == btnBack) 
               ForcaDeVendas.swapTo(ForcaDeVendas.PROCURAPRODUTOS);
            break;
      }
   }

   public void setaEdit()
   {
      edNome.setText(enviaProduto.nome);
      edCodigo.setText(enviaProduto.codigo);
      edDescr.setText(enviaProduto.descr);
      edPreco.setText(Convert.toString(enviaProduto.precoUnit));
      edData.setText(enviaProduto.data);
      edFamilia.setText(enviaProduto.familia);
      edIpi.setText(enviaProduto.ipi);
      edCodigo.setEditable(false);
   }

}*/
