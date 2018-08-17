/* ClientesMenu.java 
 */
package vendas.ui.cliente;

import vendas.*;
import vendas.ui.*;

public class ClientesMenu extends BaseMenu
{
   /** 
    *  All the internals of this function are performed by
    *  BaseMenu class, since these intermediates windows
    *  all share the same code, to create them.
    *  This is a good programming practice
    */
   public ClientesMenu( ) 
   {
      super( new String[]{ "Novo Cliente", "Procura Clientes" }, 
             new int[]{ ForcaDeVendas.NOVOCLIENTE, ForcaDeVendas.PROCURACLIENTES }, false );
   }
}
