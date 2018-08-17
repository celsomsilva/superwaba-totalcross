/*
 * CustomerDB.java All PDBDriver operations related to the Customers.pdb file, Customer
 * insertion, Customer updating, searching for a Customer inside the pdb, etc... 
 */


package vendas.bd;

import waba.sys.Settings;
import waba.sys.Vm;
import waba.util.Vector;
import superwaba.ext.xplat.sql.pdb.pdbx.*;

public class ClienteBD
{
 
   private PDBDriver pdb;
   int rowId;
   
   
   public static ClienteBD instance = new ClienteBD();

   /**
    *  Constructor which creates a table and intance to the PDBdriver
    */
   private ClienteBD()
   {
      if (!Settings.onDevice)//evita warnings
         waba.applet.JavaBridge.showMsgs = false;
      pdb = PDBDriver.getInstance(Settings.appCreatorId);
      criarTabela();
   }

   /**
    * Method to write a record ( Customer ) to the pdb file
    * 
    * @param c
    *           the Customer
    */
   public void escrever(Cliente c, boolean atualiza)
   {
      String cnpjcpf = c.cnpjcpf;
      String nome = c.nome;
      String tipo = c.tipo;
      String inscr = c.inscr;
      String canal = c.canal;
      String regiaogeo = c.regiaogeo;
      String transportador = c.transportador;
      String regiao = c.regiao;
      String agente = c.agente;
      String ramo = c.ramo;
      String checkout = c.checkout;
      String condfrete = c.condfrete;
      String atendente = c.atendente;
      String condpag = c.condpag;
      String statuscred = c.statuscred;
      String limitecred = c.limitecred;
      String saldocred = c.saldocred;
      String cnome = c.cnome;
      String cargo = c.cargo;
      String endereco = c.endereco;
      String bairro = c.bairro;
      String cidade = c.cidade;
      String cep = c.cep;
      String estado = c.estado;
      String tel1 = c.tel1;
      String ramal1 = c.ramal1;
      String tel2 = c.tel2;
      String ramal2 = c.ramal2;
      String tel3 = c.tel3;
      String ramal3 = c.ramal3;
      String fax = c.fax;
      String email = c.email;
      String enderecocob = c.enderecocob;
      String bairrocob = c.bairrocob;
      String cidadecob = c.cidadecob;
      String estadocob = c.estadocob;
      String cepcob = c.cepcob;
      String enderecoent = c.enderecoent;
      String bairroent = c.bairroent;
      String cidadeent = c.cidadeent;
      String estadoent = c.estadoent;
      String cepent = c.cepent;
      //string: '"+variavel+"'   integer: "+variavel+"
      try
      {
        if(atualiza)
           pdb.executeUpdate("update cliente set cnpjcpf='"+cnpjcpf+"', nome='"+nome+"', tipo='"+tipo+"', inscr='"+inscr+"', canal='"+canal+"', regiaogeo='"+regiaogeo+"', transportador='"+transportador+"', regiao='"+regiao+"', agente='"+agente+"', ramo='"+ramo+"', checkout='"+checkout+"', condfrete='"+condfrete+"', atendente='"+atendente+"', condpag='"+condpag+"', statuscred='"+statuscred+"', limitecred='"+limitecred+"', saldocred='"+saldocred+"', cnome='"+cnome+"', cargo='"+cargo+"', endereco='"+endereco+"', bairro='"+bairro+"', cidade='"+cidade+"', cep='"+cep+"', estado='"+estado+"', tel1='"+tel1+"', ramal1='"+ramal1+"', tel2='"+tel2+"', ramal2='"+ramal2+"', tel3='"+tel3+"', ramal3='"+ramal3+"', fax='"+fax+"', email='"+email+"', enderecocob='"+enderecocob+"', bairrocob='"+bairrocob+"', cidadecob='"+cidadecob+"', estadocob='"+estadocob+"', cepcob='"+cepcob+"', enderecoent='"+enderecoent+"', bairroent='"+bairroent+"', cidadeent='"+cidadeent+"', estadoent='"+estadoent+"', cepent='"+cepent+"' where cnpjcpf='"+cnpjcpf+"'");
        else
           pdb.executeUpdate("insert into cliente values ('"+cnpjcpf+"', '"+nome+"', '"+tipo+"', '"+inscr+"', '"+canal+"', '"+regiaogeo+"', '"+transportador+"', '"+regiao+"', '"+agente+"', '"+ramo+"', '"+checkout+"', '"+condfrete+"', '"+atendente+"', '"+condpag+"', '"+statuscred+"', '"+limitecred+"', '"+saldocred+"', '"+cnome+"', '"+cargo+"', '"+endereco+"', '"+bairro+"', '"+cidade+"', '"+cep+"', '"+estado+"', '"+tel1+"', '"+ramal1+"', '"+tel2+"', '"+ramal2+"', '"+tel3+"', '"+ramal3+"', '"+fax+"', '"+email+"', '"+enderecocob+"', '"+bairrocob+"', '"+cidadecob+"', '"+estadocob+"', '"+cepcob+"', '"+enderecoent+"', '"+bairroent+"', '"+cidadeent+"', '"+estadoent+"', '"+cepent+"')");
      } 
      catch (Throwable t)
      {
         Vm.debug(t.getMessage());
      }
   }
   

   /**
    * Removes a record ( Customer ) from the pdb file
    * 
    * @param cli
    *           the Customer
    */
   public void remover(Cliente cli)
   {
      String cliname=cli.nome;
      pdb.executeUpdate("delete cliente where nome='"+cliname+"'"); 
   }
   
   
   public String[] lerTodosOsNomes()
   {
      String cliente="cliente";
      ResultSet rs = pdb.executeQuery("select nome from cliente");
      int n = pdb.getRowCount(cliente);
      String ss[] = new String[n];
      int i = 0;
      while(rs.next())
         ss[i++]=rs.getString("nome");
      return ss;
   }
   
   
   /**
    * Returns a specific record ( Customer )
    * 
    * @param name
    *           Name of the Customer
    * @return The Customer or null if the Customer was not found
    */
   public Cliente buscaPeloNome(String nome) // info
   {
      ResultSet rs = pdb.executeQuery("select * from cliente where nome='"+nome+"'");
      Cliente c = null;
      if(rs.first())
       {
          c = new Cliente();
          c.cnpjcpf = rs.getString("cnpjcpf");
          c.nome = rs.getString("nome");
          c.tipo = rs.getString("tipo");
          c.inscr = rs.getString("inscr");
          c.canal = rs.getString("canal");
          c.regiaogeo = rs.getString("regiaogeo");
          c.transportador = rs.getString("transportador");
          c.regiao = rs.getString("regiao");
          c.agente = rs.getString("agente");
          c.ramo = rs.getString("ramo");
          c.checkout = rs.getString("checkout");
          c.condfrete = rs.getString("condfrete");
          c.atendente = rs.getString("atendente");
          c.condpag = rs.getString("condpag");
          c.statuscred = rs.getString("statuscred");
          c.limitecred = rs.getString("limitecred");
          c.saldocred = rs.getString("saldocred");
          c.cnome = rs.getString("cnome");
          c.cargo = rs.getString("cargo"); 
          c.endereco = rs.getString("endereco");
          c.bairro = rs.getString("bairro");
          c.cidade = rs.getString("cidade");
          c.cep = rs.getString("cep");
          c.estado = rs.getString("estado");
          //c.ddd = rs.getString("ddd");
          c.tel1 = rs.getString("tel1");
          c.ramal1 = rs.getString("ramal1");
          c.tel2 = rs.getString("tel2");
          c.ramal2 = rs.getString("ramal2");
          c.tel3 = rs.getString("tel3");
          c.ramal3 = rs.getString("ramal3");
          c.fax = rs.getString("fax");
          c.email = rs.getString("email");
          c.enderecocob = rs.getString("enderecocob");
          c.bairrocob = rs.getString("bairrocob");
          c.cidadecob = rs.getString("cidadecob");
          c.estadocob = rs.getString("estadocob");
          c.cepcob = rs.getString("cepcob");
          c.enderecoent = rs.getString("enderecoent");
          c.bairroent = rs.getString("bairroent");
          c.cidadeent = rs.getString("cidadeent");
          c.estadoent = rs.getString("estadoent");
          c.cepent = rs.getString("cepent");
       }
      return c;
   }
      
   /**
    * Searchs for a Customer matching the specific search fields
    * 
    * @param name
    *           Name of the Customer
    * @param city
    *           City related to the Customers
    * @param state
    *           State related to the Customers
    * @param country
    *           County related to the Customers
    * @return A vector containing the result of the search.
    */
   
 
   public Vector procuraClientes(String nome, String cidade, String estado)
   {
      Vector v = new Vector();
      // o * é usado como curinga
      ResultSet rs = pdb.executeQuery("select * from cliente where nome='"+nome+"' and cidade='"+cidade+"' and estado='"+estado+"'");
      if(rs.first())
      {   
         Cliente cli = new Cliente();
         cli.cnpjcpf = rs.getString("cnpjcpf");
         cli.nome = rs.getString("nome");
         cli.tipo = rs.getString("tipo");
         cli.inscr = rs.getString("inscr");
         cli.canal = rs.getString("canal");
         cli.regiaogeo = rs.getString("regiaogeo");
         cli.transportador = rs.getString("transportador");
         cli.regiao = rs.getString("regiao");
         cli.agente = rs.getString("agente");
         cli.ramo = rs.getString("ramo");
         cli.checkout = rs.getString("checkout");
         cli.condfrete = rs.getString("condfrete");
         cli.atendente = rs.getString("atendente");
         cli.condpag = rs.getString("condpag");
         cli.statuscred = rs.getString("statuscred");
         cli.limitecred = rs.getString("limitecred");
         cli.saldocred = rs.getString("saldocred");
         cli.cnome = rs.getString("cnome");
         cli.cargo = rs.getString("cargo"); 
         cli.endereco = rs.getString("endereco");
         cli.bairro = rs.getString("bairro");
         cli.cidade = rs.getString("cidade");
         cli.cep = rs.getString("cep");
         cli.estado = rs.getString("estado");
         cli.tel1 = rs.getString("tel1");
         cli.ramal1 = rs.getString("ramal1");
         cli.tel2 = rs.getString("tel2");
         cli.ramal2 = rs.getString("ramal2");
         cli.tel3 = rs.getString("tel3");
         cli.ramal3 = rs.getString("ramal3");
         cli.fax = rs.getString("fax");
         cli.email = rs.getString("email");
         cli.enderecocob = rs.getString("enderecocob");
         cli.bairrocob = rs.getString("bairrocob");
         cli.cidadecob = rs.getString("cidadecob");
         cli.estadocob = rs.getString("estadocob");
         cli.cepcob = rs.getString("cepcob");
         cli.enderecoent = rs.getString("enderecoent");
         cli.bairroent = rs.getString("bairroent");
         cli.cidadeent = rs.getString("cidadeent");
         cli.estadoent = rs.getString("estadoent");
         cli.cepent = rs.getString("cepent");
         
         v.add(cli);
      }
      return v;
   }
      
   
   private void criarTabela()
   {
      try
      {
         // note: instead of creating our own id, we should use the rowid (stored by the driver), but this is not implemented yet.
         pdb.execute("create table cliente(cnpjcpf char (20),nome char(30), tipo char (20), inscr char (20), canal char (15), regiaogeo char (15), transportador char (20), regiao char (20), agente char (20), ramo char(20), checkout char (15), condfrete char (10), atendente char (20), condpag char (20), statuscred char (20), limitecred char (10), saldocred char (10), cnome char(30), cargo char (20), endereco char (30), bairro char (20), cidade char(15), cep char(9), estado char(2), tel1 char(15), ramal1 char(10), tel2 char (15), ramal2 char(10), tel3 char(15), ramal3 char (10), fax char (15), email char (20), enderecocob char (30), bairrocob char (20), cidadecob char (20), estadocob char (2), cepcob char (9), enderecoent char (30), bairroent char (20), cidadeent char (20), estadoent char (2), cepent char (9))");
         pdb.execute("CREATE INDEX IDX_0 ON cliente(rowid)"); // the index names are completely ignored
         pdb.execute("CREATE INDEX IDX_1 ON cliente(nome)");
         pdb.execute("CREATE INDEX IDX_2 ON cliente(cidade)");
         pdb.execute("CREATE INDEX IDX_3 ON cliente(estado)");
      }
      catch (AlreadyCreatedException ace) {} // just ignore
      catch (PDBException pe)
      {
         Vm.debug(pe.getMessage()); // just display the message in console
      }
   }
}
