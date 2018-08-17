package vendas.ui.relatorios.vendasporproduto;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class Diario2 extends Container
{
   Button ok,back;
   Edit eddate,edtotal;
   Label choose_date,labtotal;
   Grid grid;
   
   public void onStart()
   {
      choose_date = new Label("Date: ");
      add(choose_date, LEFT,TOP + 1);
      eddate = new Edit("xx/xx/xxxx");
      add(eddate, AFTER, TOP + 1);
      eddate.setText(new Date().toString());
      ok = new Button("Ok");
      add(ok, AFTER+5, TOP + 1);
      String[] gridCaptions = { "Produto", "Qtde", "Total" };
      int gridWidths[] = 
      { 
         fm.getTextWidth("xxxxxxxxxx"), // Produto
         fm.getTextWidth("xxxx"), //quantidade
         fm.getTextWidth("xxxxxxxx"), // Total
      };
      int gridAligns[] = { CENTER, CENTER, CENTER };

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setBackColor(Color.WHITE);
      grid.setRect(LEFT, AFTER + 5, FILL,3*Settings.screenHeight/4 );
      add(edtotal = new Edit(),RIGHT-9,BOTTOM-3);
      add(labtotal = new Label("Total: "),BEFORE-4,SAME);
      add(back = new Button("Back"), LEFT+5, SAME);
      edtotal.alignment = RIGHT; 

      
      
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         ok.setBackColor(clrBack);
         ok.setForeColor(clrFore);
         back.setBackColor(clrBack);
         back.setForeColor(clrFore);
      }
      
      grid.clear();
   }
   
   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if(e.target==ok)
            {
               Date Ddate = new Date(eddate.getText());
               int date = Ddate.getDateInt();
               double total = 0;
               Vector v=PedidoBD.instance.buscarPorData(date);
               int count = v.size();
               if (count == 0)
                  grid.clear(); 
               else
               {
                 String[] []s = new String[ForcaDeVendas.MAX][];
                 int a=0;
                 for (int i = 0; i < count; i++)
                 {
                    Pedido o = (Pedido) v.items[i];
                    Vector v2 = ItemDoPedidoBD.instance.lerTudo(o.codigo);
                    int count2 = v2.size();
                    for (int i2 = 0; i2 < count2; i2++)
                    {
                       ItemDoPedido I = (ItemDoPedido) v2.items[i2];
                       Produto p = ProdutoBD.instance.buscarPeloCodigo(I.codigoproduto);
                       s[a] = new String[]
                                { 
                                p.codigo,
                                Convert.toString(I.quant),
                                Convert.toString(I.precoTotal, 2) 
                                };
                       total =total + I.precoTotal;
                       a++;
                    }
                  }
                  int n;
                  for(n=0;s[n]!=null;n++)
                    ;
                  String[] []s2 = new String[n][];
                  for(int j=0;j<n;j++)
                    s2[j]=s[j];
                  grid.setItems(s2);
                  edtotal.setText(Convert.toString(total,2));
                  edtotal.setEditable(false);
                }
              }
                 
               if(e.target==back)
                  ForcaDeVendas.swapTo(ForcaDeVendas.VENDASPORPRODUTOMENU); 
               
         }
   }
}
