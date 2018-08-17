package vendas.ui.relatorios.sumariodevendas;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class PorPeriodo extends Container
{
   Button ok,back;
   Edit edDatainicio,edDatafim,edTotal;
   Label labInicio,labFim,labTotal;
   Grid grid;
   
   public void onStart()
   {
      labFim = new Label("Até ");
      int xx = labFim.getPreferredWidth() + 2;
      labInicio = new Label("De  ");
      add(labInicio, LEFT + 1,TOP + 1);
      edDatainicio = new Edit("xx/xx/xxxx");
      add(edDatainicio);
      edDatainicio.setRect(xx,SAME,PREFERRED,PREFERRED);
      edDatainicio.setText(new Date().toString());
      add(labFim,LEFT + 1,AFTER + 1);
      edDatafim = new Edit("xx/xx/xxxx");
      add(edDatafim, AFTER + 1, SAME);
      edDatafim.setText(new Date().toString());
      ok = new Button("Ok");
      add(ok, AFTER + 1, SAME);
      String[] gridCaptions = { "Data", "Total" };
      int gridWidths[] = 
      { 
         fm.getTextWidth("xxxxxxxxx"), // Data
         fm.getTextWidth("xxxxxxxx"),//Total
      };
      int gridAligns[] = { CENTER, CENTER};

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setBackColor(Color.WHITE);
      grid.setRect(LEFT, AFTER + 3, FILL,13*Settings.screenHeight/20 );
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
               Date Ddate = new Date(edDatainicio.getText());
               Date Ddate2 = new Date(edDatafim.getText());
               if(Ddate.isAfter(Ddate2))
                  new MessageBox("Menssagem", "ATENÇÃO|A data 'De'|deve anteceder|à data 'Até'").popupModal();
               Vector v = new Vector(1);
               
               double total = 0;
               while(!(Ddate.isAfter(Ddate2)))
               {  
                  
                  int data = Ddate.getDateInt();
                  Vector vaux = new Vector(1);
                  vaux=PedidoBD.instance.buscarPorData(data);
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
                  for (int i = 0; i < count; i++)
                  {
                     Vector vvector = (Vector) v.items[i];
                     for (int j=0; j<vvector.getCount() ;j++)
                     {
                        Pedido o = (Pedido) vvector.items[j];
                        total =total + o.precoTotal; 
                        s[i] = new String[]
                                    { 
                                    Convert.toString(o.data.getDay())+"/"+
                                    Convert.toString(o.data.getMonth())+"/"+
                                    Convert.toString(o.data.getYear()),
                                    Convert.toString(o.precoTotal, 2) 
                                    };
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
                  ForcaDeVendas.swapTo(ForcaDeVendas.SUMARIODEVENDASMENU); 
      }
   }
}
            
