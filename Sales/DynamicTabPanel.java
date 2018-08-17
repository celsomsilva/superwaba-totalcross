/*********************************************************************************
 *  SuperWaba Virtual Machine, version 4                                         *
 *  Copyright (C) 2000-2003 Guilherme Campos Hazan <support@superwaba.com.br>    *
 *  Copyright (C) 1998, 1999 Wabasoft <www.wabasoft.com>                         *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is free software; you can redistribute      *
 *  it and/or modify it under the terms of the Amended GNU Lesser General        *
 *  Public License distributed with this software.                               *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *  For the purposes of the SuperWaba software we request that software using    *
 *  or linking to the SuperWaba virtual machine or its libraries display the     *
 *  following notice:                                                            *
 *                                                                               *
 *                   Created with SuperWaba                                      *
 *                  http://www.superwaba.org                                     *
 *                                                                               *
 *  Please see the software license located at SuperWabaSDK/license.txt          *
 *  for more details.                                                            *
 *                                                                               *
 *  You should have received a copy of the License along with this software;     *
 *  if not, write to                                                             *
 *                                                                               *
 *     Guilherme Campos Hazan                                                    *
 *     Av. Nossa Senhora de Copacabana 728 apto 605 - Copacabana                 *
 *     Rio de Janeiro / RJ - Brazil                                              *
 *     Cep: 22050-000                                                            *
 *     E-mail: support@superwaba.com.br                                          *
 *                                                                               *
 *********************************************************************************/

package vendas;


import waba.fx.*;
import waba.ui.*;
import waba.sys.*;

/**
 * TabPanel is a bar of tabs. (Very) Modified by Guich.
 * The panels are created automaticaly and switched when the user press the corresponding tab.
 * Makes an sound when the tab is pressed.
 *
 * Added support for Dynamic Tab addition and scrolling tabs - Adam Gugliciello
 *
 * <p>
 * Here is an example showing a tab bar being used:
 *
 * <pre>
 * public class MyProgram extends MainWindow
 * {
 *    TabPanel tab;
 *
 *    public void onStart()
 *    {
 *       String nomes[] = {"Edition","Report"};
 *       tab = new TabPanel(nomes);
 *       add(tab);
 *       tab.setGaps(2,2,2,2); // set it before setting the rect
 *       tab.setRect(getClientRect());
 *       tab.setPanel(0,new Edition()); // replace container 1 by a class that extends Container.
 *       tab.getPanel(1).add(new Label("Not implemented"),CENTER,CENTER);
 *       add(tab);
 *    }
 *
 *    public void onEvent(Event event)
 *    {
 *       if (event.type == ControlEvent.PRESSED && event.target == tp)
 *       {
 *          int activeIndex = tp.getActiveTab();
 *          ... handle tab being pressed
 *       }
 *    }
 * }
 * </pre>
 */

public class DynamicTabPanel extends Container
{
	private int activeIndex=-1;
	private String []tabCaptions;
	private Container panels[];
	private int gapL,gapT,gapB,gapR;
	private int count;
	private int tabH;
	private int xx;
	private Color captionColor = Color.BLACK;
	private boolean atTop=true;
	private Rect []rects;
	private boolean firstTabChange = true; // guich@200b4_87
	private Color fColor,cColor;
	private Color panelsColor[];
	private Rect clientRect;
   /** Set to off to disable the beep when a tab is clicked */
	public  boolean beepOn = true; // guich@230_37
	
	private int style = Window.RECT_BORDER;
	private int firstTab;
	public static final byte TABS_TOP = 0;
	public static final byte TABS_BOTTOM = 1;
	
   /** Constructs a tab bar control. */
	public DynamicTabPanel(String []tabCaptions)
	{
		started = true;
		this.tabCaptions = tabCaptions;
		count = tabCaptions.length;
		panels = new Container[count];
		// create the rects since we want to reuse them
		rects = new Rect[count];
		for (int i =0; i < count; i++)
		{
			rects[i] = new Rect();
			panels[i] = new Container();
		}
	}
	
   /** Sets the colors of the panels. The number of colors must match the number
	 * of panels.
	 */
	public void setPanelsBackColor(Color []backColors) // guich@200b4_171
	{
		if (backColors.length == count)
		{
			panelsColor = backColors;
			for (int i =0; i < count; i++) // guich@300_5: set the color to the containers too
				panels[i].setBackColor(backColors[i]);
		}
	}
	
   /** Sets the position of the tabs. use constants TAB_TOP or TAB_BOTTOM.
	 * Since the tabs are not changed dinamicaly, this method must be called
	 * after the constructor. */
	public void setType(byte type)
	{
		atTop = type == TABS_TOP;
	}
	
   /** Returns the Container for tab i */
	public Container getPanel(int i)
	{
		return panels[i];
	}
   /** sets gaps between the containers and the TabPanel. Must be called before <code>setRect</code>*/
	public void setGaps(int gapL, int gapR, int gapT, int gapB)
	{
		this.gapL = gapL;
		this.gapR = gapR;
		this.gapT = gapT;
		this.gapB = gapB;
	}
   /** Sets the type of border. see the waba.ui.Window xxx_BORDER constants. PS: currently, only the NO_BORDER and RECT_BORDER are supported. NO_BORDER only draws the line under the tabs. */
	public void setBorderStyle(byte style)
	{
		this.style = style;
	}
	
   /** Sets this tabpanel container of index i to the given container. This way you can
	 * avoid adding a container to a container and as such waste memory.
	 * Note that you must do this before the first setRect for this tabPanel; otherwise,
	 * you must explicitly call setRect again to update the added container bounds
	 */
	public void setPanel(int i, Container container)
	{
		if (panels != null && i >= 0 && i < panels.length)
		{
			Container old = panels[i];
			panels[i] = container;
			//if (i == activeIndex) // guich@300_34: fixed problem when the current tab was changed
			{
				remove(old);
				add(container);
				container.requestFocus();
			}
			/*if (!container.started) // guich@340_58: set the container's rect
			{
				Container cp = container.parent;
				container.parent = this;
				container.setRect(clientRect);
				container.parent = cp;
			}*/
		}
	}
	
   /**
	 * Sets the currently active tab. A PRESSED event will be posted to
	 * the given tab if it is not the currently active tab. the panels will be switched.
	 */
	public void setActiveTab(int tab)
	{
		if (tab != activeIndex)
		{
			if (activeIndex != -1) remove(panels[activeIndex]);
			activeIndex = tab;
			add(panels[activeIndex]);
			computeTabsRect();
			repaint();
			if (!firstTabChange) // guich@200b4_87
				postEvent(new ControlEvent(ControlEvent.PRESSED, this));
			else
				firstTabChange = false;
		}
	}
	
   /** Returns the index of the selected tab */
	public int getActiveTab()
	{
		return activeIndex;
	}
	
	
   /** Called by the system to pass events to the tab bar control. */
	public void onEvent(Event event)
	{
		if (event.type == PenEvent.PEN_DOWN && event.target == this)
		{
			PenEvent pe = (PenEvent)event;
			int xmodifier=(firstTab*(getLargestTabWidth()+2));
			int arrowOffset=0;
			int lastRect=count;
			if (this.getRect().x+getPreferredWidth()>Settings.screenWidth)
			{
				boolean isNotAllLeft=(firstTab!=0);
				int rightRemaining =(((getLargestTabWidth()+2)*(count-firstTab))+((arrowOffset*2)+6));
				boolean isNotAllRight=(rightRemaining>Settings.screenWidth);
				arrowOffset=10;	
				for (int i =0; i < count; i++)
				{
					if ((((rects[i].x+arrowOffset)-xmodifier)+rects[i].width+2)>Settings.screenWidth-arrowOffset)
					{
						lastRect=i	;
						break;
					}
				}
				if (isNotAllLeft)
				{
					if (pe.x<13 && ( ( (pe.y<tabH) && atTop) || ( (pe.y>(height-tabH)) && !atTop) ))
					{
						scrollLeft();
						return;
					}
				}
				if (isNotAllRight)
				{
					if (pe.x>Settings.screenWidth-13 && ( ( (pe.y<tabH) && atTop) || ( (pe.y>(height-tabH)) && !atTop) ))
					{
						scrollRight();
						return;
					}
				}
			}
			if (rects[0].y <= pe.y && pe.y <= rects[0].y2())
				for (int i = firstTab; i < lastRect; i++)
				{
					if ((rects[i].x-xmodifier+arrowOffset)<=pe.x &&  (rects[i].x2()-xmodifier+arrowOffset)>=pe.x)
					{
						if (i != activeIndex)
						{
							if (beepOn && waba.sys.Settings.onDevice) Sound.beep(); // guich@300_7
							setActiveTab(i);
							if (i==firstTab)
								scrollLeft();
							else if (i==lastRect-1)
								scrollRight();
						}
						break;
					}
				}
		}
	}
	public void scrollLeft()
	{
		if (firstTab>0)
		{
			firstTab--;
			repaintNow();
		}
	}
	public void scrollRight()
	{
		int arrowOffset=10;
		int xmodifier=(firstTab*(getLargestTabWidth()+2));
		if ((((rects[rects.length-1].x+arrowOffset)-xmodifier)+rects[rects.length-1].width+2)>=Settings.screenWidth-arrowOffset)
		{
			firstTab++;
			repaintNow();
		}
	}
	public void resetScroll()
	{
		firstTab=0;
	}
   /** Returns the minimum height for this TabPanel */
	public int getPreferredHeight()
	{
		return fmH + 4 + 20;
	}
   /** Returns the minimum width for this TabPanel */
	public int getPreferredWidth()
	{
		int sum = 0;
		sum = ((getLargestTabWidth()+2)*count);
		return sum+6;
	}
	private int getLargestTabWidth()
	{
		int ret = 0;
		for (int i =0; i < count; i++)
		{
			int a=fm.getTextWidth(tabCaptions[i]);
			if (a>ret)
				ret=a;
		}
		return ret;
	}
	public int getTabCount()
	{
		return count;
	}
   /** used internally. resizes all the containers. */
	protected void onBoundsChanged()
	{
		tabH = fmH + 4;
		computeTabsRect();
		int borderGap = style==Window.NO_BORDER ? 0 : 1; // guich@400_89
		int xx = gapL+borderGap;
		int yy = (atTop?tabH:borderGap)+gapT;
		int ww = width-gapL-gapR-(borderGap<<1);
		int hh = height-gapT-gapB-(borderGap<<1)-(atTop?yy:tabH);
		clientRect = new Rect(xx,yy,ww,hh);
		for (int i =0; i < count; i++)
			panels[i].setRect(xx,yy,ww,hh);
		
		if (activeIndex == -1) setActiveTab(0); // fvincent@340_40
	}
	// compute the rects that represents each tab on the screen.
	private void computeTabsRect()
	{
		int y0 = atTop?0:(height-tabH);
		int x = 1;
		int largest=0;
		for (int i =0; i<count; i++)
		{
			int cur=fm.getTextWidth(tabCaptions[i]);
			if (cur>largest)
				largest=cur;
		}
		for (int i =0; i < count; i++)
		{
			Rect r = rects[i];
			r.set(x,y0,largest+6,tabH); // create the rect as if it was the selected one
			if (i != activeIndex)
				r.modify(0,atTop?2:0,-3,-2);
			x += r.width-1;
			rects[i] = r;
		}
	}
	private boolean brightBack;
	protected void onColorsChanged(boolean colorsChanged)
	{
		if (colorsChanged)
			brightBack = waba.sys.Settings.isColor && foreColor.getAlpha() > 128;
		fColor = (enabled || !brightBack) ? getForeColor()    : foreColor.darker();
		cColor = (enabled || !brightBack) ? getCaptionColor() : captionColor.darker();
	}
	
   /** Called by the system to draw the tab bar. */
	public void onPaint(Graphics g)
	{
		Rect r;
		int y = atTop?(tabH-1):0;
		int h = atTop?(height-y):(height-tabH+1);
		int yl = atTop?y:(y+h-1);
		int arrowWidth=10;
		// erase area with parent's color
		Color panelColor = panelsColor!=null?panelsColor[activeIndex]:backColor;
		g.setBackColor(getParent().getBackColor());
		if (getParent().getBackColor().equ == panelColor.equ) // same color? fill the whole rect
			g.fillRect(0,0,width,height);
		else
		{
			// otherwise, erase tab area...
			if (atTop)
				g.fillRect(0,0,width,y);
			else
				g.fillRect(0,yl,width,height-yl);
			// ...and erase containers area
			g.setBackColor(panelColor);
			g.fillRect(0,y,width,h);
		}
		g.setForeColor(fColor);
		if (style != Window.NO_BORDER)
			g.drawRect(0,y,width,h); // guich@200b4: now the border is optional
		else
			g.drawLine(0,yl,width,yl);
		
		g.setBackColor(backColor);
		
		// Calculate totalRect Size and compare to screen width, if too large, draw arrows and set up scrolling		
		int arrowOffset=0;
		int arrowy=atTop?2:((height-tabH)+2);
		boolean isNotAllLeft=(firstTab!=0);
		int rightRemaining =(((getLargestTabWidth()+2)*(count-firstTab))+((arrowOffset*2)+6));
		boolean isNotAllRight=(rightRemaining>Settings.screenWidth);
		int lastRect=count;
		int xmodifier=(firstTab*(getLargestTabWidth()+2));
		
		if ((this.getRect().x+getPreferredWidth())>Settings.screenWidth)
		{
			arrowOffset=10;
			g.drawArrow(1,arrowy, 7, g.ARROW_LEFT, false,isNotAllLeft, Color.BLACK);
			g.drawArrow((Settings.screenWidth-arrowOffset)-1,arrowy, 7, g.ARROW_RIGHT, false,isNotAllRight, Color.BLACK);
			for (int i=firstTab; i < count; i++)
			{
				r = rects[i];
				if ((((r.x2()+arrowOffset)-xmodifier)+2)>Settings.screenWidth-arrowOffset)
				{
					lastRect=i	;
					break;
				}
			}
		}	
		if (getParent().getBackColor().equ != backColor.equ) // guich@400_40: now we need to first fill, if needed, and at last draw the border, since the text will overlap the last pixel (bottom-right or top-right)
			for (int i =firstTab; i < lastRect; i++)
			{
				r = rects[i];
				g.fillHatchedRect((r.x+arrowOffset)-xmodifier,r.y,r.width,r.height,atTop,!atTop); // (*)
			}
		// draw text
		g.setForeColor(cColor); // guich@200b4_156
		for (int i =firstTab; i < lastRect; i++)
		{
			r = rects[i];
			if (i == activeIndex)
				g.drawText(tabCaptions[i],(r.x+4+arrowOffset)-xmodifier, r.y+2);
			else
				g.drawText(tabCaptions[i],(r.x+2+arrowOffset)-xmodifier, r.y+1);
			
			g.drawHatchedRect((r.x+arrowOffset)-xmodifier,r.y,r.width,r.height,atTop,!atTop); // guich@400_40: moved from (*) to here
		}
		// guich@200b4: remove the underlaying line of the active tab.
		g.setForeColor(backColor);
		if (activeIndex>=firstTab && activeIndex<lastRect)
		{
			r = rects[activeIndex];
			g.drawLine(((r.x+arrowOffset)-xmodifier),yl,((r.x2()+arrowOffset)-xmodifier),yl);
		}
		
	}
	
   /** Sets the text color of the captions */
	public void setCaptionColor(Color capColor)
	{
		this.captionColor = capColor;
		onColorsChanged(true); // guich@200b4_169
	}
   /** Gets the text color of the captions. return a grayed value if this control is not enabled. */
	public Color getCaptionColor()
	{
		return enabled?captionColor:captionColor.brighter();
	}
   /** Returns the area excluding the tabs and borders for this TabPanel.
	* Note: do not change the returning rect object ! */
	public Rect getClientRect() // guich@340_27
	{
		return clientRect;
	}
   /** Adds a new blank panel to the control with the specified tabcaption */
	public void addPanel(String tabCaption)
	{
		count = tabCaptions.length;
		String[] newTabCaptions = new String[count+1];
		Container[] newPanels = new Container[count+1];
		Rect[] newRects = new Rect[count+1];	
		Vm.copyArray(tabCaptions, 0, newTabCaptions, 0, count);
		Vm.copyArray(panels, 0, newPanels, 0, count);
		Vm.copyArray(rects, 0, newRects, 0, count);
		newTabCaptions[count]=tabCaption;
		newRects[count] = new Rect();
		newPanels[count] = new Container();
		tabCaptions = newTabCaptions; 
		panels = newPanels;
		rects = newRects;
		count++;
		onBoundsChanged();
		
	}
}
