/* ProdutoDB.java All Catalog operations related to the Products.pdb file, product
 * insertion, product updating, searching for a product inside the pdb, etc... 
 */
package vendas.bd;

import waba.sys.Settings;
import waba.sys.Vm;
import waba.util.Vector;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class ProdutoBD
{
   public static ProdutoBD instance = new ProdutoBD();
   private PDBDriver pdb;
   int rowId;
   
   /**
    * Constructor which creates a table and intance to the PDBdriver 
    */
   private ProdutoBD()
   {
      if (!Settings.onDevice)//avert warnings
         waba.applet.JavaBridge.showMsgs = false;
      pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
    }

   public void write(Produto p, boolean update)
   {
      double pesobruto = p.pesobruto;
      String ean = p.ean;
      String dun14 = p.dun14;
      String codigo = p.codigo;
      String descr = p.descr;
      String data = p.data;
      String familia = p.familia;
      double precoUnit = p.precoUnit;
      double ipi = p.ipi;
      //string: '"+string+"'   integer: "+integer+"
      try
      {
        if(update)
           pdb.executeUpdate("update produto set pesobruto="+pesobruto+
                 ", ean='"+ean+"', dun14='"+dun14+"', codigo='"+codigo+"', descr='"+descr+"', data='"+data+"', familia='"+familia+"', precoUnit="+precoUnit+", ipi="+ipi+" where codigo='"+codigo+"'");
        else
           pdb.executeUpdate("insert into produto values ("+pesobruto+", '"+ean+"', '"+dun14+"', '"+codigo+"','"+descr+"','"+data+"','"+familia+"',"+precoUnit+","+ipi+")");
      } 
      catch (Throwable t)
      {
         Vm.debug(t.getMessage());
      }
      }

   /**
    * Reads the name of all products available on the pdb file
    * 
    * @return A vector holding all products names available
    */
   public String[] lerTodosOsCodigos()//antes era "lerTodosOsNomes"
   {
      String produto = "produto";
      ResultSet rs = pdb.executeQuery("select codigo from produto");
      int n = pdb.getRowCount(produto);
      String []ss = new String[n];
      int i = 0;
      while(rs.next())
            ss[i++]=rs.getString("codigo");
      return ss;
   }
  
   /**
    * Remove a specific product from the pdb file. 
    * 
    * @param pro
    *           The product to be removed
    */
   
   /*public void remove(Produto pro)
   {
      String pcod=pro.codigo;
     
      pdb.executeUpdate("delete produto where codigo='"+pcod+"'");
   }

   
   /**
    * Retrieves a Product information based on the product code.
    * 
    * @param prodcode
    *           The product code
    * @return The product if found or null if none is found
    */
   public Produto buscarPeloCodigo(String prodcod) // info
   {
      ResultSet rs = pdb.executeQuery("select * from produto where codigo='"+prodcod+"'");
      Produto p = null;
      if(rs.first())
       {
          p = new Produto();
          p.pesobruto = rs.getDouble("pesobruto");
          p.ean = rs.getString("ean");
          p.dun14 = rs.getString("dun14");
          p.codigo = rs.getString("codigo");
          p.descr = rs.getString("descr");
          p.data = rs.getString("data");
          p.familia = rs.getString("familia");
          p.precoUnit = rs.getDouble("precoUnit");
          p.ipi = rs.getDouble("ipi");
       }
      return p;
   }
     

   /**
    * Returns a Product matching it against a product name
    * 
    * @param prodname
    *           The product name
    * @return the product if found or null if the product was not found
    */
   public Produto buscarPeloNome(String prodnome) // infoName
   {
      ResultSet rs = pdb.executeQuery("select * from produto where name='"+prodnome+"'");
         Produto p = null;
         if(rs.first())
          {
             p = new Produto();
             p.pesobruto = rs.getDouble("pesobruto");
             p.ean = rs.getString("ean");
             p.dun14 = rs.getString("dun14");
             p.codigo = rs.getString("codigo");
             p.descr = rs.getString("descr");
             p.data = rs.getString("data");
             p.familia = rs.getString("familia");
             p.precoUnit = rs.getDouble("precoUnit");
             p.ipi = rs.getDouble("ipi");
          }
         return p;
   }
   
  
   /**
    * Search for a product inside the pdb file matching the searching fields
    * 
    * @param Nname
    *           Name of the product
    * @param Ccode
    *           Code of the product
    * @return A vector holding the matched products, if there is no matching product the
    *         vector element count is zero.
    */
   
   
   
   public Vector procuraProdutos(String ccod, String ddescr)
   {
      Vector v = new Vector();
      ResultSet rs = pdb.executeQuery("select * from produto where codigo='"+ccod+"' and descr='"+ddescr+"'");
      while(rs.next())
      {   
         Produto pro = new Produto();
         pro.pesobruto = rs.getDouble("pesobruto");
         pro.ean = rs.getString("ean");
         pro.dun14 = rs.getString("dun14");
         pro.codigo = rs.getString("codigo");
         pro.descr = rs.getString("descr");
         pro.data = rs.getString("data");
         pro.familia = rs.getString("familia");
         pro.precoUnit = rs.getDouble("precoUnit");
         pro.ipi = rs.getDouble("ipi");
         v.add(pro);
      }
      return v;
   }
      
   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table produto(pesobruto double, ean char (20), dun14 char (20), codigo char(20), descr char(30), data char (15), familia char (20), precoUnit double, ipi double)");
         pdb.execute("CREATE INDEX IDX_0 ON produto(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON produto(descr)");
         pdb.execute("CREATE INDEX IDX_2 ON produto(codigo)");
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }

}

