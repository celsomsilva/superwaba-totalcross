package vendas.ui.relatorios.sumariodevendas;

import vendas.ui.*;
import vendas.*;
import waba.ui.ControlEvent;
import waba.ui.Event;

public class SumarioDeVendasMenu extends BaseMenu
{
   
   public SumarioDeVendasMenu() 
   {
      super( new String[]{ "Di�rio", "Mensal", "Anual", "Por Per�odo" }, 
            new int[]{ ForcaDeVendas.DIARIO, ForcaDeVendas.MENSAL, ForcaDeVendas.ANUAL, ForcaDeVendas.PORPERIODO }, false );
   }
   
}
