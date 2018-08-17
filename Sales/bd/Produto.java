package vendas.bd;

/* Product.java
 * 
 * This where we define what a Product is:
 * 
 * What is a product!?
 * 
 * A product must have a name, and an identification code,
 * a short description of what the product is and its current
 * price per unit. 
 * 
 */

public class Produto
{
   public double pesobruto;
   public String ean;
   public String dun14;
   public String codigo;
   public String descr;
   public String data;
   public String familia;
   public double precoUnit;
   public double ipi;
  
   
   
   public boolean equals(Object obj)
   {
      if (obj instanceof Produto)
         return ((Produto)obj).codigo.equals(codigo);
      return obj.equals(codigo);
   }



   public Produto() 
   {
   }

}
