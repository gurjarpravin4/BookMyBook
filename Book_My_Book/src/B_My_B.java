import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class B_My_B {

	private JFrame frame;
	private JTextField bookName;
	private JTextField bookEdition;
	private JTextField bookPrice;
	private JTable table;
	private JTextField bookID;
	int id = 7;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B_My_B window = new B_My_B();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public B_My_B() {
		initialize();
		Connect();
		table_load();
	}
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	public void Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/bookmgmtsysdb", "root", "root");
			System.out.println("Database Connected!");
		}
		catch(ClassNotFoundException ex) {
			
		}
		catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public void table_load(){
		try{
		    pst = con.prepareStatement("select * from book");
		    rs = pst.executeQuery();
		    table.setModel(DbUtils.resultSetToTableModel(rs));
		}
		catch (SQLException e){
			e.printStackTrace();
		}
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setAlwaysOnTop(true);
		frame.setBounds(100, 100, 891, 538);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Book My Book");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 30));
		lblNewLabel.setBounds(244, 47, 384, 37);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Add a book!", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(30, 124, 384, 215);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name of the book : ");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1.setBounds(10, 31, 147, 35);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Edition : ");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(10, 92, 147, 35);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("Price : ");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(10, 157, 147, 35);
		panel.add(lblNewLabel_1_2);
		
		bookName = new JTextField();
		bookName.setBounds(157, 41, 198, 19);
		panel.add(bookName);
		bookName.setColumns(10);
		
		bookEdition = new JTextField();
		bookEdition.setBounds(157, 102, 96, 19);
		panel.add(bookEdition);
		bookEdition.setColumns(10);
		
		bookPrice = new JTextField();
		bookPrice.setBounds(157, 167, 96, 19);
		panel.add(bookPrice);
		bookPrice.setColumns(10);
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bName, bEdition, bPrice;
				bName = bookName.getText();
				bEdition = bookEdition.getText();
				bPrice = bookPrice.getText();
				
				try {
					pst = con.prepareStatement("insert into book(bookID, bookName, bookEdition, bookPrice)values(?,?,?,?)");
					pst.setLong(1, id);
					pst.setString(2, bName);
					pst.setString(3, bEdition);
					pst.setString(4, bPrice);
					pst.executeUpdate();
					bookName.setText("");
					bookEdition.setText("");
					bookPrice.setText("");
					id++;
					bookName.requestFocus();
					table_load();
					JFrame j=new JFrame();
					j.setAlwaysOnTop(true);
					j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					j.setVisible(true);
					j.setVisible(false);
					JOptionPane.showMessageDialog(j, "Book Added!");
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		saveButton.setBounds(56, 349, 96, 35);
		frame.getContentPane().add(saveButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Bye!");
				System.exit(0);
			}
		});
		exitButton.setBounds(177, 349, 96, 35);
		frame.getContentPane().add(exitButton);
		
		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bookName.setText("");
	            bookEdition.setText("");
	            bookPrice.setText("");
			}
		});
		clearButton.setBounds(304, 349, 96, 35);
		frame.getContentPane().add(clearButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(449, 122, 402, 261);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search by ID", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(30, 405, 384, 86);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Book ID : ");
		lblNewLabel_1_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_1_1_1.setBounds(10, 26, 147, 35);
		panel_1.add(lblNewLabel_1_1_1);
		
		bookID = new JTextField();
		bookID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				try {
			          
		            String id = bookID.getText();
		 
		                pst = con.prepareStatement("select bookName, bookEdition, bookPrice from book where bookID = ?");
		                pst.setString(1, id);
		                ResultSet rs = pst.executeQuery();
		 
		            if(rs.next()==true) {
		                String name = rs.getString(1);
		                String edition = rs.getString(2);
		                String price = rs.getString(3);
		                
		                bookName.setText(name);
		                bookEdition.setText(edition);
		                bookPrice.setText(price);
		                
		                
		            }  
		            else{
		            	bookName.setText("");
		            	bookEdition.setText("");
		            	bookPrice.setText("");
		            	JFrame j=new JFrame();
		            	j.setAlwaysOnTop(true);
		            	j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		            	j.setVisible(true);
		            	j.setVisible(false);
					    JOptionPane.showMessageDialog(j, "Book not found!");  
		            }
		        }
				catch (SQLException ex) {
		          ex.printStackTrace();
		        }
			}
		});
		bookID.setColumns(10);
		bookID.setBounds(157, 36, 96, 19);
		panel_1.add(bookID);
		
		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bName, bEdition, bPrice, bID;
				bName = bookName.getText();
				bEdition = bookEdition.getText();
				bPrice = bookPrice.getText();
				bID = bookID.getText();
				
				try {
					pst = con.prepareStatement("update book set bookName= ?, bookEdition=?, bookPrice=? where bookID =?");
					pst.setString(1, bName);
				    pst.setString(2, bEdition);
				    pst.setString(3, bPrice);
				    pst.setString(4, bID);
				    pst.executeUpdate();
				    bookName.setText("");
				    bookEdition.setText("");
				    bookPrice.setText("");
				    bookName.requestFocus();
				    table_load();
				    JFrame j=new JFrame();
					j.setAlwaysOnTop(true);
					j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					j.setVisible(true);
					j.setVisible(false);
				    JOptionPane.showMessageDialog(j, "Book Updated!");
				}
				 
				catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
		});
		updateButton.setBounds(449, 428, 96, 35);
		frame.getContentPane().add(updateButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String bID;
				bID  = bookID.getText();
				try {
				pst = con.prepareStatement("delete from book where bookID =?");
				            pst.setString(1, bID);
				            pst.executeUpdate();
				            bookName.setText("");
				            bookEdition.setText("");
				            bookPrice.setText("");
				            bookID.setText("");
				            bookName.requestFocus();
				            table_load();
				            JFrame j=new JFrame();
							j.setAlwaysOnTop(true);
							j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							j.setVisible(true);
							j.setVisible(false);
				            JOptionPane.showMessageDialog(j, "Book Deleted!");
				}
				 
				            catch (SQLException e1) {
				e1.printStackTrace();
				}
			}
		});
		deleteButton.setBounds(576, 428, 96, 35);
		frame.getContentPane().add(deleteButton);
		System.out.println("Application initialised!");
	}
}

