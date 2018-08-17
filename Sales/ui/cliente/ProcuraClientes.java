/*
 * ProcuraClientes.java
 */
package vendas.ui.cliente;

import vendas.bd.*;
import vendas.*;
import waba.sys.*;
import waba.ui.*;
import waba.util.Vector;
import waba.fx.Color;

public class ProcuraClientes extends Container
{
   private Button btnVoltar, btnBuscar, btnExibir, btnRemover;
   private Grid grid;
   private Edit edNome, edCidade;
   private ComboBox combEstado;

   public void onStart()
   {
      Button.commonGap = 1;

      add(new Label("Nome: "), LEFT + 1, TOP + 2);
      add(edNome = new Edit(""),AFTER,SAME);
      add(new Label("Cid:"), LEFT + 1, AFTER + 2);
      add(edCidade = new Edit("RIO DE JANEIRO"), AFTER, SAME);
      add(new Label("UF:"), AFTER + 1, SAME);
      add(combEstado = new ComboBox(ForcaDeVendas.estados),AFTER,SAME);
      add(btnBuscar = new Button("Buscar"));
      btnBuscar.setRect(AFTER + 2, SAME, PREFERRED, PREFERRED);
      add(btnVoltar = new Button("Voltar"), RIGHT - 1, BOTTOM - 1);
      add(btnExibir = new Button("Exibir"), BEFORE - 2, SAME);
      add(btnRemover = new Button("Remover"), BEFORE - 2, SAME);

      int gridWidths[] = 
      { 
         fm.getTextWidth("xxxxxxxxxxx"), // nome/razao
         fm.getTextWidth("xxxxxxxxx"), // cidade
         fm.getTextWidth("xxx"), // estado
      };
      int gridAligns[] = { LEFT, LEFT, CENTER };
      String[] gridCaptions = { "Nome", "Cidade", "Estado" };

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setRect(LEFT, AFTER + 5, FILL, FIT, btnBuscar);
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         btnBuscar.setBackForeColors(clrBack,clrFore);
         btnVoltar.setBackForeColors(clrBack,clrFore);
         btnExibir.setBackForeColors(clrBack,clrFore);
         btnRemover.setBackForeColors(clrBack,clrFore);
         grid.setBackColor(Color.WHITE);
      }
      Button.commonGap = 0;
   }

   public void atualiza()
   {
      String nomeStr = edNome.getText();
      String cidadeStr = edCidade.getText();
      String estadoStr = (String) combEstado.getSelectedItem();
      
      Vector v = ClienteBD.instance.procuraClientes(nomeStr, cidadeStr, estadoStr);
      int count = v.size();
      if (count == 0)
         grid.clear();
      else
      {
         String[][] s = new String[count][];

         for (int i = 0; i < count; i++)
         {
            Cliente c = (Cliente)v.items[i];
            s[i] = new String[] { c.nome, c.cidade, c.estado };
         }
         grid.setItems(s);               
      }
   }

   private Cliente buscarClienteSelecionado()
   {
      int idx = grid.getSelectedLine();
      if (idx < 0) return null;
      String []s = grid.getItem(idx);
      return ClienteBD.instance.buscaPeloNome(s[0]);
   }

   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target == btnVoltar)
               ForcaDeVendas.swapTo(ForcaDeVendas.CLIENTESMENU);
            else
            if (e.target == btnBuscar)
               atualiza();
            else
            if (e.target == btnRemover)
            {
               Cliente cli = buscarClienteSelecionado();
               if (cli != null) 
               {
                  if (!PedidoBD.instance.procuraPorCliente(cli))
                  {
                     ClienteBD.instance.remover(cli);
                     atualiza();
                  }
                  else
                     new MessageBox("Erro", "Há pedidos que dependem desse cliente. Se você quiser realmente remove-lô, apague primeiramente os pedidos.").popupModal();
               }
            }
            else
            if (e.target == btnExibir)
            {
               Cliente cli = buscarClienteSelecionado();
               if (cli != null) 
               {
                  NovoCliente.enviaCliente = cli;
                  ForcaDeVendas.swapTo(ForcaDeVendas.NOVOCLIENTE);
               }
            }
            break;
      }
   }
   
   public void onAdd()
   {
      atualiza();
   }
}
