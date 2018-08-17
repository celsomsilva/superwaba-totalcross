/*
 *NovoPedido.java
 */
package vendas.ui.pedido;

import waba.ui.*;
import waba.fx.*;


public class NovoPedido extends Container
{
   private TabPanel tp;
   protected static PedidoGeral ng;
   protected static ItemsDoPedido nv;
   //protected static PedidoTabelaDePreco nt;
   
   public void onStart()
   {
      ng = new PedidoGeral( );
      nv = new ItemsDoPedido( );
      //nt = new PedidoTabelaDePreco( );      
      String titulos []= { "Pedido",/*"Tabela de Preço",*/"Itens Pedido" };
      tp = new TabPanel(titulos);
      tp.setBorderStyle(Window.NO_BORDER); 
      Rect r = getClientRect();
      tp.setBackColor(Color.BRIGHT);
      tp.setRect(r);
      tp.setPanel(0,ng);
      //tp.setPanel(1,nt);
      tp.setPanel(1,nv);
      add( tp );
   }
}
