package vendas.bd;


/* ItemDoPedido.java
 *  
 * This is where we specify what a ItemsOrder is:
 * 
 * What is an ItemsOrder!?
 * 
 * An order must have items ( products ) related to it,
 * An item inside an order must have an associated orderid ( so that we
 * know which item is related to which order ), an id of the product ( so 
 * that we know which product is this item ), the quantity of items related
 * in that order, the price of one unit at the time that the order was done
 * ( products unit price can change ), and the price of the total amount 
 * ( quantity * unitprice ).
 * 
 */

public class ItemDoPedido
{
   public int codigopedido;
   public String codigoproduto;
   public String descr;
   public int quant;
   public double ipi;
   public double precoUnit;
   public double precoTotal;
   

   public ItemDoPedido() 
   {   
   }
}