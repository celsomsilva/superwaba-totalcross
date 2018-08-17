package vendas.ui.relatorios;

import vendas.ui.*;
import vendas.*;

public class RelatorioMenu extends BaseMenu
{  
   /** 
    *  All the internals of this function are performed by
    *  BaseMenu class, since these intermediates windows
    *  all share the same code, to create them.
    *  This is a good programming practice
    */
   public RelatorioMenu() 
   {
      super( new String[]{ "Sumário de Vendas", "Vendas por Produto" }, 
            new int[]{ ForcaDeVendas.SUMARIODEVENDASMENU, ForcaDeVendas.VENDASPORPRODUTOMENU }, false );
   }
}