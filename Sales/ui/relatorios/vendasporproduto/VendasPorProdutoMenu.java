package vendas.ui.relatorios.vendasporproduto;

import vendas.ui.*;
import vendas.*;
import waba.ui.ControlEvent;
import waba.ui.Event;

public class VendasPorProdutoMenu extends BaseMenu
{
   public VendasPorProdutoMenu() 
   {
      super( new String[]{  "Di�rio", "Mensal", "Anual", "Por Per�odo"  }, 
            new int[]{ ForcaDeVendas.DIARIO2, ForcaDeVendas.MENSAL2, ForcaDeVendas.ANUAL2, ForcaDeVendas.PORPERIODO2 }, false );
   }
}
