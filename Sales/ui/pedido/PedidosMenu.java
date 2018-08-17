package vendas.ui.pedido;

import vendas.ui.*;
import vendas.*;

public class PedidosMenu extends BaseMenu
{  
   /** 
    *  All the internals of this function are performed by
    *  BaseMenu class, since these intermediates windows
    *  all share the same code, to create them.
    *  This is a good programming practice
    */
   public PedidosMenu() 
   {
      super( new String[]{ "Novo Pedido", "Procura Pedidos" }, 
            new int[]{ ForcaDeVendas.NOVOPEDIDO, ForcaDeVendas.PROCURAPEDIDOS }, false );
   }
}
