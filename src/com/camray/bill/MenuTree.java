package com.camray.bill;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;

import com.camray.db.DBConnection;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class MenuTree extends JPanel {
    DemoArea demoArea;
    //JTextArea textArea;
    final static String newline = "\n";
    JTree tree;
    HashMap<String, MenuObject> itemlist;

    public JTree getTree() {
		return tree;
	}

	public HashMap<String, MenuObject> getItemlist() {
		return itemlist;
	}

	public void setItemlist(HashMap<String, MenuObject> itemlist) {
		this.itemlist = itemlist;
	}

	public void setTree(JTree tree) {
		this.tree = tree;
	}

	public MenuTree() {
        super(new GridBagLayout());
        itemlist=new HashMap<String, MenuObject>();
        GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;

        c.insets = new Insets(1, 1, 1, 1);
        demoArea = new DemoArea();
        gridbag.setConstraints(demoArea, c);
        add(demoArea);

        c.insets = new Insets(0, 0, 0, 0);
        /*textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(200, 75));
        gridbag.setConstraints(scrollPane, c);
        add(scrollPane);*/

        setPreferredSize(new Dimension(450, 450));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

   

    class DemoArea extends JScrollPane
                   implements TreeExpansionListener,
                              TreeWillExpandListener {
        Dimension minSize = new Dimension(100, 100);
        
        
    
        public DemoArea() {
            TreeNode rootNode = createNodes();
            tree = new JTree(rootNode);
            tree.addTreeExpansionListener(this);
            tree.addTreeWillExpandListener(this);

            setViewportView(tree);
        }
/**
 * Nodes for the Menu tree is created in the below consructors
 * @return
 */
        private TreeNode createNodes() {
            DefaultMutableTreeNode root;
            DefaultMutableTreeNode category;
            DefaultMutableTreeNode items;
            DefaultMutableTreeNode child;
            MenuObject mobj;
            DBConnection db=new DBConnection();
            root = new DefaultMutableTreeNode("MENU");
            Connection connection =
    				db.acquireConnection();
            String catquery="select distinct category from camry.menu_category_list ";
            String itemquery="select item_no,item_name,item_price from camry.menu where category=?";
            try {
    			PreparedStatement ps = connection.prepareStatement(catquery);
    			PreparedStatement psit = connection.prepareStatement(itemquery);
    			ResultSet rs = ps.executeQuery();
    			ResultSet rsit;
    			while (rs.next())
    			{
    				String a=rs.getString("CATEGORY");
		    		category = new DefaultMutableTreeNode(a);
		    		root.add(category);
		    		psit.setString(1,a);
		    		rsit=psit.executeQuery();
		    		while(rsit.next())
		    		{
		    		mobj=new MenuObject();
		    		mobj.setItemno(rsit.getString("ITEM_NO"));
		    		mobj.setItemname(rsit.getString("ITEM_NAME"));
		    		mobj.setItemprice(Integer.parseInt(rsit.getString("ITEM_PRICE")));
		    		itemlist.put(mobj.getItemname(), mobj);
		    			items=new DefaultMutableTreeNode(mobj.getItemname());
		    			category.add(items);
		    		}
    		
    		
    			}
    			connection.close();
    		} catch (SQLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            return root;
        }
    
        public Dimension getMinimumSize() {
            return minSize;
        }

        public Dimension getPreferredSize() {
            return minSize;
        }

        //Required by TreeWillExpandListener interface.
        public void treeWillExpand(TreeExpansionEvent e) 
                    throws ExpandVetoException {
           // saySomething("Tree-will-expand event detected", e);
            /*int n = JOptionPane.showOptionDialog(
                this, willExpandText, willExpandTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                willExpandOptions,
                willExpandOptions[1]);
            if (n == 0) {
                //User said cancel expansion.
                saySomething("Tree expansion cancelled", e);
                throw new ExpandVetoException(e);
            }*/
        }

        //Required by TreeWillExpandListener interface.
        public void treeWillCollapse(TreeExpansionEvent e) {
           // saySomething("Tree-will-collapse event detected", e);
        }

        // Required by TreeExpansionListener interface.
        public void treeExpanded(TreeExpansionEvent e) {
           // saySomething("Tree-expanded event detected", e);
        }

        // Required by TreeExpansionListener interface.
        public void treeCollapsed(TreeExpansionEvent e) {
           // saySomething("Tree-collapsed event detected", e);
        }
    }
}

    
