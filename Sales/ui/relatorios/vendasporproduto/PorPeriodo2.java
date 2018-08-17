package vendas.ui.relatorios.vendasporproduto;


import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class PorPeriodo2 extends Container
{
   Button ok,back;
   Edit eddatefrom,eddateto,edtotal;
   Label labfrom,labto,labtotal;
   Grid grid;
   
   public void onStart()
   {
      labfrom = new Label("De ");
      add(labfrom, LEFT+2,TOP + 1);
      eddatefrom = new Edit("xx/xx/xxxx");
      add(eddatefrom, AFTER, TOP + 1);
      eddatefrom.setText(new Date().toString());
      labto = new Label("Até    ");
      add(labto, LEFT+2,AFTER+3);
      eddateto = new Edit("xx/xx/xxxx");
      add(eddateto, AFTER, SAME);
      eddateto.setText(new Date().toString());
      ok = new Button("Ok");
      add(ok, AFTER+5, SAME);
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
      grid.setRect(LEFT, AFTER + 5, FILL,13*Settings.screenHeight/20 );
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
               Date Ddate = new Date(eddatefrom.getText());
               Date Ddate2 = new Date(eddateto.getText());
               if(Ddate.isAfter(Ddate2))
                  new MessageBox("Message", "ATTENTION|The date 'to' can|be not lower than|the date 'from'").popupModal();
               Vector v = new Vector(1);
               
               double total = 0;
               while(!(Ddate.isAfter(Ddate2)))
               {  
                  int date = Ddate.getDateInt();
                  Vector vaux = new Vector(1);
                  vaux=PedidoBD.instance.buscarPorData(date);
                  if (vaux.size()!=0)
                     v.add(vaux);
                  Ddate.advance(1);
               }
               int count = v.size();
               if (count == 0)
                  grid.clear(); 
               else
               {
                 String[] []s = new String[ForcaDeVendas.MAX][];
                 int a=0;
                 for (int i = 0; i < count; i++)
                 {
                    Vector vvector = (Vector) v.items[i];
                    for (int j=0; j<vvector.getCount() ;j++)
                    {
                       Pedido o = (Pedido) vvector.items[j];
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
