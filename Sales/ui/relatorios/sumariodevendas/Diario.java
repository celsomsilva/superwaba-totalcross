package vendas.ui.relatorios.sumariodevendas;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class Diario extends Container
{
   Button ok,back;
   Edit edData,edTotal;
   Label escolha_data,labTotal;
   Grid grid;
   
   public void onStart()
   {
      escolha_data = new Label("Escolha Data: ");
      add(escolha_data, LEFT + 1,TOP + 1);
      edData = new Edit("xx/xx/xxxx");
      add(edData, AFTER + 1, SAME);
      edData.setText(new Date().toString());
      ok = new Button("Ok");
      add(ok, AFTER + 1, SAME);
      String[] gridCaptions = { "Codigo", "Cliente", "Total" };
      int gridWidths[] = 
      { 
         fm.getTextWidth("x"), //codigo 
         fm.getTextWidth("xxxxxxxxxxxxxxxx"), // cliente
         fm.getTextWidth("x"), // total
      };
      int gridAligns[] = { LEFT, LEFT, RIGHT};

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setBackColor(Color.WHITE);
      grid.setRect(LEFT, AFTER + 5, FILL,3*Settings.screenHeight/4 );
      add(edTotal = new Edit(),RIGHT - 1,BOTTOM - 1);
      add(labTotal = new Label("Total: "),BEFORE - 1,SAME);
      add(back = new Button("Voltar"), LEFT + 1, SAME);
      edTotal.alignment = RIGHT; 

      
      
      
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
               Date Ddate = new Date(edData.getText());
               int date = Ddate.getDateInt();
               double total = 0;
               Vector v=new Vector();
               v=PedidoBD.instance.buscarPorData(date);
               int count = v.size();
               if (count == 0)
                  grid.clear(); 
               else
               {
                 String[] []s = new String[count][];
                  for (int i = 0; i < count; i++)
                  {
                     Pedido o = (Pedido) v.items[i];
                     total =total + o.precoTotal; 
                     s[i] = new String[]
                                 { 
                                 Convert.toString(o.codigo),
                                 o.cliente,
                                 Convert.toString(o.precoTotal, 2) 
                                 };
                   }
                   grid.setItems(s);
                   edTotal.setText(Convert.toString(total,2));
                   edTotal.setEditable(false);
                 }
               }
            
               if(e.target==back)
                  ForcaDeVendas.swapTo(ForcaDeVendas.SUMARIODEVENDASMENU); 
               
         }
   }
}
