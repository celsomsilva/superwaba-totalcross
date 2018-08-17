package vendas.ui.pedido;

import vendas.*;
import vendas.bd.*;
import waba.fx.*;
import waba.sys.*;
import waba.ui.*;
import waba.util.*;



public class PedidoGeral extends Container
{
   public static boolean atualiza = false;
   public static Pedido pedidoAtual;
   Vector vprod, vdelet;
   IntVector vquant;
   Color clrBack, clrFore;
   Edit edDate, edNum, edTex, edCondpagamento, edAgente, edDtentrega, edObs;
   Label lPedido;
   Button btnBack, btnClear, btnInsert;
   ComboBox cbCliente, combTipo;

   public void onStart()
   {
      edDate = new Edit("xx/xx/xxxx");
      add(edDate, RIGHT, TOP + 1);
      edDate.setMode(Edit.DATE);
      edDate.setText(new Date().toString());
      add(new Label("Data "), BEFORE, SAME);

      if( pedidoAtual == null )
      {
         pedidoAtual = new Pedido();
         pedidoAtual.codigo = PedidoBD.instance.newid;
      }

      add(lPedido = new Label("Pedido no. "), LEFT + 2, SAME);

      edNum = new Edit("123");
      add(edNum, AFTER, SAME);
      edNum.setText("" + pedidoAtual.codigo);
      edNum.setEditable(false);
           
      //Combclientecod = new ComboBox();  Falta esta combo 
      Label l1 = new Label("Cond.pagam:  ");
      int xx = l1.getPreferredWidth() + 2;
      
      cbCliente = new ComboBox();
      add(new Label("Cliente: "), LEFT + 2, AFTER + 1);
      add(cbCliente);
      cbCliente.setRect(xx, SAME, PREFERRED, PREFERRED );

      add(new Label("Tipo: "),LEFT,AFTER + 2);
      combTipo = new ComboBox(ForcaDeVendas.tipo);
      add(combTipo); 
      combTipo.setRect( xx, SAME, PREFERRED, PREFERRED );
      
      add(l1,LEFT,AFTER + 2);
      edCondpagamento = new Edit("");
      add(edCondpagamento);
      edCondpagamento.setRect( xx, SAME, FILL, PREFERRED );      
      
      add(new Label("Agente: "),LEFT,AFTER + 2);
      edAgente = new Edit("");
      add(edAgente);
      edAgente.setRect( xx, SAME, FILL, PREFERRED );
      
      add(new Label("Dt.Entrega: "),LEFT,AFTER + 2);
      edDtentrega = new Edit("");
      add(edDtentrega);
      edDtentrega.setRect( xx, SAME, FILL, PREFERRED );
      
      add(new Label("Obs: "),LEFT,AFTER + 2);
      edObs = new Edit("");
      add(edObs);
      edObs.setRect( xx, SAME, FILL, PREFERRED );

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

      vprod = new Vector();
      vquant = new IntVector();
      vdelet = new Vector();
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         btnBack.setBackColor(clrBack);
         btnBack.setForeColor(clrFore);
         btnClear.setBackColor(clrBack);
         btnClear.setForeColor(clrFore);         
         btnInsert.setBackColor(clrBack);
         btnInsert.setForeColor(clrFore);
         edNum.setBackColor(Color.DARK);
      }
      
      Button.commonGap = 0;
      upDate( atualiza );
   }

   public void upDate(boolean fillGrid)
   {
      cbCliente.removeAll();
      String []names = ClienteBD.instance.lerTodosOsNomes();
      if (names.length == 0)
         new MessageBox("Erro", "Não há clientes registrados").popupModal();
      else
      {
         cbCliente.add(names);

         NovoPedido.nv.cbProduto.removeAll();
         names = ProdutoBD.instance.lerTodosOsCodigos();
         int n = names.length;
         if (n == 0)
            new MessageBox("Erro", "Não há produtos registrados").popupModal();
         else
         {
            NovoPedido.nv.cbProduto.add(names);
            
            if (pedidoAtual != null && atualiza ) 
            {
               Vector vec = ItemDoPedidoBD.instance.lerTudo(pedidoAtual.codigo);
               if (vec == null) return;
               int l = vec.getCount();
               for (int i = 0; i < l; i++)
               {
                  ItemDoPedido ip = (ItemDoPedido) vec.items[i];
                  Produto p = ProdutoBD.instance.buscarPeloCodigo(ip.codigoproduto);
                  
                  if(NovoPedido.nv.ckComIpi.getChecked())
                      p.precoUnit = Convert.toDouble(NovoPedido.nv.edPreco.getText());
                  else
                  {   
                     Vector var = ParametrosBD.instance.buscaPeloCodigo(NovoPedido.ng.edAgente.getText());
                     int j = var.getCount();
                     
                     for(;j>0;j--)
                     {
                        Parametros par = (Parametros) var.items[j];
                        TabelaDePreco tab = TabelaDePrecoBD.instance.buscaPeloCodigo(par.codtabela);
                        if(p.codigo.equals(tab.codproduto))
                        {
                           p.precoUnit = tab.precoUnit;
                           break;
                        }
                     }
                   }
                 
                  if (p != null)
                  {
                     vprod.add(p);
                     NovoPedido.nv.vprod.add(p);
                     vquant.add(ip.quant);
                     NovoPedido.nv.vquant.add(ip.quant);
                  }
               }
         
               cbCliente.select(pedidoAtual.cliente);
               btnInsert.setText("Atualizar");
               NovoPedido.nv.btnInsert.setText("Atualizar");
               btnBack.setText("Voltar");
               NovoPedido.nv.btnBack.setText("Voltar");
               NovoPedido.nv.edTot.setText(Convert.toString(totalValue(), 2));
               NovoPedido.nv.edDescontoesp.setText(Convert.toString(desconto(), 2));
               NovoPedido.nv.edPesototal.setText(Convert.toString(peso(), 2));
               edNum.setText("" + pedidoAtual.codigo);
            }
            
            if (fillGrid)
            {
               int count = vprod.getCount( );
               if (count == 0)
                  ItemsDoPedido.grid.clear();
               else
                {
                  String[][] s = new String[count][];
      
                  for (int i = 0; i < count; i++)
                  {
                     Produto p = (Produto) vprod.items[i];
                     s[i] = new String[] 
                     {
                           p.codigo,
                           p.descr,
                           Convert.toString(vquant.items[i]),
                           Convert.toString(p.precoUnit),
                           Convert.toString(p.ipi),
                           Convert.toString(p.precoUnit * (double) vquant.items[i], 2)
                     };
                  }
                  ItemsDoPedido.grid.setItems(s);               
               }
            }
         }
      }
   }

   protected void fillGrid( )
   {
      int count = vprod.getCount( );
      if( vprod.getCount( ) <= 0 )
         return;
      
      String[][] s = new String[count][];
      
      for (int i = 0; i < count; i++)
      {
         Produto p = (Produto) vprod.items[i];
         s[i] = new String[] 
         {
               p.codigo,
               p.descr,
               Convert.toString(vquant.items[i]),
               Convert.toString(p.precoUnit),
               Convert.toString(p.ipi),
               Convert.toString(p.precoUnit * (double) vquant.items[i], 2) 
         };
      }
      ItemsDoPedido.grid.setItems(s);              
   }
   
   /**
    * Calculates the total amount of the order based on the products you selected and 
    * their quantity.
    * 
    * @return The total amount
    */
   public double totalValue()
   {
      double total = 0;
      if (vprod == null || vquant == null) return 0;  

      if (vprod.getCount() == 0 || vquant.getCount() == 0) return 0;

      for (int n = vprod.getCount() - 1; n >= 0; n--)
      {
         Produto p = (Produto) vprod.items[n];
         total += ((p.precoUnit * (double) vquant.items[n]) + ((p.precoUnit * (double) vquant.items[n])* p.ipi));
      }

      return total;
      
   }
   
   public double mercadoria()
   {
      
      double mercadoria = 0;
      if (vprod == null || vquant == null) return 0;  

      if (vprod.getCount() == 0 || vquant.getCount() == 0) return 0;

      int n = vprod.getCount() - 1;
      Produto p = (Produto) vprod.items[n];
      mercadoria = (p.precoUnit * (double) vquant.items[n]);

      return mercadoria;
      
   }
   
   public double desconto()
   {
      double desconto = 0;
      
      return desconto;
   }

   public double peso()
   {
      double peso = 0;
      if (vprod == null || vquant == null) return 0;
      
      if (vprod.getCount() == 0 || vquant.getCount() == 0) return 0;
      
      for (int n = vprod.getCount() - 1; n >= 0; n--)
      {
         Produto p = (Produto) vprod.items[n];
         peso += (p.pesobruto * (double) vquant.items[n]);
      }
      
      return peso;
   }
   
   /**
    * Clean this window, clear the products and quantity vectors, rewinds the grid, re-set
    * the date, select index 0 of both client and product combobox, and re-set the current
    * order number.
    * 
    * @return: void
    */
   public void clean()
   {
      vprod.clear();
      NovoPedido.nv.vprod.clear();
      vquant.clear();
      NovoPedido.nv.vquant.clear();
      ItemsDoPedido.grid.clear();
      edDate.setText(new Date().toString());
      combTipo.select(0);
      edCondpagamento.setText("");
      edAgente.setText("");
      edDtentrega.setText("");
      edObs.setText("");
      cbCliente.select(0);
      NovoPedido.nv.cbProduto.select(0);
      NovoPedido.nv.edQuant.setText("1");
      NovoPedido.nv.ckComIpi.setChecked(false);
      NovoPedido.nv.edPreco.setText("");
      NovoPedido.nv.edDescontoesp.setText("0");
      NovoPedido.nv.edPesototal.setText("0");
      NovoPedido.nv.edMercadoria.setText("0");
      NovoPedido.nv.edTot.setText("0.00");
      NovoPedido.nv.sb1.setValue(1);
   }
   
   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            
            if(e.target == cbCliente)
            {
               String nome = (String)cbCliente.getSelectedItem();
               Cliente c = ClienteBD.instance.buscaPeloNome(nome);
               edCondpagamento.setText(c.condpag);
            }
            else
               if (e.target == btnClear) /* apaga a tela */
                  clean();
               else
               if (e.target == btnBack) /* vai para a janela anterior */
               {
                  vdelet.clear( );
                  NovoPedido.nv.vdelet.clear( );                  
                  clean( );
                  
                  if( atualiza )
                     ForcaDeVendas.swapTo(ForcaDeVendas.PROCURAPEDIDOS );
                  else
                     ForcaDeVendas.swapTo(ForcaDeVendas.PEDIDOSMENU);
                  
                  if ( atualiza ) 
                     {
                     atualiza = false;
                     NovoPedido.nv.atualiza = false;
                     }
               }
               else
                  /*
                   * insere um pedido. Atualmente ele cria um pedido e chama o 
                   * método bd necessário para escrever um pedido para um arquivo
                   */
                  
               if (e.target == btnInsert)
               {
                  int n = vprod.getCount() - 1;
                  for (; n >= 0; n--)
                  {
                     ItemDoPedido ip = new ItemDoPedido();
                     Produto p = (Produto) vprod.items[n];
            
                     ip.codigopedido = pedidoAtual.codigo;
                     ip.codigoproduto = p.codigo;
                     ip.descr = p.descr;
                     ip.quant = vquant.items[n];
                     ip.precoUnit = p.precoUnit;
                     ip.precoTotal = (ip.quant * ip.precoUnit);
                     ip.ipi = p.ipi;
                     ItemDoPedidoBD.instance.escrever(ip, atualiza);
                  }
            
                  if ( atualiza )
                  {
                     for (n = vdelet.getCount() - 1; n >= 0; n--)
                     {
                        Produto p = (Produto) vdelet.items[n];
                        ItemDoPedidoBD.instance.removeItem(pedidoAtual.codigo, p.codigo);
                     }
                  }
                  
                  String name = (String) cbCliente.getSelectedItem();
                  if (name.length() != 0)
                  {
                     Cliente c = (Cliente) ClienteBD.instance.buscaPeloNome(name);
                     if (c == null) return;
            
                     pedidoAtual.data = new Date(edDate.getText());
                     pedidoAtual.dia = pedidoAtual.data.getDay();
                     pedidoAtual.mes = pedidoAtual.data.getMonth();
                     pedidoAtual.ano = pedidoAtual.data.getYear();
                     pedidoAtual.clientecod = c.cnpjcpf;
                     pedidoAtual.cliente = c.nome;
                     pedidoAtual.tipo = (String)combTipo.getSelectedItem();
                     pedidoAtual.condpagamento = edCondpagamento.getText();
                     pedidoAtual.agente = edAgente.getText();
                     pedidoAtual.dtentrega = edDtentrega.getText();
                     pedidoAtual.obs = edObs.getText();
                     pedidoAtual.precoTotal = totalValue();
                     pedidoAtual.descontoesp = desconto();
                     pedidoAtual.pesoTotal = peso();
                     
                     PedidoBD.instance.escrever(pedidoAtual, atualiza );
            
                     if (atualiza)
                        new MessageBox("Mensagem", "Pedido atualizado").popupModal();
                     else
                        new MessageBox("Mensagem", "Pedido inserido").popupModal();
            
                     pedidoAtual = new Pedido();
                     pedidoAtual.codigo = ++PedidoBD.instance.newid;
                     edNum.setText(Convert.toString(PedidoBD.instance.newid));
            
                     clean();
                     if (atualiza)
                     {
                        atualiza = false;
                        NovoPedido.nv.atualiza = false;
                        ForcaDeVendas.swapTo(ForcaDeVendas.PROCURAPEDIDOS);
                     }
                  }
               }

         case ControlEvent.FOCUS_OUT:
            /* corrige os dados inseridos para o formato Data */
            if (e.target == edDate)
               edDate.setText(new Date(edDate.getText()).toString());
            break;
      }
   }
   public void onAdd()
   {
      upDate(atualiza);
   }
}
