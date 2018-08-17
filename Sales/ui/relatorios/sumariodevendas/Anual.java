package vendas.ui.relatorios.sumariodevendas;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class Anual extends Container
{
   Button ok,back;
   Edit edTotal;
   ComboBox combAno;
   Label labAno,labTotal;
   Grid grid;
   public static String[] anos = {"2006","2007","2008","2009","2010","2011"};//se apagar os primeiros anos desse vetor, o evento "ok" tem que ser mudado
   public static String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};
   Date d ;
   
   
   public void onStart()
   {
      labAno = new Label("Ano ");
      add(labAno, LEFT + 1,TOP + 1);
      combAno = new ComboBox(anos);
      add(combAno,AFTER + 1,SAME);
      ok = new Button("Ok");
      add(ok, AFTER + 2, SAME);
      String[] gridCaptions = { "Mes", "Total" };
      int gridWidths[] = 
      { 
         fm.getTextWidth("xxxxxxxxxx"),//mes
         fm.getTextWidth("xxxxxxxxxx"), // Total
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
               double totalt = 0;
               double total = 0;
               int cont = 0;
               int ano = combAno.getSelectedIndex()+2006;
               Vector v=PedidoBD.instance.lerTodosOsMeses(ano);
               if (v.size() == 0)
                  grid.clear(); 
               else
               {
                 String[] []s = new String[ForcaDeVendas.MAX][];
                 Pedido o;
                 for(int mes = 1; mes < 13; mes++)
                 {
                    Vector vec=PedidoBD.instance.lerTodosOsDias(ano,mes);
                 
                  for (int i = 0; i < vec.size(); i++)
                  {
                     o = (Pedido) vec.items[i];
                     total = total + o.precoTotal;
                     if (i+1 == vec.size())
                     {
                        s[cont++] = new String[]
                                              { 
                                              meses[mes-1],
                                              Convert.toString(total, 2) 
                                              };       
                     }
                  }
                  if (vec.size()!=0)
                     totalt = totalt + total;
                  total = 0;                
                 }
                  
                   int n;
                   for(n=0;s[n]!=null;n++)
                     ;
                   String[] []s2 = new String[n][];
                   for(int j=0;j<n;j++)
                     s2[j]=s[j];
                   grid.setItems(s2);
                   edTotal.setText(Convert.toString(totalt,2));
                   edTotal.setEditable(false);
                 }
               }
            
               if(e.target==back)
                  ForcaDeVendas.swapTo(ForcaDeVendas.SUMARIODEVENDASMENU); 
               
         }
   }
}
