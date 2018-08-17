/* MainMenu
 */
package vendas.ui;

import vendas.*;

public class MenuPrincipal extends BaseMenu
{
   /** 
    *  All the internals of this function are performed by
    *  BaseMenu class, since these intermediates windows
    *  all share the same code, to create them.
    *  This is a good programming practice
    */
   public MenuPrincipal()
   {
      super(new String[]{ "Clientes", "Pedidos", "Produtos","Relatórios"}, 
            new int[]{ForcaDeVendas.CLIENTESMENU, ForcaDeVendas.PEDIDOSMENU, ForcaDeVendas.PROCURAPRODUTOS,ForcaDeVendas.RELATORIOMENU},true);
   }
}
