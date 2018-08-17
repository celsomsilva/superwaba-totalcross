/*ParametrosBD.java
 * 
 */
package vendas.bd;

import waba.sys.*;
import waba.util.*;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class ParametrosBD
{
   private PDBDriver pdb;
   int rowId;
   
   
   public static ParametrosBD instance = new ParametrosBD();

   /**
    *  Construtor que cria uma tabela e instancia para o PDBdriver 
    */
   private ParametrosBD()
   {
      if (!Settings.onDevice)//evita warnings
         waba.applet.JavaBridge.showMsgs = false;
      pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
   }
   
   
   public void write(Parametros p, boolean update)
   {      
      String codtabela = p.codtabela;
      String codvendedor = p.codvendedor;
      String nomedovendedor = p.nomedovendedor;
      
      //string: '"+string+"'   integer: "+integer+"
      try
      {
        if(update)
           pdb.executeUpdate("update parametros set codtabela='"+codtabela+"', codvendedor='"+codvendedor+"', nomedovendedor='"+nomedovendedor+"' where codtabela='"+codtabela+"'");
        else
           pdb.executeUpdate("insert into parametros values ('"+codtabela+"', '"+codvendedor+"', '"+nomedovendedor+"')");
      } 
      catch (Throwable t)
      {
         Vm.debug(t.getMessage());
      }
      }
   
  
   /**
    * Retorna um vector ( Parametros )
    * 
    * @param codvendedor
    *           Código do Vendedor
    * @return será null se não for encontrado
    */
   public Vector buscaPeloCodigo(String codvendedor) // info
   {
      Vector vec = new Vector();
      ResultSet rs = pdb.executeQuery("select * from parametros where codvendedor='"+codvendedor+"'");
      Parametros p = null;
      while(rs.next())
       {
          p = new Parametros();
          p.codtabela = rs.getString("codtabela");
          p.codvendedor = rs.getString("codvendedor");
          p.nomedovendedor = rs.getString("nomedovendedor");
          vec.add(p);
       }
      return vec;
   }
         
   
   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table parametros(codtabela char (20), codvendedor char(20), nomedovendedor char (30))");
         pdb.execute("CREATE INDEX IDX_0 ON parametros(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON parametros(codvendedor)");
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }
}