package vendas.bd;


/* Client.java
 *  
 * This is where we specify what a client is:
 * 
 * What is a client!?
 * A client must have a name, an identification number, a complete address
 * ( address, city, state, country, zip ), contact phone numbers. 
 * 
 * Sometimes when dealing with large companies you often have a person inside
 * the company that is your contact, its name ( cname ), role in the company
 * (role ), and telephone numbers ( telc, cel ) are also fields in the client
 * abstract data type.
 */

public class Cliente
{
   public String cnpjcpf;
   public String nome;
   public String tipo;
   public String inscr;
   public String canal;
   public String regiaogeo;

   public String condpag;
   public String transportador;
   public String regiao;
   public String agente;
   public String ramo;
   public String checkout;
   public String condfrete;
   public String atendente;
   
   public String statuscred;
   public String limitecred;
   public String saldocred;
   
   public String cnome;
   public String cargo;
   public String endereco;
   public String bairro;
   public String cidade;
   public String cep;
   public String estado;
   
   public String tel1;
   public String ramal1;
   public String tel2;
   public String ramal2;
   public String tel3;
   public String ramal3;
   public String fax;
   public String email;
   
   public String enderecocob;
   public String bairrocob;
   public String cidadecob;
   public String estadocob;
   public String cepcob;
   
   public String enderecoent;
   public String bairroent;
   public String cidadeent;
   public String estadoent;
   public String cepent;
   
   
   
   
   public Cliente()
   {
   }
}
