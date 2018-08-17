/* BaseMenu.java
 * Created on 07/03/2005
 * coded by guich@superwaba.com.br
 * 
 * Since most of the intermediate windows share the 
 * same code, their construction was replaced by
 * a generic class to create them all.
 */
package vendas.ui;

import vendas.*;
import waba.ui.*;
import waba.sys.*;
import waba.fx.*;

/**
 * Base class for Menus (buttons placed vertically).
 */
public abstract class BaseMenu extends Container
{
   /** Button captions and ids*/
   private String []captions;
   private int []ids;
   /** If false, there will be a button to return to the mainmenu */
   private boolean isRoot;
   Color clrBack, clrFore;
   
   public BaseMenu(String []captions, int []ids, boolean isRoot)
   {
      this.captions = captions;
      this.ids = ids;
      this.isRoot = isRoot;
   }
   
   /** Initialize the user interface. */
   public void onStart()
   {
      Button btn=null,ret;
      clrBack = new Color( 0xd9d9ff );
      clrFore = new Color( 0x000099 );
      
      ret = new Button( isRoot ? "Sair" : "Voltar" );
      Button.commonGap = 1;
      add(ret, RIGHT, BOTTOM );
      ret.appId = isRoot ? 999 : 0;
      ret.setBackColor( clrBack );
      ret.setForeColor( clrFore );
      tabOrder.del(ret); // will be added again later 
      
      Container c;
      add( c = new Container() );
      // place all controls inside another container so that this container can be centered on screen.
      Button.commonGap = 4;
      int ww = 0;
      Font font = new Font("SW", Font.BOLD, 15 );
      setFont( font );
      int n = ids.length;
      
      ww = Settings.screenWidth * 120 / 160; /* buttons fill 75% of the page */
      
      // add the buttons.
      c.setRect(0,0,ww,1000); // create a container with a huge height
      int g = Settings.screenWidth == 160 ? 2 : 4;
      for (int i = 0; i < n; i++)
      {
         c.add(btn = new Button(captions[i]) );
         btn.setFont( font );
         btn.setRect(0, AFTER+g, ww, PREFERRED);
         
         /* sets the button color */
         btn.setBackColor( clrBack );
         btn.setForeColor( clrFore );
         
         btn.appId = ids[i];
      }               
      c.setRect(CENTER,CENTER,ww,btn.getRect().y2()+g); // set the height to the correct size.
      Button.commonGap = 0;
      // move the exit button to the last place of the tab order
      tabOrder.add(ret);
   }

   /** Event manipulation: clicks on a button, then swaps to the wanted container.  */
   public void onEvent(Event e)
   {
      switch (e.type)
      {
         case ControlEvent.PRESSED:
            if (e.target instanceof Button) // if it is a button...
            {
               Button btn = (Button)e.target;
               ForcaDeVendas sw = (ForcaDeVendas)MainWindow.getMainWindow(); 
               if (btn.appId == 999)
                  sw.exit(0);
               else
                  ForcaDeVendas.swapTo(btn.appId); // goes to the container whose index is stored in the appId
            }
            break;
      }
   }
}