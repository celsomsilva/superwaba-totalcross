/*
 * ProcuraPedido.java
 */
package vendas.ui.pedido;

import vendas.bd.*;
import vendas.*;
import waba.fx.*;
import waba.ui.*;
import waba.util.*;
import waba.sys.*;

public class ProcuraPedidos extends Container
{
   private Edit edDate;
   private Grid grid;
   private Button btnSearch, btnBack, btnView, btnRemove;
   private ComboBox cbClient;

   public void onStart()
   {
      Button.commonGap = 1;

      add(new Label("Client: "), LEFT + 2, TOP + 2);
      add(cbClient = new ComboBox());
      cbClient.setRect(AFTER, SAME, FILL, PREFERRED);
      add(new Label("Date: "), LEFT + 2, AFTER + 2);
      add(edDate = new Edit("xx/xx/xxxx"), cbClient.getRect().x, SAME);
      add(btnSearch = new Button("Buscar"), AFTER + 2, SAME);
      add(btnBack = new Button("Voltar"), RIGHT - 1, BOTTOM - 1);
      add(btnView = new Button("Exibir"), BEFORE - 2, SAME);
      add(btnRemove = new Button("Remover"), BEFORE - 2, SAME);

      String[] gridCaptions = { "Código", "Data", "Cliente", "Preço Total" };
      int[] gridWidths = 
      {     
            fm.getTextWidth("xx"),
            fm.getTextWidth("xx/xx/xxxx"),
            fm.getTextWidth("nome do cliente"),
            fm.getTextWidth("123456789"), 
      };
      int[] gridAligns = { LEFT, CENTER, LEFT, RIGHT };

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add( grid );
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setRect(LEFT, AFTER + 7, FILL, FIT, btnSearch);
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         grid.setBackColor(Color.WHITE);
         btnSearch.setBackColor(clrBack);
         btnSearch.setForeColor(clrFore);
         btnBack.setBackColor(clrBack);
         btnBack.setForeColor(clrFore);
         btnView.setBackColor(clrBack);
         btnView.setForeColor(clrFore);
         btnRemove.setBackColor(clrBack);
         btnRemove.setForeColor(clrFore);
      }
      
      cbClient.removeAll();
      String []names = ClienteBD.instance.lerTodosOsNomes();
      if (names.length == 0)
         new MessageBox("Erro", "Não há clientes registrados").popupModal();
      else
         cbClient.add(names);
      update(false);
   }

   public void update(boolean fillGrid)
   {
      if (fillGrid)
      {
         String nameStr = (String)cbClient.getSelectedItem();
         String dateStr = edDate.getText();
         Vector v = PedidoBD.instance.procuraPedidos(nameStr, dateStr);
         int count = v.size();
         if (count == 0)
            grid.clear(); 
         else
         {
            String[] []s = new String[count][];
   
            for (int i = 0; i < count; i++)
            {
               Pedido o = (Pedido) v.items[i];
               s[i] = new String[] { 
                     Convert.toString(o.codigo),
                     o.data.getDate(), 
                     o.cliente,
                     Convert.toString(o.precoTotal, 2) 
               };
            }
            grid.setItems(s);               
         }
      }
   }

   private Pedido getSelectedOrder()
   {
      int idx = grid.getSelectedLine();
      if (idx < 0) return null;
      String []s = grid.getItem(idx);
      return PedidoBD.instance.procuraPeloCodigo(Convert.toInt(s[0]));
   }
   
   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target == btnBack) 
               ForcaDeVendas.swapTo(ForcaDeVendas.PEDIDOSMENU);
            else
            if (e.target == btnSearch) 
               update( true );
            else
            if (e.target == btnView)
            {
               Pedido o = getSelectedOrder();
               if (o != null)
               {
                  PedidoGeral.pedidoAtual = o;
                  PedidoGeral.atualiza = true;
                  ItemsDoPedido.atualiza = true;
                  ForcaDeVendas.swapTo(ForcaDeVendas.NOVOPEDIDO);
               }
            }
            else
            if (e.target == btnRemove)
            {
               Pedido o = getSelectedOrder();
               if (o != null)
               {
                  PedidoBD.instance.removePedido(o);
                  update(true);
               }
            }
            break;

         case ControlEvent.FOCUS_OUT:
            if (e.target == edDate) // reformat the date
            {
               if (edDate.getLength() > 0)
                  edDate.setText(new Date(edDate.getText()).toString());
            }
            break;
      }
   }
   
   public void onAdd()
   {
      update(false);
   }
}
