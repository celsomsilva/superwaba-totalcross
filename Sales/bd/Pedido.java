package vendas.bd;

import waba.util.Date;

/* Pedido.java
 * 
 * This where we define what an Order is:
 * 
 * What is an order!?
 * 
 * An order must have an unique identification number, it also
 * needs a client that the order is related to ( each order has
 * its own related client and only one ), the date in which the
 * order was issued, and the total price of the order ( each items
 * quantiy * unitprice altogether )
 *  
 */

public class Pedido
{
   public Date data;
   public int dia;
   public int mes;
   public int ano;
   public int codigo;
   public String clientecod;
   public String cliente;
   public String tipo;
   public String condpagamento;
   public String agente;
   public String dtentrega;
   public String obs;
   public double precoTotal;
   public double descontoesp;
   public double pesoTotal;
  
   
   
   
   public Pedido( ) 
   {
   }
}
