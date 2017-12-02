package com.horstmann.violet.product.diagram.sequence;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Dimension2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.Attributes.Name;

import javax.xml.transform.Templates;

import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.Condition;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentPart;
import com.horstmann.violet.product.diagram.abstracts.property.FragmentType;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.workspace.Workspace;
import com.horstmann.violet.workspace.WorkspacePanel;
import com.horstmann.violet.workspace.editorpart.EditorPart;
import com.horstmann.violet.workspace.editorpart.IEditorPart;
import com.horstmann.violet.workspace.editorpart.behavior.EditSelectedBehavior;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

public class RefNode extends RectangularNode 
{	   
   public RefNode()
   {	
	   text = new MultiLineString();
	   text.setJustification(MultiLineString.CENTER);
   }

public MultiLineString getText() {
	return text;
}

public void setText(MultiLineString text) {
	this.text = text;
}

@Override
public int getZ() {
	// TODO Auto-generated method stub
	return INFINITE_Z_LEVEL;
}

@Override
   public Rectangle2D getBounds()//改变外部边框
   {	
	   Rectangle2D b = text.getBounds();
       Point2D currentLocation = this.getLocation();
       double x;
       double y;
	   x = currentLocation.getX();
       y =  currentLocation.getY();      
       Rectangle2D currentBounds = new Rectangle2D.Double(x, y, getWidth(), getHeight());
      // Rectangle2D snapperBounds = getGraph().getGridSticker().snap(currentBounds);
       return currentBounds;
   }
 
public double getWidth() {
	return width;
}
public void setWidth(double witdh) {
	this.width = witdh;
}
public double getHeight() {
	return height;
}
public void setHeight(double height) {
	this.height = height;
}
@Override
   public Shape getShape()
   {
       Rectangle2D bounds = getBounds();
       GeneralPath path = new GeneralPath();
       path.moveTo((float) bounds.getX(), (float) bounds.getY()); 
       path.lineTo((float) (bounds.getMaxX()), (float) bounds.getY());
       path.lineTo((float) bounds.getMaxX(), (float) bounds.getY()) ;
       path.lineTo((float) bounds.getMaxX(), (float) bounds.getMaxY());
       path.lineTo((float) bounds.getX(), (float) bounds.getMaxY());
       
       path.closePath();
       return path;
   }

//绘制TimeNode节点
   @Override
   public void draw(Graphics2D g2)    
   {  	  	 	   
	  
       
       Shape path = getShape();    
       Rectangle2D bounds = getBounds();
       g2.setColor(DEFAULT_COLOR);
       g2.fill(bounds);
       
       g2.setColor(Color.black);//已改  
       GeneralPath fold = new GeneralPath();
       fold.moveTo((float) (bounds.getX()+5*d), (float) bounds.getY());  //将鼠标放置在某点
       fold.lineTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
       fold.moveTo((float) bounds.getX()+5*d, (float) bounds.getY()+(2*d));
       fold.lineTo((float) bounds.getX()+4*d, (float) bounds.getY()+(2.5*d));
       fold.moveTo((float) bounds.getX() +4*d, (float) bounds.getY()+(2.5*d));
       fold.lineTo((float) bounds.getX(), (float) bounds.getY()+(2.5*d));
       fold.closePath();
       g2.setColor(Color.BLACK);
       g2.drawString("ref",(int)bounds.getBounds().getX()+10,(int)bounds.getBounds().getY()+15);//绘制ref标志
       
       text.draw(g2, bounds);
       
       g2.draw(fold);
       g2.draw(path);
       g2.fill(fold);
    
       
   }
 
@Override
   public RefNode clone()
   {
	RefNode cloned = (RefNode) super.clone(); 
	cloned.text = text.clone();  
    return cloned;
   }		

   private MultiLineString text;
   private static Color DEFAULT_COLOR = new Color(255, 228, 181); // very pale pink
   private static int d =10;
   private double width=300,height=80;
   private static double Default_Distance=50;
   private  static Stroke Defelt_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
   private  static Stroke DOTTED_STROKE = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0.0f, new float[]
		    {
		            3.0f,
		            3.0f 
		    }, 0.0f);
   private static double Default_FirstConditionTextDistance=40;
   private static double Default_OtherConditionTextDistance=20;
   private static int INFINITE_Z_LEVEL = 10001;
}
