/*
 * ProcuraProdutos.java
 */
package vendas.ui.produto;

import vendas.bd.*;
import vendas.*;
import waba.ui.*;
import waba.fx.*;
import waba.sys.*;
import waba.util.*;

public class ProcuraProdutos extends Container
{
   private Button btnVoltar, btnBuscar, btnExibir;
   private Grid grid;
   private Edit edDesc, edCodigo;

   public void onStart()
   {
      Button.commonGap = 1;
      Label l1 = new Label("Cod: ");

      edDesc = new Edit("123456789012345");
      edCodigo = new Edit("123456789012345");
      Label l2 = new Label("Descrição: ");
      int xx = l2.getPreferredWidth( ) + 2;

      add(l1, LEFT + 2, TOP + 2);
      add(edCodigo, xx, SAME);
      
      add(l2, LEFT + 2, AFTER + 2);
      add(edDesc, xx, SAME);

      btnBuscar = new Button("Buscar");
      add(btnBuscar, AFTER + 2, SAME);

      btnVoltar = new Button("Voltar");
      add(btnVoltar, RIGHT - 1, BOTTOM - 1);

      btnExibir = new Button("Exibir");
      add(btnExibir, BEFORE - 2, SAME);

      String[] gridCaptions = { "Codigo", "Descrição", "Preço", "IPI", "Peso Bruto", "EAN" , "DUN_14"};
      int gridWidths[] = 
      { 
         fm.getTextWidth("x"), // codigo
         fm.getTextWidth("xxxxxxxxxxxxxxxxx"), //descr
         fm.getTextWidth("x"), //preço
         fm.getTextWidth("xxx"), // ipi
         fm.getTextWidth("x"),//pesobruto
         fm.getTextWidth("xxxxxxxxxxxxxxxxx"),//ean
         fm.getTextWidth("xxxxxxxxxxxxxxxxx"),//dun14
      };
      int gridAligns[] = { LEFT, LEFT, RIGHT, RIGHT, RIGHT, LEFT, LEFT};

      grid = new Grid(gridCaptions, gridWidths, gridAligns, false);
      add(grid);
      grid.verticalLineStyle = Grid.VERT_DOT;
      grid.setBackColor(Color.WHITE);
      grid.setRect(LEFT, AFTER + 5, FILL, FIT, btnBuscar);
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         btnBuscar.setBackColor(clrBack);
         btnBuscar.setForeColor(clrFore);
         btnVoltar.setBackColor(clrBack);
         btnVoltar.setForeColor(clrFore);
         btnExibir.setBackColor(clrBack);
         btnExibir.setForeColor(clrFore);
      }
      atualiza();
   }

   public void atualiza()
   {
      String descrStr = edDesc.getText();
      String codeStr = edCodigo.getText();
      Vector v = ProdutoBD.instance.procuraProdutos(descrStr, codeStr);
      int count = v.size();
      if (count == 0)
         grid.clear();
      else
      {
         String[][] s = new String[count][];

         for (int i = 0; i < count; i++)
         {
            Produto pro = (Produto)v.items[i];
            s[i] = new String[] 
            { 
               pro.codigo,
               pro.descr,
               Convert.toString(pro.precoUnit, 2), 
               Convert.toString(pro.ipi),
               Convert.toString(pro.pesobruto),
               pro.ean,
               pro.dun14,
            };
         }
         grid.setItems(s);               
      }
   }

   private Produto buscarProdutoSelecionado()
   {
      int idx = grid.getSelectedLine();
      if (idx < 0 ) return null;
      String []s = grid.getItem(idx);
      return ProdutoBD.instance.buscarPeloCodigo(s[1]);
   }

   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target == btnBuscar)
               atualiza();
            else
            if (e.target == btnVoltar)
               ForcaDeVendas.swapTo(ForcaDeVendas.MENUPRINCIPAL);
            else
            if (e.target == btnExibir)
            {
               /*COMO NÃO TEM INSERIR PRODUTOS, TABELAS E PARAMETROS, PARA TESTAR É USADO
                * O CÓDIGO COMENTADO ABAIXO:
                */
               /*Produto p = new Produto();  
               Produto p1 = new Produto();
               Produto p2 = new Produto();
               Parametros par1 = new Parametros();
               Parametros par2 = new Parametros();
               Parametros par3= new Parametros();
               Parametros par4 = new Parametros();
               TabelaDePreco tab1 = new TabelaDePreco();
               TabelaDePreco tab2 = new TabelaDePreco();
               TabelaDePreco tab3 = new TabelaDePreco();
               
               par1.nomedovendedor = "Celso";
               par1.codvendedor = "83232559";
               par1.codtabela = "567";
               
               par3.nomedovendedor = "Celso";
               par3.codvendedor = "83232559";
               par3.codtabela = "123";
               
               par4.nomedovendedor = "Celso";
               par4.codvendedor = "83232559";
               par4.codtabela = "890";
               
               par2.nomedovendedor = "Marcelo";
               par2.codvendedor = "3500483";
               par2.codtabela = "567";
               
               tab1.codtabela = "123";
               tab1.codproduto = "s";
               tab1.precoUnit = 2;
               
               tab2.codtabela = "567";
               tab2.codproduto = "a";
               tab2.precoUnit = 1;
               
               tab3.codtabela = "890";
               tab3.codproduto = "d";
               tab3.precoUnit = 3;
               
               p.pesobruto=2;
               p.ean="s";
               p.dun14="s";
               p.codigo="s";
               p.descr="s";
               p.data="s";
               p.familia="s";
               p.ipi=0.2;
               
               p1.pesobruto=1;
               p1.ean="a";
               p1.dun14="a";
               p1.codigo="a";
               p1.descr="a";
               p1.data="a";
               p1.familia="a";
               p1.ipi=0.1;
               
               p2.pesobruto=3;
               p2.ean="d";
               p2.dun14="d";
               p2.codigo="d";
               p2.descr="d";
               p2.data="d";
               p2.familia="d";
               p2.ipi=0.3;
               
               TabelaDePrecoBD.instance.write(tab1,false);
               TabelaDePrecoBD.instance.write(tab2,false);
               TabelaDePrecoBD.instance.write(tab3,false);
               ParametrosBD.instance.write(par1,false);
               ParametrosBD.instance.write(par2,false);
               ParametrosBD.instance.write(par3,false);
               ParametrosBD.instance.write(par4,false);
               ProdutoBD.instance.write(p,false);
               ProdutoBD.instance.write(p2,false);
               ProdutoBD.instance.write(p1,false);*/
               Produto p = buscarProdutoSelecionado();
               if (p != null)
               {
                  /*NovoProduto.enviaProduto = p;
                  ForcaDeVendas.swapTo(ForcaDeVendas.NOVOPRODUTO);*/
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