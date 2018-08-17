package vendas.ui.pedido;

import waba.fx.Color;
import waba.ui.*;

public class PedidoTabelaDePreco extends Container
{
   //public static Grid gridtab;
   public static Edit edIncluirpreco;
   ComboBox combTabela;
   public static Check flgipi;
   private Label lPreco,lTabela;
   
   public void onStart()
   {
      flgipi = new Check(" Tabelas de preço ativa");
      add(flgipi, LEFT + 2, TOP + 2);

      add(lPreco = new Label("Preço: "),LEFT + 2,AFTER + 2);
      edIncluirpreco = new Edit("");
      add(edIncluirpreco,  AFTER + 2, SAME);
      
      add(lTabela = new Label("Tabela: "), LEFT + 2, AFTER + 2);
      combTabela = new ComboBox(/*ForcaDeVendas.checkout*/);//FALTA O CONTEUDO DO COMBO
      add(combTabela, AFTER + 2, SAME);
      combTabela.setEnabled(false);
      combTabela.setBackColor(Color.DARK);
      lTabela.setForeColor(Color.DARK);
      /*String[] gridCaptions = { "Código", "Preço" };
      int[] gridWidths = { fm.getTextWidth("xxxxx"),
            //fm.getTextWidth("xxxxxxxxxxx"), fm.getTextWidth("123"),
            fm.getTextWidth("xxxxxxx"), };
      int[] gridAligns = { LEFT, LEFT };
      gridtab = new Grid(gridCaptions, gridWidths, gridAligns, false);*/
      
      //PedidoGeral ped = new PedidoGeral();   

        
      /*add(gridtab);
      gridtab.verticalLineStyle = Grid.VERT_DOT;
      gridtab.setRect(LEFT, AFTER + 3, FILL, FILL);
      gridtab.setBackColor(Color.WHITE); */
      
    
   
   }
   
   public void onEvent(Event e)
   {
      if(e.type==ControlEvent.PRESSED&&e.target==flgipi)
      {
         if(flgipi.getChecked())
         {
            edIncluirpreco.setEnabled(false);
            combTabela.setEnabled(true);
            edIncluirpreco.setBackColor(Color.DARK);
            combTabela.setBackColor(Color.WHITE);
            lPreco.setForeColor(Color.DARK);
            lTabela.setForeColor(Color.BLACK);
            repaint();
         }
         else
         {
            edIncluirpreco.setEnabled(true);
            combTabela.setEnabled(false);
            combTabela.setBackColor(Color.DARK);
            edIncluirpreco.setBackColor(Color.WHITE);
            lTabela.setForeColor(Color.DARK);
            lPreco.setForeColor(Color.BLACK);
            repaint();
         }
               
      }
   }
}
