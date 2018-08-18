/* SWForçaDeVendas,
 * por Celso [at] superwaba [dot] com [dot] br
 * <Celso Marcelo da Silva>
 * 
 * This is an example application coded using SuperWaba
 * its meant as a learning source  for  other SW user´s
 * on how to use and build a  real-life solution/appli-
 * cation using SuperWaba.
 * 
 * -- Everything is well-divided:
 *    - db, holds all record information regarding the PDBDriver, 
 *      and information of what is an Order, a Customer,
 *      or Product.
 *    - ui, as the name suggests, holds all user interfaces
 *      for all the windows you might see on this application.
 *      
 * thanks to: Guich and Victor for his programming skills, advices, and changes
 *            on this code, and Renato Ribeiro for usefull brainstormings
 *            and good advices on the user-interface 
 */
package vendas;

import waba.ui.*;
import waba.fx.*;
import waba.sys.*;

public class ForcaDeVendas extends MainWindow
{    
   public static Color defaultBackColor = new Color(0xd9d9ff);
   public static Color defaultForeColor = new Color(0x000099);
   public static String [] tipo = {"","Normal","Bonificação"};
   public static String [] regiao = {"","Região-01","Região-02"};
   public static String [] condfrete = {"","01-cif","02-fob"};
   public static String [] estados = {"","AC","AL","AP","AM","BA","CE","ES","GO","MA","MT","MS","MG","PA","PB","PR","PE","PI","RJ","RN","RS","RO","RR","SC","SP","SE","TO"};
   public static String [] condpag = {"","28ddl","30 dias","45 dias","Avista"};
   public static String [] canal = {"","Atacado","Varejo","Exportação","Rede"};
   public static String [] regiaogeo = {"","Litoral","Interior","Grande SP","Grande Rio"};
   public static String [] ramo = {"","Micro-Empresa","Supermercado","Rede","Atacadista"};
   public static String [] checkout = {"","Sem Checkout","01-Checkout","02-Checkout","03-Checkout"};
   public static final int MAX = 100; //size the array in month, day and period  
   private MenuBar mbar;
   
   /* constants for the swap() method 
    * each one assings a window to be swapped to
    */
   public static final int MENUPRINCIPAL = 0;

   public static final int CLIENTESMENU = 1;
   public static final int PEDIDOSMENU = 2;
   public static final int RELATORIOMENU = 3;
   
   public static final int NOVOPRODUTO = 4;
   public static final int PROCURAPRODUTOS = 5;

   public static final int NOVOCLIENTE = 6;
   public static final int PROCURACLIENTES = 7;

   public static final int NOVOPEDIDO = 8;
   public static final int PROCURAPEDIDOS = 9;
   
   public static final int SUMARIODEVENDASMENU = 10;
   public static final int VENDASPORPRODUTOMENU = 11;
   
   public static final int DIARIO = 12;
   public static final int MENSAL = 13;
   public static final int ANUAL = 14;
   public static final int PORPERIODO = 15;
   
   public static final int DIARIO2 = 16;
   public static final int MENSAL2 = 17;
   public static final int ANUAL2 = 18;
   public static final int PORPERIODO2 = 19;

   public static final int DUMMY = -1;
   
   private static String[] classNames =
   {
         "vendas.ui.MenuPrincipal",
         "vendas.ui.cliente.ClientesMenu",
         "vendas.ui.pedido.PedidosMenu",
         "vendas.ui.relatorios.RelatorioMenu",
         "vendas.ui.produto.NovoProduto",
         "vendas.ui.produto.ProcuraProdutos",
         "vendas.ui.cliente.NovoCliente",
         "vendas.ui.cliente.ProcuraClientes",
         "vendas.ui.pedido.NovoPedido",
         "vendas.ui.pedido.ProcuraPedidos",
         "vendas.ui.relatorios.sumariodevendas.SumarioDeVendasMenu",
         "vendas.ui.relatorios.vendasporproduto.VendasPorProdutoMenu",
         "vendas.ui.relatorios.sumariodevendas.Diario",
         "vendas.ui.relatorios.sumariodevendas.Mensal",
         "vendas.ui.relatorios.sumariodevendas.Anual",
         "vendas.ui.relatorios.sumariodevendas.PorPeriodo",
         "vendas.ui.relatorios.vendasporproduto.Diario2",
         "vendas.ui.relatorios.vendasporproduto.Mensal2",
         "vendas.ui.relatorios.vendasporproduto.Anual2",
         "vendas.ui.relatorios.vendasporproduto.PorPeriodo2"
   };
   
   /** The instantiated classes */
   private static Container[] screens; 
   
   public ForcaDeVendas()
   {
      super("SW Força de Vendas", TAB_ONLY_BORDER);
      Settings.setUIStyle(waba.sys.Settings.Flat);
      
      
      setBackColor( Color.WHITE );
      Color.defaultBackColor = Color.WHITE;
      
      // Instantiate the screens
      screens = new Container[classNames.length];
   }
   
   public void onStart() 
   {    
      String col0[] = 
      {
            "Arquivo",
            "Sobre",
      };
      
      setMenuBar(mbar = new MenuBar(new String[][]{col0}));
      swapTo(MENUPRINCIPAL);
   }   
   
   /**
    * This method swap windows around, i.e., this is how we
    * jump from one window to another.
    * 
    * @param x - A constant which assigns the window to swap to
    */
   public static void swapTo(int x)
   {
      if (x != DUMMY) 
      {
         if (screens[x] == null)
            try
            {
               screens[x] = (Container)Class.forName(classNames[x]).newInstance();
            }
            catch (Exception e)
            {
               throw new RuntimeException("Classe "+classNames[x]+" não pode ser instanciado!");
            }
           
         MainWindow.getMainWindow().swap(screens[x]);
      }
   }
   
   public void onEvent( Event e ) 
   {
      switch( e.type ) 
      {
       case ControlEvent.WINDOW_CLOSED:
         if( e.target == mbar )
            switch( mbar.getSelectedMenuItem() )
            {
               case 1: 
               popupModal(new MessageBox("Sobre","SW Força de Vendas|Example program for the|SuperWaba SDK. This software|is freeware. Help support|the open source projects.|Created by Celso Marcelo da Silva|www.superwaba.com.br"));
               break;
            }
      }
   }
}
