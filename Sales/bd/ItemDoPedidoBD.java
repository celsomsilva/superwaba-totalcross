package vendas.bd;

/*
 * ItemDoPedidoBD.java All PDBDriver operations related to the ItemOrders.pdb file, items
 * insertion, items updating, searching for an item inside the pdb, etc... 
 */

import waba.sys.Settings;
import waba.sys.Vm;
import waba.util.Vector;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class ItemDoPedidoBD
{
   private PDBDriver pdb;
   int rowId;
   public static ItemDoPedidoBD instance = new ItemDoPedidoBD();
   
   /**
    * Constructor which creates a table and intance to the PDBdriver 
    */
   private ItemDoPedidoBD()
   {
      if (!Settings.onDevice)//evita warnings
         waba.applet.JavaBridge.showMsgs = false;
     pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
   }
      

   /**
    * Writes a record ( ItemOrders ) to the pdb file
    * 
    * @param ip
    *           The Item related to the Order
    */
   public void escrever(ItemDoPedido ip, boolean atualiza)
   {    
         int codigopedido = ip.codigopedido;
         String codigoproduto = ip.codigoproduto;
         String descr = ip.descr;
         int quant = ip.quant;
         double ipi = ip.ipi;
         double precoUnit = ip.precoUnit;
         double precoTotal = ip.precoTotal;
         //string: '"+string+"'   integer: "+integer+"
         try
         {
           if(atualiza)
              pdb.executeUpdate("update itemdopedido set codigopedido="+codigopedido+
                    ", codigoproduto='"+codigoproduto+"', descr='"+descr+"', quant="+quant+", ipi="+ipi+", precoUnit="+precoUnit+", precoTotal="+precoTotal+" where codigoproduto='"+codigoproduto+"'");
           else
              pdb.executeUpdate("insert into itemdopedido values ("+codigopedido+",'"+codigoproduto+"','"+descr+"',"+quant+","+ipi+","+precoUnit+","+precoTotal+")");
         } 
         catch (Throwable t)
         {
            Vm.debug(t.getMessage());
         }
   }
      

   /**
    * Search for a specific order/product and remove it. 
    * 
    * @param codigopedido
    *           ID of the order
    * @param prodcode
    *           Code of the product
    */
   public void removeItem(int codigopedido, String prodcodigo)
   {
      String pcodigo = prodcodigo;
      int codord = codigopedido;
      
      pdb.executeUpdate("delete itemdopedido where codigoproduto='"+pcodigo+"' and codigopedigo="+codord+"");  
   }
      
   
   /*{
      for (int n = this.getRecordCount() - 1; n >= 0; n--)
         if (this.setRecordPos(n))
         {
            ItemOrder i = read();
            if (i.idorder == idorder && prodcode.equals(i.idproduct))
            {
               this.deleteRecord();
               return;
            }
         }
   }

   
   /**
    * Read all items order related to a specific order id
    * 
    * @param idorder
    *           The order id
    * @return A vector containing all items related to that order or null if none is found
    */
   public Vector lerTudo(int codigopedido)
   {
      Vector v = new Vector();
      ResultSet rs = pdb.executeQuery("select * from itemdopedido where codigopedido="+codigopedido+"");
      while(rs.next())
         {   
            ItemDoPedido io = new ItemDoPedido();
            io.codigopedido = rs.getInt("codigopedido");
            io.codigoproduto = rs.getString("codigoproduto");
            io.descr = rs.getString("descr");
            io.quant = rs.getInt("quant");
            io.ipi = rs.getDouble("ipi");
            io.precoUnit = rs.getDouble("precoUnit");
            io.precoTotal = rs.getDouble("precoTotal");
            v.add(io);
         }
      return v;
   }
   

   /**
    * Check if we have an order which list a specific product as it related item. This is
    * usefull when we want to delete a product but its already related to a order (
    * consistency )
    * 
    * @param p
    *           The Product
    * @return true if there is at least one item that is that product or false if there is
    *         none
    */
   public boolean pedidosPorProduto(Produto p)
   {
      String codigo = p.codigo;
      ResultSet rs = pdb.executeQuery("select codigoproduto from itemdopedido where codigoproduto='"+codigo+"'");
      if(rs.first())
          return true;
      return false;
   }
   
   
   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table itemdopedido(codigopedido int, codigoproduto char(20), descr char (30), quant int, ipi double, precoUnit double, precoTotal double)");
         pdb.execute("CREATE INDEX IDX_0 ON itemdopedido(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON itemdopedido(codigopedido)");
         pdb.execute("CREATE INDEX IDX_2 ON itemdopedido(codigoproduto)");
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }
}