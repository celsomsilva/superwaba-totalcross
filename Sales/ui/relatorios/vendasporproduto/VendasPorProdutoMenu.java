package vendas.ui.relatorios.vendasporproduto;

import vendas.ui.*;
import vendas.*;
import waba.ui.ControlEvent;
import waba.ui.Event;

public class VendasPorProdutoMenu extends BaseMenu
{
   public VendasPorProdutoMenu() 
   {
      super( new String[]{  "Diário", "Mensal", "Anual", "Por Período"  }, 
            new int[]{ ForcaDeVendas.DIARIO2, ForcaDeVendas.MENSAL2, ForcaDeVendas.ANUAL2, ForcaDeVendas.PORPERIODO2 }, false );
   }
}
