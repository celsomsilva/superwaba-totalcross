package vendas.ui.relatorios.sumariodevendas;

import vendas.ui.*;
import vendas.*;
import waba.ui.ControlEvent;
import waba.ui.Event;

public class SumarioDeVendasMenu extends BaseMenu
{
   
   public SumarioDeVendasMenu() 
   {
      super( new String[]{ "Diário", "Mensal", "Anual", "Por Período" }, 
            new int[]{ ForcaDeVendas.DIARIO, ForcaDeVendas.MENSAL, ForcaDeVendas.ANUAL, ForcaDeVendas.PORPERIODO }, false );
   }
   
}
