/*
 * PedidoBD.java All PDBDriver operations related to the Orders.pdb file, order insertion,
 * order updating, searching for an order inside the pdb, etc...
 */
package vendas.bd;

import waba.sys.*;
import waba.util.*;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class PedidoBD
{
   private PDBDriver pdb;
   int rowId;
   public static PedidoBD instance = new PedidoBD();
   public int newid;
   public int serialCount;

   /**
    * Construtor que cria uma tabela e instancia para o PDBdriver 
    */
   private PedidoBD()
   {
      if (!Settings.onDevice)//evita warnings
         waba.applet.JavaBridge.showMsgs = false;
      pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
      newid = retornaUltimoCodigoDoPedido();  
   }

   /**
    * este método escreve um pedido no arquivo pedido.pdb
    * 
    * @param ord
    *           O pedido a ser escrito
    */
   public void escrever(Pedido ord, boolean atualiza)
   {
      Date data = ord.data;
      int dataint = data.getDateInt();
      int dia = ord.dia;
      int mes = ord.mes;
      int ano = ord.ano;
      int codigo = ord.codigo;
      String cliente = ord.cliente;
      String clientecod = ord.clientecod;
      String tipo = ord.tipo;
      String condpagamento = ord.condpagamento;
      String agente = ord.agente;
      String dtentrega = ord.dtentrega;
      String obs = ord.obs;
      double precoTotal = ord.precoTotal;
      double descontoesp = ord.descontoesp;
      double pesoTotal = ord.pesoTotal;
      //string: '"+string+"'   integer: "+integer+"
      try
      {
        if(atualiza)
           pdb.executeUpdate("update pedido set data="+dataint+", dia="+dia+", mes="+mes+", ano="+ano+", codigo="+codigo+
                 ", cliente='"+cliente+"', clientecod='"+clientecod+"', tipo='"+tipo+"', condpagamento='"+condpagamento+"', agente='"+agente+"', dtentrega='"+dtentrega+"', obs='"+obs+"', precoTotal="+precoTotal+", descontoesp="+descontoesp+", pesoTotal="+pesoTotal+" where codigo="+codigo+"");
        else
           pdb.executeUpdate("insert into pedido values ("+dataint+","+dia+","+mes+","+ano+","+codigo+",'"+cliente+"','"+clientecod+"','"+tipo+"', '"+condpagamento+"','"+agente+"', '"+dtentrega+"', '"+obs+"', "+precoTotal+", "+descontoesp+", "+pesoTotal+")");
      } 
      catch (Throwable t)
      {
         Vm.debug(t.getMessage());
      }  
   }

  public Vector buscarPorData(int data)
   {
      Vector v = new Vector();
      ResultSet rs = pdb.executeQuery("select data,codigo,cliente,precoTotal from pedido where data="+data+"");
      while(rs.next())
      {   
         Pedido or = new Pedido();
         int dataint = rs.getInt("data");
         Date date = new Date(dataint);
         or.data = date;
         or.codigo = rs.getInt("codigo");
         or.cliente = rs.getString("cliente");
         or.precoTotal = rs.getDouble("precoTotal");
         v.add(or);
      }
      return v;
   }
  
   public Vector lerTodosOsMeses(int ano)
   {
      Vector v = new Vector();
      Pedido ord;
      ResultSet rs = pdb.executeQuery("select mes,precoTotal from pedido where ano="+ano+"");
      while(rs.next())
         {  
            ord = new Pedido();
            ord.mes = rs.getInt("mes");
            ord.precoTotal = rs.getDouble("precoTotal");
            v.add(ord);
         }
      return v;
   }
   
   
   public Vector lerTodosOsDias(int ano,int mes)
   {
      Vector v = new Vector();
      Pedido ord;
      ResultSet rs = pdb.executeQuery("select dia,precoTotal,codigo from pedido where mes="+mes+" and ano="+ano+"");
      while(rs.next())
      {              
            ord = new Pedido();
            ord.dia = rs.getInt("dia");
            ord.codigo = rs.getInt("codigo");
            ord.precoTotal = rs.getDouble("precoTotal");
            v.add(ord);
       }      
      return v;
   }
   
  
   /**
    * Removes an order from the pdb file, first we need to remove all associated
    * ItemsOrder, and then remove the order itself ( consistency )
    * 
    * @param o
    *           The order to be removed
    */
   public void removePedido(Pedido o)
   {
      int codigo=o.codigo;
      
      pdb.executeUpdate("delete pedido where codigo="+codigo+"");
   }
   
   
   /**
    * Search for an order inside the pdb file matching the specific name and date
    * conditions
    * 
    * @param name
    *           The name of the client related to the order
    * @param date
    *           The date in which the order was issued
    * @return A vector containing all orders that matched the searching fields. If no
    *         order match the search fields the vector element count is zero.
    */
  
   public Vector procuraPedidos(String nome,String sdata)
   {
      Vector v = new Vector();
      Date data = new Date(sdata);
      int intdata = data.getDateInt();
      ResultSet rs = pdb.executeQuery("select * from pedido where cliente='"+nome+"' and data="+intdata+"");
      while(rs.next())
      {   
         Pedido or = new Pedido();
         or.data=new Date(rs.getInt("data"));
         or.dia = rs.getInt("dia");
         or.mes = rs.getInt("mes");
         or.ano = rs.getInt("ano");
         or.codigo=rs.getInt("codigo");
         or.cliente=rs.getString("cliente");
         or.clientecod = rs.getString("clientecod");
         or.tipo = rs.getString("tipo");
         or.condpagamento = rs.getString("condpagamento");
         or.agente = rs.getString("agente");
         or.dtentrega = rs.getString("dtentrega");
         or.obs = rs.getString("obs");
         or.precoTotal = rs.getDouble("precoTotal");
         or.descontoesp = rs.getDouble("descontoesp");
         or.pesoTotal = rs.getDouble("pesoTotal");
         v.add(or);
      }
      return v;
   }
      

   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table pedido(data int, dia int, mes int, ano int, codigo int, cliente char(20), clientecod char(20), tipo char (20), condpagamento char (20), agente char(20), dtentrega char (20), obs char (40), precoTotal double, descontoesp double, pesoTotal double)");
         pdb.execute("CREATE INDEX IDX_0 ON pedido(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON pedido(codigo)");
         pdb.execute("CREATE INDEX IDX_2 ON pedido(data)");
         pdb.execute("CREATE INDEX IDX_3 ON pedido(mes)");
         pdb.execute("CREATE INDEX IDX_4 ON pedido(ano)");
         pdb.execute("CREATE INDEX IDX_5 ON pedido(cliente)");
         
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }
 
   /**
    * Search for an order associated with an specific client. This is usefull when someone
    * tries to delete a client that is associated with an order
    * 
    * @param c
    *           The client
    * @return true if thee is at least one client related to an order or false if there is
    *         none
    */
   public boolean procuraPorCliente(Cliente c) // orderClient
   {
      Vector v = this.procuraPedidos(c.nome, "");
      return v.getCount() != 0;
   }

   /**
    * Returns the order associated with its unique order id
    * 
    * @param id
    *           unique order id
    * @return the order if found or null if it wasn´t found
    */
   public Pedido procuraPeloCodigo(int id) // info
   {
      Pedido o = null;
      ResultSet rs = pdb.executeQuery("select * from pedido where codigo="+id+"");
      if(rs.first())
      {
         o = new Pedido();
         o.data = new Date(rs.getInt("data"));
         o.dia = rs.getInt("dia");
         o.mes = rs.getInt("mes");
         o.ano = rs.getInt("ano");
         o.codigo = rs.getInt("codigo");
         o.clientecod = rs.getString("clientecod");
         o.cliente = rs.getString("cliente");
         o.tipo = rs.getString("tipo");
         o.condpagamento = rs.getString("condpagamento");
         o.agente = rs.getString("agente");
         o.dtentrega = rs.getString("dtentrega");
         o.obs = rs.getString("obs");
         o.precoTotal = rs.getDouble("precoTotal");
         o.descontoesp = rs.getDouble("descontoesp");
         o.pesoTotal = rs.getDouble("pesoTotal");
      }
     return o;
   }

   /**
    * This function retrieves the last order id number available to be the next order id
    * to the next order issued, this is a growing, acumulative number, that is not meant
    * to be the best solution for this type of problem. If there is no order, we start at
    * order id number 1, else we grab the biggest order id and add one to it.
    * 
    * @return
    */
   private int retornaUltimoCodigoDoPedido()
   {
     ResultSet rs = pdb.executeQuery("select codigo from pedido");
     return rs.getRowCount();
   }
}
