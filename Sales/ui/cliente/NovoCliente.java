/*
 * NovoCliente.java
 */
package vendas.ui.cliente;

import vendas.*;
import vendas.bd.*;
import waba.sys.*;
import waba.ui.*;
import waba.fx.*;
import waba.util.*;

public class NovoCliente extends Container
{
   public static Cliente enviaCliente;

   private ClienteGeral ng;
   private ClienteMaisInfo nm;
   private ClienteEndereco nen;
   private ClienteMaisEndereco nme;
   private ClienteEnderecoDeCobranca ne;
   private ClienteEnderecoDeEntrega nee;
   private ClienteCreditos nc;
   private DynamicTabPanel tp;
   private Button btnBack, btnClear, btnInsert;
   Cliente cli = new Cliente();
   boolean sair1 = true;
   boolean sair2 = true;

   public void onStart()
   {
      ng = new ClienteGeral( );
      nm = new ClienteMaisInfo( );
      nen = new ClienteEndereco( );
      nme = new ClienteMaisEndereco( );
      ne = new ClienteEnderecoDeCobranca( );
      nee = new ClienteEnderecoDeEntrega();
      nc = new ClienteCreditos( );
      Button.commonGap = 1;

      tp = new DynamicTabPanel(new String[]{ "Geral","Mais info","Endereço","Mais End.","Cobrança","Entrega","Crédito"});
      tp.setBorderStyle(Window.NO_BORDER); // no borders, please
      Rect r = getClientRect();
      tp.setBackColor(Color.BRIGHT);

      add(btnInsert = new Button("Inserir"), RIGHT - 2, BOTTOM - 2);
      add(btnBack = new Button("Voltar"), BEFORE - 2, SAME);
      add(btnClear = new Button("Apagar"), BEFORE - 2, SAME);

      r.height = btnInsert.getPos().y-2; // don't let it get beyond the buttons
      r.x++; r.width-=2; // these adjusts make the cursor appear correctly on penless
      tp.setRect(r);
      //tp.setPanel(0,ng); ng.setRect(tp.getClientRect());
      tp.setPanel(1,nm); nm.setRect(tp.getClientRect());
      tp.setPanel(2,nen); nen.setRect(tp.getClientRect());
      tp.setPanel(3,nme); nme.setRect(tp.getClientRect());
      tp.setPanel(4,ne); ne.setRect(tp.getClientRect());
      tp.setPanel(5,nee); nee.setRect(tp.getClientRect());
      tp.setPanel(6,nc); nc.setRect(tp.getClientRect());
      tp.setPanel(0,ng); ng.setRect(tp.getClientRect()); 
      add( tp );
      
      if (Settings.isColor)
      {
         Color clrBack = ForcaDeVendas.defaultBackColor;
         Color clrFore = ForcaDeVendas.defaultForeColor;
         btnInsert.setBackColor(clrBack);
         btnInsert.setForeColor(clrFore);
         btnBack.setBackColor(clrBack);
         btnBack.setForeColor(clrFore);
         btnClear.setBackColor(clrBack);
         btnClear.setForeColor(clrFore);
      }
      // reassign the tab order
      tabOrder = new Vector(new Control[]{tp, btnClear, btnBack, btnInsert});
      atualiza( );
   }

   public void atualiza()
   {
      if (enviaCliente == null)
      {
         apagarEdit();
         btnInsert.setText("Inserir");
      }
      else
      {
         btnInsert.setText("Atualizar");
         ng.setarEdit(enviaCliente);
         nm.setarEdit(enviaCliente);
         nen.setarEdit(enviaCliente);
         nme.setarEdit(enviaCliente);
         ne.setarEdit(enviaCliente);
         nee.setarEdit(enviaCliente);
         nc.setarEdit(enviaCliente);
      }
   }

   /**
    * Empty all edit boxes and set the active panel to the first one
    */
   public void apagarEdit()
   {
      ng.apagarEdit();
      nm.apagarEdit();
      nen.apagarEdit();
      nme.apagarEdit();
      ne.apagarEdit();
      nee.apagarEdit();
      nc.apagarEdit();
      tp.setActiveTab(0);
   }

   public boolean verificaCampos(Cliente c)
   {
      String s = "";
      
      if (c.cnpjcpf.length() == 0)   s += " CNPJ/CPF|"; 
      if (c.tipo.length() == 0)  s+= " Tipo|";
      if (c.condpag.length() == 0)  s+= " Pagamento|";
      if (c.canal.length() == 0) s+= " Canal|";
      if (c.agente.length() == 0)   s+= " Agente|";
      if (c.condfrete.length() == 0) s+= " Cond.frete|";
      if (c.endereco.length() == 0) s += " Endereco|";
      if (c.bairro.length() == 0)    s += " Bairro|";
      if (c.cidade.length() == 0)    s += " Cidade|";
      if (c.estado.length() == 0)   s += " UF|";
      if (c.cep.length() == 0)     s += " CEP|";
      if (c.tel1.length() == 0)    s += " Tel.1";

      if (s.length() > 0)
      {
         new MessageBox("Erro", "Preencha por favor os seguintes campos:|"+s).popupModal();
         return false;
      }
      else
         return true;
   }

   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target == btnClear) apagarEdit();
            
            if(e.target == tp)
            {
               if(tp.getActiveTab() == 4&&sair1)
               {
                  nen.buscarDoEdit(cli);
                  ne.edEnderecocob.setText(cli.endereco);
                  ne.edBairrocob.setText(cli.bairro);
                  ne.edCidadecob.setText(cli.cidade);
                  ne.edCepcob.setText(cli.cep);
                  ne.combEstadocob.select(cli.estado);
                  sair1 = false;
               }
               if(tp.getActiveTab() == 5&&sair2)
               {
                  nee.edEnderecoent.setText(cli.endereco);
                  nee.edBairroent.setText(cli.bairro);
                  nee.edCidadeent.setText(cli.cidade);
                  nee.edCepent.setText(cli.cep);
                  nee.combEstadoent.select(cli.estado);
                  sair2 = false;
               }
            }
            
            if (e.target == btnBack)
            {
               if (enviaCliente != null)
               {
                  enviaCliente = null;
                  ForcaDeVendas.swapTo(ForcaDeVendas.PROCURACLIENTES);
               }
               else
                  ForcaDeVendas.swapTo(ForcaDeVendas.CLIENTESMENU);
            }

            if (e.target == btnInsert)
            {
               Cliente c = new Cliente();
               ng.buscarDoEdit(c);
               nm.buscarDoEdit(c);
               nen.buscarDoEdit(c);
               nme.buscarDoEdit(c);
               ne.buscarDoEdit(c);
               nee.buscarDoEdit(c);
               nc.buscarDoEdit(c);

               if (verificaCampos(c))
               {
                  ClienteBD.instance.escrever(c,enviaCliente != null);
   
                  apagarEdit();
                  if (enviaCliente != null)
                  {
                     enviaCliente = null;
                     new MessageBox("Mensagem", "Cliente atualizado").popupBlockingModal();
                     ForcaDeVendas.swapTo(ForcaDeVendas.PROCURACLIENTES);
                  }
                  else
                     new MessageBox("Mensagem", "Cliente inserido").popupModal();
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