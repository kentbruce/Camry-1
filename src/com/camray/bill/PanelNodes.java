package com.camray.bill;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.tree.*;

import com.camray.db.DBConnection;
 
public class PanelNodes extends ComponentAdapter {
    JTree tree;
    JButton addbut;
    HashMap<String, MenuObject> itemlist;
    public HashMap<String, MenuObject> getItemlist() {
		return itemlist;
	}

	public JButton getAddbut() {
		return addbut;
	}

	public void setAddbut(JButton addbut) {
		this.addbut = addbut;
	}

	public void setItemlist(HashMap<String, MenuObject> itemlist) {
		this.itemlist = itemlist;
	}
	public JTree getvTree() {
		return tree;
	}
 
    public void setTree(JTree tree) {
		this.tree = tree;
	}

	public void componentResized(ComponentEvent e) {
        if(tree.isVisible()) {
            registerUI();
        }
    }
 
    JScrollPane getContent() {
    	itemlist=new HashMap<String, MenuObject>();
    	TreeNode rootNode=getTree();
    	tree = new JTree(rootNode);
        tree.setRowHeight(30);
        //tree.setBounds(x, y, width, height)
        tree.addComponentListener(this);
        return new JScrollPane(tree);
    }
 
    private TreeNode getTree() {
    	DefaultMutableTreeNode root;
        DefaultMutableTreeNode category;
        DefaultMutableTreeNode items;
        DefaultMutableTreeNode child;
        MenuObject mobj;
        DBConnection db=new DBConnection();
        root = new DefaultMutableTreeNode(getNode("MENU"));
        Connection connection =
				db.acquireConnection();
        String catquery="select distinct category_name category from camry.menu_category_list ";
        String itemquery="select item_no,item_name,item_price from camry.menu where category=?";
        try {
			PreparedStatement ps = connection.prepareStatement(catquery);
			PreparedStatement psit = connection.prepareStatement(itemquery);
			ResultSet rs = ps.executeQuery();
			ResultSet rsit;
			while (rs.next())
			{
				String a=rs.getString("CATEGORY");
	    		category = new DefaultMutableTreeNode(getNode(a));
	    		root.add(category);
	    		psit.setString(1,a);
	    		rsit=psit.executeQuery();
	    		while(rsit.next())
	    		{
	    		mobj=new MenuObject();
	    		mobj.setItemno(rsit.getString("ITEM_NO"));
	    		mobj.setItemname(rsit.getString("ITEM_NAME"));
	    		mobj.setItemprice(Float.parseFloat((rsit.getString("ITEM_PRICE"))));
	    		itemlist.put(mobj.getItemname(), mobj);
	    			items=new DefaultMutableTreeNode(getNode(mobj.getItemname(),
	    					": "+Double.toString(mobj.getItemprice())));
	    			category.add(items);
	    		}
		}
			
    }catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
        return root;
    }
 
    private PanelNode getNode(String s) {
        return new PanelNode(s, Color.black, Color.pink);
    }
    private PanelNode getNode(String s,String p) {
        return new PanelNode(s,p, Color.black, Color.pink);
    }
 
 
    void registerUI() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                tree.setUI(new BasicWideNodeTreeUI());
            }
        });
    }
 
}
 
/**
 * Code copied/adapted from BasicTreeUI source code.
 */
class BasicWideNodeTreeUI extends BasicTreeUI {
    private int lastWidth;
    private boolean leftToRight;
    protected JTree tree;
 
    public BasicWideNodeTreeUI() {
        super();
    }
 
    public void installUI(JComponent c) {
        if ( c == null ) {
            throw new NullPointerException("null component passed to " +
                                           "BasicTreeUI.installUI()" );
        }
        tree = (JTree)c;
        super.installUI(c);
    }
 
    protected void prepareForUIInstall() {
        super.prepareForUIInstall();
        leftToRight = tree.getComponentOrientation().isLeftToRight();
        lastWidth = tree.getParent().getWidth();
    }
 
    protected TreeCellRenderer createDefaultCellRenderer() {
        return new PanelNodeRenderer();
    }
 
    protected AbstractLayoutCache.NodeDimensions createNodeDimensions() {
        return new NodeDimensionsHandler();
    }
 
    public class NodeDimensionsHandler extends AbstractLayoutCache.NodeDimensions {
        public Rectangle getNodeDimensions(Object value, int row, int depth,
                                           boolean expanded, Rectangle size) {
 
            // Return size of editing component, if editing and asking
            // for editing row.
            if(editingComponent != null && editingRow == row) {
                Dimension        prefSize = editingComponent.getPreferredSize();
            	
                int              rh = getRowHeight();
 
                if(rh > 0 && rh != prefSize.height)
                    prefSize.height = rh;
                if(size != null) {
                    size.x = getRowX(row, depth);
                    size.width = prefSize.width;
                    size.height = prefSize.height;
                }
                else {
                    size = new Rectangle(getRowX(row, depth), 0,
                                   prefSize.width, prefSize.height);
                }
 
                if(!leftToRight) {
                    size.x = lastWidth - size.width - size.x - 2;
                }
                return size;
            }
            // Not editing, use renderer.
            if(currentCellRenderer != null) {
                Component        aComponent;
 
                aComponent = currentCellRenderer.getTreeCellRendererComponent
                    (tree, value, tree.isRowSelected(row),
                     expanded, treeModel.isLeaf(value), row,
                     false);
                if(tree != null) {
                    // Only ever removed when UI changes, this is OK!
                    rendererPane.add(aComponent);
                    aComponent.validate();
                }
                Dimension        prefSize = aComponent.getPreferredSize();
 
                if(size != null) {
                    size.x = getRowX(row, depth);
                    size.width = //prefSize.width;
                                 lastWidth - size.x; // <*** the only change
                    size.height = prefSize.height;
                }
                else {
                    size = new Rectangle(getRowX(row, depth), 0,
                                         prefSize.width, prefSize.height);
                }
 
                if(!leftToRight) {
                    size.x = lastWidth - size.width - size.x - 2;
                }
                return size;
            }
            return null;
        }
    }
}
 
class PanelNodeRenderer implements TreeCellRenderer {
    JPanel panel;
    JLabel label;
    JLabel price;
 
    public PanelNodeRenderer() {
        panel = new JPanel(new BorderLayout());
        panel.setLayout(new FlowLayout());
        label = new JLabel();
        price=new JLabel();
        
     //JButton  a =new JButton("testing");
        
        
      //  label.setHorizontalAlignment(JLabel.EAST);
        panel.add(label);
        panel.add(price);
    }
 
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
                                                  boolean selected,
                                                  boolean expanded,
                                                  boolean leaf,
                                                  int row,
                                                  boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        PanelNode panelNode = (PanelNode)node.getUserObject();
        label.setText(panelNode.text);
        price.setText(panelNode.price);
        if(selected)
            panel.setBorder(BorderFactory.createLineBorder(
                                UIManager.getColor("Tree.selectionBorderColor")));
        else
            panel.setBorder(BorderFactory.createLineBorder(panelNode.borderColor));
        panel.setBackground(panelNode.background);
        return panel;
    }
}
 
class PanelNode {
    String text;
    String price;
    JButton addbut;
    Color borderColor;
    Color background;
    
    
    public JButton getAddbut() {
		return addbut;
	}


	public void setAddbut(JButton addbut) {
		this.addbut = addbut;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public PanelNode(String text, Color bc, Color bg) {
        this.text = text;
        borderColor = bc;
        background = bg;
    }
	public PanelNode(String text,String p, Color bc, Color bg) {
        this.text = text;
        this.price=p;
        borderColor = bc;
        background = bg;
        
    }
}

