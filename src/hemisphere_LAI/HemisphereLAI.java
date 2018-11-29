package hemisphere_LAI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileFilter;

public class HemisphereLAI extends JFrame implements ActionListener{  

	JMenuBar cdlan=null;
	JMenu cd1=null;
	JMenu cd2=null;
	JMenuItem cdx11=null;
	JMenuItem cdx12=null;
	JMenuItem cdx21=null;
	JMenuItem cdx22=null;
	JLabel image1=null;
	//JLabel image2=null;
	JTextField jtf=null;
	String filePath1="C:/Users/fengshow/Desktop/字.jpeg";
	String filePath2="C:/Users/fengshow/Desktop/字.jpeg";
	
	public static void main(String[] args) {
		HemisphereLAI wind1=new HemisphereLAI();
	}
	
	//构造方法
	public HemisphereLAI(){
		
		image1=new JLabel("111",SwingConstants.CENTER);
		//image2=new JLabel(new ImageIcon(filePath2));
		
		
		cdlan=new JMenuBar();
		cd1=new JMenu("文件(F)");
		cd1.setMnemonic('F');
		cd2=new JMenu("编辑(E)");
		cd2.setMnemonic('E');
		cdx11=new JMenuItem("打开(O)");
		cdx12=new JMenuItem("保存(S)");
		cdx21=new JMenuItem("孔隙度计算(C)");
		cdx22=new JMenuItem("其他");
		
		cdx11.addActionListener(this);
		cdx11.setActionCommand("openFile");
		cdx12.addActionListener(this);
		cdx12.setActionCommand("saveFile");
		cdx21.addActionListener(this);
		cdx21.setActionCommand("calcImage");
		
		cd1.add(cdx11);
		cd1.add(cdx12);
		cd2.add(cdx21);
		cd2.add(cdx22);
		cdlan.add(cd1);
		cdlan.add(cd2);
		
		
		//this.add(image2);
		
		this.setJMenuBar(cdlan);
		this.setTitle("半球摄影LAI算法");
		
		//
		this.setSize(600,500);
		this.setLocation(300, 280);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getActionCommand().equals("openFile")){
			////////////////////////////////////////////////
			  // 创建文件选择器
			  JFileChooser fileChooser = new JFileChooser();

			  // 设置当前目录
			  fileChooser.setCurrentDirectory(new File("."));
			  fileChooser.setAcceptAllFileFilterUsed(false);

			  final String[][] fileENames = { { ".java", "JAVA源程序 文件(*.java)" },
			          { ".doc", "MS-Word 2003 文件(*.doc)" },
			          { ".xls", "MS-Excel 2003 文件(*.xls)" }
			           };
			  
			  // 显示所有文件  
			  fileChooser.addChoosableFileFilter(new FileFilter() {

			   public boolean accept(File file) {

			    return true;
			   }

			   public String getDescription() {

			    return "所有文件(*.*)";
			   }
			  });
			  
			  // 循环添加需要显示的文件
			  for (final String[] fileEName : fileENames) {
			   
			   fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			 
			    public boolean accept(File file) {
			 
			     if (file.getName().endsWith(fileEName[0]) || file.isDirectory()) {
			 
			      return true;
			     }
			 
			     return false;
			    }
			 
			    public String getDescription() {
			 
			     return fileEName[1];
			    }
			 
			   });
			  }
			  
			  fileChooser.showDialog(null, null);
			  //创建文件选择器
		////////////////////////////////////////////////
			
		}
		
	}
}
