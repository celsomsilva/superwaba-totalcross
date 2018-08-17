package vendas.ui.pedido;

import vendas.ForcaDeVendas;
import vendas.bd.*;
import waba.fx.*;
import waba.ui.*;
import waba.util.*;
import waba.sys.*;

public class ItemsDoPedido extends Container
{
   public static Grid grid;
   Edit edTot,edQuant,edPreco,edDescontoesp,edPesototal,edMercadoria;
   ComboBox cbProduto;
   ScrollBar sb1;
   Button btnAdd, btnRemove;
   Check ckComIpi;
   Vector  vprod, vdelet;
   IntVector vquant;
   Button btnBack, btnClear, btnInsert;
   Label l1;
   public static boolean atualiza = false;
   
   public ItemsDoPedido()
   {
      cbProduto = new ComboBox();
   }
     
   public void onStart()
   {
      String[] gridCaptions = { "Cod", "Descrição", "Qtd", "Preço", "IPI(%)", "Total" };
      int[] gridWidths = { fm.getTextWidth("xxxxx"),
            fm.getTextWidth("xxxxxxxxxxx"), fm.getTextWidth("123"),
            fm.getTextWidth("xxxxxxx"), 
            fm.getTextWidth("xxxx"), 
            fm.getTextWidth("xxxxxxx"),};
      int[] gridAligns = { CENTER, LEFT, CENTER, RIGHT, RIGHT, RIGHT };
      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
 
      add(new Label("Produto: "), LEFT + 2, TOP + 2);
      add(cbProduto,AFTER,SAME);
      
      edQuant = new Edit("123");      
      add(new Label("Quant: "), AFTER + 2, SAME);
      add(edQuant);
      edQuant.setRect(AFTER, SAME, PREFERRED,PREFERRED);
      edQuant.setText("1");
      
      /* hack to lower scrollbar button's size */
      ScrollBar.extraArrowSize = -1;
      sb1 = new ScrollBar(ScrollBar.VERTICAL);
      sb1.setLiveScrolling(true);
      add(sb1);
      sb1.setRect(AFTER, SAME, PREFERRED, edQuant.getPreferredHeight());
      sb1.setMinimum(1);
      sb1.setVisibleItems(0);
      ScrollBar.extraArrowSize = 0;

      Button.commonGap = 1;

      btnAdd = new Button("+");
      add(btnAdd, AFTER + 3, SAME);

      btnRemove = new Button(" -");
      add(btnRemove, AFTER + 1, SAME); 
      
      ckComIpi = new Check("");
      add(ckComIpi, LEFT + 2, AFTER + 2);
      
      add(l1 = new Label("Preço Tabelado"), AFTER + 2, SAME);
      
      edPreco = new Edit("");
      add(edPreco, AFTER + 2, SAME);      
      
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setRect(LEFT + 2, AFTER + 2, FILL, Settings.screenHeight/3);
      grid.setBackColor(Color.WHITE); 
      
      edMercadoria = new Edit("1234567");
      add(new Label("Mercad.:"), LEFT + 2, AFTER );
      add(edMercadoria, AFTER + 2 , SAME);
      edMercadoria.setText(Convert.toString(NovoPedido.ng.mercadoria(), 2));
      edMercadoria.setEditable(false);

      edDescontoesp = new Edit("1234567");
      add(new Label("Desc.Esp.:"), AFTER + 2, SAME);
      add(edDescontoesp, AFTER + 2 , SAME );
      edDescontoesp.setText(Convert.toString(NovoPedido.ng.desconto(), 2));
      edDescontoesp.setEditable(false);
      
      add(new Label("Peso Tot(kg):"), LEFT + 2, AFTER + 3);
      edPesototal = new Edit("125456");
      add(edPesototal, AFTER + 2, SAME);
      edPesototal.setText(Convert.toString(NovoPedido.ng.peso(), 2));
      edPesototal.setEditable(false);
      
      edTot = new Edit("123456");
      add(edTot, RIGHT - 2, SAME);
      add(new Label("Total: "),BEFORE, SAME);
      edTot.setText(Convert.toString(NovoPedido.ng.totalValue(), 2));
      edTot.setEditable(false); 
      
      if( atualiza )
         btnInsert = new Button("Atualizar");
      else
         btnInsert = new Button("Inserir");
      
      add(btnInsert, RIGHT - 1, BOTTOM - 1);

      if( atualiza )
         btnBack = new Button("Voltar");
      else
         btnBack = new Button("Cancelar");
      add(btnBack, BEFORE - 2, BOTTOM - 1, btnInsert);

      btnClear = new Button("Apagar");
      add(btnClear, BEFORE - 2, BOTTOM - 1);
      Button.commonGap = 0;
      
      edPreco.setBackColor(Color.DARK);
      edPreco.setForeColor(Color.WHITE);
      edPreco.setEditable(false);
      l1.setForeColor(Color.DARK);
      
      vprod = new Vector();
      vquant = new IntVector();
      vdelet = new Vector();
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         edTot.setBackColor(Color.DARK);
         edDescontoesp.setBackColor(Color.DARK);
         edPesototal.setBackColor(Color.DARK);
         edMercadoria.setBackColor(Color.DARK);
         btnAdd.setBackColor(clrBack);
         btnAdd.setForeColor(clrFore);
         btnRemove.setBackColor(clrBack);
         btnRemove.setForeColor(clrFore);
         btnBack.setBackColor(clrBack);
         btnBack.setForeColor(clrFore);
         btnClear.setBackColor(clrBack);
         btnClear.setForeColor(clrFore);         
         btnInsert.setBackColor(clrBack);
         btnInsert.setForeColor(clrFore);
      }
   }
   
   
   public void onEvent(Event e)
   {
      if(e.type==ControlEvent.PRESSED&&e.target==ckComIpi)
      {
         if(ckComIpi.getChecked())
         {
            edPreco.setBackColor(Color.WHITE);
            edPreco.setForeColor(Color.BLACK);
            edPreco.setEditable(true);
            l1.setForeColor(Color.BLACK);
            repaint();
         }
         else
         {
            edPreco.setBackColor(Color.DARK);
            edPreco.setForeColor(Color.WHITE);
            edPreco.setEditable(false);
            l1.setForeColor(Color.DARK);
            repaint();
         }  
      }
   
      switch (e.type)
      {
         case ControlEvent.PRESSED:
      /* adiciona um produto no pedido */
      if (e.target == btnAdd)
      {
         if (edQuant.getLength() == 0) return;
         
         String codigo = (String) cbProduto.getSelectedItem();
         int n = vprod.indexOf(codigo);
         if (n>=0)
         {   
            vquant.items[n] = Convert.toInt(edQuant.getText());
            NovoPedido.ng.vquant.items[n] = Convert.toInt(edQuant.getText());
         }
         else
         {
            Produto p = ProdutoBD.instance.buscarPeloCodigo(codigo);
            if(ckComIpi.getChecked())
               p.precoUnit = Convert.toDouble(edPreco.getText());
            else
            {   
               Vector var = ParametrosBD.instance.buscaPeloCodigo(NovoPedido.ng.edAgente.getText());
               int i = var.getCount() - 1;
               
               for(;i>=0;i--)
               {
                  Parametros par = (Parametros) var.items[i];
                  TabelaDePreco tab = TabelaDePrecoBD.instance.buscaPeloCodigo(par.codtabela);
                  if(codigo.equals(tab.codproduto))
                  {
                     p.precoUnit = tab.precoUnit;
                     break;
                  }
               }
            }
            if ( p == null )
               return;
   
            vprod.add(p);
            NovoPedido.ng.vprod.add(p);
            vquant.add(Convert.toInt(edQuant.getText()));
            NovoPedido.ng.vquant.add(Convert.toInt(edQuant.getText()));
         }
         edDescontoesp.setText(Convert.toString(NovoPedido.ng.desconto(), 2));
         edTot.setText(Convert.toString(NovoPedido.ng.totalValue(), 2));
         edPesototal.setText(Convert.toString(NovoPedido.ng.peso(), 2));
         edMercadoria.setText(Convert.toString(NovoPedido.ng.mercadoria(), 2));
         NovoPedido.ng.fillGrid( );
      }
      
      else
      if (e.target == btnRemove) /* remove um produto no pedido */
      {
         int idx = grid.getSelectedLine();
         if (idx < 0) return;
         String []s = grid.getItem(idx);
         int n = vprod.indexOf(s[0]);
         if (n>=0)
         {
            vdelet.add(vprod.items[n]);
            vprod.del(n);
            vquant.del(n);
            NovoPedido.ng.vdelet.add(vprod.items[n]);
            NovoPedido.ng.vprod.del(n);
            NovoPedido.ng.vquant.del(n);
            edTot.setText(Convert.toString(NovoPedido.ng.totalValue(), 2));            
            edDescontoesp.setText(Convert.toString(NovoPedido.ng.desconto(), 2));
            edPesototal.setText(Convert.toString(NovoPedido.ng.peso(), 2));
            edMercadoria.setText(Convert.toString(NovoPedido.ng.mercadoria(), 2));
            grid.del(n);
         }
                  
      }
      else
      if (e.target == sb1) /* incrementa o valor no Edit edquant (quantidade de um produto)  */
         edQuant.setText(Convert.toString(sb1.getValue()));
      
      else
         if (e.target == btnClear) /* apaga a tela */
            NovoPedido.ng.clean();
         else
         if (e.target == btnBack) /* vai para a janela anterior */
         {
            vdelet.clear( );
            NovoPedido.nv.vdelet.clear( );                  
            NovoPedido.ng.clean( );
            
            if( atualiza )
               ForcaDeVendas.swapTo(ForcaDeVendas.PROCURAPEDIDOS );
            else
               ForcaDeVendas.swapTo(ForcaDeVendas.PEDIDOSMENU);
            
            if ( atualiza ) 
            {
               atualiza = false;
               NovoPedido.ng.atualiza = false;
            }
         }
         else
            /*
             * insere um pedido. Atualmente ele cria um pedido e chama o 
             * método bd necessário para escrever um pedido para um arquivo
             */
            
         if (e.target == btnInsert)//atenção: ao inserir, algumas funções só vão funcionar
            //se o Edit "Agente" tiver o código correto do vendedor
         {
            int n = vprod.getCount() - 1;
            for (; n >= 0; n--)
            {
               ItemDoPedido ip = new ItemDoPedido();
               Produto p = (Produto) vprod.items[n];

               ip.codigopedido = NovoPedido.ng.pedidoAtual.codigo;
               ip.codigoproduto = p.codigo;
               ip.descr = p.descr;
               ip.quant = vquant.items[n];
               ip.ipi = p.ipi;
               ip.precoUnit = p.precoUnit;
               ip.precoTotal = (ip.quant * ip.precoUnit);
               ItemDoPedidoBD.instance.escrever(ip, atualiza);
            }
      
            if ( atualiza )
            {
               for (n = vdelet.getCount() - 1; n >= 0; n--)
               {
                  Produto p = (Produto) vdelet.items[n];
                  ItemDoPedidoBD.instance.removeItem(NovoPedido.ng.pedidoAtual.codigo, p.codigo);
               }
            }
            
            String name = (String) NovoPedido.ng.cbCliente.getSelectedItem();
            if (name.length() != 0)
            {
               Cliente c = (Cliente) ClienteBD.instance.buscaPeloNome(name);
               if (c == null) return;
      
               NovoPedido.ng.pedidoAtual.data = new Date(NovoPedido.ng.edDate.getText());
               NovoPedido.ng.pedidoAtual.dia = NovoPedido.ng.pedidoAtual.data.getDay();
               NovoPedido.ng.pedidoAtual.mes = NovoPedido.ng.pedidoAtual.data.getMonth();
               NovoPedido.ng.pedidoAtual.ano = NovoPedido.ng.pedidoAtual.data.getYear();
               NovoPedido.ng.pedidoAtual.clientecod = c.cnpjcpf;
               NovoPedido.ng.pedidoAtual.cliente = c.nome;
               NovoPedido.ng.pedidoAtual.tipo = (String)NovoPedido.ng.combTipo.getSelectedItem();
               NovoPedido.ng.pedidoAtual.condpagamento = NovoPedido.ng.edCondpagamento.getText();
               NovoPedido.ng.pedidoAtual.agente = NovoPedido.ng.edAgente.getText();
               NovoPedido.ng.pedidoAtual.dtentrega = NovoPedido.ng.edDtentrega.getText();
               NovoPedido.ng.pedidoAtual.obs = NovoPedido.ng.edObs.getText();
               NovoPedido.ng.pedidoAtual.precoTotal = NovoPedido.ng.totalValue();
               NovoPedido.ng.pedidoAtual.descontoesp = NovoPedido.ng.desconto();
               NovoPedido.ng.pedidoAtual.pesoTotal = NovoPedido.ng.peso();
               
               PedidoBD.instance.escrever(NovoPedido.ng.pedidoAtual, atualiza );
      
               if (atualiza)
                  new MessageBox("Mensagem", "Pedido atualizado").popupModal();
               else
                  new MessageBox("Mensagem", "Pedido inserido").popupModal();
      
               NovoPedido.ng.pedidoAtual = new Pedido();
               NovoPedido.ng.pedidoAtual.codigo = ++PedidoBD.instance.newid;
               NovoPedido.ng.edNum.setText(Convert.toString(PedidoBD.instance.newid));
      
               NovoPedido.ng.clean();
               if (atualiza)
               {
                  NovoPedido.ng.atualiza = false;
                  atualiza = false;
                  ForcaDeVendas.swapTo(ForcaDeVendas.PROCURAPEDIDOS);
               }
            }
         }
      break;
      }
   }
}
