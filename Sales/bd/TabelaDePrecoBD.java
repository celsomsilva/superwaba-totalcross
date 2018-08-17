/*TabelaDePrecoBD
 * 
 */
package vendas.bd;

import waba.sys.*;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class TabelaDePrecoBD
{
   private PDBDriver pdb;
   int rowId;
   
   
   public static TabelaDePrecoBD instance = new TabelaDePrecoBD();

   /**
    *  Construtor que cria uma tabela e instancia para o PDBdriver 
    */
   private TabelaDePrecoBD()
   {
      if (!Settings.onDevice)//evita warnings
         waba.applet.JavaBridge.showMsgs = false;
      pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
   }
   
   
   public void write(TabelaDePreco p, boolean update)
   {      
      String codtabela = p.codtabela;
      String codproduto = p.codproduto;
      double precoUnit = p.precoUnit;
      
      //string: '"+string+"'   integer: "+integer+"
      try
      {
        if(update)
           pdb.executeUpdate("update tabeladepreco set codtabela='"+codtabela+"', codproduto='"+codproduto+"', precoUnit="+precoUnit+" where codtabela='"+codtabela+"'");
        else
           pdb.executeUpdate("insert into tabeladepreco values ('"+codtabela+"', '"+codproduto+"', "+precoUnit+")");
      } 
      catch (Throwable t)
      {
         Vm.debug(t.getMessage());
      }
      }
   
   
   
  
   /**
    * Retorna um item ( TabelaDePreco )
    * 
    * @param codtabela
    *           Código da Tabela
    * @return será null se não for encontrado
    */
   public TabelaDePreco buscaPeloCodigo(String codtabela) // info
   {
      ResultSet rs = pdb.executeQuery("select * from tabeladepreco where codtabela='"+codtabela+"'");
      TabelaDePreco tb = null;
      if(rs.first())
       {
          tb = new TabelaDePreco();
          tb.codtabela = rs.getString("codtabela");
          tb.codproduto = rs.getString("codproduto");
          tb.precoUnit = rs.getDouble("precoUnit");
          
       }
      return tb;
   }
         
   
   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table tabeladepreco(codtabela char (20),codproduto char(20), precoUnit double)");
         pdb.execute("CREATE INDEX IDX_0 ON tabeladepreco(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON tabeladepreco(codtabela)");
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }
}