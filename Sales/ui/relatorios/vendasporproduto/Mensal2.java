package vendas.ui.relatorios.vendasporproduto;


import vendas.bd.*;
import vendas.ui.relatorios.sumariodevendas.Anual;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class Mensal2 extends Container
{
   Button ok,back;
   Edit edTotal;
   ComboBox combAno,combMes;
   Label labAno,labMes,labTotal;
   Grid grid;
   Date d ;
   
   
   public void onStart()
   {
      labAno = new Label("Ano ");
      add(labAno, LEFT + 1,TOP + 1);
      combAno = new ComboBox(Anual.anos);
      add(combAno,AFTER + 1,SAME);
      labMes = new Label("Mês ");
      add(labMes,AFTER + 2,SAME);
      combMes = new ComboBox(Anual.meses);
      add(combMes,AFTER + 1,SAME);
      ok = new Button("Ok");
      add(ok, AFTER + 2, SAME);
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
      add(edTotal = new Edit("1234567"),RIGHT - 1,BOTTOM - 1);
      add(labTotal = new Label("Total: "),BEFORE - 1,SAME);
      add(back = new Button("Voltar"), LEFT + 1, SAME);
      edTotal.alignment = RIGHT;
      d = new Date();
      combAno.select(Convert.toString(d.getYear()));
      combMes.select(d.getMonth()-1);
      
      
      
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
               double total=0;
               int mes = combMes.getSelectedIndex()+1;
               int ano = combAno.getSelectedIndex()+2006;
               Vector v=PedidoBD.instance.lerTodosOsDias(ano,mes);
               int count = v.size();
               if (count == 0)
                  grid.clear(); 
               else
               {
                 String[] []s = new String[ForcaDeVendas.MAX][];
                 int a = 0;
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
                   edTotal.setText(Convert.toString(total,2));
                   edTotal.setEditable(false);
                 }
               }
            
               if(e.target==back)
                  ForcaDeVendas.swapTo(ForcaDeVendas.VENDASPORPRODUTOMENU); 
               
         }
   }
}
